package com.aurigo.masterworks.testframework.webUI;

import com.aurigo.masterworks.testframework.BaseFramework;
import com.aurigo.masterworks.testframework.utilities.helper.ElementHelper;
import com.aurigo.masterworks.testframework.utilities.helper.WaitHelper;
import org.openqa.selenium.WebDriver;

public class BasePage extends BaseFramework {

    protected WaitHelper waitHelper;
    protected ElementHelper elementHelper;
    private WebDriver driver;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        elementHelper = new ElementHelper(driver);
        waitHelper = new WaitHelper(driver);
    }

}
