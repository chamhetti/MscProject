package lk.ac.ucsc.oms.trs_connector.implGeneral.beans.inquiry.reply;

/**
 * @author AmilaS
 *         Date: 12/3/13
 *         Time: 4:50 PM
 */
public class CustomerInfoTrsBean {

    private String customerId;
    private String institutionCode;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }
}
