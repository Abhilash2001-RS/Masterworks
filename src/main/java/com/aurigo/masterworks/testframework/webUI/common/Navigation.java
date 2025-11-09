package com.aurigo.masterworks.testframework.webUI.common;

import com.aurigo.masterworks.testframework.utilities.ExceptionHandler;
import com.aurigo.masterworks.testframework.utilities.JavaScriptUtil;
import com.aurigo.masterworks.testframework.utilities.LocatorUtil;
import com.aurigo.masterworks.testframework.webUI.BasePage;
import com.aurigo.masterworks.testframework.webUI.constants.enums.RegexStrings;
import org.jspecify.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Navigation extends BasePage {

    private WebDriver driver;
    public By xpathTreeExpandAllBtn;
    private By formTreeSearchBar;
    public By clearSearchButton;
    private By idContentFrame;
        private By recentProjects;
    private String xpathForPageTabTemplate = ".//ul[@id='MenuTabsUL']//span[.='%s']";
    private String xpathForPageTabTemplateFacelift = ".//ul[@id='MenuTabsUL']//a[@title='%s']";

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

    public boolean switchToSpecificFrame(By locator) {
        try {
            waitHelper.waitForFrameAvailableAndSwitchToIt(locator);
            return true;
        } catch (Exception e) {
            ExceptionHandler.logAndContinueWebDriverExceptions(e, String.format("Exception occurred while switching to %s frame",
                    elementHelper.getLocatorAsString(locator)));
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
        var finalXpath = getXpathForLeftPaneTreeItem(treePath);
        var treeItemElement =  elementHelper.getElement(By.xpath(finalXpath));
        elementHelper.scrollToView(treeItemElement);
        waitHelper.waitForElementClickable(treeItemElement);
        JavaScriptUtil.clickElementByJS(treeItemElement, driver);
     }

    /**
     * Method to check if element is present in tree
     *
     * @param treePath tree path to traverse
     * @return true if element is enabled in tree
     */
     public boolean folderIsPresentInTree(String treePath)
     {
        waitHelper.waitForPageToLoad();
        logger().info("Navigating to the tree item: " + treePath);
        switchFrameToDefault();
        expandTreeList();
        return elementHelper.isElementEnabled(By.xpath(getXpathForLeftPaneTreeItem(treePath)));
     }

    public String getXpathForLeftPaneTreeItem(String treePath) {
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
     public List<String> getFolderItemsInTree(String  folderName) {
         List<String> items = new ArrayList<>();
         var eleList =  elementHelper.getElements(By.xpath("//nobr[.='" + folderName + "']/../../ul/li"));
         eleList.forEach(l-> items.add(l.findElement(By.tagName("nobr")).getText()));

         return items;
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

     public String getXPathForLeftPaneTreeItem(String treePath) {
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

     public boolean navigateToModule(String moduleName, By moduleDashboardLocator) {
        switchFrameToDefault();
        if(isModuleDisplayed(moduleName)){
            navigateToModulePageByName(moduleName);
            waitHelper.waitForPageToLoad();
            switchFrameToContent();
            waitHelper.waitForPageTabHeaderToBeClickable();
            return true;
        }
        return false;
     }

     public boolean isModuleDisplayed(String tabName) {
        var xpathForPageTab = String.format(xpathForPageTabTemplateFacelift, tabName);
        return elementHelper.isElementDisplayed(By.xpath(xpathForPageTab));
     }

     public void navigateToModulePageByName(String pageName){
        logger().info("Navigating to Page: " + pageName);
        getPage(Navigation.class).switchFrameToDefault();
        waitHelper.waitForPageToLoad();
        waitHelper.waitForElementClickable(getPageElementFromName(pageName));
        elementHelper.doClick(getPageElementFromName(pageName));
        switchFrameToContent();
        waitHelper.waitForPageTabHeaderToBeClickable();
     }

     public WebElement getPageElementFromName(String pageName) {
        var xpathForPageTab =  String.format(xpathForPageTabTemplateFacelift, pageName);
        return elementHelper.getElement(By.xpath(xpathForPageTab));
     }

    private WebElement getPageElementByName(String pageName) {
        var xpathForPageTab = String.format(xpathForPageTabTemplateFacelift, pageName);
        return elementHelper.getElement(By.xpath(xpathForPageTab));
    }

    public void getRecentProjectsList(){
        List<String> projects = new ArrayList<>();
        var eleList = elementHelper.getElements(recentProjects);
        eleList.forEach(l -> projects.add(elementHelper.doGetText(l)));
    }

    public void refreshPage() {
        logger().info("Refreshing the Page");
        driver.navigate().refresh();
        switchFrameToContent();
        waitHelper.waitForPageTabHeaderToBeClickable();
    }

    public void clickOnExpandAll()
    {
        elementHelper.doClick(xpathTreeExpandAllBtn);
    }

    public void navigateBackToPreviousPage()
    {
        logger().info("Navigating back to the Previous page");
        driver.navigate().back();
        waitHelper.waitForPageToLoad();
        switchFrameToContent();
    }

    public String getURL() {
        return driver.getCurrentUrl();
    }

    public void switchToDifferentWindow() {
        String parentWindow = driver.getWindowHandle();
        Set<String> windowHandles = driver.getWindowHandles();
        for (String window : windowHandles) {
            if (!window.equals(parentWindow)) {
                driver.switchTo().window(window);
            }
        }
    }

    public void switchToParentWindow(){
        Set<String> windows = driver.getWindowHandles();
        driver.switchTo().window(windows.iterator().next());
        switchFrameToContent();
    }



}

