package lk.ac.ucsc.oms.customer.api;

import lk.ac.ucsc.oms.common_utility.api.enums.PropertyEnable;
import lk.ac.ucsc.oms.customer.api.beans.CustomerValidationReply;
import lk.ac.ucsc.oms.customer.api.exceptions.CustomerValidationException;

public interface CustomerValidator {

    CustomerValidationReply hasValidAccount(String accountNumber) throws CustomerValidationException;

    CustomerValidationReply hasValidExchangeAccount(String accountNumber, String exchangeCode) throws CustomerValidationException;

    CustomerValidationReply hasValidCashAccount(String cashAccountNumber) throws CustomerValidationException;

    boolean isShariaCompliantAccount(String accountNumber, String exchangeCode) throws CustomerValidationException;

    boolean isRestrictedSymbolForCustomer(String accountNumber, String symbolCode) throws CustomerValidationException;

}
