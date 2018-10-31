package seedu.parking.commons.events.ui;

import seedu.parking.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of car parks
 */
public class ListCarparkRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
