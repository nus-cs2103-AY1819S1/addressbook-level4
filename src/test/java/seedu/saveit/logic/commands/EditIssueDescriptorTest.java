package seedu.saveit.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.saveit.logic.commands.CommandTestUtil.DESC_C;
import static seedu.saveit.logic.commands.CommandTestUtil.DESC_JAVA;
import static seedu.saveit.logic.commands.CommandTestUtil.VALID_DESCRIPTION_C;
import static seedu.saveit.logic.commands.CommandTestUtil.VALID_STATEMENT_C;
import static seedu.saveit.logic.commands.CommandTestUtil.VALID_TAG_UI;
import static seedu.saveit.testutil.TypicalSolutions.SOLUTION_C;

import org.junit.Test;

import seedu.saveit.logic.commands.EditCommand.EditIssueDescriptor;
import seedu.saveit.testutil.EditIssueDescriptorBuilder;

public class EditIssueDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditIssueDescriptor descriptorWithSameValues = new EditCommand.EditIssueDescriptor(DESC_JAVA);
        assertTrue(DESC_JAVA.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_JAVA.equals(DESC_JAVA));

        // null -> returns false
        assertFalse(DESC_JAVA.equals(null));

        // different types -> returns false
        assertFalse(DESC_JAVA.equals(5));

        // different values -> returns false
        assertFalse(DESC_JAVA.equals(DESC_C));

        // different name -> returns false
        EditIssueDescriptor editedJava = new EditIssueDescriptorBuilder(DESC_JAVA)
                .withStatement(VALID_STATEMENT_C).build();
        assertFalse(DESC_JAVA.equals(editedJava));

        // different description -> returns false
        editedJava = new EditIssueDescriptorBuilder(DESC_JAVA).withDescription(VALID_DESCRIPTION_C).build();
        assertFalse(DESC_JAVA.equals(editedJava));

        // different solutions -> returns false
        editedJava = new EditIssueDescriptorBuilder(DESC_JAVA).withSolutions(SOLUTION_C).build();
        assertFalse(DESC_JAVA.equals(editedJava));

        // different tags -> returns false
        editedJava = new EditIssueDescriptorBuilder(DESC_JAVA).withTags(VALID_TAG_UI).build();
        assertFalse(DESC_JAVA.equals(editedJava));

    }
}
