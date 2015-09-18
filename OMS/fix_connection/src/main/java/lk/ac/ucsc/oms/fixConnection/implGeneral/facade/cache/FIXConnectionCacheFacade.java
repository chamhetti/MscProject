package lk.ac.ucsc.oms.fixConnection.implGeneral.facade.cache;

import lk.ac.ucsc.oms.cache.api.CacheControllerInterface;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheException;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheNotFoundException;
import lk.ac.ucsc.oms.common_utility.api.enums.CacheLoadedState;
import lk.ac.ucsc.oms.common_utility.api.enums.RecordStatus;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.fixConnection.api.beans.FIXConnection;
import lk.ac.ucsc.oms.fixConnection.api.exceptions.FIXConnectionException;
import lk.ac.ucsc.oms.fixConnection.implGeneral.beans.FIXConnectionBean;
import lk.ac.ucsc.oms.system_configuration.api.SysConfigException;
import lk.ac.ucsc.oms.system_configuration.api.SysLevelParaManager;
import lk.ac.ucsc.oms.system_configuration.api.SystemParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class FIXConnectionCacheFacade {
    private CacheControllerInterface cacheController;
    private static Logger logger = LogManager.getLogger(FIXConnectionCacheFacade.class);
    private CacheLoadedState cacheLoadedState;
    private SysLevelParaManager sysLevelParaManager;

    public void initialize() throws FIXConnectionException {
        loadFixConnections();
        try {
            String isCacheOff = sysLevelParaManager.getSysLevelParameter(SystemParameter.IS_CACHE_WRITE_THROUGH).getParaValue();
            if (isCacheOff != null && "true".equalsIgnoreCase(isCacheOff.trim())) {
                cacheController.setCacheWriteThrough(true);
            }
        } catch (SysConfigException e) {
            logger.warn("System level parameter not set", e);
        }
    }

    public void delete(FIXConnectionBean fb) throws FIXConnectionException {
        fb.setStatus(RecordStatus.DELETED);
        try {
            cacheController.modifyInCache(fb);
        } catch (CacheNotFoundException e) {
            logger.error("Error in changing status into delete", e);
            throw new FIXConnectionException("Error occurred while changing fix connection status into delete", e);
        }
    }

    public void add(FIXConnectionBean fb) throws FIXConnectionException {
        fb.setStatus(RecordStatus.APPROVED);
        fb.setPrimaryKeyObject(fb.getConnectionID());
        try {
            cacheController.addToCache(fb);
        } catch (CacheException e) {
            logger.error("Error in add", e);
            throw new FIXConnectionException("Error occurred while adding fix connection bean", e);
        }
        logger.debug("fix connections added -{}", fb);
    }


    public void update(FIXConnectionBean fb) throws FIXConnectionException {
        fb.setStatus(RecordStatus.APPROVED);
        try {
            cacheController.modifyInCache(fb);
        } catch (CacheNotFoundException e) {
            logger.error("Error in update process", e);
            throw new FIXConnectionException("Error occurred while updating fix connection bean", e);
        }
        logger.debug("fix connections added -{}", fb);
    }


    public FIXConnectionBean getFIXConnection(String connectionID) throws FIXConnectionException {
        try {
            FIXConnectionBean fb = (FIXConnectionBean) cacheController.readObjectFromCache(new FIXConnectionBean(connectionID));
            if (fb != null && fb.getStatus() != null && fb.getStatus().equals(RecordStatus.DELETED)) {
                throw new FIXConnectionException("Fix connection in delete status");
            }
            return fb;
        } catch (OMSException e) {
            logger.error("Error in getting fix connections", e);
            throw new FIXConnectionException("Error in getting fix connection ", e);
        }
    }


    public List<FIXConnectionBean> getFIXConnectionBySessionQualifier(String sessionQualifier) throws FIXConnectionException {
        List<CacheObject> coList = null;
        List<FIXConnectionBean> fixConnections = new ArrayList<>();
        logger.info("Getting the FIX Connection with field -{} having value -{}", "sessionQualifier", sessionQualifier);
        try {
            coList = cacheController.searchFromCache("sessionQualifier", sessionQualifier, FIXConnectionBean.class);
        } catch (Exception e) {
            logger.error("Problem in searching the cache", e);
            logger.info("FIX Connection with give criteria does not found");
            throw new FIXConnectionException("Error in getting FIX Connection by given field", e);
        }
        if (coList != null && !coList.isEmpty()) {
            for (CacheObject aCoList : coList) {
                fixConnections.add((FIXConnectionBean) aCoList);
            }
            return fixConnections;
        }
        return fixConnections;
    }


    public List<FIXConnection> getAll() throws FIXConnectionException {
        logger.debug("request for get all fix connections");
        List<CacheObject> col = null;

        try {
            if (cacheLoadedState == CacheLoadedState.FULLY_LOADED) {
                col = cacheController.getAllCache();
                List<FIXConnection> connectionList = new ArrayList<>();
                FIXConnectionBean lb;
                for (CacheObject l : col) {
                    lb = (FIXConnectionBean) l;
                    if (lb.getStatus() != null && lb.getStatus().equals(RecordStatus.DELETED)) {
                        continue;
                    }
                    connectionList.add(lb);
                }
                return connectionList;
            } else {
                throw new FIXConnectionException("Cache load state should be fully loaded for get all in fix connections");
            }
        } catch (OMSException e) {
            logger.error("Error in loading the fix connections: ", e);
            throw new FIXConnectionException("Error occurred while loading fix connections into cache", e);
        }
    }


    public List<FIXConnection> getAll(List<String> exchangeList) throws FIXConnectionException {
        logger.debug("request for get all fix connections");
        List<CacheObject> col = null;

        try {
            if (cacheLoadedState == CacheLoadedState.FULLY_LOADED) {
                col = cacheController.getAllCache();
                List<FIXConnection> connectionList = new ArrayList<>();
                FIXConnectionBean lb;
                for (CacheObject l : col) {
                    lb = (FIXConnectionBean) l;
                    if (lb.getStatus() != null && lb.getStatus().equals(RecordStatus.DELETED)) {
                        continue;
                    }
                    if (exchangeList != null && (exchangeList.isEmpty() || exchangeList.contains(lb.getExchangeCode()))) {
                        connectionList.add(lb);
                    }
                }
                return connectionList;
            } else {
                throw new FIXConnectionException("Cache load state should be fully loaded for get all in fix connections");
            }
        } catch (OMSException e) {
            logger.error("Error in loading the fix connections: ", e);
            throw new FIXConnectionException("Error occurred while loading fix connections into cache", e);
        }
    }

    private void loadFixConnections() throws FIXConnectionException {
        logger.info("Loading all fix connections into cache");
        try {
            if (cacheLoadedState == CacheLoadedState.FULLY_LOADED) {
                cacheController.populateFullCache();
            } else {
                throw new FIXConnectionException("Cache load state should be fully loaded for fix connections");
            }
        } catch (CacheException e) {
            logger.error("Error in loading the fix connections: ", e);
            throw new FIXConnectionException("Error occurred while loading fix connections into cache", e);
        }

    }


    public void setStatus(String connectionID, RecordStatus status) throws FIXConnectionException {
        logger.debug("request for change fix connection  status for code:" + connectionID + " to status:" + status.toString());
        FIXConnectionBean lb;
        try {
            lb = (FIXConnectionBean) cacheController.readObjectFromCache(new FIXConnectionBean(connectionID));
            lb.setStatus(status);
            cacheController.modifyInCache(lb);
        } catch (CacheNotFoundException e) {
            logger.error("Error in cache - fix connection status change ", e);
            throw new FIXConnectionException("Error in cache - fix connection status change ", e);
        } catch (OMSException e) {
            logger.error("Error in set Status:", e);
            throw new FIXConnectionException("Error in changing fix connection status ", e);
        }
    }


    public void setCacheLoadedState(CacheLoadedState cacheLoadedState) {
        this.cacheLoadedState = cacheLoadedState;
    }

    public void setCacheController(CacheControllerInterface cacheController) {
        this.cacheController = cacheController;
    }

    public void setSysLevelParaManager(SysLevelParaManager sysLevelParaManager) {
        this.sysLevelParaManager = sysLevelParaManager;
    }
}
