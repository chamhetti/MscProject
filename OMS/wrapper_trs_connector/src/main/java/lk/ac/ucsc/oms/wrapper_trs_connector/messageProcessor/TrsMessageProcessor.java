package lk.ac.ucsc.oms.wrapper_trs_connector.messageProcessor;


import lk.ac.ucsc.oms.trs_connector.api.TrsConnector;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class TrsMessageProcessor {
    private static Logger logger = LogManager.getLogger(TrsMessageProcessor.class);
    private TrsConnector trsConnector;
    private static ApplicationContext ctx = new ClassPathXmlApplicationContext("/implGeneral/spring-config-wrapper_trs_connector.xml");


    public void initProcessor() {
        trsConnector = getContx().getBean("trsConnector", TrsConnector.class);
    }

    public ApplicationContext getContx() {
        return ctx;
    }


    public void processMessage(String request) {
        try {
            logger.debug("Received message from TRS - "+ request);
            trsConnector.processTrsMessage(request);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
    }
}
