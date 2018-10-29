package seedu.address.model.test.openendedtest;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.ReadOnlyTriviaBundle;
import seedu.address.model.card.Answer;
import seedu.address.model.card.Card;
import seedu.address.model.card.Question;
import seedu.address.model.test.TriviaTest;
import seedu.address.model.topic.Topic;

/**
 * Represents a trivia test that is started by the user.
 * For a {@code OpenEndedTest} to start, there must be more than 1 cards related to the topic
 * that is specified in the test.
 */
public abstract class OpenEndedTest extends TriviaTest {
    public static final String MESSAGE_TEST_CONSTRAINS = "OpenEndedTest needs more than 1 card with the"
            + " corresponding topic to proceed.";

    private final ObservableList<Question> shuffledQuestions;
    private final ObservableList<Answer> shuffledAnswers;

    public OpenEndedTest(Topic tag, ReadOnlyTriviaBundle triviaBundle) {
        super(tag, triviaBundle);

        shuffledQuestions = getQuestions(cards);
        shuffledAnswers = getAnswers(cards);

        checkArgument(isValidTest(), MESSAGE_TEST_CONSTRAINS);
    }

    public ObservableList<Question> getQuestions() {
        return this.shuffledQuestions;
    }

    /**
     * Retrieve a randomized modifiable observable list of questions to allow changes in the UI.
     * @param cards The cards to retrieve the questions from.
     * @return an observable list of questions
     */
    private ObservableList<Question> getQuestions(List<Card> cards) {
        List<Question> questions = cards.stream()
                .map(Card::getQuestion)
                .collect(Collectors.toList());
        Collections.shuffle(questions);
        return FXCollections.observableList(questions);
    }

    public ObservableList<Answer> getAnswers() {
        return this.shuffledAnswers;
    }

    /**
     * Retrieve a randomized modifiable observable list of answers to allow changes in the UI.
     * @param cards The cards to retrieve the answers from.
     * @return an observable list of answers
     */
    private ObservableList<Answer> getAnswers(List<Card> cards) {
        List<Answer> answers = cards.stream()
                .map(Card::getAnswer)
                .collect(Collectors.toList());
        Collections.shuffle(answers);
        return FXCollections.observableList(answers);
    }

    @Override
    public void startTest() {

    }

    @Override
    public void stopTest() {

    }

    private boolean isValidTest() {
        return getQuestions().size() > 1;
    }

}
