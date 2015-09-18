package lk.ac.ucsc.clientConnector.server;

import lk.ac.ucsc.clientConnector.api.TrsApplicationContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;


public class TRSController {
    private static Logger logger = LoggerFactory.getLogger(TRSController.class);
    private ConnectionHandler connHandler;

    public static void main(String[] args) {
        new TRSController().init();
    }

    public void init() {
        logger.info("Starting TRS...");
        connHandler = getConnHandler();
        connHandler.initConnHandler(TrsApplicationContextFactory.getTrsContext());
        logger.info("===== DirectFN Trade Routing Server(TRS) started in " + ManagementFactory.getRuntimeMXBean().getUptime() + "ms =====");
    }

    public ConnectionHandler getConnHandler() {
        return new ConnectionHandler();
    }
}
