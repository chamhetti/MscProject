package lk.ac.ucsc.oms.fix.impl.beans;

import lk.ac.ucsc.oms.fix.api.beans.FixOrderInterface;

import java.util.Date;


public class FixOrderBean implements FixOrderInterface {
    private String portfolioNo;
    private double avgPx;
    private String fixVersion;
    private String clordID;
    private double commission;
    private double cumQty;
    private String currency;
    private String execID;
    private String execInst;
    private int execTransType;
    private int handlInst;
    private String securityIDSource;
    private double lastPx;
    private double lastShares;
    private String msgType = "0";
    private String orderID;
    private double orderQty;
    private char ordStatus = 'X';
    private char ordType;
    private String origClordID;
    private boolean possDupFlag;
    private double price;
    private String securityID;
    private String senderCompID;
    private String senderSubID;
    private char side;
    private String symbol;
    private String targetCompID;
    private String targetSubID;
    private String text;
    private int timeInForce;
    private String transactTime;
    private int settlmntType;
    private double stopPx;
    private String exDestination;
    private int ordRejReason;
    private double minQty;
    private double maxFloor;
    private String onBehalfOfCompID;
    private String onBehalfOfSubID;
    private Date expireTime;
    private String senderLocationID;
    private char execType;
    private double leavesQty;
    private String securityType;
    private String maturityMonthYear;
    private int putOrCall;
    private double strikePrice;
    private String securityExchange;
    private char subscriptionRequestType;
    private String underlyingSymbol;
    private String tradSesReqID;
    private boolean solicitedFlag;
    private Date expireDate;
    private char cxlRejResponseTo;
    private int accountType;
    private Date createdDate;
    private int orderCategory;
    private String routingAc;
    private String routingAccRef;
    private String remoteClOrdID;
    private String remoteOrigClOrdID;
    private int letsOrdStatus;
    private int requestStatus;
    private String eSISOrderNo;
    private int channel;
    private String userID;
    private int manualRequestStatus;
    private String tworderid;
    private double ordValue;
    private String messageID;
    private double maxPrice;
    private String brokerID;
    private String brokerFIXID;
    private int sequenceNo;
    private int allowSmallOrder;
    private String fixString;
    private String connectionSid;
    private Date sendingTime;
    private String tradingSessionID;
    private String tradSesMethod;
    private int tradSesStatus;
    private String remoteSymbol;
    private String remoteAccountNumber;
    private String remoteExDestination;
    private String remoteExchange;
    private String remoteSecurityID;
    private String institutionID;
    private int deskOrderType;
    private int deskOrderAction;
    private int mubExecID;
    private char prevOrdStatus;
    private char exgOrdStatus;
    private Date settlementDate;
    private String reuterCode;
    private String isinCode;
    private String maturityDate;
    private String exchangeSymbol;
    private String orderNo;
    private String fixSessionIdentifier;
    private boolean deskOrder;
    private boolean specialOrderReplace;
    private double priceFactor;
    private String transactTimeForLogin;

    private static final int HASH_CONST = 31;
    private static final int HASH_MIN = 32;

    /**
     * @return
     */
    @Override
    public String getPortfolioNo() {
        return portfolioNo;
    }

    /**
     * FIX protocol tag 1
     *
     * @param portfolioNo
     */
    @Override
    public void setPortfolioNo(String portfolioNo) {
        this.portfolioNo = portfolioNo;
    }

    /**
     * @return
     */
    @Override
    public double getAvgPx() {
        return avgPx;
    }

    /**
     * FIX protocol tag 6
     *
     * @param avgPx
     */
    @Override
    public void setAvgPx(double avgPx) {
        this.avgPx = avgPx;
    }

    /**
     * @return
     */
    @Override
    public String getFixVersion() {
        return fixVersion;
    }

    /**
     * FIX protocol tag 8
     *
     * @param fixVersion
     */
    @Override
    public void setFixVersion(String fixVersion) {
        this.fixVersion = fixVersion;
    }

    /**
     * @return
     */
    @Override
    public String getClordID() {
        return clordID;
    }

    /**
     * FIX protocol tag 11
     *
     * @param clordID
     */
    @Override
    public void setClordID(String clordID) {
        this.clordID = clordID;
    }

    /**
     * @return
     */
    @Override
    public double getCommission() {
        return commission;
    }

    /**
     * FIX protocol tag 12
     *
     * @param commission
     */
    @Override
    public void setCommission(double commission) {
        this.commission = commission;
    }

    /**
     * @return
     */
    @Override
    public double getCumQty() {
        return cumQty;
    }

    /**
     * FIX protocol tag 14
     *
     * @param cumQty
     */
    @Override
    public void setCumQty(double cumQty) {
        this.cumQty = cumQty;
    }

    /**
     * @return
     */
    @Override
    public String getCurrency() {
        return currency;
    }

    /**
     * FIX protocol tag 15
     *
     * @param currency
     */
    @Override
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * @return
     */
    @Override
    public String getExecID() {
        return execID;
    }

    /**
     * FIX protocol tag 17
     *
     * @param execID
     */
    @Override
    public void setExecID(String execID) {
        this.execID = execID;
    }

    /**
     * @return
     */
    @Override
    public String getExecInst() {
        return execInst;
    }

    /**
     * FIX protocol tag 18
     *
     * @param execInst
     */
    @Override
    public void setExecInst(String execInst) {
        this.execInst = execInst;
    }

    /**
     * @return
     */
    @Override
    public int getExecTransType() {
        return execTransType;
    }

    /**
     * FIX protocol tag 20
     *
     * @param execTransType
     */
    @Override
    public void setExecTransType(int execTransType) {
        this.execTransType = execTransType;
    }

    /**
     * @return
     */
    @Override
    public int getHandlInst() {
        return handlInst;
    }

    /**
     * FIX protocol tag 21
     *
     * @param handlInst
     */
    @Override
    public void setHandlInst(int handlInst) {
        this.handlInst = handlInst;
    }

    /**
     * @return
     */
    @Override
    public String getSecurityIDSource() {
        return securityIDSource;
    }

    /**
     * FIX protocol tag 22
     *
     * @param securityIDSource
     */
    @Override
    public void setSecurityIDSource(String securityIDSource) {
        this.securityIDSource = securityIDSource;
    }

    /**
     * @return
     */
    @Override
    public double getLastPx() {
        return lastPx;
    }

    /**
     * FIX protocol tag 31
     *
     * @param lastPx
     */
    @Override
    public void setLastPx(double lastPx) {
        this.lastPx = lastPx;
    }

    /**
     * @return
     */
    @Override
    public double getLastShares() {
        return lastShares;
    }

    /**
     * FIX protocol tag 32
     *
     * @param lastShares
     */
    @Override
    public void setLastShares(double lastShares) {
        this.lastShares = lastShares;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }


    /**
     * @return
     */
    @Override
    public MessageType getMessageType() {
        return MessageType.getEnum(msgType);
    }

    /**
     * @param messageType
     */
    @Override
    public void setMessageType(MessageType messageType) {
        this.msgType = messageType.getCode();
    }

    /**
     * @return
     */
    @Override
    public String getOrderID() {
        return orderID;
    }

    /**
     * @param orderID
     */
    @Override
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    /**
     * FIX protocol tag 37
     *
     * @return
     */
    @Override
    public double getOrderQty() {
        return orderQty;
    }

    /**
     * FIX protocol tag 38
     *
     * @param orderQty
     */
    @Override
    public void setOrderQty(double orderQty) {
        this.orderQty = orderQty;
    }

    /**
     * @return
     */
    @Override
    public char getOrdStatus() {
        return ordStatus;
    }

    /**
     * FIX protocol tag 39
     *
     * @param ordStatus
     */
    @Override
    public void setOrdStatus(char ordStatus) {
        this.ordStatus = ordStatus;
    }

    /**
     * @return
     */
    @Override
    public char getOrdType() {
        return ordType;
    }

    /**
     * FIX protocol tag 40
     *
     * @param ordType
     */
    @Override
    public void setOrdType(char ordType) {
        this.ordType = ordType;
    }

    /**
     * @return
     */
    @Override
    public String getOrigClordID() {
        return origClordID;
    }

    /**
     * FIX protocol tag 41
     *
     * @param origClordID
     */
    @Override
    public void setOrigClordID(String origClordID) {
        this.origClordID = origClordID;
    }

    /**
     * @return
     */
    @Override
    public boolean getPossDupFlag() {
        return possDupFlag;
    }

    /**
     * FIX protocol tag 43 PossDupFlag
     *
     * @param possDupFlag
     */
    @Override
    public void setPossDupFlag(boolean possDupFlag) {
        this.possDupFlag = possDupFlag;
    }

    /**
     * @return
     */
    @Override
    public double getPrice() {
        return price;
    }

    /**
     * FIX protocol tag 44 Limit Price
     *
     * @param price
     */
    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return
     */
    @Override
    public String getSecurityID() {
        return securityID;
    }

    /**
     * FIX protocol tag 48
     *
     * @param securityID
     */
    @Override
    public void setSecurityID(String securityID) {
        this.securityID = securityID;
    }

    /**
     * @return
     */
    @Override
    public String getSenderCompID() {
        return senderCompID;
    }

    /**
     * FIX protocol tag 49
     *
     * @param senderCompID
     */
    @Override
    public void setSenderCompID(String senderCompID) {
        this.senderCompID = senderCompID;
    }

    /**
     * @return
     */
    @Override
    public String getSenderSubID() {
        return senderSubID;
    }

    /**
     * FIX protocol tag 50
     *
     * @param senderSubID
     */
    @Override
    public void setSenderSubID(String senderSubID) {
        this.senderSubID = senderSubID;
    }

    /**
     * @return
     */
    @Override
    public char getSide() {
        return side;
    }

    /**
     * FIX protocol tag 54
     *
     * @param side
     */
    @Override
    public void setSide(char side) {
        this.side = side;
    }

    /**
     * @return
     */
    @Override
    public String getSymbol() {
        return symbol;
    }

    /**
     * FIX protocol tag 55
     *
     * @param symbol
     */
    @Override
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * @return
     */
    @Override
    public String getTargetCompID() {
        return targetCompID;
    }

    /**
     * FIX protocol tag 56
     *
     * @param targetCompID
     */
    @Override
    public void setTargetCompID(String targetCompID) {
        this.targetCompID = targetCompID;
    }

    /**
     * @return
     */
    @Override
    public String getTargetSubID() {
        return targetSubID;
    }

    /**
     * FIX protocol tag 57
     *
     * @param targetSubID
     */
    @Override
    public void setTargetSubID(String targetSubID) {
        this.targetSubID = targetSubID;
    }

    /**
     * @return
     */
    @Override
    public String getText() {
        return text;
    }

    /**
     * FIX protocol tag 58
     *
     * @param text
     */
    @Override
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return
     */
    @Override
    public int getTimeInForce() {
        return timeInForce;
    }

    /**
     * FIX protocol tag 59
     *
     * @param timeInForce
     */
    @Override
    public void setTimeInForce(int timeInForce) {
        this.timeInForce = timeInForce;
    }

    /**
     * @return
     */
    @Override
    public String getTransactTime() {
        return transactTime;
    }

    /**
     * FIX protocol tag 60
     *
     * @param transactTime
     */
    @Override
    public void setTransactTime(String transactTime) {
        this.transactTime = transactTime;
    }

    /**
     * @return
     */
    @Override
    public int getSettlementType() {
        return settlmntType;
    }

    /**
     * FIX protocol tag 63
     *
     * @param settlementType
     */
    @Override
    public void setSettlementType(int settlementType) {
        this.settlmntType = settlementType;
    }

    /**
     * @return
     */
    @Override
    public double getStopPx() {
        return stopPx;
    }

    /**
     * FIX protocol tag 99
     *
     * @param stopPx
     */
    @Override
    public void setStopPx(double stopPx) {
        this.stopPx = stopPx;
    }

    /**
     * @return
     */
    @Override
    public String getExDestination() {
        return exDestination;
    }

    /**
     * FIX protocol tag 100
     *
     * @param exDestination
     */
    @Override
    public void setExDestination(String exDestination) {
        this.exDestination = exDestination;
    }

    /**
     * @return
     */
    @Override
    public int getOrdRejReason() {
        return ordRejReason;
    }

    /**
     * FIX protocol tag 102
     *
     * @param ordRejReason
     */
    @Override
    public void setOrdRejReason(int ordRejReason) {
        this.ordRejReason = ordRejReason;
    }

    /**
     * @return
     */
    @Override
    public double getMinQty() {
        return minQty;
    }

    /**
     * FIX protocol tag 110
     *
     * @param minQty
     */
    @Override
    public void setMinQty(double minQty) {
        this.minQty = minQty;
    }

    /**
     * @return
     */
    @Override
    public double getMaxFloor() {
        return maxFloor;
    }

    /**
     * FIX protocol tag 111
     *
     * @param maxFloor
     */
    @Override
    public void setMaxFloor(double maxFloor) {
        this.maxFloor = maxFloor;
    }

    /**
     * @return
     */
    @Override
    public String getOnBehalfOfCompID() {
        return onBehalfOfCompID;
    }

    /**
     * FIX protocol tag 115
     *
     * @param onBehalfOfCompID
     */
    @Override
    public void setOnBehalfOfCompID(String onBehalfOfCompID) {
        this.onBehalfOfCompID = onBehalfOfCompID;
    }

    /**
     * @return
     */
    @Override
    public String getOnBehalfOfSubID() {
        return onBehalfOfSubID;
    }

    /**
     * FIX protocol tag 116
     *
     * @param onBehalfOfSubID
     */
    @Override
    public void setOnBehalfOfSubID(String onBehalfOfSubID) {
        this.onBehalfOfSubID = onBehalfOfSubID;
    }

    /**
     * @return
     */
    @Override
    public Date getExpireTime() {
        return expireTime;
    }

    /**
     * FIX protocol tag 126
     *
     * @param expireTime
     */
    @Override
    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * @return
     */
    @Override
    public String getSenderLocationID() {
        return senderLocationID;
    }

    /**
     * @param senderLocationID
     */
    @Override
    public void setSenderLocationID(String senderLocationID) {
        this.senderLocationID = senderLocationID;
    }

    /**
     * @return
     */
    @Override
    public char getExecType() {
        return execType;
    }

    /**
     * FIX protocol tag 150
     *
     * @param execType
     */
    @Override
    public void setExecType(char execType) {
        this.execType = execType;
    }

    /**
     * @return
     */
    @Override
    public double getLeavesQty() {
        return leavesQty;
    }

    /**
     * FIX protocol tag 151
     *
     * @param leavesQty
     */
    @Override
    public void setLeavesQty(double leavesQty) {
        this.leavesQty = leavesQty;
    }

    /**
     * @return
     */
    @Override
    public String getSecurityType() {
        return securityType;
    }

    /**
     * FIX protocol tag 167
     *
     * @param securityType
     */
    @Override
    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    /**
     * @return
     */
    @Override
    public String getMaturityMonthYear() {
        return maturityMonthYear;
    }

    /**
     * FIX protocol tag 200
     *
     * @param maturityMonthYear
     */
    @Override
    public void setMaturityMonthYear(String maturityMonthYear) {
        this.maturityMonthYear = maturityMonthYear;
    }

    /**
     * @return
     */
    @Override
    public int getPutOrCall() {
        return putOrCall;
    }

    /**
     * FIX protocol tag 201
     *
     * @param putOrCall
     */
    @Override
    public void setPutOrCall(int putOrCall) {
        this.putOrCall = putOrCall;
    }

    /**
     * @return
     */
    @Override
    public double getStrikePrice() {
        return strikePrice;
    }

    /**
     * @param strikePrice
     */
    @Override
    public void setStrikePrice(double strikePrice) {
        this.strikePrice = strikePrice;
    }

    /**
     * @return
     */
    @Override
    public String getSecurityExchange() {
        return securityExchange;
    }

    /**
     * FIX protocol tag 207
     *
     * @param securityExchange
     */
    @Override
    public void setSecurityExchange(String securityExchange) {
        this.securityExchange = securityExchange;
    }

    /**
     * @return
     */
    @Override
    public char getSubscriptionRequestType() {
        return subscriptionRequestType;
    }

    /**
     * FIX protocol tag 263
     *
     * @param subscriptionRequestType
     */
    @Override
    public void setSubscriptionRequestType(char subscriptionRequestType) {
        this.subscriptionRequestType = subscriptionRequestType;
    }

    /**
     * @return
     */
    @Override
    public String getUnderlyingSymbol() {
        return underlyingSymbol;
    }

    /**
     * FIX protocol tag 311
     *
     * @param underlyingSymbol
     */
    @Override
    public void setUnderlyingSymbol(String underlyingSymbol) {
        this.underlyingSymbol = underlyingSymbol;
    }

    /**
     * @return
     */
    @Override
    public String getTradSesReqID() {
        return tradSesReqID;
    }

    /**
     * FIX protocol tag 335
     *
     * @param tradSesReqID
     */
    @Override
    public void setTradSesReqID(String tradSesReqID) {
        this.tradSesReqID = tradSesReqID;
    }

    /**
     * @return
     */
    @Override
    public boolean getSolicitedFlag() {
        return solicitedFlag;
    }

    /**
     * FIX protocol tag 377
     *
     * @param solicitedFlag
     */
    @Override
    public void setSolicitedFlag(boolean solicitedFlag) {
        this.solicitedFlag = solicitedFlag;
    }

    /**
     * @return
     */
    @Override
    public Date getExpireDate() {
        return expireDate;
    }

    /**
     * FIX protocol tag 432
     *
     * @param expireDate
     */
    @Override
    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    /**
     * @return
     */
    @Override
    public char getCxlRejResponseTo() {
        return cxlRejResponseTo;
    }

    /**
     * FIX protocol tag 434
     *
     * @param cxlRejResponseTo
     */
    @Override
    public void setCxlRejResponseTo(char cxlRejResponseTo) {
        this.cxlRejResponseTo = cxlRejResponseTo;
    }

    /**
     * @return
     */
    @Override
    public int getAccountType() {
        return accountType;
    }

    /**
     * FIX protocol tag 581
     *
     * @param accountType
     */
    @Override
    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    /**
     * @return
     */
    @Override
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * FIX protocol tag 804
     *
     * @param createdDate
     */
    @Override
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return
     */
    @Override
    public int getOrderCategory() {
        return orderCategory;
    }

    /**
     * @param orderCategory
     */
    @Override
    public void setOrderCategory(int orderCategory) {
        this.orderCategory = orderCategory;
    }

    /**
     * @return
     */
    @Override
    public String getRoutingAc() {
        return routingAc;
    }

    /**
     * @param routingAc
     */
    @Override
    public void setRoutingAc(String routingAc) {
        this.routingAc = routingAc;
    }

    /**
     * @return
     */
    @Override
    public String getRoutingAccRef() {
        return routingAccRef;
    }

    /**
     * @param routingAccRef
     */
    @Override
    public void setRoutingAccRef(String routingAccRef) {
        this.routingAccRef = routingAccRef;
    }

    /**
     * @return
     */
    @Override
    public String getRemoteClOrdID() {
        return remoteClOrdID;
    }

    /**
     * @param remoteClOrdID
     */
    @Override
    public void setRemoteClOrdID(String remoteClOrdID) {
        this.remoteClOrdID = remoteClOrdID;
    }

    /**
     * @return
     */
    @Override
    public String getRemoteOrigClOrdID() {
        return remoteOrigClOrdID;
    }

    /**
     * @param remoteOrigClOrdID
     */
    @Override
    public void setRemoteOrigClOrdID(String remoteOrigClOrdID) {
        this.remoteOrigClOrdID = remoteOrigClOrdID;
    }

    /**
     * @return
     */
    @Override
    public int getLetsOrdStatus() {
        return letsOrdStatus;
    }

    /**
     * @param letsOrdStatus
     */
    @Override
    public void setLetsOrdStatus(int letsOrdStatus) {
        this.letsOrdStatus = letsOrdStatus;
    }

    /**
     * @return
     */
    @Override
    public int getRequestStatus() {
        return requestStatus;
    }

    /**
     * @param requestStatus
     */
    @Override
    public void setRequestStatus(int requestStatus) {
        this.requestStatus = requestStatus;
    }

    /**
     * @return
     */
    @Override
    public String getESISOrderNo() {
        return eSISOrderNo;
    }

    /**
     * @param eSISOrderNo
     */
    @Override
    public void setESISOrderNo(String eSISOrderNo) {
        this.eSISOrderNo = eSISOrderNo;
    }

    /**
     * @return
     */
    @Override
    public int getChannel() {
        return channel;
    }

    /**
     * @param channel
     */
    @Override
    public void setChannel(int channel) {
        this.channel = channel;
    }

    /**
     * @return
     */
    @Override
    public String getUserID() {
        return userID;
    }

    /**
     * @param userID
     */
    @Override
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * @return
     */
    @Override
    public int getManualRequestStatus() {
        return manualRequestStatus;
    }

    /**
     * @param manualRequestStatus
     */
    @Override
    public void setManualRequestStatus(int manualRequestStatus) {
        this.manualRequestStatus = manualRequestStatus;
    }

    /**
     * @return
     */
    @Override
    public String getTwOrderId() {
        return tworderid;
    }

    /**
     * @param tworderid
     */
    @Override
    public void setTwOrderId(String tworderid) {
        this.tworderid = tworderid;
    }

    /**
     * @return
     */
    @Override
    public double getOrdValue() {
        return ordValue;
    }

    /**
     * @param ordValue
     */
    @Override
    public void setOrdValue(double ordValue) {
        this.ordValue = ordValue;
    }

    /**
     * @return
     */
    @Override
    public String getMessageID() {
        return messageID;
    }

    /**
     * @param messageID
     */
    @Override
    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    /**
     * @return
     */
    @Override
    public double getMaxPrice() {
        return maxPrice;
    }

    /**
     * @param maxPrice
     */
    @Override
    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    /**
     * @return
     */
    @Override
    public String getBrokerID() {
        return brokerID;
    }

    /**
     * @param brokerID
     */
    @Override
    public void setBrokerID(String brokerID) {
        this.brokerID = brokerID;
    }

    /**
     * @return
     */
    @Override
    public String getBrokerFIXID() {
        return brokerFIXID;
    }

    /**
     * @param brokerFIXID
     */
    @Override
    public void setBrokerFIXID(String brokerFIXID) {
        this.brokerFIXID = brokerFIXID;
    }

    /**
     * @return
     */
    @Override
    public int getSequenceNo() {
        return sequenceNo;
    }

    /**
     * @param sequenceNo
     */
    @Override
    public void setSequenceNo(int sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    /**
     * @return
     */
    @Override
    public int getAllowSmallOrder() {
        return allowSmallOrder;
    }

    /**
     * @param allowSmallOrder
     */
    @Override
    public void setAllowSmallOrder(int allowSmallOrder) {
        this.allowSmallOrder = allowSmallOrder;
    }

    /**
     * @return
     */
    @Override
    public String getFixString() {
        return fixString;
    }

    /**
     * @param fixString
     */
    @Override
    public void setFixString(String fixString) {
        this.fixString = fixString;
    }

    /**
     * @return
     */
    @Override
    public String getConnectionSid() {
        return connectionSid;
    }

    /**
     * @param connectionSid
     */
    @Override
    public void setConnectionSid(String connectionSid) {
        this.connectionSid = connectionSid;
    }

    /**
     * @return
     */
    @Override
    public String getRemoteSymbol() {
        return remoteSymbol;
    }

    /**
     * @param remoteSymbol
     */
    @Override
    public void setRemoteSymbol(String remoteSymbol) {
        this.remoteSymbol = remoteSymbol;
    }

    /**
     * @return
     */
    @Override
    public String getRemoteAccountNumber() {
        return remoteAccountNumber;
    }

    /**
     * @param remoteAccountNumber
     */
    @Override
    public void setRemoteAccountNumber(String remoteAccountNumber) {
        this.remoteAccountNumber = remoteAccountNumber;
    }

    /**
     * @return
     */
    @Override
    public String getRemoteExDestination() {
        return remoteExDestination;
    }

    /**
     * @param remoteExDestination
     */
    @Override
    public void setRemoteExDestination(String remoteExDestination) {
        this.remoteExDestination = remoteExDestination;
    }

    /**
     * @return
     */
    @Override
    public String getRemoteExchange() {
        return remoteExchange;
    }

    /**
     * @param remoteExchange
     */
    @Override
    public void setRemoteExchange(String remoteExchange) {
        this.remoteExchange = remoteExchange;
    }

    /**
     * @return
     */
    @Override
    public String getRemoteSecurityID() {
        return remoteSecurityID;
    }

    /**
     * @param remoteSecurityID
     */
    @Override
    public void setRemoteSecurityID(String remoteSecurityID) {
        this.remoteSecurityID = remoteSecurityID;
    }

    /**
     * @return
     */
    @Override
    public Date getSendingTime() {
        return sendingTime;
    }

    /**
     * FIX protocol tag 52
     *
     * @param sendingTime
     */
    @Override
    public void setSendingTime(Date sendingTime) {
        this.sendingTime = sendingTime;
    }

    /**
     * @return
     */
    @Override
    public String getTradingSessionID() {
        return tradingSessionID;
    }

    /**
     * FIX protocol tag 336
     *
     * @param tradingSessionID
     */
    @Override
    public void setTradingSessionID(String tradingSessionID) {
        this.tradingSessionID = tradingSessionID;
    }

    /**
     * @return
     */
    @Override
    public String getTradSesMethod() {
        return tradSesMethod;
    }

    /**
     * @param tradSesMethod
     */
    @Override
    public void setTradSesMethod(String tradSesMethod) {
        this.tradSesMethod = tradSesMethod;
    }

    /**
     * @return
     */
    @Override
    public int getTradSesStatus() {
        return tradSesStatus;
    }

    /**
     * FIX protocol tag 340
     *
     * @param tradSesStatus
     */
    @Override
    public void setTradSesStatus(int tradSesStatus) {
        this.tradSesStatus = tradSesStatus;
    }

    /**
     * @return
     */
    @Override
    public String getInstitutionID() {
        return institutionID;
    }

    /**
     * @param institutionID
     */
    @Override
    public void setInstitutionID(String institutionID) {
        this.institutionID = institutionID;
    }

    /**
     * @return
     */
    @Override
    public int getDeskOrderType() {
        return deskOrderType;
    }

    /**
     * @param deskOrderType
     */
    @Override
    public void setDeskOrderType(int deskOrderType) {
        this.deskOrderType = deskOrderType;
    }

    /**
     * @return
     */
    @Override
    public int getDeskOrderAction() {
        return deskOrderAction;
    }

    /**
     * @param deskOrderAction
     */
    @Override
    public void setDeskOrderAction(int deskOrderAction) {
        this.deskOrderAction = deskOrderAction;
    }

    /**
     * @return
     */
    @Override
    public int getMubExecID() {
        return mubExecID;
    }

    /**
     * @param mubExecID
     */
    @Override
    public void setMubExecID(int mubExecID) {
        this.mubExecID = mubExecID;
    }

    /**
     * @return
     */
    @Override
    public char getPrevOrdStatus() {
        return prevOrdStatus;
    }

    /**
     * @param prevOrdStatus
     */
    @Override
    public void setPrevOrdStatus(char prevOrdStatus) {
        this.prevOrdStatus = prevOrdStatus;
    }

    /**
     * @return
     */
    @Override
    public char getExgOrdStatus() {
        return exgOrdStatus;
    }

    /**
     * @param exgOrdStatus
     */
    @Override
    public void setExgOrdStatus(char exgOrdStatus) {
        this.exgOrdStatus = exgOrdStatus;
    }

    /**
     * @return
     */
    @Override
    public Date getSettlementDate() {
        return settlementDate;
    }

    /**
     * @param settlementDate
     */
    @Override
    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    /**
     * @return the reuter code of the symbol
     */
    @Override
    public String getReuterCode() {
        return reuterCode;
    }

    /**
     * @param reuterCode is the symbol reuter code
     */
    @Override
    public void setReuterCode(String reuterCode) {
        this.reuterCode = reuterCode;
    }

    /**
     * @return the isin code of the symbol
     */
    @Override
    public String getIsinCode() {
        return isinCode;
    }

    /**
     * @param isinCode is the symbol isin code
     */
    @Override
    public void setIsinCode(String isinCode) {
        this.isinCode = isinCode;
    }

    /**
     * @return the maturity date
     */
    @Override
    public String getMaturityDate() {
        return maturityDate;
    }

    /**
     * @param maturityDate is the maturity date
     */
    @Override
    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
    }

    /**
     * @return the exchange symbol code
     */
    @Override
    public String getExchangeSymbol() {
        return exchangeSymbol;
    }

    /**
     * @param exchangeSymbol is the exchange symbol
     */
    @Override
    public void setExchangeSymbol(String exchangeSymbol) {
        this.exchangeSymbol = exchangeSymbol;
    }

    /**
     * @return the order number
     */
    @Override
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * @param orderNo is the order number
     */
    @Override
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public String getFIXSessionIdentifier() {
        return fixSessionIdentifier;
    }

    @Override
    public void setFIXSessionIdentifier(String fixSessionIdentifier) {
        this.fixSessionIdentifier = fixSessionIdentifier;
    }

    @Override
    public boolean isDeskOrder() {
        return deskOrder;
    }

    @Override
    public void setDeskOrder(boolean deskOrder) {
        this.deskOrder = deskOrder;
    }

    @Override
    public boolean isSpecialOrderReplace() {
        return specialOrderReplace;
    }

    @Override
    public void setSpecialOrderReplace(boolean specialOrderReplace) {
        this.specialOrderReplace = specialOrderReplace;
    }

    @Override
    public double getPriceFactor() {
        return priceFactor;
    }

    @Override
    public void setPriceFactor(double priceFactor) {
        this.priceFactor = priceFactor;
    }

    @Override
    public String getTransactTimeForLogin() {
        return transactTimeForLogin;
    }

    @Override
    public void setTransactTimeForLogin(String transactTimeForLogin) {
        this.transactTimeForLogin = transactTimeForLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FixOrderBean)) {
            return false;
        }

        FixOrderBean orderBean = (FixOrderBean) o;

        if (accountType != orderBean.accountType) {
            return false;
        }
        if (allowSmallOrder != orderBean.allowSmallOrder) {
            return false;
        }
        if (Double.compare(orderBean.avgPx, avgPx) != 0) {
            return false;
        }
        if (channel != orderBean.channel) {
            return false;
        }
        if (Double.compare(orderBean.commission, commission) != 0) {
            return false;
        }
        if (cumQty != orderBean.cumQty) {
            return false;
        }
        if (cxlRejResponseTo != orderBean.cxlRejResponseTo) {
            return false;
        }
        if (deskOrderAction != orderBean.deskOrderAction) {
            return false;
        }
        if (deskOrderType != orderBean.deskOrderType) {
            return false;
        }
        if (execTransType != orderBean.execTransType) {
            return false;
        }
        if (execType != orderBean.execType) {
            return false;
        }
        if (exgOrdStatus != orderBean.exgOrdStatus) {
            return false;
        }
        if (handlInst != orderBean.handlInst) {
            return false;
        }
        if (Double.compare(orderBean.lastPx, lastPx) != 0) {
            return false;
        }
        if (lastShares != orderBean.lastShares) {
            return false;
        }
        if (leavesQty != orderBean.leavesQty) {
            return false;
        }
        if (letsOrdStatus != orderBean.letsOrdStatus) {
            return false;
        }
        if (manualRequestStatus != orderBean.manualRequestStatus) {
            return false;
        }
        if (maxFloor != orderBean.maxFloor) {
            return false;
        }
        if (Double.compare(orderBean.maxPrice, maxPrice) != 0) {
            return false;
        }
        if (minQty != orderBean.minQty) {
            return false;
        }
        if (msgType != orderBean.msgType) {
            return false;
        }
        if (mubExecID != orderBean.mubExecID) {
            return false;
        }
        if (ordRejReason != orderBean.ordRejReason) {
            return false;
        }
        if (ordStatus != orderBean.ordStatus) {
            return false;
        }
        if (ordType != orderBean.ordType) {
            return false;
        }
        if (Double.compare(orderBean.ordValue, ordValue) != 0) {
            return false;
        }
        if (orderCategory != orderBean.orderCategory) {
            return false;
        }
        if (orderQty != orderBean.orderQty) {
            return false;
        }
        if (possDupFlag != orderBean.possDupFlag) {
            return false;
        }
        if (prevOrdStatus != orderBean.prevOrdStatus) {
            return false;
        }
        if (Double.compare(orderBean.price, price) != 0) {
            return false;
        }
        if (putOrCall != orderBean.putOrCall) {
            return false;
        }
        if (requestStatus != orderBean.requestStatus) {
            return false;
        }
        if (sequenceNo != orderBean.sequenceNo) {
            return false;
        }
        if (settlmntType != orderBean.settlmntType) {
            return false;
        }
        if (side != orderBean.side) {
            return false;
        }
        if (solicitedFlag != orderBean.solicitedFlag) {
            return false;
        }
        if (Double.compare(orderBean.stopPx, stopPx) != 0) {
            return false;
        }
        if (Double.compare(orderBean.strikePrice, strikePrice) != 0) {
            return false;
        }
        if (subscriptionRequestType != orderBean.subscriptionRequestType) {
            return false;
        }
        if (timeInForce != orderBean.timeInForce) {
            return false;
        }
        if (tradSesStatus != orderBean.tradSesStatus) {
            return false;
        }
        if (brokerFIXID != null ? !brokerFIXID.equals(orderBean.brokerFIXID) : orderBean.brokerFIXID != null) {
            return false;
        }
        if (brokerID != null ? !brokerID.equals(orderBean.brokerID) : orderBean.brokerID != null) {
            return false;
        }
        if (clordID != null ? !clordID.equals(orderBean.clordID) : orderBean.clordID != null) {
            return false;
        }
        if (connectionSid != null ? !connectionSid.equals(orderBean.connectionSid) : orderBean.connectionSid != null) {
            return false;
        }
        if (createdDate != null ? !createdDate.equals(orderBean.createdDate) : orderBean.createdDate != null) {
            return false;
        }
        if (currency != null ? !currency.equals(orderBean.currency) : orderBean.currency != null) {
            return false;
        }
        if (eSISOrderNo != null ? !eSISOrderNo.equals(orderBean.eSISOrderNo) : orderBean.eSISOrderNo != null) {
            return false;
        }
        if (exDestination != null ? !exDestination.equals(orderBean.exDestination) : orderBean.exDestination != null) {
            return false;
        }
        if (execID != null ? !execID.equals(orderBean.execID) : orderBean.execID != null) {
            return false;
        }
        if (execInst != null ? !execInst.equals(orderBean.execInst) : orderBean.execInst != null) {
            return false;
        }
        if (expireDate != null ? !expireDate.equals(orderBean.expireDate) : orderBean.expireDate != null) {
            return false;
        }
        if (expireTime != null ? !expireTime.equals(orderBean.expireTime) : orderBean.expireTime != null) {
            return false;
        }
        if (fixString != null ? !fixString.equals(orderBean.fixString) : orderBean.fixString != null) {
            return false;
        }
        if (fixVersion != null ? !fixVersion.equals(orderBean.fixVersion) : orderBean.fixVersion != null) {
            return false;
        }

        if (maturityMonthYear != null ? !maturityMonthYear.equals(orderBean.maturityMonthYear) : orderBean.maturityMonthYear != null) {
            return false;
        }
        if (messageID != null ? !messageID.equals(orderBean.messageID) : orderBean.messageID != null) {
            return false;
        }
        if (onBehalfOfCompID != null ? !onBehalfOfCompID.equals(orderBean.onBehalfOfCompID) : orderBean.onBehalfOfCompID != null) {
            return false;
        }
        if (onBehalfOfSubID != null ? !onBehalfOfSubID.equals(orderBean.onBehalfOfSubID) : orderBean.onBehalfOfSubID != null) {
            return false;
        }
        if (orderID != null ? !orderID.equals(orderBean.orderID) : orderBean.orderID != null) {
            return false;
        }
        if (origClordID != null ? !origClordID.equals(orderBean.origClordID) : orderBean.origClordID != null) {
            return false;
        }
        if (portfolioNo != null ? !portfolioNo.equals(orderBean.portfolioNo) : orderBean.portfolioNo != null) {
            return false;
        }
        if (remoteAccountNumber != null ? !remoteAccountNumber.equals(orderBean.remoteAccountNumber) : orderBean.remoteAccountNumber != null) {
            return false;
        }
        if (remoteClOrdID != null ? !remoteClOrdID.equals(orderBean.remoteClOrdID) : orderBean.remoteClOrdID != null) {
            return false;
        }
        if (remoteExDestination != null ? !remoteExDestination.equals(orderBean.remoteExDestination) : orderBean.remoteExDestination != null) {
            return false;
        }
        if (remoteExchange != null ? !remoteExchange.equals(orderBean.remoteExchange) : orderBean.remoteExchange != null) {
            return false;
        }
        if (remoteOrigClOrdID != null ? !remoteOrigClOrdID.equals(orderBean.remoteOrigClOrdID) : orderBean.remoteOrigClOrdID != null) {
            return false;
        }
        if (remoteSecurityID != null ? !remoteSecurityID.equals(orderBean.remoteSecurityID) : orderBean.remoteSecurityID != null) {
            return false;
        }
        if (remoteSymbol != null ? !remoteSymbol.equals(orderBean.remoteSymbol) : orderBean.remoteSymbol != null) {
            return false;
        }
        if (routingAc != null ? !routingAc.equals(orderBean.routingAc) : orderBean.routingAc != null) {
            return false;
        }
        if (routingAccRef != null ? !routingAccRef.equals(orderBean.routingAccRef) : orderBean.routingAccRef != null) {
            return false;
        }
        if (securityExchange != null ? !securityExchange.equals(orderBean.securityExchange) : orderBean.securityExchange != null) {
            return false;
        }
        if (securityID != null ? !securityID.equals(orderBean.securityID) : orderBean.securityID != null) {
            return false;
        }
        if (securityIDSource != null ? !securityIDSource.equals(orderBean.securityIDSource) : orderBean.securityIDSource != null) {
            return false;
        }
        if (securityType != null ? !securityType.equals(orderBean.securityType) : orderBean.securityType != null) {
            return false;
        }
        if (senderCompID != null ? !senderCompID.equals(orderBean.senderCompID) : orderBean.senderCompID != null) {
            return false;
        }
        if (senderLocationID != null ? !senderLocationID.equals(orderBean.senderLocationID) : orderBean.senderLocationID != null) {
            return false;
        }
        if (sendingTime != null ? !sendingTime.equals(orderBean.sendingTime) : orderBean.sendingTime != null) {
            return false;
        }
        if (settlementDate != null ? !settlementDate.equals(orderBean.settlementDate) : orderBean.settlementDate != null) {
            return false;
        }
        if (symbol != null ? !symbol.equals(orderBean.symbol) : orderBean.symbol != null) {
            return false;
        }
        if (targetCompID != null ? !targetCompID.equals(orderBean.targetCompID) : orderBean.targetCompID != null) {
            return false;
        }
        if (text != null ? !text.equals(orderBean.text) : orderBean.text != null) {
            return false;
        }
        if (tradSesMethod != null ? !tradSesMethod.equals(orderBean.tradSesMethod) : orderBean.tradSesMethod != null) {
            return false;
        }
        if (tradSesReqID != null ? !tradSesReqID.equals(orderBean.tradSesReqID) : orderBean.tradSesReqID != null) {
            return false;
        }
        if (tradingSessionID != null ? !tradingSessionID.equals(orderBean.tradingSessionID) : orderBean.tradingSessionID != null) {
            return false;
        }
        if (transactTime != null ? !transactTime.equals(orderBean.transactTime) : orderBean.transactTime != null) {
            return false;
        }
        if (tworderid != null ? !tworderid.equals(orderBean.tworderid) : orderBean.tworderid != null) {
            return false;
        }
        if (underlyingSymbol != null ? !underlyingSymbol.equals(orderBean.underlyingSymbol) : orderBean.underlyingSymbol != null) {
            return false;
        }
        return !(userID != null ? !userID.equals(orderBean.userID) : orderBean.userID != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = portfolioNo != null ? portfolioNo.hashCode() : 0;
        temp = avgPx != +0.0d ? Double.doubleToLongBits(avgPx) : 0L;
        result = HASH_CONST * result + (int) (temp ^ (temp >>> HASH_MIN));
        result = HASH_CONST * result + (fixVersion != null ? fixVersion.hashCode() : 0);
        result = HASH_CONST * result + (clordID != null ? clordID.hashCode() : 0);
        temp = commission != +0.0d ? Double.doubleToLongBits(commission) : 0L;
        result = HASH_CONST * result + (int) (temp ^ (temp >>> HASH_MIN));
        temp = cumQty != +0.0d ? Double.doubleToLongBits(cumQty) : 0L;
        result = HASH_CONST * result + (int) (temp ^ (temp >>> HASH_MIN));
        result = HASH_CONST * result + (currency != null ? currency.hashCode() : 0);
        result = HASH_CONST * result + (execID != null ? execID.hashCode() : 0);
        result = HASH_CONST * result + (execInst != null ? execInst.hashCode() : 0);
        result = HASH_CONST * result + execTransType;
        result = HASH_CONST * result + handlInst;
        result = HASH_CONST * result + (securityIDSource != null ? securityIDSource.hashCode() : 0);
        temp = lastPx != +0.0d ? Double.doubleToLongBits(lastPx) : 0L;
        result = HASH_CONST * result + (int) (temp ^ (temp >>> HASH_MIN));
        temp = lastShares != +0.0d ? Double.doubleToLongBits(lastShares) : 0L;
        result = HASH_CONST * result + (int) (temp ^ (temp >>> HASH_MIN));
        result = HASH_CONST * result + msgType.hashCode();
        result = HASH_CONST * result + (orderID != null ? orderID.hashCode() : 0);
        temp = orderQty != +0.0d ? Double.doubleToLongBits(orderQty) : 0L;
        result = HASH_CONST * result + (int) (temp ^ (temp >>> HASH_MIN));
        result = HASH_CONST * result + (int) ordStatus;
        result = HASH_CONST * result + (int) ordType;
        result = HASH_CONST * result + (origClordID != null ? origClordID.hashCode() : 0);
        result = HASH_CONST * result + (possDupFlag ? 1 : 0);
        temp = price != +0.0d ? Double.doubleToLongBits(price) : 0L;
        result = HASH_CONST * result + (int) (temp ^ (temp >>> HASH_MIN));
        result = HASH_CONST * result + (securityID != null ? securityID.hashCode() : 0);
        result = HASH_CONST * result + (senderCompID != null ? senderCompID.hashCode() : 0);
        result = HASH_CONST * result + (int) side;
        result = HASH_CONST * result + (symbol != null ? symbol.hashCode() : 0);
        result = HASH_CONST * result + (targetCompID != null ? targetCompID.hashCode() : 0);
        result = HASH_CONST * result + (text != null ? text.hashCode() : 0);
        result = HASH_CONST * result + timeInForce;
        result = HASH_CONST * result + (transactTime != null ? transactTime.hashCode() : 0);
        result = HASH_CONST * result + settlmntType;
        temp = stopPx != +0.0d ? Double.doubleToLongBits(stopPx) : 0L;
        result = HASH_CONST * result + (int) (temp ^ (temp >>> HASH_MIN));
        result = HASH_CONST * result + (exDestination != null ? exDestination.hashCode() : 0);
        result = HASH_CONST * result + ordRejReason;
        temp = minQty != +0.0d ? Double.doubleToLongBits(minQty) : 0L;
        result = HASH_CONST * result + (int) (temp ^ (temp >>> HASH_MIN));
        temp = maxFloor != +0.0d ? Double.doubleToLongBits(maxFloor) : 0L;
        result = HASH_CONST * result + (int) (temp ^ (temp >>> HASH_MIN));
        result = HASH_CONST * result + (onBehalfOfCompID != null ? onBehalfOfCompID.hashCode() : 0);
        result = HASH_CONST * result + (onBehalfOfSubID != null ? onBehalfOfSubID.hashCode() : 0);
        result = HASH_CONST * result + (expireTime != null ? expireTime.hashCode() : 0);
        result = HASH_CONST * result + (senderLocationID != null ? senderLocationID.hashCode() : 0);
        result = HASH_CONST * result + (int) execType;
        temp = leavesQty != +0.0d ? Double.doubleToLongBits(leavesQty) : 0L;
        result = HASH_CONST * result + (int) (temp ^ (temp >>> HASH_MIN));
        result = HASH_CONST * result + (securityType != null ? securityType.hashCode() : 0);
        result = HASH_CONST * result + (maturityMonthYear != null ? maturityMonthYear.hashCode() : 0);
        result = HASH_CONST * result + putOrCall;
        temp = strikePrice != +0.0d ? Double.doubleToLongBits(strikePrice) : 0L;
        result = HASH_CONST * result + (int) (temp ^ (temp >>> HASH_MIN));
        result = HASH_CONST * result + (securityExchange != null ? securityExchange.hashCode() : 0);
        result = HASH_CONST * result + (int) subscriptionRequestType;
        result = HASH_CONST * result + (underlyingSymbol != null ? underlyingSymbol.hashCode() : 0);
        result = HASH_CONST * result + (tradSesReqID != null ? tradSesReqID.hashCode() : 0);
        result = HASH_CONST * result + (solicitedFlag ? 1 : 0);
        result = HASH_CONST * result + (expireDate != null ? expireDate.hashCode() : 0);
        result = HASH_CONST * result + (int) cxlRejResponseTo;
        result = HASH_CONST * result + accountType;
        result = HASH_CONST * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = HASH_CONST * result + orderCategory;
        result = HASH_CONST * result + (routingAc != null ? routingAc.hashCode() : 0);
        result = HASH_CONST * result + (routingAccRef != null ? routingAccRef.hashCode() : 0);
        result = HASH_CONST * result + (remoteClOrdID != null ? remoteClOrdID.hashCode() : 0);
        result = HASH_CONST * result + (remoteOrigClOrdID != null ? remoteOrigClOrdID.hashCode() : 0);
        result = HASH_CONST * result + letsOrdStatus;
        result = HASH_CONST * result + requestStatus;
        result = HASH_CONST * result + (eSISOrderNo != null ? eSISOrderNo.hashCode() : 0);
        result = HASH_CONST * result + channel;
        result = HASH_CONST * result + (userID != null ? userID.hashCode() : 0);
        result = HASH_CONST * result + manualRequestStatus;
        result = HASH_CONST * result + (tworderid != null ? tworderid.hashCode() : 0);
        temp = ordValue != +0.0d ? Double.doubleToLongBits(ordValue) : 0L;
        result = HASH_CONST * result + (int) (temp ^ (temp >>> HASH_MIN));
        result = HASH_CONST * result + (messageID != null ? messageID.hashCode() : 0);
        temp = maxPrice != +0.0d ? Double.doubleToLongBits(maxPrice) : 0L;
        result = HASH_CONST * result + (int) (temp ^ (temp >>> HASH_MIN));
        result = HASH_CONST * result + (brokerID != null ? brokerID.hashCode() : 0);
        result = HASH_CONST * result + (brokerFIXID != null ? brokerFIXID.hashCode() : 0);
        result = HASH_CONST * result + sequenceNo;
        result = HASH_CONST * result + allowSmallOrder;
        result = HASH_CONST * result + (fixString != null ? fixString.hashCode() : 0);
        result = HASH_CONST * result + (connectionSid != null ? connectionSid.hashCode() : 0);
        result = HASH_CONST * result + (sendingTime != null ? sendingTime.hashCode() : 0);
        result = HASH_CONST * result + (tradingSessionID != null ? tradingSessionID.hashCode() : 0);
        result = HASH_CONST * result + (tradSesMethod != null ? tradSesMethod.hashCode() : 0);
        result = HASH_CONST * result + tradSesStatus;
        result = HASH_CONST * result + (remoteSymbol != null ? remoteSymbol.hashCode() : 0);
        result = HASH_CONST * result + (remoteAccountNumber != null ? remoteAccountNumber.hashCode() : 0);
        result = HASH_CONST * result + (remoteExDestination != null ? remoteExDestination.hashCode() : 0);
        result = HASH_CONST * result + (remoteExchange != null ? remoteExchange.hashCode() : 0);
        result = HASH_CONST * result + (remoteSecurityID != null ? remoteSecurityID.hashCode() : 0);
        result = HASH_CONST * result + (institutionID != null ? institutionID.hashCode() : 0);
        result = HASH_CONST * result + deskOrderType;
        result = HASH_CONST * result + deskOrderAction;
        result = HASH_CONST * result + mubExecID;
        result = HASH_CONST * result + (int) prevOrdStatus;
        result = HASH_CONST * result + (int) exgOrdStatus;
        result = HASH_CONST * result + (settlementDate != null ? settlementDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderBean{" +
                "portfolioNo='" + portfolioNo + '\'' +
                ", avgPx=" + avgPx +
                ", fixVersion='" + fixVersion + '\'' +
                ", clordID='" + clordID + '\'' +
                ", commission=" + commission +
                ", cumQty=" + cumQty +
                ", currency='" + currency + '\'' +
                ", execID='" + execID + '\'' +
                ", execInst='" + execInst + '\'' +
                ", execTransType=" + execTransType +
                ", handlInst=" + handlInst +
                ", securityIDSource='" + securityIDSource + '\'' +
                ", lastPx=" + lastPx +
                ", lastShares=" + lastShares +
                ", msgType=" + msgType +
                ", orderID='" + orderID + '\'' +
                ", orderQty=" + orderQty +
                ", ordStatus=" + ordStatus +
                ", ordType=" + ordType +
                ", origClordID='" + origClordID + '\'' +
                ", possDupFlag=" + possDupFlag +
                ", price=" + price +
                ", securityID='" + securityID + '\'' +
                ", senderCompID='" + senderCompID + '\'' +
                ", side=" + side +
                ", symbol='" + symbol + '\'' +
                ", targetCompID='" + targetCompID + '\'' +
                ", text='" + text + '\'' +
                ", timeInForce=" + timeInForce +
                ", transactTime=" + transactTime +
                ", settlmntType=" + settlmntType +
                ", stopPx=" + stopPx +
                ", exDestination='" + exDestination + '\'' +
                ", ordRejReason=" + ordRejReason +
                ", minQty=" + minQty +
                ", maxFloor=" + maxFloor +
                ", onBehalfOfCompID='" + onBehalfOfCompID + '\'' +
                ", onBehalfOfSubID='" + onBehalfOfSubID + '\'' +
                ", expireTime=" + expireTime +
                ", senderLocationID='" + senderLocationID + '\'' +
                ", execType=" + execType +
                ", leavesQty=" + leavesQty +
                ", securityType='" + securityType + '\'' +
                ", maturityMonthYear='" + maturityMonthYear + '\'' +
                ", putOrCall=" + putOrCall +
                ", strikePrice=" + strikePrice +
                ", securityExchange='" + securityExchange + '\'' +
                ", subscriptionRequestType=" + subscriptionRequestType +
                ", underlyingSymbol='" + underlyingSymbol + '\'' +
                ", tradSesReqID='" + tradSesReqID + '\'' +
                ", solicitedFlag=" + solicitedFlag +
                ", expireDate=" + expireDate +
                ", cxlRejResponseTo=" + cxlRejResponseTo +
                ", accountType=" + accountType +
                ", createdDate=" + createdDate +
                ", orderCategory=" + orderCategory +
                ", routingAc='" + routingAc + '\'' +
                ", routingAccRef='" + routingAccRef + '\'' +
                ", remoteClOrdID='" + remoteClOrdID + '\'' +
                ", remoteOrigClOrdID='" + remoteOrigClOrdID + '\'' +
                ", letsOrdStatus=" + letsOrdStatus +
                ", requestStatus=" + requestStatus +
                ", eSISOrderNo='" + eSISOrderNo + '\'' +
                ", channel=" + channel +
                ", userID='" + userID + '\'' +
                ", manualRequestStatus=" + manualRequestStatus +
                ", tworderid='" + tworderid + '\'' +
                ", ordValue=" + ordValue +
                ", messageID='" + messageID + '\'' +
                ", maxPrice=" + maxPrice +
                ", brokerID='" + brokerID + '\'' +
                ", brokerFIXID='" + brokerFIXID + '\'' +
                ", sequenceNo=" + sequenceNo +
                ", allowSmallOrder=" + allowSmallOrder +
                ", fixString='" + fixString + '\'' +
                ", connectionSid='" + connectionSid + '\'' +
                ", sendingTime=" + sendingTime +
                ", tradingSessionID='" + tradingSessionID + '\'' +
                ", tradSesMethod='" + tradSesMethod + '\'' +
                ", tradSesStatus=" + tradSesStatus +
                ", remoteSymbol='" + remoteSymbol + '\'' +
                ", remoteAccountNumber='" + remoteAccountNumber + '\'' +
                ", remoteExDestination='" + remoteExDestination + '\'' +
                ", remoteExchange='" + remoteExchange + '\'' +
                ", remoteSecurityID='" + remoteSecurityID + '\'' +
                ", institutionID='" + institutionID + '\'' +
                ", deskOrderType=" + deskOrderType +
                ", deskOrderAction=" + deskOrderAction +
                ", mubExecID=" + mubExecID +
                ", prevOrdStatus=" + prevOrdStatus +
                ", exgOrdStatus=" + exgOrdStatus +
                ", settlementDate=" + settlementDate +
                '}';
    }
}
