package com.directfn.exchange_simulator.message_processor.impl.beans;


public class MessageObject {

    private String clOrdId;
    private String orderAction;
    private double fillQuantity;
    private double fillPrice;
    private double lastShares;

    public String getClOrdId() {
        return clOrdId;
    }

    public void setClOrdId(String clOrdId) {
        this.clOrdId = clOrdId;
    }

    public String getOrderAction() {
        return orderAction;
    }

    public void setOrderAction(String orderAction) {
        this.orderAction = orderAction;
    }

    public double getFillQuantity() {
        return fillQuantity;
    }

    public void setFillQuantity(double fillQuantity) {
        this.fillQuantity = fillQuantity;
    }

    public double getFillPrice() {
        return fillPrice;
    }

    public void setFillPrice(double fillPrice) {
        this.fillPrice = fillPrice;
    }

    public double getLastShares() {
        return lastShares;
    }

    public void setLastShares(double lastShares) {
        this.lastShares = lastShares;
    }
}
