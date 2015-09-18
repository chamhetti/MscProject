package lk.ac.ucsc.oms.trs_connector.implGeneral.beans.trading.request.normal;

import lk.ac.ucsc.oms.common_utility.api.enums.*;
import lk.ac.ucsc.oms.orderMgt.api.IOMStatus;
import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.normal.AmendOrderTRSRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Implementation of the Amend Order request
 * <p/>
 *
 * @author : Hetti
 *         Date: 1/31/13
 *         Time: 10:30 AM
 */
public class AmendOrderTRSRequestBean implements AmendOrderTRSRequest {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HH:mm:ss");//20100115-09:35:51
    private SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");

    private String clOrderId;
    private OrderType orderType;
    private double orderQty;
    private int timeInForce;
    private double price;
    private double minQty;
    private double maxFloor;
    private Date expireTime;
    private String userName;
    private ClientChannel channel;
    private String clientIp;
    private String remoteClOrdId;
    private String remoteOrigOrdId;
    private String fixVersion;
    private String securityIDSource;
    private String sessionID;
    private String customerId;
    private String userId;
    private String origClOrdID;
    private OrderSide orderSide;
    private String symbol;
    private String transactionTime;
    private String securityType;
    private String exchange;
    private String accountNumber;
    private boolean isDayOrder;
    private String instrumentType;

    private String uniqueTrsId;
    private IOMStatus internalMatchStatus;
    private String brokerFIXID;
    private String targetCompID;
    private String targetSubID;
    private String senderSubID;
    private String onBehalfOfCompID;
    private String onBehalfOfSubID;

    private String deskOrderRef;
    private String languageCode;
    private String multiNINOrderRef;
    private String multiNINOrderBatchId;
    private String connectionSid;
    private String messageType;
    private String clientReqId;
    private String callCenterOrdRef;
    private double stopPrice = 0;
    private double maximumPrice = 0;
    private int stopPriceType = 0;
    private int conditionType = 0;

    @Override
    public boolean isDayOrder() {
        return isDayOrder;
    }

    @Override
    public void setDayOrder(boolean dayOrder) {
        isDayOrder = dayOrder;
    }

    /**
     * @return the instrument type of the symbol
     */
    @Override
    public String getInstrumentType() {
        return instrumentType;
    }

    /**
     * @param instrumentType is the symbol instrument type
     */
    @Override
    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
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
    public OrderType getOrderType() {
        return orderType;
    }

    @Override
    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    @Override
    public double getOrderQty() {
        return orderQty;
    }

    @Override
    public void setOrderQty(double orderQty) {
        this.orderQty = orderQty;
    }

    @Override
    public int getTimeInForce() {
        return timeInForce;
    }

    @Override
    public void setTimeInForce(int timeInForce) {
        this.timeInForce = timeInForce;
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
    public double getMinQty() {
        return minQty;
    }

    @Override
    public void setMinQty(double minQty) {
        this.minQty = minQty;
    }

    @Override
    public double getMaxFloor() {
        return maxFloor;
    }

    @Override
    public void setMaxFloor(double maxFloor) {
        this.maxFloor = maxFloor;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public ClientChannel getChannel() {
        return channel;
    }

    @Override
    public void setChannel(ClientChannel channel) {
        this.channel = channel;
    }

    @Override
    public String getClientIp() {
        return clientIp;
    }

    @Override
    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    @Override
    public String getRemoteClOrdId() {
        return remoteClOrdId;
    }

    @Override
    public void setRemoteClOrdId(String remoteClOrdId) {
        this.remoteClOrdId = remoteClOrdId;
    }

    @Override
    public String getRemoteOrigOrdId() {
        return remoteOrigOrdId;
    }

    @Override
    public void setRemoteOrigOrdId(String remoteOrigOrdId) {
        this.remoteOrigOrdId = remoteOrigOrdId;
    }

    @Override
    public Date getExpireTime() {
        return expireTime;
    }

    @Override
    public void setExpireTime(Date expireTime) {
        try {
            if (expireTime != null) {
                try {
                    this.expireTime = sdf.parse(sdfDate.format(expireTime) + "-23:59:59");
                } catch (Exception ex) {
                    this.expireTime = sdf.parse(sdfDate.format(new Date(System.currentTimeMillis())) + "-23:59:59");
                }
            } else {
                this.expireTime = sdf.parse(sdfDate.format(new Date(System.currentTimeMillis())) + "-23:59:59");
            }
        } catch (Exception e) {
            this.expireTime = new Date(System.currentTimeMillis());
        }
    }

    @Override
    public String getFixVersion() {
        return fixVersion;
    }

    @Override
    public void setFixVersion(String fixVersion) {
        this.fixVersion = fixVersion;
    }

    @Override
    public String getSecurityIDSource() {
        return securityIDSource;
    }

    @Override
    public void setSecurityIDSource(String securityIDSource) {
        this.securityIDSource = securityIDSource;
    }

    @Override
    public String getMsgSessionID() {
        return sessionID;
    }

    @Override
    public void setMsgSessionID(String msgSessionID) {
        this.sessionID = msgSessionID;
    }

    @Override
    public String getCustomerID() {
        return customerId;
    }

    @Override
    public void setCustomerID(String customerID) {
        this.customerId = customerID;
    }

    @Override
    public String getUserID() {
        return userId;
    }

    @Override
    public void setUserID(String userID) {
        this.userId = userID;
    }

    @Override
    public String getOrigClOrdID() {
        return origClOrdID;
    }

    @Override
    public void setOrigClOrdID(String origClOrdID) {
        this.origClOrdID = origClOrdID;
    }

    @Override
    public OrderSide getOrderSide() {
        return orderSide;
    }

    @Override
    public void setOrderSide(OrderSide orderSide) {
        this.orderSide = orderSide;
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
    public String getTransactionTime() {
        return transactionTime;
    }

    @Override
    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }


    @Override
    public String getSecurityType() {
        return securityType;
    }

    @Override
    public void setSecurityType(String securityType) {
        this.securityType = securityType;
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
    public String getUniqueTrsId() {
        return uniqueTrsId;
    }

    @Override
    public void setUniqueTrsId(String uniqueTrsId) {
        this.uniqueTrsId = uniqueTrsId;
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
    public IOMStatus getInternalMatchStatus() {
        return internalMatchStatus;
    }

    @Override
    public void setInternalMatchStatus(IOMStatus internalMatchStatus) {
        this.internalMatchStatus = internalMatchStatus;
    }

    public String getBrokerFIXID() {
        return brokerFIXID;
    }

    public void setBrokerFIXID(String brokerFIXID) {
        this.brokerFIXID = brokerFIXID;
    }

    public String getTargetCompID() {
        return targetCompID;
    }

    public void setTargetCompID(String targetCompID) {
        this.targetCompID = targetCompID;
    }

    public String getTargetSubID() {
        return targetSubID;
    }

    public void setTargetSubID(String targetSubID) {
        this.targetSubID = targetSubID;
    }

    public String getSenderSubID() {
        return senderSubID;
    }

    public void setSenderSubID(String senderSubID) {
        this.senderSubID = senderSubID;
    }

    public String getOnBehalfOfCompID() {
        return onBehalfOfCompID;
    }

    public void setOnBehalfOfCompID(String onBehalfOfCompID) {
        this.onBehalfOfCompID = onBehalfOfCompID;
    }

    public String getOnBehalfOfSubID() {
        return onBehalfOfSubID;
    }

    public void setOnBehalfOfSubID(String onBehalfOfSubID) {
        this.onBehalfOfSubID = onBehalfOfSubID;
    }




    @Override
    public String getLanguageCode() {
        return languageCode;
    }

    @Override
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getMultiNINOrderRef() {
        return multiNINOrderRef;
    }

    public void setMultiNINOrderRef(String multiNINOrderRef) {
        this.multiNINOrderRef = multiNINOrderRef;
    }

    public String getMultiNINOrderBatchId() {
        return multiNINOrderBatchId;
    }

    public void setMultiNINOrderBatchId(String multiNINOrderBatchId) {
        this.multiNINOrderBatchId = multiNINOrderBatchId;
    }

    @Override
    public String getConnectionSid() {
        return connectionSid;
    }

    @Override
    public void setConnectionSid(String connectionSid) {
        this.connectionSid = connectionSid;
    }

    @Override
    public String getMessageType() {
        return messageType;
    }

    @Override
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    @Override
    public String getClientReqId() {
        return clientReqId;
    }

    @Override
    public void setClientReqId(String clientReqID) {
        this.clientReqId = clientReqID;
    }

    @Override
    public String getCallCenterOrdRef() {
        return callCenterOrdRef;
    }

    @Override
    public void setCallCenterOrdRef(String callCenterOrdRef) {
        this.callCenterOrdRef = callCenterOrdRef;
    }

    @Override
    public double getStopPrice() {
        return stopPrice;
    }

    @Override
    public void setStopPrice(double stopPrice) {
        this.stopPrice = stopPrice;
    }

    @Override
    public double getMaxPrice() {
        return maximumPrice;
    }

    @Override
    public void setMaxPrice(double maxPrice) {
        this.maximumPrice = maxPrice;
    }

    @Override
    public int getStopPriceType() {
        return stopPriceType;
    }

    @Override
    public void setStopPriceType(int stopPriceType) {
        this.stopPriceType = stopPriceType;
    }

    @Override
    public int getConditionType() {
        return conditionType;
    }

    @Override
    public void setConditionType(int conditionType) {
        this.conditionType = conditionType;
    }

    @Override
    public String toString() {
        return "AmendOrderTRSRequestBean{" +
                "sdf=" + sdf +
                ", sdfDate=" + sdfDate +
                ", clOrderId='" + clOrderId + '\'' +
                ", orderType=" + orderType +
                ", orderQty=" + orderQty +
                ", timeInForce=" + timeInForce +
                ", price=" + price +
                ", minQty=" + minQty +
                ", maxFloor=" + maxFloor +
                ", expireTime=" + expireTime +
                ", userName='" + userName + '\'' +
                ", channel=" + channel +
                ", clientIp='" + clientIp + '\'' +
                ", remoteClOrdId='" + remoteClOrdId + '\'' +
                ", remoteOrigOrdId='" + remoteOrigOrdId + '\'' +
                ", fixVersion='" + fixVersion + '\'' +
                ", securityIDSource='" + securityIDSource + '\'' +
                ", sessionID='" + sessionID + '\'' +
                ", customerId='" + customerId + '\'' +
                ", userId='" + userId + '\'' +
                ", origClOrdID='" + origClOrdID + '\'' +
                ", orderSide=" + orderSide +
                ", symbol='" + symbol + '\'' +
                ", transactionTime='" + transactionTime + '\'' +
                ", securityType='" + securityType + '\'' +
                ", exchange='" + exchange + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", isDayOrder=" + isDayOrder +
                ", instrumentType='" + instrumentType + '\'' +
                ", uniqueTrsId='" + uniqueTrsId + '\'' +
                ", internalMatchStatus=" + internalMatchStatus +
                ", brokerFIXID='" + brokerFIXID + '\'' +
                ", targetCompID='" + targetCompID + '\'' +
                ", targetSubID='" + targetSubID + '\'' +
                ", senderSubID='" + senderSubID + '\'' +
                ", onBehalfOfCompID='" + onBehalfOfCompID + '\'' +
                ", onBehalfOfSubID='" + onBehalfOfSubID + '\'' +
                ", deskOrderRef='" + deskOrderRef + '\'' +
                ", languageCode='" + languageCode + '\'' +
                ", multiNINOrderRef='" + multiNINOrderRef + '\'' +
                ", multiNINOrderBatchId='" + multiNINOrderBatchId + '\'' +
                ", connectionSid='" + connectionSid + '\'' +
                ", messageType='" + messageType + '\'' +
                ", clientReqId='" + clientReqId + '\'' +
                ", callCenterOrdRef='" + callCenterOrdRef + '\'' +
                ", stopPrice=" + stopPrice +
                ", maximumPrice=" + maximumPrice +
                ", stopPriceType=" + stopPriceType +
                ", conditionType=" + conditionType +
                '}';
    }
}
