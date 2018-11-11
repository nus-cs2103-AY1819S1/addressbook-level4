package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.storage.XmlAdaptedAssignment.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalAssignment.FALCON;
import static seedu.address.testutil.TypicalAssignment.OASIS;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.leaveapplication.Description;
import seedu.address.model.person.Name;
import seedu.address.model.project.ProjectName;
import seedu.address.testutil.Assert;

public class XmlAdaptedAssignmentTest {

    private static final String INVALID_ASSIGNMENT_NAME = "O@S!S";
    private static final String INVALID_AUTHOR = "r@Ch";
    private static final String INVALID_DESCRIPTION = " ";
    private static final String VALID_ASSIGNMENT_NAME = OASIS.getProjectName().fullProjectName;
    private static final String VALID_AUTHOR = OASIS.getAuthor().fullName;
    private static final String VALID_DESCRIPTION = OASIS.getDescription().value;

    @Test
    public void toModelType_validAssignmentDetails_returnsAssignment() throws Exception {
        XmlAdaptedAssignment assignment = new XmlAdaptedAssignment(OASIS);
        assertEquals(OASIS, assignment.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedAssignment assignment =
                new XmlAdaptedAssignment(INVALID_ASSIGNMENT_NAME, VALID_AUTHOR, VALID_DESCRIPTION);
        String expectedMessage = ProjectName.MESSAGE_PROJECT_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedAssignment assignment = new XmlAdaptedAssignment(null, VALID_AUTHOR, VALID_DESCRIPTION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ProjectName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }

    @Test
    public void toModelType_invalidAuthor_throwsIllegalValueException() {
        XmlAdaptedAssignment assignment =
                new XmlAdaptedAssignment(VALID_ASSIGNMENT_NAME, INVALID_AUTHOR, VALID_DESCRIPTION);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }

    @Test
    public void toModelType_nullAuthor_throwsIllegalValueException() {
        XmlAdaptedAssignment assignment =
                new XmlAdaptedAssignment(VALID_ASSIGNMENT_NAME, null, VALID_DESCRIPTION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        XmlAdaptedAssignment assignment =
                new XmlAdaptedAssignment(VALID_ASSIGNMENT_NAME, VALID_AUTHOR, INVALID_DESCRIPTION);
        String expectedMessage = Description.MESSAGE_DESCRIPTION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        XmlAdaptedAssignment assignment = new XmlAdaptedAssignment(VALID_ASSIGNMENT_NAME, VALID_AUTHOR, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }

    @Test
    public void equals() {
        XmlAdaptedAssignment assignment = new XmlAdaptedAssignment(OASIS);
        // same object -> returns true
        assertTrue(assignment.equals(assignment));

        // same values -> returns true
        XmlAdaptedAssignment duplicateAssignment = new XmlAdaptedAssignment(OASIS);
        assertTrue(assignment.equals(duplicateAssignment));

        // different types -> returns false
        assertFalse(assignment.equals(1));

        // null -> returns false
        assertFalse(assignment.equals(null));

        // different assignment -> returns false
        XmlAdaptedAssignment newAssignment = new XmlAdaptedAssignment(FALCON);
        assertFalse(assignment.equals(newAssignment));
    }
}
