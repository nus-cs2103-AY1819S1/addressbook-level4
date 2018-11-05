package seedu.address.model.project;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_FALCON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_FALCON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PROJECT_OASIS;
import static seedu.address.testutil.TypicalAssignment.FALCON;
import static seedu.address.testutil.TypicalAssignment.OASIS;

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
        assertTrue(OASIS.isSameAssignment(OASIS));

        // null -> returns false
        assertFalse(OASIS.isSameAssignment(null));

        // different author -> returns false
        Assignment editedAssignment = new AssignmentBuilder(OASIS).withAssignmentName(VALID_PROJECT_OASIS)
                .withAuthor(VALID_NAME_BOB).build();
        assertFalse(OASIS.isSameAssignment(editedAssignment));

        // different name -> returns false
        editedAssignment = new AssignmentBuilder(OASIS).withAssignmentName(VALID_PROJECT_FALCON).build();
        assertFalse(OASIS.isSameAssignment(editedAssignment));

        // same name, same author, different description -> returns false
        editedAssignment = new AssignmentBuilder(OASIS).withAssignmentName(VALID_PROJECT_OASIS)
                .withAuthor(VALID_NAME_BOB).withDescription(VALID_DESCRIPTION_FALCON).build();
        assertFalse(OASIS.isSameAssignment(editedAssignment));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Assignment oasisCopy = new AssignmentBuilder(OASIS).build();
        assertTrue(OASIS.equals(oasisCopy));

        // same object -> returns true
        assertTrue(OASIS.equals(OASIS));

        // null -> returns false
        assertFalse(OASIS.equals(null));

        // different type -> returns false
        assertFalse(OASIS.equals(5));

        // different assignment -> returns false
        assertFalse(OASIS.equals(FALCON));

        // different name -> returns false
        Assignment editedOasis = new AssignmentBuilder(OASIS).withAssignmentName(VALID_PROJECT_FALCON).build();
        assertFalse(OASIS.equals(editedOasis));

        // different author -> returns false
        editedOasis = new AssignmentBuilder(OASIS).withAuthor(VALID_NAME_BOB).build();
        assertFalse(OASIS.equals(editedOasis));

        // different description -> returns false
        editedOasis = new AssignmentBuilder(OASIS).withDescription(VALID_DESCRIPTION_FALCON).build();
        assertFalse(OASIS.equals(editedOasis));
    }
}
