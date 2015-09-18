package lk.ac.ucsc.oms.orderMgt.api.beans;

import lk.ac.ucsc.oms.common_utility.api.enums.*;
import lk.ac.ucsc.oms.orderMgt.api.IOMStatus;

import java.util.Date;
import java.util.List;


public interface Order extends Cloneable {

    /**
     * get Cl Order ID of the Order
     *
     * @return client order id
     */
    String getClOrdID();

    /**
     * set Cl Order ID for the Order
     *
     * @param id String Client Order ID
     */
    void setClOrdID(String id);

    /**
     * get Symbol of the Order
     *
     * @return symbol
     */
    String getSymbol();

    /**
     * set Symbol for the Order
     *
     * @param symbol String Symbol
     */
    void setSymbol(String symbol);

    /**
     * get Quantity of the Order
     *
     * @return qty
     */
    double getQuantity();

    /**
     * set Quantity for the Order
     *
     * @param quantity String Quantity of the Order
     */
    void setQuantity(double quantity);

    /**
     * get Price of the Order
     *
     * @return price
     */
    double getPrice();

    /**
     * set Price for the Order
     *
     * @param price double Price of the Order
     */
    void setPrice(double price);

    /**
     * get Side of the Order
     *
     * @return {@inheritDoc}
     */
    OrderSide getSide();

    /**
     * set Side for the Order
     *
     * @param side Side of the Order
     */
    void setSide(OrderSide side);

    /**
     * get Type of the Order
     *
     * @return {@inheritDoc}
     */
    OrderType getType();

    /**
     * set Type for the Order
     *
     * @param type Type of the Order
     */
    void setType(OrderType type);

    /**
     * get Exchange of the Order
     *
     * @return exchange
     */
    String getExchange();

    /**
     * set Exchange for the Order
     *
     * @param exchange String Exchange
     */
    void setExchange(String exchange);

    /**
     * get Security Account ID of the Order
     *
     * @return security account number of this order
     */
    String getSecurityAccount();

    /**
     * set Security Account ID for the Order
     *
     * @param securityAccount String Security Account
     */
    void setSecurityAccount(String securityAccount);

    /**
     * get Status of the Order
     *
     * @return {@inheritDoc}
     */
    OrderStatus getStatus();

    /**
     * set Status for the Order
     *
     * @param status Status of the Order
     */
    void setStatus(OrderStatus status);

    /**
     * get Execution List of the Order
     *
     * @return execution list
     */
    List<Execution> getExecutions();

    /**
     * set Execution List for the Order
     *
     * @param executionList List of Executions
     */
    void setExecutions(List<Execution> executionList);

    /**
     * get ID of the Order
     *
     * @return order id
     */
    String getOrdID();

    /**
     * set ID for the Order
     *
     * @param ordID String Order ID
     */
    void setOrdID(String ordID);

    /**
     * get Original Client Order ID
     *
     * @return original client order id
     */
    String getOrigClOrdID();

    /**
     * set Original Client Order ID
     *
     * @param origClOrdID String Original Client Order ID
     */
    void setOrigClOrdID(String origClOrdID);

    /**
     * get Cumulative Commission of the Order
     *
     * @return cumulative commission
     */
    double getCumCommission();

    /**
     * set Cumulative Commission for the Order
     *
     * @param cumCommission double Cumulative Commission
     */
    void setCumCommission(double cumCommission);

    /**
     * this field is calculated value.calculation done by adding the</br>
     * last share field in execution list </br>
     * fillQty = execution[1].lastShare + execution[2].lastShare + .....+ execution[i].lastShare
     *
     * @return current fill amount of the order
     */
    double getFillQty();

    /**
     * get Institution ID of the Order
     *
     * @return institutionID
     */
    String getInstitutionCode();

    /**
     * set Institution ID of the Order
     *
     * @param institutionID String Institution ID
     */
    void setInstitutionCode(String institutionID);

    /**
     * clone the Order object
     *
     * @return Order
     * @throws CloneNotSupportedException
     */
    Order clone() throws CloneNotSupportedException;

    /**
     * get Text of the Order
     *
     * @return text
     */
    String getText();

    /**
     * set Text of the Order
     *
     * @param text String Text
     */
    void setText(String text);

    /**
     * get Minimum Quantity of the Order
     *
     * @return minQty
     */
    double getMinQty();

    /**
     * set Minimum Quantity of the Order
     *
     * @param minQty double Minimum Quantity of the Order
     */
    void setMinQty(double minQty);

    /**
     * get Maximum Floor value of the Order
     *
     * @return maxFloor
     */
    double getMaxFloor();

    /**
     * set Maximum Floor value of the Order
     *
     * @param maxFloor double Maximum Floor
     */
    void setMaxFloor(double maxFloor);

    /**
     * get User Name of the Order
     *
     * @return userName
     */
    String getUserName();

    /**
     * set User Name of the Order
     *
     * @param userName String User Name
     */
    void setUserName(String userName);

    /**
     * get Client Channel of the Order
     *
     * @return {@inheritDoc}
     */
    ClientChannel getChannel();

    /**
     * set Client Channle of the Order
     *
     * @param channel ClientChannel of the Order
     */
    void setChannel(ClientChannel channel);

    /**
     * get Execution Broker ID
     *
     * @return execBrokerID
     */
    String getExecBrokerId();

    /**
     * set Execution Broker ID of the Order
     *
     * @param execBrokerId String Execution Broker ID of the Order
     */
    void setExecBrokerId(String execBrokerId);

    /**
     * check whether the Order is Day Order or not
     *
     * @return dayOrder
     */
    boolean isDayOrder();

    /**
     * set whether the Order is Day Order or not
     *
     * @param dayOrder boolean Day Order
     */
    void setDayOrder(boolean dayOrder);

    /**
     * get Expire Time of the Order
     *
     * @return expirtTime
     */
    Date getExpireTime();

    /**
     * set Expire Time of the Order
     *
     * @param expireTime Date Expire Time of the Order
     */
    void setExpireTime(Date expireTime);

    /**
     * get TimeInForce of the Order
     *
     * @return timeInForce
     */
    int getTimeInForce();

    /**
     * set TimeInForce of the Order
     *
     * @param timeInForce int Time In Force of the Order
     */
    void setTimeInForce(int timeInForce);

    /**
     * get Security Type of the Order
     *
     * @return securityType
     */
    String getSecurityType();

    /**
     * set Security Type of the Order
     *
     * @param securityType String Security Type of the Order
     */
    void setSecurityType(String securityType);

    /**
     * get Market Code of the Order
     *
     * @return marketCode
     */
    String getMarketCode();

    /**
     * set Market Code of the Order
     *
     * @param marketCode String Market Code of the Order
     */
    void setMarketCode(String marketCode);

    /**
     * get Customer Number of the Order
     *
     * @return customerNumber
     */
    String getCustomerNumber();

    /**
     * set Customer Number of the Order
     *
     * @param customerNumber String Customer Number of the Order
     */
    void setCustomerNumber(String customerNumber);

    /**
     * get Client Ip of the Order
     *
     * @return clientIp
     */
    String getClientIp();

    /**
     * set Client Ip of the Order
     *
     * @param clientIp String Client Ip of the Order
     */
    void setClientIp(String clientIp);


    /**
     * get Fix Connection ID of the Order
     *
     * @return fixConnectionID
     */
    String getFixConnectionId();

    /**
     * set Fix Connection ID of the Order
     *
     * @param fixConectionId String Fix Connection ID of the Order
     */
    void setFixConnectionId(String fixConectionId);

    /**
     * get Approved by name for the Order
     *
     * @return approvedBy
     */
    String getApprovedBy();

    /**
     * set Approved by name for the Order
     *
     * @param approvedBy String Approved By
     */
    void setApprovedBy(String approvedBy);

    /**
     * get Order Number of the Order
     *
     * @return orderNo
     */
    String getOrderNo();

    /**
     * set Order Number for the Order
     *
     * @param orderNo String Order Number of the Order
     */
    void setOrderNo(String orderNo);

    /**
     * get Transaction Time of the Order
     *
     * @return transaction Time
     */
    String getTransactionTime();

    /**
     * set Transaction Time of the Order
     *
     * @param transactionTime String Transaction Time of the Order
     */
    void setTransactionTime(String transactionTime);

    /**
     * get Currency of the Order
     *
     * @return currency
     */
    String getCurrency();

    /**
     * set Currency of the Order
     *
     * @param curency String Currency of the Order
     */
    void setCurrency(String curency);

    /**
     * get Commission  of the Order
     *
     * @return commission
     */
    double getCommission();

    /**
     * set Commission of the Order
     *
     * @param commission double Commision of the Order
     */
    void setCommission(double commission);

    /**
     * get Cumulative Quantity of the Order
     *
     * @return cumQty
     */
    double getCumQty();

    /**
     * set Cumulative Quantity of the Order
     *
     * @param cumQty int Cumulative Quantity of the Order
     */
    void setCumQty(double cumQty);

    /**
     * get Last Price of the Order
     *
     * @return lastPx
     */
    double getLastPx();

    /**
     * set Last Price of the Order
     *
     * @param lastPx double Last Price of the Order
     */
    void setLastPx(double lastPx);

    /**
     * get Last Shares of the Order
     *
     * @return lastShares
     */
    double getLastShares();

    /**
     * set Last Shares of the Order
     *
     * @param lastShares int Last Shares of the Order
     */
    void setLastShares(double lastShares);

    /**
     * get Average Price of the Order
     *
     * @return avgPrice
     */
    double getAvgPrice();

    /**
     * set Average price of the Order
     *
     * @param avgPrice average price of the order
     */
    void setAvgPrice(double avgPrice);

    /**
     * get creation date of the Order
     *
     * @return createDate
     */
    Date getCreateDate();

    /**
     * set creation date of the Order
     *
     * @param createDate creation date of the order
     */
    void setCreateDate(Date createDate);

    /**
     * get last update date of the Order
     *
     * @return lastUpdateDate
     */
    Date getLastUpdateDate();

    /**
     * set last update date of the order
     *
     * @param lastUpdateDate last update order
     */
    void setLastUpdateDate(Date lastUpdateDate);

    /**
     * get FixVersion of the Order
     *
     * @return fixversion
     */
    String getFixVersion();

    /**
     * set Fixversion of the Order
     *
     * @param fixVersion fixVersion of the Order
     */
    void setFixVersion(String fixVersion);

    /**
     * get Cxl Reject ResponseTo from Order
     *
     * @return cxlRejectResponseTo
     */
    char getCxlRejectResponseTo();

    /**
     * set CxlRejectResponseTo of the Order
     *
     * @param cxlRejectResponseTo cxl reject responseTo of the Order
     */
    void setCxlRejectResponseTo(char cxlRejectResponseTo);

    /**
     * get leaves quantity of the Order
     *
     * @return leavesQty
     */
    double getLeavesQty();

    /**
     * set leaves quantity of the Order
     *
     * @param leavesQty leaves quantity of the Order
     */
    void setLeavesQty(double leavesQty);

    /**
     * get execution id of the Order
     *
     * @return execID
     */
    String getExecID();

    /**
     * set execution ID of the Order
     *
     * @param execID execution ID of the Order
     */
    void setExecID(String execID);

    /**
     * get buy side broker ID of the Order
     *
     * @return buySideBrokerID
     */
    String getBuySideBrokerID();

    /**
     * set buy side broker ID of the order
     *
     * @param buySideBrokerID buy side broker ID of the order
     */
    void setBuySideBrokerID(String buySideBrokerID);

    /**
     * get security ID source of the order
     *
     * @return securityIDSource
     */
    String getSecurityIDSource();

    /**
     * set security ID source of the Order
     *
     * @param securityIDSource security ID source of the Order
     */
    void setSecurityIDSource(String securityIDSource);

    /**
     * get the master order trigger time
     *
     * @return masterOrderTriggeredTime
     */
    Date getMasterOrdTriggeredTime();

    /**
     * Set the master order triggered time
     *
     * @param masterOrdTriggeredTime of the order
     */
    void setMasterOrdTriggeredTime(Date masterOrdTriggeredTime);

    /**
     * get master order id of the order
     *
     * @return masterOrderId
     */
    String getMasterOrderId();

    /**
     * Set the master order id of the order
     *
     * @param masterOrderId of the order
     */
    void setMasterOrderId(String masterOrderId);

    /**
     * get stop price of the order
     *
     * @return stopPx
     */
    double getStopPx();

    /**
     * set stop price of the order
     *
     * @param stopPx of the order
     */
    void setStopPx(double stopPx);

    /**
     * get condition type of the order
     *
     * @return conditionType
     */
    int getConditionType();

    /**
     * set condition type of the order
     *
     * @param conditionType of the order
     */
    void setConditionType(int conditionType);

    /**
     * get the max price
     *
     * @return maxPrice
     */
    double getMaxPrice();

    /**
     * set max price of the order
     *
     * @param maxPrice of the order
     */
    void setMaxPrice(double maxPrice);

    /**
     * get stop price type of the order
     *
     * @return stopPxType
     */
    int getStopPxType();

    /**
     * set stop price type of the order
     *
     * @param stopPxType of the order
     */
    void setStopPxType(int stopPxType);

    /**
     * Instrument type of the order
     *
     * @return instrumentType
     */
    String getInstrumentType();

    /**
     * set instrument type of the order
     *
     * @param instrumentType of the order
     */
    void setInstrumentType(String instrumentType);

    /**
     * get institution id of the order
     *
     * @return institutionId
     */
    int getInstitutionId();

    /**
     * set institution id of the order
     *
     * @param institutionId of the order
     */
    void setInstitutionId(int institutionId);

    /**
     * get price block of the order
     *
     * @return priceBlock
     */
    double getPriceBlock();

    /**
     * set price block of the order
     *
     * @param priceBlock of the order
     */
    void setPriceBlock(double priceBlock);

    /**
     * get settle currency of the order
     *
     * @return settleCurrency
     */
    String getSettleCurrency();

    /**
     * set settle currency of the order
     *
     * @param settleCurrency of the order
     */
    void setSettleCurrency(String settleCurrency);

    /**
     * get order value of the order
     *
     * @return orderValue
     */
    double getOrderValue();

    /**
     * set order value of the order
     *
     * @param orderValue of the order
     */
    void setOrderValue(double orderValue);

    /**
     * get net value of the order
     *
     * @return netValue
     */
    double getNetValue();

    /**
     * set net value of the order
     *
     * @param netValue of the order
     */
    void setNetValue(double netValue);

    /**
     * get net settle of the order
     *
     * @return netSettle
     */
    double getNetSettle();

    /**
     * set net settle of the order
     *
     * @param netSettle of the order
     */
    void setNetSettle(double netSettle);

    /**
     * get blocked amount
     *
     * @return blockAmount
     */
    double getBlockAmount();

    /**
     * set blocked amount
     *
     * @param blockAmount of the order
     */
    void setBlockAmount(double blockAmount);

    /**
     * get order category of the order
     *
     * @return orderCategory
     */
    int getOrderCategory();

    /**
     * set order category of the order
     *
     * @param orderCategory of the order
     */
    void setOrderCategory(int orderCategory);

    /**
     * get parent account number of the order
     *
     * @return parentAccountNumber
     */
    String getParentAccountNumber();

    /**
     * set parent account number of the order
     *
     * @param parentAccountNumber of the order
     */
    void setParentAccountNumber(String parentAccountNumber);

    /**
     * get parent blocked amount of the order
     *
     * @return parentBlockedAmount
     */
    double getParentBlockAmount();

    /**
     * set parent blocked amount of the order
     *
     * @param parentBlockAmount of the order
     */
    void setParentBlockAmount(double parentBlockAmount);


    /**
     * get margin block of the order
     *
     * @return marginBlock
     */
    double getMarginBlock();

    /**
     * set margin block of the order
     *
     * @param marginBlock of the order
     */
    void setMarginBlock(double marginBlock);

    /**
     * get cumulative order value of the order
     *
     * @return cumOrderValue
     */
    double getCumOrderValue();

    /**
     * set cumulative order value of the order
     *
     * @param cumOrderValue of the order
     */
    void setCumOrderValue(double cumOrderValue);

    /**
     * get cumulative broker commission of the order
     *
     * @return cumBrokerCommission
     */
    double getCumBrokerCommission();

    /**
     * set cumulative broker commission of the order
     *
     * @param cumBrokerCommission of the order
     */
    void setCumBrokerCommission(double cumBrokerCommission);

    /**
     * get cumulative exchange commission of the order
     *
     * @return cumExchangeCommission
     */
    double getCumExchangeCommission();

    /**
     * set cumulative exchange commission of the order
     *
     * @param cumExchangeCommission of the order
     */
    void setCumExchangeCommission(double cumExchangeCommission);

    /**
     * get cumulative parent commission of the order
     *
     * @return cumParentCommission
     */
    double getCumParentCommission();

    /**
     * set cumulative parent commission of the order
     *
     * @param cumParentCommission of the order
     */
    void setCumParentCommission(double cumParentCommission);

    /**
     * get cumulative third party commission of the order
     *
     * @return cumThirdPartyCommission
     */
    double getCumThirdPartyCommission();

    /**
     * set cumulative third party commission of the order
     *
     * @param cumThirdPartyCommission of the order
     */
    void setCumThirdPartyCommission(double cumThirdPartyCommission);

    /**
     * get cumulative net value of the order
     *
     * @return cumNetValue
     */
    double getCumNetValue();

    /**
     * set cumulative net value of the order
     *
     * @param cumNetValue of the order
     */
    void setCumNetValue(double cumNetValue);

    /**
     * get cumulative net settle of the order
     *
     * @return cumNetSettle
     */
    double getCumNetSettle();

    /**
     * set cumulative net settle of the order
     *
     * @param cumNetSettle of the order
     */
    void setCumNetSettle(double cumNetSettle);

    /**
     * get transaction fee of the order
     *
     * @return transactionFee
     */
    double getTransactionFee();

    /**
     * set transaction fee of the order
     *
     * @param transactionFee of the order
     */
    void setTransactionFee(double transactionFee);


    double getDayMarginQuantity();

    void setDayMarginQuantity(double dayMarginQuantity);

    double getParentNetSettle();

    void setParentNetSettle(double parentNetSettle);

    double getOrderValueDiff();

    void setOrderValueDiff(double orderValueDiff);

    double getNetValueDiff();

    void setNetValueDiff(double netValueDiff);

    double getNetSettleDiff();

    void setNetSettleDiff(double netSettleDiff);


    double getParentNetValueDiff();

    void setParentNetValueDiff(double parentNetValueDiff);

    double getParentNetSettleDiff();

    void setParentNetSettleDiff(double parentNetSettleDiff);

    double getCommissionDiff();

    void setCommissionDiff(double commissionDiff);

    double getExchangeCommDiff();

    void setExchangeCommDiff(double exchangeCommDiff);

    double getThirdPartyCommDiff();

    void setThirdPartyCommDiff(double thirdPartyCommDiff);

    double getBrokerCommDiff();

    void setBrokerCommDiff(double brokerCommDiff);

    double getParentCommDiff();

    void setParentCommDiff(double parentCommDiff);

    double getIssueSettleRate();

    void setIssueSettleRate(double issueSettleRate);

    double getProfitLoss();

    void setProfitLoss(double profitLoss);

    double getParentProfitLoss();

    void setParentProfitLoss(double parentProfitLoss);

    int getModuleCode();

    void setModuleCode(int moduleCode);

    int getErrorCode();

    void setErrorCode(int errorCode);

    String getErrMsgParameters();

    void setErrMsgParameters(String errMsgParameters);

    double getMarginDue();

    void setMarginDue(double marginDue);

    double getDayMarginDue();

    void setDayMarginDue(double dayMarginDue);

    OrderStatus getIntermediateStatus();

    void setIntermediateStatus(OrderStatus status);

    String getReplacedOrderId();

    void setReplacedOrderId(String replacedOrderId);

    double getTempBlockAmount();

    void setTempBlockAmount(double tempBlockAmount);

    double getTempParentBlockAmount();

    void setTempParentBlockAmount(double tempParentBlockAmount);


    String getInternalRejReason();

    void setInternalRejReason(String internalRejReason);


    String getRoutingAccount();

    void setRoutingAccount(String routingAccount);

    String getUserId();

    void setUserId(String userId);

    String getNarration();

    void setNarration(String narration);



    int getOrderOrigin();

    void setOrderOrigin(int orderOrigin);

    String getUniqueTrsId();

    void setUniqueTrsId(String uniqueTrsId);

    String getRoutingAccRef();

    void setRoutingAccRef(String routingAccRef);

    String getReuterCode();

    void setReuterCode(String reuterCode);

    String getIsinCode();

    void setIsinCode(String isinCode);


    String getExchangeSymbol();

    void setExchangeSymbol(String exchangeSymbol);

    String getExecType();

    void setExecType(String execType);

    String getRemoteAccountNumber();

    void setRemoteAccountNumber(String remoteAccountNumber);

    double getPriceFactor();

    void setPriceFactor(double priceFactor);

    String getMubExecID();

    void setMubExecID(String mubExecID);


    String getBrokerFIXID();

    void setBrokerFIXID(String brokerFIXID);

    String getTargetCompID();

    void setTargetCompID(String targetCompID);

    String getTargetSubID();

    void setTargetSubID(String targetSubID);

    String getSenderSubID();

    void setSenderSubID(String senderSubID);

    String getOnBehalfOfCompID();

    void setOnBehalfOfCompID(String onBehalfOfCompID);

    String getOnBehalfOfSubID();

    void setOnBehalfOfSubID(String onBehalfOfSubID);

    String getConditionalOrderRef();

    void setConditionalOrderRef(String conditionalOrderRef);

    String getLanguageCode();

    void setLanguageCode(String languageCode);

    String getClientReqId();


    void setClientReqId(String clientReqId);


    double getTempMarginBlockAmt();

    void setTempMarginBlockAmt(double tempMarginBlockAmt);


}
