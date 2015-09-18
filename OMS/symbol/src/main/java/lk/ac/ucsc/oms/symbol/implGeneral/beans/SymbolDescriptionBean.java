package lk.ac.ucsc.oms.symbol.implGeneral.beans;

import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.symbol.api.beans.SymbolDescription;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Id;


@Indexed
public class SymbolDescriptionBean extends CacheObject implements SymbolDescription {
    @Field
    private long symbolId;
    @Field
    private String languageCode;
    @Field
    private String shortDescription;
    @Field
    private String longDescription;
    @Id
    @Field
    private long id;

    private static final int HASH_CODE_GENERATE_MULTIPLIER = 31;
    private static final int HASH_CODE_COMPARATOR = 32;

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
     * {@inheritDoc}
     */
    @Override
    public String getLanguageCode() {
        return languageCode;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getShortDescription() {
        return shortDescription;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getLongDescription() {
        return longDescription;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SymbolDescriptionBean)) {
            return false;
        }

        SymbolDescriptionBean that = (SymbolDescriptionBean) o;

        if (id != that.id) {
            return false;
        }
        if (symbolId != that.symbolId) {
            return false;
        }
        if (!languageCode.equals(that.languageCode)) {
            return false;
        }
        if (!longDescription.equals(that.longDescription)) {
            return false;
        }
        return shortDescription.equals(that.shortDescription);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = (int) (symbolId ^ (symbolId >>> HASH_CODE_COMPARATOR));
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (languageCode != null ? languageCode.hashCode() : 0);
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (shortDescription != null ? shortDescription.hashCode() : 0);
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (longDescription != null ? longDescription.hashCode() : 0);
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (int) (id ^ (id >>> HASH_CODE_COMPARATOR));
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "SymbolDescriptionBean{" +
                "symbolId=" + symbolId +
                ", languageCode='" + languageCode + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", longDescription='" + longDescription + '\'' +
                ", id=" + id +
                '}';
    }
}
