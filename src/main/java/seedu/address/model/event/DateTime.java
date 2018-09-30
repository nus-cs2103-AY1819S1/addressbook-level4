package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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

    public LocalDateTime getLocalDateTime() {
        return value;
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

    /**
     * Used for PopUp
     * @param currentDateTime
     * @return True if the time difference is within 1 minute
     */
    public boolean isClose(DateTime currentDateTime) {
        LocalDateTime currentLocalDateTime = currentDateTime.getLocalDateTime();
        long minutes = currentLocalDateTime.until(value, ChronoUnit.MINUTES);
        return (minutes == 0);
    }

    /**
     * Used for PopUp
     * @param currentDateTime
     * @return True if the event has already past
     */
    public boolean isPast(DateTime currentDateTime) {
        LocalDateTime currentLocalDateTime = currentDateTime.getLocalDateTime();
        long minutes = value.until(currentLocalDateTime, ChronoUnit.MINUTES);
        return (minutes >= 1);
    }

}
