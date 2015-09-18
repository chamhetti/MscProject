package lk.ac.ucsc.oms.trs_connector.implGeneral.beans.inquiry.reply;


import java.util.List;


public class CustomerListByUserReply {

    private List<CustomerInfoTrsBean> inclusiveCustomers;
    private List<CustomerInfoTrsBean> exclusiveCustomers;
    private List<String> institutions;

    public List<CustomerInfoTrsBean> getInclusiveCustomers() {
        return inclusiveCustomers;
    }

    public void setInclusiveCustomers(List<CustomerInfoTrsBean> inclusiveCustomers) {
        this.inclusiveCustomers = inclusiveCustomers;
    }

    public List<CustomerInfoTrsBean> getExclusiveCustomers() {
        return exclusiveCustomers;
    }

    public void setExclusiveCustomers(List<CustomerInfoTrsBean> exclusiveCustomers) {
        this.exclusiveCustomers = exclusiveCustomers;
    }

    public List<String> getInstitutions() {
        return institutions;
    }

    public void setInstitutions(List<String> institutions) {
        this.institutions = institutions;
    }
}
