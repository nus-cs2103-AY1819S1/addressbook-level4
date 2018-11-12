package seedu.address.model.deliveryman.exceptions;

/**
 * Indicated that the operation exceeds the order limit in deliveryman.
 */
public class OrdersLimitExceededException extends Exception {
    public OrdersLimitExceededException() {
        super("Operations would result in more orders than limit.");
    }
}
