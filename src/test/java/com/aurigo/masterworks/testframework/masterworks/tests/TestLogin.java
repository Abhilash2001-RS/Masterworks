package com.aurigo.masterworks.testframework.masterworks.tests;

import com.aurigo.masterworks.testframework.webUI.pages.HomePage;
import com.aurigo.masterworks.testframework.webUI.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestLogin extends BaseTest{

    private WebDriver driver;

    @Test
    public void budgetEstimateTest()
    {
        getPage(LoginPage.class).login();
        Assert.assertEquals(getDriver().getTitle(), "Home - MasterWorks");
        getPage(HomePage.class).clickProjects();
    }

}
