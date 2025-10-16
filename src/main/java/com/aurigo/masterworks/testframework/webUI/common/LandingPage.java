package com.aurigo.masterworks.testframework.webUI.common;

import com.aurigo.masterworks.testframework.utilities.LocatorUtil;
import org.openqa.selenium.WebDriver;

public class LandingPage extends RibbonMenu{

    private WebDriver driver;

    public LandingPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
        var locators = LocatorUtil.getLocators("LandingPage.json");

    }

    public void navigateTo()
    {
        logger().info("Navigating to Home page");
        navigation.navigateToModule("Home", null);
    }
}
