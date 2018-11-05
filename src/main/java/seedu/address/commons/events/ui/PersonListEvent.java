package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that the person list command has been run
 */
public class PersonListEvent extends BaseEvent {
    public PersonListEvent() {
    }

    @Override
    public String toString() {
        return "list";
    }
}
