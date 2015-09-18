package lk.ac.ucsc.clientConnector.api;



public interface ClientManager {

    /**
     * Starts the Client Manager and starts listening on given port
     */
    void start();

    /**
     * Stops the Client Manager and stops listening on the port
     */
    void stop();

    /**
     * This will create the client
     *
     * @param session is the connected session
     * @return newly created client
     */
    Client createClient(Object session);

    /**
     * This will set the client request handler
     *
     * @param requestHandler is the Client Request Handler
     */
    void setRequestHandler(Object requestHandler);
}
