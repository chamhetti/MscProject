package lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.common;

import com.google.gson.annotations.SerializedName;

import static lk.ac.ucsc.oms.messaging_protocol_json.api.FieldConstants.*;

/**
 * User: hetti
 */
public class BuyingPowerBean {

    @SerializedName(SECURITY_ACCOUNT_NUMBER)
    private String portfolioNumber = null;

    @SerializedName(ACCOUNT_TYPE)
    private int accountType = -1;

    @SerializedName(CURRENCY)
    private String currency = null;

    @SerializedName(BALANCE)
    private double balance;

    @SerializedName(BLOCKED_AMOUNT)
    private double blockedAmount;

    @SerializedName(OD_LIMIT)
    private double oDLimit;

    @SerializedName(BUYING_POWER)
    private double buyingPower;

    @SerializedName(MARGIN_LIMIT)
    private double marginLimit;

    @SerializedName(DATE)
    private String date = null;

    @SerializedName(UNREALIZED_SALES)
    private double unrealizedSales;

    @SerializedName(CASH_FOR_WITHDRAWAL)
    private double cashForWithdrawal;

    @SerializedName(NET_SECURITY_VALUE)
    private double netSecurityValue;

    @SerializedName(LAST_ACCOUNT_UPDATE_TIME)
    private String lastAccountUpdatedTime = null;

    @SerializedName(DAY_CASH_MARGIN)
    private double dayCashMargin;

    @SerializedName(PENDING_DEPOSITS)
    private double pendingDeposits;

    @SerializedName(CASH_MARGIN)
    private double cashMargin;

    @SerializedName(MARGIN_BLOCK)
    private double marginBlock;

    @SerializedName(MARGIN_DUE)
    private double marginDue;
    @SerializedName(CASH_ACCOUNT_NUMBER)
    private String cashAccountNumber;
    @SerializedName(PORTFOLIO_VALUE)
    private double portfolioValuation;

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBlockedAmount() {
        return blockedAmount;
    }

    public void setBlockedAmount(double blockedAmount) {
        this.blockedAmount = blockedAmount;
    }

    public double getODLimit() {
        return oDLimit;
    }

    public void setODLimit(double oDLimit) {
        this.oDLimit = oDLimit;
    }

    public double getBuyingPower() {
        return buyingPower;
    }

    public void setBuyingPower(double buyingPower) {
        this.buyingPower = buyingPower;
    }

    public double getMarginLimit() {
        return marginLimit;
    }

    public void setMarginLimit(double marginLimit) {
        this.marginLimit = marginLimit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getUnrealizedSales() {
        return unrealizedSales;
    }

    public void setUnrealizedSales(double unrealizedSales) {
        this.unrealizedSales = unrealizedSales;
    }

    public double getCashForWithdrawal() {
        return cashForWithdrawal;
    }

    public void setCashForWithdrawal(double cashForWithdrawal) {
        this.cashForWithdrawal = cashForWithdrawal;
    }

    public double getNetSecurityValue() {
        return netSecurityValue;
    }

    public void setNetSecurityValue(double netSecurityValue) {
        this.netSecurityValue = netSecurityValue;
    }

    public String getLastAccountUpdatedTime() {
        return lastAccountUpdatedTime;
    }

    public void setLastAccountUpdatedTime(String lastAccountUpdatedTime) {
        this.lastAccountUpdatedTime = lastAccountUpdatedTime;
    }


    public double getDayCashMargin() {
        return dayCashMargin;
    }

    public void setDayCashMargin(double dayCashMargin) {
        this.dayCashMargin = dayCashMargin;
    }

    public double getPendingDeposits() {
        return pendingDeposits;
    }

    public void setPendingDeposits(double pendingDeposits) {
        this.pendingDeposits = pendingDeposits;
    }


    public double getCashMargin() {
        return cashMargin;
    }

    public void setCashMargin(double cashMargin) {
        this.cashMargin = cashMargin;
    }


    public double getMarginBlock() {
        return marginBlock;
    }

    public void setMarginBlock(double marginBlock) {
        this.marginBlock = marginBlock;
    }

    public double getMarginDue() {
        return marginDue;
    }

    public void setMarginDue(double marginDue) {
        this.marginDue = marginDue;
    }


    public String getCashAccountNumber() {

        return cashAccountNumber;
    }

    public void setCashAccountNumber(String cashAccountNumber) {
        this.cashAccountNumber = cashAccountNumber;
    }


    public double getPortfolioValuation() {
        return portfolioValuation;
    }

    public void setPortfolioValuation(double portfolioValuation) {
        this.portfolioValuation = portfolioValuation;
    }

    public String getPortfolioNumber() {
        return portfolioNumber;
    }

    public void setPortfolioNumber(String portfolioNumber) {
        this.portfolioNumber = portfolioNumber;
    }


}
