package lk.ac.ucsc.oms.trs_connector.api.beans.authentication.reply;

/**
 * @author Kalana
 */
public interface RuleBeanDTOInterFace {
    /**
     * Rule ID
     *
     * @return
     */
    String getRuleID();

    /**
     * @param ruleID
     */
    void setRuleID(String ruleID);


    /**
     * Exchanges
     *
     * @return
     */
    String getExchange();

    /**
     * @param exchange
     */
    void setExchange(String exchange);

    /**
     * Condition
     *
     * @return
     */
    String getCondition();

    /**
     * @param condition
     */
    void setCondition(String condition);

    /**
     * Group ID
     *
     * @return
     */
    String getGroupID();

    /**
     * @param groupID
     */
    void setGroupID(String groupID);

    /**
     * Error Msg
     *
     * @return
     */
    String getErrorMsg();

    /**
     * @param errorMsg
     */
    void setErrorMsg(String errorMsg);
}
