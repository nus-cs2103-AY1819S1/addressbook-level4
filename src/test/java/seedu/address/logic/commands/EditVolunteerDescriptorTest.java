package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_VOLUNTEER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_VOLUNTEER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BIRTHDAY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_TAG_HUSBAND;

import org.junit.Test;

import seedu.address.logic.commands.EditVolunteerCommand.EditVolunteerDescriptor;
import seedu.address.testutil.EditVolunteerDescriptorBuilder;

public class EditVolunteerDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditVolunteerDescriptor descriptorWithSameValues = new EditVolunteerDescriptor(DESC_VOLUNTEER_AMY);
        assertTrue(DESC_VOLUNTEER_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_VOLUNTEER_AMY.equals(DESC_VOLUNTEER_AMY));

        // null -> returns false
        assertFalse(DESC_VOLUNTEER_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_VOLUNTEER_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_VOLUNTEER_AMY.equals(DESC_VOLUNTEER_BOB));

        // different name -> returns false
        EditVolunteerDescriptor editedAmy = new EditVolunteerDescriptorBuilder(DESC_VOLUNTEER_AMY).withName(VALID_VOLUNTEER_NAME_BOB).build();
        assertFalse(DESC_VOLUNTEER_AMY.equals(editedAmy));

        // different gender -> returns false
        editedAmy = new EditVolunteerDescriptorBuilder(DESC_VOLUNTEER_AMY).withGender(VALID_GENDER_BOB).build();
        assertFalse(DESC_VOLUNTEER_AMY.equals(editedAmy));

        // different birthday -> returns false
        editedAmy = new EditVolunteerDescriptorBuilder(DESC_VOLUNTEER_AMY).withBirthday(VALID_BIRTHDAY_BOB).build();
        assertFalse(DESC_VOLUNTEER_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditVolunteerDescriptorBuilder(DESC_VOLUNTEER_AMY).withPhone(VALID_VOLUNTEER_PHONE_BOB).build();
        assertFalse(DESC_VOLUNTEER_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditVolunteerDescriptorBuilder(DESC_VOLUNTEER_AMY).withEmail(VALID_VOLUNTEER_EMAIL_BOB).build();
        assertFalse(DESC_VOLUNTEER_AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditVolunteerDescriptorBuilder(DESC_VOLUNTEER_AMY).withAddress(VALID_VOLUNTEER_ADDRESS_BOB).build();
        assertFalse(DESC_VOLUNTEER_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditVolunteerDescriptorBuilder(DESC_VOLUNTEER_AMY).withTags(VALID_VOLUNTEER_TAG_HUSBAND).build();
        assertFalse(DESC_VOLUNTEER_AMY.equals(editedAmy));
    }
}
