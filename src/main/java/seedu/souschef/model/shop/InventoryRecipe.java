package seedu.souschef.model.shop;

import java.util.HashMap;

import seedu.souschef.model.ingredient.IngredientDefinition;
import seedu.souschef.model.recipe.Recipe;

public class InventoryRecipe {
    private final Recipe recipe;
    private final HashMap<IngredientDefinition, Double> matchedIngredients;

    public InventoryRecipe(Recipe recipe, HashMap<IngredientDefinition, Double> matchedIngredients) {
        this.recipe = recipe;
        this.matchedIngredients = matchedIngredients;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public HashMap<IngredientDefinition, Double> getMatchedIngredients() {
        return matchedIngredients;
    }

    public int getNumberOfIngredients() {
        return matchedIngredients.size();
    }
}
