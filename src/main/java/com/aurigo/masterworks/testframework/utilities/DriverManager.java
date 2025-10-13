package com.aurigo.masterworks.testframework.utilities;

import org.openqa.selenium.WebDriver;

public class DriverManager {

    private static final ThreadLocal<WebDriver> currentDriver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return currentDriver.get();
    }

    public static void setDriver(WebDriver driver) {
        currentDriver.set(driver);
    }
}
