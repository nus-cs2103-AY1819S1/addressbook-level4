package seedu.souschef.model;

<<<<<<< HEAD
=======
import seedu.souschef.commons.core.EventsCenter;
import seedu.souschef.model.favourite.Favourites;
>>>>>>> a0774f84ee9a4d04513be5834cde4b26f1276a17
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
<<<<<<< HEAD
    Model<Recipe> getFavouriteModel();
=======
    Model<Favourites> getFavouriteModel();

    /**
     * Registers the object as an event handler at the {@link EventsCenter}
     * @param handler usually {@code this}
     */
    default void registerAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().registerHandler(handler);
    }
>>>>>>> a0774f84ee9a4d04513be5834cde4b26f1276a17
}
