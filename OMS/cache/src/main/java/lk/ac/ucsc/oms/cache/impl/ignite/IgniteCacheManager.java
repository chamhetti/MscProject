package lk.ac.ucsc.oms.cache.impl.ignite;

import lk.ac.ucsc.oms.cache.api.CachePersister;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheNotFoundException;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSRuntimeException;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * User: chamindah
 * Date: 3/23/15
 * Time: 12:35 PM
 */
public class IgniteCacheManager {
    private static Logger logger = LogManager.getLogger(IgniteCacheManager.class);
    //default cache manager provide by infinispan
    private Ignite manager;
    private static IgniteCacheManager infiniSpanCacheManager;
    //map contain the cache and the persister of that cache
    private static Map<String, CachePersister> cacheMetaMap = new HashMap<>();


    private IgniteCacheManager() throws CacheNotFoundException {
        try {
            manager = Ignition.start("../ignite-cache.xml") ;
        } catch (Exception e) {
            logger.error("Cannot load cache from Cache manger.", e);
            throw new OMSRuntimeException("Cannot load cache from Cache manger", e);
        }
    }



    public synchronized static IgniteCacheManager getInstance() throws CacheNotFoundException {
        if (infiniSpanCacheManager == null) {
            infiniSpanCacheManager = new IgniteCacheManager();
        }
        return infiniSpanCacheManager;
    }


    public Ignite getManager() {
        return manager;
    }


    public static void registerCachePersister(String cacheName, CachePersister cachePersister) {
        cacheMetaMap.put(cacheName, cachePersister);
    }


    public static CachePersister getCachePersister(String cacheName) {
        return cacheMetaMap.get(cacheName);
    }

}
