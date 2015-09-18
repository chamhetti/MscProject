package lk.ac.ucsc.oms.fixConnection.api.exceptions;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;

public class FIXConnectionException extends OMSException {

    public FIXConnectionException(String message) {
        super(message);
    }

    public FIXConnectionException(String message, Exception e) {
        super(message,e);
    }

}
