package lk.ac.ucsc.oms.cash_trading.api;

import lk.ac.ucsc.oms.cash_trading.api.exceptions.CashTradingException;
import lk.ac.ucsc.oms.customer.api.exceptions.AccountManagementException;
import lk.ac.ucsc.oms.customer.api.exceptions.CustomerException;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;


public interface CashTradingManager {

    CashTradingReply validateRiskForNewBuyOrder(Order order) throws CashTradingException;

    CashTradingReply validateRiskForNewSellOrder(Order order) throws CashTradingException;

    CashTradingReply validateRiskForAmendOrder(Order order, double diff, double parentDiff) throws CashTradingException;

    CashTradingReply recordOrderExpiry(Order order) throws CashTradingException;

    CashTradingReply recordOrderReplacement(Order order, Order oldOrder) throws CashTradingException;

    CashTradingReply recordBuyOrderExecution(Order order, double newNetSettle, double newParentNetSettle) throws CashTradingException;

    CashTradingReply recordSellOrderExecution(Order order) throws CashTradingException;


}
