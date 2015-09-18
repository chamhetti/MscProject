package lk.ac.ucsc.oms.customer.implGeneral.facade.cache;

import lk.ac.ucsc.oms.cache.api.CacheControllerInterface;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheNotFoundException;
import lk.ac.ucsc.oms.customer.api.exceptions.CashManagementException;
import lk.ac.ucsc.oms.customer.implGeneral.beans.login.LoginHistoryBean;
import lk.ac.ucsc.oms.system_configuration.api.SysConfigException;
import lk.ac.ucsc.oms.system_configuration.api.SysLevelParaManager;
import lk.ac.ucsc.oms.system_configuration.api.SystemParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LoginHistoryCacheFacade {
    private static Logger logger = LogManager.getLogger(LoginHistoryCacheFacade.class);
    private SysLevelParaManager sysLevelParaManager;
    private CacheControllerInterface cacheController;


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


    public void setSysLevelParaManager(SysLevelParaManager sysLevelParaManager) {
        this.sysLevelParaManager = sysLevelParaManager;
    }

    public void putLoginHistory(LoginHistoryBean loginHistoryBean) throws CashManagementException {
        loginHistoryBean.setPrimaryKeyObject(loginHistoryBean.getId());
        try {
            cacheController.addToCache(loginHistoryBean);
        } catch (CacheNotFoundException e) {
            logger.error("Cannot add to Cache.Error in Cache !!", e);
            throw new CashManagementException("Error in adding the LoginHistory", e);
        }
    }

    public void updateLoginHistory(LoginHistoryBean loginHistoryBean) throws CashManagementException {
        loginHistoryBean.setPrimaryKeyObject(loginHistoryBean.getId());
        try {
            cacheController.modifyInCache(loginHistoryBean);
        } catch (CacheNotFoundException e) {
            logger.error("Cannot Update to Cache.Error in Cache !! ", e);
            throw new CashManagementException("Error in updating the loginHistory", e);
        }
    }

}
