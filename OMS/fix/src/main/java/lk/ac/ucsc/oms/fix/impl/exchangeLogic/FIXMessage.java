package lk.ac.ucsc.oms.fix.impl.exchangeLogic;

import lk.ac.ucsc.oms.common_utility.api.enums.OrderType;
import lk.ac.ucsc.oms.fix.api.beans.FixOrderInterface;
import lk.ac.ucsc.oms.fix.api.exception.FIXOrderException;
import lk.ac.ucsc.oms.fix.impl.exchangeLogic.fixMapper.FieldMap;
import lk.ac.ucsc.oms.fix.impl.util.ErrorMessageGenerator;
import lk.ac.ucsc.oms.fix.impl.util.FIXConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static lk.ac.ucsc.oms.fix.impl.util.FIXConstants.*;


public class FIXMessage {
    private static Logger logger = LogManager.getLogger(FIXMessage.class);

    private static final int MESSAGE_TYPE_NOT_SUPPORTED = 3;
    private SimpleDateFormat longDateFormat;
    private SimpleDateFormat shortDateFormat;
    private static final String MESSAGE_TYPE = "35=";   // constant message type
    private static final String SENDER_COMP_ID = "49=";   // sender comp id
    private static final String TARGET_COMP_ID = "56=";   // target comp id
    private static final String CLORD_ID = "11=";   // clord id
    private static final String SYMBOL = "55=";   // symbol
    private static final String ORD_SIDE = "54=";   // order side
    private static final String ORD_QTY = "38=";    // order quantity
    private static final String EXG = "207=";  // security exchange
    private static final String ORD_ID = "37=";    // order id
    private static final String TRANSACTION_TIME = "60=";    //transaction time
    private static final String MIN_QTY = "110=";    // Minimum Quantity
    private static final String MAX_FLOOR = "111=";    // Maximum floor
    private static final String PORTFOLIO_NO = "1=";    // portfolio number
    private static final String SETTLEMENT_TYP = "63=";    // settlement type
    private static final String ORD_TYPE = "40=";    // order type
    private static final String PRICE = "44=";   //price
    private static final String TIF = "59=";   //time in force value
    private static final String SYMBOL_CODE = "48=";   // symbol code
    private static final String ORIG_CLORD_ID = "41=";   // original cl order id
    private static final String ORD_STATUS = "39=";   // order status
    private static final String SENDER_SUBID = "50=";   // sender sub id
    private static final String TARGET_SUBID = "57=";   // target sub id
    private static final String DELIV_COMP_ID = "128=";   // deliver to comp id
    private static final String DELIV_SUB_ID = "129=";   // deliver to sub id
    private static final String EXPIRE_DATE = "126=";   // expire date
    private static final String MARKET_CODE = "336=";   // market code
    private static final String CURRENCY = "15=";   // currency
    private static final String EXPIRE_TIME = "432=";   // expire time
    private static final String STOP_PX = "99=";   // stop price
    private static final String SECURITY_ID_SOURCE = "22=";   // security id source
    private static final String SECURITY_TYPE = "167=";   // security type


    private String getNewOrderRequest(FixOrderInterface fixOrderBean) throws FIXOrderException {
        StringBuilder sb = new StringBuilder();
        //tag 35
        sb.append(MESSAGE_TYPE + fixOrderBean.getMessageType().getCode() + FIXConstants.FIELD_SEPERATOR);
        // tag 11
        sb.append(CLORD_ID + fixOrderBean.getClordID() + FIXConstants.FIELD_SEPERATOR);
        // tag 1 portfolio number/routing account number
        sb.append(PORTFOLIO_NO + fixOrderBean.getPortfolioNo() + FIXConstants.FIELD_SEPERATOR);
        // tag 60 order transaction time
        sb.append(TRANSACTION_TIME + formatDate(null, false) + FIXConstants.FIELD_SEPERATOR);
        // tag 54 order side
        sb.append(ORD_SIDE + String.valueOf(fixOrderBean.getSide()).charAt(0) + FIXConstants.FIELD_SEPERATOR);
        // tag 38 qty
        sb.append(ORD_QTY +((int)fixOrderBean.getOrderQty()) + FIXConstants.FIELD_SEPERATOR);
        // tag 40 order type
        sb.append(ORD_TYPE + fixOrderBean.getOrdType() + FIXConstants.FIELD_SEPERATOR);
        // tag 44 price is set if and only if order type is LIMIT
        if (fixOrderBean.getOrdType() == OrderType.LIMIT.getCode().charAt(0)) {
            sb.append(PRICE + fixOrderBean.getPrice() + FIXConstants.FIELD_SEPERATOR);
        }
        // tag 59 order tif
        sb.append(TIF + String.valueOf(fixOrderBean.getTimeInForce()).charAt(0) + FIXConstants.FIELD_SEPERATOR);
        // tag 207
        sb.append(EXG + fixOrderBean.getSecurityExchange() + FIXConstants.FIELD_SEPERATOR);
        // tag 126 expire time
        if (fixOrderBean.getExpireDate() != null) {
            sb.append(EXPIRE_DATE + formatDate(fixOrderBean.getExpireDate(), false) + FIXConstants.FIELD_SEPERATOR);
        }
        // tag 110 is set if and only if min qty has a value
        if (fixOrderBean.getMinQty() > 0) {
            sb.append(MIN_QTY + fixOrderBean.getMinQty() + FIXConstants.FIELD_SEPERATOR);
        }
        // tag 111 max floor is set order has valid max floor value
        if (fixOrderBean.getMaxFloor() > 0) {
            sb.append(MAX_FLOOR + fixOrderBean.getMaxFloor() + FIXConstants.FIELD_SEPERATOR);
        }
        // tag 336
        sb.append(MARKET_CODE + fixOrderBean.getTradingSessionID() + FIXConstants.FIELD_SEPERATOR);
        // tag 15
        sb.append(CURRENCY + fixOrderBean.getCurrency() + FIXConstants.FIELD_SEPERATOR);

        // if the order security type is option set the following tags
        if (fixOrderBean.getSecurityType().equalsIgnoreCase(FIXConstants.OPT)) {
            //tag 167 security type
            sb.append(SECURITY_TYPE + fixOrderBean.getSecurityType() + FIXConstants.FIELD_SEPERATOR);
            sb.append("200=" + fixOrderBean.getMaturityMonthYear() + FIXConstants.FIELD_SEPERATOR);
            sb.append("201=" + fixOrderBean.getPutOrCall() + FIXConstants.FIELD_SEPERATOR);
            sb.append("202=" + fixOrderBean.getStrikePrice() + FIXConstants.FIELD_SEPERATOR);
            sb.append("205=" + Integer.parseInt(fixOrderBean.getMaturityDate()) + FIXConstants.FIELD_SEPERATOR);

            if (fixOrderBean.getSide() == FIXConstants.T54_BUY) {
                sb.append("77=O" + FIXConstants.FIELD_SEPERATOR);
            } else if (fixOrderBean.getSide() == FIXConstants.T54_SELL) {
                sb.append("77=C" + FIXConstants.FIELD_SEPERATOR);
            }
        }
        // tag 63 , settlement type normally set for CASE only
        if (fixOrderBean.getSettlementType() > 0) {
            sb.append(SETTLEMENT_TYP + fixOrderBean.getSettlementType() + FIXConstants.FIELD_SEPERATOR);
        }
        // tag 432 for GTD orders
        if (fixOrderBean.getExpireTime() != null) {
            sb.append(EXPIRE_TIME + formatDate(fixOrderBean.getExpireTime(), true) + FIXConstants.FIELD_SEPERATOR);
        }
        // tag 99 is set for stop loss type orders
        if (fixOrderBean.getOrdType() == OrderType.STOP_LOSS.getCode().charAt(0)) {
            if (fixOrderBean.getStopPx() != 0) {
                sb.append(STOP_PX + fixOrderBean.getStopPx() + FIXConstants.FIELD_SEPERATOR);
            }
        } else if (fixOrderBean.getOrdType() == OrderType.STOP_LIMIT.getCode().charAt(0)) {
            //tag 99 and price is set of stop limit type orders
            sb.append(PRICE + fixOrderBean.getPrice() + FIXConstants.FIELD_SEPERATOR);
            sb.append(STOP_PX + fixOrderBean.getStopPx() + FIXConstants.FIELD_SEPERATOR);
        }
        //method populate security id source details
        populateSecurityIDSourceDetails(sb, fixOrderBean);

        return sb.toString();
    }


    private String getOrderAmendRequest(FixOrderInterface fixOrderBean) throws FIXOrderException {
        StringBuilder sb = new StringBuilder();
        // tag 35 value
        sb.append(MESSAGE_TYPE).append(fixOrderBean.getMessageType().getCode()).append(FIXConstants.FIELD_SEPERATOR);
        // tag 11 value
        sb.append(CLORD_ID + fixOrderBean.getClordID() + FIXConstants.FIELD_SEPERATOR);
        // tag 1 value
        sb.append(PORTFOLIO_NO + fixOrderBean.getPortfolioNo() + FIXConstants.FIELD_SEPERATOR);
        // tag 41 value
        sb.append(ORIG_CLORD_ID + fixOrderBean.getOrigClordID() + FIXConstants.FIELD_SEPERATOR);
        // tag 60
        sb.append(TRANSACTION_TIME + formatDate(null, false) + FIXConstants.FIELD_SEPERATOR);
        // tag 44 price set only for LIMIT orders
        if (fixOrderBean.getOrdType() == OrderType.LIMIT.getCode().charAt(0)) {
            sb.append(PRICE + fixOrderBean.getPrice() + FIXConstants.FIELD_SEPERATOR);
        }
        // tag 54
        sb.append(ORD_SIDE + String.valueOf(fixOrderBean.getSide()).charAt(0) + FIXConstants.FIELD_SEPERATOR);
        // tag 38
        sb.append(ORD_QTY + fixOrderBean.getOrderQty() + FIXConstants.FIELD_SEPERATOR);
        // tag 40
        sb.append(ORD_TYPE + fixOrderBean.getOrdType() + FIXConstants.FIELD_SEPERATOR);
        // tag 59
        sb.append(TIF + fixOrderBean.getTimeInForce() + FIXConstants.FIELD_SEPERATOR);
        // tag 207
        sb.append(EXG + fixOrderBean.getSecurityExchange() + FIXConstants.FIELD_SEPERATOR);
        // tag 336
        sb.append(MARKET_CODE + fixOrderBean.getTradingSessionID() + FIXConstants.FIELD_SEPERATOR);
        // tag 126
        if (fixOrderBean.getExpireDate() != null) {
            sb.append(EXPIRE_DATE + formatDate(fixOrderBean.getExpireDate(), false) + FIXConstants.FIELD_SEPERATOR);
        }
        // tag 110
        if (fixOrderBean.getMinQty() > 0) {
            sb.append(MIN_QTY + fixOrderBean.getMinQty() + FIXConstants.FIELD_SEPERATOR);
        }
        // tag 111 set for old fix version, tag 1138 set for new fix API 5.1.X, have to exclude 111 & 1138
        if (fixOrderBean.getMaxFloor() > 0) {
            sb.append(MAX_FLOOR + fixOrderBean.getMaxFloor() + FIXConstants.FIELD_SEPERATOR);
        }
        // tag 15
        sb.append(CURRENCY + fixOrderBean.getCurrency() + FIXConstants.FIELD_SEPERATOR);
        // option related values set here
        if (fixOrderBean.getSecurityType().equalsIgnoreCase(FIXConstants.OPT)) {
            sb.append("167=" + fixOrderBean.getSecurityType() + FIXConstants.FIELD_SEPERATOR);
            sb.append("200=" + fixOrderBean.getMaturityMonthYear() + FIXConstants.FIELD_SEPERATOR);
            sb.append("201=" + fixOrderBean.getPutOrCall() + FIXConstants.FIELD_SEPERATOR);
            sb.append("202=" + fixOrderBean.getStrikePrice() + FIXConstants.FIELD_SEPERATOR);
            sb.append("205=" + Integer.parseInt(fixOrderBean.getMaturityDate()) + FIXConstants.FIELD_SEPERATOR);

            if (fixOrderBean.getSide() == FIXConstants.T54_BUY) {
                sb.append("77=O" + FIXConstants.FIELD_SEPERATOR);
            } else if (fixOrderBean.getSide() == FIXConstants.T54_SELL) {
                sb.append("77=C" + FIXConstants.FIELD_SEPERATOR);
            }
        }
        // tag 37 order id
        if (fixOrderBean.getOrderID() != null && !("").equals(fixOrderBean.getOrderID())) {
            sb.append(ORD_ID + fixOrderBean.getOrderID() + FIXConstants.FIELD_SEPERATOR);
        }
        // tag 63 is for KSE
        if (fixOrderBean.getSettlementType() > 0) {
            sb.append(SETTLEMENT_TYP + fixOrderBean.getSettlementType() + FIXConstants.FIELD_SEPERATOR);
        }
        // tag 432
        if (fixOrderBean.getExpireTime() != null) {
            sb.append(EXPIRE_TIME + formatDate(fixOrderBean.getExpireTime(), true) + FIXConstants.FIELD_SEPERATOR);
        }
        // tag 99 is set for stop loss type orders
        if (fixOrderBean.getOrdType() == OrderType.STOP_LOSS.getCode().charAt(0)) {
            if (fixOrderBean.getStopPx() != 0) {
                sb.append(STOP_PX + fixOrderBean.getStopPx() + FIXConstants.FIELD_SEPERATOR);
            }
        } else if (fixOrderBean.getOrdType() == OrderType.STOP_LIMIT.getCode().charAt(0)) {
            //tag 99 and price is set of stop limit type orders
            sb.append(PRICE + fixOrderBean.getPrice() + FIXConstants.FIELD_SEPERATOR);
            sb.append(STOP_PX + fixOrderBean.getStopPx() + FIXConstants.FIELD_SEPERATOR);
        }
        //method populate security id source details
        populateSecurityIDSourceDetails(sb, fixOrderBean);

        return sb.toString();
    }

    private StringBuilder populateSecurityIDSourceDetails(StringBuilder sb, FixOrderInterface fixOrderBean) {
        // check the tag 22 value and set the symbol, codes accordingly
        // tag 22=5
        if (fixOrderBean.getSecurityIDSource() != null && REUTER_CODE.equalsIgnoreCase(fixOrderBean.getSecurityIDSource())) {
            //tag 48
            sb.append(SYMBOL_CODE + fixOrderBean.getReuterCode() + FIXConstants.FIELD_SEPERATOR);
            // tag 55
            sb.append(SYMBOL + fixOrderBean.getReuterCode() + FIXConstants.FIELD_SEPERATOR);
            // tag 22
            sb.append(SECURITY_ID_SOURCE + fixOrderBean.getSecurityIDSource() + FIXConstants.FIELD_SEPERATOR);
        } else if (fixOrderBean.getSecurityIDSource() != null && ISIN_CODE.equalsIgnoreCase(fixOrderBean.getSecurityIDSource())) {       //tag 22=4 scenario
            // tag 48
            sb.append(SYMBOL_CODE + fixOrderBean.getIsinCode() + FIXConstants.FIELD_SEPERATOR);
            // tag 55
            sb.append(SYMBOL + fixOrderBean.getIsinCode() + FIXConstants.FIELD_SEPERATOR);
            // tag 22
            sb.append(SECURITY_ID_SOURCE + fixOrderBean.getSecurityIDSource() + FIXConstants.FIELD_SEPERATOR);
        } else if (fixOrderBean.getSecurityIDSource() != null && ("8").equalsIgnoreCase(fixOrderBean.getSecurityIDSource())) {  // tag 22=8
            // tag 55
            sb.append(SYMBOL + fixOrderBean.getSymbol() + FIXConstants.FIELD_SEPERATOR);
            // tag 22
            sb.append(SECURITY_ID_SOURCE + fixOrderBean.getSecurityIDSource() + FIXConstants.FIELD_SEPERATOR);
        } else if (fixOrderBean.getSecurityIDSource() != null && ("0").equalsIgnoreCase(fixOrderBean.getSecurityIDSource())) {  // tag 22=0
            // tag 55
            sb.append(SYMBOL + fixOrderBean.getExchangeSymbol() + FIXConstants.FIELD_SEPERATOR);
            // tag 22
            sb.append(SECURITY_ID_SOURCE + "8" + FIXConstants.FIELD_SEPERATOR);
        } else if (fixOrderBean.getSecurityIDSource() != null && ("2").equalsIgnoreCase(fixOrderBean.getSecurityIDSource())) {  // tag 22=2
            // tag 55
            sb.append(SYMBOL + fixOrderBean.getExchangeSymbol() + FIXConstants.FIELD_SEPERATOR);
            // tag 22
            sb.append(SECURITY_ID_SOURCE + fixOrderBean.getSecurityIDSource() + FIXConstants.FIELD_SEPERATOR);
        } else {
            // tag 55
            sb.append(SYMBOL + fixOrderBean.getSymbol() + FIXConstants.FIELD_SEPERATOR);
            //if the exchange symbol is not null, then tag 48= exchange symbol. this is for KSE only
            if (fixOrderBean.getExchangeSymbol() != null) {
                sb.append(SYMBOL_CODE + fixOrderBean.getExchangeSymbol() + FIXConstants.FIELD_SEPERATOR);
            }
        }
        return sb;
    }

    private String getOrderCancelRequest(FixOrderInterface fixOrderBean) throws FIXOrderException {
        StringBuilder sb = new StringBuilder();
        // tag 35
        sb.append(MESSAGE_TYPE + fixOrderBean.getMessageType().getCode() + FIXConstants.FIELD_SEPERATOR);
        // tag 11
        sb.append(CLORD_ID + fixOrderBean.getClordID() + FIXConstants.FIELD_SEPERATOR);
        // tag 1
        sb.append(PORTFOLIO_NO + fixOrderBean.getPortfolioNo() + FIXConstants.FIELD_SEPERATOR);
        // tag 41
        sb.append(ORIG_CLORD_ID + fixOrderBean.getOrigClordID() + FIXConstants.FIELD_SEPERATOR);
        // tag 60
        sb.append(TRANSACTION_TIME + formatDate(null, false) + FIXConstants.FIELD_SEPERATOR);
        // tag 207
        sb.append(EXG + fixOrderBean.getSecurityExchange() + FIXConstants.FIELD_SEPERATOR);
        // tag 54
        sb.append(ORD_SIDE + String.valueOf(fixOrderBean.getSide()).charAt(0) + FIXConstants.FIELD_SEPERATOR);
        // tag 38
        sb.append(ORD_QTY + fixOrderBean.getOrderQty() + FIXConstants.FIELD_SEPERATOR);
        //  tag 37
        if (fixOrderBean.getOrderID() != null && !("").equals(fixOrderBean.getOrderID())) {
            sb.append(ORD_ID + fixOrderBean.getOrderID() + FIXConstants.FIELD_SEPERATOR);
        }
        //method populate security id source details
        populateSecurityIDSourceDetails(sb, fixOrderBean);

        return sb.toString();
    }


    private String getMarketStatusRequest(FixOrderInterface fixOrderBean) throws FIXOrderException {
        StringBuilder sb = new StringBuilder();
        // tag 35
        sb.append(MESSAGE_TYPE + fixOrderBean.getMessageType().getCode() + FIXConstants.FIELD_SEPERATOR);
        // tag 49
        if (fixOrderBean.getSenderCompID() != null) {
            sb.append(SENDER_COMP_ID + fixOrderBean.getSenderCompID() + FIXConstants.FIELD_SEPERATOR);
        }
        // tag 56
        if (fixOrderBean.getTargetCompID() != null) {
            sb.append(TARGET_COMP_ID + fixOrderBean.getTargetCompID() + FIXConstants.FIELD_SEPERATOR);
        }
        sb.append("335=" + fixOrderBean.getTradSesReqID() + FIXConstants.FIELD_SEPERATOR);
        sb.append("336=" + fixOrderBean.getTradingSessionID() + FIXConstants.FIELD_SEPERATOR);
        sb.append("263=" + fixOrderBean.getSubscriptionRequestType() + FIXConstants.FIELD_SEPERATOR);
        sb.append("338=" + fixOrderBean.getTradSesMethod() + FIXConstants.FIELD_SEPERATOR);

        return sb.toString();
    }

    private String getBuySideExecutionReport(FixOrderInterface fixOrderBean) throws FIXOrderException {
        StringBuilder sb = new StringBuilder();
        sb.append(MESSAGE_TYPE + fixOrderBean.getMessageType().getCode() + FIXConstants.FIELD_SEPERATOR);
        if (fixOrderBean.getTargetCompID() != null) {
            sb.append(SENDER_COMP_ID + fixOrderBean.getTargetCompID() + FIXConstants.FIELD_SEPERATOR);
        }
        if (fixOrderBean.getSenderCompID() != null) {
            sb.append(TARGET_COMP_ID + fixOrderBean.getSenderCompID() + FIXConstants.FIELD_SEPERATOR);
        }
        if (fixOrderBean.getSenderSubID() != null) {
            sb.append(TARGET_SUBID + fixOrderBean.getSenderSubID() + FIXConstants.FIELD_SEPERATOR);
        }
        if (fixOrderBean.getTargetSubID() != null) {
            sb.append(SENDER_SUBID + fixOrderBean.getTargetSubID() + FIXConstants.FIELD_SEPERATOR);
        }
        if (fixOrderBean.getOnBehalfOfCompID() != null) {
            sb.append(DELIV_COMP_ID + fixOrderBean.getOnBehalfOfCompID() + FIXConstants.FIELD_SEPERATOR);
        }
        if (fixOrderBean.getOnBehalfOfSubID() != null) {
            sb.append(DELIV_SUB_ID + fixOrderBean.getOnBehalfOfSubID() + FIXConstants.FIELD_SEPERATOR);
        }
        if (fixOrderBean.getPortfolioNo() != null && !("-1").equalsIgnoreCase(fixOrderBean.getPortfolioNo()) && !("").equalsIgnoreCase(fixOrderBean.getPortfolioNo())) {
            sb.append(PORTFOLIO_NO + fixOrderBean.getPortfolioNo() + FIXConstants.FIELD_SEPERATOR);
        }
        if (fixOrderBean.getSecurityIDSource() != null) {
            sb.append(SECURITY_ID_SOURCE + fixOrderBean.getSecurityIDSource() + FIXConstants.FIELD_SEPERATOR);
        }
        //if the security id source is null,then not reuter code or isin code associated with the order
        if ((fixOrderBean.getSecurityIDSource() == null) || (fixOrderBean.getSecurityIDSource() != null && fixOrderBean.getSecurityIDSource().length() <= 0)) {
            sb.append(EXG + fixOrderBean.getSecurityExchange() + FIXConstants.FIELD_SEPERATOR);
            sb.append(SYMBOL + fixOrderBean.getSymbol() + FIXConstants.FIELD_SEPERATOR);
        } else {
            //for the fix channel orders always keep the remote symbol, remote security id, remote exchange
            sb.append(SYMBOL + fixOrderBean.getRemoteSymbol() + FIXConstants.FIELD_SEPERATOR);

            if (fixOrderBean.getRemoteSecurityID() != null && fixOrderBean.getRemoteSecurityID().length() > 0) {
                sb.append(SYMBOL_CODE + fixOrderBean.getRemoteSecurityID() + FIXConstants.FIELD_SEPERATOR);
            } else {
                sb.append(SYMBOL_CODE + fixOrderBean.getRemoteSymbol() + FIXConstants.FIELD_SEPERATOR);
            }
            if (fixOrderBean.getRemoteExchange() != null) {
                sb.append(EXG + fixOrderBean.getRemoteExchange() + FIXConstants.FIELD_SEPERATOR);
            }
        }
        if (fixOrderBean.getExecTransType() >= 0) {
            sb.append("20=" + fixOrderBean.getExecTransType() + FIXConstants.FIELD_SEPERATOR);
        } else {
            sb.append("20=0" + FIXConstants.FIELD_SEPERATOR);
        }
        sb.append(ORD_ID + fixOrderBean.getOrderID() + FIXConstants.FIELD_SEPERATOR);
        sb.append("17=" + fixOrderBean.getExecID() + FIXConstants.FIELD_SEPERATOR);
        sb.append("150=" + fixOrderBean.getExecType() + FIXConstants.FIELD_SEPERATOR);
        sb.append(ORD_STATUS + fixOrderBean.getOrdStatus() + FIXConstants.FIELD_SEPERATOR);
        sb.append(ORD_SIDE + String.valueOf(fixOrderBean.getSide()).charAt(0) + FIXConstants.FIELD_SEPERATOR);
        sb.append("151=" + fixOrderBean.getLeavesQty() + FIXConstants.FIELD_SEPERATOR);
        sb.append("14=" + fixOrderBean.getCumQty() + FIXConstants.FIELD_SEPERATOR);
        sb.append("6=" + fixOrderBean.getAvgPx() + FIXConstants.FIELD_SEPERATOR);
        sb.append(CLORD_ID + fixOrderBean.getClordID() + FIXConstants.FIELD_SEPERATOR);
        if (fixOrderBean.getOrigClordID() != null && !("-1").equalsIgnoreCase(fixOrderBean.getOrigClordID())) {
            sb.append(ORIG_CLORD_ID + fixOrderBean.getOrigClordID() + FIXConstants.FIELD_SEPERATOR);
        }

        sb.append(ORD_TYPE + fixOrderBean.getOrdType() + FIXConstants.FIELD_SEPERATOR);
        sb.append(PRICE + fixOrderBean.getPrice() + FIXConstants.FIELD_SEPERATOR);
        sb.append(TIF + fixOrderBean.getTimeInForce() + FIXConstants.FIELD_SEPERATOR);
        sb.append(ORD_QTY + fixOrderBean.getOrderQty() + FIXConstants.FIELD_SEPERATOR);
        if (fixOrderBean.getStopPx() > 0) {
            sb.append(STOP_PX + fixOrderBean.getStopPx() + FIXConstants.FIELD_SEPERATOR);
        }
        if (fixOrderBean.getExpireTime() != null) {
            sb.append("126=" + formatDate(fixOrderBean.getExpireTime(), false) + FIXConstants.FIELD_SEPERATOR);
            sb.append("432=" + formatDate(fixOrderBean.getExpireTime(), true) + FIXConstants.FIELD_SEPERATOR);
        }
        sb.append("32=" + fixOrderBean.getLastShares() + FIXConstants.FIELD_SEPERATOR);
        sb.append("31=" + fixOrderBean.getLastPx() + FIXConstants.FIELD_SEPERATOR);
        if (fixOrderBean.getCommission() > 0) {
            sb.append("12=" + fixOrderBean.getCommission() + FIXConstants.FIELD_SEPERATOR);
        }
        if (fixOrderBean.getMinQty() > 0) {
            sb.append("110=" + fixOrderBean.getMinQty() + FIXConstants.FIELD_SEPERATOR);
        }
        if (fixOrderBean.getMaxFloor() > 0) {
            sb.append("111=" + fixOrderBean.getMaxFloor() + FIXConstants.FIELD_SEPERATOR);
        }
        if (fixOrderBean.getText() != null) {
            sb.append("58=" + fixOrderBean.getText() + FIXConstants.FIELD_SEPERATOR);
        }
        if (fixOrderBean.getOrdStatus() == T39_PARTIALLY_FILLED || fixOrderBean.getOrdStatus() == T39_FILLED) {
            sb.append("64=" + formatDate(null, true) + FIXConstants.FIELD_SEPERATOR);
        }
        sb.append(TRANSACTION_TIME + formatDate(null, false) + FIXConstants.FIELD_SEPERATOR);

        return sb.toString();
    }


    private String getBuySideRejectMessage(FixOrderInterface fixOrderBean) throws FIXOrderException {
        StringBuilder sb = new StringBuilder();
        sb.append(MESSAGE_TYPE + fixOrderBean.getMessageType().getCode() + FIXConstants.FIELD_SEPERATOR);
        if (fixOrderBean.getTargetCompID() != null) {
            sb.append(SENDER_COMP_ID + fixOrderBean.getTargetCompID() + FIXConstants.FIELD_SEPERATOR);
        }
        if (fixOrderBean.getSenderCompID() != null) {
            sb.append(TARGET_COMP_ID + fixOrderBean.getSenderCompID() + FIXConstants.FIELD_SEPERATOR);
        }
        if (fixOrderBean.getSenderSubID() != null) {
            sb.append(TARGET_SUBID + fixOrderBean.getSenderSubID() + FIXConstants.FIELD_SEPERATOR);
        }
        if (fixOrderBean.getTargetSubID() != null) {
            sb.append(SENDER_SUBID + fixOrderBean.getTargetSubID() + FIXConstants.FIELD_SEPERATOR);
        }
        if (fixOrderBean.getOnBehalfOfCompID() != null) {
            sb.append(DELIV_COMP_ID + fixOrderBean.getOnBehalfOfCompID() + FIXConstants.FIELD_SEPERATOR);
        }
        if (fixOrderBean.getOnBehalfOfSubID() != null) {
            sb.append(DELIV_SUB_ID + fixOrderBean.getOnBehalfOfSubID() + FIXConstants.FIELD_SEPERATOR);
        }
        if (fixOrderBean.getOrderID() != null && (!("").equalsIgnoreCase(fixOrderBean.getOrderID()) ||
                !("-1").equalsIgnoreCase(fixOrderBean.getOrderID()))) {
            sb.append(ORD_ID + fixOrderBean.getOrderID() + FIXConstants.FIELD_SEPERATOR);
        } else {
            sb.append(ORD_ID + fixOrderBean.getClordID() + FIXConstants.FIELD_SEPERATOR);
        }
        sb.append(ORD_STATUS + fixOrderBean.getOrdStatus() + FIXConstants.FIELD_SEPERATOR);
        if (fixOrderBean.getRemoteClOrdID() != null) {
            sb.append(CLORD_ID + fixOrderBean.getRemoteClOrdID() + FIXConstants.FIELD_SEPERATOR);
        }
        if (fixOrderBean.getRemoteOrigClOrdID() != null) {
            sb.append(ORIG_CLORD_ID + fixOrderBean.getRemoteOrigClOrdID() + FIXConstants.FIELD_SEPERATOR);
        }
        if (fixOrderBean.getPortfolioNo() != null) {
            sb.append(PORTFOLIO_NO + fixOrderBean.getPortfolioNo() + FIXConstants.FIELD_SEPERATOR);
        }
        sb.append("434=" + fixOrderBean.getCxlRejResponseTo() + FIXConstants.FIELD_SEPERATOR);
        if (fixOrderBean.getTransactTime() != null) {
            sb.append(TRANSACTION_TIME + fixOrderBean.getTransactTime() + FIXConstants.FIELD_SEPERATOR);
        }
        if (fixOrderBean.getText() != null) {
            sb.append("58=" + fixOrderBean.getText() + FIXConstants.FIELD_SEPERATOR);
        }
        return sb.toString();
    }

    public synchronized String getFixMessage(FixOrderInterface orderBean, Map<Integer, String> customFields,
                                             Map<Integer, Integer> replaceFields, List<Integer> tagSequence) throws FIXOrderException {
        String msg = null;
        String fixMessage;
        switch (orderBean.getMessageType()) {
            case NEW_ORDER_SINGLE:
                msg = getNewOrderRequest(orderBean);
                break;
            case ORDER_CANCEL_REQUEST:
                msg = getOrderCancelRequest(orderBean);
                break;
            case ORDER_CANCEL_REPLACE:
                msg = getOrderAmendRequest(orderBean);
                break;
            case TRADING_SESSION_STATUS_REQUEST:
                msg = getMarketStatusRequest(orderBean);
                break;
            case EXECUTION_REPORT:
            case ACCEPT:
            case AMEND_ACCEPT:
            case CANCEL_ACCEPT:
                msg = getBuySideExecutionReport(orderBean);
                break;
            case ORDER_CANCEL_REJECT:
                msg = getBuySideRejectMessage(orderBean);
                break;
            default:
                throw new FIXOrderException(ErrorMessageGenerator.getErrorMsg(MESSAGE_TYPE_NOT_SUPPORTED));
        }
        FieldMap mapper = new FieldMap(customFields, replaceFields, tagSequence, msg);
        fixMessage = mapper.getFixString();

        return fixMessage;
    }

    private String formatDate(Date date, boolean isShortFormat) throws FIXOrderException {
        String dateString = null;
        try {
            if (isShortFormat) {
                if (date != null) {
                    dateString = shortDateFormat.format(date);
                } else {
                    dateString = shortDateFormat.format(new Date(System.currentTimeMillis()));
                }
            } else {
                if (date != null) {
                    dateString = longDateFormat.format(date);
                } else {
                    dateString = longDateFormat.format(new Date(System.currentTimeMillis()));
                }
            }
        } catch (Exception e) {
            logger.error("Invalid date format - {}", e.toString());
            throw new FIXOrderException("Invalid date format");
        }
        return dateString;
    }

    public void setShortDateFormat(SimpleDateFormat shortDateFormat) {
        this.shortDateFormat = shortDateFormat;
    }


    public void setLongDateFormat(SimpleDateFormat longDateFormat) {
        this.longDateFormat = longDateFormat;
    }

}
