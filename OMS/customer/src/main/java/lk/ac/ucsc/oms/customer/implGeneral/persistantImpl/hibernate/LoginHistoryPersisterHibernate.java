package lk.ac.ucsc.oms.customer.implGeneral.persistantImpl.hibernate;

import lk.ac.ucsc.oms.cache.api.AbstractCachePersister;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;


import lk.ac.ucsc.oms.customer.api.exceptions.CustomerLoginException;
import lk.ac.ucsc.oms.customer.implGeneral.beans.login.LoginHistoryBean;
import lk.ac.ucsc.oms.customer.implGeneral.persistantImpl.LoginHistoryPersister;
import com.google.gson.Gson;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class LoginHistoryPersisterHibernate extends AbstractCachePersister implements LoginHistoryPersister {
    private static final int BATCH_SIZE = 20;
    private static Logger logger = LogManager.getLogger(LoginHistoryPersisterHibernate.class);
    private static Date lastUpdateTime;

    public LoginHistoryPersisterHibernate(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public void update(CacheObject co) throws CustomerLoginException {
        Transaction tx;
        Session session = sessionFactory.openSession();
        try {
            if (co == null) {
                throw new CustomerLoginException("LoginHistory bean can't be null");
            }
            logger.debug("updating the LoginHistory bean: {}", co);
            tx = session.beginTransaction();
            session.update(co);
            tx.commit();
            logger.debug("updating finished");
        } catch (Exception e) {
            logger.error("LoginHistory bean can't update", e);
            throw new CustomerLoginException("LoginHistory bean can't update", e);
        } finally {
            session.close();
        }

    }


    @Override
    public void insert(CacheObject co) throws CustomerLoginException {
        Transaction tx;

        Session session = sessionFactory.openSession();
        try {
            logger.debug("Inserting the LoginHistory bean:{} ", co);
            if (co == null) {
                throw new CustomerLoginException("LoginHistory bean can't be null");
            }
            tx = session.beginTransaction();
            session.save(co);
            tx.commit();
            logger.debug("Inserting LoginHistory bean finished");
        } catch (Exception e) {
            logger.error("problem in adding loginHistory", e);
            throw new CustomerLoginException("problem in adding LoginHistory", e);
        } finally {
            session.close();
        }
    }

    @Override
    public CacheObject load(CacheObject co) throws CustomerLoginException {
        LoginHistoryBean loginHistoryBean = null;
        Session session = sessionFactory.openSession();
        try {
            if (co == null) {
                throw new CustomerLoginException("LoginHistory bean can't be null");
            }

            Long loginHistory = (Long) co.getPrimaryKeyObject();
            logger.info("Loading the loginHistory with loginHistory code:{} ", loginHistory);
            if (loginHistory == null) {
                logger.warn("LoginHistory info provided not enough to load from DB");
                throw new CustomerLoginException("LoginHistory bean can't be null");
            }
            String hql = "FROM lk.ac.ucsc.oms.customer.implGeneral.beans.login.LoginHistoryBean C WHERE C.id = :loginHistoryId";
            Query query = session.createQuery(hql);
            query.setParameter("loginHistoryId", loginHistory);
            loginHistoryBean = (LoginHistoryBean) query.uniqueResult();
            if (loginHistoryBean != null) {
                loginHistoryBean.setPrimaryKeyObject(loginHistoryBean.getId());
            }
            logger.info("Loaded LoginHistory:{}", loginHistoryBean);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CustomerLoginException("Error in executing Hibernate Query for loading login history", e);
        } finally {
            session.close();
        }
        return loginHistoryBean;
    }

    @Override
    public List<CacheObject> loadAll() {
        logger.info("Loading all the LoginHistory from DB");
        List<CacheObject> cashObjectLst = new ArrayList<>();
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM lk.ac.ucsc.oms.customer.implGeneral.beans.login.LoginHistoryBean";
            Query query = session.createQuery(hql);
            List results = query.list();

            for (Object cgb : results) {
                LoginHistoryBean loginHistoryBean = (LoginHistoryBean) cgb;
                loginHistoryBean.setPrimaryKeyObject(loginHistoryBean.getId());
                cashObjectLst.add(loginHistoryBean);
            }
            logger.debug("Loaded LoginHistorys list of size:{} and list:{}", cashObjectLst.size(), cashObjectLst);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return cashObjectLst;
    }

    @Override
    public List<CacheObject> loadPartially() {
        return new ArrayList<>();
    }


    @Override
    public void remove(CacheObject co) {
        logger.error("Unsupported operation");
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
        if (objectList == null) {
            return;
        }
        Transaction tx;
        Session session = sessionFactory.openSession();
        long startTime = System.currentTimeMillis();
        try {
            int i = 0;
            tx = session.beginTransaction();
            for (CacheObject co : objectList) {
                co.setDirty(false);
                co.setNew(false);
                session.save(co);
                if (i % BATCH_SIZE == 0) { //20, same as the JDBC batch size
                    //flush a batch of inserts and release memory:
                    session.flush();
                    session.clear();
                }
                i++;
            }
            tx.commit();
            long endTime = System.currentTimeMillis();
            lastUpdateTime = new Date();
            logger.debug("Time to commit login history insertion process >>>" + (endTime - startTime));
        } catch (Exception e) {
            logger.error("Inserting Process: login history Bean Can't Update, Error : ", e);
            for (CacheObject co : objectList) {
                logger.error("Error while adding login history to the database ", new Gson().toJson(co));
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void updateList(List<CacheObject> updateList) {
        logger.error("Unsupported operation");
    }



}
