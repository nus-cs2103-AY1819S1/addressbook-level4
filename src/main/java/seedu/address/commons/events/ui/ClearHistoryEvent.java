package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author chivent
/**
 * An event that notifies HistoryListPanel on a new select command.
 */
public class ClearHistoryEvent extends BaseEvent {

    /**
     * Constructor for ClearHistoryEvent
     */
    public ClearHistoryEvent() {

    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
