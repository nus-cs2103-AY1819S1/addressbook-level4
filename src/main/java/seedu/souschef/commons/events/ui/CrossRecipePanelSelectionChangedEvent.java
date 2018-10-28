package seedu.souschef.commons.events.ui;

import seedu.souschef.model.shop.CrossRecipe;

public class CrossRecipePanelSelectionChangedEvent extends GenericPanelSelectionChangedEvent<CrossRecipe> {
    public CrossRecipePanelSelectionChangedEvent(CrossRecipe newSelection) {
        super(newSelection);
    }
}
