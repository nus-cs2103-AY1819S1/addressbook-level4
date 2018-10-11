package seedu.souschef.commons.events.ui;

import seedu.souschef.model.recipe.Recipe;

/**
 * Represents a selection change in the Recipe List Panel
 */
public class RecipePanelSelectionChangedEvent extends GenericPanelSelectionChangedEvent<Recipe>/*BaseEvent */{

    public RecipePanelSelectionChangedEvent(Recipe newSelection) {
        super(newSelection);
    }
}
