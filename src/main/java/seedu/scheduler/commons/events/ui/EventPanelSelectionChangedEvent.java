package seedu.scheduler.commons.events.ui;

import seedu.scheduler.commons.events.BaseEvent;
import seedu.scheduler.model.event.Event;

/**
 * Represents a selection change in the Event List Panel
 */
public class EventPanelSelectionChangedEvent extends BaseEvent {


    private final Event newSelection;

    public EventPanelSelectionChangedEvent(Event newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Event getNewSelection() {
        return newSelection;
    }
}
