package ssp.scheduleplanner.model.task;

import static java.util.Objects.requireNonNull;
import static ssp.scheduleplanner.commons.util.AppUtil.checkArgument;

/**
 * This class encapsulates information about the Interval class.
 * It is used for the AddRepeatCommand.
 */
public class Interval {
    public static final String MESSAGE_INTERVAL_CONSTRAINTS = "Interval should be positive integer\n"
            + "Where the number refers to the interval of repetitions.\n";

    public final String value;

    public Interval(String interval) {
        requireNonNull(interval);
        checkArgument(isValidInterval(interval), MESSAGE_INTERVAL_CONSTRAINTS);
        value = interval;
    }

    /**
     * Returns if a given string is a valid interval.
     * @param test The number of intervals in String format
     */
    public static boolean isValidInterval(String test) {
        int intValue = Integer.parseInt(test);
        return intValue > 0;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Repeat
                && value.equals(((Repeat) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
