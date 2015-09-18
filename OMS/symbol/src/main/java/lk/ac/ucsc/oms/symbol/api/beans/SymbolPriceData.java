package lk.ac.ucsc.oms.symbol.api.beans;

import java.util.Date;

/**
 * This is the data transfer interface used to give symbol price related data.
 * This has the setter and getter to access each field.
 * <p/>
 * User: Hetti
 * Date: 11/19/12
 * Time: 2:58 PM
 */
public interface SymbolPriceData {


    /**
     * get last updated time
     *
     * @return Date last updated tim
     */
    Date getLastUpdatedTime();

    /**
     * set last updated time
     *
     * @param lastUpdatedTime Date of last updated
     */
    void setLastUpdatedTime(Date lastUpdatedTime);

    /**
     * get min value
     *
     * @return double min value
     */
    double getMin();

    /**
     * set min value
     *
     * @param min double min value
     */
    void setMin(double min);

    /**
     * get max value
     *
     * @return double max value
     */
    double getMax();

    /**
     * set max value
     *
     * @param max double max value
     */
    void setMax(double max);

    /**
     * get last trade price
     *
     * @return double last trade price
     */
    double getLastTradePrice();

    /**
     * set last trade price
     *
     * @param lastTradePrice double last trade price
     */
    void setLastTradePrice(double lastTradePrice);

    /**
     * get best bid price
     *
     * @return double best bid price
     */
    double getBestBidPrice();

    /**
     * set best bid price
     *
     * @param lastBidPrice double best bid price
     */
    void setBestBidPrice(double lastBidPrice);

    /**
     * get best ask price
     *
     * @return double best ask price
     */
    double getBestAskPrice();

    /**
     * set best ask price
     *
     * @param lastAskPrice double best ask price
     */
    void setBestAskPrice(double lastAskPrice);

    /**
     * get best bid volume
     *
     * @return double best bid volume
     */
    double getBestBidVol();

    /**
     * set best bid volume
     *
     * @param lastBidVol set best bid volume
     */
    void setBestBidVol(double lastBidVol);

    /**
     * get best ask volume
     *
     * @return double best ask volume
     */
    double getBestAskVol();

    /**
     * set best ask volume
     *
     * @param lastAskVol double besk ask volume
     */
    void setBestAskVol(double lastAskVol);

    /**
     * get previous closed price
     *
     * @return get previous closed price
     */
    double getPreviousClosed();

    /**
     * set previous closed price
     *
     * @param previousClosed double previous closed price
     */
    void setPreviousClosed(double previousClosed);

    /**
     * strike price
     *
     * @return double
     */
    double getStrikePrice();

    /**
     * set strike
     *
     * @param strikePrice double
     */
    void setStrikePrice(double strikePrice);

    /**
     * Get the exchange code
     *
     * @return
     */
    String getExchangeCode();

    /**
     * Set the exchange code
     *
     * @param exchangeCode
     */
    void setExchangeCode(String exchangeCode);

    /**
     * Get the symbol code
     *
     * @return
     */
    String getSymbolCode();

    /**
     * Set the symbol code
     *
     * @param symbolCode
     */
    void setSymbolCode(String symbolCode);

    /**
     * Get the loaded to cache status
     *
     * @return
     */
    int getLoadedToCache();

    /**
     * Set loaded to cache status
     *
     * @param loadedToCache
     */
    void setLoadedToCache(int loadedToCache);

    /**
     * Get last bid price
     *
     * @return the last bid price
     */
    double getLastBidPrice();

    /**
     * Set the last bid price
     *
     * @param lastBidPrice is the last bid price
     */
    void setLastBidPrice(double lastBidPrice);

    /**
     * get the option type
     *
     * @return
     */
    int getOptionType();

    /**
     * set the option type
     *
     * @param optionType
     */
    void setOptionType(int optionType);
}
