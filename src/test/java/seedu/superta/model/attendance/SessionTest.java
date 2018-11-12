package seedu.superta.model.attendance;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_STUDENT_ID_AMY;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_STUDENT_ID_BOB;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import seedu.superta.model.student.StudentId;
import seedu.superta.testutil.Assert;

// @@author triger15
public class SessionTest {
    private final Session lab1 = new Session("Lab 1");
    private Session lab1Attended;
    private final Session tut1 = new Session("Tut 1");
    private Set<Attendance> attSet;
    private final Attendance attendance = new Attendance(new StudentId(VALID_STUDENT_ID_AMY), Presence.PRESENT);

    @Before
    public void setUp() {
        attSet = new HashSet<>();
        attSet.add(attendance);
        lab1Attended = new Session("Lab 1", attSet);
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Session(null, null));
    }

    @Test
    public void isSameAssignment() {
        // same object -> returns true
        assertTrue(lab1.isSameSession(lab1));

        // null -> returns false
        assertFalse(lab1.isSameSession(null));

        // same name, different attendance list -> returns true
        assertTrue(lab1.isSameSession(lab1Attended));

        // different session -> returns false
        assertFalse(lab1.isSameSession(tut1));
    }

    @Test
    public void addToSession_success() {
        Attendance bobAtt = new Attendance(new StudentId(VALID_STUDENT_ID_BOB), Presence.PRESENT);
        boolean result = tut1.addToSession(bobAtt);
        assertTrue(result);
    }

    @Test
    public void equals() {
        // same values -> returns true
        Session sessionCopy = new Session(lab1.getSessionName());
        assertTrue(lab1.equals(sessionCopy));

        // same object -> returns true
        assertTrue(lab1.equals(lab1));

        // null -> returns false
        assertFalse(lab1.equals(null));

        // different type -> returns false
        assertFalse(lab1.equals(5));

        // different name -> returns false
        assertFalse(lab1.equals(tut1));

        // different attendance list -> returns false
        assertFalse(lab1.equals(lab1Attended));
    }

    @Test
    public void contains() {
        assertTrue(lab1Attended.contains(attendance));
    }
}
