package lk.ac.ucsc.oms.trs_connector.implGeneral.beans.trading.request;

import lk.ac.ucsc.oms.common_utility.api.enums.ClientChannel;
import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.ExpireOrderTRSRequest;


public class ExpireOrderTRSRequestBean implements ExpireOrderTRSRequest {
    private String clOrderId;
    private ClientChannel channel;
    private String clientIp;
    private String userName;
    private String sessionID;
    private String customerId;
    private String userId;
    private String instrumentType;

    @Override
    public String getClOrderId() {
        return clOrderId;
    }

    @Override
    public void setClOrderId(String clOrderId) {
        this.clOrderId = clOrderId;
    }

    @Override
    public ClientChannel getChannel() {
        return channel;
    }

    @Override
    public void setChannel(ClientChannel channel) {
        this.channel = channel;
    }

    @Override
    public String getClientIp() {
        return clientIp;
    }

    @Override
    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getMsgSessionID() {
        return sessionID;
    }

    @Override
    public void setMsgSessionID(String msgSessionID) {
        this.sessionID = msgSessionID;
    }

    @Override
    public String getCustomerID() {
        return customerId;
    }

    @Override
    public void setCustomerID(String customerID) {
        this.customerId = customerID;
    }

    @Override
    public String getUserID() {
        return userId;
    }

    @Override
    public void setUserID(String userID) {
        this.userId = userID;
    }

    @Override
    public String getInstrumentType() {
        return instrumentType;
    }

    @Override
    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }
}
