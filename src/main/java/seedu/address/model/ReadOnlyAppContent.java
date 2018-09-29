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

//healthplan
    ObservableList<HealthPlan> getHealthPlanList();

}
