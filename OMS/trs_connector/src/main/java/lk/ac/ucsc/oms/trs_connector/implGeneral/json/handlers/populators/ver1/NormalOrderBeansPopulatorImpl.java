package lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers.populators.ver1;

import lk.ac.ucsc.oms.common_utility.api.enums.*;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Header;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.trading.*;
import lk.ac.ucsc.oms.orderMgt.api.IOMStatus;
import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.*;
import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.normal.AmendOrderTRSRequest;
import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.normal.CancelOrderTRSRequest;
import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.normal.NewOrderTRSRequest;
import lk.ac.ucsc.oms.trs_connector.implGeneral.beans.trading.request.*;

import lk.ac.ucsc.oms.trs_connector.implGeneral.beans.trading.request.normal.AmendOrderTRSRequestBean;
import lk.ac.ucsc.oms.trs_connector.implGeneral.beans.trading.request.normal.CancelOrderTRSRequestBean;
import lk.ac.ucsc.oms.trs_connector.implGeneral.beans.trading.request.normal.NewOrderTRSRequestBean;
import lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers.populators.NormalOrderBeansPopulator;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;


public class NormalOrderBeansPopulatorImpl implements NormalOrderBeansPopulator {
    private static Logger logger = LogManager.getLogger(NormalOrderBeansPopulatorImpl.class);
    private SimpleDateFormat tradingDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final String FORMATTING_ERROR = "Date formatting Error: ";

    /**
     * Populate the new order bean
     *
     * @param header json message header
     * @param req    is the json order request from the client
     * @return NewOrderTRSRequest
     */
    @Override
    public NewOrderTRSRequest populateNewOrderRequest(Header header, Message req) {
        OrderRequestBean newOrderFromClient = (OrderRequestBean) req;
        NewOrderTRSRequest newOrderTRSRequest = new NewOrderTRSRequestBean();

        newOrderTRSRequest.setOrderQty(newOrderFromClient.getOrderQty());
        newOrderTRSRequest.setOrderType(OrderType.getEnum(String.valueOf(newOrderFromClient.getOrdType())));
        newOrderTRSRequest.setPrice(newOrderFromClient.getPrice());
        newOrderTRSRequest.setSide(OrderSide.getEnum(String.valueOf(newOrderFromClient.getSide())));
        newOrderTRSRequest.setSymbolCode(newOrderFromClient.getSymbol());
        newOrderTRSRequest.setTimeInForce(newOrderFromClient.getTimeInForce());
        newOrderTRSRequest.setMinQty(newOrderFromClient.getMinimumQty());
        newOrderTRSRequest.setMaxFloor(newOrderFromClient.getMaxFloor());
        if (newOrderFromClient.getExpireTime() != null && !newOrderFromClient.getExpireTime().isEmpty()) {
            try {
                newOrderTRSRequest.setExpireTime(tradingDateFormat.parse(newOrderFromClient.getExpireTime()));
            } catch (Exception pe) {
                logger.error(FORMATTING_ERROR + ":" + pe.getMessage());
            }
        } else {
            newOrderTRSRequest.setExpireTime(new Date(System.currentTimeMillis()));
        }

        newOrderTRSRequest.setExchangeCode(newOrderFromClient.getSecurityExchange());
        newOrderFromClient.setSignature(newOrderFromClient.getSignature());
        newOrderTRSRequest.setAccountNumber(newOrderFromClient.getSecurityAccountNumber());
        newOrderTRSRequest.setChannel(ClientChannel.getEnum(header.getChannelId()));
        newOrderTRSRequest.setUserID(header.getLoggedInUserId());
        newOrderTRSRequest.setDayOrder(newOrderFromClient.getDayOrder() == 1);
        newOrderTRSRequest.setBookKeeperID(newOrderFromClient.getBookKeeperID());
        newOrderTRSRequest.setClientIp(header.getClientIp());
        newOrderTRSRequest.setMsgSessionID(header.getMsgSessionId());
        newOrderTRSRequest.setSecurityType(String.valueOf(newOrderFromClient.getSecurityType()));
        newOrderTRSRequest.setUniqueTrsId(header.getUniqueReqID());
        newOrderTRSRequest.setStopPrice(newOrderFromClient.getStopPrice());
        newOrderTRSRequest.setMaxPrice(newOrderFromClient.getMaxPrice());
        newOrderTRSRequest.setStopPriceType(newOrderFromClient.getStopPriceType());
        newOrderTRSRequest.setConditionType(newOrderFromClient.getConditionType());
        newOrderTRSRequest.setInstrumentType(newOrderFromClient.getInstrumentType());
        int iomSts = newOrderFromClient.getInternalMatchStatus();
        newOrderTRSRequest.setInternalMatchStatus(IOMStatus.getEnum(iomSts));
        newOrderTRSRequest.setDeskOrderRef(newOrderFromClient.getDeskOrdReference());
        newOrderTRSRequest.setLanguageCode(header.getMsgLangID());
        newOrderTRSRequest.setClientReqId(header.getClientReqID());
        newOrderTRSRequest.setMultiNINOrderBatchId(newOrderFromClient.getMultiNINBatchNO());
        newOrderTRSRequest.setMultiNINOrderRef(newOrderFromClient.getMultiNINBreakDownRef());
        newOrderTRSRequest.setTimeTriggerOrder(newOrderFromClient.getTimeTriggerMode() == 1);
        newOrderTRSRequest.setCallCenterOrdRef(newOrderFromClient.getCallCenterOrdRef());
        return newOrderTRSRequest;
    }


    /**
     * Populate the Amend order request
     *
     * @param header json message header
     * @return AmendOrderTRSRequest
     */
    @Override
    public AmendOrderTRSRequest populateAmendOrderRequest(Header header, Message req) {
        OrderAmendRequestBean amendOrderFromClient = (OrderAmendRequestBean) req;
        AmendOrderTRSRequest amendOrderTRSRequest = new AmendOrderTRSRequestBean();
        amendOrderTRSRequest.setOrderQty(amendOrderFromClient.getOrderQty());
        amendOrderTRSRequest.setOrderType(OrderType.getEnum(String.valueOf(amendOrderFromClient.getOrdType())));
        amendOrderTRSRequest.setClOrderId(amendOrderFromClient.getOrigClOrdID());
        amendOrderTRSRequest.setPrice(amendOrderFromClient.getPrice());
        amendOrderTRSRequest.setTimeInForce(amendOrderFromClient.getTimeInForce());
        amendOrderTRSRequest.setMinQty(amendOrderFromClient.getMinimumQty());
        amendOrderTRSRequest.setMaxFloor(amendOrderFromClient.getMaxFloor());
        try {
            if (amendOrderFromClient.getExpireTime() != null && !amendOrderFromClient.getExpireTime().isEmpty()) {
                amendOrderTRSRequest.setExpireTime(tradingDateFormat.parse(amendOrderFromClient.getExpireTime()));
            } else {
                amendOrderTRSRequest.setExpireTime(new Date(System.currentTimeMillis()));
            }
        } catch (Exception pe) {
            logger.warn(FORMATTING_ERROR + pe);
            amendOrderTRSRequest.setExpireTime(new Date(System.currentTimeMillis()));
        }
        amendOrderTRSRequest.setSecurityType(amendOrderFromClient.getSecurityType());
        amendOrderTRSRequest.setChannel(ClientChannel.getEnum(header.getChannelId()));
        amendOrderTRSRequest.setClientIp(header.getClientIp());
        amendOrderTRSRequest.setUserID(header.getLoggedInUserId());
        amendOrderTRSRequest.setMsgSessionID(header.getMsgSessionId());
        amendOrderTRSRequest.setUniqueTrsId(header.getUniqueReqID());
        amendOrderTRSRequest.setAccountNumber(amendOrderFromClient.getSecurityAccNo());
        amendOrderTRSRequest.setDayOrder(amendOrderFromClient.getDayOrder() == 1);
        amendOrderTRSRequest.setLanguageCode(header.getMsgLangID());
        amendOrderTRSRequest.setMultiNINOrderBatchId(amendOrderFromClient.getMultiNINBatchNO());
        amendOrderTRSRequest.setMultiNINOrderRef(amendOrderFromClient.getMultiNINBreakDownRef());
        amendOrderTRSRequest.setClientReqId(header.getClientReqID());
        amendOrderTRSRequest.setCallCenterOrdRef(amendOrderFromClient.getCallCenterOrdRef());
        amendOrderTRSRequest.setConditionType(amendOrderFromClient.getConditionType());
        amendOrderTRSRequest.setStopPriceType(amendOrderFromClient.getStopPriceType());
        amendOrderTRSRequest.setStopPrice(amendOrderFromClient.getStopPrice());
        amendOrderTRSRequest.setMaxPrice(amendOrderFromClient.getMaximumPrice());
        return amendOrderTRSRequest;
    }


    /**
     * Populate the cancel order request
     *
     * @param header json message header
     * @return CancelOrderTRSRequest
     */
    @Override
    public CancelOrderTRSRequest populateCancelOrderRequest(Header header, Message req) {
        OrderCancelRequestBean cancelOrderFromClient = (OrderCancelRequestBean) req;
        CancelOrderTRSRequest cancelOrderTRSRequest = new CancelOrderTRSRequestBean();
        cancelOrderTRSRequest.setChannel(ClientChannel.getEnum(header.getChannelId()));
        cancelOrderTRSRequest.setClientIp(header.getClientIp());
        cancelOrderTRSRequest.setClOrderId(cancelOrderFromClient.getOrigClOrdID());
        cancelOrderTRSRequest.setMsgSessionID(header.getMsgSessionId());
        cancelOrderTRSRequest.setUserID(header.getLoggedInUserId());
        cancelOrderTRSRequest.setAccountNumber(cancelOrderFromClient.getSecurityAccNo());
        cancelOrderTRSRequest.setUniqueTrsId(header.getUniqueReqID());
        cancelOrderTRSRequest.setMultiNINOrderBatchId(cancelOrderFromClient.getMultiNINBatchNO());
        cancelOrderTRSRequest.setMultiNINOrderRef(cancelOrderFromClient.getMultiNINBreakDownRef());
        int iomSts = cancelOrderFromClient.getInternalMatchStatus();
        cancelOrderTRSRequest.setInternalMatchStatus(IOMStatus.getEnum(iomSts));
        cancelOrderTRSRequest.setLanguageCode(header.getMsgLangID());
        cancelOrderTRSRequest.setClientReqId(header.getClientReqID());
        cancelOrderTRSRequest.setCallCenterOrdRef(cancelOrderFromClient.getCallCenterOrdRef());
        return cancelOrderTRSRequest;
    }


    /**
     * Populate the expire order request bean
     *
     * @param header json message header
     * @param req    is the expire order mix request received from the client
     * @return ExpireOrderTRSRequest
     */
    @Override
    public ExpireOrderTRSRequest populateExpireOrderRequest(Header header, Message req) {
        ExpireOrderRequestBean expireOrderFromClient = (ExpireOrderRequestBean) req;
        ExpireOrderTRSRequest expireOrderTRSRequest = new ExpireOrderTRSRequestBean();
        expireOrderTRSRequest.setChannel(ClientChannel.getEnum(header.getChannelId()));
        expireOrderTRSRequest.setClientIp(header.getClientIp());
        expireOrderTRSRequest.setClOrderId(expireOrderFromClient.getOrigClOrderId());
        expireOrderTRSRequest.setUserID(header.getLoggedInUserId());
        expireOrderTRSRequest.setUserName(header.getLoggedInUserId());

        return expireOrderTRSRequest;
    }



}
