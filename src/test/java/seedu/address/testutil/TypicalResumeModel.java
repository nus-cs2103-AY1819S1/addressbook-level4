package seedu.address.testutil;

import java.util.Optional;

import seedu.address.model.ModelManager;
import seedu.address.model.template.Template;

/**
 * An extension of the ModelManager for testing purposes. Returns the given template
 * to bypass the usual requirement for templates to be loaded through storage.
 */
public class TypicalResumeModel extends ModelManager {
    private Template testTemplate;
    public TypicalResumeModel(Template template) {
        super();
        testTemplate = template;
    }

    @Override
    public Optional<Template> getLoadedTemplate() {
        return Optional.of(testTemplate);
    }

    public static TypicalResumeModel getDefaultTemplateModel() {
        return new TypicalResumeModel(Template.getDefaultTemplate());
    }
}
