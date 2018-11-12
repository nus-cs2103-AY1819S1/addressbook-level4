package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates the user in the model has logged out.
 * In order to refresh the UI when user logged out,
 * prevent the next user read other user's information(List panel and browser panel).
 */

public class UserLoggedOutEvent extends BaseEvent {
    @Override
    public String toString() {
        return "Logged out";
    }
}
