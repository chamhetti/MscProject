package lk.ac.ucsc.oms.customer.implGeneral.facade.cache;

import lk.ac.ucsc.oms.cache.api.CacheControllerInterface;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheException;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheNotFoundException;
import lk.ac.ucsc.oms.common_utility.api.enums.CacheLoadedState;
import lk.ac.ucsc.oms.common_utility.api.enums.RecordStatus;
import lk.ac.ucsc.oms.common_utility.api.formatters.StringUtils;
import lk.ac.ucsc.oms.customer.api.beans.account.Account;
import lk.ac.ucsc.oms.customer.api.beans.account.ExchangeAccount;
import lk.ac.ucsc.oms.customer.api.exceptions.AccountManagementException;
import lk.ac.ucsc.oms.customer.implGeneral.beans.account.AccountBean;
import lk.ac.ucsc.oms.customer.implGeneral.persistantImpl.AccountPersister;
import lk.ac.ucsc.oms.sequence_generator.api.AbstractSequenceGenerator;
import lk.ac.ucsc.oms.system_configuration.api.SysConfigException;
import lk.ac.ucsc.oms.system_configuration.api.SysLevelParaManager;
import lk.ac.ucsc.oms.system_configuration.api.SystemParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class AccountCacheFacade {
    private static Logger logger = LogManager.getLogger(AccountCacheFacade.class);

    private CacheControllerInterface cacheController;
    private AccountPersister accountPersister;
    private CacheLoadedState cacheLoadedState;
    private SysLevelParaManager sysLevelParaManager;
    private AbstractSequenceGenerator sequenceGenerator;

    public void setCacheController(CacheControllerInterface cacheController) {
        this.cacheController = cacheController;
    }

    public void setAccountPersister(AccountPersister accountPersister) {
        this.accountPersister = accountPersister;
    }

    public void setCacheLoadedState(CacheLoadedState cacheLoadedState) {
        this.cacheLoadedState = cacheLoadedState;
    }

    public CacheLoadedState getCacheLoadedState() {
        return cacheLoadedState;
    }

    public void initialize() throws AccountManagementException {
        //get the last id of the account table and set it to sequence generator for id generation
        sequenceGenerator.setSequenceNumber(accountPersister.getLastAccountId());
        //load all accounts for cache based on cache level configuration
        loadAllAccount();
        //set the cache on/off base on system level config
        try {
            String isCacheOff = sysLevelParaManager.getSysLevelParameter(SystemParameter.IS_CACHE_WRITE_THROUGH).getParaValue();
            if (isCacheOff != null && "true".equalsIgnoreCase(isCacheOff.trim())) {
                cacheController.setCacheWriteThrough(true);
            }
        } catch (SysConfigException e) {
            logger.warn("System level parameter not set", e);
        }
    }

    private void loadAllAccount() throws AccountManagementException {
        logger.info("Loading all accounts into cache");
        try {
            if (cacheLoadedState == CacheLoadedState.FULLY_LOADED) {
                cacheController.populateFullCache();
            }
        } catch (CacheException e) {
            logger.error("Error in loading all accounts: ", e);
            throw new AccountManagementException("Issue occurred in loading all accounts", e);
        }
    }

    private void persist() throws AccountManagementException {
        logger.debug("Persisting account cache objects");
        try {
            cacheController.persistCacheAsBulk();
        } catch (CacheException e) {
            logger.error("Error in Persisting Account Cache", e);
            throw new AccountManagementException("Error in Persisting Account Cache", e);
        }
    }

    public AccountBean getAccount(String accountNumber) throws AccountManagementException {
        AccountBean accountBean = new AccountBean(accountNumber);
        accountBean.setPrimaryKeyObject(accountNumber);
        CacheObject co;
        try {
            co = cacheController.readObjectFromCache(accountBean);
            return (AccountBean) co;
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new AccountManagementException("Account not found in cache or DB", e);
        }
    }

    public AccountBean getAccountForUpdate(String accountNumber) throws AccountManagementException {
        AccountBean accountBean = new AccountBean(accountNumber);
        accountBean.setPrimaryKeyObject(accountNumber);
        CacheObject co;
        try {
            co = cacheController.readObjectFromCache(accountBean);
            return (AccountBean) co;
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new AccountManagementException("Account not found in cache or DB", e);
        }
    }

    public void putAccount(AccountBean accountBean) throws AccountManagementException {
        accountBean.setPrimaryKeyObject(accountBean.getAccountNumber());
        try {
            cacheController.addToCache(accountBean);
        } catch (CacheNotFoundException e) {
            logger.error("Cannot add to Cache.Error in Cache !!", e);
            throw new AccountManagementException("Error in adding the Account", e);
        }
    }

    public void updateAccount(AccountBean accountBean) throws AccountManagementException {
        accountBean.setPrimaryKeyObject(accountBean.getAccountNumber());
        try {
            cacheController.modifyInCache(accountBean);
        } catch (CacheNotFoundException e) {
            logger.error("Cannot Update to Cache.Error in Cache !! ", e);
            throw new AccountManagementException("Error in updating the account", e);
        }
    }

    public void closeAccount(AccountBean accountBean) throws AccountManagementException {
        accountBean.setPrimaryKeyObject(accountBean.getAccountNumber());
        try {
            AccountBean b = (AccountBean) cacheController.readObjectFromCache(accountBean);
            b.setStatus(RecordStatus.DELETED);
            cacheController.modifyInCache(b);
        } catch (Exception e) {
            logger.error("Cannot remove from Cache.Cache name: " + " '", e);
            throw new AccountManagementException("Error in removing the account", e);
        }
    }


    public List<String> getAccountNumbersForCustomer(String customerNumber) throws AccountManagementException {
        List<String> accountNumbers = new ArrayList<String>();
        //get accounts from data source if not found in the cache
        persist();
        accountNumbers = accountPersister.getAccountNumbersByCustomerNumber(customerNumber);
        for (String accountNumber : accountNumbers) {
            getAccount(accountNumber);   // this will populate account in the cache so next time data-source is not accessed
        }
        logger.info("Account number list found from the DB - {}", accountNumbers);
        return accountNumbers;
    }

    public List<String> getAccountNumbersForCustomerFromDb(String customerNumber) throws AccountManagementException {
        //get accounts from data source
        persist();
        return accountPersister.getAccountNumbersByCustomerNumber(customerNumber);
    }

    public List<String> getAccountNumbersForCashAccount(String cashAccNumber) throws AccountManagementException {
        persist();
        return accountPersister.getAccountNumbersByCashAccountNumber(cashAccNumber);
    }

    public String getAccountNumberByExchangeAccount(String exchangeAccNumber, String exchangeCode) throws AccountManagementException {
        persist();
        return accountPersister.getAccountNumberByExchangeAccNumber(exchangeAccNumber, exchangeCode);
    }

    public List<Account> getAllAccount(List<String> institutionList) throws AccountManagementException {
        List<CacheObject> cacheObjects = new ArrayList<>();
        List<Account> accounts = new ArrayList<>();
        //If cache is fully loaded, then get all accounts from cache, else persist cache changes and get all accounts from physical storage
        if (cacheLoadedState == CacheLoadedState.FULLY_LOADED) {
            try {
                cacheObjects = cacheController.getAllCache();
                for (String inst : institutionList) {
                    cacheObjects.addAll(cacheController.searchFromCache("instituteCode", inst, Account.class));
                }
                for (CacheObject co : cacheObjects) {
                    accounts.add((Account) co);
                }
            } catch (CacheException e) {
                logger.error("Error in loading all accounts from cache: ", e);
                throw new AccountManagementException("Issue occurred in loading all accounts from cache", e);
            }
        } else {
            persist();
            accounts = accountPersister.getAllAccounts(StringUtils.getFromListToStringBySeparatorWithQuotes(institutionList, ","));
        }


        return accounts;
    }


    public List<ExchangeAccount> getAllExchangeAccount(List<String> institutionList) throws AccountManagementException {
        if (getCacheLoadedState() == CacheLoadedState.FULLY_LOADED) {
            logger.debug("Loading all exchange accounts available in cache - cache load state fully loaded.");
            return getAllExchangeAccounts(institutionList);      //getting Exchange Accounts from cache
        } else {
            logger.debug("Loading all exchange accounts from data source - cache load status partiality loaded");
            persist();   //persisting Exchange Accounts if partially loaded
            return accountPersister.getAllExchangeAccounts(StringUtils.getFromListToStringBySeparatorWithQuotes(institutionList, ","));
        }
    }

    private List<ExchangeAccount> getAllExchangeAccounts(List<String> institutionList) throws AccountManagementException {
        List<ExchangeAccount> exchangeAccounts = new ArrayList<>();
        List<Account> accountBeans = new ArrayList<>();
        accountBeans = getAllAccount(institutionList);
        for (Account cO : accountBeans) {
            for (ExchangeAccount ex : cO.getExchangeAccountsList().values()) {
                exchangeAccounts.add(ex);
            }
        }
        return exchangeAccounts;
    }

    public void setSysLevelParaManager(SysLevelParaManager sysLevelParaManager) {
        this.sysLevelParaManager = sysLevelParaManager;
    }


    public void setSequenceGenerator(AbstractSequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }
}
