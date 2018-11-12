package seedu.scheduler.model;

import static java.util.Objects.requireNonNull;
import static seedu.scheduler.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.scheduler.model.event.Event;
import seedu.scheduler.model.event.EventList;
import seedu.scheduler.model.tag.Tag;

/**
 * Wraps all data at the scheduler level
 * Duplicates are allowed
 */
public class Scheduler implements ReadOnlyScheduler {

    private final EventList events;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        events = new EventList();
    }

    public Scheduler() {}

    /**
     * Creates an Scheduler using the Events in the {@code toBeCopied}
     */
    public Scheduler(ReadOnlyScheduler toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the event list with {@code events}.
     */
    public void setEvents(List<Event> events) {
        this.events.setEvents(events);
    }

    /**
     * Resets the existing data of this {@code Scheduler} with {@code newData}.
     */
    public void resetData(ReadOnlyScheduler newData) {
        requireNonNull(newData);
        setEvents(newData.getEventList());
    }

    //// event-level operations

    /**
     * Returns true if a event with the same data as {@code event} exists in the scheduler.
     */
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return events.contains(event);
    }

    /**
     * Adds an event to the scheduler.
     */
    public void addEvent(Event event) {
        events.add(event);
    }

    /**
     * Adds a list of event to the scheduler.
     */
    public void addEvents(List<Event> events) {
        this.events.addEvents(events);
    }

    /**
     * Replaces the given event {@code target} in the list with {@code editedEvent}.
     * {@code target} must exist in the scheduler.
     */
    public void updateEvent(Event targetEvent, Event editedEvent) {
        requireNonNull(editedEvent);
        events.setEvent(targetEvent, editedEvent);
    }

    /**
     * Replaces the given event {@code target} in the list with {@code editedEvent}.
     * {@code target} must exist in the scheduler.
     */
    public void updateEvents(Event targetEvent, List<Event> editedEvents, Predicate<Event> predicate) {
        requireAllNonNull(editedEvents);

        events.setEvents(targetEvent, editedEvents, predicate);
    }

    /**
     * Removes {@code key} from this {@code Scheduler}.
     * {@code key} must exist in the scheduler.
     */
    public void removeEvent(Event key) {
        events.remove(key);
    }

    /**
     * Removes {@code key} and its repeating events from this {@code Scheduler}.
     * {@code key} must exist in the scheduler.
     */
    public void removeEvents(Event key, Predicate<Event> predicate) {
        events.remove(key, predicate);
    }

    //// util methods
    /**
     * Removes {@code tag} from {@code event} in this {@code Scheduler}.
     */
    private void removeTagFromEvent(Tag tag, Event event) {
        Set<Tag> newTags = new HashSet<>(event.getTags());
        if (!newTags.remove(tag)) {
            return;
        }
        Event newEvent =
                new Event(event.getEventUid(), event.getEventSetUid(), event.getEventName(),
                        event.getStartDateTime(), event.getEndDateTime(),
                        event.getDescription(), event.getVenue(),
                        event.getRepeatType(), event.getRepeatUntilDateTime(), newTags,
                        event.getReminderDurationList());
        updateEvent(event, newEvent);
    }
    /**
     * Removes {@code tag} from all events in this {@code Scheduler}.
     */
    public void removeTag(Tag tag) {
        events.forEach(event -> removeTagFromEvent(tag, event));
    }

    @Override
    public String toString() {
        return events.asUnmodifiableObservableList().size() + " events";
        // TODO: refine later
    }

    @Override
    public ObservableList<Event> getEventList() {
        return events.asUnmodifiableObservableList();
    }

    @Override
    public Event getFirstInstanceOfEvent(Predicate<Event> predicate) {
        return events.asUnmodifiableObservableList().stream()
                .filter(predicate).findFirst().orElse(null);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Scheduler // instanceof handles nulls
                && events.equals(((Scheduler) other).events));
    }

    @Override
    public int hashCode() {
        return events.hashCode();
    }
}
