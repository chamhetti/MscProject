package lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.reply;

import java.util.Date;
import java.util.List;

/**
 * User: vimalanathanr
 * Date: 5/17/13
 * Time: 5:49 PM
 */
public interface CashAccountRecord {

    String getCashAccNumber();

    void setCashAccNumber(String cashAccNumber);

    int getAccountType();

    void setAccountType(int accountType);

    String getCurrency();

    void setCurrency(String currency);

    double getBalance();

    void setBalance(double balance);

    double getBlockAmt();

    void setBlockAmt(double blockAmt);

    double getOdLimit();

    void setOdLimit(double odLimit);

    double getDailyODLimit();

    void setDailyODLimit(double dailyODLimit);

    double getCashAvailableForWithdraw();

    void setCashAvailableForWithdraw(double cashAvailableForWithdraw);

    int getForexSettleType();

    void setForexSettleType(int forexSettleType);

    double getMarginDue();

    void setMarginDue(double marginDue);

    double getMarginBlock();

    void setMarginBlock(double marginBlock);

    double getNonMarginBlock();

    void setNonMarginBlock(double nonMarginBlock);

    double getDayMarginBlock();

    void setDayMarginBlock(double dayMarginBlock);

    double getDayMarginDue();

    void setDayMarginDue(double dayMarginDue);

    double getTotalMurabahMargin();

    void setTotalMurabahMargin(double totalMurabahMargin);

    String getCustomerNumber();

    void setCustomerNumber(String customerNumber);

    double getPendingDeposit();

    void setPendingDeposit(double pendingDeposit);

    Date getPrimaryOdExpireDate();

    void setPrimaryOdExpireDate(Date primaryOdExpireDate);

    Date getSecondaryOdExpireDate();

    void setSecondaryOdExpireDate(Date secondaryOdExpireDate);

    int getDailyOdEnable();

    void setDailyOdEnable(int dailyOdEnable);

    List<SecurityAccountRecord> getSecurityAccountRecords();

    void setSecurityAccountRecords(List<SecurityAccountRecord> securityAccountRecords);

    void addSecurityAccountRecord(SecurityAccountRecord securityAccountRecord);

    int getStatus();

    void setStatus(int status);

    double getSecondaryODLimit();

    void setSecondaryODLimit(double secondaryODLimit);

    double getPendingCharge();

    void setPendingCharge(double pendingCharge);

    double getPendingSettle();

    void setPendingSettle(double pendingSettle);

    double getTopUpAmount();

    void setTopUpAmount(double topUpAmount);

    double getBuyingPower();

    void setBuyingPower(double buyingPower);

    double getPrimaryODLimit();

    void setPrimaryODLimit(double primaryODLimit);

    double getShortSellCashBalance();

    void setShortSellCashBalance(double shortSellCashBalance);

    double getShortSellBlock();

    void setShortSellBlock(double shortSellBlock);

    double getShortSellCashBlock();

    void setShortSellCashBlock(double shortSellCashBlock);

    double getShortSellMarginBlock();

    void setShortSellMarginBlock(double shortSellMarginBlock);

    double getShortSellPendingBlock();

    void setShortSellPendingBlock(double shortSellPendingBlock);
}
