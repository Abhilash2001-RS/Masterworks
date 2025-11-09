package com.aurigo.masterworks.testframework.webUI.common;

import com.aurigo.masterworks.testframework.webUI.BasePage;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

public class AlertHandler extends BasePage {

    private WebDriver driver;

    public AlertHandler(WebDriver driver){
        super(driver);
        this.driver = driver;
    }
    /**
     * Function to check whether an alert is present or not
     *
     * @return alert if alert is present else null value
     */
    public Alert getAlert()
    {
        try{
            waitHelper.waitForAlertPresent();
            return driver.switchTo().alert();
        }catch (NoAlertPresentException e){
            return null;
        }
    }
    /**
     * Function to get alert without waiting
     *
     * @return alert if alert is present else null value
     */
    public Alert getAlertWithoutWait()
    {
        try{
            return driver.switchTo().alert();
        }catch (NoAlertPresentException e){
            return null;
        }
    }
    /**
     * Method to check if a browser pop up has appeared
     *
     * @param wait if True then waits for alert to appear
     * @return true if present
     */
    public boolean isAlertPresent(boolean wait) {
        if(wait){
            waitHelper.waitForAlertPresent();
        }
        return (getAlertWithoutWait() == null);
    }

    /**
     * Method to Handle Alerts
     *
     * @param accept set true if alert needs to be accepted
     */
    public void acceptAlert(boolean accept){
        Alert alert = getAlert();
        if(alert != null) {
            if(accept)
                alert.accept();
            else
                alert.dismiss();
            alert.accept();
        }
    }

    public String getAlertMessage() {
        Alert alert = getAlert();
        if (alert != null)
            return getAlert().getText();
        else
            return null;
    }
}
