package lk.ac.ucsc.oms.stp_connector.implGeneral;

import lk.ac.ucsc.oms.common_utility.api.enums.*;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.fix.api.FIXFacadeInterface;
import lk.ac.ucsc.oms.fix.api.beans.FixOrderInterface;
import lk.ac.ucsc.oms.orderMgt.api.ExecutionAuditRecordManager;
import lk.ac.ucsc.oms.orderMgt.api.OrderManager;
import lk.ac.ucsc.oms.orderMgt.api.beans.ExecutionAuditRecord;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import lk.ac.ucsc.oms.stp_connector.api.*;
import lk.ac.ucsc.oms.stp_connector.api.bean.AppiaMessageInterface;
import lk.ac.ucsc.oms.stp_connector.api.bean.STPValidationReply;
import lk.ac.ucsc.oms.stp_connector.api.exception.STPConnectException;
import lk.ac.ucsc.oms.stp_connector.api.exception.STPSenderException;
import lk.ac.ucsc.oms.stp_connector.api.exception.STPValidationException;
import lk.ac.ucsc.oms.stp_connector.implGeneral.bean.AppiaMessage;
import lk.ac.ucsc.oms.stp_connector.implGeneral.bean.STPMessage;
import lk.ac.ucsc.oms.system_configuration.api.SysConfigException;
import lk.ac.ucsc.oms.system_configuration.api.SysLevelParaManager;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.TextMessage;

public class STPConnectorImpl implements STPConnector {
    private static Logger logger = LogManager.getLogger(STPConnectorImpl.class);
    private ExchangeServiceImpl exgService;

    private ExecutionReportProcessor execReportProcessor;
    private MarketStatusMessageController marketStatusMessageController;
    private FixConnectionStatusController fixStatusProcessor;
    private FIXFacadeInterface fixFacade;
    private static final int APPLICATION_MESSAGE = 23041;
    private static final int SESSION_CONNECTED = 23210;
    private static final int SESSION_DISCONNECTED = 23211;
    private static final int MESSAGE_VALIDATION_ERROR = 23011;
    private static final int MESSAGE_EXPIRED = 23034;
    private String messageType = null;


    public STPConnectorImpl(ExchangeServiceImpl exchangeService, ExecutionReportProcessor execReportProcessor,
                            MarketStatusMessageController marketStatusMessageController, FixConnectionStatusController fixConnectionStatusController,
                            FIXFacadeInterface fixFacade, OrderManager orderManager,
                            SysLevelParaManager sysLevelParaManager, ExecutionAuditRecordManager executionAuditRecordManager) throws SysConfigException {
        this.exgService = exchangeService;
        this.execReportProcessor = execReportProcessor;
        this.marketStatusMessageController = marketStatusMessageController;
        this.fixStatusProcessor = fixConnectionStatusController;
        this.fixFacade = fixFacade;
        messageType = STPConnectorType.APPIA.getCode();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processMessage(FixOrderInterface fixOrd, String sessionId) throws STPConnectException {
        String execRepProcessorText = "execReportProcessor: ";

        if (fixOrd == null) {
            throw new STPConnectException("FIXOrder or Message processor can't be null");
        }
        switch (fixOrd.getMessageType()) {
            case REJECT:
                if (execReportProcessor == null) {
                    execReportProcessor = getFactory().getExecReportProcessor();
                }
                logger.info(execRepProcessorText + execReportProcessor);
                execReportProcessor.processOrderReject(fixOrd, sessionId);
                break;
            case EXECUTION_REPORT:
                if (execReportProcessor == null) {
                    execReportProcessor = getFactory().getExecReportProcessor();
                }
                logger.info(execRepProcessorText + execReportProcessor);

                execReportProcessor.processExecutionReport(fixOrd, sessionId);
                break;

            case TRADING_SESSION_STATUS:
                if (marketStatusMessageController == null) {
                    marketStatusMessageController = getFactory().getMarketStatusResProcessor();
                }
                logger.info("marketStatusMessageController >>" + marketStatusMessageController);
                marketStatusMessageController.processTradingSessionStatus(fixOrd, sessionId);
                break;
            default:
                throw new STPConnectException("Unrecognized fix message type: " + fixOrd.getMessageType());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Order sendQueueMessage(FixOrderInterface.MessageType messageType, Order order, String fixMessage,
                                  String sessionQualifier) throws STPSenderException {
        return exgService.sendToExchange(messageType, order, fixMessage, sessionQualifier);
    }


    @Override
    public Order isValidExchangeConnection(Order order) throws STPValidationException {
        if (order == null) {
            throw new STPValidationException("Order can't be null", null);
        }
        return exgService.isValidExchangeConnection(order);
    }

    @Override
    public STPValidationReply isValidFixConnection(Order order) throws STPValidationException {
        if (order == null) {
            throw new STPValidationException("Order can't be null", null);
        }
        return exgService.isValidFixConnection(order);
    }

    @Override
    public STPValidationReply isValidFixStatus(Order order) throws STPConnectException {
        if (order == null) {
            throw new STPConnectException("Order can't be null");
        }
        return exgService.isValidFixStatus(order);
    }

    @Override
    public void connectSession(String sessionId) {
        logger.info("FIXConstants session connected "+ sessionId);
        if (fixStatusProcessor == null) {
            fixStatusProcessor = getFactory().getFixConnectionStatusProcessor();
        }
        logger.info("fixStatusProcessor >>" + fixStatusProcessor);
        fixStatusProcessor.processConnectedSession(sessionId);
    }

    @Override
    public void disconnectSession(String sessionId) {
        logger.info("FIXConstants session -{} Disconnected "+ sessionId);
        if (fixStatusProcessor == null) {
            fixStatusProcessor = getFactory().getFixConnectionStatusProcessor();
        }
        logger.info("fixStatusProcessor >>" + fixStatusProcessor);
        fixStatusProcessor.processDisconnectSession(sessionId);
    }


    @Override
    public void processMessage(Message message) {
        try {
            String mdbMessage;
            //if the message type appia message coming as a map message and if it is stp message coming as a text message. Hence
            // need to process them separately
            if (messageType.equals(STPConnectorType.APPIA.getCode())) {
                logger.info("Received Message from the Appia queue:FromAppia " + message);

                AppiaMessage appiaMessageBean = getAppiaMessage(message);
                processAppiaMessage(appiaMessageBean);
            } else {
                logger.info("Received Message from the STP queue:FromSTP " + message);

                mdbMessage = ((TextMessage) message).getText();
                processSTPMessage(mdbMessage);
            }
        } catch (JMSException e) {
            logger.error(e.toString(), e);
        }
    }

    @Override
    public void initialize() throws STPConnectException {
        logger.info("STP Connector Module Initialized...");
    }

    private void processSTPMessage(String stpMessage) {
        STPMessage stp = getSTPMessage();
        stp.parseMessage(stpMessage);

        if (stp.getType().equalsIgnoreCase(STPMessage.MESSAGE_TYPE_STATUS)) {
            // Control messages those are related to session connection and disconnection from sell side fix gateway.
            logger.info("Control Message Received -  "+ stp.getMessage());
            if (stp.getMessage().equalsIgnoreCase(STPMessage.MESSAGE_LOG_IN)) {
                //process the session connection
                connectSession(stp.getExchange());
            } else if (stp.getMessage().equalsIgnoreCase(
                    STPMessage.MESSAGE_LOG_OUT)) {
                //process the session disconnection
                disconnectSession(stp.getExchange());
            }

        } else {
            //message related to application message under appia that is fix messages of 35=D,G,F8,9,h
            logger.info("Application Message Received from STP -  "+ stp.getMessage());
            processMessage(stp.getMessage(), stp.getExchange());
        }

    }

    private void processAppiaMessage(AppiaMessageInterface appiaMessage) {
        switch (appiaMessage.getEventType()) {
            case APPLICATION_MESSAGE:
                logger.info("Application Message Received -  "+ appiaMessage.getEventData());
                //this types of messages are related to order request, response and market status messages. that is
                // 35=D,G,F,8,9,h
                processMessage(appiaMessage.getEventData(), appiaMessage.getSessionID());
                break;
            case SESSION_CONNECTED:
                logger.info("SESSION_CONNECTED -  "+ appiaMessage.getSessionID());
                //message receive when the appia session connect to exchange.
                connectSession(appiaMessage.getSessionID());
                break;
            case SESSION_DISCONNECTED:
                logger.info("SESSION_DISCONNECTED -  "+ appiaMessage.getSessionID());
                //message receive when appia session disconnect from exchange.
                disconnectSession(appiaMessage.getSessionID());
                break;
            case MESSAGE_VALIDATION_ERROR:
                logger.warn("MESSAGE_VALIDATION_ERROR -  "+ appiaMessage.getEventData());
                break;
            default:
                logger.info("DEFAULTED EVENT_TYPE : " + appiaMessage.getEventType() + " - " + appiaMessage);
                break;

        }
    }

    private void processMessage(String stpRequest, String sessionId) {
        try {
            FixOrderInterface fixOrder = fixFacade.getParseFixMessage(stpRequest, sessionId);
            processMessage(fixOrder, sessionId);

        } catch (OMSException e) {
            logger.error("Error In Calling FIX FACADE -  " + e);
        }
    }

    public AppiaMessage getAppiaMessage(Message message) {
        return new AppiaMessage((MapMessage) message);

    }
    public STPConnectorFactory getFactory() {
        return STPConnectorFactory.getInstance();
    }

    public STPMessage getSTPMessage() {
        return new STPMessage();
    }

}



