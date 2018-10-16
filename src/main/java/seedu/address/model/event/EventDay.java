package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import java.time.DayOfWeek;

/**
 * Represents an Event's day in the address book.
 * Guarantees: immutable; and is based on the Enum class {@link DayOfWeek}
 */
public class EventDay {

    private final DayOfWeek day;

    public EventDay(DayOfWeek day) {
        requireNonNull(day);
        this.day = day;
    }

    @Override
    public String toString() {
        return day.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventDay // instanceof handles nulls
                && day.equals(((EventDay) other).day)); // state check
    }

    @Override
    public int hashCode() {
        return day.hashCode();
    }
}
