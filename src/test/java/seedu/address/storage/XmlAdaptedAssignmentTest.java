package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedAssignment.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalAssignments.ASSIGNMENT_B;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.assignment.Deadline;
import seedu.address.model.assignment.Weight;
import seedu.address.model.common.Mark;
import seedu.address.testutil.Assert;

public class XmlAdaptedAssignmentTest {
    private static final String INVALID_ASSIGNMENTNAME = " ";
    private static final String INVALID_DEADLINE = "31/31/0031";
    private static final String INVALID_MAXMARK = "aaa";
    private static final String INVALID_WEIGHT = "bbb";

    private static final String VALID_ASSIGNMENTNAME = ASSIGNMENT_B.getName().toString();
    private static final String VALID_DEADLINE = ASSIGNMENT_B.getDeadline().toString();
    private static final String VALID_MAXMARK = ASSIGNMENT_B.getMaxMark().toString();
    private static final String VALID_WEIGHT = ASSIGNMENT_B.getWeight().toString();
    private static final String VALID_UNIQUEID = ASSIGNMENT_B.getUniqueId();

    @Test
    public void toModelType_validAssignmentDetails_returnsAssignment() throws Exception {
        XmlAdaptedAssignment assignment = new XmlAdaptedAssignment(ASSIGNMENT_B);
        assertEquals(ASSIGNMENT_B, assignment.toModelType());
    }

    @Test
    public void toModelType_invalidAssignmentName_throwsIllegalValueException() {
        XmlAdaptedAssignment assignment = new XmlAdaptedAssignment(INVALID_ASSIGNMENTNAME, VALID_DEADLINE, VALID_WEIGHT,
                VALID_MAXMARK, VALID_UNIQUEID);
        String expectedMessage = AssignmentName.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }

    @Test
    public void toModelType_nullAssignmentName_throwsIllegalValueException() {
        XmlAdaptedAssignment assignment = new XmlAdaptedAssignment(null, VALID_DEADLINE, VALID_WEIGHT, VALID_MAXMARK,
                VALID_UNIQUEID);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, AssignmentName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }

    @Test
    public void toModelType_invalidDeadline_throwsIllegalValueException() {
        XmlAdaptedAssignment assignment = new XmlAdaptedAssignment(VALID_ASSIGNMENTNAME, INVALID_DEADLINE, VALID_WEIGHT,
                VALID_MAXMARK, VALID_UNIQUEID);
        String expectedMessage = Deadline.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }

    @Test
    public void toModelType_nullDeadline_throwsIllegalValueException() {
        XmlAdaptedAssignment assignment = new XmlAdaptedAssignment(VALID_ASSIGNMENTNAME, null, VALID_WEIGHT,
                VALID_MAXMARK, VALID_UNIQUEID);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Deadline.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }

    @Test
    public void toModelType_invalidMark_throwsIllegalValueException() {
        XmlAdaptedAssignment assignment = new XmlAdaptedAssignment(VALID_ASSIGNMENTNAME, VALID_DEADLINE, VALID_WEIGHT,
                INVALID_MAXMARK, VALID_UNIQUEID);
        String expectedMessage = Mark.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }

    @Test
    public void toModelType_nullMark_throwsIllegalValueException() {
        XmlAdaptedAssignment assignment = new XmlAdaptedAssignment(VALID_ASSIGNMENTNAME, VALID_DEADLINE, VALID_WEIGHT,
                null, VALID_UNIQUEID);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Mark.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }

    @Test
    public void toModelType_invalidWeight_throwsIllegalValueException() {
        XmlAdaptedAssignment assignment = new XmlAdaptedAssignment(VALID_ASSIGNMENTNAME, VALID_DEADLINE, INVALID_WEIGHT,
                VALID_MAXMARK, VALID_UNIQUEID);
        String expectedMessage = Weight.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }

    @Test
    public void toModelType_nullWeight_throwsIllegalValueException() {
        XmlAdaptedAssignment assignment = new XmlAdaptedAssignment(VALID_ASSIGNMENTNAME, VALID_DEADLINE, null,
                VALID_MAXMARK, VALID_UNIQUEID);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Weight.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, assignment::toModelType);
    }
}
