package seedu.address.model.assignment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASSIGNMENTNAME_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAXMARK_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEIGHT_B;
import static seedu.address.testutil.TypicalAssignments.ASSIGNMENT_A;
import static seedu.address.testutil.TypicalAssignments.ASSIGNMENT_B;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.AssignmentBuilder;

public class AssignmentTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameAssignment() {
        // same object -> returns true
        assertTrue(ASSIGNMENT_A.isSameAssignment(ASSIGNMENT_A));

        // null -> returns false
        assertFalse(ASSIGNMENT_A.isSameAssignment(null));

        // different name -> returns false
        Assignment editedA = new AssignmentBuilder(ASSIGNMENT_A).withName(VALID_ASSIGNMENTNAME_B).build();
        assertFalse(ASSIGNMENT_A.isSameAssignment(editedA));

        // same name, different attributes -> returns true
        editedA = new AssignmentBuilder(ASSIGNMENT_A).withDeadline(VALID_DEADLINE_B).withWeight(VALID_WEIGHT_B)
                .withMaxMark(VALID_MAXMARK_B).build();
        assertTrue(ASSIGNMENT_A.isSameAssignment(editedA));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Assignment aCopy = new AssignmentBuilder(ASSIGNMENT_A).build();
        assertTrue(ASSIGNMENT_A.equals(aCopy));

        // same object -> returns true
        assertTrue(ASSIGNMENT_A.equals(ASSIGNMENT_A));

        // null -> returns false
        assertFalse(ASSIGNMENT_A.equals(null));

        // different type -> returns false
        assertFalse(ASSIGNMENT_A.equals(5));

        // different assignment -> returns false
        assertFalse(ASSIGNMENT_A.equals(ASSIGNMENT_B));

        // different name -> returns false
        Assignment editedA = new AssignmentBuilder(ASSIGNMENT_A).withName(VALID_ASSIGNMENTNAME_B).build();
        assertFalse(ASSIGNMENT_A.equals(editedA));

        // different deadline -> returns false
        editedA = new AssignmentBuilder(ASSIGNMENT_A).withDeadline(VALID_DEADLINE_B).build();
        assertFalse(ASSIGNMENT_A.equals(editedA));

        // different weight -> returns false
        editedA = new AssignmentBuilder(ASSIGNMENT_A).withWeight(VALID_WEIGHT_B).build();
        assertFalse(ASSIGNMENT_A.equals(editedA));

        // different max mark -> returns false
        editedA = new AssignmentBuilder(ASSIGNMENT_A).withMaxMark(VALID_MAXMARK_B).build();
        assertFalse(ASSIGNMENT_A.equals(editedA));
    }
}
