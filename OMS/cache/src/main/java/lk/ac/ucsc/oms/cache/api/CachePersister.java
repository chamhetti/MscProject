package lk.ac.ucsc.oms.cache.api;

import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;

import java.util.List;

public interface CachePersister {

    void update(CacheObject co) throws OMSException;

    void insert(CacheObject co) throws OMSException;

    CacheObject load(CacheObject co) throws OMSException;

    List<CacheObject> loadAll();

    List<CacheObject> loadPartially();

    void remove(CacheObject co) throws OMSException;

    boolean changeToHistoryMode();

    boolean changeToPresentMode();

    void insertList(List<CacheObject> objectList);

    void updateList(List<CacheObject> updateList);

}
