package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyExpenseTracker;

/** Indicates the ExpenseTracker in the model has changed*/
public class ExpenseTrackerChangedEvent extends BaseEvent {

    public final ReadOnlyExpenseTracker data;

    public ExpenseTrackerChangedEvent(ReadOnlyExpenseTracker data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of expenses " + data.getExpenseList().size() + "/number of notifications"
                + data.getNotificationList().size();
    }
}
