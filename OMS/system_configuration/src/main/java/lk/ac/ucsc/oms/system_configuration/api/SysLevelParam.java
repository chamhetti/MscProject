package lk.ac.ucsc.oms.system_configuration.api;


public interface SysLevelParam {

    /**
     * give a primary key of a SysLevelParamer
     *
     * @return SystemParameter
     */
    SystemParameter getParamId();

    /**
     * set the primary key of a SysLevelParam object that used as a unique key for a SysLevelParam object
     *
     * @param paramId SystemParameter
     */
    void setParamId(SystemParameter paramId);

    /**
     * get the ParaValue.
     *
     * @return String
     */
    String getParaValue();

    /**
     * set the ParaValue
     *
     * @param paraValue String
     */
    void setParaValue(String paraValue);

    /**
     * get the SysLevelParam Description.
     *
     * @return String
     */
    String getDescription();

    /**
     * set the SysLevelParam Description
     *
     * @param description String
     */
    void setDescription(String description);
}
