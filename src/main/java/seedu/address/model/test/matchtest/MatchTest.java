package seedu.address.model.test.matchtest;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.model.AddMatchTestResultEvent;
import seedu.address.commons.events.ui.FlashMatchOutcomeEvent;
import seedu.address.commons.events.ui.ShowTriviaTestResultEvent;
import seedu.address.model.ReadOnlyTriviaBundle;
import seedu.address.model.card.Answer;
import seedu.address.model.card.Card;
import seedu.address.model.card.Question;
import seedu.address.model.test.TriviaTest;
import seedu.address.model.topic.Topic;
import seedu.address.ui.UiPart;
import seedu.address.ui.test.TriviaTestPage;
import seedu.address.ui.test.TriviaTestResultPage;
import seedu.address.ui.test.matchtest.MatchTestPage;
import seedu.address.ui.test.matchtest.MatchTestResultPage;

/**
 * Represents a trivia test that is started by the user.
 * For a {@code MatchTest} to start, there must be more than 1 cards related to the topic that is specified in the test.
 */
public class MatchTest extends TriviaTest {
    public static final String MESSAGE_MATCH_TEST_CONSTRAINS = "Matching test needs more than 1 card with the"
            + " corresponding topic to proceed.";

    private final List<MatchAttempt> attempts;

    public MatchTest(Topic tag, ReadOnlyTriviaBundle triviaBundle) {
        super(tag, triviaBundle);

        checkArgument(isValidMatchTest(), MESSAGE_MATCH_TEST_CONSTRAINS);
        attempts = new ArrayList<>();
    }

    /**
     * The logic associated to matching a question and a answer.
     *
     * @param questionIndex The display index of the question to match.
     * @param answerIndex The display index of the answer to match.
     * @return a boolean which signify whether the match is success or failure.
     * @throws IndexOutOfBoundsException when the given question and answer index is not within the range of
     * existing questions' and answers' indexes.
     */
    public boolean match(Index questionIndex, Index answerIndex) throws IndexOutOfBoundsException {
        MatchAttempt attempt = addAttempt(questionIndex, answerIndex);
        postOutcomeOfMatch(attempt);

        if (!attempt.isCorrect()) {
            return false;
        }

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    respondToCorrectAttempt(attempt);
                });
            }
        }, UiPart.FLASH_TIME);
        return true;
    }

    public List<MatchAttempt> getAttempts() {
        return attempts;
    }

    /**
     * Responds to an correct attempt accordingly.
     */
    public void respondToCorrectAttempt(MatchAttempt attempt) {
        assert attempt.isCorrect();

        removeCardFromUi(attempt);
        if (isEndOfTest()) {
            stopTest();
            EventsCenter.getInstance().post(new ShowTriviaTestResultEvent(getResultPage()));
            EventsCenter.getInstance().post(new AddMatchTestResultEvent(this));
        }
    }

    /**
     * Add an attempt to the matching test.
     *
     * @param questionIndex The index of the question to match.
     * @param answerIndex The index of the answer to match.
     * @return the new Matching attempt.
     * @throws IndexOutOfBoundsException when the given index is out of range of the given answers or questions.
     */
    private MatchAttempt addAttempt(Index questionIndex, Index answerIndex) throws IndexOutOfBoundsException {
        Question question = questions.get(questionIndex.getZeroBased());
        Answer answer = answers.get(answerIndex.getZeroBased());

        Card cardWithQuestion = cards.stream()
                .filter(card -> card.getQuestion().equals(question))
                .findFirst()
                .orElseThrow(IndexOutOfBoundsException::new);
        Card cardWithAnswer = cards.stream()
                .filter(card -> card.getAnswer().equals(answer))
                .findFirst()
                .orElseThrow(IndexOutOfBoundsException::new);

        MatchAttempt newAttempt = new MatchAttempt(cardWithQuestion, cardWithAnswer);
        attempts.add(newAttempt);
        return newAttempt;
    }

    /**
     * Remove the involved card that is answered correctly from the UI.
     * @param attempt The attempt that was made by the user in the matching test.
     */
    private void removeCardFromUi(MatchAttempt attempt) {
        assert attempt.isCorrect(); // Ensure that attempt is correct before removing.

        questions.remove(attempt.getAttemptedCard().getQuestion());
        answers.remove(attempt.getCardWithAnswer().getAnswer());
    }

    /**
     * Will create an UI event to indicate on the UI on whether the match is successful or not.
     * @param attempt The attempt of that match command.
     */
    private void postOutcomeOfMatch(MatchAttempt attempt) {
        int indexOfQuestion = getQuestions().indexOf(attempt.getAttemptedCard().getQuestion());
        int indexOfAnswer = getAnswers().indexOf(attempt.getCardWithAnswer().getAnswer());
        EventsCenter.getInstance().post(new FlashMatchOutcomeEvent(indexOfQuestion, indexOfAnswer,
                attempt.isCorrect()));
    }

    private boolean isValidMatchTest() {
        return super.getQuestions().size() > 1;
    }

    private boolean isEndOfTest() {
        return questions.isEmpty() && answers.isEmpty();
    }

    @Override
    public void startTest() {
        duration = 0;
        timer = new Timer();
        DecimalFormat timerFormat = new DecimalFormat("#.#");
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                duration = Double.parseDouble(timerFormat.format(duration + 0.1));
                System.out.println("Seconds passed: " + duration);
            }
        };

        timer.scheduleAtFixedRate(task, 0, 100);

    }

    @Override
    public void stopTest() {
        if (isEndOfTest()) {
            isCompleted = true;
        }
        timer.cancel();
    }

    @Override
    public TriviaTestPage getTestingPage() {
        return new MatchTestPage(this);
    }

    @Override
    public TriviaTestResultPage getResultPage() {
        return new MatchTestResultPage(this);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof MatchTest)) {
            return false;
        }

        // state check
        MatchTest other = (MatchTest) obj;
        return cards.equals(other.cards) && attempts.equals(other.attempts);
    }
}
