package lk.ac.ucsc.oms.symbol.implGeneral.persitantImpl.hibernate.activeSymbol;

import lk.ac.ucsc.oms.cache.api.AbstractCachePersister;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;

import lk.ac.ucsc.oms.symbol.api.beans.BaseSymbol;
import lk.ac.ucsc.oms.symbol.api.beans.SymbolMarginInfo;
import lk.ac.ucsc.oms.symbol.api.exceptions.SymbolManageException;
import lk.ac.ucsc.oms.symbol.api.exceptions.SymbolNotFoundException;
import lk.ac.ucsc.oms.symbol.implGeneral.beans.commonStok.CSSymbolBean;
import lk.ac.ucsc.oms.symbol.implGeneral.persitantImpl.SymbolPersistor;

import lk.ac.ucsc.oms.symbol.implGeneral.util.SymbolKeyManager;
import com.google.gson.Gson;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;


public class CSSymbolHibernatePersister extends AbstractCachePersister implements SymbolPersistor {
    private static final int BATCH_SIZE = 20;
    private static Logger logger = LogManager.getLogger(CSSymbolHibernatePersister.class);
    private static Date lastUpdateTime;


    /**
     * Constructor initialization with real time and history session factory
     *
     * @param realTime session factory
     * @param history  session factory
     */
    public CSSymbolHibernatePersister(SessionFactory realTime, SessionFactory history) {
        super(realTime, history);
    }



    

    /**
     * use to persist updated cache object in to the relational database
     *
     * @param co CacheObject
     */
    @Override
    public void update(CacheObject co) throws OMSException {
        Transaction tx;
        if (co == null) {
            throw new SymbolManageException("CSSymbol to update cannot be null");
        }
        Session session = realTimeSessionFactory.openSession();
        try {
            logger.debug("updating the CSSymbol bean: {}", co);
            tx = session.beginTransaction();
            session.saveOrUpdate(co);
            tx.commit();
            logger.debug("updating finished");
        } catch (Exception e) {
            logger.error("CSSymbol Bean Can't Update", e);
            logger.error("CSSymbol -update -", new Gson().toJson(co));
            throw new SymbolManageException(e.getMessage(), e);
        } finally {
            session.close();
        }

    }

    /**
     * use to persist newly created cached objects in to the relational database
     *
     * @param co CacheObject
     */
    @Override
    public void insert(CacheObject co) throws OMSException {
        Transaction tx;
        if (co == null) {
            throw new SymbolManageException("CSSymbol to update cannot be null");
        }
        Session session = sessionFactory.openSession();
        try {
            logger.info("Inserting the CSSymbol bean:{} " + co);
            logger.debug("Inserting the CSSymbol bean:{} ", co);
            tx = session.beginTransaction();
            session.save(co);
            tx.commit();
            logger.debug("Inserting CSSymbol bean finished");
        } catch (Exception e) {
            logger.error("CSSymbol Bean Already Exist", e);
            logger.error("CSSymbol -Insert -", new Gson().toJson(co));
            throw new SymbolManageException(e.getMessage(), e);
        } finally {
            session.close();
        }
    }

    /**
     * use to retrieve commission group object in to the cache when its not existing in cache
     *
     * @param co CacheObject
     */
    @Override
    public CacheObject load(CacheObject co) throws OMSException {
        CSSymbolBean symbolBean = null;
        if (co == null) {
            throw new SymbolManageException("Symbol info provided not enough to load from DB");
        }
        Session session = realTimeSessionFactory.openSession();
        try {
            String symbol = SymbolKeyManager.getSymbolFromKey((String) co.getPrimaryKeyObject());
            String exchange = SymbolKeyManager.getExchangeFromKey((String) co.getPrimaryKeyObject());
            logger.info("Loading the symbol with symbol:{} exchange:{}", symbol, exchange);
            if (symbol == null || exchange == null || "".equals(symbol) || "".equalsIgnoreCase(exchange)) {
                logger.warn("Symbol info provided not enough to load from DB");
                throw new SymbolManageException("Symbol info provided not enough to load from DB");
            }
            String hql = "FROM lk.ac.ucsc.oms.symbol.implGeneral.beans.commonStok.CSSymbolBean C WHERE C.symbol = :symbol and C.securityExchange= :exchange";
            Query query = session.createQuery(hql);
            query.setParameter("symbol", symbol);
            query.setParameter("exchange", exchange);
            symbolBean = (CSSymbolBean) query.uniqueResult();
            if (symbolBean != null) {
                symbolBean.setPrimaryKeyObject(co.getPrimaryKeyObject());
            }
            logger.info("Loaded CSSymbol:{}", symbolBean);
        } catch (Exception e) {
            logger.error("Error in loading symbol data from data source ", e.getMessage(), e);
            throw new SymbolManageException("Issue in loading object from physical storage", e);
        } finally {
            session.close();
        }
        return symbolBean;
    }

    /**
     * use to retrieve all commission stores in relational database into the cache
     */
    @Override
    public List<CacheObject> loadAll() {
        logger.info("Loading all the CSSymbol from DB");
        List<CacheObject> cashObjectLst = new ArrayList<>();
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "FROM lk.ac.ucsc.oms.symbol.implGeneral.beans.commonStok.CSSymbolBean";
            Query query = session.createQuery(hql);
            List results = query.list();

            for (Object cgb : results) {
                CSSymbolBean symbolBean = (CSSymbolBean) cgb;
                symbolBean.setPrimaryKeyObject(SymbolKeyManager.getSymbolKey(symbolBean));
                cashObjectLst.add(symbolBean);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        logger.debug("Loaded CSSymbols list of size:{} and list:{}", cashObjectLst.size(), cashObjectLst);
        return cashObjectLst;
    }

    @Override
    public List<CacheObject> loadPartially() {
        return new ArrayList<>();
    }

    /**
     * use to remove commission group from relational database
     *
     * @param co CacheObject
     */
    @Override
    public void remove(CacheObject co) throws OMSException {
        logger.info("Removing the CSSymbol:{} from DB", co);
        if (co == null) {
            throw new SymbolManageException("symol to remove is null");
        }
        CSSymbolBean symbolBean = (CSSymbolBean) co;
        symbolBean.setStatus(BaseSymbol.SymbolStatus.DELETED);
        update(symbolBean);

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
            CSSymbolBean csSymbolBean;
            for (CacheObject co : objectList) {
                co.setDirty(false);
                co.setNew(false);
                csSymbolBean = (CSSymbolBean) co;
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
            CSSymbolBean csSymbolBean;
            for (CacheObject co : updateList) {
                co.setDirty(false);
                csSymbolBean = (CSSymbolBean) co;
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
            String hql = "select count(*) FROM lk.ac.ucsc.oms.symbol.implGeneral.beans.commonStok.CSSymbolBean ";
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



    /**
     * get the symbol key in cache corresponding to the give isin
     *
     * @param isin ISIN of the symbol
     * @return symbol key in cache
     * @throws SymbolNotFoundException exception
     */
    public String getSymbolKeyFromISIN(String isin) throws SymbolNotFoundException {
        logger.info("loading CSSymbol bean using ISIN:{}", isin);
        if (isin == null || "".equalsIgnoreCase(isin)) {
            throw new SymbolNotFoundException("ISIN can't be null or empty");
        }
        Session session = realTimeSessionFactory.openSession();
        CSSymbolBean symbolBean;
        String key = null;
        try {
            String hql = "FROM lk.ac.ucsc.oms.symbol.implGeneral.beans.commonStok.CSSymbolBean C WHERE C.isinCode = :isin";
            Query query = session.createQuery(hql);
            query.setParameter("isin", isin);
            symbolBean = (CSSymbolBean) query.uniqueResult();
            if (symbolBean != null) {
                key = SymbolKeyManager.getSymbolKey(symbolBean);
            }
            logger.info("key of the loaded symbol:{}", key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SymbolNotFoundException("Error occurred while getting the key ", e);
        } finally {
            session.close();
        }
        return key;
    }

    /**
     * delete a CSSymbol from data source
     *
     * @param symbol BaseSymbol object
     * @return true or false
     */
    @Override
    public void deleteFromDB(BaseSymbol symbol) throws SymbolManageException {
        logger.info("Deleting CSSymbol bean ");
        Transaction tx;
        if (symbol == null) {
            throw new SymbolManageException("symbol to remove cannot be null");
        }
        Session session = realTimeSessionFactory.openSession();
        try {
            logger.debug("Deleting the CSSymbol bean: {}", symbol);
            tx = session.beginTransaction();
            session.delete(symbol);
            tx.commit();
            logger.info("Deleting finished");
        } catch (Exception e) {
            logger.error("CSSymbol bean can't delete", e);
            throw new SymbolManageException("error in removing symbol ", e);
        } finally {
            session.close();
        }
    }

    /**
     * Method use to find out the symbol key using RIC
     *
     * @param ric RIC of the symbol
     * @return symbol key
     * @throws lk.ac.ucsc.oms.symbol.api.exceptions.SymbolNotFoundException exception with reason
     */
    @Override
    public String getSymbolKeyFromRIC(String ric) throws SymbolNotFoundException {
        logger.info("loading CSSymbol bean using RIC:{}", ric);
        if (ric == null || "".equalsIgnoreCase(ric)) {
            throw new SymbolNotFoundException("RIC can't be null or empty");
        }
        Session session = realTimeSessionFactory.openSession();
        CSSymbolBean symbolBean;
        String key = null;
        try {
            String hql = "FROM lk.ac.ucsc.oms.symbol.implGeneral.beans.commonStok.CSSymbolBean C WHERE C.reutercode = :ric";
            Query query = session.createQuery(hql);
            query.setParameter("ric", ric);
            symbolBean = (CSSymbolBean) query.uniqueResult();
            if (symbolBean != null) {
                key = SymbolKeyManager.getSymbolKey(symbolBean);
            }
            logger.info("key of the loaded symbol:{}", key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SymbolNotFoundException("Error occurred while getting the key ", e);
        } finally {
            session.close();
        }
        return key;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLastSymbolId() throws SymbolManageException {
        logger.info("Loading last CS symbol id from DB");
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "select max(C.symbolID) FROM lk.ac.ucsc.oms.symbol.implGeneral.beans.commonStok.CSSymbolBean C ";
            Query query = session.createQuery(hql);

            Object results = query.uniqueResult();
            if (results != null) {
                return results.toString();
            } else {
                return "0";
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "0";
        } finally {
            session.close();
        }
    }

    @Override
    public List<BaseSymbol> getSymbolsForExchange(String exchange) throws SymbolManageException {
        logger.info("Loading all the CSSymbol from DB for Exchange" + exchange);
        if (exchange == null || !"".equalsIgnoreCase(exchange)) {
            throw new SymbolManageException("Exchange cannot be null or empty");
        }
        List<BaseSymbol> cashObjectLst = new ArrayList<>();
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "FROM lk.ac.ucsc.oms.symbol.implGeneral.beans.commonStok.CSSymbolBean symbol WHERE symbol.securityExchange = :exchange";
            Query query = session.createQuery(hql);
            query.setParameter("exchange", exchange);
            List results = query.list();

            for (Object cgb : results) {
                CSSymbolBean symbolBean = (CSSymbolBean) cgb;
                symbolBean.setPrimaryKeyObject(SymbolKeyManager.getSymbolKey(symbolBean));
                cashObjectLst.add(symbolBean);
            }
        } catch (Exception e) {
            logger.error("Error in getting symbol data list from data source ", e.getMessage(), e);
            throw new SymbolNotFoundException("Error in getting symbol data list from physical storage", e);
        } finally {
            session.close();
        }
        logger.debug("Loaded CSSymbols list of size:{} and list:{}", cashObjectLst.size(), cashObjectLst);
        return cashObjectLst;
    }

    @Override
    public List<BaseSymbol> getAllSymbols() throws SymbolManageException {
        logger.info("getting all the CSSymbol from DB");
        List<BaseSymbol> cashObjectLst = new ArrayList<>();
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "FROM lk.ac.ucsc.oms.symbol.implGeneral.beans.commonStok.CSSymbolBean";
            Query query = session.createQuery(hql);
            List results = query.list();

            for (Object cgb : results) {
                CSSymbolBean symbolBean = (CSSymbolBean) cgb;
                symbolBean.setPrimaryKeyObject(SymbolKeyManager.getSymbolKey(symbolBean));
                cashObjectLst.add(symbolBean);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SymbolManageException("error in retrieving all symbols ", e);
        } finally {
            session.close();
        }
        logger.debug("CSSymbols list of size:{} and list:{}", cashObjectLst.size(), cashObjectLst);
        return cashObjectLst;
    }



}
