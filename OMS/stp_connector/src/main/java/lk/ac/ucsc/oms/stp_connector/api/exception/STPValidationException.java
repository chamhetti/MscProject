package lk.ac.ucsc.oms.stp_connector.api.exception;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;


public class STPValidationException extends OMSException {


    public STPValidationException(String message, Exception cause) {
        super(message, cause);
    }
}
