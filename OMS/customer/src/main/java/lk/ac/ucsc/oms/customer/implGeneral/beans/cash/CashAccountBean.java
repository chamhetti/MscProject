package lk.ac.ucsc.oms.customer.implGeneral.beans.cash;

import lk.ac.ucsc.oms.cache.api.beans.CacheObject;

import lk.ac.ucsc.oms.common_utility.api.enums.PropertyEnable;
import lk.ac.ucsc.oms.common_utility.api.enums.RecordStatus;
import lk.ac.ucsc.oms.customer.api.beans.cash.CashAccount;
import org.hibernate.search.annotations.Field;

import javax.persistence.Id;
import java.util.Date;


public class CashAccountBean extends CacheObject implements CashAccount {

    @Id
    @Field
    private long cashAccId;
    @Field
    private String cashAccNumber;
    @Field
    private String currency;
    @Field
    private double balance;
    @Field
    private double blockAmt;
    @Field
    private double odLimit;
    @Field
    private RecordStatus status = RecordStatus.APPROVED;     //Make the default status as approved
    @Field
    private double secondaryODLimit;
    @Field
    private double dailyODLimit;
    @Field
    private double marginDue;
    @Field
    private double marginBlock;
    @Field
    private String customerNumber;
    @Field
    private double pendingDeposit;
    @Field
    private double pendingSettle;
    @Field
    private Date primaryOdExpireDate;
    @Field
    private Date secondaryOdExpireDate;
    @Field
    private PropertyEnable dailyOdEnable;

    protected CashAccountBean() {
    }


    public CashAccountBean(String cashAccNumber) {
        this.cashAccNumber = cashAccNumber;
    }

    @Override
    public String getCustomerNumber() {
        return customerNumber;
    }

    @Override
    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    @Override
    public double getMarginBlock() {
        return marginBlock;
    }

    @Override
    public void setMarginBlock(double marginBlock) {
        this.marginBlock = marginBlock;
    }

    @Override
    public double getMarginDue() {
        return marginDue;
    }

    @Override
    public void setMarginDue(double marginDue) {
        this.marginDue = marginDue;
    }

    @Override
    public double getDailyODLimit() {
        return dailyODLimit;
    }

    @Override
    public void setDailyODLimit(double dailyODLimit) {
        this.dailyODLimit = dailyODLimit;
    }

    @Override
    public double getSecondaryODLimit() {
        return secondaryODLimit;
    }

    @Override
    public void setSecondaryODLimit(double secondaryODLimit) {
        this.secondaryODLimit = secondaryODLimit;
    }

    @Override
    public RecordStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(RecordStatus status) {
        this.status = status;
    }

    @Override
    public double getOdLimit() {
        return odLimit;
    }

    @Override
    public void setOdLimit(double odLimit) {
        this.odLimit = odLimit;
    }

    @Override
    public double getBlockAmt() {
        return blockAmt;
    }

    @Override
    public void setBlockAmt(double blockAmt) {
        this.blockAmt = blockAmt;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String getCurrency() {
        return currency;
    }

    @Override
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String getCashAccNumber() {
        return cashAccNumber;
    }

    @Override
    public void setCashAccNumber(String cashAccNumber) {
        this.cashAccNumber = cashAccNumber;
    }

    @Override
    public long getCashAccId() {
        return cashAccId;
    }

    @Override
    public void setCashAccId(long cashAccId) {
        this.cashAccId = cashAccId;
    }

    @Override
    public PropertyEnable getDailyOdEnable() {
        return dailyOdEnable;
    }

    @Override
    public void setDailyOdEnable(PropertyEnable dailyOdEnable) {
        this.dailyOdEnable = dailyOdEnable;
    }

    @Override
    public double getPendingDeposit() {
        return pendingDeposit;
    }

    @Override
    public void setPendingDeposit(double pendingDeposit) {
        this.pendingDeposit = pendingDeposit;
    }


    @Override
    public double getPendingSettle() {
        return pendingSettle;
    }

    @Override
    public void setPendingSettle(double pendingSettle) {
        this.pendingSettle = pendingSettle;
    }

    @Override
    public Date getPrimaryOdExpireDate() {
        return primaryOdExpireDate;
    }

    @Override
    public void setPrimaryOdExpireDate(Date primaryOdExpireDate) {
        this.primaryOdExpireDate = primaryOdExpireDate;
    }

    @Override
    public Date getSecondaryOdExpireDate() {
        return secondaryOdExpireDate;
    }

    @Override
    public void setSecondaryOdExpireDate(Date secondaryOdExpireDate) {
        this.secondaryOdExpireDate = secondaryOdExpireDate;
    }
}
