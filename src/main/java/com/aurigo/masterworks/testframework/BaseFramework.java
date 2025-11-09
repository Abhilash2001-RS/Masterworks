package com.aurigo.masterworks.testframework;

import com.aurigo.masterworks.testframework.webUI.BasePage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;

public class BaseFramework {

    public static final String userDir = System.getProperty("user.dir");
    protected static ExtentSparkReporter htmlReporter;
    protected static ExtentReports report;
    private static Logger logger = LogManager.getLogger(BaseFramework.class);
    private static final Map<Integer, ExtentTest> extentTestMap = new HashMap<>();
    private static final HashMap<WebDriver, HashMap<String, Object>> instanceMap = new HashMap<>();
    protected static int newInstancesCount = 0;
    protected ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static synchronized ExtentTest logger() {
        return extentTestMap.get((int) (Thread.currentThread().getId()));
    }

    public synchronized <TPage extends BasePage> TPage GetInstance(Class<TPage> page, WebDriver driver) {
        try {
            if (driver == null) {
                logger().warning(String.format("Driver object is NULL for %s class.", page.getName()));
                return null;
            }
            Object instanceToReturn;
            if (instanceMap.containsKey(driver)) {
                if (instanceMap.get(driver).containsKey(page.getName())) {
                    instanceToReturn = instanceMap.get(driver).get(page.getName());
                } else {
                    newInstancesCount++;
                    instanceToReturn = page.getDeclaredConstructor(WebDriver.class).newInstance(driver);
                    instanceMap.get(driver).put(page.getName(), instanceToReturn);
                }
            } else {
                newInstancesCount++;
                instanceToReturn = page.getDeclaredConstructor(WebDriver.class).newInstance(driver);
                HashMap<String, Object> driverMap = new HashMap<>();
                driverMap.put(page.getName(), instanceToReturn);
                instanceMap.put(driver, driverMap);
            }
            return (TPage) instanceToReturn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static synchronized ExtentTest startTest(String className, String description) {
        ExtentTest test = report.createTest(className, description);
        extentTestMap.put((int) (Thread.currentThread().getId()), test);
        return test;
    }

}
