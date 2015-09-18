package lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers.helpers;



import lk.ac.ucsc.oms.messaging_protocol_json.api.ValueConstants;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Header;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.MessageImpl;
import lk.ac.ucsc.oms.trs_connector.api.TrsConnectorFactory;
import lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.request.*;

import lk.ac.ucsc.oms.trs_connector.implGeneral.beans.inquiry.reply.OrderInquiryReply;
import lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers.populators.TradingInquiryBeansPopulator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TradingInquiryHandlerHelper {
    private static Logger logger = LogManager.getLogger(TradingInquiryHandlerHelper.class);
    TrsConnectorFactory trsConnectorFactory;

    public Message getOrderList(Header header, Message tradingInquiryReq) {
        try {
            if (trsConnectorFactory == null) {
                trsConnectorFactory = TrsConnectorFactory.getInstance();
            }
            TradingInquiryBeansPopulator beansPopulator = trsConnectorFactory.getTradingInquiryPopulator(header.getProtocolVersion());
            OrderListTRSRequest trsReq = beansPopulator.populateOrderListRequest(tradingInquiryReq);

            String accountNumber = trsReq.getAccountNumber();
            int ordCategory = trsReq.getOrderCategory();
            OrderInquiryReply trsResp;

            if (accountNumber == null || accountNumber.isEmpty() || ordCategory <= 0) {
                logger.error("Error Invalid Account Number :{}", accountNumber);
                header.setRespStatus(ValueConstants.RESPONSE_STATUS_FAILURE);
                header.setRespReason("Invalid Request Parameters");
                header.setErrorCode(ValueConstants.ERR_INVALID_PARAMETERS);
                return new MessageImpl();
            }
            trsResp = trsConnectorFactory.getInquiryControllerInterface().getOrderList(trsReq.getAccountNumber());

            Message reply = beansPopulator.populateOrderListResponse(trsResp, trsReq);
            header.setRespStatus(ValueConstants.RESPONSE_STATUS_SUCCESS);
            return reply;
        } catch (Exception e) {
            logger.error("Error while processing trading inquiry request", e);
            header.setRespStatus(ValueConstants.RESPONSE_STATUS_FAILURE);
            header.setRespReason("System Error: " + e.getMessage());
            header.setErrorCode(ValueConstants.ERR_SYSTEM_ERROR);
            return new MessageImpl();
        }
    }

    public Message searchOrders(Header header, Message tradingInquiryReq) {
        try {
            if (trsConnectorFactory == null) {
                trsConnectorFactory = TrsConnectorFactory.getInstance();
            }
            TradingInquiryBeansPopulator beansPopulator = trsConnectorFactory.getTradingInquiryPopulator(header.getProtocolVersion());
            OrderSearchTRSRequest trsReq = beansPopulator.populateOrderSearchRequest(tradingInquiryReq);
            OrderInquiryReply trsResp;
            if (trsReq.getAccountNumber() == null || trsReq.getAccountNumber().isEmpty() || trsReq.getOrdCategory() <= 0) {
                logger.error("Error Invalid Request Parameters");
                header.setRespStatus(ValueConstants.RESPONSE_STATUS_FAILURE);
                header.setRespReason("Invalid Request Parameters");
                header.setErrorCode(ValueConstants.ERR_INVALID_PARAMETERS);
                return new MessageImpl();
            }

            trsResp = trsConnectorFactory.getInquiryControllerInterface().searchOrdersWithPaging(trsReq);

            Message reply = beansPopulator.populateOrderSearchResponse(trsResp, trsReq);
            header.setRespStatus(ValueConstants.RESPONSE_STATUS_SUCCESS);
            return reply;
        } catch (Exception e) {
            logger.error("Error while processing trading inquiry request", e);
            header.setRespStatus(ValueConstants.RESPONSE_STATUS_FAILURE);
            header.setRespReason("System Error: " + e.getMessage());
            header.setErrorCode(ValueConstants.ERR_SYSTEM_ERROR);
            return new MessageImpl();
        }
    }


    public Message getOrderDetails(Header header, Message tradingInquiryReq) {
        try {
            if (trsConnectorFactory == null) {
                trsConnectorFactory = TrsConnectorFactory.getInstance();
            }
            TradingInquiryBeansPopulator beansPopulator = trsConnectorFactory.getTradingInquiryPopulator(header.getProtocolVersion());
            OrderDetailTRSRequest trsReq = beansPopulator.populateOrderDetailRequest(tradingInquiryReq);

            String clOrderID = trsReq.getClOrdId();
            int ordCategory = trsReq.getOrderCategory();
            OrderInquiryReply trsResp;

            if (clOrderID == null || clOrderID.isEmpty() || ordCategory <= 0) {
                logger.error("Error Invalid Request Parameters");
                header.setRespStatus(ValueConstants.RESPONSE_STATUS_FAILURE);
                header.setRespReason("Invalid Request Parameters");
                header.setErrorCode(ValueConstants.ERR_INVALID_PARAMETERS);
                return new MessageImpl();
            }
            trsResp = trsConnectorFactory.getInquiryControllerInterface().getOrderDetails(trsReq.getClOrdId());
            Message reply = beansPopulator.populateOrderDetailsResponse(trsResp);
            header.setRespStatus(ValueConstants.RESPONSE_STATUS_SUCCESS);
            return reply;
        } catch (Exception e) {
            logger.error("Error while processing trading inquiry request", e);
            header.setRespStatus(ValueConstants.RESPONSE_STATUS_FAILURE);
            header.setRespReason("System Error: " + e.getMessage());
            header.setErrorCode(ValueConstants.ERR_SYSTEM_ERROR);
            return new MessageImpl();
        }
    }

    /*
    *usage is only for unit test
    */
    public void setTrsConnectorFactory(TrsConnectorFactory trsConnectorFactory) {
        this.trsConnectorFactory = trsConnectorFactory;
    }
}
