package seedu.parking.model.carpark;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_ADDRESS_KILO;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_CARPARK_NUMBER_KILO;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_CARPARK_TYPE_KILO;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_COORDINATE_KILO;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_FREE_PARKING_KILO;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_LOTS_AVAILABLE_KILO;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_NIGHT_PARKING_KILO;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_SHORT_TERM_KILO;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_TAG_HOME;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_TOTAL_LOTS_KILO;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_TYPE_OF_PARKING_KILO;
import static seedu.parking.testutil.TypicalCarparks.ALFA;
import static seedu.parking.testutil.TypicalCarparks.BRAVO;
import static seedu.parking.testutil.TypicalCarparks.JULIETT;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.parking.testutil.CarparkBuilder;

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

        // different car park type and coordinate -> returns false
        Carpark editedJuliett = new CarparkBuilder(JULIETT)
                .withAddress(VALID_ADDRESS_KILO)
                .withCarparkNumber(VALID_CARPARK_NUMBER_KILO)
                .withCoordinate(VALID_COORDINATE_KILO)
                .withLotsAvailable(VALID_LOTS_AVAILABLE_KILO)
                .withTotalLots(VALID_TOTAL_LOTS_KILO).build();
        assertFalse(JULIETT.isSameCarpark(editedJuliett));

        // different car park number -> returns false
        editedJuliett = new CarparkBuilder(JULIETT).withCarparkNumber(VALID_CARPARK_NUMBER_KILO).build();
        assertFalse(JULIETT.isSameCarpark(editedJuliett));

        // different free parking, different night parking, different tag -> returns true
        editedJuliett = new CarparkBuilder(JULIETT).withFreeParking(VALID_FREE_PARKING_KILO)
                .withNightParking(VALID_NIGHT_PARKING_KILO).withTags(VALID_TAG_HOME).build();
        assertTrue(JULIETT.isSameCarpark(editedJuliett));

        // different name, same email, different attributes -> returns true
        editedJuliett = new CarparkBuilder(JULIETT).withCarparkType(VALID_LOTS_AVAILABLE_KILO)
                .withShortTerm(VALID_SHORT_TERM_KILO).withTags(VALID_TAG_HOME).build();
        assertTrue(JULIETT.isSameCarpark(editedJuliett));

        // same name, same phone, same email, different attributes -> returns true
        editedJuliett = new CarparkBuilder(JULIETT).withTypeOfParking(VALID_TYPE_OF_PARKING_KILO)
                .withTags(VALID_TAG_HOME).build();
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

        // different parking -> returns false
        editedAlfa = new CarparkBuilder(ALFA).withAddress(VALID_ADDRESS_KILO).build();
        assertFalse(ALFA.equals(editedAlfa));

        // different tags -> returns false
        editedAlfa = new CarparkBuilder(ALFA).withTags(VALID_TAG_HOME).build();
        assertFalse(ALFA.equals(editedAlfa));
    }
}
