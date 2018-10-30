package seedu.clinicio.commons.events.ui;

import seedu.clinicio.commons.events.BaseEvent;
import seedu.clinicio.commons.core.UserSession;

/**
 * Indicate the login is successful.
 */
public class LoginSuccessEvent extends BaseEvent {

    private UserSession userSession;
    
    public LoginSuccessEvent(UserSession userSession) {
        this.userSession = userSession;
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
