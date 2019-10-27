package seedu.address.commons.events.storage;

import java.nio.file.Path;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates an exception during loading of a template
 */
public class TemplateLoadingExceptionEvent extends BaseEvent {

    public final Exception exception;
    public final Path filePath;

    public TemplateLoadingExceptionEvent(Exception exception, Path filePath) {
        this.exception = exception;
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "Exception while attempting to load " + filePath + ":\n" + exception.toString();
    }
}
