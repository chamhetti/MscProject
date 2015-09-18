package lk.ac.ucsc.oms.customer.implGeneral.facade.cache;

import lk.ac.ucsc.oms.cache.api.CacheControllerInterface;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheException;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheNotFoundException;
import lk.ac.ucsc.oms.common_utility.api.enums.CacheLoadedState;
import lk.ac.ucsc.oms.common_utility.api.enums.RecordStatus;
import lk.ac.ucsc.oms.common_utility.api.formatters.StringUtils;
import lk.ac.ucsc.oms.customer.api.beans.cash.CashAccount;
import lk.ac.ucsc.oms.customer.api.exceptions.CashManagementException;
import lk.ac.ucsc.oms.customer.implGeneral.beans.cash.CashAccountBean;
import lk.ac.ucsc.oms.customer.implGeneral.persistantImpl.CashAccountPersister;
import lk.ac.ucsc.oms.sequence_generator.api.AbstractSequenceGenerator;
import lk.ac.ucsc.oms.system_configuration.api.SysConfigException;
import lk.ac.ucsc.oms.system_configuration.api.SysLevelParaManager;
import lk.ac.ucsc.oms.system_configuration.api.SystemParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class CashCacheFacade {
    private static Logger logger = LogManager.getLogger(CashCacheFacade.class);

    private CacheControllerInterface cacheController;
    private CashAccountPersister cashAccountPersister;
    private CacheLoadedState cacheLoadedState;
    private SysLevelParaManager sysLevelParaManager;
    private AbstractSequenceGenerator sequenceGenerator;


    public void setCacheController(CacheControllerInterface cacheController) {
        this.cacheController = cacheController;
    }


    public void setCashAccountPersister(CashAccountPersister cashAccountPersister) {
        this.cashAccountPersister = cashAccountPersister;
    }


    public void setCacheLoadedState(CacheLoadedState cacheLoadedState) {
        this.cacheLoadedState = cacheLoadedState;
    }


    public void initialize() throws CashManagementException {
        sequenceGenerator.setSequenceNumber(cashAccountPersister.getLastCashAccountId());
        //load all cash accounts for cache based on cache level configuration
        loadAllCashAccount();
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


    private void loadAllCashAccount() throws CashManagementException {
        logger.info("Loading all cash accounts into cache");
        try {
            if (cacheLoadedState == CacheLoadedState.FULLY_LOADED) {
                cacheController.populateFullCache();
            }
        } catch (CacheException e) {
            logger.error("Error in loading all cash accounts: ", e);
            throw new CashManagementException("Issue occurred in loading all cash accounts", e);
        }
    }

    public void persist() throws CashManagementException {
        logger.info("Persisting cash account cache objects");
        try {
            cacheController.persistCache();
        } catch (CacheException e) {
            logger.error("Error in Persisting Cash Account Cache", e);
            throw new CashManagementException("Error in Persisting Cash Account Cache", e);
        }
    }


    public CashAccountBean getCashAccount(String cashAccountNumber) throws CashManagementException {
        CashAccountBean cashAccountBean = new CashAccountBean(cashAccountNumber);
        cashAccountBean.setPrimaryKeyObject(cashAccountBean.getCashAccNumber());
        CacheObject co;
        try {
            co = cacheController.readObjectFromCache(cashAccountBean);
            return (CashAccountBean) co;
        } catch (Exception e) {
            logger.debug("CashAccount not found in cache or DB", e);
            throw new CashManagementException("CashAccount not found in cache or DB", e);
        }

    }


    public CashAccountBean getCashAccountForUpdate(String cashAccountNumber) throws CashManagementException {
        CashAccountBean cashAccountBean = new CashAccountBean(cashAccountNumber);
        cashAccountBean.setPrimaryKeyObject(cashAccountBean.getCashAccNumber());
        CacheObject co;
        try {
            co = cacheController.readObjectForChange(cashAccountBean);
            return (CashAccountBean) co;
        } catch (Exception e) {
            logger.warn("CashAccount not found in cache or DB", e);
            throw new CashManagementException("CashAccount not found in cache or DB", e);
        }

    }



    public void putCashAccount(CashAccountBean cashAccountBean) throws CashManagementException {
        cashAccountBean.setPrimaryKeyObject(cashAccountBean.getCashAccNumber());
        try {
            cacheController.addToCache(cashAccountBean);  //add cashAccountBean to the cache
        } catch (CacheNotFoundException e) {
            logger.error("Cannot add to Cache.Error in Cache !!", e);
            throw new CashManagementException("Error in adding the CashAccount", e);
        }
    }


    public void updateCashAccount(CashAccountBean cashAccountBean) throws CashManagementException {
        cashAccountBean.setPrimaryKeyObject(cashAccountBean.getCashAccNumber());
        try {
            cacheController.modifyInCache(cashAccountBean); //modify cash account details already residing in the cache
        } catch (CacheNotFoundException e) {
            logger.error("Cannot Update to Cache.Error in Cache !! ", e);
            throw new CashManagementException("Error in updating the cashAccount", e);

        }
    }


    public void closeCashAccount(CashAccountBean cashAccountBean) throws CashManagementException {
        cashAccountBean.setPrimaryKeyObject(cashAccountBean.getCashAccNumber());
        try {
            CashAccountBean b = (CashAccountBean) cacheController.readObjectFromCache(cashAccountBean);
            b.setStatus(RecordStatus.DELETED);  //set cash account status to DELETED
            cacheController.modifyInCache(b);  //modify object in the cache
        } catch (Exception e) {
            logger.error("Cannot remove from Cache.Cache name: " + " '", e);
            throw new CashManagementException("Error in removing the cashAccount", e);
        }
    }



    public void persistAsBulk() throws CashManagementException {
        logger.info("Persisting cash account cache objects");
        try {
            cacheController.persistCacheAsBulk();
        } catch (CacheException e) {
            logger.error("Error in Persisting Cash Account Cache", e);
            throw new CashManagementException("Error in Persisting Cash Account Cache", e);
        }
    }


    public List<String> getCashAccountNumbersForCustomer(String customerNumber) throws CashManagementException {
        persist();
        return cashAccountPersister.getCashAccountNumbersByCustomerNumber(customerNumber);
    }


    public List<CashAccount> getAllCashAccount(List<String> institutionList) throws CashManagementException {
        List<CacheObject> cacheObjects = new ArrayList<>();
        List<CashAccount> cashAccounts = new ArrayList<>();
        //If cache is fully loaded, then get all cash accounts from cache, else persist cache changes and get all cash accounts from physical storage
        if (cacheLoadedState == CacheLoadedState.FULLY_LOADED) {
            try {
                for (String institution : institutionList) {
                    cacheObjects.addAll(cacheController.searchFromCache("instituteCode", institution, CashAccount.class));
                }
            } catch (CacheException e) {
                logger.error("Error in loading all cashAccounts from cache: ", e);
                throw new CashManagementException("Issue occurred in loading all cashAccounts from cache", e);
            }
        } else {
            persist();
            cacheObjects = cashAccountPersister.getAllCashAccountForInstitutions(StringUtils.getFromListToStringBySeparatorWithQuotes(institutionList, ","));
        }
        for (CacheObject co : cacheObjects) {
            cashAccounts.add((CashAccountBean) co);
        }
        return cashAccounts;
    }

    public void setSysLevelParaManager(SysLevelParaManager sysLevelParaManager) {
        this.sysLevelParaManager = sysLevelParaManager;
    }

    public void setSequenceGenerator(AbstractSequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }
}
