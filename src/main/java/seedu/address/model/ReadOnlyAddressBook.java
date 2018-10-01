package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.carpark.Carpark;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the carparks list.
     * This list will not contain any duplicate carparks.
     */
    ObservableList<Carpark> getCarparkList();

}
