package com.aurigo.masterworks.testframework.webUI.constants.enums;

public enum Form {

    GlobalFundList("Global Fund List"),
    PlannedProject("Planned Projects"),
    BidManagement("Bid Management"),
    BidEstimateDetails("Bid Estimate Details"),
    BidLetting("Bid Letting"),
    BudgetEstimates("Budget Estimates"),
    BudgetTemplate("Budget Template"),
    PayEstimates("Pay Estimates"),
    Portfolio("Portfolio"),
    Punchlist("Punch List"),
    RiskRegister("Risk Register"),
    EnterpriseManagement("Enterprise Management"),
    PlanningManagement("Planning Management"),
    ContractForm("Contract Form"),
    ProjectManagement("Project Management"),
    Projects("Projects"),
    DocumentManagement("Document Management"),
    ExpenseForm("Expenses Form"),
    PurchaseOrder("Purchase Order"),
    ContractDetails("Contract Details"),
    WorkflowManagement("WorkFlow Management"),
    Administration("Administration"),
    MyTasks("My Tasks"),
    Inbox("Inbox"),
    EnterpriseSearch("Enterprise Search"),
    DocumentSearch("Document Search"),
    Needs("Needs"),
    MyReports("My Reports"),
    VendorScoring("Vendor Scoring"),
    StatementOfQualification("Statement Of Qualification"),
    DailyProgressTracking("Daily Progress Report"),
    UserManagement("User Management"),
    ReviseProgram("Revise Program"),
    Program("Program"),
    ProgramCategory("Program Category"),
    ScoringCategory("Scoring Category"),
    ScoringCriteria("Scoring Criteria"),
    ScoringDepartment("Scoring Department"),
    ItemPosting("Item Posting"),
    FormBuilder("Form Builder"),
    UserDetails("User Details"),
    WorkOrders("Work Orders"),
    WorkOrdersAndContracts("Work Orders and Contracts"),
    MasterContracts("Master Contracts"),
    MasterContractRevision("Master Contract Revision"),
    FundTransaction("Fund Transaction"),
    ContractModule("Contract Module"),
    MaterialsOnHand("Materials On Hand"),
    CustomForms("Custom Forms"),
    ProjectDetails("Project Details"),
    PlanningModule("Planning Module"),
    MinutesOfMeeting("Minutes Of Meeting"),
    LibraryManagement("Library Management"),
    Budgets("Budgets"),
    ReportManagement("Report Management"),
    ContractManagement("Contract Management"),
    MasterContractCategory("Master Contract Category");


    private String value;

    Form(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
