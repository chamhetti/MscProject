package lk.ac.ucsc.oms.fix.impl.util;


public final class FIXConstants {
    //t35 msg type values
    public static final char REJECT = '3';
    public static final char EXECUTION_REPORT = '8';
    public static final char ORDER_CANCEL_REJECT = '9';
    public static final char NEW_ORDER_SINGLE = 'D';
    public static final char ORDER_CANCEL_REQUEST = 'F';
    public static final char ORDER_CANCEL_REPLACE = 'G';
    public static final char QUOTE = 'S';
    public static final char SECURITY_DEFINITION = 'd';
    public static final char TRADING_SESSION_STATUS_REQUEST = 'g';
    public static final char TRADING_SESSION_STATUS = 'h';
    public static final char BUSINESS_MESSAGE_REJECT = 'j';

    //t39 order status values
    public static final char T39_NEW = '0';
    public static final char T39_PARTIALLY_FILLED = '1';
    public static final char T39_FILLED = '2';
    public static final char T39_DONE_FOR_DAY = '3';
    public static final char T39_CANCELLED = '4';
    public static final char T39_REPLACED = '5';
    public static final char T39_PENDING_CANCEL = '6';
    public static final char T39_STOPPED = '7';
    public static final char T39_REJECTED = '8';
    public static final char T39_SUSPENDED = '9';
    public static final char T39_PENDING_NEW = 'A';
    public static final char T39_CALCULATED = 'B';
    public static final char T39_EXPIRED = 'C';
    public static final char T39_PENDING_REPLACE = 'E';
    public static final char T39_AMEND_REJECTED = 'I';
    public static final char T39_CANCEL_REJECTED = 'J';
    public static final char T39_NEW_INITIATED = 'T';
    public static final char T39_CANCEL_INITIATED = 'U';
    public static final char T39_NEW_DELETED = 'V';
    public static final char T39_AMEND_INITIATED = 'W';
    public static final char T39_FILLED_AND_PARTIALFILLED = 'Y';

    //order sides
    public static final char T54_BUY = '1';
    public static final char T54_SELL = '2';

    //order status codes
    public static final String REJECT_CODE = "201";
    public static final String REPLACE_CODE = "202";
    public static final String NEW_INITIATED_CODE = "203";
    public static final String SUSPENDED_CODE = "204";
    public static final String NEW_DELETED_CODE = "205";
    public static final String EXPIRED_CODE = "206";
    public static final String PENDING_NEW_CODE = "207";
    public static final String PARTIALLY_FILLED_CODE = "208";
    public static final String FILLED_CODE = "209";
    public static final String FILLED_AND_PARTIALFILLED_CODE = "210";
    public static final String CANCELLED_CODE = "211";
    public static final String AMEND_INITIATED_CODE = "212";
    public static final String CANCEL_INITIATED_CODE = "213";
    public static final String AMEND_REJECTED_CODE = "214";
    public static final String CANCEL_REJECTED_CODE = "215";

    //fix field names according to the fix spec
    public static final int PORTFOLIO_NO = 1;
    public static final int AVG_PX = 6;
    public static final int FIX_VERSION = 8;
    public static final int BODY_LENGTH = 9;
    public static final int CHECK_SUM = 10;
    public static final int CLORD_ID = 11;
    public static final int COMMISSION = 12;
    public static final int CUM_QTY = 14;
    public static final int CURRENCY = 15;
    public static final int EXEC_ID = 17;
    public static final int EXEC_TRANS_TYPE = 20;
    public static final int HANDLE_INST = 21;
    public static final int SECURITY_ID_SOURCE = 22;
    public static final int LAST_PX = 31;
    public static final int LAST_SHARES = 32;
    public static final int MSG_SEQ_NO = 34;
    public static final int MSG_TYPE = 35;
    public static final int ORDER_ID = 37;
    public static final int ORDER_QTY = 38;
    public static final int ORD_STATUS = 39;
    public static final int ORD_TYPE = 40;
    public static final int ORIG_CLORD_ID = 41;
    public static final int POSS_DUP_FLAG = 43;
    public static final int PRICE = 44;
    public static final int SECURITY_ID = 48;
    public static final int SENDER_COMP_ID = 49;
    public static final int SENDER_SUB_ID = 50;
    public static final int SENDING_TIME = 52;
    public static final int SIDE = 54;
    public static final int SYMBOL = 55;
    public static final int TARGET_COMP_ID = 56;
    public static final int TARGET_SUB_ID = 57;
    public static final int TEXT = 58;
    public static final int TIME_IN_FORCE = 59;
    public static final int TRANSACTION_TIME = 60;
    public static final int SETTLEMNT_TYPE = 63;
    public static final int SETTLEMNT_DATE = 64;
    public static final int SIGNATURE = 89;
    public static final int SECURE_DATA_LEN = 90;
    public static final int SIGNATURE_LENGTH = 93;
    public static final int POSS_RESEND = 97;
    public static final int STOP_PX = 99;
    public static final int EX_DESTINATION = 100;
    public static final int ORD_REJ_REASON = 102;
    public static final int ORD_REJ_REASON_103 = 103;
    public static final int MIN_QTY = 110;
    public static final int MAX_FLOOR = 111;
    public static final int ON_BEHALFOF_COMPID = 115;
    public static final int ON_BEHALFOF_SUBID = 116;
    public static final int ORIG_SENDING_TIME = 122;
    public static final int EXPIRE_TIME = 126;
    public static final int SUB_ID = 128;
    public static final int DELIVER_TO_SUBID = 129;
    public static final int SENDER_LOCATION_ID = 142;
    public static final int TARGET_LOCATION_ID = 143;
    public static final int ON_BEHALFOF_LOCATION_ID = 144;
    public static final int DELIVER_TO_LOCATION_ID = 145;
    public static final int EXEC_TYPE = 150;
    public static final int LEAVES_QTY = 151;
    public static final int SECURITY_TYPE = 167;
    public static final int MATURITY_MONTH_YEAR = 200;
    public static final int PUT_OR_CALL = 201;
    public static final int STRIKE_PRICE = 202;
    public static final int SECURITY_EXCHANGE = 207;
    public static final int XML_DATA_LEN = 212;
    public static final int XML_DATA = 213;
    public static final int SUBCRIPTION_REQUEST_TYPE = 263;
    public static final int UNDERLYING_SYMBOL = 311;
    public static final int TRAD_SES_REQ_ID = 335;
    public static final int TRAD_SES_ID = 336;
    public static final int TRAD_SES_METHOD = 338;
    public static final int TRAD_SESSION_STATUS = 340;
    public static final int MESSAGE_ENCODING = 347;
    public static final int LAST_MSG_SEQNO = 369;
    public static final int ON_BEHAF_OF_SENDING_TIME = 370;
    public static final int SOLICITED_FLAG = 377;
    public static final int EXPIRE_DATE = 432;
    public static final int CXL_REJ_RESPONSE_TO = 434;
    public static final int ESIS_ORDER_NO = 800;
    public static final int CHANNEL = 801;
    public static final int USER_ID = 802;
    public static final int CREATED_DATE = 804;
    public static final int TW_ORD_ID = 811;
    public static final int MAX_PRICE = 812;
    public static final int BROKER_ID = 815;
    public static final int SEQUENCE_NO = 816;
    public static final int MANUAL_REQUEST_STATUS = 805;
    public static final int SYMBOL_6035 = 6035;
    public static final int ALLOW_SMALL_ORDER = 7435;
    public static final int SIDE_9959 = 9959;
    public static final int ORDER_ID_9962 = 9962;
    public static final int CREATED_DATE_9964 = 9964;
    public static final int ORD_STATUS_9971 = 9971;
    public static final int TEXT_9972 = 9972;
    public static final int REQUEST_STATUS = 9973;
    public static final int CLORD_ID_9974 = 9974;
    public static final int ORD_VALUE = 9977;
    public static final int MESSAGE_ID = 9998;
    public static final int APPIA_MSG_ID = 9999;

    //order categories used
    public static final int NORMAL_ORDER = 0;
    public static final int MBS_ORDER = 1;
    public static final int EIS_ORDER = 2;

    //security types
    public static final String CS = "CS";
    public static final String OPT = "OPT";

    //request status
    public static final int REQUEST_STATUS_CONST = 100;

    //ExecTransactionTypes
    public static final int T20_NEW = 0;
    public static final int T20_CANCEL = 1;
    public static final int T20_CORRECT = 2;
    public static final int T20_STATUS = 3;

    public static final char T434_CANCEL_REJECT = '1';
    public static final char T434_AMEND_REJECT = '2';

    /**
     * By T21_BROKER_INTERVENTION_OK will set as fix tag 21 and when it is set as 2 it will placed as desk orders
     */
    public static final int T21_BROKER_INTERVENTION_OK = 2;

    public static final char T150_PARTIAL_FILL = '1';
    public static final char T150_FILLED = '2';
    public static final char T150_CANCELED = '4';
    public static final char T150_REPLACE = '5';
    public static final char T150_PENDING_CANCEL = '6';
    public static final char T150_PENDING_REPLACE = 'E';
    public static final char T150_TRADE = 'F';
    public static final char T150_DEFAULT = 'X';


    //FIXConstants 340 Trading Market Status
    public static final int T340_UNKNOWN = 0;
    public static final int T340_HALTED = 1;
    public static final int T340_OPEN = 2;
    public static final int T340_CLOSED = 3;
    public static final int T340_PRE_OPEN = 4;
    public static final int T340_PRE_CLOSE = 5;
    public static final int T340_REQUEST_REJECTED = 6;

    //file separators
    public static final String FIELD_SEPERATOR = "\u0001";
    public static final String TAG_VALUE_SEPERATOR = "\u003D";
    public static final String PORTFOLIO_SEPERATOR = "|";

    //order channels
    public static final int CHANNEL_WEB = 1;
    public static final int CHANNEL_TW = 2;
    public static final int CHANNEL_DC = 3;
    public static final int CHANNEL_MANUAL = 4;
    public static final int CHANNEL_FIX = 5;

    //fix versions
    public static final String FIX_VER_4 = "FIX.4.0";
    public static final String FIX_VER_42 = "FIX.4.2";
    public static final String FIX_VER_44 = "FIX.4.4";
    public static final String FIX_VER_T11 = "FIXT.1.1";

    //security id sources
    public static final String CUSIP = "1";
    public static final String SEDOL = "2";
    public static final String QUIK = "3";
    public static final String ISIN_CODE = "4";
    public static final String REUTER_CODE = "5";
    public static final String EXCHANGE_SYMBOL = "8";


    /**
     * Added private constructor to avoid initialize from external source
     */
    private FIXConstants() {
    }
}
