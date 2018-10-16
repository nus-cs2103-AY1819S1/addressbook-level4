package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

/** Indicates there has been a call to load a Template from a file*/
public class TemplateLoadRequestedEvent extends BaseEvent {

    public final String filepath;

    public TemplateLoadRequestedEvent(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public String toString() {
        return "Request to load template from " + filepath;
    }
}
