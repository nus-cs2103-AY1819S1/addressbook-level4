package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_JULIETT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_KILO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CARPARK_NUMBER_KILO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CARPARK_TYPE_JULIETT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CARPARK_TYPE_KILO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COORDINATE_JULIETT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COORDINATE_KILO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HOME;
import static seedu.address.testutil.TypicalCarparks.ALFA;
import static seedu.address.testutil.TypicalCarparks.BRAVO;
import static seedu.address.testutil.TypicalCarparks.JULIETT;

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
    public void isSameCarpark() {
        // same object -> returns true
        assertTrue(JULIETT.isSameCarpark(JULIETT));

        // null -> returns false
        assertFalse(JULIETT.isSameCarpark(null));

        // different phone and email -> returns false
        Carpark editedJuliett = new CarparkBuilder(JULIETT)
                .withCarparkType(VALID_CARPARK_TYPE_KILO)
                .withCoordinate(VALID_COORDINATE_KILO).build();
        assertFalse(JULIETT.isSameCarpark(editedJuliett));

        // different name -> returns false
        editedJuliett = new CarparkBuilder(JULIETT).withCarparkNumber(VALID_CARPARK_TYPE_JULIETT).build();
        assertFalse(JULIETT.isSameCarpark(editedJuliett));

        // same name, same phone, different attributes -> returns true
        editedJuliett = new CarparkBuilder(JULIETT).withCoordinate(VALID_COORDINATE_JULIETT)
                .withAddress(VALID_ADDRESS_JULIETT).withTags(VALID_TAG_HOME).build();
        assertTrue(JULIETT.isSameCarpark(editedJuliett));

        // same name, same email, different attributes -> returns true
        editedJuliett = new CarparkBuilder(JULIETT).withCarparkType(VALID_CARPARK_TYPE_KILO)
                .withAddress(VALID_ADDRESS_JULIETT).withTags(VALID_TAG_HOME).build();
        assertTrue(JULIETT.isSameCarpark(editedJuliett));

        // same name, same phone, same email, different attributes -> returns true
        editedJuliett = new CarparkBuilder(JULIETT).withAddress(VALID_ADDRESS_JULIETT).withTags(VALID_TAG_HOME).build();
        assertTrue(JULIETT.isSameCarpark(editedJuliett));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Carpark copyAlfa = new CarparkBuilder(ALFA).build();
        assertTrue(ALFA.equals(copyAlfa));

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
