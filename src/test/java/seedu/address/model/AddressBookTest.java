package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_JULIETT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HOME;
import static seedu.address.testutil.TypicalCarparks.ALFA;
import static seedu.address.testutil.TypicalCarparks.JULIETT;
import static seedu.address.testutil.TypicalCarparks.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.carpark.Carpark;
import seedu.address.model.carpark.exceptions.DuplicateCarparkException;
import seedu.address.testutil.CarparkBuilder;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getCarparkList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two carparks with the same identity fields
        Carpark editedJuliett = new CarparkBuilder(JULIETT).withAddress(VALID_ADDRESS_JULIETT).withTags(VALID_TAG_HOME)
                .build();
        List<Carpark> newPersons = Arrays.asList(JULIETT, editedJuliett);
        AddressBookStub newData = new AddressBookStub(newPersons);

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
     * A stub ReadOnlyAddressBook whose carparks list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Carpark> carparks = FXCollections.observableArrayList();

        AddressBookStub(Collection<Carpark> carparks) {
            this.carparks.setAll(carparks);
        }

        @Override
        public ObservableList<Carpark> getCarparkList() {
            return carparks;
        }
    }

}
