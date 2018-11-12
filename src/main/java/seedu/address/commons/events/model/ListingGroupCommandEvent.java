package seedu.address.commons.events.model;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.group.Group;

/** Listing all the groups.*/
public class ListingGroupCommandEvent extends BaseEvent {
    public final ObservableList<Group> listingItem;

    /**
     * Constructor method for ListingGroupCommandEvent
     * @param listingItem
     */
    public ListingGroupCommandEvent(ObservableList<Group> listingItem) {
        this.listingItem = listingItem;
    }

    @Override
    public String toString() {
        return "Listing Groups";
    }
}
