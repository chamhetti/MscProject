package lk.ac.ucsc.oms.orderMgt.implGeneral.schedules;

import lk.ac.ucsc.oms.orderMgt.implGeneral.persistantImpl.hibernate.OrderHibernatePersister;
import lk.ac.ucsc.oms.scheduler.api.beans.SimpleTaskSchedule;
import lk.ac.ucsc.oms.scheduler.api.exception.SchedulingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;


public class OrderSchedule extends SimpleTaskSchedule {
    private static Logger logger = LogManager.getLogger(OrderSchedule.class);
    private static boolean stillRunning = false;
    private OrderHibernatePersister orderHibernatePersister;

    /**
     * {@inheritDoc}
     */
    @Override
    public void runEvent() throws SchedulingException {
        if (!stillRunning) {
            stillRunning = true;
            try {
                logger.debug("order scheduler starts task " + new Date());
                if (getCacheController().getCacheWriteThrough()) {
                    logger.debug("No persist by scheduler since cache is write through");
                } else {
                    logger.debug("Processing Bulk Update of Order Persister");
                    getCacheController().persistCache();
                }
                logger.debug("order scheduler ends task " + new Date());
            } catch (Exception e) {
                logger.error("Error while persist order", e);
                throw new SchedulingException("Error while persist order", e);
            } finally {
                stillRunning = false;
            }
        } else {
            logger.debug("Order scheduler already running...");
        }
    }

    public void setOrderHibernatePersister(OrderHibernatePersister orderHibernatePersister) {
        this.orderHibernatePersister = orderHibernatePersister;
    }
}
