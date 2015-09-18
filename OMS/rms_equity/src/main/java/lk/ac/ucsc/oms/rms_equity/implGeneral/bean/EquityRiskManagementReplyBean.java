package lk.ac.ucsc.oms.rms_equity.implGeneral.bean;


import lk.ac.ucsc.oms.rms_equity.api.EquityRiskManagementReply;

public final class EquityRiskManagementReplyBean implements EquityRiskManagementReply {
    private final boolean isSuccess;
    private final int rejectCode;
    private final String rejectReason;
    private final int moduleCode;
    private final String errMsgParameterList;

    /**
     * over loaded constructor that used to send successful Equity Risk Management Replies
     */
    public EquityRiskManagementReplyBean(boolean success) {
        this(success, -1, "", -1, "");
    }

    public EquityRiskManagementReplyBean(boolean success, int rejectCode, String rejectReason, int moduleCode, String errMsgParaList) {
        isSuccess = success;
        this.rejectCode = rejectCode;
        this.rejectReason = rejectReason;
        this.moduleCode = moduleCode;
        this.errMsgParameterList = errMsgParaList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSuccess() {
        return isSuccess;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRejectCode() {
        return rejectCode;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getRejectReason() {
        return rejectReason;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getModuleCode() {
        return moduleCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getErrMsgParameterList() {
        return errMsgParameterList;
    }

}
