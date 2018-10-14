package seedu.scheduler.model;

import javafx.collections.ObservableList;
import seedu.scheduler.model.event.Event;

/**
 * Unmodifiable view of an scheduler
 */
public interface ReadOnlyScheduler {

    /**
     * Returns an unmodifiable view of the event list.
     */
    ObservableList<Event> getEventList();

}
