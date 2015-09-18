package lk.ac.ucsc.oms.fix.impl.facade;

import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.fix.api.FIXFacadeInterface;
import lk.ac.ucsc.oms.fix.api.beans.FixOrderInterface;
import lk.ac.ucsc.oms.fix.api.exception.FIXOrderException;
import lk.ac.ucsc.oms.fix.impl.beans.FixOrderBean;
import lk.ac.ucsc.oms.fix.impl.exchangeLogic.FIXMessage;
import lk.ac.ucsc.oms.fix.impl.parserLogic.MessageParser;
import lk.ac.ucsc.oms.fix.impl.util.ErrorMessageGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;


public class FIXFacade implements FIXFacadeInterface {
    private static Logger logger = LogManager.getLogger(FIXFacade.class);

    private static final int FIX_ORDER_BEAN_IS_NULL = 1;
    private static final int MESSAGE_TYPE_IS_NULL = 2;
    private static final String FROM_APPIA = "FROM_APPIA";
    private static final String TO_APPIA = "TO_APPIA";
    private MessageParser msgParserBean;
    private FIXMessage fixApiMessageBean;
    private String moduleCode;


    private static int FIX_MSG_COUNT = 1;


    @Override
    public FixOrderInterface getParseFixMessage(String exchangeMessage, String sid) throws OMSException {
        if (exchangeMessage == null) {
            throw new OMSException(ErrorMessageGenerator.getErrorMsg(FIX_ORDER_BEAN_IS_NULL));
        }
        return msgParserBean.processFixMessage(exchangeMessage, sid);
    }


    @Override
    public String getExchangeFixMessage(FixOrderInterface fixOrderBean, Map<Integer, String> customFields, Map<Integer, Integer> replaceFields, List<Integer> tagSequence) throws FIXOrderException {
        if (fixOrderBean == null) {
            throw new FIXOrderException(ErrorMessageGenerator.getErrorMsg(FIX_ORDER_BEAN_IS_NULL));
        } else if (fixOrderBean.getMessageType() == null || "".equals(fixOrderBean.getMessageType().getCode())) {
            throw new FIXOrderException(ErrorMessageGenerator.getErrorMsg(MESSAGE_TYPE_IS_NULL));
        }
        String fixMsg;
        fixMsg = fixApiMessageBean.getFixMessage(fixOrderBean, customFields, replaceFields, tagSequence);
        return fixMsg;
    }



    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
        ErrorMessageGenerator.setModuleCode(moduleCode);
    }

    public FixOrderInterface getEmptyFixOrder() {
        return new FixOrderBean();
    }



    public void setMsgParserBean(MessageParser msgParserBean) {
        this.msgParserBean = msgParserBean;
    }


    public void setFixApiMessageBean(FIXMessage fixApiMessageBean) {
        this.fixApiMessageBean = fixApiMessageBean;
    }


}
