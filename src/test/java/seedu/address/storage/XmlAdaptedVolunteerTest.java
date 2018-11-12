package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedVolunteer.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalVolunteers.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.volunteer.Address;
import seedu.address.model.volunteer.Birthday;
import seedu.address.model.volunteer.Email;
import seedu.address.model.volunteer.Gender;
import seedu.address.model.volunteer.Name;
import seedu.address.model.volunteer.Phone;
import seedu.address.model.volunteer.VolunteerId;
import seedu.address.testutil.Assert;

public class XmlAdaptedVolunteerTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_VOLUNTEERID = "Z99999999";
    private static final String INVALID_GENDER = "unknown";
    private static final String INVALID_BIRTHDAY = "44-44-4444";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_VOLUNTEERID = BENSON.getVolunteerId().id;
    private static final String VALID_GENDER = BENSON.getGender().toString();
    private static final String VALID_BIRTHDAY = BENSON.getBirthday().toString();
    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validVolunteerDetails_returnsVolunteer() throws Exception {
        XmlAdaptedVolunteer volunteer = new XmlAdaptedVolunteer(BENSON);
        assertEquals(BENSON, volunteer.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedVolunteer volunteer =
                new XmlAdaptedVolunteer(INVALID_NAME,
                        VALID_VOLUNTEERID, VALID_GENDER, VALID_BIRTHDAY, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                        VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, volunteer::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedVolunteer volunteer = new XmlAdaptedVolunteer(null, VALID_VOLUNTEERID,
                VALID_GENDER, VALID_BIRTHDAY, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, volunteer::toModelType);
    }


    @Test
    public void toModelType_invalidId_throwsIllegalValueException() {
        XmlAdaptedVolunteer volunteer =
                new XmlAdaptedVolunteer(VALID_NAME, INVALID_VOLUNTEERID, VALID_GENDER,
                        VALID_BIRTHDAY, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = VolunteerId.MESSAGE_ID_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, volunteer::toModelType);
    }

    @Test
    public void toModelType_nullId_throwsIllegalValueException() {
        XmlAdaptedVolunteer volunteer = new XmlAdaptedVolunteer(VALID_NAME,
                null, VALID_GENDER, VALID_BIRTHDAY, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, VolunteerId.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, volunteer::toModelType);
    }


    @Test
    public void toModelType_invalidGender_throwsIllegalValueException() {
        XmlAdaptedVolunteer volunteer =
                new XmlAdaptedVolunteer(VALID_NAME, VALID_VOLUNTEERID, INVALID_GENDER, VALID_BIRTHDAY,
                        VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Gender.MESSAGE_GENDER_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, volunteer::toModelType);
    }

    @Test
    public void toModelType_nullGender_throwsIllegalValueException() {
        XmlAdaptedVolunteer volunteer = new XmlAdaptedVolunteer(VALID_NAME, VALID_VOLUNTEERID, null,
                VALID_BIRTHDAY, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Gender.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, volunteer::toModelType);
    }

    @Test
    public void toModelType_invalidBirthday_throwsIllegalValueException() {
        XmlAdaptedVolunteer volunteer =
                new XmlAdaptedVolunteer(VALID_NAME, VALID_VOLUNTEERID, VALID_GENDER, INVALID_BIRTHDAY,
                        VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, volunteer::toModelType);
    }

    @Test
    public void toModelType_nullBirthday_throwsIllegalValueException() {
        XmlAdaptedVolunteer volunteer = new XmlAdaptedVolunteer(VALID_NAME, VALID_VOLUNTEERID, VALID_GENDER,
                null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Birthday.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, volunteer::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        XmlAdaptedVolunteer volunteer =
                new XmlAdaptedVolunteer(VALID_NAME, VALID_VOLUNTEERID, VALID_GENDER, VALID_BIRTHDAY,
                        INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_PHONE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, volunteer::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        XmlAdaptedVolunteer volunteer = new XmlAdaptedVolunteer(VALID_NAME, VALID_VOLUNTEERID, VALID_GENDER,
                VALID_BIRTHDAY, null, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, volunteer::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        XmlAdaptedVolunteer volunteer =
                new XmlAdaptedVolunteer(VALID_NAME, VALID_VOLUNTEERID, VALID_GENDER, VALID_BIRTHDAY, VALID_PHONE,
                        INVALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_EMAIL_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, volunteer::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        XmlAdaptedVolunteer volunteer = new XmlAdaptedVolunteer(VALID_NAME, VALID_VOLUNTEERID, VALID_GENDER,
                VALID_BIRTHDAY, VALID_PHONE, null, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, volunteer::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        XmlAdaptedVolunteer volunteer =
                new XmlAdaptedVolunteer(VALID_NAME, VALID_VOLUNTEERID, VALID_GENDER, VALID_BIRTHDAY, VALID_PHONE,
                        VALID_EMAIL, INVALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_ADDRESS_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, volunteer::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        XmlAdaptedVolunteer volunteer = new XmlAdaptedVolunteer(VALID_NAME, VALID_VOLUNTEERID, VALID_GENDER,
                VALID_BIRTHDAY, VALID_PHONE, VALID_EMAIL, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, volunteer::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedVolunteer volunteer =
                new XmlAdaptedVolunteer(VALID_NAME, VALID_VOLUNTEERID, VALID_GENDER, VALID_BIRTHDAY, VALID_PHONE,
                        VALID_EMAIL, VALID_ADDRESS, invalidTags);
        Assert.assertThrows(IllegalValueException.class, volunteer::toModelType);
    }

}
