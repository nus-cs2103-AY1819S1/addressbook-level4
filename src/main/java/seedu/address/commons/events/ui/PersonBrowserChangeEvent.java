package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.person.Person;

/**
 * Represents a change in the Person Browser Panel
 */
public class PersonBrowserChangeEvent extends BaseEvent {

    private final Person currSelection;

    public PersonBrowserChangeEvent(Person currSelection) {
        this.currSelection = currSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Person getCurrSelection() {
        return currSelection;
    }
}
