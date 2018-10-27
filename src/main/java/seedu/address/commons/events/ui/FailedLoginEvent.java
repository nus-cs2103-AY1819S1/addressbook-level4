package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that there has been an unsuccessful login.
 */
public class FailedLoginEvent extends BaseEvent {
    private String message;

    public FailedLoginEvent(String reason) {
        message = reason;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Failed Login Event: " + message;
    }
}
