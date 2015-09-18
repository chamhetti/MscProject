package lk.ac.ucsc.oms.customer.implGeneral.beans.holding;

import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.customer.api.beans.holding.HoldingLog;
import org.hibernate.search.annotations.Field;

import javax.persistence.Id;
import java.util.Date;


public class HoldingLogBean extends CacheObject implements HoldingLog {
    private static final int HASH_CODE_GENERATE_MULTIPLIER = 31;
    private static final int HASH_CODE_COMPARATOR = 32;
    @Id
    @Field
    private long holdingLogId;
    @Field
    private int status;
    @Field
    private String accountNumber;
    @Field
    private String exchange;
    @Field
    private String symbol;
    @Field
    private double avgCost;
    @Field
    private String clOrderId;
    @Field
    private int institutionId;
    @Field
    private double ordCumHolding;
    @Field
    private double netHolding;
    @Field
    private int side;
    @Field
    private String narration;
    @Field
    private Date transactionTime;
    @Field
    private double netSettle;
    @Field
    private double price;
    @Field
    private String customerNumber;
    @Field
    private double lastShares;
    @Field
    private double totalHoldings;
    @Field
    private long backOfficeId;
    @Field
    private String institutionCode;
    @Field
    private String instrumentType;
    @Field
    private double commissionDifference;


    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public long getHoldingLogId() {
        return holdingLogId;
    }

    @Override
    public void setHoldingLogId(long holdingLogId) {
        this.holdingLogId = holdingLogId;
    }

    @Override
    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String getExchange() {
        return exchange;
    }

    @Override
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public double getAvgCost() {
        return avgCost;
    }

    @Override
    public void setAvgCost(double avgCost) {
        this.avgCost = avgCost;
    }

    @Override
    public String getClOrderId() {
        return clOrderId;
    }

    @Override
    public void setClOrderId(String clOrderId) {
        this.clOrderId = clOrderId;
    }

    @Override
    public int getInstitutionId() {
        return institutionId;
    }


    @Override
    public void setInstitutionId(int institutionId) {
        this.institutionId = institutionId;
    }

    @Override
    public double getOrdCumHolding() {
        return ordCumHolding;
    }

    @Override
    public void setOrdCumHolding(double ordCumHolding) {
        this.ordCumHolding = ordCumHolding;
    }

    @Override
    public double getNetHolding() {
        return netHolding;
    }

    @Override
    public void setNetHolding(double netHolding) {
        this.netHolding = netHolding;
    }

    @Override
    public int getSide() {
        return side;
    }

    @Override
    public void setSide(int side) {
        this.side = side;
    }

    @Override
    public String getNarration() {
        return narration;
    }

    @Override
    public void setNarration(String narration) {
        this.narration = narration;
    }

    @Override
    public Date getTransactionTime() {
        return transactionTime;
    }

    @Override
    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    @Override
    public double getNetSettle() {
        return netSettle;
    }

    @Override
    public void setNetSettle(double netSettle) {
        this.netSettle = netSettle;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
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
    public double getLastShares() {
        return lastShares;
    }

    @Override
    public void setLastShares(double lastShares) {
        this.lastShares = lastShares;
    }

    @Override
    public double getTotalHoldings() {
        return totalHoldings;
    }

    @Override
    public void setTotalHoldings(double totalHoldings) {
        this.totalHoldings = totalHoldings;
    }

    @Override
    public long getBackOfficeId() {
        return backOfficeId;
    }


    @Override
    public void setBackOfficeId(long backOfficeId) {
        this.backOfficeId = backOfficeId;
    }


    @Override
    public String getInstitutionCode() {
        return institutionCode;
    }


    @Override
    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }


    @Override
    public String getInstrumentType() {
        return instrumentType;
    }


    @Override
    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }


    @Override
    public double getCommissionDifference() {
        return commissionDifference;
    }


    @Override
    public void setCommissionDifference(double commissionDifference) {
        this.commissionDifference = commissionDifference;
    }


}
