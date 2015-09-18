package lk.ac.ucsc.oms.cache.api;

import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheException;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheNotFoundException;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;

import java.lang.reflect.Field;
import java.util.List;


public interface CacheControllerInterface {

    String addToCache(CacheObject o) throws CacheNotFoundException;

    void modifyInCache(CacheObject o) throws CacheNotFoundException;
    void removeFromCache(CacheObject o) throws CacheNotFoundException;
    CacheObject readObjectFromCache(CacheObject o) throws OMSException;

    CacheObject readObjectForChange(CacheObject cacheObj) throws OMSException;

    void addToCache(List<CacheObject> cacheObjectList) throws CacheNotFoundException;

    List<CacheObject> getAllCache() throws CacheNotFoundException;

    void persistCache() throws CacheException;

    void populateFullCache() throws CacheException;

    List<CacheObject> searchFromCache(Field field, String value, Class c) throws CacheException;

    List<CacheObject> searchFromCache(String fieldName, String value, Class c) throws CacheException;

    void clearCache() throws CacheException;

    void persistCacheAsBulk() throws CacheException;

    void updateOnlyInCache(CacheObject o) throws CacheException;

    boolean getCacheWriteThrough();

    void setCacheWriteThrough(boolean cacheOff);

    void persistAndClearCacheAsBulk() throws CacheException;


}
