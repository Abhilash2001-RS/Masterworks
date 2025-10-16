package com.aurigo.masterworks.testframework.webUI.constants.enums;

public enum RibbonIcons {


    HOME("home"),
    DASHBOARD("dashboard"),
    PROJECTS("projects"),
    TASKS("tasks"),
    REPORTS("reports"),
    ADMINISTRATION("administration");

    private final String iconName;

    RibbonIcons(String iconName) {
        this.iconName = iconName;
    }

    public String getIconName() {
        return iconName;
    }




}
