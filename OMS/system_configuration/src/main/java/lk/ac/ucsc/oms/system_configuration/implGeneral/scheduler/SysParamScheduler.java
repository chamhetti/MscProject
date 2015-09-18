package lk.ac.ucsc.oms.system_configuration.implGeneral.scheduler;

import lk.ac.ucsc.oms.scheduler.api.beans.SimpleTaskSchedule;
import lk.ac.ucsc.oms.scheduler.api.exception.SchedulingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;


public class SysParamScheduler extends SimpleTaskSchedule {
    private static Logger logger = LogManager.getLogger(SysParamScheduler.class);
    private static boolean stillRunning = false;

    /**
     * {@inheritDoc}
     */
    @Override
    public void runEvent() throws SchedulingException {
        logger.info("Scheduler running");
        System.out.println("Scheduler running");
        if (!stillRunning) {
            stillRunning=true;
            try {
                logger.debug("Sysparam scheduler starts task " + new Date());
                if (getCacheController().getCacheWriteThrough()) {
                    logger.debug("No persist by scheduler since cache is write through");
                } else {
                    logger.debug("Processing Bulk Update for Rule Persister");
                    getCacheController().persistCache();
                }
                logger.debug("Sysparam scheduler ends task " + new Date());
            } catch (Exception e) {
                logger.error("error while persist rules", e);
                throw new SchedulingException("error while persist rules",e);
            } finally {
                stillRunning=false;
            }
        }else {
            logger.debug("Sysparam scheduler already running...");
        }
    }
}


