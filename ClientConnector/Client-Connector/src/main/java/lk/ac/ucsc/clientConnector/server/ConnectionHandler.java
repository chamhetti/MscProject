package lk.ac.ucsc.clientConnector.server;
import lk.ac.ucsc.clientConnector.common.api.DataSourceProvider;
import lk.ac.ucsc.clientConnector.common.api.TrsException;
import lk.ac.ucsc.clientConnector.controller.FrontOfficeController;
import lk.ac.ucsc.clientConnector.controller.WebServerController;
import lk.ac.ucsc.clientConnector.settings.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;

public class ConnectionHandler {
    public static final int TIMEOUT = 30;
    private static Logger logger = LoggerFactory.getLogger(ConnectionHandler.class);
    private WebServerController webController;
    private FrontOfficeController frontOfficeController;
    private FrontOfficeController frontOfficeController2;
    private Settings settings;

    /**
     * Initialize the Connection handler
     */
    public void initConnHandler(ApplicationContext applicationContext) {
        logger.info("Initializing controllers");
        settings = (Settings) applicationContext.getBean("settings");
        logger.info("Trying to connect to data-source...");
        DataSource dataSource = (DataSource) applicationContext.getBean("dataSource");
//        try {
//            Connection connection = dataSource.getConnection();
//            if (connection.isValid(TIMEOUT)) {
//                logger.info("Data-source connection succeeded");
//                connection.close();
//            } else {
//                throw new TrsException("Connection is invalid");
//            }
//        } catch (Exception e) {
//            logger.error("Fatal: Could not connect to data-source. Quitting..", e);
//            System.exit(1);
//        }
        DataSourceProvider.setDataSource(dataSource);

        if (settings.isFrontOfficeEnabled()) {
            logger.info("Initializing front-office controller...");
            frontOfficeController = (FrontOfficeController) applicationContext.getBean("frontOfficeController");
            frontOfficeController.initController();
            logger.info("Initializing front-office controller succeeded");
            if (settings.isNode02Enabled()) {
                logger.info("Initializing secondary front-office controller...");
                frontOfficeController2 = (FrontOfficeController) applicationContext.getBean("frontOfficeController2");
                frontOfficeController2.initController();
                logger.info("Initializing secondary front-office controller succeeded");
            }
        }
        if (settings.isWebClientEnabled() ) {
            logger.info("Initializing web server controller...");
            webController = (WebServerController) applicationContext.getBean("webController");
            try {
                webController.initController();
                logger.info("Initializing web server controller succeeded");
            } catch (Exception e) {
                logger.error("Error initializing web controller", e);
                System.exit(1);
            }
        }
        startControllers();
    }

    private void startControllers() {
        logger.info("Starting the controllers");
        try {
            if (settings.isFrontOfficeEnabled()) {
                frontOfficeController.startController();
                if (settings.isNode02Enabled()) {
                    frontOfficeController2.startController();
                }
            }
            if (settings.isWebClientEnabled()) {
                webController.startController();
            }
        } catch (Exception e) {
            logger.error("Starting controller failed", e);
            System.exit(1);
        }
    }
}
