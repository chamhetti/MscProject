package lk.ac.ucsc.oms.customer.implGeneral.schedule;

import lk.ac.ucsc.oms.scheduler.api.beans.SimpleTaskSchedule;
import lk.ac.ucsc.oms.scheduler.api.exception.SchedulingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class CustomerSchedule extends SimpleTaskSchedule {
    private static Logger logger = LogManager.getLogger(CustomerSchedule.class);
    private static boolean stillRunning = false;


    @Override
    public void runEvent() throws SchedulingException {
        if (!stillRunning) {
            stillRunning = true; // if customer scheduler not running currently start scheduler and set stillRunning true
            try {
                logger.debug("customer scheduler starts task " + new Date());
                if (getCacheController().getCacheWriteThrough()) {
                    logger.debug("No persist by scheduler since cache write through");
                } else {
                    getCacheController().persistCacheAsBulk();
                }
                logger.debug("customer scheduler ends task " + new Date());
            } catch (Exception e) {
                logger.error("Error in Persisting Customer Cache Changes to Physical Media", e);
                throw new SchedulingException("error in persist cache ", e);
            }finally {
                stillRunning=false;
            }
        }else {
            logger.debug("customer scheduler already running.... " );
        }
    }

}
