package lk.ac.ucsc.oms.orderMgt.api.beans;

/**
 * @author Chathura
 * Date: 3/15/13
 */

/**
 * This is the service API of the order key beans in the order key cache.
 * This order key is used to retrieve order using remoteClOrdId or get clOrdId for a give remoteClOrdId
 */
public interface OrderKey {

    /**
     * get clOrdId of the order key
     *
     * @return clOrdID
     */
    String getClOrdID();

    /**
     * set clOrdId of the order key
     *
     * @param clOrdID of the order key
     */
    void setClOrdID(String clOrdID);

    /**
     * get remote clOrdId of the order key
     *
     * @return remoteClOrdId of order key
     */
    String getRemoteClOrdID();

    /**
     * set remote clOrdId of the order key
     *
     * @param remoteClOrdID of the order key
     */
    void setRemoteClOrdID(String remoteClOrdID);

}
