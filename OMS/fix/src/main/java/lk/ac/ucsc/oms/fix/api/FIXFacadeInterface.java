package lk.ac.ucsc.oms.fix.api;


import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.fix.api.beans.FixOrderInterface;
import lk.ac.ucsc.oms.fix.api.exception.FIXOrderException;

import java.util.List;
import java.util.Map;


public interface FIXFacadeInterface {

    String getExchangeFixMessage(FixOrderInterface fixOrderBean, Map<Integer, String> customFields, Map<Integer, Integer> replaceFields, List<Integer> tagSequence) throws FIXOrderException;

    FixOrderInterface getParseFixMessage(String exchangeMessage, String sid) throws OMSException;

    FixOrderInterface getEmptyFixOrder();


}
