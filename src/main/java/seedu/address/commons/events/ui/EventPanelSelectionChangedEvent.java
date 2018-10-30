package seedu.address.commons.events.ui;

import java.util.List;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.event.Event;

/**
 * Represents a selection change in the Event List Panel under Tab Panel
 */
public class EventPanelSelectionChangedEvent extends BaseEvent {

    private final List<Event> newSelection;

    public EventPanelSelectionChangedEvent(List<Event> newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public List<Event> getNewSelection() {
        return newSelection;
    }
}
