package lk.ac.ucsc.oms.customer.implGeneral.schedule;

import lk.ac.ucsc.oms.scheduler.api.beans.SimpleTaskSchedule;
import lk.ac.ucsc.oms.scheduler.api.exception.SchedulingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;


public class AccountSchedule extends SimpleTaskSchedule {
    private static Logger logger = LogManager.getLogger(AccountSchedule.class);
    private static boolean stillRunning = false;

    /**
     * {@inheritDoc}
     */
    @Override
    public void runEvent() throws SchedulingException{
        if (!stillRunning) {
            stillRunning = true;
            try {
                logger.debug("Account scheduler start task " + new Date());
                if (getCacheController().getCacheWriteThrough()) {
                    logger.debug("No persist by scheduler since cache write through");
                } else {
                    getCacheController().persistCacheAsBulk();
                }
                logger.debug("Account scheduler end task " + new Date());
            } catch (Exception e) {
                logger.error("Error in Persisting Account Cache Changes to Physical Media", e);
                throw new SchedulingException("error in persist cache ", e);
            }finally {
                stillRunning=false;
            }
        } else{
          logger.debug("Account scheduler already running...");
        }
    }

}
