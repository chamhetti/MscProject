package lk.ac.ucsc.oms.trs_connector.api.beans.trading.request;

import lk.ac.ucsc.oms.common_utility.api.enums.ClientChannel;

/**
 * Expose the method of the bean which contain the information those needed to do order reverse
 *
 * @author : Hetti
 *         Date: 2/5/13
 *         Time: 12:22 PM
 */
public interface ReverseOrderTRSRequest {
    /**
     * Get the client order id
     *
     * @return the client order id
     */
    String getClOrderId();

    /**
     * set the client order id
     *
     * @param clOrderId is the client order id
     */
    void setClOrderId(String clOrderId);

    /**
     * get the client channel
     *
     * @return the channel
     */
    ClientChannel getChannel();

    /**
     * set the client channel
     *
     * @param channel is the channel
     */
    void setChannel(ClientChannel channel);

    /**
     * get the client ip address
     *
     * @return the ip address
     */
    String getClientIp();

    /**
     * set the client ip address
     *
     * @param clientIp is the ip address
     */
    void setClientIp(String clientIp);

    /**
     * get the user name
     *
     * @return the user name
     */
    String getUserName();

    /**
     * set the user name
     *
     * @param userName is the user name
     */
    void setUserName(String userName);

    /**
     * get the narration of the reverse order
     *
     * @return the narration
     */
    String getNarration();

    /**
     * set the narration for the reverse order
     *
     * @param narration is the narration
     */
    void setNarration(String narration);

    /**
     * get the session id of the message
     *
     * @return the session id
     */
    String getMsgSessionID();

    /**
     * set the session id of the message
     *
     * @param msgSessionID is the session id
     */
    void setMsgSessionID(String msgSessionID);

    /**
     * get the customer id
     *
     * @return the customer id
     */
    String getCustomerID();

    /**
     * set the customer id
     *
     * @param customerID is the customer id
     */
    void setCustomerID(String customerID);

    /**
     * get the user id
     *
     * @return the user id
     */
    String getUserID();

    /**
     * set the user id
     *
     * @param userID is the user id
     */
    void setUserID(String userID);
}
