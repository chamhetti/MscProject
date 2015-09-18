package lk.ac.ucsc.oms.exchanges.api.beans;


import lk.ac.ucsc.oms.common_utility.api.enums.PropertyEnable;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface Exchange {

    long getExchangeId();

    String getExchangeCode();

    void setExchangeCode(String exchangeCode);

    String getDescription();

    void setDescription(String description);

    ExchangeStatus getStatus();

    void setStatus(ExchangeStatus status);

    String getCreatedBy();

    void setCreatedBy(String createdBy);

    Date getCreatedDate();

    void setCreatedDate(Date createdDate);

    String getLastUpdatedBy();

    void setLastUpdatedBy(String lastUpdatedBy);

    Date getLastUpdatedDate();

    void setLastUpdatedDate(Date lastUpdatedDate);

    String getExchangeCodeReal();

    void setExchangeCodeReal(String exchangeCodeReal);

    String getExDestination();

    void setExDestination(String exDestination);

    String getDefaultCurrency();

    void setDefaultCurrency(String defaultCurrency);

    RegionType getRegion();

    void setRegion(RegionType regionType);

    String getDefaultExecutionBroker();

    void setDefaultExecutionBroker(String defaultExecutionBroker);

    long getDefaultCommGroup();

    void setDefaultCommGroup(long defaultCommGroup);

    Map<String, SubMarket> getSubMarkets();

    void setSubMarkets(Map<String, SubMarket> subMarkets);

    boolean isDirectlyConnectedToExchange();

    void setDirectlyConnectToExchange(boolean connectToExchange);

    Map<Integer, String> getChannelWiseExchangeCodes();

    void setChannelWiseExchangeCodes(Map<Integer, String> exchangeChannelIds);

    boolean isDirectlyConnected();

    void setDirectlyConnected(PropertyEnable directlyConnect);

    void setOrderedFixTagList(List<Integer> orderedFixTagList);

    List<Integer> getOrderedFixTagList();

    double getPriceFactor();

    void setPriceFactor(double priceFactor);

    String getFixVersion();

    void setFixVersion(String fixVersion);

    int getOrderNotAllowDuration();

    void setOrderNotAllowDuration(int orderNotAllowDuration);

    String getMarketCloseOrderTriggerTime();

    void setMarketCloseOrderTriggerTime(String marketCloseOrderTriggerTime);

    enum ExchangeStatus {
        PENDING_APPROVAL(0), APPROVED(1), DELETED(2), PENDING_DELETE(3);
        private int code;

        ExchangeStatus(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static ExchangeStatus getEnum(int code) {
            ExchangeStatus[] values = ExchangeStatus.values();
            if (code < 0 || code >= values.length) {
                return null;
            } else {
                return values[code];
            }
        }
    }

    /**
     * Sharia Enable status
     * NO -0
     * YES -1
     */
    enum RegionType {
        LOCAL(0), INTERNATIONAL(1);
        private int code;

        RegionType(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static RegionType getEnum(int code) {
            switch (code) {
                case 0:
                    return LOCAL;
                case 1:
                    return INTERNATIONAL;
                default:
                    return null;
            }
        }
    }
}
