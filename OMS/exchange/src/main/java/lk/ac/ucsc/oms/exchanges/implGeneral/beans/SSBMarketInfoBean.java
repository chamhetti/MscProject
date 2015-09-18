package lk.ac.ucsc.oms.exchanges.implGeneral.beans;


import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.exchanges.api.beans.MarketConnectStatus;
import lk.ac.ucsc.oms.exchanges.api.beans.MarketStatus;
import lk.ac.ucsc.oms.exchanges.api.beans.SSBMarketInfo;
import org.hibernate.search.annotations.Field;

import javax.persistence.Id;
import java.util.Date;


public class SSBMarketInfoBean extends CacheObject implements SSBMarketInfo {

    @Id
    @Field
    private int ssbMarketInfoId;
    @Field
    private String brokerCode;
    @Field
    private String exchange;
    @Field
    private String marketCode;
    @Field
    private MarketStatus marketStatus;
    @Field
    private Date lastPreOpenedDate;
    @Field
    private Date lastEODDate;
    @Field
    private int manualSuspend;
    @Field
    private MarketConnectStatus connectStatus;
    @Field
    private int statusReqAllowed;
    @Field
    private Date lastStatusReqDate;
    @Field
    private int forcefullPreOpen;
    @Field
    private int isExchange; // 1- Exchange, 0- Broker

    private static final int HASH_CODE_GENERATE_MULTIPLIER = 31;

    public int getSsbMarketInfoId() {
        return ssbMarketInfoId;
    }

    public void setSsbMarketInfoId(int ssbMarketInfoId) {
        this.ssbMarketInfoId = ssbMarketInfoId;
    }

    public String getBrokerCode() {
        return brokerCode;
    }

    public void setBrokerCode(String brokerCode) {
        this.brokerCode = brokerCode;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getMarketCode() {
        return marketCode;
    }

    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    public MarketStatus getMarketStatus() {
        return marketStatus;
    }

    public void setMarketStatus(MarketStatus marketStatus) {
        this.marketStatus = marketStatus;
    }

    public Date getLastPreOpenedDate() {
        return lastPreOpenedDate;
    }

    public void setLastPreOpenedDate(Date lastPreOpenedDate) {
        this.lastPreOpenedDate = lastPreOpenedDate;
    }

    public Date getLastEODDate() {
        return lastEODDate;
    }

    public void setLastEODDate(Date lastEODDate) {
        this.lastEODDate = lastEODDate;
    }

    @Override
    public boolean isManuallySuspend() {
        return manualSuspend == 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setManualSuspend(boolean manualSuspend) {
        if (manualSuspend) {
            this.manualSuspend = 1;
        } else {
            this.manualSuspend = 0;
        }
    }


    public int getManualSuspend() {
        return manualSuspend;
    }


    /**
     * Method to set manual suspend int value
     *
     * @param manualSuspend int 1 - manual suspend , 0 - not
     */
    public void setManualSuspend(int manualSuspend) {
        this.manualSuspend = manualSuspend;
    }

    public int getStatusReqAllowed() {
        return statusReqAllowed;
    }

    public void setStatusReqAllowed(int statusReqAllowed) {
        this.statusReqAllowed = statusReqAllowed;
    }

    public Date getLastStatusReqDate() {
        return lastStatusReqDate;
    }

    public void setLastStatusReqDate(Date lastStatusReqDate) {
        this.lastStatusReqDate = lastStatusReqDate;
    }

    public int getForcefullPreOpen() {
        return forcefullPreOpen;
    }

    public void setForcefullPreOpen(int forcefullPreOpen) {
        this.forcefullPreOpen = forcefullPreOpen;
    }

    public MarketConnectStatus getConnectStatus() {
        return connectStatus;
    }

    public void setConnectStatus(MarketConnectStatus connectStatus) {
        this.connectStatus = connectStatus;
    }

    @Override
    public int getIsExchange() {
        return isExchange;
    }

    @Override
    public void setIsExchange(int isExchange) {
        this.isExchange = isExchange;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SSBMarketInfoBean that = (SSBMarketInfoBean) o;

        if (forcefullPreOpen != that.forcefullPreOpen) {
            return false;
        }
        if (manualSuspend != that.manualSuspend) {
            return false;
        }
        if (ssbMarketInfoId != that.ssbMarketInfoId) {
            return false;
        }
        if (statusReqAllowed != that.statusReqAllowed) {
            return false;
        }
        if (brokerCode != null) {
            if (!brokerCode.equals(that.brokerCode)) {
                return false;
            }
        } else {
            if (that.brokerCode != null) {
                return false;
            }
        }
        if (lastEODDate != null) {
            if (!lastEODDate.equals(that.lastEODDate)) {
                return false;
            }
        } else {
            if (that.lastEODDate != null) {
                return false;
            }
        }
        if (lastPreOpenedDate != null) {
            if (!lastPreOpenedDate.equals(that.lastPreOpenedDate)) {
                return false;
            }
        } else {
            if (that.lastPreOpenedDate != null) {
                return false;
            }
        }
        if (lastStatusReqDate != null) {
            if (!lastStatusReqDate.equals(that.lastStatusReqDate)) {
                return false;
            }
        } else {
            if (that.lastStatusReqDate != null) {
                return false;
            }
        }
        return marketStatus == that.marketStatus;

    }

    @Override
    public int hashCode() {
        int result = ssbMarketInfoId;
        if (brokerCode != null) {
            result = HASH_CODE_GENERATE_MULTIPLIER * result + brokerCode.hashCode();
        } else {
            result = HASH_CODE_GENERATE_MULTIPLIER * result;
        }
        if (exchange != null) {
            result = HASH_CODE_GENERATE_MULTIPLIER * result + exchange.hashCode();
        } else {
            result = HASH_CODE_GENERATE_MULTIPLIER * result;
        }
        if (marketStatus != null) {
            result = HASH_CODE_GENERATE_MULTIPLIER * result + marketStatus.hashCode();
        } else {
            result = HASH_CODE_GENERATE_MULTIPLIER * result;
        }
        if (lastPreOpenedDate != null) {
            result = HASH_CODE_GENERATE_MULTIPLIER * result + lastPreOpenedDate.hashCode();
        } else {
            result = HASH_CODE_GENERATE_MULTIPLIER * result;
        }
        if (lastEODDate != null) {
            result = HASH_CODE_GENERATE_MULTIPLIER * result + lastEODDate.hashCode();
        } else {
            result = HASH_CODE_GENERATE_MULTIPLIER * result;
        }
        result = HASH_CODE_GENERATE_MULTIPLIER * result + manualSuspend;
        result = HASH_CODE_GENERATE_MULTIPLIER * result + statusReqAllowed;
        if (lastStatusReqDate != null) {
            result = HASH_CODE_GENERATE_MULTIPLIER * result + lastStatusReqDate.hashCode();
        } else {
            result = HASH_CODE_GENERATE_MULTIPLIER * result;
        }
        result = HASH_CODE_GENERATE_MULTIPLIER * result + forcefullPreOpen;
        return result;
    }

    @Override
    public String toString() {
        return "SSBMarketInfoBean{" +
                "ssbMarketInfoId=" + ssbMarketInfoId +
                ", brokerCode='" + brokerCode + '\'' +
                ", marketStatus=" + marketStatus +
                ", lastPreOpenedDate=" + lastPreOpenedDate +
                ", lastEODDate=" + lastEODDate +
                ", manualSuspend=" + manualSuspend +
                ", statusReqAllowed=" + statusReqAllowed +
                ", lastStatusReqDate=" + lastStatusReqDate +
                ", forcefullPreOpen=" + forcefullPreOpen +
                '}';
    }
}
