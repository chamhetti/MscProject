package lk.ac.ucsc.oms.trs_writer.implGeneral;

import lk.ac.ucsc.oms.common_utility.api.MessageSender;
import lk.ac.ucsc.oms.common_utility.api.enums.Delimeter;
import lk.ac.ucsc.oms.common_utility.api.exceptions.MessageSenderException;
import lk.ac.ucsc.oms.customer.api.beans.cash.CashAccount;
import lk.ac.ucsc.oms.customer.api.beans.cash.CashLog;
import lk.ac.ucsc.oms.customer.api.beans.customer.Customer;
import lk.ac.ucsc.oms.customer.api.beans.holding.HoldingLog;
import lk.ac.ucsc.oms.messaging_protocol_json.api.GroupConstants;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import lk.ac.ucsc.oms.trs_writer.api.TrsWriterFacade;
import lk.ac.ucsc.oms.trs_writer.api.TrsWriterFactory;
import lk.ac.ucsc.oms.trs_writer.api.exceptions.TrsWriterException;
import lk.ac.ucsc.oms.trs_writer.implGeneral.helpers.InquiryResponseHelper;
import lk.ac.ucsc.oms.trs_writer.implGeneral.helpers.TradeResponseHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TrsWriterFacadeImpl implements TrsWriterFacade {
    private static Logger logger = LogManager.getLogger(TrsWriterFacadeImpl.class);
    private ExecutorService threadPool = Executors.newCachedThreadPool();

    private TradeResponseHelper tradeResponseHelper;
    private InquiryResponseHelper inquiryResponseHelper;
    private MessageSender messageSender;
    private static TrsWriterFactory trsWriterFactory;

    private static final String AUTH_RESPONSE_TOPIC = "LoginResponse";
    private static final String TRADE_RESPONSE_TOPIC = "TradeResponse";
    private static final String INQUIRY_RESPONSE_TOPIC = "InquiryResponse";
    private static final String ADMIN_RESPONSE_TOPIC = "AdminResponse";
    private static final String COMBINED_RESPONSE_TOPIC = "CombinedResponse";


    /**
     * {@inheritDoc}
     */
    @Override
    public void writeResponse(int group, final String response) throws TrsWriterException {
        switch (group) {
            case GroupConstants.GROUP_AUTHENTICATION:
                writeAuthResponse(response);
                break;
            case GroupConstants.GROUP_TRADING:
                writeOtherTradeResponse(response);
                break;
            case GroupConstants.GROUP_INQUIRY:
            case GroupConstants.GROUP_CUSTOMER_INQUIRY:
            case GroupConstants.GROUP_TRADING_INQUIRY:
                writeInquiryResponse(response);
                break;
            case GroupConstants.GROUP_SYSTEM:
                writeSystemResponse(response);
                break;
            default:
                logger.error("Could not recognize Message group");
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeAuthResponse(final String authResponse) throws TrsWriterException {
        try {
            messageSender.publishMessage(AUTH_RESPONSE_TOPIC, authResponse);
        } catch (MessageSenderException e) {
            logger.error("Error publishing auth response message", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeOtherTradeResponse(final String tradeResponse) throws TrsWriterException {
        try {
            messageSender.publishMessage(TRADE_RESPONSE_TOPIC, tradeResponse);
        } catch (MessageSenderException e) {
            logger.error("Error publishing trade response message", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeSystemResponse(final String combinedResponse) {
        try {
            messageSender.publishMessage(COMBINED_RESPONSE_TOPIC, combinedResponse);
        } catch (MessageSenderException e) {
            logger.error("Error publishing combined response message", e);
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void writeNormalOrderResponse(final Order order, final List<String> allowedUserList, String customerNumber, String customerName, String execBrokerSid) {
        //populate the mix object for the trade response
        final String mixString = tradeResponseHelper.populateNormalOrderResponse(order, customerNumber, customerName, execBrokerSid);
        logger.debug("New Order response to the client - {}", mixString);
        try {
            Map mapMessage = getMapMessage(mixString, allowedUserList.toArray(new String[allowedUserList.size()]));
            messageSender.publishMessage(TRADE_RESPONSE_TOPIC, mapMessage);
        } catch (MessageSenderException e) {
            logger.error("Error publishing trade response message: {}", e);
        }
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                if (trsWriterFactory == null) {
                    trsWriterFactory = TrsWriterFactory.getInstance();
                }

            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeInvalidOrderResponse(String msgSessionID, int channel, int errorCode, String uniqueId) {
        try {
            final String response = tradeResponseHelper.populateInvalidOrderResponse(msgSessionID, channel, errorCode, uniqueId);
            writeOtherTradeResponse(response);
        } catch (TrsWriterException e) {
            logger.error("Error publishing trade response message: ", e);
        }
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public void writeAdminMsgResponse(final String adminMessage) {
        try {
            messageSender.publishMessage(ADMIN_RESPONSE_TOPIC, adminMessage);
        } catch (MessageSenderException e) {
            logger.error("Error publishing admin response message: {}", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeInquiryResponse(final String inquiryResponse) {
        logger.debug("Send Inquiry Response to Response Topic");
        logger.debug("Written message to the response Topic Inquiry - {}", inquiryResponse);
        try {
            messageSender.publishMessage(INQUIRY_RESPONSE_TOPIC, inquiryResponse);
        } catch (MessageSenderException e) {
            logger.error("Error publishing inquiry response message: ", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeAccountSummaryResponse(CashAccount cashAccount, Customer customer, String secAccNo, double buyingPower, double portfolioValue) {
        //populate the mix object for the account summary response
        final String mixString = inquiryResponseHelper.populateAccountSummaryResponse(cashAccount, customer, secAccNo, buyingPower, portfolioValue);
        logger.debug("Account summary response to the client - {} ",mixString);
        try {
            messageSender.publishMessage(INQUIRY_RESPONSE_TOPIC, mixString);
        } catch (MessageSenderException e) {
            logger.error("Error publishing inquiry response message: {}", e);
        }
    }








    public void setInquiryResponseHelper(InquiryResponseHelper inquiryResponseHelper) {
        this.inquiryResponseHelper = inquiryResponseHelper;
    }

    public InquiryResponseHelper getInquiryResponseHelper() {
        return inquiryResponseHelper;
    }

    public void setTradeResponseHelper(TradeResponseHelper tradeResponseHelper) {
        this.tradeResponseHelper = tradeResponseHelper;
    }

    public void setMessageSender(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    /**
     * Get the map message
     *
     * @param message is the message to be sent
     * @param users   set of intended receivers
     * @return
     */
    private Map getMapMessage(String message, String[] users) {
        Map<String, String> mapMessage = new HashMap<>();

        mapMessage.put("MESSAGE", message);
        mapMessage.put("USERS", getArrayAsString(users));
        return mapMessage;
    }

    /**
     * Convert string array to String object to support MapMessage since it does not support collection or arrays
     *
     * @param strings
     * @return
     */
    private String getArrayAsString(String[] strings) {
        StringBuilder output = new StringBuilder();
        for (String s : strings) {
            output.append(s).append(Delimeter.COMMA_SEPARATOR.getCode());
        }
        return output.toString();
    }

    /**
     * Setting threadPool
     * Use only for unit testing
     *
     * @param threadPool
     */
    public void setThreadPool(ExecutorService threadPool) {
        this.threadPool = threadPool;
    }

    /**
     * Setting trsWriterFactory
     * Use only for unit testing
     *
     * @param trsWriterFactory
     */
    public static void setTrsWriterFactory(TrsWriterFactory trsWriterFactory) {
        TrsWriterFacadeImpl.trsWriterFactory = trsWriterFactory;
    }
}
