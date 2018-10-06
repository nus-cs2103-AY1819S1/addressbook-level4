package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalTime;

/**
 * Represents an Event's time in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class Time {

    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Times should be in the 24-hour format HHMM, should be in the range 0000 to 2359, and it should not be " +
                    "blank";

    /**
     * A blank string " " is considered invalid.
     * Format must be in HHMM, without any separators.
     */
    public static final String TIME_VALIDATION_REGEX = "^(20|21|22|23|[01]\\d)" +
            "(([0-5]\\d){1})$";

    public final LocalTime time;

    /**
     * Constructs a {@code Time}.
     *
     * @param time A valid time.
     */
    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_TIME_CONSTRAINTS);
        this.time = LocalTime.parse(getHour(time) + ":" + getMinute(time));
    }

    public String getHour(String time) {
        return time.substring(0,2);
    }

    public String getMinute(String time) {
        return time.substring(2,4);
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return time.toString();
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

}
