package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event representing request to show patient list on the list panel.
 */
public class ShowPatientListEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
