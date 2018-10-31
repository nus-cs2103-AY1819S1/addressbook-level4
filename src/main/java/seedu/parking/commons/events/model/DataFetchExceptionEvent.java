package seedu.parking.commons.events.model;

import seedu.parking.commons.events.BaseEvent;

/**
 * Indicates an exception during a file saving
 */
public class DataFetchExceptionEvent extends BaseEvent {

    public final Exception exception;

    public DataFetchExceptionEvent(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return exception.toString();
    }

}
