package lk.ac.ucsc.oms.cache.api;

import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

import java.util.List;



public abstract class AbstractCachePersister implements CachePersister {

    private static final int RETRY_COUNT_THRESHOLD = 3;
    private static Logger errorCacheLog = LogManager.getLogger("failed.cache.objects");
    protected SessionFactory sessionFactory, realTimeSessionFactory, historySessionFactory;
    private Logger logger = LogManager.getLogger(AbstractCachePersister.class);


    public AbstractCachePersister(SessionFactory realTime, SessionFactory history) {
        this.realTimeSessionFactory = realTime;
        this.historySessionFactory = history;
        sessionFactory = realTime;
    }


    public AbstractCachePersister(SessionFactory realTime) {
        this.realTimeSessionFactory = realTime;
        sessionFactory = realTime;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized boolean changeToHistoryMode() {
        sessionFactory = historySessionFactory;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized boolean changeToPresentMode() {
        sessionFactory = realTimeSessionFactory;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertList(List<CacheObject> objectList) {
        return;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateList(List<CacheObject> updateList) {
        return;
    }




    /**
     * Update retry count if it is less than the threshold otherwise make the cache object as non persistable.
     * The non persit cache object will be logged in to seperate log file.
     *
     * @param co
     */
    public void handleFailedCacheObject(CacheObject co) {
        if (co != null) {
            if (co.getRetryCount() > RETRY_COUNT_THRESHOLD - 1) {
                errorCacheLog.warn("{} not persisted - {} \n ", co.getClass().getSimpleName(), new Gson().toJson(co));
                co.setDirty(false);
                co.setNew(false);
            } else {
                co.setRetryCount(co.getRetryCount() + 1);
            }
        } else {
            errorCacheLog.error("Somrthing Wrong here! cache object is null");
        }
    }


}
