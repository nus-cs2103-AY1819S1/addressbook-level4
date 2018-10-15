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
    private final List<Person> volunteerList;

    public RecordChangeEvent(Event newSelection, List<Person> volunteerList) {
        this.currentEvent = newSelection;
        this.volunteerList = volunteerList;
    }

    public Event getCurrentEvent() {
        return currentEvent;
    }

    public List<Person> getVolunteerList() {
        return volunteerList;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
