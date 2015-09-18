package lk.ac.ucsc.oms.cache.impl.ignite;

import com.rits.cloning.Cloner;
import com.rits.cloning.ObjenesisInstantiationStrategy;
import lk.ac.ucsc.oms.cache.impl.CachePersistenceController;
import lk.ac.ucsc.oms.cache.impl.infinispan.InfiniSpanCacheManager;
import lk.ac.ucsc.oms.cache.api.CacheControllerInterface;
import lk.ac.ucsc.oms.cache.api.CachePersister;
import lk.ac.ucsc.oms.cache.api.beans.ActiveNode;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheException;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheNotFoundException;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSRuntimeException;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.SqlQuery;
import org.apache.ignite.configuration.CacheConfiguration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.*;
import javax.cache.Cache;

/**
 * User: chamindah
 * Date: 3/23/15
 * Time: 12:32 PM
 */
public class IgniteCacheController implements CacheControllerInterface {
    private static final int CONST_MILLISECONDS_TO_A_SECOND = 1000;
    private static Logger logger = LogManager.getLogger(IgniteCacheController.class);
    private static int count = 0;
    private Ignite manager;
     private CachePersister persister;
    private IgniteCache<Object, CacheObject> cache, history;
    private CachePersistenceController persistanceController = new CachePersistenceController();
    private Cloner cloner = new Cloner(new ObjenesisInstantiationStrategy());
    private int timeOut = 30;
    private boolean isCacheWriteThrough;
    private String cacheName;

    /**
     * Registering cache and cache persister by using cache manager happens in this method
     *
     * @param per       reference of the persister
     * @param cacheName name of the accessing cache
     * @throws lk.ac.ucsc.oms.cache.api.exceptions.CacheException
     */
    public IgniteCacheController(CachePersister per, String cacheName) throws CacheException {
        if (cacheName.endsWith("_history")) {
            throw new CacheException("Cache Controller cannot initiate with history cacheNames");
        }
        logger.info("Cache Name: " + cacheName);
        this.cacheName = cacheName;
        try {
            manager = IgniteCacheManager.getInstance().getManager();

            //Below is done to check cache is defined in infinispan-cache.xml file. Default behaviour in
            //infinispan is if cache is not defined a new cache get created with default parameters, which is not the behaviour we need.
            //Hence below check is implemented
            if (!isCacheDefined(cacheName)) {
                logger.debug("Cache name " + cacheName + " not found");
                throw new CacheNotFoundException(cacheName + " is not found");
            }
            cache = manager.jcache(cacheName);
            history = manager.jcache(cacheName + "_history");
            /**
             * Adding Cache listeners to check the cache operations like eviction
             */
          //  cache.addListener(new InfiniSpanCacheListener(per));
            InfiniSpanCacheManager.registerCachePersister(cacheName, per);
        } catch (Exception e) {
            logger.error("Cannot load specific cache from Cache manger", e);
            throw new OMSRuntimeException("Cannot load specific cache from Cache manger", e);
        }
        this.persister = per;

    }


    public IgniteCacheController() {

    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        IgniteCacheController.count = count;
    }

    /**
     * get cache write though (both DB and Cache update at the same transaction
     * or write Behind (cache is updated and later using a timer Db is updated
     *
     * @return cacheWriteThrough
     */
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
            Iterator iterator = cache.iterator();
            //define temp cache map to keep the cache values
            Map tempCacheMap = new HashMap(0);
            Cache.Entry<Object, CacheObject> entry = null;
            while (iterator.hasNext()) {
                entry = (Cache.Entry) iterator.next();
                tempCacheMap.put(entry.getKey(), entry.getValue());
            }

            //persist the values from the temp cache map
            persistanceController.persistCacheEntryList(persister, tempCacheMap.values());

            //remove the values taken from the temp cache map
            for (Object obj : tempCacheMap.keySet()) {
                cache.remove(obj);
            }
        }
    }



    /**
     * To check whether given cache is defined in the cache entries or not
     *
     * @param cacheName name of the cache need to check for availability
     * @return true if available, false otherwise
     */
    private boolean isCacheDefined(String cacheName) {
        logger.info("Checking " + cacheName + " is defnied or not");
        CacheConfiguration[] configList= manager.configuration().getCacheConfiguration();
        Arrays.asList(configList) ;
        boolean isCacheDefined = false;
        for (CacheConfiguration aList : Arrays.asList(configList)) {
            if (aList.getName().equals(cacheName)) {
                isCacheDefined = true;
                logger.debug(cacheName + " is defined");
                //Once the cache name is found in the list, no need to go through the rest of the list
                break;
            }
        }
        logger.info(cacheName + " defined " + isCacheDefined);
        return isCacheDefined;
    }

    /**
     * Method to put object into cache
     *
     * @param o in put data (CacheObject)
     * @throws CacheNotFoundException
     */
    public String addToCache(CacheObject o) throws CacheNotFoundException {
        logger.debug("Add to cache started: " + cacheName);
        saveToHistory(o);                      // save to history before add to cache
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

    /**
     * Save cache entry into history
     *
     * @param o in put data (CacheObject)
     * @throws CacheNotFoundException
     */

    private synchronized void saveToHistory(CacheObject o) throws CacheNotFoundException {
        logger.debug("save history " + count);
        CacheObject tmp;
        Object tmpPrimaryKey;
        tmp = cloner.deepClone(o);
        // clone object and modify cloned object before persist it
        String timestamp = new Timestamp(new Date().getTime()).toString().concat("-" + (++count));
        tmp.setPrimaryKeyObject(tmp.getPrimaryKeyObject().toString().concat("-" + timestamp));
        // set timestamp for primary key object in history cache
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

    /**
     * modify objects in cache is done through this method.it actually does is remove the existing object from the cache
     * and put the new object to the cache
     *
     * @param o in put data (CacheObject)
     * @throws CacheNotFoundException
     */
    public void modifyInCache(CacheObject o) throws CacheNotFoundException {
        logger.debug("Modified in cache " + o.getPrimaryKeyObject());
        saveToHistory(o);              // save to history before modify
        //remove(o);                     // remove existing object
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

    /**
     * Method to read object from cache
     *
     * @param cacheObj in put data (CacheObject)
     * @return requested cache object
     * @throws CacheException
     */
    public CacheObject readObjectFromCache(CacheObject cacheObj) throws OMSException {
        logger.debug("Reading object from cache " + cacheObj.getPrimaryKeyObject());
        CacheObject co = get(cacheObj);

        if (co == null) {
            co = persistanceController.loadCacheEntry(persister, cacheObj);
            if (co == null) {
                throw new CacheException("Cannot found requested Object in physical media. id is :{}" + cacheObj.getPrimaryKeyObject().toString());
            }
            put(co);
            //co = get(cacheObj); //Dasun Commented this as it is not needed
        }
        return co;
    }

    /**
     * Method read the object from cache and return a deep coppy of it
     *
     * @param cacheObj
     * @return
     * @throws lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException
     */
    public CacheObject readObjectForChange(CacheObject cacheObj) throws OMSException {
        logger.debug("Reading object from cache " + cacheObj.getPrimaryKeyObject());
        CacheObject co = get(cacheObj);

        if (co == null) {
            co = persistanceController.loadCacheEntry(persister, cacheObj);
            if (co == null) {
                throw new CacheException("Cannot find requested Object in physical media. id is :{}" + cacheObj.getPrimaryKeyObject().toString());
            }
            put(co);
            //co = get(cacheObj); //Dasun Commented this as it is not needed
        }
        return cloner.deepClone(co);
    }

    /**
     * Method to put list of objects into cache
     *
     * @param cacheObjectList list of CacheObject(s) that want to put into cache
     * @throws CacheNotFoundException
     */
    public void addToCache(List<CacheObject> cacheObjectList) throws CacheNotFoundException {
        logger.debug("Adding cache objects list into cache - list size : " + cacheObjectList.size());
        for (CacheObject c : cacheObjectList) {
            addToCache(c);
        }
    }

    /**
     * Method to get all the objects available in cache
     *
     * @return list of CacheObject(s) that
     * @throws CacheNotFoundException
     */
    public List<CacheObject> getAllCache() throws CacheNotFoundException {
        logger.info("Get all cache ");
        List<CacheObject> cl = new ArrayList<>();
        Iterator iterator = cache.iterator();
        Cache.Entry<Object, CacheObject> entry = null;
        while (iterator.hasNext()) {
            entry = (Cache.Entry) iterator.next();
            cl.add(entry.getValue());
        }
        return cl;
    }


    public List<CacheObject> getAllInHistoryCache() throws CacheNotFoundException {
        logger.info("Get all cache ");
        List<CacheObject> cl = new ArrayList<>();
        Iterator iterator = history.iterator();
        Cache.Entry<Object, CacheObject> entry = null;
        while (iterator.hasNext()) {
            entry = (Cache.Entry) iterator.next();
            cl.add(entry.getValue());
        }
        return cl;
    }
    /**
     * Persist cache entries one by one both history entries and realtime entries
     *
     * @throws CacheException
     */
    @Override
    public synchronized void persistCache() throws CacheException {
        logger.debug("Persisting cache entry: " + cacheName);
        if (amIthePersister()) {  //Checks whether this instance of Cache Controller should persist or not
            CacheObject co;

            Iterator iterator = cache.iterator();
            Cache.Entry<Object, CacheObject> entry = null;
            while (iterator.hasNext()) {
                entry = (Cache.Entry) iterator.next();
                co = entry.getValue();
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
                iterator = history.iterator();
                while (iterator.hasNext()) {
                    entry = (Cache.Entry) iterator.next();
                    ho = entry.getValue();
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

    /**
     * Persist cache entries list
     *
     * @throws CacheException
     */
    @Override
    public synchronized void persistCacheAsBulk() throws CacheException {
        logger.debug("Persisting cache entries as bulk");
        if (amIthePersister()) {  //Checks whether this instance of Cache Controller should persist or not

            persister.changeToPresentMode();            //set real time session factory. @see CachePersister

            persistanceController.persistCacheEntryList(persister, getAllCache());

            if (persister.changeToHistoryMode()) {      // set history session factory
                if (history.size()!=0) {
                    // this is for avoid remove history entries without persisting
                    persistanceController.persistHistoryCacheEntryList(persister, getAllInHistoryCache());   // persist temp values to db
                    history.clear();
                }
            }

            persister.changeToPresentMode();                                         // change session factory to present
        }
    }

    /**
     * Remove an object from a given cache. Name of the cache and object to be changed should be given
     * as argument.
     *
     * @param o CacheObject need to remove from cache
     * @throws CacheNotFoundException
     */

    public void removeFromCache(CacheObject o) throws CacheNotFoundException {
        logger.debug("Removing " + o.getPrimaryKeyObject() + " from cache");
        remove(o);
    }

    @Override
    public void updateOnlyInCache(CacheObject o) throws CacheException {
        put(o);
    }

    /**
     * Load all the entries into cache
     *
     * @throws CacheException
     */
    @Override
    public void populateFullCache() throws CacheException {
        logger.info("Populating ful cache");
        List<CacheObject> coLst = persistanceController.loadFullCache(persister);         // load all using persister
        for (CacheObject co : coLst) {
            Object key = co.getPrimaryKeyObject();
            cache.put(key, co);                                                   // add loaded entries into cache
        }
    }

    /**
     * This is for add given entries into cache
     *
     * @param co entry need to add into cache
     * @throws CacheNotFoundException
     */
    private void put(CacheObject co) throws CacheNotFoundException {
        logger.debug("Put object into cache " + co.getPrimaryKeyObject());
        Object key = co.getPrimaryKeyObject();
        cache.put(key, co);
    }

    /**
     * This is for remove given entry from cache
     *
     * @param co entry need to remove from cache
     */
    private void remove(CacheObject co) {
        logger.debug("Removing object from cache " + co.getPrimaryKeyObject());
        Object key = co.getPrimaryKeyObject();
        cache.remove(key);
    }

    /**
     * This is for get cache object from cache
     *
     * @param co cache object with primary key object
     * @return retrieved cache object
     */
    private CacheObject get(CacheObject co) {
        logger.debug("Retrieving object from cache " + co.getPrimaryKeyObject());
        Object key = co.getPrimaryKeyObject();
        return cache.get(key);
    }

    /**
     * {@inheritDoc}
     */
    public List<CacheObject> searchFromCache(Field field, String value, Class c) throws CacheException {
        logger.debug("Searching in cache for " + field.getName() + " for " + value);
        List<CacheObject> cacheObjects = new ArrayList<>();
        String sqlWhereClause =field.getName()+"='"+value+"'";
        try {
           List<Cache.Entry<Object, CacheObject>> cursor = cache.query(SqlQuery.sql(c, sqlWhereClause)).getAll();
            for (Cache.Entry<Object, CacheObject> e : cursor) {
                cacheObjects.add(e.getValue());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return cacheObjects;
    }

    /**
     * {@inheritDoc}
     */
    public List<CacheObject> searchFromCache(String fieldName, String value, Class c) throws CacheException {
        logger.debug("Searching in cache for " + fieldName + " for " + value);
        List<CacheObject> cacheObjects = new ArrayList<>();
        String sqlWhereClause =fieldName+"='"+value+"'";
        try {
            List<Cache.Entry<Object, CacheObject>> cursor = cache.query(SqlQuery.sql(c,sqlWhereClause)).getAll();
            for (Cache.Entry<Object, CacheObject> e : cursor) {
                cacheObjects.add(e.getValue());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
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

    /**
     * This is for check whether persister can persist the cache entries. If amIthePersister returns true
     * persister can persist cache entries
     *
     * @return true persist is possible, false otherwise
     */
    private boolean amIthePersister() {
        //remove node config manager and add the machine ip address as the Node ID and time out is to be taken from
        //the spring config file
        String nodeID = "localhost";

        try {
            nodeID = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            logger.error("Error in getting the IP Address - ", e);
        }

        IgniteCache<Object, ActiveNode> controlCache;
        controlCache = manager.jcache("ControlCache");
        ActiveNode h = controlCache.get("ActiveNode");
        //Handle the situation where this is the first persistence attempt where Active node is not selected.
        if (h == null) {
            logger.debug("No Active node in ControlCache");
            logger.debug("This means trying to persist for the first time since startup");
            h = new ActiveNode();
            h.setNodeID(nodeID);
            h.setLastUsedTime(new Date(0)); //set last used time as a old date as no persistence has happened
            controlCache.put("ActiveNode", h);
            logger.debug("Made Node =" + nodeID + " as the active node for persistence");
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
                return true;
            }
        }
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public Cloner getCloner() {
        return cloner;
    }

    public void setCloner(Cloner cloner) {
        this.cloner = cloner;
    }

    public boolean isCacheWriteThrough() {
        return isCacheWriteThrough;
    }

    /**
     * set cache write though (both DB and Cache update at the same transaction
     * or write Behind (cache is updated and later using a timer Db is updated
     *
     * @param cacheWriteThrough writethrough/writebehind
     */
    @Override
    public void setCacheWriteThrough(boolean cacheWriteThrough) {
        isCacheWriteThrough = cacheWriteThrough;
    }

    public IgniteCache<Object, CacheObject> getHistory() {
        return history;
    }

    public void setHistory(IgniteCache<Object, CacheObject> history) {
        this.history = history;
    }

    public IgniteCache<Object, CacheObject> getCache() {
        return cache;
    }

    public void setCache(IgniteCache<Object, CacheObject> cache) {
        this.cache = cache;
    }

    public CachePersister getPersister() {
        return persister;
    }

    public void setPersister(CachePersister persister) {
        this.persister = persister;
    }
}
