package seedu.address.model.ride;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.ride.exceptions.DuplicatePersonException;
import seedu.address.model.ride.exceptions.PersonNotFoundException;

/**
 * A list of persons that enforces uniqueness between its elements and does not allow nulls.
 * A ride is considered unique by comparing using {@code Ride#isSamePerson(Ride)}. As such, adding and updating of
 * persons uses Ride#isSamePerson(Ride) for equality so as to ensure that the ride being added or updated is
 * unique in terms of identity in the UniquePersonList. However, the removal of a ride uses Ride#equals(Object) so
 * as to ensure that the ride with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Ride#isSamePerson(Ride)
 */
public class UniquePersonList implements Iterable<Ride> {

    private final ObservableList<Ride> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent ride as the given argument.
     */
    public boolean contains(Ride toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSamePerson);
    }

    /**
     * Adds a ride to the list.
     * The ride must not already exist in the list.
     */
    public void add(Ride toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePersonException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the ride {@code target} in the list with {@code editedRide}.
     * {@code target} must exist in the list.
     * The ride identity of {@code editedRide} must not be the same as another existing ride in the list.
     */
    public void setPerson(Ride target, Ride editedRide) {
        requireAllNonNull(target, editedRide);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PersonNotFoundException();
        }

        if (!target.isSamePerson(editedRide) && contains(editedRide)) {
            throw new DuplicatePersonException();
        }

        internalList.set(index, editedRide);
    }

    /**
     * Removes the equivalent ride from the list.
     * The ride must exist in the list.
     */
    public void remove(Ride toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PersonNotFoundException();
        }
    }

    public void setPersons(UniquePersonList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code rides}.
     * {@code rides} must not contain duplicate rides.
     */
    public void setPersons(List<Ride> rides) {
        requireAllNonNull(rides);
        if (!personsAreUnique(rides)) {
            throw new DuplicatePersonException();
        }

        internalList.setAll(rides);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Ride> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Ride> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniquePersonList // instanceof handles nulls
                        && internalList.equals(((UniquePersonList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code rides} contains only unique rides.
     */
    private boolean personsAreUnique(List<Ride> rides) {
        for (int i = 0; i < rides.size() - 1; i++) {
            for (int j = i + 1; j < rides.size(); j++) {
                if (rides.get(i).isSamePerson(rides.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
