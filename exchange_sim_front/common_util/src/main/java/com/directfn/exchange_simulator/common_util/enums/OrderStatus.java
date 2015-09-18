package com.directfn.exchange_simulator.common_util.enums;


public enum OrderStatus {
    NEW('0'),
    PARTIALLY_FILLED('1'),
    FILLED('2'),
    CANCELED('4'),
    REPLACED('5'),
    PENDING_CANCEL('6'),
    REJECTED('8'),
    PENDING_NEW('A'),
    PENDING_REPLACE('E'),
    AMEND_REJECTED('I'),
    CANCEL_REJECTED('J'),
    EXPIRED('C');


    private char code;

    private OrderStatus(char c) {
        this.code = c;
    }

    /**
     * @return code
     */
    public char getCode() {
        return this.code;
    }

    /**
     * @param code Sting Market Code
     * @return code
     */
    public static OrderStatus getEnum(String code) {

        if (code == null) {
            return null;
        }

        switch (code) {
            case "0":
                return NEW;
            case "1":
                return PARTIALLY_FILLED;
            case "2":
                return FILLED;
            case "4":
                return CANCELED;
            case "5":
                return REPLACED;
            case "6":
                return PENDING_CANCEL;
            case "8":
                return REJECTED;
            case "A":
                return PENDING_NEW;
            case "E":
                return PENDING_REPLACE;
            case "I":
                return AMEND_REJECTED;
            case "J":
                return CANCEL_REJECTED;
            case "C":
                return EXPIRED;
            default:
                return null;
        }
    }
}
