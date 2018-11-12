package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_EARTH_FLAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_PM_OF_SG;
import static seedu.address.testutil.TypicalCards.Q_FLAT_EARTH;
import static seedu.address.testutil.TypicalCards.Q_GIT_COMMIT;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import seedu.address.commons.util.DateUtil;
import seedu.address.model.test.Attempt;
import seedu.address.model.test.TestType;
import seedu.address.model.test.TriviaResult;
import seedu.address.model.topic.Topic;

/**
 * A utility class to build TriviaResult object.
 */
public class TriviaResultBuilder {

    public static final String DEFAULT_TEST_TYPE = "MATCH_TEST";
    public static final String DEFAULT_TOPIC = "PHYSICS";
    public static final String DEFAULT_DATE = "31 Jan 2006, 08:30 AM";
    public static final String DEFAULT_DURATION = "11.5";
    public static final List<Attempt> DEFAULT_ATTEMPTS = new ArrayList<>(Arrays.asList(
            new Attempt(Q_FLAT_EARTH, VALID_ANSWER_EARTH_FLAT, true),
            new Attempt(Q_GIT_COMMIT, VALID_ANSWER_PM_OF_SG, false)));

    private TestType testType;
    private Topic topic;
    private Date testDate;
    private double duration;
    private List<? extends Attempt> attempts;

    public TriviaResultBuilder() {
        testType = TestType.valueOf(DEFAULT_TEST_TYPE);
        topic = new Topic(DEFAULT_TOPIC);
        duration = Double.valueOf(DEFAULT_DURATION);
        attempts = DEFAULT_ATTEMPTS;
        try {
            testDate = DateUtil.parse(DEFAULT_DATE);
        } catch (ParseException e) {
            System.out.println("Error in parsing default date");
        }
    }

    /**
     * Initializes the TriviaResultBuilder with the data of {@code triviaResultCopy}.
     */
    public TriviaResultBuilder(TriviaResult triviaResultToCopy) {
        testType = triviaResultToCopy.getTestType();
        topic = triviaResultToCopy.getTopic();
        attempts = triviaResultToCopy.getAttempts();
        testDate = triviaResultToCopy.getTestDate();
        duration = triviaResultToCopy.getDuration();
    }

    /**
     * Sets the {@code testType} of the {@code TriviaResult} that we are building.
     */
    public TriviaResultBuilder withTestType(String testType) {
        try {
            this.testType = TestType.valueOf(testType);
        } catch (IllegalArgumentException e) {
            System.out.println(TestType.MESSAGE_TEST_TYPE_CONSTRAINTS);
        }
        return this;
    }

    /**
     * Sets the {@code topic} of the {@code TriviaResult} that we are building.
     */
    public TriviaResultBuilder withTopic(String topic) {
        this.topic = new Topic(topic);
        return this;
    }

    /**
     * Sets the {@code testDate} of the {@code TriviaResult} that we are building.
     */
    public TriviaResultBuilder withTestDate(String testDate) {
        try {
            this.testDate = DateUtil.parse(testDate);
        } catch (ParseException e) {
            System.out.println("Error in parsing the given date.");
        }
        return this;
    }

    /**
     * Sets the {@code duration} of the {@code TriviaResult} that we are building.
     */
    public TriviaResultBuilder withDuration(String duration) {
        this.duration = Double.valueOf(duration);
        return this;
    }

    /**
     * Sets the {@code attempts} of the {@code TriviaResult} that we are building.
     */
    public TriviaResultBuilder withAttempts(List<? extends Attempt> attempts) {
        this.attempts = attempts;
        return this;
    }

    public TriviaResult build() {
        return new TriviaResult(testType, topic, testDate, duration, attempts);
    }

}
