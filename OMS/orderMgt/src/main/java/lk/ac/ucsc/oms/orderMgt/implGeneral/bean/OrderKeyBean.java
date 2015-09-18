package lk.ac.ucsc.oms.orderMgt.implGeneral.bean;

import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.orderMgt.api.beans.OrderKey;

public class OrderKeyBean extends CacheObject implements OrderKey {
    private String clOrdID;
    private String remoteClOrdID;
    private final static int HASH_CODE = 31;


    /**
     * {@inheritDoc}
     */
    @Override
    public String getClOrdID() {
        return clOrdID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setClOrdID(String clOrdID) {
        this.clOrdID = clOrdID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRemoteClOrdID() {
        return remoteClOrdID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRemoteClOrdID(String remoteClOrdID) {
        this.remoteClOrdID = remoteClOrdID;
    }

    /**
     * Checks whether two OrderKeyBean objects are meaningfully equal or not
     *
     * @param o for comparison
     * @return true if equal false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderKeyBean)) {
            return false;
        }

        OrderKeyBean that = (OrderKeyBean) o;

        if (clOrdID != null ? !clOrdID.equals(that.clOrdID) : that.clOrdID != null) {
            return false;
        }
        return !(remoteClOrdID != null ? !remoteClOrdID.equals(that.remoteClOrdID) : that.remoteClOrdID != null);

    }

    /**
     * Gives a hash code value for a OrderKeyBean object
     * It uses clOrdId and remoteClOrdId to calculate hash code value
     *
     * @return hashCode
     */
    @Override
    public int hashCode() {
        int result = clOrdID != null ? clOrdID.hashCode() : 0;
        result = HASH_CODE * result + (remoteClOrdID != null ? remoteClOrdID.hashCode() : 0);
        return result;
    }

    /**
     * Provides a string representation for a OrderKeyBean object
     *
     * @return string representation
     */
    @Override
    public String toString() {
        return "OrderKeyBean{" +
                "clOrdID='" + clOrdID + '\'' +
                ", remoteClOrdID='" + remoteClOrdID + '\'' +
                '}';
    }
}
