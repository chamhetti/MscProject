package lk.ac.ucsc.oms.trs_connector.api;

import lk.ac.ucsc.oms.messaging_protocol_json.api.InvalidVersionException;
import lk.ac.ucsc.oms.messaging_protocol_json.api.MessageProtocolFacade;
import lk.ac.ucsc.oms.trs_connector.implGeneral.json.handlers.populators.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;


public class TrsConnectorFactory {
    private static Logger logger = LogManager.getLogger(TrsConnectorFactory.class);

    //lording implementation of the service interface using spring DI
    private static ApplicationContext context = new ClassPathXmlApplicationContext("/implGeneral/json/spring-config-trs_connector-json.xml");

    private static LoginControllerInterface loginControllerInterface = null;
    private static NormalOrderControllerInterface normalOrderControllerInterface = null;
    private static InquiryControllerInterface inquiryControllerInterface = null;

    private MessageProtocolFacade messageProtocolFacade;
    private Map<Integer, AuthenticationBeansPopulator> authenticationBeansPopulatorMap;
    private Map<Integer, InquiryBeansPopulator> inquiryBeansPopulatorMap;
    private Map<Integer, CustomerInquiryBeansPopulator> customerInquiryBeansPopulatorMap;
    private Map<Integer, TradingInquiryBeansPopulator> tradingInquiryBeansPopulatorMap;
    private Map<Integer, NormalOrderBeansPopulator> normalOrderBeansPopulatorMap;
       private static TrsConnectorFactory factory;

    /**
     * private constructor of the class
     */
    private TrsConnectorFactory() {
    }

    /**
     * method to get the instance of trsConnectorFactory
     *
     * @return factory
     */
    public static synchronized TrsConnectorFactory getInstance() {
        logger.info("TrsConnectorFactory start >>" + factory);
        if (factory == null) {
            factory = new TrsConnectorFactory();
        }
        logger.info("TrsConnectorFactory end >>" + factory);
        return factory;
    }

    /**
     * Get the TrsConnector interface that is used as the main interface which process all the messages from TRS
     *
     * @return
     */
    public TrsConnector getTrsConnector() {
        return context.getBean("trsConnector", TrsConnector.class);
    }

    public AuthenticationBeansPopulator getAuthPopulator(String version) throws InvalidVersionException {
        if (authenticationBeansPopulatorMap == null) {
            authenticationBeansPopulatorMap = (Map<Integer, AuthenticationBeansPopulator>) context.getBean("authenticationBeansPopulatorMap");
        }
        return authenticationBeansPopulatorMap.get(getVersionNumber(version));
    }

    public InquiryBeansPopulator getInquiryPopulator(String version) throws InvalidVersionException {
        if (inquiryBeansPopulatorMap == null) {
            inquiryBeansPopulatorMap = (Map<Integer, InquiryBeansPopulator>) context.getBean("inquiryBeansPopulatorMap");
        }
        return inquiryBeansPopulatorMap.get(getVersionNumber(version));
    }

    public CustomerInquiryBeansPopulator getCustomerInquiryPopulator(String version) throws InvalidVersionException {
        if (customerInquiryBeansPopulatorMap == null) {
            customerInquiryBeansPopulatorMap = (Map<Integer, CustomerInquiryBeansPopulator>) context.getBean("customerInquiryBeansPopulatorMap");
        }
        return customerInquiryBeansPopulatorMap.get(getVersionNumber(version));
    }

    public TradingInquiryBeansPopulator getTradingInquiryPopulator(String version) throws InvalidVersionException {
        if (tradingInquiryBeansPopulatorMap == null) {
            tradingInquiryBeansPopulatorMap = (Map<Integer, TradingInquiryBeansPopulator>) context.getBean("tradingInquiryBeansPopulatorMap");
        }
        return tradingInquiryBeansPopulatorMap.get(getVersionNumber(version));
    }

    public NormalOrderBeansPopulator getNormalOrderPopulator(String version) throws InvalidVersionException {
        if (normalOrderBeansPopulatorMap == null) {
            normalOrderBeansPopulatorMap = (Map<Integer, NormalOrderBeansPopulator>) context.getBean("normalOrderBeansPopulatorMap");
        }
        return normalOrderBeansPopulatorMap.get(getVersionNumber(version));
    }


    private int getVersionNumber(String version) throws InvalidVersionException {
        if (messageProtocolFacade == null) {
            messageProtocolFacade = context.getBean("jsonFacade", MessageProtocolFacade.class);
        }
        return messageProtocolFacade.parseVersion(version);
    }

    /**
     * get the implementation of the login controller interface.
     *
     * @return
     */
    public LoginControllerInterface getLoginController() {
        return loginControllerInterface;
    }

    /**
     * Method used to set the implementation of the Login Controller interface. This Should be set while staring the
     * application.
     *
     * @param loginController
     */
    public void setLoginController(LoginControllerInterface loginController) {
        logger.info("Setting Login Controller: " + loginController);
        loginControllerInterface = loginController;
    }

    /**
     * get the implementation of the normal order controller interface.
     *
     * @return
     */
    public NormalOrderControllerInterface getNormalOrderControllerInterface() {
        return normalOrderControllerInterface;
    }

    /**
     * Method used to set the implementation of the Normal Order Controller interface.
     * This Should be set while staring the application.
     *
     * @param normalOrderController
     */
    public void setNormalOrderControllerInterface(NormalOrderControllerInterface normalOrderController) {
        logger.info("Setting Trading Controller: " + normalOrderController);
        normalOrderControllerInterface = normalOrderController;
    }

    /**
     * get the implementation of the inquiry controller interface.
     *
     * @return the inquiryControllerInterface
     */
    public InquiryControllerInterface getInquiryControllerInterface() {
        return inquiryControllerInterface;
    }

    /**
     * Method used to set the implementation of the inquiry Controller interface.
     *
     * @param inquiryController is the inquiry controller interface
     */
    public void setInquiryControllerInterface(InquiryControllerInterface inquiryController) {
        logger.info("Setting Inquiry Controller: " + inquiryController);
        inquiryControllerInterface = inquiryController;
    }





    /**
     * usage only for unit test case
     *
     * @param authenticationBeansPopulatorMap
     */
    public void setAuthenticationBeansPopulatorMap(Map<Integer, AuthenticationBeansPopulator> authenticationBeansPopulatorMap) {
        this.authenticationBeansPopulatorMap = authenticationBeansPopulatorMap;
    }


    /**
     * usage only for unit test case
     *
     * @param customerInquiryBeansPopulatorMap
     */
    public void setCustomerInquiryBeansPopulatorMap(Map<Integer, CustomerInquiryBeansPopulator> customerInquiryBeansPopulatorMap) {
        this.customerInquiryBeansPopulatorMap = customerInquiryBeansPopulatorMap;
    }

    /**
     * usage only for unit test case
     *
     * @param tradingInquiryBeansPopulatorMap
     */
    public void setTradingInquiryBeansPopulatorMap(Map<Integer, TradingInquiryBeansPopulator> tradingInquiryBeansPopulatorMap) {
        this.tradingInquiryBeansPopulatorMap = tradingInquiryBeansPopulatorMap;
    }

    /**
     * usage only for unit test case
     *
     * @param normalOrderBeansPopulatorMap
     */
    public void setNormalOrderBeansPopulatorMap(Map<Integer, NormalOrderBeansPopulator> normalOrderBeansPopulatorMap) {
        this.normalOrderBeansPopulatorMap = normalOrderBeansPopulatorMap;
    }




    /**
     * usage only for unit test case
     *
     * @param inquiryBeansPopulatorMap
     */
    public void setInquiryBeansPopulator(Map<Integer, InquiryBeansPopulator> inquiryBeansPopulatorMap) {
        this.inquiryBeansPopulatorMap = inquiryBeansPopulatorMap;
    }


    /**
     * usage only for unit test case
     *
     * @param messageProtocolFacade
     */
    public void setMessageProtocolFacade(MessageProtocolFacade messageProtocolFacade) {
        this.messageProtocolFacade = messageProtocolFacade;
    }
}
