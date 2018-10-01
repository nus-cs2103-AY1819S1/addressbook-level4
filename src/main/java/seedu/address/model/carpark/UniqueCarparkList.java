package seedu.address.model.carpark;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.carpark.exceptions.CarparkNotFoundException;
import seedu.address.model.carpark.exceptions.DuplicateCarparkException;

/**
 * A list of carparks that enforces uniqueness between its elements and does not allow nulls.
 * A carpark is considered unique by comparing using {@code Carpark#isSameCarpark(Carpark)}.
 * As such, adding and updating of carparks uses Carpark#isSameCarpark(Carpark) for equality so as to ensure
 * that the carpark being added or updated is unique in terms of identity in the UniqueCarparkList.
 * However, the removal of a carpark uses Carpark#equals(Object) so as to ensure that the carpark with
 * exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Carpark#isSameCarpark(Carpark)
 */
public class UniqueCarparkList implements Iterable<Carpark> {
    private final ObservableList<Carpark> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent carpark as the given argument.
     */
    public boolean contains(Carpark toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameCarpark);
    }

    /**
     * Adds a carpark to the list.
     * The carpark must not already exist in the list.
     */
    public void add(Carpark toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateCarparkException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the carpark {@code target} in the list with {@code editedCarpark}.
     * {@code target} must exist in the list.
     * The carpark identity of {@code editedCarpark} must not be the same as another existing carpark in the list.
     */
    public void setCarpark(Carpark target, Carpark editedCarpark) {
        requireAllNonNull(target, editedCarpark);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new CarparkNotFoundException();
        }

        if (!target.isSameCarpark(editedCarpark) && contains(editedCarpark)) {
            throw new DuplicateCarparkException();
        }

        internalList.set(index, editedCarpark);
    }

    /**
     * Removes the equivalent carpark from the list.
     * The carpark must exist in the list.
     */
    public void remove(Carpark toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new CarparkNotFoundException();
        }
    }

    public void setCarparks(UniqueCarparkList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code carparks}.
     * {@code carparks} must not contain duplicate carparks.
     */
    public void setCarparks(List<Carpark> carparks) {
        requireAllNonNull(carparks);
        if (!carparksAreUnique(carparks)) {
            throw new DuplicateCarparkException();
        }
        internalList.setAll(carparks);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Carpark> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Carpark> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueCarparkList // instanceof handles nulls
                && internalList.equals(((UniqueCarparkList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code carparks} contains only unique carparks.
     */
    private boolean carparksAreUnique(List<Carpark> carparks) {
        for (int i = 0; i < carparks.size() - 1; i++) {
            for (int j = i + 1; j < carparks.size(); j++) {
                if (carparks.get(i).isSameCarpark(carparks.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
