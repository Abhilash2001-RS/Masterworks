package com.aurigo.masterworks.testframework.utilities.helper;

import org.openqa.selenium.WebDriver;

public class ElementHelper extends WaitHelper{

    private WebDriver driver;
    public ElementHelper(WebDriver driver)
    {
        super(driver);

    }
}
