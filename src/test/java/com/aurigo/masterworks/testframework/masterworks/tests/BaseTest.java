package com.aurigo.masterworks.testframework.masterworks.tests;

import com.aurigo.masterworks.testframework.BaseFramework;
import com.aurigo.masterworks.testframework.utilities.ExceptionHandler;
import com.aurigo.masterworks.testframework.utilities.annotations.TestInfo;
import com.aurigo.masterworks.testframework.utilities.DriverManager;
import com.aurigo.masterworks.testframework.utilities.helper.EnvironmentHelper;
import com.aurigo.masterworks.testframework.utilities.helper.ScreenshotHelper;
import com.aurigo.masterworks.testframework.webUI.BasePage;
import com.aurigo.masterworks.testframework.webUI.constants.Constants;
import com.aurigo.masterworks.testframework.webUI.pages.LoginPage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.util.Strings;

import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;


public class BaseTest extends BaseFramework
{
    protected WebDriver driver;
    private static boolean requiresWebDriver = false;
    private static final Logger logger = LogManager.getLogger(BaseTest.class);
    public LoginPage loginPage;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite()
    {
        EnvironmentHelper.loadProperties();
        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        String strDate = formatter.format(today);
        String logFilePath = System.getProperty("LogFilePath");
        if (Strings.isNullOrEmpty(logFilePath)) {
            logFilePath = Paths.get(System.getProperty("user.dir"), Constants.REPORTS_FOLDER_PATH, strDate, "/").toString();
            System.setProperty("LogFilePath", logFilePath);
        }
        String logFileLocation = Paths.get(logFilePath, "AutomationReport.html").toString();
        formatter = new SimpleDateFormat("dd MMMM yyyy");
        strDate = formatter.format(today);

        htmlReporter = new ExtentSparkReporter(logFileLocation);
        htmlReporter.config().setDocumentTitle("Automation Report " + strDate);
        htmlReporter.config().setReportName("Automation Report " + strDate);
        htmlReporter.config().setTheme(Theme.STANDARD);

        report = new ExtentReports();
        report.attachReporter(htmlReporter);
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method)
    {
        var suiteName = method.getAnnotation(Test.class).suiteName();

        String browserName = EnvironmentHelper.getPropertyValue("browser");
        if(browserName.equalsIgnoreCase("chrome"))
        {
            driver = new ChromeDriver();
            driver.get(EnvironmentHelper.getPropertyValue("url"));
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }

        loginPage = new LoginPage(driver);

        ExtentTest extentTest = startTest(this.getClass().getSimpleName(), this.getClass().getName());
        test.set(extentTest);
        extentTest.assignCategory(suiteName);
    }

    public WebDriver getDriver()
    {
        return this.driver;
    }

    public <TPage extends BasePage> TPage getPage(Class<TPage> page) {
        return GetInstance(page, driver);
    }


    @AfterMethod(alwaysRun = true)
    public synchronized void afterMethod(ITestResult result)
    {
        WebDriver driver = DriverManager.getDriver();
        String screenShotBase64String = "";

        String comment = null;
        if (result.getStatus() == ITestResult.FAILURE) {
            comment = result.getThrowable().getMessage();
            if (requiresWebDriver && driver != null) {
                screenShotBase64String = GetInstance(ScreenshotHelper.class, driver).takeFullScreenshotAndReturnBase64String(comment);
            }
            ExceptionHandler.log(result.getThrowable());
            logger().fail("Test failed");

        } else if (result.getStatus() == ITestResult.SUCCESS) {
            logger().pass("Test passed");
        } else
        {
            try {
                comment = result.getThrowable().getMessage();
                logger().skip(comment);
            } catch (Exception ex) {
                logger.error(String.format("Exception occurred while fetching error logs for a skipped test:%s", ex.getMessage()));
            }
            logger().skip("Test skipped");
            if (requiresWebDriver && driver != null) {
                screenShotBase64String = GetInstance(ScreenshotHelper.class, driver).takeFullScreenshotAndReturnBase64String(comment);

            }
        }
        flushReport();
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        if (driver != null) {
            driver.quit();
        }
       flushReport();
    }

    private static void flushReport() {
        try {
            report.flush();
        } catch (Exception ex) {
            logger.error("Error occurred while flushing the report -" + ex.getMessage());
        }
    }



}
