package lk.ac.ucsc.oms.symbol.api.beans;

/**
 * Object use to store and get the restricted information at institution wise for a symbol
 * <p/>
 * User: Hetti
 * Date: 11/22/12
 * Time: 11:22 AM
 */
public interface BlackListInfo {
    /**
     * get the symbol id
     *
     * @return symbol id unique log value
     */
    long getSymbolId();

    /**
     * set symbol id
     *
     * @return void
     */

    void setSymbolId(long symbolId);

    /**
     * get restricted or not
     * 1- Restricted
     * 0 -not restricted
     *
     * @return int
     */
    int getBlackListed();

    /**
     * get restricted or not
     * 1- Restricted
     * 0 -not restricted
     *
     * @param blackListed int
     */
    void setBlackListed(int blackListed);

    /**
     * set the institution id related to this restriction
     *
     * @param insId int
     */
    void setInsId(int insId);

    /**
     * inst id this list is applying
     *
     * @return int
     */
    int getInsId();
}
