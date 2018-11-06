package seedu.meeting.ui;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.meeting.ui.testutil.GuiTestAssert.assertCardDisplaysMeeting;

import java.time.Month;
import java.time.Year;

import org.junit.Test;

import guitests.guihandles.MeetingCardHandle;
import seedu.meeting.model.meeting.Meeting;
import seedu.meeting.model.meeting.TimeStamp;
import seedu.meeting.model.shared.Address;
import seedu.meeting.model.shared.Description;
import seedu.meeting.model.shared.Title;

// @@author jeffreyooi
public class MeetingCardTest extends GuiUnitTest {
    @Test
    public void display() {
        Meeting meeting = new Meeting(new Title("Title"),
            new TimeStamp(Year.now(), Month.OCTOBER, 23, 11, 9),
            new Address("Address"),
            new Description("Description"));
        MeetingCard meetingCard = new MeetingCard(meeting, 1);
        uiPartRule.setUiPart(meetingCard);
        assertCardDisplay(meetingCard, meeting, 1);
    }

    @Test
    public void equals() {
        Meeting meeting = new Meeting(new Title("Title"),
            new TimeStamp(Year.now(), Month.OCTOBER, 23, 11, 9),
            new Address("Address"),
            new Description("Description"));
        MeetingCard meetingCard = new MeetingCard(meeting, 0);

        // same meeting, same index -> returns true
        MeetingCard copy = new MeetingCard(meeting, 0);
        assertTrue(meetingCard.equals(copy));

        // same object -> returns true
        assertTrue(meetingCard.equals(meetingCard));

        // null -> returns false
        assertFalse(meetingCard.equals(null));

        // different types -> return false
        assertFalse(meetingCard.equals(0));

        // different meeting, same index -> return false
        Meeting differentMeeting = new Meeting(new Title("eltiT"),
            new TimeStamp(Year.now(), Month.OCTOBER, 23, 11, 9),
            new Address("Address"),
            new Description("Description"));
        assertFalse(meetingCard.equals(new MeetingCard(differentMeeting, 0)));

        // same meeting, different index -> returns false
        assertFalse(meetingCard.equals(new MeetingCard(meeting, 1)));
    }

    /**
     * Asserts that {@code groupCard} displays the details of {@code expectedGroup} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(MeetingCard meetingCard, Meeting expectedMeeting, int expectedId) {
        guiRobot.pauseForHuman();

        MeetingCardHandle meetingCardHandle = new MeetingCardHandle(meetingCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", meetingCardHandle.getId());

        // verify meeting details are displayed correctly
        assertCardDisplaysMeeting(expectedMeeting, meetingCardHandle);
    }
}
