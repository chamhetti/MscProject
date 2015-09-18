package lk.ac.ucsc.oms.orderMgt.implGeneral.persistantImpl.hibernate;

import lk.ac.ucsc.oms.cache.api.AbstractCachePersister;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;

import lk.ac.ucsc.oms.orderMgt.api.beans.Execution;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.OrderExecutionException;
import lk.ac.ucsc.oms.orderMgt.implGeneral.bean.ExecutionBean;
import lk.ac.ucsc.oms.orderMgt.implGeneral.persistantImpl.ExecutionPersister;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ExecutionHibernatePersister extends AbstractCachePersister implements ExecutionPersister {
    private static final int BATCH_SIZE = 20;
    private static Logger logger = Logger.getLogger(ExecutionHibernatePersister.class);
    private static Date lastUpdateTime;

    public ExecutionHibernatePersister(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void update(CacheObject co) throws OrderExecutionException {
        logger.info("Update ExecutionBean in the Database");
        if (co == null) {
            throw new OrderExecutionException("The Execution to update is null");
        }
        Transaction tx;
        Session session = sessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(co);
            tx.commit();
            lastUpdateTime = new Date();
            logger.debug("Updating the Database successful, ExecutionBean : " + co.getPrimaryKeyObject());
        } catch (Exception e) {
            logger.error("Execution Bean Can't Update", e);
            throw new OrderExecutionException("Execution bean can't update", e);
        } finally {
            session.close();
        }
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void insert(CacheObject co) throws OrderExecutionException {
        logger.info("Insert ExecutionBean to the Database");
        if (co == null || co.getPrimaryKeyObject() == null || "".equals(co.getPrimaryKeyObject())) {
            throw new OrderExecutionException("The Execution to insert is null");
        }
        Transaction tx;
        Session session = sessionFactory.openSession();
        try {
            ExecutionBean eb = (ExecutionBean) co;
            tx = session.beginTransaction();
            session.save(eb);
            tx.commit();
            lastUpdateTime = new Date();
            logger.debug("Inserting to the Database successful, ExecutionBean : " + eb.getExecutionId());
        } catch (Exception e) {
            logger.error("problem in adding execution", e);
            throw new OrderExecutionException("problem in adding execution", e);
        } finally {
            session.close();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CacheObject load(CacheObject co) throws OrderExecutionException {
        logger.info("Get given ExecutionBean from the Database");
        if (co == null || co.getPrimaryKeyObject() == null || "".equals(co.getPrimaryKeyObject())) {
            logger.debug("Validation fails ExecutionBean");
            throw new OrderExecutionException("The Execution to load is null");
        }
        CacheObject execution = null;
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.ExecutionBean C WHERE C.executionKey = :execution_key";
            Query query = session.createQuery(hql);
            query.setParameter("execution_key", co.getPrimaryKeyObject().toString());
            execution = (ExecutionBean) query.uniqueResult();     //rajith
            logger.debug("Retrieving Execution successful, ExecutionKey : " + co.getPrimaryKeyObject());
        } catch (Exception e) {
            logger.error("Execution Bean Can't Load", e);
            throw new OrderExecutionException("Execution bean can't load", e);
        } finally {
            session.close();
        }
        return execution;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<CacheObject> loadAll() {
        logger.info("Load all Executions from the Database");
        List<CacheObject> cashObjectLst = new ArrayList<CacheObject>();
        Session session = sessionFactory.openSession();
        String hql = "FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.ExecutionBean";
        try {
            Query query = session.createQuery(hql);
            List results = query.list();
            for (Object eb : results) {
                ExecutionBean execution = (ExecutionBean) eb;
                cashObjectLst.add(execution);
            }
        } catch (Exception e) {
            logger.error("Error in loading all executions", e);
        } finally {
            session.close();
        }
        return cashObjectLst;
    }

    @Override
    public List<CacheObject> loadPartially() {
        return new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void remove(CacheObject co) throws OrderExecutionException {
        logger.info("Remove ExecutionBean from the Database");
        if (co == null || co.getPrimaryKeyObject() == null || "".equals(co.getPrimaryKeyObject())) {
            logger.debug("Validation for given ExecutionBean fails");
            throw new OrderExecutionException("The execution to remove can't be null");
        }
        Transaction tx;
        Session session = sessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            String hql = "FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.ExecutionBean C WHERE C.executionKey = :execution_key";
            Query query = session.createQuery(hql);
            query.setParameter("execution_key", co.getPrimaryKeyObject().toString());
            ExecutionBean eb = (ExecutionBean) query.list().get(0);
            session.delete(eb);
            tx.commit();
            logger.debug("ExecutionBean successfully removed");
        } catch (Exception e) {
            logger.error("Error in remove execution", e);
            throw new OrderExecutionException("Error in remove execution", e);
        } finally {
            session.close();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean changeToHistoryMode() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean changeToPresentMode() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Execution> getExecutionsByOrdNo(String ordNo) throws OrderExecutionException {
        logger.info("Get Execution Map for given Order Number : " + ordNo);
        List<Execution> execList = new ArrayList<Execution>();
        if (ordNo == null || "".equals(ordNo)) {
            logger.debug("Order Number Can't be Null to Load Executions");
            throw new OrderExecutionException("Order Number Can't be Null to Load Executions");
        }

        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.ExecutionBean C WHERE C.orderNo = :ord_No";
            Query query = session.createQuery(hql);
            query.setParameter("ord_No", ordNo);
            execList = query.list();
        } catch (Exception e) {
            logger.error("Error in getting executions by order number", e);
            throw new OrderExecutionException("Error in getting executions by order number", e);
        } finally {
            session.close();
        }
        return execList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Execution> getExecutionsByClOrdId(String clOrdId) throws OrderExecutionException {
        logger.info("Get Execution Map for given ClOrdId : " + clOrdId);
        List<Execution> execList = new ArrayList<Execution>();
        if (clOrdId == null || "".equals(clOrdId)) {
            logger.debug("ClOrdId Can't be Null to Load Executions");
            throw new OrderExecutionException("ClOrdId Can't be Null to Load Executions");
        }

        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.ExecutionBean C WHERE C.clOrdID = :clOrdId";
            Query query = session.createQuery(hql);
            query.setParameter("clOrdId", clOrdId);
            execList = query.list();
        } catch (Exception e) {
            logger.error("Error in getting executions by clOrdId", e);
            throw new OrderExecutionException("Error in getting executions by clOrdId", e);
        } finally {
            session.close();
        }
        return execList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Execution getExecutionByBackOfficeId(String backOfficeId) throws OrderExecutionException {
        logger.info("Get Execution by back office id : " + backOfficeId);
        ExecutionBean executionBean;
        if (backOfficeId == null || "".equals(backOfficeId)) {
            logger.debug("backOfficeID Can't be Null to Load Executions");
            throw new OrderExecutionException("backOfficeID Can't be Null to Load Executions");
        }

        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.ExecutionBean C WHERE C.backOfficeId = :BackOfficeId";
            Query query = session.createQuery(hql);
            query.setParameter("BackOfficeId", backOfficeId);
            executionBean = (ExecutionBean) query.uniqueResult();

            if (executionBean == null) {
                logger.debug("ExecutionHibernatePersister::getExecutionByBackOfficeId --> Debug getting execution from data source");
                throw new OrderExecutionException("ExecutionHibernatePersister::getExecutionByBackOfficeId --> Error getting execution from data source");

            }
            executionBean.setPrimaryKeyObject(executionBean.getExecutionKey());
        } catch (Exception e) {
            logger.error("Error in getting executions by backOfficeId", e);
            throw new OrderExecutionException("Error in getting executions by backOfficeId", e);
        } finally {
            session.close();
        }
        return executionBean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertList(List<CacheObject> objectList) {
        logger.info("Inserting ExecutionBeans in the Database");
        if (objectList == null) {
            return;
        }
        Transaction tx;
        Session session = sessionFactory.openSession();
        try {
            int i = 0;
            tx = session.beginTransaction();
            ExecutionBean eb;
            for (CacheObject co : objectList) {
                co.setDirty(false);
                co.setNew(false);
                eb = (ExecutionBean) co;
                session.save(eb);
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
            logger.error("Error in Insert List : ", e);
        } finally {
            session.close();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateList(List<CacheObject> updateList) {
        logger.info("Update ExecutionBeans in the Database");
        if (updateList == null) {
            return;
        }
        Transaction tx;
        Session session = sessionFactory.openSession();
        try {
            int i = 0;
            tx = session.beginTransaction();
            ExecutionBean eb;
            for (CacheObject co : updateList) {
                co.setDirty(false);
                eb = (ExecutionBean) co;
                session.saveOrUpdate(eb);
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
            logger.error("Error in Update List : ", e);
        } finally {
            session.close();
        }
    }

}
