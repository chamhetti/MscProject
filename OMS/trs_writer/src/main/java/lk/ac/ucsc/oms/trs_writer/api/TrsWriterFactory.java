package lk.ac.ucsc.oms.trs_writer.api;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;


public class TrsWriterFactory {

    //loading implementation of the service interface using spring DI
    private static ApplicationContext context;
    private static TrsWriterFactory trsWriterFactory;


    private TrsWriterFactory() {
        synchronized (this) {
            if (context == null) {
                context = new ClassPathXmlApplicationContext("/implGeneral/spring-config-trs-writer.xml");
            }
        }
    }

    /**
     * Factory method for the Trs Writer
     *
     * @return TrsWriterFactory
     */
    public static TrsWriterFactory getInstance() {
        if (trsWriterFactory == null) {
            createInstance();
        }
        return trsWriterFactory;
    }

    /**
     * This create the TRS writer Factory instance
     *
     * @return the   TrsWriterFactory
     */
    private static synchronized TrsWriterFactory createInstance() {
        if (trsWriterFactory != null) {
            return trsWriterFactory;
        }
        trsWriterFactory = new TrsWriterFactory();
        return trsWriterFactory;
    }

    /**
     * @return TrsWriterFacade interface that is used as the main interface which writes messages to TRS
     */
    public TrsWriterFacade getTrsWriter() {
        return (TrsWriterFacade) context.getBean("trsWriter");
    }



    /**
     * Setting spring configuration file path
     * Use only for unit testing
     *
     * @param context
     */
    public static void setContext(ApplicationContext context) {
        TrsWriterFactory.context = context;
    }


}
