package lk.ac.ucsc.oms.scheduler.api.beans;

import lk.ac.ucsc.oms.cache.api.*;
import lk.ac.ucsc.oms.common_utility.api.constants.CommonConstants;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.scheduler.api.exception.SchedulingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.MDC;

import java.util.Date;
import java.util.concurrent.Callable;

/**
 * This class contains the basic implementation of the scheduler. Scheduler is used to persist the cache entries on a
 * separate thread. Since cache entries are persisted here there is a reference to the cache controller interface.
 * <p/>
 * All the classes that used cache need to have a scheduler and the scheduler needs to extend this class to invoke
 * the the run event of all schedulers. Every scheduler should extend abstract scheduler directly or indirectly.
 * <p/>
 */
public abstract class AbstractTaskSchedule implements Callable<Object> {
    private static boolean stillRunning = false;
    private static Logger logger = LogManager.getLogger(AbstractTaskSchedule.class);
    private CacheControllerInterface cacheController;
    private int counter;
    private int frequency;
    private String lastRun;

    /**
     * Name of the scheduler
     */
    private String schedulerName;

    /**
     * This method used to get the cache controller. This class needs to
     * have a reference of a cache controller since cache entries are persisting
     *
     * @return cache controller
     */
    public CacheControllerInterface getCacheController() {
        return cacheController;
    }


    /**
     * {@inheritDoc}
     */
    public Object call() throws OMSException {
        MDC.put(CommonConstants.MDC_LOGGER_ID, CommonConstants.MDC_LOGGER_SCHEDULER_START + new Date().getTime() + CommonConstants.MDC_LOGGER_SCHEDULER_END);
        runEvent();
        MDC.remove(CommonConstants.MDC_LOGGER_ID);
        return null;
    }

    /**
     * This method used to set cache controller
     *
     * @param cacheController reference
     */
    public void setCacheController(CacheControllerInterface cacheController) {
        this.cacheController = cacheController;
    }

    /**
     * Method containing the methods that need to be executed when the scheduler trigger.
     * This method should implemented in all cache using modules.
     *
     * @return true if persister invoked, false otherwise
     */
    public abstract void runEvent() throws SchedulingException;

    /**
     * This method is used to execute run event implementation of each scheduler.
     * in run event method contains the persisting type of each module.
     */
    public void runEventWrapper() {
        if (!stillRunning) {
            stillRunning = true;       // if not running, make still running true and invoke run event
            try {
                runEvent();
            } catch (SchedulingException e) {
                logger.error("error in scheduling ", e);
            } finally {
                stillRunning = false;
            }

        } else {
            logger.debug("Event triggered when same event is running: Current Time " + new Date() + " Scheduler schedulerName: " + this.getSchedulerName());
        }
    }


    /**
     * Method to set the schedulerName of the scheduler
     *
     * @param schedulerName of the scheduler
     */
    public void setSchedulerName(String schedulerName) {
        this.schedulerName = schedulerName;
    }

    /**
     * Method to get the schedulerName of the scheduler
     *
     * @return schedulerName of the schedular
     */
    public String getSchedulerName() {
        return schedulerName;
    }

    /**
     * Method to set the frequency at which scheduler should trigger
     *
     * @param fre frequency of the scheduler
     */
    public void setFrequency(int fre) {
        this.frequency = fre;
    }

    /**
     * Method to get the triggering frequency
     *
     * @return triggering frequency of the scheduler
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * get counter value
     *
     * @return int value of counter
     */
    public int getCounter() {
        return counter;
    }

    /**
     * set counter value
     *
     * @param counter
     */
    public synchronized void setCounter(int counter) {
        this.counter = counter;
    }

    /**
     * get last executed time of simple task scheduler
     * @return  string time
     */

    public String getLastRun() {
        return lastRun;
    }

    /**
     * Set last running time of simple task scheduler
     * @param lastRun string time
     */
    public void setLastRun(String lastRun) {
        this.lastRun = lastRun;
    }
}
