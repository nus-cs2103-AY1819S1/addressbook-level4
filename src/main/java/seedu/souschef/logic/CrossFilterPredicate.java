package seedu.souschef.logic;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import seedu.souschef.model.ingredient.IngredientDefinition;
import seedu.souschef.model.ingredient.IngredientPortion;
import seedu.souschef.model.recipe.Recipe;

/**
 * Tests that a {@code Recipe} includes include-be included ingredients in inventory command.
 */
public class CrossFilterPredicate implements Predicate<Recipe> {
    private final List<IngredientDefinition> include;

    public CrossFilterPredicate(List<IngredientDefinition> include) {
        this.include = include;
    }

    @Override
    public boolean test(Recipe recipe) {
        Map<IngredientDefinition, IngredientPortion> ingredients = recipe.getIngredients();

        for (IngredientDefinition key : include) {
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
