package lk.ac.ucsc.oms.rms_equity.implGeneral.facade.helper.util;

import lk.ac.ucsc.oms.customer.api.beans.holding.HoldingRecord;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import org.apache.log4j.Logger;


public class SellRiskManagerUtil {
    private static Logger logger = Logger.getLogger(SellRiskManagerUtil.class);



    public double getAvailableQuantityForSell(double quantity, String exchange, String institutionCode, HoldingRecord holdingRecord, boolean checkStocks, boolean isFullyDisclosed) {
        double availableQty = 0;
        try {
            //check whether tplus settlement is enabled
            boolean isEnableTplusSettlement = false;
            //this checks t plus settlement is enabled for the institute

                //following is the equation to calculate the available quantity to sell
                availableQty = holdingRecord.getNetHolding() - holdingRecord.getPledgeQty()
                        - holdingRecord.getSellPending() - holdingRecord.getNetDayHolding();

        } catch (Exception e) {
            logger.error("Error in Institution :" + e, e);
        }
        return availableQty;
    }


    public boolean isDayHoldingsAvailable(Order order, HoldingRecord holdingRecord) {
        double availableQty;
        if (order.isDayOrder()) {
            // net day holdings and day sell pending is consider when calculating the available quantity
            availableQty = holdingRecord.getNetDayHolding() - holdingRecord.getDaySellPending();
            if (availableQty < order.getQuantity()) {  //if available quantity is less than order quantity validation failed
                return false;
            }
        }
        return true;
    }


    public double getAvailableQtyForDay(HoldingRecord holdingRecord) {
        //available day holding quantity is calculated based on the below equation
        return holdingRecord.getNetDayHolding() - holdingRecord.getDaySellPending();
    }


    public void populateOrderBean(Order order, Order oldOrder) {
        //for non KSE orders need to set the below properties to the order object
        if (!("KSE").equalsIgnoreCase(order.getExchange())) {
            order.setNetSettle(oldOrder.getNetSettle());
            order.setParentNetSettle(oldOrder.getParentNetSettle());
            order.setNetValue(oldOrder.getNetValue());
            order.setCumOrderValue(oldOrder.getCumOrderValue());
            order.setCumCommission(oldOrder.getCumCommission());
            order.setCumExchangeCommission(oldOrder.getCumExchangeCommission());
            order.setCumBrokerCommission(oldOrder.getCumBrokerCommission());
            order.setCumThirdPartyCommission(oldOrder.getCumThirdPartyCommission());
            order.setCumParentCommission(oldOrder.getCumParentCommission());
            if (oldOrder.getCumQty() > 0) {
                order.setCumQty(oldOrder.getCumQty());
            }
        } else {
            logger.info("Order is for KSE");
        }
    }


}
