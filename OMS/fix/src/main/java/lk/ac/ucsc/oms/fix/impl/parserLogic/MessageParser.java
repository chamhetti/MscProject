package lk.ac.ucsc.oms.fix.impl.parserLogic;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.fix.api.beans.FixOrderInterface;
import lk.ac.ucsc.oms.fix.impl.util.DataSeparator;
import lk.ac.ucsc.oms.fix.impl.util.ErrorMessageGenerator;
import lk.ac.ucsc.oms.fix.impl.util.FIXConstants;

import java.util.StringTokenizer;

import static lk.ac.ucsc.oms.fix.impl.util.FIXConstants.*;


public class MessageParser {
    private static final int MESSAGE_TYPE_NOT_SUPPORTED = 3;
    private FIXOrder fixOrderBean;
    private DataSeparator dataSeparator;
    private OrderMapper orderMapper;


    public synchronized FixOrderInterface processFixMessage(String fixMessage, String sid) throws OMSException {
        char msgType = getFIXMessageType(fixMessage);
        FixOrderInterface orderBean = null;
        switch (msgType) {
            case EXECUTION_REPORT:
                orderBean = fixOrderBean.getFixOrder(fixMessage);
                break;
            case ORDER_CANCEL_REJECT:
                orderBean = fixOrderBean.getFixOrder(fixMessage);
                break;
            case TRADING_SESSION_STATUS_REQUEST:
                orderBean = fixOrderBean.getMarketStatusMessage(fixMessage);
                break;
            case TRADING_SESSION_STATUS:
                orderBean = fixOrderBean.getMarketStatusMessage(fixMessage);
                break;
            default:
                throw new OMSException(ErrorMessageGenerator.getErrorMsg(MESSAGE_TYPE_NOT_SUPPORTED));
        }

        return orderBean;
    }


    private char getFIXMessageType(String sFixMessage) {
        String sType = "X";
        String sToken = null;
        String sTag = null;
        StringTokenizer st = null;

        st = new StringTokenizer(sFixMessage, FIXConstants.FIELD_SEPERATOR);

        while (st.hasMoreTokens()) {
            sToken = st.nextToken();
            sToken = sToken.trim();
            dataSeparator.setData(sToken);
            sTag = dataSeparator.getTag();
            if (sTag != null && sTag.equals(Integer.toString(MSG_TYPE))) {
                sType = dataSeparator.getData();
                break;
            } else if (sTag != null && sTag.equals(Integer.toString(APPIA_MSG_ID))) {
                sType = dataSeparator.getData();
                break;
            }
        }
        return sType.charAt(0);
    }

    public synchronized void setFixOrderBean(FIXOrder fixOrderBean) {
        this.fixOrderBean = fixOrderBean;
    }

    public void setDataSeparator(DataSeparator dataSeparator) {
        this.dataSeparator = dataSeparator;
    }

    public synchronized void setOrderMapper(OrderMapper mapper) {
        this.orderMapper = mapper;
    }

}
