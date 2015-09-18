package lk.ac.ucsc.oms.system_configuration.implGeneral.facade.cache;

import lk.ac.ucsc.oms.cache.api.CacheControllerInterface;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheException;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheNotFoundException;
import lk.ac.ucsc.oms.common_utility.api.enums.CacheLoadedState;
import lk.ac.ucsc.oms.system_configuration.api.SysConfigException;
import lk.ac.ucsc.oms.system_configuration.api.SystemParameter;
import lk.ac.ucsc.oms.system_configuration.implGeneral.bean.SysLevelParamBean;
import lk.ac.ucsc.oms.system_configuration.implGeneral.persitantImpl.hibernate.SysLevelParamPersisterHibernate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class SysLevelParamCacheFacade {
    private static Logger logger = LogManager.getLogger(SysLevelParamCacheFacade.class);
    private CacheControllerInterface cacheController;
    private CacheLoadedState cacheLoadedState;
    private SysLevelParamPersisterHibernate sysLevelParamPersisterHibernate;

    /**
     * Method to set cache load status. It can be partially loaded or fully loaded
     *
     * @param cacheLoadedState available in spring configuration file.
     */
    public void setCacheLoadedState(CacheLoadedState cacheLoadedState) {
        this.cacheLoadedState = cacheLoadedState;
    }

    /**
     * Method to get cache load status.
     *
     * @return cacheLoadedState - partially loaded or fully loaded
     */
    public CacheLoadedState getCacheLoadedState() {
        return cacheLoadedState;
    }

    public void setCacheController(CacheControllerInterface cacheController) {
        this.cacheController = cacheController;
    }

    /**
     * load all the sysLevelParam to cache
     */
    public void loadSysLevelParams() {
        try {
            cacheController.populateFullCache();
        } catch (CacheException e) {
            logger.error("Cannot Load data from Cache", e);
        }
    }


    /**
     * put a sysLevelParam to cache
     *
     * @param sysLevelParamBean symbol bean
     * @return key of the object at cache
     * @throws lk.ac.ucsc.oms.system_configuration.api.SysConfigException
     *          error with code
     */
    public String putSysLevelParam(SysLevelParamBean sysLevelParamBean) throws SysConfigException {

        try {
            return cacheController.addToCache(sysLevelParamBean);
        } catch (CacheNotFoundException e) {
            throw new SysConfigException("Error in adding the SysLevelParam", e);
        }
    }

    /**
     * update a sysLevelParam at cache
     *
     * @param sysLevelParamBean sysLevelParam bean
     * @throws SysConfigException error with code
     */
    public void updateSysLevelParam(SysLevelParamBean sysLevelParamBean) throws SysConfigException {
        try {
            cacheController.modifyInCache(sysLevelParamBean);
        } catch (CacheNotFoundException e) {
            throw new SysConfigException("Error in updating the SysLevelParam", e);
        }
    }

    /**
     * Remove a sysLevelParam from cache
     *
     * @param sysLevelParamBean sysLevelParam bean
     * @throws SysConfigException error with code
     */
    public void markSysLevelParamAsDeleted(SysLevelParamBean sysLevelParamBean) throws SysConfigException {

        try {
            SysLevelParamBean eb = (SysLevelParamBean) cacheController.readObjectFromCache(sysLevelParamBean);
            cacheController.modifyInCache(eb);
        } catch (Exception e) {
            throw new SysConfigException("Error in removing the SysLevelParam", e);
        }
    }

    /**
     * Get a sysLevelParam in cache using sysLevelParam key
     *
     * @param sysLevelParamCode sysLevelParam key
     * @return sysLevelParam object
     * @throws SysConfigException error with reason
     */
    public SysLevelParamBean getSysLevelParam(SystemParameter sysLevelParamCode) throws SysConfigException {
        SysLevelParamBean sysLevelParamBean = new SysLevelParamBean(sysLevelParamCode);
        sysLevelParamBean.setPrimaryKeyObject(sysLevelParamCode);
        CacheObject co;
        try {
            co = cacheController.readObjectFromCache(sysLevelParamBean);
            return (SysLevelParamBean) co;
        } catch (Exception e) {
            throw new SysConfigException("SysLevelParam not found in cache or DB", e);
        }

    }

    /**
     * get all the sysLevelParam to cache
     *
     * @return List of SysLevelParams as  CacheObjects
     * @throws lk.ac.ucsc.oms.system_configuration.api.SysConfigException
     *          error with code
     */
    public List<CacheObject> getAllSysLevelParams() throws SysConfigException {
        logger.info("Retrieving all the cached SysLevel Params");
        List<CacheObject> sysLevelParams;
        try {
            sysLevelParams = cacheController.getAllCache();
            logger.debug("SysLevelParams cache objects retrieved - list size : " + sysLevelParams.size());
            if (sysLevelParams.isEmpty()) {
                throw new SysConfigException("Cached SysLevelParams not found");
            }
        } catch (Exception e) {
            throw new SysConfigException("Error while loading SysLevelParams from cache", e);
        }
        logger.info("Retrieving all the cached SysLevelParams success");
        return sysLevelParams;
    }

    /**
     * Method to persist cache objects into db. Bulk persist used in here. Either bulk persist or
     * persist cache which persist entry at a time can use
     *
     * @return success or failure of persist cache entries into db
     * @throws SysConfigException thrown while persisting cache entries
     */
    public void persist() throws SysConfigException {
        logger.info("Persisting sysLevelParams cache objects");
        try {
            cacheController.persistCacheAsBulk();
        } catch (CacheException e) {
            logger.error("Error in" + e);  //TODO --dasun-- pass e to the logger
        }
    }


    /**
     * Find all the system level parameters
     *
     * @return list of sysLevelParams
     * @throws SysConfigException
     */
    public List<CacheObject> findAllSystemLevelParams() throws SysConfigException {
        return sysLevelParamPersisterHibernate.loadAll();
    }

    /**
     * set system level parameter persister
     *
     * @param sysLevelParamPersisterHibernate
     *
     */
    public void setSysLevelParamPersisterHibernate(SysLevelParamPersisterHibernate sysLevelParamPersisterHibernate) {
        this.sysLevelParamPersisterHibernate = sysLevelParamPersisterHibernate;
    }

}
