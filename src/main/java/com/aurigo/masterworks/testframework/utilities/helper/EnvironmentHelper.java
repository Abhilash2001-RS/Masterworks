package com.aurigo.masterworks.testframework.utilities.helper;

import com.aurigo.masterworks.testframework.BaseFramework;
import com.aurigo.masterworks.testframework.webUI.constants.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.Properties;

public class EnvironmentHelper extends BaseFramework
{

    static Properties properties;

    private static final Logger logger = LogManager.getLogger(EnvironmentHelper.class);

    public static String getPropertyValue(String propertyName)
    {
        if(properties == null)
            properties = loadProperties();

        return properties.getProperty(propertyName);
    }

    public static Properties loadProperties()
    {
        String rootPath = Paths.get(userDir, Constants.CONFIG_PROPERTIES_PATH).toString();

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(rootPath));
        }
        catch (Exception e) {
            logger.info("Error while getting Properties: " + e.getMessage());
        }
        return properties;
    }
}
