package seedu.parking.commons.events.storage;

import seedu.parking.commons.events.BaseEvent;

/**
 * Indicates an exception during a file saving
 */
public class DataSavingExceptionEvent extends BaseEvent {

    public final Exception exception;

    public DataSavingExceptionEvent(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return exception.toString();
    }

}
