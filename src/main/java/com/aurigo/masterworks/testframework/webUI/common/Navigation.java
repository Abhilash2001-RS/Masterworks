package com.aurigo.masterworks.testframework.webUI.common;

import com.aurigo.masterworks.testframework.utilities.LocatorUtil;
import com.aurigo.masterworks.testframework.webUI.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.aurigo.masterworks.testframework.webUI.BasePage;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;

public class Navigation extends BasePage {

    private WebDriver driver;
    By idContentFrame;
    By xpathTreeExpandAllBtn;
    By recentProjects;
    By formTreeSearchBar;
    By clearSearchButton;

    public Navigation(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
        HashMap<String, By> locators = LocatorUtil.getLocators("Navigation.json");
        idContentFrame = locators.get("idContentFrame");
        xpathTreeExpandAllBtn = locators.get("xpathTreeExpandAllBtn");
        recentProjects =  locators.get("recentProjects");
        formTreeSearchBar =  locators.get("formTreeSearchBar");
        clearSearchButton  = locators.get("clearSearchButton");
    }

    public boolean navigateToFormInLeftPaneTree(String treePath) {
        try {
            logger().info("Navigating to the tree item: " + treePath);
            clickOnFormInLeftPaneTree(treePath);
            waitHelper.waitForPageToLoad();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickOnFormInLeftPaneTree()
    {
        waitHelper.waitForPageToLoad();
    }



    public void navigateToFOrmInLeftPaneTree(String treePath)
    {
        try{
            logger().info("Navigating to the tree item: " + treePath);
            clickOnFormInLeftPaneTree("");
            waitHelper.waitForPageToLoad();
        }
    }
}

