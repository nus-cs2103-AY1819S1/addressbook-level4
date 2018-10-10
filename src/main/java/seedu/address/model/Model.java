package seedu.address.model;

import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {

    //=========== Scheduler methods ======================================================================

    /** {@code Predicate} that always evaluate to true */
    Predicate<Event> PREDICATE_SHOW_ALL_EVENTS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyScheduler newData);

    /** Returns the Scheduler */
    ReadOnlyScheduler getScheduler();

    /**
     * Returns true if a event with the same identity as {@code event} exists in the scheduler.
     */
    boolean hasEvent(Event event);

    /**
     * Deletes the given event.
     * The event must exist in the scheduler.
     */
    void deleteEvent(Event target);

    /**
     * Adds all the given events.
     */
    void addEvents(List<Event> events);

    /**
     * Replaces the given event {@code target} with {@code editedEvent}.
     * {@code target} must exist in the scheduler.
     */
    void updateEvent(Event target, Event editedEvent);

    /** Removes the given {@code tag} from all {@code Event}s. */
    void deleteTag(Tag tag);

    /** Returns an unmodifiable view of the filtered event list */
    ObservableList<Event> getFilteredEventList();

    /**
     * Updates the filter of the filtered event list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEventList(Predicate<Event> predicate);

    /**
     * Returns true if the model has previous scheduler states to restore.
     */
    boolean canUndoScheduler();

    /**
     * Returns true if the model has undone scheduler states to restore.
     */
    boolean canRedoScheduler();

    /**
     * Restores the model's scheduler to its previous state.
     */
    void undoScheduler();

    /**
     * Restores the model's scheduler to its previously undone state.
     */
    void redoScheduler();

    /**
     * Saves the current scheduler state for undo/redo.
     */
    void commitScheduler();

}
