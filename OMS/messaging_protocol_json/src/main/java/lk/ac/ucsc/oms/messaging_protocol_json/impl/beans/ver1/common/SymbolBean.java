package lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.common;

import com.google.gson.annotations.SerializedName;

import static lk.ac.ucsc.oms.messaging_protocol_json.api.FieldConstants.*;


public class SymbolBean {

    @SerializedName(EXCHANGE)
    private String exchange;

    @SerializedName(SYMBOL)
    private String symbol;

    @SerializedName(SYMBOL_MARGINABLE_PERCENTAGE)
    private double symbolMarginablePercentage;

    @SerializedName(SYMBOL_DAY_MARGINABLE_PERCENTAGE)
    private double symbolDayMarginablePercentage;

    @SerializedName(SYMBOL_TRADE_MARGIN_FOR_BUY)
    private double symbolTradeMarginForBUY;

    @SerializedName(SYMBOL_MARGINABILITY)
    private double symbolMarginability;

    @SerializedName(INSTITUTE_ID)
    private String institutionID;

    @SerializedName(CURRENCY)
    private String currency;

    @SerializedName(SECURITY_TYPE)
    private String securityType = null;

    @SerializedName(INSTRUMENT_TYPE)
    private String instrumentType;

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getSymbolMarginablePercentage() {
        return symbolMarginablePercentage;
    }

    public void setSymbolMarginablePercentage(double symbolMarginablePercentage) {
        this.symbolMarginablePercentage = symbolMarginablePercentage;
    }

    public double getSymbolDayMarginablePercentage() {
        return symbolDayMarginablePercentage;
    }

    public void setSymbolDayMarginablePercentage(double symbolDayMarginablePercentage) {
        this.symbolDayMarginablePercentage = symbolDayMarginablePercentage;
    }

    public double getSymbolTradeMarginForBUY() {
        return symbolTradeMarginForBUY;
    }

    public void setSymbolTradeMarginForBUY(double symbolTradeMarginForBUY) {
        this.symbolTradeMarginForBUY = symbolTradeMarginForBUY;
    }

    public double getSymbolMarginability() {
        return symbolMarginability;
    }

    public void setSymbolMarginability(double symbolMarginability) {
        this.symbolMarginability = symbolMarginability;
    }

    public String getInstitutionID() {
        return institutionID;
    }

    public void setInstitutionID(String institutionID) {
        this.institutionID = institutionID;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public String getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }
}
