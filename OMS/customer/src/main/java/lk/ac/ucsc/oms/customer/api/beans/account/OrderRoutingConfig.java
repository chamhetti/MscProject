package lk.ac.ucsc.oms.customer.api.beans.account;

import lk.ac.ucsc.oms.common_utility.api.enums.PropertyEnable;

import java.util.List;


public interface OrderRoutingConfig {

    PropertyEnable getShariaComplient();

    void setShariaComplient(PropertyEnable shariaComplient);

    PropertyEnable getAllowAlgoOrder();

    void setAllowAlgoOrder(PropertyEnable allowAlgoOrder);

    PropertyEnable getAllowBracketOrder();

    void setAllowBracketOrder(PropertyEnable allowBracketOrder);

    PropertyEnable getAllowConditionalOrder();

    void setAllowConditionalOrder(PropertyEnable allowConditionalOrder);

    Long getAccId();

    void setAccId(Long accId);

    String getRestrictedSymbol();

    void setRestrictedSymbol(String restrictedSymbol);

    List<String> getRestrictedSymbolList();

    void setRestrictedSymbolList(List<String> symbolList);
}
