package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.event.Event;

/**
 * Indicates a request to jump to the list of volunteers
 */
public class RecordChangeEvent extends BaseEvent {

    private final Event currentEvent;

    public RecordChangeEvent(Event newSelection) {
        this.currentEvent = newSelection;
    }

    public Event getCurrentEvent() {
        return currentEvent;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
