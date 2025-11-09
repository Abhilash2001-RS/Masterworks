package com.aurigo.masterworks.testframework.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataTimeUtil {

    public static String getCurrentDateTime(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }

    public static String getCurrentDate(String dateFormat){
        return new SimpleDateFormat(dateFormat).format(new Date());
    }

    public String getDateString(String dateFormat, Date date){
        return new SimpleDateFormat(dateFormat).format(date);
    }
}
