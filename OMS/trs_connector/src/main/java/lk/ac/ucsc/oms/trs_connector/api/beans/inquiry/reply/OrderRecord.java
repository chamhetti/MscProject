package lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.reply;

import java.util.Date;

/**
 * @author: Shashikak
 */

public interface OrderRecord {

    String getClOrdID();

    void setClOrdID(String clOrdID);

    String getOrdID();

    void setOrdID(String ordID);

    String getOrigClOrdID();

    void setOrigClOrdID(String origClOrdID);

    String getInstitutionCode();

    void setInstitutionCode(String institutionCode);

    String getSymbol();

    void setSymbol(String symbol);

    double getQuantity();

    void setQuantity(double quantity);

    double getPrice();

    void setPrice(double price);

    String getOrderSide();

    void setOrderSide(String orderSide);

    String getOrderType();

    void setOrderType(String orderType);

    String getExchange();

    void setExchange(String exchange);

    String getSecurityAccount();

    void setSecurityAccount(String securityAccount);

    String getOrderStatus();

    void setOrderStatus(String orderStatus);

    int getInstitutionId();

    void setInstitutionId(int institutionId);

    String getText();

    void setText(String text);

    double getMinQty();

    void setMinQty(double minQty);

    double getMaxFloor();

    void setMaxFloor(double maxFloor);

    String getUserName();

    void setUserName(String userName);

    int getChannel();

    void setChannel(int channel);

    String getExecBrokerId();

    void setExecBrokerId(String execBrokerId);

    boolean isDayOrder();

    void setDayOrder(boolean dayOrder);

    Date getExpireTime();

    void setExpireTime(Date expireTime);

    int getTimeInForce();

    void setTimeInForce(int timeInForce);

    String getSecurityType();

    void setSecurityType(String securityType);

    String getMarketCode();

    void setMarketCode(String marketCode);

    String getCustomerNumber();

    void setCustomerNumber(String customerNumber);

    String getClientIp();

    void setClientIp(String clientIp);

    String getRemoteClOrdId();

    void setRemoteClOrdId(String remoteClOrdId);

    String getRemoteOrigOrdId();

    void setRemoteOrigOrdId(String remoteOrigOrdId);

    String getFixConnectionId();

    void setFixConnectionId(String fixConnectionId);

    String getApprovedBy();

    void setApprovedBy(String approvedBy);

    String getOrderNo();

    void setOrderNo(String orderNo);

    String getTransactionTime();

    void setTransactionTime(String transactionTime);

    String getCurrency();

    void setCurrency(String currency);

    double getCommission();

    void setCommission(double commission);

    double getCumCommission();

    void setCumCommission(double cumCommission);

    double getCumQty();

    void setCumQty(double cumQty);

    double getCumOrderValue();

    void setCumOrderValue(double cumOrderValue);

    double getCumBrokerCommission();

    void setCumBrokerCommission(double cumBrokerCommission);

    double getCumExchangeCommission();

    void setCumExchangeCommission(double cumExchangeCommission);

    double getCumParentCommission();

    void setCumParentCommission(double cumParentCommission);

    double getCumThirdPartyCommission();

    void setCumThirdPartyCommission(double cumThirdPartyCommission);

    double getCumNetValue();

    void setCumNetValue(double cumNetValue);

    double getCumNetSettle();

    void setCumNetSettle(double cumNetSettle);

    double getTransactionFee();

    void setTransactionFee(double transactionFee);

    double getLastPx();

    void setLastPx(double lastPx);

    double getLastShares();

    void setLastShares(double lastShares);

    double getAvgPrice();

    void setAvgPrice(double avgPrice);

    Date getCreateDate();

    void setCreateDate(Date createDate);

    Date getLastUpdateDate();

    void setLastUpdateDate(Date lastUpdateDate);

    String getFixVersion();

    void setFixVersion(String fixVersion);

    double getLeavesQty();

    void setLeavesQty(double leavesQty);

    double getFillQty();

    void setFillQty(double fillQty);

    String getExecID();

    void setExecID(String execID);

    String getBuySideBrokerID();

    void setBuySideBrokerID(String buySideBrokerID);

    String getSecurityIDSource();

    void setSecurityIDSource(String securityIDSource);

    int getSliceOrderStatus();

    void setSliceOrderStatus(int sliceOrderStatus);

    int getSliceOrderExecType();

    void setSliceOrderExecType(int sliceOrderExecType);

    Date getMasterOrdTriggeredTime();

    void setMasterOrdTriggeredTime(Date masterOrdTriggeredTime);

    int getSliceOrdInterval();

    void setSliceOrdInterval(int sliceOrdInterval);

    int getSliceOrderBlock();

    void setSliceOrderBlock(int sliceOrderBlock);

    String getSliceOrderRef();

    void setSliceOrderRef(String masterOrderId);

    double getStopPx();

    void setStopPx(double stopPx);

    int getConditionType();

    void setConditionType(int conditionType);

    double getMaxPrice();

    void setMaxPrice(double maxPrice);

    int getStopPxType();

    void setStopPxType(int stopPxType);

    String getInstrumentType();

    void setInstrumentType(String instrumentType);

    double getPriceBlock();

    void setPriceBlock(double priceBlock);

    String getSettleCurrency();

    void setSettleCurrency(String settleCurrency);

    double getOrderValue();

    void setOrderValue(double orderValue);

    double getNetValue();

    void setNetValue(double netValue);

    double getNetSettle();

    void setNetSettle(double netSettle);

    double getBlockAmount();

    void setBlockAmount(double blockAmount);

    int getOrderCategory();

    void setOrderCategory(int orderCategory);

    String getParentAccountNumber();

    void setParentAccountNumber(String parentAccountNumber);

    double getParentBlockAmount();

    void setParentBlockAmount(double parentBlockAmount);

    String getSettlementType();

    void setSettlementType(String settlementType);

    double getMarginBlock();

    void setMarginBlock(double marginBlock);

    boolean isUnsolicitedIndicator();

    void setUnsolicitedIndicator(boolean unsolicitedIndicator);

    double getDayMarginQuantity();

    void setDayMarginQuantity(double dayMarginQuantity);

    double getParentNetSettle();

    void setParentNetSettle(double parentNetSettle);

    int getOrderServiceType();

    void setOrderServiceType(int orderServiceType);

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

    String getIntermediateOrderStatus();

    void setIntermediateOrderStatus(String intermediateOrderStatus);

    String getReplacedOrderId();

    void setReplacedOrderId(String replacedOrderId);

    double getSpread();

    void setSpread(double spread);

    double getParentSpread();

    void setParentSpread(double parentSpread);

    double getStrikePrice();

    void setStrikePrice(double strikePrice);

    double getPutOrCall();

    void setPutOrCall(double putOrCall);

    String getFolOrderRef();

    void setFolOrderRef(String folOrderRef);

    String getDeskOrderRef();

    void setDeskOrderRef(String deskOrderRef);

    String getRoutingAccount();

    void setRoutingAccount(String routingAccount);

    int getInternalMatchStatus();

    void setInternalMatchStatus(int internalMatchStatus);

    String getInternallyMatchWith();

    void setInternallyMatchWith(String internallyMatchWith);

    String getUserId();

    void setUserId(String userId);

    String getNarration();

    void setNarration(String narration);

    String getRejectReason();

    void setRejectReason(String rejectReason);

    String getInternalOrderStatus();

    void setInternalOrderStatus(String internalOrderStatus);

    String getDealerName();

    void setDealerName(String dealerName);

    String getConditionValue();

    void setConditionValue(String conditionValue);

    String getOperator();

    void setOperator(String operator);

    Date getStartDate();

    void setStartDate(Date startDate);

    Date getConditionExpireTime();

    void setConditionExpireTime(Date conditionExpireTime);

    int getConditionStatus();

    void setConditionStatus(int conditionStatus);

    String getConditionParameter();

    void setConditionParameter(String conditionParameter);

    int getDeskAutoRelease();

    void setDeskAutoRelease(int deskAutoRelease);

    String getCustomerName();

    void setCustomerName(String customerName);

    double getTotalChildOrderQty();

    void setTotalChildOrderQty(double totalChildOrderQty);

    double getFolInterval();

    void setFolInterval(double folInterval);

    int getFolPriceStrategy();

    void setFolPriceStrategy(int folPriceStrategy);

    String getDeskOrderNo();

    void setDeskOrderNo(String deskOrderNo);

    String getNin();

    void setNin(String nin);

    String getCustomerExternalRef();

    void setCustomerExternalRef(String customerExternalRef);

    String getExecBrokerSId();

    void setExecBrokerSId(String execBrokerSId);

    String getConditionalOrderRef();

    void setConditionalOrderRef(String conditionalOrderRef);

    String getCreatedBy();

    void setCreatedBy(String createdBy);

    int getDeskOrderType();

    void setDeskOrderType(int deskOrderType);

    String getMultiNINOrderRef();

    void setMultiNINOrderRef(String multiNINOrderRef);

    String getMultiNINMasterOrderRef();

    void setMultiNINMasterOrderRef(String multiNINMasterOrderRef);

    String getMultiNINBatchOrderNo();

    void setMultiNINBatchOrderNo(String multiNINBatchOrderNo);

    String getMultiNINMasterOrderNo();

    void setMultiNINMasterOrderNo(String multiNINMasterOrderNo);

    double getWorkedQty();

    void setWorkedQty(double workedQty);

    boolean isTimeTriggerOrder();

    void setTimeTriggerOrder(boolean isTimeTriggerOrder);

    int getMarginEnabledType();

    void setMarginEnabledType(int marginEnabledType);

    String getCallCentreOrderRef();

    void setCallCentreOrderRef(String callCentreOrderRef);

    String getCallCentreAgentName();

    void setCallCentreAgentName(String callCentreAgentName);

    String getOrderAction();

    void setOrderAction(String orderAction);

    Date getApprovedRejectDate();

    void setApprovedRejectDate(Date approvedRejectDate);
}
