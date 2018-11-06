package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Person;

/**
 * Represents a change in the Faculty location of a Person being displayed in the Location Display panel.
 */
public class FacultyLocationDisplayChangedEvent extends BaseEvent {

    private final Person selectedPerson;

    public FacultyLocationDisplayChangedEvent(Person newSelection) {
        this.selectedPerson = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Person getSelectedPerson() {
        return selectedPerson;
    }

}
