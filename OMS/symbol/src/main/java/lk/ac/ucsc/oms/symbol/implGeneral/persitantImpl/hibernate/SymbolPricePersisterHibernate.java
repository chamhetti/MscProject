package lk.ac.ucsc.oms.symbol.implGeneral.persitantImpl.hibernate;

import lk.ac.ucsc.oms.cache.api.AbstractCachePersister;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.symbol.api.exceptions.SymbolManageException;
import lk.ac.ucsc.oms.symbol.api.exceptions.SymbolPriceException;
import lk.ac.ucsc.oms.symbol.implGeneral.beans.SymbolPriceDataBean;
import lk.ac.ucsc.oms.symbol.implGeneral.persitantImpl.SymbolPricePersistor;
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


public class SymbolPricePersisterHibernate extends AbstractCachePersister implements SymbolPricePersistor {
    private final static int BATCH_SIZE = 20;
    private static Logger logger = LogManager.getLogger(SymbolPricePersisterHibernate.class);
    private static Date lastUpdateTime;

    /**
     * set the real time and history session factories. default session factory is real time session factory
     *
     * @param realTime
     * @param history
     */
    public SymbolPricePersisterHibernate(SessionFactory realTime, SessionFactory history) {
        super(realTime, history);
    }

  
    /**
     * {@inheritDoc}
     */
    @Override
    public void update(CacheObject co) throws OMSException {
        Transaction tx;
        if (co == null) {
            throw new SymbolPriceException("error, given bean is null");
        }
        Session session = realTimeSessionFactory.openSession();
        try {

            logger.debug("updating the Symbol price data bean: {}", co);
            tx = session.beginTransaction();
            session.saveOrUpdate(co);
            tx.commit();
            logger.debug("updating finished");
        } catch (Exception e) {
            logger.error("Symbol Price Bean Can't Update", e);
            logger.error("Symbol Price -update -", new Gson().toJson(co));
            throw new SymbolPriceException(e.getMessage(), e);
        } finally {
            session.close();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(CacheObject co) throws OMSException {
        Transaction tx;
        if (co == null) {
            throw new SymbolPriceException("error, given bean is null");
        }
        Session session = sessionFactory.openSession();
        try {
            logger.info("Inserting the Symbol price data bean:{} " + co);
            logger.debug("Inserting the Symbol price data bean:{} ", co);
            tx = session.beginTransaction();
            session.save(co);
            tx.commit();
            logger.debug("Inserting CSSymbol bean finished");
        } catch (Exception e) {
            logger.error("Symbol Price Bean Already Exist", e);
            logger.error("Symbol Price -Insert -", new Gson().toJson(co));
            throw new SymbolPriceException(e.getMessage(), e);
        } finally {
            session.close();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CacheObject load(CacheObject co) throws OMSException {
        Transaction tx;
        SymbolPriceDataBean symbolBean = null;
        if (co == null) {
            throw new SymbolPriceException("error, given bean is null");
        }
        Session session = realTimeSessionFactory.openSession();
        try {
            String symbol = ((SymbolPriceDataBean) co).getSymbolCode();
            String exchange = ((SymbolPriceDataBean) co).getExchangeCode();
            logger.info("Loading the Symbol price data with symbol:{} exchange:{}", symbol, exchange);
            if (symbol == null || exchange == null || "".equals(symbol) || "".equalsIgnoreCase(exchange)) {
                logger.warn("info provided not enough to load from DB");
                throw new SymbolManageException("Symbol info provided not enough to load from DB");
            }
            String hql = "FROM lk.ac.ucsc.oms.symbol.implGeneral.beans.SymbolPriceDataBean C WHERE C.symbolCode = :symbol and C.exchangeCode= :exchange";
            Query query = session.createQuery(hql);
            query.setParameter("symbol", symbol);
            query.setParameter("exchange", exchange);
            symbolBean = (SymbolPriceDataBean) query.uniqueResult();
            if (symbolBean != null) {
                symbolBean.setPrimaryKeyObject(co.getPrimaryKeyObject());
                symbolBean.setLoadedToCache(1);
                tx = session.beginTransaction();
                session.update(symbolBean);
                tx.commit();
            }
            logger.info("Loaded Symbol price data:{}", symbolBean);
        } catch (Exception e) {
            logger.error("Error in loading symbol price data from data source ", e.getMessage(), e);
            throw new SymbolPriceException("Issue in loading object from physical storage", e);
        } finally {
            session.close();
        }
        return symbolBean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CacheObject> loadAll() {
        logger.info("Loading all the Symbol price data from DB");
        List<CacheObject> cashObjectLst = new ArrayList<>();
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "FROM lk.ac.ucsc.oms.symbol.implGeneral.beans.SymbolPriceDataBean";
            Query query = session.createQuery(hql);
            List results = query.list();

            for (Object cgb : results) {
                SymbolPriceDataBean symbolBean = (SymbolPriceDataBean) cgb;
                symbolBean.setPrimaryKeyObject(symbolBean.getExchangeCode() + "-" + symbolBean.getSymbolCode());
                cashObjectLst.add(symbolBean);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        logger.debug("Loaded Symbol price data list of size:{} and list:{}", cashObjectLst.size(), cashObjectLst);
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
    public void remove(CacheObject co) throws OMSException {
        logger.info("Removing the Symbol price data:{} from DB", co);
        if (co == null) {
            throw new SymbolManageException("symol to remove is null");
        }
        SymbolPriceDataBean symbolBean = (SymbolPriceDataBean) co;
        Session session = realTimeSessionFactory.openSession();
        try {
            session.delete(symbolBean);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SymbolPriceException("error in removing symbol from the physical media ", e);
        } finally {
            session.close();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SymbolPriceDataBean> getLoadedPriceDataList() throws SymbolPriceException {
        logger.debug("Loading all the CSSymbol from DB");
        List<SymbolPriceDataBean> cashObjectLst = new ArrayList<>();
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "FROM lk.ac.ucsc.oms.symbol.implGeneral.beans.SymbolPriceDataBean C where C.loadedToCache=1 ";
            Query query = session.createQuery(hql);
            List results = query.list();

            for (Object cgb : results) {
                SymbolPriceDataBean symbolBean = (SymbolPriceDataBean) cgb;
                symbolBean.setPrimaryKeyObject(symbolBean.getExchangeCode() + "-" + symbolBean.getSymbolCode());
                cashObjectLst.add(symbolBean);
            }
        } catch (Exception e) {
            logger.error("Error in  getting loaded price data list from data source ", e.getMessage(), e);
            throw new SymbolPriceException("Issue in getting loaded price data list from physical storage", e);
        } finally {
            session.close();
        }
        logger.debug("Loaded CSSymbols list of size:{} and list:{}", cashObjectLst.size(), cashObjectLst);
        return cashObjectLst;
    }


    public void insertList(List<CacheObject> objectList) {
        logger.info("Persisting CS Symbol cache objects list into data source");
        if (objectList == null) {
            return;
        }
        Transaction tx;
        Session session = sessionFactory.openSession();
        long startTime = System.currentTimeMillis();
        try {
            int i = 0;
            tx = session.beginTransaction();
            SymbolPriceDataBean csSymbolBean;
            for (CacheObject co : objectList) {
                co.setDirty(false);
                co.setNew(false);
                csSymbolBean = (SymbolPriceDataBean) co;
                session.save(csSymbolBean);
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
            logger.debug("Time to commit cs symbol insertion process >>>" + (endTime - startTime));
        } catch (Exception e) {
            logger.error("Inserting Process: CS Symbol Bean list Can't insert, Error : " + e.getMessage(), e);
            for (CacheObject co : objectList) {
                logger.error("CS Symbol -Insert -", new Gson().toJson(co));
            }
        } finally {
            session.close();
        }
    }

    @Override
    public void updateList(List<CacheObject> updateList) {
        logger.info("Persisting updated CS Symbol cache object list into data source");
        if (updateList == null) {
            return;
        }
        Transaction tx;
        Session session = realTimeSessionFactory.openSession();
        long startTime = System.currentTimeMillis();
        try {
            int i = 0;
            tx = session.beginTransaction();
            SymbolPriceDataBean csSymbolBean;
            for (CacheObject co : updateList) {
                co.setDirty(false);
                csSymbolBean = (SymbolPriceDataBean) co;
                session.saveOrUpdate(csSymbolBean);
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
            logger.debug("Time to commit cs symbol updating process >>>" + (endTime - startTime));
        } catch (Exception e) {
            logger.error("Updating Process: CS Symbol Bean list Can't Update, Error : " + e.getMessage(), e);
            for (CacheObject co : updateList) {
                logger.error("CS Symbol -update -", new Gson().toJson(co));
            }
        } finally {
            session.close();
        }
    }

    private long getTotalRecordCount() {
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "select count(*) FROM lk.ac.ucsc.oms.symbol.implGeneral.beans.SymbolPriceDataBean ";
            Query query = session.createQuery(hql);
            Long totalCount = (Long) query.uniqueResult();
            return totalCount;
        } catch (Exception e) {
            logger.error("Error occurred  while getting total record count ", e);
        } finally {
            session.close();
        }
        return 0;
    }


    @Override
    public void reSetCacheLoadedStatus() {
        Transaction tx;
        Session session = realTimeSessionFactory.openSession();
        try {
            tx = session.beginTransaction();
            String sqlUpdateStatus = "update esp_todays_snapshots set loadedtocache=0";
            Query query = session.createSQLQuery(sqlUpdateStatus);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            logger.error("Updating Process: CS Symbol Bean list Can't Update, Error : " + e.getMessage(), e);

        } finally {
            session.close();
        }
    }

}
