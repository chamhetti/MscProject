package lk.ac.ucsc.clientConnector.api;

import lk.ac.ucsc.clientConnector.common.api.TrsMessage;

import java.io.Serializable;


public interface Client extends Serializable {
    /**
     * Client Authentication
     *
     * @param request is the authentication request from the client
     * @return Return type is string here because clients that are synchronous (such as http)
     *         need the response returned when this method is invoked.
     *         For other clients (such as socket) the return value is ignored
     */
    String authenticate(String request);

    /**
     * This will process the requests from the different client products
     *
     * @param requestMsg is the request from the client
     */
    String processUserRequest(String requestMsg);

    /**
     * Validates the client session id and set logged in user id
     *
     * @param trsMessage is the message
     * @return the validate status
     */
    boolean validateSessionID(TrsMessage trsMessage);

    /**
     * This will send the logout response to the client
     */
    void sendLogoutResponse(String sessionId, String userId);

    /**
     * Set the message router
     *
     * @param msgRouter is the message router instance
     */
    void setMsgRouter(MessageRouter msgRouter);

    /**
     * This will return the message router
     *
     * @return the message router
     */
    MessageRouter getMsgRouter();

    /**
     * Set the connected client ip address
     *
     * @param clientAddress the ip address of the client
     */
    void setClientAddress(String clientAddress);

    /**
     * Get the client ip address
     *
     * @return the ip address
     */
    String getClientAddress();

    /**
     * Generate session id for the given user
     *
     * @param userID is the user id
     * @return the session id
     */
    String generateSessionID(String userID);

    /**
     * This will write the response to the client
     *
     * @param message is the response to the client
     */
    void writeResponse(TrsMessage message, String messageString);

    /**
     * Check the user login already exist in the cache
     *
     * @param loginAlias is the login alias
     */
    void findAndRemoveOldLoggedSessions(String loginAlias);
}
