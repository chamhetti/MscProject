package lk.ac.ucsc.oms.symbol.api.beans;

import lk.ac.ucsc.oms.common_utility.api.enums.PropertyEnable;

import java.util.Date;
import java.util.Map;

/**
 * Interface that contains the methods that can be use to get the common information related to any type of
 * symbol. this also contain enum type that can be used to set the security type, status etc.
 * <p/>
 * User: chamindah
 * Date: 11/19/12
 * Time: 2:44 PM
 */
public interface BaseSymbol {

    /**
     * get the primary key of the table related to this symbol
     *
     * @return Symbol ID long value
     */
    long getSymbolID();

    /**
     * set the ID of the symbol bean ID for this can be taken using ID generation info
     *
     * @param symbolID unique long value
     */
    void setSymbolID(long symbolID);

    /**
     * get the symbol code ex: EMAAR, ETEL etc
     *
     * @return symbol code
     */
    String getSymbol();

    /**
     * set the symbol code
     *
     * @param symbol symbol code ex: EMAAR, ETEL etc
     */
    void setSymbol(String symbol);

    /**
     * get the security type of the symbol
     *
     * @return Enum security type ex: CS,OPT
     */
    SecurityType getSecurityTypes();

    /**
     * set the security type of the symbol.
     *
     * @param securityType Enum security type ex: CS, OPT
     */
    void setSecurityTypes(SecurityType securityType);

    /**
     * get the security exchange ex: CASE, DFM
     *
     * @return security exchange
     */
    String getSecurityExchange();

    /**
     * set the security exchange
     *
     * @param securityExchange security exchange
     */
    void setSecurityExchange(String securityExchange);

    /**
     * get the currency of the symbol this give the international currency code. ex: AED, EGP etc
     *
     * @return String Currency
     */
    String getCurrency();

    /**
     * set the currency code. Expected values are international currency code.
     *
     * @param currency String
     */
    void setCurrency(String currency);

    /**
     * get the sub market of the symbol. ex: PL, NOPL in Egypt
     *
     * @return String sub market
     */
    String getMarketCode();

    /**
     * set the sub market of the symbol.
     *
     * @param marketCode String ex: PL, NOPL in Egypt
     */
    void setMarketCode(String marketCode);

    /**
     * get the reuters code of the symbol
     *
     * @return String
     */
    String getReutercode();

    /**
     * set the reuters code of the symbol
     *
     * @param reutercode String
     */
    void setReutercode(String reutercode);

    /**
     * get the international symbol identification number of the symbol
     *
     * @return String
     */
    String getIsinCode();

    /**
     * set the international symbol identification number of the symbol
     *
     * @param isinCode String
     */
    void setIsinCode(String isinCode);

    /**
     * get the status of the symbol like pending, APPROVED etc.
     *
     * @return SymbolStatus
     */
    SymbolStatus getStatus();

    /**
     * set the status of the symbol
     *
     * @param status SymbolStatus
     */
    void setStatus(SymbolStatus status);

    /**
     * get the access level of the symbol like LOCAL, REGIONAL or INTERNATIONAL
     *
     * @return int
     */
    AccessLevel getAccessLevels();

    /**
     * set the access level of the symbol like LOCAL, REGIONAL or INTERNATIONAL
     *
     * @param accessLevel int
     */
    void setAccessLevels(AccessLevel accessLevel);

    /**
     * get trading enable or disable for this symbol
     *
     * @return boolean
     */
    boolean isTradeEnable();

    /**
     * set trading enable or not
     * 1- enable
     * 0 - disable
     *
     * @param tradeEnabled int
     */
    void setTradeEnable(PropertyEnable tradeEnabled);

    /**
     * check online trading allow or not for this symbol
     *
     * @return boolean
     */
    boolean isOnlineAllow();

    /**
     * set online trading enable or not
     * 1 -enable
     * 0 -disable
     *
     * @param onlineAllowed int
     */
    void setOnlineAllow(PropertyEnable onlineAllowed);

    /**
     * get the last updated time of the symbol
     *
     * @return long last updated time
     */
    Date getLastUpdateTime();

    /**
     * set the updated time while changing any symbol related info
     *
     * @param lastUpdateTime long last updated time
     */
    void setLastUpdateTime(Date lastUpdateTime);

    /**
     * get the price ratio
     *
     * @return double
     */
    double getPriceRatio();

    /**
     * set the price ratio default is 1
     *
     * @param priceRatio double
     */
    void setPriceRatio(double priceRatio);

    /**
     * get the Buytplus of the symbol. Base on this it is decide to allow sell in same day or not for symbol
     *
     * @return int
     */
    int getBuyTPlus();

    /**
     * set the buytplus of the symbol. default value can be change base on the deploy country
     *
     * @param buyTPlus int
     */
    void setBuyTPlus(int buyTPlus);

    /**
     * get the lot size. Actual qty is the multiplication of this
     *
     * @return int
     */
    int getLotSize();

    /**
     * set the lot size
     *
     * @param lotSize int
     */
    void setLotSize(int lotSize);

    /**
     * get the nationality of the symbol
     *
     * @return String
     */
    String getNationality();

    /**
     * set the nationality of th symbol. Better to provide country code.
     *
     * @param nationality String
     */
    void setNationality(String nationality);

    /**
     * get the quantity of the minimum order allow for this symbol.
     *
     * @return int
     */
    int getMinOrdSize();

    /**
     * set the quantity of the minimum order allow for this symbol.
     *
     * @param minOrdSize int
     */
    void setMinOrdSize(int minOrdSize);

    /**
     * get BUY, SELL or Both allow for this symbol
     *
     * @return AllowDirection
     */
    AllowDirection getAllowedDirections();

    /**
     * set allowed side for the symbol for trading
     * 0-both
     * 1 - buy
     * 2 - sell
     *
     * @param allowedDirection AllowDirection
     */
    void setAllowedDirections(AllowDirection allowedDirection);

    /**
     * get the instrument type of the symbol. This is price related info and not used in oms logic
     *
     * @return int
     */
    int getInstrumentType();

    /**
     * set the instrument type of the symbol. This is price related info and not used in oms logic
     *
     * @param instrumentType int
     */
    void setInstrumentType(int instrumentType);

    /**
     * Value of the minimum order for this symbol.
     *
     * @return double
     */
    double getMinOrdValue();

    /**
     * set the value of the minimum order for this symbol
     *
     * @param minOrdValue double
     */
    void setMinOrdValue(double minOrdValue);

    /**
     * get the map which contain the sharia information about this symbol at institution basis
     * If this list is empty it assume that this is non sharia symbol and if it is sharia it is complusory to
     * set this at institution level
     *
     * @return Map of sharia enable organizations
     */
    Map<Integer, ShariaInfo> getShariaEnableOrganisations();

    /**
     * set the institution wise shari information of this symbol
     *
     * @param shariaEnableOrganisations map of sharia info beans
     */
    void setShariaEnableOrganisations(Map<Integer, ShariaInfo> shariaEnableOrganisations);

    /**
     * get the institution wise margin information. If this is empty it is assume that this is non
     * marginal symbol.
     *
     * @return map of symbol margin info bean
     */
    Map<Integer, SymbolMarginInfo> getOrgWiseMarginInfo();

    /**
     * set the institution wise margin information of teh symbol.
     *
     * @param orgWiseMarginInfo map of symbol margin info bean
     */
    void setOrgWiseMarginInfo(Map<Integer, SymbolMarginInfo> orgWiseMarginInfo);

    /**
     * get the restricted institution information for this symbol. If it is restricted for the institution it is
     * not allow to do any operation on or using this symbol for the users of that institution
     *
     * @return map of black list info
     */
    Map<Integer, BlackListInfo> getBlackListedOrganisations();

    /**
     * set the institution wise restriction for the symbol
     *
     * @param blackListedOrganisations map of black listed info bean
     */
    void setBlackListedOrganisations(Map<Integer, BlackListInfo> blackListedOrganisations);

    /**
     * Get the symbol description information in each language
     *
     * @return map of symbol description beans
     */
    Map<String, SymbolDescription> getSymbolDescriptions();

    /**
     * Set the symbol description in several languages
     *
     * @param symbolDescriptions map of symbol description beans
     */
    void setSymbolDescriptions(Map<String, SymbolDescription> symbolDescriptions);

    /**
     * get small order restricted or not
     *
     * @return Enum Small order restricted
     */
    PropertyEnable isSmallOrderRestrict();

    /**
     * set small order restriction
     * 0- no
     * 1 - yes
     *
     * @param smallOrderRestrict SmallOrderRestrict
     */
    void setSmallOrderRestrict(PropertyEnable smallOrderRestrict);

    /**
     * get exchange symbol code
     * @return String
     */
    String getExchangeSymbolCode();

    /**
     * set exchange symbol code
     * @param exchangeSymbolCode  String
     */
    void setExchangeSymbolCode(String exchangeSymbolCode);

    /**
     * get exchange symbol
     * @return String
     */
    String getExchangeSymbol();

    /**
     * set exchange symbol
     * @param exchangeSymbol String
     */
    void setExchangeSymbol(String exchangeSymbol);

    /**
     * get security sub type
     * @return String
     */
    String getSecuritySubType();

    /**
     * set security sub type
     * @param securitySubType String
     */
    void setSecuritySubType(String securitySubType);

    /**
     * get ipo
     *
     * @return int
     */
    int getIpo();

    /**
     * set ipo
     *
     * @param ipo int
     */
    void setIpo(int ipo);

    /**
     * status of the symbol
     * PENDING_APPROVAL -0
     * APPROVED -1
     * PENDING_DELETE -3
     * DELETED -2
     */
    enum SymbolStatus {
        PENDING_APPROVAL(0), APPROVED(1), PENDING_DELETE(3), DELETED(2);
        private int code;
        private static final int CONST_0 = 0;
        private static final int CONST_1 = 1;
        private static final int CONST_2 = 2;
        private static final int CONST_3 = 3;

        SymbolStatus(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static SymbolStatus getEnum(int code) {
            switch (code) {
                case CONST_0:
                    return PENDING_APPROVAL;
                case CONST_1:
                    return APPROVED;
                case CONST_3:
                    return PENDING_DELETE;
                case CONST_2:
                    return DELETED;
                default:
                    return null;
            }
        }
    }

    /**
     * status of the AccessLevel
     * LOCAL -1
     * REGIONAL -2
     * INTERNATIONAL -3
     */
    enum AccessLevel {
        LOCAL(1), REGIONAL(2), INTERNATIONAL(3);
        private int code;
        private static final int CONST_1 = 1;
        private static final int CONST_2 = 2;
        private static final int CONST_3 = 3;


        AccessLevel(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static AccessLevel getEnum(int code) {
            switch (code) {
                case CONST_1:
                    return LOCAL;
                case CONST_2:
                    return REGIONAL;
                case CONST_3:
                    return INTERNATIONAL;
                default:
                    return INTERNATIONAL;
            }
        }
    }

    /**
     * Trading Enable status
     * ALL -0
     * BUY -1
     * SELL -2
     * SELL_SHORT -3
     */
    enum AllowDirection {
        ALL(0), BUY(1), SELL(2), SELL_SHORT(3);
        private int code;
        private static final int CODE_ALL = 0;
        private static final int CODE_BUY = 1;
        private static final int CODE_SELL = 2;
        private static final int CODE_SELL_SHORT = 3;

        AllowDirection(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static AllowDirection getEnum(int code) {
            switch (code) {
                case CODE_ALL:
                    return ALL;
                case CODE_BUY:
                    return BUY;
                case CODE_SELL:
                    return SELL;
                case CODE_SELL_SHORT:
                    return SELL_SHORT;
                default:
                    return null;
            }
        }
    }

    /**
     * security type of the symbol
     * COMMON_STOCK -CS
     * FUTURE - FUT
     * OPTION - OPT
     * FOREX - FOR
     * MUTUAL_FUND - MF
     */
    enum SecurityType {
        COMMON_STOCK("CS"), FUTURE("FUT"), OPTION("OPT"), FOREX("FOR"), MUTUAL_FUND("MF");
        private String code;

        SecurityType(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public static SecurityType getEnum(String code) {
            switch (code) {
                case "CS":
                    return COMMON_STOCK;
                case "FUT":
                    return FUTURE;
                case "OPT":
                    return OPTION;
                case "FOR":
                    return FOREX;
                case "MF":
                    return MUTUAL_FUND;
                default:
                    return null;
            }
        }
    }
}
