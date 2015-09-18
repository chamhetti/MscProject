package lk.ac.ucsc.oms.customer.implGeneral.persistantImpl.hibernate;

import lk.ac.ucsc.oms.cache.api.AbstractCachePersister;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;


import lk.ac.ucsc.oms.customer.api.beans.cash.CashLog;
import lk.ac.ucsc.oms.customer.api.exceptions.CashManagementException;
import lk.ac.ucsc.oms.customer.implGeneral.beans.cash.CashLogBean;
import lk.ac.ucsc.oms.customer.implGeneral.persistantImpl.CashLogPersister;
import lk.ac.ucsc.oms.customer.implGeneral.persistantImpl.hibernate.utility.CashLogQueryGenerator;
import com.google.gson.Gson;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


public class CashLogPersisterHibernate extends AbstractCachePersister implements CashLogPersister {
    private static final int BATCH_SIZE = 20;
    private static Logger logger = LogManager.getLogger(CashLogPersisterHibernate.class);
    private static Date lastUpdateTime;
    private CashLogQueryGenerator cashLogQueryGenerator;

    public CashLogPersisterHibernate(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void setCashLogQueryGenerator(CashLogQueryGenerator cashLogQueryGenerator) {
        this.cashLogQueryGenerator = cashLogQueryGenerator;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(CacheObject co) throws CashManagementException {
        Transaction tx;
        Session session = sessionFactory.openSession();
        try {
            logger.debug("updating the CashLog bean: {}", co);
            tx = session.beginTransaction();
            session.update(co);
            tx.commit();
            logger.debug("updating finished");
        } catch (Exception e) {
            logger.error("CashLog bean can't update", e);
            logger.error("Cash Log -update -", new Gson().toJson(co));
            throw new CashManagementException("CashLog bean can't update", e);
        } finally {
            session.close();
        }

    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void insert(CacheObject co) throws CashManagementException {
        Transaction tx;

        Session session = sessionFactory.openSession();
        try {

            logger.debug("Inserting the CashLog bean:{} ", co);

            tx = session.beginTransaction();
            session.save(co);
            tx.commit();
            logger.debug("Inserting CashLog bean finished");
        } catch (Exception e) {
            logger.error("problem in adding cashLog", e);
            logger.error("Cash Log -Insert -", new Gson().toJson(co));
            throw new CashManagementException("problem in adding cashLog", e);
        } finally {
            session.close();
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CacheObject load(CacheObject co) throws CashManagementException {
        CashLogBean cashLogBean = null;
        Session session = sessionFactory.openSession();
        try {
            if (co == null) {
                logger.error("CashLog bean can't be null");
                throw new CashManagementException("CashLog bean can't be null");
            }

            long cashLog = (long) co.getPrimaryKeyObject();
            logger.info("Loading the cashLog with cashLog code:{} ", cashLog);
            if (cashLog == -1) {
                logger.warn("CashLog info provided not enough to load from DB");
                throw new CashManagementException("CashLog bean can't be null");
            }
            String hql = "FROM lk.ac.ucsc.oms.customer.implGeneral.beans.cash.CashLogBean C WHERE C.cashLogId = :cashLogId";
            Query query = session.createQuery(hql);
            query.setParameter("cashLogId", cashLog);
            cashLogBean = (CashLogBean) query.uniqueResult();
            if (cashLogBean != null) {
                cashLogBean.setPrimaryKeyObject(cashLogBean.getCashLogId());
            }
            logger.info("Loaded CashLog:{}", cashLogBean);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CashManagementException("Error in executing Hibernate Query for loading cashLog", e);
        } finally {
            session.close();
        }
        return cashLogBean;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<CacheObject> loadAll() {
        logger.info("Loading all the CashLog from DB");
        List<CacheObject> cashObjectLst = new ArrayList<>();
        Session session = sessionFactory.openSession();
        try {
            String hql = "FROM lk.ac.ucsc.oms.customer.implGeneral.beans.cash.CashLogBean";
            Query query = session.createQuery(hql);
            List results = query.list();

            for (Object cgb : results) {
                CashLogBean cashLogBean = (CashLogBean) cgb;
                cashLogBean.setPrimaryKeyObject(cashLogBean.getCashLogId());
                cashObjectLst.add(cashLogBean);
            }
            logger.debug("Loaded CashLogs list of size:{} and list:{}", cashObjectLst.size(), cashObjectLst);
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
        logger.info("cash log entries are not allow to delete");
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
    public List<CashLog> findCashLogByCriteria(String orderID, String accountNumber) throws CashManagementException {
        List<CashLog> cashLogList = new ArrayList<>();
        Session session = sessionFactory.openSession();

        String hql = "FROM lk.ac.ucsc.oms.customer.implGeneral.beans.cash.CashLogBean C WHERE " +
                "C.accountNumber=:accountNumber" +
                " AND C.orderNumber=:orderNumber";
        try {
            Query query = session.createQuery(hql);
            query.setParameter("accountNumber", accountNumber);
            query.setParameter("orderNumber", orderID);
            List results = query.list();
            for (Object ib : results) {
                CashLogBean conditionalOrderBean = (CashLogBean) ib;
                cashLogList.add(conditionalOrderBean);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CashManagementException("Error in executing Hibernate Query for filtering cash logs", e);
        } finally {
            session.close();
        }

        return cashLogList;
    }


    @Override
    public List<CashLog> findCashLogs(String cashAccId, String accountNumber, String orderNumber, String symbol, String cashLogCode,
                                      int recordFrom, int toRecord, Date startDate, Date endDate) throws CashManagementException {
        logger.info("Load all CashLogs filtered from the Database");
        String hqlQuery;
        List<CashLog> cashLogList = new ArrayList<CashLog>();
        int fromRecord = recordFrom;
        hqlQuery = cashLogQueryGenerator.createQueryForFindCashLogs(cashAccId, accountNumber, orderNumber, symbol, cashLogCode, startDate, endDate);
        try {
            Session session = sessionFactory.openSession();
            Query query = session.createQuery(hqlQuery);

            if (startDate != null && endDate != null) {
                query.setParameter("fromDate", startDate);
                query.setParameter("toDate", endDate);
            }

            //Pagination
            int totalRecords;
            if (fromRecord >= 0 && toRecord > 0 && toRecord >= fromRecord) {
                totalRecords = toRecord - fromRecord + 1;
                fromRecord--;
            } else {
                totalRecords = 0;
            }
            if (totalRecords != 0) {
                query.setFirstResult(fromRecord);
                query.setMaxResults(totalRecords);
            }

            List results = query.list();

            for (Object ob : results) {
                CashLogBean cashLog = (CashLogBean) ob;
                cashLogList.add(cashLog);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CashManagementException("Error in executing Hibernate Query for searching cash logs", e);
        }
        return cashLogList;
    }


    @Override
    public String getLastCashLogId() throws CashManagementException {
        logger.info("Loading last cash log id from DB");
        Session session = sessionFactory.openSession();
        try {
            String hql = "select max(C.cashLogId) FROM lk.ac.ucsc.oms.customer.implGeneral.beans.cash.CashLogBean C ";
            Query query = session.createQuery(hql);

            Object results = query.uniqueResult();
            Long val = (Long) results;

            if (results != null) {
                return val.toString();
            } else {
                return "0";
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CashManagementException("Error in Getting Last CashLog Id", e);
        } finally {
            session.close();
        }
    }


    @Override
    public List<CashLog> getAllCashLog(Date fromDate, Date toDate, List<String> institutionList, Map<String, String> filters) throws CashManagementException {
        logger.info("Load all CashLogs filtered from the Database");
        String hqlQuery;
        List<CashLog> cashLogList = new ArrayList<CashLog>();
        hqlQuery = cashLogQueryGenerator.createQueryToGetOrdersCashLogByDatesAndInstitutions(fromDate, toDate, institutionList, filters);
        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery(hqlQuery);

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

            for (Object ob : results) {
                CashLogBean cashLog = (CashLogBean) ob;
                cashLogList.add(cashLog);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CashManagementException("Error in executing Hibernate Query for searching cash logs", e);
        } finally {
            session.close();
        }

        return cashLogList;
    }


    @Override
    public List<CashLog> getCustomerTodayCashLog(String customerNumber){
        logger.info("Getting the customer -{} cash log entries ");
        List<CashLog> cashLogList = new ArrayList<CashLog>();
        String hql="FROM lk.ac.ucsc.oms.customer.implGeneral.beans.cash.CashLogBean C WHERE " +
                "C.customerNumber=:customerNumber" +
                " AND C.transactionDate>:transactionDate";
        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery(hql);
            query.setParameter("customerNumber", customerNumber);
            query.setParameter("transactionDate", getTodayDateWithBeginTime());
            List results = query.list();
            for (Object ib : results) {
                CashLogBean cashLogBean = (CashLogBean) ib;
                cashLogList.add(cashLogBean);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            session.close();
        }

        return cashLogList;
    }

    private Date getTodayDateWithBeginTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    @Override
    public void insertList(List<CacheObject> objectList) {

        Transaction tx;

        Session session = sessionFactory.openSession();
        try {
            int i = 0;
            tx = session.beginTransaction();
            CashLogBean cashLogBean;
            for (CacheObject co : objectList) {
                co.setDirty(false);
                co.setNew(false);
                cashLogBean = (CashLogBean) co;
                session.save(cashLogBean);
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
            logger.error("Process Cash Log Insert: Cash Log Bean Can't Update, Error : " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }


    @Override
    public void updateList(List<CacheObject> updateList) {

        Transaction tx;
        Session session = sessionFactory.openSession();
        try {
            int i = 0;
            tx = session.beginTransaction();
            CashLogBean cashLogBean;
            for (CacheObject co : updateList) {
                co.setDirty(false);
                cashLogBean = (CashLogBean) co;
                session.saveOrUpdate(cashLogBean);
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
            logger.error("Process Cash Log Update: Cash Log  Bean Can't Update, Error : " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }




}
