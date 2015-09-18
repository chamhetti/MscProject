package lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.trading;

import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import com.google.gson.annotations.SerializedName;

import static lk.ac.ucsc.oms.messaging_protocol_json.api.FieldConstants.*;


public class OrderAmendRequestBean implements Message {
    @SerializedName(ORDER_QUANTITY)
    private double orderQty = 0;

    @SerializedName(ORDER_TYPE)
    private String ordType = null;

    @SerializedName(PRICE)
    private double price = 0;

    @SerializedName(ORIGINAL_CLIENT_ORDER_ID)
    private String origClOrdID = null;

    @SerializedName(ORDER_SIDE)
    private String side = null;

    @SerializedName(SYMBOL)
    private String symbol = null;

    @SerializedName(TIME_IN_FORCE)
    private int timeInForce = 0;

    @SerializedName(MINIMUM_QUANTITY)
    private double minimumQty = 0;

    @SerializedName(MAXIMUM_FLOOR)
    private long maxFloor = 0L;

    @SerializedName(EXPIRE_TIME)
    private String expireTime = null;

    @SerializedName(SECURITY_TYPE)
    private String securityType = null;

    @SerializedName(SECURITY_EXCHANGE)
    private String securityExchange = null;

    @SerializedName(SIGNATURE)
    private String signature = null;

    @SerializedName(SECURITY_ACCOUNT_NUMBER)
    private String securityAccountNumber;

    @SerializedName(DAY_ORDER)
    private int dayOrder = -1;

    @SerializedName(BOOK_KEEPER_ID)
    private String bookKeeperId = null;

    @SerializedName(INSTRUMENT_TYPE)
    private String instrumentType = null;

    @SerializedName(INTERNAL_MATCH_STATUS)
    private int internalMatchStatus;

    @SerializedName(DESK_ORDER_REFERENCE)
    private String deskOrdReference = null;

    @SerializedName(MULTININ_BREAKDOWN_REF)
    private String multiNINBreakDownRef;

    @SerializedName(MULTININ_BATCH_NO)
    private String multiNINBatchNO;

    @SerializedName(CALL_CENTER_ORD_REF)
    private String callCenterOrdRef;

    @SerializedName(STOP_PRICE)
    private double stopPrice = 0;

    @SerializedName(MAXIMUM_PRICE)
    private double maximumPrice = 0;

    @SerializedName(STOP_PRICE_TYPE)
    private int stopPriceType = 0;

    @SerializedName(CONDITION_TYPE)
    private int conditionType = 0;

    public double getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(double orderQty) {
        this.orderQty = orderQty;
    }

    public String getOrdType() {
        return ordType;
    }

    public void setOrdType(String ordType) {
        this.ordType = ordType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOrigClOrdID() {
        return origClOrdID;
    }

    public void setOrigClOrdID(String origClOrdID) {
        this.origClOrdID = origClOrdID;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getTimeInForce() {
        return timeInForce;
    }

    public void setTimeInForce(int timeInForce) {
        this.timeInForce = timeInForce;
    }

    public double getMinimumQty() {
        return minimumQty;
    }

    public void setMinimumQty(double minimumQty) {
        this.minimumQty = minimumQty;
    }

    public long getMaxFloor() {
        return maxFloor;
    }

    public void setMaxFloor(long maxFloor) {
        this.maxFloor = maxFloor;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public String getSecurityExchange() {
        return securityExchange;
    }

    public void setSecurityExchange(String securityExchange) {
        this.securityExchange = securityExchange;
    }

    public String getSecurityAccNo() {
        return securityAccountNumber;
    }

    public void setSecurityAccNo(String securityAccNo) {
        this.securityAccountNumber = securityAccNo;
    }

    public int getDayOrder() {
        return dayOrder;
    }

    public void setDayOrder(int dayOrder) {
        this.dayOrder = dayOrder;
    }

    public String getBookKeeperID() {
        return bookKeeperId;
    }

    public void setBookKeeperID(String bookKeeperID) {
        this.bookKeeperId = bookKeeperID;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    public int getInternalMatchStatus() {
        return internalMatchStatus;
    }

    public void setInternalMatchStatus(int internalMatchStatus) {
        this.internalMatchStatus = internalMatchStatus;
    }

    public String getDeskOrdReference() {
        return deskOrdReference;
    }

    public void setDeskOrdReference(String deskOrdReference) {
        this.deskOrdReference = deskOrdReference;
    }

    public String getMultiNINBreakDownRef() {
        return multiNINBreakDownRef;
    }

    public void setMultiNINBreakDownRef(String multiNINBreakDownRef) {
        this.multiNINBreakDownRef = multiNINBreakDownRef;
    }

    public String getMultiNINBatchNO() {
        return multiNINBatchNO;
    }

    public void setMultiNINBatchNO(String multiNINBatchNO) {
        this.multiNINBatchNO = multiNINBatchNO;
    }

    public String getCallCenterOrdRef() {
        return callCenterOrdRef;
    }

    public void setCallCenterOrdRef(String callCenterOrdRef) {
        this.callCenterOrdRef = callCenterOrdRef;
    }

    public double getStopPrice() {
        return stopPrice;
    }

    public void setStopPrice(double stopPrice) {
        this.stopPrice = stopPrice;
    }

    public double getMaximumPrice() {
        return maximumPrice;
    }

    public void setMaximumPrice(double maximumPrice) {
        this.maximumPrice = maximumPrice;
    }

    public int getStopPriceType() {
        return stopPriceType;
    }

    public void setStopPriceType(int stopPriceType) {
        this.stopPriceType = stopPriceType;
    }

    public int getConditionType() {
        return conditionType;
    }

    public void setConditionType(int conditionType) {
        this.conditionType = conditionType;
    }
}
