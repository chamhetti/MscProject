package lk.ac.ucsc.oms.customer.api.beans.account;

import lk.ac.ucsc.oms.common_utility.api.enums.RecordStatus;

import java.util.Map;

public interface Account {

    Long getAccId();

    void setAccId(Long accId);

    String getAccountNumber();

    void setAccountNumber(String accountNumber);

    String getAccountName();

    void setAccountName(String accountName);

    RecordStatus getStatus();

    void setStatus(RecordStatus accountStatus);

    String getCustomerNumber();

    void setCustomerNumber(String customerNumber);

    String getCashAccNumber();

    void setCashAccNumber(String cashAccNumber);

    double getPendingOrderValue();

    void setPendingOrderValue(double pendingOrderValue);

    String getParentAccNumber();

    void setParentAccNumber(String parentAccId);

    OrderRoutingConfig getOrderRoutingConfig();

    void setOrderRoutingConfig(OrderRoutingConfig orderRoutingConfig);

    Map<String, ExchangeAccount> getExchangeAccountsList();

    void setExchangeAccountsList(Map<String, ExchangeAccount> exchangeAccountsList);

    String getInstituteCode();

    void setInstituteCode(String instituteCode);
}
