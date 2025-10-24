package com.aurigo.masterworks.testframework.utilities.helper;

import com.aurigo.masterworks.testframework.BaseFramework;
import com.aurigo.masterworks.testframework.utilities.ExceptionHandler;
import com.aurigo.masterworks.testframework.utilities.JavaScriptUtil;
import com.aurigo.masterworks.testframework.webUI.constants.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitHelper extends BaseFramework {

    private WebDriver driver;
    private static int waitTimeout = 60; //Default value for wait, It gets override by properties file value.
    private WebDriverWait wait;

    public WaitHelper(WebDriver driver)
    {
        this.driver = driver;
        try{
            waitTimeout =  Integer.parseInt(EnvironmentHelper.getPropertyValue("waitTimeout"));
            wait = new WebDriverWait(driver, Duration.ofSeconds(waitTimeout));
        }catch (Exception e)
        {
            ExceptionHandler.logAndContinueWebDriverExceptions(e, "waitTimeout property is not set in properties file. Using default wait timeout of " + waitTimeout + " seconds");
            wait = new WebDriverWait(driver, Duration.ofSeconds(waitTimeout));
        }
    }

    public void waitForElementPresent(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementPresent(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void waitForElementToBeEnabled(By locator)
    {
        FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
                .pollingEvery(Duration.ofSeconds(1))
                .withTimeout(Duration.ofSeconds(waitTimeout));
        fluentWait.until(x -> { return  driver.findElement(locator).isEnabled();
        });
    }

    public void waitForElementToBePresentAndClickable(By locator)
    {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void waitForFrameAvailableAndSwitchToIt(By locator) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
    }

    public void waitUntilElementDisappears(By locator)
    {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void waitForElementClickable(WebElement element)
    {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForElementClickable(By locator)
    {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void waitForAlertPresent()
    {
        wait.until(ExpectedConditions.alertIsPresent());
    }

    public String waitForPageTitle(String title)
    {
        wait.until(ExpectedConditions.titleContains(title));
        return driver.getTitle();
    }

    public void waitForPageToLoad()
    {
        String pageStatusScript = "return document.readyState";
        try{
            var incomingFrame = JavaScriptUtil.getCurrentFrame(driver);
            if(incomingFrame.equals(Constants.MAIN_CONTENT_FRAME))
            {
                driver.switchTo().defaultContent();
            }
            FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
                    .pollingEvery(Duration.ofSeconds(1))
                    .withTimeout(Duration.ofSeconds(waitTimeout));

            fluentWait.until(d -> ((JavascriptExecutor) d).executeScript(pageStatusScript)
                    .equals("complete"));
            fluentWait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(String.format(".//frame[@name='%s']", Constants.MAIN_CONTENT_FRAME))));

            if(!incomingFrame.equals(Constants.MAIN_CONTENT_FRAME)) {
                driver.switchTo().defaultContent();
            }
        }
        catch (Exception e){
            ExceptionHandler.logAndContinueWebDriverExceptions(e, "Exception occurred while waiting for page to load.");
            }
    }





}
