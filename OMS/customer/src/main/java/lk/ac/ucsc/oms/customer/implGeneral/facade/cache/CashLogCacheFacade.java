package lk.ac.ucsc.oms.customer.implGeneral.facade.cache;

import lk.ac.ucsc.oms.cache.api.CacheControllerInterface;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheException;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheNotFoundException;
import lk.ac.ucsc.oms.common_utility.api.enums.CacheLoadedState;
import lk.ac.ucsc.oms.customer.api.beans.cash.CashLog;
import lk.ac.ucsc.oms.customer.api.exceptions.CashManagementException;
import lk.ac.ucsc.oms.customer.implGeneral.beans.cash.CashLogBean;
import lk.ac.ucsc.oms.customer.implGeneral.persistantImpl.CashLogPersister;
import lk.ac.ucsc.oms.sequence_generator.api.AbstractSequenceGenerator;
import lk.ac.ucsc.oms.system_configuration.api.SysConfigException;
import lk.ac.ucsc.oms.system_configuration.api.SysLevelParaManager;
import lk.ac.ucsc.oms.system_configuration.api.SystemParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class CashLogCacheFacade {
    private static Logger logger = LogManager.getLogger(CashLogCacheFacade.class);

    private CacheControllerInterface cacheController;
    private CashLogPersister cashLogPersister;
    private CacheLoadedState cacheLoadedState;
    private SysLevelParaManager sysLevelParaManager;
    AbstractSequenceGenerator sequenceGenerator;

    public void setCacheController(CacheControllerInterface cacheController) {
        this.cacheController = cacheController;
    }

    public void setCashLogPersister(CashLogPersister cashLogPersister) {
        this.cashLogPersister = cashLogPersister;
    }

    public void setCacheLoadedState(CacheLoadedState cacheLoadedState) {
        this.cacheLoadedState = cacheLoadedState;
    }

    public void setSysLevelParaManager(SysLevelParaManager sysLevelParaManager) {
        this.sysLevelParaManager = sysLevelParaManager;
    }

    public void setSequenceGenerator(AbstractSequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    public void initialize() throws CashManagementException {
        //get the last id of the cash log table and set it to sequence generator for id generation
        sequenceGenerator.setSequenceNumber(cashLogPersister.getLastCashLogId());
        //load all cash logs for cache based on cache level configuration
        loadAllCashLog();
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

    private void loadAllCashLog() throws CashManagementException {
        logger.info("Loading all cash logs into cache");
        try {
            if (cacheLoadedState == CacheLoadedState.FULLY_LOADED) {
                cacheController.populateFullCache();
            }
        } catch (CacheException e) {
            logger.error("Error in loading all cash logs: ", e);
            throw new CashManagementException("Issue occurred in loading all cash logs", e);
        }
    }


    public void putCashLog(CashLogBean cashLogBean) throws CashManagementException {
        cashLogBean.setPrimaryKeyObject(cashLogBean.getCashLogId());
        try {
            cacheController.addToCache(cashLogBean);
        } catch (CacheNotFoundException e) {
            logger.error("Cannot add to Cache.Error in Cache !!", e);
            throw new CashManagementException("Error in adding the CashLog", e);
        }
    }


    public void persist() throws CashManagementException {
        logger.info("Persisting cash log cache objects");
        try {
            cacheController.persistCache();
        } catch (CacheException e) {
            logger.error("Error in Persisting CashLog Cache", e);
            throw new CashManagementException("Error in Persisting CashLog Cache", e);
        }
    }

    public void persisAsBulk() throws CashManagementException {
        logger.info("Persisting cash log cache objects");
        try {
            cacheController.persistCacheAsBulk();
        } catch (CacheException e) {
            logger.error("Error in Persisting CashLog Cache", e);
            throw new CashManagementException("Error in Persisting CashLog Cache", e);
        }
    }

    public List<CashLog> getAllCashLog(Date fromDate, Date toDate, List<String> institutionList, Map<String, String> filters) throws CashManagementException {

        List<CashLog> cashLogs = new ArrayList<CashLog>();
        //If cache is fully loaded, then get all cash logs from cache, else persist cache changes and get all cash logs from physical storage
        if (cacheLoadedState == CacheLoadedState.FULLY_LOADED) {
            throw new CashManagementException("cache does not support in fully loaded state");
        }
        if (cacheLoadedState == CacheLoadedState.PARTIALITY_LOADED) {
            persist();
        }
        cashLogs = cashLogPersister.getAllCashLog(fromDate, toDate, institutionList, filters);

        return cashLogs;
    }

    public List<CashLog> searchCashLogs(String cashAccId, String accountNumber, String orderNumber, String symbol,
                                        String cashLogCode, int fromRecord, int toRecord, Date startDate, Date endDate) throws CashManagementException {
        return cashLogPersister.findCashLogs(cashAccId, accountNumber, orderNumber, symbol, cashLogCode, fromRecord,
                toRecord, startDate, endDate);
    }

    public List<CashLog> getCashLogsByOrder(String orderID, String accountNumber) throws CashManagementException {
        return cashLogPersister.findCashLogByCriteria(orderID, accountNumber);
    }

    public List<CashLog> getCustomerTodayCashLog(String customerNUmber){
        return cashLogPersister.getCustomerTodayCashLog(customerNUmber);
    }
}
