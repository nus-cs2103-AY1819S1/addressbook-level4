package seedu.address.commons.events.ui;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.events.BaseEvent;

//@@author chivent

/**
 * An event that notifies StatusBarFooter regarding a login status change.
 */
public class LoginStatusEvent extends BaseEvent {

    public final String user;
    public final boolean loggedIn;

    /**
     * Constructor for LoginStatusEvent
     *
     * @param user The email of the logged in user
     */
    public LoginStatusEvent(String user) {
        this.user = requireNonNull(user);
        if (this.user.isEmpty()) {
            loggedIn = false;
        } else {
            loggedIn = true;
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
