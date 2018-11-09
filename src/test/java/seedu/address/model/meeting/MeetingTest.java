package seedu.address.model.meeting;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.Assert;

//@@author AyushChatto
public class MeetingTest {

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

        // invalid Dates
        assertFalse(Meeting.isValidMeeting("1234567890")); // not a real date
        assertFalse(Meeting.isValidMeeting("3102181230")); // not a real date
        assertFalse(Meeting.isValidMeeting("2902181230")); // not a valid date on a non-leap year

        // invalid Time
        assertFalse(Meeting.isValidMeeting("1202182500"));
        assertFalse(Meeting.isValidMeeting("1202181480"));

        // valid meeting
        assertTrue(Meeting.isValidMeeting("1212121212"));
        assertTrue(Meeting.isValidMeeting("12/12/121212")); // / in date
        assertTrue(Meeting.isValidMeeting("121212 1212")); // space between date and time
        assertTrue(Meeting.isValidMeeting("12121212:12")); // : in time
        assertTrue(Meeting.isValidMeeting(" 1212121212")); // leading space
        assertTrue(Meeting.isValidMeeting("1212121212 ")); // trailing space
        assertTrue(Meeting.isValidMeeting("12-12-121212")); // hyphens in date
        assertTrue(Meeting.isValidMeeting("1212121212.")); // time ends with a period
        assertTrue(Meeting.isValidMeeting("1111111111")); // minimal
        assertTrue(Meeting.isValidMeeting("29/02/16 1430")); // leap year
    }
}
