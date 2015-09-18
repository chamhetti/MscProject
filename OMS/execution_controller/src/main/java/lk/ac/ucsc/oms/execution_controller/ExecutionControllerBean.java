package lk.ac.ucsc.oms.execution_controller;

import lk.ac.ucsc.oms.common_utility.api.enums.OrderStatus;
import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSRuntimeException;
import lk.ac.ucsc.oms.fix.api.beans.FixOrderInterface;
import lk.ac.ucsc.oms.fix.impl.util.FIXConstants;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import lk.ac.ucsc.oms.execution_controller.helper.ExecReportProcessingHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import java.util.concurrent.ConcurrentLinkedQueue;

import static lk.ac.ucsc.oms.common_utility.api.GlobalLock.lock;



@Stateless
public class ExecutionControllerBean {
    private Logger logger = LogManager.getLogger(ExecutionControllerBean.class);

    private ExecReportProcessingHelper execReportProcessingHelper;
    private static ConcurrentLinkedQueue<FixOrderInterface> execReportQueue =new ConcurrentLinkedQueue<FixOrderInterface>();


    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Order processExecutionReport(FixOrderInterface fixOrder1) {
        logger.info("Waiting for the lock -{}",fixOrder1);
        long startTime = System.currentTimeMillis();
        execReportQueue.add(fixOrder1);
        synchronized (lock) {
            FixOrderInterface fixOrder =execReportQueue.poll();
            logger.info("lock obtained by -{}",fixOrder1);
            logger.info("WAIT TIME FOR LOCK - {}", (System.currentTimeMillis() -startTime));
            Order order = null;
            try {
                if (execReportProcessingHelper == null){
                    execReportProcessingHelper = new ExecReportProcessingHelper();
                }
                switch (fixOrder.getOrdStatus()) {
                    case FIXConstants.T39_NEW:
                        order = execReportProcessingHelper.processNewReply(fixOrder);
                        break;
                    case FIXConstants.T39_PENDING_NEW:
                        order = execReportProcessingHelper.processOrderAcceptedReply(OrderStatus.PENDING_NEW, fixOrder);
                        break;
                    case FIXConstants.T39_PENDING_REPLACE:
                        order = execReportProcessingHelper.processOrderAcceptedReply(OrderStatus.PENDING_REPLACE, fixOrder);
                        break;
                    case FIXConstants.T39_PENDING_CANCEL:
                        order = execReportProcessingHelper.processOrderAcceptedReply(OrderStatus.PENDING_CANCEL, fixOrder);
                        break;
                    case FIXConstants.T39_REPLACED:
                        order = execReportProcessingHelper.processReplaceReply(fixOrder.getTransactTime(), fixOrder);
                        break;
                    case FIXConstants.T39_CANCELLED:
                        order = execReportProcessingHelper.processCancelReply(fixOrder.getTransactTime(), fixOrder);
                        break;
                    case FIXConstants.T39_EXPIRED:
                        order = execReportProcessingHelper.processExpireReply(fixOrder.getTransactTime(), fixOrder);
                        break;
                    case FIXConstants.T39_REJECTED:
                        order = execReportProcessingHelper.processRejectReply(fixOrder.getText(), fixOrder.getTransactTime(), fixOrder);
                        break;
                    case FIXConstants.T39_PARTIALLY_FILLED:
                    case FIXConstants.T39_FILLED:
                        order = execReportProcessingHelper.processExecutionReply(fixOrder);
                        break;
                    default:
                        logger.error("Invalid order response received >>" + fixOrder);
                        break;

                }
                //order execution post process

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new OMSRuntimeException(e.getMessage(), e);
            }

            return order;
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void processManualExecuteOrder(Order order) {
        long startTime =System.currentTimeMillis();
        synchronized (lock) {
            logger.info("WAIT TIME FOR LOCK - {}", (System.currentTimeMillis() -startTime));
            try {
                if (execReportProcessingHelper == null) {
                    execReportProcessingHelper = new ExecReportProcessingHelper();
                }

            } catch (Exception e) {
                logger.error("Issue in processing manual order execution - {}", e);
                throw new OMSRuntimeException(e.getMessage(), e);
            }
        }
    }
}
