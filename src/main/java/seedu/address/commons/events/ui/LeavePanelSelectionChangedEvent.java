package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.leaveapplication.LeaveApplication;

/**
 * Represents a selection change in the LeaveApplication List Panel
 */
public class LeavePanelSelectionChangedEvent extends BaseEvent {

    private final LeaveApplication newSelection;

    public LeavePanelSelectionChangedEvent(LeaveApplication newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public LeaveApplication getNewSelection() {
        return newSelection;
    }
}
