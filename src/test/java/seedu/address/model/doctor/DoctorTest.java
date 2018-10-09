package seedu.address.model.doctor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.VALID_HASH_PASSWORD_BEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;

import static seedu.address.testutil.TypicalPersons.ADAM;
import static seedu.address.testutil.TypicalPersons.BEN;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.testutil.DoctorBuilder;

public class DoctorTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameDoctor() {
        // same object -> returns true
        assertTrue(ADAM.isSameDoctor(ADAM));

        // null -> returns false
        assertFalse(ADAM.isSameDoctor(null));

        // different password and id -> returns false
        Doctor editedAdam = new DoctorBuilder(ADAM).withId(VALID_ID_BEN)
                .withPassword(VALID_HASH_PASSWORD_BEN, true).build();
        assertFalse(ADAM.isSameDoctor(editedAdam));

        // different id -> returns false
        editedAdam = new DoctorBuilder(ADAM).withId(VALID_ID_BEN).build();
        assertFalse(ADAM.isSameDoctor(editedAdam));

        // same id, same password, different attributes -> returns true
        editedAdam = new DoctorBuilder(ADAM).withName(VALID_NAME_BEN).build();
        assertTrue(ADAM.isSameDoctor(editedAdam));

        // same id, same name, different attributes -> returns true
        editedAdam = new DoctorBuilder(ADAM)
                .withPassword(VALID_HASH_PASSWORD_BEN, true).build();
        assertTrue(ADAM.isSameDoctor(editedAdam));

    }

    @Test
    public void equals() {
        // same values -> returns true
        Doctor adamCopy = new DoctorBuilder(ADAM).build();
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
        Doctor editedAdam = new DoctorBuilder(ADAM).withName(VALID_NAME_BOB).build();
        assertFalse(ADAM.equals(editedAdam));

        // different id -> returns false
        editedAdam = new DoctorBuilder(ADAM).withId(VALID_ID_BEN).build();
        assertFalse(ADAM.equals(editedAdam));

        // different password -> returns false
        editedAdam = new DoctorBuilder(ADAM).withPassword(VALID_HASH_PASSWORD_BEN, true).build();
        assertFalse(ADAM.equals(editedAdam));

    }
}
