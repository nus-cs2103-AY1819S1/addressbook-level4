package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.User;

/**
 * Indicates that there has been a successful login
 */
public class SuccessfulLoginEvent extends BaseEvent {
    private User loggedInPerson;

    public SuccessfulLoginEvent(User user) {
        loggedInPerson = user;
    }

    public User getLoggedInPerson() {
        return loggedInPerson;
    }

    @Override
    public String toString() {
        return "Successful login by " + loggedInPerson;
    }
}
