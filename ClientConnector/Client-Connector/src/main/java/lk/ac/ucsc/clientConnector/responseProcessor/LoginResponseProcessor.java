package lk.ac.ucsc.clientConnector.responseProcessor;

import lk.ac.ucsc.oms.messaging_protocol_json.api.MessageConstants;
import lk.ac.ucsc.clientConnector.api.Client;
import lk.ac.ucsc.clientConnector.api.Converter;
import lk.ac.ucsc.clientConnector.client.AbstractClient;
import lk.ac.ucsc.clientConnector.client.UserManager;
import lk.ac.ucsc.clientConnector.common.api.ProcessingStatus;
import lk.ac.ucsc.clientConnector.common.api.TrsMessage;
import lk.ac.ucsc.clientConnector.exceptions.ClientConnectorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Map;

public class LoginResponseProcessor extends AbstractResponseProcessor {
    private static Logger logger = LoggerFactory.getLogger(LoginResponseProcessor.class);

    public LoginResponseProcessor(Converter converter) {
        this.converter = converter;
    }

    @Override
    public void processMessage(String response) {
        logger.info("Auth Response Received from OMS");

        TrsMessage trsMessage = converter.convertToTrsMessage(response);
        MDC.put("tid", "tid-res" + trsMessage.getUniqueReqId());
        if (trsMessage.getProcessingStatus() != ProcessingStatus.VALID) {
            logger.error("Invalid login response: {}. Discarded", response);
            return;
        }
        String sessionID = trsMessage.getMsgSessionId();

        AbstractClient user = (AbstractClient) getUser(sessionID);
        if (user == null) {
            logger.warn("Error finding client to send auth response. Client may have disconnected. " +
                    "Message is discarded. SessionId: " + sessionID);
            return;
        }
        logger.info("Processing auth response {}", trsMessage.toStringMetaData());
        processAuthResponse(response, trsMessage, user, sessionID);
        logger.info("Finished processing auth response {}", trsMessage.toStringMetaData());
        MDC.remove("tid");
    }

    @Override
    public void processMessage(String message, String[] users) {
        // not applicable for login responses
    }

    private void processAuthResponse(String response, TrsMessage trsMessage, AbstractClient user, String sessionID) {
        int msgGroup = trsMessage.getGroup();
        int responseType = trsMessage.getReqType();
        if (msgGroup == MessageConstants.GROUP_AUTHENTICATION) {
            logger.info("Message Group: " + msgGroup + " Response Type: " + responseType + " Session ID: " + sessionID);
            switch (responseType) {
                case MessageConstants.RESPONSE_TYPE_AUTH_NORMAL:
                    logger.info("Processing login response {}", trsMessage.toStringMetaData());
                    processAuthResponse(response, trsMessage, user);
                    break;


                case MessageConstants.RESPONSE_TYPE_CHANG_PWD:
                    logger.info("Sending change password response {}", trsMessage.toStringMetaData());
                    sendResponse(user, trsMessage, response);
                    // do we need to invalidate session if account is locked?
                    break;


                case MessageConstants.RESPONSE_TYPE_LOGOUT:

                    logger.info("Logging out client: {}", user.getClientAddress());
                    sendResponse(user, trsMessage, response);
                    user.close("Logged Out", false);    // user will be removed from the cache during close()
                    break;

                default:
                    logger.error("Unrecognized response type. Discarding message" + response);
                    break;
            }
        } else {
            logger.error("Non-auth response received to auth response processor. Discarding message" + response);
        }
    }

    private void processAuthResponse(String response, TrsMessage trsMessage, AbstractClient user) {
        try {
            Map<String, String> authResponseInfo = converter.processAuthResponseInfo(response);
            //if authentication is success then add user session to the cache
            // we consider first-time-login and pin-validation-required events also as authentication success
            String responseToUser;
            if (!"0".equals(authResponseInfo.get(Converter.AUTH_STATUS))) {
                logger.info("Authentication is successful. {}", trsMessage.toStringMetaData());
                user.findAndRemoveOldLoggedSessions(authResponseInfo.get(Converter.AUTH_USER_ID));
                trsMessage.setClientType(user.getClientType());
                addSessionToCache(trsMessage, authResponseInfo, user.getTrsId());
                user.setUserID(authResponseInfo.get(Converter.AUTH_USER_ID));
                user.setChannelId(trsMessage.getChannelId());
                UserManager.getInstance().clientLoggedIn(user);
                user.setAuthenticated(true);
                responseToUser = response;
            } else {
                logger.info("Authentication failed. {}", trsMessage.toStringMetaData());
                String sessionId = trsMessage.getMsgSessionId();
                // client doesn't need to know newly generated session id if authentication is failed
                trsMessage.setMsgSessionId(null);
                responseToUser = converter.convertToString(trsMessage);

                trsMessage.setMsgSessionId(sessionId); // we need session id to record the response
            }
            user.resetAuthenticating();
            sendResponse(user, trsMessage, responseToUser);
        } catch (ClientConnectorException e) {
            logger.error("Error processing auth response. Response not sent to client. Session: " + user.getSessionID());
        }
    }

    public Client getUser(String sessionID) {
        return UserManager.getInstance().getClientBySessionId(sessionID);
    }
}
