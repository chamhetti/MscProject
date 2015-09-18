package lk.ac.ucsc.oms.cache.api.exceptions;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;


public class CacheException extends OMSException {

    public CacheException(Exception e) {
        super(e);
    }

    public CacheException(String message) {
        super(message);
    }

    public CacheException(String message, Exception e) {
        super(message, e);
    }
}
