package lk.ac.ucsc.clientConnector.session_manager.api;

import lk.ac.ucsc.clientConnector.session_manager.api.beans.UserSession;

import java.util.List;


public interface SessionManagerFacade {


    void addUserSession(UserSession uSession) throws SessionManagerException;


    void removeUserSession(String sessionID) throws SessionManagerException;


    UserSession getUserSessionByUserID(String userID) throws SessionManagerException;


    List<UserSession> getUserSessionsByLogin(String loginAlias) throws SessionManagerException;


    boolean validateSessionID(String sessionID, int channelId) throws SessionManagerException;


    String getUserId(String sessionId) throws SessionManagerException;


    List<UserSession> getAllActiveSessions() throws SessionManagerException;
}
