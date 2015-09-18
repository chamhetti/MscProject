package lk.ac.ucsc.oms.cache.impl.infinispan;

import lk.ac.ucsc.oms.cache.api.CachePersister;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.annotation.CacheEntriesEvicted;
import org.infinispan.notifications.cachelistener.event.CacheEntriesEvictedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;


@Listener
public class InfiniSpanCacheListener {
    private static Logger logger = LogManager.getLogger(InfiniSpanCacheListener.class);
    private CachePersister persister;
    public InfiniSpanCacheListener(CachePersister persister) {
        this.persister = persister;
    }

    @CacheEntriesEvicted
    public void handleWhenEntriesEvicted(CacheEntriesEvictedEvent event) {
        Map<Object, Object> entries = event.getEntries();
        for (Map.Entry<Object, Object> entry : entries.entrySet()) {
            Object key = entry.getKey();
            CacheObject co = (CacheObject) entry.getValue();
            logger.info("Cache entry evicted -> Key : {} Value :{} in :{} ", key, co, this);
            if (co.isDirty()) {
                if (co.isNew()) {
                    try {
                        persister.insert(co);
                        co.setNew(false);
                        co.setDirty(false);
                        logger.info("Evicted cache entry inserted {} from the listener {}", co, this);
                    } catch (OMSException e) {
                        logger.error("Error occurred while inserting the cache object {}", co);
                        continue;
                    }
                } else {
                    try {
                        persister.update(co);
                        co.setNew(false);
                        co.setDirty(false);
                        logger.info("Evicted cache entry updated {} from the listener {}", co, this);
                    } catch (OMSException e) {
                        logger.error("Error occurred while updating the cache object {}", co);
                        continue;
                    }
                }
            } else {
                logger.debug("Cache object is not not dirty hence it will not be persisted");
            }
        }
    }
}
