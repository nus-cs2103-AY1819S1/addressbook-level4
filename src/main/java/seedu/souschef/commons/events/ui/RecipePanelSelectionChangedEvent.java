package seedu.souschef.commons.events.ui;

import seedu.souschef.commons.events.BaseEvent;
import seedu.souschef.model.recipe.Recipe;

/**
 * Represents a selection change in the Recipe List Panel
 */
public class RecipePanelSelectionChangedEvent extends BaseEvent {


    private final Recipe newSelection;

    public RecipePanelSelectionChangedEvent(Recipe newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Recipe getNewSelection() {
        return newSelection;
    }
}
