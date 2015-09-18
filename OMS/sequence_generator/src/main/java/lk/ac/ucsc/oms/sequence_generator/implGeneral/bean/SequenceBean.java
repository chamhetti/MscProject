package lk.ac.ucsc.oms.sequence_generator.implGeneral.bean;


import lk.ac.ucsc.oms.cache.api.beans.CacheObject;


public class SequenceBean extends CacheObject {
    private int number;


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
