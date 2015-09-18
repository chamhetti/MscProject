package lk.ac.ucsc.clientConnector.sync_async_mapper.impl;

import lk.ac.ucsc.clientConnector.sync_async_mapper.api.Mapper;
import lk.ac.ucsc.clientConnector.sync_async_mapper.api.exceptions.RequestTimedOutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * {@inheritDoc}
 *
 * @author dasun
 *         Date: 12/26/12
 *         Time: 11:41 AM
 */
public class MapperImpl implements Mapper {
    private static Logger logger = LoggerFactory.getLogger(MapperImpl.class);
    private long uniqueID = 1;
    private long sleepTime;      // maximum duration allowed
    private int maxNoOfRequests;    // maximum allowed concurrent requests
    private Map<Long, Object> requestList = new HashMap<Long, Object>();

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized long getUniqueID() {
        uniqueID++;
        if (uniqueID > maxNoOfRequests) {
            uniqueID = 1; //Here assumed that there cannot be more than pre-configured concurrently processed requests
        }
        return uniqueID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object processRequest(long id) throws RequestTimedOutException {
        Thread thread = getCurrentThread();
        getRequestList().put(id, thread);
        try {
            Thread.sleep(sleepTime); //sleep for 30 seconds
            requestList.remove(id);
            logger.error("Critical error: response for id:{} has not come with in {}  milliseconds", id, sleepTime);
            throw new RequestTimedOutException();
        } catch (InterruptedException e) {
            Object res = getRequestList().get(id);
            getRequestList().remove(id);
            return res;
        }
    }

    /**
     * created for testing
     *
     * @return
     */
    public Map<Long, Object> getRequestList() {
        return requestList;
    }

    /**
     * created for testing
     *
     * @throws InterruptedException
     */
    public void sleepThread() throws InterruptedException {
        Thread.sleep(sleepTime);
    }

    /**
     * created for testing
     *
     * @return
     */
    public Thread getCurrentThread() {
        return Thread.currentThread();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processResponse(long id, Object res) {
        Object t = getRequestList().get(id);
        if (t != null) {
            Thread tt = (Thread) t;
            getRequestList().put(id, res);
            tt.interrupt();
        } else {
            logger.debug("response received for a request not known");
        }
    }

    @Override
    public void setSleepTime(long sleepTime) {
        this.sleepTime = sleepTime;
    }

    @Override
    public synchronized void setMaxNoOfRequests(int maxNoOfRequests) {
        this.maxNoOfRequests = maxNoOfRequests;
    }
}
