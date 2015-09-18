package lk.ac.ucsc.oms.orderMgt.implGeneral.bean;

import lk.ac.ucsc.oms.orderMgt.api.beans.ExecutionRecordKey;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.ExecutionAuditRecordException;

import java.io.Serializable;

public class ExecutionRecordKeyBean implements Serializable, ExecutionRecordKey {
    private String day;
    private String exchange;
    private String messageID;
    private String side;
    private String execBrokerID;
    private final static int HASH_CODE = 31;


    public ExecutionRecordKeyBean(String day, String exchange, String messageID, String side, String execBrokerID) throws ExecutionAuditRecordException {
        /**
         * Check the nullability
         */

        if (day == null || exchange == null || messageID == null || side == null || execBrokerID == null) {
            throw new ExecutionAuditRecordException("Parameters to execution key can't be null");
        }
        /**
         * Check the empty
         */
        if ("".equals(day) || "".equals(exchange) || "".equals(messageID) || "".equals(side) || "".equals(execBrokerID)) {
            throw new ExecutionAuditRecordException("Parameters to execution key can't be empty");
        }
        this.day = day;
        this.exchange = exchange;
        this.messageID = messageID;
        this.side = side;
        this.execBrokerID = execBrokerID;
    }

    /**
     * default constructor
     */
    protected ExecutionRecordKeyBean() {
    }

    @Override
    public String getDay() {
        return day;
    }

    @Override
    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public String getExchange() {
        return exchange;
    }

    @Override
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    @Override
    public String getMessageID() {
        return messageID;
    }

    @Override
    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    @Override
    public String getSide() {
        return side;
    }

    @Override
    public void setSide(String side) {
        this.side = side;
    }

    @Override
    public String getExecBrokerID() {
        return execBrokerID;
    }

    @Override
    public void setExecBrokerID(String execBrokerID) {
        this.execBrokerID = execBrokerID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExecutionRecordKeyBean that = (ExecutionRecordKeyBean) o;

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
        return result;
    }

    @Override
    public String toString() {
        return "ExecutionRecordKeyBean{" +
                "day='" + day + '\'' +
                ", exchange='" + exchange + '\'' +
                ", messageID='" + messageID + '\'' +
                ", side='" + side + '\'' +
                ", execBrokerID='" + execBrokerID + '\'' +
                '}';
    }
}
