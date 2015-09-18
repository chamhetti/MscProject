package lk.ac.ucsc.oms.rms_equity.implGeneral.facade.helper.util;

import lk.ac.ucsc.oms.common_utility.api.enums.*;
import lk.ac.ucsc.oms.customer.api.AccountManager;
import lk.ac.ucsc.oms.customer.api.CustomerManager;
import lk.ac.ucsc.oms.customer.api.HoldingManager;
import lk.ac.ucsc.oms.customer.api.beans.account.Account;
import lk.ac.ucsc.oms.customer.api.beans.customer.Customer;
import lk.ac.ucsc.oms.customer.api.beans.holding.*;
import lk.ac.ucsc.oms.customer.api.exceptions.HoldingManagementException;
import lk.ac.ucsc.oms.exchanges.api.ExchangeManager;
import lk.ac.ucsc.oms.orderMgt.api.OrderManager;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.OrderException;
import lk.ac.ucsc.oms.rms_equity.api.exceptions.EquityRmsException;
import lk.ac.ucsc.oms.symbol.api.SymbolManager;
import lk.ac.ucsc.oms.symbol.api.SymbolPriceManager;
import lk.ac.ucsc.oms.symbol.api.beans.BaseSymbol;
import lk.ac.ucsc.oms.symbol.api.beans.commonStok.CSSymbol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.Date;


public class EquityRmsUtil {
    private static double PRICE_RATIO_DEFAULT = 0;
    private static Logger logger = LogManager.getLogger(EquityRmsUtil.class);
     private SymbolManager symbolManager;
    private HoldingManager holdingManager;
     private OrderManager orderManager;
    private AccountManager accountManager;
    private CustomerManager customerManager;


    
    public double getPriceBlock(String institutionCode, ClientChannel clientChannel, OrderType orderType,
                                double orderPrice, String securitySymbol, String securityExchange) throws EquityRmsException {
        double priceBlk = 0;
        //order is limit order or manual order price with price, take order price as price block
        if (orderType == OrderType.LIMIT || (clientChannel == ClientChannel.MANUAL && orderPrice > 0)) {
            priceBlk = orderPrice;
            return priceBlk;
        } else{
            return 2;
        }

    }


    public void updateBuyPending(String customerNumber, String exchange, String symbol, String accountNumber, double quantity,
                                 String instrumentType, String institutionCode) throws EquityRmsException {

        try {
            //creating the holding key
            HoldingKey holdingKey = holdingManager.getEmptyHoldingKey(customerNumber, exchange, symbol, accountNumber);
            HoldingRecord holdingRecord;
            try {
                //check whether holding record is already in DB
                holdingRecord = holdingManager.getHoldingRecordForUpdate(holdingKey);
            } catch (HoldingManagementException e) {
                //when holding record not in the DB
                holdingRecord = null;
            }
            if (holdingRecord == null) {
                //Create a new holding record
                holdingRecord = holdingManager.getEmptyHoldingRecord(holdingKey);
                holdingManager.addHoldingRecord(holdingRecord);
            }
            logger.debug("Buy pending at {} is {}", holdingKey, holdingRecord.getBuyPending());
            //set the buy pending
            holdingRecord.setBuyPending(holdingRecord.getBuyPending() + quantity);
            if (holdingRecord.getBuyPending() < 0) {
                holdingRecord.setBuyPending(0);
            }
            logger.info("New buy pending at {} is {}", holdingKey, holdingRecord.getBuyPending());
            CSSymbol symbolBean = (CSSymbol) symbolManager.getSymbol(symbol, exchange, BaseSymbol.SecurityType.COMMON_STOCK);
            try {
                holdingRecord.setInstrumentType(Integer.parseInt(instrumentType));
            } catch (Exception e) {
                logger.debug("Invalid instrument type", e);
                holdingRecord.setInstrumentType(symbolBean.getInstrumentType());
            }
            holdingRecord.setPriceRatio(symbolBean.getPriceRatio());
            holdingRecord.setInstitutionCode(institutionCode);
            //update the holding record
            holdingManager.updateHoldingRecord(holdingRecord);

        } catch (Exception e) {
            logger.error(e.toString(), e);
            throw new EquityRmsException(e.getMessage(), e);
        }

    }


    public void updateHolding(String customerNumber, String accountNumber, Order order, boolean isOrderReverse, double orderValue,
                              double netSettle, double quantity, boolean isParentAccount, String narration, boolean isFullyDisclosed) throws EquityRmsException {
        try {
            Account account = accountManager.getAccount(accountNumber);
            Customer customer = customerManager.getCustomerByCustomerNumber(account.getCustomerNumber());

            HoldingKey holdingKey = holdingManager.getEmptyHoldingKey(customerNumber, order.getExchange(), order.getSymbol(), accountNumber);

            HoldingRecord holdingRecord;
            try {
                //get the holding record
                holdingRecord = holdingManager.getHoldingRecordForUpdate(holdingKey);
            } catch (HoldingManagementException e) {
                //when holding record not in the DB
                holdingRecord = null;
            }
            //in the execution path when new symbols traded parent holding record is null, so have to create new
            if (holdingRecord == null) {
                //Create a new holding record
                holdingRecord = holdingManager.getEmptyHoldingRecord(holdingKey);
                holdingManager.addHoldingRecord(holdingRecord);
            }

            double totalHolding = holdingRecord.getNetHolding() + quantity;

            //if day order we need to update the net day holding as below
            if (order.isDayOrder() && !isParentAccount) {   //order is a day order and not the parent account
                //calculate the total day holding
                double totalDayHolding = holdingRecord.getNetDayHolding() + quantity;
                //for the buy orders only need to calculate the net day holding average price
                if (order.getSide() == OrderSide.BUY) {
                    double dayHoldingAvgPrice;
                    dayHoldingAvgPrice = ((holdingRecord.getNetDayHolding() * holdingRecord.getDayHoldingAvgPrice()) + orderValue) / totalDayHolding;
                    holdingRecord.setDayHoldingAvgPrice(dayHoldingAvgPrice);
                }
                holdingRecord.setNetDayHolding(totalDayHolding);
            }


            double avgCost;
            double avgPrice;
            if (totalHolding <= 0) {
                avgCost = 0;
                avgPrice = 0;
            } else if (order.getSide() == OrderSide.BUY) {
                double issueRate = order.getIssueSettleRate();
                if (issueRate < 0) {
                    issueRate = 1;
                }
                //calculate the average cost
                if (isOrderReverse) {
                    avgCost = ((holdingRecord.getNetHolding() * holdingRecord.getAvgCost()) - netSettle / issueRate) / totalHolding;
                } else {
                    avgCost = ((holdingRecord.getNetHolding() * holdingRecord.getAvgCost()) + netSettle / issueRate) / totalHolding;
                }
                if (holdingRecord.getAvgPrice() == 0 ) {
                    if(quantity!=0) {
                        avgPrice = orderValue /quantity;
                    } else{
                        avgPrice = 0;
                    }
                } else {
                    avgPrice = ((holdingRecord.getNetHolding() * holdingRecord.getAvgPrice()) + orderValue) / totalHolding;
                }
            } else {
                avgCost = holdingRecord.getAvgCost();
                avgPrice = holdingRecord.getAvgPrice();
            }
            logger.info("Avg cost = {} Avg Price ={}", avgCost,avgPrice);
            holdingRecord.setNetHolding(totalHolding);

            holdingRecord.setAvgCost(avgCost);
            holdingRecord.setAvgPrice(avgPrice);
            //at the order execution time check the holding record has valid price ratio. if not set the price ratio taken from the order price factor
            if (holdingRecord.getPriceRatio() == PRICE_RATIO_DEFAULT) {
                holdingRecord.setPriceRatio(order.getPriceFactor());
            }
            //update the holding record
            holdingManager.updateHoldingRecord(holdingRecord);
            //update the holding log
            HoldingLog holdingLog = holdingManager.getEmptyHoldingLog();
            holdingLog.setAccountNumber(accountNumber);
            holdingLog.setAvgCost(avgCost);
            holdingLog.setClOrderId(order.getOrderNo());
            holdingLog.setCustomerNumber(customerNumber);
            holdingLog.setExchange(order.getExchange());
            holdingLog.setInstitutionId(1);
            holdingLog.setNarration(narration);
            holdingLog.setNetHolding(quantity);
            holdingLog.setTotalHoldings(holdingRecord.getNetHolding());
            holdingLog.setNetSettle(netSettle);
            holdingLog.setOrdCumHolding(order.getCumQty());
            holdingLog.setPrice(order.getLastPx());
            holdingLog.setSide(Integer.parseInt(order.getSide().getCode()));
            holdingLog.setSymbol(order.getSymbol());
            holdingLog.setTransactionTime(new Date());
            holdingLog.setLastShares(order.getLastShares());
            holdingLog.setInstitutionCode(account.getInstituteCode());
            holdingLog.setInstrumentType(order.getInstrumentType());
            holdingLog.setCommissionDifference(order.getCommissionDiff());
            holdingManager.addHoldingLog(holdingLog);

        } catch (Exception e) {
            logger.error(e.toString(), e);
            throw new EquityRmsException(e.getMessage(), e);
        }

    }

    public void updateSellPending(double quantity, Order order, boolean isFullyDisclosed, Account customerAccount) throws EquityRmsException {
        try {
            double totalQty;
            CSSymbol symbolBean;
            boolean isEnableTplusSettlement = false;
            //get the holding key using the order information
            HoldingKey holdingKey = holdingManager.getEmptyHoldingKey(customerAccount.getCustomerNumber(), order.getExchange(), order.getSymbol(), customerAccount.getAccountNumber());
            //get the holding record
            HoldingRecord holdingRecord;
            try {
                holdingRecord = holdingManager.getHoldingRecordForUpdate(holdingKey);
            } catch (Exception e) {
                holdingRecord = holdingManager.getEmptyHoldingRecord(holdingKey);
                holdingManager.addHoldingRecord(holdingRecord);
            }



            symbolBean = (CSSymbol) symbolManager.getSymbol(order.getSymbol(), order.getExchange(), BaseSymbol.SecurityType.COMMON_STOCK);

            if (holdingRecord != null) {
                if (order.isDayOrder()) {   //for day order need to update the day sell pending
                    holdingRecord.setDaySellPending(holdingRecord.getDaySellPending() + quantity);
                    //set the order sync parameters that need to sync to the back office
                    if (holdingRecord.getDaySellPending() < 0) {
                        holdingRecord.setDaySellPending(0);
                    }
                } else {

                        totalQty = holdingRecord.getSellPending() + quantity;
                        if (totalQty < 0) {
                            totalQty = 0;
                        }
                        holdingRecord.setSellPending(totalQty);

                }

                try {
                    holdingRecord.setInstrumentType(Integer.parseInt(order.getInstrumentType()));
                } catch (Exception e) {
                    logger.debug("Invalid instrument type", e);
                    holdingRecord.setInstrumentType(symbolBean.getInstrumentType());
                }
                holdingRecord.setPriceRatio(symbolBean.getPriceRatio());
                //finally update the holding record
                holdingManager.updateHoldingRecord(holdingRecord);
            }

        } catch (Exception e) {
            logger.error("Error Occurs During Sell Pending Update Process: ", e);
            throw new EquityRmsException("Sell pending update issue", e);
        }
    }


    public boolean updateAmendOrderDetails(Order origOrder) {
        logger.info("Original order got execute during the amend");
        Order amendOrder;
        String replacedOrderID = origOrder.getReplacedOrderId();
        try {
            //get the amended order using the replace order id taken from the original order
            amendOrder = orderManager.getOrderByClOrderId(replacedOrderID);
        } catch (Exception e) {
            logger.error("Error in finding the amended order, Order ID: " + replacedOrderID);
            return false;
        }

        double adjustedMarginBlock;
        double adjustedCashBlock;
        double adjustedParentBlock;
        //adjust the cash block for amend order using the below equation
        //temp block amount is taken from the current order execution
        logger.info("AmendOrder.getBlockAmount() -{}  origOrder.getBlockAmount()-{} origOrder.getTempBlockAmount()-{}",
                amendOrder.getBlockAmount(), origOrder.getBlockAmount(), origOrder.getTempBlockAmount());
        adjustedCashBlock = (origOrder.getBlockAmount() - origOrder.getTempBlockAmount());
        logger.info("adjustedCashBlock -{}", adjustedCashBlock);
        //adjust the parent cash block for amend order using the below equation
        adjustedParentBlock = amendOrder.getParentBlockAmount() + (origOrder.getParentBlockAmount() - origOrder.getTempParentBlockAmount());
        //adjust the margin block for amend order using the below equation
        logger.info("amendOrder.getMarginBlock() - {} origOrder.getMarginBlock() -{} origOrder.getTempMarginBlockAmt() -" +
                "{}", amendOrder.getMarginBlock(), origOrder.getMarginBlock(), origOrder.getTempMarginBlockAmt());
        adjustedMarginBlock = origOrder.getMarginBlock() - origOrder.getTempMarginBlockAmt();
        logger.info("adjustedMarginBlock -{}", adjustedMarginBlock);
        double amendOrderOriginalBlk = amendOrder.getBlockAmount() + amendOrder.getMarginBlock();
        double totalBlkRemoveByExecution = adjustedCashBlock + adjustedMarginBlock;
        logger.info("amendOrderOriginalBlk -{} totalBlkRemoveByExecution -{}", amendOrderOriginalBlk, totalBlkRemoveByExecution);
        double amendOrderNewBlk = amendOrderOriginalBlk - Math.abs(totalBlkRemoveByExecution);

        logger.info("amendOrderNewBlk -{}", amendOrderNewBlk);
        amendOrder.setBlockAmount(amendOrderNewBlk);
        amendOrder.setMarginBlock(0);
        amendOrder.setParentBlockAmount(adjustedParentBlock);
        amendOrder.setCumNetValue(origOrder.getCumNetValue());
        amendOrder.setCumQty(origOrder.getCumQty());
        amendOrder.setCumCommission(origOrder.getCumCommission());
        amendOrder.setCumNetSettle(origOrder.getCumNetSettle());
        amendOrder.setCumOrderValue(origOrder.getCumOrderValue());
        amendOrder.setCumParentCommission(origOrder.getCumParentCommission());
        amendOrder.setCumExchangeCommission(origOrder.getCumExchangeCommission());
        amendOrder.setCumThirdPartyCommission(origOrder.getCumThirdPartyCommission());
        amendOrder.setCumBrokerCommission(origOrder.getCumBrokerCommission());
        amendOrder.setAvgPrice(origOrder.getAvgPrice());
        try {
            //update the amend order in the cache
            orderManager.updateOrder(amendOrder);
        } catch (OrderException e) {
            logger.error("Error in Updating the order at the cache: " + amendOrder.getClOrdID(), e);
        }
        return true;
    }

    public void setOrderManager(OrderManager orderManager) {
        this.orderManager = orderManager;
    }


    public void setHoldingManager(HoldingManager holdingManager) {
        this.holdingManager = holdingManager;
    }


    public void setSymbolManager(SymbolManager symbolManager) {
        this.symbolManager = symbolManager;
    }

    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public void setCustomerManager(CustomerManager customerManager) {
        this.customerManager = customerManager;
    }


}
