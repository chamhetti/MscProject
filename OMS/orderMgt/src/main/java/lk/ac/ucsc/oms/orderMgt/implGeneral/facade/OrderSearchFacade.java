package lk.ac.ucsc.oms.orderMgt.implGeneral.facade;

import lk.ac.ucsc.oms.common_utility.api.beans.SearchReply;
import lk.ac.ucsc.oms.common_utility.api.enums.OrderSide;
import lk.ac.ucsc.oms.orderMgt.api.OrderSearchFacadeInterface;
import lk.ac.ucsc.oms.orderMgt.api.beans.Execution;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.InvalidOrderException;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.OrderException;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.OrderExecutionException;
import lk.ac.ucsc.oms.orderMgt.implGeneral.facade.cache.ExecutionCacheFacade;
import lk.ac.ucsc.oms.orderMgt.implGeneral.facade.cache.OrderCacheFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class OrderSearchFacade implements OrderSearchFacadeInterface {
    private static Logger logger = LogManager.getLogger(OrderSearchFacade.class);

    private OrderCacheFacade orderCache;
    private ExecutionCacheFacade executionCache;

    public void setOrderCache(OrderCacheFacade orderCache) {
        this.orderCache = orderCache;
    }

    public void setExecutionCache(ExecutionCacheFacade executionCache) {
        this.executionCache = executionCache;
    }

    @Override
    public Order find(String clOrdId) throws OrderException {
        if (clOrdId == null || "".equals(clOrdId)) {
            logger.error("clOrdId Can't be null to find order");
            throw new InvalidOrderException("Client Order Id null or empty In Request");
        }
        return orderCache.findOrder(clOrdId);
    }


    @Override
    public List<Order> searchOrders(int primarySearchFilter, String primarySearchValue, int secondarySearchFilter, String secondarySearchValue,
                                    Date fromDate, Date toDate, int orderGroup, int searchMode) throws OrderException {
        int searchFilter = primarySearchFilter;
        String searchValue = primarySearchValue;
        String searchValueSecondary = secondarySearchValue;
        int searchFilterSecondary = secondarySearchFilter;

        if (primarySearchFilter < 1 && secondarySearchFilter >= 1) {
            searchFilter = secondarySearchFilter;
            searchValue = secondarySearchValue;
            searchFilterSecondary = -1;
            searchValueSecondary = null;
        }
        return orderCache.searchOrders(searchFilter, searchValue, searchFilterSecondary, searchValueSecondary,
                fromDate, toDate, orderGroup, searchMode);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public SearchReply searchOrders(int primarySearchFilter, String primarySearchValue, int secondarySearchFilter, String secondarySearchValue,
                                    Date fromDate, Date toDate, int orderGroup, int searchMode, String dealerId, boolean isGlobalDealer,
                                    int startingSeqNumber, int maxPageWidth) throws OrderException {
        int searchFilter = primarySearchFilter;
        String searchValue = primarySearchValue;
        String searchValueSecondary = secondarySearchValue;
        int searchFilterSecondary = secondarySearchFilter;

        if (primarySearchFilter < 1 && secondarySearchFilter >= 1) {
            searchFilter = secondarySearchFilter;
            searchValue = secondarySearchValue;
            searchFilterSecondary = -1;
            searchValueSecondary = null;
        }
        return orderCache.searchOrders(searchFilter, searchValue, searchFilterSecondary, searchValueSecondary,
                fromDate, toDate, orderGroup, searchMode, dealerId, isGlobalDealer, startingSeqNumber, maxPageWidth);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Execution> searchOrderExecutionsByClOrdId(String clOrdId) throws OrderExecutionException {
        if (clOrdId == null || "".equals(clOrdId)) {
            throw new OrderExecutionException("Order Number is null or empty In the Request");
        }
        return executionCache.getExecutionsByClOrdId(clOrdId);
    }

}
