package lk.ac.ucsc.oms.trs_writer.implGeneral.helpers;

import lk.ac.ucsc.oms.common_utility.api.enums.*;
import lk.ac.ucsc.oms.common_utility.api.formatters.DateFormatterUtil;
import lk.ac.ucsc.oms.common_utility.api.formatters.StringUtils;
import lk.ac.ucsc.oms.messaging_protocol_json.api.MessageConstants;
import lk.ac.ucsc.oms.messaging_protocol_json.api.MessageProtocolFacade;
import lk.ac.ucsc.oms.messaging_protocol_json.api.ValueConstants;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Header;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.HeaderImpl;

import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.trading.*;
import org.apache.log4j.Logger;



public class TradeResponseHelper {
    private static Logger logger = Logger.getLogger(TradeResponseHelper.class);
    private MessageProtocolFacade messageProtocolFacade;

    public String populateNormalOrderResponse(lk.ac.ucsc.oms.orderMgt.api.beans.Order order, String customerNumber, String customerName, String execBrokerSid) {
        OrderResponseBean orderResponse = new OrderResponseBean();

        orderResponse.setSecurityAccountNo(order.getSecurityAccount());
        orderResponse.setAveragePrice(order.getAvgPrice());
        orderResponse.setClOrdID(order.getClOrdID());
        orderResponse.setCommission(order.getCommission());
        orderResponse.setCumulativeQty(order.getCumQty());
        orderResponse.setCurrency(order.getCurrency());
        orderResponse.setExecutionID(order.getExecID());
        orderResponse.setLastPrice(order.getLastPx());
        orderResponse.setLastShares((long) order.getLastShares());
        orderResponse.setOrderID(order.getOrdID());
        orderResponse.setOrderQty(order.getQuantity());
        orderResponse.setOrdStatus(order.getStatus().getCode());
        if (order.getType() != null) {
            orderResponse.setOrdType(order.getType().getCode());
        }
        if (order.getOrigClOrdID() != null) {
            orderResponse.setOrigClOrdID(order.getOrigClOrdID());
        }
        if (order.getPriceFactor() > 0) {
            orderResponse.setPrice(order.getPrice() * order.getPriceFactor());
            orderResponse.setLastPrice(order.getLastPx() * order.getPriceFactor());
            orderResponse.setAveragePrice(order.getAvgPrice() * order.getPriceFactor());
        } else {
            orderResponse.setPrice(order.getPrice());
            orderResponse.setLastPrice(order.getLastPx());
            orderResponse.setAveragePrice(order.getAvgPrice());
        }
        if (order.getSide() != null) {
            orderResponse.setSide(order.getSide().getCode());
        }
        orderResponse.setSymbol(order.getSymbol());
        orderResponse.setText(order.getText());
        orderResponse.setOrdRejectReason(order.getText());
        orderResponse.setTimeInForce(order.getTimeInForce());
        orderResponse.setTransactionTime(order.getTransactionTime());
//        orderResponse.setSettlementType(order.getSettlementType().getCode()); todo check this
        orderResponse.setMinimumQty(order.getMinQty());
        orderResponse.setMaxFloor((long) order.getMaxFloor());
        orderResponse.setExpireTime(DateFormatterUtil.formatDateToString(order.getExpireTime(), DateFormatterUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
        orderResponse.setCreatedDate(DateFormatterUtil.formatDateToString(order.getCreateDate(), DateFormatterUtil.DATE_FORMAT_YYYYMMDDHHMMSS));
        orderResponse.setOrdValue(order.getOrderValue());
        orderResponse.setSettleCurrency(order.getSettleCurrency());
        orderResponse.setNetSettle(order.getNetSettle());
        orderResponse.setSecurityExchange(order.getExchange());
        orderResponse.setOrderNetValue(order.getNetValue());
        if (order.getExecBrokerId() != null) {
            try {
                orderResponse.setExecBrokerID(Integer.parseInt(order.getExecBrokerId()));
            } catch (Exception e) {
                logger.error("Error in setting the exec broker id from the order bean");
            }
        }
        orderResponse.setExecBrokerSID(execBrokerSid);
        orderResponse.setMarketCode(order.getMarketCode());
        orderResponse.setCumCommission(order.getCumCommission());
        orderResponse.setCumOrderValue(order.getCumOrderValue());
        orderResponse.setCumOrderNetValue(order.getCumNetValue());
        orderResponse.setOrderCategory(OrderCategory.NORMAL.getCode());
        orderResponse.setUserID(order.getUserId());
        if (order.isDayOrder()) {
            orderResponse.setDayOrder(1);
        } else {
            orderResponse.setDayOrder(0);
        }
        orderResponse.setMarketCode(order.getMarketCode());
        orderResponse.setLastUpdatedTime(DateFormatterUtil.formatDateToString(order.getLastUpdateDate(), DateFormatterUtil.DATE_FORMAT_YYYYMMDDHHMMSS));

        if (order.getMasterOrderId() != null) {
            orderResponse.setAlgoOrderRef(order.getMasterOrderId());
        }
        orderResponse.setMubasherOrderNumber(order.getOrderNo());
        orderResponse.setInstrumentType(order.getInstrumentType());
        orderResponse.setCustomerID(customerNumber);
        orderResponse.setCustomerName(customerName);
        orderResponse.setDealerName(order.getUserName());
        orderResponse.setInstituteCode(order.getInstitutionCode());
        orderResponse.setOrderApprovedBy(order.getApprovedBy());
        orderResponse.setMasterOrderRef(order.getConditionalOrderRef());
        orderResponse.setChannelId(order.getChannel().getCode());

        Header header = new HeaderImpl();
        header.setMsgGroup(MessageConstants.GROUP_TRADING);
        header.setMsgType(MessageConstants.RESPONSE_TYPE_NEW_NORMAL_ORDER);
        header.setClientIp(order.getClientIp());
        header.setChannelId(order.getChannel().getCode());
        if (order.getUniqueTrsId() != null) {
            // this is the 1st order response, hence need to sent to all clients including web clients
            header.setUniqueReqID(order.getUniqueTrsId());
            header.setClientReqID(order.getClientReqId());
            order.setUniqueTrsId(null);
        } else {
            // this is a subsequent response, therefore unsolicited
            header.setMsgSessionId("UNSOLICITED");
//            header.setUniqueReqID("UNS" + order.getClOrdID() + "C" + pushCounter++);
            //set header unique request id taken from the currently executing thread
            header.setUniqueReqID(StringUtils.getUniqueID());
        }
        header.setRespStatus(ValueConstants.RESPONSE_STATUS_SUCCESS);
        return messageProtocolFacade.getJSonString(header, orderResponse);
    }

    public String populateInvalidOrderResponse(String msgSessionID, int channel, int errorCode, String uniqueId) {
        Header header = new HeaderImpl();
        header.setMsgGroup(MessageConstants.GROUP_TRADING);
        header.setMsgType(MessageConstants.RESPONSE_TYPE_NEW_NORMAL_ORDER);
        header.setMsgSessionId(msgSessionID);
        header.setChannelId(channel);
        header.setUniqueReqID(uniqueId);
        header.setRespStatus(ValueConstants.RESPONSE_STATUS_FAILURE);
        header.setErrorCode(errorCode);
        return messageProtocolFacade.getJSonString(header, null);
    }





    public void setMessageProtocolFacade(MessageProtocolFacade messageProtocolFacade) {
        this.messageProtocolFacade = messageProtocolFacade;
    }

}
