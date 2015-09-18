package lk.ac.ucsc.oms.orderMgt.implGeneral.persistantImpl;

import lk.ac.ucsc.oms.cache.api.CachePersister;
import lk.ac.ucsc.oms.orderMgt.api.beans.Execution;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.OrderExecutionException;

import java.util.List;


public interface ExecutionPersister extends CachePersister{
    /**
     * get the Execution Map with Execution ID as the key and Execution as the Value
     *
     * @param ordNo for which executions should be retrieved
     * @return exeMap
     */
    List<Execution> getExecutionsByOrdNo(String ordNo) throws OrderExecutionException;

    /**
     * get the Execution Map with Execution ID as the key and Execution as the Value
     *
     * @param clOrdId for which executions should be retrieved
     * @return exeMap
     */
    List<Execution> getExecutionsByClOrdId(String clOrdId) throws OrderExecutionException;

    /**
     * this method is to retrieve the order execution using the back office id if that execution already exist
     * front office, this is used in sync to make sure that the same order execution in back office would not be
     * persisted twice in front office
     *
     *
     * @param backOfficeId
     * @return execution
     * @throws OrderExecutionException
     */
    Execution getExecutionByBackOfficeId(String backOfficeId) throws OrderExecutionException;
}
