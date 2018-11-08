package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that the assignment list command has been run
 */
public class AssignmentListEvent extends BaseEvent {
    public AssignmentListEvent() {
    }

    @Override
    public String toString() {
        return "listassignment";
    }
}
