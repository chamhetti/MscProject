package lk.ac.ucsc.oms.orderMgt.api;

import lk.ac.ucsc.oms.orderMgt.api.beans.ExecutionAuditRecord;
import lk.ac.ucsc.oms.orderMgt.api.beans.ExecutionRecordKey;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.ExecutionAuditRecordException;

/**
 * Interface defines the methods required to persist and get the execution report messages came from the security
 * exchanges and execution brokers
 *
 * @author Chathura
 *         Date: 6/1/14
 */
public interface ExecutionAuditRecordManager {
    /**
     * Used to initialize the module at OMS startup
     */
    void initialize() throws ExecutionAuditRecordException;

    /**
     * Method to add the execution audit records to the cache
     *
     * @param executionAuditRecord is the execution audit record
     * @throws ExecutionAuditRecordException
     */
    void addExecutionReportRecord(ExecutionAuditRecord executionAuditRecord) throws ExecutionAuditRecordException;

    /**
     * Get empty execution audit record bean
     *
     * @return ExecutionAuditRecord
     */
    ExecutionAuditRecord getEmptyExecutionAuditRecord() throws ExecutionAuditRecordException;

    /**
     * Get empty execution record key
     *
     * @param day          is the day
     * @param exchange     is the exchange
     * @param messageID    is the execution id
     * @param side         is the order side
     * @param execBrokerID is the execBroker id
     * @return ExecutionRecordKey
     * @throws ExecutionAuditRecordException
     */
    ExecutionRecordKey getEmptyExecutionRecordKey(String day, String exchange, String messageID, String side, String execBrokerID) throws ExecutionAuditRecordException;
}
