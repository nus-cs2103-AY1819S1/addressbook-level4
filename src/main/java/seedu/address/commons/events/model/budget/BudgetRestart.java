package seedu.address.commons.events.model.budget;
//@@author winsonhys

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that the current budget getting check for potentially entering a new month
 */
public class BudgetRestart extends BaseEvent {

    public BudgetRestart() {
    }

    @Override
    public String toString() {
        return "Check if budget needs to restart";
    }
}
