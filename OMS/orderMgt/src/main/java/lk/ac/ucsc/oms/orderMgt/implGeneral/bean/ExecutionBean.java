package lk.ac.ucsc.oms.orderMgt.implGeneral.bean;

import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.common_utility.api.enums.OrderExecutionType;
import lk.ac.ucsc.oms.common_utility.api.enums.OrderStatus;
import lk.ac.ucsc.oms.orderMgt.api.beans.Execution;

import java.util.Date;


//@Indexed
public class ExecutionBean extends CacheObject implements Execution {

    private String executionKey;
    private String executionId;
    private String clOrdID;
    private String ordID;
    private double ordQty;
    private double lastShare;
    private double lastPx;
    private double avgPrice;
    private String transactionTime;
    private String executionType;
    private double leaveQty;
    private double cumQty;
    private String status;
    private String orderNo;
    private double commission;
    private Date dateTime;

    /**
     * get execution key of the execution bean
     *
     * @return executionKey
     */
    public String getExecutionKey() {
        return executionKey;
    }

    /**
     * set execution key of the execution bean
     *
     * @param executionKey of the bean
     */
    public void setExecutionKey(String executionKey) {
        this.executionKey = executionKey;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getExecutionId() {
        return executionId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getClOrdID() {
        return clOrdID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setClOrdID(String clOrdID) {
        this.clOrdID = clOrdID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOrdID() {
        return ordID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOrdID(String ordID) {
        this.ordID = ordID;
    }

    /**
     * {@inheritDoc}
     */
    public double getOrdQty() {
        return ordQty;
    }

    /**
     * {@inheritDoc}
     */
    public void setOrdQty(double ordQty) {
        this.ordQty = ordQty;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getLastShare() {
        return lastShare;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLastShare(double lastShare) {
        this.lastShare = lastShare;
    }

    /**
     * {@inheritDoc}
     */
    public double getAvgPrice() {
        return avgPrice;
    }

    /**
     * {@inheritDoc}
     */
    public void setAvgPrice(double avgCost) {
        this.avgPrice = avgCost;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getLastPx() {
        return lastPx;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLastPx(double lastPx) {
        this.lastPx = lastPx;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTransactionTime() {
        return transactionTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderExecutionType getExecutionType() {
        return OrderExecutionType.getEnum(executionType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExecutionType(OrderExecutionType executionType) {
        this.executionType = executionType.getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getLeaveQty() {
        return leaveQty;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLeaveQty(double leaveQty) {
        this.leaveQty = leaveQty;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getCumQty() {
        return cumQty;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCumQty(double cumQty) {
        this.cumQty = cumQty;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrderStatus getStatus() {
        return OrderStatus.getEnum(status);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStatus(OrderStatus status) {
        this.status = status.getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }


    /**
     * get Status of the Order as a String
     *
     * @return orderStatus
     */
    @Override
    public String getOrderStatus() {
        return status;
    }

    /**
     * set Status of the Order as a String
     *
     * @param orderStatus of the execution
     */
    @Override
    public void setOrderStatus(String orderStatus) {
        this.status = orderStatus;
    }


    @Override
    public double getCommission() {
        return commission;
    }

    @Override
    public void setCommission(double commission) {
        this.commission = commission;
    }


    @Override
    public Date getDateTime() {
        return dateTime;
    }

    @Override
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * @param o to check for equality
     * @return true if equal false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExecutionBean that = (ExecutionBean) o;

        if (Double.compare(that.cumQty, cumQty) != 0) {
            return false;
        }
        if (Double.compare(that.lastPx, lastPx) != 0) {
            return false;
        }
        if (Double.compare(that.lastShare, lastShare) != 0) {
            return false;
        }
        if (Double.compare(that.leaveQty, leaveQty) != 0) {
            return false;
        }

        return !(isDifferentString(transactionTime, that.transactionTime) &&
                isDifferentString(status, that.status) &&
                isDifferentString(ordID, that.ordID) &&
                isDifferentString(executionType, that.executionType) &&
                isDifferentString(executionId, that.executionId) &&
                isDifferentString(clOrdID, that.clOrdID));

    }

    /**
     * method to check whether two strings are same or not
     *
     * @param s1 for comparison
     * @param s2 for comparison
     * @return true if strings are equal false otherwise
     */
    private boolean isDifferentString(String s1, String s2) {
        return s1 != null ? !s1.equals(s2) : s2 != null;
    }

    /**
     * to string method of the class
     *
     * @return string representation of the object
     */
    @Override
    public String toString() {
        return "ExecutionBean{" +
                "executionId='" + executionId + '\'' +
                ", clOrdID='" + clOrdID + '\'' +
                ", ordID='" + ordID + '\'' +
                ", lastShare=" + lastShare +
                ", lastPrice=" + lastPx +
                ", transactionTime='" + transactionTime + '\'' +
                ", executionType='" + executionType + '\'' +
                ", leaveQty=" + leaveQty +
                ", cumQty=" + cumQty +
                ", status='" + status + '\'' +
                '}';
    }

    /**
     * method to return the hash code of the class
     *
     * @return hashCode
     */
    @Override
    public int hashCode() {
        return executionId.hashCode();
    }
}
