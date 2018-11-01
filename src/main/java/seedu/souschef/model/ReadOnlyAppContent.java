package seedu.souschef.model;

import javafx.collections.ObservableList;
import seedu.souschef.model.favourite.Favourites;
import seedu.souschef.model.healthplan.HealthPlan;
import seedu.souschef.model.ingredient.Ingredient;
import seedu.souschef.model.planner.Day;
import seedu.souschef.model.recipe.CrossRecipe;
import seedu.souschef.model.recipe.Recipe;
import seedu.souschef.model.tag.Tag;


/**
 * Unmodifiable view of an application content
 */
public interface ReadOnlyAppContent {
    /**
     * Returns an unmodifiable view of the respective list.
     * This list will not contain any duplicate recipes.
     */
    ObservableList<Recipe> getObservableRecipeList();
    ObservableList<Tag> getObservableTagList();

    /**
     * Returns an unmodifiable view of the respective list.
     * This list will not contain any duplicate ingredients.
     */
    ObservableList<Ingredient> getObservableIngredientList();

    /**
     * Returns an unmodifiable view of the respective list.
     * This list will not contain any duplicate inventory recipes.
     */
    ObservableList<CrossRecipe> getObservableCrossRecipeList();

    /**
     * Returns an unmodifiable view of the plan list.
     * This list will not contain any duplicate plan.
     */
    ObservableList<HealthPlan> getObservableHealthPlanList();

    /**
     * Returns an unmodifiable view of the meal planner.
     * This list will not contain any duplicate days.
     */
    ObservableList<Day> getObservableMealPlanner();

    /**
     * Returns an unmodifiable view of the favourite list.
     * This list will not contain any duplicate favourite recipes.
     */
    ObservableList<Favourites> getObservableFavouritesList();

}
