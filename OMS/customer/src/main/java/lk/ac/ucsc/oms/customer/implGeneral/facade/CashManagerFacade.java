package lk.ac.ucsc.oms.customer.implGeneral.facade;

import lk.ac.ucsc.oms.common_utility.api.enums.PropertyEnable;
import lk.ac.ucsc.oms.customer.api.CashManager;
import lk.ac.ucsc.oms.customer.api.beans.cash.BalanceAndDues;
import lk.ac.ucsc.oms.customer.api.beans.cash.BlockAmount;
import lk.ac.ucsc.oms.customer.api.beans.cash.CashAccount;
import lk.ac.ucsc.oms.customer.api.beans.cash.CashLog;
import lk.ac.ucsc.oms.customer.api.exceptions.CashManagementException;
import lk.ac.ucsc.oms.customer.implGeneral.beans.cash.BalanceAndDuesBean;
import lk.ac.ucsc.oms.customer.implGeneral.beans.cash.BlockAmountBean;
import lk.ac.ucsc.oms.customer.implGeneral.beans.cash.CashAccountBean;
import lk.ac.ucsc.oms.customer.implGeneral.beans.cash.CashLogBean;
import lk.ac.ucsc.oms.customer.implGeneral.facade.cache.CashCacheFacade;
import lk.ac.ucsc.oms.customer.implGeneral.facade.cache.CashLogCacheFacade;
import lk.ac.ucsc.oms.sequence_generator.api.AbstractSequenceGenerator;
import lk.ac.ucsc.oms.sequence_generator.api.exception.SequenceGenerationException;
import lk.ac.ucsc.oms.system_configuration.api.SysLevelParaManager;
import lk.ac.ucsc.oms.system_configuration.api.SystemParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CashManagerFacade implements CashManager {
    private static Logger logger = LogManager.getLogger(CashManagerFacade.class);

    private CashCacheFacade cashCacheFacade;
    private CashLogCacheFacade cashLogCacheFacade;
    private AbstractSequenceGenerator sequenceGeneratorCash;
    private AbstractSequenceGenerator sequenceGeneratorCashLog;
    private SysLevelParaManager sysLevelParaManager;


    public void setCashCacheFacade(CashCacheFacade cashCacheFacade) {
        this.cashCacheFacade = cashCacheFacade;
    }


    public void setCashLogCacheFacade(CashLogCacheFacade cashLogCacheFacade) {
        this.cashLogCacheFacade = cashLogCacheFacade;
    }


    public void setSequenceGeneratorCash(AbstractSequenceGenerator sequenceGeneratorCash) {
        this.sequenceGeneratorCash = sequenceGeneratorCash;
    }

    public void setSequenceGeneratorCashLog(AbstractSequenceGenerator sequenceGeneratorCashLog) {
        this.sequenceGeneratorCashLog = sequenceGeneratorCashLog;
    }

    public void setSysLevelParaManager(SysLevelParaManager sysLevelParaManager) {
        this.sysLevelParaManager = sysLevelParaManager;
    }

    @Override
    public void initialize() throws CashManagementException {
        cashCacheFacade.initialize();
        cashLogCacheFacade.initialize();
    }

    @Override
    public void blockCashOrMargin(String cashAccountNumber, BlockAmount blockAmount) throws CashManagementException {
        logger.info("Update block amount cashAccountNumber - {} blockAmount -{}", cashAccountNumber, blockAmount);

        if (blockAmount == null) {
            throw new CashManagementException("block amount can't be null");
        }
        CashAccountBean cashAccountBean = (CashAccountBean) getCashAccountForUpdate(cashAccountNumber);
        logger.debug("Cash account loaded from cache CashAccount -{}", cashAccountBean);
        if (cashAccountBean == null) {
            logger.error("Cash Account Not Found");
            throw new CashManagementException("Cash Account Not Found");
        }
        cashAccountBean.setBlockAmt(cashAccountBean.getBlockAmt() + blockAmount.getCashBlock());
        //block amount can't be greater than zero for any block
        if (cashAccountBean.getBlockAmt() > 0) {
            cashAccountBean.setBlockAmt(0);
        }



        logger.debug("updating the cash account -{}", cashAccountBean);

        updateCashAccount(cashAccountBean);
    }

    @Override
    public void updateBalanceAndDues(String cashAccountNumber, BalanceAndDues balanceAndDues, CashLog cashLog) throws CashManagementException {
        if (balanceAndDues == null) {
            throw new CashManagementException("BalanceAndDues can't be null");
        }
        if (cashLog == null) {
            throw new CashManagementException("CashLog can't be null");
        }
        CashAccountBean cashAccountBean = (CashAccountBean) getCashAccountForUpdate(cashAccountNumber);
        if (cashAccountBean == null) {
            logger.error("Cash Account Not Found");
            throw new CashManagementException("Cash Account Not Found");
        }
        logger.debug("Load the cash account from the Cache cash account -{}", cashAccountBean);
        cashAccountBean.setBalance(cashAccountBean.getBalance() + balanceAndDues.getCashBalance());
        cashAccountBean.setMarginDue(cashAccountBean.getMarginDue() + balanceAndDues.getOverNightMarginDue());
        logger.debug("Updated cash account- {}", cashAccountBean);
        updateCashAccount(cashAccountBean);
        logger.info("Cash account updated in cache Result -{}");

        logger.debug("Inserting the cashlog entry");
        CashLogBean cashLogBean = (CashLogBean) cashLog;
        cashLogBean.setPrimaryKeyObject(cashLogBean.getCashLogId());
        addCashLog(cashLogBean);
        logger.debug("Cash Log entry inserted");
    }

    @Override
    public CashAccount getCashAccount(String cashAccountNumber) throws CashManagementException {
        logger.info("Loading the cash account with cashAccountNumber -{}, ", cashAccountNumber);
        if (cashAccountNumber == null || "".equals(cashAccountNumber)) { //validate cashAccountNumber for null and empty
            throw new CashManagementException("Cash account numbers is null or empty");
        }
        return cashCacheFacade.getCashAccount(cashAccountNumber);
    }

    @Override
    public CashAccount getCashAccountForUpdate(String cashAccountNumber) throws CashManagementException {
        logger.info("Loading the cash account with cashAccountNumber -{}, ", cashAccountNumber);
        if (cashAccountNumber == null || "".equals(cashAccountNumber)) { //validate cashAccountNumber for null and empty
            throw new CashManagementException("Cash account numbers is null or empty");
        }
        return cashCacheFacade.getCashAccountForUpdate(cashAccountNumber);
    }


    @Override
    public void addCashAccount(CashAccount cashAccount) throws CashManagementException {
        logger.info("Adding the cash account {} to cache", cashAccount);
        validateCashAccount(cashAccount); //validate cash account before account creation
        String sequenceNum = null;
        try {
            sequenceNum = sequenceGeneratorCash.getSequenceNumber(); //get the next cache account id using sequence generator
        } catch (SequenceGenerationException e) {
            logger.error("Cannot get sequence number for cash account Id !! ", e);
            throw new CashManagementException("Error in getting cash account Id using sequence generator", e);
        }
        cashAccount.setCashAccId(Long.parseLong(sequenceNum));
        cashCacheFacade.putCashAccount((CashAccountBean) cashAccount);
    }


    @Override
    public void updateCashAccount(CashAccount cashAccount) throws CashManagementException {
        logger.info("Updating the cash account - {}", cashAccount);
        validateCashAccount(cashAccount);  //validate cash account before updating
        cashCacheFacade.updateCashAccount((CashAccountBean) cashAccount);
    }


    @Override
    public void closeCashAccount(String cashAccountNumber) throws CashManagementException {
        logger.info("Closing the cash account with cashAccountNumber -{}", cashAccountNumber);
        CashAccountBean cashAccountBean = (CashAccountBean) getCashAccount(cashAccountNumber);
        if (cashAccountBean == null) {
            logger.error("Cash Account Not Found");
            throw new CashManagementException("Cash Account Not Found");
        }
        cashAccountBean.setPrimaryKeyObject(cashAccountBean.getCashAccNumber());
        cashCacheFacade.closeCashAccount(cashAccountBean);
    }

    @Override
    public void addCashLog(CashLog cashLog) throws CashManagementException {
        logger.info("Adding new cash log with cashAccountNumber -{}");
        validateCashLog(cashLog);
        CashLogBean cashLogBean = (CashLogBean) cashLog;
        String sequenceNum = null;
        try {
            sequenceNum = sequenceGeneratorCashLog.getSequenceNumber();
            logger.debug("addCashLog : cashLogId : ", sequenceNum);
        } catch (SequenceGenerationException e) {
            logger.error("Cannot get sequence number for cash log Id !! ", e);
            throw new CashManagementException("Error in getting sequence number for cash log", e);
        }
        cashLogBean.setCashLogId(Long.parseLong(sequenceNum));
        cashLogBean.setPrimaryKeyObject(sequenceNum);
        cashLogCacheFacade.putCashLog(cashLogBean);
    }
    @Override
    public List<CashLog> getCashLogs(String orderID, String accountNumber) throws CashManagementException {
        logger.info("Loading the cash log using the orderID -{} and accountNumber -{}", orderID, accountNumber);
        if (orderID == null || accountNumber == null || "".equals(orderID) || "".equals(accountNumber)) {
            throw new CashManagementException("Invalid Order ID or Account Number");
        }

        try {
            return cashLogCacheFacade.getCashLogsByOrder(orderID, accountNumber);
        } catch (Exception e) {
            logger.error("Cash log not in the DB", e);
            throw new CashManagementException("Error Loading Cash Logs", e);
        }
    }
    @Override
    public CashLog getEmptyCashLog() {
        return new CashLogBean();
    }

    @Override
    public CashAccount getEmptyCashAccount(String cashAccountNumber) {
        return new CashAccountBean(cashAccountNumber);
    }

    @Override
    public void persistCashLogAsBulk() throws CashManagementException { //TODO --dasun-- this should not be here.
        cashLogCacheFacade.persisAsBulk();
    }

    @Override
    public void persistCashAccountDetailsAsBulk() throws CashManagementException { //TODO --dasun-- this should not be here.
        cashCacheFacade.persistAsBulk();
    }


    @Override
    public List<CashAccount> getAllCashAccount(List<String> institutionList) throws CashManagementException {
        logger.info("get all cash accounts from cache");
        return cashCacheFacade.getAllCashAccount(institutionList);
    }

    private void validateCashAccount(CashAccount cashAccountBean) throws CashManagementException {
        if (cashAccountBean == null) {
            throw new CashManagementException("Cash Account can't be null");
        }
        if (cashAccountBean.getCashAccNumber() == null || "".equals(cashAccountBean.getCashAccNumber())) {
            throw new CashManagementException("Cash Account number can't be empty or null");
        }

        if (cashAccountBean.getCustomerNumber() == null || "".equals(cashAccountBean.getCustomerNumber())) {
            throw new CashManagementException("Customer number can't be empty or null");
        }

        if (cashAccountBean.getCurrency() == null || "".equals(cashAccountBean.getCurrency())) {
            throw new CashManagementException("Currency can't be empty or null");
        }
    }
    private void validateCashLog(CashLog cashLogBean) throws CashManagementException {
        if (cashLogBean == null) {
            throw new CashManagementException("Cash Log can't be null");
        }
        if (cashLogBean.getCashAccId() <= 0) {
            throw new CashManagementException("Cash account id can't be null");
        }
        if (cashLogBean.getCustomerNumber() == null || "".equals(cashLogBean.getCustomerNumber())) {
            throw new CashManagementException("Customer number can't be empty or null");
        }
    }

    @Override
    public List<CashLog> getAllCashLog(Date fromDate, Date toDate, List<String> institutionList, Map<String, String> filters) throws CashManagementException {
        logger.info("get all cash logs from cache");
        return cashLogCacheFacade.getAllCashLog(fromDate, toDate, institutionList, filters);
    }

    public BlockAmount getEmptyBlockAmountBean() {
        return new BlockAmountBean();
    }

    public BalanceAndDues getEmptyBalanceAndDuesBean() {
        return new BalanceAndDuesBean();
    }

    @Override
    public double getBuyingPower(String cashAccountNumber) throws CashManagementException {
        CashAccount cashAccount = getCashAccount(cashAccountNumber);
        double odLimit = getODLimit(cashAccountNumber);
        return cashAccount.getBalance() + cashAccount.getBlockAmt() + cashAccount.getPendingDeposit() + odLimit;

    }
    public double getODLimit(String cashAccountNumber) throws CashManagementException {
        CashAccount cashAccount = getCashAccount(cashAccountNumber);
        double odLimit = 0;
        double primaryODLimit = cashAccount.getOdLimit();
        double secondaryODLimit = cashAccount.getSecondaryODLimit();
        double dailyODLimit = cashAccount.getDailyODLimit();
        Date now = new Date();

        // If the secondary limit is not expired, add it.
        if (secondaryODLimit > 0 && cashAccount.getSecondaryOdExpireDate() != null && now.compareTo(
                cashAccount.getSecondaryOdExpireDate()) < 0) {
            logger.info("Secondary OD limit counted for buying power: CashAccount-" + cashAccount.getCashAccNumber() +
                    ", SecondaryOD-" + secondaryODLimit);
            odLimit = secondaryODLimit;
        }

        // If the primary limit is not expired, add it.
        if (primaryODLimit > 0 && cashAccount.getPrimaryOdExpireDate() != null && now.compareTo(cashAccount.getPrimaryOdExpireDate())
                < 0) {
            logger.info("Primary OD limit counted for buying power: CashAccount-" + cashAccount.getCashAccNumber() + ", PrimaryOD-"
                    + primaryODLimit);
            odLimit += primaryODLimit;
        } else if (primaryODLimit > 0 && cashAccount.getPrimaryOdExpireDate() == null) {// Else, if the primary expiry date is not specified, yet add it.
            logger.info("Unlimited Primary OD limit counted for buying power: CashAccount-" + cashAccount.getCashAccNumber()
                    + ", PrimaryOD-" + primaryODLimit);
            odLimit += primaryODLimit;
        }
        //DTL - Daily Trading Limit
        //If DTL is enabled at both cash account level & oms level, add it too
        if (dailyODLimit > 0 && cashAccount.getDailyOdEnable() == PropertyEnable.YES && isDailyODLimitApplicable(now)) {
            logger.info("Daily OD limit counted for buying power: CashAccount-" + cashAccount.getCashAccNumber() + ", DTL-"
                    + dailyODLimit);
            odLimit += dailyODLimit;
        }
        logger.info("OD Limit of the Customer: " + odLimit);
        return odLimit;
    }

    private boolean isDailyODLimitApplicable(java.util.Date now) {
        String omsDtlEnabled;
        int dtlStartTime; // is the daily trading limit start time
        int dtlEndTime;   // is the daily trading limit end time
        int currentTime;
        SimpleDateFormat parser;
        SimpleDateFormat formater;

        try {
            //get the system parameter which say daily trading limit enable or not
            omsDtlEnabled = sysLevelParaManager.getSysLevelParameter(SystemParameter.DTL_ENABLED).getParaValue();
            parser = new SimpleDateFormat("HH:mm:ss");     // HH24:mm:ss format
            formater = new SimpleDateFormat("HHmmss");
            /* Parse the times from 23:10:56 format into 231056 */
            //get the daily trading limit start time
            dtlStartTime = Integer.parseInt(formater.format(parser.parse(sysLevelParaManager.getSysLevelParameter(
                    SystemParameter.DTL_START_TIME).getParaValue())));
            //get the daily trading end time
            dtlEndTime = Integer.parseInt(formater.format(parser.parse(sysLevelParaManager.getSysLevelParameter(
                    SystemParameter.DTL_END_TIME).getParaValue())));
            //get the current time
            currentTime = Integer.parseInt(formater.format(now));

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error("Error in reading DTL sys parameters");
            return false;
        }

        logger.info("OMS DTL Enabled:" + omsDtlEnabled + " Start Time:" + dtlStartTime + " End Time:" + dtlEndTime +
                " Current Time:" + currentTime);
        if ("1".equalsIgnoreCase(omsDtlEnabled) && currentTime >= dtlStartTime && currentTime < dtlEndTime) {
            // in between the DTL time
            logger.info("Current Time:" + now.toString() + " DTL Enabled");
            return true;
        } else {
            return false;
        }
    }

}
