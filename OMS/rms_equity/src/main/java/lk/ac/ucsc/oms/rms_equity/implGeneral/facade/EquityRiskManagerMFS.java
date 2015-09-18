package lk.ac.ucsc.oms.rms_equity.implGeneral.facade;


import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import lk.ac.ucsc.oms.rms_equity.api.EquityRiskManagementReply;
import lk.ac.ucsc.oms.rms_equity.api.EquityRiskManager;
import lk.ac.ucsc.oms.rms_equity.api.exceptions.EquityRmsException;
import lk.ac.ucsc.oms.rms_equity.implGeneral.facade.helper.BuyRiskManager;
import lk.ac.ucsc.oms.rms_equity.implGeneral.facade.helper.SellRiskManager;


public class EquityRiskManagerMFS implements EquityRiskManager {
    private BuyRiskManager buyRiskManager;
    private SellRiskManager sellRiskManager;


    /**
     * {@inheritDoc}
     */
    @Override
    public EquityRiskManagementReply processNewOrder(Order order) throws EquityRmsException {
        //need to do the risk management for buy and sell orders separately
        switch (order.getSide()) {
            case BUY:
                return buyRiskManager.processNewOrder(order);
            case SELL:
                return sellRiskManager.processNewOrder(order);

            default:
                throw new EquityRmsException("Invalid Order Side");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EquityRiskManagementReply validateRiskForAmendOrder(Order order) throws EquityRmsException {
        //need to do the risk management for buy and sell orders separately
        switch (order.getSide()) {
            case BUY:
                return buyRiskManager.processAmendOrder(order);
            case SELL:
                return sellRiskManager.processAmendOrder(order);

            default:
                throw new EquityRmsException("Invalid Order Side");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EquityRiskManagementReply processExpireOrder(Order order) throws EquityRmsException {
        //need to do the risk management for buy, sell, buy to close and short sell orders separately
        switch (order.getSide()) {
            case BUY:
                return buyRiskManager.processExpireOrder(order);
            case SELL:
                return sellRiskManager.processExpireOrder(order);

            default:
                throw new EquityRmsException("Invalid Order Side");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EquityRiskManagementReply processReplaceOrder(Order order) throws EquityRmsException {
        //need to do the risk management for buy, sell, buy to close and short sell orders separately
        switch (order.getSide()) {
            case BUY:
                return buyRiskManager.processReplaceOrder(order);
            case SELL:
                return sellRiskManager.processReplaceOrder(order);

            default:
                throw new EquityRmsException("Invalid Order Side");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EquityRiskManagementReply processExecuteOrder(Order order) throws EquityRmsException {
        //need to do the risk management for buy,sell, buy to close and short sell orders separately
        switch (order.getSide()) {
            case BUY:
                return buyRiskManager.processExecuteOrder(order);
            case SELL:
                return sellRiskManager.processExecuteOrder(order);

            default:
                throw new EquityRmsException("Invalid Order Side");
        }
    }


    /**
     * Inject the Buy Risk Manager
     *
     * @param buyRiskManager is the Buy Risk Manager
     * @see BuyRiskManager
     */
    public void setBuyRiskManager(BuyRiskManager buyRiskManager) {
        this.buyRiskManager = buyRiskManager;
    }

    /**
     * Inject the Sell Risk Manager
     *
     * @param sellRiskManager is the Sell Risk Manager
     * @see SellRiskManager
     */
    public void setSellRiskManager(SellRiskManager sellRiskManager) {
        this.sellRiskManager = sellRiskManager;
    }

}
