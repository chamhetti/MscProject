package lk.ac.ucsc.clientConnector.session_manager.impl;

import lk.ac.ucsc.oms.cache.api.CacheControllerInterface;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheException;
import lk.ac.ucsc.clientConnector.common.impl.AbstractTrsScheduledTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SessionManagerSchedule extends AbstractTrsScheduledTask {
    private final Logger logger = LoggerFactory.getLogger(SessionManagerSchedule.class);
    private CacheControllerInterface cacheController;

    /**
     * {@inheritDoc}
     */
    @Override
    public void runEvent() {
        try {
            cacheController.persistCache();
        } catch (CacheException e) {
            logger.error("Error persisting session cache", e);
        }
    }

    public void setCacheController(CacheControllerInterface cacheController) {
        this.cacheController = cacheController;
    }

    public CacheControllerInterface getCacheController() {
        return cacheController;
    }

}
