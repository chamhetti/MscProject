package lk.ac.ucsc.clientConnector.api;

import lk.ac.ucsc.clientConnector.common.api.TrsMessage;
import lk.ac.ucsc.clientConnector.exceptions.ClientConnectorException;

import java.util.Map;


public interface Converter {

    String AUTH_STATUS = "authStat";
    String AUTH_USER_ID = "userId";
    String AUTH_LOGIN_ALIAS = "loginAlias";
    String AUTH_ACCOUNT_LIST = "accountList";

    /**
     * Get the TrsMessage object populated with necessary values from the request or response
     *
     * @param request is the request string
     * @return the TrsMessage object needed for routing, session handling, etc
     */
    TrsMessage convertToTrsMessage(String request);

    /**
     * Creates a serialized string (json, mix, etc) from the trs message
     *
     * @param message is the trs message
     * @return serialized string
     * @throws ClientConnectorException
     */
    String convertToString(TrsMessage message) throws ClientConnectorException;

    /**
     * Get Pulse message for the AbstractClient
     *
     * @param sessionID is the user session id
     * @return the pulse response
     */
    String getPulseMessage(String sessionID) throws ClientConnectorException;

    /**
     * Returns request-independent error message response.
     * This is usually sent to the client if TRS cannot determine request type due to reasons
     * such as parsing failure at protocol level
     *
     * @return invalid request message text
     */
    TrsMessage getInvalidRequestMessage(int errorCode, TrsMessage message);

    /**
     * Returns request-dependent error message.
     * This is usually sent to the client if TRS has successfully parsed the request message
     * but TRS decides not to route the message to OMS due to some reason
     *
     * @param message message with error
     * @return protocol dependent error message
     */
    TrsMessage getErrorMessage(TrsMessage message);

    /**
     * Get the log out request message to OMS
     *
     * @param userID    is the connected user id
     * @param sessionID is the session id
     * @return the logout request
     */
    String getLogOutMessage(String userID, String sessionID);

    /**
     * Create the trs message initially when an auth request is sent
     *
     * @param authRequest protocol dependent authentication request
     * @return trs representation of auth request
     */
    TrsMessage createTrsMessageFromAuthRequest(String authRequest);

    /**
     * Get the information required to process authentication response in a map
     *
     * @param response protocol dependent authentication response
     * @return auth information required for TRS
     */
    Map<String, String> processAuthResponseInfo(String response) throws ClientConnectorException;


    /**
     * Returns whether given message is a normal authentication message
     *
     * @param group message group
     * @param type  message type
     * @return is authentication message
     */
    boolean isAuthenticationMessage(int group, int type);

    /**
     * Returns whether given message is a pulse message
     *
     * @param group message group
     * @param type  message type
     * @return is authentication message
     */
    boolean isPulseMessage(int group, int type);


    /**
     * Validate given credentials at TRS.
     * This method is intended to be used in special circumstances only. Eg: comet server
     *
     * @param authRequest authentication request
     * @param username username
     * @param password password
     * @return login success or fail
     */
    boolean validateLogin(String authRequest, String username, String password);


    TrsMessage getVersionNotSupportedAuthErrorMessage(TrsMessage message);

}
