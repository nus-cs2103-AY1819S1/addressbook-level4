package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents an Event in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event {

    // identity fields
    private final EventName eventName;
    private final EventDescription eventDescription;
    private final EventDate eventDate;
    private final EventTime eventTime;

    // data fields
    private final EventAddress eventAddress;

    /**
     * Every field must be present and not null.
     */
    public Event(EventName eventName, EventDescription eventDescription, EventDate eventDate, EventTime eventTime,
                 EventAddress eventAddress) {
        requireAllNonNull(eventName, eventDescription, eventDate, eventTime, eventAddress);
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventAddress = eventAddress;
    }

    // todo: change type of getter functions when classes for the attributes are created

    public EventName getEventName() {
        return eventName;
    }

    public EventDescription getEventDescription() {
        return eventDescription;
    }

    public EventDate getEventDate() {
        return eventDate;
    }

    public EventTime getEventTime() {
        return eventTime;
    }

    public EventAddress getEventAddress() {
        return eventAddress;
    }

    // todo: define a weaker form of equality to check clashes

    /**
     * Returns true if both persons have the same identity and data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherPerson = (Event) other;
        return otherPerson.getEventName().equals(getEventName())
                && otherPerson.getEventDescription().equals(getEventDescription())
                && otherPerson.getEventDate().equals(getEventDate())
                && otherPerson.getEventTime().equals(getEventTime())
                && otherPerson.getEventAddress().equals(getEventAddress());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(eventName, eventDescription, eventDate, eventTime, eventAddress);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getEventName())
                .append(" EventDescription: ")
                .append(getEventDescription())
                .append(" EventDate: ")
                .append(getEventDate())
                .append(" EventTime: ")
                .append(getEventTime())
                .append(" Address: ")
                .append(getEventAddress());
        return builder.toString();
    }

}

