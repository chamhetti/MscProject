package lk.ac.ucsc.clientConnector.responseProcessor;

import lk.ac.ucsc.oms.messaging_protocol_json.api.MessageConstants;
import lk.ac.ucsc.clientConnector.api.Client;
import lk.ac.ucsc.clientConnector.api.Converter;
import lk.ac.ucsc.clientConnector.client.AbstractClient;
import lk.ac.ucsc.clientConnector.client.UserManager;
import lk.ac.ucsc.clientConnector.common.api.ProcessingStatus;
import lk.ac.ucsc.clientConnector.common.api.TrsMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Date;


public class TradeResponseProcessor extends AbstractResponseProcessor {
    private static Logger logger = LoggerFactory.getLogger(TradeResponseProcessor.class);

    public TradeResponseProcessor(Converter converter) {
        this.converter = converter;
    }

    @Override
    public void processMessage(String response, String[] users) {
        logger.info("Trade response received from OMS: {}", response);
        TrsMessage trsMessage = converter.convertToTrsMessage(response);
        MDC.put("tid", "tid-res" + trsMessage.getUniqueReqId());
        if (trsMessage.getProcessingStatus() != ProcessingStatus.VALID) {
            logger.error("Invalid login response: {}. Discarded", response);
            return;
        }

        int msgGroup = trsMessage.getGroup();
        int responseType = trsMessage.getReqType();
        String userID = trsMessage.getUserId(); // oms set the logged in user id field in the mix message
        logger.info("Message Group: " + msgGroup + " Response Type: " + responseType + " User ID: " + userID);

        if (msgGroup != MessageConstants.GROUP_TRADING) {
            logger.warn("Invalid response received for Trade ResponseProcessor, will be ignored");
            return;
        }

        if ("UNSOLICITED".equals(trsMessage.getMsgSessionId())) {
            trsMessage.setUnsolicited(true);
        }

        for (String userId : users) {
            Client user = getUserByUserId(userId);
            if (user != null) {
                AbstractClient abstractClient = (AbstractClient) user;
                logger.info("Sending trade response to client -> " + userId + " , Type : " + abstractClient.getClientType());
                trsMessage.setMsgSessionId(abstractClient.getSessionID());
                trsMessage.setClientType(abstractClient.getClientType());
                trsMessage.setChannelId(abstractClient.getChannelId());
                trsMessage.setUserId(userId);
                sendResponse(user, trsMessage, response);
            }
        }

        MDC.remove("tid");

    }

    @Override
    public void processMessage(String response) {
        // this method sends other trade responses such as IOM
        logger.info("Trade response received from OMS");
        TrsMessage trsMessage = converter.convertToTrsMessage(response);
        MDC.put("tid", "tid-res" + trsMessage.getUniqueReqId());
        if (trsMessage.getProcessingStatus() != ProcessingStatus.VALID) {
            logger.error("Invalid login response: {}. Discarded", response);
            return;
        }

        int msgGroup = trsMessage.getGroup();
        int responseType = trsMessage.getReqType();
        String userID = trsMessage.getUserId(); // oms set the logged in user id field in the mix message
        logger.info("Message Group: " + msgGroup + " Response Type: " + responseType + " User ID: " + userID);

        if (msgGroup != MessageConstants.GROUP_TRADING) {
            logger.warn("Invalid response received for Trade ResponseProcessor, will be ignored");
            return;
        }

        String sessionID = trsMessage.getMsgSessionId();
        Client user = getUser(sessionID);
        if (user == null) {
            logger.warn("Could not find client to send trade response. Client may be already disconnected. SessionId: " + sessionID);
            trsMessage.setTimeStamp(new Date());
            trsMessage.setProcessingStatus(ProcessingStatus.OMS_RESPONSE_RECEIVED);
            trsMessage.setInvalidReason("Client not found. Disconnected?");

            return;
        }
        sendResponse(user, trsMessage, response);
        MDC.remove("tid");
    }

    /**
     * @param sessionID
     * @return Client
     */
    public Client getUser(String sessionID) {
        return UserManager.getInstance().getClientBySessionId(sessionID);
    }  /**
     * @param userId
     * @return Client
     */
    public Client getUserByUserId(String userId) {
        return UserManager.getInstance().getClientByUserId(userId);
    }
}
