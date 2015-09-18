package lk.ac.ucsc.oms.sequence_generator.api;


public enum SequenceType {

    /**
     * this can use for to generate next order Id
     */
    ORDER_ID(),

    /**
     * this can use for to generate slice order Id
     */
    SLICE_ORDER_ID(),

    /**
     * this can use for to generate next value
     */
    SIMPLE_ID(),

    /**
     * used to generate next exchange id
     */
    EXCHANGE_ID(),

    /**
     * used to generate next institution id
     */
    INSTITUTION_ID(),

    /**
     * used to generate next customer id
     */
    CUSTOMER_ID(),

    /**
     * used to generate next account id
     */
    ACCOUNT_ID(),

    /**
     * used to generate next cash account id
     */
    CASH_ACCOUNT_ID(),

    /**
     * used to generate next cashLog id
     */
    CASH_LOG_ID(),

    /**
     * Used to generate next user login id
     */

    USER_LOGIN_ID(),

    /**
     * Used to generate next user profile id
     */
    USER_PROFILE_ID(),


    /**
     * Used to generate next cs symbol id
     */
    CS_SYMBOL_ID(),


    /**
     * Used to generate next symbol margin info id
     */
    SYMBOL_MARGIN_INFO_ID(),
    /**
     * User ID sequence generator
     */
    USER_ID(),

    /**
     * Exchange sub market id generator
     */
    EXCHANGE_SUB_MARKET_ID;

    SequenceType() {
    }
}
