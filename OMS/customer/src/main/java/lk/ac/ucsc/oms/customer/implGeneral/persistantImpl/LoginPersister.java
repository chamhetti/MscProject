package lk.ac.ucsc.oms.customer.implGeneral.persistantImpl;

import lk.ac.ucsc.oms.customer.api.exceptions.CustomerLoginException;


public interface LoginPersister {
    String getCustomerNumber(String loginName) throws CustomerLoginException;
}
