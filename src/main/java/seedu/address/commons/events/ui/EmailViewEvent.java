package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

import org.simplejavamail.email.Email;

/**
 * Indicates a request to view email.
 */
public class EmailViewEvent extends BaseEvent {

    private final Email email;

    public EmailViewEvent(Email email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Email getEmail() {
        return email;
    }
    
}
