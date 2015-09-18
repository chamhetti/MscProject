package lk.ac.ucsc.oms.orderMgt.implGeneral.persistantImpl.hibernate;

import lk.ac.ucsc.oms.cache.api.AbstractCachePersister;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;

import lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ErrorOrderPersister extends AbstractCachePersister {
    private static Logger logger = LogManager.getLogger(ErrorOrderPersister.class);
    private static Date lastUpdateTime;

    /**
     * @param sessionFactory SessionFactory
     */
    public ErrorOrderPersister(SessionFactory sessionFactory) {
        super(sessionFactory);
    }



    @Override
    public void update(CacheObject co) throws OMSException {
        logger.info("Updating the constraint violated order in the DB: {}", ((OrderBean) co).getClOrdID());
        Transaction tx;
        Session session = sessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            session.update(co);
            tx.commit();
            lastUpdateTime = new Date();
            logger.info("Updating the constraint violated order in the Database successful, OrderBean : " + co.getPrimaryKeyObject());
        } catch (Exception e) {
            logger.error("problem in adding error order to the database", e);
        } finally {
            session.close();
        }
    }

    @Override
    public void insert(CacheObject co) throws OMSException {
        logger.info("Insert Error Occurred Order to the database: " + ((OrderBean) co).getClOrdID());
        Transaction tx;
        Session session = sessionFactory.openSession();
        try {
            OrderBean ob = (OrderBean) co;
            tx = session.beginTransaction();
            session.save(ob);
            tx.commit();
            lastUpdateTime = new Date();
            logger.info("Inserting Error Occurred to the Database successful, OrderBean : " + ob.getClOrdID());
        } catch (Exception e) {
            logger.error("problem in adding error order to the database", e);
        } finally {
            session.close();
        }
    }

    @Override
    public CacheObject load(CacheObject co) throws OMSException {
        return null;
    }

    @Override
    public List<CacheObject> loadAll() {
        return new ArrayList<>();
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
        logger.error("Operation is not implemented");
    }

    @Override
    public void updateList(List<CacheObject> updateList) {
        logger.error("Operation is not implemented");
    }

    private long getTotalRecordCount() {
        return 0;
    }


}
