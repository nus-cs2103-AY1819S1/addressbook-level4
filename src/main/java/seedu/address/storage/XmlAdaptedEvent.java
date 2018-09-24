package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Date;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.Location;
import seedu.address.model.event.Name;
import seedu.address.model.event.Time;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Event.
 */
public class XmlAdaptedEvent {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String location;
    @XmlElement(required = true)
    private String startDate;
    @XmlElement(required = true)
    private String endDate;
    @XmlElement(required = true)
    private String startTime;
    @XmlElement(required = true)
    private String endTime;
    @XmlElement(required = true)
    private String description;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedEvent() {}

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given event details.
     */
    public XmlAdaptedEvent(String name, String location, String startDate, String endDate,
                           String startTime, String endTime, String description, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
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
        name = source.getName().fullName;
        location = source.getLocation().value;
        startDate = source.getStartDate().value;
        endDate = source.getEndDate().value;
        startTime = source.getStartTime().value;
        endTime = source.getEndTime().value;
        description = source.getDescription().description;

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

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (location == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                                            Location.class.getSimpleName()));
        }
        if (!Location.isValidLocation(location)) {
            throw new IllegalValueException(Location.MESSAGE_LOCATION_CONSTRAINTS);
        }
        final Location modelLocation = new Location(location);

        if (startDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(startDate)) {
            throw new IllegalValueException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        final Date modelStartDate = new Date(startDate);

        if (endDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(endDate)) {
            throw new IllegalValueException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        final Date modelEndDate = new Date(endDate);

        if (startTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }
        if (!Time.isValidTime(startTime)) {
            throw new IllegalValueException(Time.MESSAGE_TIME_CONSTRAINTS);
        }
        final Time modelStartTime = new Time(startTime);

        if (endTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }
        if (!Time.isValidTime(endTime)) {
            throw new IllegalValueException(Time.MESSAGE_TIME_CONSTRAINTS);
        }
        final Time modelEndTime = new Time(endTime);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                                                Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);


        final Set<Tag> modelTags = new HashSet<>(eventTags);
        return new Event(modelName, modelLocation, modelStartDate, modelEndDate, modelStartTime, modelEndTime,
                            modelDescription, modelTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedEvent)) {
            return false;
        }

        XmlAdaptedEvent otherPerson = (XmlAdaptedEvent) other;
        return Objects.equals(name, otherPerson.name)
                && Objects.equals(location, otherPerson.location)
                && Objects.equals(startDate, otherPerson.startDate)
                && Objects.equals(endDate, otherPerson.endDate)
                && Objects.equals(startTime, otherPerson.startTime)
                && Objects.equals(endTime, otherPerson.endTime)
                && Objects.equals(description, otherPerson.description)
                && tagged.equals(otherPerson.tagged);
    }
}
