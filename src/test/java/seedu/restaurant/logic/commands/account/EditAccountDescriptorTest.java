package seedu.restaurant.logic.commands.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.restaurant.logic.commands.CommandTestUtil.DESC_DEMO_ONE;
import static seedu.restaurant.logic.commands.CommandTestUtil.DESC_DEMO_TWO;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_PASSWORD_DEMO_THREE;

import org.junit.Test;

import seedu.restaurant.logic.commands.account.ChangePasswordCommand.EditAccountDescriptor;
import seedu.restaurant.testutil.account.EditAccountDescriptorBuilder;

//@@author AZhiKai
public class EditAccountDescriptorTest {

    @Test
    public void editedFields() {
        EditAccountDescriptor descriptorWithNoEditedFields = new EditAccountDescriptor();
        assertFalse(descriptorWithNoEditedFields.isAnyFieldEdited());

        EditAccountDescriptor descriptorWithEditedFields = new EditAccountDescriptor(DESC_DEMO_ONE);
        assertTrue(descriptorWithEditedFields.isAnyFieldEdited());
    }


    @Test
    public void equals() {
        // same values -> returns true
        EditAccountDescriptor descriptorWithSameValues = new EditAccountDescriptor(DESC_DEMO_ONE);
        assertEquals(DESC_DEMO_ONE, descriptorWithSameValues);

        // same object -> returns true
        assertEquals(DESC_DEMO_ONE, DESC_DEMO_ONE);
        assertTrue(DESC_DEMO_ONE.equals(DESC_DEMO_ONE));

        // null -> returns false
        assertNotEquals(null, DESC_DEMO_ONE);

        // different types -> returns false
        assertNotEquals(5, DESC_DEMO_ONE);

        // different values -> returns false
        assertNotEquals(DESC_DEMO_ONE, DESC_DEMO_TWO);

        assertFalse(DESC_DEMO_ONE.equals(5));

        assertFalse(DESC_DEMO_ONE.equals(null));

        assertFalse(DESC_DEMO_ONE.equals(DESC_DEMO_TWO));

        // different password -> returns false
        EditAccountDescriptor editedDemoOne =
                new EditAccountDescriptorBuilder(DESC_DEMO_ONE).withPassword(VALID_PASSWORD_DEMO_THREE).build();
        assertNotEquals(DESC_DEMO_ONE, editedDemoOne);
    }
}
