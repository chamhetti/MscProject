package lk.ac.ucsc.oms.stp_connector.implGeneral.bean;

import lk.ac.ucsc.oms.common_utility.api.enums.Delimeter;

import java.util.StringTokenizer;


public class STPMessage {
    public static final String MESSAGE_TYPE_STATUS = "STATUS";
    public static final String MESSAGE_LOG_IN = "LOGGED_INTO_EXCHANGE";
    public static final String MESSAGE_LOG_OUT = "LOGGED_OUT_FROM_EXCHANGE";
    private String exchange;
    private String institution;
    private String message;
    private String type;
    private long sequence;

    /**
     * method used to parse the message from STP and assign values to the fields.
     *
     * @param msg STP message
     */
    public void parseMessage(String msg) {
        StringTokenizer tokens;
        tokens = new StringTokenizer(msg, Delimeter.FIELD_SEPARATOR.getCode());
        exchange = tokens.nextToken();
        institution = tokens.nextToken();
        sequence = Long.parseLong(tokens.nextToken());
        type = tokens.nextToken();
        message = tokens.nextToken().trim();

    }

    /**
     * Getter for exchange
     * @return exchange name
     */
    public String getExchange() {
        return exchange;
    }

    /**
     * Setter for exchange
     * @param exchange exchange name
     */
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    /**
     * Get institution code
     * @return institution code string
     */
    public String getInstitution() {
        return institution;
    }

    /**
     * Set institution code
     * @param institution code
     */
    public void setInstitution(String institution) {
        this.institution = institution;
    }

    /**
     * Method used to get message
     *
     * @return message string
     */
    public String getMessage() {
        return message;
    }

    /**
     * Method used to set message
     * @param message string
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Method used to get type
     * @return type string
     */
    public String getType() {
        return type;
    }

    /**
     * Method used to set type
     *
     * @param type string
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Method used to get sequence id
     *
     * @return sequence id long
     */
    public long getSequence() {
        return sequence;
    }

    /**
     * Method used to set sequence id
     *
     * @param sequence id long
     */
    public void setSequence(long sequence) {
        this.sequence = sequence;
    }
}
