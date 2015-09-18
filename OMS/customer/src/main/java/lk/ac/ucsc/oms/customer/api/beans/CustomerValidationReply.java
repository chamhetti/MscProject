package lk.ac.ucsc.oms.customer.api.beans;

/**
 * User: Hetti
 * Date: 2/13/13
 * Time: 2:39 PM
 */

/**
 * this interface contains definitions of method for get validation results and get rejection reason if any.
 */
public interface CustomerValidationReply {
    /**
     * customer validation result like SUCCESS, FAILED
     * string representation of enums
     * <ul>
     * <li>customer validation result SUCCESS</li>
     * <li>customer validation result FAILED</li>
     * </ul>
     *
     */
    enum CustomerValidationResult {
        SUCCESS, FAILED
    }

    /**
     * this method returns customer validation result enum value as output with validation result whether validation
     * success or not
     *
     * @return customerValidationResult
     */
    CustomerValidationResult getValidationResult();

    /**
     * this method returns customer status message enum value as the reason for rejecting a customer
     *
     * @return customerStatusMessage {@link}
     */
    CustomerStatusMessages getRejectReason();

}
