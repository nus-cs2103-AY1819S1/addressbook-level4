package seedu.scheduler.model;

import static java.util.Objects.requireNonNull;
import static seedu.scheduler.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.scheduler.commons.core.ComponentManager;
import seedu.scheduler.commons.core.LogsCenter;
import seedu.scheduler.commons.events.model.SchedulerChangedEvent;
import seedu.scheduler.model.event.Event;
import seedu.scheduler.model.tag.Tag;

/**
 * Represents the in-memory model of the scheduler data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedScheduler versionedScheduler;
    private final FilteredList<Event> filteredEvents;

    /**
     * Initializes a ModelManager with the given scheduler and userPrefs.
     */
    public ModelManager(ReadOnlyScheduler scheduler, UserPrefs userPrefs) {
        super();
        requireAllNonNull(scheduler, userPrefs);

        logger.fine("Initializing with scheduler: " + scheduler
                + " and user prefs " + userPrefs);

        versionedScheduler = new VersionedScheduler(scheduler);
        filteredEvents = new FilteredList<>(versionedScheduler.getEventList());
    }

    public ModelManager() {
        this(new Scheduler(), new UserPrefs());
    }

    //=========== Scheduler methods =======================================================================

    @Override
    public void resetData(ReadOnlyScheduler newData) {
        versionedScheduler.resetData(newData);
        indicateSchedulerChanged();
    }

    @Override
    public ReadOnlyScheduler getScheduler() {
        return versionedScheduler;
    }

    /** Raises an event to indicate the model has changed due to scheduler change */
    private void indicateSchedulerChanged() {
        raise(new SchedulerChangedEvent(versionedScheduler));
    }

    @Override
    public Event getFirstInstanceOfEvent(Predicate<Event> predicate) {
        return versionedScheduler.getFirstInstanceOfEvent(predicate);
    }

    @Override
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return versionedScheduler.hasEvent(event);
    }

    @Override
    public void deleteEvent(Event target) {
        versionedScheduler.removeEvent(target);
        indicateSchedulerChanged();
    }

    @Override
    public void deleteRepeatingEvents(Event target) {
        versionedScheduler.removeEvents(target, event -> event.getUuid().equals(target.getUuid()));
        indicateSchedulerChanged();
    }

    @Override
    public void deleteUpcomingEvents(Event target) {
        versionedScheduler.removeEvents(target, event ->
                event.getUuid().equals(target.getUuid())
                && event.getStartDateTime().compareTo(target.getStartDateTime()) > 0);
        indicateSchedulerChanged();
    }

    @Override
    public void addEvents(List<Event> events) {
        versionedScheduler.addEvents(events);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        indicateSchedulerChanged();
    }

    @Override
    public void updateEvent(Event target, Event editedEvent) {
        requireAllNonNull(target, editedEvent);

        versionedScheduler.updateEvent(target, editedEvent);
        indicateSchedulerChanged();
    }

    @Override
    public void updateRepeatingEvents(Event target, List<Event> editedEvents) {
        requireAllNonNull(target, editedEvents);

        versionedScheduler.updateEvents(target, editedEvents, event -> event.getUuid().equals(target.getUuid()));
        indicateSchedulerChanged();
    }

    @Override
    public void updateUpcomingEvents(Event target, List<Event> editedEvents) {
        requireAllNonNull(target, editedEvents);

        versionedScheduler.updateEvents(target, editedEvents, event ->
                event.getUuid().equals(target.getUuid())
                && event.getStartDateTime().compareTo(target.getStartDateTime()) >= 0);
        indicateSchedulerChanged();
    }

    @Override
    public void deleteTag(Tag tag) {
        versionedScheduler.removeTag(tag);
    }

    //=========== Filtered Event List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Event} backed by the internal list of
     * {@code versionedScheduler}
     */
    @Override
    public ObservableList<Event> getFilteredEventList() {
        return FXCollections.unmodifiableObservableList(filteredEvents);
    }

    @Override
    public void updateFilteredEventList(Predicate<Event> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
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
                && filteredEvents.equals(other.filteredEvents);
    }

}
