package seedu.address.model.timetable;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the time in a 24 hour format, with 30 minute intervals permitted only.
 * Guarantees: immutable; is valid as declared in {@Link #isValidTime(String)}
 */
public class Time implements Comparable<Time> {
    public static final String MESSAGE_TIME_CONSTRAINTS = "Time should only be in the 24-hour format in 30 minute"
            + "intervals, without any colons. For example: 2330, 0000, 0130";

    /**
     * Regular expression used to test whether the time falls between 0000 and 2330.
     * Time must be in 30 minute intervals.
     */
    public static final String TIME_VALIDATION_REGEX = "([01]\\d|2[0-3])([03]0)";

    public final String time;

    /**
     * Constructs a {@code Time}.
     *
     * @param time A valid time.
     */
    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_TIME_CONSTRAINTS);
        this.time = time;
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return time;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && time.equals(((Time) other).time)); // state check
    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }

    /**
     * Makes the Time class comparable, with earlier times preceding the later times.
     * @param other the other Time.
     */
    @Override
    public int compareTo(Time other) {
        return this.time.compareTo(other.time);
    }
}
