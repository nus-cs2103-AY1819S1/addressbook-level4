package seedu.superta.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.superta.commons.exceptions.IllegalValueException;
import seedu.superta.model.assignment.Title;

/**
 * Xml Adapted Title
 */
public class XmlAdaptedTitle implements XmlAdapted<Title> {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Title's %s field is missing!";

    @XmlElement
    private String assignmentTitle;

    public XmlAdaptedTitle() {

    }

    public XmlAdaptedTitle(Title assignmentTitle) {
        this.assignmentTitle = assignmentTitle.assignmentTitle;
    }

    @Override
    public Title toModelType() throws IllegalValueException {
        if (assignmentTitle == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "title"));
        }
        return new Title(assignmentTitle);
    }
}
