package seedu.scheduler.testutil;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import seedu.scheduler.model.event.DateTime;
import seedu.scheduler.model.event.Description;
import seedu.scheduler.model.event.Event;
import seedu.scheduler.model.event.EventName;
import seedu.scheduler.model.event.ReminderDurationList;
import seedu.scheduler.model.event.RepeatType;
import seedu.scheduler.model.event.Venue;
import seedu.scheduler.model.tag.Tag;
import seedu.scheduler.model.util.SampleSchedulerDataUtil;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {
    public static final UUID DEFAULT_EVENTUID = UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a621");
    public static final UUID DEFAULT_EVENTSETUID = UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a633");
    public static final String DEFAULT_EVENT_NAME = "CS2103 Lecture";
    public static final LocalDateTime DEFAULT_START_DATE_TIME =
            LocalDateTime.of(2018, 9, 21, 16, 0);
    public static final LocalDateTime DEFAULT_END_DATE_TIME =
            LocalDateTime.of(2018, 9, 21, 18, 0);
    public static final String DEFAULT_DESCRIPTION = "My CS2103 Lecture";
    public static final String DEFAULT_VENUE = "iCube";
    public static final RepeatType DEFAULT_REPEAT_TYPE = RepeatType.NONE;
    public static final LocalDateTime DEFAULT_REPEAT_UNTIL_DATE_TIME =
            LocalDateTime.of(2018, 9, 21, 18, 0);

    private UUID eventUid;
    private UUID eventSetUid;
    private EventName eventName;
    private DateTime startDateTime;
    private DateTime endDateTime;
    private Description description;
    private Venue venue;
    private RepeatType repeatType;
    private DateTime repeatUntilDateTime;
    private Set<Tag> tags;
    private ReminderDurationList reminderDurationList;

    public EventBuilder() {
        eventUid = DEFAULT_EVENTUID;
        eventSetUid = DEFAULT_EVENTSETUID;
        eventName = new EventName(DEFAULT_EVENT_NAME);
        startDateTime = new DateTime(DEFAULT_START_DATE_TIME);
        endDateTime = new DateTime(DEFAULT_END_DATE_TIME);
        description = new Description(DEFAULT_DESCRIPTION);
        venue = new Venue(DEFAULT_VENUE);
        repeatType = DEFAULT_REPEAT_TYPE;
        repeatUntilDateTime = new DateTime(DEFAULT_REPEAT_UNTIL_DATE_TIME);
        tags = new HashSet<>();
        reminderDurationList = new ReminderDurationList();
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        eventUid = eventToCopy.getEventUid();
        eventSetUid = eventToCopy.getEventSetUid();
        eventName = eventToCopy.getEventName();
        startDateTime = eventToCopy.getStartDateTime();
        endDateTime = eventToCopy.getEndDateTime();
        description = eventToCopy.getDescription();
        venue = eventToCopy.getVenue();
        repeatType = eventToCopy.getRepeatType();
        repeatUntilDateTime = eventToCopy.getRepeatUntilDateTime();
        tags = new HashSet<>(eventToCopy.getTags());
        reminderDurationList = eventToCopy.getReminderDurationList();
    }

    /**
     * Sets the {@code eventUid} of the {@code Event} that we are building.
     */
    public EventBuilder withEventUid(UUID uid) {
        this.eventUid = uid;
        return this;
    }

    /**
     * Sets the {@code eventSetUid} of the {@code Event} that we are building.
     */
    public EventBuilder withEventSetUid(UUID uuid) {
        this.eventSetUid = uuid;
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
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Event} that we are building.
     */
    public EventBuilder withTags(String ... tags) {
        this.tags = SampleSchedulerDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Parses the {@code reminderDurationList} into a {@code ReminderDurationList}
     * and set it to the {@code Event} that we are building.
     */
    public EventBuilder withReminderDurationList(ReminderDurationList reminderDurationList) {
        this.reminderDurationList = reminderDurationList;
        return this;
    }

    /**
     * Initialise a new {@code Event} instance
     */
    public Event build() {
        return new Event(eventUid, eventSetUid, eventName, startDateTime, endDateTime, description,
                venue, repeatType, repeatUntilDateTime, tags, reminderDurationList);
    }

}
