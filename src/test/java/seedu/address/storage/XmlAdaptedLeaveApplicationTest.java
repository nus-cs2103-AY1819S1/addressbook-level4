package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.storage.XmlAdaptedLeaveApplication.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalLeaveApplications.ALICE_LEAVE;
import static seedu.address.testutil.TypicalLeaveApplications.BENSON_LEAVE;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.leaveapplication.Description;
import seedu.address.model.leaveapplication.LeaveStatus;
import seedu.address.testutil.Assert;

public class XmlAdaptedLeaveApplicationTest {
    private static final Integer INVALID_ID = -1;
    private static final String INVALID_DESCRIPTION = "    ";
    private static final String INVALID_STATUS = "RANDOMTEXT";
    private static final String VALID_DESCRIPTION = BENSON_LEAVE.getDescription().toString();
    private static final String VALID_STATUS = BENSON_LEAVE.getLeaveStatus().toString();

    @Test
    public void toModelType_validLeaveApplicationDetails_returnsLeaveApplication() throws Exception {
        XmlAdaptedLeaveApplication leaveApplication = new XmlAdaptedLeaveApplication(BENSON_LEAVE);
        assertEquals(BENSON_LEAVE, leaveApplication.toModelType());
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        XmlAdaptedLeaveApplication leaveApplication =
                new XmlAdaptedLeaveApplication(INVALID_DESCRIPTION, VALID_STATUS, new ArrayList<>());
        String expectedMessage = Description.MESSAGE_DESCRIPTION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, leaveApplication::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        XmlAdaptedLeaveApplication leaveApplication =
                new XmlAdaptedLeaveApplication(null, VALID_STATUS, new ArrayList<>());
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, leaveApplication::toModelType);
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        XmlAdaptedLeaveApplication leaveApplication =
                new XmlAdaptedLeaveApplication(VALID_DESCRIPTION, INVALID_STATUS, new ArrayList<>());
        String expectedMessage = LeaveStatus.MESSAGE_STATUS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, leaveApplication::toModelType);
    }

    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        XmlAdaptedLeaveApplication leaveApplication =
                new XmlAdaptedLeaveApplication(VALID_DESCRIPTION, null, new ArrayList<>());
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, LeaveStatus.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, leaveApplication::toModelType);
    }

    @Test
    public void toModelType_nullDates_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, null, ()
            -> new XmlAdaptedLeaveApplication(VALID_DESCRIPTION, VALID_STATUS, null));
    }

    @Test
    public void equals() {
        XmlAdaptedLeaveApplication leaveApplication = new XmlAdaptedLeaveApplication(BENSON_LEAVE);
        // same object -> returns true
        assertTrue(leaveApplication.equals(leaveApplication));

        // same values -> returns true
        XmlAdaptedLeaveApplication duplicateLeaveApplication = new XmlAdaptedLeaveApplication(BENSON_LEAVE);
        assertTrue(leaveApplication.equals(duplicateLeaveApplication));

        // different types -> returns false
        assertFalse(leaveApplication.equals(1));

        // null -> returns false
        assertFalse(leaveApplication.equals(null));

        // different person -> returns false
        XmlAdaptedLeaveApplication newLeaveApplication = new XmlAdaptedLeaveApplication(ALICE_LEAVE);
        assertFalse(leaveApplication.equals(newLeaveApplication));
    }

}
