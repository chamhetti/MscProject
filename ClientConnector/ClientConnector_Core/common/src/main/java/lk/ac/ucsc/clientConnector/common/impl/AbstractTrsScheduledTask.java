package lk.ac.ucsc.clientConnector.common.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;


public abstract class AbstractTrsScheduledTask implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private boolean active;
    private int frequency;
    private String schedulerName;
    private Thread schedulerThread;

    private static int counter;

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setSchedulerName(String schedulerName) {
        this.schedulerName = schedulerName;
    }

    public String getSchedulerName() {
        return schedulerName;
    }

    public abstract void runEvent();

    public void startSchedule() {
        schedulerThread = new Thread(this);
        active = true;
        schedulerThread.start();
    }

    public void stopSchedule() {
        active = false;
    }

    public void run() {

        while (active) {
            try {
                Thread.sleep(frequency * 1000);
                counter++;
                if (counter > 1000000) {
                    counter = 0;
                }
            } catch (InterruptedException e) {
                /**
                 * NO-OP
                 */
            }
            try {
                MDC.put("tid", "tid-sch-"+ counter +": " + schedulerName);
                runEvent();
            } catch (Exception e) {
                logger.error("Task encountered an error", e);
            }
        }
        MDC.remove("tid");
    }

}
