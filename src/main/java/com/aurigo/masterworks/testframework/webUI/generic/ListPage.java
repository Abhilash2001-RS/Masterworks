package com.aurigo.masterworks.testframework.webUI.generic;

import com.aurigo.masterworks.testframework.utilities.ExceptionHandler;
import com.aurigo.masterworks.testframework.utilities.JavaScriptUtil;
import com.aurigo.masterworks.testframework.utilities.LocatorUtil;
import com.aurigo.masterworks.testframework.utilities.TestDataUtil;
import com.aurigo.masterworks.testframework.utilities.helper.FileHelper;
import com.aurigo.masterworks.testframework.utilities.helper.ScreenshotHelper;
import com.aurigo.masterworks.testframework.webUI.common.AlertHandler;
import com.aurigo.masterworks.testframework.webUI.common.ToastUtil;
import com.aurigo.masterworks.testframework.webUI.common.Validations;
import com.aurigo.masterworks.testframework.webUI.constants.Constants;
import com.aurigo.masterworks.testframework.webUI.constants.enums.ImportExportOptionsInListPage;
import com.aurigo.masterworks.testframework.webUI.constants.enums.ListPageFilterOptions;
import com.aurigo.masterworks.testframework.webUI.constants.enums.RibbonIcons;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.util.Strings;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ListPage extends GenericFormProposed{

    private static final Logger log = LoggerFactory.getLogger(ListPage.class);
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


    /**
     * Function to get row number of a list page having a particular cell value in a particular column of the list page.
     *
     * @param columnNameToSearchIn This is the column name to search in the List Page.
     * @param searchText           Value of the text that needs to searched in the list page.
     * @return An integer which is the Row number of the item in the list page.
     */
    public int getRowNumberFromListPage(String columnNameToSearchIn, String searchText) {
        int returnValue = -1;

        if (getPage(Validations.class).isNoRecordsFoundDivDisplayed()) {
            logger().info("No records were found in list page");
            return returnValue;//Return -1 if no rows are found
        }
        waitHelper.waitForPageTabHeaderToBeClickable();
        List<WebElement> listPageRows = elementHelper.getElement(listPageDataTable).findElements(By.tagName("tr"));
        int columnIndex = getColumnNumberOfHeader(getListPageHeaders(columnNameToSearchIn), columnNameToSearchIn);
        for (int countera = 0; countera < listPageRows.size(); countera++) {
            if (listPageRows.get(countera).findElements(By.tagName("td")).size() > 0) {
                var found = listPageRows.get(countera).findElements(By.tagName("td")).get(columnIndex).getText();
                if (found.equalsIgnoreCase(searchText)) {
                    returnValue = countera;
                    break;
                }
            }
        }

        return returnValue;
    }

    /**
     * @param columnName This is the column name to search in the List Page
     * @return List of table headers.
     */
    public List<WebElement> getListPageHeaders(String columnName) {
        navigation.switchFrameToContent();
        var table = elementHelper.getElement(listPageHeader);
        var tableRowHeaders = table.findElement(By.xpath(".//*[text()='" + columnName + "']/ancestor::tr"));
        return tableRowHeaders.findElements(By.tagName("th"));
    }

    /**
     * Get List page headers.
     *
     * @return List of table headers.
     */
    public List<String> getListPageHeaders() {
        List<String> headers = new ArrayList<>();
        navigation.switchFrameToContent();
        waitHelper.waitForPageTabHeaderToBeClickable();
        var table = elementHelper.getElement(listPageHeader);
        var tHeaders = table.findElements(By.tagName("th")).stream().filter(th -> !th.getAttribute("style").contains("display:none") || !th.getAttribute("style").contains("display: none")).collect(Collectors.toList());
        tHeaders.stream().filter(th -> Strings.isNotNullAndNotEmpty(elementHelper.doGetText(th))).forEach(t -> headers.add(t.getText()));
        headers.remove("No Data");
        return headers;
    }

    /**
     * @param headers          List of headers.
     * @param columnToSearchIn This is the column name to search in the List Page
     * @return Cloumn number of header.
     */
    public int getColumnNumberOfHeader(List<WebElement> headers, String columnToSearchIn) {
        waitForListPageLoading();
        int columnNumber = 0;

        for (var header : headers) {
            elementHelper.scrollToView(header);
            if (header.getText().equals(columnToSearchIn)) {
                break;
            }
            columnNumber++;
        }

        if (columnNumber > 7) {
            // Handling index bound error. Moving to the first td of the filtered row and then fetch the right column index value.
            var moveToElement = elementHelper.getElement(listPageDataTable).findElement(By.tagName("tr"))
                    .findElements(By.tagName("td"))
                    .stream().filter(th -> !th.getAttribute("style").contains("display: none;")).collect(Collectors.toList()).get(0);
            elementHelper.moveToElement(moveToElement);
        }

        return columnNumber;
    }

    /**
     * Gets all list page rows.
     *
     * @return List of List page rows.
     */
    public List<WebElement> getListPageRows() {
        navigation.switchFrameToContent();
        var table = elementHelper.getElement(listPageDataTable);
        return table.findElements(By.tagName("tr"));
    }

    /**
     * Function to get the number row in a open list page
     * @return Grid rows count
     */
    public int getNumberOfRowsInListPage() {
        // Checking if this piece of code works as expected.
        navigation.switchFrameToContent();
        waitHelper.waitForPageTabHeaderToBeClickable();
        List<WebElement> listPageRows = elementHelper.getElement(listPageDataTable).findElements(By.tagName("tr"));
        int numberOfRows = listPageRows.size();
        if (listPageRows.size() == 1 && listPageRows.get(0).getAttribute("class").equals("rgNoRecords")) {
            numberOfRows = 0;
        }
        return numberOfRows;
    }

    /**
     * Filter for a column number with text.
     *
     * @param columnNumber Column number to apply filter.
     * @param columnName   Column name where filter is applied.
     * @param searchText   Filtering text.
     * @param filterType   Type of Filter.
     * @return True, if completed without error.
     */
    public boolean filterForColumnNumber(int columnNumber, String columnName, String searchText, String filterType) {
        waitForListPageLoading();
        var table = driver.findElement(listPageHeader);
        var tableDatas = table.findElements(By.xpath(".//a[text()='" + columnName + "']/ancestor::tr/following-sibling::tr/td"));
        var searchBoxTableRow = tableDatas.get(columnNumber);
        elementHelper.moveToElement(searchBoxTableRow);
        var searchBox = searchBoxTableRow.findElement(By.tagName("input"));
        waitHelper.waitForElementClickable(searchBox);
        searchBox.clear();
        if (Strings.isNotNullAndNotEmpty(searchText)) {
            searchBox.sendKeys(searchText);
        }
        waitHelper.waitForPageToLoad();
        if (filterType.equalsIgnoreCase("contains")) {
            elementHelper.doSendKeys(Keys.ENTER);
            waitHelper.waitForPageToLoad();
            return true;
        } else {
            var filterButtonElement = searchBox.findElement(By.xpath(".//ancestor::td//*[@type='submit']"));
            elementHelper.scrollToView(filterButtonElement);
            filterButtonElement.click();
            waitHelper.waitForPageToLoad();
            By filterTypeButton = null;
            if (filterType.equalsIgnoreCase("Clear All Filter")) {
                // Temp fix and would be removed once the bug is fixed. Bug 408426
                String filterSpanSelectorXpathLocator = ".//*[contains(@id,'_C1_MWGrid_rfltMenu_detached')]//a//span[text()='%s']";
                filterTypeButton = By.xpath(String.format(filterSpanSelectorXpathLocator, filterType));
                waitHelper.waitForElementPresent(filterTypeButton);
            } else {
                filterTypeButton = By.xpath(String.format(filterSpanSelectorXpath, filterType));
                waitHelper.waitForElementClickable(filterTypeButton);
            }

            elementHelper.doClick(filterTypeButton);
            return true;
        }
    }

    /**
     * Validation to check the field returning empty value
     *
     * @param columnName Column name where filter needs to be applied.
     * @return True, if Field returns empty Value.
     */
    public boolean filterForEmptyColumn(String columnName) {
        waitForListPageLoading();
        int columnNumberOfHeader = getColNumOfListPageColHeader(columnName);
        var table = driver.findElement(listPageHeader);
        var tableDatas = table.findElements(By.xpath(".//a[text()='" + columnName + "']/ancestor::tr/following-sibling::tr/td"));
        var searchBoxTableRow = tableDatas.get(columnNumberOfHeader);
        elementHelper.moveToElement(searchBoxTableRow);
        var searchBox = searchBoxTableRow.findElement(By.tagName("input"));
        waitHelper.waitForElementClickable(searchBox);
        return Strings.isNullOrEmpty(searchBox.getText());
    }

    /**
     * Filter list page.
     *
     * @param columnToSearchIn column which has to be searched
     * @param searchText       the text to be searched in the specified column
     * @param filterType       the filter criteria to be used for filtering
     */
    public void filterListPage(String columnToSearchIn, String searchText, ListPageFilterOptions filterType) {
        waitForListPageLoading();
        int columnNumberOfHeader = getColNumOfListPageColHeader(columnToSearchIn);
        filterForColumnNumber(columnNumberOfHeader, columnToSearchIn, searchText, filterType.getValue());
        waitForListPageLoading();
    }

    /**
     * Waits for list page load.
     */
    public void waitForListPageLoading() {
        navigation.switchFrameToContent();
        waitHelper.waitForPageTabHeaderToBeClickable();
    }

    /**
     * Checks if List Page contains specified cell value in the search column.
     *
     * @param columnToSearchIn column which has to be searched
     * @param searchText       the text to be searched in the specified column
     * @return Returns true if searched field doesn't exists else false.
     */
    public boolean filterToVerifyElementDoesNotExists(String columnToSearchIn, String searchText) {
        filterListPage(columnToSearchIn, searchText, ListPageFilterOptions.EqualTo);
        return getNumberOfRowsInListPage() == 0;
    }

    /**
     * Filter for a checkbox column
     *
     * @param columnToSearchIn  column in which has to be searched
     * @param shouldMarkChecked filter by marking checked or not
     * @param filterType        the filter criteria to be used for filtering
     */
    public void filterCheckBoxColumn(String columnToSearchIn, boolean shouldMarkChecked, String filterType) {
        navigation.switchFrameToContent();

        var table = elementHelper.getElement(listPageHeader);
        int columnNumber = getColNumOfListPageColHeader(columnToSearchIn);

        var tds = table.findElements(By.xpath(".//a[text()='" + columnToSearchIn + "']/ancestor::tr/following-sibling::tr/td"));
        var searchBoxTR = tds.get(columnNumber);
        var checkBox = searchBoxTR.findElement(By.tagName("input"));

        if ((shouldMarkChecked && !checkBox.isSelected()) || (!shouldMarkChecked && checkBox.isSelected()))
            checkBox.click();

        WebElement filterButtonElement = checkBox.findElement(By.xpath(".//following-sibling::input"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", filterButtonElement);
        filterButtonElement.click();
        var filterTypeButton = elementHelper.getElement(By.xpath(String.format(filterSpanSelectorXpath, filterType)));
        waitHelper.waitForElementClickable(filterTypeButton);
        elementHelper.doClick(filterTypeButton);
        waitHelper.waitForPageToLoad();
    }

    /**
     * Filter for a checkbox column by Name
     *
     * @param columnToSearchIn    column in which has to be searched
     * @param shouldMarkedChecked filter by marking checked or not
     * @param filterType          the filter criteria to be used for filtering
     */
    public void filterCheckBoxColumnByName(ListPageColumnName columnToSearchIn, ListPageCheckBoxFieldStatus shouldMarkedChecked, ListPageCheckBoxFilterOptions filterType) {
        navigation.switchFrameToContent();

        var table = elementHelper.getElement(listPageHeader);
        int columnNumber = getColNumOfListPageColHeader(columnToSearchIn.getValue());

        var tds = table.findElements(By.xpath(".//a[text()='" + columnToSearchIn.getValue() + "']/ancestor::tr/following-sibling::tr/td"));
        var searchBoxTR = tds.get(columnNumber);
        var checkBox = searchBoxTR.findElement(By.tagName("input"));

        if ((shouldMarkedChecked.getValue() && !checkBox.isSelected()) || (!shouldMarkedChecked.getValue() && checkBox.isSelected()))
            checkBox.click();

        WebElement filterButtonElement = checkBox.findElement(By.xpath(".//following-sibling::input"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", filterButtonElement);
        filterButtonElement.click();
        var filterTypeButton = elementHelper.getElement(By.xpath(String.format(filterSpanSelectorXpath, filterType.getValue())));
        waitHelper.waitForElementClickable(filterTypeButton);
        elementHelper.doClick(filterTypeButton);
        waitHelper.waitForPageToLoad();
    }

    /**
     * Function to clear all filters from a list page column.
     *
     * @param columnToClearFilter column name on which filter is cleared.
     */
    public void clearAllFilterForColumn(String columnToClearFilter) {
        //Filter is getting cleared so using dummy text.
        String dummyText = "Lorem Ipsum";
        filterListPage(columnToClearFilter, dummyText, ListPageFilterOptions.ClearAllFilter);
        waitHelper.waitForPageToLoad();
    }

    /**
     * Function to select few visible rows in list page
     *
     * @param noOfRowsToSelect No of rows to be selected
     * @return - returns true if rows are available on the list page and gets selected, false otherwise
     */
    public boolean selectVisibleRows(int noOfRowsToSelect) {
        logger().info("Selecting " + noOfRowsToSelect + " visible rows in list page");
        var checkBoxes = elementHelper.getElements(By.xpath(".//td/input[contains(@id,'_MultiSelectCheckBox')]"));
        var count = checkBoxes.size();
        if (count > 0) {
            if (noOfRowsToSelect > count) {
                logger().info("Only " + count + " rows are available on the list page, selecting them all");
            } else {
                checkBoxes = checkBoxes.subList(0, noOfRowsToSelect);
            }
            for (var item : checkBoxes) {
                item.click();
            }
            return true;
        } else {
            logger().info("No rows available on the list page to select");
            return false;
        }

    }

    /**
     * Function to single click on a particular row in a open list page
     *
     * @param rowNumber Row number in the List Page
     */
    public void singleClickOnRowListPage(int rowNumber) {
        if (rowNumber == -1) {
            getPage(ScreenshotHelper.class).takeFullScreenshot("Requested row cannot be found");
            logger().fail("Requested row cannot be found");
            return;
        }
        waitHelper.waitForPageTabHeaderToBeClickable();
        try {
            List<WebElement> listPageCells = getListPageRows().get(rowNumber).findElements(By.tagName("td"));
            for (var itr : listPageCells) {
                if (itr.getText().length() > 1) {// && itr.isDisplayed()) {
                    itr.click();
                    break;
                }
            }
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }
    }

    /**
     * Function to single click on a particular cell in a row in a list page
     *
     * @param rowNumber    Row number in the List Page
     * @param columnHeader column header for the cell
     */
    public void singleClickOnCell(int rowNumber, String columnHeader) {
        waitHelper.waitForPageToLoad();
        int columnNumberOfHeader = getColNumOfListPageColHeader(columnHeader);
        try {
            List<WebElement> listPageCells = getListPageRows().get(rowNumber).findElements(By.tagName("td"));
            var visibleCells = listPageCells.stream().filter(WebElement::isDisplayed).collect(Collectors.toList());
            var cellToClick = visibleCells.get(columnNumberOfHeader);
            elementHelper.doClick(cellToClick);
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }
    }

    /**
     * Function to double click on a particular row in a open list page
     *
     * @param rowNumber Row number in the List Page
     */
    public void doubleClickOnRowListPage(int rowNumber) {
        Optional<WebElement> rowCellData = getListPageRows().get(rowNumber).findElements(By.tagName("td"))
                .stream().filter(cellData -> cellData.getText().length() > 1 && cellData.isDisplayed()).findFirst();
        elementHelper.doDoubleClick(rowCellData.get());
    }

    /**
     * Function to select all the rows present in a page of a List Page
     */
    public void selectAllRowsInListPage() {
        //Note - After refreshing page, This element is not accessbile .
        elementHelper.doClick(listPageMultiSelectCheckBox);
    }

    /**
     * Function to select all the rows present in a page of a List Page
     *
     * @param checkAllItem To validate Check All Items
     */
    public void selectAllRowsInListPageAutodesk(boolean checkAllItem) {
        if (checkAllItem)
            elementHelper.doClick(listPageMultiSelectCheckAllItem);
        else
            elementHelper.doClick(listPageMultiSelectCheckBoxValue);
    }

    /**
     * Function to single click on a particular row in a open list page via a search text and the Column where filter needs to be applied
     *
     * @param columnNameToSearchIn Column name header where the filter needs to be applied
     * @param searchText           String that needs to be searched in the applied filter
     */
    public void singleClickOnRowListPage(String columnNameToSearchIn, String searchText) {
        filterListPage(columnNameToSearchIn, searchText, ListPageFilterOptions.Contains);
        waitHelper.waitUntilElementDisappears(listPageGridLoadingDivHidden);
        int rowNum = getRowNumberFromListPage(columnNameToSearchIn, searchText);
        singleClickOnRowListPage(rowNum);
    }

    /**
     * Function to Double click on a particular row in a open list page via a search text and the Column where filter needs to be applied
     *
     * @param columnNameToSearchIn Column name header where the filter needs to be applied
     * @param searchText           String that needs to be searched in the applied filter
     */
    public void doubleClickOnRowListPage(String columnNameToSearchIn, String searchText) {
        filterListPage(columnNameToSearchIn, searchText, ListPageFilterOptions.EqualTo);
        doubleClickOnRowListPage(0);
    }

    /**
     * This function will click on the checkbox of the given row of the list page
     *
     * @param rowNum Row Num of checkbox to be clicked.
     */
    public void selectRowCheckboxByNumber(int rowNum) {
        navigation.switchFrameToContent();
        WebElement listPageDataTableElement = elementHelper.getElement(listPageDataTable);
        List<WebElement> listPageRows = listPageDataTableElement.findElements(By.tagName("tr"));
        listPageRows.get(rowNum).findElement(listPageRowCheckbox).click();
    }

    /**
     * Select random record from the list page.
     *
     * @param excludedRecords -   Records which are not part of the selection.
     */
    public void openRandomRecord(List<String> excludedRecords) {
        navigation.switchFrameToContent();
        WebElement listPageTable = elementHelper.getElement(listPageDataTable);
        List<WebElement> listPageRows = listPageTable.findElements(By.tagName("tr"));
        if (!listPageRows.isEmpty()) {
            if (excludedRecords == null) {
                listPageRows.get(TestDataUtil.getRandomNumber(0, listPageRows.size() - 1)).click();
                clickRibbonIcon(RibbonIcons.View);
            } else {
                listPageRows = listPageRows.stream().filter(p -> !excludedRecords.contains(p.getText())).collect(Collectors.toList());
                var rowToClick = listPageRows.get(TestDataUtil.getRandomNumber(0, listPageRows.size() - 1));
                elementHelper.scrollToView(rowToClick);
                elementHelper.doClick(rowToClick);
                clickRibbonIcon(RibbonIcons.View);
            }

            elementHelper.waitForPageToLoad();
        } else
            logger().fail("No Record is present in the List page.");
    }

    /**
     * Function to read the status of Checkbox, either active or inactive
     *
     * @param rowIdentifier            RowIdentifier
     * @param columnNameToSearchIn     columnNameToSearchIn
     * @param columnNameOfExpectedData ColumnNameOfExpecteddata
     * @return Checked attribute value.
     */
    public String getCheckBoxStatusFromListPage(String rowIdentifier, String columnNameToSearchIn, String columnNameOfExpectedData) {
        return getListPageRows()
                .get(getRowNumberFromListPage(columnNameToSearchIn, rowIdentifier))
                .findElements(By.tagName("td")).get(getColNumOfListPageColHeader(columnNameOfExpectedData))
                .findElement(By.xpath(".//input")).getAttribute("checked");
    }

    /**
     * Get cell data from list page table.
     *
     * @param rowNum     Row number of cell
     * @param columnName column number of cell
     * @return Cell data in String format.
     */
    public String getCellData(int rowNum, String columnName) {
        navigation.switchFrameToContent();
        waitHelper.waitForPageTabHeaderToBeClickable();
        int targetColumnNum = getColNumOfListPageColHeader(columnName);
        waitHelper.waitForPageToLoad();
        logger().info("Target column number-" + targetColumnNum);
        String cellData = null;
        try {
            int rowNumActual;
            if (rowNum == 0) {
                rowNumActual = 0;
            } else {
                rowNumActual = rowNum - 1;
            }
            logger().info("Row number-" + rowNumActual);
            var rowId = listRowPrefix + rowNumActual;
            if (!columnName.equals("IsActive") && !columnName.equals("Is Active") && !columnName.equals("IsDefault") && !columnName.equals("Is Default")) {
                var cells = elementHelper.getElement(listPageDataTable).findElement(By.id(rowId)).findElements(By.tagName("td"));
                logger().info("total headers-" + cells.size());
                if (cells.size() > targetColumnNum) {
                    cellData = cells.get(targetColumnNum).getText().trim();
                }
            } else {
                boolean isActive = elementHelper.getElement(listPageDataTable).findElement(By.id(rowId)).findElements(By.tagName("td")).get(targetColumnNum).findElement(By.xpath(".//input")).isSelected();
                cellData = String.valueOf(isActive);
            }

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("The record was not found in the list page in the column : " + columnName);
        }
        return cellData;
    }

    /**
     * Function to obtain all data from specific column
     *
     * @param columnHeaderName column name from which data is to be obtained
     * @return Grid rows column data.
     */
    public List<String> getColumnData(String columnHeaderName) {
        navigation.switchFrameToContent();
        waitHelper.waitForPageTabHeaderToBeClickable();
        List<String> dataList = new ArrayList<>();
        String cellData = null;
        int colIndex = getColNumOfListPageColHeader(columnHeaderName);
        waitHelper.waitForPageToLoad();
        if (getNumberOfRowsInListPage() > 0) {
            for (var row : getListPageRows()) {
                if (!row.getText().isEmpty()) {
                    cellData = row.findElements(By.tagName("td")).get(colIndex).getText();
                }
                if (cellData != null && !cellData.isEmpty()) {
                    dataList.add(cellData.trim());
                }
            }
        } else {
            logger().info("No Record is present in the List page.");
        }
        return dataList;
    }

    /**
     * Method to get the number of pages list page contains
     * @return Grid page count.
     */
    public int getNumberOfPagesOnListPage() {
        navigation.switchFrameToContent();
        return Integer.parseInt(elementHelper.doGetText(listPagePageInfo)
                .split("of")[1]
                .trim());
    }

    /**
     * Method to Get all the column data from all the pages under a particular column
     *
     * @param columnName               Name of the column.
     * @param isClearAllFilterRequired true if clear all filter is required
     * @return a list of Data under the specified column Name
     */
    public List<String> getColumnDataFromAllPages(String columnName, boolean isClearAllFilterRequired) {
        if (isClearAllFilterRequired) {
            clearAllFilters();
        }
        List<String> data = new ArrayList<>();
        waitHelper.waitForPageToLoadWithCustomTimeout(30);
        navigation.switchFrameToContent();
        waitHelper.waitForElementClickable(listPageFirstPageButton);
        elementHelper.doClick(listPageFirstPageButton);
        int pageNo = 1;
        while (pageNo <= getNumberOfPagesOnListPage()) {
            logger().info("Obtaining Data from Page: " + (pageNo));
            data.addAll(getColumnData(columnName));
            pageNo++;
            waitHelper.waitForPageToLoad();
            elementHelper.doClick(listPageNextPageButton);
            waitHelper.waitForPageToLoad();
        }
        return data;
    }


    /**
     * Function to verify whether a list of captions is present in the list page or not
     *
     * @param captionList list of captions
     * @return true, if all the captions are present on the list page. Else it returns false
     */
    public boolean validateListPageCaptions(List<String> captionList) {
        boolean isValidationPassed = true;

        for (var caption : captionList) {
            ColumnNumberAndName columnGridObj = filter(listPageHeader, caption);

            if (columnGridObj.ColumnNumber == 0) {
                logger().fail("'" + caption + "' caption is not present in the list page");
                isValidationPassed = false;
            } else {
                logger().pass("'" + caption + "' caption is present in the list page");
            }
        }
        return isValidationPassed;
    }

    /**
     * Generic Function to search in Filter for a specific Column
     *
     * @param elementID            , This is the parent element Id for the Filter Header
     * @param columnNameToSearchIn ,Column name in the list page where the filter needs to be applied on.
     * @return Column name and it's number.
     */
    protected ColumnNumberAndName filter(By elementID, String columnNameToSearchIn) {
        navigation.switchFrameToContent();
        waitHelper.waitForPageTabHeaderToBeClickable();
        var table = elementHelper.getElement(elementID);
        var headerTr = table.findElement(By.xpath(".//*[text()='" + columnNameToSearchIn + "']/ancestor::tr"));
        var ths = headerTr.findElements(By.xpath(".//th"));
        int columnNumber = 0;
        for (var element : ths) {
            String text = elementHelper.doGetText(element);
            if (text.equals(columnNameToSearchIn)) {
                break;
            }
            columnNumber++;
        }
        ColumnNumberAndName columnGridObj = new ColumnNumberAndName();
        columnGridObj.ColumnNumber = columnNumber;
        List<WebElement> columnNameList = new ArrayList<>();
        columnNameList.add(headerTr);
        columnGridObj.ColumnName = columnNameList;
        return columnGridObj;
    }

    /**
     * Function to get a column number of a list page column header
     *
     * @param colHeaderName Header of the Column in the List page
     * @return Grid column number
     */
    public int getColNumOfListPageColHeader(String colHeaderName) {
        ColumnNumberAndName columnGridObj = filter(listPageHeader, colHeaderName);
        return columnGridObj.ColumnNumber;
    }

    /**
     * Function to delete a record in a list page
     *
     * @param columnNameToSearchIn Column Name To Search In
     * @param searchText           Search Text
     * @return true if the record is successfully deleted. Else it returns false
     */
    public boolean deleteARecord(String columnNameToSearchIn, String searchText) {
        getPage(AlertHandler.class).acceptAlert(true);
        return filterToVerifyElementDoesNotExists(columnNameToSearchIn, searchText);
    }

    /**
     * Function to Edit the Customize List Popup fields.
     *
     * @param column        Name of the column that needs to be edited.
     * @param caption       New Value to be set.
     * @param setCheckbox   Check/Uncheck the Checkbox.
     * @param pageSizeValue Page Size value to be set in the popup.
     */
    public <E> void editCustomizeListTable(E column, String caption, boolean setCheckbox, String pageSizeValue) {
        WebElement tBody = elementHelper.getElement(listPageMainGrid).findElement(By.tagName("tbody"));
        WebElement tRow = tBody.findElements(By.tagName("tr")).stream().filter(tr -> tr.findElement(By.className("dragRow")).getText().equals(column.toString())).findFirst().orElse(null);

        List<WebElement> inputFields = tRow.findElements(By.tagName("input"));
        WebElement checkboxHandle = inputFields.get(0);
        WebElement captionHandle = inputFields.get(1);

        if (!checkboxHandle.isSelected() && setCheckbox) {
            checkboxHandle.click();
        } else if (checkboxHandle.isSelected() && !setCheckbox) {
            checkboxHandle.click();
        }
        captionHandle.clear();
        captionHandle.sendKeys(caption);
        elementHelper.doSendKeys(customizeListPageSizeTextBox, pageSizeValue);
    }

    /**
     * Get Selected columns under Customize list.
     *
     * @return Selected columns under Customize list.
     */
    public List<String> getSelectedColumnsUnderCustomizeList() {
        List<String> selectedColumns = new ArrayList<>();
        var tBody = elementHelper.getElement(listPageMainGrid).findElement(By.tagName("tbody"));
        List<WebElement> inputFields = tBody.findElements(By.tagName("tr")).stream().filter(tr -> tr.findElement(By.tagName("input")).isSelected()).collect(Collectors.toList());
        inputFields.forEach(c -> selectedColumns.add(c.findElements(By.tagName("input")).get(1).getAttribute("value")));

        return selectedColumns;
    }

    /**
     * Select columns in Customize List model.
     *
     * @param selectCheckBox -   If true, check columns. Else uncheck the columns.
     * @param columns        -   List of columns, which needs to be Checked/Un-Checked.
     */
    public <E> void selectAndSaveCustomizeListColumns(boolean selectCheckBox, E... columns) {
        openCustomizeListFromRibbon();
        var tBody = elementHelper.getElement(listPageMainGrid).findElement(By.tagName("tbody"));
        List<WebElement> inputFields = new ArrayList<>();
        if (selectCheckBox)
            inputFields = tBody.findElements(By.tagName("tr")).stream()
                    .filter(tr -> !tr.findElement(By.tagName("input")).isSelected())
                    .collect(Collectors.toList());
        else
            inputFields = tBody.findElements(By.tagName("tr")).stream()
                    .filter(tr -> tr.findElement(By.tagName("input")).isSelected())
                    .collect(Collectors.toList());

        for (E column : columns) {
            String enumValue = getEnumValue(column);
            var checkBox = inputFields.stream().filter(c -> c.findElements(By.tagName("input")).get(1).getAttribute("value").equals(enumValue)).findFirst().orElse(null);
            if (checkBox != null) {
                checkBox = checkBox.findElement(By.tagName("input"));
                elementHelper.doClick(checkBox);
            }
        }
        saveCustomizeList();
    }

    /**
     * Select all the columns in Customize List model.
     * @param scopeSetForAll If true, set for Set scope for all
     */
    public void selectAllCustomizeListColumns(boolean scopeSetForAll) {
        openCustomizeListFromRibbon();
        var tBody = elementHelper.getElement(listPageMainGrid).findElement(By.tagName("tbody"));
        List<WebElement> inputFields = tBody.findElements(By.tagName("tr")).stream()
                .filter(tr -> !tr.findElement(By.tagName("input")).isSelected())
                .collect(Collectors.toList());

        inputFields.forEach(c -> c.findElement(By.tagName("input")).click());

        if (scopeSetForAll)
            selectSetForAllOptionUnderCustomizeList();
        else
            selectSetForMeOnlyOptionUnderCustomizeList();

        saveCustomizeList();
    }

    /**
     * Select 'Set For All' option in Customize List model.
     */
    public void selectSetForAllOptionUnderCustomizeList() {
        selectScopeOption("Set for All");
    }

    /**
     * Select 'Set For Me Only' option in Customize List model.
     */
    public void selectSetForMeOnlyOptionUnderCustomizeList() {
        selectScopeOption("Set for Me only");
    }

    /**
     * Update Column in Customize List model.
     *
     * @param columnsToUpdate -   Columns to be updated.
     * @param isSetForAll     true if set for all
     */
    public <E> void updateCustomizeListColumns(Map<E, String> columnsToUpdate, boolean... isSetForAll) {
        openCustomizeListFromRibbon();
        if ((isSetForAll.length > 0) && isSetForAll[0]) {
            selectSetForAllOptionUnderCustomizeList();
        }
        var tBody = elementHelper.getElement(listPageMainGrid).findElement(By.tagName("tbody"));
        for (E keys : columnsToUpdate.keySet()) {
            var row = tBody.findElements(By.tagName("tr")).stream().filter(c -> c.findElement(By.className("dragRow")).getText().equals(keys.toString())).findAny().orElse(null);
            if (row != null) {
                var textField = row.findElements(By.tagName("input")).get(1);
                elementHelper.doSendKeys(textField, columnsToUpdate.get(keys));
            }
        }

        saveCustomizeList();
    }

    /**
     * Open Customize List model.
     */
    public void openCustomizeListFromRibbon() {
        clickRibbonIcon(RibbonIcons.OtherCustomizeList);
        waitHelper.waitForElementClickable(customizeListCancelButton);
    }

    /**
     * Save Customize List model.
     */
    public void saveCustomizeList() {
        waitHelper.waitForPageToLoad(customizeListSaveButton);
        elementHelper.doClick(customizeListSaveButton);
        elementHelper.waitForPageToLoad();
    }

    /**
     * Close Customize List model.
     */
    public void closeCustomizeListFromRibbon() {
        clickRibbonIcon(RibbonIcons.CustomizeListCancel);
    }

    /**
     * Get Page size value in Customize List model.
     *
     * @return -    Page size.
     */
    public String getCustomizeListPageSize() {
        return elementHelper.doGetAttribute(customizeListPageSizeTextBox, "value");
    }

    /**
     * Set Page size value in Customize List model.
     *
     * @param size -   Page size to be set.
     */
    public void setCustomizeListPageSize(Integer size) {
        elementHelper.doSendKeys(customizeListPageSizeTextBox, Integer.toString(size));
    }

    /**
     * Select the scope of option in Customize List model.
     *
     * @param optionText -   Option to be selected.
     */
    private void selectScopeOption(String optionText) {
        var scopeTable = elementHelper.getElement(customizeListScopeTable);
        var option = scopeTable.findElements(By.tagName("td")).stream().filter(opt -> opt.getText().equals(optionText)).findFirst().orElse(null);
        if (option != null && !option.isSelected())
            elementHelper.doClick(option);

        elementHelper.waitForPageToLoad();
    }

    /**
     * Method to create a search filter
     *
     * @param columnName name of column
     * @param filterName name of filter to be saved as
     * @param filterText Text to filter with
     */
    public void createSearchFilter(String columnName, String filterText, String filterName) {
        filterListPage(columnName, filterText, ListPageFilterOptions.EqualTo);
        waitHelper.waitForPageToLoad();
        createNewFilter(filterName);
    }


    /**
     * Select columns in Customize List model.
     *
     * @param selectCheckBox -   If true, check columns. Else uncheck the columns.
     * @param scopeSetForAll -   if true,  Select all radio button is selected
     * @param columns        -   List of columns, which needs to be Checked/Un-Checked.
     */
    public <E> void selectAndSaveCustomizeListColumnsForAll(boolean selectCheckBox, boolean scopeSetForAll, E... columns) {
        openCustomizeListFromRibbon();
        if (scopeSetForAll) {
            selectSetForAllOptionUnderCustomizeList();
        } else {
            selectSetForMeOnlyOptionUnderCustomizeList();
        }
        var tBody = elementHelper.getElement(listPageMainGrid).findElement(By.tagName("tbody"));
        List<WebElement> inputFields = new ArrayList<>();
        if (selectCheckBox)
            inputFields = tBody.findElements(By.tagName("tr")).stream()
                    .filter(tr -> !tr.findElement(By.tagName("input")).isSelected())
                    .collect(Collectors.toList());
        else
            inputFields = tBody.findElements(By.tagName("tr")).stream()
                    .filter(tr -> tr.findElement(By.tagName("input")).isSelected())
                    .collect(Collectors.toList());

        for (E column : columns) {
            String enumValue = getEnumValue(column);
            var checkBox = inputFields.stream().filter(c -> c.findElements(By.tagName("input")).get(1).getAttribute("value").equals(enumValue)).findFirst().orElse(null);
            if (checkBox != null) {
                checkBox = checkBox.findElement(By.tagName("input"));
                elementHelper.doClick(checkBox);
            }
        }
        saveCustomizeList();
    }

    /**
     * Import a file
     *
     * @param fileName - file to be uploaded
     * @return - true if error log button is not displayed
     */
    public boolean importFile(String fileName) {
        waitHelper.waitForPageToLoad();
        waitHelper.waitForElementPresent(chooseFile);
        File file = FileHelper.getFile(fileName, null);
        elementHelper.getElement(chooseFile).sendKeys(file.getAbsolutePath());
        waitHelper.waitForElementClickable(uploadButton);
        elementHelper.doClick(uploadButton);
        waitHelper.waitForPageToLoad();
        if (elementHelper.isElementEnabled(saveButtonInImport)) {
            elementHelper.doClick(saveButtonInImport);
            waitHelper.waitForPageToLoad();
        }
        return !validateRibbonIcon(RibbonIcons.ExcelErrorLog);
    }


    /**
     * Import a file
     *
     * @param fileName - file to be uploaded
     * @return - true if error log button is not displayed
     */
    public boolean importLibraryFile(String fileName) {
        waitHelper.waitForPageToLoad();
        waitHelper.waitForElementPresent(chooseFile);
        File file = FileHelper.getLibraryFile(fileName, null);
        elementHelper.getElement(chooseFile).sendKeys(file.getAbsolutePath());
        waitHelper.waitForElementClickable(uploadButton);
        elementHelper.doClick(uploadButton);
        waitHelper.waitForPageTabHeaderToBeClickable();
        if (validateRibbonIcon(RibbonIcons.ExcelErrorLog)) {
            return false;
        } else {
            waitHelper.waitForPageToLoad(RibbonIcons.Back);
            if (elementHelper.isElementEnabled(saveButtonInImport)) {
                elementHelper.doClick(saveButtonInImport);
                waitHelper.waitForPageToLoad();
            }
            return !validateRibbonIcon(RibbonIcons.ExcelErrorLog);
        }
    }

    /**
     * Click Last page button under pagination.
     */
    public void clickLastPage() {
        waitHelper.waitForPageToLoad(lastPageBtn);
        elementHelper.doClick(lastPageBtn);
        waitForListPageLoading();
    }


    /**
     * Validate Excel Template Export
     *
     * @param fileName   - name of the file
     * @param node       - host node
     * @param columnList - List of column names
     * @param sheetName  - Sheet name
     * @return - returns true if the expected headers are displayed, else false
     */
    public boolean validateExportedExcelSheet(String fileName, List<String> columnList, Host node, String sheetName) {
        boolean isAvailable=FileHelper.waitForFileToBeAvailable(fileName,60,1,node);
        logger().info(String.format("File available status = %s",isAvailable));
        List<String> columnHeadersInTemplate = ExcelUtil.getExcelColumnHeaders(fileName, node,sheetName);
        logger().info("Expected Column List" + columnList);
        logger().info("Actual Column List" + columnHeadersInTemplate);
        return columnHeadersInTemplate.containsAll(columnList);
    }

    /**
     * Validate the required exported data in the excel sheet
     *
     * @param parameter  parameter of the record
     * @param fileName   file name exported
     * @param columnName corresponding column name
     * @param sheetName  Sheet name
     * @param node       node
     * @return true on validation of the above
     */
    public boolean validateExportedDataInExcel(String parameter, String fileName, Host node, Enum columnName, String sheetName) {
        var excelData = ExcelUtil.getColumnData(fileName, node, columnName, sheetName);
        return excelData.contains(parameter);
    }

    //AutoDesk

    /**
     * Validate Toolbar under Workflow list
     *
     * @return true on Fields available
     */
    public boolean validateWorkflow() {
        clickRibbonIcon(RibbonIcons.Workflow);
        List<By> importExportOptions = new ArrayList<>();
        importExportOptions.add(By.xpath(String.format(textValidationInRibbonMenu, WorkflowOptions.Associate.getValue())));
        importExportOptions.add(By.xpath(String.format(textValidationInRibbonMenu, WorkflowOptions.ShowPendingOnUser.getValue())));

        return getPage(GenericForm.class).validateElementsExists(importExportOptions);
    }

    /**
     * Validate Report list Toolbar
     *
     * @param listPage               To select list page report
     * @param detailsPage            To select Details report
     * @param globalFundAvailability To select Global fund availability report
     * @param others                 To select other report types
     * @return true on validation fields displayed
     */
    public boolean validateReportList(boolean listPage, boolean detailsPage, boolean globalFundAvailability, boolean others) {
        clickRibbonIcon(RibbonIcons.Reports);
        List<By> importExportOptions = new ArrayList<>();
        if (listPage)
            importExportOptions.add(By.xpath(String.format(optionsInRibbonMenu, ReportOptions.ListPageReport.getValue())));
        if (detailsPage)
            importExportOptions.add(By.xpath(String.format(textValidationInRibbonMenu, ReportOptions.DetailsReport.getValue())));
        if (globalFundAvailability)
            importExportOptions.add(By.xpath(String.format(optionsInRibbonMenu, ReportOptions.GlobalFundAvailabilityReport.getValue())));
        if (others) {
            importExportOptions.add(By.xpath(String.format(optionsInRibbonMenu, ReportOptions.UserDetailsReport.getValue())));
            importExportOptions.add(By.xpath(String.format(optionsInRibbonMenu, ReportOptions.UserAccessDetailsReport.getValue())));
            importExportOptions.add(By.xpath(String.format(optionsInRibbonMenu, ReportOptions.WeeklyAccessSummary.getValue())));
            importExportOptions.add(By.xpath(String.format(optionsInRibbonMenu, ReportOptions.LoginLogs.getValue())));
            importExportOptions.add(By.xpath(String.format(optionsInRibbonMenu, ReportOptions.UserAccountLogs.getValue())));
        }
        return getPage(GenericForm.class).validateElementsExists(importExportOptions);
    }

    /**
     * Validate Toolbar under More list
     *
     * @param markActive    Variable to validate Active Inactive option
     * @param attachment    Variable to attachment option
     * @param customizeList Variable to validate customize list option
     * @param auditLog      Variable to validate audit log list option
     * @param items         Variable to validate Items list option
     * @param standardItems Variable to validate standardItems list option
     * @return true on Fields available
     */
    public boolean validateMoreList(boolean markActive, boolean attachment, boolean customizeList, boolean auditLog, boolean items, boolean standardItems) {
        clickRibbonIcon(RibbonIcons.More);
        List<By> importExportOptions = new ArrayList<>();
        if (markActive)
            importExportOptions.add(By.xpath(String.format(optionsInRibbonMenu, MoreOptions.MarkActiveInActive.getValue())));
        if (customizeList)
            importExportOptions.add(By.xpath(String.format(optionsInRibbonMenu, MoreOptions.CustomizeList.getValue())));
        if (attachment)
            importExportOptions.add(By.xpath(String.format(optionsInRibbonMenu, MoreOptions.Attachments.getValue())));
        if (auditLog)
            importExportOptions.add(By.xpath(String.format(textValidationInRibbonMenu, MoreOptions.AuditLog.getValue())));
        if (items) {
            importExportOptions.add(By.xpath(String.format(optionsInRibbonMenu, MoreOptions.RefreshLineNumber.getValue())));
            importExportOptions.add(By.xpath(String.format(optionsInRibbonMenu, MoreOptions.Groups.getValue())));
        }
        if (standardItems) {
            importExportOptions.add(By.xpath(String.format(optionsInRibbonMenu, MoreOptions.StandardTables.getValue())));
            importExportOptions.add(By.xpath(String.format(optionsInRibbonMenu, MoreOptions.Groups.getValue())));

        }
        return getPage(GenericForm.class).validateElementsExists(importExportOptions);
    }

    /**
     * Validate Toolbar under Single record selection workflow
     *
     * @param history     To validate History workflow value
     * @param ballInCourt To validate Ball in Court workflow value
     * @param workflow    To validate Workflow value
     * @return true on Fields available
     */
    public boolean validateWorkflowValues(boolean history, boolean ballInCourt, boolean workflow) {
        clickRibbonIcon(RibbonIcons.Workflow);
        List<By> importExportOptions = new ArrayList<>();
        if (history)
            importExportOptions.add(By.xpath(String.format(textValidationInRibbonMenu, WorkflowOptions.History.getValue())));
        if (ballInCourt)
            importExportOptions.add(By.xpath(String.format(textValidationInRibbonMenu, WorkflowOptions.BallInCourt.getValue())));
        if (workflow)
            importExportOptions.add(By.xpath(String.format(textValidationInRibbonMenu, WorkflowOptions.WorkflowUser.getValue())));
        return getPage(GenericForm.class).validateElementsExists(importExportOptions);
    }

    /**
     * Validate import export options in the drop down
     *
     * @param excelExport        To validate excelExport related dropdowns
     * @param withoutExcelExport To validate without excelExport related dropdowns
     * @return true on validation of the above
     */
    public boolean validateImportExportOptions(boolean excelExport, boolean withoutExcelExport) {
        clickRibbonIcon(RibbonIcons.ExcelImportOrExportDropDown);
        List<By> importExportOptions = new ArrayList<>();
        if (withoutExcelExport) {
            importExportOptions.add(By.xpath(String.format(optionsInRibbonMenu, ImportExportOptionsInListPage.ExcelImport.getValue())));
            importExportOptions.add(By.xpath(String.format(optionsInRibbonMenu, ImportExportOptionsInListPage.ExcelTemplateWithDataXLS.getValue())));
            importExportOptions.add(By.xpath(String.format(optionsInRibbonMenu, ImportExportOptionsInListPage.ExcelTemplateWithDataXLSX.getValue())));
            importExportOptions.add(By.xpath(String.format(optionsInRibbonMenu, ImportExportOptionsInListPage.ExcelTemplateXLS.getValue())));
            importExportOptions.add(By.xpath(String.format(optionsInRibbonMenu, ImportExportOptionsInListPage.ExcelTemplateXLSX.getValue())));
        }
        if (excelExport) {
            importExportOptions.add(By.xpath(String.format(optionsInRibbonMenu, ImportExportOptionsInListPage.ExcelExportXLS.getValue())));
            importExportOptions.add(By.xpath(String.format(optionsInRibbonMenu, ImportExportOptionsInListPage.ExcelExportXLSX.getValue())));
        }
        return getPage(GenericForm.class).validateElementsExists(importExportOptions);
    }

    /**
     * Click Filter Button
     */
    public void clickFilterButton() {
        navigation.switchFrameToContent();
        waitHelper.waitForPageTabHeaderToBeClickable();
        elementHelper.scrollToView(filterButton);
        elementHelper.doClick(filterButton);
        waitHelper.waitForPageToLoad();
        waitHelper.waitForElementPresent(filterPopUpWindow);
    }

    /**
     * Close Filter
     */
    public void closeFilter() {
        waitHelper.waitForPageToLoad();
        navigation.switchFrameToContent();
        elementHelper.doClick(filterButton);
        waitHelper.waitForPageToLoad();
    }

    /**
     * Clear All Filter
     */
    public void clearAllFilters() {
        logger().info("Clearing all Filters");
        clickFilterButton();
        waitHelper.waitForPageToLoad();
        waitHelper.waitForElementPresent(clearFilter);
        if (elementHelper.getElement(clearFilter).findElements(By.xpath("//../..//div[@id='divClearFilter' and contains(@class,'filter-inactive')]")).isEmpty()) {
            waitHelper.waitForPageToLoad(clearFilter);
            elementHelper.doClick(clearFilter);
            waitHelper.waitUntilElementDisappears(filterPopUpWindow);
        } else {
            waitHelper.waitForPageToLoad(filterButton);
            elementHelper.doClick(filterButton);
        }
        waitHelper.waitForPageTabHeaderToBeClickable();
    }

    /**
     * Select Filter
     *
     * @param filterName Name of the filter
     */
    public void selectFilter(String filterName) {
        clickFilterButton();
        waitHelper.waitForPageToLoad();
        String filterTemplate = "//div[text()='%s']/../preceding-sibling::td//input";
        By filter = By.xpath(String.format(filterTemplate, filterName));
        if (!elementHelper.isCheckBoxSelected(filter)) {
            JavaScriptUtil.clickElementByJS(elementHelper.getElement(filter), driver);
        }

    }

    /**
     * Click on Apply Filter Button
     */
    public void clickOnApplyFilterButton() {
        waitHelper.waitForPageToLoad();
        elementHelper.doClick(applyFilter);
        waitHelper.waitUntilElementDisappears(filterPopUpWindow);
    }

    /**
     * Create new Filter
     *
     * @param nameOfTheFilter Name of the filter
     */
    public void createNewFilter(String nameOfTheFilter) {
        clickFilterButton();
        elementHelper.doSendKeysUsingAction(addFilter, nameOfTheFilter);
        elementHelper.doClick(saveFilter);
        getPage(ToastUtil.class).waitForToastDisappears();
    }

    /**
     * Edit Filter
     *
     * @param filterName    Name of the exisiting filter
     * @param newFilterName Name of the new filter
     */
    public void editFilter(String filterName, String newFilterName) {
        navigation.switchFrameToContent();
        JavaScriptUtil.clickElementByJS(elementHelper.getElement(editFilter), driver);
        By editFilterName = By.xpath(String.format("//div[@title='%s']/..//input[@type='text']", filterName));
        By emptyFilter = By.xpath("//div[text()='']/../input[@type='text']");
        JavaScriptUtil.clickElementByJS(elementHelper.getElement(editFilterName), driver);
        elementHelper.getElement(editFilterName).clear();
        elementHelper.getElement(emptyFilter).sendKeys(newFilterName);
        elementHelper.doClick(saveFilter);
        getPage(ToastUtil.class).waitForToastDisappears();
        elementHelper.doClick(filterButton);
    }

    /**
     * Delete Filter
     *
     * @param filterName Name of the filter
     */
    public void deleteFilter(String filterName) {
        selectFilter(filterName);
        elementHelper.doClick(deleteFilter);
        getPage(AlertHandler.class).acceptAlert(true);
        if (getPage(ToastUtil.class).validatePresenceOfToastMessage()) {
            getPage(ToastUtil.class).waitForToastDisappears();
        }
        elementHelper.doClick(filterButton);
    }

    /**
     * Delete Filter
     *
     * @param filterName Name of the filter
     */
    public void deleteSpecifiedFilter(String filterName) {
        selectFilter(filterName);
        elementHelper.doClick(deleteFilter);
        getPage(AlertHandler.class).acceptAlert(true);
        getPage(ToastUtil.class).waitForToastDisappears();
    }

    /**
     * Select Default filter Options
     *
     * @param filterName Name of the filter
     */
    public void selectDefaultFilterOption(String filterName) {
        selectFilter(filterName);
        waitHelper.waitForPageToLoad();
        elementHelper.doClick(defaultFilter);
        getPage(ToastUtil.class).waitForToastDisappears();
    }

    /**
     * Validate the presence of the filter in the filter menu
     *
     * @param filterName Name of the filter
     * @return true if filtered
     */
    public boolean validatePresenceOfFilterInFilterOptions(String filterName) {
        waitHelper.waitForPageToLoad();
        waitHelper.waitForElementPresent(filterPopUpWindow);
        By filter = By.xpath(String.format("//div[@title='%s']", filterName));
        return getPage(Validations.class).verifyElementExists(filter);
    }

    /**
     * Validate if the filter is selected
     *
     * @param filterName Name of the filter
     * @return True if the filter is selected
     */
    public boolean validateSelectFilter(String filterName) {
        waitHelper.waitForPageToLoad();
        waitHelper.waitForElementPresent(filterPopUpWindow);
        By filter = By.xpath(String.format("//div[text()='%s']/../preceding-sibling::td//input", filterName));
        return elementHelper.isCheckBoxSelected(filter) && elementHelper.doGetAttribute(applyFilter, "class").contains("inactive");
    }

    /**
     * Validate the default filter
     *
     * @return true if validated successfully
     */
    public boolean validateDefaultFilter() {
        waitHelper.waitForPageToLoad();
        waitHelper.waitForElementPresent(filterPopUpWindow);
        return elementHelper.doGetAttribute(defaultFilter, "class").contains("active");
    }

    /**
     * Validate if filtered
     *
     * @param columnName Name of the column Name
     * @return true if column is filtered
     */
    public boolean validateIfFiltered(String columnName) {
        waitHelper.waitForPageToLoad();
        By filterColumn = By.xpath(String.format("//input[contains(@id,'Filter_%s')]", columnName.replaceAll("[ .]", "")));
        return elementHelper.doGetAttribute(filterColumn, "class").contains("Filtered");
    }

    /**
     * Deselect all the selected rows
     */
    public void deselectSelectedRows() {
        waitHelper.waitForPageToLoad(deselectRows);
        elementHelper.doClick(deselectRows);
        waitHelper.waitForPageTabHeaderToBeClickable();
    }

    /**
     * Validate the customization of the column list page
     *
     * @param columnName name of the column to validate
     * @return true if there else false
     */
    public boolean validateColumnList(String columnName) {
        List<String> columns = getListPageHeaders();
        waitHelper.waitForPageToLoad();
        return columns.contains(columnName);
    }

    /**
     * @param columnName       Name of column to search in
     * @param recordIdentifier Record to select
     * @param mailMergeConfig  Name of Mail merge configuration record to select
     * @param mailBodyTemp     Name of  mail body template to select
     * @return True if email sent successfully
     */
    public boolean validateMailAndEmail(String columnName, String recordIdentifier, String mailMergeConfig, String mailBodyTemp) {
        String optionTemplate = "//option[.='%s']";
        waitHelper.waitForPageToLoad();
        singleClickOnRowListPage(columnName, recordIdentifier);
        clickRibbonIcon(RibbonIcons.MergeAndEmail);
        waitHelper.waitForPageToLoad();
        JavaScriptUtil.clickElementByJS(elementHelper.getElement(recipientPicker), driver);
        waitHelper.waitForPageToLoad();
        waitHelper.waitForElementClickable(recipientPickerContainer);
        getPage(Picker.class).singleSelectByRowNumber(1, recipientPickerContainer);
        waitHelper.waitForElementClickable(mailMergeConfigSelectionXpath);
        elementHelper.doClick(mailMergeConfigSelectionXpath);
        elementHelper.doClick(By.xpath(String.format(optionTemplate, mailMergeConfig)));
        elementHelper.doClick(mailMergeBodyTemplateSelectionXpath);
        elementHelper.doClick(By.xpath(String.format(optionTemplate, mailBodyTemp)));
        elementHelper.doClick(mailMergeSaveButtonXpath);
        waitHelper.waitUntilElementDisappears(emailMergePopUp);
        boolean isTrue = getPage(ToastUtil.class).waitAndGetToastMessage().equalsIgnoreCase(Constants.EMAIL_SUCCESS_TOAST);
        getPage(ToastUtil.class).waitForToastDisappears();
        return isTrue;
    }

    /**
     * This method is used to get selected row count
     *
     * @return selected row count
     */
    public int getSelectedRowCount() {
        waitHelper.waitForPageToLoad();
        return Integer.parseInt(elementHelper.doGetText(selectedRowCount).replace("Selected", "").trim());
    }



}
