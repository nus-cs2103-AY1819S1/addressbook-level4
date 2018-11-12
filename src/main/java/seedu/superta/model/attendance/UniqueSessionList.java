package seedu.superta.model.attendance;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.superta.model.attendance.exceptions.DuplicateSessionException;
import seedu.superta.model.attendance.exceptions.SessionNotFoundException;

/**
 * A list of attendance sessions that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of list operations.
 */
public class UniqueSessionList implements Iterable<Session> {

    private final ObservableList<Session> internalList = FXCollections.observableArrayList();

    public UniqueSessionList(List<Session> source) {
        internalList.addAll(source);
    }

    public UniqueSessionList() { }

    /**
     * Returns true if the list contains an equivalent session as the given argument.
     */
    public boolean contains(Session toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameSession);
    }

    /**
     * Method to add a session to the list.
     * @param toAdd the session to add
     */
    public void add(Session toAdd) throws DuplicateSessionException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateSessionException();
        }
        internalList.add(toAdd);
    }

    /**
     * Method to remove a session from the list.
     * @param toRemove the session to remove
     */
    public void remove(Session toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new SessionNotFoundException();
        }
    }

    /**
     * Method to clone this instance.
     */
    public UniqueSessionList clone() {
        UniqueSessionList other = new UniqueSessionList();
        for (Session session : internalList) {
            other.internalList.add(new Session(session));
        }
        return other;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Session> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Session> iterator() {
        return internalList.iterator();
    }

    @Override
    public String toString() {
        return internalList.stream()
            .map(Session::toString)
            .collect(Collectors.joining("\n"));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UniqueSessionList)) {
            return false;
        }

        return internalList.equals(((UniqueSessionList) other).internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
