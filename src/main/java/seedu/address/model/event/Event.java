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
    private final EventDay eventDay;
    private final EventTime eventStartTime;
    private final EventTime eventEndTime;

    // data fields
    private final EventAddress eventAddress;

    /**
     * Every field must be present and not null. End time must be later than start time.
     */
    public Event(EventName eventName, EventDescription eventDescription, EventDate eventDate, EventTime eventStartTime,
                 EventTime eventEndTime, EventAddress eventAddress) {
        requireAllNonNull(eventName, eventDescription, eventDate, eventStartTime, eventAddress);

        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
        this.eventDay = new EventDay(eventDate.getEventDay());
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.eventAddress = eventAddress;
    }

    public EventName getEventName() {
        return eventName;
    }

    public EventDescription getEventDescription() {
        return eventDescription;
    }

    public EventDate getEventDate() {
        return eventDate;
    }

    public EventTime getEventStartTime() {
        return eventStartTime;
    }

    public EventTime getEventEndTime() {
        return eventEndTime;
    }

    public EventAddress getEventAddress() {
        return eventAddress;
    }

    public EventDay getEventDay() {
        return eventDay;
    }

    // todo: define a weaker form of equality to check clashes
    /**
     * Returns true if both events are equal
     */
    public boolean isSameEvent(Event event) {
        return equals(event);
    }

    /**
     * Returns true if both events have the same identity and data fields.
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
                && otherPerson.getEventStartTime().equals(getEventStartTime())
                && otherPerson.getEventEndTime().equals(getEventEndTime())
                && otherPerson.getEventAddress().equals(getEventAddress());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(eventName, eventDescription, eventDate, eventStartTime, eventAddress);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getEventName())
                .append(" Event description: ")
                .append(getEventDescription())
                .append(" Event date: ")
                .append(getEventDate())
                .append(" Event day: ")
                .append(getEventDay())
                .append(" Event start time: ")
                .append(getEventStartTime())
                .append(" Event end time: ")
                .append(getEventEndTime())
                .append(" Event Address: ")
                .append(getEventAddress());
        return builder.toString();
    }

}

