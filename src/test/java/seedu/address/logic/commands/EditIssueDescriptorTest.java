package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_C;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SOLUTION_C;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATEMENT_C;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_UI;

import org.junit.Ignore;
import org.junit.Test;

import seedu.address.logic.commands.EditCommand.EditIssueDescriptor;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class EditIssueDescriptorTest {

    @Test
    @Ignore
    public void equals() {
        // same values -> returns true
        EditIssueDescriptor descriptorWithSameValues = new EditCommand.EditIssueDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditIssueDescriptor editedAmy = new EditPersonDescriptorBuilder(DESC_AMY)
                .withStatement(VALID_STATEMENT_C).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different description -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withDescription(VALID_DESCRIPTION_C).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different solutions -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withSolutions(VALID_SOLUTION_C).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_UI).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
