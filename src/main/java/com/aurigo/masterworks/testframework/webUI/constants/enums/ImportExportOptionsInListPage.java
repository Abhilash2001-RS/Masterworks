package com.aurigo.masterworks.testframework.webUI.constants.enums;

public enum ImportExportOptionsInListPage {

    ExcelImport("Excel Import"),
    ExcelExportXLS("Excel Export (xls)"),
    ExcelExportXLSX("Excel Export (xlsx)"),
    ExcelTemplateXLS("Excel Template (xls)"),
    ExcelTemplateXLSX("Excel Template (xlsx)"),
    ExcelTemplateWithDataXLS("Excel Template With Data (xls)"),
    ExcelTemplateWithDataXLSX("Excel Template With Data (xlsx)"),
    DocumentImport("Document Import");

    // declaring private variable for getting values
    private String value;

    ImportExportOptionsInListPage(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
