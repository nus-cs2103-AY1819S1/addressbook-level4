package seedu.address.model.volunteer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DRIVER;
import static seedu.address.testutil.TypicalVolunteers.ALICE;
import static seedu.address.testutil.TypicalVolunteers.BOB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.VolunteerBuilder;

public class VolunteerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Volunteer volunteer = new VolunteerBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        volunteer.getTags().remove(0);
    }

    @Test
    public void isSameVolunteer() {
        // same object -> returns true
        assertTrue(ALICE.isSameVolunteer(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameVolunteer(null));

        // different phone and email -> returns false
        Volunteer editedAlice = new VolunteerBuilder(ALICE).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSameVolunteer(editedAlice));

        // same id, different attributes -> returns true
        editedAlice = new VolunteerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_DRIVER).build();
        assertTrue(ALICE.isSameVolunteer(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Volunteer aliceCopy = new VolunteerBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different volunteer -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Volunteer editedAlice = new VolunteerBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new VolunteerBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new VolunteerBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new VolunteerBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new VolunteerBuilder(ALICE).withTags(VALID_TAG_DRIVER).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
