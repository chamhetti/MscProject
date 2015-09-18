package lk.ac.ucsc.oms.customer.api.beans.cash;



import lk.ac.ucsc.oms.common_utility.api.enums.PropertyEnable;
import lk.ac.ucsc.oms.common_utility.api.enums.RecordStatus;

import java.util.Date;


public interface CashAccount {

    long getCashAccId();

    void setCashAccId(long cashAccId);

    String getCashAccNumber();

    void setCashAccNumber(String cashAccNumber);

    String getCustomerNumber();

    void setCustomerNumber(String customerNumber);

    RecordStatus getStatus();

    void setStatus(RecordStatus status);

    String getCurrency();

    void setCurrency(String currency);

    double getOdLimit();

    void setOdLimit(double odLimit);

    double getBlockAmt();

    void setBlockAmt(double blockAmt);

    double getBalance();

    void setBalance(double balance);

    double getMarginBlock();

    void setMarginBlock(double marginBlock);

    double getMarginDue();

    void setMarginDue(double marginDue);

    double getDailyODLimit();

    void setDailyODLimit(double dailyODLimit);

    double getSecondaryODLimit();

    void setSecondaryODLimit(double secondaryODLimit);

    PropertyEnable getDailyOdEnable();

    void setDailyOdEnable(PropertyEnable dailyOdEnable);

    double getPendingDeposit();

    void setPendingDeposit(double pendingDeposit);

    double getPendingSettle();

    void setPendingSettle(double pendingSettle);

    Date getPrimaryOdExpireDate();

    void setPrimaryOdExpireDate(Date primaryOdExpireDate);

    Date getSecondaryOdExpireDate();

    void setSecondaryOdExpireDate(Date secondaryOdExpireDate);


}
