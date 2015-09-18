package com.directfn.exchange_simulator.message_processor.impl.beans;

import java.util.Date;

public class FIXPropertiesBean {

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
    private char msgType = '0';
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
    private char messageType;
    private boolean specialOrderReplace;
    private String sessionId;
    private boolean isValidToAmendOrCancel;
    private String originalMessageType;
    private boolean isValidToProcess;
    private String customerNumber;
    private String broker;
    private boolean isFixOrder;
    private int settlementType;
    private boolean isPartiallyFilled;
    private String clientType;

    public String getOriginalMessageType() {
        return originalMessageType;
    }

    public void setOriginalMessageType(String originalMessageType) {
        this.originalMessageType = originalMessageType;
    }

    public String getPortfolioNo() {
        return portfolioNo;
    }

    public void setPortfolioNo(String portfolioNo) {
        this.portfolioNo = portfolioNo;
    }

    public double getAvgPx() {
        return avgPx;
    }

    public void setAvgPx(double avgPx) {
        this.avgPx = avgPx;
    }

    public String getFixVersion() {
        return fixVersion;
    }

    public void setFixVersion(String fixVersion) {
        this.fixVersion = fixVersion;
    }

    public String getClordID() {
        return clordID;
    }

    public void setClordID(String clordID) {
        this.clordID = clordID;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public double getCumQty() {
        return cumQty;
    }

    public void setCumQty(double cumQty) {
        this.cumQty = cumQty;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getExecID() {
        return execID;
    }

    public void setExecID(String execID) {
        this.execID = execID;
    }

    public String getExecInst() {
        return execInst;
    }

    public void setExecInst(String execInst) {
        this.execInst = execInst;
    }

    public int getExecTransType() {
        return execTransType;
    }

    public void setExecTransType(int execTransType) {
        this.execTransType = execTransType;
    }

    public int getHandlInst() {
        return handlInst;
    }

    public void setHandlInst(int handlInst) {
        this.handlInst = handlInst;
    }

    public String getSecurityIDSource() {
        return securityIDSource;
    }

    public void setSecurityIDSource(String securityIDSource) {
        this.securityIDSource = securityIDSource;
    }

    public double getLastPx() {
        return lastPx;
    }

    public void setLastPx(double lastPx) {
        this.lastPx = lastPx;
    }

    public double getLastShares() {
        return lastShares;
    }

    public void setLastShares(double lastShares) {
        this.lastShares = lastShares;
    }

    public char getMsgType() {
        return msgType;
    }

    public void setMsgType(char msgType) {
        this.msgType = msgType;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public double getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(double orderQty) {
        this.orderQty = orderQty;
    }

    public char getOrdStatus() {
        return ordStatus;
    }

    public void setOrdStatus(char ordStatus) {
        this.ordStatus = ordStatus;
    }

    public char getOrdType() {
        return ordType;
    }

    public void setOrdType(char ordType) {
        this.ordType = ordType;
    }

    public String getOrigClordID() {
        return origClordID;
    }

    public void setOrigClordID(String origClordID) {
        this.origClordID = origClordID;
    }

    public boolean isPossDupFlag() {
        return possDupFlag;
    }

    public void setPossDupFlag(boolean possDupFlag) {
        this.possDupFlag = possDupFlag;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSecurityID() {
        return securityID;
    }

    public void setSecurityID(String securityID) {
        this.securityID = securityID;
    }

    public String getSenderCompID() {
        return senderCompID;
    }

    public void setSenderCompID(String senderCompID) {
        this.senderCompID = senderCompID;
    }

    public String getSenderSubID() {
        return senderSubID;
    }

    public void setSenderSubID(String senderSubID) {
        this.senderSubID = senderSubID;
    }

    public char getSide() {
        return side;
    }

    public void setSide(char side) {
        this.side = side;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTimeInForce() {
        return timeInForce;
    }

    public void setTimeInForce(int timeInForce) {
        this.timeInForce = timeInForce;
    }

    public String getTransactTime() {
        return transactTime;
    }

    public void setTransactTime(String transactTime) {
        this.transactTime = transactTime;
    }

    public int getSettlmntType() {
        return settlmntType;
    }

    public void setSettlmntType(int settlmntType) {
        this.settlmntType = settlmntType;
    }

    public double getStopPx() {
        return stopPx;
    }

    public void setStopPx(double stopPx) {
        this.stopPx = stopPx;
    }

    public String getExDestination() {
        return exDestination;
    }

    public void setExDestination(String exDestination) {
        this.exDestination = exDestination;
    }

    public int getOrdRejReason() {
        return ordRejReason;
    }

    public void setOrdRejReason(int ordRejReason) {
        this.ordRejReason = ordRejReason;
    }

    public double getMinQty() {
        return minQty;
    }

    public void setMinQty(double minQty) {
        this.minQty = minQty;
    }

    public double getMaxFloor() {
        return maxFloor;
    }

    public void setMaxFloor(double maxFloor) {
        this.maxFloor = maxFloor;
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

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getSenderLocationID() {
        return senderLocationID;
    }

    public void setSenderLocationID(String senderLocationID) {
        this.senderLocationID = senderLocationID;
    }

    public char getExecType() {
        return execType;
    }

    public void setExecType(char execType) {
        this.execType = execType;
    }

    public double getLeavesQty() {
        return leavesQty;
    }

    public void setLeavesQty(double leavesQty) {
        this.leavesQty = leavesQty;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public String getMaturityMonthYear() {
        return maturityMonthYear;
    }

    public void setMaturityMonthYear(String maturityMonthYear) {
        this.maturityMonthYear = maturityMonthYear;
    }

    public int getPutOrCall() {
        return putOrCall;
    }

    public void setPutOrCall(int putOrCall) {
        this.putOrCall = putOrCall;
    }

    public double getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(double strikePrice) {
        this.strikePrice = strikePrice;
    }

    public String getSecurityExchange() {
        return securityExchange;
    }

    public void setSecurityExchange(String securityExchange) {
        this.securityExchange = securityExchange;
    }

    public char getSubscriptionRequestType() {
        return subscriptionRequestType;
    }

    public void setSubscriptionRequestType(char subscriptionRequestType) {
        this.subscriptionRequestType = subscriptionRequestType;
    }

    public String getUnderlyingSymbol() {
        return underlyingSymbol;
    }

    public void setUnderlyingSymbol(String underlyingSymbol) {
        this.underlyingSymbol = underlyingSymbol;
    }

    public String getTradSesReqID() {
        return tradSesReqID;
    }

    public void setTradSesReqID(String tradSesReqID) {
        this.tradSesReqID = tradSesReqID;
    }

    public boolean isSolicitedFlag() {
        return solicitedFlag;
    }

    public void setSolicitedFlag(boolean solicitedFlag) {
        this.solicitedFlag = solicitedFlag;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public char getCxlRejResponseTo() {
        return cxlRejResponseTo;
    }

    public void setCxlRejResponseTo(char cxlRejResponseTo) {
        this.cxlRejResponseTo = cxlRejResponseTo;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getOrderCategory() {
        return orderCategory;
    }

    public void setOrderCategory(int orderCategory) {
        this.orderCategory = orderCategory;
    }

    public String getRoutingAc() {
        return routingAc;
    }

    public void setRoutingAc(String routingAc) {
        this.routingAc = routingAc;
    }

    public String getRoutingAccRef() {
        return routingAccRef;
    }

    public void setRoutingAccRef(String routingAccRef) {
        this.routingAccRef = routingAccRef;
    }

    public String getRemoteClOrdID() {
        return remoteClOrdID;
    }

    public void setRemoteClOrdID(String remoteClOrdID) {
        this.remoteClOrdID = remoteClOrdID;
    }

    public String getRemoteOrigClOrdID() {
        return remoteOrigClOrdID;
    }

    public void setRemoteOrigClOrdID(String remoteOrigClOrdID) {
        this.remoteOrigClOrdID = remoteOrigClOrdID;
    }

    public int getLetsOrdStatus() {
        return letsOrdStatus;
    }

    public void setLetsOrdStatus(int letsOrdStatus) {
        this.letsOrdStatus = letsOrdStatus;
    }

    public int getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(int requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String geteSISOrderNo() {
        return eSISOrderNo;
    }

    public void seteSISOrderNo(String eSISOrderNo) {
        this.eSISOrderNo = eSISOrderNo;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getManualRequestStatus() {
        return manualRequestStatus;
    }

    public void setManualRequestStatus(int manualRequestStatus) {
        this.manualRequestStatus = manualRequestStatus;
    }

    public String getTworderid() {
        return tworderid;
    }

    public void setTworderid(String tworderid) {
        this.tworderid = tworderid;
    }

    public double getOrdValue() {
        return ordValue;
    }

    public void setOrdValue(double ordValue) {
        this.ordValue = ordValue;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getBrokerID() {
        return brokerID;
    }

    public void setBrokerID(String brokerID) {
        this.brokerID = brokerID;
    }

    public String getBrokerFIXID() {
        return brokerFIXID;
    }

    public void setBrokerFIXID(String brokerFIXID) {
        this.brokerFIXID = brokerFIXID;
    }

    public int getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(int sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public int getAllowSmallOrder() {
        return allowSmallOrder;
    }

    public void setAllowSmallOrder(int allowSmallOrder) {
        this.allowSmallOrder = allowSmallOrder;
    }

    public String getFixString() {
        return fixString;
    }

    public void setFixString(String fixString) {
        this.fixString = fixString;
    }

    public String getConnectionSid() {
        return connectionSid;
    }

    public void setConnectionSid(String connectionSid) {
        this.connectionSid = connectionSid;
    }

    public Date getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(Date sendingTime) {
        this.sendingTime = sendingTime;
    }

    public String getTradingSessionID() {
        return tradingSessionID;
    }

    public void setTradingSessionID(String tradingSessionID) {
        this.tradingSessionID = tradingSessionID;
    }

    public String getTradSesMethod() {
        return tradSesMethod;
    }

    public void setTradSesMethod(String tradSesMethod) {
        this.tradSesMethod = tradSesMethod;
    }

    public int getTradSesStatus() {
        return tradSesStatus;
    }

    public void setTradSesStatus(int tradSesStatus) {
        this.tradSesStatus = tradSesStatus;
    }

    public String getRemoteSymbol() {
        return remoteSymbol;
    }

    public void setRemoteSymbol(String remoteSymbol) {
        this.remoteSymbol = remoteSymbol;
    }

    public String getRemoteAccountNumber() {
        return remoteAccountNumber;
    }

    public void setRemoteAccountNumber(String remoteAccountNumber) {
        this.remoteAccountNumber = remoteAccountNumber;
    }

    public String getRemoteExDestination() {
        return remoteExDestination;
    }

    public void setRemoteExDestination(String remoteExDestination) {
        this.remoteExDestination = remoteExDestination;
    }

    public String getRemoteExchange() {
        return remoteExchange;
    }

    public void setRemoteExchange(String remoteExchange) {
        this.remoteExchange = remoteExchange;
    }

    public String getRemoteSecurityID() {
        return remoteSecurityID;
    }

    public void setRemoteSecurityID(String remoteSecurityID) {
        this.remoteSecurityID = remoteSecurityID;
    }

    public String getInstitutionID() {
        return institutionID;
    }

    public void setInstitutionID(String institutionID) {
        this.institutionID = institutionID;
    }

    public int getDeskOrderType() {
        return deskOrderType;
    }

    public void setDeskOrderType(int deskOrderType) {
        this.deskOrderType = deskOrderType;
    }

    public int getDeskOrderAction() {
        return deskOrderAction;
    }

    public void setDeskOrderAction(int deskOrderAction) {
        this.deskOrderAction = deskOrderAction;
    }

    public int getMubExecID() {
        return mubExecID;
    }

    public void setMubExecID(int mubExecID) {
        this.mubExecID = mubExecID;
    }

    public char getPrevOrdStatus() {
        return prevOrdStatus;
    }

    public void setPrevOrdStatus(char prevOrdStatus) {
        this.prevOrdStatus = prevOrdStatus;
    }

    public char getExgOrdStatus() {
        return exgOrdStatus;
    }

    public void setExgOrdStatus(char exgOrdStatus) {
        this.exgOrdStatus = exgOrdStatus;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getReuterCode() {
        return reuterCode;
    }

    public void setReuterCode(String reuterCode) {
        this.reuterCode = reuterCode;
    }

    public String getIsinCode() {
        return isinCode;
    }

    public void setIsinCode(String isinCode) {
        this.isinCode = isinCode;
    }

    public String getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(String maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getExchangeSymbol() {
        return exchangeSymbol;
    }

    public void setExchangeSymbol(String exchangeSymbol) {
        this.exchangeSymbol = exchangeSymbol;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getFixSessionIdentifier() {
        return fixSessionIdentifier;
    }

    public void setFixSessionIdentifier(String fixSessionIdentifier) {
        this.fixSessionIdentifier = fixSessionIdentifier;
    }

    public boolean isDeskOrder() {
        return deskOrder;
    }

    public void setDeskOrder(boolean deskOrder) {
        this.deskOrder = deskOrder;
    }

    public char getMessageType() {
        return messageType;
    }

    public void setMessageType(char messageType) {
        this.messageType = messageType;
    }

    public boolean isSpecialOrderReplace() {
        return specialOrderReplace;
    }

    public void setSpecialOrderReplace(boolean specialOrderReplace) {
        this.specialOrderReplace = specialOrderReplace;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public boolean isValidToAmendOrCancel() {
        return isValidToAmendOrCancel;
    }

    public void setValidToAmendOrCancel(boolean isValidToAmendOrCancel) {
        this.isValidToAmendOrCancel = isValidToAmendOrCancel;
    }

    public boolean isValidToProcess() {
        return isValidToProcess;
    }

    public void setValidToProcess(boolean isValidToProcess) {
        this.isValidToProcess = isValidToProcess;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getBroker() {
        return broker;
    }

    public void setBroker(String broker) {
        this.broker = broker;
    }

    public boolean isFixOrder() {
        return isFixOrder;
    }

    public void setFixOrder(boolean isFixOrder) {
        this.isFixOrder = isFixOrder;
    }

    public int getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(int settlementType) {
        this.settlementType = settlementType;
    }

    public boolean isPartiallyFilled() {
        return isPartiallyFilled;
    }

    public void setPartiallyFilled(boolean isPartiallyFilled) {
        this.isPartiallyFilled = isPartiallyFilled;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }
}
