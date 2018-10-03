package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COORDINATE_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CARPARK_NUMBER_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CARPARK_TYPE_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalCarparks.ALICE;
import static seedu.address.testutil.TypicalCarparks.BOB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.carpark.Carpark;
import seedu.address.testutil.CarparkBuilder;

public class PersonTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Carpark carpark = new CarparkBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        carpark.getTags().remove(0);
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSameCarpark(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameCarpark(null));

        // different phone and email -> returns false
        Carpark editedAlice = new CarparkBuilder(ALICE).withPhone(VALID_CARPARK_TYPE_2).withEmail(VALID_COORDINATE_2).build();
        assertFalse(ALICE.isSameCarpark(editedAlice));

        // different name -> returns false
        editedAlice = new CarparkBuilder(ALICE).withName(VALID_CARPARK_NUMBER_2).build();
        assertFalse(ALICE.isSameCarpark(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new CarparkBuilder(ALICE).withEmail(VALID_COORDINATE_2).withAddress(VALID_ADDRESS_2)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameCarpark(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new CarparkBuilder(ALICE).withPhone(VALID_CARPARK_TYPE_2).withAddress(VALID_ADDRESS_2)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameCarpark(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new CarparkBuilder(ALICE).withAddress(VALID_ADDRESS_2).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameCarpark(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new CarparkBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different carpark -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new CarparkBuilder(ALICE).withName(VALID_CARPARK_NUMBER_2).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new CarparkBuilder(ALICE).withPhone(VALID_CARPARK_TYPE_2).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new CarparkBuilder(ALICE).withEmail(VALID_COORDINATE_2).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new CarparkBuilder(ALICE).withAddress(VALID_ADDRESS_2).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new CarparkBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
