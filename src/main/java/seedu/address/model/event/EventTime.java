package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 * Represents an Event's time in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTime(String)}
 */
public class EventTime {

    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Times should be in the 24-hour format HHMM, should be in the range 0000 to 2359, and it should not be " +
                    "blank";

    /**
     * A blank string " " is considered invalid.
     * Format must be in HHMM, without any separators.
     */
    public static final String TIME_VALIDATION_REGEX = "^(20|21|22|23|[01]\\d)"
            + "(([0-5]\\d){1})$";

    public final LocalTime eventTime;

    /**
     * Constructs a {@code EventTime}.
     *
     * @param eventTime A valid time.
     */
    public EventTime(String eventTime) {
        requireNonNull(eventTime);
        checkArgument(isValidTime(eventTime), MESSAGE_TIME_CONSTRAINTS);
        this.eventTime = LocalTime.parse(getHour(eventTime) + ":" + getMinute(eventTime));
    }

    public static String getHour(String time) {
        return time.substring(0,2);
    }

    public static String getMinute(String time) {
        return time.substring(2,4);
    }

    /**
     * Returns true if a given string is a valid time.
     */
    public static boolean isValidTime(String test) {
        if (!test.matches(TIME_VALIDATION_REGEX)) {
            return false;
        }

        try {
            LocalTime.parse(getHour(test) + ":" + getMinute(test));
        } catch (DateTimeParseException dte) {
            return false;
        }
        return true;
    }

    /**
     * @return String representation of time in HH:MM
     */
    @Override
    public String toString() {
        return eventTime.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventTime // instanceof handles nulls
                && eventTime.equals(((EventTime) other).eventTime)); // state check
    }

    @Override
    public int hashCode() {
        return eventTime.hashCode();
    }

}
