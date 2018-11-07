package seedu.meeting.storage;

import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;

import seedu.meeting.commons.exceptions.IllegalValueException;
import seedu.meeting.logic.parser.ParserUtil;
import seedu.meeting.logic.parser.exceptions.ParseException;
import seedu.meeting.model.meeting.Meeting;
import seedu.meeting.model.meeting.TimeStamp;
import seedu.meeting.model.shared.Address;
import seedu.meeting.model.shared.Description;
import seedu.meeting.model.shared.Title;

// @@author NyxF4ll
/**
 * JAXB-friendly version of the Meeting.
 */
public class XmlAdaptedMeeting {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Meeting's %s field is missing!";
    public static final String MESSAGE_MISSING_LOCATION_FIELD = "Meeting's Location field is missing!";

    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String time;
    @XmlElement(required = true)
    private String location;
    @XmlElement(required = true)
    private String description;

    /**
     * Constructs an XmlAdaptedMeeting.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedMeeting() {}

    /**
     * Constructs an {@code XmlAdaptedMeeting} with the given meeting details.
     */
    public XmlAdaptedMeeting(String title, String time, String location, String description) {
        this.title = title;
        this.time = time;
        this.location = location;
        this.description = description;
    }

    /**
     * Converts a given Meeting into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedMeeting
     */
    public XmlAdaptedMeeting(Meeting source) {
        title = source.getTitle().fullTitle;
        time = source.getTime().toString();
        location = source.getLocation().value;
        description = source.getDescription().statement;
    }

    /**
     * Converts this jaxb-friendly adapted meeting object into the model's Meeting object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Meeting toModelType() throws IllegalValueException {
        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Title.class.getSimpleName()));
        }

        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TimeStamp.class.getSimpleName()));
        }

        if (location == null) {
            throw new IllegalValueException(MESSAGE_MISSING_LOCATION_FIELD);
        }

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }

        Title modelTitle;
        TimeStamp modelTime;
        Address modelLocation;
        Description modelDescription;

        try {
            modelTitle = ParserUtil.parseTitle(title);
            modelTime = ParserUtil.parseTimeStamp(time);
            modelLocation = ParserUtil.parseAddress(location);
            modelDescription = ParserUtil.parseDescription(description);
        } catch (ParseException pe) {
            throw new IllegalValueException(pe.getMessage());
        }

        return new Meeting(modelTitle, modelTime, modelLocation, modelDescription);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedMeeting)) {
            return false;
        }

        XmlAdaptedMeeting otherMeeting = (XmlAdaptedMeeting) other;
        return Objects.equals(title, otherMeeting.title)
                && Objects.equals(time, otherMeeting.time)
                && Objects.equals(location, otherMeeting.location)
                && Objects.equals(description, otherMeeting.description);
    }
}
