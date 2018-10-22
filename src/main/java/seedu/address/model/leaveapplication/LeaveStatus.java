package seedu.address.model.leaveapplication;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a LeaveApplication's status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public class LeaveStatus {

    public static final String MESSAGE_STATUS_CONSTRAINTS = "Status has to be APPROVED, REJECTED, CANCELLED, PENDING";
    public final StatusEnum.Status value;

    /**
     * Constructs a {@code LeaveStatus}.
     * @param statusString A string representing a status.
     */
    public LeaveStatus(String statusString) {
        requireNonNull(statusString);
        checkArgument(isValidStatus(statusString), MESSAGE_STATUS_CONSTRAINTS);
        value = StatusEnum.Status.valueOf(statusString);
    }

    /**
     * Returns if string value passed in is a valid StatusEnum.Status.
     */
    public static boolean isValidStatus(String test) {
        requireNonNull(test);
        return StatusEnum.isValidStatus(test);
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
