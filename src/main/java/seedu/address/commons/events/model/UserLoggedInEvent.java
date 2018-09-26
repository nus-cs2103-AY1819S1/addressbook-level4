package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

public class UserLoggedInEvent extends BaseEvent {
    private final String username;

    public UserLoggedInEvent(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Logged in to user: " + username;
    }
}
