package lk.ac.ucsc.oms.fixConnection.api.beans;

import lk.ac.ucsc.oms.common_utility.api.enums.PropertyEnable;

import java.util.Map;


public interface ExchangeConnection {

    String getExchange();

    void setExchange(String exchange);

    String getMasterAccountNumber();

    void setMasterAccountNumber(String masterAccountNumber);

    PropertyEnable getMasterAccountRoutingEnabled();

    void setMasterAccountRoutingEnabled(PropertyEnable masterAccountRoutingEnabled);

    Map<Integer, String> getExchangeCustomTags();

    void setExchangeCustomTags(Map<Integer, String> exchangeCustomTags);

    Map<String, String> getOrderedFixTags();

    void setOrderedFixTags(Map<String, String> orderedFixTags);

    String getSessionQualifier();

    void setSessionQualifier(String sessionQualifier);

    int getAllowToSendPendingStatus();

    void setAllowToSendPendingStatus(int allowToSendPendingStatus);
}
