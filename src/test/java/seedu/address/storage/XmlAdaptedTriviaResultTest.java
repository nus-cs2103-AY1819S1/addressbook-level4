package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_PHYSICS;
import static seedu.address.storage.XmlAdaptedTriviaResult.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalAttempts.FLAT_EARTH_CORRECT_ATTEMPT;
import static seedu.address.testutil.TypicalAttempts.FLAT_EARTH_WRONG_ATTEMPT;
import static seedu.address.testutil.TypicalTriviaResults.RESULT_ATTEMPT_ALL_CORRECT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.DateUtil;
import seedu.address.model.test.TestType;
import seedu.address.model.topic.Topic;
import seedu.address.testutil.Assert;

public class XmlAdaptedTriviaResultTest {

    private static final String INVALID_TEST_TYPE = "Non existent Test";
    private static final String INVALID_TOPIC = "#what?";
    private static final String INVALID_DATE = "01 Jun 2016, 42:13PM";
    private static final String INVALID_DURATION = "13.2aad";

    private static final String VALID_TEST_TYPE = TestType.MATCH_TEST.toString();
    private static final String VALID_TOPIC = VALID_TOPIC_PHYSICS;
    private static final String VALID_DATE = "01 Jun 2016, 04:13 PM";
    private static final String VALID_DURATION = "11.5";
    private static final List<XmlAdaptedAttempt> VALID_ATTEMPTS = new ArrayList<>(Arrays.asList(
            new XmlAdaptedAttempt(FLAT_EARTH_CORRECT_ATTEMPT), new XmlAdaptedAttempt(FLAT_EARTH_WRONG_ATTEMPT)));


    @Test
    public void toModelType_validResult_returnsTriviaResult() throws Exception {
        XmlAdaptedTriviaResult result = new XmlAdaptedTriviaResult(RESULT_ATTEMPT_ALL_CORRECT);
        assertEquals(RESULT_ATTEMPT_ALL_CORRECT, result.toModelType());
    }

    @Test
    public void toModelType_invalidTestType_throwsIllegalValueException() {
        XmlAdaptedTriviaResult result =
                new XmlAdaptedTriviaResult(INVALID_TEST_TYPE, VALID_TOPIC, VALID_DATE, VALID_DURATION, VALID_ATTEMPTS);
        String expectedMessage = TestType.MESSAGE_TEST_TYPE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, result::toModelType);
    }

    @Test
    public void toModelType_nullTestType_throwsIllegalValueException() {
        XmlAdaptedTriviaResult result =
                new XmlAdaptedTriviaResult(null, VALID_TOPIC, VALID_DATE, VALID_DURATION, VALID_ATTEMPTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, TestType.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, result::toModelType);
    }

    @Test
    public void toModelType_invalidTopic_throwsIllegalValueException() {
        XmlAdaptedTriviaResult result =
                new XmlAdaptedTriviaResult(VALID_TEST_TYPE, INVALID_TOPIC, VALID_DATE, VALID_DURATION, VALID_ATTEMPTS);
        String expectedMessage = Topic.MESSAGE_TOPIC_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, result::toModelType);
    }

    @Test
    public void toModelType_nullTopic_throwsIllegalValueException() {
        XmlAdaptedTriviaResult result =
                new XmlAdaptedTriviaResult(VALID_TEST_TYPE, null, VALID_DATE, VALID_DURATION, VALID_ATTEMPTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Topic.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, result::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        XmlAdaptedTriviaResult result =
                new XmlAdaptedTriviaResult(VALID_TEST_TYPE, VALID_TOPIC, INVALID_DATE, VALID_DURATION, VALID_ATTEMPTS);
        String expectedMessage = DateUtil.MESSAGE_DATE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, result::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        XmlAdaptedTriviaResult result =
                new XmlAdaptedTriviaResult(VALID_TEST_TYPE, VALID_TOPIC, null, VALID_DURATION, VALID_ATTEMPTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, result::toModelType);
    }

    @Test
    public void toModelType_invalidDuration_throwsIllegalValueException() {
        XmlAdaptedTriviaResult result =
                new XmlAdaptedTriviaResult(VALID_TEST_TYPE, VALID_TOPIC, VALID_DATE, INVALID_DURATION, VALID_ATTEMPTS);
        String expectedMessage = "Duration: " + NumberFormatException.class.getSimpleName();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, result::toModelType);
    }

    @Test
    public void toModelType_nullDuration_throwsIllegalValueException() {
        XmlAdaptedTriviaResult result =
                new XmlAdaptedTriviaResult(VALID_TEST_TYPE, VALID_TOPIC, VALID_DATE, null, VALID_ATTEMPTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Duration");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, result::toModelType);
    }

}
