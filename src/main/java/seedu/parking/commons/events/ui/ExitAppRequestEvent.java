package seedu.parking.commons.events.ui;

import seedu.parking.commons.events.BaseEvent;

/**
 * Indicates a request for App termination
 */
public class ExitAppRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
