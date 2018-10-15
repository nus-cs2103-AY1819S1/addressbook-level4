package seedu.address.storage;

import static seedu.address.logic.parser.AddEventCommandParser.MESSAGE_INVALID_START_END_TIME;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventAddress;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventDescription;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;

/**
 * JAXB-friendly version of the Event.
 */
public class XmlAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    @XmlElement(required = true)
    private String eventName;
    @XmlElement(required = true)
    private String eventDescription;
    @XmlElement(required = true)
    private String eventDate;
    @XmlElement(required = true)
    private String eventStartTime;
    @XmlElement(required = true)
    private String eventEndTime;
    @XmlElement(required = true)
    private String eventAddress;

    /**
     * Constructs an XmlAdaptedEvent.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEvent() {}

    /**
     * Constructs an {@code XmlAdaptedEvent} with the given event details.
     */
    public XmlAdaptedEvent(String eventName, String eventDescription, String eventDate, String eventStartTime,
                           String eventEndTime, String eventAddress) {
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.eventAddress = eventAddress;
    }

    /**
     * Converts a given Event into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedEvent
     */
    public XmlAdaptedEvent(Event source) {
        eventName = source.getEventName().eventName;
        eventDescription = source.getEventDescription().eventDescription;
        eventDate = source.getEventDate().toString();
        eventStartTime = source.getEventStartTime().toString();
        eventEndTime = source.getEventEndTime().toString();
        eventAddress = source.getEventAddress().eventAddress;
    }

    /**
     * Converts this jaxb-friendly adapted event object into the model's Event object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event
     */
    public Event toModelType() throws IllegalValueException {
        if (eventName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EventName.class.getSimpleName()));
        }
        if (!EventName.isValidName(eventName)) {
            throw new IllegalValueException(EventName.MESSAGE_NAME_CONSTRAINTS);
        }
        final EventName modelName = new EventName(eventName);

        if (eventDescription == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EventDescription.class.getSimpleName()));
        }
        if (!EventDescription.isValidDescription(eventDescription)) {
            throw new IllegalValueException(EventDescription.MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        final EventDescription modelDescription = new EventDescription(eventDescription);

        if (eventDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EventDate.class.getSimpleName()));
        }
        if (!EventDate.isValidDate(eventDate)) {
            throw new IllegalValueException(EventDate.MESSAGE_DATE_CONSTRAINTS);
        }
        final EventDate modelDate = new EventDate(eventDate);

        if (eventStartTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "Start" + EventTime.class.getSimpleName()));
        }
        if (!EventTime.isValidTime(eventStartTime)) {
            throw new IllegalValueException(EventTime.MESSAGE_TIME_CONSTRAINTS);
        }
        final EventTime modelStartTime = new EventTime(eventStartTime);

        if (eventEndTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "End" + EventTime.class.getSimpleName()));
        }
        if (!EventTime.isValidTime(eventEndTime)) {
            throw new IllegalValueException(EventTime.MESSAGE_TIME_CONSTRAINTS);
        }
        final EventTime modelEndTime = new EventTime(eventEndTime);

        // check for logical start and end time
        if (modelEndTime.compareTo(modelStartTime) < 0) {
            throw new IllegalValueException(String.format(MESSAGE_INVALID_START_END_TIME, modelStartTime,
                    modelEndTime));
        }

        if (eventAddress == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EventAddress.class.getSimpleName()));
        }
        if (!EventAddress.isValidAddress(eventAddress)) {
            throw new IllegalValueException(EventAddress.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final EventAddress modelAddress = new EventAddress(eventAddress);

        return new Event(modelName, modelDescription, modelDate, modelStartTime, modelEndTime, modelAddress);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedEvent)) {
            return false;
        }

        XmlAdaptedEvent otherEvent = (XmlAdaptedEvent) other;
        return Objects.equals(eventName, otherEvent.eventName)
                && Objects.equals(eventDescription, otherEvent.eventDescription)
                && Objects.equals(eventDate, otherEvent.eventDate)
                && Objects.equals(eventStartTime, otherEvent.eventStartTime)
                && Objects.equals(eventEndTime, otherEvent.eventEndTime)
                && Objects.equals(eventAddress, otherEvent.eventAddress);
    }
}
