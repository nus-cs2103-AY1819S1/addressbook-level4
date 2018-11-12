package seedu.address.model.order;

/**
 * Represents an Order's Status in the order book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)} (String)}
 */
public class OrderStatus {

    public static final String MESSAGE_STATUS_CONSTRAINTS =
            "Status should be either: PENDING, ONGOING or COMPLETED";

    /**
     *  Valid Status Fields.
     */
    private enum Status {
        PENDING,
        ONGOING,
        COMPLETED
    }

    private Status orderState = null;

    /**
     * Constructs a {@code OrderStatus}.
     */
    public OrderStatus() {
        this(Status.PENDING.name());
    }

    /**
     * Constructs a {@code OrderStatus} with a parameter.
     *
     * @param status A valid Order Status string
     */
    public OrderStatus(String status) {
        orderState = Status.valueOf(status);
    }

    /**
     * Checks if the status is ongoing.
     */
    public boolean isOngoingStatus() {
        return orderState.equals(Status.ONGOING);
    }

    /**
     * Checks if the status is completed.
     */
    public boolean isCompletedStatus() {
        return orderState.equals(Status.COMPLETED);
    }

    /**
     * Returns true if a given string is a valid status.
     */
    public static boolean isValidStatus(String orderStatus) {

        for (Status s : Status.values()) {
            if (orderStatus.equals(s.name())) {
                return true;
            }
        }

        return false;
    }


    @Override
    public String toString() {
        return orderState.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OrderStatus // instanceof handles nulls
                && orderState.equals(((OrderStatus) other).orderState)); // state check
    }

    @Override
    public int hashCode() {
        return orderState.hashCode();
    }

}
