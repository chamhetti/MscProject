package lk.ac.ucsc.oms.boot_strapper.helper;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.customer.api.CustomerFactory;
import lk.ac.ucsc.oms.exchanges.api.ExchangeFactory;
import lk.ac.ucsc.oms.fix.api.FIXFacadeFactory;
import lk.ac.ucsc.oms.fixConnection.api.FIXConnectionFactory;
import lk.ac.ucsc.oms.orderMgt.api.OrderManagementFactory;


import lk.ac.ucsc.oms.stp_connector.api.ExecutionReportProcessor;
import lk.ac.ucsc.oms.stp_connector.api.FixConnectionStatusController;
import lk.ac.ucsc.oms.stp_connector.api.MarketStatusMessageController;
import lk.ac.ucsc.oms.stp_connector.api.STPConnectorFactory;
import lk.ac.ucsc.oms.symbol.api.SymbolFacadeFactory;
import lk.ac.ucsc.oms.system_configuration.api.SystemConfigurationFactory;
import lk.ac.ucsc.oms.trs_connector.api.*;
import lk.ac.ucsc.oms.execution_controller.ExecutionController;
import lk.ac.ucsc.oms.execution_controller.FixConnStatusProcessorImpl;
import lk.ac.ucsc.oms.execution_controller.TradingSessionStatusProcessor;
import lk.ac.ucsc.oms.login_controller.LoginController;
import lk.ac.ucsc.oms.trade_controller.normalOrders.NormalOrderController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SystemInitializingHelper {

    private static Logger logger = LogManager.getLogger(SystemInitializingHelper.class);

    public void injectControllerImplementations() {
        logger.info("***************************  OMS Starting ***********************");
        ExecutionReportProcessor executionReportProcessor = new ExecutionController();
        STPConnectorFactory.getInstance().setExecReportProcessor(executionReportProcessor);
        logger.info("injecting the execution controller to STP connector factory -Done");

        MarketStatusMessageController marketStatusResProcessor = new TradingSessionStatusProcessor();
        STPConnectorFactory.getInstance().setMarketStatusResProcessor(marketStatusResProcessor);
        logger.info("injecting the market status controller to STP connector factory -Done");

        FixConnectionStatusController connectionStatusProcessor = new FixConnStatusProcessorImpl();
        STPConnectorFactory.getInstance().setFixConnectionStatusProcessor(connectionStatusProcessor);
        logger.info("injecting the fix connection status processor to STP connector factory -Done");

        LoginControllerInterface loginControllerInterface = new LoginController("/spring-config-login_controller.xml");
        TrsConnectorFactory.getInstance().setLoginController(loginControllerInterface);
        logger.info("injecting login controller to TRS Connector factory -Done");

        NormalOrderControllerInterface normalOrderController = new NormalOrderController();
        TrsConnectorFactory.getInstance().setNormalOrderControllerInterface(normalOrderController);
        logger.info("injecting the normal order controller to TRS connector factory -Done");
    }


    public void initializeModules() throws OMSException {
        logger.info("Initializing modules started");
        SystemConfigurationFactory.getInstance().initialize();
        OrderManagementFactory.getInstance().initialize();
        CustomerFactory.getInstance().initialize();
        ExchangeFactory.getInstance().initialize();
        SymbolFacadeFactory.getInstance().initialize();
        FIXConnectionFactory.getInstance().initialize();
        STPConnectorFactory.getInstance().initialize();
        logger.info("Initializing modules finished");
    }


}
