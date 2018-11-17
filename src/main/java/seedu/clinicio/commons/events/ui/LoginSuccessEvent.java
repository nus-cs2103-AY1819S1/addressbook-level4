package seedu.clinicio.commons.events.ui;

import seedu.clinicio.commons.events.BaseEvent;

import seedu.clinicio.model.staff.Staff;

/**
 * Indicate the login is successful.
 */
public class LoginSuccessEvent extends BaseEvent {

    private final Staff currentUser;

    public LoginSuccessEvent(Staff currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Retrieve current user session
     */
    public Staff getCurrentUser() {
        return currentUser;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
