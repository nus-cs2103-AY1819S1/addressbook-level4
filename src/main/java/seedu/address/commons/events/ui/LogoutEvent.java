package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that the logout command has been entered
 */
public class LogoutEvent extends BaseEvent {
    public LogoutEvent() {
    }

    @Override
    public String toString() {
        return "logout";
    }
}
