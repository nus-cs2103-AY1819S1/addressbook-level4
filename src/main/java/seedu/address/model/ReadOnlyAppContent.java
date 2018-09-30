package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.healthplan.HealthPlan;
import seedu.address.model.recipe.Recipe;

/**
 * Unmodifiable view of an application content
 */
public interface ReadOnlyAppContent {

    /**
     * Returns an unmodifiable view of the recipes list.
     * This list will not contain any duplicate recipes.
     */
    ObservableList<Recipe> getRecipeList();

    /**
     *
     * returns the healthplan list
     * Returns an unmodifiable view of the plan list.
     * This list will not contain any duplicate plan.
     */
    ObservableList<HealthPlan> getHealthPlanList();

}
