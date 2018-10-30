package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

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
    private final EventAddress eventAddress;

    // data fields
    private Set<Person> eventContacts;
    private Set<Tag> eventTags = new HashSet<>();

    /**
     * Every field must be present and not null. End time must be later than start time.
     */
    public Event(EventName eventName, EventDescription eventDescription, EventDate eventDate, EventTime eventStartTime,
                 EventTime eventEndTime, EventAddress eventAddress, Set<Tag> eventTags) {
        requireAllNonNull(eventName, eventDescription, eventDate, eventStartTime, eventAddress, eventTags);

        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
        this.eventDay = new EventDay(eventDate.getEventDay());
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.eventAddress = eventAddress;
        this.eventContacts = new HashSet<>();
        this.eventTags.addAll(eventTags);
    }

    /**
     * Every field must be present and not null. End time must be later than start time.
     * This constructor allows for direct creation of an event with a non-empty {@code eventContacts}.
     */
    public Event(EventName eventName, EventDescription eventDescription, EventDate eventDate, EventTime eventStartTime,
                 EventTime eventEndTime, EventAddress eventAddress, Set<Person> eventContacts, Set<Tag> eventTags) {
        requireAllNonNull(eventName, eventDescription, eventDate, eventStartTime, eventAddress);
        requireAllNonNull(eventContacts);

        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
        this.eventDay = new EventDay(eventDate.getEventDay());
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.eventAddress = eventAddress;
        this.eventContacts = eventContacts;
        this.eventTags = eventTags;
    }

    /**
     * Sets the eventContacts of this event to {@code eventContacts}. The input must not contain any null values,
     * i.e. each element of {@code eventContacts} should be a valid Person object.
     */
    public void setEventContacts(Set<Person> eventContacts) {
        requireAllNonNull(eventContacts);
        this.eventContacts = eventContacts;
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

    /**
     * Returns an immutable {@code Person} set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Person> getEventContacts() {
        return Collections.unmodifiableSet(eventContacts);
    }

    /**
     * Returns an immutable event {@code Tag} set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getEventTags() {
        return Collections.unmodifiableSet(eventTags);
    }

    /**
     * Returns true if the two events clash
     */
    public boolean isClashingEvent(Event event) {
        return eventDate.equals(event.eventDate)
                && eventEndTime.compareTo(event.eventStartTime) > 0
                && eventStartTime.compareTo(event.eventEndTime) < 0;
    }

    //todo: check isSameEvent constraints
    /**
     * Returns true if both events are equal
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getEventName().equals(getEventName())
                && otherEvent.getEventDescription().equals(getEventDescription())
                && otherEvent.getEventDate().equals(getEventDate())
                && otherEvent.getEventStartTime().equals(getEventStartTime())
                && otherEvent.getEventEndTime().equals(getEventEndTime())
                && otherEvent.getEventAddress().equals(getEventAddress());
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

        Event otherEvent = (Event) other;
        return otherEvent.getEventName().equals(getEventName())
                && otherEvent.getEventDescription().equals(getEventDescription())
                && otherEvent.getEventDate().equals(getEventDate())
                && otherEvent.getEventStartTime().equals(getEventStartTime())
                && otherEvent.getEventEndTime().equals(getEventEndTime())
                && otherEvent.getEventAddress().equals(getEventAddress())
                && otherEvent.getEventContacts().equals(getEventContacts())
                && otherEvent.getEventTags().equals(getEventTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(eventName, eventDescription, eventDate, eventStartTime, eventEndTime,
                eventAddress, eventContacts, eventTags);
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
                .append(" Event address: ")
                .append(getEventAddress())
                .append(" Event tags: ")
                .append(getEventTags());
        getEventContacts().forEach(builder::append);
        return builder.toString();
    }

}

