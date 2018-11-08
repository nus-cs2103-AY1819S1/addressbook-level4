package seedu.parking.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_ADDRESS_JULIETT;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_TAG_HOME;
import static seedu.parking.testutil.TypicalCarparks.ALFA;
import static seedu.parking.testutil.TypicalCarparks.JULIETT;
import static seedu.parking.testutil.TypicalCarparks.getTypicalCarparkFinder;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.parking.model.carpark.Carpark;
import seedu.parking.model.carpark.exceptions.DuplicateCarparkException;
import seedu.parking.testutil.CarparkBuilder;

public class CarparkFinderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final CarparkFinder carparkFinder = new CarparkFinder();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), carparkFinder.getCarparkList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        carparkFinder.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyCarparkFinder_replacesData() {
        CarparkFinder newData = getTypicalCarparkFinder();
        carparkFinder.resetData(newData);
        assertEquals(newData, carparkFinder);
    }

    @Test
    public void resetData_withDuplicateCarparks_throwsDuplicateCarparkException() {
        // Two car parks with the same identity fields
        Carpark editedJuliett = new CarparkBuilder(JULIETT).withAddress(VALID_ADDRESS_JULIETT).withTags(VALID_TAG_HOME)
                .build();
        List<Carpark> newCarparks = Arrays.asList(JULIETT, editedJuliett);
        CarparkFinderStub newData = new CarparkFinderStub(newCarparks);

        thrown.expect(DuplicateCarparkException.class);
        carparkFinder.resetData(newData);
    }

    @Test
    public void hasCarpark_nullCarpark_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        carparkFinder.hasCarpark(null);
    }

    @Test
    public void hasCarpark_carparkNotInCarparkFinder_returnsFalse() {
        assertFalse(carparkFinder.hasCarpark(ALFA));
    }

    @Test
    public void hasCarpark_carparkInCarparkFinder_returnsTrue() {
        carparkFinder.addCarpark(ALFA);
        assertTrue(carparkFinder.hasCarpark(ALFA));
    }

    @Test
    public void hasCarpark_carparkWithSameIdentityFieldsInCarparkFinder_returnsTrue() {
        carparkFinder.addCarpark(JULIETT);
        Carpark editedJuliett = new CarparkBuilder(JULIETT).withAddress(VALID_ADDRESS_JULIETT).withTags(VALID_TAG_HOME)
                .build();
        assertTrue(carparkFinder.hasCarpark(editedJuliett));
    }

    @Test
    public void getCarparkList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        carparkFinder.getCarparkList().remove(0);
    }

    /**
     * A stub ReadOnlyCarparkFinder whose car parks list can violate interface constraints.
     */
    private static class CarparkFinderStub implements ReadOnlyCarparkFinder {
        private final ObservableList<Carpark> carparks = FXCollections.observableArrayList();

        CarparkFinderStub(Collection<Carpark> carparks) {
            this.carparks.setAll(carparks);
        }

        @Override
        public ObservableList<Carpark> getCarparkList() {
            return carparks;
        }
    }

}
