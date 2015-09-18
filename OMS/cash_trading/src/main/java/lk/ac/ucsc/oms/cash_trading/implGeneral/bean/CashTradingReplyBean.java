package lk.ac.ucsc.oms.cash_trading.implGeneral.bean;

import lk.ac.ucsc.oms.cash_trading.api.CashTradingReply;

public class CashTradingReplyBean implements CashTradingReply {
    private boolean isSuccess;
    private int rejectCode;
    private String rejectReason;
    private int moduleCode;
    private String errMsgParameterList;


    public CashTradingReplyBean(boolean success) {
        this(success, -1, "", -1, "");
    }

    public CashTradingReplyBean(boolean success, int rejectCode, String rejectReason, int moduleCode, String errMsgParaList) {
        this.isSuccess = success;
        this.rejectCode = rejectCode;
        this.rejectReason = rejectReason;
        this.moduleCode = moduleCode;
        this.errMsgParameterList = errMsgParaList;
    }

    @Override
    public boolean isSuccess() {
        return isSuccess;
    }

    @Override
    public int getRejectCode() {
        return rejectCode;
    }

    @Override
    public String getRejectReason() {
        return rejectReason;
    }

    @Override
    public int getModuleCode() {
        return moduleCode;
    }

    @Override
    public String getErrMsgParameterList() {
        return errMsgParameterList;
    }

}
