package lk.ac.ucsc.oms.scheduler.api.beans;

/**
 * Schedulers need to have a triggering frequency in order to avoid one schedule run forever as well as avoid to
 * idling schedulers. This interface used to set the frequency that scheduled tasks should invoke
 * <p/>
 * User: dasun, Hetti
 * Date: 10/30/12
 * Time: 6:21 PM
 */
public abstract class SimpleTaskSchedule extends AbstractTaskSchedule {

}
