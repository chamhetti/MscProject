package lk.ac.ucsc.oms.rms_equity.api.exceptions;


import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSRuntimeException;

/**
 * Use to throw run time Exception use in Equity RMS Module
 * User: Hetti
 */
public class EquityRmsRunTimeException extends OMSRuntimeException {
    /**
     * This will create custom exception with given detail message & root cause
     *
     * @param message is the detail message
     * @param e       is the throwable
     */
    public EquityRmsRunTimeException(String message, Exception e) {
        super(message, e);
    }

    /**
     * This will create custom exception with given detail message
     *
     * @param message is the detail message
     */
    public EquityRmsRunTimeException(String message) {
        super(message);
    }
}
