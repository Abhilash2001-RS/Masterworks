package com.aurigo.masterworks.testframework.webUI.generic;

import com.aurigo.masterworks.testframework.utilities.LocatorUtil;
import org.openqa.selenium.WebDriver;

public class GenericLibrary {

    public GenericLibrary(WebDriver driver)
    {
        var locators = LocatorUtil.getLocators("GenericLibrary.json");
    }
}
