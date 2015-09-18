package lk.ac.ucsc.oms.wrapper_stp_connector.message_processor;

import lk.ac.ucsc.oms.stp_connector.api.STPConnector;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.jms.Message;

public class STPMessageProcessor {
    private static Logger logger = Logger.getLogger(STPMessageProcessor.class);

    private STPConnector stpConnector;
    private static ApplicationContext ctx;

    /**
     * method that initialized the core modules used for message processing.
     */
    public void initProcessor() {
        if (ctx == null) {
            ctx = new ClassPathXmlApplicationContext("/implGeneral/spring-config-wrapper_stp_connector.xml");
        }
        stpConnector = ctx.getBean("stpConnector", STPConnector.class);
    }

    /**
     * Process the message from the Sell Side Connector Bean
     *
     * @param message is the message
     */
    public void processMessage(Message message) {
        try {
            stpConnector.processMessage(message);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
    }

    public static void setCtx(ApplicationContext ctx1) {
        ctx = ctx1;
    }

    public void setStpConnector(STPConnector stpConnector) {
        this.stpConnector = stpConnector;
    }

}
