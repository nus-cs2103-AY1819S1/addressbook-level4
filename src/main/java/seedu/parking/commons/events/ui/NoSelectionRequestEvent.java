package seedu.parking.commons.events.ui;

import seedu.parking.commons.events.BaseEvent;

/**
 * Indicates a request to deselect the car park
 */
public class NoSelectionRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
