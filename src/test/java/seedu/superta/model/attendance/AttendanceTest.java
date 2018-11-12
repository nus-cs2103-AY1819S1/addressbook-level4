package seedu.superta.model.attendance;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_STUDENT_ID_AMY;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_STUDENT_ID_BOB;

import org.junit.Test;

import seedu.superta.model.student.StudentId;
import seedu.superta.testutil.Assert;

// @@author triger15
public class AttendanceTest {
    private final Attendance amyAtt = new Attendance(new StudentId(VALID_STUDENT_ID_AMY), Presence.PRESENT);
    private final Attendance bobAtt = new Attendance(new StudentId(VALID_STUDENT_ID_BOB), Presence.PRESENT);

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Attendance(null, null));
    }

    @Test
    public void isSameAttendance() {
        // same object -> returns true
        assertTrue(amyAtt.isSameAttendance(amyAtt));

        // null -> returns false
        assertFalse(amyAtt.isSameAttendance(null));

        // same name, different presence -> returns true
        assertTrue(amyAtt.isSameAttendance(new Attendance(new StudentId(VALID_STUDENT_ID_AMY), Presence.ABSENT)));

        // different name -> returns false
        assertFalse(amyAtt.isSameAttendance(bobAtt));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Attendance attendanceCopy = new Attendance(amyAtt.getStudentId(), amyAtt.getPresence());
        assertTrue(amyAtt.equals(attendanceCopy));

        // same object -> returns true
        assertTrue(amyAtt.equals(amyAtt));

        // null -> returns false
        assertFalse(amyAtt.equals(null));

        // different type -> returns false
        assertFalse(amyAtt.equals(5));

        // different name -> returns false
        assertFalse(amyAtt.equals(bobAtt));

        // different presence -> returns false
        assertFalse(amyAtt.equals(new Attendance(new StudentId(VALID_STUDENT_ID_AMY), Presence.ABSENT)));
    }
}
