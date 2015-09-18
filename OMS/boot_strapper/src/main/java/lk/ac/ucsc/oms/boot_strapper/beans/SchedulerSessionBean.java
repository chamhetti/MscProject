package lk.ac.ucsc.oms.boot_strapper.beans;

import lk.ac.ucsc.oms.scheduler.api.SchedulerFactory;
import lk.ac.ucsc.oms.scheduler.api.SchedulerInterface;
import lk.ac.ucsc.oms.scheduler.api.exception.SchedulingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import java.util.Date;


@Singleton
@DependsOn("SessionStartupBean")
public class SchedulerSessionBean {
    private Logger logger = LogManager.getLogger(SchedulerSessionBean.class);
    private SchedulerInterface schedulerInterface;


    @PostConstruct
    public void initialize() {
        logger.info("SchedulerSessionBean created: ");
        try {
            schedulerInterface = getSchedulerInstance();

        } catch (SchedulingException e) {
            logger.error("Error while getting scheduler interface: ", e);
        }
    }

    public SchedulerInterface getSchedulerInstance() throws SchedulingException {
        return SchedulerFactory.getScheduler();
    }

    @Schedule(second = "1/1", minute = "*", hour = "*")
    public void runSchedulers() {
        logger.debug("EJB scheduler pulse start at : " + new Date());
        try {
            schedulerInterface.pulseEverySecond();
            logger.debug("EJB scheduler pulse end at : " + new Date());
        } catch (Exception e) {
            logger.error("Error in EJB scheduling : ", e);
        }
    }

}
