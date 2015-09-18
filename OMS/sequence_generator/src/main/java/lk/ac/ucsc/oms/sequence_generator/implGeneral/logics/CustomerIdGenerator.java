package lk.ac.ucsc.oms.sequence_generator.implGeneral.logics;


import lk.ac.ucsc.oms.common_utility.api.exceptions.OMSException;
import lk.ac.ucsc.oms.sequence_generator.api.AbstractSequenceGenerator;
import lk.ac.ucsc.oms.sequence_generator.api.exception.SequenceGenerationException;


public class CustomerIdGenerator extends AbstractSequenceGenerator {


    @Override
    public synchronized String getSequenceNumber() throws SequenceGenerationException {
        try {
            int number = getCacheFacade().getNumber(getName());
            number++;
            getCacheFacade().updateNumber(getName(), number);
            return String.valueOf(number);
        } catch (OMSException e) {
            throw new SequenceGenerationException("Could not fetch sequence number from cache", e);
        }
    }


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
        int number = Integer.parseInt(sequenceNumber);
        getCacheFacade().updateNumber(getName(), number);
    }

}

