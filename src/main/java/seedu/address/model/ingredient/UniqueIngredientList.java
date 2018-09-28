package seedu.address.model.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.ingredient.exceptions.DuplicateUniqueIngredientException;
import seedu.address.model.ingredient.exceptions.UniqueIngredientNotFoundException;

/**
 * A list of unique ingredients that enforces uniqueness between its elements and does not allow nulls.
 * An unique ingredient is considered unique by comparing using {@code UniqueIngredient#equals(Object)}. As such, adding and updating of
 * unique ingredients uses UniqueIngredient#equals(Object) for equality so as to ensure that the unique ingredient being added or updated is
 * unique in terms of identity in the UniqueIngredientList. However, the removal of an unique ingredient uses UniqueIngredient#equals(Object) so
 * as to ensure that the unique ingredient with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see UniqueIngredient#equals(Object)
 */
public class UniqueIngredientList implements Iterable<UniqueIngredient> {
    private final ObservableList<UniqueIngredient> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent uniqueIngredient as the given argument.
     */
    public boolean contains(UniqueIngredient toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a uniqueIngredient to the list.
     * The uniqueIngredient must not already exist in the list.
     */
    public void add(UniqueIngredient toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateUniqueIngredientException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the uniqueIngredient {@code target} in the list with {@code editedUniqueIngredient}.
     * {@code target} must exist in the list.
     * The uniqueIngredient identity of {@code editedUniqueIngredient} must not be the same as another existing uniqueIngredient in the list.
     */
    public void setUniqueIngredient(UniqueIngredient target, UniqueIngredient editedUniqueIngredient) {
        requireAllNonNull(target, editedUniqueIngredient);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new UniqueIngredientNotFoundException();
        }

        if (!target.equals(editedUniqueIngredient) && contains(editedUniqueIngredient)) {
            throw new DuplicateUniqueIngredientException();
        }

        internalList.set(index, editedUniqueIngredient);
    }

    /**
     * Removes the equivalent uniqueIngredient from the list.
     * The uniqueIngredient must exist in the list.
     */
    public void remove(UniqueIngredient toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new UniqueIngredientNotFoundException();
        }
    }

    public void setUniqueIngredients(UniqueIngredientList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code uniqueIngredients}.
     * {@code uniqueIngredients} must not contain duplicate uniqueIngredients.
     */
    public void setUniqueIngredients(List<UniqueIngredient> uniqueIngredients) {
        requireAllNonNull(uniqueIngredients);
        if (!uniqueIngredientsAreUnique(uniqueIngredients)) {
            throw new DuplicateUniqueIngredientException();
        }

        internalList.setAll(uniqueIngredients);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<UniqueIngredient> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<UniqueIngredient> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueIngredientList // instanceof handles nulls
                && internalList.equals(((UniqueIngredientList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code uniqueIngredients} contains only unique uniqueIngredients.
     */
    private boolean uniqueIngredientsAreUnique(List<UniqueIngredient> uniqueIngredients) {
        for (int i = 0; i < uniqueIngredients.size() - 1; i++) {
            for (int j = i + 1; j < uniqueIngredients.size(); j++) {
                if (uniqueIngredients.get(i).equals(uniqueIngredients.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
