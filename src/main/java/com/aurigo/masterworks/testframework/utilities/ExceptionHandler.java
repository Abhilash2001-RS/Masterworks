package com.aurigo.masterworks.testframework.utilities;

import com.aurigo.masterworks.testframework.BaseFramework;
import com.opencsv.exceptions.CsvBadConverterException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.util.Arrays;

import static com.aurigo.masterworks.testframework.BaseFramework.logger;

public class ExceptionHandler extends BaseFramework {

    public static void log(Exception e)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<textarea>" + e.getMessage());
        Arrays.stream(e.getStackTrace()).forEach(s->sb.append("\n\t at " + s));
        sb.append("/<textarea>");
        logger().fail(sb.toString());
    }

    public static void log(Throwable throwable)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<textarea>" + throwable.getMessage());
        Arrays.stream(throwable.getStackTrace()).forEach(s->sb.append("\n\t at " + s));
        sb.append("/<textarea>");
        logger().fail(sb.toString());
    }

    public static void log(Exception e, String message)
    {
        logger().fail("<b> "+message+"</b>");

    }

    public static void logAndContinueWebDriverExceptions(Exception e)
    {
        if(e instanceof WebDriverException) {
            logger().info(e);
        }
        else {
            logger().fail(e);
        }
    }

    public static void logAndContinueWebDriverExceptions(Exception e, String message)
    {
        if(e instanceof WebDriverException){
            logger().info("<b> "+message+"</b>");
        }
        else {
            logger().fail("<b> "+message+"</b>");
        }
    }


}
