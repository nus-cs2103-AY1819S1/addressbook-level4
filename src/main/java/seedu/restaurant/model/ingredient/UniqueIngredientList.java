package seedu.restaurant.model.ingredient;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.restaurant.model.ingredient.exceptions.DuplicateIngredientException;
import seedu.restaurant.model.ingredient.exceptions.IngredientNotEnoughException;
import seedu.restaurant.model.ingredient.exceptions.IngredientNotFoundException;

//@@author rebstan97
/**
 * A list of ingredients that enforces uniqueness between its elements and does not allow nulls.
 * A ingredient is considered unique by comparing using {@code Ingredient#isSameIngredient(Ingredient)}. As such,
 * adding and updating of ingredients uses Ingredient#isSameIngredient(Ingredient) for equality so as to ensure that
 * the ingredient being added or updated is unique in terms of identity in the UniqueIngredientList. However, the
 * removal of a ingredient uses Ingredient#equals(Object) so as to ensure that the ingredient with exactly the same
 * fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Ingredient#isSameIngredient(Ingredient)
 */
public class UniqueIngredientList implements Iterable<Ingredient> {

    private final ObservableList<Ingredient> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent ingredient as the given argument.
     */
    public boolean contains(Ingredient toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameIngredient);
    }

    /**
     * Adds a ingredient to the list.
     * The ingredient must not already exist in the list.
     */
    public void add(Ingredient toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateIngredientException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the ingredient {@code target} in the list with {@code editedIngredient}.
     * {@code target} must exist in the list.
     * The ingredient identity of {@code editedIngredient} must not be the same as another existing ingredient in the
     * list.
     */
    public void setIngredient(Ingredient target, Ingredient editedIngredient) {
        requireAllNonNull(target, editedIngredient);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new IngredientNotFoundException();
        }

        if (!target.isSameIngredient(editedIngredient) && contains(editedIngredient)) {
            throw new DuplicateIngredientException();
        }

        internalList.set(index, editedIngredient);
    }

    /**
     * Removes the equivalent ingredient from the list.
     * The ingredient must exist in the list.
     */
    public void remove(Ingredient toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new IngredientNotFoundException();
        }
    }

    /**
     * Finds the ingredient with the equivalent name from the list.
     * @throws IngredientNotFoundException if the ingredient does not exist in the list.
     */
    public Ingredient find(IngredientName ingredientName) throws IngredientNotFoundException {
        requireNonNull(ingredientName);
        Predicate<Ingredient> predicate = ingredient -> ingredient.getName().equalsIgnoreCase(ingredientName);
        if (!internalList.stream().anyMatch(predicate)) {
            throw new IngredientNotFoundException();
        }
        return internalList.stream().filter(predicate).findFirst().get();
    }

    /**
     * Stocks up the ingredients which are keys in Map {@code requiredIngredients} by increasing the number
     * of units of each ingredient.
     * @throws  IngredientNotFoundException if the ingredient does not exist in the list.
     */
    public void stockUp(Map<IngredientName, Integer> requiredIngredients) throws IngredientNotFoundException {
        requireNonNull(requiredIngredients);
        HashMap<IngredientName, Integer> stockedUpIngredients = new HashMap<>();
        for (HashMap.Entry<IngredientName, Integer> ingredientPair : requiredIngredients.entrySet()) {
            IngredientName name = ingredientPair.getKey();
            Integer unitsToAdd = ingredientPair.getValue();

            Ingredient ingredient;
            try {
                ingredient = find(name);
            } catch (IngredientNotFoundException e) {
                consume(stockedUpIngredients);
                throw new IngredientNotFoundException();
            }

            NumUnits updatedNumUnits = ingredient.getNumUnits().increase(unitsToAdd);
            Ingredient stockedUpIngredient = new Ingredient(ingredient.getName(), ingredient.getUnit(),
                    ingredient.getPrice(), ingredient.getMinimum(), updatedNumUnits);
            setIngredient(ingredient, stockedUpIngredient);
            stockedUpIngredients.put(name, unitsToAdd);
        }
    }

    /**
     * Consumes the ingredients which are keys in Map {@code requiredIngredients} by decreasing the number
     * of units of each ingredient.
     * @throws  IngredientNotFoundException if the ingredient does not exist in the list.
     * @throws  IngredientNotEnoughException if the ingredient does not have sufficient units.
     */
    public void consume(Map<IngredientName, Integer> requiredIngredients) throws IngredientNotFoundException,
            IngredientNotEnoughException {
        requireNonNull(requiredIngredients);
        HashMap<IngredientName, Integer> consumedIngredients = new HashMap<>();
        for (HashMap.Entry<IngredientName, Integer> ingredientPair : requiredIngredients.entrySet()) {
            IngredientName name = ingredientPair.getKey();
            Integer unitsToConsume = ingredientPair.getValue();

            Ingredient ingredient;
            try {
                ingredient = find(name);
            } catch (IngredientNotFoundException e) {
                stockUp(consumedIngredients);
                throw new IngredientNotFoundException();
            }

            if (ingredient.getNumUnits().getNumberOfUnits() < unitsToConsume) {
                stockUp(consumedIngredients);
                throw new IngredientNotEnoughException();
            }

            NumUnits updatedNumUnits = ingredient.getNumUnits().decrease(unitsToConsume);
            Ingredient consumedIngredient = new Ingredient(ingredient.getName(), ingredient.getUnit(),
                    ingredient.getPrice(), ingredient.getMinimum(), updatedNumUnits);
            setIngredient(ingredient, consumedIngredient);
            consumedIngredients.put(name, unitsToConsume);
        }
    }

    public void setIngredients(UniqueIngredientList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code ingredients}.
     * {@code ingredients} must not contain duplicate ingredients.
     */
    public void setIngredients(List<Ingredient> ingredients) {
        requireAllNonNull(ingredients);
        if (!ingredientsAreUnique(ingredients)) {
            throw new DuplicateIngredientException();
        }

        internalList.setAll(ingredients);
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
                || (other instanceof UniqueIngredientList // instanceof handles nulls
                && internalList.equals(((UniqueIngredientList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code ingredients} contains only unique ingredients.
     */
    private boolean ingredientsAreUnique(List<Ingredient> ingredients) {
        for (int i = 0; i < ingredients.size() - 1; i++) {
            for (int j = i + 1; j < ingredients.size(); j++) {
                if (ingredients.get(i).isSameIngredient(ingredients.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
