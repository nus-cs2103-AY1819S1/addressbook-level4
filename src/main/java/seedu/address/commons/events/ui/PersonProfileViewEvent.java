package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Person;

//@@author javenseow
/**
 * Indicates a request to view a person's profile.
 */
public class PersonProfileViewEvent extends BaseEvent{
    private final Person personSelected;

    public PersonProfileViewEvent(Person personSelected) {
        this.personSelected = personSelected;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Person getPersonSelected() {
        return personSelected;
    }
}
