package lk.ac.ucsc.clientConnector.sync_async_mapper.api;

import lk.ac.ucsc.clientConnector.sync_async_mapper.api.exceptions.RequestTimedOutException;

/**
 * This interface specifies operations needed to map
 * asynchronous request ( such as JMS) to a synchronous request ( such as HTTP GET)
 *
 * @author dasun
 *         Date: 12/26/12
 *         Time: 11:37 AM
 */
public interface Mapper {

    /**
     * Returns a unique id specific to a request. The user must first obtain this unique id in order to user Sync/Async mapper
     *
     * @return unique id
     */
    long getUniqueID();

    /**
     * Holds the synchronous request's thread until the request is finished processing asynchronously. When reply comes from front office this resumes
     * If reply doesn;t come with in defined time limit RequestTimedOutException is thrown
     * @param uniqueID unique id for this request obtained by getUniqueID()
     * @return response object specific to the request specified by unique Id
     * @throws RequestTimedOutException is thrown if pre-configured amount of time is exceeded while waiting for the request to be processed asynchronously
     */
    Object processRequest(long uniqueID) throws RequestTimedOutException;

    /**
     * This method should be invoked when asynchronous response is received from front office. This causes the thread which runs processRequest() method to resume
     *
     * @param uniqueID unique id specific to this request
     * @param res      result object
     */
    void processResponse(long uniqueID, Object res);

    /**
     * Sets the mapper timeout
     * @param sleepTime timeout
     */
    void setSleepTime(long sleepTime);

    /**
     * Sets the max number of requests
     * @param maxNoOfRequests max number of requests
     */
    void setMaxNoOfRequests(int maxNoOfRequests);
}
