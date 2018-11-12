package seedu.superta.model.assignment;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_MAXMARKS_LAB;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_TITLE_LAB;

import static seedu.superta.testutil.TypicalAssignments.LAB;
import static seedu.superta.testutil.TypicalAssignments.TUT;
import static seedu.superta.testutil.TypicalSuperTaClient.ALICE;
import static seedu.superta.testutil.TypicalSuperTaClient.BENSON;
import static seedu.superta.testutil.TypicalSuperTaClient.CARL;

import org.junit.Test;

import seedu.superta.testutil.Assert;
import seedu.superta.testutil.AssignmentBuilder;

public class AssignmentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Assignment(null, null));
    }

    @Test
    public void isSameAssignment() {
        // same object -> returns true
        assertTrue(TUT.isSameAssignment(TUT));

        // null -> returns false
        assertFalse(TUT.isSameAssignment(null));

        // different title and max marks -> returns false
        Assignment editedTut = new AssignmentBuilder(TUT).withTitle(VALID_TITLE_LAB)
            .withMaxMarks(VALID_MAXMARKS_LAB).build();
        assertFalse(TUT.isSameAssignment(editedTut));

        // different title -> returns false
        editedTut = new AssignmentBuilder(TUT).withTitle(VALID_TITLE_LAB).build();
        assertFalse(TUT.isSameAssignment(editedTut));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Assignment tutCopy = new AssignmentBuilder(TUT).build();
        assertTrue(TUT.equals(tutCopy));

        // same object -> returns true
        assertTrue(TUT.equals(TUT));

        // null -> returns false
        assertFalse(TUT.equals(null));

        // different type -> returns false
        assertFalse(TUT.equals(5));

        // different assignment -> returns false
        assertFalse(TUT.equals(LAB));

        // different title -> returns false
        Assignment editedAlice = new AssignmentBuilder(TUT).withTitle(VALID_TITLE_LAB).build();
        assertFalse(TUT.equals(editedAlice));

        // different max marks -> returns false
        editedAlice = new AssignmentBuilder(TUT).withMaxMarks(VALID_MAXMARKS_LAB).build();
        assertFalse(TUT.equals(editedAlice));

    }

    @Test
    public void assignmentClone_success() {
        Assignment original = new Assignment(new Title("Lab 1"), 10.0);
        assertEquals(original, new Assignment(original));
    }

    @Test
    public void getAverage_success() {
        Assignment assignment = new Assignment(new Title("Lab 1"), 100.0);
        assertTrue(assignment.getAverage() == 0.0);
        assignment.grade(BENSON.getStudentId(), 10.0);
        assertTrue(assignment.getAverage() == 10.0);
        assignment.grade(ALICE.getStudentId(), 20.0);
        assertTrue(assignment.getAverage() == 15.0);
    }

    @Test
    public void getMedian_success() {
        Assignment assignment = new Assignment(new Title("Lab 1"), 100.0);
        assertTrue(assignment.getMedian() == 0);
        assignment.grade(BENSON.getStudentId(), 10.0);
        assertTrue(assignment.getMedian() == 10.0);
        assignment.grade(ALICE.getStudentId(), 20.0);
        assertTrue(assignment.getMedian() == 20.0);
        assignment.grade(CARL.getStudentId(), 99.0);
        assertTrue(assignment.getMedian() == 20.0);
    }

    @Test
    public void getProjectedDifficulty_success() {
        Assignment assignment = new Assignment(new Title("Lab 1"), 100.0);
        assertTrue(assignment.getProjectedDifficulty() == 0.0);
        assignment.grade(BENSON.getStudentId(), 50.0);
        assertTrue(assignment.getProjectedDifficulty() == 5.0);
        assignment.grade(ALICE.getStudentId(), 100.0);
        assertTrue(assignment.getProjectedDifficulty() == 2.5);
    }
}
