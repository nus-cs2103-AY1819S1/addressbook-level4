package ssp.scheduleplanner.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static ssp.scheduleplanner.logic.commands.CommandTestUtil.DESC_AMY;
import static ssp.scheduleplanner.logic.commands.CommandTestUtil.DESC_BOB;
import static ssp.scheduleplanner.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static ssp.scheduleplanner.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static ssp.scheduleplanner.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static ssp.scheduleplanner.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static ssp.scheduleplanner.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.Test;

import ssp.scheduleplanner.logic.commands.EditCommand.EditTaskDescriptor;
import ssp.scheduleplanner.testutil.EditTaskDescriptorBuilder;

public class EditTaskDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditTaskDescriptor descriptorWithSameValues = new EditTaskDescriptor(DESC_AMY);
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
        EditCommand.EditTaskDescriptor editedAmy =
                new EditTaskDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different date -> returns false
        editedAmy = new EditTaskDescriptorBuilder(DESC_AMY).withDate(VALID_DATE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditTaskDescriptorBuilder(DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditTaskDescriptorBuilder(DESC_AMY).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditTaskDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
