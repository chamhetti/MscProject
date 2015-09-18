package lk.ac.ucsc.clientConnector.client;

import lk.ac.ucsc.clientConnector.api.MessageRouter;
import lk.ac.ucsc.clientConnector.api.OmsController;
import lk.ac.ucsc.clientConnector.common.api.*;
import lk.ac.ucsc.clientConnector.exceptions.ClientConnectorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class ClientMessageRouter implements MessageRouter {
    private static Logger logger = LoggerFactory.getLogger(ClientMessageRouter.class);
    private static int count;
    private OmsController frontOfficeController;
    private OmsController frontOfficeController2;
    private Map<String, MessageDestination> routingMap = new HashMap<>();
    private BlockingQueue<TrsMessage> inputQueue;
    private boolean active;
    private Map<String, String> frontOfficeRoutingMap;
    private RequestConfigManager requestConfigManager;
    private boolean node2Enabled;

    public ClientMessageRouter() {
        inputQueue = new LinkedBlockingQueue<>();
        Thread routingThread = new Thread(new RoutingWorker());
        routingThread.setName("message-router");
        active = true;
        routingThread.start();
    }

    public RequestConfigManager getRequestConfigManagerInstance() {
        return CommonFactory.getInstance().getRequestConfigManager();
    }

    public void setRoutingMap(Map<String, MessageDestination> routingMap) {
        this.routingMap = routingMap;
    }

    @Override
    public void setFrontOfficeController(OmsController frontOfficeController) {
        this.frontOfficeController = frontOfficeController;
    }

    public void setFrontOfficeController2(OmsController frontOfficeController2) {
        this.frontOfficeController2 = frontOfficeController2;
    }



    /**
     * Put the Base Object to the inputQueue
     *
     * @param message is the Base Object
     */
    @Override
    public void putMessage(TrsMessage message) throws ClientConnectorException {
        message.setDestination( MessageDestination.FRONT_OFFICE);
        inputQueue.add(message);
    }

    private void routeMessage(TrsMessage message) {
        try {
            logger.info("Routing new message. Count: " + ++count);

            switch (message.getDestination()) {
                case FRONT_OFFICE:
                    routeToFrontOffice(message);
                    break;
                default:
                    logger.error("Invalid destination. Verify routing configuration is correct");
            }
        } catch (Exception e) {
            logger.error("Error routing message", e);
        }
    }

    private void routeToFrontOffice(TrsMessage message) throws Exception {
        if (node2Enabled) {
            if (requestConfigManager == null) {
                requestConfigManager = getRequestConfigManagerInstance();
            }
            String requestConfig = requestConfigManager.getRequestConfig(message.getGroup(), message.getReqType());
            if (requestConfig != null) {
                if ("NODE01".equals(requestConfig)) {
                    frontOfficeController.placeMessage(message);

                } else if ("NODE02".equals(requestConfig)) {
                    frontOfficeController2.placeMessage(message);
                } else {
                    throw new Exception("Invalid front office destination");
                }
            } else {
                throw new Exception("Front office destination not configured");
            }
        } else {
            frontOfficeController.placeMessage(message);
        }
    }

    /**
     * Used to stop the thread that does the routing work
     */
    public void stopRouting() {
        this.active = false;
    }


    public void setFrontOfficeRoutingMap(Map frontOfficeRoutingMap) {
        this.frontOfficeRoutingMap = frontOfficeRoutingMap;
    }

    public void setNode2Enabled(boolean node2Enabled) {
        this.node2Enabled = node2Enabled;
    }

    public Map getFrontOfficeRoutingMap() {
        return frontOfficeRoutingMap;
    }

    private class RoutingWorker implements Runnable {
        @Override
        public void run() {
            TrsMessage message;
            logger.info("Router thread started: " + Thread.currentThread().getName());
            while (active) {
                try {
                    message = inputQueue.take();
                    routeMessage(message);

                } catch (InterruptedException e) {
                    logger.error(e.toString(), e);
                }
            }
        }
    }

}
