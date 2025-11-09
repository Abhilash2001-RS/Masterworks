package com.aurigo.masterworks.testframework.webUI.constants.enums;

public enum ListPageFilterOptions {

    NoFilter("No Filter"),
    Contains("Contains"),
    DoesNotContain("Does Not Contain"),
    StartsWith("Starts With"),
    EndsWith("Ends With"),
    EqualTo("Equal To"),
    NotEqualTo("Not Equal To"),
    GreaterThan("Greater Than"),
    LessThan("Less Than"),
    GreaterThanOrEqualTo("Greater Than Or Equal To"),
    LessThanOrEqualTo("Less Than Or Equal To"),
    Between("Between"),
    NotBetween("Not Between"),
    IsNull("Is Null"),
    NotIsNull("Not Is Null"),
    IsEmpty("Is Empty"),
    NotIsEmpty("Not Is Empty"),
    ClearAllFilter("Clear All Filter");

    // declaring private variable for getting values
    private String value;

    private ListPageFilterOptions(String value) {
        this.value = value;
    }

    // getter method
    public String getValue() {
        return this.value;
    }
}
