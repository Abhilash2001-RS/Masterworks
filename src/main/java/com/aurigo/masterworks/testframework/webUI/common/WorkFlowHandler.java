package com.aurigo.masterworks.testframework.webUI.common;

import com.aurigo.masterworks.testframework.utilities.ExceptionHandler;
import com.aurigo.masterworks.testframework.utilities.JavaScriptUtil;
import com.aurigo.masterworks.testframework.utilities.LocatorUtil;
import com.aurigo.masterworks.testframework.utilities.TestDataUtil;
import com.aurigo.masterworks.testframework.utilities.helper.FileHelper;
import com.aurigo.masterworks.testframework.utilities.helper.ScreenshotHelper;
import com.aurigo.masterworks.testframework.webUI.constants.Constants;
import com.aurigo.masterworks.testframework.webUI.constants.enums.*;
import com.aurigo.masterworks.testframework.webUI.generic.GenericForm;
import com.aurigo.masterworks.testframework.webUI.generic.ListPage;
import org.openqa.selenium.*;
import org.testng.util.Strings;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class WorkFlowHandler extends ListPage {

    private By workFlowPopUpOkButton;
    private By workflowHistoryTable;
    public By workFlowHistoryPopUpCloseButton;
    private By workFlowHistoryWindow;
    private final By workFlowPopUpNotes;
    private By workflowNotesLabel;
    private WebDriver driver;
    private By emailRecipients;
    private By workflowHistoryMoreDetails;
    private By workflowHistoryReport;
    private By workflowHistoryReportTitle;

    public WorkFlowHandler(WebDriver driver) {
        super(driver);
        var locators = LocatorUtil.getLocators("WorkFlowHandler.json");
        this.driver = driver;
        workFlowPopUpOkButton = locators.get("workFlowPopUpOkButton");
        workflowHistoryTable = locators.get("workflowHistoryTable");
        workFlowPopUpNotes=locators.get("workFlowPopUpNotes");
        workFlowHistoryPopUpCloseButton = locators.get("workFlowHistoryPopUpCloseButton");
        workFlowHistoryWindow = locators.get("workFlowHistoryWindow");
        emailRecipients = locators.get("emailRecipients");
        workflowNotesLabel = locators.get("workflowNotesLabel");
        workflowHistoryMoreDetails = locators.get("workflowHistoryMoreDetails");
        workflowHistoryReport = locators.get("workflowHistoryReport");
        workflowHistoryReportTitle = locators.get("workflowHistoryReportTitle");
    }

    /**
     * Selects Workflow action
     *
     * @param actionName Action name to be selected.
     */
    public void selectAction(String actionName) {
        By actionLocator = By.xpath(String.format("//a[@aria-label='%s' and not(contains(@style,'none')) and not(contains(@style,'None'))]/span[.='%s']",actionName,actionName));
        waitHelper.waitForElementToBePresentAndClickable(actionLocator);
        elementHelper.doClick(actionLocator);
        waitHelper.waitForPageToLoad(workFlowPopUpOkButton);
    }

    /**
     * Validation of workflow notes
     * @return true if validation is successful else false
     */
    public boolean validationOfNotes() {
        logger().info("Validation of notes");
        return getPage(Validations.class).verifyElementExists(workflowNotesLabel) && getPage(Validations.class).verifyElementExists(workFlowPopUpNotes);
    }

    /**
     * Clicks workflow pop up ok button.
     */
    public void clickWorkFlowPopUpOkButton() {
        waitHelper.waitForAjaxToComplete();
        elementHelper.doSendKeys(workFlowPopUpNotes,"Workflow action notes");
        waitHelper.waitForElementClickable(workFlowPopUpOkButton);
        elementHelper.doClick(workFlowPopUpOkButton);
        waitHelper.waitForPageToLoad();
    }

    /**
     * Clicks workflow pop up ok button for error validation
     */
    public void clickWorkFlowPopUpOkButtonForErrorValidation() {
        waitHelper.waitForAjaxToComplete();
        elementHelper.doSendKeys(workFlowPopUpNotes,"Workflow action notes");
        waitHelper.waitForElementClickable(workFlowPopUpOkButton);
        elementHelper.doClick(workFlowPopUpOkButton);
    }

    /**
     * Function to execute a Workflow
     *
     * @param rowString            Select the Row on which Workflow needs to be executed
     * @param columnNameToSearchIn Column Header which should be searched into
     * @param actionName           Type of Workflow that needs to be executed
     * @param expectedStatus       Expected Status in the Row after the workflow is executed
     * @return True if workflow status as expected else false.
     */
    public boolean workFlowActionProgression(String rowString, String columnNameToSearchIn, WorkFlowActions actionName, WorkFlowStatus expectedStatus) {
        return workFlowActionProgression(true, true, rowString, columnNameToSearchIn, actionName, expectedStatus);
    }

    /**
     * Function to execute a Workflow
     *
     * @param isClearFilterRequired Set true if the filters needs to be cleared
     * @param isFilteringRequired   Set true if the list page needs to be filtered
     * @param rowString             Select the Row on which Workflow needs to be executed
     * @param columnNameToSearchIn  Column Header which should be searched into
     * @param actionName            Type of Workflow that needs to be executed
     * @param expectedStatus        Expected Status in the Row after the workflow is executed
     * @return True if workflow status as expected else false.
     */
    public boolean workFlowActionProgression(boolean isClearFilterRequired, boolean isFilteringRequired, String rowString, String columnNameToSearchIn, WorkFlowActions actionName, WorkFlowStatus expectedStatus) {
        return workFlowActionProgression(isClearFilterRequired, isFilteringRequired, rowString, columnNameToSearchIn, actionName, expectedStatus, true);
    }


    /**
     * Function to execute a Workflow
     *
     * @param isClearFilterRequired Set true if the filters needs to be cleared
     * @param isFilteringRequired   Set true if the list page needs to be filtered
     * @param rowString             Select the Row on which Workflow needs to be executed
     * @param columnNameToSearchIn  Column Header which should be searched into
     * @param actionName            Type of Workflow that needs to be executed
     * @param expectedStatus        Expected Status in the Row after the workflow is executed
     * @param verificationRequired  Is verification of workflow status required.
     * @return True if workflow status as expected else false.
     */
    public boolean workFlowActionProgression(boolean isClearFilterRequired, boolean isFilteringRequired, String rowString, String columnNameToSearchIn, WorkFlowActions actionName, WorkFlowStatus expectedStatus, boolean verificationRequired) {
        navigation.switchFrameToContent();
        waitHelper.waitForPageTabHeaderToBeClickable();
        if (isClearFilterRequired) {
            clearAllFilters();
        }
        if (isFilteringRequired) {
            singleClickOnRowListPage(columnNameToSearchIn, rowString);
        } else {
            waitHelper.waitForPageToLoad();
            int rowNum = getRowNumberFromListPage(columnNameToSearchIn,rowString);
            singleClickOnRowListPage(rowNum);
        }
        waitHelper.waitForPageToLoad();
        clickWorkFlowButton();
        selectAction(actionName.getValue());
        clickWorkFlowPopUpOkButton();
        if (verificationRequired) {
            return getCellData(getRowNumberFromListPage(columnNameToSearchIn, rowString), "Workflow Status")
                    .equalsIgnoreCase(expectedStatus.getValue());
        }
        return true;
    }


    /**
     * Function to execute a Workflow using row index
     *
     * @param rowNumber      Select the Row using number on which Workflow needs to be executed
     * @param actionName     Type of Workflow that needs to be executed
     * @param expectedStatus Expected Status in the Row after the workflow is executed
     * @return True if workflow status as expected else false.
     */
    public boolean workFlowActionProgression(int rowNumber, WorkFlowActions actionName, WorkFlowStatus expectedStatus) {
        return workFlowActionProgression(rowNumber, actionName, expectedStatus, true);
    }

    /**
     * Function to execute a Workflow using row index
     *
     * @param rowNumber            Select the Row using number on which Workflow needs to be executed
     * @param actionName           Type of Workflow that needs to be executed
     * @param expectedStatus       Expected Status in the Row after the workflow is executed
     * @param verificationRequired Is verification of workflow status required.
     * @return True if workflow status as expected else false.
     */
    public boolean workFlowActionProgression(int rowNumber, WorkFlowActions actionName, WorkFlowStatus expectedStatus, boolean verificationRequired) {
        navigation.switchFrameToContent();
        singleClickOnRowListPage(rowNumber);
        clickWorkFlowButton();
        selectAction(actionName.getValue());
        clickWorkFlowPopUpOkButton();
        if (verificationRequired) {
            return getCellData(rowNumber, "Workflow Status")
                    .equalsIgnoreCase(expectedStatus.getValue());
        }
        return true;
    }

    /**
     * Function to perform workflow action on New or Edit page
     * @param actionName workflow action to be performed
     */
    public void performWorkflowActionWithinFormWithoutVerification(WorkFlowActions actionName ) {
        clickWorkFlowButton();
        selectAction(actionName.getValue());
        clickWorkFlowPopUpOkButton();
        waitHelper.waitForPageToLoad();
    }

    /**
     * Function to Execute a Workflow by grabbing the Row Number in the List Page
     *
     * @param rowNum         Row number of item to be selected.
     * @param actionName     Type of Workflow that needs to be executed
     * @param expectedStatus Expected Status in the Row after the workflow is executed
     * @return True if workflow status as expected else false.
     */
    public boolean workFlowAction(int rowNum, String actionName, String expectedStatus) {
        singleClickOnRowListPage(rowNum);
        clickWorkFlowButton();
        selectAction(actionName);
        clickWorkFlowPopUpOkButton();
        return getCellData(rowNum, "Workflow Status").equalsIgnoreCase(expectedStatus);
    }

    /**
     * Check if workflow option exists.
     *
     * @param rowNum     Row number of item to be selected.
     * @param actionName Type of Workflow that needs to be executed
     * @return True if work flow option exists.
     */
    public boolean checkWorkFlowExistence(int rowNum, String actionName) {
        clickWorkFlowButton();
        By actionLocator = By.xpath("//*[text() = '" + actionName + "']/ancestor::a[not(contains(@style,'display: none'))]");
        return !elementHelper.getElements(actionLocator).isEmpty();
    }

    /**
     * Function to validate that correct workflow history is displayed for a record
     *
     * @param expectedStatuses list of expected workflows
     * @return true if the validation is successful, else returns false
     */
    public boolean validateWorkflowHistory(List<String> expectedStatuses) {
        boolean flag = false;
        waitHelper.waitForPageToLoad();
        var workflowHistoryTableElement = elementHelper.getElement(workflowHistoryTable);
        var tdTags = workflowHistoryTableElement.findElements(By.tagName("td"));
        for (String status : expectedStatuses) {
            flag = false;
            for (var td : tdTags) {
                if (!td.getText().isEmpty() && td.getText().contains(status)) {
                    flag = true;
                    break;
                }
            }
        }

        return flag;
    }

    /**
     * Function to click on Workflow History button in the ribbon
     */
    public void openWorkflowHistory() {
        clickRibbonIcon(RibbonIcons.WorkflowHistory);
        waitHelper.waitForPageToLoad();
    }

    /**
     * Open Workflow History from the form
     */
    public void openFormWorkFlowHistory(){
        clickRibbonIcon(RibbonIcons.FormWorkflowHistory);
        waitHelper.waitForPageToLoad();
    }

    /**
     * Function to click on close button in Workflow History Popup
     */
    public void closeWorkflowHistory()
    {
        var element = elementHelper.getElementNoWait(workFlowHistoryPopUpCloseButton);
        System.out.println(element.isDisplayed());
        JavaScriptUtil.clickElementByJS(elementHelper.getElementNoWait(workFlowHistoryPopUpCloseButton), driver);
        waitHelper.waitForPageToLoad();
    }

    /**
     * This method is used to approve a record without verifying the approved state
     *
     * @param searchText           - The text to be searched for
     * @param columnNameToSearchIn - The column name to search the text
     * @param actionName           - the action to be performed
     */
    public void workflowActionWithoutVerifying(String searchText, String columnNameToSearchIn, WorkFlowActions actionName) {
        waitHelper.waitForPageToLoad();
        singleClickOnRowListPage(columnNameToSearchIn, searchText);
        waitHelper.waitForPageToLoad();
        clickWorkFlowButton();
        selectAction(actionName.getValue());
        clickWorkFlowPopUpOkButton();
        waitHelper.waitForPageToLoad();

    }

    /**
     * Validate the WorkFlow History popup
     *
     * @return true on validation of the presence of WorkFLow History popup
     */
    public boolean validateWorkFlowHistoryPopUp(){
        waitHelper.waitForElementPresent(workFlowHistoryWindow);
        return getPage(Validations.class).verifyElementExists(workFlowHistoryWindow);
    }

    /**
     * Fill the email Recipients
     */
    public void fillEmailRecipients(){
        elementHelper.doClick(emailRecipients);
        waitHelper.waitForPageToLoad();
        elementHelper.doSendKeys(emailRecipients, Keys.ENTER);
    }


    /**
     * Verify Email Recipients
     * @param name Name of the email
     * @return true if validated successfully
     */
    public boolean verifyEmailRecipients(String name){
        waitHelper.waitForPageToLoad(emailRecipients);
        By email = By.xpath(String.format("//ul[@role='listbox']//span/span[contains(@class,'MultiSelectDropdown-selected-value') and text()='%s']",name));
        return elementHelper.isElementDisplayed(email);
    }

    /**
     * Function to click More Details Button in Workflow History Page
     *
     */
    public void clickWorkFlowHistoryMoreDetailsButton() {
        waitHelper.waitForPageToLoad();
        logger().info("Clicking on workflow History for more details");
        waitHelper.waitForElementClickable(workflowHistoryMoreDetails);
        elementHelper.doClick(workflowHistoryMoreDetails);
    }

    /**
     * Function to click Workflow History Report Button to Generate WF History Report
     */
    public void clickWorkFlowHistoryReportPdf(){
        waitHelper.waitForPageToLoad();
        logger().info("Clicking on workflow History Report PDF");
        waitHelper.waitForElementClickable(workflowHistoryReport);
        elementHelper.doClick(workflowHistoryReport);
        waitHelper.waitForPageToLoad();
    }

    /**
     * Validate Workflow History Button
     * @return true if validated successfully else false
     */
    public boolean validateWorkflowHistoryReport()
    {
        waitHelper.waitForPageToLoad();
        return getPage(Validations.class).verifyElementExists(workflowHistoryReportTitle);
    }


}
