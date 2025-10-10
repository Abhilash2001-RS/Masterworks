package com.aurigo.masterworks.testframework.masterworks.tests;

import com.aurigo.masterworks.testframework.webUI.constants.Constants;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.util.Strings;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseTest {

    @BeforeSuite()
    public void beforeSuite()
    {

    }


    @BeforeMethod(alwaysRun = true)
    public void beforeMethod()
    {
        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        String startDate = formatter.format(today);

        String logFilePath = System.getProperty("LogFilePath");
        if (Strings.isNullOrEmpty(logFilePath)) {
            logFilePath = Paths.get(System.getProperty("user.dir"), Constants.REPORTS_FOLDER_PATH, startDate).toString();
            System.setProperty("LogFilePath", logFilePath);
        }

        String logFileLocation = Paths.get(logFilePath, "AutomationReport.html").toString();

        // Reformatting the date for display purposes
        formatter = new SimpleDateFormat("dd MMMM yyyy");
        startDate = formatter.format(today);  // Pass the 'today' Date object

    }
}
