package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.Patient;
import seedu.address.model.medicine.Medicine;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Patient> getPersonList();

    /**
     * Returns an unmodifiable view of the medicines list.
     * This list will not contain any duplicate medicines.
     */
    ObservableList<Medicine> getMedicineList();
}
