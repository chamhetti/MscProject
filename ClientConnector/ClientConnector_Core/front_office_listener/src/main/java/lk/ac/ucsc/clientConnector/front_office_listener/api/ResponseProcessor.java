package lk.ac.ucsc.clientConnector.front_office_listener.api;


public interface ResponseProcessor {
    /**
     * Process the message from the OMS
     *
     * @param message is the mix message received from the OMS
     */
    void processMessage(String message);

    /**
     * Process the message from the OMS
     *
     * @param message is the mix message received from the OMS
     * @param users   is the set of users intended to receive this message
     */
    void processMessage(String message, String[] users);
}
