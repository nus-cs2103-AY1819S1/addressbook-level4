package seedu.souschef.model;

import javafx.collections.ObservableList;

import seedu.souschef.model.healthplan.HealthPlan;

import seedu.souschef.model.planner.Day;
import seedu.souschef.model.recipe.Recipe;


/**
 * Unmodifiable view of an application content
 */
public interface ReadOnlyAppContent {

    /**
     * Returns an unmodifiable view of the respective list.
     * This list will not contain any duplicate recipes.
     */
    ObservableList<Recipe> getObservableRecipeList();

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
}
