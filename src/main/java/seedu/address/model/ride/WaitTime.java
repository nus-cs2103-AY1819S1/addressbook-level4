package seedu.address.model.ride;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Ride's current waiting time, in minutes, in the park management app.
 * Guarantees: immutable; is valid as declared in {@link #isValidWaitTime(String)}
 */
public class WaitTime {


    public static final String MESSAGE_WAITTIME_CONSTRAINTS =
            "Maintenance should only contain numbers with at least 1 digit long.";
    public static final String WAITTIME_VALIDATION_REGEX = "\\d+";
    public final int value;

    /**
     * Constructs a {@code WaitTime}.
     *
     * @param waitingTime Waiting time in minutes.
     */
    public WaitTime(String waitingTime) {
        requireNonNull(waitingTime);
        checkArgument(isValidWaitTime(waitingTime), MESSAGE_WAITTIME_CONSTRAINTS);
        value = Integer.parseInt(waitingTime);
    }

    /**
     * Returns true if a given string is a valid days since last maintenance.
     */
    public static boolean isValidWaitTime(String test) {
        return test.matches(WAITTIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WaitTime // instanceof handles nulls
                && value == ((WaitTime) other).value); // state check
    }

    @Override
    public int hashCode() {
        return value;
    }

}
