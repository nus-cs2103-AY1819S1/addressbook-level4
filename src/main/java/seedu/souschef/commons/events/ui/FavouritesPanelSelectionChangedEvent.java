package seedu.souschef.commons.events.ui;

import seedu.souschef.model.favourite.Favourites;
import seedu.souschef.model.recipe.Recipe;

/**
 * Represents a selection change in the Favourites List Panel
 */
public class FavouritesPanelSelectionChangedEvent extends GenericPanelSelectionChangedEvent<Recipe> {

    public FavouritesPanelSelectionChangedEvent(Favourites newSelection) {
        super(newSelection);
    }
}
