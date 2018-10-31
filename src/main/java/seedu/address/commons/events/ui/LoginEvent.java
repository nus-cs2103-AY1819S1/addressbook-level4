package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that the login button has been pressed
 */
public class LoginEvent extends BaseEvent {

    private String username;
    private String password;

    public LoginEvent(String u, String p) {
        username = u;
        password = p;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "login u:" + username + " p:" + password;
    }
}
