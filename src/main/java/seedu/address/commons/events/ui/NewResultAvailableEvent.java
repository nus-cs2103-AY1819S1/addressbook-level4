package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that a new result is available.
 */
public class NewResultAvailableEvent extends BaseEvent {

    public final String message;
    public final Boolean isValid;

    public NewResultAvailableEvent(String message, boolean isValid) {
        this.message = message;
        this.isValid = isValid;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
