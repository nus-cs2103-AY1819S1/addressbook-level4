package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that the leave list command has been run
 */
public class LeaveListEvent extends BaseEvent {
    public LeaveListEvent() {
    }

    @Override
    public String toString() {
        return "leavelist";
    }
}
