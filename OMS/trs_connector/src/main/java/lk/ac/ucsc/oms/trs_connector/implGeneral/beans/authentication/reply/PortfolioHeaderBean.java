package lk.ac.ucsc.oms.trs_connector.implGeneral.beans.authentication.reply;


import lk.ac.ucsc.oms.trs_connector.api.beans.authentication.reply.PortfolioHeader;
import lk.ac.ucsc.oms.trs_connector.api.beans.authentication.reply.TradingAccount;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PortfolioHeaderBean implements PortfolioHeader {
    private String portflioID;
    private String brokerID;
    private boolean isActivePortfolio = false;
    private String name;
    private boolean isDefault = false;
    private String accountNo;
    private String cashAccountCurrency;
    private String exchanges;
    private String bookkeeper;
    private String tradingAccount;
    private String investorAccNumber;
    private Date marginTradeExpiryDate = null;
    private Date dayMarginTradeExpiryDate = null;
    private int showMarginExpNotification = 0;
    private int showDayMarginExpNotification = 0;
    private int marginExpireInDays = 0;
    private int dayMarginExpireInDays = 0;
    private boolean marginEnabled = false;
    private boolean dayMarginEnabled = false;
    private boolean marginTradingEnabled = false;
    private boolean shortSellEnabled = false;
    private double maxMarginAmount;
    private String preferedCostMethode;
    private List<TradingAccount> tradingAccountList = new ArrayList<>();

    @Override
    public String getPortflioID() {
        return portflioID;
    }

    @Override
    public void setPortflioID(String portflioID) {
        this.portflioID = portflioID;
    }

    @Override
    public String getBrokerID() {
        return brokerID;
    }

    @Override
    public void setBrokerID(String brokerID) {
        this.brokerID = brokerID;
    }

    @Override
    public boolean isActivePortfolio() {
        return isActivePortfolio;
    }

    @Override
    public void setActivePortfolio(boolean activePortfolio) {
        isActivePortfolio = activePortfolio;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isDefault() {
        return isDefault;
    }

    @Override
    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    @Override
    public String getAccountNo() {
        return accountNo;
    }

    @Override
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    @Override
    public String getExchanges() {
        return exchanges;
    }

    @Override
    public void setExchanges(String exchanges) {
        this.exchanges = exchanges;
    }

    @Override
    public String getBookkeeper() {
        return bookkeeper;
    }

    @Override
    public void setBookkeeper(String bookkeeper) {
        this.bookkeeper = bookkeeper;
    }

    @Override
    public String getTradingAccount() {
        return tradingAccount;
    }

    @Override
    public void setTradingAccount(String tradingAccount) {
        this.tradingAccount = tradingAccount;
    }

    @Override
    public String getInvestorAccNumber() {
        return investorAccNumber;
    }

    @Override
    public void setInvestorAccNumber(String investorAccNumber) {
        this.investorAccNumber = investorAccNumber;
    }

    @Override
    public Date getMarginTradeExpiryDate() {
        return marginTradeExpiryDate;
    }

    @Override
    public void setMarginTradeExpiryDate(Date marginTradeExpiryDate) {
        this.marginTradeExpiryDate = marginTradeExpiryDate;
    }

    @Override
    public Date getDayMarginTradeExpiryDate() {
        return dayMarginTradeExpiryDate;
    }

    @Override
    public void setDayMarginTradeExpiryDate(Date dayMarginTradeExpiryDate) {
        this.dayMarginTradeExpiryDate = dayMarginTradeExpiryDate;
    }

    @Override
    public int getShowMarginExpNotification() {
        return showMarginExpNotification;
    }

    @Override
    public void setShowMarginExpNotification(int showMarginExpNotification) {
        this.showMarginExpNotification = showMarginExpNotification;
    }

    @Override
    public int getShowDayMarginExpNotification() {
        return showDayMarginExpNotification;
    }

    @Override
    public void setShowDayMarginExpNotification(int showDayMarginExpNotification) {
        this.showDayMarginExpNotification = showDayMarginExpNotification;
    }

    @Override
    public int getMarginExpireInDays() {
        return marginExpireInDays;
    }

    @Override
    public void setMarginExpireInDays(int marginExpireInDays) {
        this.marginExpireInDays = marginExpireInDays;
    }

    @Override
    public int getDayMarginExpireInDays() {
        return dayMarginExpireInDays;
    }

    @Override
    public void setDayMarginExpireInDays(int dayMarginExpireInDays) {
        this.dayMarginExpireInDays = dayMarginExpireInDays;
    }

    @Override
    public boolean isMarginEnabled() {
        return marginEnabled;
    }

    @Override
    public void setMarginEnabled(boolean marginEnabled) {
        this.marginEnabled = marginEnabled;
    }

    @Override
    public boolean isDayMarginEnabled() {
        return dayMarginEnabled;
    }

    @Override
    public void setDayMarginEnabled(boolean dayMarginEnabled) {
        this.dayMarginEnabled = dayMarginEnabled;
    }

    @Override
    public boolean isMarginTradingEnabled() {
        return marginTradingEnabled;
    }

    @Override
    public void setMarginTradingEnabled(boolean marginTradingEnabled) {
        this.marginTradingEnabled = marginTradingEnabled;
    }

    @Override
    public double getMaxMarginAmount() {
        return maxMarginAmount;
    }

    @Override
    public void setMaxMarginAmount(double maxMarginAmount) {
        this.maxMarginAmount = maxMarginAmount;
    }

    @Override
    public String getPreferedCostMethode() {
        return preferedCostMethode;
    }

    @Override
    public void setPreferedCostMethode(String preferedCostMethode) {
        this.preferedCostMethode = preferedCostMethode;
    }

    @Override
    public boolean isShortSellEnabled() {
        return shortSellEnabled;
    }

    @Override
    public void setShortSellEnabled(boolean shortSellEnabled) {
        this.shortSellEnabled = shortSellEnabled;
    }


    @Override
    public List<TradingAccount> getTradingAccountList() {
        return tradingAccountList;
    }

    @Override
    public void setTradingAccountList(List<TradingAccount> tradingAccountList) {
        this.tradingAccountList = tradingAccountList;
    }

    public String getCashAccountCurrency() {
        return cashAccountCurrency;
    }

    public void setCashAccountCurrency(String cashAccountCurrency) {
        this.cashAccountCurrency = cashAccountCurrency;
    }

    @Override
    public String toString() {
        return "PortfolioHeaderBean{" +
                "portflioID='" + portflioID + '\'' +
                ", brokerID='" + brokerID + '\'' +
                ", isActivePortfolio=" + isActivePortfolio +
                ", name='" + name + '\'' +
                ", isDefault=" + isDefault +
                ", accountNo='" + accountNo + '\'' +
                ", exchanges='" + exchanges + '\'' +
                ", bookkeeper='" + bookkeeper + '\'' +
                ", tradingAccount='" + tradingAccount + '\'' +
                ", investorAccNumber='" + investorAccNumber + '\'' +
                ", marginTradeExpiryDate='" + marginTradeExpiryDate + '\'' +
                ", dayMarginTradeExpiryDate='" + dayMarginTradeExpiryDate + '\'' +
                ", showMarginExpNotification=" + showMarginExpNotification +
                ", showDayMarginExpNotification=" + showDayMarginExpNotification +
                ", marginExpireInDays=" + marginExpireInDays +
                ", dayMarginExpireInDays=" + dayMarginExpireInDays +
                ", marginEnabled=" + marginEnabled +
                ", dayMarginEnabled=" + dayMarginEnabled +
                ", marginTradingEnabled=" + marginTradingEnabled +
                ", maxMarginAmount=" + maxMarginAmount +
                ", preferedCostMethode='" + preferedCostMethode + '\'' +
                ", tradingAccountList=" + tradingAccountList +
                '}';
    }
}
