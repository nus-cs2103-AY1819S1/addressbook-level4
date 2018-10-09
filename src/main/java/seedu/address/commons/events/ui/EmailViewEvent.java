package seedu.address.commons.events.ui;

import org.simplejavamail.email.Email;

import seedu.address.commons.events.BaseEvent;

//@@author EatOrBeEaten
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
