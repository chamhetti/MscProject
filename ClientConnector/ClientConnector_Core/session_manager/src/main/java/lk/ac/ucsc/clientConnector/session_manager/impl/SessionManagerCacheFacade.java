package lk.ac.ucsc.clientConnector.session_manager.impl;

import lk.ac.ucsc.oms.cache.api.CacheControllerInterface;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheNotFoundException;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.clientConnector.session_manager.api.SessionManagerException;
import lk.ac.ucsc.clientConnector.session_manager.api.beans.UserSession;
import lk.ac.ucsc.clientConnector.session_manager.impl.beans.UserSessionBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class SessionManagerCacheFacade {
    private static Logger logger = LoggerFactory.getLogger(SessionManagerCacheFacade.class);
    private CacheControllerInterface sessionMgtCacheControler;

    /**
     * @param sessionMgtCacheControler is the cache controller reference
     */
    public void setSessionMgtCacheControler(CacheControllerInterface sessionMgtCacheControler) {
        this.sessionMgtCacheControler = sessionMgtCacheControler;
    }


    public void add(UserSession sm) throws SessionManagerException {
        UserSessionBean smBean = (UserSessionBean) sm;
        smBean.setPrimaryKeyObject(sm.getSessionID());
        try {
            sessionMgtCacheControler.addToCache(smBean);
        } catch (CacheNotFoundException e) {
            logger.error("Error in add to cache", e);
            throw new SessionManagerException("Error adding session to cache", e);
        }
    }


    public List<UserSession> findByUserID(String userID) throws SessionManagerException {
        try {
            return findByCriteria(UserSessionBean.class.getDeclaredField("userID"), userID);
        } catch (NoSuchFieldException e) {
            logger.error("Error in finding the User Session Bean for the user id: " + userID, e);
            throw new SessionManagerException("Error in finding the User", e);
        } catch (Exception e) {
            logger.error(e.toString(), e);
            throw new SessionManagerException("Error in finding the User", e);
        }
    }


    public List<UserSession> findByLogin(String login) throws SessionManagerException {
        try {
            List<UserSession> sessionListByAlias = findByCriteria(UserSessionBean.class.getDeclaredField("loginAlias"), login);
            if (!sessionListByAlias.isEmpty()) {
                return sessionListByAlias;
            } else {
                return findByCriteria(UserSessionBean.class.getDeclaredField("userID"), login);
            }
        } catch (NoSuchFieldException e) {
            logger.error("Error in finding the User Session Bean for the login: " + login, e);
            throw new SessionManagerException("Error in finding the User session", e);
        } catch (Exception e) {
            logger.error(e.toString(), e);
            throw new SessionManagerException("Error in finding the User session", e);
        }
    }


    public void remove(String sessionID) throws SessionManagerException {
        UserSessionBean sb = createEmptyUserSessionBean();
        sb.setPrimaryKeyObject(sessionID);
        try {
            UserSessionBean uSession = (UserSessionBean) sessionMgtCacheControler.readObjectFromCache(sb);
            sessionMgtCacheControler.removeFromCache(uSession);
        } catch (Exception e) {
            throw new SessionManagerException("Could not remove session. Session may not be persisted yet", e);
        }
    }


    private List<UserSession> findByCriteria(Field field, String value) throws SessionManagerException {
        List<UserSession> userSessions = new ArrayList<UserSession>();

        List<CacheObject> searchList = null;
        try {
            searchList = sessionMgtCacheControler.searchFromCache(field, value, UserSessionBean.class);
        } catch (Exception e) {
            throw new SessionManagerException("Error find by criteria from cache", e);
        }
        for (CacheObject cacheObject : searchList) {
            userSessions.add((UserSessionBean) cacheObject);
        }
        return userSessions;
    }


    public UserSession getSession(String sessionId) throws SessionManagerException {
        UserSessionBean userSession = createEmptyUserSessionBean();
        userSession.setPrimaryKeyObject(sessionId);
        CacheObject cacheObject = null;
        try {
            cacheObject = sessionMgtCacheControler.readObjectFromCache(userSession);
            return (UserSession) cacheObject;
        } catch (OMSException e) {
            throw new SessionManagerException("Error obtaining session", e);
        }
    }

    public List<UserSession> getAllSessions() throws SessionManagerException {
        List<UserSession> sessionList = new ArrayList<>();
        try {
            for (CacheObject co : sessionMgtCacheControler.getAllCache()) {
                sessionList.add((UserSession) co);
            }
        } catch (Exception e) {
            throw new SessionManagerException("Error obtaining sessions");
        }
        return sessionList;
    }


    public UserSessionBean createEmptyUserSessionBean() {
        return new UserSessionBean();
    }

}
