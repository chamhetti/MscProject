package lk.ac.ucsc.oms.common_utility.api.enums;


public enum OrderStatus {
    NEW("0"),
    PARTIALLY_FILLED("1"),
    FILLED("2"),
    DONE_FOR_DAY("3"),
    CANCELED("4"),
    REPLACED("5"),
    PENDING_CANCEL("6"),
    STOPPED("7"),
    REJECTED("8"),
    SUSPENDED("9"),
    PENDING_NEW("A"),
    CALCULATED("B"),
    EXPIRED("C"),
    ACCEPTED_FOR_BIDDING("D"),
    PENDING_REPLACE("E"),
    DEFAULT("X"),
    VALIDATED("L"),
    RECEIVED("R"),
    OMS_ACCEPTED("O"),
    SEND_TO_EXECUTION_NEW("N"),
    SEND_TO_EXECUTION_CANCEL("F"),
    SEND_TO_EXECUTION_AMEND("G"),
    NEW_DELETED("V"),
    FILLED_AND_PARTIALITY_FILLED("Y"),
    OMS_RECEIVED("K"),
    ORDER_UNPLACED("U"),
    SEND_TO_OMS_NEW("M"),
    SEND_TO_OMS_CANCEL("P"),
    SEND_TO_OMS_AMEND("Q"),
    SEND_TO_OMS_DELETE("S"),
    NEW_INITIATED("T"),
    AMEND_INITIATED("W"),
    INITIATED("Z"),
    REQUEST_FAILED("H"),
    AMEND_REJECTED("I"),
    CANCEL_REJECTED("J"),
    APPROVED("a"),
    APPROVAL_REJECTED("b"),
    WAITING_FOR_APPROVAL("c"),
    APPROVAL_VALIDATION_REJECTED("d"),
    INVALIDATED_BY_REPLACE("e"),
    INVALIDATED("f"),
    TRADE_CANCELED("h"),
    TRADE_EXPIRED_PFILL("j"),
    ACCEPTED("u"),
    INVALIDATED_BY_CANCEL("m"),
    SEND_TO_MCD("i"),
    SELL_ALLOC_APPROVED("k"),
    SELL_ALLOC_REJECTED("l"),
    PROCESSED("n"),
    PROCESSING("p"),
    WORKING("r"),
    COMPLETED("s"),
    PENDING_TRIGGER("t"),
    STAGED("q");


    private String code;

    OrderStatus(String c) {
        this.code = c;
    }

    /**
     * @return code
     */
    public String getCode() {
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
            case "3":
                return DONE_FOR_DAY;
            case "4":
                return CANCELED;
            case "5":
                return REPLACED;
            case "6":
                return PENDING_CANCEL;
            case "7":
                return STOPPED;
            case "8":
                return REJECTED;
            case "9":
                return SUSPENDED;
            case "A":
                return PENDING_NEW;
            case "B":
                return CALCULATED;
            case "C":
                return EXPIRED;
            case "D":
                return ACCEPTED_FOR_BIDDING;
            case "E":
                return PENDING_REPLACE;
            case "X":
                return DEFAULT;
            case "L":
                return VALIDATED;
            case "R":
                return RECEIVED;
            case "O":
                return OMS_ACCEPTED;
            case "M":
                return SEND_TO_OMS_NEW;
            case "N":
                return SEND_TO_EXECUTION_NEW;
            case "F":
                return SEND_TO_EXECUTION_CANCEL;
            case "G":
                return SEND_TO_EXECUTION_AMEND;
            case "V":
                return NEW_DELETED;
            case "Y":
                return FILLED_AND_PARTIALITY_FILLED;
            case "K":
                return OMS_RECEIVED;
            case "U":
                return ORDER_UNPLACED;
            case "P":
                return SEND_TO_OMS_CANCEL;
            case "Q":
                return SEND_TO_OMS_AMEND;
            case "S":
                return SEND_TO_OMS_DELETE;
            case "T":
                return NEW_INITIATED;
            case "W":
                return AMEND_INITIATED;
            case "Z":
                return INITIATED;
            case "H":
                return REQUEST_FAILED;
            case "I":
                return AMEND_REJECTED;
            case "J":
                return CANCEL_REJECTED;
            case "a":
                return APPROVED;
            case "b":
                return APPROVAL_REJECTED;
            case "c":
                return WAITING_FOR_APPROVAL;
            case "d":
                return APPROVAL_VALIDATION_REJECTED;
            case "e":
                return INVALIDATED_BY_REPLACE;
            case "f":
                return INVALIDATED;
            case "h":
                return TRADE_CANCELED;
            case "j":
                return TRADE_EXPIRED_PFILL;
            case "u":
                return ACCEPTED;
            case "m":
                return INVALIDATED_BY_CANCEL;
            case "i":
                return SEND_TO_MCD;
            case "k":
                return SELL_ALLOC_APPROVED;
            case "l":
                return SELL_ALLOC_REJECTED;
            case "n":
                return PROCESSED;
            case "p":
                return PROCESSING;
            case "r":
                return WORKING;
            case "s":
                return COMPLETED;
            case "t":
                return PENDING_TRIGGER;
            case "q":
                return STAGED;
            default:
                return null;
        }
    }
}
