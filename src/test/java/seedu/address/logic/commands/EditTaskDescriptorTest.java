package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FREQUENCY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.Test;

import seedu.address.testutil.EditTaskDescriptorBuilder;

public class EditTaskDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditTaskDescriptor descriptorWithSameValues = new EditCommand.EditTaskDescriptor(DESC_AMY);
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
        EditCommand.EditTaskDescriptor editedAmy = new EditTaskDescriptorBuilder(DESC_AMY)
                .withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different priority -> returns false
        editedAmy = new EditTaskDescriptorBuilder(DESC_AMY).withPriority(VALID_PRIORITY_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different frequency -> returns false
        editedAmy = new EditTaskDescriptorBuilder(DESC_AMY).withFrequency(VALID_FREQUENCY_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditTaskDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
