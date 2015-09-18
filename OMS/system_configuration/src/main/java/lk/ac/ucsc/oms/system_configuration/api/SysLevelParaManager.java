package lk.ac.ucsc.oms.system_configuration.api;

import java.util.List;


public interface SysLevelParaManager {

    /**
     * Add a new SysLevelParam to cache and then to DB
     *
     * @param parameter
     * @return
     * @throws SysConfigException
     */
    String addSysLevelParameter(SysLevelParam parameter) throws SysConfigException;

    /**
     * Update the existing SysLevelParam in the system.
     *
     * @param editedParam changed SysLevelParam object that was taken using getSysLevelParameter method
     * @throws SysConfigException
     */
    void editSysLevelParameter(SysLevelParam editedParam) throws SysConfigException;

    /**
     * Remove the SysLevelParam from the cache and change the status of the rule as deleted.
     *
     * @param paramId SysLevelParams' unique id
     * @throws SysConfigException
     */
    void removeSysLevelParameter(SystemParameter paramId) throws SysConfigException;

    /**
     * Used to get the SysLevelParam using paramId
     *
     * @param paramId paramId
     * @return SysLevelParam bean with all the data
     * @throws SysConfigException Error with code
     */
    SysLevelParam getSysLevelParameter(SystemParameter paramId) throws SysConfigException;

    /**
     * Load the list of all the SysLevelParameters to the cache
     */
    void loadAllSysParam();

    /**
     * initialize the module      *
     * @throws SysConfigException Error with code
     */
    void initialize()throws SysConfigException;

    /**
     * Used to get the SysLevelParam List
     * @return SysLevelParam bean List with all the data
     * @throws SysConfigException Error with code
     */
    List<SysLevelParam> getAllSysLevelParams() throws SysConfigException;
}
