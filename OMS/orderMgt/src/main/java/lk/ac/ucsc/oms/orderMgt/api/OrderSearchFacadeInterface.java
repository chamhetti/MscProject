package lk.ac.ucsc.oms.orderMgt.api;

import lk.ac.ucsc.oms.common_utility.api.beans.SearchReply;
import lk.ac.ucsc.oms.common_utility.api.enums.OrderSide;
import lk.ac.ucsc.oms.orderMgt.api.beans.Execution;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.OrderException;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.OrderExecutionException;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface OrderSearchFacadeInterface {


    Order find(String clOrdId) throws OrderException;

    List<Order> searchOrders(int primarySearchFilter, String primarySearchValue, int secondarySearchFilter, String secondarySearchValue,
                             Date fromDate, Date toDate, int orderGroup, int searchMode) throws OrderException;


    SearchReply searchOrders(int primarySearchFilter, String primarySearchValue, int secondarySearchFilter, String secondarySearchValue,
                             Date fromDate, Date toDate, int orderGroup, int searchMode, String dealerId, boolean isGlobalDealer,
                             int startingSeqNumber, int maxPageWidth) throws OrderException;



    /**
     * get execution list with given client order id
     *
     * @param clOrdId for which executions should be retrieved
     * @return list {@inheritDoc}
     * @throws lk.ac.ucsc.oms.orderMgt.api.exceptions.OrderExecutionException
     * @throws OrderException
     */
    List<Execution> searchOrderExecutionsByClOrdId(String clOrdId) throws OrderExecutionException;



 }
