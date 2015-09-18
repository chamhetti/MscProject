package lk.ac.ucsc.oms.trs_connector.api.exceptions;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;

/**
 * @author tharinduj
 * @author AmilaS
 */
public class NoSuchProcessException extends OMSException {

    public NoSuchProcessException(String message) {
        super(message);
    }

    public NoSuchProcessException(String message, Exception e) {
        super(message, e);
    }

}
