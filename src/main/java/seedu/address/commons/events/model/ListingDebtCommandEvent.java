package seedu.address.commons.events.model;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.accounting.Debt;

/**
 * An event that raise when a debt listing command is called.
 * Pass an Observable List.
 * This event is handled in Main Window to replace the list panel item
 * with this Observable List's items.
 */

public class ListingDebtCommandEvent extends BaseEvent {

    public final ObservableList<Debt> listingItem;

    public ListingDebtCommandEvent(ObservableList<Debt> listingItem) {
        this.listingItem = listingItem;
    }

    @Override
    public String toString() {
        return "Listing Debt";
    }
}
