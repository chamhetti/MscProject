package lk.ac.ucsc.clientConnector.controller;

import lk.ac.ucsc.oms.messaging_protocol_json.api.GroupConstants;
import lk.ac.ucsc.clientConnector.api.Converter;
import lk.ac.ucsc.clientConnector.api.OmsController;
import lk.ac.ucsc.clientConnector.common.api.ProcessingStatus;
import lk.ac.ucsc.clientConnector.common.api.TrsMessage;
import lk.ac.ucsc.clientConnector.common.api.TrsSender;
import lk.ac.ucsc.clientConnector.common.impl.ConnectionPropertiesBean;
import lk.ac.ucsc.clientConnector.front_office_connector.api.FrontOfficeConnectorFacade;
import lk.ac.ucsc.clientConnector.front_office_connector.api.FrontOfficeConnectorFactory;
import lk.ac.ucsc.clientConnector.front_office_connector.api.MessageSender;
import lk.ac.ucsc.clientConnector.front_office_connector.api.exception.MessageSenderException;
import lk.ac.ucsc.clientConnector.front_office_listener.api.ResponseProcessorFacade;
import lk.ac.ucsc.clientConnector.front_office_listener.api.ResponseProcessorFactory;
import lk.ac.ucsc.clientConnector.front_office_listener.api.exception.MessageListenerException;
import lk.ac.ucsc.clientConnector.exceptions.ClientConnectorException;
import lk.ac.ucsc.clientConnector.responseProcessor.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class FrontOfficeController implements OmsController {
    private static Logger logger = LoggerFactory.getLogger(FrontOfficeController.class);
    private Converter converter;
    private MessageSender authQSender;
    private MessageSender inquiryQSender;
    private MessageSender transactionQSender;

    private Map<String, ConnectionPropertiesBean> messageQueuePropertiesMap;
    private Map<String, ConnectionPropertiesBean> messageTopicPropertiesMap;
    private ExecutorService executorService;
    private FrontOfficeConnectorFacade requestProcessorFacade = FrontOfficeConnectorFactory.getOmsConnecter();
    private ResponseProcessorFacade responseProcessorFacade = ResponseProcessorFactory.getResponseProcessorFacade();


    public FrontOfficeController() {
        executorService = Executors.newCachedThreadPool();
    }

    public void setMessageQueuePropertiesMap(Map<String, ConnectionPropertiesBean> messageQueuePropertiesMap) {
        this.messageQueuePropertiesMap = messageQueuePropertiesMap;
    }

    public void setMessageTopicPropertiesMap(Map<String, ConnectionPropertiesBean> messageTopicPropertiesMap) {
        this.messageTopicPropertiesMap = messageTopicPropertiesMap;
    }

    public void setConverter(Converter converter) {
        this.converter = converter;
    }


    public void initController() {
        authQSender = requestProcessorFacade.createMessageSender(messageQueuePropertiesMap.get("authQueue"));
        inquiryQSender = requestProcessorFacade.createMessageSender(messageQueuePropertiesMap.get("inquiryQueue"));
        transactionQSender = requestProcessorFacade.createMessageSender(messageQueuePropertiesMap.get("transQueue"));

        responseProcessorFacade.createMessageListener(messageTopicPropertiesMap.get("resAuthTopic"), new LoginResponseProcessor(converter));
        responseProcessorFacade.createMessageListener(messageTopicPropertiesMap.get("resInquiryTopic"), new InquiryResponseProcessor(converter));
        responseProcessorFacade.createMessageListener(messageTopicPropertiesMap.get("resTransTopic"), new TradeResponseProcessor(converter));
        logger.info("Queue Listeners created.");

    }

    @Override
    public boolean startController() throws MessageListenerException {

        authQSender.start();
        inquiryQSender.start();
        transactionQSender.start();
        responseProcessorFacade.initialize();
        return true;
    }

    @Override
    public boolean stopController() {
        return true;
    }

    public MessageSender getSender(TrsMessage message) {
        switch (message.getGroup()) {
            case GroupConstants.GROUP_TRADING:
                return transactionQSender;
            case GroupConstants.GROUP_AUTHENTICATION:
                return authQSender;
            case GroupConstants.GROUP_INQUIRY:
                return inquiryQSender;
            default:
                return inquiryQSender;
        }
    }


    public void placeMessage(TrsMessage trsmessage) throws ClientConnectorException {
        logger.info("Routing request to front office:" + trsmessage.toStringMetaData());
        MessageSender sender = getSender(trsmessage);
        String messageString = converter.convertToString(trsmessage);
        executorService.submit(new MessageSenderWorker(sender, messageString, trsmessage));
    }


    private static final class MessageSenderWorker implements Runnable {
        private MessageSender sender;
        private String message;
        private TrsMessage trsMessage;

        private MessageSenderWorker(MessageSender sender, String message, TrsMessage trsMessage) {
            this.sender = sender;
            this.message = message;
            this.trsMessage = trsMessage;
        }

        @Override
        public void run() {
            try {
                MDC.put("tid", "tid-req" + trsMessage.getUniqueReqId());
                sender.sendMessage(message, 0);
                try {
                    trsMessage.setProcessingStatus(ProcessingStatus.SENT_TO_OMS);
                } catch (Exception e) {
                    logger.error("Error update message status", e);
                }
            } catch (MessageSenderException e) {
                logger.error("Error sending message", e);
            }
        }
    }

}
