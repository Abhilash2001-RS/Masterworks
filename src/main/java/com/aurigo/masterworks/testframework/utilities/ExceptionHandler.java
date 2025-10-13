package com.aurigo.masterworks.testframework.utilities;

import java.util.Arrays;

import static com.aurigo.masterworks.testframework.BaseFramework.logger;

public class ExceptionHandler {

    public static void log(Exception e) {
        StringBuilder sb = new StringBuilder();
        sb.append("<textarea>" + e.getMessage());
        Arrays.stream(e.getStackTrace()).forEach(st -> sb.append("\n\t at " + st));
        sb.append("</textarea>");
        logger().fail(sb.toString());
    }

    /**
     * Log Exception.
     *
     * @param throwable Throwable Exception.
     */
    public static void log(Throwable throwable) {
        StringBuilder sb = new StringBuilder();
        sb.append("<textarea>" + throwable.getMessage());
        Arrays.stream(throwable.getStackTrace()).forEach(st -> sb.append("\n\t at " + st));
        sb.append("</textarea>");
        logger().fail(sb.toString());
    }

    /**
     * Log Exception along with message.
     *
     * @param e       Exception.
     * @param message Message to be logged.
     */
    public static void log(Exception e, String message) {
        logger().fail("<b>" + message + "</b>/n" + e);
    }

}
