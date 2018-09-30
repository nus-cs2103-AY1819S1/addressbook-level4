package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.events.model.HealthplanChangedEvent;
import seedu.address.model.recipe.Recipe;
import seedu.address.model.healthplan.HealthPlan;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Recipe> PREDICATE_SHOW_ALL_RECIPES = unused -> true;
    Predicate<HealthPlan> PREDICATE_SHOW_ALL_PLANS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAppContent newData);

    /** Returns the AppContent */
    ReadOnlyAppContent getAppContent();

    /**
     * Returns true if a recipe with the same identity as {@code recipe} exists in the application content.
     */
    boolean hasRecipe(Recipe recipe);

    /**
     * Deletes the given recipe.
     * The recipe must exist in the application content.
     */
    void deleteRecipe(Recipe target);

    /**
     * Adds the given recipe.
     * {@code recipe} must not already exist in the application content.
     */
    void addRecipe(Recipe recipe);

    /**
     * Replaces the given recipe {@code target} with {@code editedRecipe}.
     * {@code target} must exist in the application content.
     * The recipe identity of {@code editedRecipe} must not be the same as another existing recipe in the application
     * content.
     */
    void updateRecipe(Recipe target, Recipe editedRecipe);

    /** Returns an unmodifiable view of the filtered recipe list */
    ObservableList<Recipe> getFilteredRecipeList();

    /**
     * Updates the filter of the filtered recipe list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredRecipeList(Predicate<Recipe> predicate);


    //healthplans

    void updateFilteredPlans(Predicate<HealthPlan> predicate);

    void updatePlan(HealthPlan target, HealthPlan editedPlan);

    void addPlan(HealthPlan plan);

    boolean hasPlan(HealthPlan plan);

    void deletePlan(HealthPlan plan);

    ObservableList<HealthPlan> getFilteredPlans();



    /**
     * Returns true if the model has previous application content states to restore.
     */
    boolean canUndoAppContent();

    /**
     * Returns true if the model has undone application content states to restore.
     */
    boolean canRedoAppContent();

    /**
     * Restores the model's application content to its previous state.
     */
    void undoAppContent();

    /**
     * Restores the model's application content to its previously undone state.
     */
    void redoAppContent();

    /**
     * Saves the current application content state for undo/redo.
     */
    void commitAppContent();
}
