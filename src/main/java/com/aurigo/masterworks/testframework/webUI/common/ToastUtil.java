package com.aurigo.masterworks.testframework.webUI.common;

import com.aurigo.masterworks.testframework.utilities.LocatorUtil;
import com.aurigo.masterworks.testframework.utilities.helper.ScreenshotHelper;
import com.aurigo.masterworks.testframework.webUI.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

public class ToastUtil extends BasePage  {

    private WebDriver driver;

    private By toastContainer;
    private By toastCloseButton;
    private By toastMessage;

    public ToastUtil(WebDriver driver){
        super(driver);
        this.driver = driver;

        var locators = LocatorUtil.getLocators("ToastUtil.json");
        toastContainer = locators.get("toastContainer");
        toastCloseButton = locators.get("toastCloseButton");
        toastMessage = locators.get("toastMessage");
    }


    /**
     * Waits and Gets Toast message if only one Toast Exists.
     *
     * @return Toast error message
     */
    public String waitAndGetMessageForSingleToast() {
        return waitAndGetMessageForSingleToast(true);
    }

    /**
     * Waits and Gets Toast message if only one Toast Exists.
     *
     * @param requireScreenShot Is screenshot required.
     * @return Toast error message
     */
    public String waitAndGetMessageForSingleToast(boolean requireScreenShot) {
        getPage(Navigation.class).switchFrameToContent();
        waitHelper.waitForElementPresent(toastContainer);
        waitHelper.waitForElementClickable(toastMessage);
        String toastMsg = elementHelper.getElement(toastMessage).getText();
        if (requireScreenShot) {
            getPage(ScreenshotHelper.class).takeElementScreenshot(toastContainer, "Toast Message");
        }

        return toastMsg;
    }

    /**
     * Get all toasts on screen
     *
     * @param requireScreenShot if true, a screenshot is taken
     * @return list of toast messages found
     */
    public List<String> getAllToastMessages(boolean requireScreenShot) {
        getPage(Navigation.class).switchFrameToContent();
        waitHelper.waitForElementPresent(toastContainer);
        if (requireScreenShot) {
            getPage(ScreenshotHelper.class).takeElementScreenshot(toastContainer, "Toast Message");
        }
        var messageElements = elementHelper.getElements(toastMessage);
        List<String> messages = new ArrayList<>();
        for (var element : messageElements) {
            messages.add(element.getText());
        }
        return messages;
    }

    /**
     * Click close button for one existing toast.
     */
    public void waitAndCloseForSingleToast() {
        getPage(Navigation.class).switchFrameToContent();
        if (elementHelper.isElementDisplayed(toastCloseButton)) {
            elementHelper.doClick(toastCloseButton);
        }
        waitHelper.waitUntilElementDisappears(toastContainer);
    }

    /**
     * Get message from one existing information toast.
     */
    public void waitForInformationSingleToast() {
        getPage(Navigation.class).switchFrameToContent();
        waitHelper.waitForElementPresent(toastContainer);
        waitHelper.waitUntilElementDisappears(toastContainer);
    }

    /**
     * Validates the presence of Toast message
     *
     * @return true if the toast message is present
     */
    public boolean validatePresenceOfToastMessage() {
        getPage(Navigation.class).switchFrameToContent();
        return getPage(Validations.class).verifyElementExists(toastContainer);
    }

    /**
     * Waits for a toast to disappear.
     */
    public void waitForToastDisappears() {
        getPage(Navigation.class).switchFrameToContent();
        waitHelper.waitForElementPresent(toastContainer);
        waitHelper.waitUntilElementDisappears(toastContainer);
    }

    /**
     * Method to get a toast Message without waiting
     *
     * @return The toast Message
     */
    public String getToastMessage() {
        waitHelper.waitForElementPresent(toastMessage);
        return elementHelper.doGetText(toastMessage);
    }

    /**
     * Method to get a toast Message with wait
     *
     * @return The toast Message
     */
    public String waitAndGetToastMessage() {
        return elementHelper.waitAndGetText(toastMessage);
    }

    /**
     * This method used to get the toast message and retry if toast is null
     *
     * @return toast message
     */
    public String getToastAndRetryWhenToastIsNull() {
        String toast = waitAndGetToastMessage();
        if (toast == null || toast.isEmpty()) {
            toast = waitAndGetToastMessage();
        }
        return toast;
    }
}
