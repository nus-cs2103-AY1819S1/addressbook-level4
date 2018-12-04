package seedu.thanepark.model;

import javafx.collections.ObservableList;
import seedu.thanepark.model.ride.Ride;

/**
 * Unmodifiable view of an thanepark book
 */
public interface ReadOnlyThanePark {

    /**
     * Returns an unmodifiable view of the rides list.
     * This list will not contain any duplicate rides.
     */
    ObservableList<Ride> getRideList();

}
