package seedu.inventory.commons.events.model;

import seedu.inventory.commons.events.BaseEvent;

/**
 * Indicates a request for UI to change to item list
 */
public class AccessPurchaseOrderEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
