package lk.ac.ucsc.oms.customer.implGeneral.persistantImpl.hibernate;

import lk.ac.ucsc.oms.cache.api.AbstractCachePersister;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.common_utility.api.enums.RecordStatus;
import lk.ac.ucsc.oms.customer.api.beans.account.Account;
import lk.ac.ucsc.oms.customer.api.beans.account.ExchangeAccount;
import lk.ac.ucsc.oms.customer.api.exceptions.AccountManagementException;
import lk.ac.ucsc.oms.customer.implGeneral.beans.account.AccountBean;
import lk.ac.ucsc.oms.customer.implGeneral.beans.account.ExchangeAccountBean;
import lk.ac.ucsc.oms.customer.implGeneral.persistantImpl.AccountPersister;
import lk.ac.ucsc.oms.customer.implGeneral.util.CashManagerUtil;
import com.google.gson.Gson;
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
import java.util.List;


public class AccountPersisterHibernate extends AbstractCachePersister implements AccountPersister {
    private static final int BATCH_SIZE = 20;
    private static Logger logger = LogManager.getLogger(AccountPersisterHibernate.class);
    private static Date lastUpdateTime;


    public AccountPersisterHibernate(SessionFactory realTime, SessionFactory history) {
        super(realTime, history);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(CacheObject co) throws AccountManagementException {
        Transaction tx;
        Session session = realTimeSessionFactory.openSession();
        try {
            logger.debug("updating the Account bean: {}", co);
            tx = session.beginTransaction();
            session.saveOrUpdate(co);
            tx.commit();
            logger.debug("updating finished");
        } catch (Exception e) {
            logger.error("Account bean can't update", e);
            logger.error("Account -update -", new Gson().toJson(co));
            throw new AccountManagementException("Account bean can't update", e);
        } finally {
            session.close();
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void insert(CacheObject co) throws AccountManagementException {
        Transaction tx;
        Session session = sessionFactory.openSession();
        try {
            logger.debug("Inserting the Account bean:{} ", co);
            tx = session.beginTransaction();
            session.save(co);
            tx.commit();
            logger.debug("Inserting Account bean finished");
        } catch (Exception e) {
            logger.error("problem in adding account", e);
            logger.error("Account -Insert -", new Gson().toJson(co));
            throw new AccountManagementException("problem in adding account", e);
        } finally {
            session.close();
        }
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CacheObject load(CacheObject co) throws AccountManagementException {
        AccountBean accountBean = null;
        Session session = realTimeSessionFactory.openSession();
        try {
            if (co == null) {
                logger.error("The Account to load is null");
                throw new AccountManagementException("The Account to load is null");
            }

            String accountNumber = (String) co.getPrimaryKeyObject();
            logger.info("Loading the account with account code:{} ", accountNumber);
            if (accountNumber == null || "".equals(accountNumber)) {
                logger.warn("Account info provided not enough to load from DB");
                throw new AccountManagementException("The Account Number of the Account is null");
            }
            String hql = "FROM lk.ac.ucsc.oms.customer.implGeneral.beans.account.AccountBean C WHERE  C.accountNumber = :accNumber";
            Query query = session.createQuery(hql);
            query.setParameter("accNumber", accountNumber);
            accountBean = (AccountBean) query.uniqueResult();
            if (accountBean != null) {
                accountBean.setPrimaryKeyObject(accountNumber);
            }
            logger.info("Loaded Account:{}", accountBean);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AccountManagementException("Error in executing Hibernate Query for loading account", e);
        } finally {
            session.close();
        }
        return accountBean;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<CacheObject> loadAll() {
        logger.info("Loading all the Account from DB");
        List<CacheObject> cashObjectLst = new ArrayList<>();
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "FROM lk.ac.ucsc.oms.customer.implGeneral.beans.account.AccountBean";
            Query query = session.createQuery(hql);
            List results = query.list();

            for (Object cgb : results) {
                AccountBean accountBean = (AccountBean) cgb;
                accountBean.setPrimaryKeyObject(CashManagerUtil.getKey(accountBean.getCustomerNumber(), accountBean.getAccountNumber()));
                cashObjectLst.add(accountBean);
            }
            logger.debug("Loaded Accounts list of size:{} and list:{}", cashObjectLst.size(), cashObjectLst);
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
    @Transactional(propagation = Propagation.REQUIRED)
    public void remove(CacheObject co) throws AccountManagementException {
        logger.info("Removing the Account:{} from DB", co);
        if (co == null) {
            throw new AccountManagementException("The Account to remove can't be null");
        }
        AccountBean accountBean = (AccountBean) load(co);
        accountBean.setStatus(RecordStatus.DELETED);
        update(accountBean);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteFromDB(AccountBean account) throws AccountManagementException {
        logger.info("Deleting Account bean ");
        Transaction tx;
        Session session = realTimeSessionFactory.openSession();
        try {
            if (account == null) {
                throw new AccountManagementException("The Account to remove can't be null");
            }
            logger.debug("Deleting the Account bean: {}", account);
            tx = session.beginTransaction();
            session.delete(account);
            tx.commit();
            logger.info("Deleting finished");
        } catch (Exception e) {
            logger.error("Account bean can't delete", e);
            logger.error("Account -Delete -", new Gson().toJson(account));
            throw new AccountManagementException("Error in Removing Account From DB", e);
        } finally {
            session.close();
        }
    }

    @Override
    public String getLastAccountId() throws AccountManagementException {
        logger.info("Get the Last Account Id in DB");
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "select max(C.accId) FROM lk.ac.ucsc.oms.customer.implGeneral.beans.account.AccountBean C ";
            Query query = session.createQuery(hql);

            Long results = (Long) query.uniqueResult();
            if (results != null) {
                return results.toString();
            } else {
                return "0";
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AccountManagementException("Error in Getting Last Account Id", e);
        } finally {
            session.close();
        }

    }


    @Override
    public List<String> getAccountNumbersByCustomerNumber(String customerNumber) throws AccountManagementException {
        logger.info("Loading the Account Number from DB");
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "select C.accountNumber FROM lk.ac.ucsc.oms.customer.implGeneral.beans.account.AccountBean C  where C.customerNumber = :cusNumber order by C.accountNumber asc";
            Query query = session.createQuery(hql);
            query.setParameter("cusNumber", customerNumber);
            return query.list();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AccountManagementException("Error in Getting Account Numbers for Given Customer", e);
        } finally {
            session.close();
        }
    }


    @Override
    public String getAccountNumberByExchangeAccNumber(String exchangeAccNumber, String exchangeCode) throws AccountManagementException {
        logger.info("Loading the Account Number from DB");
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "select C.accountNumber FROM lk.ac.ucsc.oms.customer.implGeneral.beans.account.ExchangeAccountBean C  " +
                    "where C.exchangeAccNumber=:exAccNumber and C.exchangeCode=:exchange";

            Query query = session.createQuery(hql);
            query.setParameter("exAccNumber", exchangeAccNumber);
            query.setParameter("exchange", exchangeCode);
            List<String> accStringList = query.list();
            if (accStringList != null && !accStringList.isEmpty()) {
                return accStringList.get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AccountManagementException("Error in Getting Account Number for Given Exchange Account", e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<String> getAccountNumbersByCashAccountNumber(String cashAccNumber) throws AccountManagementException {
        logger.info("Loading the Account Number from DB");
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "select C.accountNumber FROM lk.ac.ucsc.oms.customer.implGeneral.beans.account.AccountBean C  where C.cashAccNumber=:cashAccNumber";
            Query query = session.createQuery(hql);
            query.setParameter("cashAccNumber", cashAccNumber);
            return query.list();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AccountManagementException("Error in Getting Account Numbers for Given Cash Account", e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<ExchangeAccount> getAllExchangeAccounts(String institutionList) throws AccountManagementException {
        logger.info("Loading all the Exchange Account from DB");
        List<ExchangeAccount> exchangeAccounts = new ArrayList<>();
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "select a.exchangeAccountsList FROM lk.ac.ucsc.oms.customer.implGeneral.beans.account.AccountBean a where a.instituteCode in (" + institutionList + ")";
            Query query = session.createQuery(hql);
            List results = query.list();

            for (Object cgb : results) {
                ExchangeAccountBean exchangeAccountBean = (ExchangeAccountBean) cgb;
                exchangeAccounts.add(exchangeAccountBean);
            }
            logger.debug("Loaded Accounts list of size:{} and list:{}", exchangeAccounts.size(), exchangeAccounts);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AccountManagementException("Error in Getting All Exchange Accounts", e);
        } finally {
            session.close();
        }
        return exchangeAccounts;
    }


    @Override
    public List<Account> getAllAccounts(String institutionList) throws AccountManagementException {
        logger.info("Loading all Accounts from DB");
        List<Account> accounts = new ArrayList<>();
        Session session = realTimeSessionFactory.openSession();
        try {
            String hql = "FROM lk.ac.ucsc.oms.customer.implGeneral.beans.account.AccountBean A where  A.instituteCode in (" + institutionList + ")";
            Query query = session.createQuery(hql);
            List results = query.list();

            for (Object cgb : results) {
                AccountBean accountBean = (AccountBean) cgb;
                accounts.add(accountBean);
            }
            logger.debug("Loaded Accounts list of size:{} and list:{}", accounts.size(), accounts);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AccountManagementException("Error in Getting All Accounts", e);
        } finally {
            session.close();
        }
        return accounts;
    }


    @Override
    public void insertList(List<CacheObject> objectList) {

        Transaction tx;
        Session session = sessionFactory.openSession();
        long startTime = System.currentTimeMillis();
        try {
            int i = 0;
            tx = session.beginTransaction();
            AccountBean accountBean;
            for (CacheObject co : objectList) {
                co.setDirty(false);
                co.setNew(false);
                accountBean = (AccountBean) co;
                session.save(accountBean);
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
            logger.debug("Time to commit security account insertion process >>>" + (endTime - startTime));
        } catch (Exception e) {
            logger.error("Inserting Process: Security Account Bean Can't Update, Error : " + e.getMessage(), e);
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
            AccountBean accountBean;
            for (CacheObject co : updateList) {
                co.setDirty(false);
                accountBean = (AccountBean) co;
                session.saveOrUpdate(accountBean);
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
            logger.debug("Time to commit security account updating process >>>" + (endTime - startTime));
        } catch (Exception e) {
            logger.error("Updating Process: Security Account Bean Can't Update, Error : " + e.getMessage(), e);
        } finally {
            session.close();
        }
    }

}
