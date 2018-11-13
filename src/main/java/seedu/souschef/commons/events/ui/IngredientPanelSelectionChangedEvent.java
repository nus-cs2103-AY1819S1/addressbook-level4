package seedu.souschef.commons.events.ui;

import seedu.souschef.model.ingredient.Ingredient;

/**
 * Represents a selection change in the Ingredient List Panel
 */
public class IngredientPanelSelectionChangedEvent extends GenericPanelSelectionChangedEvent<Ingredient> {

    public IngredientPanelSelectionChangedEvent(Ingredient newSelection) {
        super(newSelection);
    }
}
