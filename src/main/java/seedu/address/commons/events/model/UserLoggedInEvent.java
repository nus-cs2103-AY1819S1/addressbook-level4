package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.user.Username;

/** Indicates the a user has logged in */
public class UserLoggedInEvent extends BaseEvent {
    private final Username username;

    public UserLoggedInEvent(Username username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Logged in to user: " + username.toString();
    }
}
