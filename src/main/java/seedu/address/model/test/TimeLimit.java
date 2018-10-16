package seedu.address.model.test;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represent the time limit that the user can take for each card.
 */
public class TimeLimit {
    public static final String MESSAGE_TIME_LIMIT_CONSTRAINTS = "Time limit, which is in seconds, "
            + "must be more than 0.";
    private final float value;

    public TimeLimit(String value) {
        requireNonNull(value);
        checkArgument(isValidTimeLimit(value), MESSAGE_TIME_LIMIT_CONSTRAINTS);
        this.value = Float.parseFloat(value);
    }

    public float getValue() {
        return value;
    }

    /**
     * Returns if a given string is a valid time limit.
     */
    public static boolean isValidTimeLimit(String test) {
        try {
            return Float.parseFloat(test) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Time limit: " + String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TimeLimit
                && value == (((TimeLimit) other).value));
    }

    @Override
    public int hashCode() {
        return String.valueOf(value).hashCode();
    }
}
