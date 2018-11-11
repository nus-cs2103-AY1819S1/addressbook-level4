package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that there has been an unsuccessful login.
 */
public class FailedLoginEvent extends BaseEvent {
    public static final String INVALID_USERNAME = "No such user found";
    public static final String INVALID_PASSWORD = "Wrong password!";
    public static final String NON_CONFORMING_INPUTS = "Invalid username or password.";

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
