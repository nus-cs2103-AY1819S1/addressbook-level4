package seedu.souschef.logic;

import java.util.HashMap;
import java.util.function.Predicate;

import seedu.souschef.model.recipe.Recipe;

/**
 * Tests that a {@code Recipe} includes must-be included ingredients in inventory command.
 */
public class InventoryPredicate implements Predicate<Recipe> {
    private final HashMap<String, Double> must;

    public InventoryPredicate(HashMap<String, Double> must) {
        this.must = must;
    }

    @Override
    public boolean test(Recipe recipe) {
        HashMap<String, Double> ingredients = recipe.getIngredients();

        for (String key : must.keySet()) {
            if (!ingredients.keySet().contains(key)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InventoryPredicate // instanceof handles nulls
                && must.equals(((InventoryPredicate) other).must)); // state check
    }
}
