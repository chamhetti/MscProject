package lk.ac.ucsc.oms.execution_controller;

import lk.ac.ucsc.oms.execution_controller.helper.util.ModuleDINames;
import lk.ac.ucsc.oms.fix.api.beans.FixOrderInterface;
import lk.ac.ucsc.oms.orderMgt.api.OrderSearchFacadeInterface;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.ExecutionAuditRecordException;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.OrderException;
import lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderBean;
import lk.ac.ucsc.oms.stp_connector.api.ExecutionReportProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.naming.InitialContext;
import javax.naming.NamingException;


public class ExecutionController implements ExecutionReportProcessor {
    private static Logger logger = LogManager.getLogger(ExecutionController.class);

    private static final String EXEC_CONTROLLER_SESSION_BEAN_JNDI = "java:app/execution_controller/ExecutionControllerBean";
    private static ApplicationContext ctx;

    private ExecutionControllerBean executionControllerBean;
    private OrderSearchFacadeInterface orderSearchFacade;

    public ExecutionController() {
        synchronized (this) {
            if (ctx == null) {
                ctx = new ClassPathXmlApplicationContext("/spring-config-execution_controller.xml");
            }
        }
        orderSearchFacade = ctx.getBean(ModuleDINames.ORDER_SEARCH_FACADE_DI_NAME, OrderSearchFacadeInterface.class);
        InitialContext theContext;
        try {
            theContext = new InitialContext();
            executionControllerBean = (ExecutionControllerBean) theContext.lookup(EXEC_CONTROLLER_SESSION_BEAN_JNDI);
        } catch (NamingException e) {
            logger.error("Issue in loading Execution Controller bean - {}", e);
        }
    }


    public ExecutionController(ApplicationContext applicationContext) {

    }

    @Override
    public void processOrderReject(FixOrderInterface fixorder, String sessionID) {
        //business reject are not going to handle in oms side. Need to write OMS to avoid the business reject.
        logger.error("Business Reject Received >>>" + fixorder);
    }

    @Override
    public void processExecutionReport(FixOrderInterface fixorder, String sessionID) {
        logger.info("Process the execution report -{}", fixorder);
        try {
            fillCompulsoryFieldsFromFixMessage(fixorder, sessionID);
            Order order = executionControllerBean.processExecutionReport(fixorder);
            logger.debug("Sync execution report to back office -{}", order);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public Order processManualExecuteOrder(Order order) {
        logger.info("Process manual order execution -{}", order);
        try {
            executionControllerBean.processManualExecuteOrder(order);
            return order;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            order.setText("System Error");
            //set all the parameters to default value that need to be sync at back office since all got roll back
            return order;
        }
    }


    private void fillCompulsoryFieldsFromFixMessage(FixOrderInterface fixOrder, String sessionID) throws OrderException, ExecutionAuditRecordException {
        logger.info("Fix Order recieved -{}", fixOrder);

        if (fixOrder.getSecurityExchange() == null || "".equals(fixOrder.getSecurityExchange())) {
            try {
                OrderBean orderBean = (OrderBean) orderSearchFacade.find(fixOrder.getClordID());
                if (orderBean != null) {
                    fixOrder.setSecurityExchange(orderBean.getExchange());
                }
            } catch (OrderException e) {
                logger.error("Error in finding the Order using the order number - {}", e);
            }
        }

    }

}
