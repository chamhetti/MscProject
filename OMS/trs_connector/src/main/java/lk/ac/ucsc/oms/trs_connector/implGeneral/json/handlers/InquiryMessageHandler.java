package lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers;

import lk.ac.ucsc.oms.messaging_protocol_json.api.MessageConstants;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Envelope;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Header;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.EnvelopeImpl;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.HeaderImpl;
import lk.ac.ucsc.oms.trs_connector.api.exceptions.NoSuchProcessException;
import lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers.helpers.InquiryHandlerHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class InquiryMessageHandler implements MessageHandler {
    private static Logger logger = LogManager.getLogger(InquiryMessageHandler.class);

    private InquiryHandlerHelper inquiryHandlerHelper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Envelope processMessage(Header header, Message message) throws NoSuchProcessException {
        Message response;
        logger.info("New Inquiry Request : " + header.getMsgType());

        switch (header.getMsgType()) {

            case MessageConstants.REQUEST_TYPE_BUYING_POWER:
                response = inquiryHandlerHelper.getBuyingPowerDetails(header, message);
                header.setMsgType(MessageConstants.RESPONSE_TYPE_BUYING_POWER);
                break;

            case MessageConstants.REQUEST_TYPE_PORTFOLIO:
                response = inquiryHandlerHelper.getPortfolioDetails(header, message);
                header.setMsgType(MessageConstants.RESPONSE_TYPE_PORTFOLIO);
                break;

            default:
                throw new NoSuchProcessException("No such request type in inquiry group");
        }

        logger.info("Finished Processing Inquiry Request : " + header.getMsgType());
        //write the message to trs
        return new EnvelopeImpl((HeaderImpl) header, response);
    }

    /**
     * method to set inquiry handler helper to the class
     *
     * @param inquiryHandlerHelper
     */
    public void setInquiryHandlerHelper(InquiryHandlerHelper inquiryHandlerHelper) {
        this.inquiryHandlerHelper = inquiryHandlerHelper;
    }

}
