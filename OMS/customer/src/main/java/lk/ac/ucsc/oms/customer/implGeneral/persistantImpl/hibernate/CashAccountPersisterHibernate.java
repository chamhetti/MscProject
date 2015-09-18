package lk.ac.ucsc.oms.customer.implGeneral.persistantImpl.hibernate;

import lk.ac.ucsc.oms.cache.api.AbstractCachePersister;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.common_utility.api.enums.RecordStatus;


import lk.ac.ucsc.oms.customer.api.exceptions.CashManagementException;
import lk.ac.ucsc.oms.customer.implGeneral.beans.cash.CashAccountBean;
import lk.ac.ucsc.oms.customer.implGeneral.persistantImpl.CashAccountPersister;
import com.google.gson.Gson;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class CashAccountPersisterHibernate extends AbstractCachePersister implements CashAccountPersister {
    private static final int BATCH_SIZE = 20;
    private static Logger logger = LogManager.getLogger(CashAccountPersisterHibernate.class);
    private static Date lastUpdateTime;

    public CashAccountPersisterHibernate(SessionFactory realTime, SessionFactory history) {
        super(realTime, history);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(CacheObject co) throws CashManagementException {
        Transaction tx;
        Session session = realTimeSessionFactory.openSession();
        try {
            logger.debug("updating the CashAccount bean: {}", co);
            tx = session.beginTransaction();
            session.update(co);
            tx.commit();
            logger.debug("updating finished");
        } catch (Exception e) {
            logger.error("CashAccount bean can't update", e);
            logger.error("Cash Account -update -", new Gson().toJson(co));
            throw new CashManagementException("CashAccount bean can't update", e);
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
            logger.debug("Inserting the CashAccount bean:{} ", co);
            tx = session.beginTransaction();
            session.save(co);
            tx.commit();
            logger.debug("Inserting CashAccount bean finished");
        } catch (Exception e) {
            logger.error("problem in adding cashAccount", e);
            logger.error("Cash Account -Insert -", new Gson().toJson(co));
            throw new CashManagementException("problem in adding cash account", e);
        } finally {
            session.close();
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CacheObject load(CacheObject co) throws CashManagementException {
        CashAccountBean cashAccountBean = null;
        Session session = realTimeSessionFactory.openSession();
        try {
            if (co == null) {
                logger.error("The Cash Account to load is null");
                throw new CashManagementException("The Cash Account to load is null");
            }

            String cashAccountNumber = (String) co.getPrimaryKeyObject();
            logger.info("Loading the cashAccount with cashAccount Number:{} ", cashAccountNumber);
            if (cashAccountNumber == null || "".equals(cashAccountNumber)) {
                logger.warn("CashAccount info provided not enough to load from DB");
                throw new CashManagementException("The Account Number of the Cash Account is null");
            }
            String hql = "FROM lk.ac.ucsc.oms.customer.implGeneral.beans.cash.CashAccountBean C WHERE C.cashAccNumber = :cashAccountNumber";
            Query query = session.createQuery(hql);
            query.setParameter("cashAccountNumber", cashAccountNumber);
            cashAccountBean = (CashAccountBean) query.uniqueResult();
            if (cashAccountBean != null) {
                cashAccountBean.setPrimaryKeyObject(cashAccountBean.getCashAccNumber());
            }
            logger.info("Loaded CashAccount:{}", cashAccountBean);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CashManagementException("Error in executing Hibernate Query for loading cash account", e);
        } finally {
            session.close();
        }
        return cashAccountBean;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<CacheObject> loadAll() {
        logger.info("Loading all the CashAccount from DB");
        List<CacheObject> cashObjectLst = new ArrayList<>();
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "FROM lk.ac.ucsc.oms.customer.implGeneral.beans.cash.CashAccountBean";
            Query query = session.createQuery(hql);
            List results = query.list();

            for (Object cgb : results) {
                CashAccountBean cashAccountBean = (CashAccountBean) cgb;
                cashAccountBean.setPrimaryKeyObject(cashAccountBean.getCashAccNumber());
                cashObjectLst.add(cashAccountBean);
            }
            logger.debug("Loaded CashAccounts list of size:{} and list:{}", cashObjectLst.size(), cashObjectLst);
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
    public void remove(CacheObject co) throws CashManagementException {
        logger.info("Removing the CashAccount:{} from DB", co);
        if (co == null) {
            throw new CashManagementException("The Cash Account to remove can't be null");
        }
        CashAccountBean cashAccountBean = (CashAccountBean) load(co);
        cashAccountBean.setStatus(RecordStatus.DELETED);
        update(cashAccountBean);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteFromDB(CashAccountBean cashAccount) throws CashManagementException {
        logger.info("Deleting CashAccount bean ");
        Transaction tx;
        Session session = realTimeSessionFactory.openSession();
        try {
            if (cashAccount == null) {
                throw new CashManagementException("The Cash Account to remove can't be null");
            }
            logger.debug("Deleting the CashAccount bean: {}", cashAccount);
            tx = session.beginTransaction();
            session.delete(cashAccount);
            tx.commit();
            logger.info("Deleting finished");
        } catch (Exception e) {
            logger.error("CashAccount bean can't delete", e);
            logger.error("Cash Account -Delete -", new Gson().toJson(cashAccount));
            throw new CashManagementException("Error in Removing Cash Account From DB", e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<String> getCashAccountNumbersByCustomerNumber(String customerNumber) throws CashManagementException {
        logger.info("Loading the Account Number from DB");
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "select C.cashAccNumber FROM lk.ac.ucsc.oms.customer.implGeneral.beans.cash.CashAccountBean C  where C.customerNumber=:cusNumber";
            Query query = session.createQuery(hql);
            query.setParameter("cusNumber", customerNumber);
            return query.list();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CashManagementException("Error in Getting Cash Account Numbers for Given Customer", e);
        } finally {
            session.close();
        }
    }

    @Override
    public String getLastCashAccountId() throws CashManagementException {
        logger.info("Loading last cash account id from DB");
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "select max(C.cashAccId) FROM lk.ac.ucsc.oms.customer.implGeneral.beans.cash.CashAccountBean C ";
            Query query = session.createQuery(hql);

            Long results = (Long) query.uniqueResult();
            if (results != null) {
                return results.toString();
            } else {
                return "0";
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CashManagementException("Error in Getting Last Cash Account Id", e);
        } finally {
            session.close();
        }

    }

    @Override
    public List<CacheObject> getAllCashAccountForInstitutions(String institutionList) throws CashManagementException {
        logger.info("Loading all the CashAccount from DB");
        List<CacheObject> cashObjectLst = new ArrayList<>();
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "FROM lk.ac.ucsc.oms.customer.implGeneral.beans.cash.CashAccountBean C where C.instituteCode  in (" + institutionList + ") ";
            Query query = session.createQuery(hql);
            List results = query.list();

            for (Object cgb : results) {
                CashAccountBean cashAccountBean = (CashAccountBean) cgb;
                cashAccountBean.setPrimaryKeyObject(cashAccountBean.getCashAccNumber());
                cashObjectLst.add(cashAccountBean);
            }
            logger.debug("Loaded CashAccounts list of size:{} and list:{}", cashObjectLst.size(), cashObjectLst);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return cashObjectLst;
    }

    @Override
    public boolean updateDailyODLimit() throws CashManagementException {
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "update lk.ac.ucsc.oms.customer.implGeneral.beans.cash.CashAccountBean C set C.dailyOdEnable=0 where C.dailyODLimit>0";
            Query query = session.createQuery(hql);
            query.executeUpdate();
            logger.debug("update daily OD limit success");
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public void updateDayMarginLimit() throws CashManagementException {
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "update lk.ac.ucsc.oms.customer.implGeneral.beans.cash.CashAccountBean C set C.dayMarginBlock=0 where C.dayMarginBlock>0";
            Query query = session.createQuery(hql);
            query.executeUpdate();
            logger.info("update day margin limit success");
        } catch (Exception e) {
            logger.error("update day margin limit error ", e);
        } finally {
            session.close();
        }
    }

    @Override
    public void updateDayMarginDue() throws CashManagementException {
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "update lk.ac.ucsc.oms.customer.implGeneral.beans.cash.CashAccountBean C set C.dayMarginDue=0 where C.dayMarginDue >0";
            Query query = session.createQuery(hql);
            query.executeUpdate();
            logger.info("update day margin due is success ");
        } catch (Exception e) {
            logger.error("update day margin due error ", e);
        } finally {
            session.close();
        }
    }

    @Override
    public void updatePendingSettle() throws CashManagementException {
        Session session = realTimeSessionFactory.openSession();
        try {
            //set all the pending settle amount to zero
            String sql = "update lk.ac.ucsc.oms.customer.implGeneral.beans.cash.CashAccountBean a set a.pendingSettle=0";
            Query q1 = session.createQuery(sql);
            q1.executeUpdate();
            //re update the pending settle amount in cash accounts
            List cashAccountList = getCashAccountIdAndAmountToUpdate();
            double amount = 0;
            long cashAccId = 0;
            if (cashAccountList != null) {
                for (Iterator it = cashAccountList.iterator(); it.hasNext(); ) {
                    Object[] obj = (Object[]) it.next();
                    String hql = "update lk.ac.ucsc.oms.customer.implGeneral.beans.cash.CashAccountBean a set a.pendingSettle=:pendingSettle where a.cashAccId=:cashAccountId";
                    Query query = session.createQuery(hql);
                    amount = ((BigDecimal) obj[1]).doubleValue();
                    cashAccId = ((BigDecimal) obj[0]).intValue();
                    query.setDouble("pendingSettle", amount);
                    query.setParameter("cashAccountId", cashAccId);
                    query.executeUpdate();
                }
            }
            logger.info("update pending settle success");
        } catch (Exception e) {
            logger.error("Error while updating pending settle", e);
        } finally {
            session.close();
        }
    }

    @Override
    public void insertList(List<CacheObject> objectList) {

        Transaction tx;
        Session session = sessionFactory.openSession();
        long startTime = System.currentTimeMillis();
        try {
            int i = 0;
            tx = session.beginTransaction();
            CashAccountBean cashAccountBean;
            for (CacheObject co : objectList) {
                co.setDirty(false);
                co.setNew(false);
                cashAccountBean = (CashAccountBean) co;
                session.save(cashAccountBean);
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
            logger.debug("Time to commit cash accounts insertion process >>>" + (endTime - startTime));
        } catch (Exception e) {
            logger.error("Inserting Process: Cash Account Bean Can't Update, Error : " + e.getMessage(), e);
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
            CashAccountBean cashAccountBean;
            for (CacheObject co : updateList) {
                co.setDirty(false);
                cashAccountBean = (CashAccountBean) co;
                session.saveOrUpdate(cashAccountBean);
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
            logger.debug("Time to commit cash accounts updating process >>>" + (endTime - startTime));
        } catch (Exception e) {
            logger.error("Updating Process: Cash Account Bean Can't Update, Error : " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }


    private List getCashAccountIdAndAmountToUpdate() {
        List cashAccountList = new ArrayList();
        Session session = realTimeSessionFactory.openSession();
        try {
            String sql = "select cash_acc_id, sum(amount_in_settle_cur) as amount from core_cusmm_cashlog where cash_log_code = 'STLSEL' and trunc(settle_date)>trunc(sysdate+1) group by cash_acc_id";
            Query query = session.createSQLQuery(sql);
            cashAccountList = query.list();
        } catch (Exception e) {
            logger.error("Error while getting cash account list", e);
        } finally {
            session.close();
        }
        return cashAccountList;
    }

}
