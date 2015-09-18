package lk.ac.ucsc.oms.trs_connector.api.beans.authentication.reply;

/**
 * User: Hetti
 * Date: 7/16/13
 * Time: 5:37 PM
 */
public interface TradingAccount {
    String getExchange();

    void setExchange(String exchange);

    String getTradingAcctNo();

    void setTradingAcctNo(String tradingAcctNo);

    boolean isIstradingEnabled();

    void setIstradingEnabled(boolean istradingEnabled);

    String getTrdDisenablingReason();

    void setTrdDisenablingReason(String trdDisenablingReason);

    boolean isIsexchangeTradingEnabled();

    void setIsexchangeTradingEnabled(boolean isexchangeTradingEnabled);
}
