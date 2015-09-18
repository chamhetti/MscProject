package lk.ac.ucsc.oms.orderMgt.implGeneral.persistantImpl;

import lk.ac.ucsc.oms.cache.api.CachePersister;
import lk.ac.ucsc.oms.orderMgt.api.beans.ExecutionAuditRecord;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.ExecutionAuditRecordException;


public interface ExecutionAuditRecordPersister extends CachePersister {
    /**
     * Method will write the execution audit record to the database
     *
     * @param executionAuditRecord
     */
    void writeExecutionToDB(ExecutionAuditRecord executionAuditRecord) throws ExecutionAuditRecordException;
}
