package com.aurigo.masterworks.testframework.webUI.common;

import com.aurigo.masterworks.testframework.utilities.LocatorUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LandingPage extends RibbonMenu{

    private static final Logger log = LoggerFactory.getLogger(LandingPage.class);
    private WebDriver driver;
    private By enterpriseDashboardTab;
    private By usernameLink;
    private By logoutLink;
    private By viewProfileLink;
    private By changePasswordLink;
    private By helpLink;
    private By aboutLink;
    private By aboutPopupContainer;
    private By aboutPopupLogo;
    private By aboutPopupBuildVersion;
    private By aboutPopupCloseButton;


    public LandingPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        var locators = LocatorUtil.getLocators("LandingPage.json");
        enterpriseDashboardTab = locators.get("enterpriseDashboardTab");
        usernameLink = locators.get("usernameLink");
        logoutLink = locators.get("logoutLink");
        viewProfileLink = locators.get("viewProfileLink");
        changePasswordLink = locators.get("changePasswordLink");
        helpLink = locators.get("helpLink");
        aboutLink = locators.get("aboutLink");
        aboutPopupContainer = locators.get("aboutPopupContainer");
        aboutPopupLogo = locators.get("aboutPopupLogo");
        aboutPopupBuildVersion = locators.get("aboutPopupBuildVersion");
        aboutPopupCloseButton = locators.get("aboutPopupCloseButton");
    }

    public void navigateTo(){
        logger().info("Navigating to Home Page");
        navigation.navigateToModule("Home", enterpriseDashboardTab );
    }

    public void logout(){
        navigation.switchFrameToDefault();
        if(!driver.getCurrentUrl().toLowerCase().equals("login"));{
            waitHelper.waitForPageToLoad(usernameLink);
            elementHelper.doClickNoScroll(usernameLink);
            elementHelper.waitForPageToLoad(logoutLink);
            elementHelper.doClickNoScroll(logoutLink);
        }
    }

    public String getLoggedInUserName(){
        navigation.switchFrameToDefault();
        String loggedInUserName;
        waitHelper.waitForElementClickable(usernameLink);
        loggedInUserName = elementHelper.doGetText(usernameLink);
        return loggedInUserName;
    }
    /**
     * Method to check whether a user has already logged in
     *
     * @return true if a user is logged in
     */
    public boolean isLoggedIn(){
        navigation.switchFrameToDefault();
        return elementHelper.isElementDisplayed(usernameLink);
    }

    public boolean isPageLoaded(){
        navigation.switchFrameToContent();
        return elementHelper.isElementDisplayed(enterpriseDashboardTab);
    }

}
