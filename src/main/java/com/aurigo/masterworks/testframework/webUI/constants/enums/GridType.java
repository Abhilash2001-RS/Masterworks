package com.aurigo.masterworks.testframework.webUI.constants.enums;

import java.util.ArrayList;
import java.util.List;

public enum GridType {

    RadGrid("RadGrid"),
    DocGrid("DocGrid"),
    DynamicGrid("DynamicGrid");

    // declaring private variable for getting values
    private String value;

    GridType(String value) {
        this.value = value;
    }

    /**
     * method to return the list of the grid types
     *
     * @return list of the column names
     */
    public static List<String> getListOfColumnNames() {
        var listToReturn = new ArrayList();
        for (var item : GridType.values()) {
            listToReturn.add(item.getValue());
        }
        return listToReturn;
    }

    public String getValue() {
        return this.value;
    }
}
