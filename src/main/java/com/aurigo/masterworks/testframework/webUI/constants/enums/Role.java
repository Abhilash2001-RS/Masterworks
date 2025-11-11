package com.aurigo.masterworks.testframework.webUI.constants.enums;

public enum Role {

    Administrator("Administrator"),
    User("User");

    private String value;

    Role(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
