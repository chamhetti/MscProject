package lk.ac.ucsc.oms.fix.api.beans;

import java.util.Date;

/**
 * API interface for the Fix Order Bean
 * Contain the tag definitions of the fix order. This follows the general standards of the Quick FIXConstants in the naming
 *
 * @author
 *         Date: Oct 30, 2012
 */
public interface FixOrderInterface {
    /**
     * @return the portfolio number
     */
    String getPortfolioNo();

    /**
     * FIX protocol tag 1
     *
     * @param portfolioNo is the portfolio number
     */
    void setPortfolioNo(String portfolioNo);

    /**
     * @return the average price
     */
    double getAvgPx();

    /**
     * FIX protocol tag 6
     *
     * @param avgPx is the average price
     */
    void setAvgPx(double avgPx);

    /**
     * @return the fix version
     */
    String getFixVersion();

    /**
     * FIX protocol tag 8
     *
     * @param fixVersion is the fix version
     */
    void setFixVersion(String fixVersion);

    /**
     * @return the client order id
     */
    String getClordID();

    /**
     * FIX protocol tag 11
     *
     * @param clordID is the client order id
     */
    void setClordID(String clordID);

    /**
     * @return the commission
     */
    double getCommission();

    /**
     * FIX protocol tag 12
     *
     * @param commission is  the commission
     */
    void setCommission(double commission);

    /**
     * @return the cumulative quantity
     */
    double getCumQty();

    /**
     * FIX protocol tag 14
     *
     * @param cumQty is the cumulative quantity
     */
    void setCumQty(double cumQty);

    /**
     * @return the currency
     */
    String getCurrency();

    /**
     * FIX protocol tag 15
     *
     * @param currency is the currency
     */
    void setCurrency(String currency);

    /**
     * @return the execution id
     */
    String getExecID();

    /**
     * FIX protocol tag 17
     *
     * @param execID is the execution id
     */
    void setExecID(String execID);

    /**
     * @return the execution institute
     */
    String getExecInst();

    /**
     * FIX protocol tag 18
     *
     * @param execInst is the execution institute
     */
    void setExecInst(String execInst);

    /**
     * @return the execution trans type
     */
    int getExecTransType();

    /**
     * FIX protocol tag 20
     *
     * @param execTransType is the execution trans type
     */
    void setExecTransType(int execTransType);

    /**
     * Instructions for order handling on Broker trading floor
     *
     * @return the value
     */
    int getHandlInst();

    /**
     * FIX protocol tag 21
     *
     * @param handlInst is the value
     */
    void setHandlInst(int handlInst);

    /**
     * @return the security id source
     */
    String getSecurityIDSource();

    /**
     * FIX protocol tag 22
     *
     * @param securityIDSource is the security id source
     */
    void setSecurityIDSource(String securityIDSource);

    /**
     * @return the last price
     */
    double getLastPx();

    /**
     * FIX protocol tag 31
     *
     * @param lastPx is the last price
     */
    void setLastPx(double lastPx);

    /**
     * @return the last shares
     */
    double getLastShares();

    /**
     * FIX protocol tag 32
     *
     * @param lastShares is the last shares
     */
    void setLastShares(double lastShares);

    /**
     * @return the message type
     */
    MessageType getMessageType();

    /**
     * @param messageType is the message type
     */
    void setMessageType(MessageType messageType);

    /**
     * @return the order id
     */
    String getOrderID();

    /**
     * @param orderID is the order id
     */
    void setOrderID(String orderID);

    /**
     * FIX protocol tag 37
     *
     * @return the order quantity
     */
    double getOrderQty();

    /**
     * FIX protocol tag 38
     *
     * @param orderQty is the order quantity
     */
    void setOrderQty(double orderQty);

    /**
     * @return the order status
     */
    char getOrdStatus();

    /**
     * FIX protocol tag 39
     *
     * @param ordStatus is the order status
     */
    void setOrdStatus(char ordStatus);

    /**
     * @return the order type
     */
    char getOrdType();

    /**
     * FIX protocol tag 40
     *
     * @param ordType is the order type
     */
    void setOrdType(char ordType);

    /**
     * @return the original order id
     */
    String getOrigClordID();

    /**
     * FIX protocol tag 41
     *
     * @param origClordID is the original order id
     */
    void setOrigClordID(String origClordID);

    /**
     * @return Indicates possible retransmission of message with this sequence number
     */
    boolean getPossDupFlag();

    /**
     * FIX protocol tag 43 PossDupFlag
     *
     * @param possDupFlag is Indicates possible retransmission of message with this sequence number
     */
    void setPossDupFlag(boolean possDupFlag);

    /**
     * @return the price
     */
    double getPrice();

    /**
     * FIX protocol tag 44 Limit Price
     *
     * @param price is the price
     */
    void setPrice(double price);

    /**
     * Security identifier value of SecurityIDSource (22) type (e.g. CUSIP, SEDOL, ISIN, etc). Requires SecurityIDSource.
     *
     * @return the security id
     */
    String getSecurityID();

    /**
     * FIX protocol tag 48
     *
     * @param securityID is the security id
     */
    void setSecurityID(String securityID);

    /**
     * @return the sender comp id
     */
    String getSenderCompID();

    /**
     * FIX protocol tag 49
     *
     * @param senderCompID is the sender comp id
     */
    void setSenderCompID(String senderCompID);

    /**
     * @return the sender sub id
     */
    String getSenderSubID();

    /**
     * FIX protocol tag 50
     *
     * @param senderSubID is the sender sub id
     */
    void setSenderSubID(String senderSubID);

    /**
     * @return the order side
     */
    char getSide();

    /**
     * FIX protocol tag 54
     *
     * @param side is the order side
     */
    void setSide(char side);

    /**
     * @return the sending time
     */
    Date getSendingTime();

    /**
     * FIX protocol tag 52
     *
     * @param sendingTime is the sending time
     */
    void setSendingTime(Date sendingTime);

    /**
     * @return the symbol
     */
    String getSymbol();

    /**
     * FIX protocol tag 55
     *
     * @param symbol is the symbol
     */
    void setSymbol(String symbol);

    /**
     * @return the target comp id
     */
    String getTargetCompID();

    /**
     * FIX protocol tag 56
     *
     * @param targetCompID is the target comp id
     */
    void setTargetCompID(String targetCompID);

    /**
     * @return the target sub id
     */
    String getTargetSubID();

    /**
     * FIX protocol tag 57
     *
     * @param targetSubID is the target sub id
     */
    void setTargetSubID(String targetSubID);

    /**
     * @return the order text
     */
    String getText();

    /**
     * FIX protocol tag 58
     *
     * @param text is the order text
     */
    void setText(String text);

    /**
     * Specifies how long the order remains in effect. Absence of this field is interpreted as DAY.
     *
     * @return the time in force value
     */
    int getTimeInForce();

    /**
     * FIX protocol tag 59
     *
     * @param timeInForce is the time in force value
     */
    void setTimeInForce(int timeInForce);

    /**
     * @return the transaction time
     */
    String getTransactTime();

    /**
     * FIX protocol tag 60
     *
     * @param transactTime is the transaction time
     */
    void setTransactTime(String transactTime);

    /**
     * @return the settlement type
     */
    int getSettlementType();

    /**
     * FIX protocol tag 63
     *
     * @param settlementType is the settlement type
     */
    void setSettlementType(int settlementType);

    /**
     * @return the stop price
     */
    double getStopPx();

    /**
     * FIX protocol tag 99
     *
     * @param stopPx is the stop price
     */
    void setStopPx(double stopPx);

    /**
     * @return Execution destination as defined by institution when order is entered
     */
    String getExDestination();

    /**
     * FIX protocol tag 100
     *
     * @param exDestination is the Execution destination
     */
    void setExDestination(String exDestination);

    /**
     * @return the order reject reason
     */
    int getOrdRejReason();

    /**
     * FIX protocol tag 102
     *
     * @param ordRejReason is the order reject reason
     */
    void setOrdRejReason(int ordRejReason);

    /**
     * @return the minimum quantity
     */
    double getMinQty();

    /**
     * FIX protocol tag 110
     *
     * @param minQty is the minimum quantity
     */
    void setMinQty(double minQty);

    /**
     * @return the max floor
     */
    double getMaxFloor();

    /**
     * FIX protocol tag 111
     *
     * @param maxFloor is the max floor
     */
    void setMaxFloor(double maxFloor);

    /**
     * Assigned value used to identify firm originating message if the message was delivered by a third party
     * i.e. the third party firm identifier would be delivered in the SenderCompID field and
     * the firm originating the message in this field.
     *
     * @return the value
     */
    String getOnBehalfOfCompID();

    /**
     * FIX protocol tag 115
     *
     * @param onBehalfOfCompID the onBehalfOfCompID value
     */
    void setOnBehalfOfCompID(String onBehalfOfCompID);

    /**
     * Assigned value used to identify specific message originator (i.e. trader)
     * if the message was delivered by a third party
     *
     * @return the on behalf of sub id
     */
    String getOnBehalfOfSubID();

    /**
     * FIX protocol tag 116
     *
     * @param onBehalfOfSubID is the on behalf of sub id
     */
    void setOnBehalfOfSubID(String onBehalfOfSubID);

    /**
     * @return the expire time
     */
    Date getExpireTime();

    /**
     * FIX protocol tag 126
     *
     * @param expireTime is the expire time
     */
    void setExpireTime(Date expireTime);

    /**
     * @return the sender location id
     */
    String getSenderLocationID();

    /**
     * FIX protocol tag 142
     *
     * @param senderLocationID is the sender location id
     */
    void setSenderLocationID(String senderLocationID);

    /**
     * Describes the specific ExecutionRpt (i.e. Pending Cancel) while OrdStatus (39) will always identify
     * the current order status (i.e. Partially Filled)
     *
     * @return the execution type value
     */
    char getExecType();

    /**
     * FIX protocol tag 150
     *
     * @param execType is the execution type value
     */
    void setExecType(char execType);

    /**
     * @return the leaves quantity
     */
    double getLeavesQty();

    /**
     * FIX protocol tag 151
     *
     * @param leavesQty is the leaves quantity
     */
    void setLeavesQty(double leavesQty);

    /**
     * @return the type of security
     */
    String getSecurityType();

    /**
     * FIX protocol tag 167
     *
     * @param securityType is the type of security
     */
    void setSecurityType(String securityType);

    /**
     * @return the maturity month and year
     */
    String getMaturityMonthYear();

    /**
     * FIX protocol tag 200
     *
     * @param maturityMonthYear is the maturity month and year
     */
    void setMaturityMonthYear(String maturityMonthYear);

    /**
     * @return whether an Option is for a put or call
     */
    int getPutOrCall();

    /**
     * FIX protocol tag 201
     *
     * @param putOrCall is the Option is for a put or call
     */
    void setPutOrCall(int putOrCall);

    /**
     * @return the strike price
     */
    double getStrikePrice();

    /**
     * @param strikePrice is the strike price
     */
    void setStrikePrice(double strikePrice);

    /**
     * @return the security exchange
     */
    String getSecurityExchange();

    /**
     * FIX protocol tag 207
     *
     * @param securityExchange is the security exchange
     */
    void setSecurityExchange(String securityExchange);

    /**
     * @return the Subscription Request Type
     */
    char getSubscriptionRequestType();

    /**
     * FIX protocol tag 263
     *
     * @param subscriptionRequestType is the Subscription Request Type
     */
    void setSubscriptionRequestType(char subscriptionRequestType);

    /**
     * @return the underlying symbol
     */
    String getUnderlyingSymbol();

    /**
     * FIX protocol tag 311
     *
     * @param underlyingSymbol is the underlying symbol
     */
    void setUnderlyingSymbol(String underlyingSymbol);

    /**
     * @return the trade session request id
     */
    String getTradSesReqID();

    /**
     * FIX protocol tag 335
     *
     * @param tradSesReqID is the trade session request id
     */
    void setTradSesReqID(String tradSesReqID);

    /**
     * @return the trading session id
     */
    String getTradingSessionID();

    /**
     * FIX protocol tag 336
     *
     * @param tradingSessionID is the trading session id
     */
    void setTradingSessionID(String tradingSessionID);

    /**
     * @return the trade session method
     */
    String getTradSesMethod();

    /**
     * FIX protocol tag 338
     *
     * @param tradSesMethod is the trade session method
     */
    void setTradSesMethod(String tradSesMethod);

    /**
     * @return the trade session status
     */
    int getTradSesStatus();

    /**
     * FIX protocol tag 340
     *
     * @param tradSesStatus is the trade session status
     */
    void setTradSesStatus(int tradSesStatus);

    /**
     * @return the Solicited Flag  value
     */
    boolean getSolicitedFlag();

    /**
     * FIX protocol tag 377
     *
     * @param solicitedFlag is the Solicited Flag  value
     */
    void setSolicitedFlag(boolean solicitedFlag);

    /**
     * @return the expire date
     */
    Date getExpireDate();

    /**
     * FIX protocol tag 432
     *
     * @param expireDate is the expire date
     */
    void setExpireDate(Date expireDate);

    /**
     * @return the type of request that a Cancel Reject is in response to
     */
    char getCxlRejResponseTo();

    /**
     * FIX protocol tag 434
     *
     * @param cxlRejResponseTo is the type of request that a Cancel Reject is in response to
     */
    void setCxlRejResponseTo(char cxlRejResponseTo);

    /**
     * @return the account type
     */
    int getAccountType();

    /**
     * FIX protocol tag 581
     *
     * @param accountType is the account type
     */
    void setAccountType(int accountType);

    /**
     * @return the created date
     */
    Date getCreatedDate();

    /**
     * FIX protocol tag 804
     *
     * @param createdDate is the created date
     */
    void setCreatedDate(Date createdDate);

    /**
     * @return the order category
     */
    int getOrderCategory();

    /**
     * @param orderCategory is the order category
     */
    void setOrderCategory(int orderCategory);

    /**
     * @return the routing account
     */
    String getRoutingAc();

    /**
     * @param routingAc is the routing account
     */
    void setRoutingAc(String routingAc);

    /**
     * @return the routing account reference
     */
    String getRoutingAccRef();

    /**
     * @param routingAccRef is the routing account reference
     */
    void setRoutingAccRef(String routingAccRef);

    /**
     * @return the remote clord id
     */
    String getRemoteClOrdID();

    /**
     * @param remoteClOrdID is the remote clord id
     */
    void setRemoteClOrdID(String remoteClOrdID);

    /**
     * @return the original remote clord id
     */
    String getRemoteOrigClOrdID();

    /**
     * @param remoteOrigClOrdID is the original remote clord id
     */
    void setRemoteOrigClOrdID(String remoteOrigClOrdID);

    /**
     * @return the letsOrdStatus
     */
    int getLetsOrdStatus();

    /**
     * @param letsOrdStatus is the letsOrdStatus
     */
    void setLetsOrdStatus(int letsOrdStatus);

    /**
     * @return the request type
     */
    int getRequestStatus();

    /**
     * @param requestStatus is the request type
     */
    void setRequestStatus(int requestStatus);

    /**
     * @return the ESI order number
     */
    String getESISOrderNo();

    /**
     * @param eSISOrderNo is the ESI order number
     */
    void setESISOrderNo(String eSISOrderNo);

    /**
     * @return the order channel
     */
    int getChannel();

    /**
     * @param channel is the order channel
     */
    void setChannel(int channel);

    /**
     * @return the user id
     */
    String getUserID();

    /**
     * @param userID is the user id
     */
    void setUserID(String userID);

    /**
     * @return the manual request status
     */
    int getManualRequestStatus();

    /**
     * @param manualRequestStatus is the manual request status
     */
    void setManualRequestStatus(int manualRequestStatus);

    /**
     * @return the tw order id
     */
    String getTwOrderId();

    /**
     * @param tworderid is the tw order id
     */
    void setTwOrderId(String tworderid);

    /**
     * @return the order value
     */
    double getOrdValue();

    /**
     * @param ordValue is the order value
     */
    void setOrdValue(double ordValue);

    /**
     * @return the message id
     */
    String getMessageID();

    /**
     * @param messageID is the message id
     */
    void setMessageID(String messageID);

    /**
     * @return the max price
     */
    double getMaxPrice();

    /**
     * @param maxPrice is the max price
     */
    void setMaxPrice(double maxPrice);

    /**
     * @return the broker id
     */
    String getBrokerID();

    /**
     * @param brokerID is the broker id
     */
    void setBrokerID(String brokerID);

    /**
     * @return the broker fix id
     */
    String getBrokerFIXID();

    /**
     * @param brokerFIXID is the broker fix id
     */
    void setBrokerFIXID(String brokerFIXID);

    /**
     * @return the sequence number
     */
    int getSequenceNo();

    /**
     * @param sequenceNo is the sequence number
     */
    void setSequenceNo(int sequenceNo);

    /**
     * @return is allow small orders
     */
    int getAllowSmallOrder();

    /**
     * @param allowSmallOrder set allow small orders
     */
    void setAllowSmallOrder(int allowSmallOrder);

    /**
     * @return the fix string
     */
    String getFixString();

    /**
     * @param fixString is the fix string
     */
    void setFixString(String fixString);

    /**
     * @return the connection session id
     */
    String getConnectionSid();

    /**
     * @param connectionSid is the connection session id
     */
    void setConnectionSid(String connectionSid);

    /**
     * @return the remote symbol
     */
    String getRemoteSymbol();

    /**
     * @param remoteSymbol is the remote symbol
     */
    void setRemoteSymbol(String remoteSymbol);

    /**
     * @return the remote account number
     */
    String getRemoteAccountNumber();

    /**
     * @param remoteAccountNumber is the remote account number
     */
    void setRemoteAccountNumber(String remoteAccountNumber);

    /**
     * @return the remote execution destination
     */
    String getRemoteExDestination();

    /**
     * @param remoteExDestination is the remote execution destination
     */
    void setRemoteExDestination(String remoteExDestination);

    /**
     * @return the remote order exchange
     */
    String getRemoteExchange();

    /**
     * @param remoteExchange is the remote order exchange
     */
    void setRemoteExchange(String remoteExchange);

    /**
     * @return the remote order security id
     */
    String getRemoteSecurityID();

    /**
     * @param remoteSecurityID is the remote order security id
     */
    void setRemoteSecurityID(String remoteSecurityID);

    /**
     * @return the institution id
     */
    String getInstitutionID();

    /**
     * @param institutionID is the institution id
     */
    void setInstitutionID(String institutionID);

    /**
     * @return the desk order type
     */
    int getDeskOrderType();

    /**
     * @param deskOrderType is the desk order type
     */
    void setDeskOrderType(int deskOrderType);

    /**
     * @return the desk order action
     */
    int getDeskOrderAction();

    /**
     * @param deskOrderAction is the desk order action
     */
    void setDeskOrderAction(int deskOrderAction);

    /**
     * @return the mubasher execution id
     */
    int getMubExecID();

    /**
     * @param mubExecID is the mubasher execution id
     */
    void setMubExecID(int mubExecID);

    /**
     * @return the previous order status
     */
    char getPrevOrdStatus();

    /**
     * @param prevOrdStatus is the previous order status
     */
    void setPrevOrdStatus(char prevOrdStatus);

    /**
     * @return the exchange order status
     */
    char getExgOrdStatus();

    /**
     * @param exgOrdStatus is the exchange order status
     */
    void setExgOrdStatus(char exgOrdStatus);

    /**
     * @return the settlement date
     */
    Date getSettlementDate();

    /**
     * FIX protocol tag 64
     *
     * @param settlementDate is the settlement date
     */
    void setSettlementDate(Date settlementDate);

    /**
     * @return the reuter code of the symbol
     */
    String getReuterCode();

    /**
     * @param reuterCode is the symbol reuter code
     */
    void setReuterCode(String reuterCode);

    /**
     * @return the isin code of the symbol
     */
    String getIsinCode();

    /**
     * @param isinCode is the symbol isin code
     */
    void setIsinCode(String isinCode);

    /**
     * @return the maturity date
     */
    String getMaturityDate();

    /**
     * @param maturityDate is the maturity date
     */
    void setMaturityDate(String maturityDate);

    /**
     * @return the exchange symbol code
     */
    String getExchangeSymbol();

    /**
     * @param exchangeSymbol is the exchange symbol
     */
    void setExchangeSymbol(String exchangeSymbol);

    /**
     * @return the order number
     */
    String getOrderNo();

    /**
     * @param orderNo is the order number
     */
    void setOrderNo(String orderNo);

    /**
     * Get the fix session identifier
     *
     * @return the fix session identifier
     */
    String getFIXSessionIdentifier();

    /**
     * Set the fix session identifier
     *
     * @param fixSessionIdentifier is the fix session identifier
     */
    void setFIXSessionIdentifier(String fixSessionIdentifier);

    /**
     * Get this is a desk order
     *
     * @return the the desk order or not
     */
    boolean isDeskOrder();

    /**
     * Set this is a desk order
     *
     * @param deskOrder
     */
    void setDeskOrder(boolean deskOrder);

    /**
     * Is this is a special amend replace order
     *
     * @return special amend replace or not
     */
    boolean isSpecialOrderReplace();

    /**
     * Set this is a special amend replace request
     *
     * @param specialOrderReplace
     */
    void setSpecialOrderReplace(boolean specialOrderReplace);

    /**
     * Get the price factor
     *
     * @return the price factor
     */
    double getPriceFactor();

    /**
     * Set the price factor
     *
     * @param priceFactor is the price factor
     */
    void setPriceFactor(double priceFactor);

    /**
     * Get the transact time for login
     *
     * @return
     */
    String getTransactTimeForLogin();

    /**
     * Set the transact time for login
     *
     * @param transactTimeForLogin
     */
    void setTransactTimeForLogin(String transactTimeForLogin);

    /**
     * This will give enum values of the message types used in the fix messages
     */
    enum MessageType {
        REJECT("3"), EXECUTION_REPORT("8"), ORDER_CANCEL_REJECT("9"), NEW_ORDER_SINGLE("D"), ORDER_CANCEL_REQUEST("F"),
        ORDER_CANCEL_REPLACE("G"), TRADING_SESSION_STATUS_REQUEST("g"), TRADING_SESSION_STATUS("h"), ACCEPT("ACCEPT"),
        AMEND_ACCEPT("AMEND_ACCEPT"), CANCEL_ACCEPT("CANCEL_ACCEPT");

        private String code;

        MessageType(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public static MessageType getEnum(String code) {
            switch (code) {
                case "3":
                    return REJECT;
                case "8":
                    return EXECUTION_REPORT;
                case "9":
                    return ORDER_CANCEL_REJECT;
                case "D":
                    return NEW_ORDER_SINGLE;
                case "F":
                    return ORDER_CANCEL_REQUEST;
                case "G":
                    return ORDER_CANCEL_REPLACE;
                case "g":
                    return TRADING_SESSION_STATUS_REQUEST;
                case "h":
                    return TRADING_SESSION_STATUS;
                case "ACCEPT":
                    return ACCEPT;
                case "AMEND_ACCEPT":
                    return AMEND_ACCEPT;
                case "CANCEL_ACCEPT":
                    return CANCEL_ACCEPT;
                default:
                    return null;
            }
        }
    }
}
