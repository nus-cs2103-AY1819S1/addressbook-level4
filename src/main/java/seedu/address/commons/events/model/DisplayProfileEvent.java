package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.user.User;

/**
 * Represents the current user's profile to be displayed.
 */
public class DisplayProfileEvent extends BaseEvent {

    private final User currentUser;

    public DisplayProfileEvent(User currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
