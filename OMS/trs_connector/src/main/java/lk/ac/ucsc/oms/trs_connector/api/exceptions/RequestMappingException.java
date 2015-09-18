package lk.ac.ucsc.oms.trs_connector.api.exceptions;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;

/**
 * Protocol violation exception - MIX request and TRS connector mapping exception
 * <p/>
 * User: Buddhika Semasinghe
 * Date: 8/23/13
 * Time: 2:28 PM
 */
public class RequestMappingException extends OMSException {
    public RequestMappingException(String message) {
        super(message);
    }

    public RequestMappingException(String message, Exception e) {
        super(message, e);
    }
}
