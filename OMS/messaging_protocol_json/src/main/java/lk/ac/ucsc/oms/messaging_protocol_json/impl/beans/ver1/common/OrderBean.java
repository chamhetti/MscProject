package lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.common;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import static lk.ac.ucsc.oms.messaging_protocol_json.api.FieldConstants.*;


public class OrderBean {

    @SerializedName(CLIENT_ORDER_ID)
    private String clOrdID = null;

    @SerializedName(COMMISSION)
    private double commission = 0;

    @SerializedName(CUMULATIVE_QUANTITY)
    private double cumulativeQty = 0;

    @SerializedName(EXECUTION_ID)
    private String executionID = null;

    @SerializedName(ORDER_QUANTITY)
    private double orderQty = 0;

    @SerializedName(ORDER_TYPE)
    private String orderType = null;

    @SerializedName(PRICE)
    private double price = 0;

    @SerializedName(ORDER_SIDE)
    private String side = null;

    @SerializedName(SYMBOL)
    private String symbol = null;

    @SerializedName(TIME_IN_FORCE)
    private int timeInForce = 0;

    @SerializedName(TRANSACTION_TIME)
    private String transactionTime = null;

    @SerializedName(SETTLEMENT_TYPE)
    private int settlementType = 0;

    @SerializedName(MINIMUM_QUANTITY)
    private double minimumQty = 0;

    @SerializedName(EXPIRE_TIME)
    private String expireTime = null;

    @SerializedName(SECURITY_TYPE)
    private int securityType = 0;

    @SerializedName(SECURITY_EXCHANGE)
    private String securityExchange = null;

    @SerializedName(SECURITY_ACCOUNT_NUMBER)
    private String securityAccountNo = null;

    @SerializedName(USER_ID)
    private String userId = null;

    @SerializedName(DAY_ORDER)
    private int dayOrder = 0;

    @SerializedName(BOOK_KEEPER_ID)
    private String bookKeeperID = null;

    @SerializedName(LAST_PRICE)
    private double lastPrice = 0;

    @SerializedName(ORDER_ID)
    private String orderID = null;

    @SerializedName(TEXT)
    private String text = null;

    @SerializedName(NIN)
    private String nin = null;

    @SerializedName(EXECUTION_BROKER_SID)
    private String executionBrokerSID;

    @SerializedName(EXECUTION_BROKER_ID)
    private String executionBrokerID = null;

    @SerializedName(DEALER_ID)
    private String dealerID = null;

    @SerializedName(CREATED_DATE)
    private String createdDate = null;

    @SerializedName(ORDER_VALUE)
    private double orderValue = 0;

    @SerializedName(CUMULATIVE_COMMISSION)
    private double cumulativeCommission = 0;

    @SerializedName(NET_ORDER_VALUE)
    private double netOrdValue = 0;

    @SerializedName(CUMULATIVE_ORDER_VALUE)
    private double cumulativeOrdValue = 0;

    @SerializedName(CUMULATIVE_ORDER_NET_VALUE)
    private double cumOrdNetValue = 0;

    @SerializedName(SETTLE_CURRENCY)
    private String settleCurrency = null;

    @SerializedName(MARKET_CODE)
    private String marketCode = null;

    @SerializedName(MUBASHER_ORDER_NUMBER)
    private String mubasherOrdNumber = null;

    @SerializedName(LAST_UPDATED_TIME)
    private String lastUpdatedTime = null;

    @SerializedName(DESK_ORDER_REFERENCE)
    private String deskOrdReference = null;

    @SerializedName(ALGO_ORDER_REFERENCE)
    private String sliceOrderRef = null;

    @SerializedName(ORIGINAL_DESK_ORDER_REFERENCE)
    private String originalDeskOrdReference = null;

    @SerializedName(MASTER_ORDER_REFERENCE)
    private String masterOrdReference = null;

    @SerializedName(ORDER_APPROVED_BY)
    private String orderApprovedBy = null;

    @SerializedName(NET_SETTLE)
    private double netSettle = 0;

    @SerializedName(CUMULATIVE_ORDER_NET_SETTLE)
    private double cumulativeOrderNetSettle = 0;

    @SerializedName(CURRENCY)
    private String currency = null;

    @SerializedName(EXECUTION_TRANSACTION_TYPE)
    private int execTransType = -1;

    @SerializedName(LAST_SHARES)
    private double lastShares = 0;

    @SerializedName(ORDER_STATUS)
    private String ordStatus = "X";

    @SerializedName(ORIGINAL_CLIENT_ORDER_ID)
    private String origClOrdID = null;

    @SerializedName(STOP_PRICE)
    private double stopPrice = 0;

    @SerializedName(ORDER_REJECT_REASON)
    private String ordRejectReason = null;

    @SerializedName(MAXIMUM_FLOOR)
    private double maxFloor = 0;

    @SerializedName(LEAVES_QUANTITY)
    private double leavesQty = -1;

    @SerializedName(PUT_OR_CALL)
    private double putOrCall = -1;

    @SerializedName(STRIKE_PRICE)
    private double strikePrice = 0;

    @SerializedName(CHANNEL_ID)
    private int channel = -1;

    @SerializedName(MAXIMUM_PRICE)
    private double maximumPrice = 0;

    @SerializedName(BROKER_ID)
    private String brokerID = "";

    @SerializedName(ROUTING_ACCOUNT)
    private String routingAccount = null;

    @SerializedName(MUBASHER_SECURITY_TYPE)
    private int mubasherSecurityType = -1;

    @SerializedName(FILLED_QUANTITY)
    private double filledQuantity = -1;

    @SerializedName(ISSUE_SETTLING_RATE)
    private double issueSettlingRate = -1;

    @SerializedName(CLIENT_IP)
    private String clientIP = null;

    @SerializedName(INSTITUTE_ID)
    private String instId = null;

    @SerializedName(ORDER_NET_VALUE)
    private double orderNetValue;

    @SerializedName(ORDER_AVERAGE_COST)
    private double orderAvgCost = 0;

    @SerializedName(STOP_PRICE_TYPE)
    private int stopPxType = -1;

    @SerializedName(ORDER_CATEGORY)
    private int orderCategory = -1;

    @SerializedName(ORDER_GROUP)
    private String ordGroup = null;

    @SerializedName(INTERNAL_ORDER_STATUS)
    private String internalOrdStatus = "X";

    @SerializedName(CUSTOMER_ID)
    private String customerID;

    @SerializedName(CHILD_ORDER_LIST)
    private List<String> childOrderIDList = null;

    @SerializedName(DEALER_TYPE)
    private int isOrderFromGlobalDealer = -1;

    @SerializedName(IS_APPROVAL_REQURED)
    private int isApprovalRequred = -1;

    @SerializedName(ACCOUNT_TYPE)
    private int accountType = -1;

    @SerializedName(QUANTITY)
    private double quantity = -1;

    @SerializedName(REMOTE_ORDER_ID)
    private String remoteOrderID = null;

    @SerializedName(REMOTE_ORIGINAL_ORDER_ID)
    private String remoteOriginalOrderID = null;

    @SerializedName(PRICE_WITH_COMMISSION)
    private double priceWithCommission = 0;

    @SerializedName(TRIGGER_TYPE)
    private String triggerType = null;

    @SerializedName(TRIGGER_ACTION)
    private String triggerAction = null;

    @SerializedName(TRIGGER_PRICE)
    private double triggerPrice = 0;

    @SerializedName(TRIGGER_PRICE_TYPE)
    private String triggerPriceType = null;

    @SerializedName(TRIGGER_PRICE_DETECTION)
    private String triggerPriceDetection = null;

    @SerializedName(SIGNATURE)
    private String signature = null;

    @SerializedName(CUSTOMER_NAME)
    private String customerName = null;

    @SerializedName(TOTAL_ORDER_NET_VALUE)
    private double totalOrderNetValue = -1;

    @SerializedName(AVERAGE_PRICE)
    private double averagePrice = 0;

    @SerializedName(INSTRUMENT_TYPE)
    private String instrumentType;

    @SerializedName(DEALER_NAME)
    private String dealerName;

    @SerializedName(INTERNAL_MATCH_STATUS)
    private int internalMatchStatus;

    @SerializedName(CONDITION_TYPE)
    private int conditionType;

    @SerializedName(SLICE_ORDER_INTERVAL)
    private int sliceOrderInterval;

    @SerializedName(SLICE_ORDER_BLOCK)
    private int sliceOrderBlock;

    @SerializedName(CONDITION_VALUE)
    private String conditionValue;

    @SerializedName(OPERATOR)
    private String operator;

    @SerializedName(DESK_ORDER_AUTO_RELEASE)
    private int deskAutoRelease;

    @SerializedName(ALGO_TYPE)
    private int sliceOrderExecutionType = -1;


    @SerializedName(DESK_ORDER_TOTAL_CHILD_QTY)
    private double deskTotalChildQty;

    @SerializedName(FOL_ORDER_REFERENCE)
    private String folOrderRef;

    @SerializedName(FOL_ORDER_INTERVAL)
    private double folInterval;

    @SerializedName(FOL_ORDER_STRATEGY)
    private int folPriceStrategy;

    @SerializedName(CUSTOMER_EXTERNAL_REF)
    private String customerExternalRef;

    @SerializedName(DESK_ORDER_TYPE)
    private int deskOrderType = -1;

    @SerializedName(CREATED_BY)
    private String createdBy = null;

    @SerializedName(MULTININ_MASTER_REF)
    private String multiNINMasterRef;

    @SerializedName(MULTININ_BREAKDOWN_REF)
    private String multiNINBreakDownRef;

    @SerializedName(MULTININ_MASTER_NO)
    private String multiNINMasterOrdNo;

    @SerializedName(MULTININ_BATCH_NO)
    private String multiNINBatchId;

    @SerializedName(WORKED_QUANTITY)
    private double workedQty;

    @SerializedName(TIME_TRIGGER_MODE)
    private int timeTriggerMode = 0;

    @SerializedName(TOTAL_CHILD_QTY)
    private double totalChildOrderQty;

    @SerializedName(MARGIN_TRADING_STATUS)
    private int marginEnabledType;

    @SerializedName(CALL_CENTER_ORD_REF)
    private String callCenterOrdRef;

    @SerializedName(CALL_CENTRE_AGENT_NAME)
    private String callCentreAgentName;

    @SerializedName(ORDER_ACTION)
    private String orderAction = null;

    @SerializedName(APPROVED_DATE)
    private String approvedDate = null;

    public String getClOrdID() {
        return clOrdID;
    }

    public void setClOrdID(String clOrdID) {
        this.clOrdID = clOrdID;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public double getCumulativeQty() {
        return cumulativeQty;
    }

    public void setCumulativeQty(double cumulativeQty) {
        this.cumulativeQty = cumulativeQty;
    }

    public String getExecutionID() {
        return executionID;
    }

    public void setExecutionID(String executionID) {
        this.executionID = executionID;
    }

    public double getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(double orderQty) {
        this.orderQty = orderQty;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public int getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(int settlementType) {
        this.settlementType = settlementType;
    }

    public double getMinimumQty() {
        return minimumQty;
    }

    public void setMinimumQty(double minimumQty) {
        this.minimumQty = minimumQty;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public int getSecurityType() {
        return securityType;
    }

    public void setSecurityType(int securityType) {
        this.securityType = securityType;
    }

    public String getSecurityExchange() {
        return securityExchange;
    }

    public void setSecurityExchange(String securityExchange) {
        this.securityExchange = securityExchange;
    }

    public String getSecurityAccountNo() {
        return securityAccountNo;
    }

    public void setSecurityAccountNo(String securityAccountNo) {
        this.securityAccountNo = securityAccountNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getDayOrder() {
        return dayOrder;
    }

    public void setDayOrder(int dayOrder) {
        this.dayOrder = dayOrder;
    }

    public int getMubasherSecurityType() {
        return mubasherSecurityType;
    }

    public void setMubasherSecurityType(int mubasherSecurityType) {
        this.mubasherSecurityType = mubasherSecurityType;
    }

    public double getFilledQuantity() {
        return filledQuantity;
    }

    public void setFilledQuantity(double filledQuantity) {
        this.filledQuantity = filledQuantity;
    }

    public double getIssueSettlingRate() {
        return issueSettlingRate;
    }

    public void setIssueSettlingRate(double issueSettlingRate) {
        this.issueSettlingRate = issueSettlingRate;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

    public double getOrderNetValue() {
        return orderNetValue;
    }

    public void setOrderNetValue(double orderNetValue) {
        this.orderNetValue = orderNetValue;
    }

    public double getOrderAvgCost() {
        return orderAvgCost;
    }

    public void setOrderAvgCost(double orderAvgCost) {
        this.orderAvgCost = orderAvgCost;
    }

    public int getStopPxType() {
        return stopPxType;
    }

    public void setStopPxType(int stopPxType) {
        this.stopPxType = stopPxType;
    }

    public int getOrderCategory() {
        return orderCategory;
    }

    public void setOrderCategory(int orderCategory) {
        this.orderCategory = orderCategory;
    }

    public String getOrdGroup() {
        return ordGroup;
    }

    public void setOrdGroup(String ordGroup) {
        this.ordGroup = ordGroup;
    }

    public String getInternalOrdStatus() {
        return internalOrdStatus;
    }

    public void setInternalOrdStatus(String internalOrdStatus) {
        this.internalOrdStatus = internalOrdStatus;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public List<String> getChildOrderIDList() {
        return childOrderIDList;
    }

    public void setChildOrderIDList(List<String> childOrderIDList) {
        this.childOrderIDList = childOrderIDList;
    }

    public int getOrderFromGlobalDealer() {
        return isOrderFromGlobalDealer;
    }

    public void setOrderFromGlobalDealer(int orderFromGlobalDealer) {
        isOrderFromGlobalDealer = orderFromGlobalDealer;
    }

    public int getApprovalRequred() {
        return isApprovalRequred;
    }

    public void setApprovalRequred(int approvalRequred) {
        isApprovalRequred = approvalRequred;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getRemoteOrderID() {
        return remoteOrderID;
    }

    public void setRemoteOrderID(String remoteOrderID) {
        this.remoteOrderID = remoteOrderID;
    }

    public String getRemoteOriginalOrderID() {
        return remoteOriginalOrderID;
    }

    public void setRemoteOriginalOrderID(String remoteOriginalOrderID) {
        this.remoteOriginalOrderID = remoteOriginalOrderID;
    }

    public double getPriceWithCommission() {
        return priceWithCommission;
    }

    public void setPriceWithCommission(double priceWithCommission) {
        this.priceWithCommission = priceWithCommission;
    }

    public int getConditionType() {
        return conditionType;
    }

    public void setConditionType(int conditionType) {
        this.conditionType = conditionType;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    public String getTriggerAction() {
        return triggerAction;
    }

    public void setTriggerAction(String triggerAction) {
        this.triggerAction = triggerAction;
    }

    public double getTriggerPrice() {
        return triggerPrice;
    }

    public void setTriggerPrice(double triggerPrice) {
        this.triggerPrice = triggerPrice;
    }

    public String getTriggerPriceType() {
        return triggerPriceType;
    }

    public void setTriggerPriceType(String triggerPriceType) {
        this.triggerPriceType = triggerPriceType;
    }

    public String getTriggerPriceDetection() {
        return triggerPriceDetection;
    }

    public void setTriggerPriceDetection(String triggerPriceDetection) {
        this.triggerPriceDetection = triggerPriceDetection;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getTotalOrderNetValue() {
        return totalOrderNetValue;
    }

    public void setTotalOrderNetValue(double totalOrderNetValue) {
        this.totalOrderNetValue = totalOrderNetValue;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public String getBookKeeperID() {
        return bookKeeperID;
    }

    public void setBookKeeperID(String bookKeeperID) {
        this.bookKeeperID = bookKeeperID;
    }

    public double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNin() {
        return nin;
    }

    public void setNin(String nin) {
        this.nin = nin;
    }

    public String getExecutionBrokerSID() {
        return executionBrokerSID;
    }

    public void setExecutionBrokerSID(String executionBrokerSID) {
        this.executionBrokerSID = executionBrokerSID;
    }

    public String getExecutionBrokerID() {
        return executionBrokerID;
    }

    public void setExecutionBrokerID(String executionBrokerID) {
        this.executionBrokerID = executionBrokerID;
    }

    public String getDealerID() {
        return dealerID;
    }

    public void setDealerID(String dealerID) {
        this.dealerID = dealerID;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public double getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(double orderValue) {
        this.orderValue = orderValue;
    }

    public double getCumulativeCommission() {
        return cumulativeCommission;
    }

    public void setCumulativeCommission(double cumulativeCommission) {
        this.cumulativeCommission = cumulativeCommission;
    }

    public double getNetOrdValue() {
        return netOrdValue;
    }

    public void setNetOrdValue(double netOrdValue) {
        this.netOrdValue = netOrdValue;
    }

    public double getCumulativeOrdValue() {
        return cumulativeOrdValue;
    }

    public void setCumulativeOrdValue(double cumulativeOrdValue) {
        this.cumulativeOrdValue = cumulativeOrdValue;
    }

    public double getCumOrdNetValue() {
        return cumOrdNetValue;
    }

    public void setCumOrdNetValue(double cumOrdNetValue) {
        this.cumOrdNetValue = cumOrdNetValue;
    }

    public String getSettleCurrency() {
        return settleCurrency;
    }

    public void setSettleCurrency(String settleCurrency) {
        this.settleCurrency = settleCurrency;
    }

    public String getMarketCode() {
        return marketCode;
    }

    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    public String getMubasherOrdNumber() {
        return mubasherOrdNumber;
    }

    public void setMubasherOrdNumber(String mubasherOrdNumber) {
        this.mubasherOrdNumber = mubasherOrdNumber;
    }

    public String getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(String lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getDeskOrdReference() {
        return deskOrdReference;
    }

    public void setDeskOrdReference(String deskOrdReference) {
        this.deskOrdReference = deskOrdReference;
    }

    public String getSliceOrderRef() {
        return sliceOrderRef;
    }

    public void setSliceOrderRef(String sliceOrderRef) {
        this.sliceOrderRef = sliceOrderRef;
    }

    public String getOriginalDeskOrdReference() {
        return originalDeskOrdReference;
    }

    public void setOriginalDeskOrdReference(String originalDeskOrdReference) {
        this.originalDeskOrdReference = originalDeskOrdReference;
    }

    public String getMasterOrdReference() {
        return masterOrdReference;
    }

    public void setMasterOrdReference(String masterOrdReference) {
        this.masterOrdReference = masterOrdReference;
    }

    public String getOrderApprovedBy() {
        return orderApprovedBy;
    }

    public void setOrderApprovedBy(String orderApprovedBy) {
        this.orderApprovedBy = orderApprovedBy;
    }

    public double getNetSettle() {
        return netSettle;
    }

    public void setNetSettle(double netSettle) {
        this.netSettle = netSettle;
    }

    public double getCumulativeOrderNetSettle() {
        return cumulativeOrderNetSettle;
    }

    public void setCumulativeOrderNetSettle(double cumulativeOrderNetSettle) {
        this.cumulativeOrderNetSettle = cumulativeOrderNetSettle;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getExecTransType() {
        return execTransType;
    }

    public void setExecTransType(int execTransType) {
        this.execTransType = execTransType;
    }

    public double getLastShares() {
        return lastShares;
    }

    public void setLastShares(double lastShares) {
        this.lastShares = lastShares;
    }

    public String getOrdStatus() {
        return ordStatus;
    }

    public void setOrdStatus(String ordStatus) {
        this.ordStatus = ordStatus;
    }

    public String getOrigClOrdID() {
        return origClOrdID;
    }

    public void setOrigClOrdID(String origClOrdID) {
        this.origClOrdID = origClOrdID;
    }

    public double getStopPrice() {
        return stopPrice;
    }

    public void setStopPrice(double stopPrice) {
        this.stopPrice = stopPrice;
    }

    public String getOrdRejectReason() {
        return ordRejectReason;
    }

    public void setOrdRejectReason(String ordRejectReason) {
        this.ordRejectReason = ordRejectReason;
    }

    public double getMaxFloor() {
        return maxFloor;
    }

    public void setMaxFloor(double maxFloor) {
        this.maxFloor = maxFloor;
    }

    public double getLeavesQty() {
        return leavesQty;
    }

    public void setLeavesQty(double leavesQty) {
        this.leavesQty = leavesQty;
    }

    public double getPutOrCall() {
        return putOrCall;
    }

    public void setPutOrCall(double putOrCall) {
        this.putOrCall = putOrCall;
    }

    public double getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(double strikePrice) {
        this.strikePrice = strikePrice;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public double getMaximumPrice() {
        return maximumPrice;
    }

    public void setMaximumPrice(double maximumPrice) {
        this.maximumPrice = maximumPrice;
    }

    public String getBrokerID() {
        return brokerID;
    }

    public void setBrokerID(String brokerID) {
        this.brokerID = brokerID;
    }

    public String getRoutingAccount() {
        return routingAccount;
    }

    public void setRoutingAccount(String routingAccount) {
        this.routingAccount = routingAccount;
    }

    public String getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public int getInternalMatchStatus() {
        return internalMatchStatus;
    }

    public void setInternalMatchStatus(int internalMatchStatus) {
        this.internalMatchStatus = internalMatchStatus;
    }

    public int getSliceOrderInterval() {
        return sliceOrderInterval;
    }

    public void setSliceOrderInterval(int sliceOrderInterval) {
        this.sliceOrderInterval = sliceOrderInterval;
    }

    public int getSliceOrderBlock() {
        return sliceOrderBlock;
    }

    public void setSliceOrderBlock(int sliceOrderBlock) {
        this.sliceOrderBlock = sliceOrderBlock;
    }

    public String getConditionValue() {
        return conditionValue;
    }

    public void setConditionValue(String conditionValue) {
        this.conditionValue = conditionValue;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }



    public int getDeskAutoRelease() {
        return deskAutoRelease;
    }

    public void setDeskAutoRelease(int deskAutoRelease) {
        this.deskAutoRelease = deskAutoRelease;
    }

    public int getSliceOrderExecutionType() {
        return sliceOrderExecutionType;
    }

    public void setSliceOrderExecutionType(int sliceOrderExecutionType) {
        this.sliceOrderExecutionType = sliceOrderExecutionType;
    }

    public double getDeskTotalChildQty() {
        return deskTotalChildQty;
    }

    public void setDeskTotalChildQty(double deskTotalChildQty) {
        this.deskTotalChildQty = deskTotalChildQty;
    }

    public String getFolOrderRef() {
        return folOrderRef;
    }

    public void setFolOrderRef(String folOrderRef) {
        this.folOrderRef = folOrderRef;
    }

    public double getFolInterval() {
        return folInterval;
    }

    public void setFolInterval(double folInterval) {
        this.folInterval = folInterval;
    }

    public int getFolPriceStrategy() {
        return folPriceStrategy;
    }

    public void setFolPriceStrategy(int folPriceStrategy) {
        this.folPriceStrategy = folPriceStrategy;
    }

    public String getCustomerExternalRef() {
        return customerExternalRef;
    }

    public void setCustomerExternalRef(String customerExternalRef) {
        this.customerExternalRef = customerExternalRef;
    }

    public int getDeskOrderType() {
        return deskOrderType;
    }

    public void setDeskOrderType(int deskOrderType) {
        this.deskOrderType = deskOrderType;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }


    public String getMultiNINMasterRef() {
        return multiNINMasterRef;
    }

    public void setMultiNINMasterRef(String multiNINMasterRef) {
        this.multiNINMasterRef = multiNINMasterRef;
    }

    public String getMultiNINBreakDownRef() {
        return multiNINBreakDownRef;
    }

    public void setMultiNINBreakDownRef(String multiNINBreakDownRef) {
        this.multiNINBreakDownRef = multiNINBreakDownRef;
    }

    public String getMultiNINMasterOrdNo() {
        return multiNINMasterOrdNo;
    }

    public void setMultiNINMasterOrdNo(String multiNINMasterOrdNo) {
        this.multiNINMasterOrdNo = multiNINMasterOrdNo;
    }

    public String getMultiNINBatchId() {
        return multiNINBatchId;
    }

    public void setMultiNINBatchId(String multiNINBatchId) {
        this.multiNINBatchId = multiNINBatchId;
    }

    public double getWorkedQty() {
        return workedQty;
    }

    public void setWorkedQty(double workedQty) {
        this.workedQty = workedQty;
    }

    public int getTimeTriggerMode() {
        return timeTriggerMode;
    }

    public void setTimeTriggerMode(int timeTriggerMode) {
        this.timeTriggerMode = timeTriggerMode;
    }

    public double getTotalChildOrderQty() {
        return totalChildOrderQty;
    }

    public void setTotalChildOrderQty(double totalChildOrderQty) {
        this.totalChildOrderQty = totalChildOrderQty;
    }

    public int getMarginEnabledType() {
        return marginEnabledType;
    }

    public void setMarginEnabledType(int marginEnabledType) {
        this.marginEnabledType = marginEnabledType;
    }

    public String getCallCenterOrdRef() {
        return callCenterOrdRef;
    }

    public void setCallCenterOrdRef(String callCenterOrdRef) {
        this.callCenterOrdRef = callCenterOrdRef;
    }

    public String getCallCentreAgentName() {
        return callCentreAgentName;
    }

    public void setCallCentreAgentName(String callCentreAgentName) {
        this.callCentreAgentName = callCentreAgentName;
    }

    public String getOrderAction() {
        return orderAction;
    }

    public void setOrderAction(String orderAction) {
        this.orderAction = orderAction;
    }

    public String getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }
}
