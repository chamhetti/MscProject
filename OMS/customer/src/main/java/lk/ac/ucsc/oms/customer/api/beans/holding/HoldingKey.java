package lk.ac.ucsc.oms.customer.api.beans.holding;

import lk.ac.ucsc.oms.common_utility.api.enums.HoldingTypes;

public interface HoldingKey {

    String getAccountNumber();

    void setAccountNumber(String accountNumber);

    String getSymbol();

    void setSymbol(String symbol);

    String getExchange();

    void setExchange(String exchange);

    String getCustomerNumber();

    void setCustomerNumber(String customerNumber);

    HoldingTypes getHoldingType();

    void setHoldingType(HoldingTypes holdingType);
}
