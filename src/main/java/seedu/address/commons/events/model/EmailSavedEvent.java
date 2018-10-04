package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.EmailModel;

//@@author EatOrBeEaten
/**
 * Indicates the Email in the model has been saved.
 */
public class EmailSavedEvent extends BaseEvent {

    public final EmailModel data;

    public EmailSavedEvent(EmailModel data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return data.getEmail().getSubject();
    }

}
