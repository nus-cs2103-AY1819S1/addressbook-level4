package seedu.souschef.logic.commands;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.souschef.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.souschef.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.souschef.logic.commands.CommandTestUtil.VALID_COOKTIME_HR;
import static seedu.souschef.logic.commands.CommandTestUtil.VALID_DIFFICULTY_1;
import static seedu.souschef.logic.commands.CommandTestUtil.VALID_NAME_BEE;
import static seedu.souschef.logic.commands.CommandTestUtil.VALID_TAG_STAPLE;

import org.junit.jupiter.api.Test;

import seedu.souschef.logic.EditRecipeDescriptor;
import seedu.souschef.testutil.EditRecipeDescriptorBuilder;

public class EditRecipeDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditRecipeDescriptor descriptorWithSameValues = new EditRecipeDescriptor(DESC_AMY);
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
        EditRecipeDescriptor editedAmy = new EditRecipeDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BEE).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditRecipeDescriptorBuilder(DESC_AMY).withDifficulty(VALID_DIFFICULTY_1).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditRecipeDescriptorBuilder(DESC_AMY).withCooktime(VALID_COOKTIME_HR).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditRecipeDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_STAPLE).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
