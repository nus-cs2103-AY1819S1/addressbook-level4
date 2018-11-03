package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandPersonTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandPersonTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandPersonTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandPersonTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandPersonTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandPersonTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandPersonTestUtil.VALID_TAG_HUSBAND;

import org.junit.Test;

import seedu.address.model.person.PersonDescriptor;
import seedu.address.testutil.PersonDescriptorBuilder;

public class PersonDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        PersonDescriptor descriptorWithSameValues = new PersonDescriptor(DESC_AMY);
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
        PersonDescriptor editedAmy = new PersonDescriptorBuilder(DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new PersonDescriptorBuilder(DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new PersonDescriptorBuilder(DESC_AMY).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new PersonDescriptorBuilder(DESC_AMY).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new PersonDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
