package lk.ac.ucsc.oms.trs_connector.api.beans.authentication.reply;

import java.util.Date;
import java.util.List;

/**
 * User: Hetti
 * Date: 7/16/13
 * Time: 5:37 PM
 */
public interface PortfolioHeader {
    String getPortflioID();

    void setPortflioID(String portflioID);

    String getBrokerID();

    void setBrokerID(String brokerID);

    boolean isActivePortfolio();

    void setActivePortfolio(boolean activePortfolio);

    String getName();

    void setName(String name);

    boolean isDefault();

    void setDefault(boolean aDefault);

    String getAccountNo();

    void setAccountNo(String accountNo);

    String getExchanges();

    void setExchanges(String exchanges);

    String getBookkeeper();

    void setBookkeeper(String bookkeeper);

    String getTradingAccount();

    void setTradingAccount(String tradingAccount);

    String getInvestorAccNumber();

    void setInvestorAccNumber(String investorAccNumber);

    Date getMarginTradeExpiryDate();

    void setMarginTradeExpiryDate(Date marginTradeExpiryDate);

    Date getDayMarginTradeExpiryDate();

    void setDayMarginTradeExpiryDate(Date dayMarginTradeExpiryDate);

    int getShowMarginExpNotification();

    void setShowMarginExpNotification(int showMarginExpNotification);

    int getShowDayMarginExpNotification();

    void setShowDayMarginExpNotification(int showDayMarginExpNotification);

    int getMarginExpireInDays();

    void setMarginExpireInDays(int marginExpireInDays);

    int getDayMarginExpireInDays();

    void setDayMarginExpireInDays(int dayMarginExpireInDays);

    boolean isMarginEnabled();

    void setMarginEnabled(boolean marginEnabled);

    boolean isDayMarginEnabled();

    void setDayMarginEnabled(boolean dayMarginEnabled);

    boolean isMarginTradingEnabled();

    void setMarginTradingEnabled(boolean marginTradingEnabled);

    double getMaxMarginAmount();

    void setMaxMarginAmount(double maxMarginAmount);

    String getPreferedCostMethode();

    void setPreferedCostMethode(String preferedCostMethode);

    boolean isShortSellEnabled();

    void setShortSellEnabled(boolean shortSellEnabled);


    List<TradingAccount> getTradingAccountList();

    void setTradingAccountList(List<TradingAccount> tradingAccountList);

    String getCashAccountCurrency();

    void setCashAccountCurrency(String cashAccountCurrency);
}
