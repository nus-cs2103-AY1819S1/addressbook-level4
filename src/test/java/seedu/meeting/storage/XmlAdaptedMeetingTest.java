package seedu.meeting.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.meeting.storage.XmlAdaptedMeeting.MESSAGE_MISSING_LOCATION_FIELD;
import static seedu.meeting.storage.XmlAdaptedMeeting.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.meeting.testutil.TypicalMeetings.REHEARSAL;
import static seedu.meeting.testutil.TypicalMeetings.WEEKLY;

import org.junit.Test;

import seedu.meeting.commons.exceptions.IllegalValueException;
import seedu.meeting.model.meeting.TimeStamp;
import seedu.meeting.model.shared.Address;
import seedu.meeting.model.shared.Description;
import seedu.meeting.model.shared.Title;
import seedu.meeting.testutil.Assert;

public class XmlAdaptedMeetingTest {

    private static final String INVALID_TITLE = "@$#!&@"; // Invalid characters.
    private static final String INVALID_TIME = "100-100-200@11:210"; // Invalid date and time.
    private static final String INVALID_LOCATION = ""; // Location must not be a blank string.
    private static final String INVALID_DESCRIPTION = ""; // Description must not be a blank string.

    private static final String VALID_TITLE = REHEARSAL.getTitle().fullTitle;
    private static final String VALID_TIME = REHEARSAL.getTime().toString();
    private static final String VALID_LOCATION = REHEARSAL.getLocation().value;
    private static final String VALID_DESCRIPTION = REHEARSAL.getDescription().statement;

    @Test
    public void toModelType_validMeetingDetails_returnsMeeting() throws Exception {
        XmlAdaptedMeeting meeting = new XmlAdaptedMeeting(VALID_TITLE, VALID_TIME, VALID_LOCATION, VALID_DESCRIPTION);
        assertEquals(meeting.toModelType(), REHEARSAL);
    }

    @Test
    public void toModelType_nullTitle_throwsIllegalValueException() {
        XmlAdaptedMeeting meeting = new XmlAdaptedMeeting(null, VALID_TIME, VALID_LOCATION, VALID_DESCRIPTION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName());

        Assert.assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_nullTime_throwsIllegalValueException() {
        XmlAdaptedMeeting meeting = new XmlAdaptedMeeting(VALID_TITLE, null, VALID_LOCATION, VALID_DESCRIPTION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TimeStamp.class.getSimpleName());

        Assert.assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_nullLocation_throwsIllegalValueException() {
        XmlAdaptedMeeting meeting = new XmlAdaptedMeeting(VALID_TITLE, VALID_TIME, null, VALID_DESCRIPTION);
        String expectedMessage = String.format(MESSAGE_MISSING_LOCATION_FIELD);

        Assert.assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        XmlAdaptedMeeting meeting = new XmlAdaptedMeeting(VALID_TITLE, VALID_TIME, VALID_LOCATION, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());

        Assert.assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_invalidTitle_throwsIllegalValueException() {
        XmlAdaptedMeeting meeting =
                new XmlAdaptedMeeting(INVALID_TITLE, VALID_TIME, VALID_LOCATION, VALID_DESCRIPTION);
        String expectedMessage = Title.MESSAGE_TITLE_CONSTRAINTS;

        Assert.assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_invalidTime_throwsIllegalValueException() {
        XmlAdaptedMeeting meeting =
                new XmlAdaptedMeeting(VALID_TITLE, INVALID_TIME, VALID_LOCATION, VALID_DESCRIPTION);
        String expectedMessage = TimeStamp.MESSAGE_TIMESTAMP_CONSTRAINT;

        Assert.assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_invalidLocation_throwsIllegalValueException() {
        XmlAdaptedMeeting meeting =
                new XmlAdaptedMeeting(VALID_TITLE, VALID_TIME, INVALID_LOCATION, VALID_DESCRIPTION);
        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        System.out.println(VALID_TIME);
        Assert.assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        XmlAdaptedMeeting meeting =
                new XmlAdaptedMeeting(VALID_TITLE, VALID_TIME, VALID_LOCATION, INVALID_DESCRIPTION);
        String expectedMessage = Description.MESSAGE_DESCRIPTION_CONSTRAINTS;

        Assert.assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void equals() {
        XmlAdaptedMeeting standardMeeting = new XmlAdaptedMeeting(REHEARSAL);
        // same values -> returns true
        XmlAdaptedMeeting xmlAdaptedMeetingWithSameValues =
                new XmlAdaptedMeeting(VALID_TITLE, VALID_TIME, VALID_LOCATION, VALID_DESCRIPTION);
        assertTrue(standardMeeting.equals(xmlAdaptedMeetingWithSameValues));

        // same object -> returns true
        assertTrue(standardMeeting.equals(standardMeeting));

        // null -> returns false
        assertFalse(standardMeeting.equals(null));

        // different types -> returns false
        assertFalse(standardMeeting.equals(new XmlAdaptedPerson()));

        // different title -> returns false
        assertFalse(standardMeeting.equals(new XmlAdaptedMeeting(WEEKLY.getTitle().fullTitle,
                VALID_TIME, VALID_LOCATION, VALID_DESCRIPTION)));

        // different time -> returns false
        assertFalse(standardMeeting.equals(new XmlAdaptedMeeting(VALID_TITLE, WEEKLY.getTime().toString(),
                VALID_LOCATION, VALID_DESCRIPTION)));

        // different location -> returns false
        assertFalse(standardMeeting.equals(new XmlAdaptedMeeting(VALID_TITLE, VALID_TIME, WEEKLY.getLocation().value,
                VALID_DESCRIPTION)));

        // different description -> returns false
        assertFalse(standardMeeting.equals(new XmlAdaptedMeeting(VALID_TITLE, VALID_TIME, VALID_LOCATION,
                WEEKLY.getDescription().statement)));

    }
}
