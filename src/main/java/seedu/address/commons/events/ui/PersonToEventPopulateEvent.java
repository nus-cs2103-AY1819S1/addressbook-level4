package seedu.address.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * Represents a selection change in the Person List Panel
 */
public class PersonToEventPopulateEvent extends BaseEvent {


    private final Person newSelection;
    private ObservableList<Event> model;

    public PersonToEventPopulateEvent(Person newSelection) {
        this.newSelection = newSelection;
    }

    public PersonToEventPopulateEvent(Person newSelection, ObservableList<Event> model) {
        this.newSelection = newSelection;
        this.model = model;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Person getNewSelection() {
        return newSelection;
    }

    public ObservableList<Event> getEventModel() {
        return model;
    }

}
