package lk.ac.ucsc.oms.fixConnection.implGeneral.persistantImpl.hibernate;

import lk.ac.ucsc.oms.cache.api.AbstractCachePersister;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.common_utility.api.enums.RecordStatus;
import lk.ac.ucsc.oms.fixConnection.api.exceptions.FIXConnectionException;
import lk.ac.ucsc.oms.fixConnection.implGeneral.beans.FIXConnectionBean;
import lk.ac.ucsc.oms.fixConnection.implGeneral.persistantImpl.FIXConnectionPersister;
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


public class FIXConnectionHibernatePersister extends AbstractCachePersister implements FIXConnectionPersister {
    private static final int BATCH_SIZE = 20;
    private static Logger logger = LogManager.getLogger(FIXConnectionHibernatePersister.class);
    private static Date lastUpdateTime;


    public FIXConnectionHibernatePersister(SessionFactory realTime, SessionFactory history) {
        super(realTime, history);
    }


    @Override
    public synchronized void update(CacheObject co) throws FIXConnectionException {
        logger.info("Updating fix connection to DB -{}", co);
        Transaction tx;
        FIXConnectionBean fb = (FIXConnectionBean) co;
        Session session = realTimeSessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(fb);
            tx.commit();
            logger.debug("Persisting updated fix connection cache object success");
        } catch (Exception e) {
            logger.error("Fix connection bean can't update", e);
            logger.error("Fix connection bean - update - ", new Gson().toJson(co));
            throw new FIXConnectionException(e.getMessage(), e);
        } finally {
            session.close();
        }
    }


    @Override
    public synchronized void insert(CacheObject co) throws FIXConnectionException {
        logger.info("Inserting fix connection to DB -{}", co);
        Transaction tx;
        FIXConnectionBean fb = (FIXConnectionBean) co;
        Session session = sessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            session.save(fb);
            tx.commit();
            logger.debug("Inserting fix connection cache object into data source ");
        } catch (Exception e) {
            logger.error("Fix connection bean insertion fail", e);
            logger.error("Fix connection - Insert -", new Gson().toJson(co));
            throw new FIXConnectionException(e.getMessage(), e);
        } finally {
            session.close();
        }
    }


    @Override
    public CacheObject load(CacheObject co) throws FIXConnectionException {
        logger.info("loading fix connection to DB -{}", co);
        FIXConnectionBean fb = (FIXConnectionBean) co;
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "FROM lk.ac.ucsc.oms.fixConnection.implGeneral.beans.FIXConnectionBean C WHERE C.connectionID = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", fb.getConnectionID());
            fb = (FIXConnectionBean) query.uniqueResult();
            if (fb != null) {
                fb.setPrimaryKeyObject(fb.getConnectionID());
                logger.debug("Loading fix connection bean from data source success -{}", fb);
            }
        } catch (Exception e) {
            logger.error("Error in loading fix connection from data source", e.getMessage(), e);
            throw new FIXConnectionException("Error in loading fix connection object from data source", e);
        } finally {
            session.close();
        }
        logger.info("Loading fix connection bean success - result -{}", fb);
        return fb;
    }

    @Override
    public List<CacheObject> loadAll() {
        logger.info("Loading all fix connection beans from data source");
        List<CacheObject> connectionList = new ArrayList<CacheObject>();

        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "FROM lk.ac.ucsc.oms.fixConnection.implGeneral.beans.FIXConnectionBean";
            Query query = session.createQuery(hql);
            List results = query.list();
            for (Object o : results) {
                FIXConnectionBean fb = (FIXConnectionBean) o;
                fb.setPrimaryKeyObject(fb.getConnectionID());
                connectionList.add(fb);
            }
            logger.debug("Loading all fix connection bean from data source success - list size -{}", connectionList.size());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        logger.info("Loading all fix connection beans from data source success - list size :-{} ", connectionList.size());
        return connectionList;
    }

    @Override
    public List<CacheObject> loadPartially() {
        return new ArrayList<>();
    }

    @Override
    public void remove(CacheObject co) throws FIXConnectionException {
        logger.info("Changing fix connection status into deleted");
        FIXConnectionBean fb = (FIXConnectionBean) co;
        fb.setStatus(RecordStatus.DELETED);
        Transaction tx;
        Session session = realTimeSessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(fb);
            tx.commit();
            logger.debug("Changing fix connection status into deleted success");
        } catch (Exception e) {
            logger.error("Error in changing fix connection status into deleted ", e);
            throw new FIXConnectionException("Error in changing fix connection status into deleted", e);
        } finally {
            session.close();
        }
    }


    public void insertList(List<CacheObject> objectList) {
        logger.info("Persisting fix connection cache objects list into data source");
        if (objectList == null) {
            return;
        }
        Transaction tx;

        Session session = sessionFactory.openSession();
        long startTime = System.currentTimeMillis();
        try {
            int i = 0;
            tx = session.beginTransaction();
            FIXConnectionBean fixConnectionBean;
            for (CacheObject co : objectList) {
                co.setDirty(false);
                co.setNew(false);
                fixConnectionBean = (FIXConnectionBean) co;
                session.save(fixConnectionBean);
                if (i % BATCH_SIZE == 0) { //20, same as the JDBC batch size
                    //flush a batch of inserts and release memory:
                    session.flush();
                    session.clear();
                }
                i++;
            }
            tx.commit();
            logger.debug("Persisting fix connection cache objects list into data source success");
            lastUpdateTime = new Date();
            long endTime = System.currentTimeMillis();
            logger.debug("Time to commit fix connection insertion process >>>" + (endTime - startTime));
        } catch (Exception e) {
            logger.error("Inserting Process: Fix connection bean list Can't insert, Error : " + e.getMessage(), e);
            for (CacheObject co : objectList) {
                logger.error("Fix Connection Bean -Insert : {}", new Gson().toJson(co));
            }
            //No need to throw exception here. Since this method is call by schedulers to persist the data to DB
        } finally {
            session.close();
        }
        logger.info("Persisting fix connection cache objects list into data source success");
    }

    @Override
    public void updateList(List<CacheObject> updateList) {
        logger.info("Persisting updated fix connection cache object list into data source");
        if (updateList == null) {
            return;
        }
        Transaction tx;
        // Update doesn't happen in history. Therefore real time session factory can use
        Session session = realTimeSessionFactory.openSession();
        long startTime = System.currentTimeMillis();
        try {
            int i = 0;
            tx = session.beginTransaction();
            FIXConnectionBean fixConnectionBean;
            for (CacheObject co : updateList) {
                co.setDirty(false);
                fixConnectionBean = (FIXConnectionBean) co;
                session.saveOrUpdate(fixConnectionBean);
                if (i % BATCH_SIZE == 0) { //20, same as the JDBC batch size
                    //flush a batch of inserts and release memory:
                    session.flush();
                    session.clear();
                }
                i++;
            }
            tx.commit();
            logger.debug("Persisting updated fix connection objects success");
            long endTime = System.currentTimeMillis();
            lastUpdateTime = new Date();
            logger.debug("Time to commit fix connection updating process >>>" + (endTime - startTime));
        } catch (Exception e) {
            logger.error("Updating Process: Fix connection Bean list Can't Update, Error : " + e.getMessage(), e);
            for (CacheObject co : updateList) {
                logger.error("Fix connection -update -", new Gson().toJson(co));
            }
            //No need to throw exception here. Since this method is call by schedulers to persist the data to DB
        } finally {
            session.close();
        }
        logger.info("Persisting updated fix connection object list success");
    }


}
