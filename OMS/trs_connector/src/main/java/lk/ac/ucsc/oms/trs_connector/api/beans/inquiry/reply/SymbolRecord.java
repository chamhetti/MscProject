package lk.ac.ucsc.oms.trs_connector.api.beans.inquiry.reply;

/**
 * @author Chathura
 *         Date: Feb 18, 2013
 */
public interface SymbolRecord {

    String getExchange();

    void setExchange(String exchange);

    String getSymbol();

    void setSymbol(String symbol);

    double getSymbolMarginablePercentage();

    void setSymbolMarginablePercentage(double symbolMarginablePercentage);

    double getSymbolTradeMarginForBUY();

    void setSymbolTradeMarginForBUY(double symbolTradeMarginForBUY);

    double getSymbolMarginability();

    void setSymbolMarginability(double symbolMarginability);

    String getInstitutionID();

    void setInstitutionID(String institutionID);

    String getCurrency();

    void setCurrency(String currency);

    String getSecurityType();

    void setSecurityType(String securityType);

    int getInstrumentType();

    void setInstrumentType(int instrumentType);

    double getSymbolDayMarginablePercentage();

    void setSymbolDayMarginablePercentage(double symbolDayMarginablePercentage);

    }
