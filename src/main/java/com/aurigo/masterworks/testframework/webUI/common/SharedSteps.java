package com.aurigo.masterworks.testframework.webUI.common;

import com.aurigo.masterworks.testframework.webUI.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SharedSteps extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(SharedSteps.class);

    public SharedSteps(WebDriver driver) {
        super(driver);
    }

    /**
     * Login to Masterworks and return true for a successful login
     *
     * @param username - username for login
     * @param password - password
     * @return true, if login is successful
     */
    @Step("Login to Masterworks")
    public boolean login(String username, String password) {
        if (getPage(LandingPage.class).isLoggedIn() && getPage(LandingPage.class).getLoggedInUserName().equalsIgnoreCase(username)) {
            logger().info("Already Logged in as " + getPage(LandingPage.class).getLoggedInUserName());
            return true;
        }
        if (getPage(LandingPage.class).isLoggedIn() && !getPage(LandingPage.class).getLoggedInUserName().equalsIgnoreCase(username)) {
            logOut();
        }
        return getPage(LoginPage.class).doLogin(username, password);
    }

    /**
     * Method to perform logout from the application
     */
    @Step("Logout the MasterWorks")
    public void logOut(){
        logger().info("logging out");
        waitHelper.waitForPageToLoad();
        getPage(LandingPage.class).logout();
    }

    public boolean isModuleDisplayedInLeftPaneTree(String moduleName){
        getPage(Navigation.class).switchFrameToDefault();
        boolean isModuleDisplayed =  getPage(Navigation.class).isModuleDisplayed(moduleName);
        getPage(Navigation.class).switchFrameToContent();
        return isModuleDisplayed;
    }
}
