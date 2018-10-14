package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.RepeatType;
import seedu.address.model.event.Venue;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of Event.
 */
public class XmlAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    @XmlElement(required = true)
    private UUID uuid;
    @XmlElement(required = true)
    private String eventName;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private DateTime startDateTime;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private DateTime endDateTime;
    @XmlElement
    private String description;
    @XmlElement
    private String venue;
    @XmlElement(required = true)
    private RepeatType repeatType;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(DateTimeAdapter.class)
    private DateTime repeatUntilDateTime;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedEvent.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEvent() {}

    /**
     * Constructs an {@code XmlAdaptedEvent} with the given event details.
     */
    public XmlAdaptedEvent(UUID uuid, String eventName, DateTime startDateTime, DateTime endDateTime,
                 String description, String venue, RepeatType repeatType,
                           DateTime repeatUntilDateTime, List<XmlAdaptedTag> tagged) {
        this.uuid = uuid;
        this.eventName = eventName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.description = description;
        this.venue = venue;
        this.repeatType = repeatType;
        this.repeatUntilDateTime = repeatUntilDateTime;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Event into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedEvent
     */
    public XmlAdaptedEvent(Event source) {
        uuid = source.getUuid();
        eventName = source.getEventName().value;
        startDateTime = source.getStartDateTime();
        endDateTime = source.getEndDateTime();
        description = source.getDescription().value;
        venue = source.getVenue().value;
        repeatType = source.getRepeatType();
        repeatUntilDateTime = source.getRepeatUntilDateTime();
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted event object into the model's Event object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted event
     */
    public Event toModelType() throws IllegalValueException {

        final List<Tag> eventTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            eventTags.add(tag.toModelType());
        }

        if (uuid == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, UUID.class.getSimpleName()));
        }
        final UUID modelUuid = uuid;

        if (eventName == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, EventName.class.getSimpleName()));
        }
        if (!EventName.isValidEventName(eventName)) {
            throw new IllegalValueException(EventName.MESSAGE_EVENT_NAME_CONSTRAINTS);
        }
        final EventName modelName = new EventName(eventName);

        if (startDateTime == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, DateTime.class.getSimpleName()));
        }
        final DateTime modelStartDateTime = startDateTime;

        if (endDateTime == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, DateTime.class.getSimpleName()));
        }
        final DateTime modelEndDateTime = endDateTime;

        if (description == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        final Description modelDescription = new Description(description);

        if (venue == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Venue.class.getSimpleName()));
        }
        final Venue modelVenue = new Venue(venue);

        if (repeatType == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, RepeatType.class.getSimpleName()));
        }
        final RepeatType modelRepeatType = repeatType;

        if (repeatUntilDateTime == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, DateTime.class.getSimpleName()));
        }
        final DateTime modelRepeatUntilDateTime = repeatUntilDateTime;

        final Set<Tag> modelTags = new HashSet<>(eventTags);

        return new Event(modelUuid, modelName, modelStartDateTime, modelEndDateTime, modelDescription,
                modelVenue, modelRepeatType, modelRepeatUntilDateTime, modelTags);
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
        return Objects.equals(uuid, otherEvent.uuid)
                && Objects.equals(eventName, otherEvent.eventName)
                && Objects.equals(startDateTime, otherEvent.startDateTime)
                && Objects.equals(endDateTime, otherEvent.endDateTime)
                && Objects.equals(description, otherEvent.description)
                && Objects.equals(repeatType, otherEvent.repeatType)
                && Objects.equals(repeatUntilDateTime, otherEvent.repeatUntilDateTime)
                && tagged.equals(otherEvent.tagged);
    }
}
