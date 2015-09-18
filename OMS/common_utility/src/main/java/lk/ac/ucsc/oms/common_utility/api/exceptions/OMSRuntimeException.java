package lk.ac.ucsc.oms.common_utility.api.exceptions;


public class OMSRuntimeException extends RuntimeException {

    public OMSRuntimeException() {
        super();
    }

    public OMSRuntimeException(String message, Exception e) {
        super(message, e);
    }

    public OMSRuntimeException(String message) {
        super(message);
    }
}
