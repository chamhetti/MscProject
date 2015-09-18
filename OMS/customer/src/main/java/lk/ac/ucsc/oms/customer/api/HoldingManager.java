package lk.ac.ucsc.oms.customer.api;

import lk.ac.ucsc.oms.common_utility.api.enums.HoldingTypes;
import lk.ac.ucsc.oms.customer.api.beans.holding.*;
import lk.ac.ucsc.oms.customer.api.exceptions.HoldingManagementException;

import java.util.Date;
import java.util.List;


public interface HoldingManager {

    void initialize() throws HoldingManagementException;

    HoldingRecord getHoldingRecord(HoldingKey holdingKey) throws HoldingManagementException;

    void addHoldingRecord(HoldingRecord holdingRecord) throws HoldingManagementException;

    void updateHoldingRecord(HoldingRecord holdingRecord) throws HoldingManagementException;

    void addHoldingLog(HoldingLog holdingLog) throws HoldingManagementException;

    List<HoldingRecord> getCustomerAllHoldings(String customerNumber) throws HoldingManagementException;

    List<HoldingRecord> getAllHoldings(List<String> institutions) throws HoldingManagementException;

    List<HoldingLog> getAllHoldingLogs(List<String> institutions, Date fromDate, Date toDate) throws HoldingManagementException;

    HoldingRecord getEmptyHoldingRecord(HoldingKey holdingKey) throws HoldingManagementException;

    HoldingKey getEmptyHoldingKey(String customerNumber, String exchange, String symbol, String accountNumber) throws HoldingManagementException;

    HoldingKey getEmptyHoldingKey(String customerNumber, String exchange, String symbol, String accountNumber,HoldingTypes holdingType) throws HoldingManagementException;

    HoldingLog getEmptyHoldingLog();

    HoldingRecord getHoldingRecordForUpdate(HoldingKey holdingKey) throws HoldingManagementException;
}
