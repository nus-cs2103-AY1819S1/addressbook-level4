package seedu.parking.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_ADDRESS_JULIETT;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_TAG_HOME;
import static seedu.parking.testutil.TypicalCarparks.ALFA;
import static seedu.parking.testutil.TypicalCarparks.JULIETT;
import static seedu.parking.testutil.TypicalCarparks.getTypicalAddressBook;

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

public class AddressBookTest {

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
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        CarparkFinder newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two carparks with the same identity fields
        Carpark editedJuliett = new CarparkBuilder(JULIETT).withAddress(VALID_ADDRESS_JULIETT).withTags(VALID_TAG_HOME)
                .build();
        List<Carpark> newPersons = Arrays.asList(JULIETT, editedJuliett);
        CarparkFinderStub newData = new CarparkFinderStub(newPersons);

        thrown.expect(DuplicateCarparkException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasCarpark(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasCarpark(ALFA));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addCarpark(ALFA);
        assertTrue(addressBook.hasCarpark(ALFA));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addCarpark(JULIETT);
        Carpark editedJuliett = new CarparkBuilder(JULIETT).withAddress(VALID_ADDRESS_JULIETT).withTags(VALID_TAG_HOME)
                .build();
        assertTrue(addressBook.hasCarpark(editedJuliett));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getCarparkList().remove(0);
    }

    /**
     * A stub ReadOnlyCarparkFinder whose carparks list can violate interface constraints.
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
