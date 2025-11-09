package com.aurigo.masterworks.testframework.webUI.generic;

import com.aurigo.masterworks.testframework.utilities.LocatorUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ListPage extends GenericFormProposed{

    protected By listPageHeader;
    protected By listPageMultiSelectCheckBox;
    protected By listPageFirstPageButton;
    protected By listPageNextPageButton;
    protected By customizeListPageSizeTextBox;
    protected By customizeListSaveButton;
    protected By customizeListCancelButton;
    protected By listPageMainGrid;
    protected By isDefaultBtn;
    protected By filterPopUpAddBtn;
    protected By listPageDataTable;
    protected By listPageRowCheckbox;
    protected By filterNameText;
    protected By filterPopUp;
    protected By listPageGridLoadingDivHidden;
    protected By listPagePageInfo;
    protected By customizeListScopeTable;
    protected By listPageGridLoadingDiv;
    protected By lastPageBtn;
    protected By chooseFile;
    protected By uploadButton;
    protected By idHeader;
    protected By importSuccessMessage;
    protected By listPageMultiSelectCheckBoxValue;
    protected By listPageMultiSelectCheckAllItem;
    protected By filterButton;
    protected By filterPopUpWindow;
    protected By clearFilter;
    protected By saveFilter;
    protected By editFilter;
    protected By deleteFilter;
    protected By defaultFilter;
    protected By applyFilter;
    protected By addFilter;
    protected By deselectRows;
    protected By emailMergePopUp;
    protected By recipientPicker;
    protected By recipientPickerContainer;
    protected By mailMergeConfigSelectionXpath;
    protected By mailMergeBodyTemplateSelectionXpath;
    protected By mailMergeSaveButtonXpath;
    private By selectedRowCount;

    private By saveButtonInImport;
    private String filterSpanSelectorXpath = ".//*[@id='ctl00_C1_MWGrid_rfltMenu_detached']//span[text()='%s']";

    private String listRowPrefix;
    private WebDriver driver;

    public ListPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
        listRowPrefix = "ctl00_C1_MWGrid_ctl00__";

        var locators = LocatorUtil.getLocators("ListPage.json");
        listPageHeader = locators.get("listPageHeader");
        listPageMultiSelectCheckBox = locators.get("listPageMultiSelectCheckBox");
        listPageFirstPageButton = locators.get("listPageFirstPageButton");
        listPageNextPageButton = locators.get("listPageNextPageButton");
        customizeListPageSizeTextBox = locators.get("customizeListPageSizeTextBox");
        customizeListSaveButton = locators.get("customizeListSaveButton");
        customizeListCancelButton = locators.get("customizeListCancelButton");
        listPageMainGrid = locators.get("listPageMainGrid");
        isDefaultBtn = locators.get("isDefaultBtn");
        filterPopUpAddBtn = locators.get("filterPopUpAddBtn");
        listPageDataTable = locators.get("listPageDataTable");
        listPageRowCheckbox = locators.get("listPageRowCheckbox");
        filterNameText = locators.get("filterNameText");
        filterPopUp = locators.get("filterPopUp");
        customizeListScopeTable = locators.get("customizeListScopeTable");
        idHeader = locators.get("idHeader");
        listPageGridLoadingDivHidden = locators.get("listPageGridLoadingDivHidden");
        listPagePageInfo = locators.get("listPagePageInfo");
        chooseFile = locators.get("chooseFile");
        uploadButton = locators.get("uploadButton");
        saveButtonInImport = locators.get("saveButtonInImport");
        importSuccessMessage = locators.get("importSuccessMessage");
        listPageGridLoadingDiv = locators.get("listPageGridLoadingDiv");
        lastPageBtn = locators.get("lastPageBtn");
        listPageMultiSelectCheckAllItem = locators.get("listPageMultiSelectCheckAllItem");
        listPageMultiSelectCheckBoxValue = locators.get("listPageMultiSelectCheckBoxValue");
        filterButton = locators.get("filterButton");
        filterPopUpWindow = locators.get("filterPopUpWindow");
        clearFilter = locators.get("clearFilter");
        saveFilter = locators.get("saveFilter");
        editFilter = locators.get("editFilter");
        deleteFilter = locators.get("deleteFilter");
        defaultFilter = locators.get("defaultFilter");
        applyFilter = locators.get("applyFilter");
        addFilter = locators.get("addFilter");
        deselectRows = locators.get("deselectRows");
        emailMergePopUp = locators.get("emailMergePopUp");
        recipientPicker = locators.get("recipientPicker");
        mailMergeConfigSelectionXpath = locators.get("mailMergeConfigSelectionXpath");
        mailMergeBodyTemplateSelectionXpath = locators.get("mailMergeBodyTemplateSelectionXpath");
        mailMergeSaveButtonXpath = locators.get("mailMergeSaveButtonXpath");
        recipientPickerContainer = locators.get("recipientPickerContainer");
        selectedRowCount = locators.get("selectedRowCount");
    }




}
