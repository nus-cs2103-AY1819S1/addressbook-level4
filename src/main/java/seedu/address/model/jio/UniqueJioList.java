package seedu.address.model.jio;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.restaurant.exceptions.DuplicateRestaurantException;
import seedu.address.model.restaurant.exceptions.RestaurantNotFoundException;

/**
 * A list of jios that enforces uniqueness between its elements and does not allow nulls.
 * Supports a minimal set of list operations.
 *
 */

public class UniqueJioList implements Iterable<Jio> {

    private final ObservableList<Jio> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent jio as the given argument.
     */
    public boolean contains(Jio toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a jio to the list.
     * The jio must not already exist in the list.
     */
    public void add(Jio toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            //toChange
            throw new DuplicateRestaurantException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the jio {@code target} in the list with {@code editedJio}.
     * {@code target} must exist in the list.
     * The jio identity of {@code editedJio}
     * must not be the same as another existing jio in the list.
     */
    public void setJio(Jio target, Jio editedJio) {
        requireAllNonNull(target, editedJio);

        int index = internalList.indexOf(target);
        if (index == -1) {
            //toChange
            throw new RestaurantNotFoundException();
        }

        if (!target.equals(editedJio) && contains(editedJio)) {
            throw new DuplicateRestaurantException();
        }

        internalList.set(index, editedJio);
    }

    /**
     * Removes the equivalent jio from the list.
     * The jio must exist in the list.
     */
    public void remove(Jio toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new RestaurantNotFoundException(); //toChange
        }
    }

    public void setJios(UniqueJioList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code jio}.
     * {@code jios} must not contain duplicate jios.
     */
    public void setJios(List<Jio> jios) {
        requireAllNonNull(jios);
        if (!jiosAreUnique(jios)) {
            throw new DuplicateRestaurantException();
        }

        internalList.setAll(jios);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Jio> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    //@Override
    public Iterator<Jio> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueJioList // instanceof handles nulls
                && internalList.equals(((UniqueJioList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code rjios} contains only unique jios.
     */
    private boolean jiosAreUnique(List<Jio> jios) {
        for (int i = 0; i < jios.size() - 1; i++) {
            for (int j = i + 1; j < jios.size(); j++) {
                if (jios.get(i).equals(jios.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
