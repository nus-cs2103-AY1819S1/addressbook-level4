package seedu.address.model.leaveapplication;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a LeaveApplication's status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(StatusEnum.Status)}
 */
public class LeaveStatus {

    public static final String MESSAGE_STATUS_CONSTRAINTS = "Status should be non-empty";
    public final StatusEnum.Status value;

    /**
     * Constructs a {@code LeaveStatus}.
     * @param status A valid status.
     */
    public LeaveStatus(StatusEnum.Status status) {
        requireNonNull(status);
        checkArgument(isValidStatus(status), MESSAGE_STATUS_CONSTRAINTS);
        value = status;
    }

    /**
     * Returns if value passed in is a StatusEnum.Status, because enums are type-safe.
     */
    public static boolean isValidStatus(StatusEnum.Status test) {
        requireNonNull(test);
        return test instanceof StatusEnum.Status;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LeaveStatus // instanceof handles nulls
                && value.equals(((LeaveStatus) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
