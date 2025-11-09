package com.aurigo.masterworks.testframework.webUI.constants.enums;

import java.util.ArrayList;
import java.util.List;

public enum RibbonReports {
    ListPageReport("List Page Report"),
    DetailsReport("Details Report"),
    BudgetRevisionDetails("Budget Revision Details"),
    ForecastDetailsComparisonReport("Forecast Details Comparison Report"),
    BudgetEstimateDetails("Budget Estimate Details"),
    LoginLogs("Login – Logs"),
    UserAccessDetailsReport("User Access Details Report"),
    ProgramBudgetVsFundForecastReport("Program Budget Vs Fund Forecast Report"),
    ProgramForecastReport("Program Forecast Report"),
    WorkProjectsUnderProjectsReport("Work Projects Under Projects Report"),
    ProgramForecastComparisonReport("Program Forecast Comparison Report"),
    ProgramFundForecastReport("Program Fund Forecast Report"),
    ProgramSummaryByFundReport("Program Summary by Fund Report"),
    ProjectForecastComparisonReport("Project Forecast Comparison Report"),
    ProjectForecastReport("Project Forecast Report"),
    ProjectFundForecastReport("Project Fund Forecast Report"),
    NewInstantReport("New Instant Report"),
    NewReport("New Report"),
    ProgramForecastDetailsReport("Program Forecast Details Report"),
    CurrentBudgetDetailsReport("Current Budget Details Report");

    private String value;

    RibbonReports(String value) {
        this.value = value;
    }

    /**
     * Method to get the list of items from the enum
     *
     * @return list of Ribbon Reports
     */
    public static List<String> getList() {
        var listToReturn = new ArrayList();
        for (var item : RibbonReports.values()) {
            listToReturn.add(item.getValue());
        }
        return listToReturn;
    }

    public String getValue() {
        return this.value;
    }
}
