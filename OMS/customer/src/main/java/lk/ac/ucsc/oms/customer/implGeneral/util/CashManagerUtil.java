package lk.ac.ucsc.oms.customer.implGeneral.util;


public final class CashManagerUtil {
    private CashManagerUtil (){

    }

    public static String getKey(String customerNumber, String accountNumber){
       return customerNumber+"_"+accountNumber;
    }

    public static String getAccountNumber(String key){
        return key.split("_")[1];
    }

    public static String getCustomerNumber(String key) {
        return key.split("_")[0];
    }
}
