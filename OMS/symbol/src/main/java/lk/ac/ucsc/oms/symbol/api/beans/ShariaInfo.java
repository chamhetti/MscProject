package lk.ac.ucsc.oms.symbol.api.beans;


import lk.ac.ucsc.oms.common_utility.api.enums.PropertyEnable;

/**
 * This is the interface gives the sharia enabled or disabled status for a symbol in an institution.
 *
 * User: Hetti
 * Date: 11/22/12
 * Time: 11:23 AM
 */
public interface ShariaInfo {
    /**
     * get the symbol id
     * @return symbol id unique log value
     */
    long getSymbolId();

    /**
     * set the symbol id
     * @param symbolId  id of the symbol
     */
    void setSymbolId(long symbolId);

    /**
     * get sharia enable or not
     * @return boolean value of sharia enable or not
     */
    boolean isShariaEnable();

    /**
     * set sharia enable 1- enable 0 -not enable
     * @param shariaEnable enum for sharia enable or not
     */
    void setShariaEnableOrNot(PropertyEnable shariaEnable);

    /**
     * get the insid related to sharia info
     * @return int insid
     */
    int getInsId();

    /**
     * set the inst id, those sharia info only valid for set inst.
     * @param insId int insid
     */
    void setInsId(int insId);

}
