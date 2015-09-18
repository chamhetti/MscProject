package lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers.populators.ver1;

import lk.ac.ucsc.oms.common_utility.api.formatters.DateFormatterUtil;

import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;

import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.common.OrderBean;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.inquiry.*;
import lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.common.PaginationTRS;
import lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.reply.ExecutionRecord;

import lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.reply.OrderRecord;
import lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.request.*;


import lk.ac.ucsc.oms.trs_connector.implGeneral.beans.inquiry.common.PaginationTRSBean;

import lk.ac.ucsc.oms.trs_connector.implGeneral.beans.inquiry.reply.OrderInquiryReply;
import lk.ac.ucsc.oms.trs_connector.implGeneral.beans.inquiry.request.*;

import lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers.populators.TradingInquiryBeansPopulator;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class TradingInquiryBeansPopulatorImpl implements TradingInquiryBeansPopulator {
    private static Logger logger = LogManager.getLogger(TradingInquiryBeansPopulatorImpl.class);

    @Override
    public OrderSearchTRSRequest populateOrderSearchRequest(Message jsonReq) {
        OrderSearchTRSRequest trsReq = new OrderSearchTRSRequestBean();
        OrderSearchRequestBean tradingInquiryReq = (OrderSearchRequestBean) jsonReq;

        trsReq.setAccountNumber(tradingInquiryReq.getPortfolioId());
        trsReq.setStartDate(tradingInquiryReq.getStartDate());
        trsReq.setEndDate(tradingInquiryReq.getEndDate());
        trsReq.setOrderSide(tradingInquiryReq.getOrderSide());
        trsReq.setOrdCategory(tradingInquiryReq.getOrderCategory());
        trsReq.setOrderStatus(tradingInquiryReq.getOrderStatus());
        trsReq.setSymbol(tradingInquiryReq.getSymbol());
        trsReq.setClOrderID(tradingInquiryReq.getClOrderID());
        trsReq.setExchange(tradingInquiryReq.getExchange());

        PaginationTRS paginationTRS = new PaginationTRSBean();
        paginationTRS.setStartingSequenceNumber(tradingInquiryReq.getStartingSequenceNumber());
        paginationTRS.setPageWidth(tradingInquiryReq.getPageWidth());

        trsReq.setPaging(paginationTRS);

        return trsReq;
    }


    public PaginationTRS getPaginationTRSBean() {
        return new PaginationTRSBean();
    }


    private int getFilterOnInInteger(String filterOn) {
        if (filterOn == null || ("").equals(filterOn.trim())) {
            return -1;
        }
        int filterOnInt = -1;
        switch (filterOn.charAt(0)) {
            case 'A':
                filterOnInt = 1;
                break;
            case 'B':
                filterOnInt = 2;
                break;
            case 'C':
                filterOnInt = 3;
                break;
            case 'D':
                filterOnInt = 4;
                break;
            case 'N':
                filterOnInt = 5;
                break;
            case 'E':
                filterOnInt = 6;
                break;
            case 'F':
                filterOnInt = 7;
                break;
            case 'G':
                filterOnInt = 8;
                break;
            case 'H':
                filterOnInt = 9;
                break;
            case 'I':
                filterOnInt = 10;
                break;
            case 'J':
                filterOnInt = 11;
                break;
            case 'V':
                filterOnInt = 12;
                break;
            case 'W':
                filterOnInt = 13;
                break;
            case 'X':
                filterOnInt = 14;
                break;
            case 'L':
                filterOnInt = 15;
                break;
            case 'M':
                filterOnInt = 16;
                break;
            case 'Y':
                filterOnInt = 17;
                break;
            default:
                break;
        }
        return filterOnInt;
    }

    @Override
    public Message populateOrderListResponse(OrderInquiryReply trsResp, OrderListTRSRequest trsReq) {
        OrderListResponseBean reply = new OrderListResponseBean();
        OrderBean order;

        List<OrderRecord> orderList = trsResp.getOrderRecords();

        for (OrderRecord orderRecord : orderList) {
            order = populateOrder(orderRecord);
            reply.addOrder(order);
        }
        reply.setOrdCategory(trsReq.getOrderCategory());
        return reply;
    }



    @Override
    public Message populateOrderSearchResponse(OrderInquiryReply trsResp, OrderSearchTRSRequest trsReq) {
        OrderSearchResponseBean reply = new OrderSearchResponseBean();
        if (trsResp == null) {
            return reply;
        }
        List<OrderRecord> orderList = trsResp.getOrderRecords();
        OrderBean order;

        for (OrderRecord orderRecord : orderList) {
            order = populateOrder(orderRecord);
            reply.addOrder(order);
        }
        reply.setOrderCategory(trsReq.getOrdCategory());
        //Setting pagination related properties to response
        reply.setStartingSequenceNumber(trsReq.getPaging().getStartingSequenceNumber());
        reply.setPageWidth(trsReq.getPaging().getPageWidth());
        reply.setNextPageAvailable(trsResp.isNextPageAvailable() ? 1 : 0);
        reply.setTotalNumberOfRecords(trsResp.getTotalNumberOfRecords());

        return reply;
    }



    @Override
    public Message populateOrderDetailsResponse(OrderInquiryReply replyTRS) {
        OrderDetailsResponseBean reply = new OrderDetailsResponseBean();
        OrderBean order;

        List<OrderRecord> orderList = replyTRS.getOrderRecords();

        for (OrderRecord orderRecord : orderList) {
            order = populateOrder(orderRecord);
            reply.addOrder(order);
        }

        return reply;
    }




    @Override
    public OrderDetailTRSRequest populateOrderDetailRequest(Message jsonReq) {
        OrderDetailTRSRequest trsReq = new OrderDetailTRSRequestBean();
        OrderDetailsRequestBean tradingInquiryReq = (OrderDetailsRequestBean) jsonReq;

        trsReq.setClOrdId(tradingInquiryReq.getClOrderID());
        trsReq.setOrderCategory(tradingInquiryReq.getOrdCategory());

        return trsReq;
    }





    @Override
    public OrderListTRSRequest populateOrderListRequest(Message jsonReq) {
        OrderListTRSRequest trsReq = new OrderListTRSRequestBean();
        OrderListRequestBean tradingInquiryReq = (OrderListRequestBean) jsonReq;

        trsReq.setAccountNumber(tradingInquiryReq.getOrdPortfolioID());
        trsReq.setOrderCategory(tradingInquiryReq.getOrdCategory());

        return trsReq;
    }

    private OrderBean populateOrderForExecution(ExecutionRecord executionRecord) {
        OrderBean order = new OrderBean();

        order.setExecutionID(executionRecord.getExecutionId());
        order.setClOrdID(executionRecord.getClOrdID());
        order.setOrderID(executionRecord.getOrdID());
        order.setSymbol(executionRecord.getSymbol());
        order.setSecurityExchange(executionRecord.getExchange());
        order.setOrderQty(executionRecord.getOrderQty());
        order.setInstrumentType(executionRecord.getInstrumentType());
        order.setLastShares(executionRecord.getLastShare());
        order.setLastPrice(executionRecord.getLastPx());
        if (executionRecord.getTransactionTime() != null) {
            Date transactionTime = DateFormatterUtil.formatStringToDate(executionRecord.getTransactionTime().substring(0, 17), DateFormatterUtil.DATE_FORMAT_YYYYMMDDHHMMSS_SEPERATORS);
            String transactionTimeFormatted = DateFormatterUtil.formatDateToString(transactionTime, DateFormatterUtil.DATE_FORMAT_YYYYMMDDHHMMSS);
            order.setTransactionTime(transactionTimeFormatted);
            order.setCreatedDate(transactionTimeFormatted);
            order.setLastUpdatedTime(transactionTimeFormatted);
        }
        try {
            order.setExecTransType(Integer.parseInt(executionRecord.getExecutionType()));
        } catch (NumberFormatException e) {
            logger.error("Error in formatting the execution type - ", e);
        }
        order.setLeavesQty(executionRecord.getLeaveQty());
        order.setCumulativeQty(executionRecord.getCumQty());
        order.setOrdStatus(executionRecord.getStatus());
        order.setAveragePrice(executionRecord.getAvgPrice());
        order.setMubasherOrdNumber(executionRecord.getOrderNo());
        order.setCommission(executionRecord.getCommission());
        order.setDeskOrdReference(executionRecord.getDeskOrdRef());
        order.setOriginalDeskOrdReference(executionRecord.getOrigDeskOrdRef());
        return order;

    }

    private OrderBean populateOrder(OrderRecord orderRecord) {
        OrderBean order = new OrderBean();

        order.setClOrdID(orderRecord.getClOrdID());
        order.setMubasherOrdNumber(orderRecord.getOrderNo());
        order.setCommission(orderRecord.getCommission());
        order.setCumulativeQty(orderRecord.getCumQty());
        order.setCurrency(orderRecord.getCurrency());
        order.setExecutionID(orderRecord.getExecID());
        order.setLastPrice(orderRecord.getLastPx());
        order.setLastShares(orderRecord.getLastShares());
        order.setOrderID(orderRecord.getOrdID());
        order.setOrderQty(orderRecord.getQuantity());
        order.setOrdStatus(orderRecord.getOrderStatus());
        order.setOrderType(orderRecord.getOrderType());
        order.setOrigClOrdID(orderRecord.getOrigClOrdID());
        order.setPrice(orderRecord.getPrice());
        order.setSymbol(orderRecord.getSymbol());
        order.setText(orderRecord.getText());
        order.setTimeInForce(orderRecord.getTimeInForce());
        Date transactionTime = DateFormatterUtil.formatStringToDate(orderRecord.getTransactionTime(), DateFormatterUtil.DATE_FORMAT_YYYYMMDDHHMMSS_SEPERATORS);
        order.setTransactionTime(DateFormatterUtil.formatDateToString(transactionTime, DateFormatterUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
        order.setStopPrice(orderRecord.getStopPx());
        order.setOrdRejectReason(orderRecord.getRejectReason());
        order.setMinimumQty(orderRecord.getMinQty());
        order.setMaxFloor(orderRecord.getMaxFloor());
        order.setExpireTime(DateFormatterUtil.formatDateToString(orderRecord.getExpireTime(), DateFormatterUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
        order.setLeavesQty(orderRecord.getLeavesQty());
        order.setPutOrCall(orderRecord.getPutOrCall());
        order.setSide(orderRecord.getOrderSide());
        order.setStrikePrice(orderRecord.getStrikePrice());
        order.setSecurityExchange(orderRecord.getExchange());
        order.setChannel(orderRecord.getChannel());
        order.setUserId(orderRecord.getUserId());
        order.setInternalOrdStatus(orderRecord.getInternalOrderStatus());
        order.setDealerID(orderRecord.getUserId());
        order.setCreatedDate(DateFormatterUtil.formatDateToString(orderRecord.getCreateDate(), DateFormatterUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
        order.setOrderValue(orderRecord.getOrderValue());
        order.setMaximumPrice(orderRecord.getMaxPrice());
        order.setBrokerID(orderRecord.getExecBrokerId());
        order.setAveragePrice(orderRecord.getAvgPrice());
        order.setRoutingAccount(orderRecord.getRoutingAccount());
        order.setMarketCode(orderRecord.getMarketCode());
        order.setFilledQuantity(orderRecord.getFillQty());
        order.setIssueSettlingRate(orderRecord.getIssueSettleRate());
        order.setClientIP(orderRecord.getClientIp());
        order.setExecutionBrokerID(orderRecord.getExecBrokerId());
        try {
            order.setExecutionBrokerSID(orderRecord.getExecBrokerSId());
        } catch (Exception e) {
            logger.error("Error setting exec broker sid", e);
        }
        order.setSliceOrderExecutionType(orderRecord.getSliceOrderExecType());

        try {
            if (orderRecord.getInstrumentType() != null) {
                order.setSecurityType(Integer.parseInt(orderRecord.getInstrumentType()));
            }
        } catch (NumberFormatException e) {
            logger.error(e.toString(), e);
        }

        try {
            if (orderRecord.getSettlementType() != null) {
                order.setSettlementType(Integer.parseInt(orderRecord.getSettlementType()));
            }
        } catch (NumberFormatException e) {
            logger.error(e.toString(), e);
        }

        order.setInstrumentType(orderRecord.getInstrumentType());
        order.setSettleCurrency(orderRecord.getSettleCurrency());
        order.setInstId(orderRecord.getInstitutionCode());
        order.setCumulativeOrdValue(orderRecord.getCumOrderValue());
        order.setNetOrdValue(orderRecord.getNetValue());
        order.setCumOrdNetValue(orderRecord.getCumNetValue());
        order.setOrderNetValue(orderRecord.getNetValue());
        order.setCumulativeCommission(orderRecord.getCumCommission());
        order.setOrderAvgCost(orderRecord.getAvgPrice());
        order.setLastUpdatedTime(DateFormatterUtil.formatDateToString(orderRecord.getLastUpdateDate(), DateFormatterUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
        order.setStopPxType(orderRecord.getStopPxType());
        order.setOrderCategory(orderRecord.getOrderCategory());
        order.setDeskOrdReference(orderRecord.getDeskOrderRef());
        order.setOriginalDeskOrdReference(orderRecord.getDeskOrderNo());
        order.setMasterOrdReference(orderRecord.getConditionalOrderRef());
        order.setSliceOrderRef(orderRecord.getSliceOrderRef());
        order.setCustomerID(orderRecord.getCustomerNumber());
        order.setCustomerName(orderRecord.getCustomerName());
        order.setOrderApprovedBy(orderRecord.getApprovedBy());
        order.setQuantity(orderRecord.getQuantity());
        order.setRemoteOriginalOrderID(orderRecord.getRemoteOrigOrdId());
        order.setSecurityAccountNo(orderRecord.getSecurityAccount());
        order.setNetSettle(orderRecord.getNetSettle());
        order.setCumulativeOrderNetSettle(orderRecord.getCumNetSettle());
        order.setDayOrder(orderRecord.isDayOrder() ? 1 : 0);
        order.setRemoteOrderID(orderRecord.getRemoteClOrdId());
        order.setRemoteOriginalOrderID(orderRecord.getRemoteOrigOrdId());
        order.setDealerName(orderRecord.getUserName());    // for AT orders, "AT user (backoffice id)" will be sent
        order.setInternalMatchStatus(orderRecord.getInternalMatchStatus());
        order.setSliceOrderInterval(orderRecord.getSliceOrdInterval());
        order.setSliceOrderBlock(orderRecord.getSliceOrderBlock());
        order.setConditionType(orderRecord.getConditionType());
        order.setConditionValue(orderRecord.getConditionValue());
        order.setOperator(orderRecord.getOperator());
        order.setNin(orderRecord.getNin());
        order.setCustomerExternalRef(orderRecord.getCustomerExternalRef());
        order.setMultiNINBatchId(orderRecord.getMultiNINBatchOrderNo());
        order.setMultiNINMasterOrdNo(orderRecord.getMultiNINMasterOrderNo());


        order.setDeskAutoRelease(orderRecord.getDeskAutoRelease());
        order.setDeskTotalChildQty(orderRecord.getTotalChildOrderQty());
        order.setFolOrderRef(orderRecord.getFolOrderRef());
        order.setFolInterval(orderRecord.getFolInterval());
        order.setFolPriceStrategy(orderRecord.getFolPriceStrategy());
        order.setCreatedBy(orderRecord.getCreatedBy());
        order.setDeskOrderType(orderRecord.getDeskOrderType());
        order.setMultiNINMasterRef(orderRecord.getMultiNINMasterOrderRef());
        order.setMultiNINBreakDownRef(orderRecord.getMultiNINOrderRef());
        order.setWorkedQty(orderRecord.getWorkedQty());
        order.setTimeTriggerMode(orderRecord.isTimeTriggerOrder() ? 1 : 0);
        order.setTotalChildOrderQty(orderRecord.getTotalChildOrderQty());
        order.setMarginEnabledType(orderRecord.getMarginEnabledType());
        order.setCallCentreAgentName(orderRecord.getCallCentreAgentName());
        order.setCallCenterOrdRef(orderRecord.getCallCentreOrderRef());
        order.setOrderAction(orderRecord.getOrderAction());
        if (orderRecord.getApprovedRejectDate() != null) {
            order.setApprovedDate(DateFormatterUtil.formatDateToString(orderRecord.getApprovedRejectDate(), DateFormatterUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
        }
        return order;

    }



}
