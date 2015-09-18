package lk.ac.ucsc.oms.symbol.implGeneral.beans;


import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.common_utility.api.enums.PropertyEnable;
import lk.ac.ucsc.oms.symbol.api.beans.*;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Id;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Indexed
public abstract class BaseSymbolBean extends CacheObject implements BaseSymbol {
    @Id
    @Field
    private long symbolID = -1;
    @Field
    private String symbol; //(55)
    @Field
    private String securityType; //167
    @Field
    private String securityExchange; //207
    @Field
    private String currency; //15
    @Field
    private String marketCode; //336
    @Field
    private String reutercode; //55 -suplimentary
    @Field
    private String isinCode; // CAIRO
    @Field
    private int status;
    @Field
    private int statusCode;
    @Field
    private int accessLevel;
    @Field
    private int tradeEnabled;
    @Field
    private int onlineAllowed;
    @Field
    private Date lastUpdateTime;
    @Field
    private double priceRatio;
    @Field
    private int buyTPlus = 2;
    @Field
    private int lotSize = 1;
    @Field
    private String nationality;
    @Field
    private int minOrdSize = 1;
    @Field
    private int allowedDirection = 0; // 0-both/ 1- buy/ 2- sell 3-sell short
    @Field
    private int instrumentType = 0;  //Can be removed in the future
    @Field
    private double minOrdValue = 0;
    @Field
    private int smallOrderRestricted = 1;
    @Field
    private String exchangeSymbolCode;
    @Field
    private String exchangeSymbol;
    @Field
    private String securitySubType;
    @Field
    private int ipo=0;
    @FieldBridge
    private Map<Integer, ShariaInfo> shariaEnableOrganisations = new HashMap<>();
    @FieldBridge
    private Map<Integer, BlackListInfo> blackListedOrganisations = new HashMap<>();
    @FieldBridge
    private Map<Integer, SymbolMarginInfo> orgWiseMarginInfo = new HashMap<>();
    @FieldBridge
    private Map<String, SymbolDescription> symbolDescriptions = new HashMap<>();

    private static final int HASH_CODE_GENERATE_MULTIPLIER = 31;
    private static final int HASH_CODE_COMPARATOR = 32;

    /**
     * Default constructor with protected access modifier
     * to avoid instance creation outside the sub classes
     * and the package
     */
    protected BaseSymbolBean() {

    }

    /**
     * Constructor with symbol, exchange and security type as constructor
     * arguments
     *
     * @param symbol       code
     * @param exchange     code
     * @param securityType CS,OPT etc
     */
    public BaseSymbolBean(String symbol, String exchange, SecurityType securityType) {
        this.symbol = symbol;
        this.securityExchange = exchange;
        this.securityType = securityType.getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, SymbolDescription> getSymbolDescriptions() {
        return symbolDescriptions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSymbolDescriptions(Map<String, SymbolDescription> symbolDescriptions) {
        this.symbolDescriptions = symbolDescriptions;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Integer, ShariaInfo> getShariaEnableOrganisations() {
        return shariaEnableOrganisations;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setShariaEnableOrganisations(Map<Integer, ShariaInfo> shariaEnableOrganisations) {
        this.shariaEnableOrganisations = shariaEnableOrganisations;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Integer, BlackListInfo> getBlackListedOrganisations() {
        return blackListedOrganisations;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBlackListedOrganisations(Map<Integer, BlackListInfo> blackListedOrganisations) {
        this.blackListedOrganisations = blackListedOrganisations;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Integer, SymbolMarginInfo> getOrgWiseMarginInfo() {
        return orgWiseMarginInfo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOrgWiseMarginInfo(Map<Integer, SymbolMarginInfo> orgWiseMarginInfo) {
        this.orgWiseMarginInfo = orgWiseMarginInfo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getSymbolID() {
        return symbolID;
    }

    /**
     * {@inheritDoc}
     */
    public void setSymbolID(long symbolID) {
        this.symbolID = symbolID;
    }

    /**
     * Method to get symbol id as a string
     *
     * @return symbol id string
     */
    public String getSymbolIDString() {
        return String.valueOf(symbolID);
    }

    /**
     * Method to set symbol id as a string
     *
     * @param symbolID string
     */
    public void setSymbolIDString(String symbolID) {
        this.symbolID = Long.parseLong(symbolID);
    }

    /**
     * {@inheritDoc}
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SecurityType getSecurityTypes() {
        if (securityType == null) {
            return null;
        } else {
            return SecurityType.getEnum(securityType);
        }
    }

    /**
     * Method to get security type
     *
     * @return security type string
     */
    public String getSecurityType() {
        return securityType;
    }

    /**
     * Method to set security type
     *
     * @param securityType string
     */
    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSecurityTypes(SecurityType securityType) {
        this.securityType = securityType.getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSecurityExchange() {
        return securityExchange;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSecurityExchange(String securityExchange) {
        this.securityExchange = securityExchange;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCurrency() {
        return currency;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMarketCode() {
        return marketCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMarketCode(String marketCode) {
        this.marketCode = marketCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getReutercode() {
        return reutercode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setReutercode(String reutercode) {
        this.reutercode = reutercode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIsinCode() {
        return isinCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIsinCode(String isinCode) {
        this.isinCode = isinCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SymbolStatus getStatus() {
        return SymbolStatus.getEnum(statusCode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStatus(SymbolStatus status) {
        this.status = status.getCode();
        this.statusCode = status.getCode();
    }

    /**
     * Method to get status code
     *
     * @return status code int value
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Method to set status code int value
     *
     * @param statusCode int
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccessLevel getAccessLevels() {
        return AccessLevel.getEnum(accessLevel);
    }

    /**
     * Method to get access level
     *
     * @return access level int
     */
    public int getAccessLevel() {
        return accessLevel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAccessLevels(AccessLevel accessLevel) {
        this.accessLevel = accessLevel.getCode();
    }

    /**
     * Method to set access level
     *
     * @param accessLevel int
     */
    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    /**
     * Method to get trade enable int
     *
     * @return trade enable int
     */
    public int getTradeEnabled() {
        return tradeEnabled;
    }

    /**
     * Method to set trade enable int
     *
     * @param tradeEnabled int
     */
    public void setTradeEnabled(int tradeEnabled) {
        this.tradeEnabled = tradeEnabled;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTradeEnable(PropertyEnable tradeEnabled) {
        this.tradeEnabled = tradeEnabled.getCode();
    }

    /**
     * Method to get online allowed int
     *
     * @return online allowed int
     */
    public int getOnlineAllowed() {
        return onlineAllowed;
    }

    /**
     * Method to set online allowed int
     *
     * @param onlineAllowed int
     */
    public void setOnlineAllowed(int onlineAllowed) {
        this.onlineAllowed = onlineAllowed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOnlineAllow(PropertyEnable onlineAllowed) {
        this.onlineAllowed = onlineAllowed.getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getPriceRatio() {
        return priceRatio;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPriceRatio(double priceRatio) {
        this.priceRatio = priceRatio;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBuyTPlus() {
        return buyTPlus;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBuyTPlus(int buyTPlus) {
        this.buyTPlus = buyTPlus;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLotSize() {
        return lotSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLotSize(int lotSize) {
        this.lotSize = lotSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNationality() {
        return nationality;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMinOrdSize() {
        return minOrdSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMinOrdSize(int minOrdSize) {
        this.minOrdSize = minOrdSize;
    }

    /**
     * Method to get allowed direction
     *
     * @return allowed direction int
     */
    public int getAllowedDirection() {
        return allowedDirection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AllowDirection getAllowedDirections() {
        return AllowDirection.getEnum(allowedDirection);
    }

    /**
     * Method to set allowed direction int
     *
     * @param allowedDirection int
     */
    public void setAllowedDirection(int allowedDirection) {
        this.allowedDirection = allowedDirection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAllowedDirections(AllowDirection allowedDirection) {
        this.allowedDirection = allowedDirection.getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getInstrumentType() {
        return instrumentType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInstrumentType(int instrumentType) {
        this.instrumentType = instrumentType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getMinOrdValue() {
        return minOrdValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMinOrdValue(double minOrdValue) {
        this.minOrdValue = minOrdValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTradeEnable() {
        return tradeEnabled == 1;

    }

    /**
     * {@inheritDoc}
     */
    public boolean isOnlineAllow() {
        return onlineAllowed == 1;
    }

    /**
     * Method to get small order restricted int
     *
     * @return small order restricted int
     */
    public int getSmallOrderRestricted() {
        return smallOrderRestricted;
    }

    /**
     * Method to set small order restricted int
     *
     * @param smallOrderRestricted int
     */
    public void setSmallOrderRestricted(int smallOrderRestricted) {
        this.smallOrderRestricted = smallOrderRestricted;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PropertyEnable isSmallOrderRestrict() {
        return PropertyEnable.getEnum(smallOrderRestricted);
    }

    /**
     * {@inheritDoc}
     */
    public void setSmallOrderRestrict(PropertyEnable smallOrderRestrict) {
        this.smallOrderRestricted = smallOrderRestrict.getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getExchangeSymbolCode() {
        return exchangeSymbolCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExchangeSymbolCode(String exchangeSymbolCode) {
        this.exchangeSymbolCode = exchangeSymbolCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getExchangeSymbol() {
        return exchangeSymbol;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExchangeSymbol(String exchangeSymbol) {
        this.exchangeSymbol = exchangeSymbol;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSecuritySubType(){
        return securitySubType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSecuritySubType(String securitySubType){
       this.securitySubType = securitySubType;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int getIpo(){
        return ipo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIpo(int ipo){
        this.ipo=ipo;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BaseSymbolBean{" +
                "symbolID=" + symbolID +
                ", symbol='" + symbol + '\'' +
                ", securityType='" + securityType + '\'' +
                ", securityExchange='" + securityExchange + '\'' +
                ", currency='" + currency + '\'' +
                ", marketCode='" + marketCode + '\'' +
                ", reutercode='" + reutercode + '\'' +
                ", isinCode='" + isinCode + '\'' +
                ", status=" + status +
                ", statusCode=" + statusCode +
                ", accessLevel=" + accessLevel +
                ", tradeEnabled=" + tradeEnabled +
                ", onlineAllowed=" + onlineAllowed +
                ", lastUpdateTime=" + lastUpdateTime +
                ", priceRatio=" + priceRatio +
                ", buyTPlus=" + buyTPlus +
                ", lotSize=" + lotSize +
                ", nationality='" + nationality + '\'' +
                ", minOrdSize=" + minOrdSize +
                ", allowedDirection=" + allowedDirection +
                ", instrumentType=" + instrumentType +
                ", minOrdValue=" + minOrdValue +
                ", shariaEnableOrganisations=" + shariaEnableOrganisations.toString() +
                ", blackListedOrganisations=" + blackListedOrganisations.toString() +
                ", orgWiseMarginInfo=" + orgWiseMarginInfo.toString() +
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
        if (!(o instanceof BaseSymbolBean)) {
            return false;
        }

        BaseSymbolBean that = (BaseSymbolBean) o;

        if (accessLevel != that.accessLevel) {
            return false;
        }
        if (allowedDirection != that.allowedDirection) {
            return false;
        }
        if (buyTPlus != that.buyTPlus) {
            return false;
        }
        if (instrumentType != that.instrumentType) {
            return false;
        }
        if (lastUpdateTime != that.lastUpdateTime) {
            return false;
        }
        if (lotSize != that.lotSize) {
            return false;
        }
        if (minOrdSize != that.minOrdSize) {
            return false;
        }
        if (Double.compare(that.minOrdValue, minOrdValue) != 0) {
            return false;
        }
        if (onlineAllowed != that.onlineAllowed) {
            return false;
        }
        if (Double.compare(that.priceRatio, priceRatio) != 0) {
            return false;
        }
        if (status != that.status) {
            return false;
        }
        if (statusCode != that.statusCode) {
            return false;
        }
        if (symbolID != that.symbolID) {
            return false;
        }
        if (tradeEnabled != that.tradeEnabled) {
            return false;
        }
        if (blackListedOrganisations != null ? !blackListedOrganisations.equals(that.blackListedOrganisations) : that.blackListedOrganisations != null) {
            return false;
        }
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) {
            return false;
        }
        if (isinCode != null ? !isinCode.equals(that.isinCode) : that.isinCode != null) {
            return false;
        }
        if (marketCode != null ? !marketCode.equals(that.marketCode) : that.marketCode != null) {
            return false;
        }
        if (nationality != null ? !nationality.equals(that.nationality) : that.nationality != null) {
            return false;
        }
        if (orgWiseMarginInfo != null ? !orgWiseMarginInfo.equals(that.orgWiseMarginInfo) : that.orgWiseMarginInfo != null) {
            return false;
        }
        if (reutercode != null ? !reutercode.equals(that.reutercode) : that.reutercode != null) {
            return false;
        }
        if (securityExchange != null ? !securityExchange.equals(that.securityExchange) : that.securityExchange != null) {
            return false;
        }
        if (securityType != null ? !securityType.equals(that.securityType) : that.securityType != null) {
            return false;
        }
        if (shariaEnableOrganisations != null ? !shariaEnableOrganisations.equals(that.shariaEnableOrganisations) : that.shariaEnableOrganisations != null) {
            return false;
        }
        if (symbol != null ? !symbol.equals(that.symbol) : that.symbol != null) {
            return false;
        }
        return !(symbolDescriptions != null ? !symbolDescriptions.equals(that.symbolDescriptions) : that.symbolDescriptions != null);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (symbolID ^ (symbolID >>> HASH_CODE_COMPARATOR));
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (symbol != null ? symbol.hashCode() : 0);
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (securityType != null ? securityType.hashCode() : 0);
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (securityExchange != null ? securityExchange.hashCode() : 0);
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (currency != null ? currency.hashCode() : 0);
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (marketCode != null ? marketCode.hashCode() : 0);
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (reutercode != null ? reutercode.hashCode() : 0);
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (isinCode != null ? isinCode.hashCode() : 0);
        result = HASH_CODE_GENERATE_MULTIPLIER * result + status;
        result = HASH_CODE_GENERATE_MULTIPLIER * result + statusCode;
        result = HASH_CODE_GENERATE_MULTIPLIER * result + accessLevel;
        result = HASH_CODE_GENERATE_MULTIPLIER * result + tradeEnabled;
        result = HASH_CODE_GENERATE_MULTIPLIER * result + onlineAllowed;
        temp = priceRatio != +0.0d ? Double.doubleToLongBits(priceRatio) : 0L;
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (int) (temp ^ (temp >>> HASH_CODE_COMPARATOR));
        result = HASH_CODE_GENERATE_MULTIPLIER * result + buyTPlus;
        result = HASH_CODE_GENERATE_MULTIPLIER * result + lotSize;
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (nationality != null ? nationality.hashCode() : 0);
        result = HASH_CODE_GENERATE_MULTIPLIER * result + minOrdSize;
        result = HASH_CODE_GENERATE_MULTIPLIER * result + allowedDirection;
        result = HASH_CODE_GENERATE_MULTIPLIER * result + instrumentType;
        temp = minOrdValue != +0.0d ? Double.doubleToLongBits(minOrdValue) : 0L;
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (int) (temp ^ (temp >>> HASH_CODE_COMPARATOR));
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (shariaEnableOrganisations != null ? shariaEnableOrganisations.hashCode() : 0);
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (blackListedOrganisations != null ? blackListedOrganisations.hashCode() : 0);
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (orgWiseMarginInfo != null ? orgWiseMarginInfo.hashCode() : 0);
        result = HASH_CODE_GENERATE_MULTIPLIER * result + (symbolDescriptions != null ? symbolDescriptions.hashCode() : 0);
        return result;
    }
}
