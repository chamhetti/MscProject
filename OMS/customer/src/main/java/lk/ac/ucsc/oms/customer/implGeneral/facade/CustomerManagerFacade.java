package lk.ac.ucsc.oms.customer.implGeneral.facade;

import lk.ac.ucsc.oms.customer.api.CustomerManager;
import lk.ac.ucsc.oms.customer.api.beans.CustomerStatusMessages;
import lk.ac.ucsc.oms.customer.api.beans.customer.Customer;
import lk.ac.ucsc.oms.customer.api.beans.customer.LoginProfile;
import lk.ac.ucsc.oms.customer.api.beans.customer.PersonalProfile;
import lk.ac.ucsc.oms.customer.api.exceptions.CustomerException;
import lk.ac.ucsc.oms.customer.implGeneral.beans.customer.CustomerBean;
import lk.ac.ucsc.oms.customer.implGeneral.beans.customer.LoginProfileBean;
import lk.ac.ucsc.oms.customer.implGeneral.beans.customer.PersonalProfileBean;
import lk.ac.ucsc.oms.customer.implGeneral.facade.cache.CustomerCacheFacade;
import lk.ac.ucsc.oms.sequence_generator.api.AbstractSequenceGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class CustomerManagerFacade implements CustomerManager {
    private static Logger logger = LogManager.getLogger(CustomerManagerFacade.class);

    private CustomerCacheFacade customerCacheFacade;
    private AbstractSequenceGenerator sequenceGenerator;


    @Override
    public void initialize() throws CustomerException {
        customerCacheFacade.initialize();
    }


    @Override
    public Customer getCustomerByCustomerNumber(String customerNumber) throws CustomerException {
        logger.info("Getting the customer with customer number - {}", customerNumber);
        if (customerNumber == null || "".equalsIgnoreCase(customerNumber)) {
            throw new CustomerException("Customer Number Can't be Null or Empty" + CustomerStatusMessages.CUSTOMER_NUMBER_EMPTY);
        }
        return customerCacheFacade.getCustomer(customerNumber);
    }

    @Override
    public Customer getCustomerByCustomerId(long customerId) throws CustomerException {
        logger.info("Getting the customer with customer Id - {}", customerId);
        if (customerId < 0) {
            throw new CustomerException("Customer Id Can't be negative" + CustomerStatusMessages.CUSTOMER_NUMBER_EMPTY);
        }
        return customerCacheFacade.getCustomerByField("customerId", String.valueOf(customerId));
    }

    @Override
    public Customer getCustomerByLoginNameOrAlias(String loginName) throws CustomerException {
        logger.info("Getting the customer with login name - {}", loginName);
        if (loginName == null || "".equalsIgnoreCase(loginName)) {
            throw new CustomerException("Customer Login Name Can't be Null or Empty" + CustomerStatusMessages.CUSTOMER_NUMBER_EMPTY);
        }

        try {
            return customerCacheFacade.getCustomerByLoginNameOrAlias(loginName);
        } catch (CustomerException e) {
            logger.debug("Error in getting customer using login name or alias", e);
            throw new CustomerException("Error in getting customer using login name or alias", e);
        }

    }

    @Override
    public long addCustomer(Customer customer) throws CustomerException {
        logger.info("Adding the customer -{} to cache", customer);
        if (customer == null) {
            throw new CustomerException("Customer can't be null");
        }
        validateCustomer(customer);
        try {
            logger.debug("Getting the customer id from sequence generator");
            customer.setCustomerId(Long.parseLong(sequenceGenerator.getSequenceNumber()));
            logger.info("customer Id of the new customer {}", customer.getCustomerId());
        } catch (Exception e) {
            logger.error("Error in getting customer Id using sequence generator", e);
            throw new CustomerException("Problem in Getting Sequence Number", e);
        }
        customer.getLoginProfile().setCustomerId(customer.getCustomerId());
        customer.getPersonalProfile().setCustomerId(customer.getCustomerId());

        customerCacheFacade.putCustomer((CustomerBean) customer);
        logger.debug("Adding customer process finished");
        return customer.getCustomerId();
    }

    @Override
    public void updateCustomer(Customer customer) throws CustomerException {
        logger.info("Update the customer {}", customer);
        if (customer == null) {
            throw new CustomerException("customer can't be null");
        }
        validateCustomer(customer);
        customerCacheFacade.updateCustomer(customer);
    }

    @Override
    public void markCustomerAsDeleted(String customerNumber) throws CustomerException {
        logger.info("markCustomerAsDeleted : customerNumber : ", customerNumber);
        if (customerNumber == null || "".equalsIgnoreCase(customerNumber)) {
            throw new CustomerException(CustomerStatusMessages.CUSTOMER_NUMBER_EMPTY.name());
        }
        customerCacheFacade.markCustomerAsDeleted(customerNumber);
    }

    @Override
    public List<Customer> getAllCustomers() throws CustomerException {
        logger.info("Getting All the Customers");
        return customerCacheFacade.getAllCustomers();
    }

    @Override
    public Customer getCustomerDetails(String dealerId, String customerNumber, boolean isGlobalDealer) throws CustomerException {
        if (customerNumber == null || "".equals(customerNumber)) {
            throw new CustomerException("Customer Number can't be null or empty - {}");
        }
        Customer customer;
        customer = customerCacheFacade.getCustomer(customerNumber);
        return customer;

    }

    @Override
    public Customer getEmptyCustomer(String customerNumber) {
        return new CustomerBean(customerNumber);
    }

    @Override
    public PersonalProfile getEmptyPersonalProfile() {
        return new PersonalProfileBean();
    }

    @Override
    public LoginProfile getEmptyLoginProfile() {
        return new LoginProfileBean();
    }


    @Override
    public List<Customer> getFilteredCustomers(List<String> institutionList) throws CustomerException {
        return customerCacheFacade.getFilteredCustomers(institutionList);
    }


    private void validateCustomer(Customer customer) throws CustomerException {
        if (customer.getCustomerNumber() == null || "".equals(customer.getCustomerNumber())) {
            throw new CustomerException("Customer Number Can't be Null or Empty" + CustomerStatusMessages.CUSTOMER_NUMBER_EMPTY);
        }
    }

    public void setCustomerCacheFacade(CustomerCacheFacade customerCacheFacade) {
        this.customerCacheFacade = customerCacheFacade;
    }

    public void setSequenceGenerator(AbstractSequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }
}
