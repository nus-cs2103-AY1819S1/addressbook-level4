package seedu.superta.storage;

import javax.xml.bind.annotation.XmlElement;

import seedu.superta.commons.exceptions.IllegalValueException;
import seedu.superta.model.assignment.Assignment;

// @@author Caephler
/**
 * Class for XML-Adapted Assignment
 */
public class XmlAdaptedAssignment implements XmlAdapted<Assignment> {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Assignment's %s field is missing!";
    @XmlElement(required = true)
    private XmlAdaptedTitle title;
    @XmlElement(required = true)
    private Double maxMarks;
    @XmlElement
    private XmlAdaptedGradeBook gradebook;

    public XmlAdaptedAssignment() { }

    public XmlAdaptedAssignment(XmlAdaptedTitle title, Double maxMarks, XmlAdaptedGradeBook gradebook) {
        this.title = title;
        this.maxMarks = maxMarks;
        this.gradebook = gradebook;
    }

    public XmlAdaptedAssignment(Assignment source) {
        title = new XmlAdaptedTitle(source.getTitle());
        maxMarks = source.getMaxMarks();
        gradebook = new XmlAdaptedGradeBook(source.getGradebook());
    }

    @Override
    public Assignment toModelType() throws IllegalValueException {
        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "title"));
        }
        if (maxMarks == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "ID"));
        }

        if (gradebook == null) {
            return new Assignment(title.toModelType(), maxMarks);
        }
        return new Assignment(title.toModelType(), maxMarks, gradebook.toModelType());
    }
}
