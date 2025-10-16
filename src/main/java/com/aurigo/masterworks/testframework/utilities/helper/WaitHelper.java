package com.aurigo.masterworks.testframework.utilities.helper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitHelper {

    private WebDriver driver;
    private static int waitTimeout = 60; //Default value for wait, It gets override by properties file value.
    private WebDriverWait wait;

    public WaitHelper(WebDriver driver)
    {
        this.driver = driver;
        try{
            waitTimeout =  Integer.parseInt(EnvironmentHelper.getPropertyValue("waitTimeout"));
            wait = new WebDriverWait(driver, Duration.ofSeconds(waitTimeout));
        }catch (Exception e)
        {

            wait = new WebDriverWait(driver, Duration.ofSeconds(waitTimeout));
        }
    }


}
