package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAssignmentList;

/** Indicates the AddressBook in the model has changed*/
public class AssignmentListChangedEvent extends BaseEvent {

    public final ReadOnlyAssignmentList data;

    public AssignmentListChangedEvent(ReadOnlyAssignmentList data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of assignments " + data.getAssignmentList().size();
    }
}
