package com.aurigo.masterworks.testframework.utilities.listeners;

import com.aurigo.masterworks.testframework.utilities.ExceptionHandler;
import com.aurigo.masterworks.testframework.webUI.constants.Constants;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Test;
import org.testng.util.Strings;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

public class ConsolidatedReportListener implements ITestListener {

    private static final String FILE_NAME = "ConsolidatedReport.html";
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static Instant testStartTime;
    private static Instant testEndTime;
    private static Date today = new Date();
    private static SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
    private static String strDate = formatter.format(today);
    private static String OUTPUT_FOLDER = System.getProperty("user.dir") +"/" + Constants.REPORTS_FOLDER_PATH + strDate + "/";
    private static Map<String, ExtentTest> testMap = new HashMap<>();
    private static final Logger logger = LogManager.getLogger(ConsolidatedReportListener.class);
    private static ExtentReports extent = init();

    private static ExtentReports init(){
        var logFilePath = System.getProperty("LogFilePath");
        if(Strings.isNullOrEmpty(logFilePath)){
            System.setProperty("LogFilePath", OUTPUT_FOLDER);
        }else{
            OUTPUT_FOLDER = logFilePath;
        }
        //Converts the OUTPUT_FOLDER string (a folder path) into a Path object (part of java.nio.file API).
        Path path = Paths.get(OUTPUT_FOLDER);
        if(!Files.exists(path)){
           try{
               Files.createDirectories(path);
           }catch (IOException e){
               ExceptionHandler.log(e);
           }
        }
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(OUTPUT_FOLDER + FILE_NAME);
        formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm a");
        strDate = formatter.format(today);
        htmlReporter.config().setDocumentTitle("Consolidated Report");
        htmlReporter.config().setReportName("Consolidated Report" + strDate);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setReportUsesManualConfiguration(true);
        return extent;
    }


    public synchronized void onStart(ITestResult result) {
        testStartTime = Instant.now();
        logger.info("Test suite started");
    }

    @Override
    public synchronized void onFinish(ITestContext context) {
        testEndTime = Instant.now();
        logger.info("Test suite is ending!!");
        extent.flush();
        test.remove();
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        if(testMap.containsKey(methodName)){
            extent.removeTest(testMap.get(methodName));
        }
        logger.info(methodName + " started!" + "Start time: " + getTime(result.getStartMillis()));
        ExtentTest extentTest = extent.createTest(result.getMethod().getConstructorOrMethod().getMethod().getDeclaredAnnotation(Test.class).testName(),
                result.getMethod().getConstructorOrMethod().getMethod().getDeclaredAnnotation(Test.class).description());

        extentTest.assignCategory(result.getMethod().getConstructorOrMethod().getMethod().getDeclaredAnnotation(Test.class).suiteName());
        testMap.put(methodName, extentTest);
        test.set(extentTest);
        test.get().getModel().setStartTime(getTime(result.getStartMillis()));

    }

    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        logger.info(result.getMethod().getMethodName() +  " passed!" + "End Time: "+ getTime(result.getEndMillis()));
        test.get().pass("Test passed");
        test.get().getModel().setStartTime(getTime(result.getStartMillis()));
    }

    @Override
    public synchronized void onTestFailure(ITestResult result) {
        logger.error(result.getMethod().getMethodName() + "failed!" + "End Time: " + getTime(result.getEndMillis()));
        test.get().fail("Test Failed");
        try {
            var message = result.getThrowable().getMessage();
            logger.error(message);
            logger.error(Arrays.toString(result.getThrowable().getStackTrace()));
            test.get().fail(result.getThrowable().getMessage());
            test.get().fail(result.getThrowable());
        } catch (Exception e) {
            logger.error(String.format("Exception occurred while fetching error logs:%s", e.getMessage()));
        }
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
    }


    private Date getTime(long millis){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }




}
