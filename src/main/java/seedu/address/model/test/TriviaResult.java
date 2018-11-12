package seedu.address.model.test;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Date;
import java.util.List;

import seedu.address.model.topic.Topic;

/**
 * A class that is being used to store the data of the trivia test.
 */
public class TriviaResult {
    public static final String INCOMPLETE_TRIVIA_TEST_MESSAGE =
            "Trivia test have to completed before it can be recorded.";

    private final TestType testType;
    private final Topic topic;
    private final List<? extends Attempt> attempts;
    private final Date testDate;
    private final double duration;


    public TriviaResult(TriviaTest triviaTest) {
        checkArgument(triviaTest.isCompleted(), INCOMPLETE_TRIVIA_TEST_MESSAGE);

        testType = triviaTest.getTestType();
        topic = triviaTest.getTopic();
        attempts = triviaTest.getAttempts();
        testDate = triviaTest.getTestDate();
        duration = triviaTest.getDuration().getValue();
    }

    public TriviaResult(TestType testType, Topic topic, Date testDate, double duration,
                        List<? extends Attempt> attempts) {
        this.testType = testType;
        this.topic = topic;
        this.attempts = attempts;
        this.testDate = testDate;
        this.duration = duration;
    }

    public TestType getTestType() {
        return testType;
    }

    public Topic getTopic() {
        return topic;
    }

    public List<? extends Attempt> getAttempts() {
        return attempts;
    }

    public Date getTestDate() {
        return testDate;
    }

    public double getDuration() {
        return duration;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TriviaResult)) {
            return false;
        }

        // state check
        TriviaResult o = (TriviaResult) other;
        return testType.equals(o.testType) && topic.equals(o.topic) && testDate.equals(o.testDate)
                && (duration == o.duration) && attempts.equals(o.attempts);
    }

}
