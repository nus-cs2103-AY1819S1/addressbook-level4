package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to view the budget page.
 */
public class ShowBudgetViewEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
