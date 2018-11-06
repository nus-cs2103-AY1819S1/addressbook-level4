package seedu.address.model;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalEmails.MEETING_EMAIL;

import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

//@@author EatOrBeEaten
public class EmailModelTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final EmailModel emailModel = new EmailModel();

    @Test
    public void constructor() {
        assertEquals(Collections.emptySet(), emailModel.getExistingEmails());
    }

    @Test
    public void hasEmail_nullEmail_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        emailModel.hasEmail(null);
    }

    @Test
    public void hasEmail_emailNotInEmailModel_returnsFalse() {
        assertFalse(emailModel.hasEmail(MEETING_EMAIL.getSubject()));
    }

    @Test
    public void hasEmail_emailInEmailModel_returnsTrue() {
        emailModel.saveComposedEmail(MEETING_EMAIL);
        assertTrue(emailModel.hasEmail(MEETING_EMAIL.getSubject()));
    }
}
