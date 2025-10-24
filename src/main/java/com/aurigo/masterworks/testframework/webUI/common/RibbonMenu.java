package com.aurigo.masterworks.testframework.webUI.common;

import com.aurigo.masterworks.testframework.utilities.LocatorUtil;
import com.aurigo.masterworks.testframework.utilities.models.Locator;
import com.aurigo.masterworks.testframework.webUI.BasePage;
import com.aurigo.masterworks.testframework.webUI.constants.enums.RibbonIcons;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;

public class RibbonMenu extends BasePage {

    private final WebDriver driver;
    private static HashMap<String, Locator> locatorHashMap;
    protected Navigation navigation;

    protected String optionsInRibbonMenu = "//span[text()='%s']";
    protected String textValidationInRibbonMenu = "//a[@aria-label='%s']";
    protected String newOptions = "//li//a[@aria-label='%s']";

    public RibbonMenu(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
        navigation = GetInstance(Navigation.class, driver);
        locatorHashMap = LocatorUtil.getRibbonLocators();

    }

    public By  getRibbonIcon(RibbonIcons icon){
        return locatorHashMap.get(icon.getValue()).identifier;
    }

    public void clickRibbonIcon()
    {
        navigation.
    }
}
