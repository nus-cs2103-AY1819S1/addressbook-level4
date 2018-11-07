package seedu.meeting.model.meeting;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.meeting.testutil.TypicalGroups.GROUP_2101;
import static seedu.meeting.testutil.TypicalGroups.PROJECT_2103T;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.meeting.model.group.Group;
import seedu.meeting.model.meeting.exceptions.MeetingNotFoundException;
import seedu.meeting.testutil.GroupBuilder;

/**
 * {@author jeffreyooi}
 */
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
        uniqueMeetingList.add(PROJECT_2103T.getMeeting());
        assertTrue(uniqueMeetingList.contains(PROJECT_2103T.getMeeting()));
    }

    @Test
    public void contains_meetingAfterEditGroup_returnsTrue() {
        uniqueMeetingList.add(PROJECT_2103T.getMeeting());
        Group editedGroup = new GroupBuilder(PROJECT_2103T).withTitle("CS2103").build();
        assertTrue(uniqueMeetingList.contains(editedGroup.getMeeting()));
    }

    @Test
    public void cancel_meetingNotInList_returnsFalse() {
        uniqueMeetingList.add(PROJECT_2103T.getMeeting());
        uniqueMeetingList.remove(PROJECT_2103T.getMeeting());
        assertFalse(uniqueMeetingList.contains(PROJECT_2103T.getMeeting()));
    }

    @Test
    public void cancel_meetingInList_returnsTrue() {
        uniqueMeetingList.add(GROUP_2101.getMeeting());
        uniqueMeetingList.remove(GROUP_2101.getMeeting());
        uniqueMeetingList.add(PROJECT_2103T.getMeeting());
        assertTrue(uniqueMeetingList.contains(PROJECT_2103T.getMeeting()));
    }

    @Test
    public void add_nullMeeting_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMeetingList.add(null);
    }

    @Test
    public void setMeeting_nullTargetMeeting_returnsTrue() {
        uniqueMeetingList.setMeeting(null, PROJECT_2103T.getMeeting());
        assertTrue(uniqueMeetingList.contains(PROJECT_2103T.getMeeting()));
    }

    @Test
    public void setMeeting_nullEditedMeeting_returnsFalse() {
        uniqueMeetingList.setMeeting(PROJECT_2103T.getMeeting(), null);
        assertFalse(uniqueMeetingList.contains(PROJECT_2103T.getMeeting()));
    }

    @Test
    public void setMeeting_editedMeetingIsSameMeeting_success() {
        uniqueMeetingList.add(PROJECT_2103T.getMeeting());
        uniqueMeetingList.setMeeting(PROJECT_2103T.getMeeting(), PROJECT_2103T.getMeeting());
        UniqueMeetingList expectedUniqueMeetingList = new UniqueMeetingList();
        expectedUniqueMeetingList.add(PROJECT_2103T.getMeeting());
        assertEquals(expectedUniqueMeetingList, uniqueMeetingList);
    }

    @Test
    public void setMeeting_groupEditedButMeetingUnchanged_success() {
        uniqueMeetingList.add(PROJECT_2103T.getMeeting());
        Group editedGroup = new GroupBuilder(PROJECT_2103T).withTitle("CS2101").build();
        uniqueMeetingList.setMeeting(PROJECT_2103T.getMeeting(), editedGroup.getMeeting());
        UniqueMeetingList expectedUniqueMeetingList = new UniqueMeetingList();
        expectedUniqueMeetingList.add(editedGroup.getMeeting());
        assertEquals(expectedUniqueMeetingList, uniqueMeetingList);
    }

    @Test
    public void setMeeting_editedMeetingHasDifferentIdentity_success() {
        uniqueMeetingList.add(PROJECT_2103T.getMeeting());
        uniqueMeetingList.setMeeting(PROJECT_2103T.getMeeting(), GROUP_2101.getMeeting());
        UniqueMeetingList expectedUniqueMeetingList = new UniqueMeetingList();
        expectedUniqueMeetingList.add(GROUP_2101.getMeeting());
        assertEquals(expectedUniqueMeetingList, uniqueMeetingList);
    }

    @Test
    public void remove_nullMeeting_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueMeetingList.remove(null);
    }

    @Test
    public void remove_meetingDoesNotExist_throwsMeetingNotFoundException() {
        thrown.expect(MeetingNotFoundException.class);
        uniqueMeetingList.remove(PROJECT_2103T.getMeeting());
    }

    @Test
    public void remove_existingMeeting_success() {
        uniqueMeetingList.add(GROUP_2101.getMeeting());
        uniqueMeetingList.remove(GROUP_2101.getMeeting());
        UniqueMeetingList expectedUniqueMeetingList = new UniqueMeetingList();
        assertEquals(expectedUniqueMeetingList, uniqueMeetingList);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueMeetingList.asUnmodifiableObservableList().remove(0);
    }
}
