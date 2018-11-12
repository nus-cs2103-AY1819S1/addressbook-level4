package seedu.modsuni.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.modsuni.storage.XmlAdaptedCredential.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.modsuni.testutil.TypicalCredentials.CREDENTIAL_STUDENT_MAX;
import static seedu.modsuni.testutil.TypicalCredentials.CREDENTIAL_STUDENT_SEB;

import org.junit.Test;

import seedu.modsuni.commons.exceptions.IllegalValueException;
import seedu.modsuni.model.credential.Password;
import seedu.modsuni.model.credential.Username;
import seedu.modsuni.testutil.Assert;

public class XmlAdaptedCredentialTest {
    private static final String INVALID_USERNAME = "R@chel";

    private static final String VALID_USERNAME =
        CREDENTIAL_STUDENT_SEB.getUsername().toString();
    private static final String VALID_PASSWORD =
        CREDENTIAL_STUDENT_SEB.getPassword().toString();


    @Test
    public void toModelType_validCredentialDetails_returnsCredential() throws Exception {
        XmlAdaptedCredential credential = new XmlAdaptedCredential(CREDENTIAL_STUDENT_SEB);
        assertEquals(CREDENTIAL_STUDENT_SEB, credential.toModelType());
    }

    @Test
    public void toModelType_invalidUsername_throwsIllegalValueException() {
        XmlAdaptedCredential credential =
                new XmlAdaptedCredential(INVALID_USERNAME, VALID_PASSWORD);
        String expectedMessage = Username.MESSAGE_USERNAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage,
            credential::toModelType);
    }

    @Test
    public void toModelType_nullUsername_throwsIllegalValueException() {
        XmlAdaptedCredential credential = new XmlAdaptedCredential(
            null,
            VALID_PASSWORD);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
            Username.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage,
            credential::toModelType);
    }

    @Test
    public void toModelType_nullPassword_throwsIllegalValueException() {
        XmlAdaptedCredential credential = new XmlAdaptedCredential(VALID_USERNAME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
            Password.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage,
            credential::toModelType);
    }

    @Test
    public void equals() {
        XmlAdaptedCredential maxCredential =
            new XmlAdaptedCredential(CREDENTIAL_STUDENT_MAX);

        // same object -> returns true
        assertEquals(maxCredential, maxCredential);

        // same values -> returns true
        XmlAdaptedCredential copyMaxCredential =
            new XmlAdaptedCredential(
                CREDENTIAL_STUDENT_MAX.getUsername().getUsername(),
                CREDENTIAL_STUDENT_MAX.getPassword().getValue()
            );
        assertEquals(maxCredential, copyMaxCredential);

        // different types -> returns false
        assertFalse(maxCredential.equals(5));

        // null -> returns false
        assertFalse(maxCredential.equals(null));

        // different credential -> returns false
        assertFalse(maxCredential.equals(new XmlAdaptedCredential(CREDENTIAL_STUDENT_SEB)));
    }
}
