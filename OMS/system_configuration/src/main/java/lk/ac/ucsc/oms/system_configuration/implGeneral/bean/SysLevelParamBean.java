package lk.ac.ucsc.oms.system_configuration.implGeneral.bean;

import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.system_configuration.api.SysLevelParam;
import lk.ac.ucsc.oms.system_configuration.api.SystemParameter;

public class SysLevelParamBean extends CacheObject implements SysLevelParam {
    private SystemParameter paramId;
    private String paraValue;
    private String description;
    private final static int HASH_CODE = 31;

    /**
     * constructor with paramId given
     *
     * @param paramId
     */
    public SysLevelParamBean(SystemParameter paramId) {
        this.paramId = paramId;
    }

    /**
     * default constructor
     */
    public SysLevelParamBean() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SystemParameter getParamId() {
        return paramId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setParamId(SystemParameter paramId) {
        this.paramId = paramId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getParaValue() {
        return paraValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setParaValue(String paraValue) {
        this.paraValue = paraValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SysLevelParamBean that = (SysLevelParamBean) o;

        if (description != null ? !description.equals(that.description) : that.description != null) {
            return false;
        }
        if (paraValue != null ? !paraValue.equals(that.paraValue) : that.paraValue != null) {
            return false;
        }
        return paramId == that.paramId;

    }

    @Override
    public int hashCode() {
        int result = paramId != null ? paramId.hashCode() : 0;
        result = HASH_CODE * result + (paraValue != null ? paraValue.hashCode() : 0);
        result = HASH_CODE * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SysLevelParamBean{" +
                "paramId=" + paramId +
                ", paraValue='" + paraValue + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
