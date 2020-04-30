package seedu.restaurant.commons.events.ui.accounts;

import seedu.restaurant.commons.events.BaseEvent;

//@@author AZhiKai
/**
 * An event requesting to display AccountListPanel.
 */
public class DisplayAccountListRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
