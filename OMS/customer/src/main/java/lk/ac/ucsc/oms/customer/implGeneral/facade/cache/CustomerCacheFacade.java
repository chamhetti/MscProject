package lk.ac.ucsc.oms.customer.implGeneral.facade.cache;

import lk.ac.ucsc.oms.cache.api.CacheControllerInterface;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheException;
import lk.ac.ucsc.oms.cache.api.exceptions.CacheNotFoundException;
import lk.ac.ucsc.oms.common_utility.api.enums.CacheLoadedState;
import lk.ac.ucsc.oms.common_utility.api.enums.RecordStatus;
import lk.ac.ucsc.oms.customer.api.beans.CustomerSearchType;
import lk.ac.ucsc.oms.customer.api.beans.customer.Customer;
import lk.ac.ucsc.oms.customer.api.exceptions.CustomerException;
import lk.ac.ucsc.oms.customer.api.exceptions.CustomerLoginException;
import lk.ac.ucsc.oms.customer.implGeneral.beans.customer.CustomerBean;
import lk.ac.ucsc.oms.customer.implGeneral.persistantImpl.CustomerPersister;
import lk.ac.ucsc.oms.customer.implGeneral.persistantImpl.LoginPersister;
import lk.ac.ucsc.oms.sequence_generator.api.AbstractSequenceGenerator;
import lk.ac.ucsc.oms.system_configuration.api.SysConfigException;
import lk.ac.ucsc.oms.system_configuration.api.SysLevelParaManager;
import lk.ac.ucsc.oms.system_configuration.api.SystemParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class CustomerCacheFacade {
    private static Logger logger = LogManager.getLogger(CustomerCacheFacade.class);

    private CacheControllerInterface cacheController;
    private CustomerPersister customerPersister;
    private LoginPersister loginPersister;
    private CacheLoadedState cacheLoadedState;
    private SysLevelParaManager sysLevelParaManager;
    AbstractSequenceGenerator sequenceGenerator;

    public void setCacheController(CacheControllerInterface cacheController) {
        this.cacheController = cacheController;
    }

    public void setCustomerPersister(CustomerPersister customerPersister) {
        this.customerPersister = customerPersister;
    }
    public void setLoginPersister(LoginPersister loginPersister) {
        this.loginPersister = loginPersister;
    }

    public void setCacheLoadedState(CacheLoadedState cacheLoadedState) {
        this.cacheLoadedState = cacheLoadedState;
    }

    public void setSysLevelParaManager(SysLevelParaManager sysLevelParaManager) {
        this.sysLevelParaManager = sysLevelParaManager;
    }

    public void setSequenceGenerator(AbstractSequenceGenerator sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }


    public void initialize() throws CustomerException {
        //get the last id of the customer table and set it to sequence generator for id generation
        sequenceGenerator.setSequenceNumber(customerPersister.getLastCustomerId());
        //load all customers for cache base on cache level configuration
        loadAllCustomer();
        //set the cache on/off base on system level config
        try {
            String isCacheOff = sysLevelParaManager.getSysLevelParameter(SystemParameter.IS_CACHE_WRITE_THROUGH).getParaValue();
            if (isCacheOff != null && "true".equalsIgnoreCase(isCacheOff.trim())) {
                cacheController.setCacheWriteThrough(true);
            }
        } catch (SysConfigException e) {
            logger.warn("System level parameter not set", e);
        }
    }

    private void loadAllCustomer() throws CustomerException {
        logger.info("Loading all customers into cache");
        try {
            if (cacheLoadedState == CacheLoadedState.FULLY_LOADED) {
                cacheController.populateFullCache();
            }
        } catch (CacheException e) {
            logger.error("Error in loading all customers: ", e);
            throw new CustomerException("Issue occurred in loading all customers", e);
        }
    }

    private void persistCustomerCacheChanges() throws CustomerException {
        logger.info("Persisting customer cache objects");
        try {
            cacheController.persistCache();
        } catch (Exception e) {
            logger.error("Error in Persisting Customer Cache", e);
            throw new CustomerException("Error in Persisting Customer Cache", e);
        }
    }

    public List<Customer> getAllCustomers() throws CustomerException {
        List<CacheObject> cacheObjects;
        List<Customer> customers = new ArrayList<Customer>();
        //If cache is fully loaded, then get all customers from cache, else persist cache changes and get all customers from physical storage
        if (cacheLoadedState == CacheLoadedState.FULLY_LOADED) {
            try {
                cacheObjects = cacheController.getAllCache();
            } catch (CacheNotFoundException e) {
                logger.error("Error in loading all customers from cache: ", e);
                throw new CustomerException("Issue occurred in loading all customers from cache", e);
            }
        } else {
            persistCustomerCacheChanges();
            cacheObjects = customerPersister.loadAll();
        }
        for (CacheObject co : cacheObjects) {
            customers.add((CustomerBean) co);
        }
        return customers;
    }

    public List<Customer> getFilteredCustomers(List<String> institutionList) throws CustomerException {
        List<CacheObject> cacheObjects;
        List<Customer> customers = new ArrayList<Customer>();
        //If cache is fully loaded, then get all customers from cache, else persist cache changes and get all customers from physical storage
        if (cacheLoadedState == CacheLoadedState.FULLY_LOADED) {
            try {
                cacheObjects = new ArrayList<>();
                for (String institutionCode : institutionList) {
                    cacheObjects.addAll(cacheController.searchFromCache("institutionCode", institutionCode, CustomerBean.class));
                }
            } catch (CacheException e) {
                logger.error("Error in loading filtered customers from cache: ", e);
                throw new CustomerException("Issue occurred in loading filtered customers from cache", e);
            }
        } else {
            persistCustomerCacheChanges();
            for (String institutionCode : institutionList) {
                customers.addAll(customerPersister.getCustomerList(CustomerSearchType.INST_CODE, institutionCode));
            }
            return customers;
        }
        for (CacheObject co : cacheObjects) {
            customers.add((CustomerBean) co);
        }

        return customers;
    }

    public void putCustomer(CustomerBean customerBean) throws CustomerException {
        customerBean.setPrimaryKeyObject(customerBean.getCustomerNumber());
        try {
            cacheController.addToCache(customerBean);
        } catch (CacheNotFoundException e) {
            logger.error("Cannot add to Cache.Error in Cache !!", e);
            throw new CustomerException("Error in adding the Customer", e);
        }
    }

    public void updateCustomer(Customer customer) throws CustomerException {
        CustomerBean customerBean = (CustomerBean) customer;
        customerBean.setPrimaryKeyObject(customer.getCustomerNumber());
        try {
            if (cacheLoadedState == CacheLoadedState.PARTIALITY_LOADED) {
                getCustomer(customer.getCustomerNumber());
            }
            cacheController.modifyInCache(customerBean);
        } catch (CacheNotFoundException e) {
            logger.error("Cannot Update to Cache.Error in Cache !! ", e);
            throw new CustomerException("Error in updating the customer", e);
        }
    }

    public void markCustomerAsDeleted(String customerNumber) throws CustomerException {
        CustomerBean customerBean = new CustomerBean(customerNumber);
        customerBean.setPrimaryKeyObject(customerBean.getCustomerNumber());
        try {
            CustomerBean b = (CustomerBean) cacheController.readObjectFromCache(customerBean);
            b.setStatus(RecordStatus.DELETED);
            cacheController.modifyInCache(b);
        } catch (Exception e) {
            logger.error("Cannot remove from Cache.Cache name: " + " '", e);
            throw new CustomerException("Error in removing the customer", e);
        }
    }

    public CustomerBean getCustomer(String customerNumber) throws CustomerException {
        CustomerBean customerBean = new CustomerBean(customerNumber);
        customerBean.setPrimaryKeyObject(customerNumber);
        CacheObject co;
        try {
            co = cacheController.readObjectFromCache(customerBean);
            return (CustomerBean) co;
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw new CustomerException("Error in getting customer", e);
        }
    }

    public CustomerBean getCustomerByField(String field, String value) throws CustomerException {
        CacheObject co;
        List<CacheObject> coList = null;
        logger.info("Getting the customer with field -{} having value -{}", field, value);
        try {
            coList = cacheController.searchFromCache(field, value, CustomerBean.class);
        } catch (Exception e) {
            logger.error("Problem in seaching the cache", e);
            logger.info("Customer with give criteria does not found");
            throw new CustomerException("Error in getting customer by given field", e);
        }
        if (coList != null && !coList.isEmpty()) {
            co = coList.get(0);
            return (CustomerBean) co;
        } else {
            throw new CustomerException("Customer Not Found for Given Criteria");
        }
    }

    public Customer getCustomerByLoginNameOrAlias(String loginName) throws CustomerException {
        //assume login name is a customer number and check whether customer already in the cache
        Customer customer = null;
        try {
            customer = getCustomer(loginName);
        } catch (Exception e) {
            logger.debug("customer not found from loginname");
        }
        if (customer != null) {
            return customer;
        } else {
            logger.debug("No customer with login name as customer number");
            //login name can be login alias. Search customer cache for the customer with this login alias
            try {
                customer = getCustomerByField("loginProfile.loginAlias", loginName);
            } catch (Exception e) {
                logger.debug("customer not found by loginProfile.loginAlias");
            }
            if (customer != null) {
                return customer;
            } else {
                logger.debug("customer not in the cache and hence loading from DB");
                //getting the customer number from DB using the login alias and loading the customer from DB to
                //cache and return
                String ourCustomerNumber = null;
                try {
                    ourCustomerNumber = loginPersister.getCustomerNumber(loginName);
                    return getCustomer(ourCustomerNumber);
                } catch (CustomerLoginException e) {
                    throw new CustomerException("Customer number not found for given login name", e);
                }
            }
        }
    }

}
