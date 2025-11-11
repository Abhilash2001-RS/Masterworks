package com.aurigo.masterworks.testframework.webUI.common;

import com.aurigo.masterworks.testframework.utilities.LocatorUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class WorkFlowHandler  {

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

    public WorkFlowHandler() {
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

    public void selectAction(String actionName){}

}
