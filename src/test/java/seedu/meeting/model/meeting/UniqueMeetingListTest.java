package seedu.meeting.model.meeting;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.meeting.testutil.TypicalGroups.GROUP_2101;
import static seedu.meeting.testutil.TypicalGroups.PROJECT_2103T;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.meeting.model.group.Group;
import seedu.meeting.testutil.GroupBuilder;

// @@author jeffreyooi
public class UniqueMeetingListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueMeetingList uniqueMeetingList = new UniqueMeetingList();

    @Test
    public void contains_nullMeeting_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMeetingList.contains(null);
    }

    @Test
    public void contains_meetingNotInList_returnsFalse() {
        assertFalse(uniqueMeetingList.contains(PROJECT_2103T.getMeeting()));
    }

    @Test
    public void contains_meetingInList_returnsTrue() {
        List<Meeting> meetings = new ArrayList<>();
        meetings.add(PROJECT_2103T.getMeeting());
        uniqueMeetingList.setMeetings(meetings);
        assertTrue(uniqueMeetingList.contains(PROJECT_2103T.getMeeting()));
    }

    @Test
    public void contains_meetingAfterEditGroup_returnsTrue() {
        List<Meeting> meetings = new ArrayList<>();
        meetings.add(PROJECT_2103T.getMeeting());
        uniqueMeetingList.setMeetings(meetings);
        Group editedGroup = new GroupBuilder(PROJECT_2103T).withTitle("CS2103").build();
        assertTrue(uniqueMeetingList.contains(editedGroup.getMeeting()));
    }

    @Test
    public void cancel_meetingNotInList_returnsFalse() {
        List<Meeting> meetings = new ArrayList<>();
        meetings.add(PROJECT_2103T.getMeeting());
        uniqueMeetingList.setMeetings(meetings);
        meetings.clear();
        uniqueMeetingList.setMeetings(meetings);
        assertFalse(uniqueMeetingList.contains(PROJECT_2103T.getMeeting()));
    }

    @Test
    public void cancel_meetingInList_returnsTrue() {
        List<Meeting> meetings = new ArrayList<>();
        meetings.add(GROUP_2101.getMeeting());
        uniqueMeetingList.setMeetings(meetings);
        meetings.clear();
        uniqueMeetingList.setMeetings(meetings);
        meetings.add(PROJECT_2103T.getMeeting());
        uniqueMeetingList.setMeetings(meetings);
        assertTrue(uniqueMeetingList.contains(PROJECT_2103T.getMeeting()));
    }

    @Test
    public void add_nullMeetingList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMeetingList.setMeetings(null);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueMeetingList.asUnmodifiableObservableList().remove(0);
    }
}
