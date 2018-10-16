package seedu.souschef.commons.events.ui;

import seedu.souschef.model.favourite.Favourites;

/**
 * Represents a selection change in the Favourites List Panel
 */
public class FavouritesPanelSelectionChangedEvent extends GenericPanelSelectionChangedEvent<Favourites> {

    public FavouritesPanelSelectionChangedEvent(Favourites newSelection) {
        super(newSelection);
    }
}
