package com.directfn.exchange_simulator.common_util.utils;


import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ConfigSettings {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ConfigSettings.class);
    private Properties properties = null;
    private static String propertiesFile = "simulator.properties";

    public ConfigSettings() {

    }

    /**
     * get the configuration value by property name
     *
     * @param propertyName
     * @return
     */
    public String getConfiguration(String propertyName) {
        String propertyValue = "";
        propertyValue = getConfiguration(propertiesFile, propertyName);
        return propertyValue;
    }

    /**
     * get the configuration value by file name and property name
     *
     * @param fileName
     * @param propertyName
     * @return
     */
    public synchronized String getConfiguration(String fileName, String propertyName) {
        String propertyValue = null;
        File settingsDFile = null;
        InputStream is = null;
        try {
            String settingsFile = System.getProperty("user.dir") + "/settings/" + fileName;
            if (properties == null || properties.isEmpty()) {
                properties = new Properties();
                settingsDFile = new File(settingsFile);
                is = new FileInputStream(settingsDFile);
                properties.load(is);
            }
            propertyValue = String.valueOf(properties.get(propertyName)).trim();

        } catch (Exception e) {
            logger.error("Error in ConfigurationSettings.getConfiguration " + e.getMessage(), e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("Error while closing the file", e);
                }
            }
        }

        return propertyValue;
    }

}
