package seedu.address.commons.events.model;

import org.simplejavamail.email.Email;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates the Email in the model has been saved.
 */
public class EmailSavedEvent extends BaseEvent {

    public final Email data;

    public EmailSavedEvent(Email data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
