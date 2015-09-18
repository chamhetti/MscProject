package lk.ac.ucsc.oms.exchanges.implGeneral.beans;

import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.common_utility.api.enums.PropertyEnable;
import lk.ac.ucsc.oms.exchanges.api.beans.Exchange;

import lk.ac.ucsc.oms.exchanges.api.beans.SubMarket;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.Id;
import java.util.*;


@Indexed
public class ExchangeBean extends CacheObject implements Exchange {
    @Id
    @Field
    private long exchangeId;
    @Field
    private String exchangeCode;
    @Field
    private String description;
    @Field
    private int status;
    @Field
    private int statusCode;
    @Field
    private int directlyConnect;
    @Field
    private String createdBy;
    @Field
    private Date createdDate;
    @Field
    private String lastUpdatedBy;
    @Field
    private Date lastUpdatedDate;
    @Field
    private String exchangeCodeReal;
    @Field
    private String exDestination;  //tag-100
    @Field
    private String defaultCurrency;
    @Field
    private double priceFactor;
    @Field
    private int regionType;
    @Field
    private String defaultExecutionBroker;
    @Field
    private long defaultCommGroup;
    @IndexedEmbedded
    private Map<String, SubMarket> subMarkets = new HashMap<>();
    @IndexedEmbedded
    private Map<Integer, String> channelWiseExchangeCodes = new HashMap<>();     // Key:ClientChannel.getCode(Enum),
    // Value:Code(String)
    private String orderedFixTags;
    @Field
    private String fixVersion;

    @Field
    private int orderNotAllowDuration;
    @Field
    private String marketCloseOrderTriggerTime;

    public ExchangeBean(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    protected ExchangeBean() {
    }

    @Override
    public Map<Integer, String> getChannelWiseExchangeCodes() {
        return channelWiseExchangeCodes;
    }


    @Override
    public void setChannelWiseExchangeCodes(Map<Integer, String> channelWiseExchangeCodes) {
        this.channelWiseExchangeCodes = channelWiseExchangeCodes;
    }


    @Override
    public long getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(long exchangeId) {
        this.exchangeId = exchangeId;
    }

    @Override
    public String getExchangeCode() {
        return exchangeCode;
    }


    @Override
    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    @Override
    public String getDescription() {
        return description;
    }


    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public ExchangeStatus getStatus() {
        return ExchangeStatus.getEnum(statusCode);
    }

    public int getDirectlyConnect() {
        return directlyConnect;
    }

    public void setDirectlyConnect(int directlyConnect) {
        this.directlyConnect = directlyConnect;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRegionType() {
        return regionType;
    }

    public void setRegionType(int regionType) {
        this.regionType = regionType;
    }

    @Override
    public boolean isDirectlyConnected() {
        return directlyConnect == 1;
    }

    @Override
    public void setDirectlyConnected(PropertyEnable directlyConnect) {
        if (directlyConnect == null) {
            return;
        }
        this.directlyConnect = directlyConnect.getCode();
    }

    @Override
    public void setStatus(ExchangeStatus status) {
        this.status = status.getCode();
        this.statusCode = status.getCode();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        this.status = statusCode;
    }


    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }


    @Override
    public Date getCreatedDate() {
        return createdDate;
    }

    @Override
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    @Override
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Override
    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    @Override
    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    @Override
    public String getExchangeCodeReal() {
        return exchangeCodeReal;
    }

    @Override
    public void setExchangeCodeReal(String exchangeCodeReal) {
        this.exchangeCodeReal = exchangeCodeReal;
    }

    @Override
    public String getExDestination() {
        return exDestination;
    }

    @Override
    public void setExDestination(String exDestination) {
        this.exDestination = exDestination;
    }

    @Override
    public String getDefaultCurrency() {
        return defaultCurrency;
    }

    @Override
    public void setDefaultCurrency(String defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }



    @Override
    public RegionType getRegion() {
        return RegionType.getEnum(regionType);
    }


    @Override
    public void setRegion(RegionType region) {
        if (region == null) {
            return;
        }
        this.regionType = region.getCode();
    }

    @Override
    public String getDefaultExecutionBroker() {
        return defaultExecutionBroker;
    }


    @Override
    public void setDefaultExecutionBroker(String defaultExecutionBroker) {
        this.defaultExecutionBroker = defaultExecutionBroker;
    }

    @Override
    public long getDefaultCommGroup() {
        return defaultCommGroup;
    }


    @Override
    public void setDefaultCommGroup(long defaultCommGroup) {
        this.defaultCommGroup = defaultCommGroup;
    }


    @Override
    public Map<String, SubMarket> getSubMarkets() {
        return subMarkets;
    }

    @Override
    public void setSubMarkets(Map<String, SubMarket> subMarkets) {
        this.subMarkets = subMarkets;
    }

    @Override
    public boolean isDirectlyConnectedToExchange() {
        return directlyConnect == 1;
    }

    @Override
    public void setDirectlyConnectToExchange(boolean connectToExchange) {
        if (connectToExchange) {
            directlyConnect = 1;
        } else {
            directlyConnect = 0;
        }
    }


    @Override
    public List<Integer> getOrderedFixTagList() {
        List<Integer> fixTagList = new ArrayList<>();
        if (orderedFixTags != null && !orderedFixTags.isEmpty()) {
            for (String s : orderedFixTags.split(",")) {
                fixTagList.add(Integer.parseInt(s));
            }
        }
        return fixTagList;
    }


    @Override
    public void setOrderedFixTagList(List<Integer> torderedFixTagList) {
        for (Integer tag : torderedFixTagList) {
            orderedFixTags = orderedFixTags + "," + tag;
        }
    }

    @Override
    public double getPriceFactor() {
        return priceFactor;
    }

    @Override
    public void setPriceFactor(double priceFactor) {
        this.priceFactor = priceFactor;
    }


    public String getOrderedFixTags() {
        return orderedFixTags;
    }

    public void setOrderedFixTags(String orderedFixTags) {
        this.orderedFixTags = orderedFixTags;
    }

    @Override
    public String getFixVersion() {
        return fixVersion;
    }

    @Override
    public void setFixVersion(String fixVersion) {
        this.fixVersion = fixVersion;
    }


    @Override
    public int getOrderNotAllowDuration() {
        return orderNotAllowDuration;
    }

    @Override
    public void setOrderNotAllowDuration(int orderNotAllowDuration) {
        this.orderNotAllowDuration = orderNotAllowDuration;
    }

    @Override
    public String getMarketCloseOrderTriggerTime() {
        return marketCloseOrderTriggerTime;
    }

    @Override
    public void setMarketCloseOrderTriggerTime(String marketCloseOrderTriggerTime) {
        this.marketCloseOrderTriggerTime = marketCloseOrderTriggerTime;
    }


}