package lk.ac.ucsc.oms.fix.impl.beans;

import java.util.Date;


public class SessionStatusBean extends FixOrderBean {
    private char msgType;
    private String targetCompID;
    private String senderCompID;
    private int tradSesStatus;
    private String tradingSessionID;
    private Date sendingTime;
    private char subscriptionRequestType;

    private static final int HASH_CONST = 31;

    /**
     * {@inheritDoc}
     */
    @Override
    public MessageType getMessageType() {
        return MessageType.getEnum(String.valueOf(msgType));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMessageType(MessageType messageType) {
        this.msgType = messageType.getCode().charAt(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTargetCompID() {
        return targetCompID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTargetCompID(String targetCompID) {
        this.targetCompID = targetCompID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSenderCompID() {
        return senderCompID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSenderCompID(String senderCompID) {
        this.senderCompID = senderCompID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTradSesStatus() {
        return tradSesStatus;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTradSesStatus(int tradSesStatus) {
        this.tradSesStatus = tradSesStatus;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTradingSessionID() {
        return tradingSessionID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTradingSessionID(String tradingSessionID) {
        this.tradingSessionID = tradingSessionID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getSendingTime() {
        return sendingTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSendingTime(Date sendingTime) {
        this.sendingTime = sendingTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char getSubscriptionRequestType() {
        return subscriptionRequestType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSubscriptionRequestType(char subscriptionRequestType) {
        this.subscriptionRequestType = subscriptionRequestType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SessionStatusBean)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        SessionStatusBean that = (SessionStatusBean) o;

        if (msgType != that.msgType) {
            return false;
        }
        if (subscriptionRequestType != that.subscriptionRequestType) {
            return false;
        }
        if (tradSesStatus != that.tradSesStatus) {
            return false;
        }
        if (senderCompID != null ? !senderCompID.equals(that.senderCompID) : that.senderCompID != null) {
            return false;
        }
        if (sendingTime != null ? !sendingTime.equals(that.sendingTime) : that.sendingTime != null) {
            return false;
        }
        if (targetCompID != null ? !targetCompID.equals(that.targetCompID) : that.targetCompID != null) {
            return false;
        }
        return !(tradingSessionID != null ? !tradingSessionID.equals(that.tradingSessionID) : that.tradingSessionID != null);

    }

    @Override
    public int hashCode() {
        int result = (int) msgType;
        result = HASH_CONST * result + (targetCompID != null ? targetCompID.hashCode() : 0);
        result = HASH_CONST * result + (senderCompID != null ? senderCompID.hashCode() : 0);
        result = HASH_CONST * result + tradSesStatus;
        result = HASH_CONST * result + (tradingSessionID != null ? tradingSessionID.hashCode() : 0);
        result = HASH_CONST * result + (sendingTime != null ? sendingTime.hashCode() : 0);
        result = HASH_CONST * result + (int) subscriptionRequestType;
        return result;
    }

    @Override
    public String toString() {
        return "SessionStatusBean{" +
                "msgType=" + msgType +
                ", targetCompID='" + targetCompID + '\'' +
                ", senderCompID='" + senderCompID + '\'' +
                ", tradSesStatus=" + tradSesStatus +
                ", tradingSessionID='" + tradingSessionID + '\'' +
                ", sendingTime=" + sendingTime +
                ", subscriptionRequestType=" + subscriptionRequestType +
                '}';
    }
}
