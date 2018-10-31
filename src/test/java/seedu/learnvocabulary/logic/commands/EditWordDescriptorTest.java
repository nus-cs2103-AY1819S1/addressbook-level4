package seedu.learnvocabulary.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.DESC_FLY;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.DESC_LEVITATE;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_NAME_LEVITATE;

import org.junit.Test;

import seedu.learnvocabulary.logic.commands.EditCommand.EditWordDescriptor;
import seedu.learnvocabulary.testutil.EditWordDescriptorBuilder;

public class EditWordDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditWordDescriptor descriptorWithSameValues = new EditWordDescriptor(DESC_FLY);
        assertTrue(DESC_FLY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_FLY.equals(DESC_FLY));

        // null -> returns false
        assertFalse(DESC_FLY.equals(null));

        // different types -> returns false
        assertFalse(DESC_FLY.equals(5));

        // different values -> returns false
        assertFalse(DESC_FLY.equals(DESC_LEVITATE));

        // different name -> returns false
        EditCommand.EditWordDescriptor editedFly = new EditWordDescriptorBuilder(DESC_FLY)
                .withName(VALID_NAME_LEVITATE).build();
        assertFalse(DESC_FLY.equals(editedFly));
    }
}
