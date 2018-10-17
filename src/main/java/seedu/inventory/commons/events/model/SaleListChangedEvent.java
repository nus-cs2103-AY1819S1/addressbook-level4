package seedu.inventory.commons.events.model;

import seedu.inventory.commons.events.BaseEvent;
import seedu.inventory.model.ReadOnlySaleList;

/** Indicates the Sale List in the model has changed*/
public class SaleListChangedEvent extends BaseEvent {

    public final ReadOnlySaleList data;

    public SaleListChangedEvent(ReadOnlySaleList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of items " + data.getSaleList().size();
    }
}
