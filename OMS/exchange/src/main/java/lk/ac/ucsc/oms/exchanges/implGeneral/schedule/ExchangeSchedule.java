package lk.ac.ucsc.oms.exchanges.implGeneral.schedule;


import lk.ac.ucsc.oms.scheduler.api.beans.SimpleTaskSchedule;
import lk.ac.ucsc.oms.scheduler.api.exception.SchedulingException;
import lk.ac.ucsc.oms.scheduler.api.exception.SchedulingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;


public class ExchangeSchedule extends SimpleTaskSchedule {
    private static Logger logger = LogManager.getLogger(ExchangeSchedule.class);
    private static boolean stillRunning = false;

    @Override
    public void runEvent() throws SchedulingException {
        if (!stillRunning) {
            stillRunning = true;
            try {
                logger.debug("exchange scheduler starts task " + new Date());
                if (getCacheController().getCacheWriteThrough()) {
                    logger.debug("No persist by scheduler since cache is write through");
                } else {
                    getCacheController().persistCacheAsBulk();
                }
                logger.debug("exchange scheduler ends task " + new Date());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new SchedulingException("error in persist cache ", e);
            } finally {
                stillRunning = false;
            }
        } else {
            logger.debug("Exchange scheduler already running...");
        }
    }

}
