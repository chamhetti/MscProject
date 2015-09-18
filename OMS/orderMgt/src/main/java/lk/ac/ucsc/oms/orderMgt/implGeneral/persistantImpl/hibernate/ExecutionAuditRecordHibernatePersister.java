package lk.ac.ucsc.oms.orderMgt.implGeneral.persistantImpl.hibernate;

import lk.ac.ucsc.oms.cache.api.AbstractCachePersister;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.orderMgt.api.beans.ExecutionAuditRecord;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.ExecutionAuditRecordException;
import lk.ac.ucsc.oms.orderMgt.implGeneral.bean.ExecutionAuditRecordBean;
import lk.ac.ucsc.oms.orderMgt.implGeneral.persistantImpl.ExecutionAuditRecordPersister;

import org.apache.log4j.LogManager;
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

public class ExecutionAuditRecordHibernatePersister extends AbstractCachePersister implements ExecutionAuditRecordPersister {
    private static Logger logger = LogManager.getLogger(ExecutionAuditRecordHibernatePersister.class);
    private static Date lastUpdateTime;


    public ExecutionAuditRecordHibernatePersister(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    @Override
    public void update(CacheObject co) throws OMSException {
        throw new OMSException("Operation is not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void insert(CacheObject co) throws ExecutionAuditRecordException {
        logger.info("Insert Execution Audit Record to the Database");
        if (co == null || co.getPrimaryKeyObject() == null || "".equals(co.getPrimaryKeyObject())) {
            throw new ExecutionAuditRecordException("The Execution Audit Record is null");
        }
        Transaction tx;
        Session session = sessionFactory.openSession();
        try {
            ExecutionAuditRecordBean eb = (ExecutionAuditRecordBean) co;
            tx = session.beginTransaction();
            session.save(eb);
            tx.commit();
            lastUpdateTime = new Date();
        } catch (Exception e) {
            logger.error("problem in adding Execution Audit Record", e);
            throw new ExecutionAuditRecordException("Problem in adding Execution Audit Record. Already Exist", e);
        } finally {
            session.close();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CacheObject load(CacheObject co) throws ExecutionAuditRecordException {
        logger.debug("Operation is not implemented");
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<CacheObject> loadAll() {
        logger.info("Load all Execution Audit Records from the Database");
        List<CacheObject> cashObjectLst = new ArrayList<CacheObject>();
        Session session = sessionFactory.openSession();
        String hql = "FROM lk.ac.ucsc.oms.orderMgt.implGeneral.bean.ExecutionAuditRecordBean";
        try {
            Query query = session.createQuery(hql);
            List results = query.list();
            for (Object eb : results) {
                ExecutionAuditRecordBean execution = (ExecutionAuditRecordBean) eb;
                cashObjectLst.add(execution);
            }
        } catch (Exception e) {
            logger.error("Error in loading all Execution Audit Records", e);
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
        throw new OMSException("Operation is not implemented");
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
        logger.debug("Operation is not implemented");
    }

    @Override
    public void updateList(List<CacheObject> updateList) {
        logger.debug("Operation is not implemented");
    }

    private long getTotalRecordCount() {
        return 0;
    }



    @Override
    public void writeExecutionToDB(ExecutionAuditRecord executionAuditRecord) throws ExecutionAuditRecordException {
        logger.info("Insert Execution Audit Record to the Database");
        if (executionAuditRecord == null) {
            throw new ExecutionAuditRecordException("The Execution Audit Record is null");
        }
        Transaction tx;
        Session session = sessionFactory.openSession();
        try {
            ExecutionAuditRecordBean eb = (ExecutionAuditRecordBean) executionAuditRecord;
            tx = session.beginTransaction();
            session.save(eb);
            tx.commit();
        } catch (Exception e) {
            logger.error("problem in adding Execution Audit Record", e);
            throw new ExecutionAuditRecordException("Problem in adding Execution Audit Record. Already Exist", e);
        } finally {
            session.close();
        }
    }
}
