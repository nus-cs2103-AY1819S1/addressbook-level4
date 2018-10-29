package seedu.clinicio.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static seedu.clinicio.storage.XmlAdaptedStaff.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.clinicio.testutil.TypicalPersons.BEN;

import org.junit.jupiter.api.Test;

import seedu.clinicio.commons.exceptions.IllegalValueException;

import seedu.clinicio.model.staff.Password;
import seedu.clinicio.model.person.Name;

import seedu.clinicio.testutil.Assert;


//@@author jjlee050
public class XmlAdapterStaffTest {

    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PASSWORD = "";


    private static final String VALID_NAME = BEN.getName().toString();
    private static final String VALID_PASSWORD = BEN.getPassword().toString();

    @Test
    public void toModelType_validDoctorDetails_returnsDoctor() throws Exception {
        XmlAdaptedStaff doctor = new XmlAdaptedStaff(BEN);
        assertEquals(BEN, doctor.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedStaff doctor =
                new XmlAdaptedStaff(INVALID_NAME, VALID_PASSWORD);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, doctor::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedStaff doctor = new XmlAdaptedStaff(null, VALID_PASSWORD);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, doctor::toModelType);
    }

    @Test
    public void toModelType_invalidPassword_throwsIllegalValueException() {
        XmlAdaptedStaff doctor =
                new XmlAdaptedStaff(VALID_NAME, INVALID_PASSWORD);
        String expectedMessage = Password.MESSAGE_PASSWORD_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, doctor::toModelType);
    }

    @Test
    public void toModelType_nullPassword_throwsIllegalValueException() {
        XmlAdaptedStaff doctor = new XmlAdaptedStaff(VALID_NAME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Password.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, doctor::toModelType);
    }

}
