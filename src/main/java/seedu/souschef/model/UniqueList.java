package seedu.souschef.model;

import static java.util.Objects.requireNonNull;
import static seedu.souschef.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.souschef.model.exceptions.DuplicateException;
import seedu.souschef.model.exceptions.NotFoundException;

/**
 * A list of elements that enforces uniqueness between its elements and does not allow nulls.
 *  * A element is considered unique by comparing using {@code UniqueType#isSame(UniqueType)}. As such, adding and
 *  updating of
 *  * Unique list uses UniqueType#isSame(UniqueType) for equality so as to ensure that the recipe being added or
 *  updated is
 *  * unique in terms of identity in the UniqueRecipeList. However, the removal of a recipe uses UniqueType#equals
 *  (Object) so
 *  * as to ensure that the recipe with exactly the same fields will be removed.
 * @param <T> Element of unique type
 */
public class UniqueList<T extends UniqueType> implements Iterable<T> {

    private final ObservableList<T> internalList =
            FXCollections.observableArrayList();

    /**
     * Check for existence of similar element exist in list
     * @param toCheck element exist in the list
     * @return True if the element is found in list (weak equality), or else false
     */
    public boolean contains(T toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSame);
    }

    /**
     * Add unique element into a list.
     * Element must not exist in the list.
     * @param toAdd Element to be added
     */
    public void add(T toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            // throw new duplicate T exception
            throw new DuplicateException(this.getClass().getSimpleName());
        }
        internalList.add(toAdd);
    }

    public void set(T target, T edited) {
        requireAllNonNull(target, edited);

        int index = internalList.indexOf(target);
        if (index == -1) {
            // throw new T not found exception
            throw new NotFoundException(this.getClass().getSimpleName());
        }

        if (!target.isSame(edited) && contains(edited)) {
            // throw new duplicate T exception
            throw new DuplicateException(this.getClass().getSimpleName());
        }

        internalList.set(index, edited);
    }

    public void set(UniqueList<T> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    public void set(List<T> list) {
        requireNonNull(list);
        if (!areUnique(list)) {
            // throw new duplicate T exception
            throw new DuplicateException(this.getClass().getSimpleName());
        }
        internalList.setAll(list);
    }

    /**
     * Removes the equivalent element from the list.
     * The element must exist in the list.
     */
    public void remove(T toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            // throw new T not found exception
            throw new NotFoundException(this.getClass().getSimpleName());
        }
    }

    public ObservableList<T> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<T> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof UniqueList
                && internalList.equals(((UniqueList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code UniqueType} contains only unique recipes.
     */
    private boolean areUnique(List<T> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i).isSame(list.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
