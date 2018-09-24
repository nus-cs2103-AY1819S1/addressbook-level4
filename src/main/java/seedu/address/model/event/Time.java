package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Event's time in the application.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class Time {
    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Event times can take in HH:mm input, and should not be blank";

    /*
     * HH accepts 0-9, 1-9, 00-09, 10-19, 20-23
     * mm accepts 00-59
     */
    public static final String TIME_VALIDATION_REGEX = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

    public final String value;

    /**
     * Constructs an {@code Time}.
     *
     * @param time A valid Time.
     */
    public Time(String time) {
        requireNonNull(time);
        checkArgument(isValidTime(time), MESSAGE_TIME_CONSTRAINTS);
        value = time;
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }

    /**
     * Returns true if current time occurs at an earlier period or at the same period as the other time.
     */
    public boolean isLessThanOrEqualTo(Time otherTime) {
        if (otherTime == this) {
            return true;
        }

        String[] timeParts = this.toString().split(":");
        //parseInt ignores leading zeros like 01 or 09 when converting from String to int
        int minute = Integer.parseInt(timeParts[1]);
        int hour = Integer.parseInt(timeParts[0]);

        String[] otherTimeParts = otherTime.toString().split("-");
        int otherMinute = Integer.parseInt(otherTimeParts[1]);
        int otherHour = Integer.parseInt(otherTimeParts[0]);

        if (hour > otherHour) {
            return false;
        } else if (minute > otherMinute) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && value.equals(((Time) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
