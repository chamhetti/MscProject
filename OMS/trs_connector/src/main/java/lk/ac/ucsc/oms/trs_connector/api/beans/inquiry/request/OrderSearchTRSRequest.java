package lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.request;

import lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.common.PaginationTRS;

/**
 * User: vimalanathanr
 * Date: 8/26/13
 * Time: 10:16 AM
 */
public interface OrderSearchTRSRequest {

    String getAccountNumber();

    void setAccountNumber(String accountNumber);

    String getStartDate();

    void setStartDate(String startDate);

    String getEndDate();

    void setEndDate(String endDate);

    int getOrderSide();

    void setOrderSide(int orderSide);

    String getOrderStatus();

    void setOrderStatus(String orderStatus);

    String getSymbol();

    void setSymbol(String symbol);

    String getClOrderID();

    void setClOrderID(String clOrderID);

    String getExchange();

    void setExchange(String exchange);

    PaginationTRS getPaging();

    void setPaging(PaginationTRS paging);

    int getOrdCategory();

    void setOrdCategory(int ordCategory);

    String getOrdCategoryStr();

    void setOrdCategoryStr(String ordCategoryStr);

}
