package seedu.scheduler.model.event;

import static seedu.scheduler.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import seedu.scheduler.model.tag.Tag;

/**
 * Represents an Event in the scheduler.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event {

    public static final String MESSAGE_START_END_DATETIME_CONSTRAINTS =
            "Event's start date and time should be equal or before event's end date and time";
    public static final String MESSAGE_END_REPEAT_UNTIL_DATETIME_CONSTRAINTS =
            "Event's end date and time should be equal or before event's repeat until date and time";

    // Identity fields
    private final UUID eventUid; //distinct for recurring events
    private final UUID eventSetUid; //same for recurring events
    private final EventName eventName;
    private final DateTime startDateTime;
    private final DateTime endDateTime;

    // Data fields
    private final Description description;
    private final Venue venue;
    private final RepeatType repeatType;
    private final DateTime repeatUntilDateTime;
    private final Set<Tag> tags = new HashSet<>();
    private ReminderDurationList reminderDurationList;

    /**
     * Original Constructor
     * Every field must be present and not null
     */
    public Event(UUID eventUid, UUID eventSetUid, EventName eventName, DateTime startDateTime, DateTime endDateTime,
                 Description description, Venue venue,
                 RepeatType repeatType, DateTime repeatUntilDateTime, Set<Tag> tags,
                 ReminderDurationList reminderDurationList) {
        requireAllNonNull(eventUid, eventSetUid, eventName, startDateTime, endDateTime, description,
                venue, repeatType, tags, repeatUntilDateTime, reminderDurationList);
        this.eventUid = eventUid;
        this.eventSetUid = eventSetUid;
        this.eventName = eventName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.description = description;
        this.venue = venue;
        this.repeatType = repeatType;
        this.repeatUntilDateTime = repeatUntilDateTime;
        this.tags.addAll(tags);
        this.reminderDurationList = reminderDurationList;
    }

    /**
     * Does not take in eventUid. Will generate a random eventUid
     * Used every time a new event is created
     */
    public Event(UUID eventSetUid, EventName eventName, DateTime startDateTime, DateTime endDateTime,
                 Description description, Venue venue,
                 RepeatType repeatType, DateTime repeatUntilDateTime, Set<Tag> tags,
                 ReminderDurationList reminderDurationList) {
        this(UUID.randomUUID(), eventSetUid, eventName, startDateTime, endDateTime, description,
                venue, repeatType, repeatUntilDateTime, tags, reminderDurationList);
    }


    /**
     * Does not take in reminderDurationList, which is set to an Empty ReminderDurationList
     */
    public Event(UUID eventUid, UUID eventSetUid, EventName eventName, DateTime startDateTime, DateTime endDateTime,
                 Description description, Venue venue,
                 RepeatType repeatType, DateTime repeatUntilDateTime, Set<Tag> tags) {
        this(eventUid, eventSetUid, eventName, startDateTime, endDateTime, description,
                venue, repeatType, repeatUntilDateTime, tags, new ReminderDurationList());
    }

    /**
     * Does not take in reminderDurationList and eventUid
     */
    public Event(UUID eventSetUid, EventName eventName, DateTime startDateTime, DateTime endDateTime,
                 Description description, Venue venue,
                 RepeatType repeatType, DateTime repeatUntilDateTime, Set<Tag> tags) {
        this(UUID.randomUUID(), eventSetUid, eventName, startDateTime, endDateTime, description,
                venue, repeatType, repeatUntilDateTime, tags, new ReminderDurationList());
    }

    /**
     * Does not take in reminderDurationList and eventUid and eventSetUid
     */
    public Event(EventName eventName, DateTime startDateTime, DateTime endDateTime,
                 Description description, Venue venue,
                 RepeatType repeatType, DateTime repeatUntilDateTime, Set<Tag> tags,
                 ReminderDurationList reminderDurationList) {
        this(UUID.randomUUID(), UUID.randomUUID(), eventName, startDateTime, endDateTime,
                description, venue, repeatType, repeatUntilDateTime,
                tags, reminderDurationList);
    }

    public UUID getEventUid() {
        return eventUid;
    }

    public UUID getEventSetUid() {
        return eventSetUid;
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

    public Venue getVenue() {
        return venue;
    }

    public RepeatType getRepeatType() {
        return repeatType;
    }

    public DateTime getRepeatUntilDateTime() {
        return repeatUntilDateTime;
    }

    public ReminderDurationList getReminderDurationList() {
        return reminderDurationList;
    }

    /**
     * Returns true if the Event is an instance of RepeatEvent (EventSet)
     * Returns false otherwise.
     */
    public boolean isRepeatEvent() {
        return this.repeatType != RepeatType.NONE;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if end datetime is after start datetime
     */
    public static boolean isValidEventDateTime(DateTime startDateTime, DateTime endDateTime) {
        return startDateTime.compareTo(endDateTime) <= 0;
    }

    /**
     * Returns true if both event have the same eventSetUid.
     * This defines a weaker notion of equality between two events.
     * Identifies recurring events as the same event
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getEventSetUid().equals(getEventSetUid());
    }

    /**
     * Returns true if both events have the same identity and data fields.
     * This defines a stronger notion of equality between two events.
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
        return otherEvent.getEventSetUid().equals(getEventSetUid())
                && otherEvent.getEventName().equals(getEventName())
                && otherEvent.getStartDateTime().equals(getStartDateTime())
                && otherEvent.getEndDateTime().equals(getEndDateTime())
                && otherEvent.getDescription().equals(getDescription())
                && otherEvent.getVenue().equals(getVenue())
                && otherEvent.getRepeatType().equals(getRepeatType())
                && otherEvent.getRepeatUntilDateTime().equals(getRepeatUntilDateTime())
                && otherEvent.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing our own
        return Objects.hash(eventName, startDateTime, endDateTime, description,
                venue, repeatType, repeatUntilDateTime, tags, reminderDurationList);
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
                .append(getDescription())
                .append(" venue: ")
                .append(getVenue())
                .append(" repeat type: ")
                .append(getRepeatType())
                .append(" repeat until: ")
                .append(getRepeatUntilDateTime())
                .append(" Tags: ").append(getRepeatUntilDateTime())
                .append(" reminders: ")
                .append(getReminderDurationList().toString());
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
