package seedu.restaurant.commons.events.storage;

import seedu.restaurant.commons.core.session.UserSession;
import seedu.restaurant.commons.events.BaseEvent;
import seedu.restaurant.model.account.Account;
import seedu.restaurant.model.account.Username;

//@@author AZhiKai
/**
 * Indicates a user account has just been updated.
 */
public class UpdateAccountEvent extends BaseEvent {

    public final Username username;

    public UpdateAccountEvent(Account account) {
        UserSession.update(account);
        this.username = account.getUsername();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
