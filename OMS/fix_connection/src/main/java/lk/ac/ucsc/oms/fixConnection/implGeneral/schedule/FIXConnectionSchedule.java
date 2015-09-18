package lk.ac.ucsc.oms.fixConnection.implGeneral.schedule;

import lk.ac.ucsc.oms.scheduler.api.beans.SimpleTaskSchedule;
import lk.ac.ucsc.oms.scheduler.api.exception.SchedulingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;


public class FIXConnectionSchedule extends SimpleTaskSchedule {
    private static Logger logger = LogManager.getLogger(FIXConnectionSchedule.class);
    private static boolean stillRunning = false;

    @Override
    public void runEvent() throws SchedulingException {
        if (!stillRunning) {
            stillRunning = true;
            try {
                logger.debug("FIX connection scheduler starts task " + new Date());
                if (getCacheController().getCacheWriteThrough()) {
                    logger.debug("No persist by scheduler since cache is write through");
                } else {
                    getCacheController().persistCacheAsBulk();
                }
                logger.debug("FIX connection scheduler ends task " + new Date());
            } catch (Exception e) {
                logger.error("Error in persisting fix connection beans ", e);
                throw new SchedulingException("error in persist cache ", e);
            } finally {
                stillRunning = false;
            }
        }else {
            logger.debug("FIX connection scheduler already running...");
        }
    }


}
