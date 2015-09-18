package lk.ac.ucsc.oms.trs_connector.implGeneral.json;

import lk.ac.ucsc.oms.messaging_protocol_json.api.*;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Envelope;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Header;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.EnvelopeImpl;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.HeaderImpl;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.MessageImpl;
import lk.ac.ucsc.oms.trs_connector.api.TrsConnector;
import lk.ac.ucsc.oms.trs_connector.api.beans.authentication.reply.PortfolioHeader;
import lk.ac.ucsc.oms.trs_connector.api.beans.authentication.reply.TradingAccount;

import lk.ac.ucsc.oms.trs_connector.implGeneral.beans.authentication.reply.PortfolioHeaderBean;
import lk.ac.ucsc.oms.trs_connector.implGeneral.beans.authentication.reply.TradingAccountBean;

import lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers.MessageHandler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import lk.ac.ucsc.oms.trs_writer.api.TrsWriterFactory;
import lk.ac.ucsc.oms.common_utility.api.constants.CommonConstants;



public class TrsConnectorJsonImpl implements TrsConnector {
    private static Logger logger = LogManager.getLogger(TrsConnectorJsonImpl.class);

    private MessageProtocolFacade messageProtocolFacade;

    private MessageHandler authMessageHandler;
    private MessageHandler tradeMessageHandler;
    private MessageHandler inquiryMessageHandler;
    private MessageHandler customerInquiryMessageHandler;
    private MessageHandler tradingInquiryMessageHandler;

    private TrsWriterFactory trsWriterFactory;


    /**
     * {@inheritDoc}
     */
    @Override
    public void processTrsMessage(String messageString) {
        if (trsWriterFactory == null) {
            trsWriterFactory = TrsWriterFactory.getInstance();
        }
        try {
            Header header = messageProtocolFacade.getHeaderFromEnvelop(messageString);
            String uniqueRequestId = header.getUniqueReqID();
            try {
                Message message = messageProtocolFacade.getMessageFromEnvelop(header, messageString);

                int messageGroup = header.getMsgGroup();
                Envelope response;
                switch (messageGroup) {
                    case GroupConstants.GROUP_AUTHENTICATION:
                        response = authMessageHandler.processMessage(header, message);
                        trsWriterFactory.getTrsWriter().writeAuthResponse(messageProtocolFacade.getJSonString(response));
                        break;
                    case GroupConstants.GROUP_TRADING:
                        tradeMessageHandler.processMessage(header, message);
                        break;
                    case GroupConstants.GROUP_INQUIRY:
                        response = inquiryMessageHandler.processMessage(header, message);
                        trsWriterFactory.getTrsWriter().writeInquiryResponse(messageProtocolFacade.getJSonString(response));
                        break;
                    case GroupConstants.GROUP_CUSTOMER_INQUIRY:
                        response = customerInquiryMessageHandler.processMessage(header, message);
                        trsWriterFactory.getTrsWriter().writeInquiryResponse(messageProtocolFacade.getJSonString(response));
                        break;
                    case GroupConstants.GROUP_TRADING_INQUIRY:
                        response = tradingInquiryMessageHandler.processMessage(header, message);
                        trsWriterFactory.getTrsWriter().writeInquiryResponse(messageProtocolFacade.getJSonString(response));
                        break;

                    default:
                        logger.error("Could not recognize Message group");
                        break;
                }
            } catch (InvalidVersionException e) {
                header.setRespStatus(ValueConstants.RESPONSE_STATUS_FAILURE);
                header.setRespReason("Invalid version");
                header.setErrorCode(ValueConstants.ERR_INVALID_VERSION);
                trsWriterFactory.getTrsWriter().writeResponse(header.getMsgGroup(), messageProtocolFacade.getJSonString(new EnvelopeImpl((HeaderImpl) header, new MessageImpl())));
            } catch (MessageProtocolException e) {
                header.setRespStatus(ValueConstants.RESPONSE_STATUS_FAILURE);
                header.setRespReason("Invalid Message");
                header.setErrorCode(ValueConstants.ERR_INVALID_MESSAGE);
                trsWriterFactory.getTrsWriter().writeResponse(header.getMsgGroup(), messageProtocolFacade.getJSonString(new EnvelopeImpl((HeaderImpl) header, new MessageImpl())));
            } catch (Exception e) {
                logger.error("Error processing client request: " + header.getMsgGroup() + "-" + header.getMsgType(), e);
                header.setRespStatus(ValueConstants.RESPONSE_STATUS_FAILURE);
                header.setErrorCode(ValueConstants.ERR_SYSTEM_ERROR);
                trsWriterFactory.getTrsWriter().writeResponse(header.getMsgGroup(), messageProtocolFacade.getJSonString(new EnvelopeImpl((HeaderImpl) header, new MessageImpl())));
            }
        } catch (Exception e) {
            logger.error("Error processing client request >>" + e.getMessage(), e);
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public PortfolioHeader getEmptyPortfolioHeader() {
        return new PortfolioHeaderBean();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TradingAccount getEmptyTradingAccount() {
        return new TradingAccountBean();
    }

    /**
     * inject trading inquiry message handler
     *
     * @param tradingInquiryMessageHandler trading inquiry message handler
     */
    public void setTradingInquiryMessageHandler(MessageHandler tradingInquiryMessageHandler) {
        this.tradingInquiryMessageHandler = tradingInquiryMessageHandler;
    }




    /**
     * Inject message protocol messageProtocolFacade
     *
     * @param protocolFacade messageProtocolFacade
     */
    public void setMessageProtocolFacade(MessageProtocolFacade protocolFacade) {
        this.messageProtocolFacade = protocolFacade;
    }



    /**
     * Inject authentication message handler
     *
     * @param authMessageHandler authentication message handler
     */
    public void setAuthMessageHandler(MessageHandler authMessageHandler) {
        this.authMessageHandler = authMessageHandler;
    }

    /**
     * Inject Trade Message handler
     *
     * @param tradeMessageHandler Trade Message handler
     */
    public void setTradeMessageHandler(MessageHandler tradeMessageHandler) {
        this.tradeMessageHandler = tradeMessageHandler;
    }

    /**
     * Inject Inquiry message handler
     *
     * @param inquiryMessageHandler Inquiry message handler
     */
    public void setInquiryMessageHandler(MessageHandler inquiryMessageHandler) {
        this.inquiryMessageHandler = inquiryMessageHandler;
    }

    /**
     * Inject customer inquiry message handler
     *
     * @param customerInquiryMessageHandler customer inquiry message handler
     */
    public void setCustomerInquiryMessageHandler(MessageHandler customerInquiryMessageHandler) {
        this.customerInquiryMessageHandler = customerInquiryMessageHandler;
    }



    /**
     * usage is only for unit test case
     *
     * @param trsWriterFactory
     */
    public void setTrsWriterFactory(TrsWriterFactory trsWriterFactory) {
        this.trsWriterFactory = trsWriterFactory;
    }

}


