package lk.ac.ucsc.oms.cache.impl.infinispan;

import lk.ac.ucsc.oms.cache.api.CachePersister;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheNotFoundException;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSRuntimeException;
import org.infinispan.manager.DefaultCacheManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;


public final class InfiniSpanCacheManager {
    private static Logger logger = LogManager.getLogger(InfiniSpanCacheManager.class);
    private DefaultCacheManager manager;
    private static InfiniSpanCacheManager infiniSpanCacheManager;
    private static Map<String, CachePersister> cacheMetaMap = new HashMap<>();

    private InfiniSpanCacheManager() throws CacheNotFoundException {
        try {
            manager = new DefaultCacheManager("infinispan-cache.xml");

        } catch (Exception e) {
            logger.error("Cannot load cache from Cache manger.", e);
            throw new OMSRuntimeException("Cannot load cache from Cache manger", e);
        }
    }

    public synchronized static InfiniSpanCacheManager getInstance() throws CacheNotFoundException {
        if (infiniSpanCacheManager == null) {
            infiniSpanCacheManager = new InfiniSpanCacheManager();
        }
        return infiniSpanCacheManager;
    }

    public DefaultCacheManager getManager() {
        return manager;
    }

    public static void registerCachePersister(String cacheName, CachePersister cachePersister) {
        cacheMetaMap.put(cacheName, cachePersister);
    }

}
