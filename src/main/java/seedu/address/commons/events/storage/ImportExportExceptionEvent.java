package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates an exception during a file saving
 */
public class ImportExportExceptionEvent extends BaseEvent {

    public final Exception exception;

    public ImportExportExceptionEvent(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return exception.getMessage();
    }

}
