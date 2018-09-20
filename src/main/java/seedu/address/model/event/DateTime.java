package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;

/**
 * Represents a Event's date and time in the scheduler.
 * Guarantees: immutable; is valid
 */
public class DateTime implements Comparable<DateTime> {

    public final LocalDateTime dateTime;

    /**
     * Constructs a {@code DateTime}.
     *
     * @param formmatedDateTime A local date time.
     */
    public DateTime(LocalDateTime formmatedDateTime) {
        requireNonNull(formmatedDateTime);
        dateTime = formmatedDateTime;
    }

    @Override
    public String toString() {
        return dateTime.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                && dateTime.equals(((DateTime) other).dateTime)); // state check
    }

    @Override
    public int hashCode() {
        return dateTime.hashCode();
    }

    @Override
    public int compareTo(DateTime otherDateTime) {
        return dateTime.compareTo(otherDateTime.dateTime);
    }
}
