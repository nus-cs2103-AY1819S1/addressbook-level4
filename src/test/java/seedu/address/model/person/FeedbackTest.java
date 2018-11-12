package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class FeedbackTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Feedback(null));
    }

    @Test
    public void constructor_invalidFeedback_throwsIllegalArgumentException() {
        String invalidFeedback = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Feedback(invalidFeedback));
    }

    @Test
    public void isValidFeedback() {
        // null feedback
        Assert.assertThrows(NullPointerException.class, () -> Feedback.isValidFeedback(null));

        // invalid feedback
        assertFalse(Feedback.isValidFeedback("")); // empty string
        assertFalse(Feedback.isValidFeedback(" ")); // spaces only

        // valid feedback
        assertTrue(Feedback.isValidFeedback("Work harder."));
        assertTrue(Feedback.isValidFeedback("-")); // one character
        assertTrue(Feedback.isValidFeedback("I'm really impressed by your work performance. "
                                            + "Keep it up!")); // long feedback
    }

    @Test
    public void hasNoProfanityFeedback() {
        // feedback with profanity --> rejected
        assertFalse(Feedback.hasNoProfanity("fuck")); // one profanity
        assertFalse(Feedback.hasNoProfanity("ass")); // hard coded profanity
        assertFalse(Feedback.hasNoProfanity("fuck asshole")); // profanities split with space
        assertFalse(Feedback.hasNoProfanity("zkanasaiz")); // profanities within word
        assertFalse(Feedback.hasNoProfanity("You very kpkb, nobody ask you to stfu meh "
            + "jibai pubor kia")); // many profanities

        // clean feedback --> accepted
        assertTrue(Feedback.hasNoProfanity("Work harder."));
        assertTrue(Feedback.hasNoProfanity("pakistan")); // paki should be rejected but ignore check on pakistan
        assertTrue(Feedback.hasNoProfanity("What happened to you? You were really rocking it last month, "
                + "but it is evident that you are sloppy these few days.")); // long feedback
    }

    @Test
    public void equals() {
        Feedback positiveFeedback = new Feedback("You are good!");
        Feedback negativeFeedback = new Feedback("You are bad!");

        // same object -> returns true
        assertTrue(positiveFeedback.equals(positiveFeedback));

        // null -> returns false
        assertFalse(positiveFeedback.equals(null));

        // different type -> returns false
        assertFalse(positiveFeedback.equals("0"));

        // different rating -> returns false
        assertFalse(positiveFeedback.equals(negativeFeedback));
    }
}
