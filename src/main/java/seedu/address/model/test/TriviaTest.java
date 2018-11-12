package seedu.address.model.test;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.util.Duration;
import seedu.address.model.ReadOnlyTriviaBundle;
import seedu.address.model.card.Card;
import seedu.address.model.card.TopicIsKeywordPredicate;
import seedu.address.model.topic.Topic;
import seedu.address.ui.test.TriviaTestPage;
import seedu.address.ui.test.TriviaTestResultPage;

/**
 * An abstract model for the different kinds tests. Require a Topic and Trivia Bundle to start a test.
 */
public abstract class TriviaTest {
    protected final Topic topic;
    protected final Date testDate;

    protected final ObservableList<Card> cards;

    protected boolean isCompleted;
    protected SimpleDoubleProperty duration;
    protected Timeline timer;

    public TriviaTest(Topic topic, ReadOnlyTriviaBundle triviaBundle) {
        this.topic = topic;
        testDate = new Date();
        duration = new SimpleDoubleProperty(0);
        isCompleted = false;
        cards = triviaBundle.getListOfCardFilteredByTopic(new TopicIsKeywordPredicate(topic.topicName));
    }

    /**
     * Starts the timer of the Trivia Test.
     */
    protected void startTimer() {
        DecimalFormat timerFormat = new DecimalFormat("#.#");
        timer = new Timeline(new KeyFrame(Duration.seconds(0.1), ev -> {
            duration.setValue(Double.parseDouble(timerFormat.format(duration.getValue() + 0.1)));
        }));
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();
    }

    public abstract void startTest();

    public abstract void stopTest();

    public abstract List<? extends Attempt> getAttempts();

    public abstract TestType getTestType();

    public abstract Supplier<? extends TriviaTestPage> getTestingPage();

    public abstract Supplier<? extends TriviaTestResultPage> getResultPage();

    public Topic getTopic() {
        return topic;
    }

    public Date getTestDate() {
        return testDate;
    }

    public SimpleDoubleProperty getDuration() {
        return duration;
    }

    public List<Card> getCardsTested() {
        return cards;
    }

    public boolean isCompleted() {
        return isCompleted;
    }
}
