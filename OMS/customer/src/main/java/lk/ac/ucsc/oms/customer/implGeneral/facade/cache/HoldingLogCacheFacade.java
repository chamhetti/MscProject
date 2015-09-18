package lk.ac.ucsc.oms.customer.implGeneral.facade.cache;

import lk.ac.ucsc.oms.cache.api.CacheControllerInterface;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheException;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheNotFoundException;
import lk.ac.ucsc.oms.common_utility.api.enums.CacheLoadedState;
import lk.ac.ucsc.oms.customer.api.beans.holding.HoldingLog;
import lk.ac.ucsc.oms.customer.api.exceptions.HoldingManagementException;
import lk.ac.ucsc.oms.customer.implGeneral.beans.holding.HoldingLogBean;
import lk.ac.ucsc.oms.customer.implGeneral.persistantImpl.HoldingLogPersister;
import lk.ac.ucsc.oms.system_configuration.api.SysConfigException;
import lk.ac.ucsc.oms.system_configuration.api.SysLevelParaManager;
import lk.ac.ucsc.oms.system_configuration.api.SystemParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HoldingLogCacheFacade {
    private static Logger logger = LogManager.getLogger(HoldingLogCacheFacade.class);

    private CacheControllerInterface cacheController;
    private HoldingLogPersister holdingLogPersister;
    private CacheLoadedState cacheLoadedState;
    private SysLevelParaManager sysLevelParaManager;


    public void initialize() {
        try {
            String isCacheOff = sysLevelParaManager.getSysLevelParameter(SystemParameter.IS_CACHE_WRITE_THROUGH).getParaValue();
            if (isCacheOff != null && "true".equalsIgnoreCase(isCacheOff.trim())) {
                cacheController.setCacheWriteThrough(true);
            }
        } catch (SysConfigException e) {
            logger.warn("System level parameter not set", e);
        }
    }

    public void setCacheController(CacheControllerInterface cacheController) {
        this.cacheController = cacheController;
    }

    public void setHoldingLogPersister(HoldingLogPersister holdingLogPersister) {
        this.holdingLogPersister = holdingLogPersister;
    }

    public void setCacheLoadedState(CacheLoadedState cacheLoadedState) {
        this.cacheLoadedState = cacheLoadedState;
    }

    public void setSysLevelParaManager(SysLevelParaManager sysLevelParaManager) {
        this.sysLevelParaManager = sysLevelParaManager;
    }

    public void putHoldingLog(HoldingLogBean holdingLogBean) throws HoldingManagementException {
        holdingLogBean.setPrimaryKeyObject(holdingLogBean.getHoldingLogId());
        try {
            cacheController.addToCache(holdingLogBean);
        } catch (CacheNotFoundException e) {
            logger.error("Cannot add to Cache.Error in Cache !!", e);
            throw new HoldingManagementException("Error in adding the HoldingLog", e);
        }
    }

    public void persistAsBulk() throws HoldingManagementException {
        logger.info("Persisting holding log cache objects");
        try {
            cacheController.persistCacheAsBulk();
        } catch (CacheException e) {
            logger.error("Error in Persisting HoldingLog Cache", e);
            throw new HoldingManagementException("Error in Persisting HoldingLog Cache", e);
        }
    }

    public List<HoldingLog> getAllHoldingLogs(List<String> institutionList, Date fromDate, Date toDate) throws HoldingManagementException {
        List<CacheObject> cacheObjects = null;
        List<HoldingLog> holdingLogs = new ArrayList<>();
        persistAsBulk();
        StringBuilder institutionListStr = new StringBuilder();

        if ((institutionList != null) && !institutionList.isEmpty()) {
            for (String institution : institutionList) {
                institutionListStr.append('\'').append(institution).append("',");
            }
        }

        String str;
        if (institutionListStr.toString().length() == 0) {
            str = "";
        } else {
            str = institutionListStr.toString().substring(0, institutionListStr.toString().length() - 1);
        }

        cacheObjects = holdingLogPersister.loadWithInstitutionDateFilter(str, fromDate, toDate);
        for (CacheObject co : cacheObjects) {
            holdingLogs.add((HoldingLog) co);
        }

        return holdingLogs;
    }

}
