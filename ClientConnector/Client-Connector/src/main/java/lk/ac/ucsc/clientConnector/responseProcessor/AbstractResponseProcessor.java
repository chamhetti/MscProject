package lk.ac.ucsc.clientConnector.responseProcessor;

import lk.ac.ucsc.clientConnector.api.Client;
import lk.ac.ucsc.clientConnector.api.Converter;
import lk.ac.ucsc.clientConnector.common.api.ProcessingStatus;
import lk.ac.ucsc.clientConnector.common.api.TrsMessage;
import lk.ac.ucsc.clientConnector.front_office_listener.api.ResponseProcessor;
import lk.ac.ucsc.clientConnector.session_manager.api.SessionManagerException;
import lk.ac.ucsc.clientConnector.session_manager.api.SessionManagerFacade;
import lk.ac.ucsc.clientConnector.session_manager.api.SessionManagerFactory;
import lk.ac.ucsc.clientConnector.session_manager.api.beans.UserSession;
import lk.ac.ucsc.clientConnector.session_manager.impl.beans.UserSessionBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;



public abstract class AbstractResponseProcessor implements ResponseProcessor {
    private static Logger logger = LoggerFactory.getLogger(AbstractResponseProcessor.class);
    protected Converter converter;

    public AbstractResponseProcessor() {
    }

    @Override
    public abstract void processMessage(String mixResponse);

    /**
     * @param user     is the user
     * @param response is the response object
     */
    public void sendResponse(Client user, TrsMessage trsMessage, String response) {
        trsMessage.setTimeStamp(new Date());
        trsMessage.setProcessingStatus(ProcessingStatus.OMS_RESPONSE_RECEIVED);
        String responseToSend = response;

        logger.debug("Sending to client --> message :" + trsMessage.toStringMetaData());
        try {
            user.writeResponse(trsMessage, responseToSend);
            logger.debug("Finished sending to client --> message :" + trsMessage.toStringMetaData());
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
    }

    /**
     * @param authResponseInfoMap is the authentication response
     * @param trsId               is the trs id
     * @return the status
     */
    public void addSessionToCache(TrsMessage trsMessage, Map<String, String> authResponseInfoMap, String trsId) {
        logger.debug("Adding session to cache (login success): {} ", trsMessage.getMsgSessionId());

        UserSession userSession = new UserSessionBean();
        userSession.setSessionID(trsMessage.getMsgSessionId());
        userSession.setUserID(authResponseInfoMap.get(Converter.AUTH_USER_ID));
        userSession.setLoginAlias(authResponseInfoMap.get(Converter.AUTH_LOGIN_ALIAS));
        userSession.setChannelID(trsMessage.getChannelId());
        userSession.setTrsID(trsId);
        userSession.setStartTime(new Date());
        userSession.setAccountNumbers(authResponseInfoMap.get(Converter.AUTH_ACCOUNT_LIST));
        try {
            logger.info("Adding new session: Client ID: " + userSession.getUserID() + " Login Alias: " + userSession.getLoginAlias() +
                    " User ID: " + userSession.getUserID() + " Channel ID: " + trsMessage.getChannelId() +
                    " Session ID: " + trsMessage.getMsgSessionId() + " Account Numbers list: " + userSession.getAccountNumbers());
            getSessionManager().addUserSession(userSession);
        } catch (SessionManagerException e) {
            logger.error("Session Manager Error", e);
        }

        logger.debug("Finished adding session to cache (login success): {}", trsMessage.getMsgSessionId());
    }

    /**
     * @return SessionManagerFacade
     */
    public SessionManagerFacade getSessionManager() {
        return SessionManagerFactory.getSessionManager();
    }


}
