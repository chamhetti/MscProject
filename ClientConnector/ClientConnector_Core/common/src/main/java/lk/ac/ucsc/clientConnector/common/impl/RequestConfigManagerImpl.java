package lk.ac.ucsc.clientConnector.common.impl;

import lk.ac.ucsc.clientConnector.common.api.RequestConfigManager;

import java.util.Map;

/**
 * Created by amila on 5/22/14.
 */
public class RequestConfigManagerImpl implements RequestConfigManager {
    private Map<String, String> configBeanMap;

    @Override
    public String getRequestConfig(int group, int type) {
        return configBeanMap.get(group + "-" + type);
    }

    public void setConfigBeanMap(Map<String, String> configBeanMap) {
        this.configBeanMap = configBeanMap;
    }
}
