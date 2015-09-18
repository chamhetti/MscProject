package com.directfn.exchange_simulator.message_processor.impl.helper;

import com.directfn.exchange_simulator.common_util.exceptions.SimulatorException;
import com.directfn.exchange_simulator.message_processor.impl.beans.FIXPropertiesBean;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: Ruchira Ranaweera
 * Date: 5/8/14
 */
public class FixMessageGenerator {
    public static final char NEW_ORDER_SINGLE = 'D';
    public static final char ORDER_CANCEL_REQUEST = 'F';
    public static final char ORDER_CANCEL_REPLACE = 'G';
    public static final char TRADING_SESSION_STATUS_RESPONSE = 'h';
    public static final char EXECUTION_REPORT = '8';
    public static final char ORDER_CANCEL_REJECT = '9';
    public static final char SESSION_LOG_ON = 'A';
    public static final String REUTER_CODE = "5";
    public static final String ISIN_CODE = "4";

    public static final String FIELD_SEPARATOR = "\u0001";


    public synchronized String getFixMessage(FIXPropertiesBean fixPropertiesBean) throws SimulatorException {
        String fixMessage;
        switch (fixPropertiesBean.getMessageType()) {
            case NEW_ORDER_SINGLE:
                fixMessage = getNewOrderRequest(fixPropertiesBean);
                break;
            case ORDER_CANCEL_REPLACE:
                fixMessage = getOrderAmendRequest(fixPropertiesBean);
                break;
            case ORDER_CANCEL_REQUEST:
                fixMessage = getOrderCancelRequest(fixPropertiesBean);
                break;
            case TRADING_SESSION_STATUS_RESPONSE:
                fixMessage = getTradingSessionStatusFixMessage(fixPropertiesBean);
                break;
            case EXECUTION_REPORT:
            case ORDER_CANCEL_REJECT:
                fixMessage = getExecutionReport(fixPropertiesBean);
                break;
            case SESSION_LOG_ON:
                fixMessage = getSessionLogOnFixMessage(fixPropertiesBean);
                break;
            default:
                throw new SimulatorException("Invalid message type");
        }

        return fixMessage;
    }

    private String getTradingSessionStatusFixMessage(FIXPropertiesBean fixPropertiesBean) {
        StringBuilder fixMessage = new StringBuilder();
        fixMessage.append(FIXConstant.BEGIN_STRING + "FIX.4.4" + FIELD_SEPARATOR);
        fixMessage.append(FIXConstant.BODY_LENGTH + 93 + FIELD_SEPARATOR);
        fixMessage.append(FIXConstant.MSG_TYPE).append(fixPropertiesBean.getMessageType()).append(FIELD_SEPARATOR);
        fixMessage.append(FIXConstant.SENDER_COMP_ID).append(fixPropertiesBean.getSenderCompID()).append(FIELD_SEPARATOR);
        fixMessage.append(FIXConstant.TARGET_COMP_ID).append(fixPropertiesBean.getTargetCompID()).append(FIELD_SEPARATOR);
        fixMessage.append(FIXConstant.MSG_SEQ_NUM).append("172").append(FIELD_SEPARATOR);
        fixMessage.append(FIXConstant.SENDING_TIME).append(new Date().toString()).append(FIELD_SEPARATOR);
        fixMessage.append(FIXConstant.TRADING_SESSION_ID).append(fixPropertiesBean.getTradingSessionID()).append(FIELD_SEPARATOR);
        fixMessage.append(FIXConstant.TRAD_SES_STATUS).append(fixPropertiesBean.getTradSesStatus()).append(FIELD_SEPARATOR);
        fixMessage.append(FIXConstant.TEXT).append(fixPropertiesBean.getText()).append(FIELD_SEPARATOR);
        fixMessage.append(FIXConstant.TRAD_SES_REQ_ID).append(fixPropertiesBean.getTradSesReqID()).append(FIELD_SEPARATOR);
        fixMessage.append(FIXConstant.CHECK_SUM).append("140").append(FIELD_SEPARATOR);

        return fixMessage.toString();
    }

    private String getSessionLogOnFixMessage(FIXPropertiesBean fixPropertiesBean) {
        StringBuilder fixMessage = new StringBuilder();
        fixMessage.append(FIXConstant.BEGIN_STRING + "FIX.4.4" + FIELD_SEPARATOR);
        fixMessage.append(FIXConstant.BODY_LENGTH + 70 + FIELD_SEPARATOR);
        fixMessage.append(FIXConstant.MSG_TYPE).append(fixPropertiesBean.getMessageType()).append(FIELD_SEPARATOR);
        fixMessage.append(FIXConstant.MSG_SEQ_NUM).append("1003").append(FIELD_SEPARATOR);
        fixMessage.append(FIXConstant.SENDER_COMP_ID).append(fixPropertiesBean.getSenderCompID()).append(FIELD_SEPARATOR);
        fixMessage.append(FIXConstant.TARGET_COMP_ID).append(fixPropertiesBean.getTargetCompID()).append(FIELD_SEPARATOR);
        fixMessage.append(FIXConstant.SENDING_TIME).append(getFormattedDate(new Date())).append(FIELD_SEPARATOR);
        fixMessage.append(FIXConstant.ENCRYPT_METHOD).append(0).append(FIELD_SEPARATOR);
        fixMessage.append(FIXConstant.HEART_BT_INT).append(30).append(FIELD_SEPARATOR);
        fixMessage.append(FIXConstant.CHECK_SUM).append("192").append(FIELD_SEPARATOR);

        return fixMessage.toString();
    }

    private String getExecutionReport(FIXPropertiesBean fixPropertiesBean) {
        StringBuilder sb = new StringBuilder();
        sb.append(FIXConstant.BEGIN_STRING + "FIX.4.2" + FIELD_SEPARATOR);
        sb.append(FIXConstant.MSG_TYPE + fixPropertiesBean.getMessageType() + FIELD_SEPARATOR);
        if (fixPropertiesBean.getTargetCompID() != null) {
            sb.append(FIXConstant.SENDER_COMP_ID + fixPropertiesBean.getTargetCompID() + FIELD_SEPARATOR);
        }
        if (fixPropertiesBean.getSenderCompID() != null) {
            sb.append(FIXConstant.TARGET_COMP_ID + fixPropertiesBean.getSenderCompID() + FIELD_SEPARATOR);
        }
        if (fixPropertiesBean.getSenderSubID() != null) {
            sb.append(FIXConstant.TARGET_SUBID + fixPropertiesBean.getSenderSubID() + FIELD_SEPARATOR);
        }
        if (fixPropertiesBean.getTargetSubID() != null) {
            sb.append(FIXConstant.SENDER_SUBID + fixPropertiesBean.getTargetSubID() + FIELD_SEPARATOR);
        }
        if (fixPropertiesBean.getOnBehalfOfCompID() != null) {
            sb.append(FIXConstant.DELIV_COMP_ID + fixPropertiesBean.getOnBehalfOfCompID() + FIELD_SEPARATOR);
        }
        if (fixPropertiesBean.getOnBehalfOfSubID() != null) {
            sb.append(FIXConstant.DELIV_SUB_ID + fixPropertiesBean.getOnBehalfOfSubID() + FIELD_SEPARATOR);
        }
        sb.append(FIXConstant.PORTFOLIO_NO + fixPropertiesBean.getPortfolioNo() + FIELD_SEPARATOR);
        if (fixPropertiesBean.getSecurityIDSource() != null) {
            sb.append("22=" + fixPropertiesBean.getSecurityIDSource() + FIELD_SEPARATOR);
        }
        //if the security id source is null,then not reuter code or isin code associated with the order
        if ((fixPropertiesBean.getSecurityIDSource() == null) || (fixPropertiesBean.getSecurityIDSource() != null && fixPropertiesBean.getSecurityIDSource().length() <= 0)) {
            sb.append(FIXConstant.EXG + fixPropertiesBean.getSecurityExchange() + FIELD_SEPARATOR);
            sb.append(FIXConstant.SYMBOL + fixPropertiesBean.getSymbol() + FIELD_SEPARATOR);
        } else {
            //for the fix channel orders always keep the remote symbol, remote security id, remote exchange
            sb.append(FIXConstant.SYMBOL + fixPropertiesBean.getRemoteSymbol() + FIELD_SEPARATOR);

            if (fixPropertiesBean.getRemoteSecurityID() != null && fixPropertiesBean.getRemoteSecurityID().length() > 0) {
                sb.append(FIXConstant.SYMBOL_CODE + fixPropertiesBean.getRemoteSecurityID() + FIELD_SEPARATOR);
            } else {
                sb.append(FIXConstant.SYMBOL_CODE + fixPropertiesBean.getRemoteSymbol() + FIELD_SEPARATOR);
            }
            if (fixPropertiesBean.getRemoteExchange() != null) {
                sb.append(FIXConstant.EXG + fixPropertiesBean.getRemoteExchange() + FIELD_SEPARATOR);
            }
        }
        if (fixPropertiesBean.getExecTransType() >= 0) {
            sb.append("20=" + fixPropertiesBean.getExecTransType() + FIELD_SEPARATOR);
        } else {
            sb.append("20=0" + FIELD_SEPARATOR);
        }
        sb.append(FIXConstant.SENDING_TIME + getFormattedDate(new Date()) + FIELD_SEPARATOR);
        sb.append(FIXConstant.ORD_ID + fixPropertiesBean.getOrderID() + FIELD_SEPARATOR);
        sb.append("17=" + fixPropertiesBean.getExecID() + FIELD_SEPARATOR);
        sb.append("150=" + fixPropertiesBean.getExecType() + FIELD_SEPARATOR);
        sb.append(FIXConstant.ORD_STATUS + fixPropertiesBean.getOrdStatus() + FIELD_SEPARATOR);
        sb.append(FIXConstant.ORD_SIDE + String.valueOf(fixPropertiesBean.getSide()).charAt(0) + FIELD_SEPARATOR);
        sb.append("151=" + fixPropertiesBean.getLeavesQty() + FIELD_SEPARATOR);
        sb.append("14=" + fixPropertiesBean.getCumQty() + FIELD_SEPARATOR);
        sb.append("6=" + fixPropertiesBean.getAvgPx() + FIELD_SEPARATOR);
        sb.append(FIXConstant.CLORD_ID + fixPropertiesBean.getClordID() + FIELD_SEPARATOR);
        if (fixPropertiesBean.getOrigClordID() != null && !"-1".equals(fixPropertiesBean.getOrigClordID())) {
            sb.append(FIXConstant.ORIG_CLORD_ID + fixPropertiesBean.getOrigClordID() + FIELD_SEPARATOR);
        }

        sb.append(FIXConstant.ORD_TYPE + fixPropertiesBean.getOrdType() + FIELD_SEPARATOR);
        sb.append(FIXConstant.PRICE + fixPropertiesBean.getPrice() + FIELD_SEPARATOR);
        sb.append(FIXConstant.TIF + fixPropertiesBean.getTimeInForce() + FIELD_SEPARATOR);
        sb.append(FIXConstant.ORD_QTY + fixPropertiesBean.getOrderQty() + FIELD_SEPARATOR);
        if (fixPropertiesBean.getStopPx() > 0) {
            sb.append("99=" + fixPropertiesBean.getStopPx() + FIELD_SEPARATOR);
        }
        if (fixPropertiesBean.getExpireTime() != null) {
            sb.append("126=" + getFormattedDate(fixPropertiesBean.getExpireTime()) + FIELD_SEPARATOR);
            sb.append("432=" + getFormattedDate(fixPropertiesBean.getExpireTime()) + FIELD_SEPARATOR);
        }
        sb.append("32=" + fixPropertiesBean.getLastShares() + FIELD_SEPARATOR);
        sb.append("31=" + fixPropertiesBean.getLastPx() + FIELD_SEPARATOR);
        if (fixPropertiesBean.getCommission() > 0) {
            sb.append("12=" + fixPropertiesBean.getCommission() + FIELD_SEPARATOR);
        }
        if (fixPropertiesBean.getMinQty() > 0) {
            sb.append("110=" + fixPropertiesBean.getMinQty() + FIELD_SEPARATOR);
        }
        if (fixPropertiesBean.getMaxFloor() > 0) {
            sb.append("111=" + fixPropertiesBean.getMaxFloor() + FIELD_SEPARATOR);
        }
        if (fixPropertiesBean.getOrdStatus() == '1' || fixPropertiesBean.getOrdStatus() == '2') {
            sb.append("64=" + getFormattedDateOnly(null) + FIELD_SEPARATOR);
        }
        sb.append(FIXConstant.TRANSACTION_TIME + getFormattedDate(new Date()) + FIELD_SEPARATOR);
        if (fixPropertiesBean.getCxlRejResponseTo() == '1' || fixPropertiesBean.getCxlRejResponseTo() == '2') {
            sb.append("434=" + fixPropertiesBean.getCxlRejResponseTo() + FIELD_SEPARATOR);
        }
        if (fixPropertiesBean.getText() != null) {
            sb.append("58=" + fixPropertiesBean.getText() + FIELD_SEPARATOR);
        }
        return sb.toString();
    }

    private String getNewOrderRequest(FIXPropertiesBean fixOrderBean) {
        StringBuilder sb = new StringBuilder();
        sb.append(FIXConstant.BEGIN_STRING + "FIX.4.2" + FIELD_SEPARATOR);
        sb.append(FIXConstant.MSG_TYPE + "D" + FIELD_SEPARATOR);
        sb.append(FIXConstant.CLORD_ID + fixOrderBean.getClordID() + FIELD_SEPARATOR);
        sb.append(FIXConstant.SENDER_COMP_ID + fixOrderBean.getTargetCompID() + FIELD_SEPARATOR);
        sb.append(FIXConstant.TARGET_COMP_ID + fixOrderBean.getSenderCompID() + FIELD_SEPARATOR);
        if(fixOrderBean.getPortfolioNo()!=null){
            sb.append(FIXConstant.PORTFOLIO_NO + fixOrderBean.getPortfolioNo() + FIELD_SEPARATOR);
        }
        sb.append(FIXConstant.TRANSACTION_TIME + getFormattedDate(new Date()) + FIELD_SEPARATOR);
        sb.append(FIXConstant.SENDING_TIME+getFormattedDate(fixOrderBean.getSendingTime())+FIELD_SEPARATOR);
        sb.append(FIXConstant.SYMBOL + fixOrderBean.getSymbol() + FIELD_SEPARATOR);
        sb.append(FIXConstant.ORD_SIDE + String.valueOf(fixOrderBean.getSide()).charAt(0) + FIELD_SEPARATOR);
        sb.append(FIXConstant.ORD_QTY + fixOrderBean.getOrderQty() + FIELD_SEPARATOR);
        sb.append(FIXConstant.ORD_TYPE + fixOrderBean.getOrdType() + FIELD_SEPARATOR);
        sb.append(FIXConstant.PRICE + fixOrderBean.getPrice() + FIELD_SEPARATOR);
        sb.append(FIXConstant.TIF + String.valueOf(fixOrderBean.getTimeInForce()).charAt(0) + FIELD_SEPARATOR);
        sb.append(FIXConstant.EXG + fixOrderBean.getSecurityExchange() + FIELD_SEPARATOR);
        sb.append("126=" + getFormattedDate(fixOrderBean.getExpireTime()) + FIELD_SEPARATOR);
        sb.append(FIXConstant.MIN_QTY + fixOrderBean.getMinQty() + FIELD_SEPARATOR);
        sb.append(FIXConstant.MAX_FLOOR + fixOrderBean.getMaxFloor() + FIELD_SEPARATOR);
        sb.append("336=" + fixOrderBean.getTradingSessionID() + FIELD_SEPARATOR);
        if(fixOrderBean.getCurrency()!=null){
            sb.append("15=" +fixOrderBean.getCurrency() + FIELD_SEPARATOR);
        }
        //todo for session qualifier 'BLOOM1' need to set symbol for the both tag 55 and tag 48 (both 22=4,5)
        if (fixOrderBean.getSecurityIDSource() != null && fixOrderBean.getSecurityIDSource().equalsIgnoreCase(REUTER_CODE)) {
            sb.append(FIXConstant.SYMBOL_CODE + fixOrderBean.getReuterCode() + FIELD_SEPARATOR);
            sb.append(FIXConstant.SYMBOL + fixOrderBean.getReuterCode() + FIELD_SEPARATOR);
        } else if (fixOrderBean.getSecurityIDSource() != null && fixOrderBean.getSecurityIDSource().equalsIgnoreCase(ISIN_CODE)) {
            sb.append(FIXConstant.SYMBOL_CODE + fixOrderBean.getIsinCode() + FIELD_SEPARATOR);
            sb.append(FIXConstant.SYMBOL + fixOrderBean.getIsinCode() + FIELD_SEPARATOR);
        }
        if (fixOrderBean.getSecurityType() != null && fixOrderBean.getSecurityType().equalsIgnoreCase(FIXConstant.OPT)) {
            sb.append("167=" + fixOrderBean.getSecurityType() + FIELD_SEPARATOR);
            sb.append("200=" + fixOrderBean.getMaturityMonthYear() + FIELD_SEPARATOR);
            sb.append("201=" + fixOrderBean.getPutOrCall() + FIELD_SEPARATOR);
            sb.append("202=" + fixOrderBean.getStrikePrice() + FIELD_SEPARATOR);
            sb.append("205=" + Integer.parseInt(fixOrderBean.getMaturityDate()) + FIELD_SEPARATOR);

            if (fixOrderBean.getSide() == FIXConstant.T54_BUY) {
                sb.append("77=O" + FIELD_SEPARATOR);
            } else if (fixOrderBean.getSide() == FIXConstant.T54_SELL) {
                sb.append("77=C" + FIELD_SEPARATOR);
            }
        }
        if (fixOrderBean.getExchangeSymbol() != null) {
            sb.append(FIXConstant.SYMBOL_CODE).append(fixOrderBean.getExchangeSymbol()).append(FIELD_SEPARATOR);
        }
        if(fixOrderBean.getSenderSubID()!=null){
            sb.append("50=").append(fixOrderBean.getSenderSubID()).append(FIELD_SEPARATOR);
        }
        if(fixOrderBean.getSenderLocationID()!=null){
            sb.append("142=").append(fixOrderBean.getSenderLocationID()).append(FIELD_SEPARATOR);
        }
        if(fixOrderBean.getText()!=null){
            sb.append(FIXConstant.TEXT).append(fixOrderBean.getText()).append(FIELD_SEPARATOR);
        }
        return sb.toString();
    }

    private String getOrderAmendRequest(FIXPropertiesBean fixOrderBean) {
        StringBuilder sb = new StringBuilder();
        sb.append(FIXConstant.BEGIN_STRING + "FIX.4.2" + FIELD_SEPARATOR);
        sb.append(FIXConstant.MSG_TYPE).append(fixOrderBean.getMessageType()).append(FIELD_SEPARATOR);
        sb.append(FIXConstant.CLORD_ID + fixOrderBean.getClordID() + FIELD_SEPARATOR);
        sb.append(FIXConstant.SENDER_COMP_ID + fixOrderBean.getTargetCompID() + FIELD_SEPARATOR);
        sb.append(FIXConstant.TARGET_COMP_ID + fixOrderBean.getSenderCompID() + FIELD_SEPARATOR);
        sb.append(FIXConstant.PORTFOLIO_NO + fixOrderBean.getPortfolioNo() + FIELD_SEPARATOR);
        sb.append(FIXConstant.ORIG_CLORD_ID + fixOrderBean.getOrigClordID() + FIELD_SEPARATOR);
        sb.append(FIXConstant.TRANSACTION_TIME + getFormattedDate(new Date()) + FIELD_SEPARATOR);
        sb.append(FIXConstant.PRICE + fixOrderBean.getPrice() + FIELD_SEPARATOR);
        sb.append(FIXConstant.SYMBOL + fixOrderBean.getSymbol() + FIELD_SEPARATOR);
        sb.append(FIXConstant.ORD_SIDE + String.valueOf(fixOrderBean.getSide()).charAt(0) + FIELD_SEPARATOR);
        sb.append(FIXConstant.ORD_QTY + fixOrderBean.getOrderQty() + FIELD_SEPARATOR);
        sb.append(FIXConstant.ORD_TYPE + fixOrderBean.getOrdType() + FIELD_SEPARATOR);
        sb.append(FIXConstant.TIF + fixOrderBean.getTimeInForce() + FIELD_SEPARATOR);
        sb.append(FIXConstant.EXG + fixOrderBean.getSecurityExchange() + FIELD_SEPARATOR);
        sb.append("336=" + fixOrderBean.getTradingSessionID() + FIELD_SEPARATOR);
        sb.append("126=" + getFormattedDate(fixOrderBean.getExpireTime()) + FIELD_SEPARATOR);
        sb.append(FIXConstant.MIN_QTY + fixOrderBean.getMinQty() + FIELD_SEPARATOR);
        sb.append(FIXConstant.MAX_FLOOR + fixOrderBean.getMaxFloor() + FIELD_SEPARATOR);
        sb.append("336=" + fixOrderBean.getTradingSessionID() + FIELD_SEPARATOR);
        if(fixOrderBean.getCurrency()!=null) {
            sb.append("15=" + fixOrderBean.getCurrency() + FIELD_SEPARATOR);
        }
        if (fixOrderBean.getSecurityIDSource() != null && fixOrderBean.getSecurityIDSource().equalsIgnoreCase(REUTER_CODE)) {
            sb.append(FIXConstant.SYMBOL_CODE + fixOrderBean.getReuterCode() + FIELD_SEPARATOR);
            sb.append(FIXConstant.SYMBOL + fixOrderBean.getReuterCode() + FIELD_SEPARATOR);
        } else if (fixOrderBean.getSecurityIDSource() != null && fixOrderBean.getSecurityIDSource().equalsIgnoreCase(ISIN_CODE)) {
            sb.append(FIXConstant.SYMBOL_CODE + fixOrderBean.getIsinCode() + FIELD_SEPARATOR);
            sb.append(FIXConstant.SYMBOL + fixOrderBean.getIsinCode() + FIELD_SEPARATOR);
        }
        if (fixOrderBean.getSecurityType()!=null&&fixOrderBean.getSecurityType().equalsIgnoreCase(FIXConstant.OPT)) {
            sb.append("167=" + fixOrderBean.getSecurityType() + FIELD_SEPARATOR);
            sb.append("200=" + fixOrderBean.getMaturityMonthYear() + FIELD_SEPARATOR);
            sb.append("201=" + fixOrderBean.getPutOrCall() + FIELD_SEPARATOR);
            sb.append("202=" + fixOrderBean.getStrikePrice() + FIELD_SEPARATOR);
            sb.append("205=" + Integer.parseInt(fixOrderBean.getMaturityDate()) + FIELD_SEPARATOR);

            if (fixOrderBean.getSide() == FIXConstant.T54_BUY) {
                sb.append("77=O" + FIELD_SEPARATOR);
            } else if (fixOrderBean.getSide() == FIXConstant.T54_SELL) {
                sb.append("77=C" + FIELD_SEPARATOR);
            }
        }
        if (fixOrderBean.getOrderID() != null && !("").equals(fixOrderBean.getOrderID())) {
            sb.append(FIXConstant.ORD_ID + fixOrderBean.getOrderID() + FIELD_SEPARATOR);
        }
        if (fixOrderBean.getExchangeSymbol() != null) {
            sb.append(FIXConstant.SYMBOL_CODE).append(fixOrderBean.getExchangeSymbol()).append(FIELD_SEPARATOR);
        }
        if(fixOrderBean.getSenderSubID()!=null){
            sb.append("50=").append(fixOrderBean.getSenderSubID()).append(FIELD_SEPARATOR);
        }
        if(fixOrderBean.getSenderLocationID()!=null){
            sb.append("142=").append(fixOrderBean.getSenderLocationID()).append(FIELD_SEPARATOR);
        }
        return sb.toString();
    }

    private String getOrderCancelRequest(FIXPropertiesBean fixOrderBean) {
        StringBuilder sb = new StringBuilder();
        sb.append(FIXConstant.BEGIN_STRING + "FIX.4.2" + FIELD_SEPARATOR);
        sb.append(FIXConstant.MSG_TYPE + fixOrderBean.getMessageType() + FIELD_SEPARATOR);
        sb.append(FIXConstant.CLORD_ID + fixOrderBean.getClordID() + FIELD_SEPARATOR);
        sb.append(FIXConstant.SENDER_COMP_ID + fixOrderBean.getTargetCompID() + FIELD_SEPARATOR);
        sb.append(FIXConstant.TARGET_COMP_ID + fixOrderBean.getSenderCompID() + FIELD_SEPARATOR);
        sb.append(FIXConstant.PORTFOLIO_NO + fixOrderBean.getPortfolioNo() + FIELD_SEPARATOR);
        sb.append(FIXConstant.ORIG_CLORD_ID + fixOrderBean.getOrigClordID() + FIELD_SEPARATOR);
        sb.append(FIXConstant.TRANSACTION_TIME + getFormattedDate(new Date()) + FIELD_SEPARATOR);
        sb.append(FIXConstant.SYMBOL + fixOrderBean.getSymbol() + FIELD_SEPARATOR);
        sb.append(FIXConstant.EXG + fixOrderBean.getSecurityExchange() + FIELD_SEPARATOR);
        sb.append(FIXConstant.ORD_SIDE + String.valueOf(fixOrderBean.getSide()).charAt(0) + FIELD_SEPARATOR);
        sb.append(FIXConstant.ORD_QTY + fixOrderBean.getOrderQty() + FIELD_SEPARATOR);
        if (fixOrderBean.getSecurityIDSource() != null && fixOrderBean.getSecurityIDSource().equalsIgnoreCase(REUTER_CODE)) {
            sb.append(FIXConstant.SYMBOL_CODE + fixOrderBean.getReuterCode() + FIELD_SEPARATOR);
            sb.append(FIXConstant.SYMBOL + fixOrderBean.getReuterCode() + FIELD_SEPARATOR);
        } else if (fixOrderBean.getSecurityIDSource() != null && fixOrderBean.getSecurityIDSource().equalsIgnoreCase(ISIN_CODE)) {
            sb.append(FIXConstant.SYMBOL_CODE + fixOrderBean.getIsinCode() + FIELD_SEPARATOR);
            sb.append(FIXConstant.SYMBOL + fixOrderBean.getIsinCode() + FIELD_SEPARATOR);
        }
        if (fixOrderBean.getOrderID() != null && !("").equals(fixOrderBean.getOrderID())) {
            sb.append(FIXConstant.ORD_ID + fixOrderBean.getOrderID() + FIELD_SEPARATOR);
        }
        if (fixOrderBean.getExchangeSymbol() != null) {
            sb.append(FIXConstant.SYMBOL_CODE).append(fixOrderBean.getExchangeSymbol()).append(FIELD_SEPARATOR);
        }
        if(fixOrderBean.getSenderSubID()!=null){
            sb.append("50=").append(fixOrderBean.getSenderSubID()).append(FIELD_SEPARATOR);
        }
        if(fixOrderBean.getSenderLocationID()!=null){
            sb.append("142=").append(fixOrderBean.getSenderLocationID()).append(FIELD_SEPARATOR);
        }
        return sb.toString();
    }

    private String getFormattedDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd-HH:mm:ss");
        if (date != null) {
            return simpleDateFormat.format(date);
        } else {
            return simpleDateFormat.format(new Date());
        }

    }

    private String getFormattedDateOnly(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        if (date != null) {
            return simpleDateFormat.format(date);
        } else {
            return simpleDateFormat.format(new Date());
        }

    }


}
