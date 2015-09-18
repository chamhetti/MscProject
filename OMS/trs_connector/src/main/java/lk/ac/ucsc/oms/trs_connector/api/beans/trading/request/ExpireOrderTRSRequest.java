package lk.ac.ucsc.oms.trs_connector.api.beans.trading.request;

import lk.ac.ucsc.oms.common_utility.api.enums.ClientChannel;

/**
 * User: Hetti
 * Date: 1/31/13
 * Time: 10:58 AM
 */
public interface ExpireOrderTRSRequest {
    String getClOrderId();

    void setClOrderId(String clOrderId);

    ClientChannel getChannel();

    void setChannel(ClientChannel channel);

    String getClientIp();

    void setClientIp(String clientIp);

    String getUserName();

    void setUserName(String userName);

    String getMsgSessionID();

    void setMsgSessionID(String msgSessionID);

    String getCustomerID();

    void setCustomerID(String customerID);

    String getUserID();

    void setUserID(String userID);

    String getInstrumentType();

    void setInstrumentType(String instrumentType);
}
