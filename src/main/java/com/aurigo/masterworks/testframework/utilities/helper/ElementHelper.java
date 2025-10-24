package com.aurigo.masterworks.testframework.utilities.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class ElementHelper extends WaitHelper {

    private WebDriver driver;
    private Actions actions;
    private static final Logger logger = LogManager.getLogger(ElementHelper.class);

    public ElementHelper(WebDriver driver) {
        super(driver);
        this.driver = driver;
        actions = new Actions(driver);
    }

    public WebElement getElement(By locator) {
        waitForElementPresent(locator);
        WebElement element = driver.findElement(locator);
        return element;
    }

    public List<WebElement> getElements(By locator) {
        List<WebElement> elements = driver.findElements(locator);
        return elements;
    }

    public void doClick(By locator) {
        try {
            var element = getElement(locator);
            if (!isElementDisplayed(locator)) {
                scrollToView(element);
            }
            element.click();
        } catch (Exception e) {
            logger().info("Locator not found : " + locator);
            logger().info("Some exception occurred while clicking on webelement " + e);
            throw e;
        }
    }

    public void doClickNoScroll(By locator) {
        try {
            var element = getElement(locator);
            element.click();
        } catch (Exception e) {
            logger().info("Locator not found : " + locator);
            logger().info("Some exception occurred while clicking on webelement " + e);
            throw e;
        }
    }

    public boolean isElementDisplayed(By locator) {
        try {
            var element = driver.findElement(locator);
            boolean isDisplayed = element.isDisplayed();
            logger().info("IsElement " + getLocatorAsString(locator) + isDisplayed);
            return isDisplayed;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void doClickWithRetry(By locator) {
        int maximumRetryAttempts = 3;//Should come from config.properties
        boolean actionCompleted;
        do {
            --maximumRetryAttempts;
            try {
                var element = getElement(locator);
                scrollToView(element);
                element.click();
                actionCompleted = true;
            } catch (Exception e) {
                actionCompleted = false;
                logger().info("Locator not found : " + locator);
                logger().info("Some exception occurred while clicking on webelement " + e);
                if (maximumRetryAttempts <= 0)
                    throw e;
            }
        }
        while (actionCompleted);
    }

    public String getLocatorAsString(By locator) {
        return locator.toString().substring(locator.toString().indexOf(":") + 1).trim();
    }

    public void scrollToView(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(false);", element);
        } catch (Exception e) {
            logger.info("Unable to scroll to view");
        }
    }

    public void doClickCheckboxLabelByJS(By checkboxLocator) {
        try {
            var checkbox = getElement(checkboxLocator).findElement(By.xpath("./following-sibling::label"));
            scrollToView(checkbox);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", checkbox);
        }catch (Exception e) {
            logger().info("Locator not found : " + checkboxLocator);
            logger().info("Some exception occurred while clicking on checkbox label " + e);
            throw e;
        }
    }
    //Is element displayed (checks Label following an element (for checkbox)
    public boolean isElementDisplayedByLabel(By locator) {
        try {
            var element = getElement(locator).findElement(By.xpath("./following-sibling::label"));
            scrollToView(element);
            return element.isDisplayed();
        } catch (Exception e) {
            logger().info("Locator not found : " + locator);
            logger().info("Some exception occurred while checking display status of element by label " + e);
            throw e;
        }
    }

    //Clicks WebElement
    public void doClick(WebElement element)
    {
        try{
            scrollToView(element);
            element.click();
        }catch (Exception e){
            logger().info("Some exception occurred while clicking on webelement " + e);
            throw e;
        }
    }

    public void doClickUsingActions(By locator){
        try{
            var element = getElement(locator);
            scrollToView(element);
            actions.click().perform();
        }catch (Exception e){
            logger().info("Locator not found : " + locator);
            logger().info("Some exception occurred while clicking on webelement using Actions " + e);
            throw e;
        }
    }

    public void doDoubleClick(WebElement element)
    {
        scrollToView(element);
        actions.doubleClick(element).perform();
    }

    public String doGetInnerHtml(WebElement element)
    {
        scrollToView(element);
        return element.getAttribute("innerHTML").trim();
    }

    public void doClickAndHold(By locator){
        var element = getElement(locator);
        scrollToView(element);
        actions.clickAndHold(element).perform();
        if(getElement(locator) != null){
            actions.moveToElement(element).release().build().perform();
        }else {
            actions.release().build().perform();
        }

        waitForPageToLoad();
    }

    public void doContextClick(WebElement element)
    {
        scrollToView(element);
        actions.contextClick(element).perform();
    }

    public void doContextClick(By locator){
        var element = getElement(locator);
        scrollToView(element);
        actions.contextClick(element).perform();
    }

    public void doSendKeys(By locator, String value)
    {
        var element = getElement(locator);
        scrollToView(element);
        element.clear();
        element.sendKeys(value);
    }

    public void doSendKeys(By locator, Double value)
    {
        var element = getElement(locator);
        scrollToView(element);
        element.clear();
        element.sendKeys(value.toString());
    }

    public void doSendKeysUsingAction(String value) {
        actions.sendKeys(value).perform();
    }

    public void doSendKeysUsingAction(By locator, String value) {
        doClickUsingActions(locator);
        actions.sendKeys(value).perform();
    }

    public void doSendKeysUsingAction(WebElement element, String value) {
        doClick(element);
        actions.sendKeys(value).perform();
    }

    public void doSendKeysUsingAction(By locator, Double value) {
        doClickUsingActions(locator);
        actions.sendKeys(value.toString()).perform();
    }

    public void selectComboBoxItemByText(By locator, String value) {
        Select comboBoxContent = new Select(getElement(locator));
        scrollToView(getElement(locator));
        comboBoxContent.selectByVisibleText(value);
        waitForPageToLoad();
        getFirstSelectedOption(locator);
    }

    public void  selectComboBoxItemByTextWithoutWait(By locator, String value) {
        Select comboBoxContent = new Select(getElement(locator));
        scrollToView(getElement(locator));
        comboBoxContent.selectByVisibleText(value);
    }

    public String getFirstSelectedOption(By locator)
    {
         Select comboBoxContent = new Select(getElement(locator));
         scrollToView(getElement(locator));
         return comboBoxContent.getFirstSelectedOption().getText();
    }

    public void selectComboBoxItemByValue(By locator, String value) {
        Select comboBoxContent = new Select(getElement(locator));
        scrollToView(getElement(locator));
        comboBoxContent.selectByValue(value);
        waitForPageToLoad();
    }

    public String getFirstSelectedOption(WebElement element) {
        Select comboBoxContent = new Select(element);
        scrollToView(element);
        return comboBoxContent.getFirstSelectedOption().getText();
    }

    public List<WebElement> getComboBoxSelectOptions(By locator) {
        Select comboBoxContent = new Select(getElement(locator));
        scrollToView(getElement(locator));
        return comboBoxContent.getOptions();
        //Returns a list of all <option> elements inside the <select>
    }

    public List<String> getSelectOptions(By locator){
        List<WebElement> dropDownOptions = getComboBoxSelectOptions(locator);
        List<String> options = new ArrayList<>();
        for (int i=0; i< dropDownOptions.size();i++){
            String value = doGetText(dropDownOptions.get(i));
            logger().info("Option :" + value);
            if (!value.isEmpty()){
                options.add(value);
            }
        }
        return options;
    }

    public String selectComboBoxItemByIndex(By locator, int index) {
        Select comboBoxContent = new Select(getElement(locator));
        scrollToView(getElement(locator));
        comboBoxContent.selectByIndex(index);
        return getFirstSelectedOption(locator);
    }

    public String selectComboBoxItemByIndex(WebElement element, int index) {
        Select comboBoxContent = new Select(element);
        scrollToView(element);
        comboBoxContent.selectByIndex(index);
        return getFirstSelectedOption(element);
    }

    public String doGetText(By locator)
    {
        var element = getElement(locator);
        scrollToView(element);
        return element.getText().trim();
    }

    public String doGetText(WebElement element)
    {
        scrollToView(element);
        return element.getText().trim();
    }

    public String doGetAttribute(By locator, String attribute) {
        return getElement(locator).getAttribute(attribute);
    }

    public boolean isAttributePresent(By locator, String attributeName){
        return doGetAttribute(locator, attributeName) != null;
    }

    public boolean isElementEnabled(By locator) {
        try{
            return getElement(locator).isEnabled();
        }catch(ElementNotInteractableException e){
            return false;
        }
    }

    public void clearText(By locator) {
        getElement(locator).clear();
    }

    public void scrollToView(By locator) {
        var element = getElement(locator);
        scrollToView(element);
    }

    public void moveToElementWithOffset(By locator, int offset) {
        actions.moveToElement(getElement(locator)).clickAndHold().moveByOffset(0, offset).release().perform();
    }

    public void moveToElement(WebElement element) {
        actions.moveToElement(element).perform();
    }

    public WebElement getParentElement(WebElement childElement) {
        return (WebElement) ((JavascriptExecutor) driver).executeScript("return arguments[0].parentNode;", childElement);
    }

    public boolean isCheckBoxSelected(By locator) {
        return isCheckBoxSelected(driver.findElement(locator));
    }

    public boolean isCheckBoxSelected(WebElement element){
        try {
            boolean checkedStatus = element.isSelected();
            return checkedStatus;
        }catch (ElementNotInteractableException e){
            logger().info("Checkbox cannot be checked");
            return false;
        }
    }

    public void dragAndDrop(By sourceLocator, By targetLocator) {
        WebElement sourceElement = getElement(sourceLocator);
        WebElement targetElement = getElement(targetLocator);
        actions.dragAndDrop(sourceElement, targetElement).perform();
    }

    public boolean isElementDisabled(By locator) {
        return doGetAttribute(locator, "class").contains("rrbDisabled");
    }

    public boolean isElementDisabledNonRibbon(By locator){
        String value = doGetAttribute(locator, "disabled");
        return value.equals("disabled") || value.equals("true");
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public List<String> getListOfText(By locator){
        List<WebElement> allElements =  getElements(locator);
        List<String> allText = new ArrayList<>();
        for (WebElement element : allElements){
            logger().info("Element Text: " + doGetText(element));
            allText.add(element.getText());
        }
        return allText;
    }

    public String getWindowHandle() {
        return driver.getWindowHandle();
    }

    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    public void scrollElementIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", element);
    }

    public void selectComboBoxItemByTextWithoutValidation(By locator, String optionToSelect) {
        new Select(getElement(locator)).selectByVisibleText(optionToSelect);
    }

    public void clickAndHoldAndDragTheElement(By source,By destination){
        actions.clickAndHold(getElement(source)).moveToElement(getElement(destination)).release(getElement(destination)).build().perform();
    }










}
