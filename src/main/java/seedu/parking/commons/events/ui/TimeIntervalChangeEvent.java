package seedu.parking.commons.events.ui;

import seedu.parking.commons.events.BaseEvent;

/**
 * Sets the time interval for notify command.
 */
public class TimeIntervalChangeEvent extends BaseEvent {

    public final int value;

    public TimeIntervalChangeEvent(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}