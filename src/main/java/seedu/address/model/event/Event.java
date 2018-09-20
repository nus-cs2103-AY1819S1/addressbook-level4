package seedu.address.model.event;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents an Event in the scheduler.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event {

    public static final String MESSAGE_DATETIME_CONSTRAINTS =
            "Event's start date and time should be before event's end date and time";

    // Identity fields
    private final EventName eventName;
    private final DateTime startDateTime;
    private final DateTime endDateTime;

    // Data fields
    private final Description description;

    /**
     * Every field must be present and not null.
     */
    public Event(EventName eventName, DateTime startDateTime, DateTime endDateTime, Description description) {
        requireAllNonNull(eventName, startDateTime, endDateTime, description);
        this.eventName = eventName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.description = description;
    }

    public EventName getEventName() {
        return eventName;
    }

    public DateTime getStartDateTime() {
        return startDateTime;
    }

    public DateTime getEndDateTime() {
        return endDateTime;
    }

    public Description getDescription() {
        return description;
    }

    /**
     * Returns true end datetime is not before start datetime
     */
    public static boolean isValidEventDateTime(DateTime startDateTime, DateTime endDateTime) {
        return startDateTime.compareTo(endDateTime) <= 0;
    }

    /**
     * Returns true if both events of the same name have the same startDateTime and endDateTime.
     * This defines a weaker notion of equality between two events.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getEventName().equals(getEventName())
                && (otherEvent.getStartDateTime().equals(getStartDateTime())
                || otherEvent.getEndDateTime().equals(getEndDateTime()));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return otherEvent.getEventName().equals(getEventName())
                && otherEvent.getStartDateTime().equals(getStartDateTime())
                && otherEvent.getEndDateTime().equals(getEndDateTime())
                && otherEvent.getDescription().equals(getDescription());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing our own
        return Objects.hash(eventName, startDateTime, endDateTime, description);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getEventName())
                .append(" startDateTime: ")
                .append(getStartDateTime())
                .append(" endDateTime: ")
                .append(getEndDateTime())
                .append(" description: ")
                .append(getDescription());
        return builder.toString();
    }

}
