package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Represents an Event's date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class EventDate implements Comparable<EventDate> {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Dates should be in the format YYYY-MM-DD, should be a valid date in the calendar, "
                    + "and it should not be blank";

    /**
     * A blank string " " is considered invalid.
     * Format must be in YYYY-MM-DD, using only - as the separator.
     */
    public static final String DATE_VALIDATION_REGEX = "^[0-9]{4}-"
            + "(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|"
            + "(02-(0[1-9]|[1-2][0-9]))|"
            + "((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$";

    public final LocalDate eventDate;

    /**
     * Constructs a {@code EventDate}.
     *
     * @param eventDate A valid date.
     */
    public EventDate(String eventDate) {
        requireNonNull(eventDate);
        checkArgument(isValidDate(eventDate), MESSAGE_DATE_CONSTRAINTS);
        this.eventDate = LocalDate.parse(eventDate);
    }

    public DayOfWeek getEventDay() {
        return eventDate.getDayOfWeek();
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) throws DateTimeParseException {
        if (!test.matches(DATE_VALIDATION_REGEX)) {
            return false;
        }

        // includes checks for leap years
        try {
            LocalDate.parse(test);
        } catch (DateTimeParseException dte) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return eventDate.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventDate // instanceof handles nulls
                && eventDate.equals(((EventDate) other).eventDate)); // state check
    }

    @Override
    public int compareTo(EventDate other) {
        return eventDate.compareTo(other.eventDate);
    }

    @Override
    public int hashCode() {
        return eventDate.hashCode();
    }

}
