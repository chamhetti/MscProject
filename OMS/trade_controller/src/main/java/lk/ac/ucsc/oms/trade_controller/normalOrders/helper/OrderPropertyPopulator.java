package lk.ac.ucsc.oms.trade_controller.normalOrders.helper;


import lk.ac.ucsc.oms.common_utility.api.enums.ClientChannel;
import lk.ac.ucsc.oms.common_utility.api.enums.OrderStatus;
import lk.ac.ucsc.oms.orderMgt.api.OrderManagementFactory;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import lk.ac.ucsc.oms.rms_equity.api.EquityRiskManagementReply;
import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.ExpireOrderTRSRequest;
import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.ReverseOrderTRSRequest;
import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.normal.AmendOrderTRSRequest;
import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.normal.CancelOrderTRSRequest;
import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.normal.NewOrderTRSRequest;

import lk.ac.ucsc.oms.trade_controller.helper.ValidationReply;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;


public class OrderPropertyPopulator {
    private static final String DEFAULT_ORDER_IDS = "-1";
    private static Logger logger = LogManager.getLogger(OrderPropertyPopulator.class);
    private static OrderPropertyPopulator instance;

    /**
     * Define private constructor
     */
    private OrderPropertyPopulator() {

    }

    public static synchronized OrderPropertyPopulator getInstance() {
        if (instance == null) {
            instance = new OrderPropertyPopulator();
        }
        return instance;
    }



    /**
     * Method that populate the order object from new order request.
     *
     * @param request            new order request
     * @param orderFacadeFactory is the order facade factory
     */
    public Order fillOrderFromNewOrderRequest(NewOrderTRSRequest request, OrderManagementFactory orderFacadeFactory) {
        //taking a empty order that is used in future processing
        logger.debug("Populating the order from new Order request");
        Order order;
        try {
            order = orderFacadeFactory.getOrderManagementFacadeInterface().getEmptyOrder();
        } catch (Exception e) {
            logger.error("Issue in getting empty order", e);
            return null;
        }

        order.setSecurityAccount(request.getAccountNumber());
        order.setSymbol(request.getSymbolCode());
        order.setExchange(request.getExchangeCode());
        order.setPrice(request.getPrice());
        order.setQuantity(request.getOrderQty());
        order.setSide(request.getSide());
        order.setType(request.getOrderType());
        order.setTimeInForce(request.getTimeInForce());
        order.setChannel(request.getChannel());
        order.setDayOrder(request.isDayOrder());
        order.setMaxFloor(request.getMaxFloor());
        order.setMinQty(request.getMinQty());
        order.setExecBrokerId(request.getExecBrokerId());
        order.setUserId(request.getUserID());
        order.setExpireTime(request.getExpireTime());
        order.setClientIp(request.getClientIp());
        order.setOrdID(DEFAULT_ORDER_IDS);
        order.setOrigClOrdID(DEFAULT_ORDER_IDS);
        order.setFixVersion(request.getFixVersion());
        order.setSecurityIDSource(request.getSecurityIDSource());
        order.setOrderCategory(request.getOrderCategory());
        order.setParentAccountNumber(request.getParentAccountNumber());
        order.setLeavesQty(request.getOrderQty());
        order.setUserId(request.getUserID());
        order.setMasterOrderId(request.getMasterOrderID());
        order.setUniqueTrsId(request.getUniqueTrsId());
        order.setMasterOrdTriggeredTime(request.getMasterOrderTriggerTime());
        order.setStopPx(request.getStopPrice());
        order.setMaxPrice(request.getMaxPrice());
        order.setStopPxType(request.getStopPriceType());
        order.setConditionType(request.getConditionType());
        order.setInstrumentType(request.getInstrumentType());
        order.setRemoteAccountNumber(request.getRemoteAccountNumber());
        order.setBrokerFIXID(request.getBrokerFIXID());
        order.setTargetCompID(request.getTargetCompID());
        order.setSenderSubID(request.getSenderSubID());
        order.setTargetSubID(request.getTargetSubID());
        order.setOnBehalfOfSubID(request.getOnBehalfOfSubID());
       order.setOnBehalfOfCompID(request.getOnBehalfOfCompID());
        order.setOrderOrigin(1); // for front office order origin should be 1
        order.setLanguageCode(request.getLanguageCode());
        order.setClientReqId(request.getClientReqId());

        logger.debug("Populated order from order request >> {}", order);
        return order;
    }

    /**
     * populate the order from Amend order request.
     *
     * @param request            Amend order request
     * @param orderFacadeFactory is the order facade factory
     */
    public Order fillOrderFromAmendRequest(AmendOrderTRSRequest request, OrderManagementFactory orderFacadeFactory) {
        //taking a empty order that is used in future processing
        logger.debug("Populating the order from Amend Order request");
        Order order;
        try {
            order = orderFacadeFactory.getOrderManagementFacadeInterface().getEmptyOrder();
        } catch (Exception e) {
            return null;
        }
        order.setOrigClOrdID(request.getClOrderId());
        order.setPrice(request.getPrice());
        order.setQuantity(request.getOrderQty());
        order.setType(request.getOrderType());
        order.setTimeInForce(request.getTimeInForce());
        order.setChannel(request.getChannel());
        order.setMaxFloor(request.getMaxFloor());
        order.setMinQty(request.getMinQty());
        order.setClientIp(request.getClientIp());
        order.setExpireTime(request.getExpireTime());
        order.setFixVersion(request.getFixVersion());
        order.setSecurityIDSource(request.getSecurityIDSource());
        order.setUserId(request.getUserID());
        order.setSecurityAccount(request.getAccountNumber());
        order.setUniqueTrsId(request.getUniqueTrsId());
        order.setDayOrder(request.isDayOrder());
        order.setUserId(request.getUserID());
        order.setBrokerFIXID(request.getBrokerFIXID());
        order.setTargetCompID(request.getTargetCompID());
        order.setSenderSubID(request.getSenderSubID());
        order.setTargetSubID(request.getTargetSubID());
        order.setOnBehalfOfSubID(request.getOnBehalfOfSubID());
        order.setOnBehalfOfCompID(request.getOnBehalfOfCompID());
              order.setLanguageCode(request.getLanguageCode());

        order.setClientReqId(request.getClientReqId());
        order.setStopPx(request.getStopPrice());
        order.setMaxPrice(request.getMaxPrice());
        order.setStopPxType(request.getStopPriceType());
        order.setConditionType(request.getConditionType());

        logger.debug("Populated order from Amend request >> {}", order);
        return order;
    }

    /**
     * Filled the amend order properties from original order
     *
     * @param amendOrder empty amend order
     * @param oldOrder   original order
     */
    public void fillFieldFromOriginalOrderForAmend(Order amendOrder, Order oldOrder) {
        logger.debug("Populating Amend order from original order");
        amendOrder.setOrigClOrdID(oldOrder.getClOrdID());
        amendOrder.setText(oldOrder.getText());
        amendOrder.setCumCommission(oldOrder.getCumCommission());
        amendOrder.setCumQty(oldOrder.getCumQty());
        amendOrder.setCustomerNumber(oldOrder.getCustomerNumber());
        amendOrder.setExchange(oldOrder.getExchange());
        amendOrder.setExecBrokerId(oldOrder.getExecBrokerId());
        amendOrder.setInstitutionCode(oldOrder.getInstitutionCode());
        amendOrder.setMarketCode(oldOrder.getMarketCode());
        amendOrder.setOrdID(oldOrder.getOrdID());
        amendOrder.setOrderNo(oldOrder.getOrderNo());
        amendOrder.setSecurityAccount(oldOrder.getSecurityAccount());
        amendOrder.setSecurityType(oldOrder.getSecurityType());
        amendOrder.setSide(oldOrder.getSide());
        amendOrder.setStatus(OrderStatus.RECEIVED);
        amendOrder.setSymbol(oldOrder.getSymbol());
        amendOrder.setFixConnectionId(oldOrder.getFixConnectionId());
        amendOrder.setApprovedBy(oldOrder.getApprovedBy());
        amendOrder.setMasterOrderId(oldOrder.getMasterOrderId());
        if (oldOrder.getPriceFactor() > 0) {
            amendOrder.setAvgPrice(oldOrder.getAvgPrice() * oldOrder.getPriceFactor());
        } else {
            amendOrder.setAvgPrice(oldOrder.getAvgPrice());

        }
        amendOrder.setCurrency(oldOrder.getCurrency());
        amendOrder.setFixVersion(oldOrder.getFixVersion());
        amendOrder.setLeavesQty(amendOrder.getQuantity() - oldOrder.getCumQty());
        amendOrder.setMasterOrdTriggeredTime(oldOrder.getMasterOrdTriggeredTime());
        amendOrder.setBuySideBrokerID(oldOrder.getBuySideBrokerID());
        amendOrder.setParentAccountNumber(oldOrder.getParentAccountNumber());
        amendOrder.setSettleCurrency(oldOrder.getSettleCurrency());
        amendOrder.setCumParentCommission(oldOrder.getCumParentCommission());
        amendOrder.setCumNetValue(oldOrder.getCumNetValue());
        amendOrder.setCumNetSettle(oldOrder.getCumNetSettle());
        amendOrder.setCumBrokerCommission(oldOrder.getCumBrokerCommission());
        amendOrder.setInstitutionId(oldOrder.getInstitutionId());
        amendOrder.setRoutingAccount(oldOrder.getRoutingAccount());
        amendOrder.setRoutingAccRef(oldOrder.getRoutingAccRef());
        amendOrder.setInstrumentType(oldOrder.getInstrumentType());
        amendOrder.setCumExchangeCommission(oldOrder.getCumExchangeCommission());
        amendOrder.setCumThirdPartyCommission(oldOrder.getCumThirdPartyCommission());
        amendOrder.setTransactionFee(oldOrder.getTransactionFee());
        if (oldOrder.getPriceFactor() > 0) {
            amendOrder.setLastPx(oldOrder.getLastPx() * oldOrder.getPriceFactor());
        } else {
            amendOrder.setLastPx(oldOrder.getLastPx());

        }
        amendOrder.setLastShares(oldOrder.getLastShares());
        amendOrder.setLeavesQty(oldOrder.getLeavesQty());
        amendOrder.setOrderOrigin(oldOrder.getOrderOrigin());
        amendOrder.setDayOrder(oldOrder.isDayOrder());
        amendOrder.setCumOrderValue(oldOrder.getCumOrderValue());
        amendOrder.setRemoteAccountNumber(oldOrder.getRemoteAccountNumber());
        amendOrder.setConditionalOrderRef(oldOrder.getConditionalOrderRef());
        amendOrder.setPriceFactor(oldOrder.getPriceFactor());
        amendOrder.setLanguageCode(oldOrder.getLanguageCode());
        amendOrder.setOrderCategory(oldOrder.getOrderCategory());
        logger.debug("populated amend order from original order >> {}", amendOrder);
    }

    /**
     * Filled the empty order from cancel order request
     *
     * @param request            cancel order request
     * @param orderFacadeFactory is the order facade factory
     */
    public Order fillOrderFromCancelRequest(CancelOrderTRSRequest request, OrderManagementFactory orderFacadeFactory) {
        //taking a empty order that is used in future processing
        logger.debug("populating cancel order from request");
        Order order;
        try {
            order = orderFacadeFactory.getOrderManagementFacadeInterface().getEmptyOrder();
        } catch (Exception e) {
            return null;
        }
        order.setOrigClOrdID(request.getClOrderId());
        order.setChannel(request.getChannel());
        order.setClientIp(request.getClientIp());
        order.setFixVersion(request.getFixVersion());
        order.setSecurityIDSource(request.getSecurityIDSource());
        order.setUserId(request.getUserID());
        order.setSecurityAccount(request.getAccountNumber());
        order.setUniqueTrsId(request.getUniqueTrsId());
        order.setBrokerFIXID(request.getBrokerFIXID());
        order.setTargetCompID(request.getTargetCompID());
        order.setSenderSubID(request.getSenderSubID());
        order.setTargetSubID(request.getTargetSubID());
        order.setOnBehalfOfSubID(request.getOnBehalfOfSubID());
        order.setOnBehalfOfCompID(request.getOnBehalfOfCompID());
        order.setLanguageCode(request.getLanguageCode());
        order.setClientReqId(request.getClientReqId());
        logger.debug("populated cancel order from request >>{}", order);
        return order;
    }

    /**
     * Filled the cancel order properties from original order
     *
     * @param cancelOrder cancel order
     * @param oldOrder    original order
     */
    public void fillFieldFromOriginalOrderForCancel(Order cancelOrder, Order oldOrder) {
        logger.debug("populate cancel order from original order");
        cancelOrder.setOrigClOrdID(oldOrder.getClOrdID());
        if (oldOrder.getPriceFactor() > 0) {
            cancelOrder.setPrice(oldOrder.getPrice() * oldOrder.getPriceFactor());
        } else {
            cancelOrder.setPrice(oldOrder.getPrice());
        }
        cancelOrder.setQuantity(oldOrder.getQuantity());
        cancelOrder.setType(oldOrder.getType());
        cancelOrder.setTimeInForce(oldOrder.getTimeInForce());
        cancelOrder.setMaxFloor(oldOrder.getMaxFloor());
        cancelOrder.setMinQty(oldOrder.getMinQty());
        cancelOrder.setText(oldOrder.getText());
        cancelOrder.setCumCommission(oldOrder.getCumCommission());
        cancelOrder.setCumQty(oldOrder.getCumQty());
        cancelOrder.setCustomerNumber(oldOrder.getCustomerNumber());
        cancelOrder.setExchange(oldOrder.getExchange());
        cancelOrder.setExecBrokerId(oldOrder.getExecBrokerId());
        cancelOrder.setInstitutionCode(oldOrder.getInstitutionCode());
        cancelOrder.setMarketCode(oldOrder.getMarketCode());
        cancelOrder.setOrdID(oldOrder.getOrdID());
        cancelOrder.setOrderNo(oldOrder.getOrderNo());
        cancelOrder.setSecurityAccount(oldOrder.getSecurityAccount());
        cancelOrder.setSecurityType(oldOrder.getSecurityType());
        cancelOrder.setSide(oldOrder.getSide());
        cancelOrder.setStatus(OrderStatus.RECEIVED);
        cancelOrder.setSymbol(oldOrder.getSymbol());
        cancelOrder.setExpireTime(oldOrder.getExpireTime());
        cancelOrder.setFixConnectionId(oldOrder.getFixConnectionId());
        cancelOrder.setApprovedBy(oldOrder.getApprovedBy());
        cancelOrder.setMasterOrderId(oldOrder.getMasterOrderId());
        if (oldOrder.getPriceFactor() > 0) {
            cancelOrder.setAvgPrice(oldOrder.getAvgPrice() * oldOrder.getPriceFactor());
        } else {
            cancelOrder.setAvgPrice(oldOrder.getAvgPrice());
        }
        cancelOrder.setCurrency(oldOrder.getCurrency());
        cancelOrder.setFixVersion(oldOrder.getFixVersion());
        cancelOrder.setLeavesQty(oldOrder.getLeavesQty());
        if (oldOrder.getPriceFactor() > 0) {
            cancelOrder.setLastPx(oldOrder.getLastPx() * oldOrder.getPriceFactor());
        } else {
            cancelOrder.setLastPx(oldOrder.getLastPx());

        }
        cancelOrder.setLastShares(oldOrder.getLastShares());
        cancelOrder.setMasterOrdTriggeredTime(oldOrder.getMasterOrdTriggeredTime());
        cancelOrder.setBuySideBrokerID(oldOrder.getBuySideBrokerID());
        cancelOrder.setParentAccountNumber(oldOrder.getParentAccountNumber());
        cancelOrder.setSettleCurrency(oldOrder.getSettleCurrency());
        cancelOrder.setCumParentCommission(oldOrder.getCumParentCommission());
        cancelOrder.setCumNetValue(oldOrder.getCumNetValue());
        cancelOrder.setCumNetSettle(oldOrder.getCumNetSettle());
        cancelOrder.setCumBrokerCommission(oldOrder.getCumBrokerCommission());
        cancelOrder.setBlockAmount(oldOrder.getBlockAmount());
        cancelOrder.setParentBlockAmount(oldOrder.getParentBlockAmount());
        cancelOrder.setMarginBlock(oldOrder.getMarginBlock());
        cancelOrder.setRoutingAccount(oldOrder.getRoutingAccount());
        cancelOrder.setRoutingAccRef(oldOrder.getRoutingAccRef());
        cancelOrder.setDayOrder(oldOrder.isDayOrder());
        cancelOrder.setInstrumentType(oldOrder.getInstrumentType());
        cancelOrder.setInstitutionId(oldOrder.getInstitutionId());
        cancelOrder.setCommission(oldOrder.getCommission());
        cancelOrder.setCumOrderValue(oldOrder.getCumOrderValue());
        cancelOrder.setCumExchangeCommission(oldOrder.getCumExchangeCommission());
        cancelOrder.setCumThirdPartyCommission(oldOrder.getCumThirdPartyCommission());
        cancelOrder.setTransactionFee(oldOrder.getTransactionFee());
        cancelOrder.setPriceBlock(oldOrder.getPriceBlock());
        cancelOrder.setOrderValue(oldOrder.getOrderValue());
        cancelOrder.setNetValue(oldOrder.getNetValue());
        cancelOrder.setNetSettle(oldOrder.getNetSettle());
        cancelOrder.setIssueSettleRate(oldOrder.getIssueSettleRate());
        cancelOrder.setMarginDue(oldOrder.getMarginDue());
        cancelOrder.setDayMarginDue(oldOrder.getDayMarginDue());
        cancelOrder.setOrderOrigin(oldOrder.getOrderOrigin());
        cancelOrder.setRemoteAccountNumber(oldOrder.getRemoteAccountNumber());
        cancelOrder.setUserName(oldOrder.getUserName());
        cancelOrder.setConditionalOrderRef(oldOrder.getConditionalOrderRef());
        cancelOrder.setPriceFactor(oldOrder.getPriceFactor());
        cancelOrder.setLanguageCode(oldOrder.getLanguageCode());
        oldOrder.setUniqueTrsId(cancelOrder.getUniqueTrsId());
        cancelOrder.setOrderCategory(oldOrder.getOrderCategory());
        if (cancelOrder.getChannel() == ClientChannel.NON_EXISTING) {
            cancelOrder.setChannel(oldOrder.getChannel());
        }
        logger.debug("populated cancel order from original order >>{}", cancelOrder);
    }


    /**
     * populate the basic validation reject properties to the order
     *
     * @param order           is the order bean
     * @param validationReply is the order validation reply
     */
    public void populateRejectOrderProperties(Order order, EquityRiskManagementReply validationReply) {
        order.setModuleCode(validationReply.getModuleCode());
        order.setErrMsgParameters(validationReply.getErrMsgParameterList());
        order.setText(validationReply.getRejectReason());
        order.setInternalRejReason(validationReply.getRejectReason());
    }

    /**
     * populate the basic validation reject properties to the order
     *
     * @param order           is the order bean
     * @param validationReply is the order validation reply
     */
    public void populateRejectOrderProperties(Order order, ValidationReply validationReply) {
        order.setModuleCode(validationReply.getModuleCode());
        order.setErrMsgParameters(validationReply.getErrorMsgParameters());
        order.setText(validationReply.getRejectReasonText());
        order.setInternalRejReason(validationReply.getRejectReasonText());
    }

    /**
     * Filled the empty slice order from slice order request
     *
     * @param reverseOrderRequest reverse order request
     * @param order               is the order bean
     */
    public void fillOrderFromReverseRequest(ReverseOrderTRSRequest reverseOrderRequest, Order order) {
        order.setChannel(reverseOrderRequest.getChannel());
        order.setUserId(reverseOrderRequest.getUserID());
        order.setClientIp(reverseOrderRequest.getClientIp());
        order.setNarration(reverseOrderRequest.getNarration());
    }

    /**
     * Filled the required filed from expire order request
     *
     * @param request expire order request
     * @param order   empty cancel order
     */
    public void fillOrderFromExpireRequest(ExpireOrderTRSRequest request, Order order) {
        order.setChannel(request.getChannel());
        order.setUserId(request.getUserID());
        order.setClientIp(request.getClientIp());
    }



}
