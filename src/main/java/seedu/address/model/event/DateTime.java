package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;

/**
 * Represents an Event's date and time in the scheduler.
 * Guarantees: immutable; is valid
 */
public class DateTime implements Comparable<DateTime> {

    public final LocalDateTime value;

    /**
     * Constructs a {@code DateTime}.
     *
     * @param formmatedDateTime A local date time.
     */
    public DateTime(LocalDateTime formmatedDateTime) {
        requireNonNull(formmatedDateTime);
        value = formmatedDateTime;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateTime // instanceof handles nulls
                && value.equals(((DateTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(DateTime otherDateTime) {
        return value.compareTo(otherDateTime.value);
    }
}
