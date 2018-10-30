package seedu.scheduler.model;

import java.util.function.Predicate;

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

    /**
     * Returns an immutable event according to the predicate.
     */
    Event getFirstInstanceOfEvent(Predicate<Event> predicate);

}
