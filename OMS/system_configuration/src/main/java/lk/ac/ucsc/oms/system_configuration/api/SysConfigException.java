package lk.ac.ucsc.oms.system_configuration.api;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;


public class SysConfigException extends OMSException {
    public SysConfigException(String message) {
        super(message);
    }

    public SysConfigException(String message, Exception e) {
        super(message, e);
    }
}
