package seedu.restaurant.commons.events.ui.accounts;

import seedu.restaurant.commons.core.session.UserSession;
import seedu.restaurant.commons.events.BaseEvent;
import seedu.restaurant.model.account.Account;
import seedu.restaurant.model.account.Username;

//@@author AZhiKai
/**
 * Indicates a user has just logged into the system.
 */
public class LoginEvent extends BaseEvent {

    public final Username username;

    public LoginEvent(Account account) {
        UserSession.create(account);
        this.username = account.getUsername();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
