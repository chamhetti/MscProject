package lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers.helpers;


import lk.ac.ucsc.oms.messaging_protocol_json.api.ValueConstants;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Header;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.MessageImpl;
import lk.ac.ucsc.oms.trs_connector.api.TrsConnectorFactory;
import lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.request.BuyingPowerTRSRequest;
import lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.request.PortfolioDetailsTRSRequest;
import lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers.populators.InquiryBeansPopulator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class InquiryHandlerHelper {
    private static Logger logger = LogManager.getLogger(InquiryHandlerHelper.class);
    TrsConnectorFactory trsConnectorFactory;


    public Message getBuyingPowerDetails(Header header, Message inquiryReq) {
        try {

            if (trsConnectorFactory == null) {
                trsConnectorFactory = TrsConnectorFactory.getInstance();
            }
            InquiryBeansPopulator beansPopulator = trsConnectorFactory.getInquiryPopulator(header.getProtocolVersion());
            BuyingPowerTRSRequest trsReq = beansPopulator.populateBuyingPowerRequest(header, inquiryReq);

            String accountNumbers = trsReq.getAccountNumbers();
            if (accountNumbers == null || accountNumbers.isEmpty()) {
                logger.error("Error Invalid Request Parameters");
                header.setRespStatus(ValueConstants.RESPONSE_STATUS_FAILURE);
                header.setRespReason("Invalid Request Parameters");
                header.setErrorCode(ValueConstants.ERR_INVALID_PARAMETERS);
                return new MessageImpl();
            }

            logger.info("getAccountSummaryDetails >> Account Number: " + accountNumbers);
            header.setRespStatus(ValueConstants.RESPONSE_STATUS_SUCCESS);
            return null;
        }  catch (Exception e) {
            logger.error("Error While Processing Buying Power Request", e);
            header.setRespStatus(ValueConstants.RESPONSE_STATUS_FAILURE);
            // sending a generic error to avoid exposing system implementation details
            header.setRespReason("Error retrieving data: " + e.getMessage());
            header.setErrorCode(ValueConstants.ERR_SYSTEM_ERROR);
            return new MessageImpl();
        }
    }


    public Message getPortfolioDetails(Header header, Message inquiryReq) {
        try {

            if (trsConnectorFactory == null) {
                trsConnectorFactory = TrsConnectorFactory.getInstance();
            }
            InquiryBeansPopulator beansPopulator = trsConnectorFactory.getInquiryPopulator(header.getProtocolVersion());
            PortfolioDetailsTRSRequest trsReq = beansPopulator.populatePortfolioDetailsRequest(header, inquiryReq);

            String accountNumbers = trsReq.getAccountNumbers();
            String symbol = trsReq.getSymbol();
            if (accountNumbers == null || accountNumbers.isEmpty()) {
                logger.error("Error Invalid Request Parameters");
                header.setRespStatus(ValueConstants.RESPONSE_STATUS_FAILURE);
                header.setRespReason("Invalid Request Parameters");
                header.setErrorCode(ValueConstants.ERR_INVALID_PARAMETERS);
                return new MessageImpl();
            }


            logger.debug("getPortfolioDetails >> Account Numbers: " + accountNumbers);

            header.setRespStatus(ValueConstants.RESPONSE_STATUS_SUCCESS);
            return null;
        }  catch (Exception e) {
            logger.error("Error while processing inquiry request", e);
            header.setRespStatus(ValueConstants.RESPONSE_STATUS_FAILURE);
            //avoid exposing system implementation details
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
