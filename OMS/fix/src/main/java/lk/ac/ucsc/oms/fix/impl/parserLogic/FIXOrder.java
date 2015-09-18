package lk.ac.ucsc.oms.fix.impl.parserLogic;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.fix.api.beans.FixOrderInterface;
import lk.ac.ucsc.oms.fix.impl.beans.FixOrderBean;
import lk.ac.ucsc.oms.fix.impl.beans.SessionStatusBean;
import lk.ac.ucsc.oms.fix.impl.util.DataSeparator;
import lk.ac.ucsc.oms.fix.impl.util.FIXConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import static lk.ac.ucsc.oms.fix.impl.util.FIXConstants.*;

public class FIXOrder {
    private static Logger logger = LogManager.getLogger(FIXOrder.class);
    private SimpleDateFormat sdf;
    private SimpleDateFormat shortDateFormat;
    private static final int ORD_STAT = 199;

    public FixOrderInterface getFixOrder(String exchangeMessage) throws OMSException {
        FixOrderInterface order = new FixOrderBean();
        DataSeparator dataSeparator = getDataSeparator();
        String sToken;
        String sTag;
        int iTag;
        String value;
        StringTokenizer st;

        st = new StringTokenizer(exchangeMessage, FIXConstants.FIELD_SEPERATOR);

        while (st.hasMoreTokens()) {
            sToken = st.nextToken();
            dataSeparator.setData(sToken);
            sTag = dataSeparator.getTag();
            if (sTag == null) {
                continue;
            }
            try {
                iTag = Integer.parseInt(sTag);
            } catch (NumberFormatException e) {
                continue;
            }
            value = dataSeparator.getData();

            switch (iTag) {
                case PORTFOLIO_NO:
                    order.setPortfolioNo(value);
                    break;
                case MSG_TYPE:
                    order.setMessageType(FixOrderInterface.MessageType.getEnum(String.valueOf(value.charAt(0))));
                    break;
                case SECURITY_ID_SOURCE:
                    order.setSecurityIDSource(value);
                    break;
                case SYMBOL:
                    order.setSymbol(value);
                    break;
                case CLORD_ID:
                    order.setClordID(value);
                    break;
                case CLORD_ID_9974:
                    order.setClordID(value);
                    break;
                case ORIG_CLORD_ID:
                    order.setOrigClordID(value);
                    break;
                case POSS_DUP_FLAG:
                    if ("Y".equals(value)) {
                        order.setPossDupFlag(true);
                    } else {
                        order.setPossDupFlag(false);
                    }
                    break;
                case ORDER_ID:
                    order.setOrderID(value);
                    break;
                case EXEC_ID:
                    order.setExecID(value);
                    break;
                case SECURITY_TYPE:
                    order.setSecurityType(value);
                    break;
                case SECURITY_EXCHANGE:
                    order.setSecurityExchange(value);
                    break;
                case CURRENCY:
                    order.setCurrency(value);
                    break;
                case ORD_TYPE:
                    order.setOrdType(value.charAt(0));
                    break;
                case SIDE:
                    order.setSide(value.charAt(0));
                    break;
                case SIDE_9959:
                    order.setSide(value.charAt(0));
                    break;
                case ORDER_QTY:
                    order.setOrderQty(Double.parseDouble(value));
                    break;
                case PRICE:
                    order.setPrice(Double.parseDouble(value));
                    break;
                case STOP_PX:
                    order.setStopPx(Double.parseDouble(value));
                    break;
                case TIME_IN_FORCE:
                    order.setTimeInForce(Integer.parseInt(value));
                    break;
                case EXPIRE_TIME:
                    order.setExpireTime(formatDate(value));
                    break;
                case EXPIRE_DATE:
                    order.setExpireDate(formatExpireDate(value));
                    break;
                case MIN_QTY:
                    order.setMinQty(Double.parseDouble(value));
                    break;
                case MAX_FLOOR:
                    order.setMaxFloor(Double.parseDouble(value));
                    break;
                case SETTLEMNT_TYPE:
                    order.setSettlementType(Integer.parseInt(value));
                    break;
                case HANDLE_INST:
                    order.setHandlInst(Integer.parseInt(value));
                    break;
                case LAST_PX:
                    order.setLastPx(Double.parseDouble(value));
                    break;
                case AVG_PX:
                    order.setAvgPx(Double.parseDouble(value));
                    break;
                case EXEC_TYPE:
                    order.setExecType(value.charAt(0));
                    break;
                case ORD_STATUS:
                    order.setOrdStatus(value.charAt(0));
                    break;
                case ORD_STATUS_9971:
                    updateOrderStatus(order, value);
                    break;
                case EXEC_TRANS_TYPE:
                    order.setExecTransType(Integer.parseInt(value));
                    break;
                case ORD_REJ_REASON:
                    order.setOrdRejReason(Integer.parseInt(value));
                    break;
                case ORD_REJ_REASON_103:
                    order.setOrdRejReason(Integer.parseInt(value));
                    break;
                case TEXT:
                    if (value != null) {
                        String val = value.replace("\n", "");
                        order.setText(val);
                    }
                    break;
                case LAST_SHARES:
                    order.setLastShares(Double.parseDouble(value));
                    break;
                case CUM_QTY:
                    order.setCumQty(Double.parseDouble(value));
                    break;
                case LEAVES_QTY:
                    order.setLeavesQty(Double.parseDouble(value));
                    break;
                case TRANSACTION_TIME:
                    order.setTransactTime(value);
                    break;
                case SECURITY_ID:
                    order.setSecurityID(value);
                    break;
                case SENDER_COMP_ID:
                    order.setSenderCompID(value);
                    break;
                case TARGET_COMP_ID:
                    order.setTargetCompID(value);
                    break;
                case CXL_REJ_RESPONSE_TO:
                    order.setCxlRejResponseTo(value.charAt(0));
                    break;
                case FIX_VERSION:
                    order.setFixVersion(value);
                    break;
                case SENDING_TIME:
                    order.setSendingTime(formatDate(value));
                    break;
                default:
                    break;
            }
        }
        order.setFixString(exchangeMessage);

        return order;
    }


    public DataSeparator getDataSeparator() {
        return new DataSeparator("=");
    }

    public FixOrderInterface getMarketStatusMessage(String exchangeMessage) throws OMSException {
        FixOrderInterface sessionBean = new SessionStatusBean();
        DataSeparator dataSeparator = getDataSeparator();
        String sToken = null;
        String sTag = null;
        int iTag;
        String value = null;
        StringTokenizer st = null;

        st = new StringTokenizer(exchangeMessage, FIXConstants.FIELD_SEPERATOR);

        while (st.hasMoreTokens()) {
            sToken = st.nextToken();
            dataSeparator.setData(sToken);
            sTag = dataSeparator.getTag();
            if (sTag == null) {
                continue;
            }
            try {
                iTag = Integer.parseInt(sTag);
            } catch (NumberFormatException e) {
                logger.error(e.getMessage(), e);
                continue;
            }
            value = dataSeparator.getData();

            switch (iTag) {
                case MSG_TYPE:
                    sessionBean.setMessageType(FixOrderInterface.MessageType.getEnum(String.valueOf(value.charAt(0))));
                    break;
                case TRAD_SESSION_STATUS:
                    sessionBean.setTradSesStatus(Integer.parseInt(value));
                    break;
                case TRAD_SES_ID:
                    sessionBean.setTradingSessionID(value);
                    break;
                case SENDER_COMP_ID:
                    sessionBean.setSenderCompID(value);
                    break;
                case SENDING_TIME:
                    sessionBean.setSendingTime(formatDate(value));
                    break;
                default:
                    break;
            }
        }

        return sessionBean;
    }


    public Date formatDate(String dateValue) {
        Date date = null;
        try {
            date = sdf.parse(dateValue);
        } catch (ParseException e) {
            logger.error("Invalid date format");
        }
        return date;
    }

    public Date formatExpireDate(String dateValue) {
        Date date = null;
        try {
            date = shortDateFormat.parse(dateValue);
        } catch (ParseException e) {
            logger.error("Invalid date format");
        }
        return date;
    }

    private void updateOrderStatus(FixOrderInterface order, String value) {
        if (value != null && !("").equals(value)) {
            if (value.equals(REJECT_CODE)) {
                order.setOrdStatus(T39_REJECTED);
            } else if (value.equals(REPLACE_CODE)) {
                order.setOrdStatus(T39_REPLACED);
            } else if (value.equals(NEW_INITIATED_CODE)) {
                order.setOrdStatus(T39_NEW_INITIATED);
            } else if (value.equals(SUSPENDED_CODE)) {
                order.setOrdStatus(T39_SUSPENDED);
            } else if (value.equals(NEW_DELETED_CODE)) {
                order.setOrdStatus(T39_NEW_DELETED);
            } else if (value.equals(EXPIRED_CODE)) {
                order.setOrdStatus(T39_EXPIRED);
            } else if (value.equals(PENDING_NEW_CODE)) {
                order.setOrdStatus(T39_PENDING_NEW);
            } else if (value.equals(PARTIALLY_FILLED_CODE)) {
                order.setOrdStatus(T39_PARTIALLY_FILLED);
            } else if (value.equals(FILLED_CODE)) {
                order.setOrdStatus(T39_FILLED);
            } else if (value.equals(FILLED_AND_PARTIALFILLED_CODE)) {
                order.setOrdStatus(T39_FILLED_AND_PARTIALFILLED);
            } else if (value.equals(CANCELLED_CODE)) {
                order.setOrdStatus(T39_CANCELLED);
            } else if (value.equals(AMEND_INITIATED_CODE)) {
                order.setOrdStatus(T39_AMEND_INITIATED);
            } else if (value.equals(CANCEL_INITIATED_CODE)) {
                order.setOrdStatus(T39_CANCEL_INITIATED);
            } else if (value.equals(AMEND_REJECTED_CODE)) {
                order.setOrdStatus(T39_AMEND_REJECTED);
            } else if (value.equals(CANCEL_REJECTED_CODE)) {
                order.setOrdStatus(T39_CANCEL_REJECTED);
            }
            try {
                order.setLetsOrdStatus(Integer.parseInt(value));
            } catch (NumberFormatException e2) {
                order.setLetsOrdStatus(ORD_STAT);
                logger.error(e2.getMessage(), e2);
            }
        }
    }

    public void setDateFormatter(SimpleDateFormat dateFormatter) {
        this.sdf = dateFormatter;
    }

    public void setShortDateFormat(SimpleDateFormat shortDateFormat) {
        this.shortDateFormat = shortDateFormat;
    }
}
