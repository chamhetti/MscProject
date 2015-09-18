package lk.ac.ucsc.oms.scheduler.api;

import lk.ac.ucsc.oms.scheduler.api.beans.AbstractTaskSchedule;
import lk.ac.ucsc.oms.scheduler.api.beans.CronTaskSchedule;
import lk.ac.ucsc.oms.scheduler.api.beans.SimpleTaskSchedule;
import lk.ac.ucsc.oms.scheduler.api.exception.SchedulingException;

import java.util.Map;

/**
 * This is the main service interface of the scheduler module. This interface
 * contains the scheduling event method which is main method of initializing scheduler
 * <p/>
 * User: dasun
 * Date: 10/16/12
 * Time: 2:32 PM
 */
public interface SchedulerInterface {
    /**
     * This method is used to schedule an invent by providing scheduler class reference
     *
     * @param se reference of a scheduler
     * @return true if scheduler started, otherwise thrown scheduler exception
     * @throws SchedulingException
     */
    boolean scheduleThisEvent(AbstractTaskSchedule se) throws SchedulingException;

    /**
     * This method is triggered from MFG_OMS and this is responsible to start schedulers in each module.
     * This need to trigger from out side to work SimpleTaskSchedulers in each modules. Trigger frequency must be 1 seconds.
     * Else this scheduling functionality will not work correct.
     */
    void pulseEverySecond();

    /**
     * This method will call from mfg_oms in the initializing process to make sure that all schedulers initializel
     *
     * @return true if number of initialized schedulers == define schedulers in modules
     */
    boolean isAllSchedulersInitialized();

    /**
     * This method is use to get cron scheduler by name
     *
     * @param schedulerName
     * @return
     */
    CronTaskSchedule getSchedulerBySchedulerName(CronSchedulerName schedulerName);

    /**
     * This method returns all schedulers initialized
     *
     * @return
     */
    Map<String, CronTaskSchedule> getAllCronSchedulerMap();

    /**
     * Method to get all simple task schedulers
     *
     * @return
     */
    Map<String, SimpleTaskSchedule> getAllSimpleTaskSchedulerMap();
}
