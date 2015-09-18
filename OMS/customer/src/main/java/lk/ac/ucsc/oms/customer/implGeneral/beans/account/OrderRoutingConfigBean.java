package lk.ac.ucsc.oms.customer.implGeneral.beans.account;

import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.common_utility.api.enums.PropertyEnable;
import lk.ac.ucsc.oms.customer.api.beans.account.OrderRoutingConfig;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;


@Indexed
public class OrderRoutingConfigBean extends CacheObject implements OrderRoutingConfig {
    @Id
    @Field
    private long accId;
    @Field
    private PropertyEnable allowConditionalOrder;
    @Field
    private PropertyEnable allowBracketOrder;
    @Field
    private PropertyEnable allowAlgoOrder;
    @Field
    private PropertyEnable shariaComplient;
    @Field
    private String restrictedSymbol;

    @Override
    public PropertyEnable getShariaComplient() {
        return shariaComplient;
    }

    @Override
    public void setShariaComplient(PropertyEnable shariaComplient) {
        this.shariaComplient = shariaComplient;
    }

    @Override
    public PropertyEnable getAllowAlgoOrder() {
        return allowAlgoOrder;
    }

    @Override
    public void setAllowAlgoOrder(PropertyEnable allowAlgoOrder) {
        this.allowAlgoOrder = allowAlgoOrder;
    }

    @Override
    public PropertyEnable getAllowBracketOrder() {
        return allowBracketOrder;
    }

    @Override
    public void setAllowBracketOrder(PropertyEnable allowBracketOrder) {
        this.allowBracketOrder = allowBracketOrder;
    }

    @Override
    public PropertyEnable getAllowConditionalOrder() {
        return allowConditionalOrder;
    }

    @Override
    public void setAllowConditionalOrder(PropertyEnable allowConditionalOrder) {
        this.allowConditionalOrder = allowConditionalOrder;
    }

    @Override
    public Long getAccId() {
        return accId;
    }

    @Override
    public void setAccId(Long accId) {
        this.accId = accId;
    }

    @Override
    public String getRestrictedSymbol() {
        return restrictedSymbol;
    }

    @Override
    public void setRestrictedSymbol(String restrictedSymbol) {
        this.restrictedSymbol = restrictedSymbol;
    }

    @Override
    public List<String> getRestrictedSymbolList() {
        List<String> symList = new ArrayList<>();
        if (restrictedSymbol != null && !restrictedSymbol.isEmpty()) {
            for (String symbol : restrictedSymbol.split(",")) {
                symList.add(symbol);
            }
        }
        return symList;
    }

    @Override
    public void setRestrictedSymbolList(List<String> symbolList) {
        StringBuilder symbols = new StringBuilder("");
        if (!symbolList.isEmpty()) {
            for (String symbol : symbolList) {
                symbols.append(symbol).append(',');
            }
            this.restrictedSymbol = symbols.substring(0, symbols.length() - 1);
        } else {
            this.restrictedSymbol = symbols.toString();
        }
    }

}
