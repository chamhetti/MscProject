package lk.ac.ucsc.oms.common_utility.api.beans;

import java.util.List;

/**
 * @author hetti
 */
public interface SearchReply {

    List getResultObjects();

    void setResultObjects(List resultObjects);

    int getTotalNumberOfRecords();

    void setTotalNumberOfRecords(int totalNumberOfRecords);
}
