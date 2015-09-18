package lk.ac.ucsc.oms.sequence_generator.implGeneral.logics;


import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.sequence_generator.api.AbstractSequenceGenerator;
import lk.ac.ucsc.oms.sequence_generator.api.exception.SequenceGenerationException;

import java.text.SimpleDateFormat;


public class OrderIDGenerator extends AbstractSequenceGenerator {

    static final int SEQ_NUMBER_INDEX = 9;
    static final int DATE_SUB_STRING_INDEX = 6;
    static final int DATE_STRING_LENGTH_OLD = 8;
    private static String date = "";
    private int appID = System.getProperty("server.id") != null ? Integer.valueOf(System.getProperty("server.id")) : 0;

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized String getSequenceNumber() throws SequenceGenerationException {
        try {
            int number = getCacheFacade().getNumber(getName());
            //this logic is used to generate ids per day, that means order id contains the day concatted with incrementing
            //number at last. when a new day begins the last number should start over again, for example
            //20130522-1 , 20130522-2 , 20130522-3 ,... it increments like this and when its 23rd it should be like below
            //20130523-1 , 20130523-2 ,.... etc (last number should start over)
            String dateNow = new SimpleDateFormat("yyMMdd").format(System.currentTimeMillis());
            if ((dateNow.trim()).equals(date.trim())) {
                number++;
                getCacheFacade().updateNumber(getName(), number);
            } else {
                date = dateNow;
                number = 1;
                getCacheFacade().updateNumber(getName(), number);
            }
            return dateNow+"-" + appID+"-" + String.format("%05d", number);
        } catch (OMSException e) {
            throw new SequenceGenerationException("Could not fetch sequence number from cache", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean resetSequence() {
        getCacheFacade().updateNumber(getName(), 0);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSequenceNumber(String sequenceNumber) {
        date = sequenceNumber.trim().substring(0, sequenceNumber.indexOf("-"));
        if(date.trim().length() == DATE_STRING_LENGTH_OLD){
            sequenceNumber = sequenceNumber.substring(2);
            date = date.substring(2);
        }
        int number = Integer.parseInt(sequenceNumber.trim().substring(SEQ_NUMBER_INDEX));
        getCacheFacade().updateNumber(getName(), number);
    }
}
