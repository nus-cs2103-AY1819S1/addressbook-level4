package seedu.clinicio.commons.events.ui;

import seedu.clinicio.commons.events.BaseEvent;

import seedu.clinicio.model.staff.Staff;

/**
 * Indicate the login is successful.
 */
public class LoginSuccessEvent extends BaseEvent {

    private Staff currentUser;

    public LoginSuccessEvent(Staff currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
