package lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.reply;

/**
 * @author: Shashikak
 */

public interface ExecutionRecord {

    /**
     * get Execution ID of the Execution
     *
     * @return order Execution ID
     */
    String getExecutionId();

    /**
     * set Execution ID for the Execution
     *
     * @param executionId String Execution ID
     */
    void setExecutionId(String executionId);

    /**
     * get Cl Order ID of the Execution
     *
     * @return Client Order ID
     */
    String getClOrdID();

    /**
     * set Cl Order ID for the Execution
     *
     * @param orderId String Client Order ID
     */
    void setClOrdID(String orderId);

    /**
     * get LastShare of the Execution
     *
     * @return LastShare
     */
    double getLastShare();

    /**
     * set LastShare for the Execution
     *
     * @param lastShare double LastShare
     */
    void setLastShare(double lastShare);

    /**
     * get LastPrice of the Execution
     *
     * @return Last Price
     */
    double getLastPx();

    /**
     * set LastPrice for the Execution
     *
     * @param lastPrice double LastPrice
     */
    void setLastPx(double lastPrice);

    /**
     * get Average Price of the Execution
     *
     * @return Average Price
     */
    double getAvgPrice();

    /**
     * aet Average Price of the Execution
     *
     * @param avgPrice
     */
    void setAvgPrice(double avgPrice);

    /**
     * get Transaction Time of the Execution
     *
     * @return TransAction Time
     */
    String getTransactionTime();

    /**
     * set Transaction Time for the Execution
     *
     * @param transactionTime String Transaction Time
     */
    void setTransactionTime(String transactionTime);

    /**
     * get Execution Type of the Execution
     *
     * @return Execution Type
     */
    String getExecutionType();

    /**
     * set Execution Type for the Execution
     *
     * @param executionType Type ExecutionType
     */
    void setExecutionType(String executionType);

    /**
     * get LeaveQty of the Execution
     *
     * @return Leaves Quantity
     */
    double getLeaveQty();

    /**
     * set LeaveQty for the Execution
     *
     * @param leaveQty double LeaveQty
     */
    void setLeaveQty(double leaveQty);

    /**
     * get Cumulative Quantity of the Execution
     *
     * @return Cumilative Quantity
     */
    double getCumQty();

    /**
     * set Cumulative Quantity for the Execution
     *
     * @param cumQty double CumQty
     */
    void setCumQty(double cumQty);

    /**
     * get Order Status from the Execution
     *
     * @return Order Status
     */
    String getStatus();

    /**
     * set Order Status in the Execution
     *
     * @param status String Status of the Execution Order
     */
    void setStatus(String status);

    /**
     * get Order ID from the Execution
     *
     * @return Order ID
     */
    String getOrdID();

    /**
     * set Order ID in the Execution
     *
     * @param ordID String Order ID of the Execution Order
     */
    void setOrdID(String ordID);

    /**
     * get Order Number of the Execution
     *
     * @return orderNo
     */
    String getOrderNo();

    /**
     * set Order Number of the Execution
     *
     * @param orderNo String Order Number of the Execution Order
     */
    void setOrderNo(String orderNo);

    String getSymbol();

    void setSymbol(String symbol);

    String getExchange();

    void setExchange(String exchange);

    String getInstrumentType();

    void setInstrumentType(String instrumentType);

    double getOrderQty();

    void setOrderQty(double orderQty);

    /**
     * get commission
     *
     * @return
     */
    double getCommission();

    /**
     * set commission
     */
    void setCommission(double commission);

    String getDeskOrdRef();

    void setDeskOrdRef(String deskOrdRef);

    String getOrigDeskOrdRef();

    void setOrigDeskOrdRef(String origDeskOrdRef);
}
