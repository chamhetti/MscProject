package lk.ac.ucsc.oms.cache.impl.infinispan;

import lk.ac.ucsc.oms.cache.api.CacheControllerInterface;
import lk.ac.ucsc.oms.cache.api.CachePersister;
import lk.ac.ucsc.oms.cache.api.beans.ActiveNode;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheException;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheNotFoundException;
import lk.ac.ucsc.oms.cache.impl.CachePersistenceController;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSRuntimeException;
import com.rits.cloning.Cloner;
import com.rits.cloning.ObjenesisInstantiationStrategy;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.query.CacheQuery;
import org.infinispan.query.SearchManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.*;


public class InfiniSpanCacheController implements CacheControllerInterface {
    private static final int CONST_MILLISECONDS_TO_A_SECOND = 1000;
    private static Logger logger = LogManager.getLogger(InfiniSpanCacheController.class);
    private static int count = 0;
    private DefaultCacheManager manager;
    private SearchManager searchManager;
    private CachePersister persister;
    private Cache<Object, CacheObject> cache, history;
    private CachePersistenceController persistanceController = new CachePersistenceController();
    private Cloner cloner = new Cloner(new ObjenesisInstantiationStrategy());
    private int timeOut = 0;
    private boolean isCacheWriteThrough;
    private String cacheName;
    private static String nodeID = "localhost";
    private static boolean isHistoryCacheEnabled = true;

    static {
        try {
            nodeID = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            nodeID = "localhost";
            logger.error("Error in getting the IP Address - ", e);
        }
        try {
            String historyCacheEnable = System.getProperty("HISTORY_CACHE_ENABLED") != null ? System.getProperty("HISTORY_CACHE_ENABLED").substring(0, 1) : "1";
            isHistoryCacheEnabled = historyCacheEnable.equalsIgnoreCase("1");
        } catch (Exception e) {
            isHistoryCacheEnabled = true;
            logger.error("Error in getting system property ");
        }
    }

    public InfiniSpanCacheController(CachePersister per, String cacheName) throws CacheException {
        if (cacheName.endsWith("_history")) {
            throw new CacheException("Cache Controller cannot initiate with history cacheNames");
        }
        logger.info("Cache Name: " + cacheName);
        this.cacheName = cacheName;
        try {
            manager = InfiniSpanCacheManager.getInstance().getManager();
            if (!isCacheDefined(cacheName)) {
                logger.debug("Cache list =" + manager.getCacheNames());
                logger.debug("Cache name " + cacheName + " not found");
                throw new CacheNotFoundException(cacheName + " is not found");
            }
            cache = manager.getCache(cacheName);
            history = manager.getCache(cacheName + "_history");
            cache.addListener(new InfiniSpanCacheListener(per));
            InfiniSpanCacheManager.registerCachePersister(cacheName, per);
        } catch (Exception e) {
            logger.error("Cannot load specific cache from Cache manger", e);
            throw new OMSRuntimeException("Cannot load specific cache from Cache manger", e);
        }
        this.persister = per;

    }

    public InfiniSpanCacheController() {

    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        InfiniSpanCacheController.count = count;
    }

    @Override
    public boolean getCacheWriteThrough() {
        return isCacheWriteThrough;
    }

    @Override
    //This is used to persist cash and holding history.
    public void persistAndClearCacheAsBulk() throws CacheException {
        logger.debug("Persisting cache entries as bulk");
        if (amIthePersister()) {  //Checks whether this instance of Cache Controller should persist or not
            persister.changeToPresentMode();            //set real time session factory. @see CachePersister
            //define temp cache map to keep the cache values
            Map tempCacheMap = new HashMap(0);
            for (Object obj : cache.keySet()) {
                tempCacheMap.put(obj, cache.get(obj));
            }
            //persist the values from the temp cache map
            persistanceController.persistCacheEntryList(persister, tempCacheMap.values());
            //remove the values taken from the temp cache map
            for (Object obj : tempCacheMap.keySet()) {
                cache.remove(obj);
            }
        }
    }

    private boolean isCacheDefined(String cacheName) {
        logger.info("Checking " + cacheName + " is defnied or not");
        java.util.Set<java.lang.String> list = manager.getCacheNames();
        boolean isCacheDefined = false;
        for (String aList : list) {
            if (aList.equalsIgnoreCase(cacheName)) {
                isCacheDefined = true;
                logger.debug(cacheName + " is defined");
                //Once the cache name is found in the list, no need to go through the rest of the list
                break;
            }
        }
        logger.info(cacheName + " defined " + isCacheDefined);
        return isCacheDefined;
    }

    public String addToCache(CacheObject o) throws CacheNotFoundException {
        logger.debug("Add to cache started: " + cacheName);
        if(isHistoryCacheEnabled) {
            saveToHistory(o);                      // save to history before add to cache
        }
        o.setNew(true);                        // for a new object, new is true
        o.setDirty(true);                      // since it should be persisted set dirty is true
        put(o);                                // put object into cache
        logger.debug("Add to cache finished: " + cacheName);
        if (isCacheWriteThrough) {
            try {
                persister.insert(o);
            } catch (OMSException e) {
                logger.error(e.toString(), e);
            }
            o.setNew(false);
            o.setDirty(false);
            cache.replace(o.getPrimaryKeyObject(), o);
        }
        return o.getPrimaryKeyObject().toString();
    }

    private synchronized void saveToHistory(CacheObject o) throws CacheNotFoundException {
        logger.debug("save history " + count);
        CacheObject tmp;
        Object tmpPrimaryKey;
        tmp = cloner.deepClone(o);
        String timestamp = new Timestamp(new Date().getTime()).toString().concat("-" + (++count));
        tmp.setPrimaryKeyObject(tmp.getPrimaryKeyObject().toString().concat("-" + timestamp));
        tmp.setLastChanged(new Timestamp(System.currentTimeMillis()));
        logger.debug("Adding object into history " + tmp.getPrimaryKeyObject());
        tmpPrimaryKey = tmp.getPrimaryKeyObject();

        //logic added to check whether null objects are saving to the history
        if (tmp != null) {
            history.put(tmpPrimaryKey, tmp);
        } else {
            logger.error("Null object found. History Key - {}, Original Cache Object - {}", tmpPrimaryKey, o);
        }

        if (isCacheWriteThrough) {
            if (persister.changeToHistoryMode()) {
                try {
                    persister.insert(tmp);
                } catch (OMSException e) {
                    logger.error(e.toString(), e);
                }
            }
            persister.changeToPresentMode();
            history.remove(tmpPrimaryKey);
        }
    }

    public void modifyInCache(CacheObject o) throws CacheNotFoundException {
        logger.debug("Modified in cache " + o.getPrimaryKeyObject());
        if(isHistoryCacheEnabled) {
            saveToHistory(o);                      // save to history before add to cache
        }
        o.setDirty(true);              // set this to true to indicate that this should be updated in physical media
        CacheObject ob = get(o);        //get the object at in the cache
        if (ob != null) {               //check whether object taken from the cache is not null
            o.setNew(ob.isNew());          //set the isNew status from it. This is to fix the issue of changing isnew of the object at cache after reading it
        }
        cache.replace(o.getPrimaryKeyObject(), o);   // add modified object
        if (isCacheWriteThrough) {
            try {
                persister.update(o);
            } catch (OMSException e) {
                logger.error(e.toString(), e);
            }
            o.setDirty(false);
            cache.replace(o.getPrimaryKeyObject(), o);
        }
    }

    public CacheObject readObjectFromCache(CacheObject cacheObj) throws OMSException {
        logger.debug("Reading object from cache " + cacheObj.getPrimaryKeyObject());
        if (isCacheWriteThrough) {
            CacheObject co = persistanceController.loadCacheEntry(persister, cacheObj);
            if (co == null) {
                throw new CacheException("Cannot found requested Object in physical media. id is :{}" + cacheObj.getPrimaryKeyObject().toString());
            }
            return co;
        } else {
            CacheObject co = get(cacheObj);

            if (co == null) {
                co = persistanceController.loadCacheEntry(persister, cacheObj);
                if (co == null) {
                    throw new CacheException("Cannot found requested Object in physical media. id is :{}" + cacheObj.getPrimaryKeyObject().toString());
                }
                put(co);
            }
            return co;
        }
    }

    public CacheObject readObjectForChange(CacheObject cacheObj) throws OMSException {
        logger.debug("Reading object from cache " + cacheObj.getPrimaryKeyObject());
        if (isCacheWriteThrough) {
            CacheObject co = persistanceController.loadCacheEntry(persister, cacheObj);
            if (co == null) {
                throw new CacheException("Cannot find requested Object in physical media. id is :{}" + cacheObj.getPrimaryKeyObject().toString());
            }
            return co;

        } else {
            CacheObject co = get(cacheObj);

            if (co == null) {
                co = persistanceController.loadCacheEntry(persister, cacheObj);
                if (co == null) {
                    throw new CacheException("Cannot find requested Object in physical media. id is :{}" + cacheObj.getPrimaryKeyObject().toString());
                }
                put(co);
            }
            return cloner.deepClone(co);
        }
    }

    public void addToCache(List<CacheObject> cacheObjectList) throws CacheNotFoundException {
        logger.debug("Adding cache objects list into cache - list size : " + cacheObjectList.size());
        for (CacheObject c : cacheObjectList) {
            addToCache(c);
        }
    }

    public List<CacheObject> getAllCache() throws CacheNotFoundException {
        logger.info("Get all cache ");
        List<CacheObject> cl = new ArrayList<>();
        for (Object aValue : cache.values()) {
            cl.add((CacheObject) aValue);
        }
        return cl;
    }

    @Override
    public synchronized void persistCache() throws CacheException {
        logger.debug("Persisting cache entry: " + cacheName);
        if (amIthePersister()) {  //Checks whether this instance of Cache Controller should persist or not
            CacheObject co;
            for (Object aValue : cache.values()) {
                co = (CacheObject) aValue;
                if (!co.isDirty()) {
                    continue;
                }
                persister.changeToPresentMode();//set real time session factory. @see CachePersister
                if (persistanceController.persistCacheEntry(persister, co)) {
                    co.setDirty(false);
                    co.setNew(false);
                    remove(co);
                    put(co);
                } else {
                    logger.error("Cannot persist object " + co.toString());
                }
            }
            //persist history entries
            if (persister.changeToHistoryMode()) {                // set history session factory
                CacheObject ho;
                Object primaryKey;
                for (Object hoValues : history.values()) {
                    ho = (CacheObject) hoValues;
                    primaryKey = ho.getPrimaryKeyObject();
                    if (persistanceController.persistHistoryCacheEntry(persister, ho)) {
                        history.remove(primaryKey); //remove persisted entries from history cache
                    } else {
                        logger.error("Cannot persist history object " + ho.toString());
                    }
                }
            }
            persister.changeToPresentMode();
        }
        logger.debug("Finished persisting cache: " + cacheName);
    }

    @Override
    public synchronized void persistCacheAsBulk() throws CacheException {
        logger.debug("Persisting cache entries as bulk");
        if (amIthePersister()) {  //Checks whether this instance of Cache Controller should persist or not

            persister.changeToPresentMode();            //set real time session factory. @see CachePersister
            persistanceController.persistCacheEntryList(persister, cache.values());

            if (persister.changeToHistoryMode()) {      // set history session factory
                if (!history.isEmpty()) {
                    Map temp = new HashMap(0);            // get history cache to temp map
                    // this is for avoid remove history entries without persisting
                    for (Object obj : history.keySet()) {
                        Object tempObj = history.get(obj);
                        if (tempObj != null) {
                            temp.put(obj, tempObj);
                        } else {
                            logger.error("Trying to save null object to DB from history cache={} key={}", cacheName, obj);
                        }
                    }
                    persistanceController.persistHistoryCacheEntryList(persister, temp.values());   // persist temp values to db

                    for (Object obj : temp.keySet()) {                                // remove temp values from history
                        history.remove(obj);
                    }
                    temp.clear();                                                   // clear temp
                }
            }
            persister.changeToPresentMode();                                         // change session factory to present
        }
    }


    @Override
    public void updateOnlyInCache(CacheObject o) throws CacheException {
        put(o);
    }

    public void removeFromCache(CacheObject o) throws CacheNotFoundException {
        logger.debug("Removing " + o.getPrimaryKeyObject() + " from cache");
        remove(o);
    }

    @Override
    public void populateFullCache() throws CacheException {
        logger.info("Populating ful cache");
        List<CacheObject> coLst = persistanceController.loadFullCache(persister);         // load all using persister
        for (CacheObject co : coLst) {
            Object key = co.getPrimaryKeyObject();
            cache.put(key, co);                                                   // add loaded entries into cache
        }
    }

    private void put(CacheObject co) throws CacheNotFoundException {
        logger.debug("Put object into cache " + co.getPrimaryKeyObject());
        Object key = co.getPrimaryKeyObject();
        cache.put(key, co);
    }

    private void remove(CacheObject co) {
        logger.debug("Removing object from cache " + co.getPrimaryKeyObject());
        Object key = co.getPrimaryKeyObject();
        cache.remove(key);
    }

    private CacheObject get(CacheObject co) {
        logger.debug("Retrieving object from cache " + co.getPrimaryKeyObject());
        Object key = co.getPrimaryKeyObject();
        return cache.get(key);
    }


    public List<CacheObject> searchFromCache(Field field, String value, Class c) throws CacheException {
        logger.debug("Searching in cache for " + field.getName() + " for " + value);
        List<CacheObject> cacheObjects = new ArrayList<>();
        searchManager = org.infinispan.query.Search.getSearchManager(cache);
        QueryBuilder queryBuilder = searchManager.buildQueryBuilderForClass(c).get();
        org.apache.lucene.search.Query luceneQuery = queryBuilder.keyword().onField(field.getName()).matching(value).createQuery();
        logger.debug("luceneQuery>>" + luceneQuery);
        CacheQuery query = searchManager.getQuery(luceneQuery, c);
        logger.debug("query>>" + query);
        List<Object> objectList = query.list();
        for (Object co : objectList) {
            CacheObject cashedObject = (CacheObject) co;
            cacheObjects.add(cashedObject);
        }
        return cacheObjects;
    }


    public List<CacheObject> searchFromCache(String fieldName, String value, Class c) throws CacheException {
        logger.debug("Search in cache " + fieldName + " for " + value);
        List<CacheObject> cacheObjects = new ArrayList<>();
        searchManager = org.infinispan.query.Search.getSearchManager(cache);
        QueryBuilder queryBuilder = searchManager.buildQueryBuilderForClass(c).get();
        org.apache.lucene.search.Query luceneQuery = queryBuilder.keyword().onField(fieldName).matching(value).createQuery();
        logger.debug("luceneQuery>>" + luceneQuery);
        CacheQuery query = searchManager.getQuery(luceneQuery, c);
        logger.debug("query>>" + query);
        List<Object> objectList = query.list();
        for (Object co : objectList) {
            CacheObject cashedObject = (CacheObject) co;
            cacheObjects.add(cashedObject);
        }
        return cacheObjects;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearCache() throws CacheException {
        logger.debug("Clear cache invoked ");
        cache.clear();
    }

    private boolean amIthePersister() {
        //remove node config manager and add the machine ip address as the Node ID and time out is to be taken from
        //the spring config file
        Cache<Object, ActiveNode> controlCache;
        controlCache = manager.getCache("ControlCache");
        ActiveNode h = controlCache.get("ActiveNode");
        //Handle the situation where this is the first persistence attempt where Active node is not selected.
        if (h == null) {
            logger.error("No Active node in ControlCache");
            logger.error("This means trying to persist for the first time since startup");
            h = new ActiveNode();
            h.setNodeID(nodeID);
            h.setLastUsedTime(new Date(0)); //set last used time as a old date as no persistence has happened
            controlCache.put("ActiveNode", h);
            logger.error("Made Node =" + nodeID + " as the active node for persistence");
        }
        if (h.getNodeID() != null && h.getNodeID().equalsIgnoreCase(nodeID)) {
            h.setLastUsedTime(new Date());   //Set current time as last persisted time
            return true;
        } else {
            Date lastUsedTime = h.getLastUsedTime();
            Date currentTime = new Date();
            long timeDiff = (currentTime.getTime() - lastUsedTime.getTime()) / CONST_MILLISECONDS_TO_A_SECOND;
            if (timeDiff > timeOut) {
                //This is interpreted as ActiveNode is dead
                //Make this node as ActiveNode
                h = new ActiveNode();
                h.setNodeID(nodeID);
                h.setLastUsedTime(new Date());
                controlCache.put("ActiveNode", h);
                return true;
            } else {
                return false;
            }
        }
    }


    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    @Override
    public void setCacheWriteThrough(boolean cacheWriteThrough) {
        isCacheWriteThrough = cacheWriteThrough;
    }

    public Cache<Object, CacheObject> getHistory() {
        return history;
    }

    public void setHistory(Cache<Object, CacheObject> history) {
        this.history = history;
    }

    public Cache<Object, CacheObject> getCache() {
        return cache;
    }

    public void setCache(Cache<Object, CacheObject> cache) {
        this.cache = cache;
    }

    public CachePersister getPersister() {
        return persister;
    }

    public void setPersister(CachePersister persister) {
        this.persister = persister;
    }
}