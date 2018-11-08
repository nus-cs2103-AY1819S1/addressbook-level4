package seedu.address.storage;

import static seedu.address.logic.parser.AddEventCommandParser.MESSAGE_INVALID_START_END_TIME;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventAddress;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventDescription;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

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

    @XmlElement
    // eventContacts in XML format should be an ordered list
    private List<XmlAdaptedPerson> eventContacts = new ArrayList<>();

    @XmlElement
    private List<XmlAdaptedTag> eventTags = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedEvent.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEvent() {}

    /**
     * Constructs an {@code XmlAdaptedEvent} with the given event details.
     */
    public XmlAdaptedEvent(String eventName, String eventDescription, String eventDate, String eventStartTime,
                           String eventEndTime, String eventAddress, List<XmlAdaptedPerson> eventContacts,
                           List<XmlAdaptedTag> eventTags) {
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.eventAddress = eventAddress;
        if (eventContacts != null) {
            this.eventContacts = new ArrayList<>(eventContacts);
        }
        if (eventTags != null) {
            this.eventTags = new ArrayList<>(eventTags);
        }
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
        eventContacts = source.getEventContacts().stream()
                .map(XmlAdaptedPerson::new)
                .collect(Collectors.toList());
        eventTags = source.getEventTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted event object into the model's Event object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event
     */
    public Event toModelType() throws IllegalValueException {
        final List<Person> personList = new ArrayList<>();
        for (XmlAdaptedPerson person : eventContacts) {
            personList.add(person.toModelType());
        }

        final List<Tag> tagList = new ArrayList<>();
        for (XmlAdaptedTag tag : eventTags) {
            tagList.add(tag.toModelType());
        }

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

        final Set<Person> modelContacts = new HashSet<>(personList);
        final Set<Tag> modelTags = new HashSet<>(tagList);

        return new Event(modelName, modelDescription, modelDate, modelStartTime, modelEndTime,
                modelAddress, modelContacts, modelTags);
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
                && Objects.equals(eventAddress, otherEvent.eventAddress)
                && eventContacts.equals(otherEvent.eventContacts)
                && eventTags.equals(otherEvent.eventTags);
    }
}
