package seedu.address.model.leaveapplication;

import java.util.Arrays;

/**
 * Represents an enumeration of possible Status values.
 */
public class StatusEnum {
    /**
     * The enum dictates all possible status values.
     */
    public enum Status {
        PENDING, APPROVED, REJECTED, CANCELLED;
    }

    public static boolean isValidStatus(String statusString) {
        return Arrays.stream(Status.values())
                .anyMatch(status -> statusString.equals(status));
    }
}
