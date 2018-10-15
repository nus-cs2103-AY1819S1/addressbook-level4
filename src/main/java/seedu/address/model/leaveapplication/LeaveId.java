package seedu.address.model.leaveapplication;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a LeaveApplication's leave ID in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLeaveId(Integer)}
 */
public class LeaveId {

    public static final String MESSAGE_LEAVEID_CONSTRAINTS =
            "Leave ID should be a non-negative integer";
    public final Integer value;

    /**
     * Constructs a {@code LeaveId}.
     * @param leaveId A valid leave ID.
     */
    public LeaveId(Integer leaveId) {
        requireNonNull(leaveId);
        checkArgument(isValidLeaveId(leaveId), MESSAGE_LEAVEID_CONSTRAINTS);
        value = leaveId;
    }

    /**
     * Returns true if a given integer is a valid leave ID.
     */
    public static boolean isValidLeaveId(Integer test) { return test >= 0; }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LeaveId // instanceof handles nulls
                && value.equals(((LeaveId) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
