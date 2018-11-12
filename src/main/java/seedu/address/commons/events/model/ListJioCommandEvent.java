package seedu.address.commons.events.model;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.jio.Jio;

/**
 * Event when listJio command.
 */
public class ListJioCommandEvent extends BaseEvent {
    public final ObservableList<Jio> listingItem;
    public ListJioCommandEvent(ObservableList<Jio> listingItem) {
        this.listingItem = listingItem;
    }
    @Override
    public String toString() {
        return "Listing Jios";
    }
}
