import lk.ac.ucsc.oms.common_utility.api.enums.ClientChannel;
import lk.ac.ucsc.oms.common_utility.api.enums.OrderSide;
import lk.ac.ucsc.oms.common_utility.api.enums.OrderStatus;
import lk.ac.ucsc.oms.common_utility.api.enums.OrderType;
import lk.ac.ucsc.oms.orderMgt.api.OrderManagementFactory;
import lk.ac.ucsc.oms.orderMgt.api.OrderManager;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * User: chamindah
 * Date: 4/6/15
 * Time: 11:38 AM
 */
public class OrderTest {

    public  static void main(String [] args){
        createOrder();
    }


    public static void createOrder() {
        try {
            OrderManagementFactory orderManagementFactory =  OrderManagementFactory.getInstance();
            OrderManager ordermanager = orderManagementFactory.getOrderManagementFacadeInterface();
            orderManagementFactory.initialize();
//            OrderBean order = new OrderBean();
            Order order = ordermanager.getEmptyOrder();
            order.setApprovedBy("Nalaka");
            order.setChannel(ClientChannel.WEB);
            order.setClientIp("194.56.75.43");
            order.setClOrdID("1");
            order.setCumCommission(89);
            order.setCustomerNumber("55");
            order.setDayOrder(true);
            order.setExchange("DFM");
            order.setExecBrokerId("asiaSec");
            order.setExpireTime(new Date());
//            order.setExecutions(lst);
            order.setFixConnectionId("54");
            order.setInstitutionCode("mub");
            order.setMarketCode("eod");
            order.setMaxFloor(70);
            order.setMinQty(5);
            order.setOrdID("4");
            order.setOrigClOrdID("1");
            order.setPrice(50);
            order.setQuantity(400);
            order.setRemoteClOrdId("90");
            order.setRemoteOrigOrdId("70");
            order.setSecurityAccount("asiasecmy");
            order.setSecurityType("norm");
            order.setSide(OrderSide.SELL);
            order.setStatus(OrderStatus.DEFAULT);
            order.setSymbol("NL");
            order.setText("buy order");
            order.setTimeInForce(4);
            order.setType(OrderType.MARKET);
            order.setUserName("saman");
            order.setOrderNo("00001");
            order.setTransactionTime("09.55");

            String dtStr = "2008/06/02";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date dt = sdf.parse(dtStr);
            Calendar cal = Calendar.getInstance();
            cal.setTime(dt);

            Date dd = cal.getTime();

            order.setCreateDate(new Date());

            ordermanager.createOrder(order);
//
//            System.out.println("debug order " + order.getClOrdID());
/*
            Order order1 = factory.getEmptyOrder();
//            OrderBean order1 = new OrderBean();
            Order order88 = order.clone();
            order1.setApprovedBy("thakshila");
            order1.setChannel(ClientChannel.ANDROID);
            order1.setClientIp("200.56.75.43");
            order1.setClOrdID("4");
            order1.setCumCommission(9);
            order1.setCustomerNumber("34");
            order1.setDayOrder(false);
            order1.setExchange("SLP");
            order1.setExecBrokerId("aliance");
            order1.setExpireTime(new Date());
//            order1.setExecutions(lst);
            order1.setFixConnectionId("87");
            order1.setInstitutionCode("mubasher");
            order1.setMarketCode("slpst");
            order1.setMaxFloor(65);
            order1.setMinQty(2);
            order1.setOrdID("6");
            order1.setOrigClOrdID("2");
            order1.setPrice(43);
            order1.setQuantity(453);
            order1.setRemoteClOrdId("44");
            order1.setRemoteOrigOrdId("65");
            order1.setSecurityAccount("slpsecmmy");
            order1.setSecurityType("hard");
            order1.setSide(Order.Side.BUY);
            order1.setStatus(Order.Status.AMEND_INITIATED);
            order1.setSymbol("jhy");
            order1.setText("sell jhy");
            order1.setTimeInForce(6);
            order1.setType(OrderType.SQUARE_OFF);
            order1.setUserName("kamal");
            order.setOrderNo("00002");
            order.setTransactionTime("08.34");

            ordermanager.createOrder(order1);*/

//            System.out.println("debug order1 "+order1.getClOrdID());

//            List<Execution> lst = order.getExecutions();
//            for (Execution ex : lst) {
//                ordermanager.addOrderExecution(ex);
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
