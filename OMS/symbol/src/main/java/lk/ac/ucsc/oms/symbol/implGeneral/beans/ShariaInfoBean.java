package lk.ac.ucsc.oms.symbol.implGeneral.beans;


import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.common_utility.api.enums.PropertyEnable;
import lk.ac.ucsc.oms.symbol.api.beans.ShariaInfo;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;



@Indexed
public class ShariaInfoBean extends CacheObject implements ShariaInfo {
    @Field
    private long symbolId;
    @Field
    private int shariaEnables;
    @Field
    private int insId;
    @Field
    @DocumentId
    private long id;

    private static final int HASH_CODE_GENERATE_MULTIPLIER = 31;
    private static final int HASH_CODE_COMPARATOR = 32;

    /**
     * {@inheritDoc}
     */
    @Override
    public long getSymbolId() {
        return symbolId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSymbolId(long symbolId) {
        this.symbolId = symbolId;
    }

    /**
     * Method to get sharia enables
     *
     * @return sharia enables int
     */
    public int getShariaEnables() {
        return shariaEnables;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isShariaEnable() {
        return shariaEnables == 1;
    }

    /**
     * Method to set sharia enables
     *
     * @param shariaEnables int
     */
    public void setShariaEnables(int shariaEnables) {
        this.shariaEnables = shariaEnables;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setShariaEnableOrNot(PropertyEnable shariaEnable) {
        this.shariaEnables = shariaEnable.getCode();
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
     * @return id long
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
        return "ShariaInfoBean{" +
                "symbolId=" + symbolId +
                ", shariaEnables=" + shariaEnables +
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
        if (!(o instanceof ShariaInfoBean)) {
            return false;
        }

        ShariaInfoBean that = (ShariaInfoBean) o;

        if (id != that.id) {
            return false;
        }
        if (insId != that.insId) {
            return false;
        }
        if (shariaEnables != that.shariaEnables) {
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
        result = HASH_CODE_GENERATE_MULTIPLIER * result + shariaEnables;
        result = HASH_CODE_GENERATE_MULTIPLIER * result + insId;
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (int) (id ^ (id >>> HASH_CODE_COMPARATOR));
        return result;
    }
}
