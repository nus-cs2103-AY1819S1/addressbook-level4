package seedu.address.model.card;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AnswerTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Question(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidQuestion = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Question(invalidQuestion));
    }

    @Test
    public void isValidQuestion() {
        // null answer
        Assert.assertThrows(NullPointerException.class, () -> Question.isValidQuestion(null));

        // invalid answer
        assertFalse(Question.isValidQuestion("")); // empty string
        assertFalse(Question.isValidQuestion(" ")); // spaces only

        // valid answer
        assertTrue(Question.isValidQuestion("this is a question")); // alphabets only
        assertTrue(Question.isValidQuestion("1234")); // numbers only
        assertTrue(Question.isValidQuestion("question 1 2 3  4")); // alphanumeric characters
        assertTrue(Question.isValidQuestion("Capital Tan?")); // with capital letters
        assertTrue(Question.isValidQuestion("*@#*) weird question but still a question")); // long names
    }
}
