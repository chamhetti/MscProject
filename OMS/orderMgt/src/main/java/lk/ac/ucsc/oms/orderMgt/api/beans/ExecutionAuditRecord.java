package lk.ac.ucsc.oms.orderMgt.api.beans;

/**
 * Interface to contain the execution audit record details of the order execution report
 *
 * @author Chathura
 *         Date: 5/29/14
 */
public interface ExecutionAuditRecord {
    /**
     * Get transaction day
     *
     * @return
     */
    String getDay();

    /**
     * Set transaction day
     *
     * @param day
     */
    void setDay(String day);

    /**
     * Get the security exchange
     *
     * @return
     */
    String getExchange();

    /**
     * Set security exchange
     *
     * @param exchange
     */
    void setExchange(String exchange);

    /**
     * Get the message id
     *
     * @return
     */
    String getMessageID();

    /**
     * Set the message id
     *
     * @param messageID
     */
    void setMessageID(String messageID);

    /**
     * Get the order side
     *
     * @return
     */
    String getSide();

    /**
     * Set the order side
     *
     * @param side
     */
    void setSide(String side);

    /**
     * Get the exec broker id
     *
     * @return
     */
    String getExecBrokerID();

    /**
     * Set the exec broker id
     *
     * @param execBrokerID
     */
    void setExecBrokerID(String execBrokerID);

    /**
     * Get the fix message
     *
     * @return
     */
    String getFixMessage();

    /**
     * Set the fix message
     *
     * @param fixMessage
     */
    void setFixMessage(String fixMessage);

}
