package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.leaveapplication.Description;
import seedu.address.model.person.Name;
import seedu.address.model.project.Assignment;
import seedu.address.model.project.ProjectName;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedAssignment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Assignment's %s field is missing!";

    @XmlElement(required = true)
    private String assignmentName;
    @XmlElement(required = true)
    private String author;
    @XmlElement(required = true)
    private String description;

    /**
     * Constructs an XmlAdaptedAssignment.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAssignment() {
    }

    /**
     * Constructs an {@code XmlAdaptedAssignment} with the given assignment details.
     */
    public XmlAdaptedAssignment(String assignmentName, String author, String description) {
        this.assignmentName = assignmentName;
        this.author = author;
        this.description = description;
    }

    /**
     * Converts a given Assignment into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedAssignment
     */
    public XmlAdaptedAssignment(Assignment source) {
        assignmentName = source.getProjectName().fullProjectName;
        author = source.getAuthor().fullName;
        description = source.getDescription().value;
    }

    /**
     * Converts this jaxb-friendly adapted assignment object into the model's Assignment object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted assignment
     */
    public Assignment toModelType() throws IllegalValueException {
        if (assignmentName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ProjectName.class.getSimpleName()));
        }
        if (!ProjectName.isValidName(assignmentName)) {
            throw new IllegalValueException(ProjectName.MESSAGE_PROJECT_NAME_CONSTRAINTS);
        }
        final ProjectName modelAssignmentName = new ProjectName(assignmentName);

        if (author == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Name.class.getSimpleName()));
        }
        if (!Name.isValidName(author)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(author);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_DESCRIPTION_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        return new Assignment(modelAssignmentName, modelName, modelDescription);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedAssignment)) {
            return false;
        }

        XmlAdaptedAssignment otherAssignment = (XmlAdaptedAssignment) other;
        return Objects.equals(assignmentName, otherAssignment.assignmentName)
                && Objects.equals(author, otherAssignment.author)
                && Objects.equals(description, otherAssignment.description);
    }
}
