package seedu.address.commons.events.ui;

import java.util.List;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * Indicates a request to jump to the list of persons
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
