package lk.ac.ucsc.oms.orderMgt.implGeneral.schedules;

import lk.ac.ucsc.oms.scheduler.api.beans.SimpleTaskSchedule;
import lk.ac.ucsc.oms.scheduler.api.exception.SchedulingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

public class OrderExecutionSchedule extends SimpleTaskSchedule {
    private static Logger logger = LogManager.getLogger(OrderExecutionSchedule.class);
    private static boolean stillRunning = false;

    /**
     * {@inheritDoc}
     */
    @Override
    public void runEvent() throws SchedulingException {
        if (!stillRunning) {
            stillRunning = true;
            try {
                logger.debug("order execution scheduler starts task " + new Date());
                if (getCacheController().getCacheWriteThrough()) {
                    logger.debug("No persist by scheduler since cache is write through");
                } else {
                    logger.debug("Processing Bulk Update for Order Persister");
                    getCacheController().persistCacheAsBulk();
                }
                logger.debug("order execution scheduler ends task " + new Date());
            } catch (Exception e) {
                logger.error("Error while persist execution order details", e);
                throw new SchedulingException("Error while persist execution order details", e);
            } finally {
                stillRunning = false;
            }
        } else {
            logger.debug("Order execution scheduler already running...");
        }
    }
}
