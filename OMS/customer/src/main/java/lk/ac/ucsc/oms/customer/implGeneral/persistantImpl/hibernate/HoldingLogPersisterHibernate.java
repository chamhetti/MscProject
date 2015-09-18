package lk.ac.ucsc.oms.customer.implGeneral.persistantImpl.hibernate;

import lk.ac.ucsc.oms.cache.api.AbstractCachePersister;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;


import lk.ac.ucsc.oms.customer.api.beans.holding.HoldingLog;
import lk.ac.ucsc.oms.customer.api.exceptions.HoldingManagementException;
import lk.ac.ucsc.oms.customer.implGeneral.beans.holding.HoldingLogBean;
import lk.ac.ucsc.oms.customer.implGeneral.persistantImpl.HoldingLogPersister;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class HoldingLogPersisterHibernate extends AbstractCachePersister implements HoldingLogPersister {
    private static final int BATCH_SIZE = 20;
    private static Logger logger = LogManager.getLogger(HoldingLogPersisterHibernate.class);
    private static Date lastUpdateTime;


    public HoldingLogPersisterHibernate(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(CacheObject co) throws HoldingManagementException {
        Transaction tx;
        Session session = sessionFactory.openSession();
        try {
            if (co == null) {
                throw new HoldingManagementException("HoldingLog bean can't be null");
            }
            logger.debug("updating the HoldingLog bean: {}", co);
            tx = session.beginTransaction();
            session.update(co);
            tx.commit();
            logger.debug("updating finished");
        } catch (Exception e) {
            logger.error("HoldingLog bean can't update", e);
            throw new HoldingManagementException("HoldingLog bean can't update", e);
        } finally {
            session.close();
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void insert(CacheObject co) throws HoldingManagementException {
        Transaction tx;
        Session session = sessionFactory.openSession();
        try {
            logger.debug("Inserting the HoldingLog bean:{} ", co);
            if (co == null) {
                throw new HoldingManagementException("HoldingLog bean can't be null");
            }
            tx = session.beginTransaction();
            session.save(co);
            tx.commit();
            logger.debug("Inserting HoldingLog bean finished");
        } catch (Exception e) {
            logger.error("problem in adding holdingLog", e);
            throw new HoldingManagementException("problem in adding holdingLog", e);
        } finally {
            session.close();
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CacheObject load(CacheObject co) throws HoldingManagementException {
        HoldingLogBean holdingLogBean = null;
        Session session = sessionFactory.openSession();
        try {
            if (co == null) {
                logger.error("The HoldingLog to load is null");
                throw new HoldingManagementException("The HoldingLog to load is null");
            }

            Long holdingLog = (Long) co.getPrimaryKeyObject();
            logger.info("Loading the holdingLog with holdingLog code:{} ", holdingLog);
            if (holdingLog == null) {
                logger.warn("HoldingLog info provided not enough to load from DB");
                throw new HoldingManagementException("The Holding Log Id is null");
            }
            String hql = "FROM lk.ac.ucsc.oms.customer.implGeneral.beans.holding.HoldingLogBean C WHERE C.holdingLogId = :holdingLogId";
            Query query = session.createQuery(hql);
            query.setParameter("holdingLogId", holdingLog);
            holdingLogBean = (HoldingLogBean) query.uniqueResult();
            if (holdingLogBean != null) {
                holdingLogBean.setPrimaryKeyObject(holdingLogBean.getHoldingLogId());
            }
            logger.info("Loaded HoldingLog:{}", holdingLogBean);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new HoldingManagementException("Error in executing Hibernate Query for loading holding log", e);
        } finally {
            session.close();
        }
        return holdingLogBean;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<CacheObject> loadAll() {
        logger.info("Loading all the HoldingLog from DB");
        List<CacheObject> cashObjectLst = new ArrayList<>();
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM lk.ac.ucsc.oms.customer.implGeneral.beans.holding.HoldingLogBean";
            Query query = session.createQuery(hql);
            List results = query.list();

            for (Object cgb : results) {
                HoldingLogBean holdingLogBean = (HoldingLogBean) cgb;
                holdingLogBean.setPrimaryKeyObject(holdingLogBean.getHoldingLogId());
                cashObjectLst.add(holdingLogBean);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        logger.debug("Loaded HoldingLogs list of size:{} and list:{}", cashObjectLst.size(), cashObjectLst);
        return cashObjectLst;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<CacheObject> loadWithInstitutionDateFilter(String institutionList, Date fromDate, Date toDate) {
        logger.info("Loading all the HoldingLog from DB");
        List<CacheObject> cashObjectLst = new ArrayList<>();
        Session session = sessionFactory.openSession();

        try {
            String hql;
            if (!"".equals(institutionList)) {
                hql = "FROM lk.ac.ucsc.oms.customer.implGeneral.beans.holding.HoldingLogBean H where H.institutionCode in(" + institutionList + ") and H.transactionTime between :fromDate and :toDate";
            } else {
                hql = "FROM lk.ac.ucsc.oms.customer.implGeneral.beans.holding.HoldingLogBean H where  H.transactionTime between :fromDate and :toDate";
            }
            Query query = session.createQuery(hql);

            if (fromDate != null && toDate != null) {
                query.setParameter("fromDate", fromDate);
                query.setParameter("toDate", toDate);
            } else if (fromDate != null) {
                query.setParameter("fromDate", fromDate);
                query.setParameter("toDate", fromDate);
            } else if (toDate != null) {
                query.setParameter("fromDate", toDate);
                query.setParameter("toDate", toDate);
            }
            List results = query.list();


            for (Object cgb : results) {
                HoldingLogBean holdingLogBean = (HoldingLogBean) cgb;
                holdingLogBean.setPrimaryKeyObject(holdingLogBean.getHoldingLogId());
                cashObjectLst.add(holdingLogBean);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        logger.debug("Loaded HoldingLogs list of size:{} and list:{}", cashObjectLst.size(), cashObjectLst);
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
        try {
            int i = 0;
            tx = session.beginTransaction();
            HoldingLogBean holdingRecordBean;
            for (CacheObject co : objectList) {
                co.setDirty(false);
                co.setNew(false);
                holdingRecordBean = (HoldingLogBean) co;
                session.save(holdingRecordBean);
                if (i % BATCH_SIZE == 0) { //20, same as the JDBC batch size
                    //flush a batch of inserts and release memory:
                    session.flush();
                    session.clear();
                }
                i++;
            }
            tx.commit();
            lastUpdateTime = new Date();
        } catch (Exception e) {
            logger.error("Process Holding Log Insert: Holding Log Bean Can't Update, Error : " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }


    @Override
    public void updateList(List<CacheObject> updateList) {
        if (updateList == null) {
            return;
        }
        Transaction tx;
        Session session = sessionFactory.openSession();
        try {
            int i = 0;
            tx = session.beginTransaction();
            HoldingLogBean holdingRecordBean;
            for (CacheObject co : updateList) {
                co.setDirty(false);
                holdingRecordBean = (HoldingLogBean) co;
                session.saveOrUpdate(holdingRecordBean);
                if (i % BATCH_SIZE == 0) { //20, same as the JDBC batch size
                    //flush a batch of inserts and release memory:
                    session.flush();
                    session.clear();
                }
                i++;
            }
            tx.commit();
            lastUpdateTime = new Date();
        } catch (Exception e) {
            logger.error("Process Holding Log Update: Holding Log Bean Can't Update, Error : " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }

}
