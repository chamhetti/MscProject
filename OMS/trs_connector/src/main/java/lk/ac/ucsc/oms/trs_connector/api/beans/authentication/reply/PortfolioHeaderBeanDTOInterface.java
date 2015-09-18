package lk.ac.ucsc.oms.trs_connector.api.beans.authentication.reply;

import java.util.List;

/**
 * User: kalanaa
 * Date: 1/21/13
 * Time: 3:31 PM
 */
public interface PortfolioHeaderBeanDTOInterface {
    /**
     * portfolio Id
     *
     * @return
     */
    String getPortflioID();

    /**
     * @param portflioID
     */
    void setPortflioID(String portflioID);

    /**
     * Broker Id for the Portfolio
     *
     * @return
     */
    String getBrokerID();

    /**
     * @param brokerID
     */
    void setBrokerID(String brokerID);


    /**
     * Portfolio Name
     *
     * @return
     */
    String getName();

    /**
     * @param name
     */
    void setName(String name);

    /**
     * Portfolio Is Default Or not
     *
     * @return
     */
    boolean getDefault();

    /**
     * @param aDefault
     */
    void setDefault(boolean aDefault);

    /**
     * Account Number For the portfolio
     *
     * @return
     */
    String getAccountNo();

    /**
     * Account Number For the portfolio
     *
     * @param accountNo
     */
    void setAccountNo(String accountNo);

    /**
     * Exchanges For the Portfolio
     *
     * @return
     */
    String getExchanges();

    /**
     * @param exchanges
     */
    void setExchanges(String exchanges);

    /**
     * MarginTrade Expiry Date
     *
     * @return
     */
    String getMarginTradeExpiryDate();

    /**
     * @param marginTradeExpiryDate
     */
    void setMarginTradeExpiryDate(String marginTradeExpiryDate);

    String getDayMarginTradeExpiryDate();

    /**
     * DayMarginTrade ExpiryDate(
     *
     * @param dayMarginTradeExpiryDate
     */
    void setDayMarginTradeExpiryDate(String dayMarginTradeExpiryDate);

    /**
     * Show Margin Expiry Notification
     *
     * @return
     */
    int getShowMarginExpNotification();

    /**
     * Show Margin Expiry Notification
     *
     * @param showMarginExpNotification
     */
    void setShowMarginExpNotification(int showMarginExpNotification);

    /**
     * @return
     */
    int getShowDayMarginExpNotification();

    /**
     * Show DayMargin Expiry notification
     *
     * @param showDayMarginExpNotification
     */
    void setShowDayMarginExpNotification(int showDayMarginExpNotification);

    /**
     * Margin Expiry In Days
     *
     * @return
     */
    int getMarginExpireInDays();

    /**
     * @param marginExpireInDays
     */
    void setMarginExpireInDays(int marginExpireInDays);

    /**
     * DayMargin Expire in Days
     *
     * @return
     */
    int getDayMarginExpireInDays();

    /**
     * @param dayMarginExpireInDays
     */
    void setDayMarginExpireInDays(int dayMarginExpireInDays);



    /**
     * @return
     */
    boolean isActivePortfolio();

    /**
     * @param activePortfolio
     */
    void setActivePortfolio(boolean activePortfolio);

    /**
     * @return
     */
    boolean isMarginEnabled();

    /**
     * @param marginEnabled
     */
    void setMarginEnabled(boolean marginEnabled);

    /**
     * @return
     */
    boolean isDayMarginEnabled();

    /**
     * @param dayMarginEnabled
     */
    void setDayMarginEnabled(boolean dayMarginEnabled);

    /**
     * @return
     */
    boolean isMarginTradingEnabled();

    /**
     * @param marginTradingEnabled
     */
    void setMarginTradingEnabled(boolean marginTradingEnabled);


}
