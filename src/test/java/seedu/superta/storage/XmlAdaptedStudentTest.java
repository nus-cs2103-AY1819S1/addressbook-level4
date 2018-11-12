package seedu.superta.storage;

import static org.junit.Assert.assertEquals;
import static seedu.superta.storage.XmlAdaptedStudent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.superta.testutil.TypicalSuperTaClient.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.superta.commons.exceptions.IllegalValueException;
import seedu.superta.model.student.Email;
import seedu.superta.model.student.Name;
import seedu.superta.model.student.Phone;
import seedu.superta.model.student.StudentId;
import seedu.superta.testutil.Assert;

public class XmlAdaptedStudentTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_STUDENT_ID = BENSON.getStudentId().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        XmlAdaptedStudent person = new XmlAdaptedStudent(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedStudent person =
                new XmlAdaptedStudent(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_STUDENT_ID,
                                     VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedStudent person = new XmlAdaptedStudent(null, VALID_PHONE, VALID_EMAIL,
                                                       VALID_STUDENT_ID,
                                                       VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedStudent person =
                new XmlAdaptedStudent(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_STUDENT_ID,
                                     VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedStudent person = new XmlAdaptedStudent(VALID_NAME, null, VALID_EMAIL,
                                                       VALID_STUDENT_ID,
                                                       VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        XmlAdaptedStudent person =
                new XmlAdaptedStudent(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_STUDENT_ID, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_EMAIL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        XmlAdaptedStudent person = new XmlAdaptedStudent(VALID_NAME, VALID_PHONE, null,
                                                       VALID_STUDENT_ID,
                                                       VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullStudentId_throwsIllegalValueException() {
        XmlAdaptedStudent person = new XmlAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                                                       VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, StudentId.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedStudent person =
                new XmlAdaptedStudent(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_STUDENT_ID,
                                     invalidTags);
        Assert.assertThrows(IllegalValueException.class, person::toModelType);
    }

}
