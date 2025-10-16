package com.aurigo.masterworks.testframework.webUI.pages;

import com.aurigo.masterworks.testframework.webUI.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BasePage {

    WebDriver driver;

    @FindBy(xpath = "//span[@class='menuTabImage mw-icon mw-project']")
    WebElement projectsBtn;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickProjects() {
        projectsBtn.click();
    }

}
