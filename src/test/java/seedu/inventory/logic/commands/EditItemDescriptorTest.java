package seedu.inventory.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.logic.commands.CommandTestUtil.DESC_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.DESC_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_IMAGE_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_NAME_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_QUANTITY_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_SKU_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_TAG_SMARTPHONE;

import org.junit.Test;

import seedu.inventory.testutil.EditItemDescriptorBuilder;

public class EditItemDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditItemDescriptor descriptorWithSameValues = new EditCommand.EditItemDescriptor(DESC_OPPO);
        assertTrue(DESC_OPPO.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_OPPO.equals(DESC_OPPO));

        // null -> returns false
        assertFalse(DESC_OPPO.equals(null));

        // different types -> returns false
        assertFalse(DESC_OPPO.equals(5));

        // different values -> returns false
        assertFalse(DESC_OPPO.equals(DESC_SONY));

        // different name -> returns false
        EditCommand.EditItemDescriptor editedOppo =
                new EditItemDescriptorBuilder(DESC_OPPO).withName(VALID_NAME_SONY).build();
        assertFalse(DESC_OPPO.equals(editedOppo));

        // different quantity -> returns false
        editedOppo = new EditItemDescriptorBuilder(DESC_OPPO).withQuantity(VALID_QUANTITY_SONY).build();
        assertFalse(DESC_OPPO.equals(editedOppo));

        // different SKU -> returns false
        editedOppo = new EditItemDescriptorBuilder(DESC_OPPO).withSku(VALID_SKU_SONY).build();
        assertFalse(DESC_OPPO.equals(editedOppo));

        // different image -> returns false
        editedOppo = new EditItemDescriptorBuilder(DESC_OPPO).withImage(VALID_IMAGE_SONY).build();
        assertFalse(DESC_OPPO.equals(editedOppo));

        // different tags -> returns false
        editedOppo = new EditItemDescriptorBuilder(DESC_OPPO).withTags(VALID_TAG_SMARTPHONE).build();
        assertFalse(DESC_OPPO.equals(editedOppo));
    }
}
