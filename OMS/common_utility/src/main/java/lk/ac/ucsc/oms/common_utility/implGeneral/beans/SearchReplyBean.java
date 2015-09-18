package lk.ac.ucsc.oms.common_utility.implGeneral.beans;

import lk.ac.ucsc.oms.common_utility.api.beans.SearchReply;

import java.util.ArrayList;
import java.util.List;

public class SearchReplyBean implements SearchReply {
    private List resultObjects = new ArrayList();
    private int totalNumberOfRecords;

    public List getResultObjects() {
        return resultObjects;
    }

    public void setResultObjects(List resultObjects) {
        this.resultObjects = resultObjects;
    }

    public int getTotalNumberOfRecords() {
        return totalNumberOfRecords;
    }

    public void setTotalNumberOfRecords(int totalNumberOfRecords) {
        this.totalNumberOfRecords = totalNumberOfRecords;
    }
}
