package seedu.inventory.commons.events.model;

import seedu.inventory.commons.events.BaseEvent;
import seedu.inventory.model.ReadOnlyInventory;

/** Indicates the Inventory in the model has changed*/
public class InventoryChangedEvent extends BaseEvent {

    public final ReadOnlyInventory data;

    public InventoryChangedEvent(ReadOnlyInventory data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of items " + data.getItemList().size();
    }
}
