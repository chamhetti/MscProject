package lk.ac.ucsc.oms.execution_controller;

import lk.ac.ucsc.oms.fixConnection.api.ConnectionStatus;
import lk.ac.ucsc.oms.fixConnection.api.FIXConnectionFacade;
import lk.ac.ucsc.oms.fixConnection.implGeneral.beans.FIXConnectionBean;
import lk.ac.ucsc.oms.stp_connector.api.FixConnectionStatusController;
import lk.ac.ucsc.oms.execution_controller.helper.util.ModuleDINames;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;



public class FixConnStatusProcessorImpl implements FixConnectionStatusController {
    private static Logger logger = LogManager.getLogger(ExecutionController.class);
    private static ApplicationContext ctx;
    private FIXConnectionFacade fixConnectionFacade;

    public FixConnStatusProcessorImpl() {
        if (ctx == null) {
            ctx = new ClassPathXmlApplicationContext("/spring-config-execution_controller.xml");
        }

        fixConnectionFacade = ctx.getBean(ModuleDINames.FIX_CONNECTION_FACADE, FIXConnectionFacade.class);
    }



    @Override
    public void processConnectedSession(String sessionQualifier) {
        try {
            List<FIXConnectionBean> fixConnections = fixConnectionFacade.getFixConnectionBySessionQualifier(sessionQualifier);
            logger.info("Fix connections load - {}", fixConnections);
            if (fixConnections == null) {
                logger.error("Fix connection not configured correctly at OMS level Session Qualifier - {}", sessionQualifier);
                return;
            }
            logger.debug("changing the status of the fix connection as connected");
            for (FIXConnectionBean fixConn : fixConnections) {
                fixConn.setConnectionStatus(ConnectionStatus.CONNECTED);
                logger.debug("update the connection status changes to cache");
                fixConnectionFacade.update(fixConn);

            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


    @Override
    public void processDisconnectSession(String sessionId) {
        try {
            List<FIXConnectionBean> fixConnections = fixConnectionFacade.getFixConnectionBySessionQualifier(sessionId);
            logger.info("Fix connections load - {}", fixConnections);
            if (fixConnections == null) {
                logger.error("Fix connection not configured correctly at OMS level Qualifier - {}", sessionId);
                return;
            }
            for (FIXConnectionBean fixConn : fixConnections) {
                fixConn.setConnectionStatus(ConnectionStatus.DISCONNECTED);
                logger.debug("update the connection status changes to cache");
                fixConnectionFacade.update(fixConn);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


}
