package lk.ac.ucsc.oms.customer.implGeneral.schedule;

import lk.ac.ucsc.oms.scheduler.api.beans.SimpleTaskSchedule;
import lk.ac.ucsc.oms.scheduler.api.exception.SchedulingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;


public class HoldingLogSchedule extends SimpleTaskSchedule {
    private static Logger logger = LogManager.getLogger(HoldingLogSchedule.class);
    private static boolean stillRunning = false;


    @Override
    public void runEvent() throws SchedulingException {
        if (!stillRunning) {
            stillRunning = true;
            try {
                logger.debug("holding log scheduler start task " + new Date());
                if (getCacheController().getCacheWriteThrough()) {
                    logger.debug("No persist by scheduler since cache write through");
                } else {
                    getCacheController().persistAndClearCacheAsBulk();
                }
                logger.debug("holding log scheduler end task " + new Date());
            } catch (Exception e) {
                logger.error("Error in Persisting Holding Log Cache Changes to Physical Media", e);
                throw new SchedulingException("error in persist cache ", e);
            } finally {
                stillRunning = false;
            }
        } else {
            logger.debug("Holding log scheduler already running...");
        }

    }

}
