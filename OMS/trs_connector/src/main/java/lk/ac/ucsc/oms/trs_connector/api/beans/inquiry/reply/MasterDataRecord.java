package lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.reply;

/**
 * @author Chathura
 *         Date: Feb 18, 2013
 */
public interface MasterDataRecord {
    /**
     * @return
     */
    String getMasterDataRecordID();

    /**
     * @param masterDataRecordID is the master data record id
     */
    void setMasterDataRecordID(String masterDataRecordID);

    /**
     * @return
     */
    String getMasterDataRecordName();

    /**
     * @param masterDataRecordname is the master data record name
     */
    void setMasterDataRecordName(String masterDataRecordname);
}
