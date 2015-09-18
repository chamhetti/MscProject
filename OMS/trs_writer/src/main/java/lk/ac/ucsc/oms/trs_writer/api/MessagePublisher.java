package lk.ac.ucsc.oms.trs_writer.api;

import lk.ac.ucsc.oms.trs_writer.api.exceptions.TrsWriterException;


public interface MessagePublisher {
    /**
     * Initialize the connection
     *
     * @throws TrsWriterException is the exception thrown while initializing a connection
     */
    void initializeConnection() throws TrsWriterException;

    /**
     * Publishes the given message to the TRS response topic. The response is intended for a single receiver <p/>
     * A JMS text message will be used.
     *
     * @param message is the message to be sent
     * @throws TrsWriterException is the exception that can be thrown when sending a message
     */
    void publishMessage(String message) throws TrsWriterException;

    /**
     * Publishes the message to the TRS response topic specifying the list of intended receivers.
     * TRS responsible for sending responses back to the list of users specified here <p/>
     * A JMS map message will be used.
     *
     * @param message is the message to be sent
     * @param users   set of intended receivers
     * @throws TrsWriterException is the exception that can be thrown when sending a message
     */
    void publishMessage(String message, String[] users) throws TrsWriterException;

}
