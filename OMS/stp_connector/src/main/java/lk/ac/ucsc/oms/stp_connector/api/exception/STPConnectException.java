package lk.ac.ucsc.oms.stp_connector.api.exception;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;


public class STPConnectException extends OMSException {

    public STPConnectException(String message) {
        super(message);
    }


    public STPConnectException(String message, Exception cause) {
        super(message, cause);
    }
}
