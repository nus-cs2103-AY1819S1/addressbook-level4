package seedu.souschef.model;

import seedu.souschef.model.healthplan.HealthPlan;
import seedu.souschef.model.ingredient.Ingredient;
import seedu.souschef.model.recipe.Recipe;

/**
 * The API of the ModelSet component.
 */
public interface ModelSet {
    /** Returns read-only app content */
    ReadOnlyAppContent getAppContent();

    /** Returns the model for recipes*/
    Model<Recipe> getRecipeModel();

    /** Returns the model for ingredients*/
    Model<Ingredient> getIngredientModel();

    /** Returns the model for healthplans*/
    Model<HealthPlan> getHealthPlanModel();
}
