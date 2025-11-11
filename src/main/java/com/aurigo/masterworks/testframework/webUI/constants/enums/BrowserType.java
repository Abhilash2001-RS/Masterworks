package com.aurigo.masterworks.testframework.webUI.constants.enums;

public enum BrowserType {

    Chrome("Chrome"),
    Edge("Edge"),
    IE("Internet Explorer 11"),
    Windows("Windows"),
    Firefox("Firefox");

    private String value;

    BrowserType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
