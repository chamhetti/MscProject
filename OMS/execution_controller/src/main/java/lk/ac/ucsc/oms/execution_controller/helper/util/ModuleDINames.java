package lk.ac.ucsc.oms.execution_controller.helper.util;

/**
 * Utility Class to contain bean definition names in the spring configuration files
 *
 * @author Chathura
 *         Date: 9/2/13
 */
public final class ModuleDINames {
    private ModuleDINames() {

    }

    public static final String ORDER_FACTORY_DI_NAME = "orderFactory";
    public static final String SLICE_ORDER_FACTORY_DI_NAME = "sliceOrderFactory";
    public static final String MRE_CONNECTOR_FACTORY_DI_NAME = "mreConnectorFactory";
    public static final String STP_CONNECTOR_FACTORY_DI_NAME = "stpConnector";
    public static final String RISK_MANAGER_FACTORY_DI_NAME = "rmsFactory";
    public static final String EXCHANGE_FACTORY_DI_NAME = "exchangeFactory";
    public static final String SYS_CONFIG_FACTORY_DI_NAME = "sysConfigFactory";
    public static final String FIX_CONNECTION_FACADE = "fixConnectionFacade";
    public static final String CONDITIONAL_ORDER_MANAGER_DI_NAME = "conditionOrderManager";
    public static final String ORDER_MANAGER_DI_NAME = "orderManager";
    public static final String TRS_CONNECTOR_DI_NAME = "trsConnector";
    public static final String TO_BACK_OFFICE_CONTROLLER_DI_NAME = "toBackOfficeIntegrationController";
    public static final String RESPONSE_CONTROLLER_DI_NAME = "responseController";
    public static final String ORDER_SEARCH_FACADE_DI_NAME = "orderSearchManager";
    public static final String ERROR_FACADE_DI_NAME = "errorFacade";
    public static final String SELL_SIDE_BROKER_DI_NAME = "sellSideBrokerFacade";
}
