package seedu.address.testutil;

import seedu.address.model.UserParticulars;
import seedu.address.model.template.Template;

/**
 * An extension of the ModelManager for testing purposes. Returns the given UserParticulars
 * to bypass the usual requirement for UserParticulars to be loaded with UserPrefs through storage.
 */
public class UserParticularsModel extends TypicalResumeModel {
    private UserParticulars testParticulars;

    public UserParticularsModel(UserParticulars particulars) {
        super(Template.getDefaultTemplate());
        testParticulars = particulars;
    }

    @Override
    public UserParticulars getUserParticulars() {
        return testParticulars;
    }
}
