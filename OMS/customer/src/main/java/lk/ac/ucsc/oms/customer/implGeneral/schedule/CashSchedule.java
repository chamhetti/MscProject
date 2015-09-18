package lk.ac.ucsc.oms.customer.implGeneral.schedule;

import lk.ac.ucsc.oms.scheduler.api.beans.SimpleTaskSchedule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.SchedulingException;

import java.util.Date;

public class CashSchedule extends SimpleTaskSchedule {
    private static Logger logger = LogManager.getLogger(CashSchedule.class);
    private static boolean stillRunning = false;


    @Override
    public void runEvent() throws SchedulingException {
        if (!stillRunning) {
            stillRunning = true;
            try {
                logger.debug("Cash scheduler starts task " + new Date());
                if (getCacheController().getCacheWriteThrough()) {
                    logger.debug("No persist by scheduler since cache write through");
                } else {
                    getCacheController().persistCacheAsBulk();
                }
                logger.debug("Cash scheduler ends task " + new Date());
            } catch (Exception e) {
                logger.error("Error in Persisting Cash Cache Changes to Physical Media", e);
                throw new SchedulingException("Error in Persisting Cash Cache Changes to Physical Media", e);
            } finally {
                stillRunning = false;
            }
        } else {
            logger.debug("Cash scheduler already running...");
        }
    }

}
