package lk.ac.ucsc.oms.orderMgt.implGeneral.bean;


import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.common_utility.api.enums.*;
import lk.ac.ucsc.oms.orderMgt.api.IOMStatus;
import lk.ac.ucsc.oms.orderMgt.api.beans.Execution;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class OrderBean extends CacheObject implements Order {
    private long id;            // primary key in the db table, auto generated
    private String clOrdID;     // primary key in cache
    private String ordID;
    private String origClOrdID;
    private String institutionCode;
    private String symbol;
    private double quantity;
    private double price;
    private String orderSide;
    private String orderType;
    private String exchange;
    private String securityAccount;
    private String orderStatus = OrderStatus.DEFAULT.getCode();
    private int institutionId;
    private List<Execution> list = new ArrayList<>();
    private String text;
    private double minQty;
    private double maxFloor;
    private String userName;
    private int channel;
    private String execBrokerId;
    private boolean isDayOrder;
    private Date expireTime;
    private int timeInForce;
    private String securityType;
    private String marketCode;
    private String customerNumber;
    private String clientIp;
    private String approvedBy;
    private String orderNo;
    private String transactionTime;
    private String currency;
    private double commission;
    private double cumCommission;
    private double cumQty;
    private double cumOrderValue;
    private double cumBrokerCommission;
    private double cumExchangeCommission;
    private double cumParentCommission;
    private double cumThirdPartyCommission;
    private double cumNetValue;
    private double cumNetSettle;
    private double transactionFee;
    private double lastPx;
    private double lastShares;
    private double avgPrice;
    private Date createDate;
    private Date lastUpdateDate;
    private String fixVersion;
    private char cxlRejectResponseTo;//do not need in the db
    private double leavesQty;
    private String execID;
    private String buySideBrokerID;
    private String securityIDSource;
    private Date masterOrdTriggeredTime;
    private String masterOrderId;
    private double stopPx;
    private int conditionType;
    private double maxPrice;
    private int stopPxType;
    private String instrumentType;
    private double priceBlock;//TODO Dasun rename this as blockPrice
    private String settleCurrency;
    private double orderValue;
    private double netValue;
    private double netSettle;
    private double blockAmount;
    private int orderCategory;
    private String parentAccountNumber;
    private double parentBlockAmount;
    private double marginBlock;
    private double dayMarginQuantity;
    private double parentNetSettle;
    private double orderValueDiff;
    private double netValueDiff;
    private double netSettleDiff; //TODO-SS-- --dasun-- please define these variables very clearly.
    private double parentNetValueDiff;
    private double parentNetSettleDiff;
    private double commissionDiff;
    private double exchangeCommDiff;
    private double thirdPartyCommDiff;
    private double brokerCommDiff;
    private double parentCommDiff;
    private double issueSettleRate;
    private double profitLoss;
    private double parentProfitLoss;
    private int moduleCode;
    private int errorCode;
    private String errMsgParameters;
    private String languageCode;
    private double marginDue;
    private double dayMarginDue;
    private String intermediateOrderStatus = OrderStatus.RECEIVED.getCode();
    private String replacedOrderId;
    private double tempBlockAmt; //no need to persist this value
    private double tempMarginBlockAmt; //no need to persist this value
    private double tempParentBlockAmt; //no need to persist this value
    private String conditionalOrderRef;// Reference for the conditional Order
    private String internalRejReason;
    private String routingAccount; //set the exchange account number
    private IOMStatus internalMatchStatus = IOMStatus.DEFAULT;
    private String userId;
    private String narration;// no need to persist this value
    private int orderOrigin; //set the order origin. if front office 1 else if back office 0
    private String uniqueTrsId; // two purposes: measuring response time and sync/async mapping in http clients
    private String clientReqId; // no need to persist, a requirement for DT
    private String routingAccRef;
    private String reuterCode; // no need to persist
    private String isinCode; // no need to persist
    private String exchangeSymbol; // no need to persist
    private String execType = OrderStatus.DEFAULT.getCode();   // no need to persist
    private String remoteAccountNumber;
    private double priceFactor;
    private String mubExecID; // no need to persist
    private String brokerFIXID;
    private String targetCompID;
    private String targetSubID;
    private String senderSubID;
    private String onBehalfOfCompID;
    private String onBehalfOfSubID;
    private String fixConnectionId;


    /**
     * constructor for order bean which will assign instantiate date as the createDate and lastUpdatedDate of the bean
     */
    public OrderBean() {
        createDate = new Date();
        lastUpdateDate = createDate;
    }

    /**
     * get id of order bean
     *
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * set id of the order bean
     *
     * @param id of order bean
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMarketCode() {
        return marketCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSecurityType() {
        return securityType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getMinQty() {
        return minQty;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMinQty(double minQty) {
        this.minQty = minQty;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getMaxFloor() {
        return maxFloor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMaxFloor(double maxFloor) {
        this.maxFloor = maxFloor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserName() {
        return userName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClientChannel getChannel() {
        return ClientChannel.getEnum(channel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setChannel(ClientChannel channel) {
        this.channel = channel.getCode();
    }

    /**
     * get Channel Code as a int
     *
     * @return channelCode
     */
    public int getChannelCode() {
        return channel;
    }

    /**
     * set Channel Code using a int
     *
     * @param channel of the bean
     */
    public void setChannelCode(int channel) {
        this.channel = channel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getExecBrokerId() {
        return execBrokerId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExecBrokerId(String execBrokerId) {
        this.execBrokerId = execBrokerId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDayOrder() {
        return isDayOrder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDayOrder(boolean dayOrder) {
        isDayOrder = dayOrder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getExpireTime() {
        return expireTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTimeInForce() {
        return timeInForce;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTimeInForce(int timeInForce) {
        this.timeInForce = timeInForce;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getClOrdID() {
        return clOrdID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setClOrdID(String clOrdID) {
        this.clOrdID = clOrdID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOrdID() {
        return ordID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOrdID(String ordID) {
        this.ordID = ordID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOrigClOrdID() {
        return origClOrdID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOrigClOrdID(String origClOrdID) {
        this.origClOrdID = origClOrdID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSymbol() {
        return symbol;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getQuantity() {
        return quantity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPrice() {
        return price;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderSide getSide() {
        return OrderSide.getEnum(orderSide);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSide(OrderSide side) {
        this.orderSide = side != null ? side.getCode() : "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderType getType() {
        return OrderType.getEnum(orderType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setType(OrderType type) {
        this.orderType = type != null ? type.getCode() : "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getExchange() {
        return exchange;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSecurityAccount() {
        return securityAccount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSecurityAccount(String securityAccount) {
        this.securityAccount = securityAccount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderStatus getStatus() {
        return OrderStatus.getEnum(orderStatus);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStatus(OrderStatus status) {
        this.orderStatus = status != null ? status.getCode() : OrderStatus.RECEIVED.getCode();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Execution> getExecutions() {
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExecutions(List<Execution> executionList) {
        this.list = executionList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getCumCommission() {
        return cumCommission;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCumCommission(double cumCommission) {
        this.cumCommission = cumCommission;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getFillQty() {
        double filledQty = 0;
        for (Execution e : this.list) {
            filledQty += e.getLastShare();
        }
        return filledQty;
    }

    /**
     * get Order Side as a String
     *
     * @return orderSide
     */
    public String getOrderSide() {
        return orderSide;
    }

    /**
     * set Order Side using a String
     *
     * @param orderSide of the order
     */
    public void setOrderSide(String orderSide) {
        this.orderSide = orderSide;
    }

    /**
     * get Order Type as a String
     *
     * @return orderType
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * set Order Type using a String
     *
     * @param orderType of the order
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    /**
     * get Order Status as a String
     *
     * @return orderStatus
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * set Order Status using a String
     *
     * @param orderStatus of the order
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getInstitutionCode() {
        return institutionCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getInstitutionId() {
        return institutionId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInstitutionId(int institutionId) {
        this.institutionId = institutionId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getText() {
        return text;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setText(String text) {
        this.text = text;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCustomerNumber() {
        return customerNumber;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getClientIp() {
        return clientIp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getFixConnectionId() {
        return fixConnectionId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFixConnectionId(String fixConnectionId) {
        this.fixConnectionId = fixConnectionId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getApprovedBy() {
        return approvedBy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTransactionTime() {
        return transactionTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCurrency() {
        return currency;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getCommission() {
        return commission;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCommission(double commission) {
        this.commission = commission;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getCumQty() {
        return cumQty;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCumQty(double cumQty) {
        this.cumQty = cumQty;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getLastPx() {
        return lastPx;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLastPx(double lastPx) {
        this.lastPx = lastPx;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getLastShares() {
        return lastShares;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLastShares(double lastShares) {
        this.lastShares = lastShares;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getAvgPrice() {
        return avgPrice;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAvgPrice(double avgPrice) {
        this.avgPrice = avgPrice;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFixVersion() {
        return fixVersion;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFixVersion(String fixVersion) {
        this.fixVersion = fixVersion;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char getCxlRejectResponseTo() {
        return cxlRejectResponseTo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCxlRejectResponseTo(char cxlRejectResponseTo) {
        this.cxlRejectResponseTo = cxlRejectResponseTo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getLeavesQty() {
        return leavesQty;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLeavesQty(double leavesQty) {
        this.leavesQty = leavesQty;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getExecID() {
        return execID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExecID(String execID) {
        this.execID = execID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBuySideBrokerID() {
        return buySideBrokerID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBuySideBrokerID(String buySideBrokerID) {
        this.buySideBrokerID = buySideBrokerID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSecurityIDSource() {
        return securityIDSource;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSecurityIDSource(String securityIDSource) {
        this.securityIDSource = securityIDSource;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Date getMasterOrdTriggeredTime() {
        return masterOrdTriggeredTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMasterOrdTriggeredTime(Date masterOrdTriggeredTime) {
        this.masterOrdTriggeredTime = masterOrdTriggeredTime;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getMasterOrderId() {
        return masterOrderId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMasterOrderId(String masterOrderId) {
        this.masterOrderId = masterOrderId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getStopPx() {
        return stopPx;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStopPx(double stopPx) {
        this.stopPx = stopPx;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getConditionType() {
        return conditionType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setConditionType(int conditionType) {
        this.conditionType = conditionType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getMaxPrice() {
        return maxPrice;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getStopPxType() {
        return stopPxType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStopPxType(int stopPxType) {
        this.stopPxType = stopPxType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getInstrumentType() {
        return instrumentType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    /**
     * method to clone order bean to another and return that order
     *
     * @return order
     * @throws CloneNotSupportedException
     */
    @Override
    public Order clone() throws CloneNotSupportedException {
        OrderBean obClone = (OrderBean) super.clone();
        obClone.list = null;
        return obClone;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPriceBlock() {
        return priceBlock;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPriceBlock(double priceBlock) {
        this.priceBlock = priceBlock;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSettleCurrency() {
        return settleCurrency;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSettleCurrency(String settleCurrency) {
        this.settleCurrency = settleCurrency;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getOrderValue() {
        return orderValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOrderValue(double orderValue) {
        this.orderValue = orderValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getNetValue() {
        return netValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNetValue(double netValue) {
        this.netValue = netValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getNetSettle() {
        return netSettle;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNetSettle(double netSettle) {
        this.netSettle = netSettle;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getBlockAmount() {
        return blockAmount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBlockAmount(double blockAmount) {
        this.blockAmount = blockAmount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getOrderCategory() {
        return orderCategory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOrderCategory(int orderCategory) {
        this.orderCategory = orderCategory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getParentAccountNumber() {
        return parentAccountNumber;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setParentAccountNumber(String parentAccountNumber) {
        this.parentAccountNumber = parentAccountNumber;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getParentBlockAmount() {
        return parentBlockAmount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setParentBlockAmount(double parentBlockAmount) {
        this.parentBlockAmount = parentBlockAmount;
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public double getMarginBlock() {
        return marginBlock;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMarginBlock(double marginBlock) {
        this.marginBlock = marginBlock;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getCumOrderValue() {
        return cumOrderValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCumOrderValue(double cumOrderValue) {
        this.cumOrderValue = cumOrderValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getCumBrokerCommission() {
        return cumBrokerCommission;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCumBrokerCommission(double cumBrokerCommission) {
        this.cumBrokerCommission = cumBrokerCommission;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getCumExchangeCommission() {
        return cumExchangeCommission;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCumExchangeCommission(double cumExchangeCommission) {
        this.cumExchangeCommission = cumExchangeCommission;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getCumParentCommission() {
        return cumParentCommission;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCumParentCommission(double cumParentCommission) {
        this.cumParentCommission = cumParentCommission;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getCumThirdPartyCommission() {
        return cumThirdPartyCommission;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCumThirdPartyCommission(double cumThirdPartyCommission) {
        this.cumThirdPartyCommission = cumThirdPartyCommission;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getCumNetValue() {
        return cumNetValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCumNetValue(double cumNetValue) {
        this.cumNetValue = cumNetValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getCumNetSettle() {
        return cumNetSettle;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCumNetSettle(double cumNetSettle) {
        this.cumNetSettle = cumNetSettle;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getTransactionFee() {
        return transactionFee;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTransactionFee(double transactionFee) {
        this.transactionFee = transactionFee;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public double getDayMarginQuantity() {
        return dayMarginQuantity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDayMarginQuantity(double dayMarginQuantity) {
        this.dayMarginQuantity = dayMarginQuantity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getParentNetSettle() {
        return parentNetSettle;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setParentNetSettle(double parentNetSettle) {
        this.parentNetSettle = parentNetSettle;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getOrderValueDiff() {
        return orderValueDiff;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOrderValueDiff(double orderValueDiff) {
        this.orderValueDiff = orderValueDiff;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getNetValueDiff() {
        return netValueDiff;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNetValueDiff(double netValueDiff) {
        this.netValueDiff = netValueDiff;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getNetSettleDiff() {
        return netSettleDiff;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNetSettleDiff(double netSettleDiff) {
        this.netSettleDiff = netSettleDiff;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getParentNetValueDiff() {
        return parentNetValueDiff;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setParentNetValueDiff(double parentNetValueDiff) {
        this.parentNetValueDiff = parentNetValueDiff;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getParentNetSettleDiff() {
        return parentNetSettleDiff;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setParentNetSettleDiff(double parentNetSettleDiff) {
        this.parentNetSettleDiff = parentNetSettleDiff;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getCommissionDiff() {
        return commissionDiff;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCommissionDiff(double commissionDiff) {
        this.commissionDiff = commissionDiff;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getExchangeCommDiff() {
        return exchangeCommDiff;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExchangeCommDiff(double exchangeCommDiff) {
        this.exchangeCommDiff = exchangeCommDiff;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getThirdPartyCommDiff() {
        return thirdPartyCommDiff;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setThirdPartyCommDiff(double thirdPartyCommDiff) {
        this.thirdPartyCommDiff = thirdPartyCommDiff;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getBrokerCommDiff() {
        return brokerCommDiff;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBrokerCommDiff(double brokerCommDiff) {
        this.brokerCommDiff = brokerCommDiff;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getParentCommDiff() {
        return parentCommDiff;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setParentCommDiff(double parentCommDiff) {
        this.parentCommDiff = parentCommDiff;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getIssueSettleRate() {
        return issueSettleRate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIssueSettleRate(double issueSettleRate) {
        this.issueSettleRate = issueSettleRate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getProfitLoss() {
        return profitLoss;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setProfitLoss(double profitLoss) {
        this.profitLoss = profitLoss;
    }

    @Override
    public double getParentProfitLoss() {
        return parentProfitLoss;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setParentProfitLoss(double parentProfitLoss) {
        this.parentProfitLoss = parentProfitLoss;
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public int getModuleCode() {
        return moduleCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setModuleCode(int moduleCode) {
        this.moduleCode = moduleCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getErrMsgParameters() {
        return errMsgParameters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setErrMsgParameters(String errMsgParameters) {
        this.errMsgParameters = errMsgParameters;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getMarginDue() {
        return marginDue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMarginDue(double marginDue) {
        this.marginDue = marginDue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getDayMarginDue() {
        return dayMarginDue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDayMarginDue(double dayMarginDue) {
        this.dayMarginDue = dayMarginDue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderStatus getIntermediateStatus() {
        return OrderStatus.getEnum(intermediateOrderStatus);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIntermediateStatus(OrderStatus status) {
        this.intermediateOrderStatus = status != null ? status.getCode() : OrderStatus.RECEIVED.getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getReplacedOrderId() {
        return replacedOrderId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setReplacedOrderId(String replacedOrderId) {
        this.replacedOrderId = replacedOrderId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getTempBlockAmount() {
        return tempBlockAmt;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTempBlockAmount(double tempBlockAmount) {
        this.tempBlockAmt = tempBlockAmount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getTempMarginBlockAmt() {
        return tempMarginBlockAmt;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTempMarginBlockAmt(double tempMarginBlockAmt) {
        this.tempMarginBlockAmt = tempMarginBlockAmt;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public double getTempParentBlockAmount() {
        return tempParentBlockAmt;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTempParentBlockAmount(double tempParentBlockAmount) {
        this.tempParentBlockAmt = tempParentBlockAmount;
    }

    /**
     * get Order Status as a String
     *
     * @return orderStatus
     */
    public String getIntermediateOrderStatus() {
        return intermediateOrderStatus;
    }

    /**
     * set Order Status using a String
     *
     * @param intermediateOrderStatus of the order
     */
    public void setIntermediateOrderStatus(String intermediateOrderStatus) {
        this.intermediateOrderStatus = intermediateOrderStatus;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getInternalRejReason() {
        return internalRejReason;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInternalRejReason(String internalRejReason) {
        this.internalRejReason = internalRejReason;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRoutingAccount() {
        return routingAccount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRoutingAccount(String routingAccount) {
        this.routingAccount = routingAccount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUniqueTrsId() {
        return uniqueTrsId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUniqueTrsId(String uniqueTrsId) {
        this.uniqueTrsId = uniqueTrsId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getReuterCode() {
        return reuterCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setReuterCode(String reuterCode) {
        this.reuterCode = reuterCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIsinCode() {
        return isinCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIsinCode(String isinCode) {
        this.isinCode = isinCode;
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
     * @return the exec type
     */
    @Override
    public String getExecType() {
        return execType;
    }

    /**
     * @param execType is the order exec type
     */
    @Override
    public void setExecType(String execType) {
        this.execType = execType;
    }

    /**
     * @return the remote account number
     */
    @Override
    public String getRemoteAccountNumber() {
        return remoteAccountNumber;
    }

    /**
     * @param remoteAccountNumber is the remote order account number
     */
    @Override
    public void setRemoteAccountNumber(String remoteAccountNumber) {
        this.remoteAccountNumber = remoteAccountNumber;
    }

    /**
     * @return the price factor
     */
    @Override
    public double getPriceFactor() {
        return priceFactor;
    }

    /**
     * @param priceFactor is the price factor
     */
    @Override
    public void setPriceFactor(double priceFactor) {
        this.priceFactor = priceFactor;
    }

    /**
     * @return the mubasher execution id
     */
    @Override
    public String getMubExecID() {
        return mubExecID;
    }

    /**
     * @param mubExecID is the mubasher execution id
     */
    @Override
    public void setMubExecID(String mubExecID) {
        this.mubExecID = mubExecID;
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


    /**
     * get internal order match status as int
     */
    public int getImStatus() {
        return internalMatchStatus != null ? internalMatchStatus.getCode() : IOMStatus.DEFAULT.getCode();
    }

    /**
     * set internal order match status as int
     */
    public void setImStatus(int imStatus) {
        internalMatchStatus = IOMStatus.getEnum(imStatus);
    }

    /**
     * get the user id
     *
     * @return
     */
    @Override
    public String getUserId() {
        return userId;
    }

    /**
     * Set the user id. This is the logged in id of the customer or the dealer who has placed the order
     *
     * @param userId
     */
    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Get the narration of the order
     *
     * @return the narration
     */
    @Override
    public String getNarration() {
        return narration;
    }

    /**
     * set the narration for the order
     *
     * @param narration
     */
    @Override
    public void setNarration(String narration) {
        this.narration = narration;
    }


    /**
     * Get the order origin
     *
     * @return
     */
    @Override
    public int getOrderOrigin() {
        return orderOrigin;
    }

    /**
     * Set the order origin
     *
     * @param orderOrigin
     */
    @Override
    public void setOrderOrigin(int orderOrigin) {
        this.orderOrigin = orderOrigin;
    }

    @Override
    public String getRoutingAccRef() {
        return routingAccRef;
    }

    @Override
    public void setRoutingAccRef(String routingAccRef) {
        this.routingAccRef = routingAccRef;
    }

    @Override
    public String getConditionalOrderRef() {
        return conditionalOrderRef;
    }

    @Override
   public void setConditionalOrderRef(String conditionalOrderRef) {
        this.conditionalOrderRef = conditionalOrderRef;
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
    public String getClientReqId() {
        return clientReqId;
    }

    @Override
    public void setClientReqId(String clientReqId) {
        this.clientReqId = clientReqId;
    }



}
