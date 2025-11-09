package com.aurigo.masterworks.testframework.webUI.generic;

import com.aurigo.masterworks.testframework.utilities.LocatorUtil;
import com.aurigo.masterworks.testframework.webUI.common.RibbonMenu;
import com.aurigo.masterworks.testframework.webUI.constants.enums.Form;
import com.aurigo.masterworks.testframework.webUI.constants.enums.GridType;
import com.aurigo.masterworks.testframework.webUI.constants.enums.ListPageFilterOptions;
import com.aurigo.masterworks.testframework.webUI.constants.enums.RibbonIcons;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class GenericFormProposed extends RibbonMenu {

    private By filterOptionMenu;
    private By linkedRecordFrame;
    private By searchWithinComboBox;

    /**
     * Grid Templates
     */
    public static final String RAD_GRID_HEADER_TEMPLATE = "//div[contains(@id,'%s')]//th[not(contains(@class,'rgCheck'))  and (not(contains(@style,'display:none;'))) and @class='rgHeader']";
    public static final String RAD_GRID_HEADER_TEMPLATE_1 = "//div[contains(@id,'%s')]//th[(not(contains(@style,'display:none;')))]";
    public static final String DOC_GRID_HEADER_TEMPLATE = "//div[contains(@id,'%s')]//th[(not(contains(@style,'display:none')))]//a";
    public static final String DOC_GRID_ATTACHMENT_HEADER_TEMPLATE = "//table[@id='%s']//th//nobr";
    public static final String DYNAMIC_GRID_HEADER_TEMPLATE = "//div[contains(@id,'%s')]//th[@data-title and not(contains(@style,'none'))]//a[@class='k-link']//span";


    public static final String RAD_GRID_DATA_TEMPLATE = "//div[contains(@id,'%s')]//td[((not(contains(@style,'none;'))) and not(@class))]";
    public static final String RAD_GRID_DATA_TEMPLATE_2 = "//div[contains(@id,'%s')]//td[((not(contains(@style,'none;')))]";
    public static final String RAD_GRID_DATA_TEMPLATE_1 = "//div[contains(@id,'%s')]//div";
    public static final String RAD_GRID_DATA_CHECK_TEMPLATE = "//div[contains(@id,'%s')]//td[not(contains(@style,'none;'))][%d]";
    public static final String RAD_GRID_DATA_CHECKBOX_TEMPLATE_CHECK = "//div[contains(@id,'%s')]//th[contains(@class,'rgCheck')]";
    public static final String RAD_GRID_WITH_CLASS_DATA_TEMPLATE = "//div[contains(@id,'%s')]//tbody//td[(not(contains(@style,'none;'))) and " +
            "((@class='gridCellUnique') or (@class='gridInlineEditing') or not(@class) or (@class='rgExpandCol') or (@class='attachmentCtrlRowClass'))][%d]";
    public static final String DOC_GRID_DATA_TEMPLATE = "//div[contains(@id,'%s')]//tr[@role='row']//td[not(@class='multiselect-column')][%d]";
    public static final String DOC_GRID_ATTACHMENT_DATA_TEMPLATE = "//table[@id='%s']//td[%d]//nobr";
    public static final String DYNAMIC_GRID_DATA_TEMPLATE = "//div[contains(@id,'%s')]//tr[@role='row' and @data-uid]";

    public static final String RAD_GRID_ROW_DATA_TEMPLATE = "//td[text()='%s']/..//td[not(contains(@style,'none'))]";
    public static final String RAD_GRID_ROW_DATA = "//td[text()='%s']/..";
    public static final String DYNAMIC_GRID_ROW_DATA_TEMPLATE = "//td[@title='%s']/..//td[(not(contains(@style,'display:none'))) and @class]";
    public static final String DOC_GRID_ROW_DATA_TEMPLATE = "//td[text()='%s']/..//td[(not(contains(@style,'display:none')))]";
    public static final String DOC_GRID_ROW_ATTACHMENT_DATA_TEMPLATE = "//nobr[text()='%s']/../..";

    public static final String DYNAMIC_ROW_SELECTION = "//td[text()='%s']/..//input";

    public static final String RAD_GRID_FILTER_TEXT_BOX = "//div[@id='%s']//input[@type='text' and (contains(@name,'%s'))]";
    public static final String DOC_GRID_FILTER_TEXT_BOX = "//input[contains(@id,'FilterTextBox') and @aria-label='%s']";


    public static final String RAD_GRID_FILTER_BUTTON = "//div[@id='%s']//input[@type='text' and (contains(@name,'%s'))]/..//button";
    public static final String DOC_GRID_FILTER_BUTTON = "//input[contains(@id,'FilterTextBox') and @aria-label='%s']/..//input[@title='Filter']";

    public static final String DYNAMIC_GRID_ENTER_DATA = "//div[@id='%s']//div[contains(@class,'k-grid-content')]//tr//td[not(contains(@style,'display:none')) and @class]";

    public static final String GENERAL_GRID_DATA_TEMPLATE = "//div[contains(@id,'%s')]/descendant::tr[@class='rgRow' and not(contains(@style,'display:none') or contains(@style,'display: none'))]/descendant::td[not(contains(@style,'display:none') or contains(@style,'display: none')) and not(contains(@class,'multiselect-column'))]";

    public static final String standardFiscalYear = "Fiscal Year 1";
    public static final String nonSeptFiscalYear = "Not September";

    public GenericFormProposed(WebDriver driver) {
        super(driver);
        var locators = LocatorUtil.getLocators("GenericForm.json");

        filterOptionMenu = locators.get("filterOptionMenu");
        linkedRecordFrame = locators.get("linkedRecordFrame");
        searchWithinComboBox = locators.get("searchWithinComboBox");
    }


    /**
     * Get Grid Web Elements
     *
     * @param radGridLocator Rad Grid Locator
     * @return List of the Web Elements
     */
    protected List<WebElement> getGridWebElements(By radGridLocator) {
        waitHelper.waitForPageToLoad();
        return elementHelper.getElements(radGridLocator);
    }

    /**
     * Get Data from the list Web elements
     *
     * @param locator locator for the set of data
     * @param tag     Type of tag (text or Attribute)
     * @param maxRows maxRows
     * @return The data from the webelements
     */
    protected List<String> getDataFromListWebElements(By locator, String tag, Integer... maxRows) {
        waitHelper.waitForPageToLoad();
        waitHelper.waitForElementPresent(locator);
        var data = elementHelper.getElements(locator);
        List<String> stringData = new ArrayList<>();
        Integer maxLength = maxRows.length > 0 ? maxRows[0] : 99999999;
        Integer dataLength = Math.min(maxLength, data.size());
        for (int i = 0; i < dataLength; i++) {
            String cellData;
            if (tag.equals("text")) {
                cellData = elementHelper.doGetText(data.get(i));
            } else {
                cellData = data.get(i).getAttribute(tag);
            }
            logger().info(String.format("Data %d %s \n", i + 1, cellData));
            if (!cellData.isEmpty() && !data.contains(cellData)) {
                stringData.add(cellData);
            }
        }
        return stringData;
    }

    /**
     * Header Data Mapping
     *
     * @param dataLocator   Grid Data Locator
     * @param headerLocator Header Locator
     * @param gridType      Grid Type
     * @return Map of data to header
     */
    private Map<String, String> headerDataMapping(By dataLocator, By headerLocator, GridType gridType) {
        waitHelper.waitForPageToLoad();
        var header = getGridHeaders(gridType, headerLocator);
        var data = getDataFromListWebElements(dataLocator, "text");
        Map<String, String> headerData = new HashMap<>();
        for (int i = 0; i < Math.min(header.size(), data.size()); i++) {
            if (data.get(i).contains("&nbsp")) {
                headerData.put(header.get(i), " ");
            } else {
                headerData.put(header.get(i), data.get(i));
            }
        }
        return headerData;
    }

    /**
     * Get Column Index
     *
     * @param columnName Name of the column
     * @param gridLocator Grid locator
     * @param gridType Grid Type
     * @return the Column Index
     */
    public Integer getColumnIndex(String columnName, By gridLocator, GridType gridType) {
        waitHelper.waitForPageToLoad();
        navigation.switchFrameToContent();
        List<WebElement> data;
        Integer columnNumber = 0;
        switch (gridType) {
            case RadGrid:
                By radGridHeader = By.xpath(String.format(RAD_GRID_HEADER_TEMPLATE, elementHelper.getLocatorAsString(gridLocator)));
                waitHelper.waitForPageToLoad();
                if (!elementHelper.isElementDisplayed(radGridHeader)) {
                    radGridHeader = By.xpath(String.format(RAD_GRID_HEADER_TEMPLATE + "//a", elementHelper.getLocatorAsString(gridLocator)));
                }
                data = getGridWebElements(radGridHeader);
                break;
            case DocGrid:
                By docGridHeader = By.xpath(String.format(DOC_GRID_HEADER_TEMPLATE, elementHelper.getLocatorAsString(gridLocator)));
                waitHelper.waitForPageToLoad();
                if (!elementHelper.isElementDisplayed(docGridHeader)) {
                    docGridHeader = By.xpath(String.format(DOC_GRID_ATTACHMENT_HEADER_TEMPLATE, elementHelper.getLocatorAsString(gridLocator)));
                }
                data = getGridWebElements(docGridHeader);
                break;
            case DynamicGrid:
                By dynamicGridHeader = By.xpath(String.format(DYNAMIC_GRID_HEADER_TEMPLATE, elementHelper.getLocatorAsString(gridLocator)));
                data = getGridWebElements(dynamicGridHeader);
                break;

            default:
                data = new ArrayList<>();
        }

        for (int i = 0; i < data.size(); i++) {
            waitHelper.waitForPageToLoad();
            if (elementHelper.doGetText(data.get(i)).equals(columnName)) {
                columnNumber = i + 1;
                break;
            }
        }

        return columnNumber;

    }

    /**
     * Get Grid Headers
     *
     * @param gridType      Type of the grid
     * @param gridIdLocator Grid ID locator
     * @return List of Grid Headers
     */
    public List<String> getGridHeaders(GridType gridType, By gridIdLocator) {
        waitHelper.waitForPageToLoad();
        switch (gridType) {
            case RadGrid:
                By radGridHeader = By.xpath(String.format(RAD_GRID_HEADER_TEMPLATE, elementHelper.getLocatorAsString(gridIdLocator)));
                if (!elementHelper.isElementDisplayed(radGridHeader)) {
                    radGridHeader = By.xpath(String.format(RAD_GRID_HEADER_TEMPLATE_1, elementHelper.getLocatorAsString(gridIdLocator)));
                }
                return getDataFromListWebElements(radGridHeader, "text");

            case DocGrid:
                By docGridHeader = By.xpath(String.format(DOC_GRID_HEADER_TEMPLATE, elementHelper.getLocatorAsString(gridIdLocator)));
                if (!elementHelper.isElementDisplayed(docGridHeader)) {
                    docGridHeader = By.xpath(String.format(DOC_GRID_HEADER_TEMPLATE, elementHelper.getLocatorAsString(gridIdLocator)));
                }
                return getDataFromListWebElements(docGridHeader, "text");

            case DynamicGrid:
                By dynamicGridHeader = By.xpath(String.format(DYNAMIC_GRID_HEADER_TEMPLATE, elementHelper.getLocatorAsString(gridIdLocator)));
                return getDataFromListWebElements(dynamicGridHeader, "text");

            default:
                return new ArrayList<>();
        }
    }

    /**
     * Get Grid Data
     *
     * @param gridType          Grid Type
     * @param columnName        Column Name
     * @param gridDataLocator   Grid Data Locator
     * @param gridHeaderLocator Grid Header Locator
     * @param maxRows           Maximum Number of rows
     * @return The column Grid Data
     */
    public List<String> getGridData(GridType gridType, String columnName, By gridDataLocator, By gridHeaderLocator, Integer... maxRows) {
        waitHelper.waitForPageToLoad(gridHeaderLocator);
        Integer columnIndex = getColumnIndex(columnName, gridHeaderLocator, gridType);
        logger().info(String.format("Column Name: %s Column Number: %d", columnName, columnIndex));
        switch (gridType) {
            case RadGrid:
                By radGridDataLocator;
                By radGridDataClassLocator = By.xpath(String.format(RAD_GRID_WITH_CLASS_DATA_TEMPLATE, elementHelper.getLocatorAsString(gridDataLocator), columnIndex));
                radGridDataLocator = By.xpath(String.format(RAD_GRID_DATA_TEMPLATE + "[%d]", elementHelper.getLocatorAsString(gridDataLocator), columnIndex));
                boolean gridCase = false;
                By checkBoxCheck = By.xpath(String.format(RAD_GRID_DATA_CHECKBOX_TEMPLATE_CHECK, elementHelper.getLocatorAsString(gridHeaderLocator)));
                boolean checkBoxLatest = elementHelper.isElementDisplayed(checkBoxCheck);
                if (!elementHelper.isElementDisplayed(radGridDataLocator)) {
                    radGridDataLocator = By.xpath(String.format(RAD_GRID_DATA_TEMPLATE_1, elementHelper.getLocatorAsString(gridDataLocator)));
                    gridCase = true;
                }
                if (elementHelper.isElementDisplayed(checkBoxCheck) && !gridCase && checkBoxLatest) {
                    radGridDataLocator = By.xpath(String.format(RAD_GRID_DATA_CHECK_TEMPLATE, elementHelper.getLocatorAsString(gridDataLocator), columnIndex + 1));
                    gridCase = true;
                }
                if (elementHelper.isElementDisplayed(radGridDataClassLocator) && (!gridCase)) {
                    radGridDataLocator = radGridDataClassLocator;
                }
                logger().info("radGridDataLocator: " + elementHelper.getLocatorAsString(radGridDataLocator));
                return getDataFromListWebElements(radGridDataLocator, "text", maxRows);

            case DocGrid:
                By docGridDataLocator = By.xpath(String.format(DOC_GRID_DATA_TEMPLATE, elementHelper.getLocatorAsString(gridDataLocator), columnIndex));
                if (!elementHelper.isElementDisplayed(docGridDataLocator)) {
                    docGridDataLocator = By.xpath(String.format(DOC_GRID_ATTACHMENT_DATA_TEMPLATE, elementHelper.getLocatorAsString(gridDataLocator), columnIndex));
                }
                return getDataFromListWebElements(docGridDataLocator, "text", maxRows);

            case DynamicGrid:
                By dynamicGridDataLocator = By.xpath(String.format(DYNAMIC_GRID_DATA_TEMPLATE + "//input/../following-sibling::td[not(contains(@style,'none'))][%d]", elementHelper.getLocatorAsString(gridDataLocator), columnIndex));
                if (!elementHelper.isElementDisplayed(dynamicGridDataLocator)) {
                    dynamicGridDataLocator = By.xpath(String.format(DYNAMIC_GRID_DATA_TEMPLATE + "//td[not(contains(@style,'none'))][%d]", elementHelper.getLocatorAsString(gridDataLocator), columnIndex));
                }
                return getDataFromListWebElements(dynamicGridDataLocator, "text", maxRows);

            default:
                return new ArrayList<>();
        }
    }

    /**
     * Get Grid Data
     *
     * @param gridType          Grid Type
     * @param columnName        Column Name
     * @param gridDataLocator   Grid Data Locator
     * @param gridHeaderLocator Grid Header Locator
     * @param rowIdentifier Row identifier
     * @return The column Grid Data
     */
    public Map<String, String> getRowData(GridType gridType, String columnName, By gridDataLocator, By gridHeaderLocator, String rowIdentifier) {
        waitHelper.waitForPageToLoad();
        switch (gridType) {
            case RadGrid:
                By radGridDataLocator = By.xpath(String.format(RAD_GRID_ROW_DATA_TEMPLATE, rowIdentifier));
                return headerDataMapping(radGridDataLocator, gridHeaderLocator, GridType.RadGrid);

            case DocGrid:
                By docGridDataLocator = By.xpath(String.format(DOC_GRID_ROW_DATA_TEMPLATE, rowIdentifier));
                if (!elementHelper.isElementDisplayed(docGridDataLocator)) {
                    docGridDataLocator = By.xpath(String.format(DOC_GRID_ROW_ATTACHMENT_DATA_TEMPLATE + "//td[not(contains(@style,'display:none'))]//nobr", rowIdentifier));
                }
                return headerDataMapping(docGridDataLocator, gridHeaderLocator, GridType.DocGrid);

            case DynamicGrid:
                By dynamicGridDataLocator = By.xpath(String.format(DYNAMIC_GRID_ROW_DATA_TEMPLATE, rowIdentifier));
                return headerDataMapping(dynamicGridDataLocator, gridHeaderLocator, GridType.DynamicGrid);

            default:
                return new HashMap<>();
        }
    }

    /**
     * Select Row
     *
     * @param gridType      Grid Type
     * @param rowIdentifier Row Identifier
     * @return Row to
     */
    public WebElement selectRow(GridType gridType, String rowIdentifier) {
        waitHelper.waitForPageToLoad();
        navigation.switchFrameToContent();
        switch (gridType) {
            case RadGrid:
                return elementHelper.getElement(By.xpath(String.format(RAD_GRID_ROW_DATA, rowIdentifier)));

            case DocGrid:
                By docGridDataLocator = By.xpath(String.format(DOC_GRID_ROW_DATA_TEMPLATE, rowIdentifier));
                if (!elementHelper.isElementDisplayed(docGridDataLocator)) {
                    return elementHelper.getElement(By.xpath(String.format(DOC_GRID_ROW_ATTACHMENT_DATA_TEMPLATE, rowIdentifier)));
                } else {
                    return elementHelper.getElement(By.xpath(String.format(DOC_GRID_ROW_DATA_TEMPLATE, rowIdentifier)));
                }
            case DynamicGrid:
                return elementHelper.getElement(By.xpath(String.format(DYNAMIC_ROW_SELECTION, rowIdentifier)));

            default:
                return null;
        }
    }

    /**
     * Select Row
     *
     * @param gridType    Grid Type
     * @param gridData    Grid data to add
     * @param gridLocator Grid Locator
     */
    public void enterData(GridType gridType, By gridLocator, List<String> gridData) {
        waitHelper.waitForPageToLoad();
        elementHelper.doClick(gridLocator);
        switch (gridType) {
            case RadGrid:
                //TODO: WIP

            case DocGrid:
                //TODO: WIP

            case DynamicGrid:
                var element = getPage(GenericFormProposed.class).
                        getGridWebElements(By.xpath(String.format(DYNAMIC_GRID_ENTER_DATA, elementHelper.getLocatorAsString(gridLocator))));
                for (int i = 0; i < Math.min(element.size(), gridData.size()); i++) {
                    elementHelper.doDoubleClick(element.get(i));
                    By inputLocator = By.xpath(String.format(DYNAMIC_GRID_ENTER_DATA + "//input", elementHelper.getLocatorAsString(gridLocator)));
                    if (!elementHelper.isElementDisplayed(inputLocator)) {
                        inputLocator = By.xpath(String.format(DYNAMIC_GRID_ENTER_DATA + "//span//input[@type='text' and (not(contains(@style,'none')))]", elementHelper.getLocatorAsString(gridLocator)));
                    }
                    elementHelper.getElement(inputLocator).clear();
                    elementHelper.doDoubleClick(element.get(i));
                    waitHelper.waitForPageToLoad(inputLocator);
                    elementHelper.getElement(inputLocator).sendKeys(gridData.get(i));
                    waitHelper.waitForPageToLoad();
                }
            default:
        }
    }

    /**
     * Todo: implement for generic is required
     * Select Row
     *
     * @param gridType Grid Type
     * @param gridLocator Grid locator
     * @param gridHeader Grid header
     * @param columnName Column name in grid
     * @return Row element
     */
    public WebElement getFirstRowCell(GridType gridType, By gridLocator, By gridHeader, String columnName) {
        waitHelper.waitForPageToLoad();
        elementHelper.doClick(gridLocator);
        switch (gridType) {
            case RadGrid:
                Integer radColumnIndex = getPage(GenericFormProposed.class).getColumnIndex(columnName, gridHeader, GridType.RadGrid);
                var allRadCellElement = getPage(GenericFormProposed.class).getGridWebElements(By.xpath(String.format(GENERAL_GRID_DATA_TEMPLATE, elementHelper.getLocatorAsString(gridLocator))));
                return allRadCellElement.get(radColumnIndex - 1);
            case DocGrid:
                Integer docColumnIndex = getPage(GenericFormProposed.class).getColumnIndex(columnName, gridHeader, GridType.DocGrid);
                var allDocCellElement = getPage(GenericFormProposed.class).getGridWebElements(By.xpath(String.format(GENERAL_GRID_DATA_TEMPLATE, elementHelper.getLocatorAsString(gridLocator))));
                return allDocCellElement.get(docColumnIndex - 1);
            case DynamicGrid:
                Integer columnIndex = getPage(GenericFormProposed.class).getColumnIndex(columnName, gridHeader, GridType.DynamicGrid);
                var element = getPage(GenericFormProposed.class).getGridWebElements(By.xpath(String.format(DYNAMIC_GRID_ENTER_DATA, elementHelper.getLocatorAsString(gridLocator))));
                var data = element.get(columnIndex - 1);
                elementHelper.doDoubleClick(data);
                By inputLocator = By.xpath(String.format(DYNAMIC_GRID_ENTER_DATA + "//input", elementHelper.getLocatorAsString(gridLocator)));
                if (!elementHelper.isElementDisplayed(inputLocator)) {
                    inputLocator = By.xpath(String.format(DYNAMIC_GRID_ENTER_DATA + "//span//input[@type='text' and (not(contains(@style,'none')))]", elementHelper.getLocatorAsString(gridLocator)));
                }
                return elementHelper.getElement(inputLocator);

            default:
        }
        return null;
    }

    /**
     * Filter Grid
     *
     * @param gridType      Type of Grid
     * @param gridLocator   Grid Locator
     * @param columnName    Column Name to Filter
     * @param valueToFilter Value to be filtered
     * @param filterOptions Filter option to be applied
     */
    public void filterGrid(GridType gridType, By gridLocator, String columnName, String valueToFilter, ListPageFilterOptions filterOptions) {
        By filterTextBox;
        By filterButton;
        waitHelper.waitForPageToLoad();
        switch (gridType) {
            case RadGrid:
                filterTextBox = By.xpath(String.format(RAD_GRID_FILTER_TEXT_BOX, elementHelper.getLocatorAsString(gridLocator), columnName));
                waitHelper.waitForPageToLoad(gridLocator);
                elementHelper.scrollToView(gridLocator);
                if (!elementHelper.isElementDisplayed(filterTextBox)) {
                    filterTextBox = By.xpath(String.format(RAD_GRID_FILTER_TEXT_BOX, elementHelper.getLocatorAsString(gridLocator), columnName.replaceAll(" ", "")));
                }
                filterButton = By.xpath(String.format(RAD_GRID_FILTER_BUTTON, elementHelper.getLocatorAsString(gridLocator), columnName));
                if (!elementHelper.isElementDisplayed(filterButton)) {
                    filterButton = By.xpath(String.format(RAD_GRID_FILTER_BUTTON, elementHelper.getLocatorAsString(gridLocator), columnName.replaceAll(" ", "")));
                }
                waitHelper.waitForPageToLoad();
                elementHelper.scrollToView(filterTextBox);
                waitHelper.waitForPageToLoad();
                elementHelper.doClick(filterTextBox);
                elementHelper.getElement(filterTextBox).clear();
                elementHelper.doSendKeysUsingAction(filterTextBox, valueToFilter);
                elementHelper.scrollToView(filterButton);
                waitHelper.waitForPageToLoad(filterButton);
                elementHelper.doClickUsingActions(filterButton);
                waitHelper.waitForElementPresent(filterOptionMenu);
                By filterOption = By.xpath(String.format("//ul[not(@style='display: none')]//span[.='%s']", filterOptions.getValue()));
                if (!elementHelper.isElementDisplayed(filterOption)) {
                    var option = By.xpath(String.format("//span[contains(text(),'%s')]", filterOptions.getValue()));
                    waitHelper.waitForElementToBePresentAndClickable(option);
                    elementHelper.doClickUsingActions(option);
                } else {
                    waitHelper.waitForElementToBePresentAndClickable(filterOption);
                    elementHelper.doClickUsingActions(filterOption);
                    waitHelper.waitForPageToLoad();
                    waitHelper.waitForLoadingSpinnerDisappear();
                }
                waitHelper.waitForPageToLoad();
                break;

            case DocGrid:
                filterTextBox = By.xpath(String.format(DOC_GRID_FILTER_TEXT_BOX, columnName));
                elementHelper.scrollToView(gridLocator);
                filterButton = By.xpath(String.format(DOC_GRID_FILTER_BUTTON, columnName));
                elementHelper.scrollToView(filterTextBox);
                waitHelper.waitForPageToLoad();
                elementHelper.doSendKeysUsingAction(filterTextBox, valueToFilter);
                elementHelper.doClickUsingActions(filterButton);
                waitHelper.waitForElementPresent(filterOptionMenu);
                By elementToClick = By.xpath(String.format("//span[contains(text(),'%s')]", filterOptions.getValue()));
                waitHelper.waitForPageToLoad(elementToClick);
                elementHelper.doClick(elementToClick);
                waitHelper.waitForPageToLoad();
                break;


            case DynamicGrid://TODO : Work In Progress
            default:
        }
    }

    /**
     * Capitalize String
     *
     * @param data String value
     * @return The New modified string
     */
    public String capitalizeString(String data) {
        String dataToBeConverted = data.toLowerCase(Locale.ROOT);
        return dataToBeConverted.toUpperCase().charAt(0) + dataToBeConverted.substring(1, dataToBeConverted.length());
    }

    /**
     * Select Fiscal year based on the current date
     * For 23rd sept to 30th sept, select
     *
     * @param fiscalYearId Id of the locator
     */
    public void selectFiscalYear(By fiscalYearId) {
        LocalDate currentDate = LocalDate.now();
        var currentYear = currentDate.getYear();
        var start = LocalDate.of(currentYear, Month.SEPTEMBER, 22);
        var end = LocalDate.of(currentYear, Month.OCTOBER, 1);
        waitHelper.waitForPageToLoad(fiscalYearId);
        if (currentDate.isAfter(start) && currentDate.isBefore(end)) {
            elementHelper.selectComboBoxItemByText(fiscalYearId, nonSeptFiscalYear);
        } else {
            elementHelper.selectComboBoxItemByText(fiscalYearId, standardFiscalYear);
        }
        waitHelper.waitForPageToLoad(getRibbonIcon(RibbonIcons.Save));
    }

    /**
     * Method to select the module in Select Form combo box
     *
     * @param moduleName - Module name to be selected
     */
    public void selectFormForLinkedRecords(Form moduleName) {
        logger().info("Search within: " + moduleName.getValue());
        navigation.switchToSpecificFrame(linkedRecordFrame);
        waitHelper.waitForPageToLoad(searchWithinComboBox);
        elementHelper.doSendKeysUsingAction(searchWithinComboBox, moduleName.getValue());
        waitHelper.waitForPageToLoad();
        elementHelper.doSendKeysUsingAction(searchWithinComboBox, Keys.chord(Keys.ENTER));
        waitHelper.waitForPageToLoad();
    }


}
