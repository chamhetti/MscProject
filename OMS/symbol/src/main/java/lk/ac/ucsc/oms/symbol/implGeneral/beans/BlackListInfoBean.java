package lk.ac.ucsc.oms.symbol.implGeneral.beans;

import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.symbol.api.beans.BlackListInfo;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Id;


@Indexed
public class BlackListInfoBean extends CacheObject implements BlackListInfo {

    @Field
    private long symbolId;
    @Field
    private int blackListed;
    @Field
    private int insId;
    @Id
    @Field
    private long id;

    private static final int HASH_CODE_GENERATE_MULTIPLIER = 31;
    private static final int HASH_CODE_COMPARATOR = 32;

    /**
     * Method to get symbol id
     *
     * @return symbol id long
     */
    public long getSymbolId() {
        return symbolId;
    }

    /**
     * Method to set symbol id
     *
     * @param symbolId long
     */
    public void setSymbolId(long symbolId) {
        this.symbolId = symbolId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBlackListed() {
        return blackListed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBlackListed(int blackListed) {
        this.blackListed = blackListed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getInsId() {
        return insId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInsId(int insId) {
        this.insId = insId;
    }

    /**
     * Method to get id
     *
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Method to set id
     *
     * @param id long
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BlackListInfoBean{" +
                "symbolId=" + symbolId +
                ", blackListed=" + blackListed +
                ", insId=" + insId +
                ", id=" + id +
                '}';
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BlackListInfoBean)) {
            return false;
        }

        BlackListInfoBean that = (BlackListInfoBean) o;

        if (blackListed != that.blackListed) {
            return false;
        }
        if (id != that.id) {
            return false;
        }
        if (insId != that.insId) {
            return false;
        }
        return symbolId == that.symbolId;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = (int) (symbolId ^ (symbolId >>> HASH_CODE_COMPARATOR));
        result = HASH_CODE_GENERATE_MULTIPLIER * result + blackListed;
        result = HASH_CODE_GENERATE_MULTIPLIER * result + insId;
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (int) (id ^ (id >>> HASH_CODE_COMPARATOR));
        return result;
    }
}
