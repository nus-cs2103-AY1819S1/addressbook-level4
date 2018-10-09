package seedu.souschef.model;

import seedu.souschef.model.recipe.Recipe;

/**
 * The API of the ModelSet component.
 */
public interface ModelSet {
    /** Returns read-only app content */
    ReadOnlyAppContent getAppContent();

    /** Returns the model for recipes*/
    Model<Recipe> getRecipeModel();
}
