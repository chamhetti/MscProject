package lk.ac.ucsc.oms.orderMgt.implGeneral.persistantImpl;

import lk.ac.ucsc.oms.orderMgt.api.OrderSearchType;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.OrderException;

import java.util.List;
import java.util.Map;


public interface OrderHistoryPersister {

    /**
     * search From order History table  for given value equal to given search type
     *
     * @param searchType field to be searched
     * @param value of the field
     * @return OrderList
     */
    List<Order> filterOrdersByFieldAndValue(OrderSearchType searchType, String value) throws OrderException;

    /**
     * search From Order History table  for given value equal to given  all search types  set in Map
     * Based on SearchType1=value1 && SearchType2=value2 && SearchType3=value3 &&....
     *
     * @param criteriaMap for search
     * @return OrderList
     */
    List<Order> filterOrdersForAllGivenTypeValues(Map<OrderSearchType, String> criteriaMap) throws OrderException;

    /**
     * search From order History table  for given  either value equal to given  search types  set in Map
     * Based on SearchType1=value1 || SearchType2=value2 || SearchType3=value3 ||...   search types and values set in map
     *
     * @param criteriaMap for search
     * @return OrderList
     */
    List<Order> filterOrdersForEitherGivenTypeValues(Map<OrderSearchType, String> criteriaMap) throws OrderException;

}
