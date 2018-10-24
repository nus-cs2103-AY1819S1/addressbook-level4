package seedu.parking.model;

import javafx.collections.ObservableList;
import seedu.parking.model.carpark.Carpark;

/**
 * Unmodifiable view of an car park finder
 */
public interface ReadOnlyCarparkFinder {

    /**
     * Returns an unmodifiable view of the car parks list.
     * This list will not contain any duplicate car parks.
     */
    ObservableList<Carpark> getCarparkList();

}
