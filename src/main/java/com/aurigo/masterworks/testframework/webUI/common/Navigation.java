package com.aurigo.masterworks.testframework.webUI.common;

import com.aurigo.masterworks.testframework.utilities.ExceptionHandler;
import com.aurigo.masterworks.testframework.utilities.JavaScriptUtil;
import com.aurigo.masterworks.testframework.utilities.LocatorUtil;
import com.aurigo.masterworks.testframework.webUI.BasePage;
import com.aurigo.masterworks.testframework.webUI.constants.enums.RegexStrings;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;

public class Navigation extends BasePage {

    private WebDriver driver;
    public By xpathTreeExpandAllBtn;
    private By formTreeSearchBar;
    public By clearSearchButton;
    private By idContentFrame;
    private By recentProjects;

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

     public boolean navigateToFormInLeftPaneTree(String treePath)
     {
        try{
            logger().info("Navigating to the tree item: " + treePath);
            clickOnFormInLeftPaneTree(treePath);
            waitHelper.waitForPageToLoad();
            return true;
        }catch (Exception e){
            ExceptionHandler.logAndContinueWebDriverExceptions(e, "Exception occurred while navigating to the tree item: " + treePath);
            return false;
        }
     }

     public void clickOnFormInLeftPaneTree(String treePath) {
        waitHelper.waitForPageToLoad();
        logger().info("Clicking on the form in left pane tree: " + treePath);
        switchFrameToDefault();
        expandTreeList();
        var levels = treePath.split(RegexStrings.forwardSlash.getValue());
        var formToClick =  levels[levels.length-1];
        elementHelper.doSendKeys(formTreeSearchBar, formToClick);
        var finalXpath = getXPathFolLeftPaneTreeItem(treePath);
        var treeItemElement =  elementHelper.getElement(By.xpath(finalXpath));
        elementHelper.scrollToView(treeItemElement);
        waitHelper.waitForElementClickable(treeItemElement);
        JavaScriptUtil.clickElementByJS(treeItemElement, driver);
     }

     public boolean folderIsPresentInTree(String treePath)
     {
        waitHelper.waitForPageToLoad();
        logger().info("Navigating to the tree item: " + treePath);
        switchFrameToDefault();
        expandTreeList();
        return elementHelper.isElementEnabled(By.xpath(getXPathFolLeftPaneTreeItem(treePath)));
     }

     public void getXpathForLeftPaneTreeItem(){

     }

     public void expandTreeList() {
        switchFrameToDefault();
        waitHelper.waitForElementClickable(xpathTreeExpandAllBtn);
        elementHelper.doClick(xpathTreeExpandAllBtn);
     }

     public boolean switchFrameToDefault()
     {
        try{
            driver.switchTo().defaultContent();
            return true;
        }catch (Exception e){
            ExceptionHandler.logAndContinueWebDriverExceptions(e, "Exception occurred while switching to default frame");
            return false;
        }
     }

     public boolean switchFrameToContent()
     {
        try{
            if(JavaScriptUtil.getCurrentFrame(driver).equalsIgnoreCase("contentFrame")){
                return true;
            }
            waitHelper.waitForFrameAvailableAndSwitchToIt(idContentFrame);
            return true;
        }catch (Exception e){
            ExceptionHandler.logAndContinueWebDriverExceptions(e, "Exception occurred while switching to content frame");
            return false;
        }
     }

     public String getXPathFolLeftPaneTreeItem(String treePath) {
        var xpathOfTreeItem = new StringBuilder();
        var levels = treePath.split(RegexStrings.forwardSlash.getValue());
        xpathOfTreeItem.append("//div[@id='treeWrapper']//nobr[.='").append(levels[0]).append("']/..");
         if ((levels.length == 2) && (levels[0].equals(levels[1]))) {
             xpathOfTreeItem.append("/../..//*[not(@aria-level='1')]/*[.='").append(levels[0]).append("']/..");
         } else if (levels.length > 1) {
             for (int i = 1; i < levels.length; i++) {
                 xpathOfTreeItem.append("/..//*[.='").append(levels[i]).append("']/..");
             }
         }
         return xpathOfTreeItem.toString();
     }



}

