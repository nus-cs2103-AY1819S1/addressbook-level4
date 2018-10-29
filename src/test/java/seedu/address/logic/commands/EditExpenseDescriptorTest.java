package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.DESC_GAME;
import static seedu.address.logic.commands.CommandTestUtil.DESC_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CATEGORY_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COST_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_IPHONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.Test;

import seedu.address.model.expense.EditExpenseDescriptor;
import seedu.address.testutil.EditExpenseDescriptorBuilder;

public class EditExpenseDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditExpenseDescriptor descriptorWithSameValues = new EditExpenseDescriptor(DESC_GAME);
        assertTrue(DESC_GAME.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_GAME.equals(DESC_GAME));

        // null -> returns false
        assertFalse(DESC_GAME.equals(null));

        // different types -> returns false
        assertFalse(DESC_GAME.equals(5));

        // different values -> returns false
        assertFalse(DESC_GAME.equals(DESC_IPHONE));

        // different name -> returns false
        EditExpenseDescriptor editedAmy =
                new EditExpenseDescriptorBuilder(DESC_GAME).withName(VALID_NAME_IPHONE).build();
        assertFalse(DESC_GAME.equals(editedAmy));

        // different category -> returns false
        editedAmy = new EditExpenseDescriptorBuilder(DESC_GAME).withCategory(VALID_CATEGORY_IPHONE).build();
        assertFalse(DESC_GAME.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditExpenseDescriptorBuilder(DESC_GAME).withCost(VALID_COST_IPHONE).build();
        assertFalse(DESC_GAME.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditExpenseDescriptorBuilder(DESC_GAME).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_GAME.equals(editedAmy));
    }
}
