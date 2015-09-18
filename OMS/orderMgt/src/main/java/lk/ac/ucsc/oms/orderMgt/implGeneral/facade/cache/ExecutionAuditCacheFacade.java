package lk.ac.ucsc.oms.orderMgt.implGeneral.facade.cache;

import lk.ac.ucsc.oms.cache.api.CacheControllerInterface;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheException;
import lk.ac.ucsc.oms.common_utility.api.enums.CacheLoadedState;
import lk.ac.ucsc.oms.orderMgt.api.beans.ExecutionAuditRecord;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.ExecutionAuditRecordException;
import lk.ac.ucsc.oms.orderMgt.implGeneral.bean.ExecutionAuditRecordBean;
import lk.ac.ucsc.oms.orderMgt.implGeneral.persistantImpl.ExecutionAuditRecordPersister;
import lk.ac.ucsc.oms.system_configuration.api.SysConfigException;
import lk.ac.ucsc.oms.system_configuration.api.SysLevelParaManager;
import lk.ac.ucsc.oms.system_configuration.api.SystemParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ExecutionAuditCacheFacade {
    private static Logger logger = LogManager.getLogger(ExecutionAuditCacheFacade.class);
    private CacheControllerInterface exeAuditRecordCacheController;
    private CacheLoadedState executionCacheLoadedState;
    private SysLevelParaManager sysLevelParaManager;
    private ExecutionAuditRecordPersister executionAuditRecordPersister;

    public void initialize() throws ExecutionAuditRecordException {
        //load all executions for cache base on cache level configuration
        loadExecutionAuditRecords();

        //set the cache on/off base on system level config
        try {
            String isCacheOff = sysLevelParaManager.getSysLevelParameter(SystemParameter.IS_CACHE_WRITE_THROUGH).getParaValue();
            if (isCacheOff != null && "true".equalsIgnoreCase(isCacheOff.trim())) {
                exeAuditRecordCacheController.setCacheWriteThrough(true);
            }
        } catch (SysConfigException e) {
            logger.warn("System level parameter not set", e);
        }
    }


    private void loadExecutionAuditRecords() throws ExecutionAuditRecordException {
        logger.info("Loading all orders into cache");
        try {
            if (executionCacheLoadedState == CacheLoadedState.FULLY_LOADED) {
                exeAuditRecordCacheController.populateFullCache();
            }
        } catch (CacheException e) {
            logger.error("Error in loading all execution audit records - {} ", e);
            throw new ExecutionAuditRecordException("Issue occurred in loading all execution audit records", e);
        }
    }


    public void addToDB(ExecutionAuditRecord exe) throws ExecutionAuditRecordException {
        logger.info("Add Given Execution Audit Record to the Database, Execution : " + exe);
        if (exe == null) {
            throw new ExecutionAuditRecordException("Execution can't be null");
        }
        ExecutionAuditRecordBean exeBean = (ExecutionAuditRecordBean) exe;
        executionAuditRecordPersister.writeExecutionToDB(exeBean);
    }


    public void setExeAuditRecordCacheController(CacheControllerInterface exeAuditRecordCacheController) {
        this.exeAuditRecordCacheController = exeAuditRecordCacheController;
    }

    public void setExecutionCacheLoadedState(CacheLoadedState executionCacheLoadedState) {
        this.executionCacheLoadedState = executionCacheLoadedState;
    }

    public void setSysLevelParaManager(SysLevelParaManager sysLevelParaManager) {
        this.sysLevelParaManager = sysLevelParaManager;
    }

    public void setExecutionAuditRecordPersister(ExecutionAuditRecordPersister executionAuditRecordPersister) {
        this.executionAuditRecordPersister = executionAuditRecordPersister;
    }
}
