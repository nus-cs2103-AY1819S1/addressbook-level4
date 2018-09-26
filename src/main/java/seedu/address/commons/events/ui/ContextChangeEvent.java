package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a selection change in the Event List Panel
 */
public class ContextChangeEvent extends BaseEvent {


    private final String contextId;

    public ContextChangeEvent(String contextId) {
        this.contextId = contextId;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public String getNewContext() {
        return contextId;
    }
}
