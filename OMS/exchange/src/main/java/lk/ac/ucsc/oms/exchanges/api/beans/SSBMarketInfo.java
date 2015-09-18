package lk.ac.ucsc.oms.exchanges.api.beans;

import java.util.Date;


public interface SSBMarketInfo {

    int getSsbMarketInfoId();

    void setSsbMarketInfoId(int ssbMarketInfoId);

    String getBrokerCode();

    void setBrokerCode(String brokerCode);

    String getExchange();

    void setExchange(String exchange);

    String getMarketCode();

    void setMarketCode(String marketCode);

    MarketStatus getMarketStatus();

    void setMarketStatus(MarketStatus marketStatus);

    Date getLastPreOpenedDate();

    void setLastPreOpenedDate(Date lastPreOpenedDate);

    Date getLastEODDate();

    void setLastEODDate(Date lastEODDate);

    boolean isManuallySuspend();

    void setManualSuspend(boolean manualSuspend);

    int getStatusReqAllowed();

    void setStatusReqAllowed(int statusReqAllowed);

    Date getLastStatusReqDate();

    void setLastStatusReqDate(Date lastStatusReqDate);

    int getForcefullPreOpen();

    void setForcefullPreOpen(int forcefullPreOpen);

    MarketConnectStatus getConnectStatus();

    void setConnectStatus(MarketConnectStatus connectStatus);

    int getIsExchange();

    void setIsExchange(int isExchange);
}
