package lk.ac.ucsc.oms.symbol.api.beans;

/**
 * This is the data transfer interface used to give short and long descriptions for symbols in different languages.
 *
 * User: Hetti
 * Date: 11/23/12
 * Time: 10:45 AM
 */
public interface SymbolDescription {
    /**
     * Id of the symbol
     * @return long
     */
    long getSymbolId();

    /**
     * set the ID of the symbol
     * @param symbolId  long
     */
    void setSymbolId(long symbolId);

    /**
     * get the international language code related to descriptions
     * @return  String
     */
    String getLanguageCode();

    /**
     * set the language code. It must give the international language code
     * @param languageCode  String
     */
    void setLanguageCode(String languageCode);

    /**
     * short description of the symbol from a language define in language code
     * @return  String
     */
    String getShortDescription();

    /**
     * set the short description using the language related to language code
     * @param shortDescription String
     */
    void setShortDescription(String shortDescription);

    /**
     * get the long description
     * @return  String
     */
    String getLongDescription();

    /**
     * set the long description from a language define in language code
     * @param longDescription   String
     */
    void setLongDescription(String longDescription);
}
