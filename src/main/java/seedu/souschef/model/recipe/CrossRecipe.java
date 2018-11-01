package seedu.souschef.model.recipe;

import java.util.Map;

import seedu.souschef.model.UniqueType;
import seedu.souschef.model.ingredient.IngredientDefinition;
import seedu.souschef.model.ingredient.IngredientPortion;

/**
 * Represents a Recipe with its needed ingredient information.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class CrossRecipe extends Recipe {
    private final Map<IngredientDefinition, IngredientPortion> neededIngredients;

    public CrossRecipe(Recipe recipe, Map<IngredientDefinition, IngredientPortion> neededIngredients) {
        super(recipe.getName(), recipe.getDifficulty(), recipe.getCookTime(), recipe.getInstructions(),
                recipe.getTags());
        this.neededIngredients = neededIngredients;
    }

    public Recipe getRecipe() {
        return new Recipe(getName(), getDifficulty(), getCookTime(), getInstructions(), getTags());
    }

    public Map<IngredientDefinition, IngredientPortion> getNeededIngredients() {
        return neededIngredients;
    }

    @Override
    public boolean isSame(UniqueType other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CrossRecipe)) {
            return false;
        }

        CrossRecipe otherRecipe = (CrossRecipe) other;

        return otherRecipe != null
                && otherRecipe.getRecipe().equals(getRecipe());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CrossRecipe)) {
            return false;
        }

        CrossRecipe otherRecipe = (CrossRecipe) other;

        return otherRecipe != null
                && otherRecipe.getRecipe().equals(getRecipe());
    }
}
