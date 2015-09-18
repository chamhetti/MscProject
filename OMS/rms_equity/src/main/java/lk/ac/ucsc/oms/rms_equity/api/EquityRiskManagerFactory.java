package lk.ac.ucsc.oms.rms_equity.api;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EquityRiskManagerFactory {
    private static EquityRiskManagerFactory facadeFactory;
    private ApplicationContext context;

    /**
     * Default Constructor of the Equity Risk Manager Factory
     */
    private EquityRiskManagerFactory() {
        context = new ClassPathXmlApplicationContext("/implGeneral/spring-config-equityrms.xml");
    }

    /**
     * Return the instance of equity risk management factory which is used to get the service interface related
     * to risk management of equity orders
     *
     * @return the EquityRiskManagerFactory
     */
    public static EquityRiskManagerFactory getInstance() {
        if (facadeFactory == null) {
            return createInstance();
        }
        return facadeFactory;
    }

    /**
     * ensure to create only one instance of the module
     *
     * @return
     */
    private static synchronized EquityRiskManagerFactory createInstance() {
        if (facadeFactory != null) {
            return facadeFactory;
        }
        facadeFactory = new EquityRiskManagerFactory();
        return facadeFactory;
    }

    /**
     * Returns the Equity Risk Manager Interface which contain all the risk management service methods
     * that is required for Equity Orders
     * Ex: Amend/Cancel/Reverse/Replace/Reject/Expired/Execution
     *
     * @return the EquityRiskManager
     * @see EquityRiskManager
     */
    public EquityRiskManager getEquityRiskManager() {
        return context.getBean("equityRiskManager", EquityRiskManager.class);
    }
}
