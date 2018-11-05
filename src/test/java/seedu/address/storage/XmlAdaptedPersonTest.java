package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.storage.XmlAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Password;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfilePic;
import seedu.address.model.person.Salary;
import seedu.address.model.person.Username;
import seedu.address.testutil.Assert;

public class XmlAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_SALARY = " ";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_USERNAME = " hi";
    private static final String INVALID_PERMISSION = "INVALID_PERMISSION";
    private static final String INVALID_PROFILE_PIC = " ";
    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_SALARY = BENSON.getSalary().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_USERNAME = BENSON.getUsername().toString();
    private static final XmlAdaptedPassword VALID_PASSWORD = new XmlAdaptedPassword(BENSON.getPassword());
    private static final List<XmlAdaptedProject> VALID_PROJECTS = BENSON.getProjects().stream()
            .map(XmlAdaptedProject::new)
            .collect(Collectors.toList());
    private static final List<XmlAdaptedPermission> VALID_PERMISSION = BENSON.getPermissionSet()
            .getGrantedPermission().stream()
            .map(XmlAdaptedPermission::new)
            .collect(Collectors.toList());
    private static final List<XmlAdaptedLeaveApplication> VALID_LEAVE_APPLICATIONS = BENSON
            .getLeaveApplications().stream()
            .map(XmlAdaptedLeaveApplication::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        XmlAdaptedPerson person = new XmlAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL,
                        VALID_ADDRESS, VALID_SALARY, VALID_USERNAME, VALID_PASSWORD, VALID_PROJECTS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(null, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_SALARY, VALID_USERNAME, VALID_PASSWORD, VALID_PROJECTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_SALARY, VALID_USERNAME, VALID_PASSWORD, VALID_PROJECTS);
        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, null, VALID_EMAIL,
                VALID_ADDRESS, VALID_SALARY, VALID_USERNAME, VALID_PASSWORD, VALID_PROJECTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS,
                        VALID_SALARY, VALID_USERNAME, VALID_PASSWORD, VALID_PROJECTS);
        String expectedMessage = Email.MESSAGE_EMAIL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, null,
                VALID_ADDRESS, VALID_SALARY, VALID_USERNAME, VALID_PASSWORD, VALID_PROJECTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS,
                        VALID_SALARY, VALID_USERNAME, VALID_PASSWORD, VALID_PROJECTS);
        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                null, VALID_SALARY, VALID_USERNAME, VALID_PASSWORD, VALID_PROJECTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidSalary_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        INVALID_SALARY, VALID_USERNAME, VALID_PASSWORD, VALID_PROJECTS);
        String expectedMessage = Salary.SALARY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullSalary_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, null, VALID_USERNAME, VALID_PASSWORD, VALID_PROJECTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Salary.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidProfilePic_throwsIllegalValueException() {
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_SALARY, VALID_USERNAME, VALID_PASSWORD, VALID_PROJECTS, INVALID_PROFILE_PIC,
                        VALID_PERMISSION, VALID_LEAVE_APPLICATIONS);
        String expectedMessage = ProfilePic.MESSAGE_PROFILEPIC_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidUsername_throwsIllegalValueException() {
        XmlAdaptedPerson person =
            new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_SALARY, INVALID_USERNAME, VALID_PASSWORD, VALID_PROJECTS);
        String expectedMessage = Username.MESSAGE_USERNAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullUsername_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
            VALID_ADDRESS, VALID_SALARY, null, VALID_PASSWORD, VALID_PROJECTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Username.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPassword_throwsIllegalValueException() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL,
            VALID_ADDRESS, VALID_SALARY, VALID_USERNAME, null, VALID_PROJECTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Password.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPermission_throwsIllegalValueException() {
        List<XmlAdaptedPermission> invalidPermission = new ArrayList<>(VALID_PERMISSION);
        invalidPermission.add(new XmlAdaptedPermission(INVALID_PERMISSION));
        XmlAdaptedPerson person =
                new XmlAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_SALARY, VALID_USERNAME,
                        VALID_PASSWORD, VALID_PROJECTS, invalidPermission);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void equals() {
        XmlAdaptedPerson person = new XmlAdaptedPerson(BENSON);
        // same object -> returns true
        assertTrue(person.equals(person));

        // same values -> returns true
        XmlAdaptedPerson duplicatePerson = new XmlAdaptedPerson(BENSON);
        assertTrue(person.equals(duplicatePerson));

        // different types -> returns false
        assertFalse(person.equals(1));

        // null -> returns false
        assertFalse(person.equals(null));

        // different person -> returns false
        XmlAdaptedPerson newPerson = new XmlAdaptedPerson(ALICE);
        assertFalse(person.equals(newPerson));
    }

}
