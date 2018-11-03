package seedu.scheduler.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.scheduler.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.scheduler.model.event.exceptions.EventNotFoundException;
import seedu.scheduler.model.event.exceptions.EventOverflowException;

/**
 * A list of events that does not allow nulls.
 *
 * Supports a minimal set of list operations.
 */
public class EventList implements Iterable<Event> {

    private final ObservableList<Event> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains the same event as the given argument.
     */
    public boolean contains(Event toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds an event to the list.
     */
    public void add(Event toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Adds a list of events to the list.
     */
    public void addEvents(List<Event> listToAdd) {
        requireNonNull(listToAdd);
        if (listToAdd.size() > 100) {
            throw new EventOverflowException();
        }
        internalList.addAll(listToAdd);
    }

    /**
     * Replaces the event {@code target} in the list with {@code editedEvent}.
     * {@code target} must exist in the list.
     * The event identity of {@code editedEvent} must not be the same as another existing event in the list.
     */
    public void setEvent(Event target, Event editedEvent) {
        requireAllNonNull(target, editedEvent);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new EventNotFoundException();
        }

        internalList.set(index, editedEvent);
    }

    /**
     * Removes the equivalent event from the list.
     * The event must exist in the list.
     */
    public void remove(Event toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new EventNotFoundException();
        }
    }

    /**
     * Removes the events from the list according to predicate.
     * The event must exist in the list.
     */
    public void remove(Event toRemove, Predicate<Event> predicate) {
        requireAllNonNull(toRemove, predicate);
        if (!internalList.remove(toRemove)) {
            throw new EventNotFoundException();
        }
        internalList.removeIf(predicate);
    }

    /**
     * Replaces the events from the filtered list after applying {@code predicate} with editedEvents
     * {@code target} must exist in the list.
     */
    public void setEvents(Event target, List<Event> editedEvents, Predicate<Event> predicate) {
        requireAllNonNull(target, editedEvents, predicate);

        int i = 0;
        List<Event> filteredList = internalList.stream().filter(predicate).collect(Collectors.toList());
        if (filteredList.isEmpty()) {
            throw new EventNotFoundException();
        }
        while (i < filteredList.size() && i < editedEvents.size()) {
            internalList.set(internalList.indexOf(filteredList.get(i)), editedEvents.get(i++));
        }
        internalList.addAll(editedEvents.subList(i, editedEvents.size()));
        while (i < filteredList.size()) {
            internalList.remove(filteredList.get(i++));
        }
    }

    public void setEvents(EventList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code events}.
     */
    public void setEvents(List<Event> events) {
        requireAllNonNull(events);
        internalList.setAll(events);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Event> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Event> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventList // instanceof handles nulls
                && internalList.equals(((EventList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
