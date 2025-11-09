package com.aurigo.masterworks.testframework.webUI.generic;


import com.aurigo.masterworks.testframework.utilities.LocatorUtil;
import com.aurigo.masterworks.testframework.utilities.TestDataUtil;
import com.aurigo.masterworks.testframework.utilities.helper.FileHelper;
import com.aurigo.masterworks.testframework.webUI.common.AlertHandler;
import com.aurigo.masterworks.testframework.webUI.common.RibbonMenu;
import com.aurigo.masterworks.testframework.webUI.common.ToastUtil;
import com.aurigo.masterworks.testframework.webUI.common.Validations;
import com.aurigo.masterworks.testframework.webUI.constants.Constants;
import com.aurigo.masterworks.testframework.webUI.constants.enums.RibbonIcons;
import com.aurigo.masterworks.testframework.webUI.constants.enums.TimeZoneList;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.util.Strings;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static com.aventstack.extentreports.Status.INFO;

/**
 * Generic Page for Forms.
 */
public class GenericForm extends RibbonMenu {

    public By linkDocumentButton;
    public By uploadDocument;
    public By downloadDocument;
    public By deleteDocument;
    public By attachmentPageTable;
    public By documentPicker;
    public By okButtonPicker;
    public By editDocumentTitle;
    public By attachmentTitle;
    public By cancelButtonPicker;
    public By noRecordDisplayPath;
    public By importXMLButton;
    public By chooseFile;
    public By addButtonCustomDynamicGrid;
    public By customDGridColumn0Input;
    public By customDGridColumn1Input;
    public By customDGridColumn2Input;
    public By customDGridColumn4Input;
    public By dynamicGridDataSaveButton;
    public By isFormPublished;
    public By attachmentTable;
    public By attachmentTableHeader;
    public By attachmentLabel;
    public By pageTabHeaderName;

    private String filterSpanSelectorXpath = "//div[contains(@id,'rfltMenu_detached')]//span[text()='%s']";
    private String detailTableInRadGridRow = "//tbody//tr[%d]//following-sibling::*[1]//table[contains(@class,'rgDetailTable')]";
    private String detailTableCellsTemplate = ".//div[contains(@id,'%s')]//tbody//tr[%d]//following-sibling::*[1]//table[contains(@class,'rgDetailTable')]//tbody//tr[%d]//td[not(contains(@style,'display:none;')) and not(contains(@style,'display: none;'))]";
    private String detailTableRowsTemplate = ".//div[contains(@id,'%s')]//tbody//tr//following-sibling::*[1]//table[contains(@class,'rgDetailTable')]//tbody//tr[not(contains(@style,'display:none;')) and not(contains(@style,'display: none;'))]";
    private String getColumnRows = "//div[@id='%s']//thead/tr/th";
    private String dGridInputBox = "//div[@id='%s']//thead/tr/following-sibling::tr/td[%d]/input";
    private String dGridColumnHeader = "//div[@id='%s']//thead/tr/td[%d]";
    private String filterButton = "//div[@id='%s']//thead/tr/following-sibling::tr/td[%d]//button";
    private String filterTypePath = "//span[text()='%s']";
    private String firstElementOfDGrid = "//div[@id='%s']//td/input[@type='checkbox']";
    private String ascendingArrowFilter = "//div[@id='%s']//th[%d]/input[@title='Sorted Ascending']";
    private String descendingArrowFilter = "//div[@id='%s']//th[%d]/input[@title='Sorted Descending']";
    private String rowValueLocator = "//div[@id='%s']/div/following-sibling::div//tbody/tr[%d]/td[%d]/div";


    public GenericForm(WebDriver driver) {
        super(driver);

        var locators = LocatorUtil.getLocators("GenericForm.json");
        linkDocumentButton = locators.get("linkDocument");
        uploadDocument = locators.get("uploadDocument");
        downloadDocument = locators.get("downloadDocument");
        deleteDocument = locators.get("deleteDocument");
        attachmentPageTable = locators.get("attachmentPageTable");
        documentPicker = locators.get("documentPicker");
        okButtonPicker = locators.get("okButtonPicker");
        editDocumentTitle = locators.get("editDocumentTitle");
        attachmentTitle = locators.get("attachmentTitle");
        cancelButtonPicker = locators.get("cancelButtonPicker");
        noRecordDisplayPath = locators.get("noRecordDisplayPath");
        importXMLButton = locators.get("importXMLButton");
        chooseFile = locators.get("chooseFile");
        addButtonCustomDynamicGrid = locators.get("addButtonCustomDynamicGrid");
        customDGridColumn0Input = locators.get("customDGridColumn0Input");
        customDGridColumn1Input = locators.get("customDGridColumn1Input");
        customDGridColumn2Input = locators.get("customDGridColumn2Input");
        customDGridColumn4Input = locators.get("customDGridColumn4Input");
        dynamicGridDataSaveButton = locators.get("dynamicGridDataSaveButton");
        isFormPublished = locators.get("isFormPublished");
        attachmentTable = locators.get("attachmentTable");
        attachmentTableHeader = locators.get("attachmentTableHeader");
        attachmentLabel = locators.get("attachmentLabel");
        pageTabHeaderName = locators.get("pageTabHeaderName");
    }

    /**
     * Enters text to text field's.
     *
     * @param fields A Map of locator and the text to be sent.
     */
    public void enterTextValues(Map<By, String> fields) {
        logger().info("Entering Text Field Values.");
        navigation.switchFrameToContent();
        waitHelper.waitForPageTabHeaderToBeClickable();
        fields.forEach((k, v) -> {
            waitHelper.waitForElementClickable(k);
            elementHelper.doSendKeys(k, v);
        });
    }


    /**
     * Select's Items from drop down field's using index.
     *
     * @param fields A Map of locator and the index to be selected.
     * @return List of Values at selected indexes
     */
    public LinkedHashMap<By, String> selectDropDownItemsUsingIndex(LinkedHashMap<By, Integer> fields) {
        logger().info("Selecting Drop Down Indexes.");
        LinkedHashMap<By, String> values = new LinkedHashMap<>();
        navigation.switchFrameToContent();
        waitHelper.waitForPageTabHeaderToBeClickable();
        fields.forEach((k, v) -> {
            waitHelper.waitForElementToBePresentAndClickable(k);
            values.put(k, elementHelper.selectComboBoxItemByIndex(k, v));
            navigation.switchFrameToContent();
            waitHelper.waitForPageTabHeaderToBeClickable();
        });
        return values;
    }

    /**
     * Gets Button element in dynamic grid.
     *
     * @param gridLocator Location of grid.
     * @param buttonText  Text of button.
     * @return Button element
     */
    public WebElement getButtonsDynamicGrid(By gridLocator, String buttonText) {
        // To be removed after validation on multiple forms
        // div[@id = 'Personnel']//input[contains(@value,'Add From')]
        navigation.switchFrameToContent();
        waitHelper.waitForElementPresent(gridLocator);
        elementHelper.scrollToView(gridLocator);
        String buttonGridLocator = String.format("//div[@id='%s']//input[@value='%s']", elementHelper.getLocatorAsString(gridLocator), buttonText);
        return elementHelper.getElement(By.xpath(buttonGridLocator));
    }

    /**
     * Gets list of rows element in dynamic grid.
     *
     * @param gridLocator Location of grid.
     * @return List of web element rows
     */
    public List<WebElement> getRowsDynamicGrid(By gridLocator) {
        navigation.switchFrameToContent();
        waitHelper.waitForElementPresent(gridLocator);
        elementHelper.scrollToView(gridLocator);
        String rowGridLocator = String.format("//div[@id='%s']//div[contains(@class,'k-grid-content')]//tr", elementHelper.getLocatorAsString(gridLocator));
        return elementHelper.getElements(By.xpath(rowGridLocator));
    }

    /**
     * Navigating to form builder
     *
     * @return - true if navigated
     */
    public boolean navigateToFormBuilder() {
        navigation.navigateToModulePageByName("Administration");
        boolean navigatedToFormBuilder = navigation.navigateToFormInLeftPaneTree("Form Builder");
        navigation.switchFrameToContent();
        waitHelper.waitForElementClickable(getRibbonIcon(RibbonIcons.New));
        return navigatedToFormBuilder;
    }


    /**
     * Gets list of rows element in Rad grid
     *
     * @param gridLocatorId Location of  grid by Id
     * @return List of web element rows
     */
    public List<WebElement> getRowsRadGrid(By gridLocatorId) {
        navigation.switchFrameToContent();
        waitHelper.waitForElementPresent(gridLocatorId);
        elementHelper.scrollToView(gridLocatorId);
        String rowRadGridLocator = String.format("//div[@id='%s']/table[contains(@class,'rgMasterTable')]/tbody//tr[not(contains(@style,'display:none;')) and not(contains(@style,'display: none;'))]", elementHelper.getLocatorAsString(gridLocatorId));
        return elementHelper.getElements(By.xpath(rowRadGridLocator));
    }

    /**
     * Gets list of all the rows element in Rad grid inclusive of hidden rows as well
     *
     * @param gridLocatorId Location of  grid by Id
     * @return List of web element rows
     */
    public List<WebElement> getAllRowsRadGrid(By gridLocatorId) {
        navigation.switchFrameToContent();
        waitHelper.waitForPageTabHeaderToBeClickable();
        elementHelper.scrollToView(gridLocatorId);
        String allGridLocator = String.format("//div[@id='%s']/table[contains(@class,'rgMasterTable')]/tbody//tr[not(contains(@style,'display:none;')) and not(contains(@style,'display: none;'))]", elementHelper.getLocatorAsString(gridLocatorId));
        return elementHelper.getElements(By.xpath(allGridLocator));
    }

    /**
     * Gets column index of any Grid
     *
     * @param gridId     Locator of the grid
     * @param columnName name of column to search
     * @return index of matching column name
     */
    public int getColumnIndexFromGrid(By gridId, String columnName) {
        navigation.switchFrameToContent();
        waitHelper.waitForElementPresent(gridId);
        elementHelper.scrollToView(gridId);
        var table = elementHelper.getElement(gridId);
        var divHeader = table.findElement(By.tagName("thead"));
        var allHeaders = divHeader.findElements(By.tagName("th"));
        List<String> columnNames = new ArrayList<>();
        allHeaders.stream().filter(h -> Strings.isNotNullAndNotEmpty(elementHelper.doGetText(h))).forEach(h -> columnNames.add(elementHelper.doGetText(h)));
        if (!allHeaders.get(0).findElements(By.xpath(".//input[@type='checkbox']")).isEmpty()) {
            // For SCON Builds
            return (columnNames.indexOf(columnName) + 1);
        } else if (!allHeaders.get(1).findElements(By.xpath(".//input[@type='checkbox']")).isEmpty()) {
            // For MCON Builds
            return (columnNames.indexOf(columnName) + 2);
        }
        return columnNames.indexOf(columnName);
    }

    /**
     * Gets list of rows element in Doc grid
     *
     * @param tableId Locator of the table containing "thead" and "tbody"
     * @return List of web element rows
     */
    public List<WebElement> getRowsFromDocGrid(By tableId) {
        navigation.switchFrameToContent();
        waitHelper.waitForElementPresent(tableId);
        elementHelper.scrollToView(tableId);
        String docGridLocator = String.format("//table[@id='%s']/tbody//tr[not(contains(@style,'display:none;')) and not(contains(@style,'display: none;'))]", elementHelper.getLocatorAsString(tableId));
        return elementHelper.getElements(By.xpath(docGridLocator));
    }

    /**
     * Get all cell in a row for Rad grid with the grid ID.
     *
     * @param gridLocatorId ID Locator of grid. Grid locator with By.id("") will only work
     * @param rowNumber     Number of the row.
     * @return List of cell web elements.
     */
    public List<WebElement> getRowCellsRadGrid(By gridLocatorId, int rowNumber) {
        navigation.switchFrameToContent();
        waitHelper.waitForElementPresent(gridLocatorId);
        elementHelper.scrollToView(gridLocatorId);
        String rowCellGridLocator = String.format("//div[@id='%s']//table/tbody[not(contains(@style,'display:none;')) and not(contains(@style,'display: none;'))]" +
                "//tr[not(contains(@style,'display:none;')) and not(contains(@style,'display: none;'))][%d]" +
                "//td[not(contains(@style,'display:none;')) and not(contains(@style,'display: none;'))]", elementHelper.getLocatorAsString(gridLocatorId), rowNumber);
        return elementHelper.getElements(By.xpath(rowCellGridLocator));
    }

    /**
     * Get all cell in Rad Grid footer with the Footer grid Id given.
     *
     * @param footerGridId ID Locator footer of grid.
     * @return List of cell web elements.
     */
    public List<WebElement> getFooterCellsForRadGrid(By footerGridId) {
        navigation.switchFrameToContent();
        waitHelper.waitForElementPresent(footerGridId);
        elementHelper.scrollToView(footerGridId);
        String footerLocator = String.format("//div[@id='%s']//tr[@class='rgFooter']/td", elementHelper.getLocatorAsString(footerGridId));
        return elementHelper.getElements(By.xpath(footerLocator));
    }

    /**
     * Get all cell  for Rad grid with the grid ID.
     *
     * @param gridLocatorId ID Locator of grid. Grid locator with By.id("") will only work
     * @return List of cell web elements.
     */
    public List<String> getAllCellsRadGrid(By gridLocatorId) {
        List<WebElement> values = new ArrayList<>();
        List<String> stringValue = new ArrayList<>();
        navigation.switchFrameToContent();
        waitHelper.waitForElementPresent(gridLocatorId);
        elementHelper.scrollToView(gridLocatorId);
        int rows = getRowsRadGrid(gridLocatorId).size();
        for (int i = 0; i < rows; i++) {
            values.addAll(elementHelper.getElements(By.xpath("//div[@id = '" + elementHelper.getLocatorAsString(gridLocatorId)
                    + "']/table/tbody//tr[" + i + "]//td[not(contains(@style,'display:none;')) and not(contains(@style,'display: none;'))]")));
        }
        for (WebElement value : values) {
            stringValue.add(value.getText());
        }

        return stringValue;
    }

    //Will be updated to handle multiple matching rows

    /**
     * Method to check if the row exists.
     *
     * @param tableId       ID Locator of table. Table locator with By.id("") will only work
     * @param columnName    Column name to search in
     * @param value         Value to  select
     * @param radGridHeader The rad grid to select
     * @return True if record exist, else false.
     */
    public boolean isRecordExistsInRadGrid(By tableId, By radGridHeader, String columnName, String value) {
        navigation.switchFrameToContent();
        var tableName = elementHelper.getElement(tableId);
        var divHeader = elementHelper.getElement(radGridHeader);
        List<WebElement> tableHeaders = divHeader.findElements(By.tagName("th"));
        List<String> headers = new ArrayList<>();
        tableHeaders.stream().filter(h -> Strings.isNotNullAndNotEmpty(elementHelper.doGetText(h))).forEach(h -> headers.add(elementHelper.doGetText(h)));
        int colIndex = headers.indexOf(columnName);
        var rows = tableName.findElements(By.tagName("tr")).stream().filter(r -> (!r.getAttribute("style").contains("display:none;") || (!r.getAttribute("style").contains("display: none;"))) && elementHelper.doGetText(r).contains(value)).collect(Collectors.toList());
        if (!rows.isEmpty()) {
            var found = rows.get(0).findElements(By.tagName("td")).stream().filter(d -> (!d.getAttribute("style").contains("display:none;")) && (!d.getAttribute("style").contains("display: none;")) && (!d.getAttribute("class").contains("rgExpandCol")) && (!d.getAttribute("class").contains("multiselect-column"))).collect((Collectors.toList()));
            if (!found.get(0).findElements(By.tagName("input")).isEmpty()) {
                if (found.get(1).getText().equals("")) {
                    colIndex += 1;
                }
                colIndex += 1;
            }
            return found.get(colIndex).getText().contains(value);
        }
        return false;
    }

    //Will be updated to handle multiple matching rows

    /**
     * Method to return the row with specified string
     *
     * @param tableId       ID Locator of table. Table locator with By.id("") will only work (give div id)
     * @param columnName    Column name to search in (give div id)
     * @param value         Value to  select
     * @param radGridHeader The rad grid to select
     * @return the row with the specified text.
     */
    public WebElement getRowFromRadGrid(By tableId, By radGridHeader, String columnName, String value) {
        var tableName = elementHelper.getElement(tableId);
        var divHeader = elementHelper.getElement(radGridHeader);
        List<WebElement> tableHeaders = divHeader.findElements(By.tagName("th"));
        List<String> headers = new ArrayList<>();
        tableHeaders.stream().filter(h -> Strings.isNotNullAndNotEmpty(elementHelper.doGetText(h))).forEach(h -> headers.add(elementHelper.doGetText(h)));
        int colIndex = headers.indexOf(columnName);
        var rows = tableName.findElements(By.tagName("tr")).stream().filter(r -> (!r.getAttribute("style").contains("display:none;") || (!r.getAttribute("style").contains("display: none;"))) && elementHelper.doGetText(r).contains(value)).collect(Collectors.toList());
        if (!rows.isEmpty()) {
            var found = rows.get(0).findElements(By.tagName("td")).stream().filter(d -> (!d.getAttribute("style").contains("display:none;")) && (!d.getAttribute("style").contains("display: none;"))).collect((Collectors.toList()));
            if (!found.get(0).findElements(By.tagName("input")).isEmpty()) {
                colIndex += 1;
            }
            if (!found.get(0).findElements(By.tagName("button")).isEmpty()) {
                colIndex += 1;
            }
            if (found.get(colIndex).getText().equals(value))
                return found.get(colIndex);
        }
        return null;
    }

    /***
     * Method to return row index with a specified string
     * @param gridId ID Locator of "div" tag. Grid locator with By.id("") will only work (give div id)
     * @param columnName Column name to search in (give div id)
     * @param value Value to  select
     * @return the row index with the specified text.
     */
    public int getRowIndexByRadGrid(By gridId, String columnName, String value) {
        int colNum = getColumnIndexFromGrid(gridId, columnName);
        List<String> colValue = getAllCellValuesInAColumnRadGrid(gridId, colNum + 1);
        if (!colValue.isEmpty()) {
            return colValue.indexOf(value) + 1;
        }
        return -1;
    }

    /**
     * Method to return the row index with specified string
     *
     * @param gridId        ID Locator of "div" tag. Grid locator with By.id("") will only work (give div id)
     * @param columnName    Column name to search in (give div id)
     * @param value         Value to  select
     * @param radGridHeader The rad grid to select
     * @return the row index with the specified text.
     */
    public int getRowIndexRadGrid(By gridId, By radGridHeader, String columnName, String value) {
        int colNum = getColumnIndexFromGrid(radGridHeader, columnName);
        List<String> colValue = getAllCellValuesInAColumnRadGrid(gridId, colNum + 1);
        if (!colValue.isEmpty()) {
            return colValue.indexOf(value) + 1;
        }
        return -1;
    }

    /**
     * Get value of cell in Rad grid.
     *
     * @param gridLocatorId ID Locator of grid. Grid locator with By.id("") will only work
     * @param rowNumber     Row number of cell
     * @param cellNumber    Cell number greater than or equal to 1.
     * @return Cell value.
     */
    public String getCellValueRadGrid(By gridLocatorId, int rowNumber, int cellNumber) {
        return elementHelper.doGetText(getRowCellsRadGrid(gridLocatorId, rowNumber)
                .get(cellNumber - 1));
    }

    /**
     * Get value of checkbox cell in Rad grid.
     *
     * @param gridLocatorId ID Locator of grid. Grid locator with By.id("") will only work
     * @param rowNumber     Row number of cell
     * @param cellNumber    Cell number greater than or equal to 1.
     * @return Cell value.
     */
    public String getCheckboxCellValueRadGrid(By gridLocatorId, int rowNumber, int cellNumber) {
        var elements = getRowCellsRadGrid(gridLocatorId, rowNumber).get(cellNumber - 1);
        var checkbox = elements.findElement(By.xpath("//input[@type='checkbox']"));
        var checked = checkbox.getAttribute("checked");
        return Strings.isNullOrEmpty(checked) ? "No" : checked.equals("true") ? "Yes" : "No";
    }

    /**
     * Get value of cell in Rad grid.
     *
     * @param gridLocatorId ID Locator of grid.
     * @param rowNumber     Row number of cell
     * @param columnName    Column name
     */
    public String getCellValueRadGrid(By gridLocatorId, int rowNumber, String columnName) {
        var columnMap = getRadGridColumnMap(gridLocatorId);
        var cellNumber = columnMap.get(columnName);
        var element = getRowCellsRadGrid(gridLocatorId, rowNumber)
                .get(cellNumber);
        return element.getText();
    }

    /**
     * Method to get the column map of a rad grid
     *
     * @param gridLocatorId Locator of the RadGrid
     * @return Column Map of Rad Grid
     */
    public HashMap<String, Integer> getRadGridColumnMap(By gridLocatorId) {
        var thElements = elementHelper.getElement(gridLocatorId).findElements(By.xpath(".//th[not(contains(@style,'display:none'))]"));
        HashMap<String, Integer> columnMap = new HashMap<>();
        for (int i = 0; i < thElements.size(); i++) {
            var innerText = elementHelper.doGetText(thElements.get(i));
            if (Strings.isNotNullAndNotEmpty(innerText)) {
                columnMap.put(innerText, i);
            }
        }
        return columnMap;
    }

    /**
     * Enter value of cell in Rad grid.
     *
     * @param gridLocatorId ID Locator of grid.
     * @param rowNumber     Row number of cell
     * @param columnName    Column name
     * @param text          text value to be entered
     */
    public void enterCellValueRadGrid(By gridLocatorId, int rowNumber, String columnName, String text) {
        var columnMap = getRadGridColumnMap(gridLocatorId);
        var cellNumber = columnMap.get(columnName);
        var element = getRowCellsRadGrid(gridLocatorId, rowNumber)
                .get(cellNumber);
        waitHelper.waitForPageToLoad();
        elementHelper.doSendKeysUsingAction(element, text);
    }

    /**
     * Enter value of cell in Rad grid detail table.
     *
     * @param gridLocatorId       ID Locator of grid.
     * @param rowNumber           Row number of cell
     * @param detailItemRowNumber row number of the detail table row
     * @param columnName          Column name
     * @param text                text value to be entered
     */
    public void enterCellValueDetailTableRadGrid(By gridLocatorId, int rowNumber, int detailItemRowNumber, String columnName, String text) {
        var element = getRadGridDetailTableCell(gridLocatorId, rowNumber, detailItemRowNumber, columnName);
        elementHelper.scrollToView(element);
        elementHelper.doSendKeysUsingAction(element, text);
    }

    /**
     * Get value of cell in Rad grid detail table.
     *
     * @param gridLocatorId       ID Locator of grid.
     * @param rowNumber           Row number of cell
     * @param detailItemRowNumber row number of the detail table row
     * @param columnName          Column name
     * @return Cell value
     */
    public String getCellValueDetailTableRadGrid(By gridLocatorId, int rowNumber, int detailItemRowNumber, String columnName) {
        var element = getRadGridDetailTableCell(gridLocatorId, rowNumber, detailItemRowNumber, columnName);
        elementHelper.scrollToView(element);
        return element.getText();
    }

    /**
     * Get the rows in rad grid detail table
     *
     * @param gridLocatorId grid locator id
     * @return rad grid table rows
     */
    public List<WebElement> getRadGridDetailTableRows(By gridLocatorId) {
        return elementHelper.getElements(By.xpath(String.format(detailTableRowsTemplate, elementHelper.getLocatorAsString(gridLocatorId))));
    }

    /**
     * Get cell in Rad grid detail table.
     *
     * @param gridLocatorId       ID Locator of grid.
     * @param rowNumber           Row number of cell
     * @param detailItemRowNumber row number of the detail table row
     * @param columnName          Column name
     */
    public WebElement getRadGridDetailTableCell(By gridLocatorId, int rowNumber, int detailItemRowNumber, String columnName) {
        var detailTable = elementHelper.getElement(gridLocatorId).findElement(By.xpath(String.format(detailTableInRadGridRow, rowNumber)));
        var thElements = detailTable.findElements(By.xpath(".//thead//tr//th[not(contains(@style,'display:none'))]"));
        HashMap<String, Integer> columnMap = new HashMap<>();
        for (int i = 0; i < thElements.size(); i++) {
            var innerText = elementHelper.doGetText(thElements.get(i));
            if (Strings.isNotNullAndNotEmpty(innerText)) {
                columnMap.put(innerText, i);
            }
        }
        var cellNumber = columnMap.get(columnName);
        var cells = elementHelper.getElements(By.xpath(String.format(detailTableCellsTemplate, elementHelper.getLocatorAsString(gridLocatorId), rowNumber, detailItemRowNumber)));
        return cells.get(cellNumber);
    }

    /**
     * Method to get CellData From Rad Grid Table
     *
     * @param tableId    ID Locator of table. Table locator with By.id("") will only work
     * @param columnName Column name to search in
     * @param rowNum     Row number to select from
     * @return String value of the Cell data
     */
    public String getCellDataFromRadGrid(By tableId, String columnName, int rowNum) {
        int columnNum = 1;
        var tableName = elementHelper.getElement(tableId);
        List<WebElement> tableHeaders = tableName.findElements(By.tagName("th"));
        for (WebElement tableHeader : tableHeaders) {
            if (tableHeader.getText().equals(columnName)) {
                break;
            } else {
                columnNum += 1;
            }
        }
        return elementHelper.getElement(By.xpath("//table[@id='" + elementHelper.getLocatorAsString(tableId) +
                "']/tbody/tr[" + rowNum + "]/td[" + columnNum + "]")).getText();
    }

    /**
     * Method to single click on a row in Rad Grid
     *
     * @param tableId ID Locator of table. Table locator with By.id("") will only wor
     * @param rowNum  Row number to click on
     */
    public void singleClickOnRow(By tableId, int rowNum) {
        var table = elementHelper.getElement(tableId);
        elementHelper.doClick(table.findElement(By.xpath("./tbody/tr[" + rowNum + "]")));
    }

    /**
     * Method to double click on a row in Rad Grid
     *
     * @param tableId ID Locator of table. Table locator with By.id("") will only wor
     * @param rowNum  Row number to click on
     */
    public void doubleClickOnRow(By tableId, int rowNum) {
        var table = elementHelper.getElement(tableId);
        elementHelper.doDoubleClick(table.findElement(By.xpath("./tbody/tr[" + rowNum + "]")));
    }

    /**
     * Method to click on Row in Document Attachment section in View Mode
     *
     * @param rowNum Row number
     */
    public void singleClickOnAttachmentGridInViewMode(int rowNum) {
        elementHelper.doClick(By.xpath("//div[contains(@id,'gridAttachment_GridData')]//table[contains(@id,'gridAttachment') and not(contains(@id,'app'))]//tbody/tr[" + rowNum + "]"));
    }

    /**
     * Method to single click on a row in Rad Grid
     *
     * @param tableId ID Locator of table.
     * @param rowNum  Row number to click on
     */

    public void singleClickOnRowCheckBox(By tableId, int rowNum) {
        elementHelper.doClick(By.xpath("//table[contains(@id,'" + elementHelper.getLocatorAsString(tableId) +
                "')]/tbody/tr[" + rowNum + "]//input[contains(@id,'MultiSelectCheckBox')]"));
    }

    /**
     * Method to get xpath of the Multi select checkbox from the table ID
     *
     * @param tableId ID Locator of table.
     * @param rowNum  Row number to click on
     * @return xpath of the checkbox
     */
    public By getXpathOfMultiSelectCheckBoxFromTableId(By tableId, int rowNum) {
        return By.xpath("//table[contains(@id,'" + elementHelper.getLocatorAsString(tableId) +
                "')]/tbody/tr[" + rowNum + "]//input[contains(@id,'MultiSelectCheckBox')]");
    }

    /**
     * Get all cell values in a column from a rad grid
     *
     * @param gridLocatorId ID Locator of grid. Grid locator with By.id("") will only work
     * @param columnNumber  Column number.
     * @return List of cell values in a column
     */
    public List<String> getAllCellValuesInAColumnRadGrid(By gridLocatorId, int columnNumber) {
        List<String> cellValues = new ArrayList<>();
        var rows = getAllRowsRadGrid(gridLocatorId);
        int i = 1;
        while (i <= rows.size()) {
            if (rows.get(i - 1).getAttribute("class").equals("rgNoRecords")) {
                ++i;
                continue;
            }
            cellValues.add(getCellValueRadGrid(gridLocatorId, i, columnNumber));
            ++i;
        }
        return cellValues;
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
        waitHelper.waitForPageTabHeaderToBeClickable();
        fields.forEach((fieldIdentifier, expectedText) -> {
            var actualText = elementHelper.doGetText(By.id(elementHelper.getLocatorAsString(fieldIdentifier) + "_Text"));
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
     * Verify the warning Required text when trying to save without entering required values.
     *
     * @param requiredText Required labels locator's
     * @return True if no issue occurred, else false
     */
    public boolean verifyRequiredWarningLabels(List<By> requiredText) {
        logger().log(INFO, "Verifying Required fields.");
        clickRibbonIcon(RibbonIcons.New);
        waitHelper.waitForPageToLoad();
        clickRibbonIcon(RibbonIcons.Save);
        boolean validated = validateElementsExists(requiredText);
        clickCancel();
        return validated;
    }

    /**
     * Verify given text labels with it's locators are verified.
     *
     * @param labels Locators of elements to check if exists.
     * @return True if no issue occurred, else false
     */
    public boolean validateElementsExists(List<By> labels) {
        try {
            waitHelper.waitForPageToLoad();
            navigation.switchFrameToContent();
            elementHelper.scrollToView(labels.get(0));
            waitHelper.waitForElementPresent(labels.get(0));
            getPage(Validations.class).verifyElementsExists(labels);
            waitHelper.waitForPageToLoad();
            return true;
        } catch (AssertionError e) {
            return false;
        }
    }

    /**
     * Click New and wait for Cancel button in new page to appear.
     */
    public void clickNew() {
        logger().log(INFO, "Clicking New");
        clickRibbonIcon(RibbonIcons.New);
        navigation.switchFrameToContent();
        waitHelper.waitForPageTabHeaderToBeClickable();
    }

    /**
     * Click Delete.
     */
    public void clickDelete() {
        logger().log(INFO, "Clicking Delete");
        clickRibbonIcon(RibbonIcons.Delete);
    }

    /**
     * Click Delete with Alert
     */
    public void clickDeleteWithAlert() {
        logger().log(INFO, "Clicking Delete");
        clickRibbonIcon(RibbonIcons.Delete);
        getPage(AlertHandler.class).acceptAlert(true);
        waitHelper.waitForPageToLoad();
    }

    /**
     * Click Save with Alert
     *
     * @param accept true if the alert is to be accepted
     */
    public void clickSaveWithAlert(boolean accept) {
        logger().log(INFO, "Clicking Save");
        clickRibbonIcon(RibbonIcons.Save);
        getPage(AlertHandler.class).acceptAlert(accept);
        if (accept) {
            waitHelper.waitForPageToLoad(getRibbonIcon(RibbonIcons.New));
        } else {
            waitHelper.waitForPageToLoad(getRibbonIcon(RibbonIcons.Save));
        }
    }

    /**
     * Click Save and wait for New button in list page to appear.
     */
    public void clickSave() {
        logger().log(INFO, "Clicking Save");
        clickRibbonIcon(RibbonIcons.Save);
        navigation.switchFrameToContent();
        waitHelper.waitForPageTabHeaderToBeClickable();
    }

    /**
     * Click Save and wait for New button in list page to appear based on the flag.
     *
     * @param waitForNewButton if true, wait for ribbon menu new button
     */
    public void clickSave(boolean waitForNewButton) {
        logger().log(INFO, "Clicking Save");
        clickRibbonIcon(RibbonIcons.Save);
        navigation.switchFrameToContent();
        waitHelper.waitForPageToLoad();
        if (waitForNewButton) {
            waitHelper.waitForElementClickable(getRibbonIcon(RibbonIcons.View));
        }
    }

    /**
     * Click Back and wait for page to load.
     */
    public void clickBack() {
        logger().log(INFO, "Clicking Back");
        clickRibbonIcon(RibbonIcons.Back);
        waitHelper.waitForPageTabHeaderToBeClickable();
    }

    /**
     * Click Cancel and wait for View button in list page to appear.
     *
     * @param waitForViewButton To wait for New button
     */
    public void clickCancel(boolean waitForViewButton) {
        logger().log(INFO, "Clicking Cancel");
        clickRibbonIcon(RibbonIcons.Cancel);
        navigation.switchFrameToContent();
        waitHelper.waitForPageToLoad();
        if (waitForViewButton) {
            waitHelper.waitForElementClickable(getRibbonIcon(RibbonIcons.View));
        }
    }

    /**
     * Click Cancel and moves to the list page.
     */
    public void clickCancel() {
        logger().log(INFO, "Clicking Cancel");
        clickRibbonIcon(RibbonIcons.Cancel);
        navigation.switchFrameToContent();
        waitHelper.waitForLoadingSpinnerDisappear();
    }

    /**
     * Click Close button.
     */
    public void clickClose() {
        logger().log(INFO, "Clicking Close");
        clickRibbonIcon(RibbonIcons.Close);
        navigation.switchFrameToContent();
        waitHelper.waitForPageToLoad();
    }

    /**
     * Click Edit button.
     */
    public void clickEdit() {
        logger().log(INFO, "Clicking Edit");
        clickRibbonIcon(RibbonIcons.Edit);
        navigation.switchFrameToContent();
        waitHelper.waitForPageTabHeaderToBeClickable();
    }

    /**
     * Click Edit and wait for Cancel button in edit page to appear.
     *
     * @param waitForCancelButton To wait for Cancel button
     */
    public void clickEdit(boolean waitForCancelButton) {
        logger().log(INFO, "Clicking Edit");
        clickRibbonIcon(RibbonIcons.Edit);
        navigation.switchFrameToContent();
        waitHelper.waitForPageToLoad();
        if (waitForCancelButton) {
            waitHelper.waitForElementClickable(getRibbonIcon(RibbonIcons.Cancel));
        }
    }

    /**
     * Click View button.
     */
    public void clickView() {
        logger().log(INFO, "Clicking View");
        clickRibbonIcon(RibbonIcons.View);
        navigation.switchFrameToContent();
        waitHelper.waitForPageToLoad();
    }

    /**
     * Click View and wait for Cancel button in view page to appear.
     *
     * @param waitForCancelButton To wait for Cancel button
     */
    public void clickView(boolean waitForCancelButton) {
        logger().log(INFO, "Clicking View");
        clickRibbonIcon(RibbonIcons.View);
        navigation.switchFrameToContent();
        waitHelper.waitForPageToLoad();
        if (waitForCancelButton) {
            waitHelper.waitForElementClickable(getRibbonIcon(RibbonIcons.Cancel));
        }
    }

    /**
     * Click attachments ribbon icon
     */
    public void clickAttachments() {
        clickRibbonIcon(RibbonIcons.Attachment);
    }

    /**
     * Get Generic Xpath for the asterisk sign from the label's xpath
     *
     * @param labelXpath Label's xpath
     * @return Asterisk sign By xpath locator
     */
    public By getRequiredAsteriskXpath(By labelXpath) {
        String prefixXpath = elementHelper.getLocatorAsString(labelXpath);
        return By.xpath(prefixXpath + "/following-sibling::*[contains(text(),'*')]");
    }

    /**
     * Get generic requires label Xpath using Label's Xpath
     *
     * @param labelXpath Label's Xpath
     * @return Required warning xpath locator.
     */
    public By getRequiredWarningXpath(By labelXpath) {
        String prefixXpath = elementHelper.getLocatorAsString(labelXpath);
        return By.xpath(prefixXpath + "/../following-sibling::td//span[contains(text(),'Required!')]");
    }

    /**
     * Get generic requires label Xpath using Label's Xpath if "Clear"is present next to it
     *
     * @param labelXpath Label's Xpath
     * @return Required warning xpath locator.
     */
    public By getRequiredWarningXpathIfClearPresent(By labelXpath) {
        String prefixXpath = elementHelper.getLocatorAsString(labelXpath);
        return By.xpath(String.format("%s/ancestor::td[@class='tdTextValue']/following-sibling::td/span[text()='Required!']", prefixXpath));
    }

    /**
     * Get generic requires label Xpath using Label's Xpath
     *
     * @param labelXpath Label's Xpath
     * @return Required warning xpath locator.
     */
    public By getRequiredWarningXpathIfPickerPresent(By labelXpath, boolean isDatePickerPresent) {
        String prefixXpath = elementHelper.getLocatorAsString(labelXpath);
        if (isDatePickerPresent) {
            return By.xpath(String.format("%s/ancestor::td[@data-mw-redesign='true']/following-sibling::td//span[(contains(text(),'Required!')) and (not(contains(@style,'display:none;')))]", prefixXpath));
        }
        return By.xpath(String.format("%s/following-sibling::span[(contains(text(),'Required!')) and (not(contains(@style,'display:none;')))]", prefixXpath));
    }

    /**
     * Expands all rows in a rad grid
     *
     * @param radGridSelector Rad Grid Selector
     */
    public void expandAllRowsInRadGrid(By radGridSelector) {
        var expandAllBtn = elementHelper.getElement(radGridSelector).findElement(By.xpath(".//*[contains(@id,'AllBtnExpandColumn')]"));
        waitHelper.waitForElementClickable(expandAllBtn);
        elementHelper.doClick(expandAllBtn);
        waitHelper.waitForPageToLoad();
    }

    /**
     * Get xpath of the required element under the specified column in a table
     *
     * @param rowNumber  row number of the element
     * @param headerName column header name
     * @param tableXpath xpath of the table
     * @return xpath of the element
     */
    protected By getXpathOfElementInTable(int rowNumber, String headerName, By tableXpath) {
        logger().info(String.format("Get xpath of the element in row - %s and under column - %s", rowNumber, headerName));
        int columnNumber = getColumnNumberInTable(headerName, tableXpath);
        var xpathString = String.format("%s/tbody/tr[%s]/td[%s]", elementHelper.getLocatorAsString(tableXpath), rowNumber, columnNumber);
        return By.xpath(xpathString);
    }

    /**
     * Function to get ColumnNumber for specified header in a table
     *
     * @param headerName column header name
     * @param tableXpath xpath of the table
     * @return - Column Number
     */
    protected int getColumnNumberInTable(String headerName, By tableXpath) {
        logger().info(String.format("Get the column number of the column - %s", headerName));
        var headerXpath = By.xpath(String.format("%s/thead/tr/th", elementHelper.getLocatorAsString(tableXpath)));
        List<WebElement> headerTextElements = elementHelper.getElements(headerXpath);
        for (int i = 0; i < headerTextElements.size(); i++) {
            String text = headerTextElements.get(i).getText();
            if (text.equals(headerName)) {
                return i + 1;
            }
        }
        return -1;
    }

    /**
     * Validate the Program year drop down
     *
     * @param programDurationInAdministration program year duration from administration
     * @return true on validation of the above
     */
    public boolean validateProgramYearDropDown(String programDurationInAdministration, String timeZone, String dateFormat, By programYearDropDownXpath) {
        logger().info("Validating the program year drop down");
        waitHelper.waitForPageToLoad(programYearDropDownXpath);
        var programYearValuesInDropDown = elementHelper.getComboBoxSelectOptions(programYearDropDownXpath);
        var programYearValuesString = new ArrayList<>();
        programYearValuesInDropDown.forEach(a -> programYearValuesString.add(a.getText()));
        var programDuration = Integer.valueOf(programDurationInAdministration);
        var rangeOfProgramYear = new ArrayList<String>();
        String currentDate = DateTimeUtil.getDateBasedOnTimeZone(TimeZoneList.valueOf(timeZone), dateFormat);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        LocalDate date = LocalDate.parse(currentDate, formatter);

        Integer currentYear = date.getYear();
        Integer minYear = currentYear - programDuration;
        Integer maxYear = currentYear + programDuration;
        rangeOfProgramYear.add(String.valueOf(minYear));
        rangeOfProgramYear.add(String.valueOf(maxYear));
        return programYearValuesString.containsAll(rangeOfProgramYear);
    }

    /**
     * Linking of document
     *
     * @param documentName Name of the document
     */
    public void linkingDocument(String documentName) {
        waitHelper.waitForPageToLoad(linkDocumentButton);
        elementHelper.doClick(linkDocumentButton);
        waitHelper.waitForPageToLoad();
        elementHelper.doClickUsingActions(By.xpath(String.format("//td[text()='%s']/..//input", documentName)));
    }

    /**
     * Link Document
     *
     * @param documentName Name of the document
     * @return True if uploaded successfully
     */
    public boolean linkDocument(String documentName) {
        linkingDocument(documentName);
        waitHelper.waitForPageToLoad();
        elementHelper.doClick(okButtonPicker);
        waitHelper.waitForPageToLoad(getRibbonIcon(RibbonIcons.Save));
        By document = By.xpath(String.format("//tr[contains(@class,'rgRow') and not(contains(@style,'display: none;'))]//td[contains(@class ,'attachmentCtrlRowClass') and contains(text(),'%s')]", documentName));
        waitHelper.waitForPageToLoad(RibbonIcons.Save);
        elementHelper.scrollToView(document);
        return getPage(Validations.class).verifyElementExists(document);
    }

    /**
     * Edit Document Title
     *
     * @param newDocumentTitle New Document Title
     * @param documentName     Document Name
     */
    public void editDocumentTitleInAttachments(String newDocumentTitle, String documentName) {
        waitHelper.waitForPageToLoad(editDocumentTitle);
        elementHelper.doSendKeysUsingAction(editDocumentTitle, newDocumentTitle);
        elementHelper.doClick(By.xpath(String.format("//td[text()='%s']", documentName)));
    }

    /**
     * Validate Document Title
     *
     * @param documentTitle Name of the document title
     * @return true if validated successfully
     */
    public boolean validateDocumentTitle(String documentTitle) {
        waitHelper.waitForPageToLoad();
        return getPage(Validations.class).verifyElementExists(By.xpath(String.format("//tr[@class='rgRow' and not(contains(@style,'display: none;'))]//td[contains(@class ,'attachmentCtrlRowClass') and (contains(text() ,'%s'))]", documentTitle)));
    }

    /**
     * Validate the cancel of link Document
     *
     * @param documentName name of the document
     * @return false if not there
     */
    public boolean validateCancelOfLinkDocument(String documentName) {
        linkingDocument(documentName);
        elementHelper.doClick(cancelButtonPicker);
        waitHelper.waitForPageToLoad(getRibbonIcon(RibbonIcons.Save));
        return getPage(Validations.class).verifyElementExists(By.xpath(String.format("//tr[@class='rgRow' and not(contains(@style,'display: none;'))]//td[contains(@class ,'attachmentCtrlRowClass') and text() ='%s']", documentName)));
    }

    /**
     * Date Picker
     *
     * @param day            Day to be selected
     * @param month          Month to be selected
     * @param year           Year to be selected
     * @param dateToBeChosen Date
     * @param calendarToWait Calendar to wait
     * @param monthDropDown  Month drop down
     * @param yearDropDown   Year Drop down
     * @param dateTemplate   Date template
     * @param dateLabel      Date Label
     */
    public void dateSelector(Integer day, String month, Integer year, By dateToBeChosen, By calendarToWait,
                             By monthDropDown, By yearDropDown, String dateTemplate, By dateLabel) {
        waitHelper.waitForPageToLoad(dateToBeChosen);
        elementHelper.doClick(dateToBeChosen);
        waitHelper.waitForElementPresent(calendarToWait);
        waitHelper.waitForElementToBePresentAndClickable(monthDropDown);
        elementHelper.selectComboBoxItemByText(monthDropDown, month);
        waitHelper.waitForElementToBePresentAndClickable(yearDropDown);
        elementHelper.selectComboBoxItemByText(yearDropDown, year.toString());
        By date = By.xpath(String.format(dateTemplate, day.toString()));
        waitHelper.waitForElementToBePresentAndClickable(date);
        elementHelper.doClick(date);
        waitHelper.waitForPageToLoad(dateLabel);
        elementHelper.doClick(dateLabel);
    }

    /**
     * Get generic requires label Xpath using Label's Xpath
     *
     * @param labelXpath Label's Xpath
     * @return Required warning xpath locator.
     */
    public By getRequiredWarningXpathIfExtendableFieldPresent(By labelXpath) {
        String prefixXpath = elementHelper.getLocatorAsString(labelXpath);
        return By.xpath(String.format("%s/../following-sibling::span[(contains(text(),'Required!')) and (not(contains(@style,'display:none;')))]", prefixXpath));
    }

    /**
     * Method to add a document
     *
     * @param fileName filename
     */
    public void uploadDocumentInForm(String fileName) {
        waitHelper.waitForPageToLoad();
        File file = FileHelper.createFile(fileName, true);
        elementHelper.doSendKeys(uploadDocument, file.getAbsolutePath());
    }

    /**
     * Method to validate document Upload toast message
     *
     * @return true if uploaded successfully
     */
//    public boolean validateUploadDocumentToast() {
//        String toast = getPage(ToastUtil.class).waitAndGetMessageForSingleToast();
//        boolean toastMsgValidation = (TestDataUtil.onlyAlphabeticalCharactersAndSpecialCharacters(toast).contains(Constants.UPLOAD_SUCCESSFUL));
//        getPage(ToastUtil.class).waitAndCloseForSingleToast();
//        waitHelper.waitForPageToLoad();
//        return toastMsgValidation;
//    }


    /**
     * click on backButton
     */
    public void clickBackButton() {
        clickRibbonIcon(RibbonIcons.Back);
        waitHelper.waitForPageToLoad();
    }

    /**
     * Get all cell in a row for dynamic grid.
     *
     * @param gridLocator Locator of grid.
     * @param rowNumber   Number of the row.
     * @return List of cell web elements.
     */
    public List<WebElement> getRowCellsDynamicGrid(By gridLocator, int rowNumber) {
        waitHelper.waitForPageToLoad();
        navigation.switchFrameToContent();
        waitHelper.waitForElementPresent(gridLocator);
        elementHelper.scrollToView(gridLocator);
        return elementHelper.getElements(By.xpath(
                "//div[@id = '" + elementHelper.getLocatorAsString(gridLocator)
                        + "']//div[contains(@class,'k-grid-content')]//tr["
                        + rowNumber + "]//td[not(contains(@style,'display:none'))]"));
    }

    /**
     * Get value of cell in dynamic grid.
     *
     * @param gridLocator Locator of the grid.
     * @param rowNumber   Row number of cell.
     * @param cellNumber  Cell number.
     * @return Cell value.
     */
    public String getCellValueDynamicGrid(By gridLocator, int rowNumber, int cellNumber) {
        return getRowCellsDynamicGrid(gridLocator, rowNumber)
                .get(cellNumber - 1).getText();
    }

    /**
     * Enter value in a cell in dynamic grid.
     *
     * @param gridLocator Locator of the grid.
     * @param rowNumber   Row number of cell.
     * @param cellNumber  Cell number.
     * @param value       Value to be entered in cell
     * @return True if no issue occurred, else false
     */
    public boolean enterCellValueDynamicGrid(By gridLocator, int rowNumber, int cellNumber, String value) {
        var cell = getRowCellsDynamicGrid(gridLocator, rowNumber).get(cellNumber - 1);
        String locator = String.format("//div[@id='%s']//th/input[@data-role='checkbox']/following-sibling::label", elementHelper.getLocatorAsString(gridLocator));
        waitHelper.waitForElementClickable(By.xpath(locator));
        var cellXpath = By.xpath("//div[@id = '" + elementHelper.getLocatorAsString(gridLocator)
                + "']//div[contains(@class,'k-grid-content')]//tr["
                + rowNumber + "]//td[not(contains(@style,'display:none'))][" + cellNumber + "]");
        logger().info(String.format("Row: %d Cell: %d Value: %s", rowNumber, cellNumber, value));
        for (int i = 0; i < 3; i++) {
            var enteredValue = elementHelper.doGetText(cellXpath);
            try {
                elementHelper.doClick(cell);
                waitHelper.waitForPageToLoad();
                var input = getRowCellsDynamicGrid(gridLocator, rowNumber).get(cellNumber - 1).findElement(By.tagName("input"));
                if (!Objects.equals(enteredValue, "")) {
                    elementHelper.doDoubleClick(input);
                }
                elementHelper.doSendKeysUsingAction(value);
                elementHelper.doSendKeys(Keys.TAB);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            enteredValue = elementHelper.doGetText(cellXpath);
            if (enteredValue.contains(":")) {
                enteredValue = enteredValue.replace(":", "");
            }
            if (enteredValue.equals(value)) {
                break;
            }
        }
        return true;
    }

    /**
     * Enter values in row for dynamic grid.
     *
     * @param gridLocator Locator of the grid.
     * @param rowIndex    Row Index of cell.
     * @param cellValues  List of values to be entered.
     * @return Enter cell values .
     */
    public boolean enterRowValuesDynamicGrid(By gridLocator, int rowIndex, HashMap<Integer, String> cellValues) {
        cellValues.forEach((key, value) -> enterCellValueDynamicGrid(gridLocator, rowIndex, key, value));
        return true;
    }

    /**
     * Get all cell values in a column.
     *
     * @param gridLocator  Locator of the grid.
     * @param columnNumber Column number.
     * @return List of cell values in a column
     */
    public List<String> getAllCellValuesInAColumnDynamicGrid(By gridLocator, int columnNumber) {
        List<String> cellValues = new ArrayList<>();
        var rows = getRowsDynamicGrid(gridLocator);
        for (int i = 1; i <= rows.size(); i++) {
            cellValues.add(getCellValueDynamicGrid(gridLocator, i, columnNumber));
        }
        return cellValues;
    }

    /**
     * Get row number from a row context of the given column.
     *
     * @param gridLocator  Locator of grid.
     * @param textToSearch text to search in the column
     * @param columnName   Name of column to search in
     * @return index of the matching row
     */
    public int getRowIndexDynamicGrid(By gridLocator, String textToSearch, String columnName) {
        waitHelper.waitForPageToLoad();
        navigation.switchFrameToContent();
        waitHelper.waitForElementPresent(gridLocator);
        elementHelper.scrollToView(gridLocator);
        int colNum = getColumnIndexFromGrid(gridLocator, columnName);
        List<String> values = getAllCellValuesInAColumnDynamicGrid(gridLocator, colNum + 1);
        return values.indexOf(textToSearch) + 1;
    }

    /**
     * Gets list of rows element in Doc grid with table xpath locator
     *
     * @param tableId Locator of the table containing "thead" and "tbody"
     * @return List of web element rows
     */
    public List<WebElement> getRowsFromDocGridForVersion(By tableId) {
        navigation.switchFrameToContent();
        waitHelper.waitForElementPresent(tableId);
        elementHelper.scrollToView(tableId);
        return elementHelper.getElements(By.xpath(
                elementHelper.getLocatorAsString(tableId) + "//tbody/tr[not(contains(@style,'display:none;')) and not(contains(@style,'display: none;')) and @id]"));
    }

    /**
     * Click on document name
     *
     * @param fileName Name of file to select
     */
    public void clickOnDocumentName(String fileName) {
        String docName = "//a[text()='%s']";
        elementHelper.doClick(By.xpath(String.format(docName, fileName)));
        waitHelper.waitForPageToLoad(RibbonIcons.Back);
    }

    /**
     * This method is used to get the page tab header name
     *
     * @return page tab header name
     */
    public String getPageTabHeaderName() {
        waitHelper.waitForPageToLoad();
        return elementHelper.doGetText(pageTabHeaderName);
    }
}


