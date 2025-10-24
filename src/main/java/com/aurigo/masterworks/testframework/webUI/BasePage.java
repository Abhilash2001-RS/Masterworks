package com.aurigo.masterworks.testframework.webUI;

import com.aurigo.masterworks.testframework.BaseFramework;
import com.aurigo.masterworks.testframework.utilities.helper.ElementHelper;
import com.aurigo.masterworks.testframework.utilities.helper.WaitHelper;
import com.google.common.base.Strings;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Method;

public class BasePage extends BaseFramework {

    protected WaitHelper waitHelper;
    protected ElementHelper elementHelper;
    private WebDriver driver;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        elementHelper = new ElementHelper(driver);
        waitHelper = new WaitHelper(driver);
    }

    /**
     * Get Enum Value
     *
     * @param enumObj - Enum Object
     * @param <E>     - Corresponding Enum
     * @return - Enum Value
     */

    protected <E> String getEnumValue(E enumObj) {
        Method method = null;

        try{
            Class cls = enumObj.getClass(); //Gets the runtime class of the enum object
            method = cls.getDeclaredMethod("getValue"); //Gets the method named "getValue" from the enum class
            method.invoke(enumObj); //Invokes the "getValue" method on the enum object
        }catch (NoSuchMethodException  | IllegalAccessException | java.lang.reflect.InvocationTargetException e){
            e.printStackTrace();
        }

        return Strings.nullToEmpty(null); //returns an empty string instead of null. This is a Guava utility
    }

    public <TPage extends BasePage> TPage getPage(Class<TPage> page)
    {
        return GetInstance(page, driver);
    }
}
