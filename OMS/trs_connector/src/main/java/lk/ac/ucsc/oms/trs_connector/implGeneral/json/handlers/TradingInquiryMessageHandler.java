package lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers;


import lk.ac.ucsc.oms.messaging_protocol_json.api.MessageConstants;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Envelope;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Header;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.EnvelopeImpl;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.HeaderImpl;
import lk.ac.ucsc.oms.trs_connector.api.exceptions.NoSuchProcessException;
import lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers.helpers.TradingInquiryHandlerHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TradingInquiryMessageHandler implements MessageHandler {
    private static Logger logger = LogManager.getLogger(TradingInquiryMessageHandler.class);

    private TradingInquiryHandlerHelper tradingInquiryHandlerHelper;
    /**
     * {@inheritDoc}
     */
    @Override
    public Envelope processMessage(Header header, Message message) throws NoSuchProcessException {
        Message response;
        logger.info("New Trading Inquiry Request : " + header.getMsgType());
        switch (header.getMsgType()) {
            case MessageConstants.REQUEST_TYPE_ORDER_SEARCH:
                response = tradingInquiryHandlerHelper.searchOrders(header, message);
                header.setMsgType(MessageConstants.RESPONSE_TYPE_ORDER_SEARCH);
                break;
            case MessageConstants.REQUEST_TYPE_ORDER_DETAILS:
                response = tradingInquiryHandlerHelper.getOrderDetails(header, message);
                header.setMsgType(MessageConstants.RESPONSE_TYPE_ORDER_DETAILS);
                break;
            case MessageConstants.REQUEST_TYPE_ORDER_LIST:
                response = tradingInquiryHandlerHelper.getOrderList(header, message);
                header.setMsgType(MessageConstants.RESPONSE_TYPE_ORDER_LIST);
                break;
            default:
                throw new NoSuchProcessException("No such request type in trading inquiry group");
        }
        //write the message to trs
        return new EnvelopeImpl((HeaderImpl) header, response);
    }

    /**
     * inject trading inquiry handler helper to the class
     *
     * @param tradingInquiryHandlerHelper
     */
    public void setTradingInquiryHandlerHelper(TradingInquiryHandlerHelper tradingInquiryHandlerHelper) {
        this.tradingInquiryHandlerHelper = tradingInquiryHandlerHelper;
    }
}
