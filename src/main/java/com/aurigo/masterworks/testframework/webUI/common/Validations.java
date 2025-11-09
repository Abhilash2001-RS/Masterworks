package com.aurigo.masterworks.testframework.webUI.common;

import com.aurigo.masterworks.testframework.utilities.LocatorUtil;
import com.aurigo.masterworks.testframework.utilities.helper.ScreenshotHelper;
import com.aurigo.masterworks.testframework.webUI.constants.enums.ImportExportOptionsInListPage;
import com.aurigo.masterworks.testframework.webUI.constants.enums.RegexStrings;
import com.aurigo.masterworks.testframework.webUI.constants.enums.RibbonIcons;
import com.aurigo.masterworks.testframework.webUI.generic.GenericForm;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;
import org.testng.util.Strings;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.aventstack.extentreports.Status.INFO;

public class Validations extends RibbonMenu{

    private final WebDriver driver;

    private final By treeDiv;
    private final By westLayoutDiv;
    private final By expandCollapseMenuBtn;
    private final By visibleMenuTabItem;
    private final By expandTreeBtn;
    private final By expandedFolder;
    private final By collapseTreeBtn;
    private final By collapsedFolder;
    private final By leafNode;
    private final By noRecordsFoundDiv;
    private final By loginButton;
    private final By errorMessage;
    private final By selectedModuleNameSpan;
    private final By breadCrumbsPopupMenuActive;
    private final By ribbonBarControl;
    private final By pageTabHeader;

    private final String tabPageItemXpathTemplate = ".//div[@id='PageTabs']//a[.='%s']";
    private final String filterButton = "%s//a[.='%s']/../a[contains(@class,'k-grid-filter')]";
    private final String buttonValidation = "%s//input[(@value = '%s') and not(contains(@style,'display: none'))]";

    public Validations(WebDriver driver) {
        super(driver);
        this.driver = driver;

        var locators = LocatorUtil.getLocators("Validations.json");
        treeDiv = locators.get("treeDiv");
        westLayoutDiv = locators.get("westLayoutDiv");
        expandCollapseMenuBtn = locators.get("expandCollapseMenuBtn");
        expandTreeBtn = locators.get("expandTreeBtn");
        collapseTreeBtn = locators.get("collapseTreeBtn");
        leafNode = locators.get("leafNode");
        noRecordsFoundDiv = locators.get("noRecordsFoundDiv");
        loginButton = locators.get("loginButton");
        errorMessage = locators.get("errorMessage");
        visibleMenuTabItem = locators.get("visibleMenuTabItem");
        expandedFolder = locators.get("expandedFolder");
        collapsedFolder = locators.get("collapsedFolder");
        selectedModuleNameSpan = locators.get("selectedModuleNameSpan");
        breadCrumbsPopupMenuActive = locators.get("breadCrumbsPopupMenuActive");
        ribbonBarControl = locators.get("ribbonBarControl");
        pageTabHeader = locators.get("pageTabHeader");
    }

    /**
     * Function to check if the filter and button validation in picker
     * * @param pickerId Picker element value is being passed
     *
     * @param columnName                 -Column name to select the filter
     * @param isButtonValidationRequired Used to select to enable button validations
     * @return Returns true if the validation is passed
     */
    public SoftAssert validateFilterAndButtonsInPicker(By pickerId, String columnName, boolean isButtonValidationRequired) {
        logger().info("Checking whether the filter option is visible or not");
        var softAssert = new SoftAssert();
        var pickerLocator = elementHelper.getLocatorAsString(pickerId);
        softAssert.assertTrue(elementHelper.isElementDisplayed(By.xpath(String.format(filterButton, pickerLocator, columnName))));
        if (isButtonValidationRequired) {
            softAssert.assertTrue(elementHelper.isElementDisplayed(By.xpath(String.format(buttonValidation, pickerLocator, "Select"))));
            softAssert.assertTrue(elementHelper.isElementDisplayed(By.xpath(String.format(buttonValidation, pickerLocator, "Cancel"))));
        }
        return softAssert;
    }

    /**
     * Method to validate whether the ExpandAll/Collapse Menu functionality is  working or not
     *
     * @return softAssert
     */
    public SoftAssert validateExpandAndCollapseMenuFunctionality() {
        logger().info("Validating expand/collapse menu functionality");
        var softAssert = new SoftAssert();
        logger().info("Clicking on Expand/Collapse Menu button");
        navigation.switchFrameToDefault();
        getPage(ScreenshotHelper.class).takeElementScreenshot(westLayoutDiv, "Before collapsing menu");
        elementHelper.doClick(expandCollapseMenuBtn);
        var visibleMenuTabItems = elementHelper.getElements(visibleMenuTabItem);
        softAssert.assertTrue(visibleMenuTabItems.isEmpty(), "Validating collapsed menu tab");
        getPage(ScreenshotHelper.class).takeElementScreenshot(westLayoutDiv, "After collapsing menu");
        elementHelper.doClick(expandCollapseMenuBtn);//Expanding the menu back for continuing with other tests
        waitHelper.waitForPageToLoad(westLayoutDiv);
        return softAssert;
    }

    /**
     * Method to validate whether the ExpandAll/CollapseAll functionality in tree is working or not
     *
     * @param moduleName - Name of the module to verify
     * @return softAssert
     */
    public SoftAssert validateExpandAndCollapseTreeFunctionality(String moduleName) {
        logger().info("Validating expand/collapse tree functionality");
        var softAssert = new SoftAssert();
        navigation.switchFrameToDefault();
        softAssert.assertEquals(elementHelper.doGetText(selectedModuleNameSpan), moduleName, "Validating the module name displayed");
        logger().info("Expanding all");
        elementHelper.doClick(expandTreeBtn);
        waitHelper.waitForPageToLoad();
        getPage(ScreenshotHelper.class).takeElementScreenshot(treeDiv, "Expanded Tree");
        var collapsedFolders = elementHelper.getElements(collapsedFolder);
        var expandedFolders = elementHelper.getElements(expandedFolder);
        var leafNodes = elementHelper.getElements(leafNode);
        if (collapsedFolders.isEmpty() && expandedFolders.isEmpty() && !leafNodes.isEmpty()) {
            logger().info("No folders present in tree to expand or collapse");
            softAssert.assertTrue(!leafNodes.isEmpty(), "Validating presence of leaf nodes");
        } else {
            softAssert.assertTrue(collapsedFolders.isEmpty() && !expandedFolders.isEmpty(), "Validating expand all in tree");
            logger().info("Collapsing all");
            elementHelper.doClick(collapseTreeBtn);
            waitHelper.waitForPageToLoad();
            getPage(ScreenshotHelper.class).takeElementScreenshot(treeDiv, "Collapsed Tree");
            collapsedFolders = elementHelper.getElements(collapsedFolder);
            expandedFolders = elementHelper.getElements(expandedFolder);
            softAssert.assertTrue(expandedFolders.isEmpty() && !collapsedFolders.isEmpty(), "Validating collapse all in tree");
        }
        return softAssert;
    }

    /**
     * Method to check whether the No Records Found div is displayed on the list page
     *
     * @return True if the No Records Found div is displayed
     */
    public boolean isNoRecordsFoundDivDisplayed() {
        logger().info("Checking whether 'No records Found' text is displayed or not");
        navigation.switchFrameToContent();
        waitHelper.waitForPageToLoad(pageTabHeader);
        return verifyElementExists(noRecordsFoundDiv);
    }

    /**
     * Method to check whether a form exists in Left Pane Tree
     *
     * @param formPath - Path of the form in Left Pane Tree
     * @return True if the form is available
     */
    @Step("Check whether a form exists in Left Pane Tree")
    public boolean isFormAvailableInLeftPaneTree(String formPath) {
        logger().info("Checking whether the form '" + formPath + "' is available or not");
        try {
            navigation.switchFrameToDefault();
            waitHelper.waitForElementClickable(navigation.xpathTreeExpandAllBtn);
            elementHelper.doClick(navigation.xpathTreeExpandAllBtn);
            elementHelper.doClick(navigation.clearSearchButton);
            var formXpath = navigation.getXPathForLeftPaneTreeItem(formPath);
            if (verifyElementExists(By.xpath(formXpath))) {
                var formItem = elementHelper.getElement(By.xpath(formXpath));
                logger().info("Form '" + formPath + "' is found");
                elementHelper.scrollToView(formItem);
                getPage(ScreenshotHelper.class).takeElementScreenshot(treeDiv, "Left Pane Tree");
                return true;
            }
        } catch (NoSuchElementException e) {
            logger().info("Form '" + formPath + "' is not found");
        }
        return false;
    }


    /**
     * Method to check whether the user is logged out or not
     *
     * @return True if the user is logged out
     */
    public boolean isLoggedOut() {
        logger().info("Checking whether the user is logged out or not");
        return driver.getCurrentUrl().toLowerCase().contains("login") || elementHelper.isElementDisplayed(loginButton);
    }

    /**
     * Function to check if the header of the page is displayed
     *
     * @param tabName tab text
     * @return true if the form page header is visible
     */
    public boolean isFormPageTabVisible(String tabName) {
        logger().info("Checking whether the page header '" + tabName + "' is visible or not");
        waitHelper.waitForPageToLoad();
        return elementHelper.isElementDisplayed(By.xpath(String.format(tabPageItemXpathTemplate, tabName)));
    }


    /**
     * Function to verify if element is uneditable
     *
     * @param locator element
     * @return true if the element is uneditable. If the element is editable then it returns false
     */
    public boolean isElementUneditable(By locator) {
        logger().info("Checking whether the element at locator " + elementHelper.getLocatorAsString(locator) + " is editable or not");
        var element = elementHelper.getElement(locator);
        return (Strings.isNotNullAndNotEmpty(element.getAttribute("readonly")) && element.getAttribute("readonly").equals("true") ||
                (Strings.isNotNullAndNotEmpty(element.getAttribute("disabled")) && element.getAttribute("disabled").equals("true")));
    }

    /**
     * Validate whether the Breadcrumbs icon is visible or not
     *
     * @return true if breadcrumbs icon visible
     */
    public boolean isBreadCrumbsIconVisible() {
        navigation.switchFrameToDefault();
        getPage(ScreenshotHelper.class).takeFullScreenshot("Current Screen");
        return elementHelper.isElementDisplayed(breadCrumbsPopupMenuActive);
    }

    /**
     * Function to verify whether save and cancel button are displayed in a form or not
     *
     * @return true, if both are displayed. Else it returns false
     */
    public boolean verifySaveAndCancelButtonsAreDisplayed() {
        waitHelper.waitForPageToLoad();
        getPage(ScreenshotHelper.class).takeElementScreenshot(ribbonBarControl, "Ribbon Menu");
        return isSaveButtonPresent() && isCancelButtonPresent();
    }

    /**
     * Method to check if Save button is Present in the form
     *
     * @return true if the save button is present
     */
    public boolean isSaveButtonPresent() {
        logger().info("Checking whether the Save button is displayed");
        return validateRibbonIcon(RibbonIcons.Save);
    }

    /**
     * Method to check if Save button is enabled in the form
     *
     * @return true if the save button is enabled
     */
    public boolean isSaveButtonEnabled() {
        logger().info("Checking whether the Save button is enabled");
        waitHelper.waitForPageToLoad();
        return validateRibbonIcon(RibbonIcons.Save);
    }

    /**
     * Method to check if Cancel button is Present in the form
     *
     * @return true if the Cancel button is present
     */
    public boolean isCancelButtonPresent() {
        logger().info("Checking whether the Cancel button is displayed");
        return validateRibbonIcon(RibbonIcons.Cancel);
    }

    /***
     * Method to check if View button is Present in the form
     *
     * @return true if the View button is present
     */
    public boolean isViewButtonPresent() {
        logger().info("Checking whether the View button is displayed");
        waitHelper.waitForPageToLoad();
        return validateRibbonIcon(RibbonIcons.View);
    }

    /**
     * Function to validate if the page has any errors
     *
     * @return true if any error exists on the page
     */
    public boolean checkPageForError() {
        navigation.switchFrameToContent();
        if (elementHelper.isElementDisplayed(errorMessage)) {
            logger().info("Error message is displayed on the page. Message:" + elementHelper.doGetText(errorMessage));
            return true;
        }
        logger().info("No error occurred");
        return false;
    }

    /**
     * Function to verify whether a list of tabs are present in the form
     *
     * @param tabList - List of tabs
     */
    public void verifyIfTabsArePresent(List<String> tabList) {
        waitHelper.waitForPageToLoad();
        navigation.switchFrameToContent();
        var softAssert = new SoftAssert();
        logger().info("Checking for tabs: " + tabList.toString());
        for (var tab : tabList) {
            softAssert.assertTrue(isFormPageTabVisible(tab), "Validating presence of tab '" + tab + "'");
        }
        softAssert.assertAll();
    }

    /**
     * Method to validate the non text field values
     *
     * @param fields - fields to validate
     * @return true if all validations are successful
     */
    public boolean viewFormVerifyNonTextFieldValues(Map<By, String> fields) {
        AtomicBoolean textValuesEqual = new AtomicBoolean(true);
        logger().log(INFO, "Verifying Non-Text field values in view form");
        navigation.switchFrameToContent();
        fields.forEach((fieldIdentifier, expectedText) -> {
            var actualText = elementHelper.doGetAttribute(fieldIdentifier, "value");
            if (!expectedText.equals(actualText)) {
                textValuesEqual.set(false);
                logger().fail("Validation failed for <b>" + fieldIdentifier.toString() +
                        "</b> field <p>Expected Text: <b>" + expectedText + "</b></p><p>Actual Text: <b>" + actualText + "</b></p>");
            } else {
                logger().pass("Validation passed for <b>" + fieldIdentifier.toString() + "</b> field");
            }
        });
        return textValuesEqual.get();
    }

    /**
     * Verify text values of the text box in view form.
     *
     * @param fields A Map of locator and the text to be verified.
     * @return True if no issue occurred, else false
     */
    public boolean viewFormVerifyTextValues(Map<By, String> fields) {
        AtomicBoolean textValuesEqual = new AtomicBoolean(true);
        logger().log(INFO, "Verifying Text Values in view form");
        navigation.switchFrameToContent();
        fields.forEach((fieldIdentifier, expectedText) -> {
            var actualText = elementHelper.doGetText(fieldIdentifier);
            if (!expectedText.equals(actualText)) {
                textValuesEqual.set(false);
                logger().fail("Validation failed for <b>" + fieldIdentifier.toString() +
                        "</b> field <p>Expected Text: <b>" + expectedText + "</b></p><p>Actual Text: <b>" + actualText + "</b></p>");
            } else {
                logger().pass("Validation passed for <b>" + fieldIdentifier.toString() + "</b> field");
            }
        });
        return textValuesEqual.get();
    }

    /**
     * Function to check whether an element exists or not
     *
     * @param locator - Locator of the element
     * @return true if element exists
     */
    public boolean verifyElementExists(By locator) {
        logger().info("Checking whether the element at locator '" + elementHelper.getLocatorAsString(locator) + "' exists or not");
        return elementHelper.isElementDisplayed(locator);
    }


    /**
     * Function to verify that list of elements exist or not
     *
     * @param locators - list of locators
     */
    public void verifyElementsExists(List<By> locators) {
        var softAssert = new SoftAssert();
        for (var locator : locators) {
            softAssert.assertTrue(verifyElementExists(locator), "Verifying the element at the locator '" + elementHelper.getLocatorAsString(locator) + "'");
        }
        softAssert.assertAll();
    }


    /**
     * Checks if required field signs are present at the labels
     *
     * @param labelLocators - List of locators of the labels which are supposed to have asterisk.
     * @return Returns softAssert with validations for all fields.
     */
    public SoftAssert isAsteriskSignPresentOnLabel(List<By> labelLocators) {
        logger().info("Validating asterisk symbol for mandatory fields");
        getPage(ScreenshotHelper.class).takeScreenshotOfContentFrame();
        String asteriskXpathAppend = "/following-sibling::td[@class = 'required']";
        var softAssert = new SoftAssert();
        for (var locator : labelLocators) {
            softAssert.assertTrue(elementHelper.isElementDisplayed(By.xpath(elementHelper.getLocatorAsString(locator) + asteriskXpathAppend)), "Validating asterisk sign for the locator: " + locator.toString());
        }
        return softAssert;
    }

    /**
     * Validate Delete option
     *
     * @return true on Fields available
     */
    public boolean validateDelete() {
        return validateRibbonIcon(RibbonIcons.Delete);
    }

    /**
     * Method to check Masking for Password fields
     *
     * @param locator - Locator of element.
     * @return true on successfull validation
     */
    public boolean verifyMaskingForFields(By locator) {
        String attributeValue1 = elementHelper.doGetAttribute(locator, "type");
        String attributeValue2 = elementHelper.doGetAttribute(locator, "controlType");
        return attributeValue1.contains("password") && attributeValue2.contains("Password");
    }

    /**
     * Method to check if a field is Read-only
     *
     * @param locator - Locator of element.
     * @return true on successfull validation
     */
    public boolean verifyIfAFieldIsReadOnly(By locator) {
        String attributeValue = elementHelper.doGetAttribute(locator, "readOnly");
        return attributeValue.contains("true");
    }

    /**
     * Check if "New" button is enabled
     *
     * @return true if the "New" button is enabled
     */
    public boolean isNewEnabled() {
        navigation.switchFrameToContent();
        String value = elementHelper.doGetAttribute(getRibbonIcon(RibbonIcons.New), "isdisabled");
        if (Strings.isNotNullAndNotEmpty(value)) {
            return !value.equals("disabled");
        }
        return true;
    }

    /**
     * Validate import export options in the drop down
     *
     * @return true on validation of the above
     */
    public boolean validateImportExportOptions() {
        clickRibbonIcon(RibbonIcons.ExcelImportOrExportDropDown);
        List<By> importExportOptions = new ArrayList<>();
        importExportOptions.add(By.xpath(String.format(optionsInRibbonMenu, ImportExportOptionsInListPage.ExcelImport.getValue())));
        importExportOptions.add(By.xpath(String.format(optionsInRibbonMenu, ImportExportOptionsInListPage.ExcelExportXLS.getValue())));
        importExportOptions.add(By.xpath(String.format(optionsInRibbonMenu, ImportExportOptionsInListPage.ExcelExportXLSX.getValue())));
        importExportOptions.add(By.xpath(String.format(optionsInRibbonMenu, ImportExportOptionsInListPage.ExcelTemplateWithDataXLS.getValue())));
        importExportOptions.add(By.xpath(String.format(optionsInRibbonMenu, ImportExportOptionsInListPage.ExcelTemplateWithDataXLSX.getValue())));
        importExportOptions.add(By.xpath(String.format(optionsInRibbonMenu, ImportExportOptionsInListPage.ExcelTemplateXLS.getValue())));
        importExportOptions.add(By.xpath(String.format(optionsInRibbonMenu, ImportExportOptionsInListPage.ExcelTemplateXLSX.getValue())));

        return getPage(GenericForm.class).validateElementsExists(importExportOptions);
    }

    /**
     * Method to check if Edit button is Present in the form
     *
     * @return true if the Edit button is present
     */
    public boolean isEditButtonPresent() {
        return validateRibbonIcon(RibbonIcons.Edit);
    }


}
