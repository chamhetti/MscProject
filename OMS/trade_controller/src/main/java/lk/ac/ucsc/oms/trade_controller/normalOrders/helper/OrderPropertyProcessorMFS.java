package lk.ac.ucsc.oms.trade_controller.normalOrders.helper;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.exchanges.api.exceptions.ExchangeException;
import lk.ac.ucsc.oms.orderMgt.api.OrderManagementFactory;
import lk.ac.ucsc.oms.orderMgt.api.OrderManager;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.OrderException;
import lk.ac.ucsc.oms.symbol.api.SymbolFacadeFactory;
import lk.ac.ucsc.oms.symbol.api.SymbolManager;
import lk.ac.ucsc.oms.symbol.api.beans.BaseSymbol;
import lk.ac.ucsc.oms.symbol.api.exceptions.SymbolNotFoundException;
import lk.ac.ucsc.oms.trade_controller.helper.ExchangeCodes;
import lk.ac.ucsc.oms.trade_controller.helper.ModuleDINames;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;


public class OrderPropertyProcessorMFS {
    private static Logger logger = LogManager.getLogger(OrderPropertyProcessorMFS.class);
    private SymbolManager symbolManager;
    private OrderManager orderManagementFacadeInterface;
    private static final double KSE_PRICE_FACTOR_CONST = 0.001;

    /**
     * Constructs the Order Property processor using the given application context
     *
     * @param ctx is the application context
     */
    public OrderPropertyProcessorMFS(ApplicationContext ctx) {
        symbolManager = ctx.getBean(ModuleDINames.SYMBOL_FACADE_FACTORY_DI_NAME, SymbolFacadeFactory.class).getSymbolManager();
        orderManagementFacadeInterface = ctx.getBean(ModuleDINames.ORDER_FACTORY_DI_NAME, OrderManagementFactory.class).getOrderManagementFacadeInterface();
    }

    /**
     * process the property of the order such as applying price factors etc.
     *
     * @param order is the order bean
     * @return Order
     * @throws ExchangeException
     * @throws SymbolNotFoundException
     */
    public Order processOrder(Order order) throws OMSException {
        if (!order.getExchange().trim().equalsIgnoreCase(ExchangeCodes.KSE)) {
            //Apply the exchange specific price factors for all the prices
            BaseSymbol baseSymbol = symbolManager.getSymbol(order.getSymbol(), order.getExchange(), BaseSymbol.SecurityType.getEnum(order.getSecurityType()));
            //Apply symbol specific price factors for all the prices
            if (baseSymbol.getPriceRatio() > 0) {
                orderManagementFacadeInterface.applyPriceFactor(1.0D / baseSymbol.getPriceRatio(), order);
            } else {
                logger.warn("Base Symbol price ratio is less than or equal to zero - {}", order.getSymbol());
            }
        }
        return order;
    }

    /**
     * This method will apply the KSE price factor to the order
     *
     * @param order is the order bean
     * @return the order bean
     * @throws OrderException
     */
    public Order applyKSEPriceFactor(Order order) throws OrderException {
        if (order.getExchange().trim().equalsIgnoreCase(ExchangeCodes.KSE)) {
            orderManagementFacadeInterface.applyPriceFactor(KSE_PRICE_FACTOR_CONST, order);
        }
        return order;
    }
}
