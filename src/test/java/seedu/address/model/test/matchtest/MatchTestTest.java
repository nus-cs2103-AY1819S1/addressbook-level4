package seedu.address.model.test.matchtest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_GIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_NO_TOPIC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_PHYSICS;
import static seedu.address.testutil.MatchTestUtil.startMatchTest;
import static seedu.address.testutil.TypicalCards.Q_DENSITY_FORMULA;
import static seedu.address.testutil.TypicalCards.Q_EARTH_ROUND;
import static seedu.address.testutil.TypicalCards.Q_FORCE_FORMULA;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.test.TestType;
import seedu.address.model.test.TriviaResults;
import seedu.address.model.topic.Topic;
import seedu.address.testutil.MatchIndexPair;
import seedu.address.testutil.MatchTestUtil;
import seedu.address.testutil.TypicalCards;
import seedu.address.testutil.TypicalPersons;

public class MatchTestTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager model;
    private MatchTest matchTest;

    /** The indexes of matching question and answer from the test. */
    private MatchIndexPair earthRoundIndexes;
    private MatchIndexPair forceFormulaIndexes;
    private MatchIndexPair densityFormulaIndexes;

    @Before
    public void setUp() {
        model = new ModelManager(TypicalPersons.getTypicalAddressBook(), TypicalCards.getTypicalTriviaBundle(),
                new TriviaResults(), new UserPrefs());

        // There will be 3 cards in this matchTest
        matchTest = startMatchTest(model, new Topic(VALID_TOPIC_PHYSICS));

        earthRoundIndexes = new MatchIndexPair(matchTest, Q_EARTH_ROUND);
        forceFormulaIndexes = new MatchIndexPair(matchTest, Q_FORCE_FORMULA);
        densityFormulaIndexes = new MatchIndexPair(matchTest, Q_DENSITY_FORMULA);
    }

    @After
    public void cleanUp() {
        if (model.isInTestingState()) {
            model.stopTriviaTest();
        }
    }

    @Test
    public void invalid_matchTest() {
        thrown.expect(IllegalArgumentException.class);
        matchTest = new MatchTest(new Topic(VALID_TOPIC_NO_TOPIC), model.getTriviaBundle());
    }

    @Test
    public void test_matchPass() {
        assertTrue(model.matchQuestionAndAnswer(earthRoundIndexes.getQIndex(), earthRoundIndexes.getAIndex()));
        assertTrue(model.matchQuestionAndAnswer(forceFormulaIndexes.getQIndex(), forceFormulaIndexes.getAIndex()));
        assertTrue(model.matchQuestionAndAnswer(densityFormulaIndexes.getQIndex(), densityFormulaIndexes.getAIndex()));

        assertEquals(matchTest.getAttempts().size(), 3);
    }

    @Test
    public void test_matchFail() {
        assertFalse(model.matchQuestionAndAnswer(earthRoundIndexes.getQIndex(), forceFormulaIndexes.getAIndex()));
        assertFalse(model.matchQuestionAndAnswer(forceFormulaIndexes.getQIndex(), densityFormulaIndexes.getAIndex()));
        assertFalse(model.matchQuestionAndAnswer(densityFormulaIndexes.getQIndex(), earthRoundIndexes.getAIndex()));
    }

    @Test
    public void test_responseToCorrectMatchAttempt() {
        // makes sure the cards that are involved in the correct attempts are removed.
        matchTest.respondToCorrectAttempt(MatchTestUtil.generateCorrectMatchAttempt(earthRoundIndexes));

        assertEquals(matchTest.getQuestions().size(), 2);
        assertEquals(matchTest.getAnswers().size(), 2);

        matchTest.respondToCorrectAttempt(MatchTestUtil.generateCorrectMatchAttempt(densityFormulaIndexes));
        matchTest.respondToCorrectAttempt(MatchTestUtil.generateCorrectMatchAttempt(forceFormulaIndexes));

        assertEquals(matchTest.getAnswers().size(), 0);
        assertEquals(matchTest.getQuestions().size(), 0);
    }

    @Test
    public void test_isCompletedFlagFalse() {
        // if the ongoing test isn't stopped, isCompleted will be false
        assertFalse(matchTest.isCompleted());

        // If there are existing unanswered questions, even if test is stopped, isCompleted flag should be false.
        matchTest.match(earthRoundIndexes.getQIndex(), earthRoundIndexes.getAIndex());
        model.stopTriviaTest();
        assertFalse(matchTest.isCompleted());
    }

    @Test
    public void test_isCompletedFlagTrue() {
        // If there are no more existing unanswered questions, isCompleted flag should be true.
        matchTest.respondToCorrectAttempt(MatchTestUtil.generateCorrectMatchAttempt(earthRoundIndexes));
        matchTest.respondToCorrectAttempt(MatchTestUtil.generateCorrectMatchAttempt(forceFormulaIndexes));
        model.matchQuestionAndAnswer(densityFormulaIndexes.getQIndex(), densityFormulaIndexes.getAIndex());
        model.stopTriviaTest();
        assertTrue(matchTest.isCompleted());
        assert(model.getTriviaResultList().size() == 1);
    }

    @Test
    public void test_assertTestType() {
        assertEquals(matchTest.getTestType(), TestType.MATCH_TEST);
    }

    @Test
    public void equals() {
        // same values -> returns true
        MatchTest matchTestCopy = new MatchTest(matchTest.getTopic(), model.getTriviaBundle());
        assertTrue(matchTest.equals(matchTestCopy));

        // same object -> returns true
        assertTrue(matchTest.equals(matchTest));

        // null -> returns false
        assertFalse(matchTest.equals(null));

        // different type -> returns false
        assertFalse(matchTest.equals(5));

        // different cards being tested -> returns false
        assertFalse(matchTest.equals(new MatchTest(new Topic(VALID_TOPIC_GIT), model.getTriviaBundle())));

    }
}
