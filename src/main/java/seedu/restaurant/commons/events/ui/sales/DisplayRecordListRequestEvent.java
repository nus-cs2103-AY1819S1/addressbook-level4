package seedu.restaurant.commons.events.ui.sales;

import seedu.restaurant.commons.events.BaseEvent;

/**
 * An event requesting to display RecordListPanel.
 */
public class DisplayRecordListRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
