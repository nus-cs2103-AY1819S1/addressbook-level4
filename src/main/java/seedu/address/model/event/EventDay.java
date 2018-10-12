package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import java.time.DayOfWeek;
import java.time.LocalDate;

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
                || (other instanceof EventDate // instanceof handles nulls
                && day.equals(((EventDate) other).eventDate)); // state check
    }

    @Override
    public int hashCode() {
        return day.hashCode();
    }
}
