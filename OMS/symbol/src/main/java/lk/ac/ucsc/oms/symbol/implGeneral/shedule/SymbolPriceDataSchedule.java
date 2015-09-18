package lk.ac.ucsc.oms.symbol.implGeneral.shedule;


import lk.ac.ucsc.oms.scheduler.api.beans.SimpleTaskSchedule;
import lk.ac.ucsc.oms.scheduler.api.exception.SchedulingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;


public class SymbolPriceDataSchedule extends SimpleTaskSchedule {
    private static Logger logger = LogManager.getLogger(SymbolPriceDataSchedule.class);
    private static boolean stillRunning = false;

    @Override
    public void runEvent() throws SchedulingException {
        if (!stillRunning) {
            stillRunning=true;
            try {
                logger.debug("symbol price data scheduler starts task " + new Date());
                if (getCacheController().getCacheWriteThrough()) {
                    logger.debug("No persist by scheduler since cache is write through");
                } else {
                    getCacheController().persistCacheAsBulk();
                }
                logger.debug("symbol price data scheduler ends task " + new Date());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new SchedulingException("error in persist cache ", e);
            } finally {
                stillRunning=false;
            }
        }else {
            logger.debug("Symbol price data scheduler already running...");
        }
    }
}
