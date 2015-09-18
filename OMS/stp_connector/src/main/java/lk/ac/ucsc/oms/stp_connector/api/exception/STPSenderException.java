package lk.ac.ucsc.oms.stp_connector.api.exception;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;


public class STPSenderException extends OMSException {

    public STPSenderException(String message) {
        super(message);
    }


    public STPSenderException(String message, Exception cause) {
        super(message, cause);
    }
}
