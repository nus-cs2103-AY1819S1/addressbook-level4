package seedu.inventory.commons.events.ui;

import seedu.inventory.commons.events.BaseEvent;
import seedu.inventory.model.purchaseorder.PurchaseOrder;

/**
 * Represents a selection change in the Item List Panel
 */
public class PurchaseOrderSelectionChangedEvent extends BaseEvent {

    private final PurchaseOrder newSelection;

    public PurchaseOrderSelectionChangedEvent(PurchaseOrder newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public PurchaseOrder getNewSelection() {
        return newSelection;
    }
}
