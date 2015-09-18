package lk.ac.ucsc.oms.customer.implGeneral.beans.holding;

import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.common_utility.api.enums.HoldingTypes;
import lk.ac.ucsc.oms.customer.api.beans.holding.HoldingKey;
import lk.ac.ucsc.oms.customer.api.beans.holding.HoldingRecord;
import lk.ac.ucsc.oms.customer.api.exceptions.HoldingManagementException;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.Id;


public class HoldingRecordBean extends CacheObject implements HoldingRecord {

    @Id
    @Field
    private long holdingRecId;
    @Field
    private int status;
    @IndexedEmbedded
    private HoldingKeyBean holdingInfoKey;
    @Field
    private double netHolding;
    @Field
    private double avgCost;
    @Field
    private double sellPending;
    @Field
    private double pledgeQty;
    @Field
    private double buyPending;
    @Field
    private double avgPrice;
    @Field
    private double priceRatio;
    @Field
    private double netDayHolding;
    @Field
    private double pendingHolding;
    @Field
    private int instrumentType;
    @Field
    private double daySellPending;
    @Field
    private double dayHoldingAvgPrice;
    @Field
    private String institutionCode;
    private String customerNumberTemp;
    private String exchangeTemp;
    private String symbolTemp;
    private String accountNumberTemp;
    private HoldingTypes holdingTypeTemp;


    /**
     * default constructor for holding record bean
     */
    protected HoldingRecordBean() {
    }

    public HoldingRecordBean(HoldingKey holdingKey) throws HoldingManagementException {
        if (holdingKey == null) {
            throw new HoldingManagementException("Holding Key can't be null");
        }
        this.holdingInfoKey = (HoldingKeyBean) holdingKey;
        this.customerNumberTemp = holdingInfoKey.getCustomerNumber();
        this.exchangeTemp = holdingInfoKey.getExchange();
        this.symbolTemp = holdingInfoKey.getSymbol();
        this.accountNumberTemp = holdingInfoKey.getAccountNumber();
        this.holdingTypeTemp = holdingInfoKey.getHoldingType();
    }


    /**
     * {@inheritDoc}
     */
    public long getHoldingRecId() {
        return holdingRecId;
    }

    /**
     * {@inheritDoc}
     */
    public void setHoldingRecId(long holdingRecId) {
        this.holdingRecId = holdingRecId;
    }

    /**
     * {@inheritDoc}
     */
    public int getStatus() {
        return status;
    }

    /**
     * {@inheritDoc}
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HoldingKeyBean getHoldingInfoKey() {
        return holdingInfoKey;
    }

    public void setHoldingInfoKey(HoldingKeyBean holdingInfoKey) {
        this.holdingInfoKey = holdingInfoKey;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHoldingInfoKey(HoldingKey holdingInfoKey) {
        this.holdingInfoKey = (HoldingKeyBean) holdingInfoKey;
        this.customerNumberTemp = holdingInfoKey.getCustomerNumber();
        this.exchangeTemp = holdingInfoKey.getExchange();
        this.symbolTemp = holdingInfoKey.getSymbol();
        this.accountNumberTemp = holdingInfoKey.getAccountNumber();
        this.holdingTypeTemp = holdingInfoKey.getHoldingType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getDayHoldingAvgPrice() {
        return dayHoldingAvgPrice;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDayHoldingAvgPrice(double dayHoldingAvgPrice) {
        this.dayHoldingAvgPrice = dayHoldingAvgPrice;
    }

    @Override
    public String getInstitutionCode() {
        return institutionCode;
    }

    @Override
    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getDaySellPending() {
        return daySellPending;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDaySellPending(double daySellPending) {
        this.daySellPending = daySellPending;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getInstrumentType() {
        return instrumentType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInstrumentType(int instrumentType) {
        this.instrumentType = instrumentType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPendingHolding() {
        return pendingHolding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPendingHolding(double pendingHolding) {
        this.pendingHolding = pendingHolding;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public double getNetDayHolding() {
        return netDayHolding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNetDayHolding(double netDayHolding) {
        this.netDayHolding = netDayHolding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPriceRatio() {
        return priceRatio;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPriceRatio(double priceRatio) {
        this.priceRatio = priceRatio;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getAvgPrice() {
        return avgPrice;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAvgPrice(double avgPrice) {
        this.avgPrice = avgPrice;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getBuyPending() {
        return buyPending;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBuyPending(double buyPending) {
        this.buyPending = buyPending;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPledgeQty() {
        return pledgeQty;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPledgeQty(double pledgeQty) {
        this.pledgeQty = pledgeQty;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getSellPending() {
        return sellPending;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSellPending(double sellPending) {
        this.sellPending = sellPending;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getAvgCost() {
        return avgCost;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAvgCost(double avgCost) {
        this.avgCost = avgCost;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getNetHolding() {
        return netHolding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNetHolding(double netHolding) {
        this.netHolding = netHolding;
    }


    /**
     * set symbol temp of the bean
     *
     * @param symbolTemp of the bean
     */
    public void setSymbolTemp(String symbolTemp) {
        this.symbolTemp = symbolTemp;
    }

    /**
     * get account number temp of the bean
     *
     * @return accountNumberTemp
     */
    public String getAccountNumberTemp() {
        return accountNumberTemp;
    }

    /**
     * set account number temp of the bean
     *
     * @param accountNumberTemp of the bean
     */
    public void setAccountNumberTemp(String accountNumberTemp) {
        this.accountNumberTemp = accountNumberTemp;
    }

    /**
     * get temp holding type
     * @return temp holding type long/hrot
     */
    public HoldingTypes getHoldingTypeTemp() {
        return holdingTypeTemp;
    }

    /**
     * set holding type temp
     * @param holdingTypeTemp
     */
    public void setHoldingTypeTemp(HoldingTypes holdingTypeTemp) {
        this.holdingTypeTemp = holdingTypeTemp;
    }


}
