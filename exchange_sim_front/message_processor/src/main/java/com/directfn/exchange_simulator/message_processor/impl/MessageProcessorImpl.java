package com.directfn.exchange_simulator.message_processor.impl;

import com.directfn.exchange_simulator.common_util.enums.OrderStatus;
import com.directfn.exchange_simulator.common_util.exceptions.SimulatorException;
import com.directfn.exchange_simulator.common_util.utils.ConfigSettings;
import com.directfn.exchange_simulator.common_util.utils.Configuration;
import com.directfn.exchange_simulator.common_util.utils.GlobalStatus;
import com.directfn.exchange_simulator.jms_writer.JMSQueueWriter;
import com.directfn.exchange_simulator.message_processor.api.MessageProcessor;
import com.directfn.exchange_simulator.message_processor.impl.beans.DataSeparator;
import com.directfn.exchange_simulator.message_processor.impl.beans.FIXPropertiesBean;
import com.directfn.exchange_simulator.message_processor.impl.beans.FixMessageBean;
import com.directfn.exchange_simulator.message_processor.impl.beans.TradingSessions;
import com.directfn.exchange_simulator.message_processor.impl.helper.FixMessageGenerator;
import com.directfn.exchange_simulator.message_processor.impl.helper.MessageMap;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quickfix.*;

import javax.jms.MapMessage;
import javax.jms.Message;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * User: Ruchira Ranaweera
 * Date: 4/24/14
 */

/**
 * Implementation for message processor interface
 */
public class MessageProcessorImpl implements MessageProcessor, Callable<Object> {
    private static Logger logger = LoggerFactory.getLogger(MessageProcessorImpl.class);
    private MemoryStore memoryStore;
    private static String queueName = new ConfigSettings().getConfiguration("queueToWrite");
    private static String omsIP = new ConfigSettings().getConfiguration("omsIP");
    private static final String FIELD_SEPERATOR = "\u0001";
    private DataSeparator dataSeparator = new DataSeparator("=");
    private static final int MSG_TYPE = 35;
    private static final int CLORD_ID = 11;
    private static final int ACCOUNT = 1;
    private static final int ORDER_QUANTITY = 38;
    private static final int TRAD_SESSION_STATUS = 340;
    private static final int SENDER_COMP_ID = 49;
    private static final int SENDING_TIME = 52;
    private static final int TARGET_COMP_ID = 56;
    private static final int TRADING_SESSION_REQUEST_ID = 335;
    private static final int APPIA_MSG_ID = 9999;
    public static final int SENDER_SUB_ID = 50;
    public static final int SENDER_LOCATION_ID = 142;
    public static final int SYMBOL = 55;
    public static final int SIDE = 54;
    public static final int TRANSACTION_TIME = 60;
    public static final int HANDLE_INST = 21;
    public static final int TRAD_SES_ID = 336;
    public static final int ORD_TYPE = 40;
    public static final int TIME_IN_FORCE = 59;
    public static final int EXPIRE_TIME = 126;
    public static final int SECURITY_EXCHANGE = 207;
    public static final int ORIG_CLORD_ID = 41;
    public static final int PRICE = 44;
    public static final int MIN_QTY = 110;
    public static final int MAX_FLOOR = 111;
    public static final int SECURITY_ID_SOURCE = 22;
    public static final int CLORD_ID_9974 = 9974;
    public static final int POSS_DUP_FLAG = 43;
    public static final int ORDER_ID = 37;
    public static final int EXEC_ID = 17;
    public static final int SECURITY_TYPE = 167;
    public static final int CURRENCY = 15;
    public static final int SIDE_9959 = 9959;
    public static final int STOP_PX = 99;
    public static final int EXPIRE_DATE = 432;
    public static final int SETTLEMNT_TYPE = 63;
    public static final int LAST_PX = 31;
    public static final int AVG_PX = 6;
    public static final int EXEC_TYPE = 150;
    public static final int ORD_STATUS = 39;
    public static final int ORD_STATUS_9971 = 9971;
    public static final int EXEC_TRANS_TYPE = 20;
    public static final int ORD_REJ_REASON = 102;
    public static final int ORD_REJ_REASON_103 = 103;
    public static final int TEXT = 58;
    public static final int LAST_SHARES = 32;
    public static final int CUM_QTY = 14;
    public static final int LEAVES_QTY = 151;
    public static final int SECURITY_ID = 48;
    public static final int CXL_REJ_RESPONSE_TO = 434;
    public static final int FIX_VERSION = 8;

    private static final char TRADING_SESSION_STATUS_REQUEST = 'g';
    public static final char NEW_ORDER_SINGLE = 'D';
    public static final char ORDER_CANCEL_REPLACE = 'G';
    public static final char ORDER_CANCEL_REQUEST = 'F';
    public static final char EXECUTION_REPORT = '8';

    private static final int SERVER_PORT1099 = 1099;
    private static final int TIME_OUT_CONST = 5;
    private JMSQueueWriter jmsQueueWriter = null;
    private Message message;
    private double price;
    private double blockSize;
    private static Gson gson = new Gson();
    private static long orderID;
    private static long executionID;
    private boolean isDefaultSettingsEnabled = false;
    private long timeGapBetweenTwoExecutions = 0L;
    private static int i = 0;
    private static int constant;
    private static Application application;

    /**
     * constructor for MessageProcessorImpl
     *
     * @param message
     */
    public MessageProcessorImpl(Message message) {
        this.message = message;
    }

    /**
     * constructor for MessageProcessorImpl
     */
    public MessageProcessorImpl() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processRequestMessageReadFromToAppia(Message message) {
        FixMessageBean fixMessageBean = new FixMessageBean((MapMessage) message);
        String messageToProcess = null;
        if (fixMessageBean.getEventData() != null) {
            messageToProcess = fixMessageBean.getEventData();
        } else if (fixMessageBean.getMetaData() != null) {
            messageToProcess = fixMessageBean.getMetaData();
        } else if (fixMessageBean.getMessageData() != null) {
            messageToProcess = fixMessageBean.getMessageData();
        }
        char messageType = '\u0001';
        if (messageToProcess != null) {
            messageType = getFIXMessageType(messageToProcess);
            logger.info("fix message received. message type: {} and fix message: {}",messageType,messageToProcess);
        }else {
            logger.info("received message is invalid");
        }
        // process FIX message based on the messageType
        switch (messageType) {
            case TRADING_SESSION_STATUS_REQUEST:
                processTradingSessionStatusRequestMessage(messageToProcess, fixMessageBean);
                break;
            case NEW_ORDER_SINGLE:
                processNewOrderRequestMessage(messageToProcess, fixMessageBean);
                break;
            case ORDER_CANCEL_REPLACE:
                processReplaceOrderRequestMessage(messageToProcess, fixMessageBean);
                break;
            case ORDER_CANCEL_REQUEST:
                processCancelOrderRequestMessage(messageToProcess, fixMessageBean);
                break;
            default:
                logger.error("message type not found", messageType);
                break;
        }
//        logger.info("Message type: " + messageType);
    }

    /**
     * process trading session status request message
     *
     * @param messageToProcess
     * @param fixMessageBean
     */
    private void processTradingSessionStatusRequestMessage(String messageToProcess, FixMessageBean fixMessageBean) {
        FIXPropertiesBean fixPropertiesBean = getMarketStatusMessage(messageToProcess);
        fixPropertiesBean.setSessionId(fixMessageBean.getSessionID());
        fixPropertiesBean.setClientType(fixMessageBean.getClientType());

    }

    /**
     * process new order request message
     *
     * @param messageToProcess
     * @param fixMessageBean
     */
    private void processNewOrderRequestMessage(String messageToProcess, FixMessageBean fixMessageBean) {
        FIXPropertiesBean fixPropertiesBean = getTradingMessage(messageToProcess);
        fixPropertiesBean.setSessionId(fixMessageBean.getSessionID());
        fixPropertiesBean.setOriginalMessageType("New Order(D)");
        fixPropertiesBean.setClientType(fixMessageBean.getClientType());
        processNewOrderMessage(fixPropertiesBean);
    }
    /**
     * process replace order request message
     *
     * @param messageToProcess
     * @param fixMessageBean
     */
    private void processReplaceOrderRequestMessage(String messageToProcess, FixMessageBean fixMessageBean) {
        Configuration configuration = new Configuration();
        FIXPropertiesBean fixPropertiesBean = getTradingMessage(messageToProcess);
        fixPropertiesBean.setSessionId(fixMessageBean.getSessionID());
        fixPropertiesBean.setOriginalMessageType("Change Order(G)");
        fixPropertiesBean.setClientType(fixMessageBean.getClientType());
        processReplaceMessage(fixPropertiesBean);

    }

    /**
     * process cancel order request message
     *
     * @param messageToProcess
     * @param fixMessageBean
     */
    private void processCancelOrderRequestMessage(String messageToProcess, FixMessageBean fixMessageBean) {
        FIXPropertiesBean fixPropertiesBean = getTradingMessage(messageToProcess);
        fixPropertiesBean.setSessionId(fixMessageBean.getSessionID());
        fixPropertiesBean.setOriginalMessageType("Cancel Order(F)");
        fixPropertiesBean.setClientType(fixMessageBean.getClientType());
        processCancelMessage(fixPropertiesBean);
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized boolean generateResponseFixMessage(FIXPropertiesBean fixPropertiesBean) {
        String fixMessage;
        MessageMap messageMap;
        if (jmsQueueWriter == null) {
            jmsQueueWriter = new JMSQueueWriter(queueName, omsIP, SERVER_PORT1099, TIME_OUT_CONST);
        }
        try {
            fixMessage = new FixMessageGenerator().getFixMessage(fixPropertiesBean);
                       String messageType;
            if (fixPropertiesBean.getClordID() == null) {
                messageType = "SESSION";
            } else {
                messageType = "TRADING";
            }
            logger.debug("FIX message generated for order: {}:====>fix message: {}", fixPropertiesBean.getClordID(), fixMessage);
            // send response based on client type
            if (fixPropertiesBean.getClientType() != null && fixPropertiesBean.getClientType().equals("FIX")) {
                quickfix.Message message;
                SessionID sessionID;
                try {
                    message = new quickfix.Message(fixMessage, false);
                    sessionID = new SessionID(fixPropertiesBean.getSessionId());
                } catch (InvalidMessage e) {
                    logger.error("Invalid fix message: {}", fixMessage, e);
                    return false;
                }
                try {
                    boolean result;
                    result = Session.sendToTarget(message, sessionID);
                    if (result) {
                        logger.info("Message: {} sent", message);
                    } else {
                        logger.info("Message: {} sending fail", message);
                    }
                } catch (Exception e) {
                    logger.info("Error occurred: {}", e.getMessage(), e);
                }
            } else {
                messageMap = new MessageMap(fixPropertiesBean.getSessionId(), String.valueOf(fixPropertiesBean.getMessageType()), "FIX4.4", fixMessage, "", "", "23041", "");
                Map<String, String> map = messageMap.getMessageMap();
                jmsQueueWriter.sendMessage(map, 1);
            }
            return true;
        } catch (SimulatorException e) {
            logger.error("Error while getting fix message", e);
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean generateTradingSessionStatusMapMessage(String sessionId, String eventType) {
        MessageMap messageMap;
        if (jmsQueueWriter == null) {
            jmsQueueWriter = new JMSQueueWriter(queueName, omsIP, SERVER_PORT1099, TIME_OUT_CONST);
        }
        try {
            messageMap = new MessageMap(sessionId, "", "", sessionId, "", "", eventType, "");
            Map<String, String> map = messageMap.getMessageMap();
            jmsQueueWriter.sendMessage(map, 1);
            return true;
        } catch (SimulatorException e) {
            logger.error("Error while getting fix message", e);
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    public void processNewOrderMessage(FIXPropertiesBean fixPropertiesBean) {
        List<FIXPropertiesBean> fixPropertiesBeanList;
        Map<String, String> map = GlobalStatus.getClOrdIdOverIsValidToProcess();
        if (map == null) {
            map = new HashMap<>();
        }
        map.put(fixPropertiesBean.getClordID(), "NEW");
        GlobalStatus.setClOrdIdOverIsValidToProcess(map);
        GlobalStatus.addOpenOrder(fixPropertiesBean.getClordID(),fixPropertiesBean);
        fixPropertiesBeanList = getTradingMessageFixPropertiesForDefaultExecutionForNewOrder(fixPropertiesBean);
        if (!fixPropertiesBeanList.isEmpty()) {
            for (FIXPropertiesBean i : fixPropertiesBeanList) {
                try {
                    if (timeGapBetweenTwoExecutions == 0L) {
                        timeGapBetweenTwoExecutions = Long.parseLong(new ConfigSettings().getConfiguration("timeGap"));
                    }
                    Thread.sleep(timeGapBetweenTwoExecutions);
                } catch (InterruptedException e) {
                    logger.error("Thread interrupted error ", e);
                }
                if ("NEW".equals(GlobalStatus.getClOrdIdOverIsValidToProcess().get(i.getClordID()))) {
                    generateResponseFixMessage(i);
                    if (i.getOrdStatus() == '2') {
                        i.setValidToAmendOrCancel(false);
                    } else {
                        i.setValidToAmendOrCancel(true);
                    }
                    GlobalStatus.addOpenOrder(fixPropertiesBean.getClordID(),i);
                } else {
                    break;
                }
            }
            GlobalStatus.removeOpenOrder(fixPropertiesBean.getClordID());
        } else {
            logger.error("execution list not generated");
        }
    }

    /**
     * get trading message FIX properties for default execution for new order
     *
     * @param fixPropertiesBean
     * @return
     */
    private synchronized List<FIXPropertiesBean> getTradingMessageFixPropertiesForDefaultExecutionForNewOrder(FIXPropertiesBean fixPropertiesBean) {
        List<FIXPropertiesBean> listOfExecution = new LinkedList<>();
        if (price == 0.0) {
            price = Double.parseDouble(new ConfigSettings().getConfiguration("marketOrderPrice"));
        }
        TradingSessions tradingSessions = null;

        if (tradingSessions == null) {
            tradingSessions = new TradingSessions();
            tradingSessions.setTradingSession(fixPropertiesBean.getSessionId());
            tradingSessions.setRemoteFirmId(fixPropertiesBean.getTargetCompID());
            tradingSessions.setLocalFirmId(fixPropertiesBean.getSenderCompID());
        }
        if (tradingSessions != null) {
            double orderQuantity = fixPropertiesBean.getOrderQty();
            if (blockSize == 0) {
                blockSize = Double.parseDouble(new ConfigSettings().getConfiguration("fillingQuantity"));
            }
            if (orderQuantity > blockSize) {
                fixPropertiesBean.setOrdStatus(OrderStatus.NEW.getCode());
                listOfExecution.add(populateOrderResponse(fixPropertiesBean, tradingSessions));
                boolean isValid = true;
                double fillQuantity = 0;
                while (isValid) {
                    fillQuantity = fillQuantity + blockSize;
                    fixPropertiesBean.setOrdStatus(OrderStatus.PARTIALLY_FILLED.getCode());
                    fixPropertiesBean.setLeavesQty(orderQuantity - fillQuantity);
                    fixPropertiesBean.setLastShares(blockSize);
                    fixPropertiesBean.setCumQty(fillQuantity);
                    if (fixPropertiesBean.getOrdType() == '1') {
                        fixPropertiesBean.setAvgPx(price);
                        fixPropertiesBean.setPrice(price);
                        fixPropertiesBean.setLastPx(price);
                    } else {
                        fixPropertiesBean.setAvgPx(fixPropertiesBean.getPrice());
                        fixPropertiesBean.setPrice(fixPropertiesBean.getPrice());
                        fixPropertiesBean.setLastPx(fixPropertiesBean.getPrice());
                    }
                    listOfExecution.add(populateOrderResponse(fixPropertiesBean, tradingSessions));
                    if (orderQuantity - fillQuantity <= blockSize) {
                        isValid = false;
                        fixPropertiesBean.setCumQty(orderQuantity);
                        fixPropertiesBean.setLeavesQty(0);
                        fixPropertiesBean.setLastShares(orderQuantity - fillQuantity);
                        fixPropertiesBean.setOrdStatus(OrderStatus.FILLED.getCode());
                        fixPropertiesBean.setValidToAmendOrCancel(false);
                        if (fixPropertiesBean.getOrdType() == '1') {
                            fixPropertiesBean.setAvgPx(price);
                            fixPropertiesBean.setPrice(price);
                            fixPropertiesBean.setLastPx(price);
                        } else {
                            fixPropertiesBean.setAvgPx(fixPropertiesBean.getPrice());
                            fixPropertiesBean.setPrice(fixPropertiesBean.getPrice());
                            fixPropertiesBean.setLastPx(fixPropertiesBean.getPrice());
                        }
                        listOfExecution.add(populateOrderResponse(fixPropertiesBean, tradingSessions));
                    }
                }
            } else {
                fixPropertiesBean.setOrdStatus(OrderStatus.NEW.getCode());
                listOfExecution.add(populateOrderResponse(fixPropertiesBean, tradingSessions));
                fixPropertiesBean.setOrdStatus(OrderStatus.FILLED.getCode());
                fixPropertiesBean.setValidToAmendOrCancel(false);
                fixPropertiesBean.setLeavesQty(0);
                fixPropertiesBean.setLastShares(orderQuantity);
                fixPropertiesBean.setCumQty(orderQuantity);
                if (fixPropertiesBean.getOrdType() == '1') {
                    fixPropertiesBean.setAvgPx(price);
                    fixPropertiesBean.setPrice(price);
                    fixPropertiesBean.setLastPx(price);
                } else {
                    fixPropertiesBean.setAvgPx(fixPropertiesBean.getPrice());
                    fixPropertiesBean.setPrice(fixPropertiesBean.getPrice());
                    fixPropertiesBean.setLastPx(fixPropertiesBean.getPrice());
                }
                listOfExecution.add(populateOrderResponse(fixPropertiesBean, tradingSessions));
            }
        }
        return listOfExecution;
    }

    /**
     * {@inheritDoc}
     */
    public synchronized void processReplaceMessage(FIXPropertiesBean fixPropertiesBean) {
        FIXPropertiesBean fixPropertiesBean1 = (FIXPropertiesBean)GlobalStatus.getOrderBean(fixPropertiesBean.getOrigClordID());
        List<FIXPropertiesBean> fixPropertiesBeanList = null;

        if (fixPropertiesBean1 != null) {
            if (fixPropertiesBean1.isValidToAmendOrCancel()) {
                Map<String, String> map = GlobalStatus.getClOrdIdOverIsValidToProcess();
                if (map == null) {
                    map = new HashMap<>();
                }
                int count = i + 1;
                i++;
                if (fixPropertiesBean1.getOrigClordID() != null) {
                    map.put(fixPropertiesBean1.getOrigClordID(), "CHANGE" + count);
                    map.put(fixPropertiesBean1.getClordID(), "CHANGE" + count);
                } else {
                    map.put(fixPropertiesBean1.getClordID(), "CHANGE" + count);
                }
                GlobalStatus.setClOrdIdOverIsValidToProcess(map);
                fixPropertiesBean.setCumQty(fixPropertiesBean1.getCumQty());
                fixPropertiesBeanList = getTradingMessageFixPropertiesForDefaultExecutionForChangedOrder(fixPropertiesBean);
                for (FIXPropertiesBean i : fixPropertiesBeanList) {
                    try {
                        Thread.sleep(Long.parseLong(new ConfigSettings().getConfiguration("timeGap")));
                    } catch (InterruptedException e) {
                        logger.error("Thread interrupted error ", e);
                    }
                    if (("CHANGE" + count).equals(GlobalStatus.getClOrdIdOverIsValidToProcess().get(i.getOrigClordID()))) {
                        generateResponseFixMessage(i);
                        if (i.getOrdStatus() == '2') {
                            i.setValidToAmendOrCancel(false);
                        } else {
                            i.setValidToAmendOrCancel(true);
                        }
                        i = populateFromOriginal(i, i.getClordID());
                        GlobalStatus.addOpenOrder(i.getClordID(),i);
                    } else {
                        break;
                    }
                }
                GlobalStatus.removeOpenOrder(fixPropertiesBean.getClordID());
            } else {
                fixPropertiesBeanList = getAmendRejectFixPropertiesBeanList(fixPropertiesBean);
                for (FIXPropertiesBean i : fixPropertiesBeanList) {
                    try {
                        Thread.sleep(Long.parseLong(new ConfigSettings().getConfiguration("timeGap")));
                    } catch (InterruptedException e) {
                        logger.error("Thread interrupted error ", e);
                    }
                    generateResponseFixMessage(i);
                }
            }
        }
    }

    /**
     * get trading message FIX properties for default execution for changed order
     *
     * @param fixPropertiesBean
     * @return
     */
    private synchronized List<FIXPropertiesBean> getTradingMessageFixPropertiesForDefaultExecutionForChangedOrder(FIXPropertiesBean fixPropertiesBean) {
        List<FIXPropertiesBean> listOfExecution = new LinkedList<>();
        if (price == 0.0) {
            price = Double.parseDouble(new ConfigSettings().getConfiguration("marketOrderPrice"));
        }
        TradingSessions tradingSessions = null;

        if (tradingSessions == null) {
            tradingSessions = new TradingSessions();
            tradingSessions.setTradingSession(fixPropertiesBean.getSessionId());
            tradingSessions.setRemoteFirmId(fixPropertiesBean.getTargetCompID());
            tradingSessions.setLocalFirmId(fixPropertiesBean.getSenderCompID());
        }
        if (tradingSessions != null) {
            double orderQuantity = fixPropertiesBean.getOrderQty();
            if (blockSize == 0) {
                blockSize = Double.parseDouble(new ConfigSettings().getConfiguration("fillingQuantity"));
            }
            fixPropertiesBean.setOrdStatus(OrderStatus.PENDING_REPLACE.getCode());
            listOfExecution.add(populateOrderResponse(fixPropertiesBean, tradingSessions));
            if (orderQuantity - fixPropertiesBean.getCumQty() > blockSize) {
                fixPropertiesBean.setOrdStatus(OrderStatus.REPLACED.getCode());
                listOfExecution.add(populateOrderResponse(fixPropertiesBean, tradingSessions));
                boolean isValid = true;
                double fillQuantity = fixPropertiesBean.getCumQty();
                while (isValid) {
                    fillQuantity = fillQuantity + blockSize;
                    fixPropertiesBean.setOrdStatus(OrderStatus.PARTIALLY_FILLED.getCode());
                    fixPropertiesBean.setLeavesQty(orderQuantity - fillQuantity);
                    fixPropertiesBean.setLastShares(blockSize);
                    fixPropertiesBean.setCumQty(fillQuantity);
                    if (fixPropertiesBean.getOrdType() == '1') {
                        fixPropertiesBean.setAvgPx(price);
                        fixPropertiesBean.setPrice(price);
                        fixPropertiesBean.setLastPx(price);
                    } else {
                        fixPropertiesBean.setAvgPx(fixPropertiesBean.getPrice());
                        fixPropertiesBean.setPrice(fixPropertiesBean.getPrice());
                        fixPropertiesBean.setLastPx(fixPropertiesBean.getPrice());
                    }
                    listOfExecution.add(populateOrderResponse(fixPropertiesBean, tradingSessions));
                    if (orderQuantity - fillQuantity <= blockSize) {
                        isValid = false;
                        fixPropertiesBean.setCumQty(orderQuantity);
                        fixPropertiesBean.setLeavesQty(0);
                        fixPropertiesBean.setLastShares(orderQuantity - fillQuantity);
                        fixPropertiesBean.setOrdStatus(OrderStatus.FILLED.getCode());
                        fixPropertiesBean.setValidToAmendOrCancel(false);
                        if (fixPropertiesBean.getOrdType() == '1') {
                            fixPropertiesBean.setAvgPx(price);
                            fixPropertiesBean.setPrice(price);
                            fixPropertiesBean.setLastPx(price);
                        } else {
                            fixPropertiesBean.setAvgPx(fixPropertiesBean.getPrice());
                            fixPropertiesBean.setPrice(fixPropertiesBean.getPrice());
                            fixPropertiesBean.setLastPx(fixPropertiesBean.getPrice());
                        }
                        listOfExecution.add(populateOrderResponse(fixPropertiesBean, tradingSessions));
                    }
                }
            } else {
                fixPropertiesBean.setOrdStatus(OrderStatus.REPLACED.getCode());
                listOfExecution.add(populateOrderResponse(fixPropertiesBean, tradingSessions));
                fixPropertiesBean.setOrdStatus(OrderStatus.FILLED.getCode());
                fixPropertiesBean.setLeavesQty(0);
                fixPropertiesBean.setLastShares(orderQuantity);
                fixPropertiesBean.setCumQty(orderQuantity);
                fixPropertiesBean.setValidToAmendOrCancel(false);
                if (fixPropertiesBean.getOrdType() == '1') {
                    fixPropertiesBean.setAvgPx(price);
                    fixPropertiesBean.setPrice(price);
                    fixPropertiesBean.setLastPx(price);
                } else {
                    fixPropertiesBean.setAvgPx(fixPropertiesBean.getPrice());
                    fixPropertiesBean.setPrice(fixPropertiesBean.getPrice());
                    fixPropertiesBean.setLastPx(fixPropertiesBean.getPrice());
                }
                listOfExecution.add(populateOrderResponse(fixPropertiesBean, tradingSessions));
            }
        }
        return listOfExecution;
    }

    /**
     * get amend reject fix properties bean list
     *
     * @param fixPropertiesBean
     * @return
     */
    private synchronized List<FIXPropertiesBean> getAmendRejectFixPropertiesBeanList(FIXPropertiesBean fixPropertiesBean) {
        List<FIXPropertiesBean> listOfExecution = new LinkedList<>();
        TradingSessions tradingSessions = null;

        if (tradingSessions == null) {
            tradingSessions = new TradingSessions();
            tradingSessions.setTradingSession(fixPropertiesBean.getSessionId());
            tradingSessions.setRemoteFirmId(fixPropertiesBean.getTargetCompID());
            tradingSessions.setLocalFirmId(fixPropertiesBean.getSenderCompID());
        }
        fixPropertiesBean.setOrdStatus(OrderStatus.PENDING_REPLACE.getCode());
        listOfExecution.add(populateOrderResponse(fixPropertiesBean, tradingSessions));
        fixPropertiesBean.setOrdStatus(OrderStatus.AMEND_REJECTED.getCode());
        fixPropertiesBean.setCxlRejResponseTo('2');
        fixPropertiesBean.setText("Original Order is already Filled,Expired or Cancelled");
        listOfExecution.add(populateOrderResponse(fixPropertiesBean, tradingSessions));

        return listOfExecution;
    }


    /**
     * {@inheritDoc}
     */
    public synchronized void processCancelMessage(FIXPropertiesBean fixPropertiesBean) {
        FIXPropertiesBean fixPropertiesBean1 = (FIXPropertiesBean)GlobalStatus.getOrderBean(fixPropertiesBean.getOrigClordID());
        List<FIXPropertiesBean> fixPropertiesBeanList;

        if (fixPropertiesBean1 != null) {
            if (fixPropertiesBean1.isValidToAmendOrCancel()) {
                Map<String, String> map = GlobalStatus.getClOrdIdOverIsValidToProcess();
                if (map == null) {
                    map = new HashMap<>();
                }
                if (fixPropertiesBean1.getOrigClordID() != null) {
                    map.put(fixPropertiesBean1.getOrigClordID(), "CANCEL");
                    map.put(fixPropertiesBean1.getClordID(), "CANCEL");
                } else {
                    map.put(fixPropertiesBean1.getClordID(), "CANCEL");
                }
                GlobalStatus.setClOrdIdOverIsValidToProcess(map);
                fixPropertiesBean.setCumQty(fixPropertiesBean1.getCumQty());
                fixPropertiesBean.setOrdType(fixPropertiesBean1.getOrdType());
                fixPropertiesBeanList = getTradingMessageFixPropertiesForDefaultExecutionForCancelOrder(fixPropertiesBean);
                for (FIXPropertiesBean i : fixPropertiesBeanList) {
                    try {
                        Thread.sleep(Long.parseLong(new ConfigSettings().getConfiguration("timeGap")));
                    } catch (InterruptedException e) {
                        logger.error("Thread interrupted error ", e);
                    }
                    if ("CANCEL".equals(GlobalStatus.getClOrdIdOverIsValidToProcess().get(i.getOrigClordID()))) {
                        generateResponseFixMessage(i);

                    } else {
                        break;
                    }
                    GlobalStatus.addOpenOrder(i.getClordID(),i);
                }

            } else {
                fixPropertiesBeanList = getCancelRejectFixPropertiesBeanList(fixPropertiesBean);
                for (FIXPropertiesBean i : fixPropertiesBeanList) {
                    try {
                        Thread.sleep(Long.parseLong(new ConfigSettings().getConfiguration("timeGap")));
                    } catch (InterruptedException e) {
                        logger.error("Thread interrupted error ", e);
                    }
                    generateResponseFixMessage(i);
                    GlobalStatus.addOpenOrder(i.getClordID(),i);
                }
            }
        }
    }

    /**
     * get trading message FIX properties for default execution for cancel order
     *
     * @param fixPropertiesBean
     * @return
     */
    private synchronized List<FIXPropertiesBean> getTradingMessageFixPropertiesForDefaultExecutionForCancelOrder(FIXPropertiesBean fixPropertiesBean) {
        List<FIXPropertiesBean> listOfExecution = new LinkedList<>();
        TradingSessions tradingSessions = null;

        if (tradingSessions == null) {
            tradingSessions = new TradingSessions();
            tradingSessions.setTradingSession(fixPropertiesBean.getSessionId());
            tradingSessions.setRemoteFirmId(fixPropertiesBean.getTargetCompID());
            tradingSessions.setLocalFirmId(fixPropertiesBean.getSenderCompID());
        }
        if (tradingSessions != null) {
            fixPropertiesBean.setOrdStatus(OrderStatus.PENDING_CANCEL.getCode());
            listOfExecution.add(populateOrderResponse(fixPropertiesBean, tradingSessions));
            fixPropertiesBean.setOrdStatus(OrderStatus.CANCELED.getCode());
            fixPropertiesBean.setValidToAmendOrCancel(false);
            listOfExecution.add(populateOrderResponse(fixPropertiesBean, tradingSessions));
        }
        return listOfExecution;
    }

    /**
     * get cancel reject FIX properties bean list
     *
     * @param fixPropertiesBean
     * @return
     */
    private synchronized List<FIXPropertiesBean> getCancelRejectFixPropertiesBeanList(FIXPropertiesBean fixPropertiesBean) {
        List<FIXPropertiesBean> listOfExecution = new LinkedList<>();
        TradingSessions tradingSessions = null;

        if (tradingSessions == null) {
            tradingSessions = new TradingSessions();
            tradingSessions.setTradingSession(fixPropertiesBean.getSessionId());
            tradingSessions.setRemoteFirmId(fixPropertiesBean.getTargetCompID());
            tradingSessions.setLocalFirmId(fixPropertiesBean.getSenderCompID());
        }
        fixPropertiesBean.setOrdStatus(OrderStatus.PENDING_CANCEL.getCode());
        listOfExecution.add(populateOrderResponse(fixPropertiesBean, tradingSessions));
        fixPropertiesBean.setOrdStatus(OrderStatus.CANCEL_REJECTED.getCode());
        fixPropertiesBean.setText("Original Order is already Filled,Expired or Cancelled");
        fixPropertiesBean.setCxlRejResponseTo('1');
        listOfExecution.add(populateOrderResponse(fixPropertiesBean, tradingSessions));

        return listOfExecution;
    }

    /**
     * populate fix properties bean from initial fix message
     *
     * @param updated
     * @param clOrdId
     * @return FIXPropertiesBean
     */
    public FIXPropertiesBean populateFromOriginal(FIXPropertiesBean updated, String clOrdId) {
        FIXPropertiesBean fixPropertiesBean = null;

        if (fixPropertiesBean != null) {
            fixPropertiesBean.setOrdStatus(updated.getOrdStatus());
            fixPropertiesBean.setLeavesQty(updated.getLeavesQty());
            fixPropertiesBean.setLastShares(updated.getLastShares());
            fixPropertiesBean.setCumQty(updated.getCumQty());
            fixPropertiesBean.setAvgPx(updated.getAvgPx());
            fixPropertiesBean.setPrice(updated.getPrice());
            fixPropertiesBean.setLastPx(updated.getLastPx());
            fixPropertiesBean.setValidToAmendOrCancel(updated.isValidToAmendOrCancel());
        }

        return fixPropertiesBean;
    }

    /**
     * This method is used to find the message type of the FIX messages
     *
     * @param sFixMessage
     * @return fix message type
     */
    private char getFIXMessageType(String sFixMessage) {
        String sType = "X";
        String sToken;
        String sTag;
        StringTokenizer st;

        st = new StringTokenizer(sFixMessage, FIELD_SEPERATOR);

        while (st.hasMoreTokens()) {
            sToken = st.nextToken();
            sToken = sToken.trim();
            dataSeparator.setData(sToken);
            sTag = dataSeparator.getTag();
            if (sTag != null && sTag.equals(Integer.toString(MSG_TYPE))) {
                sType = dataSeparator.getData();
                break;
            } else if (sTag != null && sTag.equals(Integer.toString(APPIA_MSG_ID))) {
                sType = dataSeparator.getData();
                break;
            }
        }
        return sType.charAt(0);
    }

    /**
     * set the data related to market status request
     *
     * @param eventData
     * @return FIXPropertiesBean
     */
    private FIXPropertiesBean getMarketStatusMessage(String eventData) {
        FIXPropertiesBean sessionBean = new FIXPropertiesBean();
        String sToken;
        String sTag;
        int iTag;
        String value;
        StringTokenizer st;

        st = new StringTokenizer(eventData, FIELD_SEPERATOR);

        while (st.hasMoreTokens()) {
            sToken = st.nextToken();
            dataSeparator.setData(sToken);
            sTag = dataSeparator.getTag();
            if (sTag == null) {
                continue;
            }
            try {
                iTag = Integer.parseInt(sTag);
            } catch (NumberFormatException e) {
                logger.error(e.getMessage(), e);
                continue;
            }
            value = dataSeparator.getData();

            switch (iTag) {
                case MSG_TYPE:
                    sessionBean.setMessageType(value.charAt(0));
                    break;
                case TRAD_SESSION_STATUS:
                    sessionBean.setTradSesStatus(Integer.parseInt(value));
                    break;
                case TRAD_SES_ID:
                    sessionBean.setTradingSessionID(value);
                    break;
                case SENDER_COMP_ID:
                    sessionBean.setSenderCompID(value);
                    break;
                case SENDING_TIME:
                    sessionBean.setSendingTime(formatDate(value));
                    break;
                case TARGET_COMP_ID:
                    sessionBean.setTargetCompID(value);
                    break;
                case TRADING_SESSION_REQUEST_ID:
                    sessionBean.setTradSesReqID(value);
                    break;
                default:
                    break;
            }
        }

        return sessionBean;
    }

    /**
     * get FIXPropertiesBean from received fix message
     *
     * @param eventData
     * @return FIXPropertiesBean
     */
    private FIXPropertiesBean getTradingMessage(String eventData) {
        FIXPropertiesBean tradingMessage = new FIXPropertiesBean();
        String sToken;
        String sTag;
        int iTag;
        String value;
        StringTokenizer st;

        st = new StringTokenizer(eventData, FIELD_SEPERATOR);

        while (st.hasMoreTokens()) {
            sToken = st.nextToken();
            dataSeparator.setData(sToken);
            sTag = dataSeparator.getTag();
            if (sTag == null) {
                continue;
            }
            try {
                iTag = Integer.parseInt(sTag);
            } catch (NumberFormatException e) {
                logger.error(e.getMessage(), e);
                continue;
            }
            value = dataSeparator.getData();

            switch (iTag) {
                case MSG_TYPE:
                    tradingMessage.setMessageType(value.charAt(0));
                    break;
                case CLORD_ID:
                    tradingMessage.setClordID(value);
                    break;
                case ACCOUNT:
                    tradingMessage.setPortfolioNo(value);
                    break;
                case SENDER_COMP_ID:
                    tradingMessage.setSenderCompID(value);
                    break;
                case SENDING_TIME:
                    tradingMessage.setSendingTime(formatDate(value));
                    break;
                case TARGET_COMP_ID:
                    tradingMessage.setTargetCompID(value);
                    break;
                case ORDER_QUANTITY:
                    if (isNotNull(value)) {
                        tradingMessage.setOrderQty(Double.parseDouble(value));
                    }
                    break;
                case SENDER_SUB_ID:
                    tradingMessage.setSenderSubID(value);
                    break;
                case SENDER_LOCATION_ID:
                    tradingMessage.setSenderLocationID(value);
                    break;
                case SYMBOL:
                    tradingMessage.setSymbol(value);
                    break;
                case SIDE:
                    tradingMessage.setSide(value.charAt(0));
                    break;
                case TRANSACTION_TIME:
                    tradingMessage.setTransactTime(value);
                    break;
                case HANDLE_INST:
                    if (isNotNull(value)) {
                        tradingMessage.setHandlInst(Integer.parseInt(value));
                    }
                    break;
                case TRAD_SES_ID:
                    tradingMessage.setTradingSessionID(value);
                    break;
                case ORD_TYPE:
                    tradingMessage.setOrdType(value.charAt(0));
                    break;
                case TIME_IN_FORCE:
                    if (isNotNull(value)) {
                        tradingMessage.setTimeInForce(Integer.parseInt(value));
                    }
                    break;
                case EXPIRE_TIME:
                    tradingMessage.setExpireTime(formatDate(value));
                    break;
                case SECURITY_EXCHANGE:
                    tradingMessage.setSecurityExchange(value);
                    break;
                case ORIG_CLORD_ID:
                    tradingMessage.setOrigClordID(value);
                    break;
                case PRICE:
                    if (isNotNull(value)) {
                        tradingMessage.setPrice(Double.parseDouble(value));
                    }
                    break;
                case MIN_QTY:
                    if (isNotNull(value)) {
                        tradingMessage.setMinQty(Double.parseDouble(value));
                    }
                    break;
                case MAX_FLOOR:
                    if (isNotNull(value)) {
                        tradingMessage.setMaxFloor(Double.parseDouble(value));
                    }
                    break;
                default:
                    break;
            }
        }

        return tradingMessage;
    }


    /**
     * date formatter
     *
     * @param dateValue
     * @return formatted Date
     */
    private Date formatDate(String dateValue) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMdd-HH:mm:ss").parse(dateValue);
        } catch (ParseException e) {
            logger.error("Invalid date format");
        }
        return date;
    }

    /**
     * use to null check of some properties when crating trading response
     *
     * @param value
     * @return
     */
    private boolean isNotNull(String value) {
        return value != null;
    }

    /**
     * This method will call by JVM  when execute the future task
     *
     * @return
     * @throws Exception
     */
    @Override
    public Object call() throws Exception {
        processRequestMessageReadFromToAppia(message);
        return null;
    }

    /**
     * populate order response from fix properties bean and trading session bean
     *
     * @param fixPropertiesBean
     * @param tradingSessions
     * @return
     */
    private synchronized FIXPropertiesBean populateOrderResponse(FIXPropertiesBean fixPropertiesBean, TradingSessions tradingSessions) {
        FIXPropertiesBean fixPropertiesBean1 =new FIXPropertiesBean();
        if (fixPropertiesBean == null || tradingSessions == null) {
            logger.error("Input parameters can not be null", fixPropertiesBean, tradingSessions);
            return null;
        }

        if (fixPropertiesBean1 != null) {
            if ((fixPropertiesBean.getOrdStatus() == OrderStatus.AMEND_REJECTED.getCode()) || (fixPropertiesBean.getOrdStatus() == OrderStatus.CANCEL_REJECTED.getCode())) {
                fixPropertiesBean1.setMessageType('9');
                fixPropertiesBean1.setCxlRejResponseTo(fixPropertiesBean.getCxlRejResponseTo());
                fixPropertiesBean.setOrdStatus(OrderStatus.REJECTED.getCode());
                fixPropertiesBean1.setText("Original Order is already Filled,Expired or Cancelled");
            } else {
                fixPropertiesBean1.setMessageType('8');
            }
            fixPropertiesBean1.setTargetCompID(fixPropertiesBean.getSenderSubID());
            fixPropertiesBean1.setSenderCompID(tradingSessions.getRemoteFirmId());
            fixPropertiesBean1.setTargetCompID(tradingSessions.getLocalFirmId());
            fixPropertiesBean1.setSendingTime(getFormattedDate(new Date()));
            fixPropertiesBean1.setLastShares(fixPropertiesBean.getLastShares());
            fixPropertiesBean1.setOrderQty(fixPropertiesBean.getOrderQty());
            fixPropertiesBean1.setOrdStatus(fixPropertiesBean.getOrdStatus());
            fixPropertiesBean1.setAvgPx(fixPropertiesBean.getAvgPx());
            orderID = new Date().getTime() - 1000;
            fixPropertiesBean1.setOrderID(String.valueOf(orderID));
            fixPropertiesBean1.setOrdType(fixPropertiesBean.getOrdType());
            fixPropertiesBean1.setClordID(fixPropertiesBean.getClordID());
            fixPropertiesBean1.setOrigClordID(fixPropertiesBean.getOrigClordID());
            fixPropertiesBean1.setCumQty(fixPropertiesBean.getCumQty());
            executionID = new Date().getTime()+constant;
            constant++;
            fixPropertiesBean1.setExecID(String.valueOf(executionID));
            fixPropertiesBean1.setSymbol(fixPropertiesBean.getSymbol());
            fixPropertiesBean1.setHandlInst(fixPropertiesBean.getHandlInst());
            fixPropertiesBean1.setSide(fixPropertiesBean.getSide());
            fixPropertiesBean1.setExecTransType(0);
            fixPropertiesBean1.setExecType('O');
            fixPropertiesBean1.setPrice(fixPropertiesBean.getPrice());
            fixPropertiesBean1.setLastPx(fixPropertiesBean.getLastPx());
            fixPropertiesBean1.setTransactTime(getFormattedDate(new Date()).toString());
            fixPropertiesBean1.setLeavesQty(fixPropertiesBean.getLeavesQty());
            fixPropertiesBean1.setOriginalMessageType(fixPropertiesBean.getOriginalMessageType());
        }
        return fixPropertiesBean1;

    }





    /**
     * use to format date
     *
     * @param date
     * @return
     */
    private static Date getFormattedDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HH:mm:ss");
        try {
            return dateFormat.parse(dateFormat.format(date));
        } catch (ParseException e) {
            logger.error(date + " is not passable");
            return null;
        }
    }


}
