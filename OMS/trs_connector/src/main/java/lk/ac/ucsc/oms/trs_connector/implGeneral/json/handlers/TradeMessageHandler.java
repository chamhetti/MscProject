package lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers;

import lk.ac.ucsc.oms.messaging_protocol_json.api.*;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Envelope;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Header;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Message;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.EnvelopeImpl;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.HeaderImpl;
import lk.ac.ucsc.oms.trs_connector.api.exceptions.NoSuchProcessException;
import lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers.helpers.TradingHandlerHelper;
import lk.ac.ucsc.oms.trs_writer.api.TrsWriterFactory;
import lk.ac.ucsc.oms.trs_writer.api.exceptions.TrsWriterException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TradeMessageHandler implements MessageHandler {
    private static Logger logger = LogManager.getLogger(TradeMessageHandler.class);

    private TradingHandlerHelper tradingHandlerHelper;
    private MessageProtocolFacadeFactory messageProtocolFacadeFactory;
    private TrsWriterFactory trsWriterFactory;

    /**
     * {@inheritDoc}
     */
    @Override
    public Envelope processMessage(Header header, Message message) throws NoSuchProcessException {
        Message response = new Message() {
        };
        logger.info("New Trade Request received - " + header.getMsgType());

        try {
            switch (header.getMsgType()) {
                case MessageConstants.REQUEST_TYPE_NEW_NORMAL_ORDER:
                    tradingHandlerHelper.processNewOrder(header, message);
                    header.setMsgType(MessageConstants.RESPONSE_TYPE_NEW_NORMAL_ORDER);
                    break;

                case MessageConstants.REQUEST_TYPE_AMEND_NORMAL_ORDER:
                    tradingHandlerHelper.processAmendOrder(header, message);
                    header.setMsgType(MessageConstants.RESPONSE_TYPE_NEW_NORMAL_ORDER);
                    break;

                case MessageConstants.REQUEST_TYPE_CANCEL_NORMAL_ORDER:
                    tradingHandlerHelper.processCancelOrder(header, message);
                    header.setMsgType(MessageConstants.RESPONSE_TYPE_NEW_NORMAL_ORDER);
                    break;
                case MessageConstants.REQUEST_TYPE_EXPIRE_ORDER:
                    tradingHandlerHelper.processExpireOrder(header, message);
                    break;
                default:
                    throw new NoSuchProcessException("No such request type in trading group");
            }
            header.setRespStatus(ValueConstants.RESPONSE_STATUS_SUCCESS);
        } catch (InvalidVersionException e) {
            logger.error("Error while processing trade request", e);
            header.setRespStatus(ValueConstants.RESPONSE_STATUS_FAILURE);
            header.setRespReason("System Error: " + e.getMessage());
            header.setErrorCode(ValueConstants.ERR_SYSTEM_ERROR);
        }

        return new EnvelopeImpl((HeaderImpl) header, response); // no responses are (other than few exceptions) sent for trade messages
    }

    /**
     * method used to send the authentication response to client
     *
     * @param response
     */
    private void sendTradeResponse(Header header, Message response) {
        try {
            if (messageProtocolFacadeFactory == null) {
                messageProtocolFacadeFactory = MessageProtocolFacadeFactory.getInstance();
            }
            if (trsWriterFactory == null) {
                trsWriterFactory = TrsWriterFactory.getInstance();
            }
            MessageProtocolFacade messageProtocolFacade = messageProtocolFacadeFactory.getMessageProtocolFacade();
            String jSonString = messageProtocolFacade.getJSonString(header, response);
            logger.info("Sending json reponse -{} to clients", jSonString);
            trsWriterFactory.getTrsWriter().writeOtherTradeResponse(jSonString);
        } catch (TrsWriterException e) {
            logger.error("Error sending authentication response", e);
        }
    }

    /**
     * Inject the trading handler helper
     *
     * @param tradingHandlerHelper is the trading handler helper implementation
     */
    public void setTradingHandlerHelper(TradingHandlerHelper tradingHandlerHelper) {
        this.tradingHandlerHelper = tradingHandlerHelper;
    }

    /**
     * only usage is unit test case
     */
    public void setMessageProtocolFacadeFactory(MessageProtocolFacadeFactory messageProtocolFacadeFactory) {
        this.messageProtocolFacadeFactory = messageProtocolFacadeFactory;
    }

    /**
     * only usage is unit test case
     */
    public void setTrsWriterFactory(TrsWriterFactory trsWriterFactory) {
        this.trsWriterFactory = trsWriterFactory;
    }
}
