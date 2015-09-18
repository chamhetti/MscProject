package lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.normal;

import lk.ac.ucsc.oms.common_utility.api.enums.ClientChannel;
import lk.ac.ucsc.oms.common_utility.api.enums.OrderSide;
import lk.ac.ucsc.oms.orderMgt.api.IOMStatus;

/**
 * Cancel Order request is used as the data transfer object between the trs connector and oms. When a new cancel order
 * request is came to the trs connector it will populate the CancelOrderTRSRequest and send the request object to the oms
 * <p/>
 * User: Hetti
 * Date: 1/31/13
 * Time: 10:54 AM
 */
public interface CancelOrderTRSRequest {
    /**
     * @return the client order id
     */
    String getClOrderId();

    /**
     * @param clOrderId is the client order id
     */
    void setClOrderId(String clOrderId);

    /**
     * @return the client channel
     */
    ClientChannel getChannel();

    /**
     * @param channel is the client channel
     */
    void setChannel(ClientChannel channel);

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
     * @return the user name
     */
    String getUserName();

    /**
     * @param userName is the user name
     */
    void setUserName(String userName);

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
     * @return the message session id
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
     * @return the instrument type of the symbol
     */
    String getInstrumentType();

    /**
     * @param instrumentType is the symbol instrument type
     */
    void setInstrumentType(String instrumentType);

    /**
     * set the get internal match status
     *
     * @return
     */
    IOMStatus getInternalMatchStatus();

    /**
     * set the internal match status
     *
     * @param internalMatchStatus
     */
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

    String getMultiNINOrderBatchId();

    void setMultiNINOrderBatchId(String multiNINOrderBatchId);

    String getMultiNINOrderRef();

    void setMultiNINOrderRef(String multiNINOrderRef);

    String getConnectionSid();

    void setConnectionSid(String connectionSid);

    String getMessageType();

    void setMessageType(String messageType);

    /**
     * For desk order amend, child order cancel need to set desk order reference
     * @param deskOrderRef
     */
    void setDeskOrderRef(String deskOrderRef);

    /**
     * For desk order amend, child order cancel need to get desk order reference
     */
    String getDeskOrderRef();

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
     * @return
     */
    String getCallCenterOrdRef();

    /**
     * set the call center order entry ref
     * @param callCenterOrdRef
     */
    void setCallCenterOrdRef(String callCenterOrdRef);
}
