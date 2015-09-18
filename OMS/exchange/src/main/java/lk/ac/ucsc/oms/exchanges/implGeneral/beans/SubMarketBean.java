package lk.ac.ucsc.oms.exchanges.implGeneral.beans;

import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.exchanges.api.beans.*;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.Id;
import java.util.Date;
import java.util.Map;


@Indexed
public class SubMarketBean extends CacheObject implements SubMarket {
    @Id
    @Field
    private int subMarketId;
    @Field
    private String marketCode;
    @Field
    private MarketStatus marketStatus;
    @Field
    private MarketConnectStatus connectStatus;
    @Field
    private String preOpenTime;
    @Field
    private String preCloseTime;
    @Field
    private String openTime;
    @Field
    private String closeTime;
    @Field
    private String exchangeId;
    @Field
    private Date lastPreOpenedDate;
    @Field
    private Date lastEODDate;
    @Field
    private int preOpenRun;
    @Field
    private int eodRan;
    @Field
    private int manualSuspend;
    @Field
    private int eodInProgress;
    @Field
    private int statusReqAllowed;
    @Field
    private Date lastStatusReqDate;
    @Field
    private int forcefullPreOpen;
    @FieldBridge
    private OrderRoutingInfo orderRoutingInfo;
    @IndexedEmbedded
    private Map<String, SSBMarketInfo> ssbMarketInfo;


    private static final int HASH_CODE_GENERATE_MULTIPLIER = 31;


    @Override
    public int getForcefullPreOpen() {
        return forcefullPreOpen;
    }


    @Override
    public void setForcefullPreOpen(int forcefullPreOpen) {
        this.forcefullPreOpen = forcefullPreOpen;
    }


    @Override
    public String getMarketCode() {
        return marketCode;
    }


    @Override
    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }


    @Override
    public MarketStatus getMarketStatus() {
        return marketStatus;
    }


    @Override
    public void setMarketStatus(MarketStatus marketStatus) {
        this.marketStatus = marketStatus;
    }


    @Override
    public String getPreOpenTime() {
        return preOpenTime;
    }


    @Override
    public void setPreOpenTime(String preOpenTime) {
        this.preOpenTime = preOpenTime;
    }


    @Override
    public String getPreCloseTime() {
        return preCloseTime;
    }


    @Override
    public void setPreCloseTime(String preCloseTime) {
        this.preCloseTime = preCloseTime;
    }


    @Override
    public String getOpenTime() {
        return openTime;
    }


    @Override
    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }


    @Override
    public String getCloseTime() {
        return closeTime;
    }


    @Override
    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }


    @Override
    public String getExchangeId() {
        return exchangeId;
    }


    @Override
    public void setExchangeId(String exchangeId) {
        this.exchangeId = exchangeId;
    }


    @Override
    public Date getLastPreOpenedDate() {
        return lastPreOpenedDate;
    }


    @Override
    public void setLastPreOpenedDate(Date lastPreOpenedDate) {
        this.lastPreOpenedDate = lastPreOpenedDate;
    }


    @Override
    public Date getLastEODDate() {
        return lastEODDate;
    }


    @Override
    public void setLastEODDate(Date lastEODDate) {
        this.lastEODDate = lastEODDate;
    }


    @Override
    public boolean isPreOpenRan() {
        return preOpenRun == 1;
    }


    @Override
    public void setPreOpenRan(boolean preOpenRan) {
        if (preOpenRan) {
            this.preOpenRun = 1;
        }
    }


    @Override
    public boolean isEODRan() {
        return eodRan == 1;
    }


    public int getEodRan() {
        return eodRan;
    }


    public void setEodRan(int eodRan) {
        this.eodRan = eodRan;
    }


    public int getPreOpenRun() {
        return preOpenRun;
    }


    public void setPreOpenRun(int preOpenRun) {
        this.preOpenRun = preOpenRun;
    }


    @Override
    public void setEODRan(boolean eODRan) {
        if (eODRan) {
            eodRan = 1;
        }
    }

    public int getManualSuspend() {
        return manualSuspend;
    }


    public void setManualSuspend(int manualSuspend) {
        this.manualSuspend = manualSuspend;
    }


    @Override
    public boolean isManuallySuspend() {
        return manualSuspend == 1;
    }


    @Override
    public void setManualSuspend(boolean manualSuspend) {
        if (manualSuspend) {
            this.manualSuspend = 1;
        } else {
            this.manualSuspend = 0;
        }
    }


    @Override
    public int getEodInProgress() {
        return eodInProgress;
    }

    @Override
    public void setEodInProgress(int eodInProgress) {
        this.eodInProgress = eodInProgress;
    }


    @Override
    public int getStatusReqAllowed() {
        return statusReqAllowed;
    }


    @Override
    public void setStatusReqAllowed(int statusReqAllowed) {
        this.statusReqAllowed = statusReqAllowed;
    }


    @Override
    public Date getLastStatusReqDate() {
        return lastStatusReqDate;
    }


    @Override
    public void setLastStatusReqDate(Date lastStatusReqDate) {
        this.lastStatusReqDate = lastStatusReqDate;
    }


    public int getSubMarketId() {
        return subMarketId;
    }


    public void setSubMarketId(int subMarketId) {
        this.subMarketId = subMarketId;
    }


    @Override
    public OrderRoutingInfo getOrderRoutingInfo() {
        return orderRoutingInfo;
    }


    @Override
    public void setOrderRoutingInfo(OrderRoutingInfo orderRoutingInfo) {
        this.orderRoutingInfo = orderRoutingInfo;
    }


    public Map<String, SSBMarketInfo> getSsbMarketInfo() {
        return ssbMarketInfo;
    }


    public void setSsbMarketInfo(Map<String, SSBMarketInfo> ssbMarketInfo) {
        this.ssbMarketInfo = ssbMarketInfo;
    }


    public MarketConnectStatus getConnectStatus() {
        return connectStatus;
    }


    public void setConnectStatus(MarketConnectStatus connectStatus) {
        this.connectStatus = connectStatus;
    }


}
