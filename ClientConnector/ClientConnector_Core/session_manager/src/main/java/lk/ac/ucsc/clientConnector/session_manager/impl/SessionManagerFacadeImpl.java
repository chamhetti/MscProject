package lk.ac.ucsc.clientConnector.session_manager.impl;

import lk.ac.ucsc.clientConnector.session_manager.api.SessionManagerException;
import lk.ac.ucsc.clientConnector.session_manager.api.SessionManagerFacade;
import lk.ac.ucsc.clientConnector.session_manager.api.beans.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class SessionManagerFacadeImpl implements SessionManagerFacade {
    private static Logger logger = LoggerFactory.getLogger(SessionManagerFacadeImpl.class);
    private SessionManagerCacheFacade sessionMgtCacheFacade;

    public SessionManagerFacadeImpl() {
    }

    public void setSessionManagerCacheFacade(SessionManagerCacheFacade sessionMgtCacheFacade) {
        this.sessionMgtCacheFacade = sessionMgtCacheFacade;
    }


    @Override
    public void addUserSession(UserSession uSession) throws SessionManagerException {
        sessionMgtCacheFacade.add(uSession);
    }


    @Override
    public void removeUserSession(String sessionID) throws SessionManagerException {
        sessionMgtCacheFacade.remove(sessionID);
    }


    @Override
    public UserSession getUserSessionByUserID(String userID) throws SessionManagerException {
        List<UserSession> userSessions = sessionMgtCacheFacade.findByUserID(userID);
        if (userSessions.isEmpty()) {
            throw new SessionManagerException("Session not found");
        }
        if (userSessions.size() > 1) {
            logger.error("More than one session found. This should not generally happen. UserID: " + userID);
        }
        return userSessions.get(0);
    }


    @Override
    public List<UserSession> getUserSessionsByLogin(String loginAlias) throws SessionManagerException {
        return sessionMgtCacheFacade.findByLogin(loginAlias);
    }


    @Override
    public boolean validateSessionID(String sessionId, int channelId) throws SessionManagerException {
        if (sessionId == null) {
            throw new SessionManagerException("Invalid session id");
        }
        UserSession session = sessionMgtCacheFacade.getSession(sessionId);
        if (session == null) {
            throw new SessionManagerException("Session not found");
        }
        return sessionId.equals(session.getSessionID()) && session.getChannelID() == channelId;

    }


    @Override
    public String getUserId(String sessionId) throws SessionManagerException {
        UserSession session = sessionMgtCacheFacade.getSession(sessionId);
        return session.getUserID();
    }


    @Override
    public List<UserSession> getAllActiveSessions() throws SessionManagerException {
        return sessionMgtCacheFacade.getAllSessions();
    }

}
