package lk.ac.ucsc.oms.rms_equity.api.exceptions;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;

/**
 * Use to throw checked Exception use in Equity RMS Module
 * User: Hetti
 */
public class EquityRmsException extends OMSException {
    /**
     * This will create custom exception with given detail message
     *
     * @param message is the detail message
     */
    public EquityRmsException(String message) {
        super(message);
    }

    /**
     * This will create custom exception with given detail message & root cause
     *
     * @param message is the detail message
     * @param e       is the throwable
     */
    public EquityRmsException(String message, Exception e) {
        super(message, e);
    }
}
