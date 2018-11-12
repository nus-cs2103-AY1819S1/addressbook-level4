package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.SchedulerChangedEvent;
import seedu.address.model.calendarevent.CalendarEvent;

/**
 * Represents the in-memory model of the scheduler data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedScheduler versionedScheduler;
    private FilteredList<CalendarEvent> filteredCalendarEvents;
    private SortedList<CalendarEvent> sortedFilteredCalendarEvents;

    /**
     * Initializes a ModelManager with the given scheduler and userPrefs.
     */
    public ModelManager(ReadOnlyScheduler scheduler, UserPrefs userPrefs) {
        super();
        requireAllNonNull(scheduler, userPrefs);

        logger.fine("Initializing with scheduler: " + scheduler + " and user prefs " + userPrefs);

        versionedScheduler = new VersionedScheduler(scheduler);
        filteredCalendarEvents = new FilteredList<>(versionedScheduler.getCalendarEventList());
        sortedFilteredCalendarEvents = new SortedList<>(filteredCalendarEvents);
    }

    /**
     * Initializes a ModelManager with the given scheduler, userPrefs,
     * as well as filtering and sorting the list with the given predicate and comparator
     */
    public ModelManager(ReadOnlyScheduler scheduler, UserPrefs userPrefs,
                        Predicate<? super CalendarEvent> predicate, Comparator<? super CalendarEvent> comparator) {
        this(scheduler, userPrefs);
        filteredCalendarEvents.setPredicate(predicate);
        sortedFilteredCalendarEvents.setComparator(comparator);
    }

    public ModelManager() {
        this(new Scheduler(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyScheduler newData) {
        versionedScheduler.resetData(newData);
        indicateSchedulerChanged();
    }

    @Override
    public ReadOnlyScheduler getScheduler() {
        return versionedScheduler;
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateSchedulerChanged() {
        raise(new SchedulerChangedEvent(versionedScheduler));
    }

    @Override
    public boolean hasCalendarEvent(CalendarEvent calendarEvent) {
        requireNonNull(calendarEvent);
        return versionedScheduler.hasCalendarEvent(calendarEvent);
    }

    @Override
    public void deleteCalendarEvent(CalendarEvent target) {
        versionedScheduler.removeCalendarEvent(target);
        indicateSchedulerChanged();
    }

    @Override
    public void addCalendarEvent(CalendarEvent calendarEvent) {
        versionedScheduler.addCalendarEvent(calendarEvent);
        indicateSchedulerChanged();
    }

    @Override
    public void updateCalendarEvent(CalendarEvent target, CalendarEvent editedCalendarEvent) {
        requireAllNonNull(target, editedCalendarEvent);

        versionedScheduler.updateCalendarEvent(target, editedCalendarEvent);
        indicateSchedulerChanged();
    }

    //=========== Filtered and Sorted List Accessors ========================================================

    /**
     * Returns an unmodifiable view of the list of {@code CalendarEvent} backed by the internal list of
     * {@code versionedScheduler}
     */
    @Override
    public ObservableList<CalendarEvent> getFullCalendarEventList() {
        return FXCollections.unmodifiableObservableList(versionedScheduler.getCalendarEventList());
    }

    /**
     * Returns an unmodifiable view of the Filtered and {@code SortedList} of {@code CalendarEvent} backed by
     * the internal list of {@code versionedScheduler}
     */
    @Override
    public ObservableList<CalendarEvent> getFilteredAndSortedCalendarEventList() {
        return FXCollections.unmodifiableObservableList(sortedFilteredCalendarEvents);
    }

    @Override
    public void updateFilteredCalendarEventList(Predicate<CalendarEvent>... predicates) {
        requireNonNull(predicates);
        Predicate<CalendarEvent> combinedPredicate = Arrays.stream(predicates).reduce(Predicate::and).orElse(null);
        filteredCalendarEvents.setPredicate(combinedPredicate);
    }

    @Override
    public void sortFilteredCalendarEventList(Comparator<CalendarEvent> comparator) {
        requireNonNull(comparator);
        sortedFilteredCalendarEvents.setComparator(comparator);
    }

    @Override
    public void clearAllPredicatesAndComparators() {
        filteredCalendarEvents.setPredicate(null);
        sortedFilteredCalendarEvents.setComparator(null);
    }

    @Override
    public Predicate<? super CalendarEvent> getPredicate() {
        return filteredCalendarEvents.getPredicate();
    }

    @Override
    public Comparator<? super CalendarEvent> getComparator() {
        return sortedFilteredCalendarEvents.getComparator();
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoScheduler() {
        return versionedScheduler.canUndo();
    }

    @Override
    public boolean canRedoScheduler() {
        return versionedScheduler.canRedo();
    }

    @Override
    public void undoScheduler() {
        versionedScheduler.undo();
        indicateSchedulerChanged();
    }

    @Override
    public void redoScheduler() {
        versionedScheduler.redo();
        indicateSchedulerChanged();
    }

    @Override
    public void commitScheduler() {
        versionedScheduler.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedScheduler.equals(other.versionedScheduler)
                && filteredCalendarEvents.equals(other.filteredCalendarEvents)
                && sortedFilteredCalendarEvents.equals(other.sortedFilteredCalendarEvents);
    }

}
