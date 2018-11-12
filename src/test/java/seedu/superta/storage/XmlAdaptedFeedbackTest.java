package seedu.superta.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import seedu.superta.model.student.Feedback;

public class XmlAdaptedFeedbackTest {
    @Test
    public void feedbackEqualsItself_success() {
        XmlAdaptedFeedback feedback = new XmlAdaptedFeedback(new Feedback("Hello"));
        assertEquals(feedback, feedback);
    }

    @Test
    public void feedbackNotEquals_success() {
        Feedback source = new Feedback("Hello");
        Feedback sourceTwo = new Feedback("Not hello");
        XmlAdaptedFeedback feedback = new XmlAdaptedFeedback(source);
        XmlAdaptedFeedback feedbackTwo = new XmlAdaptedFeedback(sourceTwo);
        assertNotEquals(feedback, feedbackTwo);
    }

    @Test
    public void feedback_toModelType_success() {
        Feedback source = new Feedback("Hello");
        XmlAdaptedFeedback feedback = new XmlAdaptedFeedback(source);
        assertEquals(feedback.toModelType(), source);
    }
}
