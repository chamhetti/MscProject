package lk.ac.ucsc.oms.scheduler.api.beans;

/**
 * This is an interface which used to schedule a task to runs base on given scheduling logic.
 * For a example :
 * If it is required to run a particular task ones for a day. Then CronTaskSchedule should be extended and should be
 * implemeted the runEvent method.
 * <p/>
 * User: Hetti
 * Date: 12/1/12
 * Time: 11:36 AM
 */
public abstract class CronTaskSchedule extends AbstractTaskSchedule {

    /**
     * Scheduling logic as a regular expression
     */
    private String schedulingLogic;

    /**
     * last trigger time
     */
    private String lastTriggerTime;
    /**
     * Method to set the scheduling logic
     *
     * @param schedulingLogic as a regular expression
     */
    public void setSchedulingLogic(String schedulingLogic) {
        this.schedulingLogic = schedulingLogic;
    }

    /**
     * Method to get the scheduling logic.
     *
     * @return scheduling logic regular expression
     */
    public String getSchedulingLogic() {
        return schedulingLogic;
    }

    /**
     * Gets lastTriggerTime.
     *
     * @return Value of lastTriggerTime.
     */
    public String getLastTriggerTime() {
        return lastTriggerTime;
    }

    /**
     * Sets new lastTriggerTime.
     *
     * @param lastTriggerTime New value of lastTriggerTime.
     */
    public void setLastTriggerTime(String lastTriggerTime) {
        this.lastTriggerTime = lastTriggerTime;
    }
}
