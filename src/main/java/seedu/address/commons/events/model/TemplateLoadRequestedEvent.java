package seedu.address.commons.events.model;

import java.nio.file.Path;

import seedu.address.commons.events.BaseEvent;

/** Indicates there has been a call to load a Template from a file*/
public class TemplateLoadRequestedEvent extends BaseEvent {

    public final Path filePath;

    public TemplateLoadRequestedEvent(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public String toString() {
        return "Request to load template from " + filePath.toString();
    }
}
