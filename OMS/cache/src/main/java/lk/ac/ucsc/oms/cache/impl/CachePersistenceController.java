package lk.ac.ucsc.oms.cache.impl;

import lk.ac.ucsc.oms.cache.api.CachePersister;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheException;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class CachePersistenceController {

    private static Logger logger = LogManager.getLogger(CachePersistenceController.class);


    public boolean persistCacheEntry(CachePersister c, CacheObject co) {
        if (co.isNew()) {            // check whether new object or not
            try {
                c.insert(co);
                return true;
            } catch (OMSException e) {
                logger.error(e.toString() + ":{}", e, e);
            }

        } else if (co.isDirty()) {   // is dirty means should update object
            try {
                c.update(co);
                return true;
            } catch (OMSException e) {
                logger.error(e.toString() + "{}", e, e);
            }
        } else {
            return true;
        }
        return false;
    }


    public boolean persistHistoryCacheEntry(CachePersister c, CacheObject co) {
        try {
            c.insert(co);
            return true;
        } catch (OMSException e) {
            logger.error(e.toString() + "{}", e, e);
        }
        return false;
    }

    public List<CacheObject> loadFullCache(CachePersister c) throws CacheException {
        return c.loadAll();
    }


    public List<CacheObject> loadCachePartially(CachePersister c) throws CacheException {
        return c.loadPartially();
    }


    public CacheObject loadCacheEntry(CachePersister c, CacheObject co) throws OMSException {
        return c.load(co);
    }


    public boolean persistCacheEntryList(CachePersister c, Collection<CacheObject> co) {
        boolean result = true;
        List<CacheObject> newObjectList = createCacheObjectArrayList();
        List<CacheObject> updatedObjectList = createCacheObjectArrayList();
        for (CacheObject o : co) {
            if (!o.isDirty()) {                 // if is dirty false, then no change in the object. no need to persist
                continue;
            }
            if (o.isNew() && o.isDirty()) {     // if is new and is dirty true, then it is a new object
                newObjectList.add(o);
                continue;
            }
            if (o.isDirty()) {                   // if is dirty true then it is an updated object
                updatedObjectList.add(o);
            }
        }

        //insert the new cache objects to the database
        if (!newObjectList.isEmpty()) {
            c.insertList(newObjectList);
        }
        //update the cache objects to the database
        if (!updatedObjectList.isEmpty()) {
            c.updateList(updatedObjectList);
        }

        return result;

    }

    /**
     * This method is used to create CacheObject lists
     *
     * @return a List of CacheObject
     */
    public List<CacheObject> createCacheObjectArrayList() {
        return new ArrayList<>();
    }


    /**
     * Method used to insert list of history cache objects to DB
     *
     * @param c
     * @param co
     * @return
     */
    public boolean persistHistoryCacheEntryList(CachePersister c, Collection<CacheObject> co) {
        boolean result = true;
        List<CacheObject> newObjectList = createCacheObjectArrayList();
        for (CacheObject o : co) {
            newObjectList.add(o);
        }

        //insert the new cache objects to the database
        if (!newObjectList.isEmpty()) {
            c.insertList(newObjectList);
        }

        return result;

    }
}
