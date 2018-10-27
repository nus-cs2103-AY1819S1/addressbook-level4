package seedu.clinicio.model.receptionist;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_HASH_PASSWORD_ALAN;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_HASH_PASSWORD_FRANK;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NAME_ALAN;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_NAME_FRANK;

import static seedu.clinicio.testutil.TypicalPersons.ALAN;
import static seedu.clinicio.testutil.TypicalPersons.FRANK;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import seedu.clinicio.testutil.ReceptionistBuilder;

public class ReceptionistTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameReceptionist() {
        // same object -> returns true
        assertTrue(ALAN.isSameReceptionist(ALAN));

        // null -> returns false
        assertFalse(ALAN.isSameReceptionist(null));

        // different password and name -> returns false
        Receptionist editedAlan = new ReceptionistBuilder(ALAN).withName(VALID_NAME_FRANK)
                .withPassword(VALID_HASH_PASSWORD_FRANK, true).build();
        assertFalse(ALAN.isSameReceptionist(editedAlan));

        // same password, different attributes -> returns true
        editedAlan = new ReceptionistBuilder(ALAN).withName(VALID_NAME_ALAN).build();
        assertTrue(ALAN.isSameReceptionist(editedAlan));

        // same name, different attributes -> returns true
        editedAlan = new ReceptionistBuilder(ALAN)
                .withPassword(VALID_HASH_PASSWORD_ALAN, true).build();
        assertTrue(ALAN.isSameReceptionist(editedAlan));

    }

    @Test
    public void equals() {
        // same values -> returns true
        Receptionist alanCopy = new ReceptionistBuilder(ALAN).build();
        assertTrue(ALAN.equals(alanCopy));

        // same object -> returns true
        assertTrue(ALAN.equals(ALAN));

        // null -> returns false
        assertFalse(ALAN.equals(null));

        // different type -> returns false
        assertFalse(ALAN.equals(5));

        // different person -> returns false
        assertFalse(ALAN.equals(FRANK));

        // different name -> returns false
        Receptionist editedAlan = new ReceptionistBuilder(ALAN).withName(VALID_NAME_FRANK).build();
        assertFalse(ALAN.equals(editedAlan));

        // different password -> returns false
        editedAlan = new ReceptionistBuilder(ALAN).withPassword(VALID_HASH_PASSWORD_FRANK, true).build();
        assertFalse(ALAN.equals(editedAlan));

    }
}
