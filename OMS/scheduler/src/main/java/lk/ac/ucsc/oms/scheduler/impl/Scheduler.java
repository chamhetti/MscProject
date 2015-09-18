package lk.ac.ucsc.oms.scheduler.impl;

import lk.ac.ucsc.oms.scheduler.api.CronSchedulerName;
import lk.ac.ucsc.oms.scheduler.api.SchedulerInterface;
import lk.ac.ucsc.oms.scheduler.api.beans.AbstractTaskSchedule;
import lk.ac.ucsc.oms.scheduler.api.beans.CronTaskSchedule;
import lk.ac.ucsc.oms.scheduler.api.beans.SimpleTaskSchedule;
import lk.ac.ucsc.oms.scheduler.api.exception.SchedulingException;
import lk.ac.ucsc.oms.scheduler.quartzImpl.TaskJob;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * User: Ruchira Ranaweera
 * Date: 11/18/13
 */
public class Scheduler implements SchedulerInterface {
    private static Logger logger = LogManager.getLogger(Scheduler.class);
    private static Map<String, AbstractTaskSchedule> scheduleEventMap = new HashMap();
    private static Map<String, AbstractTaskSchedule> quartzScheduleEventMap = new HashMap();
    private static ExecutorService executorService;
    private static Map<String, ExecutorService> threadPoolMap = new HashMap<>();
    private org.quartz.Scheduler quartzScheduler;
    private SchedulerFactory schedulerFactory = new org.quartz.impl.StdSchedulerFactory();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean scheduleThisEvent(AbstractTaskSchedule se) throws SchedulingException {
        if (se instanceof SimpleTaskSchedule) {   // SimpleTaskSchedules triggered by mfg_oms
            logger.info("Initializing >>>> simple task scheduler start " + se.getSchedulerName());
            scheduleEventMap.put(se.getSchedulerName(), se);
            executorService = Executors.newFixedThreadPool(1);
            threadPoolMap.put(se.getSchedulerName(), executorService);
            logger.info("Initializing >>>> simple task scheduler end " + se.getSchedulerName());
            return true;
        } else if (se instanceof CronTaskSchedule) { // CronTaskSchedulers triggered by quartz
            quartzScheduleEventMap.put(se.getSchedulerName(), se);
            try {
                if (quartzScheduler == null) {
                    quartzScheduler = schedulerFactory.getScheduler();
                    quartzScheduler.start();
                    logger.info("Scheduler started...");
                }
                if (!quartzScheduler.isStarted()) {
                    quartzScheduler.start();
                }
                scheduleCronTask((CronTaskSchedule) se);
                return true;
            } catch (SchedulerException e) {
                logger.error("Cron task scheduling error", e);
                return false;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pulseEverySecond() {
        for (AbstractTaskSchedule abstractTaskSchedule : scheduleEventMap.values()) {
//            MDC.put(CommonConstants.MDC_LOGGER_ID, CommonConstants.MDC_LOGGER_SCHEDULER_START + new Date().getTime() + CommonConstants.MDC_LOGGER_SCHEDULER_END);
            if ((abstractTaskSchedule.getFrequency() != 0) && (abstractTaskSchedule.getCounter() >= abstractTaskSchedule.getFrequency())) {
                try {
                    // taking particular Thread pool by scheduler name and put scheduler to Thread pool
                    logger.debug("Name " + abstractTaskSchedule.getSchedulerName() + " Frequency " + abstractTaskSchedule.getFrequency() + " Counter " + abstractTaskSchedule.getCounter());
                    abstractTaskSchedule.setCounter(0);
                    abstractTaskSchedule.setLastRun(new Date().toString());
                    threadPoolMap.get(abstractTaskSchedule.getSchedulerName()).execute(new FutureTask<Object>(abstractTaskSchedule));
                    logger.debug(abstractTaskSchedule.getSchedulerName() + " is Triggered");

                } catch (Exception e) {
                    logger.error("Error in schedule" + abstractTaskSchedule.getSchedulerName());
                }
            } else {
                abstractTaskSchedule.setCounter(abstractTaskSchedule.getCounter() + 1);
            }
//            MDC.remove(CommonConstants.MDC_LOGGER_ID);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAllSchedulersInitialized() {
        if (threadPoolMap.size() == 49) {
            return true;
        } else {
            logger.error("Schedulers are not fully initialized : only " + threadPoolMap.size() + " are initialized " + threadPoolMap);
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CronTaskSchedule getSchedulerBySchedulerName(CronSchedulerName schedulerName) {
        Map<String, CronTaskSchedule> cronTaskScheduleMap = new HashMap<>();
        for (Map.Entry<String, AbstractTaskSchedule> entry : quartzScheduleEventMap.entrySet()) {
            cronTaskScheduleMap.put(entry.getKey(), (CronTaskSchedule) entry.getValue());
        }
        return cronTaskScheduleMap.get(schedulerName.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, CronTaskSchedule> getAllCronSchedulerMap() {
        Map<String, CronTaskSchedule> cronTaskScheduleMap = new HashMap<>();
        for (Map.Entry<String, AbstractTaskSchedule> entry : quartzScheduleEventMap.entrySet()) {
            cronTaskScheduleMap.put(entry.getKey(), (CronTaskSchedule) entry.getValue());
        }
        return cronTaskScheduleMap;
    }

    @Override
    public Map<String, SimpleTaskSchedule> getAllSimpleTaskSchedulerMap() {
        Map<String, SimpleTaskSchedule> simpleTaskScheduleMap = new HashMap<>();
        for (Map.Entry<String, AbstractTaskSchedule> entry : scheduleEventMap.entrySet()) {
            simpleTaskScheduleMap.put(entry.getKey(), (SimpleTaskSchedule) entry.getValue());
        }
        return simpleTaskScheduleMap;
    }

    /**
     * Schedulers with conditional scheduling logic will be handled by this method
     *
     * @param cse scheduler with conditional logic
     * @throws SchedulerException thrown while execution logical scheduler
     */
    private void scheduleCronTask(CronTaskSchedule cse) throws SchedulerException {

        logger.info("Initializing >>>> Cron task scheduler started " + cse.getSchedulerName());

        JobDetail jobDetail = newJob(TaskJob.class)
                .withIdentity(cse.getSchedulerName(), cse.getSchedulerName()).withDescription(cse.getSchedulerName())
                .build();

        Trigger trigger = newTrigger()
                .withIdentity(cse.getSchedulerName(), cse.getSchedulerName())
                .withSchedule(cronSchedule(cse.getSchedulingLogic())).build();
        //"0 42 10 * * ?"
        quartzScheduler.scheduleJob(jobDetail, trigger);

        logger.info("Initializing >>>> Cron task scheduler end " + cse.getSchedulerName());
    }

    /**
     * Method to get job list. Job list contains the tasks that need to execute
     *
     * @param description name of the scheduler which contains task to execute
     * @return requested scheduler
     */
    public static AbstractTaskSchedule getJob(String description) {
        return quartzScheduleEventMap.get(description);
    }

    /**
     * set the scheduler Factory
     * Usage in unit testing only.
     *
     * @param schedulerFactory
     */
    public void setSchedulerFactory(SchedulerFactory schedulerFactory) {
        this.schedulerFactory = schedulerFactory;
    }

    /**
     * set the scheduler Map
     * Usage in unit testing only.
     *
     * @param scheduleEventMap
     */
    public void setScheduleEventMap(Map<String, AbstractTaskSchedule> scheduleEventMap) {
        Scheduler.scheduleEventMap = scheduleEventMap;
    }

    /**
     * get the ScheduleEvent Map
     * Usage in unit testing only.
     */
    public Map<String, AbstractTaskSchedule> getScheduleEventMap() {
        return scheduleEventMap;
    }

    /**
     * set the ThreadPool Map
     * Usage in unit testing only.
     *
     * @param threadPoolMap
     */
    public void setThreadPoolMap(Map<String, ExecutorService> threadPoolMap) {
        Scheduler.threadPoolMap = threadPoolMap;
    }

    /**
     * get the ThreadPool Map
     * Usage in unit testing only.
     */
    public Map<String, ExecutorService> getThreadPoolMap() {
        return threadPoolMap;
    }

    /**
     * set the QuartzScheduleEvent Map
     * Usage in unit testing only.
     *
     * @param quartzScheduleEventMap
     */
    public static void setQuartzScheduleEventMap(Map<String, AbstractTaskSchedule> quartzScheduleEventMap) {
        Scheduler.quartzScheduleEventMap = quartzScheduleEventMap;
    }
}
