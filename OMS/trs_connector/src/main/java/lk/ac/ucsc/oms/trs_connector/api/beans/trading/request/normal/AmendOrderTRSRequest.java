package lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.normal;

import lk.ac.ucsc.oms.common_utility.api.enums.*;
import lk.ac.ucsc.oms.orderMgt.api.IOMStatus;

import java.util.Date;

/**
 * Amend Order request is used as the data transfer object between the trs connector and oms. When a new amend order
 * request is came to the trs connector it will populate the AmendOrderTRSRequest and send the request object to the oms
 * <p/>
 *
 * @author : Hetti
 *         Date: 1/31/13
 *         Time: 10:42 AM
 */
public interface AmendOrderTRSRequest {
    /**
     * @return the client order id
     */
    String getClOrderId();

    /**
     * @param clOrderId is the client order id
     */
    void setClOrderId(String clOrderId);

    /**
     * @return the order type
     */
    OrderType getOrderType();

    /**
     * @param orderType is the order type
     */
    void setOrderType(OrderType orderType);

    /**
     * @return the order quantity in the amend order request
     */
    double getOrderQty();

    /**
     * @param orderQty is the order quantity in the amend order
     */
    void setOrderQty(double orderQty);

    /**
     * @return the time in force value
     */
    int getTimeInForce();

    /**
     * @param timeInForce is the time in force value
     */
    void setTimeInForce(int timeInForce);

    /**
     * @return the price
     */
    double getPrice();

    /**
     * @param price is the order price
     */
    void setPrice(double price);

    /**
     * @return the minimum quantity
     */
    double getMinQty();

    /**
     * @param minQty is the minimum quantity
     */
    void setMinQty(double minQty);

    /**
     * @return the max floor of the symbol
     */
    double getMaxFloor();

    /**
     * @param maxFloor is the max floor value
     */
    void setMaxFloor(double maxFloor);

    /**
     * @return the user name
     */
    String getUserName();

    /**
     * @param userName is the user name
     */
    void setUserName(String userName);

    /**
     * @return the client channel
     */
    ClientChannel getChannel();

    /**
     * @param channel is the client channel
     */
    void setChannel(ClientChannel channel);

    /**
     * @return the client ip
     */
    String getClientIp();

    /**
     * @param clientIp is the client ip
     */
    void setClientIp(String clientIp);

    /**
     * @return the remote client order id
     */
    String getRemoteClOrdId();

    /**
     * @param remoteClOrdId is the remote client order id
     */
    void setRemoteClOrdId(String remoteClOrdId);

    /**
     * @return the remote original order id
     */
    String getRemoteOrigOrdId();

    /**
     * @param remoteOrigOrdId is the remote original order id
     */
    void setRemoteOrigOrdId(String remoteOrigOrdId);

    /**
     * @return the expire time
     */
    Date getExpireTime();

    /**
     * @param expireTime is the expire time
     */
    void setExpireTime(Date expireTime);

    /**
     * @return the fix message version
     */
    String getFixVersion();

    /**
     * @param fixVersion is the fix message version
     */
    void setFixVersion(String fixVersion);

    /**
     * @return the security id source
     */
    String getSecurityIDSource();

    /**
     * @param securityIDSource is the security id source
     */
    void setSecurityIDSource(String securityIDSource);

    /**
     * @return the session id of the message
     */
    String getMsgSessionID();

    /**
     * @param msgSessionID is the message session id
     */
    void setMsgSessionID(String msgSessionID);

    /**
     * @return the customer id
     */
    String getCustomerID();

    /**
     * @param customerID is the customer id
     */
    void setCustomerID(String customerID);

    /**
     * @return the user id
     */
    String getUserID();

    /**
     * @param userID is the user id
     */
    void setUserID(String userID);

    /**
     * @return the original client order id
     */
    String getOrigClOrdID();

    /**
     * @param origClOrdID is the original client order id
     */
    void setOrigClOrdID(String origClOrdID);

    /**
     * @return the order side
     */
    OrderSide getOrderSide();

    /**
     * @param orderSide is the order side
     */
    void setOrderSide(OrderSide orderSide);

    /**
     * @return the symbol
     */
    String getSymbol();

    /**
     * @param symbol is the symbol
     */
    void setSymbol(String symbol);

    /**
     * @return the transaction time
     */
    String getTransactionTime();

    /**
     * @param transactionTime is the transaction time
     */
    void setTransactionTime(String transactionTime);



    /**
     * @return the security type of the symbol
     */
    String getSecurityType();

    /**
     * @param securityType is the security type of the symbol
     */
    void setSecurityType(String securityType);

    /**
     * @return the security exchange
     */
    String getExchange();

    /**
     * @param exchange is the security exchange
     */
    void setExchange(String exchange);

    /**
     * @return unique trs id
     */
    String getUniqueTrsId();

    /**
     * @param uniqueId unique trs id
     */
    void setUniqueTrsId(String uniqueId);

    /**
     * @return account number
     */
    String getAccountNumber();

    /**
     * @param accountNumber account number
     */
    void setAccountNumber(String accountNumber);

    /**
     * @return order is a day order or not
     */
    boolean isDayOrder();

    /**
     * @param dayOrder order is a day
     */
    void setDayOrder(boolean dayOrder);

    /**
     * @return the instrument type of the symbol
     */
    String getInstrumentType();

    /**
     * @param instrumentType is the symbol instrument type
     */
    void setInstrumentType(String instrumentType);

    IOMStatus getInternalMatchStatus();

    void setInternalMatchStatus(IOMStatus internalMatchStatus);

    /**
     * for fix channel order replies this is the tag 56 value
     *
     * @return the broker fix id
     */
    String getBrokerFIXID();

    /**
     * fix tag 49 (sender comp id)is set as the broker fix id
     *
     * @param brokerFIXID
     */
    void setBrokerFIXID(String brokerFIXID);

    /**
     * for fix channel order replies this is the tag 49 value
     *
     * @return the target comp id
     */
    String getTargetCompID();

    /**
     * fix tag 56 (target comp id)
     *
     * @param targetCompID
     */
    void setTargetCompID(String targetCompID);

    /**
     * for fix channel order replies this is the tag 50 value
     *
     * @return the target sub id
     */
    String getTargetSubID();

    /**
     * fix tag 57 (target sub id)
     *
     * @param targetSubID
     */
    void setTargetSubID(String targetSubID);

    /**
     * for fix channel order replies this is the tag 57 value
     *
     * @return
     */
    String getSenderSubID();

    /**
     * fix tag 50 (sender sub id)
     *
     * @param senderSubID
     */
    void setSenderSubID(String senderSubID);

    /**
     * for fix channel order replies this is the tag 128 value
     *
     * @return the value
     */
    String getOnBehalfOfCompID();

    /**
     * fix tag 115
     *
     * @param onBehalfOfCompID the onBehalfOfCompID value
     */
    void setOnBehalfOfCompID(String onBehalfOfCompID);

    /**
     * for fix channel order replies this is the tag 129 value
     *
     * @return the on behalf of sub id
     */
    String getOnBehalfOfSubID();

    /**
     * FIX tag 116
     *
     * @param onBehalfOfSubID is the on behalf of sub id
     */
    void setOnBehalfOfSubID(String onBehalfOfSubID);



    String getLanguageCode();

    void setLanguageCode(String languageCode);

    /**
     * Return the multiNIN Order Reference
     *
     * @return
     */
    String getMultiNINOrderRef();

    /**
     * Set the multiNIN order reference
     *
     * @param multiNINOrdeRef
     */
    void setMultiNINOrderRef(String multiNINOrdeRef);

    /**
     * Returns the MultiNIN batch order id
     *
     * @return
     */
    String getMultiNINOrderBatchId();

    /**
     * Set the multiNIN order batch id
     *
     * @param multiNINOrderBatchId
     */
    void setMultiNINOrderBatchId(String multiNINOrderBatchId);

    String getConnectionSid();

    void setConnectionSid(String connectionSid);

    String getMessageType();

    void setMessageType(String messageType);

    /**
     * Returns client request id
     *
     * @return client request id
     */
    String getClientReqId();

    /**
     * Sets client request id
     *
     * @param clientReqID client request id
     */
    void setClientReqId(String clientReqID);

    /**
     * give the call center order entry ref
     *
     * @return
     */
    String getCallCenterOrdRef();

    /**
     * set the call center order entry ref
     *
     * @param callCenterOrdRef
     */
    void setCallCenterOrdRef(String callCenterOrdRef);

    /**
     * @return the stop price
     */
    double getStopPrice();

    /**
     * @param stopPrice stop price
     */
    void setStopPrice(double stopPrice);

    /**
     * @return the maximum price
     */
    double getMaxPrice();

    /**
     * @param maxPrice is the max price
     */
    void setMaxPrice(double maxPrice);

    /**
     * @return the stop price type
     */
    int getStopPriceType();

    /**
     * @param stopPriceType is the stop price type
     */
    void setStopPriceType(int stopPriceType);

    /**
     * @return the condition type
     */
    int getConditionType();

    /**
     * set the condition type
     */
    void setConditionType(int conditionType);

}
