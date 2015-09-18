package lk.ac.ucsc.oms.orderMgt.implGeneral.bean;

import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.orderMgt.api.beans.ExecutionAuditRecord;


public class ExecutionAuditRecordBean extends CacheObject implements ExecutionAuditRecord {
    private String day;
    private String exchange;
    private String messageID;
    private String side;
    private String execBrokerID;
    private String fixMessage = "";
    private final static int HASH_CODE = 31;

    public ExecutionAuditRecordBean() {
    }
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getExecBrokerID() {
        return execBrokerID;
    }

    public void setExecBrokerID(String execBrokerID) {
        this.execBrokerID = execBrokerID;
    }

    public String getFixMessage() {
        return fixMessage;
    }

    public void setFixMessage(String fixMessage) {
        this.fixMessage = fixMessage;
    }

    @Override
    public String toString() {
        return "ExecutionAuditRecordBean{" +
                "day='" + day + '\'' +
                ", exchange='" + exchange + '\'' +
                ", messageID='" + messageID + '\'' +
                ", side='" + side + '\'' +
                ", execBrokerID='" + execBrokerID + '\'' +
                ", fixMessage='" + fixMessage + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExecutionAuditRecordBean that = (ExecutionAuditRecordBean) o;

        if (day != null ? !day.equals(that.day) : that.day != null) {
            return false;
        }
        if (exchange != null ? !exchange.equals(that.exchange) : that.exchange != null) {
            return false;
        }
        if (execBrokerID != null ? !execBrokerID.equals(that.execBrokerID) : that.execBrokerID != null) {
            return false;
        }
        if (messageID != null ? !messageID.equals(that.messageID) : that.messageID != null) {
            return false;
        }
        return !(side != null ? !side.equals(that.side) : that.side != null);

    }

    @Override
    public int hashCode() {
        int result = day != null ? day.hashCode() : 0;
        result = HASH_CODE * result + (exchange != null ? exchange.hashCode() : 0);
        result = HASH_CODE * result + (messageID != null ? messageID.hashCode() : 0);
        result = HASH_CODE * result + (side != null ? side.hashCode() : 0);
        result = HASH_CODE * result + (execBrokerID != null ? execBrokerID.hashCode() : 0);
        result = HASH_CODE * result + (fixMessage != null ? fixMessage.hashCode() : 0);
        return result;
    }
}
