package lk.ac.ucsc.oms.trade_controller.helper;

/**
 * Utility Class to contain bean definition names in the spring configuration files
 *
 * @author : Hetti
 *         Date: 4/22/13
 *         Time: 12:33 PM
 */
public final class ModuleDINames {
    private ModuleDINames() {

    }

    public static final String ORDER_FACTORY_DI_NAME = "orderFactory";
    public static final String ORDER_SEARCH_FACADE_DI_NAME = "orderSearchFacade";
    public static final String CONDITIONAL_ORDER_FACTORY_DI_NAME = "conditionalOrderFactory";
    public static final String SLICE_ORDER_FACTORY_DI_NAME = "sliceOrderFactory";
    public static final String SLICE_ORDER_SEARCH_FACADE_DI_NAME = "sliceOrderSearchFacade";
    public static final String SLICE_ORDER_MANAGER_DI_NAME = "sliceOrderManager";
    public static final String MRE_CONNECTOR_DI_NAME = "mreConnector";
    public static final String STP_CONNECTOR_DI_NAME = "stpConnector";
    public static final String TRS_CONNECTOR_FACTORY_DI_NAME = "trsConnectorFactory";
    public static final String RISK_MANAGER_FACTORY_DI_NAME = "riskMangerFactory";
    public static final String EXCHANGE_FACTORY_DI_NAME = "exchangeFactory";
    public static final String SYS_PARA_MANAGER_DI_NAME = "sysParaManager";
    public static final String DESK_ORDER_FACTORY_DI_NAME = "deskOrderFactory";
    public static final String DESK_ORDER_MANAGER_DI_NAME = "deskOrderManager";
    public static final String INSTITUTION_FACTORY_DI_NAME = "institutionFactory";
    public static final String USER_FACTORY_DI_NAME = "userFacadeFactory";
    public static final String CUSTOMER_FACTORY_DI_NAME = "customerFactory";
    public static final String SYMBOL_FACADE_FACTORY_DI_NAME = "symbolFactory";
    public static final String CURRENCY_FACADE_FACTORY_DI_NAME = "currencyFactory";
    public static final String FOL_ORDER_FACTORY_DI_NAME = "folOrderFactory";
    public static final String ACCOUNT_MANAGER_DI_NAME = "accountManager";
    public static final String IOM_MANAGER_DI_NAME = "iomManager";
    public static final String EXCHANGE_MANAGER_DI_NAME = "exchangeManager";
    public static final String ERROR_FACADE_DI_NAME = "errorFacade";
    public static final String RDBM_WRITER_DI_NAME = "rdbmConnectorFactory";
    public static final String SYMBOL_PRICE_MANAGER_DI_NAME = "symbolPriceManager";
    public static final String STP_CONNECTOR_FACTORY_DI_NAME = "stpConnectionFactory";
    public static final String INSTITUTION_MANAGER_DI_NAME = "institutionManager";
    public static final String DAY_TRADING_MANAGER_DI_NAME = "dayTradingManager";
    public static final String NORMAL_ORDER_SESSION_BEAN_JNDI = "java:app/trade_controller/NormalOrderControllerBean";
    public static final String CONDITIONAL_ORDER_MANAGER_DI_NAME = "conditionalOrderManager";
    public static final String BACK_OFFICE_CONTROLLER_DI_NAME = "backOfficeIntegrationController";
    public static final String FOL_ORDER_MANAGER_DI_NAME = "folOrderManager";
    public static final String TRS_CONNECTOR_DI_NAME = "trsConnector";
    public static final String MESSAGE_SENDER_DI_NAME = "messageSender";
    public static final String FIX_CONNECTION_FACADE_DI_NAME = "fixConnectionFacade";
    public static final String SELL_SIDE_BROKER_FACADE_DI_NAME = "sellSideBrokerFacade";
    public static final String FIX_FACADE_DI_NAME = "fixFacade";
    public static final String USER_FACADE_DI_NAME = "userFacade";
    public static final String MULTI_NIN_ORDER_SESSION_BEAN_JNDI = "java:app/trade_controller/MultiNINOrderControllerBean";
    public static final String MULTI_NIN_ORDER_MANAGER_DI_NAME = "multiNINOrderManager";
    public static final String MULTI_NIN_ORDER_SEARCH_DI_NAME = "multiNINOrderSearchManager";
    public static final String CASH_MANAGER_DI_NAME = "cashManager";
    public static final String SYMBOL_WHITE_LIST_MANAGER_DI_NAME = "symbolWhiteListManager";

}
