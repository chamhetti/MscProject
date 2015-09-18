package lk.ac.ucsc.oms.fix.api.exception;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;

public class FIXOrderException extends OMSException {

    public FIXOrderException(String message) {
        super(message);
    }

    public FIXOrderException(String message, Exception e) {
        super(message, e);
    }
}
