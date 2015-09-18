package lk.ac.ucsc.clientConnector.responseProcessor;

import lk.ac.ucsc.oms.messaging_protocol_json.api.MessageConstants;
import lk.ac.ucsc.clientConnector.api.Client;
import lk.ac.ucsc.clientConnector.api.Converter;
import lk.ac.ucsc.clientConnector.client.AbstractClient;
import lk.ac.ucsc.clientConnector.client.UserManager;
import lk.ac.ucsc.clientConnector.common.api.ProcessingStatus;
import lk.ac.ucsc.clientConnector.common.api.TrsMessage;
import lk.ac.ucsc.clientConnector.session_manager.api.SessionManagerException;
import lk.ac.ucsc.clientConnector.session_manager.api.SessionManagerFacade;
import lk.ac.ucsc.clientConnector.session_manager.api.SessionManagerFactory;
import lk.ac.ucsc.clientConnector.session_manager.api.beans.UserSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Date;

public class InquiryResponseProcessor extends AbstractResponseProcessor {
    private static Logger logger = LoggerFactory.getLogger(InquiryResponseProcessor.class);
    private SessionManagerFacade sessionManagerFacade;

    public InquiryResponseProcessor(Converter converter) {
        this.converter = converter;
    }

    @Override
    public void processMessage(String response) {

        logger.info("Inquiry response received from OMS");
        TrsMessage trsMessage = converter.convertToTrsMessage(response);
        MDC.put("tid", "tid-res" + trsMessage.getUniqueReqId());
        if (trsMessage.getProcessingStatus() != ProcessingStatus.VALID) {
            logger.error("Invalid inquiry response: {}. Discarded", response);
            return;
        }
        int msgGroup = trsMessage.getGroup();
        int responseType = trsMessage.getReqType();
        String userID = trsMessage.getUserId(); // oms sets the logged in user id field at header during authentication
        logger.info("Message Group: " + msgGroup + " Response Type: " + responseType + " User ID: " + userID);

        String sessionId = trsMessage.getMsgSessionId();
        if (sessionId == null) {
            logger.error("Session id is null. Message is discarded: ", response);
            return;
        } else if ("UNSOLICITED".equals(sessionId)) {
            logger.info("Processing unsolicited inquiry response");
            trsMessage.setUnsolicited(true);
            // push response to comet server
            try {
                UserSession session = getSessionManagerFacade().getUserSessionByUserID(userID);
                if (session != null) {
                    sessionId = session.getSessionID();
                    trsMessage.setMsgSessionId(sessionId);
                }
            } catch (SessionManagerException e) {
                logger.info("Could not find session id to send unsolicited inquiry response for client: " + userID);
                return;
            }
        }

        try {
            Client user = getUser(sessionId);
            if (user == null) {
                logger.info("Could not find client to send inquiry response. " +
                        "Client may be already disconnected. Message discarded SessionId: " + sessionId);
                trsMessage.setTimeStamp(new Date());
                trsMessage.setProcessingStatus(ProcessingStatus.OMS_RESPONSE_RECEIVED);
                trsMessage.setInvalidReason("Client not found. Disconnected?");

                return;
            }
            if (trsMessage.isUnsolicited()) {
                trsMessage.setClientType(((AbstractClient) user).getClientType());
                trsMessage.setChannelId(((AbstractClient) user).getChannelId());
            }
            switch (msgGroup) {
                case MessageConstants.GROUP_INQUIRY:
                case MessageConstants.GROUP_CUSTOMER_INQUIRY:
                case MessageConstants.GROUP_TRADING_INQUIRY:
                    logger.debug("Sending to client --> " + trsMessage.toStringMetaData());
                    sendResponse(user, trsMessage, response);
                    break;
                default:
                    logger.error("Unexpected response received, was expecting an inquiry response. Message will be discarded: \n" + response);
                    break;
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        MDC.remove("tid");
    }

    @Override
    public void processMessage(String message, String[] users) {
        logger.info("Inquiry response received from OMS to be pushed to {} users", users.length);
        TrsMessage trsMessage = converter.convertToTrsMessage(message);
        MDC.put("tid", "tid-res" + trsMessage.getUniqueReqId());
        if (trsMessage.getProcessingStatus() != ProcessingStatus.VALID) {
            logger.error("Invalid login response: {}. Discarded", message);
            return;
        }

        int msgGroup = trsMessage.getGroup();
        int responseType = trsMessage.getReqType();
        String userID = trsMessage.getUserId(); // oms set the logged in user id field in the mix message
        logger.info("Message Group: " + msgGroup + " Response Type: " + responseType + " User ID: " + userID);

        if ("UNSOLICITED".equals(trsMessage.getMsgSessionId())) {
            trsMessage.setUnsolicited(true);
        }

        for (String userId : users) {
            Client user = getUserByUserId(userId);
            if (user != null) {
                AbstractClient abstractClient = (AbstractClient) user;
                logger.info("Sending inquiry response to client -> " + userId + " , Type : " + abstractClient.getClientType());
                trsMessage.setMsgSessionId(abstractClient.getSessionID());
                trsMessage.setClientType(abstractClient.getClientType());
                trsMessage.setChannelId(abstractClient.getChannelId());
                trsMessage.setUserId(userId);
                sendResponse(user, trsMessage, message);
            }
        }
        MDC.remove("tid");

    }

    public SessionManagerFacade getSessionManagerFacade() {
        if (sessionManagerFacade != null) {
            return sessionManagerFacade;
        } else {
            return SessionManagerFactory.getSessionManager();
        }
    }

    public void setSessionManagerFacade(SessionManagerFacade sessionManagerFacade) {
        this.sessionManagerFacade = sessionManagerFacade;
    }

    /**
     * @param sessionID
     * @return Client
     */
    public Client getUser(String sessionID) {
        return UserManager.getInstance().getClientBySessionId(sessionID);
    }

    public Client getUserByUserId(String userId) {
        return UserManager.getInstance().getClientByUserId(userId);
    }
}
