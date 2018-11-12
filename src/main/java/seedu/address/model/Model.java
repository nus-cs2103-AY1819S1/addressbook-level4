package seedu.address.model;

import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.calendarevent.CalendarEvent;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyScheduler newData);

    /**
     * Returns the Scheduler
     */
    ReadOnlyScheduler getScheduler();

    /**
     * Returns true if a calendar event with the same identity as {@code calendarevent} exists in the scheduler.
     */
    boolean hasCalendarEvent(CalendarEvent calendarEvent);

    /**
     * Deletes the given calendar event.
     * {@code target} must exist in the scheduler.
     */
    void deleteCalendarEvent(CalendarEvent target);

    /**
     * Adds the given calendar event.
     * {@code calendarEvent} must not already exist in the scheduler.
     */
    void addCalendarEvent(CalendarEvent calendarEvent);

    /**
     * Replaces the given calendar event {@code target} with {@code editedCalendarEvent}.
     * {@code target} must exist in the scheduler.
     * The calendar event identity of {@code editedCalendarEvent} must not be the same as another existing calendar
     * event in the scheduler.
     */
    void updateCalendarEvent(CalendarEvent target, CalendarEvent editedCalendarEvent);

    /**
     * Returns an unmodifiable view of the full calendar event list
     */
    ObservableList<CalendarEvent> getFullCalendarEventList();

    /**
     * Returns an unmodifiable view of the filtered and sorted of calendar events
     */
    ObservableList<CalendarEvent> getFilteredAndSortedCalendarEventList();

    /**
     * Updates the filter of the filtered calendar event list to filter by all of the given {@code predicates}.
     * Note: if multiple predicates are passed, it will filter by the logical AND of all the predicates
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredCalendarEventList(Predicate<CalendarEvent>... predicates);

    /**
     * Sorts the filtered calendar event list by the given {@code comparator}.
     *
     * @throws NullPointerException if {@code comparator} is null.
     */
    void sortFilteredCalendarEventList(Comparator<CalendarEvent> comparator);

    /**
     * Removes all predicates from the {@code FilteredList} and {@code SortedList} of calendar events.
     */
    void clearAllPredicatesAndComparators();

    /**
     * Returns the predicate currently used to filter the {@code FilteredList} of calendar events.
     */
    Predicate<? super CalendarEvent> getPredicate();

    /**
     * Returns the comparator currently used to sort the {@code SortedList} of calendar events.
     */
    Comparator<? super CalendarEvent> getComparator();

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
