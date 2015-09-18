package lk.ac.ucsc.oms.orderMgt.implGeneral.facade;

import lk.ac.ucsc.oms.orderMgt.api.ExecutionAuditRecordManager;
import lk.ac.ucsc.oms.orderMgt.api.beans.ExecutionAuditRecord;
import lk.ac.ucsc.oms.orderMgt.api.beans.ExecutionRecordKey;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.ExecutionAuditRecordException;
import lk.ac.ucsc.oms.orderMgt.implGeneral.bean.ExecutionAuditRecordBean;
import lk.ac.ucsc.oms.orderMgt.implGeneral.bean.ExecutionRecordKeyBean;
import lk.ac.ucsc.oms.orderMgt.implGeneral.facade.cache.ExecutionAuditCacheFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ExecutionAuditRecordManagerFacade implements ExecutionAuditRecordManager {
    private static Logger logger = LogManager.getLogger(ExecutionAuditRecordManagerFacade.class);

    private ExecutionAuditCacheFacade cacheFacade;


    @Override
    public void initialize() throws ExecutionAuditRecordException {
        logger.debug("Initializing the Execution manager facade ...");
        cacheFacade.initialize();
    }

    @Override
    public void addExecutionReportRecord(ExecutionAuditRecord executionAuditRecord) throws ExecutionAuditRecordException {
        cacheFacade.addToDB(executionAuditRecord);
    }


    @Override
    public ExecutionAuditRecord getEmptyExecutionAuditRecord() throws ExecutionAuditRecordException {
        return new ExecutionAuditRecordBean();
    }

    @Override
    public ExecutionRecordKey getEmptyExecutionRecordKey(String day, String exchange, String messageID, String side, String execBrokerID) throws ExecutionAuditRecordException {
        return new ExecutionRecordKeyBean(day, exchange, messageID, side, execBrokerID);
    }


    public void setCacheFacade(ExecutionAuditCacheFacade cacheFacade) {
        this.cacheFacade = cacheFacade;
    }

}
