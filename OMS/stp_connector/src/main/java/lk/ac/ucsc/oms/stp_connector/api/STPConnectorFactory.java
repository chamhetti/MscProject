package lk.ac.ucsc.oms.stp_connector.api;

import lk.ac.ucsc.oms.stp_connector.api.exception.STPConnectException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class STPConnectorFactory {

    private static STPConnectorFactory stpConnectorFactory;
    private static ApplicationContext ctx = new ClassPathXmlApplicationContext("/implGeneral/spring-config-stpConnector.xml");
    private static ExecutionReportProcessor execReportProcessor;
    private static MarketStatusMessageController marketStatusMessageController;
    private static FixConnectionStatusController fixConnectionStatusController;


    public static STPConnectorFactory getInstance() {
        if (stpConnectorFactory == null) {
            return createInstance();
        }
        return stpConnectorFactory;
    }


    private static synchronized STPConnectorFactory createInstance() {
        if (stpConnectorFactory != null) {
            return stpConnectorFactory;
        }
        stpConnectorFactory = new STPConnectorFactory();

        return stpConnectorFactory;
    }

    private STPConnectorFactory() {
    }


    public STPConnector getSTPConnector() {
        return ctx.getBean("stpConnector", STPConnector.class);
    }


    public ExecutionReportProcessor getExecReportProcessor() {
        return execReportProcessor;
    }

    public void setExecReportProcessor(ExecutionReportProcessor execReportProcessor) {
        STPConnectorFactory.execReportProcessor = execReportProcessor;
    }

    public MarketStatusMessageController getMarketStatusResProcessor() {
        return marketStatusMessageController;
    }

    public void setMarketStatusResProcessor(MarketStatusMessageController marketStatusMessageController) {
        STPConnectorFactory.marketStatusMessageController = marketStatusMessageController;
    }


    public FixConnectionStatusController getFixConnectionStatusProcessor() {
        return fixConnectionStatusController;
    }


    public void setFixConnectionStatusProcessor(FixConnectionStatusController fixConnectionStatusController) {
        STPConnectorFactory.fixConnectionStatusController = fixConnectionStatusController;
    }

    public void initialize() throws STPConnectException {
        getSTPConnector().initialize();
    }


    public static void setContext(ApplicationContext context) {
        STPConnectorFactory.ctx = context;
    }

}
