package com.aurigo.masterworks.testframework.webUI.pages;

import com.aurigo.masterworks.testframework.utilities.models.User;
import com.aurigo.masterworks.testframework.utilities.models.UserDataReader;
import com.aurigo.masterworks.testframework.webUI.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    WebDriver driver;

    @FindBy(name = "txtUserID")
    WebElement username;

    @FindBy(name = "password")
    WebElement password;

    @FindBy(name = "btnLogin")
    WebElement loginBtn;

    public LoginPage(WebDriver driver) {
        super(driver);

        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void login() {
        User userName = UserDataReader.getUser();
        username.sendKeys(userName.getUsername());
        password.sendKeys(userName.getPassword());
        loginBtn.click();
    }
}

