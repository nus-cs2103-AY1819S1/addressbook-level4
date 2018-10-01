package seedu.address.model.meeting;

import org.junit.jupiter.api.Test;
import seedu.address.testutil.Assert;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

//@@author AyushChatto
class MeetingTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Meeting(null));
    }

    @Test
    public void constructor_invalidMeeting_throwsIllegalArgumentException() {
        String invalidMeeting = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Meeting(invalidMeeting));
    }

    @Test
    void isValidMeeting() {
        // null meeting
        Assert.assertThrows(NullPointerException.class, () -> Meeting.isValidMeeting(null));

        // blank meeting
        assertFalse(Meeting.isValidMeeting("")); // empty string
        assertFalse(Meeting.isValidMeeting(" ")); // spaces only

        // invalid length
        assertFalse(Meeting.isValidMeeting("1234")); // too short
        assertFalse(Meeting.isValidMeeting("12345678900")); // too long

        // invalid parts
        assertFalse(Meeting.isValidMeeting("12/34/567890")); // / in date
        assertFalse(Meeting.isValidMeeting("123456 7890")); // space between date and time
        assertFalse(Meeting.isValidMeeting("12345678:90")); // : in time
        assertFalse(Meeting.isValidMeeting(" 1234567890")); // leading space
        assertFalse(Meeting.isValidMeeting("1234567890 ")); // trailing space
        assertFalse(Meeting.isValidMeeting("12-34-567890")); // hyphens in date
        assertFalse(Meeting.isValidMeeting("1234567890.")); // time ends with a period

        // valid meeting
        assertTrue(Meeting.isValidMeeting("1234567890"));
        assertTrue(Meeting.isValidMeeting("1111111111")); // minimal
    }
}