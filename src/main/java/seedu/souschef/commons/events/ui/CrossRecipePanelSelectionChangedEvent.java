package seedu.souschef.commons.events.ui;

import seedu.souschef.model.recipe.CrossRecipe;

public class CrossRecipePanelSelectionChangedEvent extends GenericPanelSelectionChangedEvent<CrossRecipe> {
    public CrossRecipePanelSelectionChangedEvent(CrossRecipe newSelection) {
        super(newSelection);
    }
}
