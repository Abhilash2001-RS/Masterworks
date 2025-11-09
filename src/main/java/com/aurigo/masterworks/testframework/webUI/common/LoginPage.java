package com.aurigo.masterworks.testframework.webUI.common;

import com.aurigo.masterworks.testframework.utilities.LocatorUtil;
import com.aurigo.masterworks.testframework.webUI.BasePage;
import com.google.common.base.Stopwatch;
import lombok.extern.java.Log;
import org.apache.commons.lang3.time.StopWatch;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class LoginPage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(LoginPage.class);
    private By emailID;
    private By passwordField;
    private By loginButton;
    private By errorMessage;
    private By buildVersion;
    private By emailTextField;

    public LoginPage(WebDriver driver){
        super(driver);
        var locators = LocatorUtil.getLocators("LoginPage.json");
        emailID =locators.get("emailId");
        passwordField = locators.get("passwordField");
        loginButton = locators.get("loginButton");
        errorMessage = locators.get("errorMessage");
    }

    public boolean doLogin(String username, String password){
        boolean isLoaded;
        Stopwatch stopwatch = Stopwatch.createStarted();
        try{
            login(username, password);
            waitHelper.waitForPageToLoad();
        }catch (Exception e){
            stopwatch.stop();
            logger().fail(String.format("Page load failed after : %d seconds", stopwatch.elapsed(TimeUnit.SECONDS)));
            throw e;
        }
        isLoaded = getPage(LandingPage.class).isPageLoaded();
        stopwatch.stop();
        logger().pass(String.format("Time taken for login: %d seconds", stopwatch.elapsed(TimeUnit.SECONDS)));
        return isLoaded;
    }

    public void login(String username, String password) {
        logger().info("Logging in as: " + username);
        waitHelper.waitForElementPresent(emailID);
        elementHelper.doSendKeys(emailID, username);
        waitHelper.waitForElementPresent(passwordField);
        elementHelper.doSendKeys(passwordField, password);
        elementHelper.doClick(loginButton);
    }
    public String getErrorMessage(){
        return elementHelper.getElement(errorMessage).getText();
    }

    /**
     * Validate the login field
     *
     * @return true if username and passwords are present
     */
    public boolean validateLoginFields(){
        boolean userName = getPage(Validations.class).verifyElementExists(emailID);
        boolean passwordCheck = getPage(Validations.class).verifyElementExists(passwordField);
        return (userName && passwordCheck);

    }
}
