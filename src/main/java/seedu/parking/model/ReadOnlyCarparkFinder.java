package seedu.parking.model;

import javafx.collections.ObservableList;
import seedu.parking.model.carpark.Carpark;

/**
 * Unmodifiable view of an car park finder
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the carparks list.
     * This list will not contain any duplicate carparks.
     */
    ObservableList<Carpark> getCarparkList();

}
