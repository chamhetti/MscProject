package lk.ac.ucsc.oms.trade_controller.normalOrders;


import lk.ac.ucsc.oms.common_utility.api.enums.*;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.customer.api.AccountManager;
import lk.ac.ucsc.oms.customer.api.beans.account.Account;

import lk.ac.ucsc.oms.exchanges.api.ExchangeManager;
import lk.ac.ucsc.oms.fix.api.beans.FixOrderInterface;

import lk.ac.ucsc.oms.fix.impl.util.FIXConstants;

import lk.ac.ucsc.oms.orderMgt.api.*;

import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.OrderException;
import lk.ac.ucsc.oms.stp_connector.api.STPConnectorFactory;
import lk.ac.ucsc.oms.system_configuration.api.SysLevelParaManager;

import lk.ac.ucsc.oms.trs_connector.api.NormalOrderControllerInterface;

import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.*;

import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.normal.AmendOrderTRSRequest;
import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.normal.CancelOrderTRSRequest;
import lk.ac.ucsc.oms.trs_connector.api.beans.trading.request.normal.NewOrderTRSRequest;

import lk.ac.ucsc.oms.trade_controller.helper.*;
import lk.ac.ucsc.oms.trade_controller.normalOrders.helper.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static lk.ac.ucsc.oms.common_utility.api.GlobalLock.lock;


public class NormalOrderController implements NormalOrderControllerInterface {
    private static final String NEW_ORDER_ACTION = "NEW";
    private static final String CHANGE_ORDER_ACTION = "CHANGE";
    private static final String CANCEL_ORDER_ACTION = "CANCEL";
    private static final String REVERSE_ORDER_ACTION = "REVERSE_EXECUTED";
    private static final String EXPIRED_ORDER_ACTION = "EXPIRED";
    private static final String APPROVE_ACTION = "APPROVED";
    private static final String EXECUTION_REPORT = "EXECUTION_REPORT";
    private static final String ORDER_STATUS_CHANGE = "ORDER_STATUS_CHANGE";
    private static final String PROBLEM_IN_GETTING_NEW_ORDER = "Problem in getting empty order";
    private static Logger logger = LogManager.getLogger(NormalOrderController.class);
    private static ApplicationContext ctx;
    private OrderPropertyProcessorMFS propertyProcessor;
    private OrderValidator orderValidator;
    private OrderManagementFactory orderFacadeFactory;
    private OrderCacheUpdater orderCacheUpdater;

    private ExchangeManager exchangeManager;
    private STPConnectorFactory stpConnectorFactory;
    private STPConnectorHelper stpConnectorHelper;
    private AccountManager accountManager;
    private SysLevelParaManager sysLevelParaManager;
    @EJB
    private NormalOrderControllerBean normalOrderControllerBean;


    public NormalOrderController() {
        synchronized (this) {
            if (ctx == null) {
                ctx = new ClassPathXmlApplicationContext("/spring-config-trading_controller.xml");
            }
        }

        propertyProcessor = new OrderPropertyProcessorMFS(ctx);
        orderValidator = new OrderValidatorMFS(ctx);
        orderFacadeFactory = ctx.getBean(ModuleDINames.ORDER_FACTORY_DI_NAME, OrderManagementFactory.class);
        orderCacheUpdater = new OrderCacheUpdater(ctx);
        exchangeManager = ctx.getBean(ModuleDINames.EXCHANGE_MANAGER_DI_NAME, ExchangeManager.class);
        stpConnectorFactory = ctx.getBean(ModuleDINames.STP_CONNECTOR_FACTORY_DI_NAME, STPConnectorFactory.class);
        stpConnectorHelper = new STPConnectorHelper(ctx);
        accountManager = ctx.getBean(ModuleDINames.ACCOUNT_MANAGER_DI_NAME, AccountManager.class);
        sysLevelParaManager = ctx.getBean(ModuleDINames.SYS_PARA_MANAGER_DI_NAME, SysLevelParaManager.class);
        InitialContext theContext;
        try {
            theContext = new InitialContext();
            normalOrderControllerBean = (NormalOrderControllerBean) theContext.lookup(ModuleDINames.NORMAL_ORDER_SESSION_BEAN_JNDI);
        } catch (NamingException e) {
            logger.error("Issue in loading normal order transaction Control bean - {}", e);
        }

    }

    /**
     * Use this constructor only for unit testing
     *
     * @param stpConnectorFactory
     */
    public NormalOrderController(STPConnectorFactory stpConnectorFactory) {

    }

    public static ApplicationContext getCtx() {
        return ctx;
    }

    public static void setCtx(ApplicationContext ctx) {
        NormalOrderController.ctx = ctx;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Order processNewOrderFromTrs(NewOrderTRSRequest newOrderRequest) {

        if (newOrderRequest.getChannel() != ClientChannel.DT) {
            String customerNumber = newOrderRequest.getUserID();
            List<Account> accountList;
            try {
                accountList = accountManager.getAllAccountsOfCustomer(customerNumber);
                boolean validAccount = false;
                for (Account account : accountList) {
                    if (account.getAccountNumber().equals(newOrderRequest.getAccountNumber())) {
                        validAccount = true;
                        break;
                    }
                }
                if (!validAccount) {
                    throw new OMSException("Security validation failed");
                }
            } catch (Exception e) {
                logger.error("Invalid new Order request", e);
                return null;
            }
        }

        return processNewOrder(newOrderRequest);
    }

    /**
     * This is the method which contain the logic for validating the order request sent by client, call risk management module to do risk management and
     *
     * @param newOrderRequest new order request from client
     * @return created order by oms
     */
    @Override
    public Order processNewOrder(NewOrderTRSRequest newOrderRequest) {
        logger.info("New Order Waiting to obtain the lock - {}", newOrderRequest);
        long startTime = System.currentTimeMillis();
        synchronized (lock) {
            logger.info("WAIT TIME FOR LOCK - {}", (System.currentTimeMillis() - startTime));
            logger.info("Lock obtained by New Order Request- {}", newOrderRequest);
            //check the validity of the request
            if (newOrderRequest == null) {
                logger.error("Invalid new Order Request >> Order Request is Null");
                return null;
            }

            //Fill the order property from the request
            Order order = getOrderPropertyPopulator().fillOrderFromNewOrderRequest(newOrderRequest, orderFacadeFactory);
            if (order == null) {
                logger.error(PROBLEM_IN_GETTING_NEW_ORDER);
                return null;
            }

            //add the order to the order cache before further processing. This is happen out side the transaction hence order
            //get saved regardless of the future operation success or not.
            try {
                orderCacheUpdater.addToOrderCache(order);
            } catch (Exception e) {
                logger.warn("Invalid new Order request");
                order.setText("Invalid New Order Request::" + e.getMessage());
                order.setInternalRejReason("Invalid New Order Request::" + e.getMessage());
                order.setStatus(OrderStatus.REJECTED);
                pushOrderResponse(order);
                //sync with back office
                return order;
            }


            return processNewOrTriggeredOrder(order);
        }
    }

    private Order processNewOrTriggeredOrder(Order order) {
        long startTime = System.currentTimeMillis();
        try {
            //Do all risk management in a session bean to manage transaction using CMT.
            normalOrderControllerBean.processNewOrder(order);
        } catch (Exception e) {
            order.setStatus(OrderStatus.REJECTED);
            order.setText("System Error - " + e.getMessage());
            order.setInternalRejReason("System Error - " + e.getMessage());
            try {
                //update the order
                orderCacheUpdater.updateOrder(order);
            } catch (Exception e2) {
                logger.error("Error while saving the transaction rolled back order to the cache ", e2);
            }
            //push order response to the clients
            pushOrderResponse(order);
            return order;
        }

        //if validation fail return the order to caller to send the reply to client
        if (order.getStatus() == OrderStatus.REJECTED
                || (order.getStatus() == OrderStatus.RECEIVED &&  order.getExchange().equals(ExchangeCodes.OPRA))
                || order.getStatus() == OrderStatus.WAITING_FOR_APPROVAL) {
            //persist the order
            orderCacheUpdater.updateOrder(order);
            //push order response to the clients
            pushOrderResponse(order);
            return order;
        }


        //At this point all the validation including risk management of the order is finished and ready for sending to execution
        order.setStatus(OrderStatus.VALIDATED);
        order.setStatus(OrderStatus.SEND_TO_OMS_NEW);

        stpConnectorHelper.sendQueueMessage(FixOrderInterface.MessageType.NEW_ORDER_SINGLE, order);

        //update the order at cache
        orderCacheUpdater.updateOrder(order);

        //push the order responses to the clients
        pushOrderResponse(order);
        //record the order in back office
        long endTime = System.currentTimeMillis();
        logger.error("Time Elapsed For New Order- " + (endTime - startTime));

        return order;
    }

    /**
     * This method contain the flow of amend order. This include request validation, risk management and sending the order
     * to execution party by calling relevant core modules.
     *
     * @param amendOrderRequest Amend order request from client
     */
    @Override
    public void processAmendOrder(AmendOrderTRSRequest amendOrderRequest) {
        logger.info("Amend Order Waiting to obtain the lock - {}", amendOrderRequest);

        long startTime = System.currentTimeMillis();
        synchronized (lock) {
            logger.info("WAIT TIME FOR LOCK - {}", (System.currentTimeMillis() - startTime));
            logger.info("Lock obtained by Amend Order Request- {}", amendOrderRequest);
            startTime = System.currentTimeMillis();
            // security validation
            if (!(amendOrderRequest.getChannel() == ClientChannel.FIX
                    || amendOrderRequest.getChannel() == ClientChannel.DT
                    || amendOrderRequest.getChannel() == ClientChannel.MANUAL
                    || amendOrderRequest.getChannel() == ClientChannel.AT)) {
                String customerNumber = amendOrderRequest.getUserID();
                List<Account> accountList;
                try {
                    accountList = accountManager.getAllAccountsOfCustomer(customerNumber);

                    boolean validAccount = false;
                    for (Account account : accountList) {
                        if (account.getAccountNumber().equals(amendOrderRequest.getAccountNumber())) {
                            validAccount = true;
                            break;
                        }
                    }
                    if (!validAccount) {
                        throw new OMSException("Security validation failed: Account "
                                + amendOrderRequest.getAccountNumber() + "does not belog to customer " + customerNumber);
                    }
                } catch (Exception e) {
                    logger.error("Invalid Amend Order request", e);
                }
            }

            //Fill the Amend order from request
            Order order = getOrderPropertyPopulator().fillOrderFromAmendRequest(amendOrderRequest, orderFacadeFactory);
            Order oldOrder;
            //getting the original order from cache
            try {

                    oldOrder = orderFacadeFactory.getOrderManagementFacadeInterface().getOrderByClOrderId(order.getOrigClOrdID());

            } catch (Exception e) {
                //old order not found hence reject the amend request
                order.setStatus(OrderStatus.REJECTED);
                order.setText("Original Order not found");
                order.setInternalRejReason("Original Order not found");
                order.setCxlRejectResponseTo(FIXConstants.T434_AMEND_REJECT);
                //persist the order
                orderCacheUpdater.persistOrder(order);
                //push order response
                pushOrderResponse(order);
                return;
            }
            //populate the order from old order
            getOrderPropertyPopulator().fillFieldFromOriginalOrderForAmend(order, oldOrder);
            //Check whether is this a call center order and if so add it to call center order list and push the order

            //store the amend order before risk management
            orderValidator.validateBasicOrderProperties(order);
            orderCacheUpdater.persistOrder(order);

            //if this is KSE order apply price factor
            try {
                propertyProcessor.applyKSEPriceFactor(order);
            } catch (OrderException e) {
                logger.error("Problem in Applying KSE Price Factor - {}", e);
                order.setStatus(OrderStatus.REJECTED);
                order.setCumQty(0);
                order.setText(e.getMessage());
                order.setInternalRejReason(e.getMessage());
                orderCacheUpdater.persistOrder(order);
                order.setCxlRejectResponseTo(FIXConstants.T434_AMEND_REJECT);
                //push order response
                pushOrderResponse(order);
                //sync with back office
                return;
            }

            try {
                //Do all risk management in a session bean to manage transaction using CMT.
                normalOrderControllerBean.processAmendOrder(order, oldOrder);
            } catch (Exception e) {
                order.setStatus(OrderStatus.REJECTED);
                order.setCxlRejectResponseTo(FIXConstants.T434_AMEND_REJECT);
                order.setCumQty(0);
                order.setText("System Error - " + e.getMessage());
                order.setInternalRejReason("System Error - " + e.getMessage());
                try {
                    //persist the order
                    orderCacheUpdater.updateOrder(order);
                } catch (Exception e2) {
                    logger.error("Error while saving the transaction rolled back amend order to the cache ", e2);
                }
                //push order response to the clients
                pushOrderResponse(order);
                return;
            }

            if (order.getStatus() == OrderStatus.REJECTED) {
                order.setCxlRejectResponseTo(FIXConstants.T434_AMEND_REJECT);
                order.setCumQty(0);
            }

            if (order.getStatus() == OrderStatus.REJECTED || order.getStatus() == OrderStatus.PROCESSING
                    || order.getStatus() == OrderStatus.REPLACED || order.getStatus() == OrderStatus.WAITING_FOR_APPROVAL) {
                orderCacheUpdater.updateOrder(order);
                //update the old order at the cache
                orderCacheUpdater.updateOrder(oldOrder);
                //push order response
                pushOrderResponse(order);
                //sync with back office

                return;
            }
            //At this point all the validation of the order is finished and ready for sending to execution
            order.setStatus(OrderStatus.VALIDATED);

            //send the order to exchange
            stpConnectorHelper.sendQueueMessage(FixOrderInterface.MessageType.ORDER_CANCEL_REPLACE, order);

            //check the status of the order as oms accepted if so other has not send to the exchange due to market close or
            //problem in the fix connection
            if (order.getStatus() != OrderStatus.OMS_ACCEPTED) {

                order.setStatus(OrderStatus.SEND_TO_OMS_AMEND);
                oldOrder.setIntermediateStatus(OrderStatus.SEND_TO_OMS_AMEND);


                oldOrder.setReplacedOrderId(order.getClOrdID());
            }

            //update the order at cache
            orderCacheUpdater.updateOrder(order);
            //update the old order at the cache
            orderCacheUpdater.updateOrder(oldOrder);
            //need to right the code related to sending messages to client. That is pushing orders to Dealers, direct customers
            //push order response
            pushOrderResponse(order);
            //record the order in back office
            long endTime = System.currentTimeMillis();
            logger.info("Time Elapsed For Amend Order- " + (endTime - startTime));
        }
    }

    /**
     * This method contain the flow of cancel order. This include cancel request validation, risk management and
     * sending the order to execution party by calling relevant core modules.
     *
     * @param cancelOrderRequest Cancel order request from client
     */
    @Override
    public void processCancelOrder(CancelOrderTRSRequest cancelOrderRequest) {
        logger.info("Cancel Order Waiting to obtain the lock - {}", cancelOrderRequest);
        //Base of the type of the dealer send the order to pending status

        long startTime = System.currentTimeMillis();
        synchronized (lock) {
            logger.info("WAIT TIME FOR LOCK - {}", (System.currentTimeMillis() - startTime));
            logger.info("Lock obtained by Cancel Order Request- {}", cancelOrderRequest);
            startTime = System.currentTimeMillis();
            // security validation
            if (!(cancelOrderRequest.getChannel() == ClientChannel.FIX
                    || cancelOrderRequest.getChannel() == ClientChannel.DT
                    || cancelOrderRequest.getChannel() == ClientChannel.MANUAL
                    || cancelOrderRequest.getChannel() == ClientChannel.AT)) {
                String customerNumber = cancelOrderRequest.getCustomerID();
                /**
                 * Added since the orders sent from the back office does not have a customer number hence validation is not
                 * needed
                 */
                if (customerNumber != null && !"".equalsIgnoreCase(customerNumber.trim())) {
                    List<Account> accountList;
                    try {
                        accountList = accountManager.getAllAccountsOfCustomer(customerNumber);

                        boolean validAccount = false;
                        for (Account account : accountList) {
                            if (account.getAccountNumber().equals(cancelOrderRequest.getAccountNumber())) {
                                validAccount = true;
                                break;
                            }
                        }
                        if (!validAccount) {
                            throw new OMSException("Security validation failed");
                        }
                    } catch (Exception e) {
                        logger.error("Invalid Cancel Order request", e);
                    }
                } else {
                    logger.info("Customer Number is not found . Hence validation is bypassed. Seems order originated from Old RIA");
                }
            }

            //Fill the cancel order from request
            Order order = getOrderPropertyPopulator().fillOrderFromCancelRequest(cancelOrderRequest, orderFacadeFactory);

            Order oldOrder;
            //getting the original order from cache
            try {

                    //get the old order
                    oldOrder = orderFacadeFactory.getOrderManagementFacadeInterface().getOrderByClOrderId(order.getOrigClOrdID());

            } catch (Exception e) {
                //old order not found hence reject the cancel request
                order.setStatus(OrderStatus.REJECTED);
                order.setText("Order not found");
                order.setInternalRejReason("Order not found");
                order.setCxlRejectResponseTo(FIXConstants.T434_CANCEL_REJECT);
                //push order response
                pushOrderResponse(order);
                return;
            }


            //populate the order from original order
            getOrderPropertyPopulator().fillFieldFromOriginalOrderForCancel(order, oldOrder);


            //Validate the cancel order
            orderValidator.validateCancelOrder(order, oldOrder);
            //if validation fail return the order to caller to send the reply to client
            if (order.getStatus() == OrderStatus.REJECTED) {
                order.setCxlRejectResponseTo(FIXConstants.T434_CANCEL_REJECT);
                //persist the order
                orderCacheUpdater.persistOrder(order);
                //update the old order in cache
                orderCacheUpdater.updateOrder(oldOrder);
                //push order response
                pushOrderResponse(order);
                //sync with back office
                return;
            }
            //check whether internal matching enable status if this cancel for IOM. That is withdraw from market for IOM

            if (oldOrder.getStatus() == OrderStatus.OMS_ACCEPTED) {
                //if order is in oms accepted,processing or oms accepted status mean still in our oms and not yet send to market.
                // Hence cancellation is same as expiration of the order from oms side
                try {
                    //set the user Id of the user perform the cancel
                    oldOrder.setUserId(order.getUserId());
                    oldOrder.setUserName(order.getUserName());

                    //Do operation inside session bean to get the support of CMT
                    normalOrderControllerBean.processCancelOrder(oldOrder);
                    //push order response
                    pushOrderResponse(oldOrder);
                    //sync with back office
                    return;
                } catch (Exception e) {
                    //internal matching not enable for customer. Hence no allow to cancel
                    order.setStatus(OrderStatus.REJECTED);
                    order.setText("System Error - " + e.getMessage());
                    order.setInternalRejReason("System Error - " + e.getMessage());
                    order.setCxlRejectResponseTo(FIXConstants.T434_CANCEL_REJECT);
                    //persist the order
                    orderCacheUpdater.persistOrder(order);
                    //push order response
                    pushOrderResponse(order);
                    return;
                }
            }

            //process the property of the order by applying price factors etc.
            try {
                propertyProcessor.applyKSEPriceFactor(order);
                propertyProcessor.processOrder(order);
            } catch (Exception e) {
                logger.error("Problem in populating the order parameters - {}", e);
                order.setStatus(OrderStatus.REJECTED);
                order.setText(e.getMessage());
                order.setInternalRejReason(e.getMessage());
                order.setCxlRejectResponseTo(FIXConstants.T434_CANCEL_REJECT);
                orderCacheUpdater.persistOrder(order);
                //push order response
                pushOrderResponse(order);
                //sync with back office
                return;
            }
            //At this point all the validation of the order is finished and ready for sending to execution
            order.setStatus(OrderStatus.VALIDATED);

            orderCacheUpdater.persistOrder(order);

            //send the order to execution
            stpConnectorHelper.sendQueueMessage(FixOrderInterface.MessageType.ORDER_CANCEL_REQUEST, order);

            order.setStatus(OrderStatus.SEND_TO_OMS_CANCEL);
            oldOrder.setIntermediateStatus(OrderStatus.SEND_TO_OMS_CANCEL);
            oldOrder.setReplacedOrderId(order.getClOrdID());

            //update the order at cache
            orderCacheUpdater.updateOrder(order);
            //update the old order at the cache
            orderCacheUpdater.updateOrder(oldOrder);

            //push order response
            pushOrderResponse(order);

            long endTime = System.currentTimeMillis();
            logger.info("Time Elapsed For Cancel Order- " + (endTime - startTime));
        }
    }

    /**
     * This method contain the flow of the order expiration. This include expire request validation, risk management and
     * Here the order is not sending to the execution party, but all the pending values will be removed at this risk
     * management level
     *
     * @param expireOrderRequest expire order request from client
     */
    @Override
    public void processExpireOrder(ExpireOrderTRSRequest expireOrderRequest) {
        logger.info("Processing a new expire order request -{}", expireOrderRequest);
        long startTime = System.currentTimeMillis();
        //getting the order using request data
        Order order;
        try {
            //get the original order from cache.
            order = orderFacadeFactory.getOrderManagementFacadeInterface().getOrderByClOrderId(expireOrderRequest.getClOrderId());
        } catch (Exception e) {
            logger.warn("Order does not found in the system");
            return;
        }

        //populate the required fields from the expire order request
        getOrderPropertyPopulator().fillOrderFromExpireRequest(expireOrderRequest, order);
        //check the current status of the order. Only open orders are allow to expire.
        if (order.getStatus() != OrderStatus.VALIDATED && order.getStatus() != OrderStatus.NEW && order.getStatus() !=
                OrderStatus.OMS_ACCEPTED && order.getStatus() != OrderStatus.SEND_TO_OMS_NEW && order.getStatus() !=
                OrderStatus.REPLACED && order.getStatus() != OrderStatus.PARTIALLY_FILLED &&
                order.getStatus() != OrderStatus.SUSPENDED && order.getStatus() != OrderStatus.PENDING_NEW) {
            order.setText("Not allow to expire");
            order.setInternalRejReason("Not allow to expire");
            return;
        }
        try {
            normalOrderControllerBean.processExpireOrder(order);
        } catch (Exception e) {
            order.setStatus(OrderStatus.REJECTED);
            order.setText("System Error. Please contact broker -" + e.getMessage());
            order.setInternalRejReason("System Error. Please contact broker -" + e.getMessage());
            pushOrderResponse(order);
            return;
        }

        //need to right the code related to sending messages to client. That is pushing orders to Dealers, direct customers
        // and to fix customers. Need to call to the trs_connector and stp_connector
        //push order response
        pushOrderResponse(order);

        long endTime = System.currentTimeMillis();
        logger.info("Time Elapsed For Expire Order- " + (endTime - startTime));
    }

    /**
     * method used to expire a offline order
     *
     * @param expireOrderRequest is the expire order request bean
     */
    @Override
    public void processExpireOfflineOrder(ExpireOrderTRSRequest expireOrderRequest) {
        logger.info("Processing a new expire order request -{}", expireOrderRequest);
        long startTime = System.currentTimeMillis();
        //getting the order using request data
        Order order;
        try {
            //get the original order from cache.
            order = orderFacadeFactory.getOrderManagementFacadeInterface().getOrderByClOrderId(expireOrderRequest.getClOrderId());
        } catch (Exception e) {
            logger.warn("Order does not found in the system");
            return;
        }

        //populate the required fields from the expire order request
        getOrderPropertyPopulator().fillOrderFromExpireRequest(expireOrderRequest, order);
        //check the current status of the order. Only open orders are allow to expire.
        if (order.getStatus() != OrderStatus.COMPLETED) {
            order.setText("Not allow to expire");
            order.setInternalRejReason("Not allow to expire");
            return;
        }
        try {
            normalOrderControllerBean.processExpireOrder(order);
        } catch (Exception e) {
            order.setStatus(OrderStatus.REJECTED);
            order.setText("System Error. Please contact broker -" + e.getMessage());
            order.setInternalRejReason("System Error. Please contact broker -" + e.getMessage());
            pushOrderResponse(order);
            return;
        }
        //need to right the code related to sending messages to client. That is pushing orders to Dealers, direct customers
        // and to fix customers. Need to call to the trs_connector and stp_connector
        //push order response
        pushOrderResponse(order);

        //sync with back office
        long endTime = System.currentTimeMillis();
        logger.info("Time Elapsed For Expire offline Order- " + (endTime - startTime));
    }


    /**
     * This wil push order responses to the TRS and other external parties
     *
     * @param order is the order bean
     */
    private void pushOrderResponse(Order order) {

        //need to set the order category since in the order push responses clients only know there is only one response
        //for all the order types. This is set in the populate normal order response method in the TRS Writer
        //send the response to the relevant parties
        getResponseSenderHelper().sendResponsesToCorrespondingParties(order);
    }


    /**
     * this method is used in jUnit testing
     *
     * @return
     */
    public OrderPropertyPopulator getOrderPropertyPopulator() {
        return OrderPropertyPopulator.getInstance();
    }


    /**
     * this method is used in jUnit testing
     *
     * @return
     */
    public ResponseSenderHelper getResponseSenderHelper() {
        return ResponseSenderHelper.getInstance();
    }


}
