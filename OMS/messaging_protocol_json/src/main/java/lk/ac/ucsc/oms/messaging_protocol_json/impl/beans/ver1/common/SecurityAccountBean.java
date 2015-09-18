package lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.common;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import static lk.ac.ucsc.oms.messaging_protocol_json.api.FieldConstants.*;


public class SecurityAccountBean {

    @SerializedName(PORTFOLIO_NAME)
    private String portfolioName = null;

    @SerializedName(INVESTOR_ACCOUNT)
    private String investorAccount = null;

    @SerializedName(IS_MARGIN)
    private int isMargin = -1;

    @SerializedName(MARGIN_APPROVAL_STATUS)
    private String marginApprovalStatus;

    @SerializedName(PORTFOLIO_MARGIN)
    private double portfolioMargin = 0;

    @SerializedName(CURRENCY)
    private String currency = null;

    @SerializedName(SEC_ACC_APPROVAL_STATUS)
    private int secApprovalStatus = -1;

    @SerializedName(PORTFOLIO_MARGIN_BLOCK)
    private double portfolioMarginBlock = 0;

    @SerializedName(INITIAL_MARGIN)
    private double initialMargin = 0;

    @SerializedName(DAY_INITIAL_MARGIN)
    private double initialDayMargin = 0;

    @SerializedName(DAY_MARGIN_ENABLE_STATUS)
    private int dayMarginEnableStatus = -1;

    @SerializedName(MARGIN_LIQUIDATION_STATUS)
    private int marginLiquidationStatus = -1;

    @SerializedName(DAY_MARGIN_LIQUIDATION_STATUS)
    private int dayMarginLiquidationStatus = -1;

    @SerializedName(SECURITY_ACCOUNT_NUMBER)
    private String accountNo = null;

    @SerializedName(CASH_ACCOUNT_NUMBER)
    private String cashAccountID = null;

    @SerializedName(STATUS)
    private int accountStatus = -1;

    @SerializedName(ACCOUNT_TYPE)
    private int accountType = -1;

    @SerializedName(EXTERNAL_REFERENCE_NUMBER)
    private String externalReferenceNo = null;

    @SerializedName(STATUS_ID)
    private String statusID = null;

    @SerializedName(PORTFOLIO_ACCOUNT_TYPE)
    private String portfolioAccountType = null;

    @SerializedName(MARGIN_TRADING_MAX_DAY_MARGIN_AMOUNT)
    private double dayMarginMaxAmount = 0;

    @SerializedName(MARGIN_TRADING_MAX_MARGIN_AMOUNT)
    private double marginMaxAmount = 0;

    @SerializedName(RAPV_WITH_BUY_PENDING)
    private double marginablePVWithBuyPending = 0;

    @SerializedName(OVERNIGHT_MARGIN_ENABLE_STATUS)
    private int overnightMarginEnableStatus = -1;

    @SerializedName(MARGIN_TRADE_EXPIRY_DATE)
    private String marginTradeExpiryDate = null;

    @SerializedName(DAY_MARGIN_TRADE_EXPIRY_DATE)
    private String dayMarginTradeExpiryDate = null;

    @SerializedName(MAINTENANCE_CALL_LEVEL)
    private double maintenanceCallLevel = 0;

    @SerializedName(SELL_OUT_LEVEL)
    private double sellOutLevel = 0;

    @SerializedName(SEC_ACCOUNT_CLASSIFICATION)
    private int accountClassification = -1;

    @SerializedName(ACCOUNT_NET_WORTH)
    private double accountNetWorth = 0;

    @SerializedName(PENDING_ORDER_VALUE)
    private double pendingOrderValue = 0;

    @SerializedName(PORTFOLIO_VALUE)
    private double portfolioValue = 0;

    @SerializedName(TOP_UP_AMOUNT)
    private double topUpAmount = 0;

    @SerializedName(LIQUIDATION_AMOUNT)
    private double liquidationAmount = 0;

    @SerializedName(RISK_ADJUSTED_PORTFOLIO_VALUE)
    private double riskAdjustedPortfolioValue = 0;

    @SerializedName(RAPV_OF_PENDING_ORDERS)
    private double rAPVofPendingOrders = 0;

    @SerializedName(RAPV_DAY)
    private double rapvDay = 0;

    @SerializedName(OVER_NIGHT_MARGIN_BUYING_POWER)
    private double overnightBuyingPower = 0;

    @SerializedName(INTRA_DAY_MARGIN_BUYING_POWER)
    private double intradayBuyingPower = 0;

    @SerializedName(MARGIN_UTILIZED)
    private double totalUtilization = -1;

    @SerializedName(SHT_SELL_ENBL)
    private int shortSellEnabled;

    @SerializedName(SHT_SELL_LMT)
    private double shortSellLimit;

    @SerializedName(SHT_POS_MKT_VAL)
    private double shortSellMarketValue;

    @SerializedName(EXCHANGE_ACCOUNT_LIST)
    private List<ExchangeAccountBean> exchangeAccounts = new ArrayList<ExchangeAccountBean>();

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public String getInvestorAccount() {
        return investorAccount;
    }

    public void setInvestorAccount(String investorAccount) {
        this.investorAccount = investorAccount;
    }

    public int getMargin() {
        return isMargin;
    }

    public void setMargin(int margin) {
        isMargin = margin;
    }

    public String getMarginApprovalStatus() {
        return marginApprovalStatus;
    }

    public void setMarginApprovalStatus(String marginApprovalStatus) {
        this.marginApprovalStatus = marginApprovalStatus;
    }

    public double getPortfolioMargin() {
        return portfolioMargin;
    }

    public void setPortfolioMargin(double portfolioMargin) {
        this.portfolioMargin = portfolioMargin;
    }

    public double getPortfolioValue() {
        return portfolioValue;
    }

    public void setPortfolioValue(double portfolioValue) {
        this.portfolioValue = portfolioValue;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getSecApprovalStatus() {
        return secApprovalStatus;
    }

    public void setSecApprovalStatus(int secApprovalStatus) {
        this.secApprovalStatus = secApprovalStatus;
    }

    public double getPortfolioMarginBlock() {
        return portfolioMarginBlock;
    }

    public void setPortfolioMarginBlock(double portfolioMarginBlock) {
        this.portfolioMarginBlock = portfolioMarginBlock;
    }

    public double getInitialMargin() {
        return initialMargin;
    }

    public void setInitialMargin(double initialMargin) {
        this.initialMargin = initialMargin;
    }

    public double getInitialDayMargin() {
        return initialDayMargin;
    }

    public void setInitialDayMargin(double initialDayMargin) {
        this.initialDayMargin = initialDayMargin;
    }

    public double getRiskAdjustedPortfolioValue() {
        return riskAdjustedPortfolioValue;
    }

    public void setRiskAdjustedPortfolioValue(double riskAdjustedPortfolioValue) {
        this.riskAdjustedPortfolioValue = riskAdjustedPortfolioValue;
    }

    public double getrAPVofPendingOrders() {
        return rAPVofPendingOrders;
    }

    public void setrAPVofPendingOrders(double rAPVofPendingOrders) {
        this.rAPVofPendingOrders = rAPVofPendingOrders;
    }

    public double getLiquidationAmount() {
        return liquidationAmount;
    }

    public void setLiquidationAmount(double liquidationAmount) {
        this.liquidationAmount = liquidationAmount;
    }

    public int getDayMarginEnableStatus() {
        return dayMarginEnableStatus;
    }

    public void setDayMarginEnableStatus(int dayMarginEnableStatus) {
        this.dayMarginEnableStatus = dayMarginEnableStatus;
    }

    public int getMarginLiquidationStatus() {
        return marginLiquidationStatus;
    }

    public void setMarginLiquidationStatus(int marginLiquidationStatus) {
        this.marginLiquidationStatus = marginLiquidationStatus;
    }

    public int getDayMarginLiquidationStatus() {
        return dayMarginLiquidationStatus;
    }

    public void setDayMarginLiquidationStatus(int dayMarginLiquidationStatus) {
        this.dayMarginLiquidationStatus = dayMarginLiquidationStatus;
    }

    public List<ExchangeAccountBean> getExchangeAccounts() {
        return exchangeAccounts;
    }

    public void setExchangeAccounts(List<ExchangeAccountBean> exchangeAccounts) {
        this.exchangeAccounts = exchangeAccounts;
    }

    public void addExchangeAccount(ExchangeAccountBean exchangeAccount) {
        this.exchangeAccounts.add(exchangeAccount);
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getCashAccountID() {
        return cashAccountID;
    }

    public void setCashAccountID(String cashAccountID) {
        this.cashAccountID = cashAccountID;
    }

    public int getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(int accountStatus) {
        this.accountStatus = accountStatus;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public String getExternalReferenceNo() {
        return externalReferenceNo;
    }

    public void setExternalReferenceNo(String externalReferenceNo) {
        this.externalReferenceNo = externalReferenceNo;
    }

    public String getStatusID() {
        return statusID;
    }

    public void setStatusID(String statusID) {
        this.statusID = statusID;
    }

    public String getPortfolioAccountType() {
        return portfolioAccountType;
    }

    public void setPortfolioAccountType(String portfolioAccountType) {
        this.portfolioAccountType = portfolioAccountType;
    }

    public double getDayMarginMaxAmount() {
        return dayMarginMaxAmount;
    }

    public void setDayMarginMaxAmount(double dayMarginMaxAmount) {
        this.dayMarginMaxAmount = dayMarginMaxAmount;
    }

    public double getMarginMaxAmount() {
        return marginMaxAmount;
    }

    public void setMarginMaxAmount(double marginMaxAmount) {
        this.marginMaxAmount = marginMaxAmount;
    }

    public double getMarginablePVWithBuyPending() {
        return marginablePVWithBuyPending;
    }

    public void setMarginablePVWithBuyPending(double marginablePVWithBuyPending) {
        this.marginablePVWithBuyPending = marginablePVWithBuyPending;
    }

    public int getOvernightMarginEnableStatus() {
        return overnightMarginEnableStatus;
    }

    public void setOvernightMarginEnableStatus(int overnightMarginEnableStatus) {
        this.overnightMarginEnableStatus = overnightMarginEnableStatus;
    }

    public String getMarginTradeExpiryDate() {
        return marginTradeExpiryDate;
    }

    public void setMarginTradeExpiryDate(String marginTradeExpiryDate) {
        this.marginTradeExpiryDate = marginTradeExpiryDate;
    }

    public String getDayMarginTradeExpiryDate() {
        return dayMarginTradeExpiryDate;
    }

    public void setDayMarginTradeExpiryDate(String dayMarginTradeExpiryDate) {
        this.dayMarginTradeExpiryDate = dayMarginTradeExpiryDate;
    }

    public double getTopUpAmount() {
        return topUpAmount;
    }

    public void setTopUpAmount(double topUpAmount) {
        this.topUpAmount = topUpAmount;
    }

    public double getOvernightBuyingPower() {
        return overnightBuyingPower;
    }

    public void setOvernightBuyingPower(double overnightBuyingPower) {
        this.overnightBuyingPower = overnightBuyingPower;
    }

    public double getIntradayBuyingPower() {
        return intradayBuyingPower;
    }

    public void setIntradayBuyingPower(double intradayBuyingPower) {
        this.intradayBuyingPower = intradayBuyingPower;
    }

    public double getMaintenanceCallLevel() {
        return maintenanceCallLevel;
    }

    public void setMaintenanceCallLevel(double maintenanceCallLevel) {
        this.maintenanceCallLevel = maintenanceCallLevel;
    }

    public double getSellOutLevel() {
        return sellOutLevel;
    }

    public void setSellOutLevel(double sellOutLevel) {
        this.sellOutLevel = sellOutLevel;
    }

    public double getRapvDay() {
        return rapvDay;
    }

    public void setRapvDay(double rapvDay) {
        this.rapvDay = rapvDay;
    }

    public int getAccountClassification() {
        return accountClassification;
    }

    public void setAccountClassification(int accountClassification) {
        this.accountClassification = accountClassification;
    }

    public double getAccountNetWorth() {
        return accountNetWorth;
    }

    public void setAccountNetWorth(double accountNetWorth) {
        this.accountNetWorth = accountNetWorth;
    }

    public double getPendingOrderValue() {
        return pendingOrderValue;
    }

    public void setPendingOrderValue(double pendingOrderValue) {
        this.pendingOrderValue = pendingOrderValue;
    }

    public double getTotalUtilization() {
        return totalUtilization;
    }

    public void setTotalUtilization(double totalUtilization) {
        this.totalUtilization = totalUtilization;
    }

    public int getShortSellEnabled() {
        return shortSellEnabled;
    }

    public void setShortSellEnabled(int shortSellEnabled) {
        this.shortSellEnabled = shortSellEnabled;
    }

    public double getShortSellLimit() {
        return shortSellLimit;
    }

    public void setShortSellLimit(double shortSellLimit) {
        this.shortSellLimit = shortSellLimit;
    }

    public double getShortSellMarketValue() {
        return shortSellMarketValue;
    }

    public void setShortSellMarketValue(double shortSellMarketValue) {
        this.shortSellMarketValue = shortSellMarketValue;
    }
}
