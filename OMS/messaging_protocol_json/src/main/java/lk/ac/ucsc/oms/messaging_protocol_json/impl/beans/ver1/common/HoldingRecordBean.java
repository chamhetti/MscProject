package lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.common;

import com.google.gson.annotations.SerializedName;

import static lk.ac.ucsc.oms.messaging_protocol_json.api.FieldConstants.*;


public class HoldingRecordBean {

    @SerializedName(SYMBOL)
    private String symbol = null;

    @SerializedName(QUANTITY)
    private int quantity = -1;

    @SerializedName(EXCHANGE)
    private String exchange = null;

    @SerializedName(MUBASHER_ORDER_NUMBER)
    private String orderNumber = null;

    @SerializedName(TRANSACTION_DATE_TIME)
    private String transactionDate = null;

    @SerializedName(TRANSACTION_TYPE)
    private String transactionType = null;

    @SerializedName(AVERAGE_PRICE)
    private double averagePrice = 0;

    @SerializedName(DESCRIPTION)
    private String description = null;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
