package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_JAVA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SOLUTION_C;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SOLUTION_STACKOVERLOW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATEMENT_C;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_UI;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.IssueBuilder;

public class IssueTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Issue issue = new IssueBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        issue.getTags().remove(0);
    }

    @Test
    public void isSameIssue() {
        // same object -> returns true
        assertTrue(ALICE.isSameIssue(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameIssue(null));

        // different description -> returns false
        Issue editedAlice = new IssueBuilder(ALICE).withDescription(VALID_DESCRIPTION_JAVA).build();
        assertFalse(ALICE.isSameIssue(editedAlice));

        // different statement -> returns false
        editedAlice = new IssueBuilder(ALICE).withStatement(VALID_STATEMENT_C).build();
        assertFalse(ALICE.isSameIssue(editedAlice));

        // same name, same description, different attributes -> returns true
        editedAlice = new IssueBuilder(ALICE).withSolutions(VALID_SOLUTION_STACKOVERLOW)
                .withTags(VALID_TAG_UI).build();
        assertTrue(ALICE.isSameIssue(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Issue aliceCopy = new IssueBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different issue -> returns false
        assertFalse(ALICE.equals(BOB));

        // different statement -> returns false
        Issue editedAlice = new IssueBuilder(ALICE).withStatement(VALID_STATEMENT_C).build();
        assertFalse(ALICE.equals(editedAlice));

        // different description -> returns false
        editedAlice = new IssueBuilder(ALICE).withDescription(VALID_DESCRIPTION_JAVA).build();
        assertFalse(ALICE.equals(editedAlice));

        // different solutions -> returns false
        editedAlice = new IssueBuilder(ALICE).withDescription(VALID_SOLUTION_C).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new IssueBuilder(ALICE).withTags(VALID_TAG_UI).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
