package lk.ac.ucsc.oms.scheduler.quartzImpl;


import lk.ac.ucsc.oms.scheduler.api.CronSchedulerName;
import lk.ac.ucsc.oms.scheduler.api.SchedulerInterface;
import lk.ac.ucsc.oms.scheduler.api.beans.AbstractTaskSchedule;
import lk.ac.ucsc.oms.scheduler.api.beans.CronTaskSchedule;
import lk.ac.ucsc.oms.scheduler.api.beans.SimpleTaskSchedule;
import lk.ac.ucsc.oms.scheduler.api.exception.SchedulingException;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * This class contains the implementation of the scheduler interface. Scheduler specific
 * methods are also included in this class. Implementation of scheduling process should
 * include in this class
 * <p/>
 * User: dasun
 * Date: 10/29/12
 * Time: 11:53 PM
 */
public class SchedulerQuartImpl implements SchedulerInterface {
    private org.quartz.Scheduler sched;
    private SchedulerFactory schedulerFactory = new org.quartz.impl.StdSchedulerFactory();

    // map which contains the tasks need to execute
    private static Map<String, AbstractTaskSchedule> joblist = new HashMap();

    private static Logger logger = LogManager.getLogger(SchedulerQuartImpl.class);

    /**
     * {@inheritDoc}
     */
    public synchronized boolean scheduleThisEvent(AbstractTaskSchedule st) throws SchedulingException {
        try {
            // get scheduler instance and starting the schedule
            if (sched == null) {
                sched = schedulerFactory.getScheduler();
                sched.start();
                logger.info("Scheduler started...");
            }
            if (!sched.isStarted()) {
                sched.start(); //This is needed for Test code
            }

            // is scheduler is an instance of a simple task scheduler it contains triggering frequency
            // then scheduling simple task in invoking

            if (st instanceof SimpleTaskSchedule) {
                joblist.put(st.getSchedulerName(), st);  // adding simple task into job list
                logger.debug("Scheduler - simple task added into job list " + st.getSchedulerName());
                scheduleSimpletask((SimpleTaskSchedule) st);
            } else {
                // if scheduler is an instance of a CronTaskScheduler then it contains scheduling logic
                // then scheduling cron task in invoking

                if (st instanceof CronTaskSchedule) {
                    joblist.put(st.getSchedulerName(), st);      // adding cron task into job list
                    logger.debug("Scheduler - cron task added into job list " + st.getSchedulerName());
                    scheduleCronTask((CronTaskSchedule) st);
                } else {
                    logger.error("Undefined type scheduler");
                    throw new SchedulerException("No Such Schedule Task type defined");
                }
            }
        } catch (SchedulerException e) {
            throw new SchedulingException("Error in Scheduling", e);
        }
        return true;
    }

    @Override
    public void pulseEverySecond() {
        logger.info("Unsupported operation");
    }

    @Override
    public boolean isAllSchedulersInitialized() {
        return false;
    }

    @Override
    public CronTaskSchedule getSchedulerBySchedulerName(CronSchedulerName schedulerName) {
        return null;
    }

    @Override
    public Map<String, CronTaskSchedule> getAllCronSchedulerMap() {
        return null;
    }

    @Override
    public Map<String, SimpleTaskSchedule> getAllSimpleTaskSchedulerMap() {
        return null;
    }

    /**
     * Method to get job list. Job list contains the tasks that need to execute
     *
     * @param description name of the scheduler which contains task to execute
     * @return requested scheduler
     */
    public static AbstractTaskSchedule getJob(String description) {
        return joblist.get(description);
    }

    /**
     * Schedulers with specific triggering frequency will be handled by this method
     *
     * @param se scheduler contains the triggering frequency
     * @throws SchedulerException thrown while executing simple scheduler
     */
    private void scheduleSimpletask(SimpleTaskSchedule se) throws SchedulerException {

        logger.info("Simple task scheduler started");

        Trigger trigger = newTrigger()
                .withIdentity(se.getSchedulerName(), se.getSchedulerName())
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(se.getFrequency())
                        .repeatForever())
                .build();

        JobDetail jobDetail = newJob(TaskJob.class)
                .withDescription(se.getSchedulerName())
                .build();
        sched.scheduleJob(jobDetail, trigger);
    }

    /**
     * Schedulers with conditional scheduling logic will be handled by this method
     *
     * @param cse scheduler with conditional logic
     * @throws SchedulerException thrown while execution logical scheduler
     */
    private void scheduleCronTask(CronTaskSchedule cse) throws SchedulerException {

        logger.info("Cron task scheduler started");

        JobDetail jobDetail = newJob(TaskJob.class)
                .withIdentity(cse.getSchedulerName(), cse.getSchedulerName()).withDescription(cse.getSchedulerName())
                .build();

        Trigger trigger = newTrigger()
                .withIdentity(cse.getSchedulerName(), cse.getSchedulerName())
                .withSchedule(cronSchedule(cse.getSchedulingLogic())).build();
        //"0 42 10 * * ?"
        sched.scheduleJob(jobDetail, trigger);
    }

    /**
     * Method to get scheduler
     *
     * @return quartz scheduler
     */
    public synchronized org.quartz.Scheduler getSched() {
        return sched;
    }

    /**
     * Method to set scheduler
     *
     * @param sched quartz scheduler need to return
     */
    public synchronized void setSched(org.quartz.Scheduler sched) {
        this.sched = sched;
    }

    /**
     * Method to get scheduler factory
     *
     * @return scheduler factory
     */
    public SchedulerFactory getSchedulerFactory() {
        return schedulerFactory;
    }

    /**
     * Method to set scheduler factory
     *
     * @param schedulerFactory instance
     */
    public void setSchedulerFactory(SchedulerFactory schedulerFactory) {
        this.schedulerFactory = schedulerFactory;
    }

    /**
     * Method to get static job list map
     *
     * @return job list map
     */
    public static Map<String, AbstractTaskSchedule> getJoblist() {
        return joblist;
    }

    /**
     * Method to set job list
     *
     * @param joblist map
     */
    public static void setJoblist(Map<String, AbstractTaskSchedule> joblist) {
        SchedulerQuartImpl.joblist = joblist;
    }
}
