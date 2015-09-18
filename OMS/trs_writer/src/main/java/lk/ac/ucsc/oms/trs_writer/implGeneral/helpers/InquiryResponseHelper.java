package lk.ac.ucsc.oms.trs_writer.implGeneral.helpers;

import lk.ac.ucsc.oms.common_utility.api.formatters.DateFormatterUtil;
import lk.ac.ucsc.oms.common_utility.api.formatters.StringUtils;
import lk.ac.ucsc.oms.customer.api.beans.cash.CashAccount;
import lk.ac.ucsc.oms.customer.api.beans.customer.Customer;
import lk.ac.ucsc.oms.messaging_protocol_json.api.MessageConstants;
import lk.ac.ucsc.oms.messaging_protocol_json.api.MessageProtocolFacade;
import lk.ac.ucsc.oms.messaging_protocol_json.api.ValueConstants;
import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Header;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.HeaderImpl;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.common.BuyingPowerBean;

import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.inquiry.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;


public class InquiryResponseHelper {
    private static Logger logger = LogManager.getLogger(InquiryResponseHelper.class);
    private MessageProtocolFacade messageProtocolFacade;

    public String populateAccountSummaryResponse(CashAccount cashAccount, Customer customer, String portfolioNo, double buyingPower, double portfolioValue) {
        BuyingPowerResponseBean accountSummaryResponse = new BuyingPowerResponseBean();
        BuyingPowerBean accountSummery = new BuyingPowerBean();

        accountSummery.setPortfolioNumber(portfolioNo);
        accountSummery.setCashAccountNumber(cashAccount.getCashAccNumber());
        accountSummery.setBalance(cashAccount.getBalance());
        accountSummery.setBlockedAmount(cashAccount.getBlockAmt());
        accountSummery.setODLimit(cashAccount.getOdLimit());
        accountSummery.setPendingDeposits(cashAccount.getPendingDeposit());
        accountSummery.setCurrency(cashAccount.getCurrency());
        accountSummery.setAccountType(customer.getAccountType().getCode());
        accountSummery.setBuyingPower(buyingPower);
        accountSummery.setCashMargin(0);       // not sent
        accountSummery.setDayCashMargin(0);    // not sent
        accountSummery.setNetSecurityValue(portfolioValue);
        accountSummery.setPortfolioValuation(portfolioValue);
        accountSummery.setLastAccountUpdatedTime(DateFormatterUtil.formatDateToString(new Date(), DateFormatterUtil.DATE_FORMAT_YYYYMMDDHHMMSS));

        double availableCashForWithdraw = cashAccount.getBalance() - cashAccount.getPendingSettle() + cashAccount.getBlockAmt() ;
        if (availableCashForWithdraw < 0) {
            availableCashForWithdraw = 0;
        }
        accountSummery.setCashForWithdrawal(availableCashForWithdraw);
        accountSummery.setPendingDeposits(cashAccount.getPendingDeposit());
        // margin trading params
        accountSummery.setMarginBlock(cashAccount.getMarginBlock());
        accountSummery.setMarginDue(cashAccount.getMarginDue());

        accountSummery.setUnrealizedSales(cashAccount.getPendingSettle());    // from old oms

        accountSummaryResponse.addBuyingPower(accountSummery);

        Header header = new HeaderImpl();
        header.setMsgGroup(MessageConstants.GROUP_INQUIRY);
        header.setMsgType(MessageConstants.RESPONSE_TYPE_BUYING_POWER_UNSOLICITED);
        header.setMsgSessionId("UNSOLICITED");
        header.setLoggedInUserId(customer.getCustomerNumber());
        header.setRespStatus(ValueConstants.RESPONSE_STATUS_SUCCESS);
        //set header unique request id taken from the currently executing thread
        header.setUniqueReqID(StringUtils.getUniqueID());

        return messageProtocolFacade.getJSonString(header, accountSummaryResponse);
    }

    public void setMessageProtocolFacade(MessageProtocolFacade messageProtocolFacade) {
        this.messageProtocolFacade = messageProtocolFacade;
    }


}
