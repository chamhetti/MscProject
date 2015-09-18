package lk.ac.ucsc.oms.exchanges.api.beans;

import java.util.Date;
import java.util.Map;


public interface SubMarket {

    int getSubMarketId();

    void setSubMarketId(int id);

    String getMarketCode();

    void setMarketCode(String marketCode);

    MarketStatus getMarketStatus();

    void setMarketStatus(MarketStatus marketStatus);

    String getPreOpenTime();

    void setPreOpenTime(String preOpenTime);

    String getPreCloseTime();

    void setPreCloseTime(String preCloseTime);

    String getOpenTime();

    void setOpenTime(String openTime);

    String getCloseTime();

    void setCloseTime(String closeTime);

    String getExchangeId();

    void setExchangeId(String exchangeId);

    Date getLastPreOpenedDate();

    void setLastPreOpenedDate(Date lastPreOpenedDate);

    Date getLastEODDate();

    void setLastEODDate(Date lastEODDate);

    boolean isPreOpenRan();

    void setPreOpenRan(boolean preOpenRan);

    boolean isEODRan();

    void setEODRan(boolean eODRan);

    int getStatusReqAllowed();

    void setStatusReqAllowed(int statusReqAllowed);

    Date getLastStatusReqDate();

    void setLastStatusReqDate(Date lastStatusReqDate);

    int getForcefullPreOpen();

    void setForcefullPreOpen(int forcefullPreOpen);

    OrderRoutingInfo getOrderRoutingInfo();

    void setOrderRoutingInfo(OrderRoutingInfo orderRoutingInfoBean);

    boolean isManuallySuspend();

    void setManualSuspend(boolean manualSuspend);

    int getEodInProgress();

    void setEodInProgress(int eodInProgress);

    Map<String, SSBMarketInfo> getSsbMarketInfo();


    MarketConnectStatus getConnectStatus();

    void setConnectStatus(MarketConnectStatus connectStatus);
}
