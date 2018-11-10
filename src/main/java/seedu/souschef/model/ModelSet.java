package seedu.souschef.model;

import seedu.souschef.model.favourite.Favourites;
import seedu.souschef.model.healthplan.HealthPlan;
import seedu.souschef.model.ingredient.Ingredient;
import seedu.souschef.model.planner.Day;
import seedu.souschef.model.recipe.CrossRecipe;
import seedu.souschef.model.recipe.Recipe;
/**
 * The API of the ModelSet component.
 */
public interface ModelSet {
    /** Returns read-only app content */
    ReadOnlyAppContent getAppContent();

    /** Returns the model for recipes*/
    Model<Recipe> getRecipeModel();

    /** Returns the model for meal planner*/
    Model<Day> getMealPlannerModel();

    /** Returns the model for ingredients*/
    Model<Ingredient> getIngredientModel();

    /** Returns the model for inventory recipes*/
    Model<CrossRecipe> getCrossRecipeModel();

    /** Returns the model for recipes*/
    Model<HealthPlan> getHealthPlanModel();

    /** Returns the model for favourite*/
    Model<Favourites> getFavouriteModel();
}
