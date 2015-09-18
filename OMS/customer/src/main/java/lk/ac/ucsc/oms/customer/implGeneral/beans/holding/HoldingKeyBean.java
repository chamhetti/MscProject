package lk.ac.ucsc.oms.customer.implGeneral.beans.holding;

import lk.ac.ucsc.oms.common_utility.api.enums.HoldingTypes;
import lk.ac.ucsc.oms.customer.api.beans.holding.HoldingKey;
import lk.ac.ucsc.oms.customer.api.exceptions.HoldingManagementException;
import org.hibernate.search.annotations.Field;

import java.io.Serializable;

public class HoldingKeyBean implements Serializable, HoldingKey {
    @Field
    private String customerNumber;
    @Field
    private String exchange;
    @Field
    private String symbol;
    @Field
    private String accountNumber;
    @Field
    private HoldingTypes holdingTypes =HoldingTypes.LONG;

    private static final int HASH_CODE_GENERATE_MULTIPLIER = 31;

    public HoldingKeyBean(String customerNumber, String exchange, String symbol, String accountNumber) throws HoldingManagementException {
        if (customerNumber == null || "".equals(customerNumber) || exchange == null || "".equals(exchange) ||
                symbol == null || "".equals(symbol) || accountNumber == null || "".equals(accountNumber)) {
            throw new HoldingManagementException("Parameters to holding key can't be null or empty");
        }
        this.customerNumber = customerNumber;
        this.exchange = exchange;
        this.symbol = symbol;
        this.accountNumber = accountNumber;
        this.holdingTypes = HoldingTypes.LONG;
    }

    public HoldingKeyBean(String customerNumber, String exchange, String symbol, String accountNumber, HoldingTypes holdingTypes) {
        this.customerNumber = customerNumber;
        this.exchange = exchange;
        this.symbol = symbol;
        this.accountNumber = accountNumber;
        this.holdingTypes = holdingTypes;
    }

    protected HoldingKeyBean() {
    }


    @Override
    public String getAccountNumber() {
        return accountNumber;
    }

    @Override
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String getExchange() {
        return exchange;
    }

    @Override
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    @Override
    public String getCustomerNumber() {
        return customerNumber;
    }

    @Override
    public void setCustomerNumber(String customerNumber1) {
        this.customerNumber = customerNumber1;
    }

    @Override
    public HoldingTypes getHoldingType() {
        return holdingTypes;
    }

    @Override
    public void setHoldingType(HoldingTypes holdingType) {
        this.holdingTypes = holdingType;
    }


}
