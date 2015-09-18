package lk.ac.ucsc.oms.orderMgt.implGeneral.facade.cache;

import lk.ac.ucsc.oms.cache.api.CacheControllerInterface;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheException;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheNotFoundException;
import lk.ac.ucsc.oms.common_utility.api.enums.CacheLoadedState;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.orderMgt.api.beans.Execution;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.OrderExecutionException;
import lk.ac.ucsc.oms.orderMgt.implGeneral.bean.ExecutionBean;
import lk.ac.ucsc.oms.orderMgt.implGeneral.persistantImpl.ExecutionPersister;
import lk.ac.ucsc.oms.sequence_generator.api.AbstractSequenceGenerator;
import lk.ac.ucsc.oms.system_configuration.api.SysConfigException;
import lk.ac.ucsc.oms.system_configuration.api.SysLevelParaManager;
import lk.ac.ucsc.oms.system_configuration.api.SystemParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ExecutionCacheFacade {
    private static Logger logger = LogManager.getLogger(ExecutionCacheFacade.class);

    private CacheControllerInterface exeCacheController;
    private ExecutionPersister executionPersister;
    private CacheLoadedState executionCacheLoadedState;
    private CacheControllerInterface cacheController;
    private SysLevelParaManager sysLevelParaManager;

    private static int count = 0;
    private AbstractSequenceGenerator sequenceGenerator;


    public void setExeCacheController(CacheControllerInterface exeCacheController) {
        this.exeCacheController = exeCacheController;
    }


    public void setExecutionPersister(ExecutionPersister executionPersister) {
        this.executionPersister = executionPersister;
    }


    public void setExecutionCacheLoadedState(CacheLoadedState executionCacheLoadedState) {
        this.executionCacheLoadedState = executionCacheLoadedState;
    }

    public void initialize() throws OrderExecutionException {
        //load all executions for cache base on cache level configuration
        loadAllExecutions();

        //set the cache on/off base on system level config
        try {
            String isCacheOff = sysLevelParaManager.getSysLevelParameter(SystemParameter.IS_CACHE_WRITE_THROUGH).getParaValue();
            if (isCacheOff != null && "true".equalsIgnoreCase(isCacheOff.trim())) {
                exeCacheController.setCacheWriteThrough(true);
            }
        } catch (SysConfigException e) {
            logger.warn("System level parameter not set", e);
        }
    }

    private void loadAllExecutions() throws OrderExecutionException {
        logger.info("Loading all orders into cache");
        try {
            if (executionCacheLoadedState == CacheLoadedState.FULLY_LOADED) {
                exeCacheController.populateFullCache();
            }
        } catch (CacheException e) {
            logger.error("Error in loading all executions: ", e);
            throw new OrderExecutionException("Issue occurred in loading all executions", e);
        }
    }


    public List<Execution> findByOrderNo(String ordNo) throws OrderExecutionException {
        logger.info("Getting List of Executions for a given Order Number : ", ordNo);
        if (ordNo == null || "".equals(ordNo)) {
            throw new OrderExecutionException("Order Number can't be null or empty");
        }

        logger.info("Validation for Order Number successful, Order Number : ", ordNo);
        try {
            return findByCriteria(ExecutionBean.class.getDeclaredField("orderNo"), ordNo);
        } catch (NoSuchFieldException e) {
            logger.error("Error in find executions for given order number from cache", e);
            throw new OrderExecutionException("Error in find executions for given order number from cache", e);
        }
    }



    public void add(Execution exe) throws OrderExecutionException {
        logger.info("Add Given Execution to the Cache, Execution : " + exe);
        if (exe == null) {
            throw new OrderExecutionException("Execution can't be null");
        }
        if (exe.getExecutionId() == null || "".equals(exe.getExecutionId())) {
            throw new OrderExecutionException("Execution ID can't be null or empty");
        }
        logger.debug("Validation of the given Execution successful, Execution : ", exe);
        ExecutionBean exeBean = (ExecutionBean) exe;
        exeBean.setExecutionKey(exe.getExecutionId() + "_" + System.currentTimeMillis() + "_" + count++);
        exeBean.setPrimaryKeyObject(exeBean.getExecutionKey());
        try {
            exeCacheController.addToCache(exeBean);
            logger.info("Execution successfully added to cache, Execution : " + exe);
        } catch (CacheNotFoundException e) {
            logger.error("Error in adding execution to cache", e);
            throw new OrderExecutionException("Error in adding execution to cache", e);
        }
    }


    private List<Execution> findByCriteria(Field field, String value) throws OrderExecutionException {
        logger.info("Validation for given Field and Value successful, Field : " + field.toString() + ", Value : " + value);
        List<Execution> executions = new ArrayList<>();
        try {
            List<CacheObject> searchList = exeCacheController.searchFromCache(field, value, ExecutionBean.class);
            for (CacheObject cacheObject : searchList) {
                executions.add((Execution) cacheObject);
            }
            logger.info("Retrieving Execution list for given search criteria successful, List size : ", executions.size());
            return executions;
        } catch (CacheException e) {
            logger.error("Error ", e);
            throw new OrderExecutionException("Error while finding for the cache", e);
        }
    }


    public void persistAsBulk() throws OrderExecutionException {
        try {
            exeCacheController.persistCacheAsBulk();
        } catch (CacheException e) {
            logger.error("Error in persist executions: ", e);
            throw new OrderExecutionException("Error in persisting executions", e);
        }
    }


    public List<Execution> getExecutionsByOrderNumber(String orderNo) throws OrderExecutionException {
        List<Execution> executions;
        if (executionCacheLoadedState == CacheLoadedState.FULLY_LOADED) {
            executions = findByOrderNo(orderNo);
        } else {
            persistAsBulk();
            executions = executionPersister.getExecutionsByOrdNo(orderNo);
        }
        return executions;
    }


    public Execution getExecutionsByBackOfficeId(String backOfficeId) throws OrderExecutionException {
        return executionPersister.getExecutionByBackOfficeId(backOfficeId);
    }

    public List<Execution> getExecutionsByOrdNo(String orderNo) throws OrderExecutionException {
        persistAsBulk();
        return executionPersister.getExecutionsByOrdNo(orderNo);
    }

    public List<Execution> getExecutionsByClOrdId(String clOrdId) throws OrderExecutionException {
        persistAsBulk();
        return executionPersister.getExecutionsByClOrdId(clOrdId);
    }


    public void setSequenceGenerator(AbstractSequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    public void setCacheController(CacheControllerInterface cacheController) {
        this.cacheController = cacheController;
    }

    public void setSysLevelParaManager(SysLevelParaManager sysLevelParaManager) {
        this.sysLevelParaManager = sysLevelParaManager;
    }
}
