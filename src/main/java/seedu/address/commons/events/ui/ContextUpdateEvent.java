package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that a new context related update is available.
 */
public class ContextUpdateEvent extends BaseEvent {

    public final String message;

    public ContextUpdateEvent(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
