package seedu.address.model.receptionist;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

public class ReceptionistTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameReceptionist() {
        // same object -> returns true
        assertTrue(ADAM.isSameDoctor(ADAM));

        // null -> returns false
        assertFalse(ADAM.isSameDoctor(null));

        // different password and name -> returns false
        Receptionist editedAdam = new ReceptionistBuilder(ADAM).withName(VALID_NAME_BEN)
                .withPassword(VALID_HASH_PASSWORD_BEN, true).build();
        assertFalse(ADAM.isSameDoctor(editedAdam));

        // same password, different attributes -> returns true
        editedAdam = new ReceptionistBuilder(ADAM).withName(VALID_NAME_BEN).build();
        assertTrue(ADAM.isSameDoctor(editedAdam));

        // same name, different attributes -> returns true
        editedAdam = new ReceptionistBuilder(ADAM)
                .withPassword(VALID_HASH_PASSWORD_BEN, true).build();
        assertTrue(ADAM.isSameDoctor(editedAdam));

    }

    @Test
    public void equals() {
        // same values -> returns true
        Receptionist adamCopy = new ReceptionistBuilder(ADAM).build();
        assertTrue(ADAM.equals(adamCopy));

        // same object -> returns true
        assertTrue(ADAM.equals(ADAM));

        // null -> returns false
        assertFalse(ADAM.equals(null));

        // different type -> returns false
        assertFalse(ADAM.equals(5));

        // different person -> returns false
        assertFalse(ADAM.equals(BEN));

        // different name -> returns false
        Receptionist editedAdam = new ReceptionistBuilder(ADAM).withName(VALID_NAME_BOB).build();
        assertFalse(ADAM.equals(editedAdam));

        // different password -> returns false
        editedAdam = new ReceptionistBuilder(ADAM).withPassword(VALID_HASH_PASSWORD_BEN, true).build();
        assertFalse(ADAM.equals(editedAdam));

    }
}
