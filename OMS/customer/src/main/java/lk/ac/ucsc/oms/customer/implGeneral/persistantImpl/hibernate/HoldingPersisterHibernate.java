package lk.ac.ucsc.oms.customer.implGeneral.persistantImpl.hibernate;

import lk.ac.ucsc.oms.cache.api.AbstractCachePersister;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.common_utility.api.enums.HoldingTypes;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;

import lk.ac.ucsc.oms.customer.api.beans.holding.HoldingKey;
import lk.ac.ucsc.oms.customer.api.beans.holding.HoldingRecord;
import lk.ac.ucsc.oms.customer.api.exceptions.HoldingManagementException;
import lk.ac.ucsc.oms.customer.implGeneral.beans.holding.HoldingKeyBean;
import lk.ac.ucsc.oms.customer.implGeneral.beans.holding.HoldingRecordBean;
import lk.ac.ucsc.oms.customer.implGeneral.persistantImpl.HoldingPersister;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class HoldingPersisterHibernate extends AbstractCachePersister implements HoldingPersister {
    private static final int BATCH_SIZE = 20;
    private static Logger logger = LogManager.getLogger(HoldingPersisterHibernate.class);
    private static Date lastUpdateTime;

    public HoldingPersisterHibernate(SessionFactory realTime, SessionFactory history) {
        super(realTime, history);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(CacheObject co) throws HoldingManagementException {
        Transaction tx;
        Session session = realTimeSessionFactory.openSession();
        try {
            if (co == null) {
                throw new HoldingManagementException("HoldingRecord bean can't be null");
            }
            logger.debug("updating the HoldingRecord bean: {}", co);
            tx = session.beginTransaction();
            session.update(co);
            tx.commit();
            logger.debug("updating finished");
        } catch (Exception e) {
            logger.error("HoldingRecord bean can't update", e);
            throw new HoldingManagementException("HoldingRecord bean can't update", e);
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
            logger.debug("Inserting the HoldingRecord bean:{} ", co);
            if (co == null) {
                throw new HoldingManagementException("HoldingRecord bean can't be null");
            }
            tx = session.beginTransaction();
            session.save(co);
            tx.commit();
            logger.debug("Inserting HoldingRecord bean finished");
        } catch (Exception e) {
            logger.error("problem in adding holdingRecord", e);
            throw new HoldingManagementException("problem in adding HoldingRecord", e);
        } finally {
            session.close();
        }
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CacheObject load(CacheObject co) throws HoldingManagementException {
        HoldingRecordBean holdingRecordBean = null;
        Session session = realTimeSessionFactory.openSession();
        try {
            if (co == null) {
                throw new HoldingManagementException("HoldingRecord bean can't be null");
            }

            HoldingKeyBean holdingRecord = (HoldingKeyBean) co.getPrimaryKeyObject();
            logger.info("Loading the holdingRecord with holdingRecord code:{} ", holdingRecord);
            if (holdingRecord == null) {
                logger.warn("HoldingRecord info provided not enough to load from DB");
                throw new HoldingManagementException("HoldingRecord bean can't be null");
            }
            String hql = "FROM lk.ac.ucsc.oms.customer.implGeneral.beans.holding.HoldingRecordBean C WHERE C.holdingInfoKey = :holdingKey";
            Query query = session.createQuery(hql);
            query.setParameter("holdingKey", holdingRecord);
            holdingRecordBean = (HoldingRecordBean) query.uniqueResult();
            if (holdingRecordBean != null) {
                holdingRecordBean.setPrimaryKeyObject(holdingRecordBean.getHoldingInfoKey());
            }
            logger.info("Loaded HoldingRecord:{}", holdingRecordBean);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new HoldingManagementException("Error in executing Hibernate Query for loading holding record", e);
        } finally {
            session.close();
        }
        return holdingRecordBean;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<CacheObject> loadAll() {
        logger.info("Loading all the HoldingRecord from DB");

        List<CacheObject> cashObjectLst = new ArrayList<>();
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "FROM lk.ac.ucsc.oms.customer.implGeneral.beans.holding.HoldingRecordBean";
            Query query = session.createQuery(hql);
            List results = query.list();

            for (Object cgb : results) {
                HoldingRecordBean holdingRecordBean = (HoldingRecordBean) cgb;
                holdingRecordBean.setPrimaryKeyObject(holdingRecordBean.getHoldingInfoKey());
                cashObjectLst.add(holdingRecordBean);
            }
            logger.debug("Loaded HoldingRecords list of size:{} and list:{}", cashObjectLst.size(), cashObjectLst);
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
    public void remove(CacheObject co) throws OMSException {
        throw new OMSException("Unsupported operation");
    }


    @Override
    public List<HoldingKey> getCustomerAllHoldingKeys(String customerNumber) throws HoldingManagementException {
        logger.info("Loading all the holding key from DB");

        List<HoldingKey> cashObjectLst = new ArrayList<>();
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "FROM lk.ac.ucsc.oms.customer.implGeneral.beans.holding.HoldingRecordBean C where C.holdingInfoKey.customerNumber=:cusNumber " +
                    "and (C.netHolding >0 or C.buyPending>0 or C.sellPending>0)";
            Query query = session.createQuery(hql);
            query.setParameter("cusNumber", customerNumber);
            List results = query.list();

            for (Object cgb : results) {
                HoldingRecordBean holdingRecordBean = (HoldingRecordBean) cgb;
                cashObjectLst.add(holdingRecordBean.getHoldingInfoKey());
            }
            logger.debug("Loaded HoldingKey list of size:{} and list:{}", cashObjectLst.size(), cashObjectLst);
        } catch (Exception e) {
            logger.error("Error in Getting Customer All Holdings", e);
            throw new HoldingManagementException("Error in Getting Customer All Holding Keys", e);
        } finally {
            session.close();
        }
        return cashObjectLst;
    }

    @Override
    public List<CacheObject> getHoldingsForInstitution(String institutions) throws HoldingManagementException {
        logger.info("Loading institution filtered the holding key from DB");

        List<CacheObject> cashObjectLst = new ArrayList<>();
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "FROM lk.ac.ucsc.oms.customer.implGeneral.beans.holding.HoldingRecordBean H where H.institutionCode in (" + institutions + ") ";
            Query query = session.createQuery(hql);
            List results = query.list();

            for (Object cgb : results) {
                HoldingRecordBean holdingRecordBean = (HoldingRecordBean) cgb;
                holdingRecordBean.setPrimaryKeyObject(holdingRecordBean.getHoldingInfoKey());
                cashObjectLst.add(holdingRecordBean);
            }
            logger.debug("Loaded HoldingRecords list of size:{} and list:{}", cashObjectLst.size(), cashObjectLst);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return cashObjectLst;
    }


    @Override
    public void insertList(List<CacheObject> objectList) {
        Transaction tx;
        Session session = sessionFactory.openSession();
        long startTime = System.currentTimeMillis();
        try {
            int i = 0;
            tx = session.beginTransaction();
            HoldingRecordBean holdingRecordBean;
            for (CacheObject co : objectList) {
                if (co == null) {
                    logger.error("cache object is null");
                    logger.error("", new Exception("StackTrace"));
                    continue;
                }
                co.setDirty(false);
                co.setNew(false);
                holdingRecordBean = (HoldingRecordBean) co;
                session.save(holdingRecordBean);
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
            logger.debug("Time to commit holding insertion process >>>" + (endTime - startTime));
        } catch (Exception e) {
            logger.error("Process Holding Insert: Holding Bean Can't insert, Error : " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }

    @Override
    public void updateList(List<CacheObject> updateList) {
        Transaction tx;
        Session session = realTimeSessionFactory.openSession();
        long startTime = System.currentTimeMillis();
        try {
            int i = 0;
            tx = session.beginTransaction();
            HoldingRecordBean holdingRecordBean;
            for (CacheObject co : updateList) {
                co.setDirty(false);
                holdingRecordBean = (HoldingRecordBean) co;
                session.saveOrUpdate(holdingRecordBean);
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
            logger.debug("Time to commit holding updating process >>>" + (endTime - startTime));
        } catch (Exception e) {
            logger.error("Process Holding Update: Holding Bean Can't Update, Error : " + e.getMessage(), e);
        } finally {
            session.close();
        }

    }



}
