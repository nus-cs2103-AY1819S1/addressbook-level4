package seedu.souschef.commons.events.ui;

import seedu.souschef.model.recipe.CrossRecipe;

/**
 * Represents a selection change in the Cross Recipe List Panel
 */
public class CrossRecipePanelSelectionChangedEvent extends GenericPanelSelectionChangedEvent<CrossRecipe> {
    public CrossRecipePanelSelectionChangedEvent(CrossRecipe newSelection) {
        super(newSelection);
    }
}
