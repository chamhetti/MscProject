package lk.ac.ucsc.oms.trs_connector.implGeneral.beans.inquiry.reply;

import java.util.ArrayList;
import java.util.List;


public class CustomersListReply {
    private List<Long> customersIdList = new ArrayList<Long>();

    public CustomersListReply() {
        customersIdList = new ArrayList<Long>();
    }

    public List<Long> getCustomersIdList() {
        return customersIdList;
    }

    public void setCustomersIdList(List<Long> customersIdList) {
        this.customersIdList = customersIdList;
    }

    public void addCustomer(long record) {
        this.customersIdList.add(record);
    }
}
