package seedu.inventory.storage;

import static org.junit.Assert.assertEquals;
import static seedu.inventory.storage.XmlAdaptedStaff.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.inventory.testutil.TypicalStaffs.TENGXIONG;

import org.junit.Test;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.model.staff.Password;
import seedu.inventory.model.staff.Staff;
import seedu.inventory.model.staff.StaffName;
import seedu.inventory.model.staff.Username;
import seedu.inventory.testutil.Assert;

public class XmlAdaptedStaffTest {
    private static final String INVALID_USERNAME = "y@0-tx";
    private static final String INVALID_PASSWORD = "..asdas..";
    private static final String INVALID_NAME = "@asdvd daniel";

    private static final String VALID_USERNAME = TENGXIONG.getUsername().toString();
    private static final String VALID_PASSWORD = TENGXIONG.getPassword().toString();
    private static final String VALID_NAME = TENGXIONG.getStaffName().toString();

    @Test
    public void toModelType_validStaffDetails_returnsStaff() throws Exception {
        XmlAdaptedStaff staff = new XmlAdaptedStaff(TENGXIONG);
        assertEquals(TENGXIONG, staff.toModelType());
    }

    @Test
    public void toModelType_invalidUsername_throwsIllegalValueException() {
        XmlAdaptedStaff staff = new XmlAdaptedStaff(INVALID_USERNAME, VALID_PASSWORD, VALID_NAME, Staff.Role.user);
        String expectedMessage = Username.MESSAGE_USERNAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, staff::toModelType);
    }

    @Test
    public void toModelType_nullUsername_throwsIllegaleValueException() {
        XmlAdaptedStaff staff = new XmlAdaptedStaff(null, VALID_PASSWORD, VALID_NAME, Staff.Role.user);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Username.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, staff::toModelType);
    }

    @Test
    public void toModelType_invalidPassword_throwsIllegalValueException() {
        XmlAdaptedStaff staff = new XmlAdaptedStaff(VALID_USERNAME, INVALID_PASSWORD, VALID_NAME, Staff.Role.user);
        String expectedMessage = Password.MESSAGE_PASSWORD_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, staff::toModelType);
    }

    @Test
    public void toModelType_nullPassword_throwsIllegaleValueException() {
        XmlAdaptedStaff staff = new XmlAdaptedStaff(VALID_USERNAME, null, VALID_NAME, Staff.Role.user);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Password.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, staff::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedStaff staff = new XmlAdaptedStaff(VALID_USERNAME, VALID_PASSWORD, INVALID_NAME, Staff.Role.user);
        String expectedMessage = Username.MESSAGE_USERNAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, staff::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegaleValueException() {
        XmlAdaptedStaff staff = new XmlAdaptedStaff(VALID_USERNAME, VALID_PASSWORD, null, Staff.Role.user);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StaffName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, staff::toModelType);
    }

    @Test
    public void toModelType_nullRole_throwsIllegaleValueException() {
        XmlAdaptedStaff staff = new XmlAdaptedStaff(VALID_USERNAME, VALID_PASSWORD, VALID_NAME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Staff.Role.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, staff::toModelType);
    }
}
