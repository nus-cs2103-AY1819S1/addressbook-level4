package seedu.superta.storage;

import static junit.framework.TestCase.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.superta.commons.exceptions.IllegalValueException;
import seedu.superta.model.assignment.Title;

public class XmlAdaptedTitleTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_success() throws IllegalValueException {
        Title source = new Title("Test");
        XmlAdaptedTitle xmlAdaptedTitle = new XmlAdaptedTitle(source);
        assertTrue(xmlAdaptedTitle.toModelType().equals(source));
    }

    @Test
    public void toModelType_failure() throws IllegalValueException {
        XmlAdaptedTitle xmlAdaptedTitle = new XmlAdaptedTitle();
        thrown.expect(IllegalValueException.class);
        xmlAdaptedTitle.toModelType();
    }
}
