package lk.ac.ucsc.oms.customer.api;

import lk.ac.ucsc.oms.customer.api.beans.customer.Customer;
import lk.ac.ucsc.oms.customer.api.beans.customer.LoginProfile;
import lk.ac.ucsc.oms.customer.api.beans.customer.PersonalProfile;
import lk.ac.ucsc.oms.customer.api.exceptions.CustomerException;

import java.util.List;

public interface CustomerManager {

    void initialize() throws CustomerException;

    Customer getCustomerByCustomerNumber(String customerNumber) throws CustomerException;

    Customer getCustomerByCustomerId(long customerId) throws CustomerException;

    Customer getCustomerByLoginNameOrAlias(String loginName) throws CustomerException;

    long addCustomer(Customer customer) throws CustomerException;

    void updateCustomer(Customer customer) throws CustomerException;

    void markCustomerAsDeleted(String customerNumber) throws CustomerException;

    List<Customer> getAllCustomers() throws CustomerException;

    Customer getCustomerDetails(String dealerId, String customerNumber, boolean isGlobalDealer) throws CustomerException;

    Customer getEmptyCustomer(String customerNumber);

    PersonalProfile getEmptyPersonalProfile();

    LoginProfile getEmptyLoginProfile();

    List<Customer> getFilteredCustomers(List<String> institutionList) throws CustomerException;
}

