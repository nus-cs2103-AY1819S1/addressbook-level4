package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.event.Event;

/**
 * Represents a selection change in the Event List Panel
 */
public class ContextChangeEvent extends BaseEvent {


    private final String contextId;
    private final Event currentEvent;

    public ContextChangeEvent(String contextId) {
        this.contextId = contextId;
        currentEvent = null;
    }

    public ContextChangeEvent(String contextId, Event e) {
        this.contextId = contextId;
        this.currentEvent = e;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public String getNewContext() {
        return contextId;
    }

    public Event getCurrentEvent() {
        return currentEvent;
    }
}
