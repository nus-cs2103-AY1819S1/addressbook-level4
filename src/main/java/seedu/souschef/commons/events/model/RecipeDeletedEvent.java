package seedu.souschef.commons.events.model;

import seedu.souschef.commons.events.BaseEvent;
import seedu.souschef.model.recipe.Recipe;

/**
 * Event that is raised whenever a Recipe object is deleted from the recipeModel. Contains the recipe that is deleted.
 */
public class RecipeDeletedEvent extends BaseEvent {

    public final Recipe recipe;

    public RecipeDeletedEvent(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + recipe.getName().fullName + "]";
    }
}
