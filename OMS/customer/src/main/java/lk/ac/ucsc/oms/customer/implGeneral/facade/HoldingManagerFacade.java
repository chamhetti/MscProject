package lk.ac.ucsc.oms.customer.implGeneral.facade;

import lk.ac.ucsc.oms.common_utility.api.enums.HoldingTypes;
import lk.ac.ucsc.oms.customer.api.HoldingManager;
import lk.ac.ucsc.oms.customer.api.beans.holding.*;
import lk.ac.ucsc.oms.customer.api.exceptions.HoldingManagementException;
import lk.ac.ucsc.oms.customer.implGeneral.beans.holding.*;
import lk.ac.ucsc.oms.customer.implGeneral.facade.cache.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HoldingManagerFacade implements HoldingManager {
    private static final String INSUFFICIENT_INPUT_PARAMETERS = "Insufficient input parameters";
    private static Logger logger = LogManager.getLogger(HoldingManagerFacade.class);
    private static int holdingLogCount = 0;
    private HoldingCacheFacade holdingCacheFacade;
    private HoldingLogCacheFacade holdingLogCacheFacade;

    @Override
    public void initialize() throws HoldingManagementException {
        holdingCacheFacade.initialize();
        holdingLogCacheFacade.initialize();

    }

    @Override
    public HoldingRecord getHoldingRecord(HoldingKey holdingKey) throws HoldingManagementException {
        logger.debug("Getting the holding record with key -{}", holdingKey);
        if (holdingKey == null) {
            throw new HoldingManagementException("Holding key can't be null");
        }
        return holdingCacheFacade.getHoldingRecord(holdingKey);
    }

    @Override
    public HoldingRecord getHoldingRecordForUpdate(HoldingKey holdingKey) throws HoldingManagementException {
        logger.info("Getting the holding record with key -{}", holdingKey);
        if (holdingKey == null) {
            throw new HoldingManagementException("Holding key can't be null");
        }
        return holdingCacheFacade.getHoldingRecordForUpdate(holdingKey);
    }

    @Override
    public void addHoldingRecord(HoldingRecord holdingRecord) throws HoldingManagementException {
        logger.info("Adding the holding record -{}", holdingRecord);
        if (holdingRecord == null) {
            throw new HoldingManagementException("Holding record can't be null");
        }
        HoldingRecordBean holdingRecordBean = (HoldingRecordBean) holdingRecord;

        holdingRecordBean.setPrimaryKeyObject(holdingRecord.getHoldingInfoKey());
        logger.debug("addHoldingRecord : holdingRecordBean : ", holdingRecordBean);
        holdingCacheFacade.putHoldingRecord(holdingRecordBean);

    }

    @Override
    public void updateHoldingRecord(HoldingRecord holdingRecord) throws HoldingManagementException {
        logger.info("Updating the holding record -{}", holdingRecord);
        if (holdingRecord == null) {
            throw new HoldingManagementException("Holding record can't be null");
        }
        HoldingRecordBean holdingRecordBean = (HoldingRecordBean) holdingRecord;
        holdingRecordBean.setPrimaryKeyObject(holdingRecord.getHoldingInfoKey());
        logger.debug("updateHoldingRecord : holdingRecordBean : ", holdingRecordBean);
        holdingCacheFacade.updateHoldingRecord(holdingRecordBean);
    }

    @Override
    public void addHoldingLog(HoldingLog holdingLog) throws HoldingManagementException {
        logger.info("Add the holding log {}", holdingLog);
        if (holdingLog == null) {
            throw new HoldingManagementException("Holding log can't be null");
        }
        long holdingLogId = System.nanoTime() + holdingLogCount++;
        HoldingLogBean holdingLogBean = (HoldingLogBean) holdingLog;
        holdingLog.setHoldingLogId(holdingLogId);
        holdingLogBean.setPrimaryKeyObject(holdingLogBean.getHoldingLogId());
        holdingLogCacheFacade.putHoldingLog(holdingLogBean);
        logger.debug("addHoldingLog : holdingLogBean : ", holdingLogBean);

    }

    @Override
    public List<HoldingRecord> getCustomerAllHoldings(String customerNumber) throws HoldingManagementException {
        logger.info("Getting the all holding list for the customer -{} ", customerNumber);
        if (customerNumber == null || "".equals(customerNumber)) {
            throw new HoldingManagementException(INSUFFICIENT_INPUT_PARAMETERS);
        }
        List<HoldingRecord> holdingList = new ArrayList<>();
        List<HoldingKey> holdingKeys = holdingCacheFacade.getCustomerAllHoldingKeys(customerNumber);
        if (holdingKeys != null) {
            for (HoldingKey key : holdingKeys) {
                holdingList.add(getHoldingRecord(key));
            }
        }
        logger.debug("getCustomerAllHoldings : holdingList : ", holdingList);
        return holdingList;
    }

    @Override
    public List<HoldingRecord> getAllHoldings(List<String> institutions) throws HoldingManagementException {
        logger.info("getAllHoldings from cache or db");
        return holdingCacheFacade.getAllHolding(institutions);
    }

    public List<HoldingLog> getAllHoldingLogs(List<String> institutionList, Date fromDate, Date toDate) throws HoldingManagementException {
        logger.info("getAllHoldingLogs from holding log cache facade");
        List<HoldingLog> holdingLogList = new ArrayList<>();
        try {
            holdingLogList = holdingLogCacheFacade.getAllHoldingLogs(institutionList, fromDate, toDate);
        } catch (HoldingManagementException e) {
            throw new HoldingManagementException("Error loading holding list", e);
        }
        logger.debug("getAllHoldingLogs : holdingLogList : ", holdingLogList);
        return holdingLogList;
    }

    @Override
    public HoldingRecord getEmptyHoldingRecord(HoldingKey holdingKey) throws HoldingManagementException {
        return new HoldingRecordBean(holdingKey);
    }

    @Override
    public HoldingKey getEmptyHoldingKey(String customerNumber, String exchange, String symbol, String accountNumber) throws HoldingManagementException {
        return new HoldingKeyBean(customerNumber, exchange, symbol, accountNumber);
    }

    @Override
    public HoldingKey getEmptyHoldingKey(String customerNumber, String exchange, String symbol, String accountNumber, HoldingTypes holdingType) throws HoldingManagementException {
        return new HoldingKeyBean(customerNumber,exchange,symbol,accountNumber,holdingType);
    }

    @Override
    public HoldingLog getEmptyHoldingLog() {
        return new HoldingLogBean();
    }

    public void setHoldingCacheFacade(HoldingCacheFacade holdingCacheFacade) {
        this.holdingCacheFacade = holdingCacheFacade;
    }

    public void setHoldingLogCacheFacade(HoldingLogCacheFacade holdingLogCacheFacade) {
        this.holdingLogCacheFacade = holdingLogCacheFacade;
    }

}
