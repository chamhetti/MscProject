package lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.reply;

import java.util.Date;

/**
 * User: Chathura
 * Date: Jan 31, 2013
 */
public interface AccountSummaryRecord {

    String getAccountID();

    void setAccountID(String accountID);

    int getAccountType();

    void setAccountType(int accountType);

    String getCurrency();

    void setCurrency(String currency);

    double getBalance();

    void setBalance(double balance);

    double getBlockAmount();

    void setBlockAmount(double blockAmount);

    double getOdLimit();

    void setOdLimit(double odLimit);

    double getBuyingPower();

    void setBuyingPower(double buyingPower);

    double getMarginLimit();

    void setMarginLimit(double marginLimit);

    double getUnrealizedSales();

    void setUnrealizedSales(double unrealizedSales);

    double getCashForWithdrawal();

    void setCashForWithdrawal(double cashForWithdrawal);

    double getNetSecurityValue();

    void setNetSecurityValue(double netSecurityValue);

    Date getLastAccountUpdatedTime();

    void setLastAccountUpdatedTime(Date lastAccountUpdatedTime);

    int getPortfolioType();

    void setPortfolioType(int portfolioType);

    int getStatus();

    void setStatus(int status);

    int getMarginTradingEnabled();

    void setMarginTradingEnabled(int marginTradingEnabled);

    double getDayCashMargin();

    void setDayCashMargin(double dayCashMargin);

    double getPendingDeposits();

    void setPendingDeposits(double pendingDeposits);

    double getPendingTransfers();

    void setPendingTransfers(double pendingTransfers);

    double getCashMargin();

    void setCashMargin(double cashMargin);

    double getMarginBlock();

    void setMarginBlock(double marginBlock);

    double getMarginDue();

    void setMarginDue(double marginDue);

    double getNonMarginableCashBlock();

    void setNonMarginableCashBlock(double nonMarginableCashBlock);

    String getCashAccountNumber();

    void setCashAccountNumber(String accountNo);

    String getPortfolioNumber();

    void setPortfolioNumber(String portfolioNumber);

    double getMarginMaxAmount();

    void setMarginMaxAmount(double marginMaxAmount);

    double getDayMarginMaxAmount();

    void setDayMarginMaxAmount(double dayMarginMaxAmount);

    double getDayMarginDue();

    void setDayMarginDue(double dayMarginDue);

    double getRiskAdjustedPortfolioValue();

    void setRiskAdjustedPortfolioValue(double riskAdjustedPortfolioValue);

    double getRapvDay();

    void setRapvDay(double rapvDay);

    double getLiquidationAmount();

    void setLiquidationAmount(double liquidationAmount);

    double getTopUpAmount();

    void setTopUpAmount(double topUpAmount);

    double getOverNightBuyingPower();

    void setOverNightBuyingPower(double overNightBuyingPower);

    double getIntraDayBuyingPower();

    void setIntraDayBuyingPower(double intraDayBuyingPower);

    double getPendingMarginFee();

    void setPendingMarginFee(double pendingMarginFee);

    Date getMarginExpiryDate();

    void setMarginExpiryDate(Date marginExpiryDate);

    Date getDayMarginExpiryDate();

    void setDayMarginExpiryDate(Date dayMarginExpiryDate);

    int getMarginType();

    void setMarginType(int marginType);

    double getTotalUtilization();

    void setTotalUtilization(double totalUtilization);

    double getPendingOrderValue();

    void setPendingOrderValue(double pendingOrderValue);

    double getMaintenanceCallLevel();

    void setMaintenanceCallLevel(double maintenanceCallLevel);

    double getSellOutLevel();

    void setSellOutLevel(double sellOutLevel);

    double getPendingSettle();

    void setPendingSettle(double pendingSettle);

    double getDayMarginBlock();

    void setDayMarginBlock(double dayMarginBlock);

    double getTotalMurabahAmount();

    void setTotalMurabahAmount(double totalMurabahAmount);

    double getShortSellCashBalance();

    void setShortSellCashBalance(double shortSellCashBalance);

    double getShortSellBlock();

    void setShortSellBlock(double shortSellBlock);

    double getShortSellCashBlock();

    void setShortSellCashBlock(double shortSellCashBlock);

    double getShortSellMarginBlock();

    void setShortSellMarginBlock(double shortSellMarginBlock);

    double getShortSellMarketValue();

    void setShortSellMarketValue(double shortSellMarketValue);

    double getShortSellPendingBlock();

    void setShortSellPendingBlock(double shortSellPendingBlock);

    double getShortSellLimit();

    void setShortSellLimit(double shortSellLimit);
}
