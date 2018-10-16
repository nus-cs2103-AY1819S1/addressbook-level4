package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_KILO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CARPARK_NUMBER_KILO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CARPARK_TYPE_KILO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COORDINATE_KILO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HOME;
import static seedu.address.testutil.TypicalCarparks.ALFA;
import static seedu.address.testutil.TypicalCarparks.BRAVO;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.carpark.Carpark;
import seedu.address.testutil.CarparkBuilder;

public class CarparkTest {
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
        assertTrue(ALFA.isSameCarpark(ALFA));

        // null -> returns false
        assertFalse(ALFA.isSameCarpark(null));

        // different phone and email -> returns false
        Carpark editedAlfa = new CarparkBuilder(ALFA)
                .withCarparkType(VALID_CARPARK_TYPE_KILO)
                .withCoordinate(VALID_COORDINATE_KILO).build();
        assertFalse(ALFA.isSameCarpark(editedAlfa));

        // different name -> returns false
        editedAlfa = new CarparkBuilder(ALFA).withCarparkNumber(VALID_CARPARK_NUMBER_KILO).build();
        assertFalse(ALFA.isSameCarpark(editedAlfa));

        // same name, same phone, different attributes -> returns true
        editedAlfa = new CarparkBuilder(ALFA).withCoordinate(VALID_COORDINATE_KILO).withAddress(VALID_ADDRESS_KILO)
                .withTags(VALID_TAG_HOME).build();
        assertTrue(ALFA.isSameCarpark(editedAlfa));

        // same name, same email, different attributes -> returns true
        editedAlfa = new CarparkBuilder(ALFA).withCarparkType(VALID_CARPARK_TYPE_KILO).withAddress(VALID_ADDRESS_KILO)
                .withTags(VALID_TAG_HOME).build();
        assertTrue(ALFA.isSameCarpark(editedAlfa));

        // same name, same phone, same email, different attributes -> returns true
        editedAlfa = new CarparkBuilder(ALFA).withAddress(VALID_ADDRESS_KILO).withTags(VALID_TAG_HOME).build();
        assertTrue(ALFA.isSameCarpark(editedAlfa));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Carpark AlfaCopy = new CarparkBuilder(ALFA).build();
        assertTrue(ALFA.equals(AlfaCopy));

        // same object -> returns true
        assertTrue(ALFA.equals(ALFA));

        // null -> returns false
        assertFalse(ALFA.equals(null));

        // different type -> returns false
        assertFalse(ALFA.equals(5));

        // different carpark -> returns false
        assertFalse(ALFA.equals(BRAVO));

        // different name -> returns false
        Carpark editedAlfa = new CarparkBuilder(ALFA).withCarparkNumber(VALID_CARPARK_NUMBER_KILO).build();
        assertFalse(ALFA.equals(editedAlfa));

        // different phone -> returns false
        editedAlfa = new CarparkBuilder(ALFA).withCarparkType(VALID_CARPARK_TYPE_KILO).build();
        assertFalse(ALFA.equals(editedAlfa));

        // different email -> returns false
        editedAlfa = new CarparkBuilder(ALFA).withCoordinate(VALID_COORDINATE_KILO).build();
        assertFalse(ALFA.equals(editedAlfa));

        // different address -> returns false
        editedAlfa = new CarparkBuilder(ALFA).withAddress(VALID_ADDRESS_KILO).build();
        assertFalse(ALFA.equals(editedAlfa));

        // different tags -> returns false
        editedAlfa = new CarparkBuilder(ALFA).withTags(VALID_TAG_HOME).build();
        assertFalse(ALFA.equals(editedAlfa));
    }
}
