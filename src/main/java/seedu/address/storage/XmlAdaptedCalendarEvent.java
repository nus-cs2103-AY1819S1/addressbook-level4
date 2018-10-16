package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.model.calendarevent.Description;
import seedu.address.model.calendarevent.Title;
import seedu.address.model.calendarevent.Venue;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Calendar Event.
 */
public class XmlAdaptedCalendarEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";

    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String description;
    @XmlElement(required = true)
    private String venue;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedCalendarEvent.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedCalendarEvent() {
    }

    /**
     * Constructs an {@code XmlAdaptedCalendarEvent} with the given calendar event details.
     */
    public XmlAdaptedCalendarEvent(String title, String description, String venue,
                                   List<XmlAdaptedTag> tagged) {
        this.title = title;
        this.description = description;
        this.venue = venue;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Calendar Event into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedCalendarEvent
     */
    public XmlAdaptedCalendarEvent(CalendarEvent source) {
        title = source.getTitle().value;
        description = source.getDescription().value;
        venue = source.getVenue().value;
        tagged = source.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted calendarevent object into the model's CalendarEvent object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted calendar event
     */
    public CalendarEvent toModelType() throws IllegalValueException {
        final List<Tag> calendarEventTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            calendarEventTags.add(tag.toModelType());
        }

        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        if (!Title.isValid(title)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }
        final Title modelName = new Title(title);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Description.class.getSimpleName()));
        }
        if (!Description.isValid(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (venue == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Venue.class.getSimpleName()));
        }
        if (!Venue.isValid(venue)) {
            throw new IllegalValueException(Venue.MESSAGE_CONSTRAINTS);
        }
        final Venue modelVenue = new Venue(venue);

        final Set<Tag> modelTags = new HashSet<>(calendarEventTags);
        return new CalendarEvent(modelName, modelDescription, modelVenue, modelTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedCalendarEvent)) {
            return false;
        }

        XmlAdaptedCalendarEvent otherCalendarEvent = (XmlAdaptedCalendarEvent) other;
        return Objects.equals(title, otherCalendarEvent.title)
            && Objects.equals(description, otherCalendarEvent.description)
            && Objects.equals(venue, otherCalendarEvent.venue)
            && tagged.equals(otherCalendarEvent.tagged);
    }
}
