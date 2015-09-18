package lk.ac.ucsc.oms.rms_equity.api;


import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import lk.ac.ucsc.oms.rms_equity.api.exceptions.EquityRmsException;


public interface EquityRiskManager {
    /**
     * Do all the risk management related  to New equity order.
     *
     * @param order is the order bean
     * @return the EquityRiskManagementReply
     */
    EquityRiskManagementReply processNewOrder(Order order) throws EquityRmsException;

    /**
     * Used to manage the risk related to Amend equity orders
     *
     * @param order is the order bean
     * @return the EquityRiskManagementReply
     */
    EquityRiskManagementReply validateRiskForAmendOrder(Order order) throws EquityRmsException;

    /**
     * Do all the risk management related to expire equity order
     *
     * @param order is the order bean
     * @return the EquityRiskManagementReply
     */
    EquityRiskManagementReply processExpireOrder(Order order) throws EquityRmsException;

    /**
     * Do all the risk management related to replace equity order
     *
     * @param order is the order bean
     * @return the EquityRiskManagementReply
     */
    EquityRiskManagementReply processReplaceOrder(Order order) throws EquityRmsException;

    /**
     * Do all the risk management related to execute equity order
     *
     * @param order is the order bean
     * @return the EquityRiskManagementReply
     */
    EquityRiskManagementReply processExecuteOrder(Order order) throws EquityRmsException;

}
