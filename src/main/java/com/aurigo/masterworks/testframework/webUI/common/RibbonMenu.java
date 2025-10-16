package com.aurigo.masterworks.testframework.webUI.common;

import com.aurigo.masterworks.testframework.utilities.models.Locator;
import com.aurigo.masterworks.testframework.webUI.BasePage;
import com.aurigo.masterworks.testframework.webUI.constants.enums.RibbonIcons;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;

public class RibbonMenu extends BasePage {

    private final WebDriver driver;
    protected Navigation navigation;
    private static HashMap<String, Locator> locatorHashMap;

    public RibbonMenu(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
        navigation = GetInstance(Navigation.class, driver);

    }

    public By getRibbonIcon(RibbonIcons icon) {
        return locatorHashMap.get(icon.getValue()).identifier;
    }

    public void clickRibbonIcon(RibbonIcons icon) {
        navigation.switchFrameToContent();
        waitHelper.waitForPageToLoad();
        clickIcon(icon.getValue());
    }
}
