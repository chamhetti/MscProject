package lk.ac.ucsc.oms.fix.impl.parserLogic;


import lk.ac.ucsc.oms.customer.api.AccountManager;
import lk.ac.ucsc.oms.customer.api.CustomerManager;
import lk.ac.ucsc.oms.customer.api.beans.account.Account;

import lk.ac.ucsc.oms.exchanges.api.ExchangeManager;

import lk.ac.ucsc.oms.orderMgt.api.OrderSearchFacadeInterface;
import lk.ac.ucsc.oms.symbol.api.SymbolManager;
import lk.ac.ucsc.oms.symbol.api.beans.BaseSymbol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class OrderMapper {
    private static Logger logger = LogManager.getLogger(OrderMapper.class);

    private AccountManager accountManager;
    private CustomerManager customerManager;
    private SymbolManager symbolManager;
    private ExchangeManager exchangeManager;
    private OrderSearchFacadeInterface orderSearchFacade;



    /**
     * Get the Symbol Bean using the Remote Symbol and Security type
     *
     * @param symbol       is the symbol
     * @param exchange     is the security exchange
     * @param securityType is the security type
     * @param it           base symbol security type values as an iterator
     * @return the Base Symbol Bean
     */
    private BaseSymbol getSymbol(String symbol, String exchange, String securityType, BaseSymbol.SecurityType[] it) {
        logger.info("Getting Symbol Bean. Remote Symbol - {}, Remote Exchange - {}, Security Type - {}", symbol, exchange, securityType);
        BaseSymbol baseSymbolBean = null;
        try {
            //if the fix order has a security type then take it to find the symbol bean
            if (securityType != null) {
                baseSymbolBean = symbolManager.getSymbol(symbol, exchange, BaseSymbol.SecurityType.getEnum(securityType));
            } else {
                //if the fix order doesn't have valid security type
                //check the valid symbol is available in the system with OMS supported security types
                //if find valid symbol then consider it as the valid symbol bean
                for (BaseSymbol.SecurityType anIt : it) {
                    baseSymbolBean = symbolManager.getSymbol(symbol, exchange, BaseSymbol.SecurityType.getEnum(anIt.getCode()));
                    if (baseSymbolBean != null) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Cannot get the symbol. Error in the Symbol: " + symbol, e.getMessage());
        }
        return baseSymbolBean;
    }

    /**
     * Get the security exchange code by passing the execution destination and the security exchange
     *
     * @param destination      is the destination
     * @param securityExchange is the security exchange
     * @return the exchange code
     */
    private String getExchangeCode(String destination, String securityExchange) {
        logger.info("Getting the Exchange Code. Remote Execution Destination - {}, Security Exchange - [}",
                destination, securityExchange);
        String exchange = null;
        try {
            exchange = exchangeManager.getMubasherExchangeCode(destination, securityExchange);
        } catch (Exception e) {
            logger.error("Cannot get the Exchange Code. Error in the Exchange: " + securityExchange, e);
        }
        return exchange;
    }

    /**
     * Get the mubasher portfolio number by giving the order portfolio number,security exchange, fix login name
     *
     * @param portfolioNo      is the portfolio number
     * @param securityExchange is the security exchange
     * @param fixLoginName     is the fix login name
     * @return the mubasher portfolio number
     */
    private String getPortfolioByExchangeAcc(String portfolioNo, String securityExchange, String fixLoginName) {
        logger.info("Get portfolio by exchange account. Remote portfolio number - {}, Security exchange - {}, " +
                "Fix login Name - {}", portfolioNo, securityExchange, fixLoginName);
        String portfolioNO = null;
        try {
            portfolioNO = accountManager.getAccountNumberByExchangeAccount(portfolioNo, securityExchange);
        } catch (Exception e) {
            logger.error("Cannot Locate the Portfolio NO for the PortfolioNo| Exchange | Fix Order Login Name " + portfolioNo + "| " + securityExchange + " |" + fixLoginName, e);
        }
        return portfolioNO;
    }

    /**
     * Get the Security Account Bean
     *
     * @param portfolioNo is the portfolio no
     * @return the Security Account
     */
    private Account getSecurityAccBean(String portfolioNo) {
        Account secAccBean = null;
        try {
            secAccBean = accountManager.getAccount(portfolioNo);
        } catch (Exception e) {
            logger.info("Cannot Locate the Security Account for the portfolio| Exchange " + portfolioNo + "| ");
        }
        return secAccBean;
    }


    /**
     * Inject account manager interface
     *
     * @param accountManager {@link AccountManager}
     */
    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    /**
     * Inject the customer manager interface
     *
     * @param customerManager {@link CustomerManager}
     */
    public void setCustomerManager(CustomerManager customerManager) {
        this.customerManager = customerManager;
    }

    /**
     * Inject the symbol manager interface
     *
     * @param symbolManager {@link SymbolManager}
     */
    public void setSymbolManager(SymbolManager symbolManager) {
        this.symbolManager = symbolManager;
    }

    /**
     * Inject the exchange manager
     *
     * @param exchangeManager {@link ExchangeManager}
     */
    public void setExchangeManager(ExchangeManager exchangeManager) {
        this.exchangeManager = exchangeManager;
    }



    /**
     * Inject the order search facade
     *
     * @param orderSearchFacade {@link OrderSearchFacadeInterface}
     */
    public void setOrderSearchFacade(OrderSearchFacadeInterface orderSearchFacade) {
        this.orderSearchFacade = orderSearchFacade;
    }



}
