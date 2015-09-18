package com.directfn.exchange_simulator.message_processor.impl.helper;

/**
 * User: Ruchira Ranaweera
 * Date: 5/8/14
 */
public final class FIXConstant {
    public static final String BEGIN_STRING = "8=";
    public static final String BODY_LENGTH = "9=";
    public static final String MSG_TYPE = "35=";
    public static final String SENDER_COMP_ID = "49=";
    public static final String TARGET_COMP_ID = "56=";
    public static final String MSG_SEQ_NUM = "34=";
    public static final String SENDING_TIME = "52=";
    public static final String TRADING_SESSION_ID = "336=";
    public static final String TRAD_SES_STATUS = "340=";
    public static final String TEXT = "58=";
    public static final String TRAD_SES_REQ_ID = "335=";
    public static final String CHECK_SUM = "10=";
    public static final String ENCRYPT_METHOD = "98=";
    public static final String HEART_BT_INT = "108=";
    public static final String TARGET_SUBID = "57=";
    public static final String SENDER_SUBID = "50=";
    public static final String DELIV_COMP_ID = "128=";
    public static final String DELIV_SUB_ID = "129=";
    public static final String PORTFOLIO_NO = "1=";
    public static final String EXG = "207=";
    public static final String SYMBOL = "55=";
    public static final String SYMBOL_CODE = "48=";
    public static final String ORD_ID = "37=";
    public static final String ORD_STATUS = "39=";
    public static final String ORD_SIDE = "54=";
    public static final String CLORD_ID = "11=";
    public static final String ORIG_CLORD_ID = "41=";
    public static final String ORD_TYPE = "40=";
    public static final String PRICE = "44=";
    public static final String TIF = "59=";
    public static final String ORD_QTY = "38=";
    public static final String TRANSACTION_TIME = "60=";
    public static final String MIN_QTY = "110=";    // Minimum Quantity
    public static final String MAX_FLOOR = "111=";    // Maximum floor

    public static final String OPT = "OPT";
    public static final char T54_BUY = '1';
    public static final char T54_SELL = '2';

    private FIXConstant() {

    }

}
