package lk.ac.ucsc.oms.cash_trading.api;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CashTradingFactory {
    private static ApplicationContext context;
    private static CashTradingFactory facadeFactory;

    private CashTradingFactory() {
    }

    public static CashTradingFactory getInstance() {
        if (facadeFactory == null) {
            return  createInstance();
        }
        return facadeFactory;
    }

    private static synchronized CashTradingFactory createInstance() {
        if (context == null) {
            context = new ClassPathXmlApplicationContext("/implGeneral/spring-config-cashtrading.xml");
        }
        if (facadeFactory != null) {
            return facadeFactory;
        }
        facadeFactory = new CashTradingFactory();
        return facadeFactory;
    }

    public static void setContext(ApplicationContext context) {
        CashTradingFactory.context = context;
    }

    public CashTradingManager getCashTradingManager() {
        return context.getBean("cashTradingManager", CashTradingManager.class);
    }



}
