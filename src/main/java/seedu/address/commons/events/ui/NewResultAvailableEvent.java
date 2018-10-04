package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that a new result is available.
 */
public class NewResultAvailableEvent extends BaseEvent {

    public final String message;
    public final boolean isSuccessful;

    //@@author jroberts91-reused
    //Reused from https://github.com/se-edu/addressbook-level4/pull/799/files
    public NewResultAvailableEvent(String message, boolean isSuccessful) {
        this.message = message;
        this.isSuccessful = isSuccessful;
    }
    //@@author

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
