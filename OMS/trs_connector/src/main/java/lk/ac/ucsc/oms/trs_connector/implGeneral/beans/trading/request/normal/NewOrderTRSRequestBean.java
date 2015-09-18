package lk.ac.ucsc.oms.trs_connector.implGeneral.beans.trading.request.normal;

import lk.ac.ucsc.oms.common_utility.api.enums.*;
import lk.ac.ucsc.oms.orderMgt.api.IOMStatus;
import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.normal.NewOrderTRSRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: Hetti
 * Date: 1/24/13
 * Time: 10:48 AM
 */
public class NewOrderTRSRequestBean implements NewOrderTRSRequest {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HH:mm:ss");//20100115-09:35:51
    private SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");

    private String accountNumber;
    private String symbolCode;
    private String exchangeCode;
    private OrderType orderType;
    private OrderSide side;
    private double orderQty;
    private int timeInForce;
    private double price;
    private double minQty;
    private double maxFloor;
    private String userName;
    private ClientChannel channel;
    private String execBrokerId;
    private boolean isDayOrder;
    private Date expireTime;
    private String clientIp;
    private String remoteClOrdId;
    private String remoteOrigOrdId;
    private String fixVersion;
    private String securityIDSource;
    private int orderCategory;
    private String parentAccountNumber;
    private String sessionID;
    private String customerId;
    private String userId;
    private String folOrderId;
    private String transactionTime;
    private String bookKeeperId;
    private String securityType;
    private String signature;
    private String masterOrderID;
    private Date masterOrderTriggerTime;
    private double stopPrice = 0;
    private double maximumPrice = 0;
    private int stopPriceType = 0;
    private int conditionType = 0;
    private String instrumentType;

    private String uniqueTrsId; // two purposes: measuring response time and sync/async mapping in http clients
    private String deskOrderRef;
    private IOMStatus internalMatchStatus;
    private String remoteAccountNumber;
    private String brokerFIXID;
    private String targetCompID;
    private String targetSubID;
    private String senderSubID;
    private String onBehalfOfCompID;
    private String onBehalfOfSubID;
    private String remoteExchange;
    private String remoteSymbol;
    private String remoteSecurityID;
    private String condOrdRef;
    private String languageCode;
    private String multiNINOrderRef;
    private String multiNINOrderBatchId;
    private String clientReqId;
    private boolean isTimeTriggerOrder;
    private boolean isOfflineDirectExecuteOrder;
    private String connectionSid;
    private String messageType;
    private String callCenterOrdRef;

    @Override
    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String getSymbolCode() {
        return symbolCode;
    }

    @Override
    public void setSymbolCode(String symbolCode) {
        this.symbolCode = symbolCode;
    }

    @Override
    public String getExchangeCode() {
        return exchangeCode;
    }

    @Override
    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
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
    public OrderSide getSide() {
        return side;
    }

    @Override
    public void setSide(OrderSide side) {
        this.side = side;
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
    public String getExecBrokerId() {
        return execBrokerId;
    }

    @Override
    public void setExecBrokerId(String execBrokerId) {
        this.execBrokerId = execBrokerId;
    }

    @Override
    public boolean isDayOrder() {
        return isDayOrder;
    }

    @Override
    public void setDayOrder(boolean dayOrder) {
        isDayOrder = dayOrder;
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
    public int getOrderCategory() {
        return orderCategory;
    }

    @Override
    public void setOrderCategory(int orderCategory) {
        this.orderCategory = orderCategory;
    }

    @Override
    public String getParentAccountNumber() {
        return parentAccountNumber;
    }

    @Override
    public void setParentAccountNumber(String parentAccountNumber) {
        this.parentAccountNumber = parentAccountNumber;
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

    public String getFolOrderId() {
        return folOrderId;
    }

    public void setFolOrderId(String folOrderId) {
        this.folOrderId = folOrderId;
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
    public String getBookKeeperID() {
        return bookKeeperId;
    }

    @Override
    public void setBookKeeperID(String bookKeeperID) {
        this.bookKeeperId = bookKeeperID;
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
    public String getSignature() {
        return signature;
    }

    @Override
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * @return the master order id
     */
    @Override
    public String getMasterOrderID() {
        return masterOrderID;
    }

    /**
     * @param masterOrderID is the master order id
     */
    @Override
    public void setMasterOrderID(String masterOrderID) {
        this.masterOrderID = masterOrderID;
    }

    @Override
    public String getUniqueTrsId() {
        return uniqueTrsId;
    }

    @Override
    public void setUniqueTrsId(String uniqueTrsId) {
        this.uniqueTrsId = uniqueTrsId;
    }

    /**
     * @return the master order triggered time
     */
    @Override
    public Date getMasterOrderTriggerTime() {
        return masterOrderTriggerTime;
    }

    /**
     * @param masterOrderTriggerTime is the master order triggered time
     */
    @Override
    public void setMasterOrderTriggerTime(Date masterOrderTriggerTime) {
        this.masterOrderTriggerTime = masterOrderTriggerTime;
    }

    /**
     * @return the stop price
     */
    @Override
    public double getStopPrice() {
        return stopPrice;
    }

    /**
     * @param stopPrice stop price
     */
    @Override
    public void setStopPrice(double stopPrice) {
        this.stopPrice = stopPrice;
    }

    /**
     * @return the maximum price
     */
    @Override
    public double getMaxPrice() {
        return maximumPrice;
    }

    /**
     * @param maxPrice is the max price
     */
    @Override
    public void setMaxPrice(double maxPrice) {
        this.maximumPrice = maxPrice;
    }

    /**
     * @return the stop price type
     */
    @Override
    public int getStopPriceType() {
        return stopPriceType;
    }

    /**
     * @param stopPriceType is the stop price type
     */
    @Override
    public void setStopPriceType(int stopPriceType) {
        this.stopPriceType = stopPriceType;
    }

    /**
     * @return the condition type
     */
    @Override
    public int getConditionType() {
        return conditionType;
    }

    /**
     * set the condition type
     */
    @Override
    public void setConditionType(int conditionType) {
        this.conditionType = conditionType;
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
    public String getDeskOrderRef() {
        return deskOrderRef;
    }

    @Override
    public void setDeskOrderRef(String deskOrderRef) {
        this.deskOrderRef = deskOrderRef;
    }

    @Override
    public IOMStatus getInternalMatchStatus() {
        return internalMatchStatus;
    }

    @Override
    public void setInternalMatchStatus(IOMStatus internalMatchStatus) {
        this.internalMatchStatus = internalMatchStatus;
    }

    @Override
    public String getRemoteAccountNumber() {
        return remoteAccountNumber;
    }

    @Override
    public void setRemoteAccountNumber(String remoteAccountNumber) {
        this.remoteAccountNumber = remoteAccountNumber;
    }

    @Override
    public String getBrokerFIXID() {
        return brokerFIXID;
    }

    @Override
    public void setBrokerFIXID(String brokerFIXID) {
        this.brokerFIXID = brokerFIXID;
    }

    @Override
    public String getTargetCompID() {
        return targetCompID;
    }

    @Override
    public void setTargetCompID(String targetCompID) {
        this.targetCompID = targetCompID;
    }

    @Override
    public String getTargetSubID() {
        return targetSubID;
    }

    @Override
    public void setTargetSubID(String targetSubID) {
        this.targetSubID = targetSubID;
    }

    @Override
    public String getSenderSubID() {
        return senderSubID;
    }

    @Override
    public void setSenderSubID(String senderSubID) {
        this.senderSubID = senderSubID;
    }

    @Override
    public String getOnBehalfOfCompID() {
        return onBehalfOfCompID;
    }

    @Override
    public void setOnBehalfOfCompID(String onBehalfOfCompID) {
        this.onBehalfOfCompID = onBehalfOfCompID;
    }

    @Override
    public String getOnBehalfOfSubID() {
        return onBehalfOfSubID;
    }

    @Override
    public void setOnBehalfOfSubID(String onBehalfOfSubID) {
        this.onBehalfOfSubID = onBehalfOfSubID;
    }

    @Override
    public String getRemoteExchange() {
        return remoteExchange;
    }

    @Override
    public void setRemoteExchange(String remoteExchange) {
        this.remoteExchange = remoteExchange;
    }

    @Override
    public String getRemoteSymbol() {
        return remoteSymbol;
    }

    @Override
    public void setRemoteSymbol(String remoteSymbol) {
        this.remoteSymbol = remoteSymbol;
    }

    @Override
    public String getRemoteSecurityID() {
        return remoteSecurityID;
    }

    @Override
    public void setRemoteSecurityID(String remoteSecurityID) {
        this.remoteSecurityID = remoteSecurityID;
    }

    @Override
    public String getLanguageCode() {
        return languageCode;
    }

    @Override
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    @Override
    public String getMultiNINOrderRef() {
        return multiNINOrderRef;
    }

    @Override
    public void setMultiNINOrderRef(String multiNINOrderRef) {
        this.multiNINOrderRef = multiNINOrderRef;
    }

    @Override
    public String getMultiNINOrderBatchId() {
        return multiNINOrderBatchId;
    }

    @Override
    public void setMultiNINOrderBatchId(String multiNINOrderBatchId) {
        this.multiNINOrderBatchId = multiNINOrderBatchId;
    }

    @Override
    public String getClientReqId() {
        return clientReqId;
    }

    @Override
    public void setClientReqId(String clientReqId) {
        this.clientReqId = clientReqId;
    }

    @Override
    public boolean isTimeTriggerOrder() {
        return isTimeTriggerOrder;
    }

    @Override
    public void setTimeTriggerOrder(boolean isTimeTriggerOrder) {
        this.isTimeTriggerOrder = isTimeTriggerOrder;
    }

    @Override
    public boolean isOfflineDirectExecuteOrder() {
        return isOfflineDirectExecuteOrder;
    }

    @Override
    public void setOfflineDirectExecuteOrder(boolean isOfflineDirectExecuteOrder) {
        this.isOfflineDirectExecuteOrder = isOfflineDirectExecuteOrder;
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
    public String getCallCenterOrdRef() {
        return callCenterOrdRef;
    }

    @Override
    public void setCallCenterOrdRef(String callCenterOrdRef) {
        this.callCenterOrdRef = callCenterOrdRef;
    }

    @Override
    public String toString() {
        return "NewOrderTRSRequestBean{" +
                "sdf=" + sdf +
                ", sdfDate=" + sdfDate +
                ", accountNumber='" + accountNumber + '\'' +
                ", symbolCode='" + symbolCode + '\'' +
                ", exchangeCode='" + exchangeCode + '\'' +
                ", orderType=" + orderType +
                ", side=" + side +
                ", orderQty=" + orderQty +
                ", timeInForce=" + timeInForce +
                ", price=" + price +
                ", minQty=" + minQty +
                ", maxFloor=" + maxFloor +
                ", userName='" + userName + '\'' +
                ", channel=" + channel +
                ", execBrokerId='" + execBrokerId + '\'' +
                ", isDayOrder=" + isDayOrder +
                ", expireTime=" + expireTime +
                ", clientIp='" + clientIp + '\'' +
                ", remoteClOrdId='" + remoteClOrdId + '\'' +
                ", remoteOrigOrdId='" + remoteOrigOrdId + '\'' +
                ", fixVersion='" + fixVersion + '\'' +
                ", securityIDSource='" + securityIDSource + '\'' +
                ", orderCategory=" + orderCategory +
                ", parentAccountNumber='" + parentAccountNumber + '\'' +
                ", sessionID='" + sessionID + '\'' +
                ", customerId='" + customerId + '\'' +
                ", userId='" + userId + '\'' +
                ", folOrderId='" + folOrderId + '\'' +
                ", transactionTime='" + transactionTime + '\'' +
                ", bookKeeperId='" + bookKeeperId + '\'' +
                ", securityType='" + securityType + '\'' +
                ", signature='" + signature + '\'' +
                ", masterOrderID='" + masterOrderID + '\'' +
                ", masterOrderTriggerTime=" + masterOrderTriggerTime +
                ", stopPrice=" + stopPrice +
                ", maximumPrice=" + maximumPrice +
                ", stopPriceType=" + stopPriceType +
                ", conditionType=" + conditionType +
                ", instrumentType='" + instrumentType + '\'' +
                ", uniqueTrsId='" + uniqueTrsId + '\'' +
                ", deskOrderRef='" + deskOrderRef + '\'' +
                ", internalMatchStatus=" + internalMatchStatus +
                ", remoteAccountNumber='" + remoteAccountNumber + '\'' +
                ", brokerFIXID='" + brokerFIXID + '\'' +
                ", targetCompID='" + targetCompID + '\'' +
                ", targetSubID='" + targetSubID + '\'' +
                ", senderSubID='" + senderSubID + '\'' +
                ", onBehalfOfCompID='" + onBehalfOfCompID + '\'' +
                ", onBehalfOfSubID='" + onBehalfOfSubID + '\'' +
                ", remoteExchange='" + remoteExchange + '\'' +
                ", remoteSymbol='" + remoteSymbol + '\'' +
                ", remoteSecurityID='" + remoteSecurityID + '\'' +
                ", condOrdRef='" + condOrdRef + '\'' +
                ", languageCode='" + languageCode + '\'' +
                ", multiNINOrderRef='" + multiNINOrderRef + '\'' +
                ", multiNINOrderBatchId='" + multiNINOrderBatchId + '\'' +
                ", clientReqId='" + clientReqId + '\'' +
                ", isTimeTriggerOrder=" + isTimeTriggerOrder +
                ", isOfflineDirectExecuteOrder=" + isOfflineDirectExecuteOrder +
                ", connectionSid='" + connectionSid + '\'' +
                ", messageType='" + messageType + '\'' +
                ", callCenterOrdRef='" + callCenterOrdRef + '\'' +
                '}';
    }
}
