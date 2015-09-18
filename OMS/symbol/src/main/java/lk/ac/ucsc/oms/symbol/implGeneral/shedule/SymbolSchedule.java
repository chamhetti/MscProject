package lk.ac.ucsc.oms.symbol.implGeneral.shedule;

import lk.ac.ucsc.oms.cache.api.CacheControllerInterface;
import lk.ac.ucsc.oms.scheduler.api.beans.SimpleTaskSchedule;
import lk.ac.ucsc.oms.scheduler.api.exception.SchedulingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;

public class SymbolSchedule extends SimpleTaskSchedule {
    private static Logger logger = LogManager.getLogger(SymbolSchedule.class);
    private List<CacheControllerInterface> symbolCaches;
    private static boolean stillRunning = false;

    /**
     * symbol caches consist of 5 caches
     * 1.csSymbolCacheControl
     * 2.forSymbolCacheControl
     * 3.futSymbolCacheControl
     * 4.mfSymbolCacheControl
     * 5.optSymbolCacheControl
     *
     * @param symbolCaches
     */
    public void setSymbolCaches(List<CacheControllerInterface> symbolCaches) {
        this.symbolCaches = symbolCaches;
    }

    /**
     * This is the method which contain the logic that need to be run at the schedule time.
     *
     * @return boolean sucess or not
     */
    @Override
    public void runEvent() throws SchedulingException {
        if (!stillRunning) {
            stillRunning=true;
            try {
                logger.debug("symbol scheduler starts task " + new Date());
                for (CacheControllerInterface cacheControllerInterface : symbolCaches) {
                    if (cacheControllerInterface.getCacheWriteThrough()) {
                        logger.debug("No persist by scheduler since cache is write through");
                    } else {
                        cacheControllerInterface.persistCacheAsBulk();
                    }
                }
                logger.debug("symbol scheduler ends task " + new Date());
            } catch (Exception e) {
                logger.error("Error while persisting symbol cache", e);
                throw new SchedulingException("error in persist cache ", e);
            } finally {
               stillRunning=false;
            }
        }else {
            logger.debug("Symbol scheduler already running...");
        }
    }
}
