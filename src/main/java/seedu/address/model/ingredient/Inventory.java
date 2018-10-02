package seedu.address.model.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.ingredient.exceptions.DuplicateIngredientException;
import seedu.address.model.ingredient.exceptions.IngredientNotFoundException;

/**
 * A list of ingredients that enforces uniqueness between its elements and does not allow nulls.
 * An ingredient is considered unique by comparing using {@code Ingredient#isSameIngredient(Ingredient)}.
 * As such, adding and updating of ingredients uses Ingredient#isSameIngredient(Ingredient) for equality
 * so as to ensure that the ingredient being added or updated is unique in terms of identity in the Inventory.
 * However, the removal of an ingredient uses Ingredient#isSameIngredient(Ingredient)
 * so as to ensure that the ingredient with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Ingredient#isSameIngredient(Ingredient)
 */
public class Inventory implements Iterable<Ingredient> {
    private final ObservableList<Ingredient> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent uniqueIngredient as the given argument.
     */
    public boolean contains(Ingredient toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a uniqueIngredient to the list.
     * The uniqueIngredient must not already exist in the list.
     */
    public void add(Ingredient toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateIngredientException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the uniqueIngredient {@code target} in the list with {@code editedIngredient}.
     * {@code target} must exist in the list.
     * The uniqueIngredient identity of {@code editedIngredient} must not be the same
     * as another existing uniqueIngredient in the list.
     */
    public void setIngredient(Ingredient target, Ingredient editedIngredient) {
        requireAllNonNull(target, editedIngredient);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new IngredientNotFoundException();
        }

        if (!target.equals(editedIngredient) && contains(editedIngredient)) {
            throw new DuplicateIngredientException();
        }

        internalList.set(index, editedIngredient);
    }

    /**
     * Removes the equivalent uniqueIngredient from the list.
     * The uniqueIngredient must exist in the list.
     */
    public void remove(Ingredient toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new IngredientNotFoundException();
        }
    }

    public void setIngredients(Inventory replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code uniqueIngredients}.
     * {@code uniqueIngredients} must not contain duplicate uniqueIngredients.
     */
    public void setIngredients(List<Ingredient> uniqueIngredients) {
        requireAllNonNull(uniqueIngredients);
        if (!uniqueIngredientsAreUnique(uniqueIngredients)) {
            throw new DuplicateIngredientException();
        }

        internalList.setAll(uniqueIngredients);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Ingredient> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Ingredient> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Inventory // instanceof handles nulls
                && internalList.equals(((Inventory) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code uniqueIngredients} contains only unique uniqueIngredients.
     */
    private boolean uniqueIngredientsAreUnique(List<Ingredient> uniqueIngredients) {
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
