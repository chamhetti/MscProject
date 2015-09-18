package lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers.helpers;

import lk.ac.ucsc.oms.common_utility.api.enums.ClientChannel;
import lk.ac.ucsc.oms.messaging_protocol_json.api.ValueConstants;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Header;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.MessageImpl;
import lk.ac.ucsc.oms.trs_connector.api.TrsConnectorFactory;
import lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.reply.CustomerRecordTrs;
import lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.request.CustomerAccountDetailTRSRequest;
import lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.request.CustomerDetailTRSRequest;
import lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.request.CustomerSearchTRSRequest;
import lk.ac.ucsc.oms.trs_connector.implGeneral.beans.inquiry.reply.CustomerListByUserReply;
import lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers.populators.CustomerInquiryBeansPopulator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CustomerInquiryHandlerHelper {
    private static Logger logger = LogManager.getLogger(CustomerInquiryHandlerHelper.class);
    TrsConnectorFactory trsConnectorFactory;


    public Message getCustomerDetails(Header header, Message reqObj) {

        try {
            if (trsConnectorFactory == null) {
                trsConnectorFactory = TrsConnectorFactory.getInstance();
            }
            CustomerInquiryBeansPopulator beansPopulator = trsConnectorFactory.getCustomerInquiryPopulator(header.getProtocolVersion());

            CustomerDetailTRSRequest trsReq = beansPopulator.populateCustomerDetailTRSRequest(reqObj, header);
            String customerID = trsReq.getCustomerId();
            ClientChannel channel = trsReq.getChannel();

            if (customerID == null || customerID.isEmpty()) {
                logger.error("Error Invalid Parameters for customer details request, customer id cannot be null");
                header.setRespStatus(ValueConstants.RESPONSE_STATUS_FAILURE);
                header.setRespReason("Invalid Parameters");
                header.setErrorCode(ValueConstants.ERR_INVALID_PARAMETERS);
                return new MessageImpl();

            } else if (channel != ClientChannel.DT && !header.getLoggedInUserId().equals(trsReq.getCustomerId())) {
                logger.error("Error Invalid Parameters for customer details request, customer id must be equal to logged-in user id for non-dealers");
                header.setRespStatus(ValueConstants.RESPONSE_STATUS_FAILURE);
                header.setRespReason("Security violation: customer id must be equal to logged-in user id");
                header.setErrorCode(ValueConstants.ERR_SECURITY_VIOLATION);
                return new MessageImpl();
            }

            logger.info("getCustomerDetails >> Customer Account number: " + customerID);
            CustomerRecordTrs replyTRS = trsConnectorFactory.getInquiryControllerInterface().getCustomerDetails(trsReq.getCustomerId(), trsReq.getChannel(), trsReq.getDealerID(), trsReq.isGlobalDealer());
            Message customerDetailResponse = beansPopulator.populateCustomerDetailResponse(replyTRS);
            header.setRespStatus(ValueConstants.RESPONSE_STATUS_SUCCESS);
            return customerDetailResponse;
        } catch (Exception e) {
            logger.error("Error while processing customer inquiry request", e);
            header.setRespStatus(ValueConstants.RESPONSE_STATUS_FAILURE);
            // sending a generic error to avoid exposing system implementation details
            header.setRespReason("Error retrieving data");
            header.setErrorCode(ValueConstants.ERR_SYSTEM_ERROR);
            return new MessageImpl();
        }
    }

    /**
     * Getting all the customer account related information
     *
     * @param reqObj is the CustomerAccountDetailRequest
     * @return the response object CustomerAccountDetailResponse
     */
    public Message getCustomerAccountDetails(Header header, Message reqObj) {
        try {
            if (trsConnectorFactory == null) {
                trsConnectorFactory = TrsConnectorFactory.getInstance();
            }
            CustomerInquiryBeansPopulator beansPopulator = trsConnectorFactory.getCustomerInquiryPopulator(header.getProtocolVersion());

            CustomerAccountDetailTRSRequest trsReq = beansPopulator.populateCustomerAccountDetailTRSRequest(reqObj);

            String dealerId = trsReq.getDealerID();
            String customerId = trsReq.getCustomerId();

            if (customerId == null || customerId.isEmpty()) {
                logger.error("Error Invalid Parameters");
                header.setRespStatus(ValueConstants.RESPONSE_STATUS_FAILURE);
                header.setRespReason("Invalid Parameters");
                header.setErrorCode(ValueConstants.ERR_INVALID_PARAMETERS);
                return new MessageImpl();
            }
            if (dealerId == null || dealerId.isEmpty()) {
                logger.error("Error Invalid Parameters");
                header.setRespStatus(ValueConstants.RESPONSE_STATUS_FAILURE);
                header.setRespReason("Invalid Parameters");
                header.setErrorCode(ValueConstants.ERR_INVALID_PARAMETERS);
                return new MessageImpl();
            }

            ClientChannel channel = ClientChannel.getEnum(header.getChannelId());

            if (channel != ClientChannel.DT && !header.getLoggedInUserId().equals(customerId)) {
                logger.error("Error Invalid Parameters for customer details request, customer id must be equal to logged-in user id for non-dealers");
                header.setRespStatus(ValueConstants.RESPONSE_STATUS_FAILURE);
                header.setRespReason("Security violation: customer id must be equal to logged-in user id");
                header.setErrorCode(ValueConstants.ERR_SECURITY_VIOLATION);
                return new MessageImpl();
            }

            logger.info("getCustomerAccountDetails >> Customer Account number: " + customerId + " dealerId: " + dealerId);
         // CustomerAccountDtlReply replyTRS = trsConnectorFactory.getInquiryControllerInterface().getCustomerAccountDetails(trsReq.getDealerID(), trsReq.getCustomerId(), trsReq.isGlobalDealer());
          //  Message reply = beansPopulator.populateCustomerAccountDetailResponse(replyTRS, trsReq);
            header.setRespStatus(ValueConstants.RESPONSE_STATUS_SUCCESS);
            return null;
        } catch (Exception e) {
            logger.error("Error while processing customer inquiry request", e);
            header.setRespStatus(ValueConstants.RESPONSE_STATUS_FAILURE);
            // sending a generic error to avoid exposing system implementation details
            header.setRespReason("Error retrieving data");
            header.setErrorCode(ValueConstants.ERR_SYSTEM_ERROR);
            return new MessageImpl();
        }
    }

    public Message getCustomerListByUser(Header header, Message reqObj) {
        try {
            if (trsConnectorFactory == null) {
                trsConnectorFactory = TrsConnectorFactory.getInstance();
            }
            CustomerInquiryBeansPopulator beansPopulator = trsConnectorFactory.getCustomerInquiryPopulator(header.getProtocolVersion());
            logger.info("getCustomerListByUser >> UserId: " + header.getLoggedInUserId());
            long userId = Long.parseLong(header.getLoggedInUserId());
            CustomerListByUserReply replyTRS = trsConnectorFactory.getInquiryControllerInterface().getCustomerListByUser(userId);
            Message reply = beansPopulator.populateCustomerListByUserResponse(replyTRS);
            header.setRespStatus(ValueConstants.RESPONSE_STATUS_SUCCESS);
            return reply;
        } catch (Exception e) {
            logger.error("Error while processing customer inquiry request", e);
            header.setRespStatus(ValueConstants.RESPONSE_STATUS_FAILURE);
            // sending a generic error to avoid exposing system implementation details
            header.setRespReason("Error retrieving data");
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
