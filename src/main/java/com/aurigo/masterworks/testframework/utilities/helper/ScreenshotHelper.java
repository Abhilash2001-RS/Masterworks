package com.aurigo.masterworks.testframework.utilities.helper;

import com.aurigo.masterworks.testframework.webUI.BasePage;
import com.aurigo.masterworks.testframework.webUI.constants.Constants;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import static com.aurigo.masterworks.testframework.BaseFramework.logger;

public class ScreenshotHelper extends BasePage {

    private WebDriver driver;

    protected ScreenshotHelper(WebDriver driver) {
        super(driver);
    }

    public String takeFullScreenshotAndReturnBase64String(String title) {
        String base64String = "";
        try {
            base64String = takeScreenshotAsBase64String();
            logger().info(title, MediaEntityBuilder.createScreenCaptureFromBase64String(base64String).build());
        } catch (Exception e) {
            logger().info("Error while taking full screenshot" + e.getMessage());
        }
        return base64String;
    }

    private String takeScreenshotAsBase64String() {
        String base64String = "";
        try {
            base64String = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            logger().info("Some exception occurred while taking screenshot " + e.getMessage());
        }
        return base64String;
    }

    public void takeElementScreenshot(By locator, String title) {
        try {
            logger().info(title, MediaEntityBuilder.createScreenCaptureFromBase64String(takeScreenshotOfElementAsBase64String(locator)).build());
        } catch (Exception e) {
            logger().info("Error while taking element screenshot" + e.getMessage());
        }
    }

    public void takeFullScreenshot(String title) {
        try {
            logger().info(title, MediaEntityBuilder.createScreenCaptureFromBase64String(takeScreenshotAsBase64String()).build());
        } catch (Exception e) {
            logger().info("Error while taking full screenshot" + e.getMessage());
        }
    }

    private String takeScreenshotOfElementAsBase64String(By locator) {
        String base64String = "";
        try {
            base64String = elementHelper.getElement(locator).getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            logger().info("Some exception occurred while taking screenshot " + e.getMessage());
        }
        return base64String;
    }

    public void takeScreenshotOfContentFrame() {
        try {
            var xpathString = String.format(".//iframe[@name='%s']", Constants.MAIN_CONTENT_FRAME_NAME);
            driver.switchTo().defaultContent();
            logger().info("Content Frame", MediaEntityBuilder.createScreenCaptureFromBase64String(takeScreenshotOfElementAsBase64String(By.xpath(xpathString))).build());
            driver.switchTo().frame(Constants.MAIN_CONTENT_FRAME_NAME);
        } catch (Exception e) {
            logger().info("Error while taking content frame screenshot" + e.getMessage());
            driver.switchTo().defaultContent();

        }
    }
}
