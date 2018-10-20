package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;

//@@author EatOrBeEaten

/**
 * Indicates that Email is to be loaded from local directory.
 */
public class EmailLoadEvent extends BaseEvent {

    public final String emailName;

    public EmailLoadEvent(String emailName) {
        this.emailName = emailName;
    }

    @Override
    public String toString() {
        return emailName;
    }

}
