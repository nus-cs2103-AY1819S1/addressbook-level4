package seedu.souschef.logic;

import java.util.Map;
import java.util.function.Predicate;

import seedu.souschef.model.ingredient.IngredientDefinition;
import seedu.souschef.model.recipe.Recipe;

/**
 * Tests that a {@code Recipe} includes include-be included ingredients in inventory command.
 */
public class CrossFilterPredicate implements Predicate<Recipe> {
    private final Map<IngredientDefinition, Double> include;

    public CrossFilterPredicate(Map<IngredientDefinition, Double> include) {
        this.include = include;
    }

    @Override
    public boolean test(Recipe recipe) {
        Map<IngredientDefinition, Double> ingredients = recipe.getIngredients();

        for (IngredientDefinition key : include.keySet()) {
            if (!ingredients.keySet().contains(key)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CrossFilterPredicate // instanceof handles nulls
                && include.equals(((CrossFilterPredicate) other).include)); // state check
    }
}
