package lk.ac.ucsc.oms.customer.implGeneral.persistantImpl;

import lk.ac.ucsc.oms.cache.api.CachePersister;
import lk.ac.ucsc.oms.customer.api.beans.CustomerSearchType;
import lk.ac.ucsc.oms.customer.api.beans.customer.Customer;
import lk.ac.ucsc.oms.customer.api.exceptions.CustomerException;
import lk.ac.ucsc.oms.customer.implGeneral.beans.customer.CustomerBean;

import java.util.List;


public interface CustomerPersister extends CachePersister {


    void deleteFromDB(CustomerBean customer) throws CustomerException;

    String getLastCustomerId() throws CustomerException;

    CustomerBean loadByCustomerNumber(String customerNumber) throws CustomerException;

    List<Customer> getCustomerList(CustomerSearchType customerSearchType, String value) throws CustomerException;


}
