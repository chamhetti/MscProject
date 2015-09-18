package lk.ac.ucsc.oms.customer.implGeneral.facade.cache;

import lk.ac.ucsc.oms.cache.api.CacheControllerInterface;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheException;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheNotFoundException;
import lk.ac.ucsc.oms.common_utility.api.enums.CacheLoadedState;
import lk.ac.ucsc.oms.customer.api.beans.holding.HoldingKey;
import lk.ac.ucsc.oms.customer.api.beans.holding.HoldingRecord;
import lk.ac.ucsc.oms.customer.api.exceptions.HoldingManagementException;
import lk.ac.ucsc.oms.customer.implGeneral.beans.holding.HoldingRecordBean;
import lk.ac.ucsc.oms.customer.implGeneral.persistantImpl.HoldingPersister;
import lk.ac.ucsc.oms.system_configuration.api.SysConfigException;
import lk.ac.ucsc.oms.system_configuration.api.SysLevelParaManager;
import lk.ac.ucsc.oms.system_configuration.api.SystemParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class HoldingCacheFacade {
    private static Logger logger = LogManager.getLogger(HoldingCacheFacade.class);

    private CacheControllerInterface cacheController;
    private HoldingPersister holdingPersister;
    private CacheLoadedState cacheLoadedState;
    private SysLevelParaManager sysLevelParaManager;

    public void setCacheController(CacheControllerInterface cacheController) {
        this.cacheController = cacheController;
    }

    public void setHoldingPersister(HoldingPersister holdingPersister) {
        this.holdingPersister = holdingPersister;
    }

    public CacheLoadedState getCacheLoadedState() {
        return cacheLoadedState;
    }

    public void setCacheLoadedState(CacheLoadedState cacheLoadedState) {
        this.cacheLoadedState = cacheLoadedState;
    }

    public void setSysLevelParaManager(SysLevelParaManager sysLevelParaManager) {
        this.sysLevelParaManager = sysLevelParaManager;
    }

    public void initialize() throws HoldingManagementException {
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

    public HoldingRecord getHoldingRecord(HoldingKey holdingKey) throws HoldingManagementException {
        HoldingRecordBean holdingRecord = new HoldingRecordBean(holdingKey);
        holdingRecord.setPrimaryKeyObject(holdingKey);
        CacheObject co;
        try {
            co = cacheController.readObjectFromCache(holdingRecord);
            return (HoldingRecordBean) co;
        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            throw new HoldingManagementException("HoldingRecord not found in cache or DB", e);
        }
    }

    public HoldingRecord getHoldingRecordForUpdate(HoldingKey holdingKey) throws HoldingManagementException {
        HoldingRecordBean holdingRecord = new HoldingRecordBean(holdingKey);
        holdingRecord.setPrimaryKeyObject(holdingKey);
        CacheObject co;
        try {
            co = cacheController.readObjectForChange(holdingRecord);
            return (HoldingRecordBean) co;
        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            throw new HoldingManagementException("HoldingRecord not found in cache or DB", e);
        }
    }


    public void putHoldingRecord(HoldingRecordBean holdingRecord) throws HoldingManagementException {
        holdingRecord.setPrimaryKeyObject(holdingRecord.getHoldingInfoKey());
        try {
            cacheController.addToCache(holdingRecord);
        } catch (CacheNotFoundException e) {
            logger.error("Cannot add to Cache.Error in Cache !!", e);
            throw new HoldingManagementException("Error in adding the HoldingRecord", e);
        }
    }


    public void updateHoldingRecord(HoldingRecordBean holdingRecord) throws HoldingManagementException {
        holdingRecord.setPrimaryKeyObject(holdingRecord.getHoldingInfoKey());
        try {
            cacheController.modifyInCache(holdingRecord);
        } catch (CacheNotFoundException e) {
            logger.error("Cannot Update to Cache.Error in Cache !! ", e);
            throw new HoldingManagementException("Error in updating the holdingRecord", e);

        }
    }

    public void persistAsBulk() throws HoldingManagementException {
        try {
            cacheController.persistCacheAsBulk();
        } catch (CacheException e) {
            logger.error("Error in Persisting Holding Cache", e);
            throw new HoldingManagementException("Error in Persisting Holding Cache", e);
        }
    }

    public void persistAsBulkWithoutHistory() throws HoldingManagementException {
        try {
            cacheController.persistCacheAsBulk();
        } catch (CacheException e) {
            logger.error("Error in Persisting Holding Cache", e);
            throw new HoldingManagementException("Error in Persisting Holding Cache", e);
        }
    }


    public List<HoldingKey> getCustomerAllHoldingKeys(String customerNumber) throws HoldingManagementException {
        persistAsBulkWithoutHistory();
        return holdingPersister.getCustomerAllHoldingKeys(customerNumber);
    }


    public List<HoldingRecord> getAllHolding(List<String> institutions) throws HoldingManagementException {
        List<CacheObject> cacheObjects = new ArrayList<>();
        List<HoldingRecord> holdingRecords = new ArrayList<>();
        //If cache is fully loaded, then get all holding records from cache, else persist cache changes and get all holding records from physical storage
        if (cacheLoadedState == CacheLoadedState.FULLY_LOADED) {
            try {
                for (String inst : institutions) {
                    cacheObjects.addAll(cacheController.searchFromCache("institutionCode", inst, HoldingRecord.class));
                }
            } catch (CacheException e) {
                logger.error("Error in loading all holdingRecords from cache: ", e);
                throw new HoldingManagementException("Issue occurred in loading all holdingRecords from cache", e);
            }
        } else {
            persistAsBulkWithoutHistory();
            StringBuilder institutionListStr = new StringBuilder();
            if ((institutions != null) && !institutions.isEmpty()) {
                for (String institution : institutions) {
                    institutionListStr.append('\'').append(institution).append("',");
                }
            }
            cacheObjects = holdingPersister.getHoldingsForInstitution(institutionListStr.toString().substring(0, institutionListStr.toString().length() - 1));
        }
        for (CacheObject co : cacheObjects) {
            holdingRecords.add((HoldingRecordBean) co);
        }
        return holdingRecords;
    }
}
