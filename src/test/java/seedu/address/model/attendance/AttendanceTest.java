package seedu.address.model.attendance;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SESSION_B;
import static seedu.address.testutil.TypicalAttendance.ATTENDANCE_A;
import static seedu.address.testutil.TypicalAttendance.ATTENDANCE_B;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.AttendanceBuilder;

public class AttendanceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameAttendance() {
        // same object -> returns true
        assertTrue(ATTENDANCE_A.isSameAttendance(ATTENDANCE_A));

        // null -> returns false
        assertFalse(ATTENDANCE_A.isSameAttendance(null));

        // different name -> returns false
        Attendance editedA = new AttendanceBuilder(ATTENDANCE_A).withName(VALID_SESSION_B).build();
        assertFalse(ATTENDANCE_A.isSameAttendance(editedA));

        // same name, different attributes -> returns true
        editedA = new AttendanceBuilder(ATTENDANCE_A).withDate(VALID_DATE_B).withDate(VALID_DATE_B).build();
        assertTrue(ATTENDANCE_A.isSameAttendance(editedA));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Attendance aCopy = new AttendanceBuilder(ATTENDANCE_A).build();
        assertTrue(ATTENDANCE_A.equals(aCopy));

        // same object -> returns true
        assertTrue(ATTENDANCE_A.equals(ATTENDANCE_A));

        // null -> returns false
        assertFalse(ATTENDANCE_A.equals(null));

        // different type -> returns false
        assertFalse(ATTENDANCE_A.equals(34));

        // different assignment -> returns false
        assertFalse(ATTENDANCE_A.equals(ATTENDANCE_B));

        // different name -> returns false
        Attendance editedA = new AttendanceBuilder(ATTENDANCE_A).withName(VALID_SESSION_B).build();
        assertFalse(ATTENDANCE_A.equals(editedA));
    }
}
