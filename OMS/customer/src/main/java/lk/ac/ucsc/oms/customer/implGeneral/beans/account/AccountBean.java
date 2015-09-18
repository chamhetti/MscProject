package lk.ac.ucsc.oms.customer.implGeneral.beans.account;

import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.common_utility.api.enums.RecordStatus;
import lk.ac.ucsc.oms.customer.api.beans.account.*;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;


@Indexed
public class AccountBean extends CacheObject implements Account {
    @Id
    @Field
    private long accId;
    @Field
    private String accountNumber;
    @Field
    private String accountName;
    @Field
    private String parentAccNumber;
    @Field
    private String customerNumber;
    @Field
    private String cashAccNumber;
    @Field
    private double pendingOrderValue;
    @Field
    private RecordStatus status;
    @IndexedEmbedded
    private OrderRoutingConfigBean orderRoutingConfig = new OrderRoutingConfigBean();

    @IndexedEmbedded
    private Map<String, ExchangeAccount> exchangeAccountsList = new HashMap<String, ExchangeAccount>();
    @Field
    private String instituteCode;



    public AccountBean(String accountNumber, String customerNumber) {
        this.accountNumber = accountNumber;
        this.customerNumber = customerNumber;
    }

    public AccountBean(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    protected AccountBean() {
    }


    @Override
    public String getCustomerNumber() {
        return customerNumber;
    }


    @Override
    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }


    @Override
    public OrderRoutingConfig getOrderRoutingConfig() {
        return orderRoutingConfig;
    }


    @Override
    public void setOrderRoutingConfig(OrderRoutingConfig orderRoutingConfig) {
        this.orderRoutingConfig = (OrderRoutingConfigBean) orderRoutingConfig;
    }


    @Override
    public RecordStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(RecordStatus status) {
        this.status = status;
    }


    @Override
    public String getParentAccNumber() {
        return parentAccNumber;
    }


    @Override
    public void setParentAccNumber(String parentAccNumber) {
        this.parentAccNumber = parentAccNumber;
    }

    @Override
    public String getAccountName() {
        return accountName;
    }

    @Override
    public void setAccountName(String accountName) {
        this.accountName = accountName;
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
    public Long getAccId() {
        return accId;
    }

    public void setAccId(Long accId) {
        this.accId = accId;
    }

    @Override
    public Map<String, ExchangeAccount> getExchangeAccountsList() {
        return exchangeAccountsList;
    }

    @Override
    public void setExchangeAccountsList(Map<String, ExchangeAccount> exchangeAccountsList) {
        this.exchangeAccountsList = exchangeAccountsList;
    }
    @Override
    public String getCashAccNumber() {
        return cashAccNumber;
    }

    @Override
    public void setCashAccNumber(String cashAccNumber) {
        this.cashAccNumber = cashAccNumber;
    }

    @Override
    public double getPendingOrderValue() {
        return pendingOrderValue;
    }

    @Override
    public void setPendingOrderValue(double pendingOrderValue) {
        this.pendingOrderValue = pendingOrderValue;
    }

    @Override
    public String getInstituteCode() {
        return instituteCode;
    }

    @Override
    public void setInstituteCode(String instituteCode) {
        this.instituteCode = instituteCode;
    }


    @Override
    public String toString() {
        return "AccountBean{" +
                "accId=" + accId +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountName='" + accountName + '\'' +
                ", parentAccNumber='" + parentAccNumber + '\'' +
                ", customerNumber='" + customerNumber + '\'' +
                ", cashAccNumber='" + cashAccNumber + '\'' +
                ", pendingOrderValue=" + pendingOrderValue +
                ", status=" + status +
                ", orderRoutingConfig=" + orderRoutingConfig +
                ", exchangeAccountsList=" + exchangeAccountsList +
                ", instituteCode='" + instituteCode + '\'' +
                '}';
    }

}
