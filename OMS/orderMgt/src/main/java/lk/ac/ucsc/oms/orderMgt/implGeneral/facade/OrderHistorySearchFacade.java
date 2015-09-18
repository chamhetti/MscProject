package lk.ac.ucsc.oms.orderMgt.implGeneral.facade;

import lk.ac.ucsc.oms.orderMgt.api.OrderHistorySearchFacadeInterface;
import lk.ac.ucsc.oms.orderMgt.api.OrderSearchType;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.InvalidOrderException;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.OrderException;
import lk.ac.ucsc.oms.orderMgt.implGeneral.facade.cache.OrderCacheFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;


public class OrderHistorySearchFacade implements OrderHistorySearchFacadeInterface {
    private static Logger logger = LogManager.getLogger(OrderHistorySearchFacade.class);

    private OrderCacheFacade orderCacheFacade;

    public void setOrderCacheFacade(OrderCacheFacade orderCacheFacade) {
        this.orderCacheFacade = orderCacheFacade;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> searchOrders(OrderSearchType searchType, String value) throws OrderException {
        logger.info("Search Orders for given SearchType : " + searchType + " equals given Value : " + value);
        if (searchType == null || value == null || "".equals(value)) {
            logger.error("Search Type or value not set In Request");
            throw new InvalidOrderException("Search Type or value not set In Request");
        }

        return orderCacheFacade.filterOrderHistoryByFieldAndValue(searchType, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> searchOrdersForAllGivenTypeValues(Map<OrderSearchType, String> searchTypeValue) throws OrderException {
        logger.info("Search Orders for given Search Criteria : ", searchTypeValue);
        if (searchTypeValue == null) {
            logger.error("searchTypeValue Map not set In Request");
            throw new InvalidOrderException("searchTypeValue Map not set In Request");
        }
        return orderCacheFacade.filterOrderHistoryForAllGivenTypeValues(searchTypeValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> searchOrdersForEitherGivenTypeValues(Map<OrderSearchType, String> searchTypeValue) throws OrderException {
        logger.info("Search Orders for given Search Criteria : ", searchTypeValue);
        if (searchTypeValue == null) {
            logger.error("searchTypeValue Map not set In Request");
            throw new InvalidOrderException("searchTypeValue Map not set In Request");
        }
        return orderCacheFacade.filterOrderHistoryForEitherGivenTypeValues(searchTypeValue);
    }

}
