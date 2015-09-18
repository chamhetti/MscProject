package lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers;

import lk.ac.ucsc.oms.messaging_protocol_json.api.MessageConstants;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Envelope;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Header;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.EnvelopeImpl;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.HeaderImpl;
import lk.ac.ucsc.oms.trs_connector.api.exceptions.NoSuchProcessException;
import lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers.helpers.CustomerInquiryHandlerHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CustomerInquiryMessageHandler implements MessageHandler {
    private static Logger logger = LogManager.getLogger(CustomerInquiryMessageHandler.class);

    private CustomerInquiryHandlerHelper customerInquiryHandlerHelper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Envelope processMessage(Header header, Message message) throws NoSuchProcessException {
        Message response;
        logger.info("New Customer Inquiry Request : " + header.getMsgType());

        switch (header.getMsgType()) {
            case MessageConstants.REQUEST_TYPE_CUSTOMER_DETAIL:
                response = customerInquiryHandlerHelper.getCustomerDetails(header, message);
                header.setMsgType(MessageConstants.RESPONSE_TYPE_CUSTOMER_DETAIL);
                break;
            case MessageConstants.REQUEST_TYPE_CUSTOMER_ACCOUNT_DETAIL:
                response = customerInquiryHandlerHelper.getCustomerAccountDetails(header, message);
                header.setMsgType(MessageConstants.RESPONSE_TYPE_CUSTOMER_ACCOUNT_DETAIL);
                break;
            case MessageConstants.REQUEST_TYPE_CUSTOMER_LIST_BY_USER:
                response = customerInquiryHandlerHelper.getCustomerListByUser(header, message);
                header.setMsgType(MessageConstants.RESPONSE_TYPE_CUSTOMER_LIST_BY_USER);
                break;
            default:
                throw new NoSuchProcessException("No such request type in customer inquiry group");
        }
        //write the message to trs
        return new EnvelopeImpl((HeaderImpl) header, response);
    }

    /**
     * method to set customer inquiry handler helper to the class
     *
     * @param customerInquiryHandlerHelper
     */
    public void setCustomerInquiryHandlerHelper(CustomerInquiryHandlerHelper customerInquiryHandlerHelper) {
        this.customerInquiryHandlerHelper = customerInquiryHandlerHelper;
    }
}
