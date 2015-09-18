package lk.ac.ucsc.oms.cache.api.exceptions;


public class CacheNotFoundException extends CacheException {

    public CacheNotFoundException(Exception e) {
        super(e);
    }

    public CacheNotFoundException(String message) {
        super(message);
    }

    public CacheNotFoundException(String message,Exception e) {
        super(message,e);
    }

}
