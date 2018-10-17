package seedu.inventory.commons.events.model;

import seedu.inventory.commons.events.BaseEvent;
import seedu.inventory.model.ReadOnlyStaffList;

/**
 * Indicates the StaffList in the model has changed.
 */
public class StaffListChangedEvent extends BaseEvent {

    public final ReadOnlyStaffList data;

    /**
     * Constructs the StaffListChangedEvent.
     * @param data the staff list
     */
    public StaffListChangedEvent(ReadOnlyStaffList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of items " + data.getStaffList().size();
    }
}
