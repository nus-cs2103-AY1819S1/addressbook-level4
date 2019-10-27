package seedu.address.commons.events.storage;

import java.nio.file.Path;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.template.Template;

/**
 * Indicates that a template has been successfully loaded
 */
public class TemplateLoadedEvent extends BaseEvent {

    public final Template template;
    public final Path filePath;

    public TemplateLoadedEvent(Template template, Path filePath) {
        this.template = template;
        this.filePath = filePath;
    }

    public Template getTemplate() {
        return template;
    }

    @Override
    public String toString() {
        return "Successfully loaded template from " + filePath.toString();
    }
}
