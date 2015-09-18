package lk.ac.ucsc.oms.symbol.implGeneral.beans;

import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.symbol.api.beans.SymbolPriceData;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Id;
import java.util.Date;


@Indexed
public class SymbolPriceDataBean extends CacheObject implements SymbolPriceData {
    @Id
    @Field
    private String symbolCode;
    @Id
    @Field
    private String exchangeCode;
    @Field
    private double previousClosed = 0;
    @Field
    private double min = 0;
    @Field
    private double max = 0;
    @Field
    private double lastTradePrice = 0;
    @Field
    private double bestBidPrice = 0;
    @Field
    private double bestAskPrice = 0;
    @Field
    private double bestBidVol = 0;
    @Field
    private double bestAskVol = 0;
    @Field
    private double strikePrice;
    @Field
    private Date lastUpdatedTime = null;
    @Field
    private int loadedToCache = 0;
    @Field
    private double lastBidPrice = 0;
    @Field
    private int optionType = 0;

    private static final int HASH_CODE_GENERATE_MULTIPLIER = 31;
    private static final int HASH_CODE_COMPARATOR = 32;


    @Override
    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    @Override
    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    @Override
    public double getMin() {
        return min;
    }

    @Override
    public void setMin(double min) {
        this.min = min;
    }

    @Override
    public double getMax() {
        return max;
    }

    @Override
    public void setMax(double max) {
        this.max = max;
    }

    @Override
    public double getLastTradePrice() {
        return lastTradePrice;
    }

    @Override
    public void setLastTradePrice(double lastTradePrice) {
        this.lastTradePrice = lastTradePrice;
    }

    @Override
    public double getBestBidPrice() {
        return bestBidPrice;
    }

    @Override
    public void setBestBidPrice(double bestBidPrice) {
        this.bestBidPrice = bestBidPrice;
    }

    @Override
    public double getBestAskPrice() {
        return bestAskPrice;
    }

    @Override
    public void setBestAskPrice(double bestAskPrice) {
        this.bestAskPrice = bestAskPrice;
    }

    @Override
    public double getBestBidVol() {
        return bestBidVol;
    }

    @Override
    public void setBestBidVol(double bestBidVol) {
        this.bestBidVol = bestBidVol;
    }

    @Override
    public double getBestAskVol() {
        return bestAskVol;
    }

    @Override
    public void setBestAskVol(double bestAskVol) {
        this.bestAskVol = bestAskVol;
    }

    @Override
    public double getPreviousClosed() {
        return previousClosed;
    }

    @Override
    public void setPreviousClosed(double previousClosed) {
        this.previousClosed = previousClosed;
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
    public String getSymbolCode() {
        return symbolCode;
    }

    @Override
    public void setSymbolCode(String symbolCode) {
        this.symbolCode = symbolCode;
    }

    @Override
    public double getStrikePrice() {
        return strikePrice;
    }

    @Override
    public void setStrikePrice(double strikePrice) {
        this.strikePrice = strikePrice;
    }

    @Override
    public int getLoadedToCache() {
        return loadedToCache;
    }

    @Override
    public void setLoadedToCache(int loadedToCache) {
        this.loadedToCache = loadedToCache;
    }

    @Override
    public double getLastBidPrice() {
        return lastBidPrice;
    }

    @Override
    public void setLastBidPrice(double lastBidPrice) {
        this.lastBidPrice = lastBidPrice;
    }

    @Override
    public int getOptionType() {
        return optionType;
    }

    @Override
    public void setOptionType(int optionType) {
        this.optionType = optionType;
    }

    @Override
    public String toString() {
        return "SymbolPriceDataBean{" +
                "symbolCode='" + symbolCode + '\'' +
                ", exchangeCode='" + exchangeCode + '\'' +
                ", previousClosed=" + previousClosed +
                ", min=" + min +
                ", max=" + max +
                ", lastTradePrice=" + lastTradePrice +
                ", bestBidPrice=" + bestBidPrice +
                ", bestAskPrice=" + bestAskPrice +
                ", bestBidVol=" + bestBidVol +
                ", bestAskVol=" + bestAskVol +
                ", strikePrice=" + strikePrice +
                ", lastUpdatedTime=" + lastUpdatedTime +
                ", loadedToCache=" + loadedToCache +
                ", lastBidPrice=" + lastBidPrice +
                ", optionType=" + optionType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SymbolPriceDataBean)) {
            return false;
        }

        SymbolPriceDataBean that = (SymbolPriceDataBean) o;

        if (Double.compare(that.bestAskPrice, bestAskPrice) != 0) {
            return false;
        }
        if (Double.compare(that.bestAskVol, bestAskVol) != 0) {
            return false;
        }
        if (Double.compare(that.bestBidPrice, bestBidPrice) != 0) {
            return false;
        }
        if (Double.compare(that.bestBidVol, bestBidVol) != 0) {
            return false;
        }
        if (Double.compare(that.lastTradePrice, lastTradePrice) != 0) {
            return false;
        }
        if (Double.compare(that.max, max) != 0) {
            return false;
        }
        if (Double.compare(that.min, min) != 0) {
            return false;
        }
        if (Double.compare(that.previousClosed, previousClosed) != 0) {
            return false;
        }
        if (Double.compare(that.strikePrice, strikePrice) != 0) {
            return false;
        }
        if (exchangeCode != null ? !exchangeCode.equals(that.exchangeCode) : that.exchangeCode != null) {
            return false;
        }
        if (lastUpdatedTime != null ? !lastUpdatedTime.equals(that.lastUpdatedTime) : that.lastUpdatedTime != null) {
            return false;
        }
        return !(symbolCode != null ? !symbolCode.equals(that.symbolCode) : that.symbolCode != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = 0;
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (symbolCode != null ? symbolCode.hashCode() : 0);
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (exchangeCode != null ? exchangeCode.hashCode() : 0);
        temp = previousClosed != +0.0d ? Double.doubleToLongBits(previousClosed) : 0L;
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (int) (temp ^ (temp >>> HASH_CODE_COMPARATOR));
        temp = min != +0.0d ? Double.doubleToLongBits(min) : 0L;
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (int) (temp ^ (temp >>> HASH_CODE_COMPARATOR));
        temp = max != +0.0d ? Double.doubleToLongBits(max) : 0L;
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (int) (temp ^ (temp >>> HASH_CODE_COMPARATOR));
        temp = lastTradePrice != +0.0d ? Double.doubleToLongBits(lastTradePrice) : 0L;
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (int) (temp ^ (temp >>> HASH_CODE_COMPARATOR));
        temp = bestBidPrice != +0.0d ? Double.doubleToLongBits(bestBidPrice) : 0L;
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (int) (temp ^ (temp >>> HASH_CODE_COMPARATOR));
        temp = bestAskPrice != +0.0d ? Double.doubleToLongBits(bestAskPrice) : 0L;
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (int) (temp ^ (temp >>> HASH_CODE_COMPARATOR));
        temp = bestBidVol != +0.0d ? Double.doubleToLongBits(bestBidVol) : 0L;
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (int) (temp ^ (temp >>> HASH_CODE_COMPARATOR));
        temp = bestAskVol != +0.0d ? Double.doubleToLongBits(bestAskVol) : 0L;
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (int) (temp ^ (temp >>> HASH_CODE_COMPARATOR));
        temp = strikePrice != +0.0d ? Double.doubleToLongBits(strikePrice) : 0L;
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (int) (temp ^ (temp >>> HASH_CODE_COMPARATOR));
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (lastUpdatedTime != null ? lastUpdatedTime.hashCode() : 0);
        return result;
    }
}
