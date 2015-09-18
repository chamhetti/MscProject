package lk.ac.ucsc.oms.orderMgt.api.beans;

/**
 * this interface contains getters and setters of variable in execution record key bean
 *
 * @author Chathura
 *         Date: 6/2/14
 */
public interface ExecutionRecordKey {
    /**
     * Get the trading day
     *
     * @return the trading day
     */
    String getDay();

    /**
     * Set the trading day
     *
     * @param day is the day
     */
    void setDay(String day);

    /**
     * Get the exchange
     *
     * @return the security exchange
     */
    String getExchange();

    /**
     * Set the exchange
     *
     * @param exchange is the security exchange
     */
    void setExchange(String exchange);

    /**
     * Get the message id
     *
     * @return the message id
     */
    String getMessageID();

    /**
     * Set the message id
     *
     * @param messageID is the message id
     */
    void setMessageID(String messageID);

    /**
     * Get the order side
     *
     * @return the side
     */
    String getSide();

    /**
     * Set the order side
     *
     * @param side is the order side
     */
    void setSide(String side);

    /**
     * Get the execution broker id
     *
     * @return the exec broker id
     */
    String getExecBrokerID();

    /**
     * Set the execution broker id
     *
     * @param execBrokerID is the exec broker id
     */
    void setExecBrokerID(String execBrokerID);
}
