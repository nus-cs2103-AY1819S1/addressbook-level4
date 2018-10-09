package seedu.address.testutil;

import seedu.address.model.event.Event;
import seedu.address.model.event.EventAddress;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventDescription;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;

/**
 * A utility class to help with building Event objects. File naming is set to be in line with the other Event utils.
 */

public class ScheduledEventBuilder {

    public static final String DEFAULT_EVENT_NAME = "Doctor Appointment";
    public static final String DEFAULT_EVENT_DESC = "Consultation";
    public static final String DEFAULT_EVENT_DATE = "2018-09-01";
    public static final String DEFAULT_EVENT_TIME = "1015";
    public static final String DEFAULT_EVENT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private EventName eventName;
    private EventDescription eventDescription;
    private EventDate eventDate;
    private EventTime eventTime;
    private EventAddress eventAddress;

    /**
     * Builds a new Event with the default values.
     */
    public ScheduledEventBuilder() {
        eventName = new EventName(DEFAULT_EVENT_NAME);
        eventDescription = new EventDescription(DEFAULT_EVENT_DESC);
        eventDate = new EventDate(DEFAULT_EVENT_DATE);
        eventTime = new EventTime(DEFAULT_EVENT_TIME);
        eventAddress = new EventAddress(DEFAULT_EVENT_ADDRESS);
    }

    /**
     * Initializes the EvebtBuilder with the data of {@code eventToCopy}.
     */
    public ScheduledEventBuilder(Event eventToCopy) {
        eventName = eventToCopy.getEventName();
        eventDescription = eventToCopy.getEventDescription();
        eventDate = eventToCopy.getEventDate();
        eventTime = eventToCopy.getEventTime();
        eventAddress = eventToCopy.getEventAddress();
    }

    /**
     * Sets the {@code EventName} of the {@code Event} that we are building.
     */
    public ScheduledEventBuilder withEventName(String eventName) {
        this.eventName = new EventName(eventName);
        return this;
    }

    /**
     * Sets the {@code EventDescription} of the {@code Event} that we are building.
     */
    public ScheduledEventBuilder withEventDescription(String eventDesc) {
        this.eventDescription = new EventDescription(eventDesc);
        return this;
    }

    /**
     * Sets the {@code EventDate} of the {@code Event} that we are building.
     */
    public ScheduledEventBuilder withEventDate(String eventDate) {
        this.eventDate = new EventDate(eventDate);
        return this;
    }

    /**
     * Sets the {@code EventTime} of the {@code Event} that we are building.
     */
    public ScheduledEventBuilder withEventTime(String eventTime) {
        this.eventTime = new EventTime(eventTime);
        return this;
    }

    /**
     * Sets the {@code EventAddress} of the {@code Event} that we are building.
     */
    public ScheduledEventBuilder withEventAddress(String eventAddress) {
        this.eventAddress = new EventAddress(eventAddress);
        return this;
    }

    public Event build() {
        return new Event(eventName, eventDescription, eventDate, eventTime, eventAddress);
    }
}
