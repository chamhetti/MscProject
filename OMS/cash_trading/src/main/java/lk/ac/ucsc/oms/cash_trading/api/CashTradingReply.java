package lk.ac.ucsc.oms.cash_trading.api;

public interface CashTradingReply {

    boolean isSuccess();

    int getRejectCode();

    String getRejectReason();

    int getModuleCode();

    String getErrMsgParameterList();

}
