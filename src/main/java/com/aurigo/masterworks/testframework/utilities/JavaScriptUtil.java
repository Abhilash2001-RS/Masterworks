package com.aurigo.masterworks.testframework.utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptUtil {

    public static void clickElementByJs(WebElement element, WebDriver driver)
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

    public static void flash(WebElement element, WebDriver driver) {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        String bgcolor = element.getCssValue("backgroundColor");
        for (int i = 0; i < 7; i++) {
            changeColor("rgb(0,200,0)", element, driver);// 1
            changeColor(bgcolor, element, driver);// 2
        }
    }

    public static void changeColor(String color, WebElement element, WebDriver driver) {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].style.backgroundColor = '" + color + "'", element);

        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
        }
    }

    public static void clickElementByJS(WebElement element, WebDriver driver) {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("arguments[0].click();", element);
    }

    public static void scrollIntoView(WebElement element, WebDriver driver)
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);");
    }

    public static String getCurrentFrame(WebDriver driver)
    {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (String) js.executeScript("return self.name;");
    }
}
