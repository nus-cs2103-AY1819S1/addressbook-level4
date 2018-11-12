package seedu.address.model.test.openendedtest;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import seedu.address.model.ReadOnlyTriviaBundle;
import seedu.address.model.card.Card;
import seedu.address.model.test.Attempt;
import seedu.address.model.test.TestType;
import seedu.address.model.test.TriviaTest;
import seedu.address.model.topic.Topic;
import seedu.address.ui.test.TriviaTestPage;
import seedu.address.ui.test.TriviaTestResultPage;
import seedu.address.ui.test.openendedtest.OpenEndedTestPage;
import seedu.address.ui.test.openendedtest.OpenEndedTestResultPage;

/**
 * Represents a trivia test that is started by the user.
 * For a {@code OpenEndedTest} to start, there must be at least 1 card related to the topic
 * that is specified in the test.
 */
public class OpenEndedTest extends TriviaTest {
    public static final String MESSAGE_TEST_CONSTRAINS = "OpenEndedTest needs at least 1 card with the"
            + " corresponding topic to proceed.";

    public final TestType testType = TestType.OPEN_ENDED_TEST;

    private List<Attempt> attempts;


    private ArrayList<Card> shuffledCards;


    private Card currCard;

    private String userAnswer;

    public OpenEndedTest(Topic tag, ReadOnlyTriviaBundle triviaBundle) {
        super(tag, triviaBundle);

        this.shuffledCards = shuffleCards(cards);

        attempts = new ArrayList<>();

        checkArgument(isValidTest(), MESSAGE_TEST_CONSTRAINS);

        currCard = getNextCard();
    }

    /**
     * Retrieve a randomized list of cards
     * @param cards The cards to shuffle from.
     * @return an list of cards
     */
    private ArrayList<Card> shuffleCards(List<Card> cards) {
        ArrayList<Card> shuffledCards = new ArrayList<>(cards);
        Collections.shuffle(shuffledCards);
        return shuffledCards;
    }

    /**
     * Retrieves the next card from shuffledCards, and removes it from shuffledCards
     * @return card
     */
    private Card getNextCard() {
        if (shuffledCards.size() == 0) {
            return null;
        }
        Card nextCard = shuffledCards.get(0);
        shuffledCards.remove(0);
        return nextCard;
    }

    @Override
    public void startTest() {
        startTimer();
    }

    @Override
    public void stopTest() {
        timer.stop();
    }

    public void recordAnswer(String userInput) {
        this.userAnswer = userInput;
    }

    @Override
    public TestType getTestType() {
        return testType;
    }

    @Override
    public List<Attempt> getAttempts() {
        return attempts;
    }

    public ArrayList<Card> getShuffledCards() {
        return shuffledCards;
    }

    public Card getCurrCard() {
        return currCard;
    }

    private boolean isValidTest() {
        return shuffledCards.size() >= 1;
    }

    /**
     * Creates a new attempt with the question, answer, user answer and whether it was right or wrong
     * @param in The user input to indicate if the user answered correctly or wrong
     * @return A boolean value corresponding to the correctness of the attempt
     */
    public boolean addAttempt(char in) {
        boolean isCorrect = (in == 'y' || in == 'Y');
        Attempt currAttempt = new Attempt(currCard, this.userAnswer, isCorrect);
        attempts.add(currAttempt);
        return isCorrect;
    }

    public void advanceCard() {
        currCard = getNextCard();
    }

    @Override
    public boolean isCompleted() {
        return (this.shuffledCards.size() == 0);
    }

    /**
     * Retrieves total number of correct attempts currently in attempts
     * @return int of correct attempts
     */
    public int getCorrectAttempts() {
        int correct = 0;
        for (int i = 0; i < this.attempts.size(); i += 1) {
            if (attempts.get(i).isCorrect()) {
                correct += 1;
            }
        }
        return correct;
    }

    @Override
    public Supplier<? extends TriviaTestPage> getTestingPage() {
        return () -> new OpenEndedTestPage(this);
    }

    @Override
    public Supplier<? extends TriviaTestResultPage> getResultPage() {
        return () -> new OpenEndedTestResultPage(this);
    }

}
