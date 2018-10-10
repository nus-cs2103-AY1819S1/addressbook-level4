package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import seedu.address.model.tag.Tag;

/**
 * Represents an Event in the scheduler.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event {

    public static final String MESSAGE_DATETIME_CONSTRAINTS =
            "Event's start date and time should be before event's end date and time";

    // Identity fields
    private final UUID uuid;
    private final EventName eventName;
    private final DateTime startDateTime;
    private final DateTime endDateTime;

    // Data fields
    private final Description description;
    private final Priority priority;
    private final Venue venue;
    private final RepeatType repeatType;
    private final DateTime repeatUntilDateTime;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Event(UUID uuid, EventName eventName, DateTime startDateTime, DateTime endDateTime,
                 Description description, Priority priority, Venue venue,
                 RepeatType repeatType, DateTime repeatUntilDateTime) {
        requireAllNonNull(uuid, eventName, startDateTime, endDateTime, description,
                priority, venue, repeatType, repeatUntilDateTime);
        this.uuid = uuid;
        this.eventName = eventName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.description = description;
        this.priority = priority;
        this.venue = venue;
        this.repeatType = repeatType;
        this.repeatUntilDateTime = repeatUntilDateTime;
    }

    public UUID getUuid() {
        return uuid;
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

    public Priority getPriority() {
        return priority;
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
     * Generate all repeated events from {@code targetEvent} according to its repeat type.
     * {@code targetEvent} must have a valid event repeat type.
     * Returns an unmodifiable list of repeated events.
     */
    public static List<Event> generateAllRepeatedEvents(Event targetEvent) {
        switch(targetEvent.getRepeatType()) {
        case DAILY:
            return Collections.unmodifiableList(generateDailyRepeatEvents(targetEvent));
        case WEEKLY:
            return Collections.unmodifiableList(generateWeeklyRepeatEvents(targetEvent));
        case MONTHLY:
            return Collections.unmodifiableList(generateMonthlyRepeatEvents(targetEvent));
        case YEARLY:
            return Collections.unmodifiableList(generateYearlyRepeatEvents(targetEvent));
        default:
            return List.of(targetEvent);
        }
    }

    /**
     * Generate all events that are repeated daily from {@code targetEvent}.
     * Returns a list of events that are repeated daily.
     */
    protected static List<Event> generateDailyRepeatEvents(Event targetEvent) {
        List<Event> repeatedEventList = new ArrayList<>();
        LocalDateTime repeatStartDateTime = targetEvent.getStartDateTime().value;
        LocalDateTime repeatUntilDateTime = targetEvent.getRepeatUntilDateTime().value;
        Duration durationDiff = Duration.between(targetEvent.getStartDateTime().value,
                targetEvent.getEndDateTime().value);
        while (repeatStartDateTime.isBefore(repeatUntilDateTime)) {
            repeatedEventList.add(new Event(
                    targetEvent.getUuid(),
                    targetEvent.getEventName(),
                    new DateTime(repeatStartDateTime),
                    new DateTime(repeatStartDateTime.plus(durationDiff)),
                    targetEvent.getDescription(),
                    targetEvent.getPriority(),
                    targetEvent.getVenue(),
                    targetEvent.getRepeatType(),
                    targetEvent.getRepeatUntilDateTime()
            ));
            repeatStartDateTime = repeatStartDateTime.plusDays(1);
        }
        return repeatedEventList;
    }

    /**
     * Generate all events that are repeated weekly from {@code targetEvent}.
     * Returns a list of events that are repeated weekly.
     */
    protected static List<Event> generateWeeklyRepeatEvents(Event targetEvent) {
        List<Event> repeatedEventList = new ArrayList<>();
        LocalDateTime repeatStartDateTime = targetEvent.getStartDateTime().value;
        LocalDateTime repeatUntilDateTime = targetEvent.getRepeatUntilDateTime().value;
        Duration durationDiff = Duration.between(targetEvent.getStartDateTime().value,
                targetEvent.getEndDateTime().value);
        while (repeatStartDateTime.isBefore(repeatUntilDateTime)) {
            repeatedEventList.add(new Event(
                    targetEvent.getUuid(),
                    targetEvent.getEventName(),
                    new DateTime(repeatStartDateTime),
                    new DateTime(repeatStartDateTime.plus(durationDiff)),
                    targetEvent.getDescription(),
                    targetEvent.getPriority(),
                    targetEvent.getVenue(),
                    targetEvent.getRepeatType(),
                    targetEvent.getRepeatUntilDateTime()
            ));
            repeatStartDateTime = repeatStartDateTime.plusWeeks(1);
        }
        return repeatedEventList;
    }

    /**
     * Generate all events that are repeated monthly from {@code targetEvent}.
     * Returns a list of events that are repeated monthly.
     */
    protected static List<Event> generateMonthlyRepeatEvents(Event targetEvent) {
        List<Event> repeatedEventList = new ArrayList<>();
        LocalDateTime repeatStartDateTime = targetEvent.getStartDateTime().value;
        LocalDateTime repeatUntilDateTime = targetEvent.getRepeatUntilDateTime().value;
        Duration durationDiff = Duration.between(targetEvent.getStartDateTime().value,
                targetEvent.getEndDateTime().value);
        while (repeatStartDateTime.isBefore(repeatUntilDateTime)) {
            repeatedEventList.add(new Event(
                    targetEvent.getUuid(),
                    targetEvent.getEventName(),
                    new DateTime(repeatStartDateTime),
                    new DateTime(repeatStartDateTime.plus(durationDiff)),
                    targetEvent.getDescription(),
                    targetEvent.getPriority(),
                    targetEvent.getVenue(),
                    targetEvent.getRepeatType(),
                    targetEvent.getRepeatUntilDateTime()
            ));
            repeatStartDateTime = repeatStartDateTime.with((temporal) -> {
                do {
                    try {
                        temporal = temporal.plus(1, ChronoUnit.MONTHS).with(ChronoField.DAY_OF_MONTH,
                                targetEvent.getStartDateTime().value.getDayOfMonth());
                    } catch (DateTimeException e) {
                        temporal = temporal.plus(1, ChronoUnit.MONTHS);
                    }
                } while (temporal.get(ChronoField.DAY_OF_MONTH)
                        != targetEvent.getStartDateTime().value.getDayOfMonth());
                return temporal;
            });
        }
        return repeatedEventList;
    }

    /**
     * Generate all events that are repeated yearly from {@code targetEvent}.
     * Returns a list of events that are repeated yearly.
     */
    protected static List<Event> generateYearlyRepeatEvents(Event targetEvent) {
        List<Event> repeatedEventList = new ArrayList<>();
        LocalDateTime repeatStartDateTime = targetEvent.getStartDateTime().value;
        LocalDateTime repeatUntilDateTime = targetEvent.getRepeatUntilDateTime().value;
        Duration durationDiff = Duration.between(targetEvent.getStartDateTime().value,
                targetEvent.getEndDateTime().value);
        while (repeatStartDateTime.isBefore(repeatUntilDateTime)) {
            repeatedEventList.add(new Event(
                    targetEvent.getUuid(),
                    targetEvent.getEventName(),
                    new DateTime(repeatStartDateTime),
                    new DateTime(repeatStartDateTime.plus(durationDiff)),
                    targetEvent.getDescription(),
                    targetEvent.getPriority(),
                    targetEvent.getVenue(),
                    targetEvent.getRepeatType(),
                    targetEvent.getRepeatUntilDateTime()
            ));
            repeatStartDateTime = repeatStartDateTime.with((temporal) -> {
                do {
                    try {
                        temporal = temporal.plus(1, ChronoUnit.YEARS).with(ChronoField.DAY_OF_MONTH,
                                targetEvent.getStartDateTime().value.getDayOfMonth());
                    } catch (DateTimeException e) {
                        temporal = temporal.plus(1, ChronoUnit.YEARS);
                    }
                } while (temporal.get(ChronoField.DAY_OF_MONTH)
                        != targetEvent.getStartDateTime().value.getDayOfMonth());
                return temporal;
            });
        }
        return repeatedEventList;
    }

    /**
     * Returns true if both event have the same uuid.
     * This defines a weaker notion of equality between two events.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getUuid().equals(getUuid());
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
        return otherEvent.getUuid().equals(getUuid())
                && otherEvent.getEventName().equals(getEventName())
                && otherEvent.getStartDateTime().equals(getStartDateTime())
                && otherEvent.getEndDateTime().equals(getEndDateTime())
                && otherEvent.getDescription().equals(getDescription())
                && otherEvent.getPriority().equals(getPriority())
                && otherEvent.getVenue().equals(getVenue())
                && otherEvent.getRepeatType().equals(getRepeatType())
                && otherEvent.getRepeatUntilDateTime().equals(getRepeatUntilDateTime());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing our own
        return Objects.hash(eventName, startDateTime, endDateTime, description,
                priority, venue, repeatType, repeatUntilDateTime);
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
                .append(" priority: ")
                .append(getPriority())
                .append(" venue: ")
                .append(getVenue())
                .append(" repeat type: ")
                .append(getRepeatType())
                .append(" repeat until: ")
                .append(getRepeatUntilDateTime())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
