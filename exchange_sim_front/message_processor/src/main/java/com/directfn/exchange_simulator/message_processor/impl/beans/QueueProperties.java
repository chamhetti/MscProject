package com.directfn.exchange_simulator.message_processor.impl.beans;


public class QueueProperties {
    private String queueName;
    private boolean conStatus;
    private long connectedTime;

    public long getConnectedTime() {
        return connectedTime;
    }

    public void setConnectedTime(long connectedTime) {
        this.connectedTime = connectedTime;
    }

    public boolean isConStatus() {
        return conStatus;
    }

    public void setConStatus(boolean conStatus) {
        this.conStatus = conStatus;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
}
