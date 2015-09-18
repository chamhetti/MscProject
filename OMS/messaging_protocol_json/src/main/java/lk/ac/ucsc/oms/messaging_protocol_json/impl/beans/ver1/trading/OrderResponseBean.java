package lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.trading;

import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import com.google.gson.annotations.SerializedName;

import static lk.ac.ucsc.oms.messaging_protocol_json.api.FieldConstants.*;


public class OrderResponseBean implements Message {

    @SerializedName(AVERAGE_PRICE)
    private double averagePrice = 0;

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

    @SerializedName(ORDER_STATUS)
    private String orderStatus = null;

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

    @SerializedName(MAXIMUM_FLOOR)
    private long maxFlow = 0;

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

    @SerializedName(LAST_SHARES)
    private long lastShare = 0;

    @SerializedName(ORDER_ID)
    private String orderID = null;

    @SerializedName(ORIGINAL_CLIENT_ORDER_ID)
    private String originalCLOrdID = null;

    @SerializedName(TEXT)
    private String text = null;

    @SerializedName(ORDER_REJECT_REASON)
    private String orderRejectReason;

    @SerializedName(NIN)
    private String nin = null;

    @SerializedName(EXECUTION_BROKER_SID)
    private String executionBrokerSID;

    @SerializedName(EXECUTION_BROKER_ID)
    private int executionBrokerID = 0;

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
    private String algoOrdReference = null;

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

    @SerializedName(ORDER_CATEGORY)
    private int orderCategory = -1;

    @SerializedName(INSTRUMENT_TYPE)
    private String instrumentType = null;

    @SerializedName(ALGO_TYPE)
    private int sliceOrderExecutionType = -1;

    @SerializedName(SLICE_ORDER_INTERVAL_TYPE)
    private int sliceOrderIntervalType = -1;

    @SerializedName(SLICE_ORDER_BLOCK)
    private int sliceOrderBlockSize = 0;

    @SerializedName(SLICE_ORDER_INTERVAL)
    private int sliceOrderInterval;

    @SerializedName(EXPIRY_DATE)
    private String sliceOrderExpDate;

    @SerializedName(CUSTOMER_ID)
    private String customerID;

    @SerializedName(CUSTOMER_NAME)
    private String customerName;

    @SerializedName(INSTITUTE_ID)
    private String instituteCode;

    @SerializedName(DEALER_NAME)
    private String dealerName;

    @SerializedName(FOL_ORDER_REFERENCE)
    private String folOrdReference = null;

    @SerializedName(CHANNEL_ID)
    private int channelId = -1;

    @SerializedName(INTERNAL_MATCH_STATUS)
    private int internalMatchStatus;

    @SerializedName(MULTININ_MASTER_REF)
    private String multiNINMasterRef;

    @SerializedName(MULTININ_BREAKDOWN_REF)
    private String multiNINBreakDownRef;

    @SerializedName(MULTININ_MASTER_NO)
    private String multiNINMasterOrdNo;

    @SerializedName(MULTININ_BATCH_NO)
    private String multiNINBatchId;

    @SerializedName(TIME_TRIGGER_MODE)
    private int timeTriggerMode = 0;

    @SerializedName(CALL_CENTER_ORD_REF)
    private String callCenterOrdRef;

    @SerializedName(CALL_CENTRE_AGENT_NAME)
    private String callCentreAgentName;

    public double getAveragePrice() {
        return averagePrice;

    }

    public void setAveragePrice(double averagePrice) {
        this.averagePrice = averagePrice;
    }

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

    public String getOrdStatus() {
        return orderStatus;
    }

    public void setOrdStatus(String ordStatus) {
        this.orderStatus = ordStatus;
    }

    public String getOrdType() {
        return orderType;
    }

    public void setOrdType(String ordType) {
        this.orderType = ordType;
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

    public long getMaxFloor() {
        return maxFlow;
    }

    public void setMaxFloor(long maxFloor) {
        this.maxFlow = maxFloor;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public int getMubasherSecurityType() {
        return securityType;
    }

    public void setMubasherSecurityType(int mubasherSecurityType) {
        this.securityType = mubasherSecurityType;
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

    public String getUserID() {
        return userId;
    }

    public void setUserID(String userID) {
        this.userId = userID;
    }

    public int getDayOrder() {
        return dayOrder;
    }

    public void setDayOrder(int dayOrder) {
        this.dayOrder = dayOrder;
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

    public long getLastShares() {
        return lastShare;
    }

    public void setLastShares(long lastShares) {
        this.lastShare = lastShares;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrigClOrdID() {
        return originalCLOrdID;
    }

    public void setOrigClOrdID(String origClOrdID) {
        this.originalCLOrdID = origClOrdID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getOrdRejectReason() {
        return orderRejectReason;
    }

    public void setOrdRejectReason(String ordRejectReason) {
        this.orderRejectReason = ordRejectReason;
    }

    public String getnIN() {
        return nin;
    }

    public void setnIN(String nIN) {
        this.nin = nIN;
    }

    public String getExecBrokerSID() {
        return executionBrokerSID;
    }

    public void setExecBrokerSID(String execBrokerSID) {
        this.executionBrokerSID = execBrokerSID;
    }

    public int getExecBrokerID() {
        return executionBrokerID;
    }

    public void setExecBrokerID(int execBrokerID) {
        this.executionBrokerID = execBrokerID;
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

    public double getOrdValue() {
        return orderValue;
    }

    public void setOrdValue(double ordValue) {
        this.orderValue = ordValue;
    }

    public double getCumCommission() {
        return cumulativeCommission;
    }

    public void setCumCommission(double cumCommission) {
        this.cumulativeCommission = cumCommission;
    }

    public double getOrderNetValue() {
        return netOrdValue;
    }

    public void setOrderNetValue(double orderNetValue) {
        this.netOrdValue = orderNetValue;
    }

    public double getCumOrderValue() {
        return cumulativeOrdValue;
    }

    public void setCumOrderValue(double cumOrderValue) {
        this.cumulativeOrdValue = cumOrderValue;
    }

    public double getCumOrderNetValue() {
        return cumOrdNetValue;
    }

    public void setCumOrderNetValue(double cumOrderNetValue) {
        this.cumOrdNetValue = cumOrderNetValue;
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

    public String getMubasherOrderNumber() {
        return mubasherOrdNumber;
    }

    public void setMubasherOrderNumber(String mubasherOrderNumber) {
        this.mubasherOrdNumber = mubasherOrderNumber;
    }

    public String getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(String lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getDeskOrderRef() {
        return deskOrdReference;
    }

    public void setDeskOrderRef(String deskOrderRef) {
        this.deskOrdReference = deskOrderRef;
    }

    public String getAlgoOrderRef() {
        return algoOrdReference;
    }

    public void setAlgoOrderRef(String algoOrderRef) {
        this.algoOrdReference = algoOrderRef;
    }

    public String getOrigDeskOrderRef() {
        return originalDeskOrdReference;
    }

    public void setOrigDeskOrderRef(String origDeskOrderRef) {
        this.originalDeskOrdReference = origDeskOrderRef;
    }

    public String getMasterOrderRef() {
        return masterOrdReference;
    }

    public void setMasterOrderRef(String masterOrderRef) {
        this.masterOrdReference = masterOrderRef;
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

    public double getCumOrderNetSettle() {
        return cumulativeOrderNetSettle;
    }

    public void setCumOrderNetSettle(double cumOrderNetSettle) {
        this.cumulativeOrderNetSettle = cumOrderNetSettle;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getOrderCategory() {
        return orderCategory;
    }

    public void setOrderCategory(int orderCategory) {
        this.orderCategory = orderCategory;
    }

    public String getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    public int getSliceOrderExecutionType() {
        return sliceOrderExecutionType;
    }

    public void setSliceOrderExecutionType(int sliceOrderExecutionType) {
        this.sliceOrderExecutionType = sliceOrderExecutionType;
    }

    public int getSliceOrderIntervalType() {
        return sliceOrderIntervalType;
    }

    public void setSliceOrderIntervalType(int sliceOrderIntervalType) {
        this.sliceOrderIntervalType = sliceOrderIntervalType;
    }

    public int getSliceOrderBlockSize() {
        return sliceOrderBlockSize;
    }

    public void setSliceOrderBlockSize(int sliceOrderBlockSize) {
        this.sliceOrderBlockSize = sliceOrderBlockSize;
    }

    public int getSliceOrderInterval() {
        return sliceOrderInterval;
    }

    public void setSliceOrderInterval(int sliceOrderInterval) {
        this.sliceOrderInterval = sliceOrderInterval;
    }

    public String getSliceOrderExpDate() {
        return sliceOrderExpDate;
    }

    public void setSliceOrderExpDate(String sliceOrderExpDate) {
        this.sliceOrderExpDate = sliceOrderExpDate;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getInstituteCode() {
        return instituteCode;
    }

    public void setInstituteCode(String instituteCode) {
        this.instituteCode = instituteCode;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getFolOrdReference() {
        return folOrdReference;
    }

    public void setFolOrdReference(String folOrdReference) {
        this.folOrdReference = folOrdReference;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public int getInternalMatchStatus() {
        return internalMatchStatus;
    }

    public void setInternalMatchStatus(int internalMatchStatus) {
        this.internalMatchStatus = internalMatchStatus;
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

    public int getTimeTriggerMode() {
        return timeTriggerMode;
    }

    public void setTimeTriggerMode(int timeTriggerMode) {
        this.timeTriggerMode = timeTriggerMode;
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
}
