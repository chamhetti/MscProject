package com.directfn.exchange_simulator.common_util.utils;

import java.util.HashMap;
import java.util.Map;


public class Configuration {
    private Map<String, String> configurationMap = new HashMap<>();
    ConfigSettings configSettings = new ConfigSettings();
    private String defaultSettings = configSettings.getConfiguration("defaultSettings");
    private String fillingQuantity = configSettings.getConfiguration("fillingQuantity");
    private String timeGap = configSettings.getConfiguration("timeGap");
    private String marketOrderPrice = configSettings.getConfiguration("marketOrderPrice");

    /**
     * constructor for Configuration
     */
    public Configuration() {
        this.configurationMap.put("defaultSettings", defaultSettings);
        this.configurationMap.put("fillingQuantity", fillingQuantity);
        this.configurationMap.put("timeGap", timeGap);
        this.configurationMap.put("marketOrderPrice", marketOrderPrice);
    }

    /**
     * will return the configuration map
     *
     * @return Map<String,String>
     */
    public Map<String, String> getConfigurationMap() {
        return configurationMap;
    }
}
