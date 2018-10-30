package seedu.scheduler.model;

import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.scheduler.model.event.Event;
import seedu.scheduler.model.tag.Tag;
import seedu.scheduler.storage.Storage;

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
     * Deletes the given event and its repeats.
     * The event must exist in the scheduler.
     */
    void deleteRepeatingEvents(Event target);

    /**
     * Deletes the given event and its upcoming events.
     * The event must exist in the scheduler.
     */
    void deleteUpcomingEvents(Event target);

    /**
     * Adds all the given events.
     */
    void addEvents(List<Event> events);

    /**
     * Replaces the given event {@code target} with {@code editedEvent}.
     * {@code target} must exist in the scheduler.
     */
    void updateEvent(Event target, Event editedEvent);

    /**
     * Replaces the given event {@code target} and its repeat events with {@code editedEvents}.
     * {@code target} must exist in the scheduler.
     */
    void updateRepeatingEvents(Event target, List<Event> editedEvents);

    /**
     * Replaces the given event {@code target} and its upcoming events with {@code editedEvents}.
     * {@code target} must exist in the scheduler.
     */
    void updateUpcomingEvents(Event target, List<Event> editedEvents);

    /** Returns an unmodifiable view of the filtered event list */
    ObservableList<Event> getFilteredEventList();

    /**
     * Updates the filter of the filtered event list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEventList(Predicate<Event> predicate);

    /** Returns the first event out of all similar repeating events in the event list according to predicate*/
    Event getFirstInstanceOfEvent(Predicate<Event> predicate);

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

    /**
     * Sync the ReminderDurationList with popUpManager
     */
    void syncWithPopUpManager(PopUpManager popUpManager, Storage storage);

    /**
     * Returns true if a tag with the same identity as {@code tag} exists in the scheduler.
     */
    //boolean hasTag(Tag tag);

    /**
     * Adds all the given tags.
     */
    //void addTags(List<Tag> tags);

    /** Removes the given {@code tag} from all {@code Event}s. */
    void deleteTag(Tag tag);

    /**
     * Saves the current tag book state for undo/redo.
     */
    //void commitTagRecord();

}
