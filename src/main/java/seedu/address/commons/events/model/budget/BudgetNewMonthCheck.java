package seedu.address.commons.events.model.budget;
//@@author winsonhys

import java.time.LocalDate;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that the current budget getting check for potentially entering a new month
 */
public class BudgetNewMonthCheck extends BaseEvent {
    public final LocalDate newDate;

    public BudgetNewMonthCheck(LocalDate newDate) {
        this.newDate = newDate;
    }

    @Override
    public String toString() {
        return "new date = " + this.newDate.toString();
    }
}
