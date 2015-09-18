package lk.ac.ucsc.oms.trs_connector.api.exceptions;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;

/**
 * @author tharinduj
 * @author AmilaS
 */
public class NoSuchGroupException extends OMSException {
    public NoSuchGroupException(String message) {
        super(message);
    }

    public NoSuchGroupException(String message, Exception e) {
        super(message, e);
    }
}
