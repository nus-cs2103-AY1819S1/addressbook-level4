package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.meeting.Meeting;

/**
 * JAXB-friendly version of the Meeting.
 */
public class XmlAdaptedMeeting {
    @XmlElement(required = true)
    private String meeting;

    /**
     * Constructs an XmlAdaptedMeeting.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedMeeting() {}

    /**
     * Constructs a {@code XmlAdaptedMeeting} with the given {@code meeting}.
     */
    public XmlAdaptedMeeting(String meeting) {
        this.meeting = meeting;
    }

    /**
     * Converts a given Meeting into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedMeeting(Meeting source) {
        meeting = source.value;
    }

    /**
     * Converts this jaxb-friendly adapted meeting object into the model's Meeting object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted meeting
     */
    public Meeting toModelType() throws IllegalValueException {
        if (!Meeting.isValidMeeting(meeting)) {
            throw new IllegalValueException(Meeting.MESSAGE_MEETING_CONSTRAINTS);
        }
        return new Meeting(meeting);
    }


}
