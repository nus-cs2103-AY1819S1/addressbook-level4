package seedu.souschef.model;

import javafx.collections.ObservableList;
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
}
