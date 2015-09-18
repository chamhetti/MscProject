package lk.ac.ucsc.oms.orderMgt.api.beans;

import lk.ac.ucsc.oms.common_utility.api.enums.OrderExecutionType;
import lk.ac.ucsc.oms.common_utility.api.enums.OrderStatus;

import java.util.Date;


public interface Execution {

    String getExecutionKey();

    void setExecutionKey(String executionKey);

    String getExecutionId();

    void setExecutionId(String executionId);

    String getClOrdID();

    void setClOrdID(String orderId);

    double getLastShare();

    void setLastShare(double lastShare);

    double getAvgPrice();

    void setAvgPrice(double avgCost);

    double getLastPx();

    void setLastPx(double lastPrice);

    String getTransactionTime();

    void setTransactionTime(String transactionTime);

    OrderExecutionType getExecutionType();

    void setExecutionType(OrderExecutionType executionType);

    double getLeaveQty();

    void setLeaveQty(double leaveQty);

    double getCumQty();

    void setCumQty(double cumQty);

    OrderStatus getStatus();

    void setStatus(OrderStatus status);

    String getOrdID();

    void setOrdID(String ordID);

    double getOrdQty();

    void setOrdQty(double ordQty);

    String getOrderNo();

    void setOrderNo(String orderNo);

    String getOrderStatus();

    void setOrderStatus(String orderStatus);

    double getCommission();

    void setCommission(double commission);

    Date getDateTime();

    void setDateTime(Date dateTime);
}
