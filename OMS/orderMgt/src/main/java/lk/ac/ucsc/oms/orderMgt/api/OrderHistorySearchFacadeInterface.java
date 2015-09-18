package lk.ac.ucsc.oms.orderMgt.api;

import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.OrderException;

import java.util.List;
import java.util.Map;

/**
 * This is the service interface used to search order history information in order management module.
 *
 * @author Kalana
 *
 */
public interface OrderHistorySearchFacadeInterface {

    /**
     * search From order History table for given value equal to given search type
     *
     * @param searchType field to be searched
     * @param value value of the field
     * @return History Order List
     */
    List<Order> searchOrders(OrderSearchType searchType, String value) throws OrderException;

    /**
     * search From order History table  for given value equal to given  all search types  set in Map
     * Based on SearchType1=value1 && SearchType2=value2 && SearchType3=value3 &&....
     *
     * @param searchTypeValue map for search
     * @return History Order List
     */
    List<Order> searchOrdersForAllGivenTypeValues(Map<OrderSearchType, String> searchTypeValue) throws OrderException;

    /**
     * search From order History table  for given  either value equal to given  search types  set in Map
     * Based on SearchType1=value1 || SearchType2=value2 || SearchType3=value3 ||...   search types and values set in map
     *
     * @param searchTypeValue map for search
     * @return History Order List
     */
    List<Order> searchOrdersForEitherGivenTypeValues(Map<OrderSearchType, String> searchTypeValue) throws OrderException;

}
