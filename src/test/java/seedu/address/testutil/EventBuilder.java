package seedu.address.testutil;

import java.time.LocalDateTime;
import java.util.UUID;

import seedu.address.model.event.DateTime;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Priority;
import seedu.address.model.event.RepeatType;
import seedu.address.model.event.Venue;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final UUID DEFAULT_UUID = UUID.randomUUID();
    public static final String DEFAULT_EVENT_NAME = "CS2103 Lecture";
    public static final LocalDateTime DEFAULT_START_DATE_TIME =
            LocalDateTime.of(2018, 9, 21, 16, 0);
    public static final LocalDateTime DEFAULT_END_DATE_TIME =
            LocalDateTime.of(2018, 9, 21, 18, 0);
    public static final String DEFAULT_DESCRIPTION = "My CS2103 Lecture";
    public static final Priority DEFAULT_PRIORIY = Priority.NONE;
    public static final String DEFAULT_VENUE = "iCube";
    public static final RepeatType DEFAULT_REPEAT_TYPE = RepeatType.NONE;
    public static final LocalDateTime DEFAULT_REPEAT_UNTIL_DATE_TIME =
            LocalDateTime.of(2018, 9, 21, 18, 0);

    private UUID uuid;
    private EventName eventName;
    private DateTime startDateTime;
    private DateTime endDateTime;
    private Description description;
    private Priority priority;
    private Venue venue;
    private RepeatType repeatType;
    private DateTime repeatUntilDateTime;

    public EventBuilder() {
        uuid = DEFAULT_UUID;
        eventName = new EventName(DEFAULT_EVENT_NAME);
        startDateTime = new DateTime(DEFAULT_START_DATE_TIME);
        endDateTime = new DateTime(DEFAULT_END_DATE_TIME);
        description = new Description(DEFAULT_DESCRIPTION);
        priority = DEFAULT_PRIORIY;
        venue = new Venue(DEFAULT_VENUE);
        repeatType = DEFAULT_REPEAT_TYPE;
        repeatUntilDateTime = new DateTime(DEFAULT_REPEAT_UNTIL_DATE_TIME);
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        uuid = eventToCopy.getUuid();
        eventName = eventToCopy.getEventName();
        startDateTime = eventToCopy.getStartDateTime();
        endDateTime = eventToCopy.getEndDateTime();
        description = eventToCopy.getDescription();
        priority = eventToCopy.getPriority();
        venue = eventToCopy.getVenue();
        repeatType = eventToCopy.getRepeatType();
        repeatUntilDateTime = eventToCopy.getRepeatUntilDateTime();
    }

    /**
     * Sets the {@code uuid} of the {@code Event} that we are building.
     */
    public EventBuilder withUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    /**
     * Sets the {@code EventName} of the {@code Event} that we are building.
     */
    public EventBuilder withEventName(String eventName) {
        this.eventName = new EventName(eventName);
        return this;
    }

    /**
     * Sets the {@code startDateTime} of the {@code Event} that we are building.
     */
    public EventBuilder withStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = new DateTime(startDateTime);
        return this;
    }

    /**
     * Sets the {@code endDateTime} of the {@code Event} that we are building.
     */
    public EventBuilder withEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = new DateTime(endDateTime);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Event} that we are building.
     */
    public EventBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code Event} that we are building.
     */
    public EventBuilder withPriority(Priority priority) {
        this.priority = priority;
        return this;
    }

    /**
     * Sets the {@code Venue} of the {@code Event} that we are building.
     */
    public EventBuilder withVenue(String venue) {
        this.venue = new Venue(venue);
        return this;
    }

    /**
     * Sets the {@code RepeatType} of the {@code Event} that we are building.
     */
    public EventBuilder withRepeatType(RepeatType repeatType) {
        this.repeatType = repeatType;
        return this;
    }

    /**
     * Sets the {@code repeatUntilDateTime} of the {@code Event} that we are building.
     */
    public EventBuilder withRepeatUntilDateTime(LocalDateTime repeatUntilDateTime) {
        this.repeatUntilDateTime = new DateTime(repeatUntilDateTime);
        return this;
    }

    /**
     * Initialise a new {@code Event} instance
     */
    public Event build() {
        return new Event(uuid, eventName, startDateTime, endDateTime, description,
                priority, venue, repeatType, repeatUntilDateTime);
    }

}
