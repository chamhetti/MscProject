package lk.ac.ucsc.clientConnector.session_manager.impl;

import lk.ac.ucsc.oms.cache.api.AbstractCachePersister;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;

import lk.ac.ucsc.clientConnector.session_manager.api.SessionManagerException;
import lk.ac.ucsc.clientConnector.session_manager.impl.beans.UserSessionBean;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


public class SessionBeanHibernatePersistor extends AbstractCachePersister {
    private static Logger logger = LoggerFactory.getLogger(SessionBeanHibernatePersistor.class);

    public SessionBeanHibernatePersistor(SessionFactory sessionFactory) {
        super(sessionFactory, sessionFactory);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void update(CacheObject co) throws SessionManagerException {
        logger.debug("Persisting updated session cache object -{}", co);
        Transaction tx;
        Session session = sessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(co);
            tx.commit();
            logger.debug("Persisting updated session cache object success ");
        } catch (ConstraintViolationException e) {
            logger.error("User Session Bean Can't Update", co.getPrimaryKeyObject());
            throw new SessionManagerException("Issue in updating session", e);
        } finally {
            session.close();
        }
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void insert(CacheObject co) throws SessionManagerException {
        logger.debug("Persisting new session cache object -{}", co);
        Transaction tx;
        UserSessionBean sb = (UserSessionBean) co;
        sb.setSessionID(String.valueOf(co.getPrimaryKeyObject()));
        Session session = sessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            session.save(sb);
            tx.commit();
            logger.debug("Persisting new session cache object success ");
        } catch (ConstraintViolationException e) {
            logger.error("User Session Bean Already Exist" + co.getPrimaryKeyObject().toString());
            throw new SessionManagerException("Issue in updating session", e);
        } finally {
            session.close();
        }
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CacheObject load(CacheObject co) {
        CacheObject sessionBean = null;
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM lk.ac.ucsc.clientConnector.session_manager.impl.beans.UserSessionBean C WHERE C.sessionID = :sessionID";
            Query query = session.createQuery(hql);
            query.setParameter("sessionID", co.getPrimaryKeyObject().toString());
            List results = query.list();

            for (Object ob : results) {
                sessionBean = (UserSessionBean) ob;
            }
        } finally {
            session.close();
        }
        return sessionBean;
    }

    /**
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<CacheObject> loadAll() {
        List<CacheObject> cashObjectLst = new ArrayList<CacheObject>();
        Session session = sessionFactory.openSession();
        String hql = "FROM lk.ac.ucsc.clientConnector.session_manager.impl.beans.UserSessionBean";
        Query query = session.createQuery(hql);
        List results = query.list();
        try {
            for (Object sb : results) {
                UserSessionBean sessionBean = (UserSessionBean) sb;
                sessionBean.setPrimaryKeyObject(((UserSessionBean) sb).getSessionID());
                cashObjectLst.add(sessionBean);
            }
        } finally {
            session.close();
        }
        return cashObjectLst;
    }

    /**
     * @param co CacheObject
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void remove(CacheObject co) throws SessionManagerException {
        Transaction tx;
        Session session = sessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            String hql = " FROM lk.ac.ucsc.clientConnector.session_manager.impl.beans.UserSessionBean C where C.sessionID = :sessionID";
            Query query = session.createQuery(hql);
            query.setParameter("sessionID", co.getPrimaryKeyObject().toString());
            UserSessionBean sb = (UserSessionBean) query.list().get(0);
            session.delete(sb);
            tx.commit();
        } catch (Exception e) {
            logger.error("User Session Bean Doesn't  Exist in DB" + co.getPrimaryKeyObject().toString());
            throw new SessionManagerException("Issue in updating session", e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<CacheObject> loadPartially() {
        return new ArrayList<>();
    }

    @Override
    public boolean changeToHistoryMode() {
        return false;
    }

    @Override
    public boolean changeToPresentMode() {
        return false;
    }

    @Override
    public void insertList(List<CacheObject> objectList) {
        // not applicable
    }

    @Override
    public void updateList(List<CacheObject> updateList) {
        // not applicable
    }


}
