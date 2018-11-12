package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Password;

/**
 * Indicates that there has been an admin password modification.
 * */
public class AdminPasswordModificationEvent extends BaseEvent {
    public final Password newPassword;

    public AdminPasswordModificationEvent(Password newP) {
        newPassword = newP;
    }

    @Override
    public String toString() {
        return "Admin password changed: " + newPassword;
    }
}
