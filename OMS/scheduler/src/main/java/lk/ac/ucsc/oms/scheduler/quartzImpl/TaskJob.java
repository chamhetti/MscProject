package lk.ac.ucsc.oms.scheduler.quartzImpl;

import lk.ac.ucsc.oms.common_utility.api.constants.CommonConstants;
import lk.ac.ucsc.oms.scheduler.api.beans.AbstractTaskSchedule;
import org.quartz.Job;
import org.quartz.JobExecutionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.MDC;

import java.util.Date;

/**
 * This is the implementation of org.quartz.Job interface. This class contains the
 * implementation of execute method included int the org.quartz.Job interface.
 * <p/>
 * User: dasun
 * Date: 10/30/12
 * Time: 6:09 PM
 */
public class TaskJob implements Job {

    private static Logger logger = LogManager.getLogger(TaskJob.class);

    /**
     * {@inheritDoc}
     */
    public void execute(org.quartz.JobExecutionContext jobExecutionContext) throws org.quartz.JobExecutionException {
        MDC.put(CommonConstants.MDC_LOGGER_ID, CommonConstants.MDC_LOGGER_CRON_SCHEDULER_START + new Date().getTime() + CommonConstants.MDC_LOGGER_CRON_SCHEDULER_END);
        String description = jobExecutionContext.getJobDetail().getDescription();    // name of the scheduler
        logger.debug("Execute in task job " + description);

        if (description == null || description.isEmpty()) {
            logger.error("Critical Error: description is null or empty");
            throw new JobExecutionException();
        }

        // get the scheduler from static job list available in the scheduler class
        AbstractTaskSchedule st = SchedulerQuartImpl.getJob(description);

        if (st == null) {
            logger.error("Critical Error: Job is not found for the description = " + description);
            throw new JobExecutionException();
        }

        // executing run event wrapper in abstract scheduler
        st.runEventWrapper();
        MDC.remove(CommonConstants.MDC_LOGGER_ID);
    }
}
