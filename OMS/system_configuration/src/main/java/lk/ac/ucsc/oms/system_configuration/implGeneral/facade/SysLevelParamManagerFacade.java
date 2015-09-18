package lk.ac.ucsc.oms.system_configuration.implGeneral.facade;

import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.common_utility.api.enums.CacheLoadedState;
import lk.ac.ucsc.oms.system_configuration.api.SysConfigException;
import lk.ac.ucsc.oms.system_configuration.api.SysLevelParaManager;
import lk.ac.ucsc.oms.system_configuration.api.SysLevelParam;
import lk.ac.ucsc.oms.system_configuration.api.SystemParameter;
import lk.ac.ucsc.oms.system_configuration.implGeneral.bean.SysLevelParamBean;
import lk.ac.ucsc.oms.system_configuration.implGeneral.facade.cache.SysLevelParamCacheFacade;

import java.util.ArrayList;
import java.util.List;


public class SysLevelParamManagerFacade implements SysLevelParaManager {

    private SysLevelParamCacheFacade sysLevelParamCacheFacade;

    /**
     * @param sysLevelParamCacheFacade SystemLevelParamCacheFacade
     */
    public void setSysLevelParamCacheFacade(SysLevelParamCacheFacade sysLevelParamCacheFacade) {
        this.sysLevelParamCacheFacade = sysLevelParamCacheFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String addSysLevelParameter(SysLevelParam parameter) throws SysConfigException {
        if (parameter == null) {
            throw new SysConfigException("SysLevelParam cannot be null or empty in add sysLevelParameters");
        }
        SysLevelParamBean sysLevelParamBean = (SysLevelParamBean) parameter;
        sysLevelParamBean.setPrimaryKeyObject(sysLevelParamBean.getParamId());
        return sysLevelParamCacheFacade.putSysLevelParam(sysLevelParamBean);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void editSysLevelParameter(SysLevelParam editedParam) throws SysConfigException {
        if (editedParam == null) {
            throw new SysConfigException("SysLevelParam cannot be null or empty in edit sysLevelParameters");
        }
        SysLevelParamBean sysLevelParamBean = (SysLevelParamBean) editedParam;
        sysLevelParamBean.setPrimaryKeyObject(sysLevelParamBean.getParamId());
        sysLevelParamCacheFacade.updateSysLevelParam(sysLevelParamBean);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeSysLevelParameter(SystemParameter paramId) throws SysConfigException {
        if (paramId == null) {
            throw new SysConfigException("SysLevelParam cannot be null or empty in removeSysLevelParameters");
        }
        SysLevelParamBean sysLevelParamBean = sysLevelParamCacheFacade.getSysLevelParam(paramId);
        sysLevelParamCacheFacade.markSysLevelParamAsDeleted(sysLevelParamBean);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysLevelParam getSysLevelParameter(SystemParameter paramId) throws SysConfigException {
        if (paramId == null) {
            throw new SysConfigException("SysLevelParam cannot be null or empty in getSysLevelParameters ");
        }
        return sysLevelParamCacheFacade.getSysLevelParam(paramId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadAllSysParam() {
        sysLevelParamCacheFacade.loadSysLevelParams();
    }

    @Override
    public List<SysLevelParam> getAllSysLevelParams() throws SysConfigException {
        List<SysLevelParam> sysLevelParams = new ArrayList<>();
        List<CacheObject> sysLevelParamBeans = null;
        try {
            if (sysLevelParamCacheFacade.getCacheLoadedState() == CacheLoadedState.FULLY_LOADED) {
                sysLevelParamBeans = sysLevelParamCacheFacade.getAllSysLevelParams();
            } else if (sysLevelParamCacheFacade.getCacheLoadedState() == CacheLoadedState.PARTIALITY_LOADED) { //TODO --dasun-- no need to implement partail loading.
                sysLevelParamCacheFacade.persist();
                sysLevelParamBeans = sysLevelParamCacheFacade.getAllSysLevelParams();
            }
        } catch (SysConfigException e) {
            throw new SysConfigException("SysLevelParam cache error", e);
        }
        if (sysLevelParamBeans == null) {
            sysLevelParamBeans = sysLevelParamCacheFacade.findAllSystemLevelParams();
        }
        if (sysLevelParamBeans == null) {
            throw new SysConfigException("Institution not found");
        }
        for (CacheObject cO : sysLevelParamBeans) {
            sysLevelParams.add((SysLevelParam) cO);
        }
        return sysLevelParams;
    }

    /**
     * Get the the instance of the SysLevelParam bean
     *
     * @return SysLevelParam
     */
    public SysLevelParam getEmptySysLevelParam(SystemParameter sysParamId) {
        return new SysLevelParamBean(sysParamId);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize() throws SysConfigException {
        loadAllSysParam();
    }
}
