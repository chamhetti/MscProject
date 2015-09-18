package lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.normal;

import lk.ac.ucsc.oms.common_utility.api.enums.*;
import lk.ac.ucsc.oms.orderMgt.api.IOMStatus;

import java.util.Date;

/**
 * New Order request is used as the data transfer object between the trs connector and the oms.
 * When a new order request is came to the trs connector it will populate the NewOrderTRSRequest and send the request
 * object to the oms
 * <p/>
 * User: Hetti
 * Date: 1/24/13
 * Time: 11:47 AM
 */
public interface NewOrderTRSRequest {
    /**
     * @return the account number
     */
    String getAccountNumber();

    /**
     * @param accountNumber is the account number
     */
    void setAccountNumber(String accountNumber);

    /**
     * @return the symbol
     */
    String getSymbolCode();

    /**
     * @param symbolCode is the trading symbol
     */
    void setSymbolCode(String symbolCode);

    /**
     * @return the security exchange
     */
    String getExchangeCode();

    /**
     * @param exchangeCode is the security exchange
     */
    void setExchangeCode(String exchangeCode);

    /**
     * @return the order type
     */
    OrderType getOrderType();

    /**
     * @param orderType is the order type
     */
    void setOrderType(OrderType orderType);

    /**
     * @return the order side
     */
    OrderSide getSide();

    /**
     * @param side is the order side
     */
    void setSide(OrderSide side);

    /**
     * @return the order quantity
     */
    double getOrderQty();

    /**
     * @param orderQty is the order quantity
     */
    void setOrderQty(double orderQty);

    /**
     * @return the time in force value
     */
    int getTimeInForce();

    /**
     * @param timeInForce is the time in force
     */
    void setTimeInForce(int timeInForce);

    /**
     * @return the price
     */
    double getPrice();

    /**
     * @param price is the price of the stock
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
     * @return the max floor value
     */
    double getMaxFloor();

    /**
     * @param maxFloor is the max floor
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
     * @return the execution broker id
     */
    String getExecBrokerId();

    /**
     * @param execBrokerId is the execution broker id
     */
    void setExecBrokerId(String execBrokerId);

    /**
     * @return order is a day order or not
     */
    boolean isDayOrder();

    /**
     * @param dayOrder order is a day
     */
    void setDayOrder(boolean dayOrder);

    /**
     * @return the expire time
     */
    Date getExpireTime();

    /**
     * @param expireTime is the expire time of the order
     */
    void setExpireTime(Date expireTime);

    /**
     * @return the client ip address
     */
    String getClientIp();

    /**
     * @param clientIp is the client ip address
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
     * @return the fix version
     */
    String getFixVersion();

    /**
     * @param fixVersion is the fix version
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
     * @return the order category
     */
    int getOrderCategory();

    /**
     * @param orderCategory is the order category
     */
    void setOrderCategory(int orderCategory);

    /**
     * @return the parent account number
     */
    String getParentAccountNumber();

    /**
     * @param parentAccountNumber is the parent account number
     */
    void setParentAccountNumber(String parentAccountNumber);

    /**
     * @return the session id of the message
     */
    String getMsgSessionID();

    /**
     * @param msgSessionID is the session id of the message
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
     * @return the fol order id
     */
    String getFolOrderId();

    /**
     * @param folOrderId is the fol order id
     */
    void setFolOrderId(String folOrderId);

    /**
     * @return the transaction time
     */
    String getTransactionTime();

    /**
     * @param transactionTime is the transaction time
     */
    void setTransactionTime(String transactionTime);

    /**
     * @return the book keeper id
     */
    String getBookKeeperID();

    /**
     * @param bookKeeperID is the book keeper id
     */
    void setBookKeeperID(String bookKeeperID);

       /**
     * @return the security type of the symbol
     */
    String getSecurityType();

    /**
     * @param securityType is the security type of the symbol
     */
    void setSecurityType(String securityType);

    /**
     * @return the signature  required for the order
     */
    String getSignature();

    /**
     * @param signature is the signature required
     */
    void setSignature(String signature);

    /**
     * @return the master order id
     */
    String getMasterOrderID();

    /**
     * @param masterOrderID is the master order id
     */
    void setMasterOrderID(String masterOrderID);

    /**
     * @return unique trs id
     */
    String getUniqueTrsId();

    /**
     * @param uniqueId unique trs id
     */
    void setUniqueTrsId(String uniqueId);

    /**
     * @return the master order triggered time
     */
    Date getMasterOrderTriggerTime();

    /**
     * @param masterOrderTriggerTime is the master order triggered time
     */
    void setMasterOrderTriggerTime(Date masterOrderTriggerTime);

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

    /**
     * @return the instrument type of the symbol
     */
    String getInstrumentType();

    /**
     * @param instrumentType is the symbol instrument type
     */
    void setInstrumentType(String instrumentType);

    /**
     * set the desk order ref
     *
     * @return
     */
    String getDeskOrderRef();

    /**
     * get the desk order ref
     *
     * @param deskOrderRef
     */
    void setDeskOrderRef(String deskOrderRef);

    /**
     * @return the internal match status
     */
    IOMStatus getInternalMatchStatus();

    /**
     * @param internalMatchStatus is the IOM Status
     */
    void setInternalMatchStatus(IOMStatus internalMatchStatus);

    /**
     * @return the remote account number
     */
    String getRemoteAccountNumber();

    /**
     * @param remoteAccountNumber is the remote account number
     */
    void setRemoteAccountNumber(String remoteAccountNumber);

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

    /**
     * @return the remote exchange from the fix channel orers
     */
    String getRemoteExchange();

    /**
     * set the remote exchange from the fix channel order
     *
     * @param remoteExchange is the remote exchange
     */
    void setRemoteExchange(String remoteExchange);

    /**
     * Get the remote symbol associated with the fix channel order
     *
     * @return the remote symbol
     */
    String getRemoteSymbol();

    /**
     * set the remote symbol from the fix channel order
     *
     * @param remoteSymbol is the remote symbol
     */
    void setRemoteSymbol(String remoteSymbol);

    /**
     * get the security id from the fix channel order
     *
     * @return the security id
     */
    String getRemoteSecurityID();

    /**
     * set the security id from the fix channel order
     *
     * @param remoteSecurityID is the security id
     */
    void setRemoteSecurityID(String remoteSecurityID);

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
     * Returns whether the order is a time tiggered one
     *
     * @return
     */
    boolean isTimeTriggerOrder();

    /**
     * set whether the order is a time tiggered one
     *
     * @param isTimeTriggerOrder
     */
    void setTimeTriggerOrder(boolean isTimeTriggerOrder);

    boolean isOfflineDirectExecuteOrder();

    void setOfflineDirectExecuteOrder(boolean isOfflineDirectExecuteOrder);

    String getConnectionSid();

    void setConnectionSid(String connectionSid);

    String getMessageType();

    void setMessageType(String messageType);

    /**
     * give the call center order entry ref
     * @return
     */
    String getCallCenterOrdRef();

    /**
     * set the call center order entry ref
     * @param callCenterOrdRef
     */
    void setCallCenterOrdRef(String callCenterOrdRef);
}
