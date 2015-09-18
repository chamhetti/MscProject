package lk.ac.ucsc.oms.customer.implGeneral.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class AccountNumberStore {
    private static Logger logger = LogManager.getLogger(AccountNumberStore.class);
    private static long lastUpdateTime = 0;
    private static ConcurrentMap<String, List<String>> accountStore = new ConcurrentHashMap<>();


    private AccountNumberStore() {

    }

    public static List<String> getAccountNumbers(String parentCustomerNumber) {
        //clear the store for every 10 min time
        int cacheClearDuration = 10 * 60 * 1000;
        long now = System.currentTimeMillis();
        List<String> accountList;

        if (lastUpdateTime == 0) {
            lastUpdateTime = now;
        }
        if ((now - lastUpdateTime) > cacheClearDuration) {
            try {
                accountStore.clear();
            } catch (Exception e) {
                logger.debug("Nothing to do here", e);
            }
            lastUpdateTime = now;
        }

        accountList = accountStore.get(parentCustomerNumber);

        return accountList;
    }

    public static synchronized void addToAccountStore(String parentCustomerNumber, List<String> accountList) {
        logger.info("Adding parent account list to the cache store. parent number - {}, account list -{}", parentCustomerNumber, accountList);
        accountStore.put(parentCustomerNumber, accountList);
    }
}
