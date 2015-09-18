package lk.ac.ucsc.oms.trs_connector.implGeneral.beans.inquiry.reply;

import lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.reply.ExecutionRecord;
import lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.reply.OrderRecord;

import java.util.ArrayList;
import java.util.List;


public class OrderInquiryReply {
    private List<OrderRecord> orderRecords;
    private List<ExecutionRecord> executionRecords;
    private int totalNumberOfRecords;
    private boolean isNextPageAvailable;

    public OrderInquiryReply() {
        orderRecords = new ArrayList<>();
        executionRecords = new ArrayList<>();
    }

    public List<OrderRecord> getOrderRecords() {
        return orderRecords;
    }

    public void setOrderRecords(List<OrderRecord> orderRecords) {
        this.orderRecords = orderRecords;
    }

    public void addOrderRecord(OrderRecord record) {
        this.orderRecords.add(record);
    }

    public List<ExecutionRecord> getExecutionRecords() {
        return executionRecords;
    }

    public void setExecutionRecords(List<ExecutionRecord> executionRecords) {
        this.executionRecords = executionRecords;
    }

    public void addExecutionRecord(ExecutionRecord execution) {
        this.executionRecords.add(execution);
    }

    public int getTotalNumberOfRecords() {
        return totalNumberOfRecords;
    }

    public void setTotalNumberOfRecords(int totalNumberOfRecords) {
        this.totalNumberOfRecords = totalNumberOfRecords;
    }

    public boolean isNextPageAvailable() {
        return isNextPageAvailable;
    }

    public void setNextPageAvailable(boolean nextPageAvailable) {
        isNextPageAvailable = nextPageAvailable;
    }
}
