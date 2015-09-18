package lk.ac.ucsc.oms.sequence_generator.api;

import lk.ac.ucsc.oms.sequence_generator.api.exception.SequenceGenerationException;
import lk.ac.ucsc.oms.sequence_generator.implGeneral.facade.cache.SequenceGeneratorCacheFacade;


public abstract class AbstractSequenceGenerator {

    private String name;
    private SequenceGeneratorCacheFacade cacheFacade;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public abstract String getSequenceNumber() throws SequenceGenerationException;


    public abstract boolean resetSequence();

    public abstract void setSequenceNumber(String sequenceNumber);

    public SequenceGeneratorCacheFacade getCacheFacade() {
        return cacheFacade;
    }

    public void setCacheFacade(SequenceGeneratorCacheFacade cacheFacade) {
        this.cacheFacade = cacheFacade;
    }
}


