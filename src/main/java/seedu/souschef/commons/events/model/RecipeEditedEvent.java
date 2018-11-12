package seedu.souschef.commons.events.model;

import seedu.souschef.commons.events.BaseEvent;
import seedu.souschef.model.recipe.Recipe;

/**
 * Event that is raised whenever a recipe is edited.
 */
public class RecipeEditedEvent extends BaseEvent {

    public final Recipe oldRecipe;
    public final Recipe newRecipe;

    public RecipeEditedEvent(Recipe oldRecipe, Recipe newRecipe) {
        this.oldRecipe = oldRecipe;
        this.newRecipe = newRecipe;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[old: "
            + oldRecipe.getName().fullName + "]"
            + "[new: " + newRecipe.getName().fullName + "]";
    }
}
