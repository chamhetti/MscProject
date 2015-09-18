package lk.ac.ucsc.oms.exchanges.implGeneral.persistantImpl.hibernate;

import lk.ac.ucsc.oms.cache.api.AbstractCachePersister;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;

import lk.ac.ucsc.oms.exchanges.api.beans.Exchange;
import lk.ac.ucsc.oms.exchanges.api.exceptions.ExchangeException;
import lk.ac.ucsc.oms.exchanges.implGeneral.beans.ExchangeBean;
import lk.ac.ucsc.oms.exchanges.implGeneral.beans.SubMarketBean;
import lk.ac.ucsc.oms.exchanges.implGeneral.persistantImpl.ExchangePersister;
import org.hibernate.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ExchangePersisterHibernate extends AbstractCachePersister implements ExchangePersister {
    private static final int BATCH_SIZE = 20;
    private static Logger logger = LogManager.getLogger(ExchangePersisterHibernate.class);
    private static Date lastUpdateTime;

    public ExchangePersisterHibernate(SessionFactory realTime, SessionFactory history) {
        super(realTime, history);
    }

    @Override
    public void update(CacheObject co) throws OMSException {
        Transaction tx;
        Session session = realTimeSessionFactory.openSession();
        try {
            if (co == null) {
                return;
            }
            logger.debug("updating the Exchange bean: {}", co);
            tx = session.beginTransaction();
            session.update(co);
            tx.commit();
            lastUpdateTime = new Date();
            logger.debug("updating finished");
        } catch (Exception e) {
            handleFailedCacheObject(co);
            logger.error("Exchange bean can't update", e);
        } finally {
            session.close();
        }

    }

    @Override
    public void insert(CacheObject co) throws OMSException {
        Transaction tx;

        Session session = sessionFactory.openSession();
        try {

            logger.debug("Inserting the Exchange bean:{} ", co);
            if (co == null) {
                return;
            }
            tx = session.beginTransaction();
            session.save(co);
            tx.commit();
            lastUpdateTime = new Date();
            logger.debug("Inserting Exchange bean finished");
        } catch (Exception e) {
            handleFailedCacheObject(co);
            logger.error("problem in adding exchange", e);
            throw new ExchangeException(e.getMessage(), e);
        } finally {
            session.close();
        }
    }


    @Override
    public CacheObject load(CacheObject co) {
        ExchangeBean exchangeBean = null;
        Session session = realTimeSessionFactory.openSession();
        try {
            if (co == null) {
                return null;
            }

            String exchange = (String) co.getPrimaryKeyObject();
            logger.info("Loading the exchange with exchange code:{} ", exchange);
            if (exchange == null || ("").equals(exchange)) {
                logger.warn("Exchange info provided not enough to load from DB");
                return null;
            }
            String hql = "FROM lk.ac.ucsc.oms.exchanges.implGeneral.beans.ExchangeBean C WHERE C.exchangeCode " +
                    "= :exchange";
            Query query = session.createQuery(hql);
            query.setParameter("exchange", exchange);
            exchangeBean = (ExchangeBean) query.uniqueResult();
            if (exchangeBean != null) {
                exchangeBean.setPrimaryKeyObject(exchangeBean.getExchangeCode());
            }
            logger.info("Loaded Exchange:{}", exchangeBean);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            session.close();
        }
        return exchangeBean;
    }


    @Override
    public List<CacheObject> loadAll() {
        logger.info("Loading all the Exchange from DB");
        List<CacheObject> cashObjectLst = new ArrayList<>();
        Session session = realTimeSessionFactory.openSession();
        try {

            String hql = "FROM lk.ac.ucsc.oms.exchanges.implGeneral.beans.ExchangeBean";
            Query query = session.createQuery(hql);
            List results = query.list();

            for (Object cgb : results) {
                ExchangeBean exchangeBean = (ExchangeBean) cgb;
                exchangeBean.setPrimaryKeyObject(exchangeBean.getExchangeCode());
                cashObjectLst.add(exchangeBean);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        logger.debug("Loaded Exchanges list of size:{} and list:{}", cashObjectLst.size(), cashObjectLst);
        return cashObjectLst;
    }

    @Override
    public List<CacheObject> loadPartially() {
        return new ArrayList<>();
    }



    @Override
    public void remove(CacheObject co) throws OMSException {
        logger.info("Removing the Exchange:{} from DB", co);
        if (co == null) {
            return;
        }
        ExchangeBean exchangeBean = (ExchangeBean) co;
        exchangeBean.setStatus(Exchange.ExchangeStatus.DELETED);
        update(exchangeBean);

    }


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
            ExchangeBean exchangeBean;
            for (CacheObject co : objectList) {
                exchangeBean = (ExchangeBean) co;
                try {
                    session.save(exchangeBean);
                    co.setDirty(false);
                    co.setNew(false);
                    if (i % BATCH_SIZE == 0) { //20, same as the JDBC batch size
                        //flush a batch of inserts and release memory:
                        session.flush();
                        session.clear();
                    }
                    i++;
                } catch (HibernateException e) {
                    handleFailedCacheObject(co);
                    logger.error("Inserting Process: Exchange Bean Can't insert, Error : " + e.getMessage(), e);
                }
            }
            tx.commit();
            long endTime = System.currentTimeMillis();
            lastUpdateTime = new Date();
            logger.debug("Time to commit exchange insertion process >>>" + (endTime - startTime));
        } catch (Exception e) {
            logger.error("Inserting Process: Exchange Bean Can't insert, Error : " + e.getMessage(), e);
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
        Session session = realTimeSessionFactory.openSession();
        long startTime = System.currentTimeMillis();
        try {
            int i = 0;
            tx = session.beginTransaction();
            ExchangeBean exchangeBean;
            for (CacheObject co : updateList) {
                exchangeBean = (ExchangeBean) co;
                try {
                    session.saveOrUpdate(exchangeBean);
                    co.setDirty(false);
                    if (i % BATCH_SIZE == 0) { //20, same as the JDBC batch size
                        //flush a batch of inserts and release memory:
                        session.flush();
                        session.clear();
                    }
                    i++;
                } catch (HibernateException e) {
                    handleFailedCacheObject(co);
                    logger.error("Updating Process: Exchange Bean Can't Update, Error : " + e.getMessage(), e);
                }
            }
            tx.commit();
            long endTime = System.currentTimeMillis();
            lastUpdateTime = new Date();
            logger.debug("Time to commit exchange updating process >>>" + (endTime - startTime));
        } catch (Exception e) {
            logger.error("Updating Process: Exchange Bean Can't Update, Error : " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }


    @Override
    public boolean deleteFromDB(ExchangeBean exchange) {
        logger.info("Deleting Exchange bean ");
        Transaction tx;
        Session session = realTimeSessionFactory.openSession();
        try {
            if (exchange == null) {
                return false;
            }
            logger.debug("Deleting the Exchange bean: {}", exchange);
            tx = session.beginTransaction();
            session.delete(exchange);
            tx.commit();
            logger.info("Deleting finished");
        } catch (Exception e) {
            logger.error("Exchange bean can't delete", e);
            return false;
        } finally {
            session.close();
        }

        return true;
    }


    @Override
    public List<String> getAllExchangeCodes() {
        logger.info("Loading all the Exchange codes from DB");
        List<String> exchangeCodeList = new ArrayList<>();
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "FROM lk.ac.ucsc.oms.exchanges.implGeneral.beans.ExchangeBean";
            Query query = session.createQuery(hql);
            List results = query.list();

            for (Object cgb : results) {
                ExchangeBean exchangeBean = (ExchangeBean) cgb;
                exchangeCodeList.add(exchangeBean.getExchangeCode());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            session.close();
        }

        logger.debug("Loaded Exchange code list of size:{} and list:{}", exchangeCodeList.size(), exchangeCodeList);
        return exchangeCodeList;
    }


    @Override
    public String getMubasherExchangeCodes(String exDestination, String exchange) {
        logger.info("Loading all the Exchange codes from DB");
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "FROM lk.ac.ucsc.oms.exchanges.implGeneral.beans.ExchangeBean C where C.exchangeCode=" +
                    " :exchange OR C.exchangeCodeReal=:exchange OR " +
                    "C.exDestination=:exchange OR C.exchangeCode= :exDestination OR C.exchangeCodeReal=:exDestination" +
                    " OR C.exDestination=:exDestination ";
            Query query = session.createQuery(hql);
            query.setParameter("exchange", exchange);
            query.setParameter("exDestination", exDestination);
            ExchangeBean results = (ExchangeBean) query.uniqueResult();
            if (results != null) {
                return results.getExchangeCode();
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            session.close();
        }
    }


    @Override
    public String getLastExchangeId() {
        logger.info("Loading all the Exchange codes from DB");
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "select max(C.exchangeId) FROM lk.ac.ucsc.oms.exchanges.implGeneral.beans" +
                    ".ExchangeBean C ";
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
    public String getLastExchangeSubMarketId() {
        logger.info("Loading Last sub market id from DB");
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "select max(C.subMarketId) FROM lk.ac.ucsc.oms.exchanges.implGeneral.beans" +
                    ".SubMarketBean C ";
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
    public List<Exchange> getAllExchanges() {
        logger.info("Loading all the Exchange from DB");
        List<Exchange> exchangeList = new ArrayList<>();
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "FROM lk.ac.ucsc.oms.exchanges.implGeneral.beans.ExchangeBean";
            Query query = session.createQuery(hql);
            List results = query.list();

            for (Object cgb : results) {
                ExchangeBean exchangeBean = (ExchangeBean) cgb;
                exchangeList.add(exchangeBean);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            session.close();
        }

        logger.debug("Loaded Exchange code list of size:{} and list:{}", exchangeList.size(), exchangeList);
        return exchangeList;
    }

    @Override
    public void updateSuspendedStatusForSubMarket() {
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "select b from lk.ac.ucsc.oms.exchanges.implGeneral.beans.ExchangeBean a," +
                    "lk.ac.ucsc.oms.exchanges.implGeneral.beans.SubMarketBean b where  (a.exchangeCode=:dfm " +
                    "or a.exchangeCode=:dsm) and (a.exchangeId=b.exchangeId)";
            Query query = session.createQuery(hql);
            query.setParameter("dfm", "DFM");
            query.setParameter("dsm", "DSM");
            List result = query.list();
            for (Object i : result) {
                SubMarketBean subMarketBean = (SubMarketBean) i;
                hql = "update lk.ac.ucsc.oms.exchanges.implGeneral.beans.SubMarketBean a set a.manualSuspend=1 where a.subMarketId=:subMarketId";
                query = session.createQuery(hql);
                query.setParameter("subMarketId", subMarketBean.getSubMarketId());
                query.executeUpdate();
            }
            logger.debug("update suspended status for sub market");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            session.close();
        }
    }
}
