package seedu.address.model.card;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class QuestionTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Answer(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidAnswer = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Answer(invalidAnswer));
    }

    @Test
    public void isValidQuestion() {
        // null answer
        Assert.assertThrows(NullPointerException.class, () -> Answer.isValidAnswer(null));

        // invalid question
        assertFalse(Answer.isValidAnswer("")); // empty string
        assertFalse(Answer.isValidAnswer(" ")); // spaces only

        // valid question
        assertTrue(Answer.isValidAnswer("answers are great!")); // alphabets only
        assertTrue(Answer.isValidAnswer("1234")); // numbers only
        assertTrue(Answer.isValidAnswer("question 1 2 3  4")); // alphanumeric characters
        assertTrue(Answer.isValidAnswer("Capital Tan?")); // with capital letters
        assertTrue(Answer.isValidAnswer("*@#*) weird answer but still an answer")); // long names
    }
}
