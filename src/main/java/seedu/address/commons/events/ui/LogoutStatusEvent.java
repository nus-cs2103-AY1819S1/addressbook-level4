package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author chivent

/**
 * An event that notifies StatusBarFooter regarding a login status change.
 */
public class LogoutStatusEvent extends BaseEvent {
    /**
     * Constructor for LogoutStatusEvent
     */
    public LogoutStatusEvent() {
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
