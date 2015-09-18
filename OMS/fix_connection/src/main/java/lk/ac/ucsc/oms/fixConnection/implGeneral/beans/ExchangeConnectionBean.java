package lk.ac.ucsc.oms.fixConnection.implGeneral.beans;

import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.common_utility.api.enums.PropertyEnable;
import lk.ac.ucsc.oms.fixConnection.api.beans.ExchangeConnection;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Id;
import java.util.Map;

@Indexed
public class ExchangeConnectionBean extends CacheObject implements ExchangeConnection {
    @Id
    @Field
    private int exchangeConnectionID;   // is the primary key
    @Field
    private String exchange;   // security exchange name
    @Field
    private String masterAccountNumber;   // master account number for the exchange.

    Map<Integer, String> exchangeCustomTags;
    @Field
    private PropertyEnable masterAccountRoutingEnabled; // if the master account routing is enabled for the exchange

    private Map<String, String> orderedFixTags;

    @Field
    private String sessionQualifier;
    @Field
    private int allowToSendPendingStatus; // allow to send pending order status

    public int getExchangeConnectionID() {
        return exchangeConnectionID;
    }

    public void setExchangeConnectionID(int exchangeConnectionID) {
        this.exchangeConnectionID = exchangeConnectionID;
    }

    @Override
    public String getExchange() {
        return exchange;
    }

    @Override
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    @Override
    public String getMasterAccountNumber() {
        return masterAccountNumber;
    }

    @Override
    public void setMasterAccountNumber(String masterAccountNumber) {
        this.masterAccountNumber = masterAccountNumber;
    }

    @Override
    public PropertyEnable getMasterAccountRoutingEnabled() {
        return masterAccountRoutingEnabled;
    }

    @Override
    public void setMasterAccountRoutingEnabled(PropertyEnable masterAccountRoutingEnabled) {
        this.masterAccountRoutingEnabled = masterAccountRoutingEnabled;
    }

    @Override
    public Map<Integer, String> getExchangeCustomTags() {
        return exchangeCustomTags;
    }

    @Override
    public void setExchangeCustomTags(Map<Integer, String> exchangeCustomTags) {
        this.exchangeCustomTags = exchangeCustomTags;
    }

    @Override
    public Map<String, String> getOrderedFixTags() {
        return orderedFixTags;
    }

    @Override
    public void setOrderedFixTags(Map<String, String> orderedFixTags) {
        this.orderedFixTags = orderedFixTags;
    }

    @Override
    public String getSessionQualifier() {
        return sessionQualifier;
    }

    @Override
    public void setSessionQualifier(String sessionQualifier) {
        this.sessionQualifier = sessionQualifier;
    }

    @Override
    public int getAllowToSendPendingStatus() {
        return allowToSendPendingStatus;
    }

    @Override
    public void setAllowToSendPendingStatus(int allowToSendPendingStatus) {
        this.allowToSendPendingStatus = allowToSendPendingStatus;
    }


}
