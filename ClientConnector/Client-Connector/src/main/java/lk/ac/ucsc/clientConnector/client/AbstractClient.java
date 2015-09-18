package lk.ac.ucsc.clientConnector.client;


import lk.ac.ucsc.clientConnector.api.Client;

import lk.ac.ucsc.clientConnector.api.Converter;
import lk.ac.ucsc.clientConnector.api.MessageRouter;
import lk.ac.ucsc.clientConnector.common.api.ClientType;
import lk.ac.ucsc.clientConnector.common.api.ErrorCodes;
import lk.ac.ucsc.clientConnector.common.api.ProcessingStatus;
import lk.ac.ucsc.clientConnector.common.api.TrsMessage;

import lk.ac.ucsc.clientConnector.session_manager.api.SessionManagerException;
import lk.ac.ucsc.clientConnector.session_manager.api.SessionManagerFacade;
import lk.ac.ucsc.clientConnector.session_manager.api.SessionManagerFactory;
import lk.ac.ucsc.clientConnector.session_manager.api.beans.UserSession;
import lk.ac.ucsc.clientConnector.sync_async_mapper.api.Mapper;
import lk.ac.ucsc.clientConnector.sync_async_mapper.api.MapperFactory;

import lk.ac.ucsc.clientConnector.settings.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.IOException;
import java.util.Date;
import java.util.List;


public abstract class AbstractClient implements Client {
    private static final int RANDOM_NUM_CONST = 10000000;
    private static Logger logger = LoggerFactory.getLogger(AbstractClient.class);
    private boolean isConnected;
    private String userID;
    private boolean isAuthenticated;
    private boolean isAuthenticating;
    private String clientAddress;
    private Converter converter;
    private String sessionID;
    private MessageRouter msgRouter;
    private String trsId;
    private ClientType clientType;
    private boolean rejectRepeatedAuthentication = false;
    private boolean qosEnabled;
    private int channelId;
    private Settings settings;


    TrsMessage preProcessAuthentication(String request) {
        TrsMessage trsMessage;

        if (request == null || request.isEmpty()) {
            trsMessage = createTrsMessage();
            trsMessage.setProcessingStatus(ProcessingStatus.EMPTY_REQUEST);
            trsMessage.setErrorCode(ErrorCodes.INVALID_MESSAGE);
            logger.error("Invalid message for authentication request is empty");
            trsMessage.setInvalidReason("Invalid request: request is empty");
            return trsMessage;
        }
        trsMessage = getConverter().createTrsMessageFromAuthRequest(request);
        trsMessage.setClientType(clientType);
        trsMessage.setTimeStamp(new Date());

        long uniqueID = this.getMapper().getUniqueID();
        MDC.put("tid", "tid-req" + uniqueID);
        logger.info("Unique Request ID: " + uniqueID);
        trsMessage.setUniqueReqId(Long.toString(uniqueID));

        this.setSessionID(generateSessionID(trsMessage.getUserId()));
        logger.info("Authenticating: Assigned new session id: " + this.getSessionID());
        trsMessage.setMsgSessionId(this.getSessionID());

        if (trsMessage.getProcessingStatus() != ProcessingStatus.VALID) {
            return trsMessage;
        }

        if (trsMessage.getProcessingStatus() != ProcessingStatus.VALID) {
            return trsMessage;
        }

        if (!converter.isAuthenticationMessage(trsMessage.getGroup(), trsMessage.getReqType())) {
            trsMessage.setProcessingStatus(ProcessingStatus.INVALID);
            trsMessage.setErrorCode(ErrorCodes.NON_AUTH_REQUEST_FOR_UNAUTHENTICATED_CLIENT);
            if (trsMessage.getMsgSessionId() != null) {
                // there is a session id, this can happen if client sends messages from a previous invalidated session
                String messageType = trsMessage.getGroup() + "-" + trsMessage.getReqType();
                logger.info("Authenticating: Non-authentication request (type:{}) from invalidated session detected. Will be discarded", messageType);
                trsMessage.setInvalidReason("Non-authentication request (type: " + messageType + ") from a previously invalidated session.");
            } else {
                // there is no session id, this is due to a client side error
                logger.warn("Non-authentication request while the client is not yet authenticated. Request: {}", request);
                trsMessage.setInvalidReason("Non-authentication request while the client is not yet authenticated");
            }
            return trsMessage;
        }

        if (trsMessage.getProcessingStatus() != ProcessingStatus.VALID) {
            logger.error("Invalid message for authentication: " + trsMessage.toStringMetaData());
            return trsMessage;
        } else {
            logger.info("Authenticating: parsing and channel validation complete. Login name: {}", trsMessage.getUserId());
        }


        if (trsMessage.getClientIp() != null) {
            this.setClientAddress(trsMessage.getClientIp());
        }  else {
            trsMessage.setClientIp(getClientAddress());
        }

        getUserManager().addClientToClientsTable(this);
        this.setAuthenticating(true);
        logger.debug("Authentication new client: " + trsMessage.getUserId());
        return trsMessage;

    }

    /**
     * This method is used in jUnit testing
     *
     * @return
     */
    public UserManager getUserManager() {
        return UserManager.getInstance();
    }
    /**
     * This method i used in jUnit testing
     *
     * @return
     */
    public Mapper getMapper() {
        return MapperFactory.getInstance().getMapper();
    }

    /**
     * This method is used in jUnit testing
     *
     * @return
     */
    public TrsMessage createTrsMessage() {
        return new TrsMessage();
    }

    TrsMessage preProcessRequest(String request) {

        TrsMessage trsMessage = getConverter().convertToTrsMessage(request);
        trsMessage.setClientType(clientType);
        trsMessage.setTimeStamp(new Date());

        long uniqueID = this.getMapper().getUniqueID();
        MDC.put("tid", "tid-req" + uniqueID);
        logger.info("Unique Request ID: " + uniqueID);
        trsMessage.setUniqueReqId(Long.toString(uniqueID));


        if (trsMessage.getProcessingStatus() != ProcessingStatus.VALID) {
            logger.error("Invalid message: {}-{} {} ", trsMessage.getGroup(), trsMessage.getReqType(), trsMessage.getInvalidReason());
            return trsMessage;
        }

        int messageGroup = trsMessage.getGroup();
        int requestType = trsMessage.getReqType();

        if (converter.isAuthenticationMessage(messageGroup, requestType)) {
            logger.info("Already authenticated client is sending an authentication message.");
            if (rejectRepeatedAuthentication) {
                trsMessage.setProcessingStatus(ProcessingStatus.INVALID);
                trsMessage.setInvalidReason("Authentication messages are not allowed for already authenticated clients");
                return trsMessage;
            }
            // session validation is not performed for repeated authentication to address a RIA integration issue
            logger.info("Skipping session validation for repeated authentication");
            this.getUserManager().removeClientFromClientsTable(this.getSessionID());
            trsMessage = getConverter().createTrsMessageFromAuthRequest(request);
            trsMessage.setClientType(clientType);
            trsMessage.setTimeStamp(new Date());
            this.setSessionID(generateSessionID(trsMessage.getUserId()));
            trsMessage.setUniqueReqId(Long.toString(uniqueID));
            trsMessage.setMsgSessionId(this.getSessionID());
            this.getUserManager().addClientToClientsTable(this);

        } else {
            boolean status = validateSessionID(trsMessage);
            if (!status) {
                logger.info("Session validation failed for Id: " + trsMessage.getMsgSessionId() + " for the User: " + getUserID());
                trsMessage.setProcessingStatus(ProcessingStatus.INVALID);
                trsMessage.setInvalidReason("Session validation failed");
                trsMessage.setErrorCode(ErrorCodes.SESSION_VALIDATION_FAILED);
                return trsMessage;
            }
        }


        if (trsMessage.getClientIp() == null || trsMessage.getClientIp().isEmpty()) { // give priority to ip sent by client
        trsMessage.setClientIp(getClientAddress());
        }
        trsMessage.setProcessingStatus(ProcessingStatus.VALID);
        return trsMessage;

    }



    public void close(String reason, boolean sendLogOut) {

        if (isConnected) {
            logger.info("{} client is closing. IP Address: {}, UserId: {}, SessionId: {}, Reason: {}  ", clientType, clientAddress, userID, sessionID, reason);
            if (sendLogOut) {
                sendLogoutResponse(this.sessionID, this.userID);
            }
            if (sessionID != null) {
                this.getUserManager().removeClientFromClientsTable(sessionID);
                if (isAuthenticated) {
                    removeUserFromCache(sessionID);
                }
            }
            isConnected = false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean validateSessionID(TrsMessage trsMessage) {
        if (sessionID == null) {
            return false;
        }
        try {
            boolean status = getSessionManager().validateSessionID(trsMessage.getMsgSessionId(), trsMessage.getChannelId());
            trsMessage.setUserId(getSessionManager().getUserId(sessionID));
            return status;
        } catch (SessionManagerException e) {
            logger.warn("Session Validation Failed" + e.getMessage());
            return false;
        }
    }

    /**
     * Sending pulse message to the socket client
     * used with mina framework
     */
    void processPulse() {
        if (isConnected) {
            try {
                logger.debug("Pulse received from session: " + getSessionID());
                String response = converter.getPulseMessage(getSessionID());
                logger.debug("Sending pulse response to session: " + getSessionID());
                write(response);
            } catch (Exception e) {
                logger.error("Error sending pulse", e.getMessage());
            }
        }
    }

    TrsMessage createErrorResponse(TrsMessage trsMessage) {
        TrsMessage respMessage;
        try {
            switch (trsMessage.getProcessingStatus()) {
                case EMPTY_REQUEST:
                case PARSE_ERROR:
                    respMessage = converter.getInvalidRequestMessage(ErrorCodes.INVALID_MESSAGE, trsMessage);
                    break;
                case NOT_SUPPORTED:
                    respMessage = converter.getInvalidRequestMessage(ErrorCodes.MESSAGE_TYPE_IS_NOT_SUPPORTED, trsMessage);
                    break;
                case INVALID:
                    if (converter.isAuthenticationMessage(trsMessage.getGroup(), trsMessage.getReqType())
                            && trsMessage.getErrorCode() == ErrorCodes.ERR_INVALID_CLIENT_VERSION) {
                        respMessage = converter.getVersionNotSupportedAuthErrorMessage(trsMessage);
                    } else {
                        respMessage = converter.getErrorMessage(trsMessage);
                    }
                    break;
                default:
                    throw new Exception("Invalid processing status");
            }
        } catch (Exception e) {
            logger.warn("Error creating error response", e);
            respMessage = new TrsMessage();
            respMessage.setOriginalMessage("Error!");
            respMessage.setMsgSessionId(trsMessage.getMsgSessionId());
            respMessage.setUniqueReqId(trsMessage.getUniqueReqId());
        }
        return respMessage;
    }

    @Override
    public String generateSessionID(String loginAlias) {
        String sSessionID = trsId + "-" + loginAlias + "-" + System.currentTimeMillis() + Math.round(Math.random() * RANDOM_NUM_CONST);
        logger.debug("Session ID: " + sSessionID);
        return sSessionID;
    }

    /**
     * Remove user from the cache
     *
     * @param sessionID is the user session id
     */
    void removeUserFromCache(String sessionID) {
        logger.info("Removing user : " + userID + " From the cache. Session ID: " + sessionID);
        try {
            if (sessionID != null) {
                this.getSessionManager().removeUserSession(sessionID);
            }
        } catch (SessionManagerException e) {
            logger.error("Error removing the user from cache: " + e.getMessage());
        }
    }


    @Override
    public void writeResponse(TrsMessage message, String responseString) {
        try {
            write(responseString);
        } catch (Exception e) {
            logger.error("Error writing response to client", e);
        }
        try {
            message.setProcessingStatus(ProcessingStatus.SENT_TO_CLIENT);
        } catch (Exception e) {
            logger.error("Error recording response message", e);
        }
    }


    abstract void write(String message) throws IOException;


    @Override
    public void sendLogoutResponse(String sessionId, String userId) {
        String logOutResponse;
        if ((sessionId != null) && (userId != null)) {
            logOutResponse = converter.getLogOutMessage(userId, sessionId);
            logger.debug("Send Logout Response to the Client: ");
            try {
                write(logOutResponse);
            } catch (Exception e) {
                logger.debug("Error while sending logout to the client: {}. Reason", getClientAddress(), e.getMessage());
            }
        }
    }


    @Override
    public void findAndRemoveOldLoggedSessions(String login) {
        logger.info("Checking for previous session for login: " + login);
        SessionManagerFacade sessionManagerFacade = getSessionManager();
        try {
            //if already session exist for the user we need to remove the old user session and send logout to the user
            List<UserSession> uSessionList = sessionManagerFacade.getUserSessionsByLogin(login);

            if (uSessionList.isEmpty()) {
                logger.info("No previous session found for login: " + login);
            }
            // typically, there will be only one session at a time.
            // but in cases where trs is terminated while there are active sessions, there can be old sessions left.
            for (UserSession userSession : uSessionList) {

                if (!(userSession.getLoginAlias().equals(login) || userSession.getUserID().equals(login))) {
                    logger.debug("Searched login: {}, Found login: {}", login, userSession.getLoginAlias());
                    continue;
                }

                logger.info("Previous session already exists for login: " + login
                        + ". Existing session id: " + userSession.getSessionID() + "New session id: " + this.getSessionID()
                        + "Removing existing session..");

                AbstractClient client = (AbstractClient) this.getUserManager().getClientBySessionId(userSession.getSessionID());
                if (client != null) {
                    //close client session and send logout message. here, client will be removed from client table and cache
                    client.close("New session initiated", true);
                } else {
                    logger.info("Expected client does not exist in client table. Stale session removed. This can happen during repeated authentication");
                    removeUserFromCache(userSession.getSessionID());
                }
            }
        } catch (Exception e) {
            logger.error("Error in finding the user session from the  cache: " + e);
        }
    }

    // ========== getters and setters ============

    @Override
    public MessageRouter getMsgRouter() {
        return msgRouter;
    }

    @Override
    public void setMsgRouter(MessageRouter msgRouter) {
        this.msgRouter = msgRouter;
    }

    public String getTrsId() {
        return trsId;
    }

    void setTrsId(String trsId) {
        this.trsId = trsId;
    }

    public String getSessionID() {
        return sessionID;
    }

    void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    Converter getConverter() {
        return converter;
    }

    void setConverter(Converter converter) {
        this.converter = converter;
    }


    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    void setAuthenticating(boolean authenticating) {
        isAuthenticating = authenticating;
    }

    public void resetAuthenticating() {
        isAuthenticating = false;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

    public SessionManagerFacade getSessionManager() {
        return SessionManagerFactory.getSessionManager();
    }


    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
