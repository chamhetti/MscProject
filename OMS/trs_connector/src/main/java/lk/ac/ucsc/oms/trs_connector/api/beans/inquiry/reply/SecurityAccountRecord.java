package lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.reply;

import java.util.Date;
import java.util.List;

/**
 * User: vimalanathanr
 * Date: 5/16/13
 * Time: 12:25 PM
 */
public interface SecurityAccountRecord {

    String getAccountNumber();

    void setAccountNumber(String accountNumber);

    String getAccountName();

    void setAccountName(String accountName);

    String getParentAccNumber();

    void setParentAccNumber(String parentAccNumber);

    String getCustomerNumber();

    void setCustomerNumber(String customerNumber);

    String getCashAccNumber();

    void setCashAccNumber(String cashAccNumber);

    String getCurrency();

    void setCurrency(String currency);

    double getPortfolioValue();

    void setPortfolioValue(double portfolioValue);

    int getAllowDayOrder();

    void setAllowDayOrder(int allowDayOrder);

    int getAllowConditionalOrder();

    void setAllowConditionalOrder(int allowConditionalOrder);

    int getAllowBracketOrder();

    void setAllowBracketOrder(int allowBracketOrder);

    int getAllowAlgoOrder();

    void setAllowAlgoOrder(int allowAlgoOrder);

    int getShariaComplient();

    void setShariaComplient(int shariaComplient);

    String getRestrictedSymbol();

    void setRestrictedSymbol(String restrictedSymbol);

    int getOrverNightMarginEnable();

    void setOrverNightMarginEnable(int orverNightMarginEnable);

    int getDayMarginEnable();

    void setDayMarginEnable(int dayMarginEnable);

    double getMarginFactorOverNight();

    void setMarginFactorOverNight(double marginFactorOverNight);

    double getMarginFactorDay();

    void setMarginFactorDay(double marginFactorDay);

    double getMaxMarginOverNight();

    void setMaxMarginOverNight(double maxMarginOverNight);

    double getMaxMarginDay();

    void setMaxMarginDay(double maxMarginDay);

    int getMarginType();

    void setMarginType(int marginType);

    Date getMarginExpiryDateOverNight();

    void setMarginExpiryDateOverNight(Date marginExpiryDateOverNight);

    Date getMarginExpiryDateDay();

    void setMarginExpiryDateDay(Date marginExpiryDateDay);

    int getApplyOverNightExpDate();

    void setApplyOverNightExpDate(int applyOverNightExpDate);

    int getApplyDayExpDate();

    void setApplyDayExpDate(int applyDayExpDate);

    int getLiquidationAllow();

    void setLiquidationAllow(int liquidationAllow);

    List<ExchangeAccountRecord> getExchangeAccountRecords();

    void setExchangeAccountRecords(List<ExchangeAccountRecord> exchangeAccountRecords);

    void addExchangeAccountRecord(ExchangeAccountRecord exchangeAccountRecord);

    double getTopUpAmount();

    void setTopUpAmount(double topUpAmount);

    double getLiquidationAmount();

    void setLiquidationAmount(double liquidationAmount);

    double getRapv();

    void setRapv(double rapv);

    double getRapvOfPendingOrders();

    void setRapvOfPendingOrders(double rapvOfPendingOrders);

    double getRepvDay();

    void setRepvDay(double repvDay);

    double getOverNightBuyingPower();

    void setOverNightBuyingPower(double overNightBuyingPower);

    double getIntraDayBuyingPower();

    void setIntraDayBuyingPower(double intraDayBuyingPower);

    double getPendingOrderValue();

    void setPendingOrderValue(double pendingOrderValue);

    int getStatus();

    void setStatus(int status);

    int getMarginLiquidationStatus();

    void setMarginLiquidationStatus(int marginLiquidationStatus);

    int getDayMarginLiquidationStatus();

    void setDayMarginLiquidationStatus(int dayMarginLiquidationStatus);

    double getMarginUtilization();

    void setMarginUtilization(double marginUtilization);

    double getAccountNetWorth();

    void setAccountNetWorth(double accountNetWorth);

    double getMaintenanceCallLevel();

    void setMaintenanceCallLevel(double maintenanceCallLevel);

    double getSellOutLevel();

    void setSellOutLevel(double sellOutLevel);

    int getShortSellEnabled();

    void setShortSellEnabled(int shortSellEnabled);

    double getShortSellLimit();

    void setShortSellLimit(double shortSellLimit);

    double getShortSellMarketValue();

    void setShortSellMarketValue(double shortSellMarketValue);
}
