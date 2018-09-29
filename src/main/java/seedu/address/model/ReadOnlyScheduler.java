package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.event.Event;

/**
 * Unmodifiable view of an scheduler
 */
public interface ReadOnlyScheduler {

    /**
     * Returns an unmodifiable view of the event list.
     */
    ObservableList<Event> getEventList();

}
