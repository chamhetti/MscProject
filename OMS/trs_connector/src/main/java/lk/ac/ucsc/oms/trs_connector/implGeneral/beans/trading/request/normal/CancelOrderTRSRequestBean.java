package lk.ac.ucsc.oms.trs_connector.implGeneral.beans.trading.request.normal;

import lk.ac.ucsc.oms.common_utility.api.enums.ClientChannel;
import lk.ac.ucsc.oms.common_utility.api.enums.OrderSide;
import lk.ac.ucsc.oms.orderMgt.api.IOMStatus;
import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.normal.CancelOrderTRSRequest;

/**
 * User: Hetti
 * Date: 1/31/13
 * Time: 10:48 AM
 */
public class CancelOrderTRSRequestBean implements CancelOrderTRSRequest {
    private String clOrderId;
    private ClientChannel channel;
    private String clientIp;
    private String remoteClOrdId;
    private String remoteOrigOrdId;
    private String userName;
    private String fixVersion;
    private String securityIDSource;
    private String sessionID;
    private String customerId;
    private String userId;
    private OrderSide orderSide;
    private String symbol;
    private String exchange;
    private String accountNumber;
    private String instrumentType;
    private String uniqueTrsId;
    private IOMStatus internalMatchStatus;
    private String brokerFIXID;
    private String targetCompID;
    private String targetSubID;
    private String senderSubID;
    private String onBehalfOfCompID;
    private String onBehalfOfSubID;
    private String languageCode;
    private String multiNINOrderRef;
    private String multiNINOrderBatchId;
    private String connectionSid;
    private String messageType;
    private String deskOrderRef;
    private String clientReqId;
    private String callCenterOrdRef;


    @Override
    public String getClOrderId() {
        return clOrderId;
    }

    @Override
    public void setClOrderId(String clOrderId) {
        this.clOrderId = clOrderId;
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
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
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

    /**
     * @return the order side
     */
    @Override
    public OrderSide getOrderSide() {
        return orderSide;
    }

    /**
     * @param orderSide is the order side
     */
    @Override
    public void setOrderSide(OrderSide orderSide) {
        this.orderSide = orderSide;
    }

    /**
     * @return the symbol
     */
    @Override
    public String getSymbol() {
        return symbol;
    }

    /**
     * @param symbol is the symbol
     */
    @Override
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * @return the security exchange
     */
    @Override
    public String getExchange() {
        return exchange;
    }

    /**
     * @param exchange is the security exchange
     */
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

    @Override
    public String getMultiNINOrderBatchId() {
        return multiNINOrderBatchId;
    }

    @Override
    public void setMultiNINOrderBatchId(String multiNINOrderBatchId) {
        this.multiNINOrderBatchId = multiNINOrderBatchId;
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
    public void setDeskOrderRef(String deskOrderRef) {
        this.deskOrderRef = deskOrderRef;
    }

    @Override
    public String getDeskOrderRef() {
        return deskOrderRef;
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
}
